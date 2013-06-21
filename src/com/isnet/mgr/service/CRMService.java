package com.isnet.mgr.service;

import java.util.List;
import java.util.Map;

public interface CRMService {

	/**
	 * 고객사 정보를 저장합니다.
	 * @param paramMap
	 */
	void insertCrmCompany(Map<String, Object> paramMap);
	
	/**
	 * 고객정보를 저장합니다.
	 * @param paramMap
	 */
	void insertCrmCustomer(Map<String, Object> paramMap);
	
	/**
	 * 고객사 방문이력을 저장합니다.
	 * @param paramMap
	 */
	void insertCrmSheet(Map<String, Object> paramMap);
	
	/**
	 * 고객사 방문자 정보를 저장합니다.
	 * @param paramMap
	 */
	void insertCrmVisitedEmployee(Map<String, Object> paramMap);
	
	/**
	 * 고객의 가족정보를 저장합니다.
	 * @param paramMap
	 */
	void insertCrmCustomerFamily(Map<String, Object> paramMap);
	
	/**
	 * 고객사 전체 정보를 조회합니다.
	 * @return
	 */
	List<Map<String, Object>> getCrmCompanyList();
	
	/**
	 * 검색 조건에 해당하는 고객리스트를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getCrmCustomerList(Map<String, Object> paramMap);
	
	/**
	 * 고객사별 담당자를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getCustomeListByCompanyNo(Map<String, Object> paramMap);
	
	/**
	 * 검색 조건에 해당하는 고객관리이력을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getCrmManagementList(Map<String, Object> paramMap);
	
	/**
	 * 고객번호에 해당하는 고객정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getCrmCustomerInfo(Map<String, Object> paramMap);
	
	/**
	 * 고객번호에 해당하는 가족정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getCrmFamilyList(Map<String, Object> paramMap);
	
	/**
	 * 고객관리이력 정보를 변경합니다.
	 * @param paramMap
	 */
	void modifyCrmManagementInfo(Map<String, Object> paramMap);
	
	/**
	 * 고객 정보를 변경합니다.
	 * @param paramMap
	 */
	void modifyCrmCustomerInfo(Map<String, Object> paramMap);
	
	/**
	 * 고객사 정보를 변경합니다.
	 * @param paramMap
	 */
	void modifyCrmCompanyInfo(Map<String, Object> paramMap);
	
	/**
	 * 고객가족 정보를 변경합니다.
	 * @param paramMap
	 */
	void modifyCrmFamilyInfo(Map<String, Object> paramMap);
	
	/**
	 * 고객관리이력 정보를 삭제합니다.
	 * @param paramMap
	 */
	void deleteCrmManagementInfo(Map<String, Object> paramMap);
	
	/**
	 * 검색 조건에 해당하는 고객사 정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getCrmCompanyList(Map<String, Object> paramMap);

	/**
	 * 고객의 기념일을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getMemorialDayList(Map<String, Object> paramMap);

	
}
