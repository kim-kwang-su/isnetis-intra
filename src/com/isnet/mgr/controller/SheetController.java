package com.isnet.mgr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.isnet.mgr.common.CommonCodeBean;
import com.isnet.mgr.common.StringUtil;
import com.isnet.mgr.service.SheetService;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/worksheet")
public class SheetController {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private MappingJacksonJsonView jsonView;
	
	@Autowired
	private SheetService sheetService;
	
	@Autowired
	private CommonCodeBean commonCodeMap;
	
	// 리스트 정보를 조회합니다.
	@RequestMapping(value="/list_01.isnet")
	public ModelAndView list_01(@RequestParam(value="page", defaultValue="1")int page,
								@RequestParam(value="rows", defaultValue="10")int rows,
								@RequestParam(value="sidx", required=false)String sidx,
								@RequestParam(value="sord", required=false)String sord,
								@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
								@RequestParam(value="searchField", required=false)String searchField,
								@RequestParam(value="searchString", required=false)String searchString,
								@RequestParam(value="searchOper", required=false)String searchOper) {
		
		logger.debug("["+getClass().getSimpleName()+"] [list_01] start");
		
		logger.info("["+getClass().getSimpleName()+"] [list_01] page ["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] rows ["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] sidx ["+sidx+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] sord ["+sord+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] _search ["+_search+"]");
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
				
				if(_search){
					paramMap.put("searchField", searchField);
					paramMap.put("searchString", searchString);
					paramMap.put("searchOper", searchOper);
				}
			}
			
