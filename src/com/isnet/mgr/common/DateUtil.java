package com.isnet.mgr.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.ibm.icu.util.ChineseCalendar;

public class DateUtil {

	public static Date getPeriodDate(String str, String date_format, int period){
		Date date = stringToDate(str, date_format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, day + period);
		
		return cal.getTime();
	}
	public static String getPeriodDateToString(String str, String date_format, int period){
		Date date = getPeriodDate(str, date_format, period);
		return dateToString(date, date_format);
	}
	
	public static String dateToString(String date_format){
		SimpleDateFormat sdf = new SimpleDateFormat(date_format);
		return sdf.format(new Date());
	}
	
	public static String dateToString(Date date, String date_format){
		SimpleDateFormat sdf = new SimpleDateFormat(date_format);
		return sdf.format(date);
	}
	
	public static Date stringToDate(String str, String date_format) {
		try {
			return DateUtils.parseDate(str, new String[]{date_format});
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String get(Calendar cal, int field){
		int value = cal.get(field);
		if(field == Calendar.MONTH){
			value += 1;
		}
		return (value<10 ? "0"+value : String.valueOf(value));
	}
	
	public static String getMaxim(Calendar cal, int field){
		return String.valueOf(cal.getActualMaximum(field));
	}
	
	public static Date getLunarDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		ChineseCalendar cc = new ChineseCalendar();
		cc.setTimeInMillis(cal.getTimeInMillis());
		
		return cc.getTime();
	}
	
	/*public static ChineseCalendar solarTolunarCalendar(int year, int month, int day){
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		
		ChineseCalendar cc = new ChineseCalendar();
		cc.setTimeInMillis(cal.getTimeInMillis());
		
		return cc;
	}
	
	public static String getLunarDate(ChineseCalendar cc){
		Calendar cal = Calendar.getInstance();
		int lunar_year = cc.get(ChineseCalendar.EXTENDED_YEAR)-2637;
	    int lunar_month = cc.get(ChineseCalendar.MONTH);
	    int lunar_day = cc.get(ChineseCalendar.DAY_OF_MONTH);
	    
	    cal.set(Calendar.YEAR, lunar_year);
	    cal.set(Calendar.MONTH, lunar_month);
	    cal.set(Calendar.DAY_OF_MONTH, lunar_day);
	    
		Date date = new Date(cal.getTimeInMillis());
		return DateUtil.dateToString(date, "yyyy-MM-dd");
	}
	
	public boolean isLeapMonth(ChineseCalendar cc){
		int value = cc.get(ChineseCalendar.IS_LEAP_MONTH);
		return value == 0 ? false : true;
	}
	
	public static Date lunarToSolarDate(int year, int month, int day, int isLeapMonth){
		
		ChineseCalendar cc = new ChineseCalendar();
		cc.set(ChineseCalendar.EXTENDED_YEAR, year + 2637);
		cc.set(ChineseCalendar.MONTH, month-1);
		cc.set(ChineseCalendar.DAY_OF_MONTH, day);
		cc.set(ChineseCalendar.IS_LEAP_MONTH, isLeapMonth);
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cc.getTimeInMillis());
		
		return new Date(cal.getTimeInMillis());
	}*/
	
}
