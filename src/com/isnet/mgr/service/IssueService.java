package com.isnet.mgr.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IssueService {
	
	/**
	 * 최근 이슈를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getRecentIssueList(Map<String, Object> paramMap);
	/**
	 * 이슈의 상세정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getIssueInfo(Map<String, Object> paramMap);
	/**
	 * 이슈의 진행상황을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getIssueProgressList(Map<String, Object> paramMap);
	/**
	 * 첨부파일 정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getIssueFiles(Map<String, Object> paramMap);
	/**
	 * QA 테스트요청 목록을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getQaRequestList(Map<String, Object> paramMap);
	/**
	 * 이슈를 진행상황별로 검색합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getStateIssueList(Map<String, Object> paramMap);
	/**
	 * QA 테스트요청 정보를 조회합니다.
	 * @param paramMap
	 */
	Map<String, Object> getRequestInfo(Map<String, Object> paramMap);
	/**
	 * QA 버그리포트 정보를 조회합니다.
	 * @param paramMap
	 */
	Map<String, Object> getBugReport(Map<String, Object> paramMap);
	/**
	 * 이슈의 담당개발자를 등록합니다.
	 * @param paramMap
	 */
	void insert_developer(Map<String, Object> paramMap);
	/**
	 * 이슈를 등록합니다.
	 * @param paramMap
	 * @return
	 */
	int add_issue(Map<String, Object> paramMap);
	/**
	 * 이슈의 진행상황을 등록합니다.
	 * @param paramMap
	 */
	void add_issue_history(Map<String, Object> paramMap);
	/**
	 * 이슈의 첨부파일을 저장합니다.
	 * @param paramMap
	 */
	void add_file(Map<String, Object> paramMap);
	/**
	 * QA 테스트요청을 등록합니다.
	 * @param paramMap
	 */
	void add_qa_request(Map<String, Object> paramMap);
	/**
	 * 버그 리포트를 등록합니다.
	 * @param paramMap
	 */
	void add_bug_report(Map<String, Object> paramMap);
	/**
	 * 이슈 정보를 변경합니다.
	 * @param paramMap
	 */
	void update_issue(Map<String, Object> paramMap);
	/**
	 * QA 테스트 결과를 저장합니다.
	 * @param paramMap
	 */
	void update_qa_request(Map<String, Object> paramMap);
}
