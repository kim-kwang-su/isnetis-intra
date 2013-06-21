package com.isnet.mgr.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.isnet.mgr.common.CommonConstant;
import com.isnet.mgr.common.DateUtil;
import com.isnet.mgr.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private MappingJacksonJsonView jsonView;

	
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(value="/employee_01.isnet", method=RequestMethod.GET)
	public ModelAndView employee_01(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/employee/employee_01");
		mav.addObject("deptList", employeeService.getDeptList());
		
		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/update_01.isnet", method=RequestMethod.POST)
	public ModelAndView update_01(@RequestParam(value="LOGIN_PASSWORD", required=false )String login_password,
								@RequestParam("DEPT_NO")int dept_no,
								HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [update_01] start");
		logger.info("["+getClass().getSimpleName()+"] [update_01] login_password ["+login_password+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_01] dept_no ["+dept_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			logger.info("["+getClass().getSimpleName()+"] [add_issue01] employee_no ["+employee_no+"]");
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if(login_password != null){
				String md5_password = DigestUtils.md5DigestAsHex(login_password.getBytes());
				paramMap.put("LOGIN_PASSWORD", md5_password);
			}
			paramMap.put("DEPT_NO", dept_no);
			paramMap.put("EMPLOYEE_NO", employee_no);
			
			employeeService.updateEmployeeInfo(paramMap);
			mav.addObject("isSuccess", true);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("["+getClass().getSimpleName()+"] [update_01] 사용자정보 변경 중 오류 발생");
			mav.addObject("isSuccess", false);
			mav.addObject("error_msg", "사용자정보 변경 중 오류 발생하였습니다.");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_01] end");
		}
		
		return mav;
	}	
	
	@RequestMapping(value="/login.isnet", method=RequestMethod.POST)
	public ModelAndView login(@RequestParam("EMAIL")String email,
								@RequestParam("LOGIN_PASSWORD")String login_pwd,
								HttpSession session){
		
		if(!email.contains("@")){
			email = email + "@isnetis.com";
		}
		
		logger.debug("["+getClass().getSimpleName()+"] [login] start");
		logger.info("["+getClass().getSimpleName()+"] [login] email ["+email+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("EMAIL", email);
		
		try{
			
			Map<String, Object> employee = employeeService.getEmployeeInfo(paramMap);
			logger.debug("["+getClass().getSimpleName()+"] [login] employee_info ["+employee+"]");			
			
			if(employee == null){
				mav.addObject("isSuccess", false);
				mav.addObject("error_msg", "등록되지 않은 사용자입니다.");
			}else{
				if(((String)employee.get("IS_USED")).equals("Y")){
					
					String md5_password = DigestUtils.md5DigestAsHex(login_pwd.getBytes());
					
					if(((String)employee.get("LOGIN_PASSWORD")).equals(md5_password)){
						mav.addObject("isSuccess", true);
						employee.remove("LOGIN_PASSWORD");
						session.setAttribute("LOGIN_USER", employee);
						
						paramMap.clear();
						paramMap.put("EMPLOYEE_NO", employee.get("EMPLOYEE_NO"));
						paramMap.put("LAST_LOGIN_TIME", DateUtil.dateToString(CommonConstant.DATE_FORMAT_05));
						employeeService.updateEmployeeInfo(paramMap);
						
					}else{
						mav.addObject("isSuccess", false);
						mav.addObject("error_msg", "비밀번호가 일치하지 않습니다.");
					}
					
				}else{
					mav.addObject("isSuccess", false);
					mav.addObject("error_msg", "사용 인증을 받지 못한 사용자입니다.");	
				}
				
			}			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [login] 로그인 처리 중 오류발생");
			mav.addObject("isSuccess", false);
			mav.addObject("error_msg", "로그인 처리 중 오류발생가 발생하였습니다.");	
		}
				
		return mav;
	}
}
