package com.isnet.mgr.dao;

import java.util.List;
import java.util.Map;

public interface SheetDao {

	/**
	 * 업무일지 목록 조회
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_sheet_01(Map<String, Object> paramMap);
	/**
	 * 업무일지 건수 조회
	 * @param paramMap
	 * @return
	 */
	int select_sheet_02(Map<String, Object> paramMap);
	/**
	 * 지원형태 조회
	 * @return
	 */
	List<Map<String, Object>> select_support_type();
	/**
	 * 업무일지 상세 조회
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_sheet_03(Map<String, Object> paramMap);
	/**
	 * 업무일지 상세 조회
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> select_sheet_04(Map<String, Object> paramMap);
	/**
	 * 지원형태 조회
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_sheet_05(Map<String, Object> paramMap);
	/**
	 * 원격지원 일지 조회 - 날짜별
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_sheet_08(Map<String, Object> paramMap);
	/**
	 * 원격지원 일지 조회 - 지원번호별
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> select_sheet_10(Map<String, Object> paramMap);
	
	List<Map<String, Object>> select_sheet_11(Map<String, Object> paramMap);
	
	List<Map<String, Object>> select_sheet_12(Map<String, Object> paramMap);

	/**
	 * 업무일지 등록
	 * @param paramMap
	 */
	void add_sheet01(Map<String, Object> paramMap);
	void add_sheet02(Map<String, Object> paramMap);
	/**
	 * 업무일지 지원 형태 등록
	 * @param param
	 */
	void add_sheet03(Map<String, Object> param);

	/**
	 * 업무일지 삭제
	 * @param paramMap
	 */
	void del_Sheet01(Map<String, Object> paramMap);
	/**
	 * 지원 형태 정보삭제
	 * @param paramMap
	 */
	void del_Sheet02(Map<String, Object> paramMap);

	void del_Remote01(Map<String, Object> paramMap);

	void update_sheet01(Map<String, Object> paramMap);

	void update_sheet02(Map<String, Object> paramMap);
}
	


