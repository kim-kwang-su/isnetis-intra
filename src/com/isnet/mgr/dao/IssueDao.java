package com.isnet.mgr.dao;

import java.util.List;
import java.util.Map;

public interface IssueDao {
	
	/**
	 * TB_ISSUE 테이블의 상태정보를 조회합니다.
	 * @return HashMap AUTO_INCREMENT값을 조회하면 INDEX값을 얻을 수 있다.
	 */
	Map<String, Object> select_issue_00();

	/**
	 * 조건에 만족하는 전체 행의 갯수를 반환합니다.
	 * @param _SEARCH, SEARCH_OPER, SEARCH_FIELD, SEARCH_STRING
	 * @return INTEGER
	 */
	int select_issue_01(Map<String, Object> paramMap);
	
	/**
	 * 최근 접수된 이슈내역을 반환합니다.
	 * @param SIDX, SORD, BEGIN_INDEX, ROWS, _SEARCH, SEARCH_OPER, SEARCH_FIELD, SEARCH_STRING
	 * @return TB_ISSUE에서 조건을 만족하는 이슈내역을 반환합니다.
	 */
	List<Map<String, Object>> select_issue_02(Map<String, Object> paramMap);
	
	/**
	 * ISSUE_NO에 해당하는 이슈 상세정보를 반환합니다.
	 * @param ISSUE_NO
	 * @return TB_ISSUE에서 ISSUE_NO에 해당하는 ISSUE의 상세정보를 반환합니다.
	 */
	Map<String, Object> select_issue_03(Map<String, Object> paramMap);
	
	/**
	 * ISSUE_NO의 이슈 담당개발자 목록을 반환합니다.
	 * @param ISSUE_NO, IS_SE
	 * @return TB_DEVELOPER에서 ISSUE_NO에 해당하는 담당 개발자의 목록을 반환합니다.
	 */
	List<Map<String, Object>> select_issue_04(Map<String, Object> paramMap);
	
	/**
	 * ISSUE_NO에 해당하는 진행상황 정보를 반환합니다.
	 * @param ISSUE_NO
	 * @return TB_ISSUE_HISTORY에서 ISSUE_NO에 해당하는 ISSUE의 진행상황정보를 반환합니다.
	 */
	List<Map<String, Object>> select_issue_05(Map<String, Object> paramMap);
	
	/**
	 * 이슈 진행상태 코드를 반환합니다.
	 * @return TB_ISSUE_STATE의 STATE_NO, STATE_NAME 정보를 반환합니다.
	 */
	List<Map<String, Object>> select_issue_06();
	
	/**
	 * 이슈 종류 코드를 반환합니다.
	 * @return TB_ISSUE_KIND의 KIND_NO, KIND_NAME 정보를 반환합니다.
	 */
	List<Map<String, Object>> select_issue_07();
	
	/**
	 * 이슈 첨부파일 정보를 반환합니다.
	 * @param ISSUE_NO 혹은 FILE_NO
	 * @return TB_ISSUE_FILE의 FILE_NO, FILE_NAME 정보를 반환합니다.
	 */
	List<Map<String, Object>> select_issue_08(Map<String, Object> paramMap);
	
	/**
	 * 진행상황 전체 행의 갯수를 반환합니다.
	 * @param _SEARCH, SEARCH_OPER, SEARCH_FIELD, SEARCH_STRING
	 * @return INTEGER
	 */
	int select_issue_09(Map<String, Object> paramMap);
	
	/**
	 * 진행상황 이슈내역을 반환합니다.
	 * @param SIDX, SORD, BEGIN_INDEX, ROWS, _SEARCH, SEARCH_OPER, SEARCH_FIELD, SEARCH_STRING
	 * @return TB_ISSUE에서 조건을 만족하는 이슈내역을 반환합니다.
	 */
	List<Map<String, Object>> select_issue_10(Map<String, Object> paramMap);
	/**
	 * QA 테스트 요청 건수를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	int select_issue_11(Map<String, Object> paramMap);
	/**
	 * QA 테스트 요청 목록을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_issue_12(Map<String, Object> paramMap);
	/**
	 * QA 테스트 요청을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> select_issue_13(Map<String, Object> paramMap);
	
	/**
	 * QA 버그리포트 정보를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> select_issue_14(Map<String, Object> paramMap);
	
	/**
	 * 이슈 정보를 저장합니다.
	 * @param paramMap ISSUE_NO, ACCEPT_DATE, CUSTOMER_NO, EMPLOYEE_NO, PRODUCT_NO, ISSUE_KIND, ISSUE_MEMO, ISSUE_PRIORITY, STATE_NO, EXPECTED_COMPLETE_DATE, ISSUE_REMARK
	 */
	void insert_issue_00(Map<String, Object> paramMap);
	
	/**
	 * 이슈별 담당개발자를 TB_DEVELOPER에 등록합니다.
	 * @param paramMap ISSUE_NO, EMPLOYEE_NO(복수개 가능)
	 */
	void insert_issue_01(Map<String, Object> paramMap);
	
	/**
	 * 이슈별 진행상황 정보를 TB_ISSUE_HISTORY에 저장합니다.
	 * @param paramMap PROCESS_MEMO, REGISTER_DATE, EMPLOYEE_NO, ISSUE_NO
	 */
	void insert_issue_02(Map<String, Object> paramMap);
	
	/**
	 * 이슈별 첨부파일을 저장합니다.
	 * @param paramMap FILE_NAME, ISSUE_NO
	 */
	void insert_issue_03(Map<String, Object> paramMap);
	
	/**
	 * QA 테스트요청을 TB_QA_REQUEST에 등록합니다.
	 * @param paramMap 
	 */
	void insert_issue_04(Map<String, Object> paramMap);
	
	/**
	 * 버그리포트를 	TB_QA_BUG_REPORT에 등록합니다.
	 * @param paramMap 
	 */
	void insert_issue_05(Map<String, Object> paramMap);
	
	/**
	 * 지정된 이슈의 담당개발자를 TB_DEVELOPER에서 삭제합니다.
	 * @param paramMap ISSUE_NO
	 */
	void delete_issue_01(Map<String, Object> paramMap);
	
	/**
	 * 지정된 이슈의 진행상태를 변경합니다.
	 * @param paramMap ISSUE_NO, STATE_NO
	 */
	void update_issue_01(Map<String, Object> paramMap);
	
	/**
	 * QA 테스트 결과를 반영합니다.
	 * @param paramMap 
	 */
	void update_issue_02(Map<String, Object> paramMap);
}
