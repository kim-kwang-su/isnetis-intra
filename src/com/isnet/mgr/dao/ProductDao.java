package com.isnet.mgr.dao;

import java.util.List;
import java.util.Map;

public interface ProductDao {

	/** 
	 * TB_PRODUCT의 제품을 조회한다.
	 * @return PRODUCT_NO, PRODUCT_NAME
	 */
	List<Map<String, Object>> select_product_01();
	
	/** 
	 * TB_PRODUCT의 제품을 조회한다.
	 * @return PRODUCT_NO, PRODUCT_NAME
	 */
	List<Map<String, Object>> select_product_02(Map<String, Object> paramMap);

	void add_product(Map<String, Object> paramMap);
}
