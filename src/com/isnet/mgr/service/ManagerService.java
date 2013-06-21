package com.isnet.mgr.service;

import java.util.Map;

public interface ManagerService {

	Map<String, Object> getManagerList(Map<String, Object> paramMap);

	boolean addManager(Map<String, Object> paramMap);

	void updateManager(Map<String, Object> paramMap);
}
