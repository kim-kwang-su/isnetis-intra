package com.isnet.mgr.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.isnet.mgr.common.CommonCodeBean;
import com.isnet.mgr.common.CommonConstant;
import com.isnet.mgr.common.DateUtil;
import com.isnet.mgr.common.SearchUtil;
import com.isnet.mgr.common.StringUtil;
import com.isnet.mgr.domain.Rule;
import com.isnet.mgr.service.IssueMailServiceImp;
import com.isnet.mgr.service.IssueService;
import com.isnet.mgr.service.QaMailServiceImp;
import com.isnet.mgr.view.FileDownloadView;

@Controller
@RequestMapping("/issue")
public class IssueController {
	
	Logger logger = Logger.getLogger(getClass());
	
	@Value("#{config['issue.savedpath']}")
	private String saved_path;
	
	@Value("#{config['qa.savedpath']}")
	private String qa_saved_path;
	
	@Autowired
	private MappingJacksonJsonView jsonView;
	@Autowired
	private FileDownloadView fileDownloadView;
	
	@Autowired
	private IssueService issueService;
	@Autowired
	private IssueMailServiceImp mailService;
	@Autowired
	private QaMailServiceImp qaMailService;
	@Autowired
	private CommonCodeBean commonCodeMap;


	// 이슈 리스트 페이지로 이동
	@RequestMapping(value="/issue_01.isnet", method=RequestMethod.GET)
	public ModelAndView issue_01(){
		
		logger.debug("["+getClass().getSimpleName()+"] [issue_01] start");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/issue/issue_01");
		
		try{
			// 이슈리스트 그리드에서 검색에 필요한 정보를 조회합니다.
			// 검색조건에 사용될 고객정보, 제품정보, 진행상태 정보를 조회합니다.
			List<Map<String, Object>> customerList = commonCodeMap.getCustomerList();
			List<Map<String, Object>> productList = commonCodeMap.getProductList();
			List<Map<String, Object>> stateList = commonCodeMap.getStateList();
			List<Map<String, Object>> kindList = commonCodeMap.getKindList();
			List<Map<String, Object>> employeeList = commonCodeMap.getEmployeeList();
			List<Map<String, Object>> seList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> developerList = new ArrayList<Map<String,Object>>();
			for(Map<String, Object> employee : employeeList){
				int dept_no = (Integer)employee.get("DEPT_NO");
				if(dept_no == CommonConstant.DEPT_NO_01){
					seList.add(employee);
				}else if(dept_no == CommonConstant.DEPT_NO_02 || dept_no == CommonConstant.DEPT_NO_05){
					developerList.add(employee);
				}
			}
			
			
			// 검색조건의 selectbox에 적합한 데이터형태로 값을 가공합니다.
			// "1:가고객사;2:나고객사;3:다고객사;4:라고객사"의 형태로 값을 가공한다.
			// jqGrid columnModel에서 searchoption의 value로 사용됨
			String customerListString = StringUtil.listToString(customerList, "CUSTOMER_NAME", "CUSTOMER_NO");
			String productListString = StringUtil.listToString(productList, "PRODUCT_NAME", "PRODUCT_NO");
			String stateListString = StringUtil.listToString(stateList, "STATE_NAME", "STATE_NO");
			String kindListString = StringUtil.listToString(kindList, "KIND_NAME", "KIND_NO");
			String seListString = StringUtil.listToString(seList, "EMPLOYEE_NAME", "EMPLOYEE_NO");
			String developerListString = StringUtil.listToString(developerList, "EMPLOYEE_NAME", "EMPLOYEE_NO");
			
			mav.addObject("customerValues", customerListString);
			mav.addObject("productValues", productListString);
			mav.addObject("stateValues", stateListString);
			mav.addObject("kindValues", kindListString);
			mav.addObject("seValues", seListString);
			mav.addObject("developerValues", developerListString);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [issue_01] end");
		}		
		return mav;
	}
	
	// 이슈 상세페이지로 이동
	@RequestMapping(value="/issue_02.isnet", method=RequestMethod.GET)
	public ModelAndView issue_02(@RequestParam("in")int issue_no){
		
		logger.debug("["+getClass().getSimpleName()+"] [issue_02] start");
		logger.info("["+getClass().getSimpleName()+"] [issue_02] issue_no ["+issue_no+"]");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ISSUE_NO", issue_no);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/issue/issue_02");
		
		try{
			Map<String, Object> commonData = commonCodeMap.getCommonCodeMap();
			Map<String, Object> issueInfo = issueService.getIssueInfo(paramMap);
			
			mav.addAllObjects(commonData);
			mav.addAllObjects(issueInfo);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [issue_02] issue_no["+issue_no+"]의 모듈상세정보 조회 중 오류발생");
			mav.setViewName("/issue/issue_01");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [issue_02] end");
		}
		
