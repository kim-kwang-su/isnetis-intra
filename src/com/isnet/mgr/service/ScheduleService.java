package com.isnet.mgr.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ScheduleService {

	void addSchedule(Map<String, Object> paramMap);
	List<Map<String, Object>> getScheduleList(Map<String, Object> paramMap);
	List<Map<String, Object>> getScheduleListByDept(Map<String, Object> paramMap);
	void deleteSchedule(Map<String, Object> paramMap);
	void updateSchedule(Map<String, Object> paramMap);
}
