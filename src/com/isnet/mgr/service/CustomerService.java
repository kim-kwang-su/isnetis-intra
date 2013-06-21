package com.isnet.mgr.service;

import java.util.List;
import java.util.Map;

public interface CustomerService {

	List<Map<String, Object>> getCustomerList();

	Map<String, Object> getCustomerList(Map<String, Object> paramMap);

	boolean addCustomer(Map<String, Object> paramMap);

	void updateCustomer(Map<String, Object> paramMap);

	Map<String, Object> search_customer(Map<String, Object> paramMap);
	
	List<Map<String, Object>> getTitleCustomer();
}
