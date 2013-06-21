package com.isnet.mgr.dao;

import java.util.List;
import java.util.Map;

public interface CRMDao {
	/**
	 * CRM_COMPANY에 고객사 정보를 저장합니다.
	 * @param paramMap
	 */
	void insert_crm_01(Map<String, Object> paramMap);
	/**
	 * CRM_PERSON에 고객 정보를 저장합니다.
	 * @param paramMap
	 */
	void insert_crm_02_01(Map<String, Object> paramMap);
	/**
	 * CRM_PERSON_ADDITION에 고객 부가정보를 저장합니다.
	 * @param paramMap
	 */
	void insert_crm_02_02(Map<String, Object> paramMap);
	/**
	 * CRM_CALENDAR에 고객 기념일정보를 저장합니다.
	 * @param paramMap
	 */
	void insert_crm_02_03(Map<String, Object> paramMap);
	
	/**
	 * CRM_VISITED_SHEET에 고객사 방문처리 결과를 저장합니다.
	 * @param paramMap
	 */
	void insert_crm_03(Map<String, Object> paramMap);
	/**
	 * CRM_VISITED_EMPLOYEE에 고객사 방문자정보를 저장합니다.
	 * @param paramMap
	 */
	void insert_crm_04(Map<String, Object> paramMap);
	/**
	 * 고객 목록을 조회합니다.
	 * @return
	 */
	List<Map<String, Object>> select_crm_01();
	/**
	 * CRM_CUSTOMER에서 전체 고객 건수를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	int select_crm_02(Map<String, Object> paramMap);
	/**
	 * CRM_CUSTOMER에서 조회조건을 만족하는 고객 리스트 정보를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_crm_03(Map<String, Object> paramMap);
	/**
	 * CRM_CUSTOMER에서 고객사별 담당자를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_crm_04(Map<String, Object> paramMap);
	/**
	 * CRM_VISITED_SHEET에서 검색조건을 만족하는 고객 관리이력 건수를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	int select_crm_05(Map<String, Object> paramMap);
	/**
	 * CRM_VISITED_SHEET에서 검색조건을 만족하는 고객 관리이력정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_crm_06(Map<String, Object> paramMap);
	/**
	 * CRM_VISITED_EMPLOYEE에서 방문자명을 조회합니다.
	 * @param sheet_no
	 * @return
	 */
	List<String> select_crm_07(int sheet_no);
	
	/**
	 * CRM_COMPANY에서 고객사 건수를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	int select_crm_08(Map<String, Object> paramMap);
	/**
	 * CRM_COMPANY에서 검색조건을 만족하는 고객사 리스틀 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_crm_09(Map<String, Object> paramMap);
	/**
	 * CRM_PERSON에서 고객 기본정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> select_crm_10_01(Map<String, Object> paramMap);
	/**
	 * CRM_PERSON_ADDITION에서 고객 부가정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> select_crm_10_02(Map<String, Object> paramMap);
	/**
	 * CRM_CALENDAR에서 고객 기념일 정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_crm_10_03(Map<String, Object> paramMap);
	/**
	 * CRM_PERSON에서 가족정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_crm_11(Map<String, Object> paramMap);
	/**
	 * CRM_CALENDAR에서 기념일정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_crm_12(Map<String, Object> paramMap);
	/**
	 * CRM_VISITED_EMPLOYEE에서 해당 방문이력의 방문자 정보를 삭제합니다.
	 * @param paramMap
	 */
	void delete_crm_01(Map<String, Object> paramMap);
	
	/**
	 * CRM_CALENDAR의 고객 기념일 정보를 삭제합니다.
	 * @param paramMap
	 */
	void delete_crm_03(Map<String, Object> paramMap);
	/**
	 * CRM_VISITED_SHEET의 고객관리이력 정보를 변경한다.
	 * @param paramMap
	 */
	void update_crm_01(Map<String, Object> paramMap);
	/**
	 * CRM_PERSON의 고객 기본 정보를 변경합니다.
	 * @param paramMap
	 */
	void update_crm_02_01(Map<String, Object> paramMap);
	/**
	 * CRM_PERSION_ADDITION의 고객 부가 정보를 변경합니다.
	 * @param paramMap
	 */
	void update_crm_02_02(Map<String, Object> paramMap);
	/**
	 * CRM_COMPANY의 고객사 정보를 변경합니다.
	 * @param paramMap
	 */
	void update_crm_03(Map<String, Object> paramMap);
}
