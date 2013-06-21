package com.isnet.mgr.dao;

import java.util.List;
import java.util.Map;

public interface EmployeeDao {

	
	/**
	 * TB_EMPLOYEE 에서 부서별 사원정보를 조회합니다.
	 * @param 부서번호 DEPT_NO
	 * @return EMPLOYEE_NO, EMPLOYEE_NAME, DEPT_NO
	 */
	List<Map<String, Object>> select_employee_01(Map<String, Object> paramMap);
	
	/**
	 * TB_EMPLOYEE 에서 사원 상세정보를 조회합니다.
	 * @param EMPLOYEE_NO 혹은 EMAIL
	 * @return EMPLOYEE_NO, EMPLOYEE_NAME, DEPT_NO, LOGIN_PASSWORD, EMAIL, IS_USED
	 */
	List<Map<String, Object>> select_employee_02(Map<String, Object> paramMap);
	
	/**
	 * 사용자 수를 반환합니다.
	 * @param EMPLOYEE_NO 혹은 EMAIL
	 */
	int select_employee_03(Map<String, Object> paramMap);

	/**
	 * 이슈 메일 참조로 설정된 사용자의 이메일을 조회합니다.
	 * @return
	 */
	List<String> select_employee_04();
	
	/**
	 * 새로운 사용자를 등록합니다.
	 * @param EMPLOYEE_NAME, EMAIL, LOGIN_PASSWORD, DEPT_NO
	 */
	void insert_employee_01(Map<String, Object> paramMap);
	
	/**
	 * 사용자의 사용여부를 변경한다.
	 * @param  EMPLOYEE_NO, IS_USED
	 */
	void update_employee_01(Map<String, Object> paramMap);
}
