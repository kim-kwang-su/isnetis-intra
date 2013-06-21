package com.isnet.mgr.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EmployeeService {

	Map<String, Object> getEmployeeInfo(Map<String, Object> paramMap);
	List<Map<String, Object>> getDeptList();
	boolean addEmployee(Map<String, Object> paramMap);
	Map<String, Object> getEmployeeList(Map<String, Object> paramMap);
	void updateEmployeeInfo(Map<String, Object> paramMap);
}
