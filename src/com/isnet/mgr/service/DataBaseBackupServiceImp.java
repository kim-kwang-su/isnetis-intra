package com.isnet.mgr.service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.isnet.mgr.common.CommonConstant;
import com.isnet.mgr.common.DateUtil;

@Service
public class DataBaseBackupServiceImp implements DatabaseBackupService{

	Logger logger = Logger.getLogger(getClass());
	
	@Value("#{config['backup.database.name']}")
	private String databaseName;
	@Value("#{config['backup.database.username']}")
	private String userName;
	@Value("#{config['backup.database.password']}")
	private String userPassword;
	@Value("#{config['backup.database.savepath']}")
	private String savePath;
	
	@Override
	public Map<String, Object> getBakupFiles(Map<String, Object> paramMap) {
		logger.info("["+getClass().getSimpleName()+"] [getBackupFiles] start"); 

		File directory = new File(this.savePath);
		File[] listFiles = directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				File file = new File(dir, name);
				// 파일이면서 확장자가 .sql로 끝나면 파일만 가져온다.
				if(file.isFile()){
					if(name.endsWith(".sql")){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}
		});
		ArrayUtils.reverse(listFiles);	
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		int end_index = page * rows;
		
		
		int total_rows = listFiles.length;
		logger.info("["+getClass().getSimpleName()+"] [getBackupFiles] total_rows["+total_rows+"]");
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getBackupFiles] total_pages["+total_pages+"]");
		
		if(end_index > total_rows){
			end_index = total_rows;
		}
		logger.info("["+getClass().getSimpleName()+"] [getBackupFiles] begin_index["+begin_index+"]");
		logger.info("["+getClass().getSimpleName()+"] [getBackupFiles] end_indx["+end_index+"]");
		
		
		String[] backupfiles = new String[end_index - begin_index];
		int index = 0;
		for(int i=begin_index; i<end_index; i++){
			backupfiles[index++] = listFiles[i].getName();
		}
		logger.info("["+getClass().getSimpleName()+"] [getBackupFiles] backupfiles["+ArrayUtils.toString(backupfiles)+"]");
		
		List<Map<String, Object>> records= new ArrayList<Map<String,Object>>();
		for(String filename : backupfiles){
			String date = filename.substring(filename.indexOf("_") + 1, filename.indexOf("."));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("DATE_NO", date);
			map.put("BACKUP_DATE", date);			
			map.put("FILE_NAME", filename);
			
			records.add(map);
		}		
		
		resultData.put("page", page);
		resultData.put("records", records.size());	
		resultData.put("rows", records);
		resultData.put("total", total_pages);
		
		logger.info("["+getClass().getSimpleName()+"] [getBackupFiles] end");
		return resultData;
	}
	
	@Override
	public void backup() {
		logger.info("["+getClass().getSimpleName()+"] [backup] ["+DateUtil.dateToString(CommonConstant.DATE_FORMAT_05)+"] start");
		
		String currentTime = DateUtil.dateToString("yyyyMMddHHmm");
		String backupFileName = databaseName+ "_" + currentTime + ".sql";
		// 서버관련 버젼으로 변경
		String executeCmd = "mysqldump.exe  -u "+ userName + " -p" + userPassword + " " + databaseName  + " -r " + savePath + backupFileName;
		logger.info("mysql cmd["+executeCmd+"]");
		Process runtimeProcess = null;
		try{
			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();
			if(processComplete == 0){
				logger.info("["+getClass().getSimpleName()+"] " + databaseName + " 백업 완료");
			}else{
				logger.info("["+getClass().getSimpleName()+"] " + databaseName + " 백업 오류");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [backup] " + databaseName + "백업 오류");
		}finally{
			logger.info("["+getClass().getSimpleName()+"] [backup] ["+DateUtil.dateToString(CommonConstant.DATE_FORMAT_05)+"] end");
		}
	}
	
	@Override
	public boolean restore(Map<String, Object> paramMap) {
		logger.info("["+getClass().getSimpleName()+"] [restore] start");
		
		String date_no = (String)paramMap.get("DATE_NO");
		logger.info("["+getClass().getSimpleName()+"] [restore] date_no["+date_no+"]");
		String backupFileName = databaseName + "_" + date_no + ".sql";
		
		Process runtimeProcess = null;
		try{
			File file = new File(new File(savePath),  backupFileName);
			if(file.exists()){
				// 서버 관련 버젼으로 변경
				String cmd[] = {"cmd.exe", "/c", "mysql -u "+userName+" -p"+userPassword+" "+databaseName+" < " + file.getAbsolutePath()};
				runtimeProcess = Runtime.getRuntime().exec(cmd);
				int processComplete = runtimeProcess.waitFor();
				if(processComplete == 0){
					logger.info("["+getClass().getSimpleName()+"] [restore] date_no["+date_no+"] 복원완료.");
					return true;
				}else{
					logger.error("["+getClass().getSimpleName()+"] [restore] date_no["+date_no+"] 복원실패.");
					return false;
				}
			}else{
				logger.error("["+getClass().getSimpleName()+"] [restore] date_no["+date_no+"] 백업파일이 존재하지 않습니다.");
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [restore] date_no["+date_no+"] 데이타베이스 복원 오류");
			return false;
		}finally{
			logger.info("["+getClass().getSimpleName()+"] [restore] end");
		}
		
	}
}
