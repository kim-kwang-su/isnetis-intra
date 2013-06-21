package com.isnet.mgr.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.isnet.mgr.common.CommonCodeBean;
import com.isnet.mgr.common.CommonConstant;
import com.isnet.mgr.common.DateUtil;
import com.isnet.mgr.common.StringUtil;
import com.isnet.mgr.service.ScheduleService;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private CommonCodeBean commonCodeBean;
	@Autowired
	private MappingJacksonJsonView jsonView;
	@Autowired
	private ScheduleService scheduleService;
	
	@RequestMapping(value="/schedule_01.isnet", method=RequestMethod.GET)
	public ModelAndView schedule_01(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/schedule/schedule_01");
		
		return mav;
	}
	
	@RequestMapping(value="/schedule_02.isnet", method=RequestMethod.GET)
	public ModelAndView schedule_02(){
		
		ModelAndView mav = new ModelAndView();
		
		mav.addAllObjects(commonCodeBean.getCommonCodeMap());
		
		mav.setViewName("/schedule/schedule_02");
		
		
		return mav;
	}
	
	@RequestMapping(value="/schedule_03.isnet", method=RequestMethod.GET)
	public ModelAndView schedule_03(){
		
		ModelAndView mav = new ModelAndView();
		
		mav.addAllObjects(commonCodeBean.getCommonCodeMap());
		
		mav.setViewName("/schedule/schedule_03");
		
		
		return mav;
	}
	
	/*
	 * 직원별 일정을 조회합니다.
	 */
	@RequestMapping(value="/list_schedule_01.isnet", method=RequestMethod.GET)
	public ModelAndView list_schedule_01(@RequestParam("START_YEAR")String start_year,
										@RequestParam("START_MONTH")String start_month,
										@RequestParam("START_DAY")String start_day,
										@RequestParam(value="END_YEAR", required=false)String end_year,
										@RequestParam(value="END_MONTH", required=false)String end_month,
										@RequestParam(value="END_DAY", required=false)String end_day,
										@RequestParam(value="EMPLOYEE_NO", required=false)int employee_no){
	
		logger.debug("["+getClass().getSimpleName()+"] [list_schedule_01] start");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_01] start_year ["+ start_year+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_01] start_month ["+ start_month+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_01] start_day ["+ start_day+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_01] end_year ["+end_year+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_01] end_month ["+ end_month+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_01] end_day ["+ end_day+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_01] employee_no ["+ employee_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			
			Date start = this.getDate(start_year, start_month, start_day, null, null);
			Date end = this.getDate(end_year, end_month, end_day, null, null);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("EMPLOYEE_NO", employee_no);
			param.put("START", DateUtil.dateToString(start, CommonConstant.DATE_FORMAT_02) );
			param.put("END", DateUtil.dateToString(end, CommonConstant.DATE_FORMAT_02));
			logger.debug("["+getClass().getSimpleName()+"] [list_schedule_01] param ["+param+"]");
			
			List<Map<String, Object>> scheduleList = scheduleService.getScheduleList(param);
			logger.debug("["+getClass().getSimpleName()+"] [list_schedule_01] scheduleList ["+scheduleList+"]");
			logger.info("["+getClass().getSimpleName()+"] [list_schedule_01] scheduleList.size ["+scheduleList.size()+"]");
			
			mav.addObject("scheduleList", scheduleList);
			
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_schedule_01] end");
		}
		
		return mav;
	}
	
	
	/*
	 * 부서별 일정을 조회합니다.
	 */
	@RequestMapping(value="/list_schedule_02.isnet", method=RequestMethod.GET)
	public ModelAndView list_schedule_02(@RequestParam("START_YEAR")String start_year,
										@RequestParam("START_MONTH")String start_month,
										@RequestParam("START_DAY")String start_day,
										@RequestParam(value="END_YEAR", required=false)String end_year,
										@RequestParam(value="END_MONTH", required=false)String end_month,
										@RequestParam(value="END_DAY", required=false)String end_day,
										@RequestParam(value="DEPT_NO", required=false)int dept_no){
	
		logger.debug("["+getClass().getSimpleName()+"] [list_schedule_02] start");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_02] start_year ["+ start_year+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_02] start_month ["+ start_month+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_02] start_day ["+ start_day+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_02] end_year ["+end_year+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_02] end_month ["+ end_month+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_02] end_day ["+ end_day+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_schedule_02] dept_no ["+ dept_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			
			Date start = this.getDate(start_year, start_month, start_day, null, null);
			Date end = this.getDate(end_year, end_month, end_day, null, null);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("DEPT_NO", dept_no);
			param.put("START", DateUtil.dateToString(start, CommonConstant.DATE_FORMAT_02) );
			param.put("END", DateUtil.dateToString(end, CommonConstant.DATE_FORMAT_02));
			logger.debug("["+getClass().getSimpleName()+"] [list_schedule_02] param ["+param+"]");
			
			List<Map<String, Object>> scheduleList = scheduleService.getScheduleListByDept(param);
			logger.debug("["+getClass().getSimpleName()+"] [list_schedule_02] scheduleList ["+scheduleList+"]");
			logger.info("["+getClass().getSimpleName()+"] [list_schedule_02] scheduleList.size ["+scheduleList.size()+"]");
			
			mav.addObject("scheduleList", scheduleList);
			
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_schedule_02] end");
		}
		
		return mav;
	}
	
	/*
	 * 새로운 일정을 등록합니다.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add_schedule.isnet", method=RequestMethod.POST)
	public ModelAndView add_schedule(@RequestParam("SCHEDULE_TITLE")String schedule_title,
									@RequestParam("SCHEDULE_MEMO")String schedule_memo,
									@RequestParam(value="ALLDAY", defaultValue="Y")String allday_mode,
									@RequestParam("START_YEAR")String start_year,
									@RequestParam("START_MONTH")String start_month,
									@RequestParam("START_DAY")String start_day,
									@RequestParam(value="START_HOUR", required=false)String start_hour,
									@RequestParam(value="START_MINUTE", required=false)String start_minute,
									@RequestParam(value="END_YEAR", required=false)String end_year,
									@RequestParam(value="END_MONTH", required=false)String end_month,
									@RequestParam(value="END_DAY", required=false)String end_day,
									@RequestParam(value="END_HOUR", required=false)String end_hour,
									@RequestParam(value="END_MINUTE", required=false)String end_minute,
									HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_schedule] start");
		logger.info("["+getClass().getSimpleName()+"] [add_schedule] schedule_title ["+schedule_title+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_schedule] schedule_memo ["+schedule_memo+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_schedule] allday_mode ["+allday_mode+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_schedule] start_year ["+ start_year+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_schedule] start_month ["+ start_month+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_schedule] start_day ["+ start_day+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_schedule] start_hour ["+start_hour+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_schedule] start_minute ["+start_minute+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_schedule] end_year ["+end_year+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_schedule] end_month ["+ end_month+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_schedule] end_day ["+ end_day+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_schedule] end_hour ["+end_hour+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_schedule] end_minute ["+end_minute+"]");
		
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("SCHEDULE_TITLE", schedule_title);
			param.put("SCHEDULE_MEMO", schedule_memo);
			param.put("ALLDAY", allday_mode);			
			
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			logger.info("["+getClass().getSimpleName()+"] [add_schedule] employee_no ["+employee_no+"]");
			param.put("EMPLOYEE_NO", employee_no);
			
			Date start = this.getDate(start_year, start_month, start_day, start_hour, start_minute);
			param.put("START_SCHEDULE", start);

			if(allday_mode.equals("N")){				
				Date end = this.getDate(end_year, end_month, end_day, end_hour, end_minute);
				param.put("END_SCHEDULE", end);
			}else{
				param.put("END_SCHEDULE", start);
			}
			
			scheduleService.addSchedule(param);
			mav.addObject("isSuccess", true);
			mav.addObject("SCHEDULE_NO", param.get("SCHEDULE_NO"));
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_schedule] 일정 등록 중 오류 발생");
			mav.addObject("isSuccess", false);
		}finally{
			logger.info("["+getClass().getSimpleName()+"] [add_schedule] end");
		}
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/delete_schedule.isnet", method=RequestMethod.GET)
	public ModelAndView delete_schedule(@RequestParam("SCHEDULE_NO")int schedule_no,
											HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [delete_schedule_01] start");
		logger.info("["+getClass().getSimpleName()+"] [delete_schedule_01] schedule_no ["+schedule_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("SCHEDULE_NO", schedule_no);
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			logger.info("["+getClass().getSimpleName()+"] [add_issue01] employee_no ["+employee_no+"]");
			map.put("EMPLOYEE_NO", employee_no);
			
			scheduleService.deleteSchedule(map);
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [delete_schedule_01] schedule_no["+schedule_no+"] 일정 삭제 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [delete_schedule_01] end");
		}
		
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/update_schedule.isnet", method=RequestMethod.POST)
	public ModelAndView update_schedule(@RequestParam("SCHEDULE_NO")int schedule_no,
									@RequestParam("SCHEDULE_TITLE")String schedule_title,
									@RequestParam("SCHEDULE_MEMO")String schedule_memo,
									@RequestParam(value="ALLDAY", defaultValue="Y")String allday_mode,
									@RequestParam("START_YEAR")String start_year,
									@RequestParam("START_MONTH")String start_month,
									@RequestParam("START_DAY")String start_day,
									@RequestParam(value="START_HOUR", required=false)String start_hour,
									@RequestParam(value="START_MINUTE", required=false)String start_minute,
									@RequestParam(value="END_YEAR", required=false)String end_year,
									@RequestParam(value="END_MONTH", required=false)String end_month,
									@RequestParam(value="END_DAY", required=false)String end_day,
									@RequestParam(value="END_HOUR", required=false)String end_hour,
									@RequestParam(value="END_MINUTE", required=false)String end_minute,
									HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [update_schedule] start");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] schedule_no ["+schedule_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] schedule_title ["+schedule_title+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] schedule_memo ["+schedule_memo+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] allday_mode ["+allday_mode+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] start_year ["+ start_year+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] start_month ["+ start_month+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] start_day ["+ start_day+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] start_hour ["+start_hour+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] start_minute ["+start_minute+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] end_year ["+end_year+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] end_month ["+ end_month+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] end_day ["+ end_day+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] end_hour ["+end_hour+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_schedule] end_minute ["+end_minute+"]");
		
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);		
		
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("SCHEDULE_NO", schedule_no);
			param.put("SCHEDULE_TITLE", schedule_title);
			param.put("SCHEDULE_MEMO", schedule_memo);
			param.put("ALLDAY", allday_mode);			
			
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			logger.info("["+getClass().getSimpleName()+"] [update_schedule] employee_no ["+employee_no+"]");
			param.put("EMPLOYEE_NO", employee_no);
			
			Date start = this.getDate(start_year, start_month, start_day, start_hour, start_minute);
			param.put("START_SCHEDULE", start);

			if(allday_mode.equals("N")){				
				Date end = this.getDate(end_year, end_month, end_day, end_hour, end_minute);
				param.put("END_SCHEDULE", end);
			}else{
				param.put("END_SCHEDULE", start);
			}
			
			scheduleService.updateSchedule(param);
			mav.addObject("isSuccess", true);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_schedule] schedule_no["+schedule_no+"] 일정변경 중 오류 발생");
			mav.addObject("isSuccess", false);
		}finally{
			logger.info("["+getClass().getSimpleName()+"] [update_schedule] end");
		}
		return mav;
	}
	
	
	private Date getDate(String year, String month, String day, String hour, String minute){
		
		Calendar start = Calendar.getInstance();
		
		start.set(Calendar.YEAR, StringUtil.nullToZero(year));
		start.set(Calendar.MONTH, StringUtil.nullToZero(month));
		start.set(Calendar.DAY_OF_MONTH, StringUtil.nullToZero(day));
		start.set(Calendar.HOUR_OF_DAY, StringUtil.nullToZero(hour));
		start.set(Calendar.MINUTE, StringUtil.nullToZero(minute));
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		
		return start.getTime();
	}
}
