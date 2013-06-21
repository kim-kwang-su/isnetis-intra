package com.isnet.mgr.dao;

import java.util.List;
import java.util.Map;

public interface OptionDao {

	/**
	 * TB_OPTION_FILE에서 파일명 조회
	 * @param 
	 * @return OPTION_FILE_NO, OPTION_FILE_NAME,LOCATION_NO,PRODUCT_NO
	 */
	List<Map<String, Object>> select_option_file_01(Map<String, Object> map);
	/**
	 * TB_OPTION_FILE , TB_OPTION_APP T2,TB_OPTIONS ,TB_COMMENTS 에서 옵션조회
	 * @param 
	 * @return ~
	 */
	List<Map<String, Object>> select_option_file_03(Map<String, Object> map);

	/**
	 * TB_CUSTOMER_APPLY_OPTIONS 삭제
	 * @param CUSTOMER_NO, PRODUCT_NO
	 * @return 
	 */
	int delete_option_01(Map<String, Object> paramMap);

	/**
	 * TB_CUSTOMER_APPLY_OPTIONS 추가
	 * @param CUSTOMER_NO, PRODUCT_NO
	 * @return 
	 */
	int insert_option_01(Map<String, Object> tempMap);

	/**
	 * TB_COMMENTS에서 옵션의 주석정보를 조회합니다.
	 * @return
	 */
	List<Map<String, Object>> select_option_01();
	/**
	 * TB_OPTIONS에서 옵션정보를 조회합니다.
	 * @param 
	 * @return 
	 */
	List<Map<String, Object>> select_option_02(Map<String, Object> map);


	/**
	 * TB_OPTIONS에서 총 카운트조회
	 * @param PRODUCT_NO, OPTION_FILE_NO
	 * @return 
	 */
	int select_option_03(Map<String, Object> paramMap);
	
	
	/**
	 * TB_OPTION_FILE에서 조회
	 * @param PRODUCT_NO, OPTION_FILE_NO
	 * @return 
	 */
	List<Map<String, Object>> select_option_03_01(Map<String, Object> paramMap);
	
	/**
	 * TB_OPTION_APP에서 조회
	 * @param OPTION_FILE_NO, OPTION_APP_NO
	 * @return TB_MODULE의 전체 ROW 갯수
	 */
	List<Map<String, Object>> select_option_03_02(Map<String, Object> paramMap);

	/**
	 * TB_OPTIONS에서 조회
	 * @param OPTION_APP_NO, OPTION_NO
	 * @return 
	 */
	List<Map<String, Object>> select_option_03_03(Map<String, Object> paramMap);
	
	/**
	 * TB_COMMENT에서 조회
	 * @param OPTION_NO
	 * @return 
	 */
	List<Map<String, Object>> select_option_03_04(Map<String, Object> paramMap);

	/**
	 * TB_OPTION_FILE에서 조회
	 * @param PRODUCT_NO, OPTION_FILE_NO
	 * @return 
	 */
	List<Map<String, Object>> select_option_03_05();
	
	
	/**
	 * TB_OPTION_FILE에  추가
	 * @param OPTION_NO
	 * @return 
	 */
	
	void add_file(Map<String, Object> paramMap);
	void update_file(Map<String, Object> paramMap);
	void delete_file(Map<String, Object> paramMap);
	List<Map<String, Object>> select_option_05_02();
	
	/**
	 * TB_OPTION_APP에 추가
	 * @param 
	 * @return 
	 */
	
	void add_app(Map<String, Object> paramMap);
	void update_app(Map<String, Object> paramMap);
	void delete_app(Map<String, Object> paramMap);
	List<Map<String, Object>> select_option_05_03();
	/**
	 * TB_OPTIONS 에  추가
	 * @param 
	 * @return 
	 */
	
	void add_option(Map<String, Object> paramMap);	
	void update_option(Map<String, Object> paramMap);	
	void delete_option(Map<String, Object> paramMap);	
	List<Map<String, Object>> select_option_05_04();
	
	/**
	 * TB_COMMENT 에서  추가
	 * @param 
	 * @return 
	 */
	
	void add_comment(Map<String, Object> paramMap);
	void update_comment(Map<String, Object> paramMap);

	
	/**
	 * TB_OPTION_FILE에서MAX(OPTION_FILE_NO) 조회
	 * @param 
	 * @return OPTION_FILE_NO
	 */
	int select_option_04_02();

	/**
	 * TB_OPTION_APP에서MAX(OPTION_APP_NO) 조회
	 * @param 
	 * @return OPTION_APP_NO
	 */
	int select_option_04_03();	


	/**
	 * TB_OPTIONS 에서MAX(OPTION_NO) 조회
	 * @param 
	 * @return OPTION_NO
	 */
	int select_option_04_04();

	/**
	 * TB_COMMENT 에서MAX(COMMENT_NO) 조회
	 * @param 
	 * @return COMMENT_NO
	 */
	int select_option_04_05();

	/**
	 * TB_COMMENT 삭제
	 * @param OPTION_NO, COMMENT_NO
	 * @return 
	 */
	int delete_comment(Map<String, Object> paramMap);	

	/**
	 * 옵션비교정보
	 * @param 
	 * @return 
	 */

	List<Map<String, Object>> select_option_05_01(Map<String, Object> paramMap);

	/**
	 * 옵션 상세정보 조회
	 * @param paramMap
	 */
	Map<String, Object> select_option_06(Map<String, Object> paramMap);


}



