package com.isnet.mgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isnet.mgr.dao.EmployeeDao;
import com.isnet.mgr.dao.ScheduleDao;

@Service
public class ScheduleServiceImp implements ScheduleService{

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ScheduleDao scheduleDao;
	@Autowired
	private EmployeeDao employeeDao;
	
	@Override
	public void addSchedule(Map<String, Object> paramMap) {
		scheduleDao.insert_schedule_01(paramMap);		
	}
	
	@Override
	public List<Map<String, Object>> getScheduleList(Map<String, Object> paramMap) {
		return scheduleDao.select_schedule_01(paramMap);
	}
	
	@Override
	public void deleteSchedule(Map<String, Object> paramMap) {
		scheduleDao.delete_schedule_01(paramMap);
	}
	
	@Override
	public void updateSchedule(Map<String, Object> paramMap) {
		scheduleDao.update_schedule_01(paramMap);
	}
	
	@Override
	public List<Map<String, Object>> getScheduleListByDept(Map<String, Object> paramMap) {
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		logger.info("["+getClass().getSimpleName()+"] [getScheduleListByDept] dept_no ["+paramMap.get("DEPT_NO")+"]");
		
		logger.info("["+getClass().getSimpleName()+"] [getScheduleListByDept] dept_no ["+paramMap.get("DEPT_NO")+"] 부서 스케줄조회");
		List<Map<String, Object>> deptList = employeeDao.select_employee_01(paramMap);
		for(Map<String, Object> map : deptList){
			paramMap.put("EMPLOYEE_NO", map.get("EMPLOYEE_NO"));
			List<Map<String, Object>> result = scheduleDao.select_schedule_01(paramMap);
			resultList.addAll(result);
		}		
		
		return resultList;
	}
}
