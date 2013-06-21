package com.isnet.mgr.service;

import java.util.List;
import java.util.Map;

public interface ProductService {

	List<Map<String, Object>> getProductList();
	
	List<Map<String, Object>> getProductList02(Map<String, Object> paramMap);

	void add_product(Map<String, Object> paramMap);
}
