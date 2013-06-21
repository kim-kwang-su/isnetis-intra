package com.isnet.mgr.service;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.isnet.mgr.common.CommonConstant;
import com.isnet.mgr.dao.EmployeeDao;
import com.isnet.mgr.dao.ModuleDao;

@Service("moduleMailService")
public class ModuleMailServiceImp implements MailService{

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private ModuleDao moduleDao;
	@Autowired
	private CommonCodeBean commonCode;
	
	private @Value("${mail.from}") String from;
	@Override
	public void sendMail(Map<String, Object> paramMap)  {
		logger.debug("["+getClass().getSimpleName()+"] [sendMail] start");
		
		String subject = "모듈 등록[변경] 안내메일";
		List<String> toList = new ArrayList<String>();
		List<Map<String, Object>> employeeList = commonCode.getEmployeeList();
		for(Map<String, Object> employee : employeeList){
			int deptNo = (Integer)employee.get("DEPT_NO");
			if(deptNo == CommonConstant.DEPT_NO_01 || deptNo == CommonConstant.DEPT_NO_02){
				toList.add((String)employee.get("EMAIL"));
			}
		}
		String[] toArray = new String[toList.size()];
		toArray = toList.toArray(toArray);
		
		
		List<String> emailList = employeeDao.select_employee_04();
		String cc = StringUtils.collectionToCommaDelimitedString(emailList);
		
		Map<String, Object> moduleInfo = moduleDao.select_module_03(paramMap);
		String text = getHtml(moduleInfo);
		
		logger.info("["+getClass().getSimpleName()+"] [subject] ["+subject+"]");
		logger.info("["+getClass().getSimpleName()+"] [from] ["+from+"]");
		logger.info("["+getClass().getSimpleName()+"] [to] ["+Arrays.toString(toArray)+"]");
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
				logger.error("["+getClass().getSimpleName()+"] [sendMail] to["+to+"] 메일 발송완료");
			}
			
		}catch(Exception e){
			logger.error("["+getClass().getSimpleName()+"] [sendMail] 메일발송 오류");
		}
		
		logger.debug("["+getClass().getSimpleName()+"] [sendMail] end");
	}
	
	private String getHtml(Map<String, Object> moduleInfo){
		
		StringBuilder text = new StringBuilder();
		text.append("<html>");
		text.append("<body><h3>모듈이 수정[등록]되었습니다. </h3>");
		text.append("<dl>");
		text.append("<dt><b>제품명</b></dt><dd>"+moduleInfo.get("PRODUCT_NAME")+"</dd>");
		text.append("<dt><b>모듈명</b></dt><dd>"+moduleInfo.get("MODULE_NAME")+"</dd>");
		text.append("<dt><b>모듈 수정자</b></dt><dd>"+moduleInfo.get("EMPLOYEE_NAME")+"</dd>");
		text.append("<dt><b>파일버젼</b></dt><dd>"+moduleInfo.get("FILE_VERSION")+"</dd>");
		text.append("<dt><b>수정내용</b></dt><dd>"+String.valueOf(moduleInfo.get("UPDATE_MEMO")).replace("\n", "<br />")+"</dd>");
		text.append("</dl>");
		text.append("</body>");
		text.append("</html>");
		
		return text.toString();
	}
}
