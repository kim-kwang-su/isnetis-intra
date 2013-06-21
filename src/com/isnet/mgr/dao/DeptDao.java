package com.isnet.mgr.dao;

import java.util.List;
import java.util.Map;

public interface DeptDao {

	/**
	 * 부서정보를 반환합니다.
	 * @return TB_DEPT의 DEPT_NO, DEPT_NAME 정보를 반환합니다.
	 */
	List<Map<String, Object>> select_dept_01();
	
}
