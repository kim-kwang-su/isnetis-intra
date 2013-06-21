package com.isnet.mgr.service;

import java.util.List;
import java.util.Map;

public interface BoardService {

	/**
	 * FAQ를 등록합니다.
	 * @param paramMap
	 */
	void insertFAQ(Map<String, Object> paramMap);
	
	/**
	 * FAQ 목록을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getFAQList(Map<String, Object> paramMap);
	
	/**
	 * FAQ를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getFAQ(Map<String, Object> paramMap);

	/**
	 * FAQ를 수정합니다.
	 * @param paramMap
	 */
	void updateFAQ(Map<String, Object> paramMap);

	/**
	 * 질문글을 등록합니다.
	 * @param paramMap
	 */
	void insertQuestion(Map<String, Object> paramMap);
	
	/**
	 * 답글을 등록합니다.
	 * @param paramMap
	 */
	void insertAnswer(Map<String, Object> paramMap);

	/**
	 * QnA의 질문글을 삭제합니다.
	 * @param paramMap
	 */
	void deleteQuestion(Map<String, Object> paramMap);

	/**
	 * QnA의 답변글을 삭제합니다.
	 * @param paramMap
	 */
	void deleteAnswer(Map<String, Object> paramMap);

	/**
	 * QnA의 질문글을 수정합니다.
	 * @param paramMap
	 */
	
	void updateQuestion(Map<String, Object> paramMap);

	/**
	 * QnA의 답변글을 수정합니다.
	 * @param paramMap
	 */
	void updateAnswer(Map<String, Object> paramMap);

	/**
	 * QnA 질문을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getQnaQuestionList(Map<String, Object> paramMap);

	/**
	 * QnA 질문을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getQuestion(Map<String, Object> paramMap);

	/**
	 * QnA 답글을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getAnswerList(Map<String, Object> paramMap);

	/**
	 * 서식을 등록합니다.
	 * @param paramMap
	 */
	void insertForm(Map<String, Object> paramMap);
	
	/**
	 * 서식 목록을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getFormList(Map<String, Object> paramMap);

	/**
	 * 서식을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getFormInfo(Map<String, Object> paramMap);

	/**
	 * 서식을 삭제합니다.
	 * @param paramMap
	 */
	void deleteForm(Map<String, Object> paramMap);
	
	/**
	 * 제안을 등록합니다.
	 * @param paramMap
	 */
	void insertSuggestion(Map<String, Object> paramMap);
	
	/**
	 * 의견을 등록합니다.
	 * @param paramMap
	 */
	void insertComment(Map<String, Object> paramMap);

	/**
	 * 제안을 삭제합니다.
	 * @param paramMap
	 */
	void deleteSuggestion(Map<String, Object> paramMap);

	/**
	 * 의견을 삭제합니다.
	 * @param paramMap
	 */
	void deleteComment(Map<String, Object> paramMap);

	/**
	 * 제안을 수정합니다.
	 * @param paramMap
	 */
	
	void updateSuggestion(Map<String, Object> paramMap);

	/**
	 * 의견을 수정합니다.
	 * @param paramMap
	 */
	void updateComment(Map<String, Object> paramMap);

	/**
	 * 제안을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getSuggestionList(Map<String, Object> paramMap);

	/**
	 * 제안을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getSuggestion(Map<String, Object> paramMap);

	/**
	 * 의견을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getCommentList(Map<String, Object> paramMap);

	
}
