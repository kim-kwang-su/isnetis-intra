package com.isnet.mgr.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OptionService {

	/**
	 * 고객사별 제품별 옵션 정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getOptionList(Map<String, Object> paramMap);
	
	/**
	 * 고객사별, 제품별 옵션파일 정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>>  getOptionFileList(Map<String, Object> paramMap);
	/**
	 * 옵션값을 수정합니다.
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> update_option_01(Map<String, Object> paramMap) throws Exception;

	/**
	 * 전체 옵션리스트정보를 조회합니다.
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getFullOptionList(Map<String, Object> paramMap);

	//Map<String, Object> getThirdOptionList(Map<String, Object> paramMap);
	
	Map<String, Object> getForthOptionList(Map<String, Object> paramMap);

	List<Map<String, Object>> select_option_03_01(Map<String, Object> paramMap);
	
	List<Map<String, Object>> select_option_03_02(Map<String, Object> paramMap);
	
	List<Map<String, Object>> select_option_03_03(Map<String, Object> paramMap);
	
	List<Map<String, Object>> select_option_03_04(Map<String, Object> paramMap);

	int add_file(Map<String, Object> paramMap);
	
	int add_app(Map<String, Object> paramMap);
	
	int add_option(Map<String, Object> paramMap);
	
	int add_comment(Map<String, Object> paramMap);

	int delete_comment(Map<String, Object> paramMap);

	List<Map<String, Object>> select_option_03_05();

	void update_file(Map<String, Object> paramMap);

	void update_app(Map<String, Object> paramMap);

	void update_option(Map<String, Object> paramMap);
	
	Map<String, Object> getOptionInfo(Map<String, Object> paramMap);

}
