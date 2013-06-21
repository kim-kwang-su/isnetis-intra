package com.isnet.mgr.dao;

import java.util.List;
import java.util.Map;

public interface ManagerDao {

	int select_manager_01(Map<String, Object> paramMap);
	
	List<Map<String, Object>> select_manager_02(Map<String, Object> paramMap);
	
	List<Map<String, Object>> select_manager_03(Map<String, Object> paramMap);
	
	void insert_manager_01(Map<String, Object> paramMap);
	
	void update_manager_01(Map<String, Object> paramMap);
}
