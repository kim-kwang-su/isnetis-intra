package com.isnet.mgr.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ModuleService {

	// 최근 모듈 변경이력을 조회합니다.
	Map<String, Object> getRecentModuleList(Map<String, Object> paramMap);
	// 모듈 검색결과를 반환합니다.
	Map<String, Object> getModuleList(Map<String, Object> paramMap);
	// 모듈 상세정보를 조회합니다.
	Map<String, Object> getModuleInfo(Map<String, Object> paramMap);
	// 해당 모듈의 변경이력을 조회합니다.
	Map<String, Object> getModuleHistory(Map<String, Object> paramMap);
	// 모듈변경이력 정보를 저장합니다.
	void addModuleHistory(Map<String, Object> paramMap);
	// 신규 모듈을 등록합니다.
	void addModule(Map<String, Object> paramMap);
	// 모듈의 지정된 변경이력정보를 반환합니다.
	Map<String, Object> getModuleHistoryInfo(Map<String, Object> paramMap);
	// 제품별 최종버전정보를 조회합니다.
	Map<String, Object> getProductLastVersionList(Map<String, Object> paramMap);
	// 고객사별 최종버전정보를 조회합니다.
	Map<String, Object> getCustomerLastVersionList(Map<String, Object> paramMap);
	//최종 변경이력을 수정합니다.
	void updateLastModuleHistoryNo(Map<String, Object> paramMap);
	// TB_CUSTOMER_APPLY_MODULES에 데이터 추가 
	void addCustomerApplyModule(Map<String, Object> paramMap);	
	Map<String, Object> getCustomerApple(Map<String, Object> paramMap);
	// 모듈기본 정보를 수정합니다.
	void updateModuleInfo(Map<String, Object> paramMap);
	// 모듈변경이력을 수정합니다.
	void updateModuleHistoryInfo(Map<String, Object> paramMap);
}
