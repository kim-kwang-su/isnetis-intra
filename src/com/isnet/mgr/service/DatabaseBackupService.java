package com.isnet.mgr.service;

import java.util.Map;

public interface DatabaseBackupService {

	/**
	 * 데이타베이스를 백업한 파일 목록을 조회합니다.
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getBakupFiles(Map<String, Object> paramMap);
	
	/**
	 * 데이타베이스를 백업합니다.
	 */
	public void backup();
	
	/**
	 * 지정된 날짜의 백업파일을 이용해서 데이타베이스를 복원합니다. 
	 * @param paramMap
	 * @return
	 */
	public boolean restore(Map<String, Object> paramMap);
}