			Map<String, Object> resultMap = sheetService.getSheetList(paramMap);
			mav.addAllObjects(resultMap);
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			mav.addObject("isSuccess", false);
			logger.error("["+getClass().getSimpleName()+"] [list_01] 업무리스트 조회 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_01] end");
		}
		
		return mav;
	}

	
	// 작업현황 리스트 정보를 조회합니다.	
	@RequestMapping(value="/list_03.isnet")
	public ModelAndView list_03(@RequestParam(value="dept_no", required=false)int dept_no,
								@RequestParam(value="start_date", required=false)String start_date,
								@RequestParam(value="end_date", required=false)String end_date) {
		
		logger.debug("["+getClass().getSimpleName()+"] [list_03] start");
		logger.info("["+getClass().getSimpleName()+"] [list_03] dept_no ["+dept_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_03] start_date ["+start_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_03] end_date ["+end_date+"]");
	
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);

		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("DEPT_NO", dept_no);
			paramMap.put("START_DATE", start_date);
			paramMap.put("END_DATE", end_date);

			List<Map<String, Object>> employeeList = commonCodeMap.getEmployeeList();
			List<String> employeeNames = new ArrayList<String>();
			StringBuilder sql = new StringBuilder();
			int count = 0;
			for(Map<String, Object> employee : employeeList){
				if(((Integer)employee.get("DEPT_NO")) == dept_no){
					count++;
					employeeNames.add(String.valueOf(employee.get("EMPLOYEE_NAME")));
					sql.append(" SUM(IF(EMPLOYEE_NAME = '"+ employee.get("EMPLOYEE_NAME") +"',CNT, 0)) MEMBER"+ count + ", ");
				}
			}
			paramMap.put("QUERY",sql.toString());
			Map<String, Object> resultData = sheetService.getSheetListByDept(paramMap);
			
			mav.addAllObjects(resultData);
			mav.addObject("employeeNames", employeeNames);
			mav.addObject("isSuccess", true);
			
		}catch(Exception e){
			e.printStackTrace();
			mav.addObject("isSuccess", false);
			logger.error("["+getClass().getSimpleName()+"] [list_03] 작업현황 조회 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_03] end");
		}
		//paramMap
		
		/*ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		//colNames
		JSONArray  colNames = new JSONArray ()
		   .element("DEPT_NO")
		   .element("SEQ")
		   .element("작성일자")
		   .element("DEPT_NAME");
		
		//colModels 내용
		JSONArray  colModels = new JSONArray ();
		
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("name", "DEPT_NO");
		tempMap.put("index", "DEPT_NO");
		tempMap.put("hidden", true);
		
		colModels.element(tempMap);
		tempMap.clear();
		
		tempMap.put("name", "SEQ");
		tempMap.put("index", "SEQ");
		tempMap.put("hidden", true);
		
		colModels.element(tempMap);
		tempMap.clear();
	
		tempMap.put("name", "WRITE_DATE");
		tempMap.put("index", "WRITE_DATE");
		tempMap.put("width", 100);
		tempMap.put("align", "center");
		
		colModels.element(tempMap);
		tempMap.clear();
		
		tempMap.put("name", "DEPT_NAME");
		tempMap.put("index", "DEPT_NAME");
		tempMap.put("hidden", true);
	
		colModels.element(tempMap);
		tempMap.clear();
		
		//query 
		String query="";
		
		try{
			paramMap.put("DEPT_NAME",deptName);
			
			List<Map<String, Object>> employee_list =  employeeService.getSheetEmployee(paramMap);
			
			for(int i =0;i < employee_list.size();i++) {
				Map<String, Object> employee = employee_list.get(i);
				colNames.element(employee.get("EMPLOYEE_NAME"));
				tempMap.put("name", "MEMBER"+i);
				tempMap.put("index", "MEMBER"+i);
				tempMap.put("width", 80);
				tempMap.put("align", "right");
				tempMap.put("search", false);
				
				colModels.element(tempMap);
				tempMap.clear();
				
				if(i+1 == employee_list.size()) {
					query+= " SUM(IF(EMPLOYEE_NAME = '"+ employee.get("EMPLOYEE_NAME") +"',CNT, 0)) MEMBER"+ i + " ";
				}else {
					query+= " SUM(IF(EMPLOYEE_NAME = '"+ employee.get("EMPLOYEE_NAME") +"',CNT, 0)) MEMBER"+ i + ", ";
				}
						
			}
			paramMap.put("QUERY",query);
			
			Map<String, Object> resultMap = sheetService.getThirdList(paramMap);
			
			resultMap.put("colNames", colNames);
			resultMap.put("colModels", colModels);
			
			
			logger.info("["+getClass().getSimpleName()+"] [list_03] query ["+query+"]");
			logger.info("["+getClass().getSimpleName()+"] [list_03] colNames ["+colNames.toString()+"]");
			logger.info("["+getClass().getSimpleName()+"] [list_03] colModels ["+colModels.toString()+"]");
			
			mav.addAllObjects(resultMap);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_03] 작업현황 조회 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_03] end");
		}
		*/
		return mav;
	}
	
	@RequestMapping(value="/list_07")
	public ModelAndView list_07(@RequestParam("START_DATE")String start_date,
								@RequestParam("END_DATE")String end_date,
								HttpSession session){
		logger.debug("["+getClass().getSimpleName()+"] [list_07] start");
		logger.info("["+getClass().getSimpleName()+"] [list_07] start_date["+start_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_07] end_date["+end_date+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("START_DATE", start_date);
			paramMap.put("END_DATE", end_date);
			paramMap.put("EMPLOYEE_NO", employee_no);
			
			List<Map<String, Object>> sheetList = sheetService.getMyWorkSheetList(paramMap);
			mav.addObject("sheetList", sheetList);
			
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			logger.error("["+getClass().getSimpleName()+"] [list_07] 내 일지보기 오류");
			e.printStackTrace();
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_07] end");
		}
		return mav;
	}

	@RequestMapping(value="/sheet_01.isnet")
	public ModelAndView sheet_01(){
		logger.debug("["+getClass().getSimpleName()+"] [sheet_01] start");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/worksheet/sheet_01");
		
		try{
			Map<String, Object> resultData = commonCodeMap.getCommonCodeMap();
			
			List<Map<String, Object>> customerList = (List<Map<String, Object>>)resultData.get("customerList");
			List<Map<String, Object>> employeeList = (List<Map<String, Object>>)resultData.get("employeeList");
			List<Map<String, Object>> supportList = (List<Map<String, Object>>)resultData.get("supportTypeList");
			
			String customerListString = StringUtil.listToString(customerList, "CUSTOMER_NAME", "CUSTOMER_NO");
			String employeeListString = StringUtil.listToString(employeeList, "EMPLOYEE_NAME", "EMPLOYEE_NO");
			String supportListString = StringUtil.listToString(supportList, "SUPPORT_TYPE_NAME", "SUPPORT_TYPE_NAME");
	
			mav.addObject("customerValues", customerListString);
			mav.addObject("employeeValues", employeeListString);
			mav.addObject("supportValues", supportListString);
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [sheet_01] end");
		}		
		return mav;
	}

	//업무일지 상세 보기
	@RequestMapping(value="/sheet_02.isnet")
	public ModelAndView sheet_02(   @RequestParam(value="write_date",required=true)String write_date,
									@RequestParam(value="employee_no", required=true)int employee_no){
		logger.debug("["+getClass().getSimpleName()+"] [sheet_02] start");
		
		logger.info("["+getClass().getSimpleName()+"] [sheet_02]write_date ["+write_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [sheet_02]employee_no ["+employee_no+"]");


		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("WRITE_DATE", write_date);
		paramMap.put("EMPLOYEE_NO", employee_no);
				
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/worksheet/sheet_02");
		
		try{

			mav.addObject("write_date", write_date);
			mav.addObject("employee_no", employee_no);
			
			Map<String, Object>resultData = sheetService.getSheetByDate(paramMap);
			logger.debug("["+getClass().getSimpleName()+"] [sheet_02] resultData ["+resultData.size()+"]");
			mav.addAllObjects(resultData);
		
		}catch(Exception e){
			e.printStackTrace();
			mav.setViewName("/worksheet/sheet_01");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [sheet_02] end");
		}		
		return mav;
	}
	
	//업무일지 수정	
	@RequestMapping(value="/sheet_03.isnet")
	public ModelAndView sheet_03(@RequestParam(value="sheet_no")int sheet_no){
		logger.debug("["+getClass().getSimpleName()+"] [sheet_03] start");
		logger.info("["+getClass().getSimpleName()+"] [sheet_03] sheet_no["+sheet_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/worksheet/sheet_03");
		
		try{

			Map<String, Object> resultData = commonCodeMap.getCommonCodeMap();
			List<Map<String, Object>> customerList = (List<Map<String, Object>>)resultData.get("customerList");
			List<Map<String, Object>> employeeList = (List<Map<String, Object>>)resultData.get("employeeList");
			List<Map<String, Object>> supportList = (List<Map<String, Object>>)resultData.get("supportTypeList");
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("SHEET_NO", sheet_no);
			Map<String, Object> sheet =  sheetService.getSheetByNo(paramMap);
			
			mav.addObject("customerList", customerList);
			mav.addObject("employeeList", employeeList);
			mav.addObject("supportList", supportList);

			mav.addObject("sheet", sheet);

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [sheet_03] end");
		}		
		return mav;
	}
	
	//유선 및 원격지원 등록
	
	@RequestMapping(value="/sheet_04.isnet")
	public ModelAndView sheet_04(@RequestParam(value="remote_no")int remote_no){
		logger.debug("["+getClass().getSimpleName()+"] [sheet_04] start");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/worksheet/sheet_04");
		
		try{
			Map<String, Object> resultData = commonCodeMap.getCommonCodeMap();
			List<Map<String, Object>> customerList = (List<Map<String, Object>>)resultData.get("customerList");
			List<Map<String, Object>> employeeList = (List<Map<String, Object>>)resultData.get("employeeList");
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("REMOTE_NO", remote_no);
			
			Map<String, Object> remote = (Map<String, Object>)sheetService.getRemoteSheet(paramMap);
			
			mav.addObject("remote", remote);
			mav.addObject("customerList", customerList);
			mav.addObject("employeeList", employeeList);
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [sheet_04] end");
		}		
		return mav;
	}
	
	
	
	//업무일지 등록페이지
	
	@RequestMapping(value="/sheet_05.isnet")
	public ModelAndView sheet_05(){
		logger.debug("["+getClass().getSimpleName()+"] [sheet_05] start");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/worksheet/sheet_05");
		
		try{
			Map<String, Object> resultData = commonCodeMap.getCommonCodeMap();
			
			List<Map<String, Object>> customerList = (List<Map<String, Object>>)resultData.get("customerList");
			List<Map<String, Object>> employeeList = (List<Map<String, Object>>)resultData.get("employeeList");
			List<Map<String, Object>> supportList = (List<Map<String, Object>>)resultData.get("supportTypeList");

			mav.addObject("customerList", customerList);
			mav.addObject("employeeList", employeeList);
			mav.addObject("supportList", supportList);
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [sheet_05] end");
		}		
		return mav;
	}
	
	//작업현황
	@RequestMapping(value="/sheet_06.isnet")
	public ModelAndView sheet_06(){
		logger.debug("["+getClass().getSimpleName()+"] [sheet_06] start");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/worksheet/sheet_06");
	
		Map<String, Object> resultData = commonCodeMap.getCommonCodeMap();
		mav.addAllObjects(resultData);
		
		logger.debug("["+getClass().getSimpleName()+"] [sheet_06] end");
		return mav;
	}

	@RequestMapping(value="/sheet_07.isnet", method=RequestMethod.GET)
	public ModelAndView sheet_07(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/worksheet/sheet_07");
		return mav;
	}

	@RequestMapping(value="/add_sheet01.isnet", method = { RequestMethod.POST,RequestMethod.GET } )
	public ModelAndView add_sheet01(@RequestParam(value="WRITE_DATE")String write_date,
									@RequestParam(value="CUSTOMER_NO")int customer_no,
									@RequestParam(value="CLIENT_NAME")String client_name,
									@RequestParam(value="START_TIME")String start_time,
									@RequestParam(value="START_MIN")String start_min,
									@RequestParam(value="END_TIME")String end_time,
									@RequestParam(value="END_MIN")String end_min,
									@RequestParam(value="SHEET_CONTENT")String sheet_content,
									@RequestParam(value="SHEET_TITLE")String sheet_title,
									@RequestParam(value="SUPPORT_TYPE_NO")String [] support_type_no,
									HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_sheet01] start");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet01] write_date ["+write_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet01] customer_no ["+customer_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet01] client_name ["+client_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet01] start_time ["+start_time+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet01] start_min ["+start_min+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet01] end_time ["+end_time+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet01] end_min ["+end_min+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet01] sheet_content ["+sheet_content+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet01] sheet_title ["+sheet_title+"]");

		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("WRITE_DATE", write_date);
		paramMap.put("CUSTOMER_NO", customer_no);
		paramMap.put("CLIENT_NAME", client_name);
		paramMap.put("START_TIME",start_time +":"+start_min);
		paramMap.put("END_TIME", end_time+":"+end_min);
		paramMap.put("SHEET_CONTENT", sheet_content);
		paramMap.put("SHEET_TITLE", sheet_title);
		paramMap.put("SUPPORT_TYPE_NO", support_type_no);

		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			logger.info("["+getClass().getSimpleName()+"] [add_sheet01] employee_no ["+employee_no+"]");

			paramMap.put("EMPLOYEE_NO", employee_no);

			sheetService.addSheet(paramMap);			
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_sheet01] 업무일지 등록중 오류가 발생하였습니다.");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_sheet01] end");
		}
		
		return mav;
	}

	//원격지원 등록
	
	@RequestMapping(value="/add_sheet02.isnet", method = { RequestMethod.POST,RequestMethod.GET } )
	public ModelAndView add_sheet02(@RequestParam(value="WRITE_DATE1")String write_date,
									@RequestParam(value="CUSTOMER_NO1")int customer_no,
									@RequestParam(value="CLIENT_NAME1")String client_name,
									@RequestParam(value="START_TIME1")String start_time,
									@RequestParam(value="START_MIN1")String start_min,
									@RequestParam(value="END_TIME1")String end_time,
									@RequestParam(value="END_MIN1")String end_min,
									@RequestParam(value="CONTENTS1")String contents,
									HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_sheet02] start");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet02] write_date ["+write_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet02] customer_no ["+customer_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet02] client_name ["+client_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet02] start_time ["+start_time+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet02] start_min ["+start_min+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet02] end_time ["+end_time+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet02] end_min ["+end_min+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_sheet02] content ["+contents+"]");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("WRITE_DATE", write_date);
		paramMap.put("CUSTOMER_NO", customer_no);
		paramMap.put("CLIENT_NAME", client_name);
		paramMap.put("ACCEPTED_TIME",start_time +":"+start_min);
		paramMap.put("COMPLETED_TIME", end_time+":"+end_min);
		paramMap.put("REMOTE_CONTENT", contents);
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			logger.info("["+getClass().getSimpleName()+"] [add_sheet02] employee_no ["+employee_no+"]");
			paramMap.put("EMPLOYEE_NO", employee_no);

			sheetService.addRemoteSheet(paramMap);
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_sheet02] 이슈 등록중 오류가 발생하였습니다.");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_sheet02] end");
		}
		
		return mav;
	}
	
	//업무일지 삭제
	@RequestMapping(value="/del_sheet01.isnet")
	public ModelAndView del_Sheet01(@RequestParam(value="sheet_no")int sheet_no){
		
		logger.debug("["+getClass().getSimpleName()+"] [del_Sheet01] start");
		logger.info("["+getClass().getSimpleName()+"] [del_Sheet01] sheet_no ["+sheet_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("SHEET_NO", sheet_no);
			sheetService.deleteSheet(paramMap);
			mav.addObject("isSuccess", true);
			
		}catch(Exception e){
			e.printStackTrace();
			mav.addObject("isSuccess", false);
			logger.error("["+getClass().getSimpleName()+"] [del_Sheet01] 업무일지 삭제중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [del_Sheet01] end");
		}
		
		return mav;
	}
	
	//원격지원 일지 삭제
	
	@RequestMapping(value="/del_remote01.isnet")
	public ModelAndView del_Remote01(@RequestParam(value="remote_no")int remote_no,
																		HttpSession session){
		Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
		int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
		logger.info("["+getClass().getSimpleName()+"] [add_sheet01] employee_no ["+employee_no+"]");
		
		logger.debug("["+getClass().getSimpleName()+"] [del_Remote01] start");
		logger.info("["+getClass().getSimpleName()+"] [del_Remote01] sheet_no ["+remote_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("REMOTE_NO", remote_no);
		paramMap.put("EMPLOYEE_NO", employee_no);
		
		try{
			
			sheetService.deleteRemoteSheet(paramMap);
			mav.addObject("isSuccess", true);
			
		}catch(Exception e){
			e.printStackTrace();
			mav.addObject("isSuccess", false);
			logger.error("["+getClass().getSimpleName()+"] [del_Remote01] 원격지원일지 삭제중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [del_Remote01] end");
		}
		
		return mav;
	}

	//업무일지 수정
		
		@RequestMapping(value="/update_sheet01.isnet", method = { RequestMethod.POST,RequestMethod.GET } )
		public ModelAndView update_sheet01(@RequestParam(value="WRITE_DATE")String write_date,
										@RequestParam(value="SHEET_NO")int sheet_no,
										@RequestParam(value="CUSTOMER_NO")int customer_no,
										@RequestParam(value="CLIENT_NAME")String client_name,
										@RequestParam(value="START_TIME")String start_time,
										@RequestParam(value="START_MIN")String start_min,
										@RequestParam(value="END_TIME")String end_time,
										@RequestParam(value="END_MIN")String end_min,
										@RequestParam(value="SHEET_CONTENT")String sheet_content,
										@RequestParam(value="SHEET_TITLE")String sheet_title,
										@RequestParam(value="SUPPORT_TYPE_NO")String [] support_type_no,
										HttpSession session){
			
			logger.debug("["+getClass().getSimpleName()+"] [update_sheet01] start");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet01] write_date ["+write_date+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet01] customer_no ["+customer_no+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet01] client_name ["+client_name+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet01] start_time ["+start_time+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet01] start_min ["+start_min+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet01] end_time ["+end_time+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet01] end_min ["+end_min+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet01] sheet_title ["+sheet_title+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet01] sheet_content ["+sheet_content+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet01] support_type_no ["+support_type_no.length+"]");

			

			ModelAndView mav = new ModelAndView();
			mav.setView(jsonView);
			
			try{
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("SHEET_NO", sheet_no);
				paramMap.put("WRITE_DATE", write_date);
				paramMap.put("CUSTOMER_NO", customer_no);
				paramMap.put("CLIENT_NAME", client_name);
				paramMap.put("START_TIME",start_time +":"+start_min);
				paramMap.put("END_TIME", end_time+":"+end_min);
				paramMap.put("SHEET_TITLE", sheet_title);
				paramMap.put("SHEET_CONTENT", sheet_content);
				paramMap.put("SUPPORT_TYPE_NO", support_type_no);
				
				sheetService.updateSheet(paramMap);
				
				mav.addObject("isSuccess", true);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("["+getClass().getSimpleName()+"] [update_sheet01] 업무일지 등록중 오류가 발생하였습니다.");
				mav.addObject("isSuccess", false);
			}finally{
				logger.debug("["+getClass().getSimpleName()+"] [update_sheet01] end");
			}
			
			return mav;
		}
	
		//원격지원 등록
		
		@RequestMapping(value="/update_sheet02.isnet", method = { RequestMethod.POST,RequestMethod.GET } )
		public ModelAndView update_sheet02(@RequestParam(value="REMOTE_NO")int remote_no,
				                        @RequestParam(value="WRITE_DATE")String write_date,
										@RequestParam(value="CUSTOMER_NO")int customer_no,
										@RequestParam(value="CLIENT_NAME")String client_name,
										@RequestParam(value="START_TIME")String start_time,
										@RequestParam(value="START_MIN")String start_min,
										@RequestParam(value="END_TIME")String end_time,
										@RequestParam(value="END_MIN")String end_min,
										@RequestParam(value="REMOTE_CONTENT")String contents,
										HttpSession session){
			
			logger.debug("["+getClass().getSimpleName()+"] [add_sheet02] start");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet02] remote_no ["+remote_no+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet02] write_date ["+write_date+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet02] customer_no ["+customer_no+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet02] client_name ["+client_name+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet02] start_time ["+start_time+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet02] start_min ["+start_min+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet02] end_time ["+end_time+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet02] end_min ["+end_min+"]");
			logger.info("["+getClass().getSimpleName()+"] [update_sheet02] contents ["+contents+"]");
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("REMOTE_NO", remote_no);
			paramMap.put("WRITE_DATE", write_date);
			paramMap.put("CUSTOMER_NO", customer_no);
			paramMap.put("CLIENT_NAME", client_name);
			paramMap.put("ACCEPTED_TIME",start_time +":"+start_min);
			paramMap.put("COMPLETED_TIME", end_time+":"+end_min);
			paramMap.put("REMOTE_CONTENT", contents);
			
			ModelAndView mav = new ModelAndView();
			mav.setView(jsonView);
			
			try{
				Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
				int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
				logger.info("["+getClass().getSimpleName()+"] [update_sheet02] employee_no ["+employee_no+"]");
				paramMap.put("EMPLOYEE_NO", employee_no);

				sheetService.updateRemoteSheet(paramMap);
				mav.addObject("isSuccess", true);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("["+getClass().getSimpleName()+"] [update_sheet02] 이슈 등록중 오류가 발생하였습니다.");
				mav.addObject("isSuccess", false);
			}finally{
				logger.debug("["+getClass().getSimpleName()+"] [update_sheet02] end");
			}
			
			return mav;
		}
		
}
