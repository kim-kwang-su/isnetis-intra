package com.isnet.mgr.dao;

import java.util.List;
import java.util.Map;

public interface CustomerDao {

	/**
	 * TB_CUSTOMER 에서 고객사를 조회합니다.
	 * @return CUSTOMER_NO, CUSTOMER_NAME
	 */
	List<Map<String, Object>> select_customer_01();

	int select_customer_02(Map<String, Object> paramMap);

	List<Map<String, Object>> select_customer_03(Map<String, Object> paramMap);

	List<Map<String, Object>> select_customer_04(Map<String, Object> paramMap);

	void insert_customer_01(Map<String, Object> paramMap);
	
	void update_customer_01(Map<String, Object> paramMap);

	List<Map<String, Object>> select_customer_05(Map<String, Object> paramMap);
	
	List<Map<String, Object>> select_customer_06();
}
