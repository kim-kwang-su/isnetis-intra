package com.isnet.mgr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ibm.icu.util.ChineseCalendar;
import com.isnet.mgr.common.DateUtil;
import com.isnet.mgr.common.StringUtil;
import com.isnet.mgr.dao.CRMDao;

@Service
public class CRMServiceImp implements CRMService{
	
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private CRMDao crmDao;

	@Override
	public void insertCrmCompany(Map<String, Object> paramMap) {
		crmDao.insert_crm_01(paramMap);
	}

	@Override
	public void insertCrmCustomer(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [insertCrmCustomer] start");
		
		paramMap.put("CUSTOMER_GUBUN", "본인");
		crmDao.insert_crm_02_01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [insertCrmCustomer] 고객번호["+paramMap.get("CUSTOMER_NO")+"] 생성");
		logger.info("["+getClass().getSimpleName()+"] [insertCrmCustomer] customer_no["+paramMap.get("CUSTOMER_NO")+"] 고객 기본 정보 저장 완료");

		crmDao.insert_crm_02_02(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [insertCrmCustomer] customer_no["+paramMap.get("CUSTOMER_NO")+"] 고객 부가 정보 저장 완료");
		
		if(! String.valueOf(paramMap.get("CUSTOMER_BIRTH")).isEmpty()){
			paramMap.put("MEMORIAL_DATE", paramMap.get("CUSTOMER_BIRTH"));
			paramMap.put("MEMORIAL_DATE_GUBUN", "B");
			crmDao.insert_crm_02_03(paramMap);
			logger.info("["+getClass().getSimpleName()+"] [insertCrmCustomer] customer_no["+paramMap.get("CUSTOMER_NO")+"] 고객 생일 정보 저장 완료");
		}
		if(! String.valueOf(paramMap.get("WEDDING_DAY")).isEmpty() ){
			paramMap.put("MEMORIAL_DATE", paramMap.get("WEDDING_DAY"));
			paramMap.put("MEMORIAL_DATE_GUBUN", "W");
			paramMap.put("IS_LUNAR_DATE", "N");
			paramMap.put("IS_LEAP_MONTH", "N");
			crmDao.insert_crm_02_03(paramMap);
			logger.info("["+getClass().getSimpleName()+"] [insertCrmCustomer] customer_no["+paramMap.get("CUSTOMER_NO")+"] 고객 결혼기념일 정보 저장 완료");
		}
		
		logger.debug("["+getClass().getSimpleName()+"] [insertCrmCustomer] end");
	}

	@Override
	public void insertCrmSheet(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [insertCrmSheet] start");
		// 방문처리 결과를 저장합니다.
		crmDao.insert_crm_03(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [insertCrmSheet] sheet_no["+(Integer)paramMap.get("SHEET_NO")+"] 신규 고객관리이력 등록완료");
		
		// 방문자 정보를 저장합니다.
		int[] employee_noes = (int[])paramMap.get("EMPLOYEE_NOES");
		for(int employee_no : employee_noes){
			paramMap.put("EMPLOYEE_NO", employee_no);
			crmDao.insert_crm_04(paramMap);
		}
		logger.info("["+getClass().getSimpleName()+"] [insertCrmSheet] sheet_no["+(Integer)paramMap.get("SHEET_NO")+"] 신규 고객관리이력 방문자 등록완료");
		
		logger.debug("["+getClass().getSimpleName()+"] [insertCrmSheet] end");
		
	}

	@Override
	public void insertCrmVisitedEmployee(Map<String, Object> paramMap) {
		
	}

	@Override
	public void insertCrmCustomerFamily(Map<String, Object> paramMap) {		
		// 가족 정보를 저장합니다.
		crmDao.insert_crm_02_01(paramMap);
		// 가족의 생일정보를 저장합니다.
		if(! String.valueOf(paramMap.get("CUSTOMER_BIRTH")).isEmpty()){
			paramMap.put("MEMORIAL_DATE", paramMap.get("CUSTOMER_BIRTH"));
			paramMap.put("MEMORIAL_DATE_GUBUN", "B");
			crmDao.insert_crm_02_03(paramMap);
			logger.info("["+getClass().getSimpleName()+"] [insertCrmCustomer] customer_no["+paramMap.get("CUSTOMER_NO")+"] 고객 생일 정보 저장 완료");
		}
	}

	@Override
	public List<Map<String, Object>> getCrmCompanyList() {
		return crmDao.select_crm_01();
	}

	@Override
	public Map<String, Object> getCrmCustomerList(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [getCrmCustomerList] start");
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		paramMap.put("BEGIN_INDEX", begin_index);
		
		int total_rows = crmDao.select_crm_02(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getCrmCustomerList] total_rows["+total_rows+"]");
		
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getCrmCustomerList] total_pages["+total_pages+"]");
		
		List<Map<String, Object>> customerList = crmDao.select_crm_03(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getCrmCustomerList] crmCustomerList.size["+customerList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getCrmCustomerList] rows["+customerList+"]");
		
		int records = customerList.size();
		logger.info("["+getClass().getSimpleName()+"] [getCrmCustomerList] records["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", customerList);
		resultData.put("total", total_pages);

		logger.debug("["+getClass().getSimpleName()+"] [getRecentIssueList] end");
		
		return resultData;
	}

	@Override
	public List<Map<String, Object>> getCustomeListByCompanyNo(Map<String, Object> paramMap) {
		return crmDao.select_crm_04(paramMap);
	}

	@Override
	public Map<String, Object> getCrmManagementList(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [getCrmManagementList] start");
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		paramMap.put("BEGIN_INDEX", begin_index);
		
		int total_rows = crmDao.select_crm_05(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getCrmManagementList] total_rows["+total_rows+"]");
		
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getCrmManagementList] total_pages["+total_pages+"]");
		
		List<Map<String, Object>> crmSheetList = crmDao.select_crm_06(paramMap);
		for(Map<String, Object> sheet : crmSheetList){
			int sheet_no = (Integer)sheet.get("SHEET_NO");
			List<String> employeeList = crmDao.select_crm_07(sheet_no);
			sheet.put("EMPLOYEE_NAME", StringUtils.collectionToCommaDelimitedString(employeeList));
		}
		logger.info("["+getClass().getSimpleName()+"] [getCrmManagementList] crmSheetList.size["+crmSheetList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getCrmManagementList] rows["+crmSheetList+"]");
		
		int records = crmSheetList.size();
		logger.info("["+getClass().getSimpleName()+"] [getCrmManagementList] records["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", crmSheetList);
		resultData.put("total", total_pages);

		logger.debug("["+getClass().getSimpleName()+"] [getCrmManagementList] end");
		
		return resultData;
	}

	@Override
	public Map<String, Object> getCrmCustomerInfo(Map<String, Object> paramMap) {

		Map<String, Object> resultData = new HashMap<String, Object>();
		
		Map<String, Object> customerBasicInfo = crmDao.select_crm_10_01(paramMap);
		Map<String, Object> customerAdditionInfo = crmDao.select_crm_10_02(paramMap);
		List<Map<String, Object>> customerMemorialDay = crmDao.select_crm_10_03(paramMap);
		
		resultData.putAll(customerBasicInfo);
		resultData.putAll(customerAdditionInfo);
		
		for(Map<String, Object> memorialDay : customerMemorialDay){
			String memorial_date_gubun = String.valueOf(memorialDay.get("MEMORIAL_DATE_GUBUN"));
			if("B".equals(memorial_date_gubun)){
				resultData.put("CUSTOMER_BIRTH", memorialDay.get("MEMORIAL_DATE"));
				resultData.put("IS_LUNAR_DATE", memorialDay.get("IS_LUNAR_DATE"));
				resultData.put("IS_LEAP_MONTH", memorialDay.get("IS_LEAP_MONTH"));
			}else{
				resultData.put("WEDDING_DAY", memorialDay.get("MEMORIAL_DATE"));
			}			
		}
		
		return resultData;
	}

	@Override
	public List<Map<String, Object>> getCrmFamilyList(Map<String, Object> paramMap) {
		List<Map<String, Object>> familyList = crmDao.select_crm_11(paramMap);
		for(Map<String, Object> family : familyList){
			
			List<Map<String, Object>> customerMemorialDay = crmDao.select_crm_10_03(family);
			for(Map<String, Object> memorialDay : customerMemorialDay){				
				family.put("CUSTOMER_BIRTH", memorialDay.get("MEMORIAL_DATE"));
				family.put("IS_LUNAR_DATE", memorialDay.get("IS_LUNAR_DATE"));
				family.put("IS_LEAP_MONTH", memorialDay.get("IS_LEAP_MONTH"));
			}
		}
		return familyList;
	}

	@Override
	public void modifyCrmManagementInfo(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [modifyCrmManagementInfo] start");
		// 고객 방문이력 정보를 변경합니다.
		crmDao.update_crm_01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [modifyCrmManagementInfo] sheet_no["+(Integer)paramMap.get("SHEET_NO")+"] 신규 고객관리이력 수정완료");
		// 기존 방문자 정보를 삭제합니다.
		crmDao.delete_crm_01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [modifyCrmManagementInfo] sheet_no["+(Integer)paramMap.get("SHEET_NO")+"] 기존 방문자 정보 삭제 완료");
		// 방문자 정보를 추가합니다.
		int[] employee_noes = (int[])paramMap.get("EMPLOYEE_NOES");
		for(int employee_no : employee_noes){
			paramMap.put("EMPLOYEE_NO", employee_no);
			crmDao.insert_crm_04(paramMap);
		}
		logger.info("["+getClass().getSimpleName()+"] [modifyCrmManagementInfo] sheet_no["+(Integer)paramMap.get("SHEET_NO")+"] 신규 방문자정보 추가 완료");
		logger.debug("["+getClass().getSimpleName()+"] [modifyCrmManagementInfo] end");
		
	}

	@Override
	public void modifyCrmCustomerInfo(Map<String, Object> paramMap) {
		// 고객 기본 정보 수정
		crmDao.update_crm_02_01(paramMap);
		
		if((Boolean)paramMap.get("IS_MODIFY")){
			// 고객 부가 정보 수정
			crmDao.update_crm_02_02(paramMap);
			// 고객 기념일 정보 삭제
			crmDao.delete_crm_03(paramMap);
			
			// 고객 기념일 정보 새로 추가
			if(! String.valueOf(paramMap.get("CUSTOMER_BIRTH")).isEmpty()){
				paramMap.put("MEMORIAL_DATE", paramMap.get("CUSTOMER_BIRTH"));
				paramMap.put("MEMORIAL_DATE_GUBUN", "B");
				crmDao.insert_crm_02_03(paramMap);
				logger.info("["+getClass().getSimpleName()+"] [insertCrmCustomer] customer_no["+paramMap.get("CUSTOMER_NO")+"] 고객 생일 정보 저장 완료");
			}
			if(! String.valueOf(paramMap.get("WEDDING_DAY")).isEmpty() ){
				paramMap.put("MEMORIAL_DATE", paramMap.get("WEDDING_DAY"));
				paramMap.put("MEMORIAL_DATE_GUBUN", "W");
				paramMap.put("IS_LUNAR_DATE", "N");
				paramMap.put("IS_LEAP_MONTH", "N");
				crmDao.insert_crm_02_03(paramMap);
				logger.info("["+getClass().getSimpleName()+"] [insertCrmCustomer] customer_no["+paramMap.get("CUSTOMER_NO")+"] 고객 결혼기념일 정보 저장 완료");
			}
			
		}
	}

	@Override
	public void modifyCrmCompanyInfo(Map<String, Object> paramMap) {
		crmDao.update_crm_03(paramMap);
	}

	@Override
	public void modifyCrmFamilyInfo(Map<String, Object> paramMap) {
		// 가족 정보 수정
		crmDao.update_crm_02_01(paramMap);
		
		// 고객의 기념일 정보를 변경합니다.
		if((Boolean)paramMap.get("IS_MODIFY")){
			if(! String.valueOf(paramMap.get("CUSTOMER_BIRTH")).isEmpty()){
				paramMap.put("MEMORIAL_DATE", paramMap.get("CUSTOMER_BIRTH"));
				paramMap.put("MEMORIAL_DATE_GUBUN", "B");
				crmDao.insert_crm_02_03(paramMap);
				logger.info("["+getClass().getSimpleName()+"] [insertCrmCustomer] customer_no["+paramMap.get("CUSTOMER_NO")+"] 고객 생일 정보 저장 완료");
			}
		}
	}

	@Override
	public void deleteCrmManagementInfo(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [deleteCrmManagementInfo] start");
		
		crmDao.update_crm_01(paramMap);
		
		logger.debug("["+getClass().getSimpleName()+"] [deleteCrmManagementInfo] start");
	}

	@Override
	public Map<String, Object> getCrmCompanyList(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [getCrmCompanyList] start");
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		paramMap.put("BEGIN_INDEX", begin_index);
		
		int total_rows = crmDao.select_crm_08(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getCrmCompanyList] total_rows["+total_rows+"]");
		
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getCrmCompanyList] total_pages["+total_pages+"]");
		
		List<Map<String, Object>> crmCompanyList = crmDao.select_crm_09(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getCrmCompanyList] crmCompanyList.size["+crmCompanyList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getCrmCompanyList] rows["+crmCompanyList+"]");
		
		int records = crmCompanyList.size();
		logger.info("["+getClass().getSimpleName()+"] [getCrmCompanyList] records["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", crmCompanyList);
		resultData.put("total", total_pages);

		logger.debug("["+getClass().getSimpleName()+"] [getCrmCompanyList] end");
		
		return resultData;
	}

	@Override
	public List<Map<String, Object>> getMemorialDayList(Map<String, Object> paramMap) {
		
		List<Map<String, Object>> memorialList = new ArrayList<Map<String, Object>>();
		int month = (Integer)paramMap.get("MONTH");

		paramMap.put("IS_LUNAR_DATE", "N");
		paramMap.put("MONTH", StringUtil.lpad(String.valueOf(month), 2, "0"));
		logger.info("["+getClass().getSimpleName()+"] [getMemorialDayList] paramMap["+paramMap+"]");
		memorialList.addAll(crmDao.select_crm_12(paramMap));
		
		paramMap.put("IS_LUNAR_DATE", "Y");
		paramMap.put("FIRST_MONTH", ( month + 12 )/12);
		paramMap.put("SECOND_MONTH", month-1);
		paramMap.put("THIRD_MONTH", month-2);
		logger.info("["+getClass().getSimpleName()+"] [getMemorialDayList] paramMap["+paramMap+"]");
		memorialList.addAll(crmDao.select_crm_12(paramMap));
		
		return memorialList;
	}
	
	
}
