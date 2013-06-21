package com.isnet.mgr.controller;

import java.util.HashMap;
import java.util.List;
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

import com.isnet.mgr.common.CommonCodeBean;
import com.isnet.mgr.common.CommonConstant;
import com.isnet.mgr.common.DateUtil;
import com.isnet.mgr.common.StringUtil;
import com.isnet.mgr.service.CustomerService;
import com.isnet.mgr.service.DatabaseBackupService;
import com.isnet.mgr.service.EmployeeService;
import com.isnet.mgr.service.ManagerService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	Logger logger = Logger.getLogger(getClass());

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private DatabaseBackupService databaseBackupService;

	@Autowired
	private MappingJacksonJsonView jsonView;
	
	@Autowired
	CommonCodeBean commonCodeBean;

	@RequestMapping(value="/admin_01.isnet", method=RequestMethod.GET)
	public ModelAndView admin_01(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/admin_01");
		mav.addObject("deptList", employeeService.getDeptList());

		return mav;
	}

	@RequestMapping(value="/admin_02.isnet", method=RequestMethod.GET)
	public ModelAndView admin_02(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/admin_02");
	
		List<Map<String, Object>> customerList = customerService.getCustomerList();
		String customerListString = StringUtil.listToString(customerList, "CUSTOMER_NAME", "CUSTOMER_NO");
	
		mav.addObject("customerValues", customerListString);
	
		return mav;
	}

	@RequestMapping(value="/admin_03.isnet", method=RequestMethod.GET)
	public ModelAndView admin_03(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/admin_03");
	
		List<Map<String, Object>> customerList = customerService.getCustomerList();
		String customerListString = StringUtil.listToString(customerList, "CUSTOMER_NAME", "CUSTOMER_NO");
	
		mav.addObject("customerList", customerList);
		mav.addObject("customerValues", customerListString);
	
		return mav;
	}
	
	@RequestMapping(value="/admin_04.isnet", method=RequestMethod.GET)
	public ModelAndView admin_04(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/admin_04");
		
		return mav;
	}

	@RequestMapping(value="/add_employee.isnet", method=RequestMethod.POST)
	public ModelAndView add_employee(@RequestParam("EMPLOYEE_NAME")String employee_name,
			@RequestParam("EMAIL")String email,
			@RequestParam("IS_MAIL_CC")String is_mail_cc,
			@RequestParam("LOGIN_PASSWORD")String login_password,
			@RequestParam("DEPT_NO")int dept_no,
			@RequestParam("DEPT_POSITION")String dept_position){

		logger.debug("["+getClass().getSimpleName()+"] [add_employee] start");

		logger.info("["+getClass().getSimpleName()+"] [add_employee] employee_name ["+employee_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_employee] email ["+email+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_employee] is_mail_cc ["+is_mail_cc+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_employee] login_pasword [***]");
		logger.info("["+getClass().getSimpleName()+"] [add_employee] dept_no ["+dept_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_employee] dept_position ["+dept_position+"]");


		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{

			//login_password = dept_no + login_password + "isnet" + email;

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("EMPLOYEE_NAME", employee_name);
			paramMap.put("EMAIL", email);
			paramMap.put("IS_MAIL_CC", is_mail_cc);
			paramMap.put("LOGIN_PASSWORD", DigestUtils.md5DigestAsHex(login_password.getBytes()));
			paramMap.put("DEPT_NO", dept_no);
			paramMap.put("DEPT_POSITION", dept_position);
			paramMap.put("CREATE_DATE", DateUtil.dateToString(CommonConstant.DATE_FORMAT_05));

			boolean isAdded = employeeService.addEmployee(paramMap);
			if(isAdded){
				mav.addObject("isSuccess", true);
				commonCodeBean.init();				
			}else{
				mav.addObject("isSuccess", false);
				mav.addObject("error_msg", "이미 등록된 사용자이거나, 등록 중 오류가 발생하였습니다.");	
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_employee] 사용자 등록 중 오류발생");
			mav.addObject("isSuccess", false);
			mav.addObject("error_msg", "사용자 등록 중 오류가 발생하였습니다.");	
		}


		logger.debug("["+getClass().getSimpleName()+"] [add_employee] end");
		return mav;
	}

	@RequestMapping(value="/add_customer.isnet", method=RequestMethod.POST)
	public ModelAndView add_customer(@RequestParam("CUSTOMER_NAME")String customer_name,
			@RequestParam("CUSTOMER_NO")int customer_no,
			@RequestParam("ADDR")String addr,
			@RequestParam("TEL")String [] tel,
			@RequestParam("IS_USED")String isUsed){
	
		logger.debug("["+getClass().getSimpleName()+"] [add_customer] start");
		logger.info("["+getClass().getSimpleName()+"] [add_customer] customer_no ["+customer_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_customer] customer_name ["+customer_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_customer] addr ["+addr+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_customer] tel ["+tel.length+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_customer] isUsed ["+isUsed+"]");
	
	
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
	
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("CUSTOMER_NAME", customer_name);
			paramMap.put("IS_USED", isUsed);
	
			if (addr != null && !addr.equals(""))
				paramMap.put("CUSTOMER_ADDRESS", addr);
			else 
				paramMap.put("CUSTOMER_ADDRESS", "");
	
			if (tel != null && (!tel[0].equals("") && !tel[1].equals("") && !tel[2].equals("")))
				paramMap.put("CUSTOMER_TEL", tel[0]+"-" + tel[1] + "-" + tel[2]);
			else 
				paramMap.put("CUSTOMER_TEL", "");
	
	
			if(customer_no == 0) {
				boolean isAdded = customerService.addCustomer(paramMap);
				if(isAdded){
					mav.addObject("isSuccess", true);
					mav.addObject("success_msg", "고객사 등록이 완료 되었습니다.");
					commonCodeBean.init();				
				}else{
					mav.addObject("isSuccess", false);
					mav.addObject("error_msg", "이미 등록된 고객사 이거나, 등록 중 오류가 발생하였습니다.");	
				}
			}else {
				paramMap.put("CUSTOMER_NO", customer_no);
				customerService.updateCustomer(paramMap);
	
				mav.addObject("isSuccess", true);
				mav.addObject("success_msg", "고객사 수정이 완료 되었습니다.");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_customer] 고객사 등록 중 오류발생");
			mav.addObject("isSuccess", false);
			mav.addObject("error_msg", "고객사  등록 중 오류가 발생하였습니다.");	
		}
	
	
		logger.debug("["+getClass().getSimpleName()+"] [add_customer] end");
		return mav;
	}

	@RequestMapping(value="/add_manager.isnet", method=RequestMethod.POST)
	public ModelAndView add_manager(@RequestParam("CUSTOMER_NO")int customer_no,
			@RequestParam("MANAGER_NO")int manager_no,
			@RequestParam("MANAGER_NAME")String manager_name,
			@RequestParam("EMAIL")String [] email,
			@RequestParam("TEL")String [] tel,
			@RequestParam("IS_USED")String isUsed){
	
		logger.debug("["+getClass().getSimpleName()+"] [add_manager] start");
		logger.info("["+getClass().getSimpleName()+"] [add_manager] customer_no ["+customer_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_manager] manager_no ["+manager_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_manager] manager_name ["+manager_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_manager] email ["+email.length+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_manager] tel ["+tel.length+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_manager] isUsed ["+isUsed+"]");
	
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
	
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("CUSTOMER_NO", customer_no);
			paramMap.put("MANAGER_NAME", manager_name);
			paramMap.put("IS_USED", isUsed);
	
			if (email != null && (!email[0].equals("") && !email[1].equals("")))
				paramMap.put("MANAGER_EMAIL", email[0]+"@" + email[1]);
			else 
				paramMap.put("MANAGER_EMAIL", "");
	
			if (tel != null && (!tel[0].equals("") && !tel[1].equals("") && !tel[2].equals("")))
				paramMap.put("MANAGER_TEL", tel[0]+"-" + tel[1] + "-" + tel[2]);
			else 
				paramMap.put("MANAGER_TEL", "");
	
	
			if(manager_no == 0) {
				boolean isAdded = managerService.addManager(paramMap);
				if(isAdded){
					mav.addObject("isSuccess", true);
					mav.addObject("success_msg", "담당자 등록이 완료 되었습니다.");
					commonCodeBean.init();				
				}else{
					mav.addObject("isSuccess", false);
					mav.addObject("error_msg", "이미 등록된 담당자 이거나, 등록 중 오류가 발생하였습니다.");	
				}
			}else {
				paramMap.put("MANAGER_NO", manager_no);
				managerService.updateManager(paramMap);
	
				mav.addObject("isSuccess", true);
				mav.addObject("success_msg", "담당자 수정이 완료 되었습니다.");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_manager] 담당자 등록 중 오류발생");
			mav.addObject("isSuccess", false);
			mav.addObject("error_msg", "담당자  등록 중 오류가 발생하였습니다.");	
		}
	
	
		logger.debug("["+getClass().getSimpleName()+"] [add_manager] end");
		return mav;
	}

	@RequestMapping(value="/list_01.isnet", method=RequestMethod.POST)
	public ModelAndView list_01(@RequestParam(value="page", defaultValue="1")int page,
			@RequestParam(value="rows", defaultValue="10")int rows){

		logger.debug("["+getClass().getSimpleName()+"] [list_01] start");
		logger.info("["+getClass().getSimpleName()+"] [list_01] page ["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] rows ["+rows+"]");

		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("PAGE", page);
			paramMap.put("ROWS", rows);

			Map<String, Object> resultData = employeeService.getEmployeeList(paramMap);
			mav.addAllObjects(resultData);

			mav.addObject("isSuccess", true);

		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_01] 사용자목록 조회중 오류 발생");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_01] end");
		}

		return mav;
	}
	
	@RequestMapping(value="/list_02.isnet", method=RequestMethod.POST)
	public ModelAndView list_02(@RequestParam(value="page", defaultValue="1")int page,
								@RequestParam(value="rows", defaultValue="10")int rows){

		logger.debug("["+getClass().getSimpleName()+"] [list_02] start");
		logger.info("["+getClass().getSimpleName()+"] [list_02] page ["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_02] rows ["+rows+"]");

		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("PAGE", page);
			paramMap.put("ROWS", rows);

			Map<String, Object> resultData = databaseBackupService.getBakupFiles(paramMap);
			mav.addAllObjects(resultData);

			mav.addObject("isSuccess", true);

		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_02] 사용자목록 조회중 오류 발생");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_02] end");
		}

		return mav;
	}

	
	@RequestMapping(value="/list_customer.isnet")
	public ModelAndView list_customer(@RequestParam(value="page", defaultValue="1")int page,
			@RequestParam(value="rows", defaultValue="10")int rows,
			@RequestParam(value="sidx", required=false)String sidx,
			@RequestParam(value="sord", required=false)String sord,
			@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
			@RequestParam(value="searchField", required=false)String searchField,
			@RequestParam(value="searchString", required=false)String searchString,
			@RequestParam(value="searchOper", required=false)String searchOper) {
	
		logger.debug("["+getClass().getSimpleName()+"] [list_01] start");
	
		logger.info("page ["+page+"]");
		logger.info("rows ["+rows+"]");
		logger.info("sidx ["+sidx+"]");
		logger.info("sord ["+sord+"]");
		logger.info("_search ["+_search+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] searchField ["+searchField+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] searchString ["+searchString+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] searchOper ["+searchOper+"]");
	
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("PAGE", page);
		paramMap.put("ROWS", rows);
		paramMap.put("SIDX", sidx);
		paramMap.put("SORD", sord);
		paramMap.put("_SEARCH", _search);
	
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
	
		try{
			if(_search){
	
				logger.info("searchField ["+searchField+"]");
				logger.info("searchString ["+searchString+"]");
				logger.info("searchOper ["+searchOper+"]");
	
				paramMap.put("SEARCH_FIELD", searchField);
				paramMap.put("SEARCH_STRING", searchString);
				paramMap.put("SEARCH_OPER", searchOper);
			}
	
			Map<String, Object> resultMap = customerService.getCustomerList(paramMap);
			mav.addAllObjects(resultMap);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_01] 업무리스트 조회 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_01] end");
		}
	
		return mav;
	}

	@RequestMapping(value="/list_manager.isnet")
	public ModelAndView list_manager(@RequestParam(value="page", defaultValue="1")int page,
			@RequestParam(value="rows", defaultValue="20")int rows,
			@RequestParam(value="sidx", required=false)String sidx,
			@RequestParam(value="sord", required=false)String sord,
			@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
			@RequestParam(value="searchField", required=false)String searchField,
			@RequestParam(value="searchString", required=false)String searchString,
			@RequestParam(value="searchOper", required=false)String searchOper) {
	
		logger.debug("["+getClass().getSimpleName()+"] [list_manager] start");
	
		logger.info("page ["+page+"]");
		logger.info("rows ["+rows+"]");
		logger.info("sidx ["+sidx+"]");
		logger.info("sord ["+sord+"]");
		logger.info("_search ["+_search+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_manager] searchField ["+searchField+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_manager] searchString ["+searchString+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_manager] searchOper ["+searchOper+"]");
	
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("PAGE", page);
		paramMap.put("ROWS", rows);
		paramMap.put("SIDX", sidx);
		paramMap.put("SORD", sord);
		paramMap.put("_SEARCH", _search);
	
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
	
		try{
			if(_search){
	
				logger.info("searchField ["+searchField+"]");
				logger.info("searchString ["+searchString+"]");
				logger.info("searchOper ["+searchOper+"]");
	
				paramMap.put("SEARCH_FIELD", searchField);
				paramMap.put("SEARCH_STRING", searchString);
				paramMap.put("SEARCH_OPER", searchOper);
			}
	
			Map<String, Object> resultMap = managerService.getManagerList(paramMap);
			mav.addAllObjects(resultMap);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_manager] 업무리스트 조회 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_manager] end");
		}
	
		return mav;
	}

	@RequestMapping(value="/update_01.isnet", method=RequestMethod.GET)
	public ModelAndView update_01(@RequestParam("en")int employee_no,
			@RequestParam("iu")String is_used){
		logger.debug("["+getClass().getSimpleName()+"] [update_01] start");
		logger.info("["+getClass().getSimpleName()+"] [update_01] employee_no ["+employee_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_01] is_used ["+is_used+"]");


		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/admin_01");
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("EMPLOYEE_NO", employee_no);
			paramMap.put("IS_USED", is_used);

			employeeService.updateEmployeeInfo(paramMap);
			commonCodeBean.init();	

		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_01] employee_no ["+employee_no+"]의 사용여부["+is_used+"] 변경 중 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_01] end");
		}

		return mav;
	}

	@RequestMapping(value="/update_02.isnet")
	public ModelAndView update_02(@RequestParam("en")int employee_no){
		logger.debug("["+getClass().getSimpleName()+"] [update_02] start");
		logger.info("["+getClass().getSimpleName()+"] [update_02] employee_no ["+employee_no+"]");

		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("EMPLOYEE_NO", employee_no);
			paramMap.put("LOGIN_PASSWORD", DigestUtils.md5DigestAsHex("qwe123!".getBytes()));
			employeeService.updateEmployeeInfo(paramMap);

			mav.addObject("isSuccess", true);
			mav.addObject("success_msg", "비밀번호가 초기화 되었습니다.");
			commonCodeBean.init();	
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_02] employee_no ["+employee_no+"]의 패스워츠 초기화 중 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_02] end");
		}

		return mav;
	}
	
	@RequestMapping(value="/update_03.isnet", method=RequestMethod.POST)
	public ModelAndView update_03(@RequestParam("DEPT_NO")int dept_no,
								@RequestParam("DEPT_POSITION")String dept_position,
								@RequestParam("EMPLOYEE_NO")int employee_no,
								@RequestParam("EMPLOYEE_NAME")String employee_name,
								@RequestParam("EMAIL")String email,
								@RequestParam("IS_MAIL_CC")String is_mail_cc,
								@RequestParam(value="LOGIN_PASSWORD", required=false )String login_password,
								HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [update_03] start");
		logger.info("["+getClass().getSimpleName()+"] [update_03] dept_no ["+dept_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_03] dept_position ["+dept_position+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_03] employee_no ["+employee_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_03] employee_name ["+employee_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_03] email ["+email+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_03] is_mail_cc ["+is_mail_cc+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_03] login_password ["+(login_password==null?"null":"**********")+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if(login_password != null){
				String md5_password = DigestUtils.md5DigestAsHex(login_password.getBytes());
				paramMap.put("LOGIN_PASSWORD", md5_password);
			}
			paramMap.put("DEPT_NO", dept_no);
			paramMap.put("DEPT_POSITION", dept_position);
			paramMap.put("EMPLOYEE_NO", employee_no);
			paramMap.put("EMPLOYEE_NAME", employee_name);
			paramMap.put("EMAIL", email);
			paramMap.put("IS_MAIL_CC", is_mail_cc);
			
			employeeService.updateEmployeeInfo(paramMap);
			mav.addObject("isSuccess", true);
			commonCodeBean.init();
			logger.info("["+getClass().getSimpleName()+"] [update_03] employee_name["+employee_name+"] 사용자정보 변경 완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_01] employee_name["+employee_name+"] 사용자정보 변경 중 오류 발생");
			mav.addObject("isSuccess", false);
			mav.addObject("error_msg", "사용자정보 변경 중 오류 발생하였습니다.");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_01] end");
		}
		
		return mav;
	}	

	@RequestMapping(value="/update_customer.isnet", method=RequestMethod.POST)
	public ModelAndView update_customer(@RequestParam("CUSTOMER_NAME")String customer_name,
			@RequestParam("ADDR")String addr,
			@RequestParam("TEL")String tel){
	
		logger.debug("["+getClass().getSimpleName()+"] [update_customer] start");
	
		logger.info("["+getClass().getSimpleName()+"] [update_customer] customer_name ["+customer_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_customer] addr ["+addr+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_customer] tel ["+tel+"]");
	
	
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
	
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("CUSTOMER_NAME", customer_name);
			if (addr != null && !addr.equals(""))
				paramMap.put("CUSTOMER_ADDRESS", addr);
			else 
				paramMap.put("CUSTOMER_ADDRESS", "");
	
			if (tel != null && !tel.equals(""))
				paramMap.put("CUSTOMER_TEL", tel);
			else 
				paramMap.put("CUSTOMER_TEL", "");
	
	
			boolean isAdded = customerService.addCustomer(paramMap);
			if(isAdded){
				mav.addObject("isSuccess", true);
				commonCodeBean.init();				
			}else{
				mav.addObject("isSuccess", false);
				mav.addObject("error_msg", "이미 등록된 고객사 이거나, 등록 중 오류가 발생하였습니다.");	
			}
	
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_customer] 고객사 등록 중 오류발생");
			mav.addObject("isSuccess", false);
			mav.addObject("error_msg", "고객사  등록 중 오류가 발생하였습니다.");	
		}
	
	
		logger.debug("["+getClass().getSimpleName()+"] [update_customer] end");
		return mav;
	}

	@RequestMapping(value="/search_customer.isnet")
	public ModelAndView search_customer(
			@RequestParam(value="term", required=false)String term) {

		logger.debug("["+getClass().getSimpleName()+"] [search_customer] start");
		logger.info("term ["+term+"]");


		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("VALUE", term);

		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);

		try{

			Map<String, Object> resultMap = customerService.search_customer(paramMap);
			mav.addAllObjects(resultMap);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [search_customer] 고객사 검색중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [search_customer] end");
		}

		return mav;
	}

	@RequestMapping("/restore.isnet")
	public ModelAndView restore(@RequestParam(value="DATE_NO")String date_no){
		logger.debug("["+getClass().getSimpleName()+"] [restore] start");
		logger.info("["+getClass().getSimpleName()+"] [restore] date_no["+date_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("DATE_NO", date_no);
			
			boolean isSuccess = databaseBackupService.restore(paramMap);
			mav.addObject("isSuccess", isSuccess);
			if(isSuccess){
				logger.info("["+getClass().getSimpleName()+"] [restore] date_no["+date_no+"] 데이타베이스 복원 완료");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [restore] date_no["+date_no+"] 데이타베이스 복원 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [restore] end");
		}
		return mav;
	}
	
}
