package com.isnet.mgr.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SheetService {

	/**
	 * 업무일지 리스트 조회
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getSheetList(Map<String, Object> paramMap);

	/**
	 * 업무일지 상세정보 조회 - 
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getSheetByDate(Map<String, Object> paramMap);

	/**
	 * 업무읿지 상세정보 조회 - sheet_no 기준
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getSheetByNo(Map<String, Object> paramMap);
	
	Map<String, Object> getRemoteSheet(Map<String, Object> paramMap);

	/**
	 * 업무일지 작성현황을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getSheetListByDept(Map<String, Object> paramMap);

	/**
	 * 내 업무일지보기
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getMyWorkSheetList(Map<String, Object> paramMap);

	/**
	 * 업무일지 등록
	 * @param paramMap
	 */
	void addSheet(Map<String, Object> paramMap);

	/**
	 * 원격지원 등록
	 * @param paramMap
	 */
	void addRemoteSheet(Map<String, Object> paramMap);

	/**
	 * 업무일지 삭제
	 * @param paramMap
	 */
	void deleteSheet(Map<String, Object> paramMap);

	/**
	 * 원격지원 삭제
	 * @param paramMap
	 */
	void deleteRemoteSheet(Map<String, Object> paramMap);
	/**
	 * 업무일지 수정
	 * @param paramMap
	 */
	void updateSheet(Map<String, Object> paramMap);
	void updateRemoteSheet(Map<String, Object> paramMap);

	
}
