package com.isnet.mgr.dao;

import java.util.List;
import java.util.Map;

public interface ScheduleDao {

	/**
	 * 일정정보를 저장합니다.
	 * @param EMPLOYEE_NO, SCHEDULE_TITLE, ALLDAY, SCHEDULE_MEMO, START_SCHEDULE, END_SCHEDULE
	 */
	void insert_schedule_01(Map<String, Object> paramMap);

	/**
	 * 직원별 일정정보를 조회합니다.
	 * @param EMPLOYEE_NO, START, END
	 * @return SCHEDULE_TITLE, ALLDAY, START_SCHEDULE, END_SCHEDULE
	 */
	List<Map<String, Object>> select_schedule_01(Map<String, Object> paramMap);
	
	/**
	 * 일정을 삭제합니다.
	 * @param SCHEDULE_NO
	 */
	void delete_schedule_01(Map<String, Object> paramMap);
	
	/**
	 * 일정을 수정합니다.
	 * @param SCHEDULE_NO, SCHEDULE_TITLE, ALLDAY, SCHEDULE_MEMO, START_SCHEDULE, END_SCHEDULE
	 */
	void update_schedule_01(Map<String, Object> paramMap);
}
