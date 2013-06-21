package com.isnet.mgr.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.isnet.mgr.service.DatabaseBackupService;

@Component
public class DatabaseBackup {
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private DatabaseBackupService databaseBackupService;
	
	               //초 분 시 일 월 주
	@Scheduled(cron="0 0 1 1/1 * ?")
	public void backup(){
		databaseBackupService.backup();
	}
}
