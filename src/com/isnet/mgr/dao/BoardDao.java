package com.isnet.mgr.dao;

import java.util.List;
import java.util.Map;

public interface BoardDao {

	/**
	 * TB_FAQ에 FAQ를 등록합니다.
	 * @param paramMap
	 */
	void insert_faq_01(Map<String, Object> paramMap);
	
	/**
	 * TB_FAQ에서 조건을 만족하는 자료수를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	int select_faq_01(Map<String, Object> paramMap);

	/**
	 * TB_FAQ에서 조건을 만족하는 데이타를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_faq_02(Map<String, Object> paramMap);
	
	/**
	 * TB_FAQ에서 조건을 만족하는 데이타를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> select_faq_03(Map<String, Object> paramMap);

	/**
	 * TB_FAQ의 정보를 변경합니다.
	 * @param paramMap
	 */
	void update_faq_01(Map<String, Object> paramMap);

	/**
	 * TB_QNA_QUESTION에 질문을 등록합니다.
	 * @param paramMap
	 */
	void insert_qna_01(Map<String, Object> paramMap);
	
	/**
	 * TB_QNA_ANSWER에 답글을 등록합니다.
	 * @param paramMap
	 */
	void insert_qna_02(Map<String, Object> paramMap);
	
	/**
	 * TB_QNA_QUESTION의 레코드를 삭제합니다.
	 * @param paramMap
	 */
	void delete_qna_01(Map<String, Object> paramMap);

	/**
	 * TB_QNA_ANSWER의 레코드를 삭제합니다.
	 * @param paramMap
	 */
	void delete_qna_02(Map<String, Object> paramMap);

	/**
	 * TB_QNA_QUESTION에서 조건을 만족하는 자료수를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	int select_qna_01(Map<String, Object> paramMap);

	/**
	 * TB_QNA_QUESTION에서 조건을 만족하는 데이타를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_qna_02(Map<String, Object> paramMap);

	/**
	 * TB_QNA_QUESTION에서 조건을 만족하는 데이타를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> select_qna_03(Map<String, Object> paramMap);

	/**
	 * TB_QNA_ANSWER에서 조건을 만족하는 댓글 데이타를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_qna_04(Map<String, Object> paramMap);

	/**
	 * TB_QNA_QUESTION의 정보를 변경합니다.
	 * @param paramMap
	 */
	void update_qna_01(Map<String, Object> paramMap);

	/**
	 * TB_QNA_ANSWER의 정보를 변경합니다.
	 * @param paramMap
	 */
	void update_qna_02(Map<String, Object> paramMap);

	/**
	 * TB_FORM에 서식을 등록합니다.
	 * @param paramMap
	 */
	void insert_form_01(Map<String, Object> paramMap);
	
	/**
	 * TB_FORM에서 조건을 만족하는 자료수를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	int select_form_01(Map<String, Object> paramMap);

	/**
	 * TB_FORM에서 조건을 만족하는 데이타를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_form_02(Map<String, Object> paramMap);

	/**
	 * TB_FORM에서 조건을 만족하는 데이타를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> select_form_03(Map<String, Object> paramMap);

	/**
	 * TB_FORM의 정보를 변경합니다.
	 * @param paramMap
	 */
	void update_form_01(Map<String, Object> paramMap);
	
	/**
	 * TB_QNA_QUESTION에 질문을 등록합니다.
	 * @param paramMap
	 */
	void insert_sug_01(Map<String, Object> paramMap);
	
	/**
	 * TB_QNA_ANSWER에 답글을 등록합니다.
	 * @param paramMap
	 */
	void insert_sug_02(Map<String, Object> paramMap);
	
	/**
	 * TB_QNA_QUESTION의 레코드를 삭제합니다.
	 * @param paramMap
	 */
	void delete_sug_01(Map<String, Object> paramMap);

	/**
	 * TB_QNA_ANSWER의 레코드를 삭제합니다.
	 * @param paramMap
	 */
	void delete_sug_02(Map<String, Object> paramMap);

	/**
	 * TB_QNA_QUESTION에서 조건을 만족하는 자료수를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	int select_sug_01(Map<String, Object> paramMap);

	/**
	 * TB_QNA_QUESTION에서 조건을 만족하는 데이타를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_sug_02(Map<String, Object> paramMap);

	/**
	 * TB_QNA_QUESTION에서 조건을 만족하는 데이타를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> select_sug_03(Map<String, Object> paramMap);

	/**
	 * TB_QNA_ANSWER에서 조건을 만족하는 댓글 데이타를 반환합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> select_sug_04(Map<String, Object> paramMap);

	/**
	 * TB_QNA_QUESTION의 정보를 변경합니다.
	 * @param paramMap
	 */
	void update_sug_01(Map<String, Object> paramMap);

	/**
	 * TB_QNA_ANSWER의 정보를 변경합니다.
	 * @param paramMap
	 */
	void update_sug_02(Map<String, Object> paramMap);
	
}
