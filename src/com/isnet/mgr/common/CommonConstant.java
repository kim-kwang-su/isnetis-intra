package com.isnet.mgr.common;

public class CommonConstant {

	/**
	 * yyyy.MM.dd
	 */
	public static final String DATE_FORMAT_01 = "yyyy.MM.dd";
	/**
	 * yyyy-MM-dd
	 */
	public static final String DATE_FORMAT_02 = "yyyy-MM-dd";
	/**
	 * yyyy/MM/dd
	 */
	public static final String DATE_FORMAT_03 = "yyyy/MM/dd";
	/**
	 * yyyy.MM.dd hh:mm:ss
	 */
	public static final String DATE_FORMAT_04 = "yyyy.MM.dd HH:mm:ss";
	/**
	 * yyyy-MM-dd hh:mm:ss
	 */
	public static final String DATE_FORMAT_05 = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy/MM/dd hh:mm.ss
	 */
	public static final String DATE_FORMAT_06 = "yyyy/MM/dd HH:mm.ss";
	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static final String DATE_FORMAT_07 = "yyyy-MM-dd HH:mm";
	
	/**
	 * MM-dd
	 */
	public static final String DATE_FORMAT_08 = "MM-dd";
	
	public static final String SUCCESS_PROCESS = "success";
	public static final String FAIL_PROCESS = "fail";
	
	public static final int INVALID_VALUE = -1;
	
	public static final String RESPONSE_TYPE_FOR_JSON = "json";
	public static final String RESPONSE_TYPE_FOR_EXCEL = "excel";
	
	// 부서 정보
	/**
	 * SE
	 */
	public static final int DEPT_NO_01 = 1;
	/**
	 * 연구소
	 */
	public static final int DEPT_NO_02 = 2;
	/**
	 * 영업
	 */
	public static final int DEPT_NO_03 = 3;
	/**
	 * 임원실
	 */
	public static final int DEPT_NO_04 = 4;
	/**
	 * 외주
	 */
	public static final int DEPT_NO_05 = 5;
	
	// QS
	public static final int QA_REQUEST_TEST = 1;
	public static final int QA_ADD_TEST_RESULT = 2;
	public static final int QA_ADD_BUG_REPORT = 3;
	
	// 이슈 진행상태
	/**
	 * 고객접수
	 */
	public static final int ISSUE_STATE_NO_01 = 11;
	public static final String ISSUE_STATE_01 = "11";
	/**
	 * 담당자 지정
	 */
	public static final int ISSUE_STATE_NO_02 = 12;
	public static final String ISSUE_STATE_02 = "12";
	/**
	 * 담당자 접수
	 */
	public static final int ISSUE_STATE_NO_03 = 13;
	public static final String ISSUE_STATE_03 = "13";
	/**
	 * 개발 진행 중
	 */
	public static final int ISSUE_STATE_NO_04 = 21;
	public static final String ISSUE_STATE_04 = "21";
	/**
	 * SE 조치 중
	 */
	public static final int ISSUE_STATE_NO_05 = 22;
	public static final String ISSUE_STATE_05 = "22";
	/**
	 * 개발완료
	 */
	public static final int ISSUE_STATE_NO_06 = 23;
	public static final String ISSUE_STATE_06 = "23";
	/**
	 * SE 테스트 중
	 */
	public static final int ISSUE_STATE_NO_07 = 31;
	public static final String ISSUE_STATE_07 = "31";
	/**
	 * SE 테스트 완료
	 */
	public static final int ISSUE_STATE_NO_08 = 32;
	public static final String ISSUE_STATE_08 = "32";
	/**
	 * 고객사 릴리즈
	 */
	public static final int ISSUE_STATE_NO_09 = 33;
	public static final String ISSUE_STATE_09 = "33";
}
