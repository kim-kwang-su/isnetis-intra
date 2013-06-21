package com.isnet.mgr.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.isnet.mgr.common.CommonCodeBean;
import com.isnet.mgr.common.CommonConstant;
import com.isnet.mgr.common.DateUtil;
import com.isnet.mgr.common.StringUtil;
import com.isnet.mgr.service.CRMService;
import com.isnet.mgr.view.CrmExcelView;

@Controller
@RequestMapping("/crm")
public class CRMController {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private CommonCodeBean commonCodeBean;
	@Autowired
	private MappingJacksonJsonView jsonView;
	@Autowired
	private CrmExcelView crmExcelView;
	@Autowired
	private CRMService crmService;
	
	// 불견일수 리스트 페이지로 이동
	@RequestMapping(value="/crm_00.isnet", method=RequestMethod.GET)
	public ModelAndView crm_00(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/crm/crm_00");
		try{
			List<Map<String, Object>> companyList = crmService.getCrmCompanyList();
			mav.addObject("crmCompanyList", companyList);
			
			List<Map<String, Object>> employeeList = commonCodeBean.getEmployeeList();
			mav.addObject("employeeList", employeeList);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [crm_00] 관리내역페이지 정보 조회 중 오류");
		}
		
		return mav;
	}
	
	// 고객 리스트 페이지로 이동
	@RequestMapping(value="/crm_01.isnet", method=RequestMethod.GET)
	public ModelAndView crm_01(){
		
		ModelAndView mav = new ModelAndView();

		try{
			List<Map<String, Object>> companyList = crmService.getCrmCompanyList();
			String companyListString = StringUtil.listToString(companyList, "COMPANY_NAME", "COMPANY_NO");
			mav.addObject("companyValues", companyListString);
			
			mav.setViewName("/crm/crm_01");
		}catch(Exception e){
			e.printStackTrace();
			mav.setViewName("/login");
		}
		
		return mav;
	}
	
	// 고객 등록페이지로 이동
	@RequestMapping(value="/crm_02.isnet", method=RequestMethod.GET)
	public ModelAndView crm_02(){
		
		ModelAndView mav = new ModelAndView();

		try{
			List<Map<String, Object>> companyList = crmService.getCrmCompanyList();
			mav.addObject("crmCompanyList", companyList);
			mav.setViewName("/crm/crm_02");
		}catch(Exception e){
			e.printStackTrace();
			mav.setViewName("/login");
		}
		
		return mav;
	}
	
	// 고객사 리스트 페이지로 이동
	@RequestMapping(value="/crm_03.isnet")
	public ModelAndView crm_03(){
		ModelAndView mav = new ModelAndView();
		try{
			mav.setViewName("/crm/crm_03");
		}catch(Exception e){
			e.printStackTrace();
			mav.setViewName("/login");
		}
		return mav;
	}
	
	// 고객사 캘린더 페이지로 이동
	@RequestMapping(value="/crm_05.isnet")
	public ModelAndView crm_05(){
		ModelAndView mav = new ModelAndView();
		try{
			mav.setViewName("/crm/crm_05");
		}catch(Exception e){
			e.printStackTrace();
			mav.setViewName("/login");
		}
		return mav;
	}
	