		return mav;
	}
	
	// 이슈 등록 페이지로 이동
	@RequestMapping(value="/issue_03.isnet", method=RequestMethod.GET)
	public ModelAndView issue_03(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/issue/issue_03");
		
		try{
			Map<String, Object> resultData = commonCodeMap.getCommonCodeMap();
			mav.addAllObjects(resultData);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [issue_01] end");
		}		
		
		
		return mav;
	}
	
	// 이슈 수정 페이지로 이동
	@RequestMapping(value="/issue_04.isnet", method=RequestMethod.GET)
	public ModelAndView issue_04(@RequestParam("in")int issue_no){
		
		logger.debug("["+getClass().getSimpleName()+"] [issue_04] start");
		logger.info("["+getClass().getSimpleName()+"] [issue_04] issue_no ["+issue_no+"]");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ISSUE_NO", issue_no);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/issue/issue_04");
		
		try{
			Map<String, Object> commonData = commonCodeMap.getCommonCodeMap();
			Map<String, Object> issueInfo = issueService.getIssueInfo(paramMap);
			
			mav.addAllObjects(commonData);
			mav.addAllObjects(issueInfo);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [issue_04] issue_no["+issue_no+"]의 모듈상세정보 조회 중 오류발생");
			mav.setViewName("/issue/issue_01");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [issue_04] end");
		}
		
		return mav;
	}
	
	
	// 이슈 리스트 페이지로 이동
	@RequestMapping(value="/issue_05.isnet", method=RequestMethod.GET)
	public ModelAndView issue_05(){
		
		logger.debug("["+getClass().getSimpleName()+"] [issue_05] start");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/issue/issue_05");
		
		try{
			// 이슈리스트 그리드에서 검색에 필요한 정보를 조회합니다.
			// 검색조건에 사용될 고객정보, 제품정보, 진행상태 정보를 조회합니다.
			List<Map<String, Object>> stateList = commonCodeMap.getStateList();
			List<Map<String, Object>> employeeList = commonCodeMap.getEmployeeList();
			List<Map<String, Object>> customerList = commonCodeMap.getCustomerList();
			
			
			
			
			mav.addObject("stateList", stateList);
			mav.addObject("employeeList", employeeList);
			mav.addObject("customerList", customerList);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [issue_05] end");
		}		
		return mav;
	}
	
	@RequestMapping(value="/issue_06.isnet", method=RequestMethod.GET)
	public ModelAndView issue_06(){
		
		logger.debug("["+getClass().getSimpleName()+"] [issue_06] start");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/issue/issue_06");
		
		try{
			List<Map<String, Object>> employeeList = commonCodeMap.getEmployeeList();
			List<Map<String, Object>> developerList = new ArrayList<Map<String,Object>>();
			for(Map<String, Object> employee : employeeList){
				int dept_no = (Integer)employee.get("DEPT_NO");
				if(dept_no == CommonConstant.DEPT_NO_02 || dept_no == CommonConstant.DEPT_NO_01){
					developerList.add(employee);
				}
			}
			
			String developerListString = StringUtil.listToString(developerList, "EMPLOYEE_NAME", "EMPLOYEE_NO");
			mav.addObject("developerValues", developerListString);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [issue_06] end");
		}		
		return mav;
	}
	
	// 파일업로드 팝업으로 이동합니다.
	@RequestMapping(value="/issue_popup.isnet")
	public ModelAndView issue_popup(@RequestParam("in")int issue_no){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("issue/issue_popup");
		mav.addObject("ISSUE_NO", issue_no);
		
		return mav;
	}
	
	// QA 테스트요청 정보를 반환합니다.
	@RequestMapping(value="/detail_issue01.isnet")
	public ModelAndView detail_issue01(@RequestParam(value="REQUEST_NO")int request_no){
		logger.debug("["+getClass().getSimpleName()+"] [detail_issue01] start");
		logger.info("["+getClass().getSimpleName()+"] [detail_issue01] request_no["+request_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("REQUEST_NO", request_no);
			
			Map<String, Object> requestInfo = issueService.getRequestInfo(paramMap);
			mav.addObject("requestInfo", requestInfo);
			
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [detail_issue01] request_no["+request_no+"] QA 테스트요청 정보 조회 완료");
		}catch(Exception e){
			e.printStackTrace();
			mav.addObject("isSuccess", false);
			logger.error("["+getClass().getSimpleName()+"] [detail_issue01] request_no["+request_no+"] QA 테스트요청 정보 조회 오류");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [detail_issue01] end");
		}
		return mav;
	}
	
	@RequestMapping(value="/detail_issue02.isnet")
	public ModelAndView detail_issue02(@RequestParam(value="REQUEST_NO")int request_no){
		logger.debug("["+getClass().getSimpleName()+"] [detail_issue02] start");
		logger.info("["+getClass().getSimpleName()+"] [detail_issue02] request_no["+request_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("REQUEST_NO", request_no);
			
			Map<String, Object> bugReport = issueService.getBugReport(paramMap);
			mav.addObject("bugReport", bugReport);
			
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [detail_issue02] request_no["+request_no+"] 버그리포트 정보 조회 완료");
		}catch(Exception e){
			e.printStackTrace();
			mav.addObject("isSuccess", false);
			logger.error("["+getClass().getSimpleName()+"] [detail_issue02] request_no["+request_no+"] 버그리포트 정보 조회 오류");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [detail_issue02] end");
		}
		return mav;
	}
	
	// 새로운 이슈를 등록합니다.
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add_issue01.isnet", method=RequestMethod.POST)
	public ModelAndView add_issue01(@RequestParam(value="ACCEPT_DATE")String accept_date,
									@RequestParam(value="CUSTOMER_REQUEST_DATE")String customer_request_date,
									@RequestParam(value="EXPECTED_COMPLETE_DATE", required=false)String expected_complete_date,
									@RequestParam(value="EXPECTED_DEVELOPED_DATE", required=false)String expected_developed_date,
									@RequestParam(value="DEVELOPED_PERIOD", required=false)String developed_period,
									@RequestParam(value="CUSTOMER_NO")int customer_no,
									@RequestParam(value="PRODUCT_NO")int product_no,
									@RequestParam(value="KIND_NO")int kind_no,
									@RequestParam(value="ISSUE_PRIORITY")String issue_priority,
									@RequestParam(value="SE_NO")int se_no,
									@RequestParam(value="DEVELOPER_NO", required=false)int developer_no,
									@RequestParam(value="ISSUE_MEMO")String issue_memo,
									@RequestParam(value="ISSUE_REMARK", required=false)String issue_remark,
									HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_issue01] start");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] accept_date ["+accept_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] customer_request_date ["+customer_request_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] expected_complete_date ["+expected_complete_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] expected_developed_date ["+expected_developed_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] developed_period ["+developed_period+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] customer_no ["+customer_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] product_no ["+product_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] kind_no ["+kind_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] issue_priority ["+issue_priority+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] se_no ["+se_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] developer_no ["+developer_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] issue_memo ["+issue_memo+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] issue_remark ["+issue_remark+"]");

		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ACCEPT_DATE", accept_date);
		if(StringUtil.hasText(customer_request_date)){
			paramMap.put("CUSTOMER_REQUEST_DATE", customer_request_date);
		}
		if(StringUtil.hasText(expected_complete_date)){
			paramMap.put("EXPECTED_COMPLETE_DATE", expected_complete_date);
		}
		if(StringUtil.hasText(expected_developed_date)){
			paramMap.put("EXPECTED_DEVELOPED_DATE", expected_developed_date);
		}
		if(StringUtil.hasText(developed_period)){
			paramMap.put("DEVELOPED_PERIOD", developed_period);
		}
		paramMap.put("CUSTOMER_NO", customer_no);
		paramMap.put("PRODUCT_NO", product_no);
		paramMap.put("KIND_NO", kind_no);
		paramMap.put("ISSUE_PRIORITY", issue_priority);
		paramMap.put("SE_NO", se_no);
		paramMap.put("CREATE_DATE", DateUtil.dateToString(CommonConstant.DATE_FORMAT_02));
		paramMap.put("DEVELOPER_NO", developer_no);
		
		paramMap.put("ISSUE_MEMO", issue_memo);
		if(StringUtil.hasText(issue_remark)){
			paramMap.put("ISSUE_REMARK", issue_remark);		
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			logger.info("["+getClass().getSimpleName()+"] [add_issue01] employee_no ["+employee_no+"]");
			paramMap.put("EMPLOYEE_NO", employee_no);

			int issue_no = issueService.add_issue(paramMap);
			
			paramMap.put("ISSUE_NO", issue_no);
			mailService.sendMail(paramMap);
			
			mav.addObject("isSuccess", true);
			mav.addObject("issue_no", issue_no);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_issue01] 이슈 등록중 오류가 발생하였습니다.");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_issue01] end");
		}
		
		return mav;
	}
	// 이슈 상세페이지에서 이슈 담당개발자를 지정합니다.
	@RequestMapping(value="/add_issue02.isnet", method=RequestMethod.POST)
	public ModelAndView add_issue02(@RequestParam(value="ISSUE_NO")int issue_no, 
									@RequestParam(value="STATE_NO", required=false)int state_no,
										@RequestParam(value="EMPLOYEE_NO")int[] employee_no){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_issue02] start");
		logger.info("["+getClass().getSimpleName()+"] [add_issue02] issue_no ["+issue_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue02] state_no ["+state_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue02] employee_no ["+Arrays.toString(employee_no)+"]");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ISSUE_NO", issue_no);
		paramMap.put("STATE_NO", state_no);
		paramMap.put("EMPLOYEE_NO_ARRAY", employee_no);
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			// 담당 개발자를 지정합니다.
			issueService.insert_developer(paramMap);
			mav.addObject("isSuccess", true);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_issue02] 이슈번호["+issue_no+"]번의 개발담당자 지정 중 오류 발생");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_issue02] end");
		}	
		
		return mav;
	}
	
	// 이슈 상세페이지에서 이슈 진행상황을 등록합니다.
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add_issue03.isnet", method=RequestMethod.POST)
	public ModelAndView add_issue03(@RequestParam(value="ISSUE_NO")int issue_no,
									@RequestParam(value="STATE_NO", required=false)int state_no,
									@RequestParam(value="PROCESS_MEMO")String process_memo,
									@RequestParam(value="RELEASE_DATE", required=false)String release_date,
									//@RequestParam(value="EXPECTED_COMPLETE_DATE", required=false)String expected_complete_date,
									//@RequestParam(value="EXPECTED_DEVELOPED_DATE", required=false)String expected_developed_date,
									//@RequestParam(value="DEVELOPED_COMPLETE_DATE", required=false)String developed_complete_date,
									//@RequestParam(value="DEVELOPED_PERIOD", required=false)String developed_period,
									HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_issue03] start");
		logger.info("["+getClass().getSimpleName()+"] [add_issue03] issue_no ["+issue_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue03] state_no ["+state_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue03] process_memo ["+process_memo+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue03] release_date ["+release_date+"]");
		//logger.info("["+getClass().getSimpleName()+"] [add_issue03] expected_complete_date ["+expected_complete_date+"]");
		//logger.info("["+getClass().getSimpleName()+"] [add_issue03] expected_developed_date ["+expected_developed_date+"]");
		//logger.info("["+getClass().getSimpleName()+"] [add_issue03] developed_complete_date ["+developed_complete_date+"]");
		//logger.info("["+getClass().getSimpleName()+"] [add_issue03] developed_period ["+developed_period+"]");
		
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ISSUE_NO", issue_no);
		paramMap.put("STATE_NO", state_no);
		paramMap.put("PROCESS_MEMO", process_memo);
		paramMap.put("REGISTER_DATE", new Date());
		//paramMap.put("EXPECTED_COMPLETE_DATE", expected_complete_date);
		//paramMap.put("EXPECTED_DEVELOPED_DATE", expected_developed_date);
		//paramMap.put("DEVELOPED_COMPLETE_DATE", developed_complete_date);
		//paramMap.put("DEVELOPED_PERIOD", developed_period);
		
		// 진행상태가 개발완료,고객테스트 완료, 고객 릴리즈완료인 경우에는 테스트 완료일과 고갤 릴리즈일을 저장한다.
		if(state_no == CommonConstant.ISSUE_STATE_NO_08){
			paramMap.put("TEST_COMPLETE_DATE", new Date());
		}else if(state_no == CommonConstant.ISSUE_STATE_NO_09){
			paramMap.put("RELEASE_DATE", new Date());
		}else if(state_no == CommonConstant.ISSUE_STATE_NO_06){
			paramMap.put("DEVELOPED_COMPLETE_DATE", new Date());
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			logger.info("["+getClass().getSimpleName()+"] [add_issue03] employee_no ["+employee_no+"]");
			paramMap.put("EMPLOYEE_NO", employee_no);
			
			issueService.add_issue_history(paramMap);
			//if(expected_complete_date != null){
			//	issueService.update_issue(paramMap);
			//}
			
			mailService.sendMail(paramMap);
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_issue03] issue_no["+issue_no+"]의 이슈진행상황 등록 중 오류 발생");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_issue03] end");
		}
		
		return mav;
	}

	// 이슈와 관련된 첨부파일 업로드를 지원합니다.
	@RequestMapping(value="add_issue04.isnet")
	@ResponseBody
	public String add_issue04(@RequestParam("in")int issue_no,
									@RequestParam("file")MultipartFile file){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_issue04] start");
		JSONObject obj = new JSONObject();
		
		try{
			
			if(!file.isEmpty()){
				logger.info("["+getClass().getSimpleName()+"] [add_issue04] file_name [" + file.getOriginalFilename() + "]");
				logger.info("["+getClass().getSimpleName()+"] [add_issue04] issue_no [" + issue_no + "]");
				
				String originalFilename = file.getOriginalFilename();
				String newFilename = System.currentTimeMillis() + "_"+ originalFilename;
				
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(new File(saved_path, newFilename)));
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("ISSUE_NO", issue_no);
				paramMap.put("FILE_NAME", newFilename);
				
				issueService.add_file(paramMap);
				
				obj.put("isSuccess", true);
				obj.put("status", "success");
				obj.put("message", "파일 업로드가 완료되었습니다.");
			}else{
				obj.put("status", "error");
				obj.put("message", "정상 파일이 아닙니다.");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_issue04] 첨부파일 업로드 중 오류가 발생하였습니다.");
			obj.put("isSuccess", false);
			obj.put("status", "error");
			obj.put("message", "파일 업로드 중 오류가 발생하였습니다.");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_issue04] end");
		}
		return obj.toString();
	}
	
	// QA_테스트 요청을 등록합니다.
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add_issue_05.isnet", method=RequestMethod.POST)
	@ResponseBody
	public String add_issue_05(@RequestParam(value="ISSUE_NO", required=false, defaultValue="0")int issue_no,
								@RequestParam(value="STATE_NO", required=false, defaultValue=CommonConstant.ISSUE_STATE_06)int state_no,
								@RequestParam(value="MODULE_NAME", required=false)String module_name,
								@RequestParam(value="MODULE_FILE", required=false)MultipartFile module_file,
								@RequestParam(value="MODIFY_SUMMARY", required=false)String modify_summary,
								@RequestParam(value="EFFECT_SUMMARY", required=false)String effect_summary,
								HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_issue_05] start");
		logger.info("["+getClass().getSimpleName()+"] [add_issue_05] issue_no["+issue_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue_05] module_name["+module_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue_05] modify_summary["+modify_summary+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue_05] effect_summary["+effect_summary+"]");
		
		boolean isSuccess = false;
		try{
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			logger.info("["+getClass().getSimpleName()+"] [add_issue_05] employee_no ["+employee_no+"]");
			
			// 이슈 진행상황 등록
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if(issue_no != 0){
				
					paramMap.put("ISSUE_NO", issue_no);
					paramMap.put("STATE_NO", state_no);
					paramMap.put("PROCESS_MEMO", modify_summary);
					paramMap.put("REGISTER_DATE", new Date());
					paramMap.put("DEVELOPED_COMPLETE_DATE", new Date());
					paramMap.put("EMPLOYEE_NO", employee_no);
					
					issueService.add_issue_history(paramMap);	
					// 진행상황 변경 메일 발송
					mailService.sendMail(paramMap);				
			}
			
			// QA 테스트 요청 등록
			paramMap.put("MODULE_NAME", module_name);
			paramMap.put("MODIFY_SUMMARY", modify_summary);
			paramMap.put("EFFECT_SUMMARY", effect_summary);
			if(module_file != null && !module_file.isEmpty()){
				logger.info("["+getClass().getSimpleName()+"] [add_issue_05] file_name [" + module_file.getOriginalFilename() + "]");
				
				String originalFilename = module_file.getOriginalFilename();
				String newFilename = System.currentTimeMillis() + "_"+ originalFilename;
				
				FileCopyUtils.copy(module_file.getInputStream(), new FileOutputStream(new File(qa_saved_path, newFilename)));
				paramMap.put("MODULE_FILE_NAME", newFilename);
			}
			paramMap.put("DEVELOPER_NO", employee_no);
			paramMap.put("REQUEST_DATE", DateUtil.dateToString("yyyy-MM-dd"));
			
			issueService.add_qa_request(paramMap);
			isSuccess = true;
			
			// 테스트 요청 메일 발송
			paramMap.put("QA_GUBUN", CommonConstant.QA_REQUEST_TEST);
			paramMap.put("FROM", employee_no);
			qaMailService.sendMail(paramMap);
			
			logger.info("["+getClass().getSimpleName()+"] [add_issue_05]  issue_no["+issue_no+"] QA 테스트요청 등록 완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_issue_05] issue_no["+issue_no+"] QA 테스트요청 등록 오류");
			isSuccess = false;
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_issue_05] end");
		}
		
		return "{\"isSuccess\": "+isSuccess+"}";
	}
	
	// 버그리포트 등록
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add_issue_06.isnet", method=RequestMethod.POST)
	@ResponseBody
	public String add_issue_06(@RequestParam("REQUEST_NO")int request_no,
										@RequestParam(value="TEST_COUNT")int test_count,
										@RequestParam(value="BUG_SUMMARY")String bug_summary,
										@RequestParam(value="BUG_FREQUENCY")String bug_frequency,
										@RequestParam(value="BUG_IMPORTANCE")String bug_importance,
										@RequestParam(value="QA_DATE")String qa_date,
										@RequestParam(value="RESOLVE_YN")String resolve_yn,
										@RequestParam(value="REPORT_FILE", required=false)MultipartFile report_file,
										HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [update_issue_02] start");
		logger.info("["+getClass().getSimpleName()+"] [update_issue_02] request_no["+request_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_issue_02] test_count["+test_count+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_issue_02] bug_summary["+bug_summary+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_issue_02] bug_frequency["+bug_frequency+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_issue_02] bug_importance["+bug_importance+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_issue_02] qa_date["+qa_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_issue_02] resolve_yn["+resolve_yn+"]");
		
		boolean isSuccess = false;
		try{
			// 버그 리포트 저장
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("REQUEST_NO", request_no);
			paramMap.put("TEST_COUNT", test_count);
			paramMap.put("BUG_SUMMARY", bug_summary);
			paramMap.put("BUG_FREQUENCY", bug_frequency);
			paramMap.put("BUG_IMPORTANCE", bug_importance);
			paramMap.put("QA_DATE", qa_date);
			paramMap.put("RESOLVE_YN", resolve_yn);
			paramMap.put("NOTICE_DATE", DateUtil.dateToString("yyyy-MM-dd"));
			paramMap.put("TESTER_NO", sessionUser.get("EMPLOYEE_NO"));
			
			if(report_file != null && !report_file.isEmpty()){
				String originalFilename = report_file.getOriginalFilename();
				String newFilename = System.currentTimeMillis() + "_"+ originalFilename;
				
				FileCopyUtils.copy(report_file.getInputStream(), new FileOutputStream(new File(qa_saved_path, newFilename)));
				paramMap.put("FILE_NAME", newFilename);
			}
			
			issueService.add_bug_report(paramMap);
			isSuccess = true;		
			
			// 메일 발송
			paramMap.put("QA_GUBUN", CommonConstant.QA_ADD_BUG_REPORT);
			paramMap.put("FROM", sessionUser.get("EMPLOYEE_NO"));
			qaMailService.sendMail(paramMap);
		}catch(Exception e){
			e.printStackTrace();
			isSuccess = false;
			logger.error("["+getClass().getSimpleName()+"] [update_issue_02] quest_no["+request_no+"]버그리포트 등록 오류");
		}
		
		return "{\"isSuccess\": "+isSuccess+"}";
	}
	
	// 이슈 리스트 정보를 조회합니다.
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list_01.isnet")
	public ModelAndView list_01(@RequestParam(value="page", defaultValue="1")int page,
								@RequestParam(value="rows", defaultValue="20")int rows,
								@RequestParam(value="sidx", required=false)String sidx,
								@RequestParam(value="sord", required=false)String sord,
								@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
								@RequestParam(value="filters", required=false)String filters,
								HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_01] start");
		
		logger.info("["+getClass().getSimpleName()+"] [list_01] page ["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] rows ["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] sidx ["+sidx+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] sord ["+sord+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] _search ["+_search+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] filters ["+filters+"]");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("PAGE", page);
		paramMap.put("ROWS", rows);
		paramMap.put("SIDX", sidx);
		paramMap.put("SORD", sord);
		paramMap.put("_SEARCH", _search);

		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			if(_search && filters != null){
				
				session.setAttribute("filters", filters);
				
				Map<String, Object> map = SearchUtil.getQueryMap(filters);
				List<Rule> rules = (List<Rule>)map.get("param");		
				logger.info("["+getClass().getSimpleName()+"] [list_01] rules ["+rules+"]");
				if(!rules.isEmpty()){
					paramMap.put("rules", rules);
					paramMap.put("groupOp", map.get("groupOp"));
				}
				
			}else{
				session.removeAttribute("filters");
			}
			logger.info("["+getClass().getSimpleName()+"] [list_01] paramMap ["+paramMap+"]");
			Map<String, Object> resultData = issueService.getRecentIssueList(paramMap);
			mav.addAllObjects(resultData);				
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			mav.addObject("isSuccess", false);
			logger.error("["+getClass().getSimpleName()+"] [list_01] 최근 접수된 이슈 내역 조회 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_01] end");
		}
		
		return mav;
	}
	
	// 이슈 상세페이지에 보여줄 이슈 진행상황정보를 조회합니다.
	@RequestMapping(value="/list_02.isnet")
	public ModelAndView list_02(@RequestParam(value="in")int issue_no){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_02] start");
		
		logger.info("["+getClass().getSimpleName()+"] [list_02] issue_no ["+issue_no+"]");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ISSUE_NO", issue_no);
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> resultData = issueService.getIssueProgressList(paramMap);
			mav.addAllObjects(resultData);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_02] 이슈번호["+issue_no+"]번 이슈 진행상황 조회중 오류");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_02] end");
		}
		
		return mav;
	}
	
	// 진행상황 정보를 조회합니다.
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list_03.isnet")
	public ModelAndView list_03(@RequestParam(value="page", defaultValue="1")int page,
								@RequestParam(value="rows", defaultValue="20")int rows,
								@RequestParam(value="sidx", required=false)String sidx,
								@RequestParam(value="sord", required=false)String sord,
								@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
								@RequestParam(value="filters", required=false)String filters,
								HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_03] start");
		
		logger.info("["+getClass().getSimpleName()+"] [list_03] page ["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_03] rows ["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_03] sidx ["+sidx+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_03] sord ["+sord+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_03] _search ["+_search+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_03] filters ["+filters+"]");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("PAGE", page);
		paramMap.put("ROWS", rows);
		paramMap.put("SIDX", sidx);
		paramMap.put("SORD", sord);
		paramMap.put("_SEARCH", _search);

		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			if(_search && filters != null){
				session.setAttribute("filters", filters);
				
				Map<String, Object> map = SearchUtil.getQueryMap(filters);
				List<Rule> rules = (List<Rule>)map.get("param");
				for(Rule rule : rules){
					paramMap.put(rule.getColumn(),rule.getValue() );
				}				
			}else{
				session.removeAttribute("filters");
			}
			
			logger.info("["+getClass().getSimpleName()+"] [list_03] paramMap ["+paramMap+"]");
			Map<String, Object> resultData = issueService.getStateIssueList(paramMap);
			mav.addAllObjects(resultData);				

		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_03] 최근 접수된 이슈 내역 조회 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_03] end");
		}
		
		return mav;
	}
	
	// 진행상황 정보를 조회합니다.
	@RequestMapping(value="/list_04.isnet")
	public ModelAndView list_04(@RequestParam(value="page", defaultValue="1")int page,
								@RequestParam(value="rows", defaultValue="20")int rows,
								@RequestParam(value="sidx", required=false)String sidx,
								@RequestParam(value="sord", required=false)String sord,
								@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
								@RequestParam(value="searchField", required=false)String searchField,
								@RequestParam(value="searchString", required=false)String searchString,
								@RequestParam(value="searchOper", required=false)String searchOper,
								HttpSession session){

		logger.debug("["+getClass().getSimpleName()+"] [list_04] start");
		
		logger.info("["+getClass().getSimpleName()+"] [list_04] page ["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] rows ["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] sidx ["+sidx+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] sord ["+sord+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] _search ["+_search+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] searchField ["+searchField+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] searchString ["+searchString+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] searchOper ["+searchOper+"]");
		
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
				paramMap.put("SEARCH_FIELD", searchField);
				paramMap.put("SEARCH_STRING", searchString);
				paramMap.put("SEARCH_OPER", searchOper);
			}
			
			logger.info("["+getClass().getSimpleName()+"] [list_04] paramMap ["+paramMap+"]");
			Map<String, Object> resultData = issueService.getQaRequestList(paramMap);
			mav.addAllObjects(resultData);				
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			mav.addObject("isSuccess", false);
			logger.error("["+getClass().getSimpleName()+"] [list_04] 최근 접수된 이슈 내역 조회 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_04] end");
		}
		
		return mav;
	}
	
	
	
	// 파일 다운로드
	@RequestMapping(value="/download_file.isnet")
	public ModelAndView download_file(@RequestParam(value="no")int no,
										@RequestParam(value="gubun")String gubun){
		logger.debug("["+getClass().getSimpleName()+"] [download_file] start");
		logger.info("["+getClass().getSimpleName()+"] [download_file] no ["+no+"]");
		logger.info("["+getClass().getSimpleName()+"] [download_file] gubun ["+gubun+"]");
		
		
		ModelAndView mav = new ModelAndView();
		mav.setView(fileDownloadView);
		try{
			if("issue".equals(gubun)){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("FILE_NO", no);
				List<Map<String, Object>> fileList = issueService.getIssueFiles(paramMap);
				if(!fileList.isEmpty()){
					Map<String, Object> map = (Map<String, Object>)fileList.get(0);
					// 실제 파일시스템에 저장된 이름
					String file_name = (String)map.get("FILE_NAME");
					
					mav.addObject("downloadFile", new File(saved_path, file_name));
					mav.addObject("isTimeIncluded", true);
				}
				
			}else if("module".equals(gubun)){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("REQUEST_NO", no);
				Map<String, Object> file = issueService.getRequestInfo(paramMap);
				// 실제 파일시스템에 저장된 이름
				String file_name = (String)file.get("MODULE_FILE_NAME");
				
				mav.addObject("downloadFile", new File(qa_saved_path, file_name));
				mav.addObject("isTimeIncluded", true);
				
			}else if("result".equals(gubun)){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("REQUEST_NO", no);
				Map<String, Object> file = issueService.getRequestInfo(paramMap);
				// 실제 파일시스템에 저장된 이름
				String file_name = (String)file.get("RESULT_FILE_NAME");
				
				mav.addObject("downloadFile", new File(qa_saved_path, file_name));
				mav.addObject("isTimeIncluded", true);
				
			}else if("report".equals(gubun)){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("REQUEST_NO", no);
				Map<String, Object> file = issueService.getBugReport(paramMap);
				// 실제 파일시스템에 저장된 이름
				String file_name = (String)file.get("FILE_NAME");
				
				mav.addObject("downloadFile", new File(qa_saved_path, file_name));
				mav.addObject("isTimeIncluded", true);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [download_file] 번호["+no+"]번 파일 다운로드 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [download_file] end");
		}
		
		return mav;
	}
	
	// 이슈 정보를 수정합니다.
	@RequestMapping(value="/modify_issue01.isnet", method=RequestMethod.POST)
	public ModelAndView modify_issue01(@RequestParam(value="ISSUE_NO")String issue_no,
									@RequestParam(value="ACCEPT_DATE")String accept_date,
									@RequestParam(value="CUSTOMER_REQUEST_DATE")String customer_request_date,
									@RequestParam(value="EXPECTED_COMPLETE_DATE", required=false)String expected_complete_date,
									@RequestParam(value="EXPECTED_DEVELOPED_DATE", required=false)String expected_developed_date,
									@RequestParam(value="DEVELOPED_PERIOD", required=false)String developed_period,
									@RequestParam(value="CUSTOMER_NO")int customer_no,
									@RequestParam(value="PRODUCT_NO")int product_no,
									@RequestParam(value="KIND_NO")int kind_no,
									@RequestParam(value="ISSUE_PRIORITY")String issue_priority,
									@RequestParam(value="SE_NO")int se_no,
									@RequestParam(value="ISSUE_MEMO")String issue_memo,
									@RequestParam(value="ISSUE_REMARK", required=false)String issue_remark,
									@RequestParam(value="DEVELOPER_NO", required=false)int[] developer_no_arr){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_issue01] start");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] issue_no ["+issue_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] accept_date ["+accept_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] customer_request_date ["+customer_request_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] expected_complete_date ["+expected_complete_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] expected_developed_date ["+expected_developed_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] developed_period ["+developed_period+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] customer_no ["+customer_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] product_no ["+product_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] kind_no ["+kind_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] issue_priority ["+issue_priority+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] se_no ["+se_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] issue_memo ["+issue_memo+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_issue01] issue_remark ["+issue_remark+"]");

		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ISSUE_NO", issue_no);
		paramMap.put("ACCEPT_DATE", accept_date);
		if(StringUtil.hasText(customer_request_date)){
			paramMap.put("CUSTOMER_REQUEST_DATE", customer_request_date);
		}
		if(StringUtil.hasText(expected_complete_date)){
			paramMap.put("EXPECTED_COMPLETE_DATE", expected_complete_date);
		}
		if(StringUtil.hasText(expected_developed_date)){
			paramMap.put("EXPECTED_DEVELOPED_DATE", expected_developed_date);
		}
		if(StringUtil.hasText(developed_period)){
			paramMap.put("DEVELOPED_PERIOD", developed_period);
		}
		paramMap.put("CUSTOMER_NO", customer_no);
		paramMap.put("PRODUCT_NO", product_no);
		paramMap.put("KIND_NO", kind_no);
		paramMap.put("ISSUE_PRIORITY", issue_priority);
		paramMap.put("SE_NO", se_no);
		paramMap.put("CREATE_DATE", DateUtil.dateToString(CommonConstant.DATE_FORMAT_02));
		
		if(developer_no_arr != null){
			paramMap.put("DEVELOPER_NO", developer_no_arr);
		}
		
		paramMap.put("ISSUE_MEMO", issue_memo);
		if(StringUtil.hasText(issue_remark)){
			paramMap.put("ISSUE_REMARK", issue_remark);		
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			issueService.update_issue(paramMap);
			
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_issue01] issue_no["+issue_no+"] 이슈 수정중 오류가 발생하였습니다.");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_issue01] end");
		}
		
		return mav;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/modify_issue02.isnet", method=RequestMethod.POST)
	@ResponseBody
	public String modify_issue02(@RequestParam(value="REQUEST_NO")int request_no,
										@RequestParam(value="TEST_SUMMARY")String test_summary,
										@RequestParam(value="TEST_RESULT")String test_result,
										@RequestParam(value="RESULT_FILE", required=false)MultipartFile result_file,
										HttpSession session){
		logger.debug("["+getClass().getSimpleName()+"] [modify_issue02] start");
		logger.info("["+getClass().getSimpleName()+"] [modify_issue02] request_no["+request_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [modify_issue02] test_summary["+test_summary+"]");
		logger.info("["+getClass().getSimpleName()+"] [modify_issue02] test_result["+test_result+"]");
		
		boolean isSuccess = false;
		try{
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("REQUEST_NO", request_no);
			paramMap.put("TEST_SUMMARY", test_summary);
			paramMap.put("TEST_RESULT", test_result);
			paramMap.put("TEST_DATE", DateUtil.dateToString("yyyy-MM-dd"));
			paramMap.put("TESTER_NO", sessionUser.get("EMPLOYEE_NO"));
			
			if(result_file != null && !result_file.isEmpty()){
				String originalFilename = result_file.getOriginalFilename();
				String newFilename = System.currentTimeMillis() + "_"+ originalFilename;
				
				FileCopyUtils.copy(result_file.getInputStream(), new FileOutputStream(new File(qa_saved_path, newFilename)));
				paramMap.put("RESULT_FILE_NAME", newFilename);
			}
			
			issueService.update_qa_request(paramMap);
			
			isSuccess = true;
			logger.info("["+getClass().getSimpleName()+"] [modify_issue02] request_no["+request_no+"] 테스트결과 등록완료");

			paramMap.put("QA_GUBUN", CommonConstant.QA_ADD_TEST_RESULT);
			paramMap.put("FROM", sessionUser.get("EMPLOYEE_NO"));
			qaMailService.sendMail(paramMap);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [modify_issue02] request_no["+request_no+"] 테스트결과 등록실패");
			isSuccess = false;
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [modify_issue02] end");
		}
		return "{\"isSuccess\": "+isSuccess+"}";
	}
	
	
}
