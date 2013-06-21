package com.isnet.mgr.dao;

import java.util.List;
import java.util.Map;

public interface ModuleDao {

	/**
	 * TB_MODULE에 모듈정보 저장
	 * @param  MODULE_NAME, PRODUCT_NO, LOCATION_NO, OS_TYPE, MODULE_STATE('신규','변경','폐기')
	 * @return MODULE_NO
	 */
	void insert_module_01(Map<String, Object> paramMap);
	
	/**
	 * TB_MODULE에 모듈이력정보 저장
	 * @param  FILE_VERSION, MODULE_MODE, SAVE_PATH, UPDATE_DATE, MODULE_NO, EMPLOYEE_NO, HASH_VALUE, UPDATE_MEMO
	 * @return  MODULE_HISTORY_NO
	 */
	void insert_module_02(Map<String, Object> paramMap);
	
	/**
	 * TB_MODULE_HISTORY의 전체 ROW 개수 반환
	 * @param _SEARCH, SEARCH_OPER, SEARCH_FIELD, SEARCH_STRING
	 * @return TB_MODULE_HISTORY의 전체 ROW 갯수
	 */
	int select_module_01(Map<String, Object> paramMap);
	
	/**
	 * TB_MODULE_HISTORY에서 모듈변경내역을 조회합니다.
	 * @param SIDX, SORD, BEGIN_INDEX, ROWS, _SEARCH, SEARCH_OPER, SEARCH_FIELD, SEARCH_STRING
	 * @return TB_MODULE_HISTORY의 변경이력
	 */
	List<Map<String, Object>> select_module_02(Map<String, Object> paramMap);
	
	/**
	 * MODULE_NO에 해당하는 모듈의 상세정보를 조회합니다.
	 * @param  MODULE_HISTORY_NO
	 * @return TB_MODULE, TB_MODULE_HISTORY에서 MODULE_HISTORY_NO의 상세정보를 반환한다.
	 */
	Map<String, Object> select_module_03(Map<String, Object> paramMap);
	
	/**
	 * MODULE_HISTORY_NO에 해당하는 모듈의 적용 고객정보를 반환합니다.
	 * @param  MODULE_HISTORY_NO
	 * @return TB_CUSTOMER의 고객 정보를 반환합니다.
	 */
	List<Map<String, Object>> select_module_04(Map<String, Object> paramMap);
	
	/**
	 * MODULE_NO에 해당하는 모듈의 수정이력을 반환합니다.
	 * @param  MODULE_NO
	 * @return TB_MODULE, TB_MODULE_HISTORY에서 MODULE_NO에 해당하는 모듈의 변경 이력을 반환합니다.
	 */
	List<Map<String, Object>> select_module_05(Map<String, Object> paramMap);
	
	/**
	 * MODULE_HISTORY_NO에 해당하는 태그정보를 반환합니다.
	 * @param  MODULE_HISTORY_NO
	 * @return TB_TAG의 태그 정보를 반환합니다.
	 */
	List<Map<String, Object>> select_module_06(Map<String, Object> paramMap);
	
	/**
	 * MODULE_HISTORY_NO에 해당하는 파일정보를 반환합니다.
	 * @param  MODULE_HISTORY_NO
	 * @return TB_MODULE_HISTORY의 파일저장정보를 반환합니다.
	 */
	Map<String, Object> select_module_07(Map<String, Object> paramMap);
	
	/**
	 * TB_FILE_PATH의 설치경로 정보를 반환합니다.
	 * @return TB_FILE_PATH의 설치폴더 정보를 반환합니다.
	 */
	List<Map<String, Object>> select_module_08();
	
	/**
	 * 제품별 모듈의 최종버전 정보를 반환합니다.
	 * @param PRODUCT_NO, LOCATION_NO
	 * @return TB_MODULE와 TB_MODULE_HISTORY의 MODULE_NO, MODULE_HISTORY_NO, MODULE_NAME, FILE_VERSION, SAVE_PATH, UPDATE_MEMO, UPDATE_DATE
	 */
	List<Map<String, Object>> select_module_09(Map<String, Object> paramMap);
	
	/**
	 * TB_MODULE의 전체 ROW 개수 반환
	 * @param PRODUCT_NO, LOCATION_NO
	 * @return TB_MODULE의 전체 ROW 갯수
	 */
	int select_module_10(Map<String, Object> paramMap);
	
	
	/**
	 * 고객사별 모듈의 최종버전 정보를 반환합니다.
	 * @param CUSTOMER_NO, PRODUCT_NO, LOCATION_NO
	 * @return TB_CUSTOMER_APPLY_MODULES와 TB_MODULE_HISTORY의 MODULE_NO, MODULE_HISTORY_NO, MODULE_NAME, FILE_VERSION, SAVE_PATH, UPDATE_MEMO, UPDATE_DATE
	 */
	List<Map<String, Object>> select_module_11(Map<String, Object> paramMap);
	
	/**
	 * TB_MODULE의 전체 ROW 개수 반환
	 * @param CUSTOMER_NO, PRODUCT_NO, LOCATION_NO
	 * @return TB_CUSTOMER_APPLY_MODULES의 전체 ROW 갯수
	 */
	int select_module_12(Map<String, Object> paramMap);
	
	/**
	 * TB_CUSTOMER_APPLY_MODULES 조회
	 * @param MODULE_HISTORY_NO
	 * @return 
	 */
	List<Map<String, Object>> select_module_13(Map<String, Object> paramMap);
	
	/**
	 * TB_MODULE에서 PRODUCT_NO와 MODULE_NAME가 일치하는 모듈의 갯수 반환
	 * @param MODULE_HISTORY_NO
	 * @return 
	 */
	int select_module_14(Map<String, Object> paramMap);
	/**
	 * TB_MODULE에서 PRODUCT_NO와 MODULE_NAME가 일치하는 모듈정보 반환
	 * @param MODULE_HISTORY_NO
	 * @return 
	 */
	List<Map<String, Object>> select_module_15(Map<String, Object> paramMap);
	
	/**
	 * TB_MODULE의 MODULE_HISTORY_NO 변경
	 * @param  MODULE_NO, MODULE_HISTORY_NO
	 * @return TB_MODULE이 UPDATE 건수
	 */
	int update_module_01(Map<String, Object> paramMap);
	/**
	 * TB_MODULE의 PRODUCT_NO, MODULE_NAME, LOCATION_NO 변경
	 * @param  MODULE_NO
	 * @return TB_MODULE이 UPDATE 건수
	 */
	int update_module_02(Map<String, Object> paramMap);

	/**
	 * TB_MODULE_HISTORY의 UPDATE_MEMO, X86_RELEASE, X64_RELEASE, X86_DEBUG, X86_DEBUG 변경
	 * @param MODULE_HISTORY_NO
	 * @return
	 */
	int update_module_03(Map<String, Object> paramMap);
	/**
	 * TB_CUSTOMER_APPLY_MODULES 추가
	 * @param paramMap
	 */
	void insert_module_03(Map<String, Object> paramMap);
	
	/**
	 * TB_CUSTOMER_APPLY_MODULES 삭제
	 * @param paramMap
	 */
	void delete_module_01(Map<String, Object> paramMap);

	

}