	// 고객 등록
	@RequestMapping(value="/add_crm_01.isnet", method=RequestMethod.POST)
	public ModelAndView add_crm_01(@RequestParam(value="COMPANY_NO")String company_no,
									@RequestParam(value="CUSTOMER_NAME")String customer_name,
									@RequestParam(value="CUSTOMER_DEPT", required=false)String customer_dept,
									@RequestParam(value="CUSTOMER_JOB", required=false)String customer_job,
									@RequestParam(value="CUSTOMER_POSITION", required=false)String customer_position,
									@RequestParam(value="CUSTOMER_BIRTH", required=false)String customer_birth,
									@RequestParam(value="IS_LUNAR_DATE", required=false)String is_lunar_date,
									@RequestParam(value="IS_LEAP_MONTH", required=false)String is_leap_month,
									@RequestParam(value="CUSTOMER_AGE", required=false)String customer_age,
									@RequestParam(value="CUSTOMER_BLOOD", required=false)String customer_blood,
									@RequestParam(value="CUSTOMER_HEIGHT", required=false)String customer_height,
									@RequestParam(value="CUSTOMER_WEIGHT", required=false)String customer_weight,
									@RequestParam(value="CUSTOMER_CAR", required=false)String customer_car,
									@RequestParam(value="CUSTOMER_PHONE", required=false)String customer_phone,
									@RequestParam(value="CUSTOMER_EMAIL", required=false)String customer_email,
									@RequestParam(value="CUSTOMER_HOME_TEL", required=false)String customer_home_tel,
									@RequestParam(value="CUSTOMER_COMPANY_TEL", required=false)String customer_company_tel,
									@RequestParam(value="CUSTOMER_HOME_ADDR", required=false)String customer_home_addr,
									@RequestParam(value="CUSTOMER_HOMETOWN", required=false)String customer_homeTown,
									@RequestParam(value="ISNET_IMPRESSION", required=false)String isnet_impression,
									@RequestParam(value="FIRST_COMPANY_NAME")String first_company_name,
									@RequestParam(value="SECOND_COMPANY_NAME", required=false)String second_company_name,
									@RequestParam(value="THIRD_COMPANY_NAME", required=false)String third_company_name,
									@RequestParam(value="GRADUATE_SCHOOL_NAME", required=false)String graduate_school_name,
									@RequestParam(value="UNIVERSITY_NAME", required=false)String university_name,
									@RequestParam(value="HIGH_SCHOOL_NAME", required=false)String high_school_name,
									@RequestParam(value="MIDDLE_SCHOOL_NAME", required=false)String middle_school_name,
									@RequestParam(value="PRIMARY_SCHOOL_NAME", required=false)String primary_school_name,
									@RequestParam(value="MAJOR_NAME", required=false)String major_name,
									@RequestParam(value="MARRIED_YN", required=false)String married_yn,
									@RequestParam(value="WEDDING_DAY", required=false)String wedding_day,
									@RequestParam(value="HOBBY", required=false)String hobby,
									@RequestParam(value="HOBBY_GRADE", required=false)String hobby_grade,
									@RequestParam(value="CUSTOMER_ETC", required=false)String customer_etc){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_crm_01] start");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] company_no["+company_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_name["+customer_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_dept["+customer_dept+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_job["+customer_job+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_position["+customer_position+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_birth["+customer_birth+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] is_lunar_date["+is_lunar_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] is_leap_month["+is_leap_month+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_age["+customer_age+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_blood["+customer_blood+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_height["+customer_height+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_weight["+customer_weight+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_car["+customer_car+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_phone["+customer_phone+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_email["+customer_email+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_home_tel["+customer_home_tel+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_company_tel["+customer_company_tel+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_home_addr["+customer_home_addr+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_homeTown["+customer_homeTown+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] isnet_impression["+isnet_impression+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] first_company_name["+first_company_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] second_company_name["+second_company_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] third_company_name["+third_company_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] graduate_school_name["+graduate_school_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] university_name["+university_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] high_school_name["+high_school_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] middle_school_name["+middle_school_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] primary_school_name["+primary_school_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] major_name["+major_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] married_yn["+married_yn+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] wedding_day["+wedding_day+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] hobby["+hobby+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] hobby["+hobby_grade+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_01] customer_etc["+customer_etc+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("COMPANY_NO", company_no);
			paramMap.put("CUSTOMER_NAME", customer_name);
			paramMap.put("CUSTOMER_DEPT", customer_dept);
			paramMap.put("CUSTOMER_JOB", customer_job);
			paramMap.put("CUSTOMER_POSITION", customer_position);
			paramMap.put("CUSTOMER_BIRTH", customer_birth);
			if(is_lunar_date == null){
				paramMap.put("IS_LUNAR_DATE", "N");
			}else{
				paramMap.put("IS_LUNAR_DATE", is_lunar_date);
			}
			if(is_leap_month == null){
				paramMap.put("IS_LEAP_MONTH", "N");
			}else{
				paramMap.put("IS_LEAP_MONTH", is_leap_month);
			}
			paramMap.put("CUSTOMER_AGE", customer_age);
			paramMap.put("CUSTOMER_BLOOD", customer_blood);
			paramMap.put("CUSTOMER_HEIGHT", customer_height);
			paramMap.put("CUSTOMER_WEIGHT", customer_weight);
			paramMap.put("CUSTOMER_CAR", customer_car);
			paramMap.put("CUSTOMER_PHONE", customer_phone);
			paramMap.put("CUSTOMER_EMAIL", customer_email);
			paramMap.put("CUSTOMER_HOME_TEL", customer_home_tel);
			paramMap.put("CUSTOMER_COMPANY_TEL", customer_company_tel);
			paramMap.put("CUSTOMER_HOME_ADDR", customer_home_addr);
			paramMap.put("CUSTOMER_HOMETOWN", customer_homeTown);
			paramMap.put("ISNET_IMPRESSION", isnet_impression);
			paramMap.put("FIRST_COMPANY_NAME", first_company_name);
			paramMap.put("SECOND_COMPANY_NAME", second_company_name);
			paramMap.put("THIRD_COMPANY_NAME", third_company_name);
			paramMap.put("GRADUATE_SCHOOL_NAME", graduate_school_name);
			paramMap.put("UNIVERSITY_NAME", university_name);
			paramMap.put("HIGH_SCHOOL_NAME", high_school_name);
			paramMap.put("MIDDLE_SCHOOL_NAME", middle_school_name);
			paramMap.put("PRIMARY_SCHOOL_NAME", primary_school_name);
			paramMap.put("MAJOR_NAME", major_name);
			paramMap.put("MARRIED_YN", married_yn);
			paramMap.put("WEDDING_DAY", wedding_day);
			paramMap.put("HOBBY", hobby);
			paramMap.put("HOBBY_GRADE", hobby_grade);
			paramMap.put("CUSTOMER_ETC", customer_etc);
			
			crmService.insertCrmCustomer(paramMap);
			
			mav.addObject("CUSTOMER_NO", paramMap.get("CUSTOMER_NO"));
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_crm_01] 고개정보 등록 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_crm_01] end");
		}
		return mav;
	}
	
	// 고객방문이력 등록
	@RequestMapping(value="/add_crm_02.isnet", method=RequestMethod.POST)
	public ModelAndView add_crm_02(@RequestParam("COMPANY_NO")int company_no,
									@RequestParam("CUSTOMER_NO")int customer_no,
									@RequestParam("EMPLOYEE_NO")int[] employee_noes,
									@RequestParam("VISITED_DATE")String visited_date,
									@RequestParam("VISITED_GUBUN")String visited_gubun,
									@RequestParam("VISITED_MEMO")String visited_memo){
		logger.debug("["+getClass().getSimpleName()+"] [add_crm_02] start");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_02] company_no["+company_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_02] customer_no["+customer_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_02] employee_noes["+Arrays.toString(employee_noes)+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_02] visited_date["+visited_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_02] visited_gubun["+visited_gubun+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_02] visited_memo["+visited_memo+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("COMPANY_NO", company_no);
			paramMap.put("CUSTOMER_NO", customer_no);
			paramMap.put("EMPLOYEE_NOES", employee_noes);
			paramMap.put("VISITED_DATE", visited_date);
			paramMap.put("VISITED_GUBUN", visited_gubun);
			paramMap.put("VISITED_MEMO", visited_memo);
			paramMap.put("IS_USED", "Y");
			paramMap.put("CREATE_DATE", DateUtil.dateToString(CommonConstant.DATE_FORMAT_05));
			
			crmService.insertCrmSheet(paramMap);
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_crm_02] 고객 관리내역 등록 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_crm_02] end");
		}
		
		return mav;
	}
	
	// 고객사를 등록합니다.
	@RequestMapping(value="/add_crm_03.isnet", method=RequestMethod.POST)
	public ModelAndView add_crm_03(@RequestParam("COMPANY_NAME")String company_name,
									@RequestParam(value="COMPANY_TEL", required=false)String company_tel,
									@RequestParam(value="COMPANY_FAX", required=false)String company_fax,
									@RequestParam(value="COMPANY_ADDR", required=false)String company_addr){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_crm_03] start");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_03] company_name["+company_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_03] company_tel["+company_tel+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_03] company_fax["+company_fax+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_03] company_addr["+company_addr+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("COMPANY_NAME", company_name);
			paramMap.put("COMPANY_TEL", company_tel);
			paramMap.put("COMPANY_FAX", company_fax);
			paramMap.put("COMPANY_ADDR", company_addr);
			
			crmService.insertCrmCompany(paramMap);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [add_crm_03] 고객사정보 등록 완료");
		}catch(Exception e){
			mav.addObject("isSuccess", false);
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_crm_03] ["+e.getMessage()+"]");
			logger.error("["+getClass().getSimpleName()+"] [add_crm_03] 고객사 정보 등록 중 오류가 발생하였습니다.");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_crm_03] end");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/add_crm_04.isnet", method=RequestMethod.POST)
	public ModelAndView add_crm_04(@RequestParam("REF_CUSTOMER_NO")int ref_customer_no,
									@RequestParam("CUSTOMER_NAME")String family_name,
									@RequestParam("CUSTOMER_GUBUN")String family_gubun,
									@RequestParam(value="CUSTOMER_JOB", required=false)String family_job,
									@RequestParam(value="CUSTOMER_AGE", required=false)String family_age,
									@RequestParam(value="CUSTOMER_BIRTH", required=false)String family_birth,
									@RequestParam(value="IS_LUNAR_DATE", required=false)String is_lunar_date,
									@RequestParam(value="IS_LEAP_MONTH", required=false)String is_leap_month){
		logger.debug("["+getClass().getSimpleName()+"] [add_crm_04] start");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_04] ref_customer_no["+ref_customer_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_04] customer_name["+family_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_04] customer_gubun["+family_gubun+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_04] customer_job["+family_job+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_04] customer_age["+family_age+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_04] customer_birth["+family_birth+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_04] is_lunar_date["+is_lunar_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_crm_04] is_leap_month["+is_leap_month+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("REF_CUSTOMER_NO", ref_customer_no);
			paramMap.put("CUSTOMER_NAME", family_name);
			paramMap.put("CUSTOMER_GUBUN", family_gubun);
			paramMap.put("CUSTOMER_JOB", family_job);
			paramMap.put("CUSTOMER_AGE", family_age);
			paramMap.put("CUSTOMER_BIRTH", family_birth);
			if(is_lunar_date == null){
				paramMap.put("IS_LUNAR_DATE", "N");
			}else{
				paramMap.put("IS_LUNAR_DATE", is_lunar_date);
			}
			if(is_leap_month == null){
				paramMap.put("IS_LEAP_MONTH", "N");
			}else{
				paramMap.put("IS_LEAP_MONTH", is_leap_month);
			}
			
			crmService.insertCrmCustomerFamily(paramMap);
			mav.addObject("isSuccess", true);
			
			logger.info("["+getClass().getSimpleName()+"] ref_customer_no["+ref_customer_no+"] 가족정보 저장완료");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_crm_04] 고객 가족정보 등록 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_crm_04] end");
		}
		return mav;
		
	}
	
	// 고객정보 상세보기 및 수정 페이지로 이동합니다.
	@RequestMapping(value="/detail_crm_01.isnet", method=RequestMethod.GET)
	public ModelAndView detail_crm_01(@RequestParam(value="CUSTOMER_NO")int customer_no){
		logger.debug("["+getClass().getSimpleName()+"] [detail_crm_01] start");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/crm/crm_04");
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("CUSTOMER_NO", customer_no);
			
			Map<String, Object> customerInfo = crmService.getCrmCustomerInfo(paramMap);
			mav.addObject("customerInfo", customerInfo);
			
			List<Map<String, Object>> companyList = crmService.getCrmCompanyList();
			mav.addObject("crmCompanyList", companyList);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [detail_crm_01] customer_no["+customer_no+"] 고객정보 조회 오류");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/detail_crm_02.isnet", method=RequestMethod.GET)
	public ModelAndView detail_crm_02(@RequestParam(value="CUSTOMER_NO")int customer_no){
		logger.debug("["+getClass().getSimpleName()+"] [detail_crm_02] start");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("REF_CUSTOMER_NO", customer_no);
			
			List<Map<String, Object>> familyList = crmService.getCrmFamilyList(paramMap);
			mav.addObject("familyList", familyList);
			mav.addObject("isSuccess", true);
		}catch (Exception e) {
			e.printStackTrace();
			mav.addObject("isSuccess", false);
			logger.error("["+getClass().getSimpleName()+"] [detail_crm_01] customer_no["+customer_no+"] 고객정보 조회 오류");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/list_crm_01.isnet", method=RequestMethod.POST)
	public ModelAndView list_crm_01(@RequestParam(value="page", defaultValue="1")int page,
								@RequestParam(value="rows", defaultValue="20")int rows,
								@RequestParam(value="sidx", required=false)String sidx,
								@RequestParam(value="sord", required=false)String sord,
								@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
								@RequestParam(value="searchField", required=false)String searchField,
								@RequestParam(value="searchString", required=false)String searchString,
								@RequestParam(value="searchOper", required=false)String searchOper){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_crm_01] start");
		
		logger.info("["+getClass().getSimpleName()+"] [list_crm_01] page ["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_01] rows ["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_01] sidx ["+sidx+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_01] sord ["+sord+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_01] _search ["+_search+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_01] searchField ["+searchField+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_01] searchString ["+searchString+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_01] searchOper ["+searchOper+"]");
		
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
			
			Map<String, Object> resultData = crmService.getCrmCustomerList(paramMap);
			mav.addAllObjects(resultData);

		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_crm_01] 고객방문내역 조회 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_crm_01] end");
		}
		return mav;
	}
	
	// 고객사별 담당자를 조회합니다.
	@RequestMapping(value="/list_crm_02.isnet", method=RequestMethod.GET)
	public ModelAndView list_crm_02(@RequestParam("COMPANY_NO")int company_no){
		logger.debug("["+getClass().getSimpleName()+"] [list_crm_02] start");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_02] company_no["+company_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("COMPANY_NO", company_no);
			List<Map<String, Object>> crmCompanyList = crmService.getCustomeListByCompanyNo(paramMap);
			mav.addObject("crmCustomerList", crmCompanyList);
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_crm_02] company_no["+company_no+"] 고객사별 담당자 조회 중 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_crm_02] end");
		}
		return mav;
	}
	
	// 고객 방문이력 조회
	@RequestMapping(value="/list_crm_03.isnet", method=RequestMethod.POST)
	public ModelAndView list_crm_03(@RequestParam(value="page", defaultValue="1")int page,
									@RequestParam(value="rows", defaultValue="20")int rows,
									@RequestParam(value="sidx", required=false)String sidx,
									@RequestParam(value="sord", required=false)String sord,
									@RequestParam(value="COMPANY_NO")int company_no,
									@RequestParam(value="EMPLOYEE_NO")int employee_no,
									@RequestParam(value="START_DATE")String start_date,
									@RequestParam(value="END_DATE")String end_date,
									@RequestParam(value="RES_TYPE", defaultValue="json")String res_type){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_crm_03] start");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_03] page["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_03] rows["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_03] sidx["+sidx+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_03] sord["+sord+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_03] company_no["+company_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_03] employee_no["+employee_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_03] start_date["+start_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_03] end_date["+end_date+"]");
		
		ModelAndView mav = new ModelAndView();
		if(res_type.equals(CommonConstant.RESPONSE_TYPE_FOR_JSON)){
			mav.setView(jsonView);
		}else if(res_type.equals(CommonConstant.RESPONSE_TYPE_FOR_EXCEL)){
			mav.setView(crmExcelView);
		}
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("PAGE", page);
			paramMap.put("ROWS", rows);
			paramMap.put("SIDX", sidx);
			paramMap.put("SORD", sord);
			if(company_no != CommonConstant.INVALID_VALUE){
				paramMap.put("COMPANY_NO", company_no);
			}
			if(employee_no != CommonConstant.INVALID_VALUE){
				paramMap.put("EMPLOYEE_NO", employee_no);
			}
			
			start_date = DateUtil.getPeriodDateToString(start_date, CommonConstant.DATE_FORMAT_02, -1);
			end_date = DateUtil.getPeriodDateToString(end_date, CommonConstant.DATE_FORMAT_02, 1);
			paramMap.put("START_DATE", start_date);			
			paramMap.put("END_DATE", end_date);
			
			Map<String, Object> resultData = crmService.getCrmManagementList(paramMap);
			mav.addAllObjects(resultData);
			
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_crm_03] 관리이력 조회 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_crm_03] end");
		}
		
		return mav;
	}
	
	// 고객사 리스트를 조회합니다.
	@RequestMapping(value="/list_crm_04.isnet")
	public ModelAndView list_crm_04(@RequestParam(value="page", defaultValue="1")int page,
									@RequestParam(value="rows", defaultValue="20")int rows,
									@RequestParam(value="sidx", required=false)String sidx,
									@RequestParam(value="sord", required=false)String sord,
									@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
									@RequestParam(value="searchField", required=false)String searchField,
									@RequestParam(value="searchString", required=false)String searchString,
									@RequestParam(value="searchOper", required=false)String searchOper){
		logger.debug("["+getClass().getSimpleName()+"] [list_crm_04] start");
		
		logger.info("["+getClass().getSimpleName()+"] [list_crm_04] page ["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_04] rows ["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_04] sidx ["+sidx+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_04] sord ["+sord+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_04] _search ["+_search+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_04] searchField ["+searchField+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_04] searchString ["+searchString+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_04] searchOper ["+searchOper+"]");
		
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
					
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("PAGE", page);
		paramMap.put("ROWS", rows);
		paramMap.put("SIDX", sidx);
		paramMap.put("SORD", sord);
		paramMap.put("_SEARCH", _search);

		try{
			if(_search){				
				paramMap.put("SEARCH_FIELD", searchField);
				paramMap.put("SEARCH_STRING", searchString);
				paramMap.put("SEARCH_OPER", searchOper);
			}
			
			Map<String, Object> resultData = crmService.getCrmCompanyList(paramMap);
			mav.addAllObjects(resultData);

		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_crm_04] 고객방문내역 조회 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_crm_04] end");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/list_crm_05.isnet", method=RequestMethod.GET)
	public ModelAndView list_crm_05(@RequestParam("MONTH")int month){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_crm_05] start");
		logger.info("["+getClass().getSimpleName()+"] [list_crm_05] month ["+ month+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("MONTH", month);
			
			List<Map<String, Object>> memorialDayList = crmService.getMemorialDayList(paramMap);
			mav.addObject("memorialDayList", memorialDayList);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [list_crm_05] 고객 생일 조회 완료");
			
		}catch(Exception e){
			e.printStackTrace();
			mav.addObject("isSuccess", false);
		}finally{
			logger.info("["+getClass().getSimpleName()+"] [list_crm_05] end");
		}
		
		return mav;
	}
	
	// 고객 방문이력 변경
	@RequestMapping(value="/update_crm_01.isnet", method=RequestMethod.POST)
	public ModelAndView update_crm_01(@RequestParam("SHEET_NO")int sheet_no,
									@RequestParam("COMPANY_NO")int company_no,
									@RequestParam("CUSTOMER_NO")int customer_no,
									@RequestParam("EMPLOYEE_NO")int[] employee_noes,
									@RequestParam("VISITED_DATE")String visited_date,
									@RequestParam("VISITED_GUBUN")String visited_gubun,
									@RequestParam("VISITED_MEMO")String visited_memo){
		logger.debug("["+getClass().getSimpleName()+"] [update_crm_01] start");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_01] sheet_no["+sheet_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_01] company_no["+company_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_01] customer_no["+customer_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_01] employee_noes["+Arrays.toString(employee_noes)+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_01] visited_date["+visited_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_01] visited_gubun["+visited_gubun+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_01] visited_memo["+visited_memo+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("SHEET_NO", sheet_no);
			paramMap.put("COMPANY_NO", company_no);
			paramMap.put("CUSTOMER_NO", customer_no);
			paramMap.put("EMPLOYEE_NOES", employee_noes);
			paramMap.put("VISITED_DATE", visited_date);
			paramMap.put("VISITED_GUBUN", visited_gubun);
			paramMap.put("VISITED_MEMO", visited_memo);
			
			crmService.modifyCrmManagementInfo(paramMap);
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_crm_01] 고객 관리내역 등록 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_crm_01] end");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/update_crm_02.isnet", method=RequestMethod.POST)
	public ModelAndView update_crm_02(@RequestParam(value="CUSTOMER_NO")int customer_no,
										@RequestParam(value="COMPANY_NO")int company_no,
										@RequestParam(value="CUSTOMER_NAME")String customer_name,
										@RequestParam(value="CUSTOMER_DEPT", required=false)String customer_dept,
										@RequestParam(value="CUSTOMER_JOB", required=false)String customer_job,
										@RequestParam(value="CUSTOMER_POSITION", required=false)String customer_position,
										@RequestParam(value="CUSTOMER_BIRTH", required=false)String customer_birth,
										@RequestParam(value="IS_LUNAR_DATE", required=false)String is_lunar_date,
										@RequestParam(value="IS_LEAP_MONTH", required=false)String is_leap_month,
										@RequestParam(value="CUSTOMER_AGE", required=false)String customer_age,
										@RequestParam(value="CUSTOMER_BLOOD", required=false)String customer_blood,
										@RequestParam(value="CUSTOMER_HEIGHT", required=false)String customer_height,
										@RequestParam(value="CUSTOMER_WEIGHT", required=false)String customer_weight,
										@RequestParam(value="CUSTOMER_CAR", required=false)String customer_car,
										@RequestParam(value="CUSTOMER_PHONE", required=false)String customer_phone,
										@RequestParam(value="CUSTOMER_EMAIL", required=false)String customer_email,
										@RequestParam(value="CUSTOMER_HOME_TEL", required=false)String customer_home_tel,
										@RequestParam(value="CUSTOMER_COMPANY_TEL", required=false)String customer_company_tel,
										@RequestParam(value="CUSTOMER_HOME_ADDR", required=false)String customer_home_addr,
										@RequestParam(value="CUSTOMER_HOMETOWN", required=false)String customer_homeTown,
										@RequestParam(value="ISNET_IMPRESSION", required=false)String isnet_impression,
										@RequestParam(value="FIRST_COMPANY_NAME")String first_company_name,
										@RequestParam(value="SECOND_COMPANY_NAME", required=false)String second_company_name,
										@RequestParam(value="THIRD_COMPANY_NAME", required=false)String third_company_name,
										@RequestParam(value="GRADUATE_SCHOOL_NAME", required=false)String graduate_school_name,
										@RequestParam(value="UNIVERSITY_NAME", required=false)String university_name,
										@RequestParam(value="HIGH_SCHOOL_NAME", required=false)String high_school_name,
										@RequestParam(value="MIDDLE_SCHOOL_NAME", required=false)String middle_school_name,
										@RequestParam(value="PRIMARY_SCHOOL_NAME", required=false)String primary_school_name,
										@RequestParam(value="MAJOR_NAME", required=false)String major_name,
										@RequestParam(value="MARRIED_YN", required=false)String married_yn,
										@RequestParam(value="WEDDING_DAY", required=false)String wedding_day,
										@RequestParam(value="HOBBY", required=false)String hobby,
										@RequestParam(value="HOBBY_GRADE", required=false)String hobby_grade,
										@RequestParam(value="CUSTOMER_ETC", required=false)String customer_etc){
		
		logger.debug("["+getClass().getSimpleName()+"] [update_crm_02] start");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_no["+customer_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] company_no["+company_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_name["+customer_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_dept["+customer_dept+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_job["+customer_job+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_position["+customer_position+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_birth["+customer_birth+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] is_lunar_date["+is_lunar_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] is_leap_month["+is_leap_month+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_age["+customer_age+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_blood["+customer_blood+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_height["+customer_height+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_weight["+customer_weight+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_car["+customer_car+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_phone["+customer_phone+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_email["+customer_email+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_home_tel["+customer_home_tel+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_company_tel["+customer_company_tel+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_home_addr["+customer_home_addr+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_homeTown["+customer_homeTown+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] isnet_impression["+isnet_impression+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] first_company_name["+first_company_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] second_company_name["+second_company_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] third_company_name["+third_company_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] graduate_school_name["+graduate_school_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] university_name["+university_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] high_school_name["+high_school_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] middle_school_name["+middle_school_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] primary_school_name["+primary_school_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] major_name["+major_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] married_yn["+married_yn+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] wedding_day["+wedding_day+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] hobby["+hobby+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] hobby["+hobby_grade+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_etc["+customer_etc+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("CUSTOMER_NO", customer_no);
			paramMap.put("COMPANY_NO", company_no);
			paramMap.put("CUSTOMER_NAME", customer_name);
			paramMap.put("CUSTOMER_DEPT", customer_dept);
			paramMap.put("CUSTOMER_JOB", customer_job);
			paramMap.put("CUSTOMER_POSITION", customer_position);
			paramMap.put("CUSTOMER_BIRTH", customer_birth);
			if(is_lunar_date == null){
				paramMap.put("IS_LUNAR_DATE", "N");
			}else{
				paramMap.put("IS_LUNAR_DATE", is_lunar_date);
			}
			if(is_leap_month == null){
				paramMap.put("IS_LEAP_MONTH", "N");
			}else{
				paramMap.put("IS_LEAP_MONTH", is_leap_month);
			}
			paramMap.put("CUSTOMER_AGE", customer_age);
			paramMap.put("CUSTOMER_BLOOD", customer_blood);
			paramMap.put("CUSTOMER_HEIGHT", customer_height);
			paramMap.put("CUSTOMER_WEIGHT", customer_weight);
			paramMap.put("CUSTOMER_CAR", customer_car);
			paramMap.put("CUSTOMER_PHONE", customer_phone);
			paramMap.put("CUSTOMER_EMAIL", customer_email);
			paramMap.put("CUSTOMER_HOME_TEL", customer_home_tel);
			paramMap.put("CUSTOMER_COMPANY_TEL", customer_company_tel);
			paramMap.put("CUSTOMER_HOME_ADDR", customer_home_addr);
			paramMap.put("CUSTOMER_HOMETOWN", customer_homeTown);
			paramMap.put("ISNET_IMPRESSION", isnet_impression);
			paramMap.put("FIRST_COMPANY_NAME", first_company_name);
			paramMap.put("SECOND_COMPANY_NAME", second_company_name);
			paramMap.put("THIRD_COMPANY_NAME", third_company_name);
			paramMap.put("GRADUATE_SCHOOL_NAME", graduate_school_name);
			paramMap.put("UNIVERSITY_NAME", university_name);
			paramMap.put("HIGH_SCHOOL_NAME", high_school_name);
			paramMap.put("MIDDLE_SCHOOL_NAME", middle_school_name);
			paramMap.put("PRIMARY_SCHOOL_NAME", primary_school_name);
			paramMap.put("MAJOR_NAME", major_name);
			paramMap.put("MARRIED_YN", married_yn);
			paramMap.put("WEDDING_DAY", wedding_day);
			paramMap.put("HOBBY", hobby);
			paramMap.put("HOBBY_GRADE", hobby_grade);
			paramMap.put("CUSTOMER_ETC", customer_etc);
			paramMap.put("IS_MODIFY", true);
			
			crmService.modifyCrmCustomerInfo(paramMap);
			
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [update_crm_02] customer_no["+customer_no+"] 고객 정보 수정 완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_crm_02] customer_no["+customer_no+"] 고객 정보 수정 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] end");
		}
		
		return mav;
	}
	
	// 고객사 정보를 변경합니다.
	@RequestMapping(value="/update_crm_03.isnet", method=RequestMethod.POST)
	public ModelAndView update_crm_03(@RequestParam(value="COMPANY_NO")int company_no,
									@RequestParam(value="COMPANY_NAME", required=false)String company_name,
									@RequestParam(value="COMPANY_TEL", required=false)String company_tel,
									@RequestParam(value="COMPANY_FAX", required=false)String company_fax,
									@RequestParam(value="COMPANY_ADDR", required=false)String company_addr,
									@RequestParam(value="IS_USED", required=false)String is_used){	
		logger.debug("["+getClass().getSimpleName()+"] [update_crm_03] start");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_03] company_no["+company_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_03] company_name["+company_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_03] company_tel["+company_tel+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_03] company_fax["+company_fax+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_03] company_addr["+company_addr+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_03] is_used["+is_used+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("COMPANY_NO", company_no);
			if(company_name != null){
				paramMap.put("COMPANY_NAME", company_name);
			}
			if(company_tel != null){
				paramMap.put("COMPANY_TEL", company_tel);
			}
			if(company_fax != null){
				paramMap.put("COMPANY_FAX", company_fax);
			}
			if(company_addr != null){
				paramMap.put("COMPANY_ADDR", company_addr);
			}
			if(is_used != null){
				paramMap.put("IS_USED", is_used);
			}
			
			crmService.modifyCrmCompanyInfo(paramMap);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [update_crm_03] company_no["+company_no+"] 고객사정보 수정 완료");
		}catch(Exception e){
			mav.addObject("isSuccess", false);
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_crm_03] ["+e.getMessage()+"]");
			logger.error("["+getClass().getSimpleName()+"] [update_crm_03] company_no["+company_no+"] 고객사 정보 수정 중 오류가 발생하였습니다.");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_crm_03] end");
		}
		
		return mav;
	}
	
	// 가족 정보를 변경합니다.
	@RequestMapping(value="/update_crm_04.isnet", method=RequestMethod.POST)
	public ModelAndView update_crm_04(@RequestParam("CUSTOMER_NO")int family_no,
										@RequestParam("CUSTOMER_NAME")String family_name,
										@RequestParam("CUSTOMER_GUBUN")String family_gubun,
										@RequestParam(value="CUSTOMER_JOB", required=false)String family_job,
										@RequestParam(value="CUSTOMER_AGE", required=false)String family_age,
										@RequestParam(value="CUSTOMER_BIRTH", required=false)String family_birth,
										@RequestParam(value="IS_LUNAR_DATE", required=false)String is_lunar_date,
										@RequestParam(value="IS_LEAP_MONTH", required=false)String is_leap_month){
		
		logger.debug("["+getClass().getSimpleName()+"] [update_crm_04] start");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_04] customer_no["+family_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_04] customer_name["+family_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_04] customer_gubun["+family_gubun+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_04] customer_job["+family_job+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_04] customer_age["+family_age+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_04] customer_birth["+family_birth+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_04] is_lunar_date["+is_lunar_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_crm_04] is_leap_month["+is_leap_month+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("CUSTOMER_NO", family_no);
			paramMap.put("CUSTOMER_NAME", family_name);
			paramMap.put("CUSTOMER_GUBUN", family_gubun);
			paramMap.put("CUSTOMER_JOB", family_job);
			paramMap.put("CUSTOMER_AGE", family_age);
			paramMap.put("CUSTOMER_BIRTH", family_birth);
			if(is_lunar_date == null){
				paramMap.put("IS_LUNAR_DATE", "N");
			}else{
				paramMap.put("IS_LUNAR_DATE", is_lunar_date);
			}
			if(is_leap_month == null){
				paramMap.put("IS_LEAP_MONTH", "N");
			}else{
				paramMap.put("IS_LEAP_MONTH", is_leap_month);
			}
			paramMap.put("IS_MODIFY", true);
			crmService.modifyCrmFamilyInfo(paramMap);
			mav.addObject("isSuccess", true);
			
			logger.info("["+getClass().getSimpleName()+"] family_no["+family_no+"] 가족정보 변경 완료");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_crm_04] family_no["+family_no+"] 고객 가족정보 변경 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_crm_04] end");
		}
		return mav;
		
	}
	
	
	// 고객 방문이력 삭제
	@RequestMapping(value="/del_crm_01.isnet", method=RequestMethod.GET)
	public ModelAndView del_crm_01(@RequestParam("SHEET_NO")int sheet_no){
		logger.debug("["+getClass().getSimpleName()+"] [del_crm_01] start");
		logger.info("["+getClass().getSimpleName()+"] [del_crm_01] sheet_no["+sheet_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("SHEET_NO", sheet_no);
			paramMap.put("IS_USED", "N");
			
			crmService.deleteCrmManagementInfo(paramMap);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [del_crm_01] sheet_no["+sheet_no+"] 고객관리이력 삭제");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [del_crm_01] sheet_no["+sheet_no+"] 관리이력삭제 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [del_crm_01] emd]");
		}
		return mav;
	}
	
	// 고객 정보 삭제
	@RequestMapping(value="/del_crm_02.isnet", method=RequestMethod.GET)
	public ModelAndView del_crm_02(@RequestParam("CUSTOMER_NO")int customer_no){
		logger.debug("["+getClass().getSimpleName()+"] [del_crm_02] start");
		logger.info("["+getClass().getSimpleName()+"] [del_crm_02] customer_no["+customer_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("CUSTOMER_NO", customer_no);
			paramMap.put("IS_USED", "N");
			paramMap.put("IS_MODIFY", false);
			
			crmService.modifyCrmCustomerInfo(paramMap);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [del_crm2] customer_no["+customer_no+"] 고객 정보 삭제 완료");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [del_crm_02] 고객 정보 삭제 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [del_crm_02] end");
		}
		return mav;
	}
	
	// 가족정보 삭제
	@RequestMapping(value="/del_crm_03.isnet", method=RequestMethod.GET)
	public ModelAndView del_crm_03(@RequestParam("CUSTOMER_NO")int family_no){
		logger.debug("["+getClass().getSimpleName()+"] [del_crm_03] start");
		logger.info("["+getClass().getSimpleName()+"] [del_crm_03] customer_no["+family_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("CUSTOMER_NO", family_no);
			paramMap.put("IS_USED", "N");
			paramMap.put("IS_MODIFY", false);
			
			crmService.modifyCrmFamilyInfo(paramMap);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [del_crm_03] family_no["+family_no+"] 가족 정보 삭제완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [del_crm_03] familiy_no["+family_no+"] 가족정보 삭제 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [del_crm_03] end");
		}
		return mav;
	}
	
	private Date getDate(String year, String month, String day, String hour, String minute){
		
		Calendar start = Calendar.getInstance();
		
		start.set(Calendar.YEAR, StringUtil.nullToZero(year));
		start.set(Calendar.MONTH, StringUtil.nullToZero(month));
		start.set(Calendar.DAY_OF_MONTH, StringUtil.nullToZero(day));
		start.set(Calendar.HOUR_OF_DAY, StringUtil.nullToZero(hour));
		start.set(Calendar.MINUTE, StringUtil.nullToZero(minute));
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		
		return start.getTime();
	}
}
