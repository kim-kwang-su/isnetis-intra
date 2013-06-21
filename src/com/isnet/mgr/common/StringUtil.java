package com.isnet.mgr.common;

import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

public class StringUtil {

	/**
	 * 원본 문자열이 text를 가진 문자열인지 여부를 반환한다.
	 * @param src 문자열
	 * @return null이나  빈 문자열 혹은 공백만을 가진 경우 false를 반환한다.
	 */
	public static boolean hasText(String src){
		return StringUtils.hasText(src);
	}
	
	/**
	 * 원본 문자열의 좌우 공백을 제거합니다.
	 * @param src 문자열
	 * @return 원본 문자열의 좌우 공백이 제거된 문자열을 반환합니다. 원본 문자열의 값이 null인 경우에는 ""을 반환합니다.
	 */
	public static String trim(String src){
		if(hasText(src)){
			return StringUtils.trimWhitespace(src);
		}else{
			return "";
		}
	}
	
	/**
	 * 원본 문자열의 값이 null인 경우 빈 문자열을 반환합니다.
	 * @param src 문자열
	 * @return 원본 문자열이 null인 경우 공백을 반환합니다. null이 아닌 경우 좌우 공백이 제거된 문자열을 반환합니다.
	 */
	public static String nullToBlank(String src){
		if(hasText(src)){
			return StringUtil.trim(src);
		}else{
			return "";
		}
	}
	
	/**
	 * 원본 문자열의 값이 null인 경우 빈 문자열을 반환합니다.
	 * @param src 문자열
	 * @return 원본 문자열이 null인 경우 공백을 반환합니다. null이 아닌 경우 좌우 공백이 제거된 문자열을 반환합니다.
	 */
	public static int nullToZero(String src){
		if(hasText(src)){
			try{
				return Integer.parseInt(src);
			}catch (Exception e) {
				return 0;
			}
		}else{
			return 0;
		}
	}
	
	/**
	 * 원본 문자열에서 length만큼 잘라서 반환합니다.
	 * @param src 문자열
	 * @param length 반환할 문자열 길이
	 * @return 원본 문자열이 null인 경우에는 null을 반환하고, 공백을 제거한 후 원본 문자열의 길이가 length보다 짧을 경우에는 공백이 제거된 원본 문자열을 그대로 반환합니다.
	 */
	public static String trimByLength(String src, int length){
		if(hasText(src)){
			src = StringUtil.trim(src);
			int src_length = src.length();
			if(src_length < length){
				return src;
			}else{
				return src.substring(0, length);
			}
		}else{
			return "";
		}
	}
	
	/**
	 * 그리드의 COMBO BOX 검색 조건을 생성한다.
	 * @param list
	 * @param keyname
	 * @param valuename
	 * @return
	 */
	public static String listToString(List<Map<String, Object>> list, String keyname, String valuename){
		StringBuilder sb = new StringBuilder();
		sb.append("선택하세요:선택하세요;");
		for(Map<String, Object> map : list){
			String value1 = String.valueOf(map.get(keyname));
			String value2 = String.valueOf(map.get(valuename));
			
			sb.append(value2 + ":" + value1 + ";");
		}
		
		String str = sb.toString();
		str = str.substring(0, str.length()-1);
		
		return str;
	}
	
	public static String lpad(String src, int length, String padding){
		StringBuilder sb = new StringBuilder();
		if(src == null){
			for(int i=0; i<length; i++){
				sb.append(padding);
			}
		}else{
			for(int i=0; i<length - src.length(); i++){
				sb.append(padding);
			}
			sb.append(src);
		}
		
		return sb.toString();
	}
	
}
