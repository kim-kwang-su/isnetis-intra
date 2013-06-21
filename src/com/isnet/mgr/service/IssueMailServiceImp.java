package com.isnet.mgr.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.isnet.mgr.common.CommonCodeBean;
import com.isnet.mgr.dao.EmployeeDao;
import com.isnet.mgr.dao.IssueDao;

@Service("issueMailService")
public class IssueMailServiceImp implements MailService{
	
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private IssueDao issueDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private CommonCodeBean commonCode;
	
	private @Value("${mail.from}") String from;
	
	@Override
	public void sendMail(Map<String, Object> param){
		
		logger.debug("["+getClass().getSimpleName()+"] [sendMail] start");
		
		Map<String, Object> issue = issueDao.select_issue_03(param);		
		List<Map<String, Object>> employees = issueDao.select_issue_04(param);
		List<Map<String, Object>> issue_list = issueDao.select_issue_05(param);
		
		List<String> emailList = employeeDao.select_employee_04();
		String cc = StringUtils.collectionToCommaDelimitedString(emailList);
		
		String subject = "이슈 접수[변경] 안내메일";
		String[] toArray = new String[employees.size()];
		
		for(int index=0; index<employees.size(); index++){
			Map<String, Object> developer = (Map<String, Object>)employees.get(index);
			String email = (String)developer.get("EMAIL");
			toArray[index] = email;
		}
		String text = html(issue, employees, issue_list);
		
		logger.info("["+getClass().getSimpleName()+"] [subject] ["+subject+"]");
		logger.info("["+getClass().getSimpleName()+"] [from] ["+from+"]");
		logger.info("["+getClass().getSimpleName()+"] [to] ["+toArray+"]");
		logger.info("["+getClass().getSimpleName()+"] [cc] ["+cc+"]");
		logger.debug("["+getClass().getSimpleName()+"] [text] ["+text+"]");
		
		try{
			
			boolean isSend = false;
			for(String to : toArray){
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message,false, "utf-8");
				helper.setSubject(subject);
				helper.setFrom(from);
				helper.setTo(to);
				if(!isSend){
					helper.setCc(cc.split(","));
					isSend = true;
				}
				helper.setText(text, true);		
				
				mailSender.send(message);	
				logger.info("["+getClass().getSimpleName()+"] [sendMail] to["+to+"] 메일 발송완료");
			}
			
		}catch(Exception e){
			logger.error("["+getClass().getSimpleName()+"] [sendMail] 메일발송 오류");
		}
		logger.debug("["+getClass().getSimpleName()+"] [sendMail] end");
	}
	
	private String html(Map<String, Object> issue, List<Map<String, Object>> developers, List<Map<String, Object>> issueList){
		StringBuilder text = new StringBuilder();
		
		
		String state_name = (String)issue.get("STATE_NAME");

		List<Map<String, Object>> stateList = commonCode.getStateList();
		StringBuilder stateListStr = new StringBuilder();
		int limit = stateList.size();
		for(int index=0; index<limit; index++){
			Map<String, Object> state = stateList.get(index);
			String name = (String)state.get("STATE_NAME");
			if(state_name.equals(name)){
				stateListStr.append("<strong>");
				stateListStr.append(name);
				stateListStr.append("</strong>");
			}else{
				stateListStr.append(name);
			}
			if(index < limit-1){
				stateListStr.append(">");
			}
		}
		
		String accept_date = String.valueOf((Date)issue.get("ACCEPT_DATE"));
		String employee_name = (String)issue.get("EMPLOYEE_NAME");
		String se_name = "";
		StringBuilder developer_name = new StringBuilder();
		for(Map<String, Object> map : developers ){
			String is_se = (String)map.get("IS_SE");
			String name = (String)map.get("EMPLOYEE_NAME");
			if("Y".equals(is_se)){
				se_name = name;
			}else{
				developer_name.append(name);
				developer_name.append(" ");
			}
		}
		String customer_name = (String)issue.get("CUSTOMER_NAME");
		String product_name = (String)issue.get("PRODUCT_NAME");
		String kind_name = (String)issue.get("KIND_NAME");
		String issue_priority = (String)issue.get("ISSUE_PRIORITY");
		String memo = (String)issue.get("ISSUE_MEMO");
		String remark = (issue.get("ISSUE_REMARK") != null ? (String)issue.get("ISSUE_REMARK") : "");
		memo = memo.replaceAll("\n", "<br />");
		remark = remark.replaceAll("\n", "<br />");
		
		StringBuilder sb = new StringBuilder();
		for(Map<String, Object> map : issueList){
			sb.append("["+String.valueOf((Date)map.get("REGISTER_DATE"))+"]["+(String)map.get("STATE_NAME")+"]["+(String)map.get("PROCESS_MEMO")+"]<br />");
		}
		
		text.append("<html><body style='font-size: small;'><table>");
		text.append("<tr><th style='text-align: right;'>진행상태:</th><td colspan='7'>"+stateListStr.toString()+"</td></tr>");
		text.append("<tr><th style='text-align: right;'>접수일자:</th><td>"+accept_date+"</td><th style='text-align: right;'>접수자:</th><td>"+employee_name+"</td>");
		text.append("<th style='text-align: right;'>담당SE:</th><td>"+se_name+"</td><th style='text-align: right;'>개발자:</th><td>"+developer_name.toString()+"</td></tr>");
		text.append("<tr><th style='text-align: right;'>고객사:</th><td>"+customer_name+"</td><th style='text-align: right;'>제품명:</th><td>"+product_name+"</td></tr>");
		text.append("<tr><th style='text-align: right;'>요청분류:</th><td>"+kind_name+"</td><th style='text-align: right;'>우선순위:</th><td>"+issue_priority+"</td></tr>");
		text.append("</table><dl>");
		text.append("<dt style='font-weight: bold;'>접수내용</dt><dd>"+memo+"</dd>");
		text.append("<dt style='font-weight: bold;'>비고</dt><dd>"+remark+"</dd>");
		text.append("<dt style='font-weight: bold;'>진행상황</dt><dd>"+sb.toString()+"</dd>");
		text.append("</dl></body></html>");
		
		return text.toString();
	}
}
