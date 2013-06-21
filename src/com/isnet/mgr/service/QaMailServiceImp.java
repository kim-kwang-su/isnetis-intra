package com.isnet.mgr.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.isnet.mgr.common.CommonConstant;
import com.isnet.mgr.dao.EmployeeDao;
import com.isnet.mgr.dao.IssueDao;

@Service("QaMailServiceImp")
public class QaMailServiceImp implements MailService{

	Logger logger = Logger.getLogger(getClass());
	
	@Value("#{config['qa.savedpath']}")
	private String qa_saved_path;
	
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private IssueDao issueDao;
	private @Value("${mail.from}") String default_from;
		
	@Override
	public void sendMail(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [sendMail] start");
		
		int qa_gubun = (Integer)paramMap.get("QA_GUBUN");
		String subject = "QA 안내 메일";
		String from = default_from;
		String from_name = null;
		String from_position = null;
		String to = null;
		String to_name = null;
		String to_position = null;
		String file_name = null;
		int issue_no = 0;
		Map<String, Object> requestInfo = null;
		
		StringBuilder text = new StringBuilder();
		// 송신자 정보 조회하기
		int employee_no = (Integer)paramMap.get("FROM");
		paramMap.put("EMPLOYEE_NO", employee_no);
		List<Map<String, Object>> employeeList = employeeDao.select_employee_02(paramMap);
		if(!employeeList.isEmpty()){
			Map<String, Object> employee = employeeList.get(0);
			from = (String) employee.get("EMAIL");
			from_name = (String) employee.get("EMPLOYEE_NAME");
			from_position = (String)employee.get("DEPT_POSITION");
		}
		
		// 수신자 정보 조회하기
		if(paramMap.containsKey("REQUEST_NO")){
			requestInfo = issueDao.select_issue_13(paramMap);
			employee_no = (Integer)requestInfo.get("DEVELOPER_NO");
			try{
				issue_no = (Integer)requestInfo.get("ISSUE_NO");
			}catch(NullPointerException e){}
			paramMap.put("EMPLOYEE_NO", employee_no);
			employeeList = employeeDao.select_employee_02(paramMap);
			if(!employeeList.isEmpty()){
				Map<String, Object> employee = employeeList.get(0);
				to = (String) employee.get("EMAIL");
				to_name = (String) employee.get("EMPLOYEE_NAME");
				to_position = (String)employee.get("DEPT_POSITION"); 
			}
		}	
		
		if(qa_gubun == CommonConstant.QA_REQUEST_TEST){
			subject = "QA 테스트 [요청] 안내 메일";
			to = from;
			to_name = from_name;
			if(paramMap.containsKey("MODULE_FILE_NAME")){
				file_name = (String)paramMap.get("MODULE_FILE_NAME");
			}
			
			text.append("QA 테스트를 요청합니다\n\n");
			text.append("요청자 : " + from_name + " " + from_position + "\n");
			text.append("요청일 : " + paramMap.get("REQUEST_DATE") + "\n");
			if(issue_no != 0){
				text.append("이슈번호 : " + paramMap.get("ISSUE_NO") + "\n");
			}
			text.append("모듈명 : " + paramMap.get("MODULE_NAME") + "\n");
			text.append("수정내용 : " + String.valueOf(paramMap.get("MODIFY_SUMMARY")) + "\n");
			text.append("영향도 : " + String.valueOf(paramMap.get("EFFECT_SUMMARY")) + "\n");

		}else if(qa_gubun == CommonConstant.QA_ADD_TEST_RESULT){
			subject = "QA 테스트 [결과등록] 안내 메일";
			if(requestInfo.containsKey("RESULT_FILE_NAME")){
				file_name = (String)requestInfo.get("RESULT_FILE_NAME");
			}
			
			if(issue_no != 0){
				text.append("이슈번호 ["+issue_no+"]의 QA 테스트 결과입니다.\n\n");
			}else{
				text.append("QA 테스트 결과입니다.\n\n");
			}
			text.append("요청자 : "+to_name+" " +to_position +"\n");
			text.append("요청일 : "+requestInfo.get("REQUEST_DATE")+"\n");
			text.append("테스트 결과 : "+paramMap.get("TEST_RESULT")+"\n");
			text.append("테스트 날짜 : "+paramMap.get("TEST_DATE")+"\n");
			text.append("테스트 내용 : "+String.valueOf(paramMap.get("TEST_SUMMARY")) + "\n");
			
		}else if(qa_gubun == CommonConstant.QA_ADD_BUG_REPORT){
			subject = "QA 테스트 [버그리포트 등록] 안내 메일";
			Map<String, Object> report = issueDao.select_issue_14(paramMap);
			
			if(report.containsKey("FILE_NAME")){
				file_name = (String)report.get("FILE_NAME");
			}
			
			text.append("모듈명 : "+report.get("MODULE_NAME")+"\n");
			text.append("버그빈도 : "+report.get("BUG_FREQUENCY") +"\n");
			text.append("중요도 : "+report.get("BUG_IMPORTANCE") +"\n");
			text.append("QA 일자 : "+report.get("QA_DATE") +"\n");
			text.append("개발 담당자 : "+report.get("EMPLOYEE_NAME") +"\n");
			text.append("통보 일자 : "+report.get("NOTICE_DATE") +"\n");
			text.append("해결여부 : "+report.get("RESOLVE_YN") +"\n");
			text.append("버그요약 정보 : "+String.valueOf(report.get("BUG_SUMMARY")) +"\n");
		}
		
		// 참조자 조회
		List<String> emailList = employeeDao.select_employee_04();
		String cc = StringUtils.collectionToCommaDelimitedString(emailList);

		logger.info("["+getClass().getSimpleName()+"] [sendMail] subject["+subject+"]");
		logger.info("["+getClass().getSimpleName()+"] [sendMail] from["+from+"]");
		logger.info("["+getClass().getSimpleName()+"] [sendMail] to["+to+"]");
		logger.info("["+getClass().getSimpleName()+"] [sendMail] cc["+cc+"]");
		logger.info("["+getClass().getSimpleName()+"] [sendMail] file_name["+file_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [sendMail] text["+text.toString()+"]");

		try{
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setSubject(subject);
			
			helper.setFrom(from);
			helper.setTo(to);
			helper.setCc(cc.split(","));
			
			helper.setText(text.toString());					
			
			if(file_name != null){
				FileSystemResource file = new FileSystemResource(new File(qa_saved_path, file_name));
				helper.addAttachment(new String(file_name.substring(14).getBytes("UTF-8"), "8859_1"), file);
			}
			mailSender.send(message);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [sendMail] 메일발송 오류");
		}
		
	}
}
