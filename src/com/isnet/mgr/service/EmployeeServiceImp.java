package com.isnet.mgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isnet.mgr.dao.DeptDao;
import com.isnet.mgr.dao.EmployeeDao;

@Service
public class EmployeeServiceImp implements EmployeeService{

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private DeptDao deptDao;
	
	
	@Override
	public Map<String, Object> getEmployeeInfo(Map<String, Object> paramMap) {
		List<Map<String, Object>> employeeList = employeeDao.select_employee_02(paramMap);
		if(employeeList.size() > 0){
			return employeeList.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<Map<String, Object>> getDeptList() {
		return deptDao.select_dept_01();
	}
	
	@Override
	public boolean addEmployee(Map<String, Object> paramMap) {
		
		boolean isSuccess = false;
		
		List<Map<String, Object>> employeeList = employeeDao.select_employee_02(paramMap);
		if(employeeList.size() == 0){
			
			employeeDao.insert_employee_01(paramMap);
			isSuccess = true;
		}
		return isSuccess;
	}
	
	@Override
	public Map<String, Object> getEmployeeList(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [getEmployeeList] start");
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		// TB_MODULE에서 페이지번호에 해당하는 ROW 조회
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		
		paramMap.put("BEGIN_INDEX", begin_index);
		
		int total_rows = employeeDao.select_employee_03(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getEmployeeList] total_rows ["+total_rows+"]");
		// 전체 페이지 갯수를 계산
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getEmployeeList] total_pages ["+total_pages+"]");
		
		
		List<Map<String, Object>> employeeList = employeeDao.select_employee_02(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getEmployeeList] employeeList.size ["+employeeList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getEmployeeList] employeeList ["+employeeList+"]");
		for(Map<String, Object> map  : employeeList){
			String url = "";
			String pwd_reset ="";
			
			if(((String)map.get("IS_USED")).equals("Y")){
				url = "<a href='update_01.isnet?en="+String.valueOf(map.get("EMPLOYEE_NO"))+"&iu=N'>정지</a>";
				pwd_reset ="<a href='javascript:passwordReset("+String.valueOf(map.get("EMPLOYEE_NO"))+")'>초기화</a>";
			}else{
				url = "<a href='update_01.isnet?en="+String.valueOf(map.get("EMPLOYEE_NO"))+"&iu=Y'>복구</a>";
				pwd_reset ="";
			}
			
			
			map.put("UPDATE_URL", url);
			map.put("PWD_RESET", pwd_reset);
		}
		
		
		int records = employeeList.size();
		logger.info("["+getClass().getSimpleName()+"] [getEmployeeList] records ["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", employeeList);
		resultData.put("total", total_pages);	
		
		logger.debug("["+getClass().getSimpleName()+"] [getEmployeeList] end");
		return resultData;
	}
	
	@Override
	public void updateEmployeeInfo(Map<String, Object> paramMap) {
		employeeDao.update_employee_01(paramMap);
	}

}
