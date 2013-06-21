package com.isnet.mgr.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class OptionFileUtil {
	Logger logger = Logger.getLogger(getClass());

	//파일 경로 및 임시폴더명 파일명
	String fileName ="";
	String appName ="";

	public String createFile(String filePath, List<Map<String, Object>> resultList) throws Exception{
		
		logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] start");
	
		/*
		  첫번째 파일을 만들기 위해 필요한 것 (앞으로 비교해서 새로운 파일 작성여부 판가름)
		  1. 파일명
		  2. 앱이름(앱이 바뀔 때 마다 new Line 추가되어야 하기 때문에 필요
		  3. zip 파일 만들때 필요한 fileList 생성
		 */
		Map<String, Object> tempFirstMap = resultList.get(0);
		fileName = (String) tempFirstMap.get("OPTION_FILE_NAME");
		appName = (String) tempFirstMap.get("OPTION_APP_NAME");

		List<String> fileList = new ArrayList<String>();
		fileList.add(fileName);
		
		logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] fileName = " + fileName) ;
		logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] list_size : " + resultList.size());
		
		
		logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] 파일 폴더 생성");
		File dirPath = new File(filePath);
		dirPath.mkdir();
		
		logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] 파일경로명 : " + filePath +fileName);
		
		File file = new File(filePath , fileName);
		BufferedWriter output = new BufferedWriter(new FileWriter(file));
		
		logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] 1. 앱 이름 : " + "["+(String)tempFirstMap.get("OPTION_APP_NAME")+"]");
		output.write("["+(String)tempFirstMap.get("OPTION_APP_NAME")+"]");
		output.newLine();

		
		for(int i =0;i < resultList.size();i++) {
			Map<String, Object> tempMap = resultList.get(i);
			
			/* 
			   파일명이 같지 않으면 파일명과 앱명을 변수에 저장 ,  신규 파일 생성
			 */
			if(!fileName.equals((String)tempMap.get("OPTION_FILE_NAME"))) {
				logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] 0.5  파일 비교 " + fileName + " == "+ tempMap.get("OPTION_FILE_NAME") );
				
				if (output != null)
					try { output.close(); }catch(Exception ex) {}
				
				fileName = (String) tempMap.get("OPTION_FILE_NAME");
				appName = (String) tempMap.get("OPTION_APP_NAME");
				fileList.add(fileName);
				
				file = new File(filePath , fileName);
				output = new BufferedWriter(new FileWriter(file));
				
				logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] 1. 앱 이름 : " + "["+(String)tempMap.get("OPTION_APP_NAME")+"]");
				output.write("["+(String)tempMap.get("OPTION_APP_NAME")+"]");
				output.newLine();
			
			}
			/* 
			        앱명이 다르면 앱명을 변수에 저장 , 파일에 new Line 추가
			 */
			if(!appName.equals((String)tempMap.get("OPTION_APP_NAME"))) {
				
				logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] 1.5  앱이름 비교 " + appName + " == "+ tempMap.get("OPTION_APP_NAME") );					
				appName = (String)tempMap.get("OPTION_APP_NAME");

				output.newLine();
				
				logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] 2. 앱 이름 : " + "["+tempMap.get("OPTION_APP_NAME")+"]");
				output.write("["+(String)tempMap.get("OPTION_APP_NAME")+"]");
				output.newLine();
			}

			/* 주석 작성	 */
			if(tempMap.get("COMMENT")!= null && !tempMap.get("COMMENT").equals("null")) {
				
				logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] 3. 주석작성  : " + "//"+ tempMap.get("OPTION_COMMENT") + " ("+tempMap.get("COMMENT")+")");
				output.write("//"+ tempMap.get("OPTION_COMMENT") + " ("+tempMap.get("COMMENT")+")");
			    output.newLine();
			
			}else if(tempMap.get("OPTION_COMMENT") != null && !tempMap.get("OPTION_COMMENT").equals("null")) {
				
				logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] 4. 주석작성  : " + "//"+ tempMap.get("OPTION_COMMENT"));
				output.write("//"+ tempMap.get("OPTION_COMMENT"));
				output.newLine();
			
			}else {
				logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] 5. 주석작성  : " + "//");
				output.write("//");
				output.newLine();
			}
			
			/* 옵션 KEY - VALUE 값이 없는 경우 디폴트 값으로 대체 */
			if(tempMap.get("OPTION_VALUE")!= null){
				
				logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] 6. 옵션 KEY - VALUE  : " + tempMap.get("OPTION_KEY_NAME")+" = " +tempMap.get("OPTION_VALUE"));
				output.write(tempMap.get("OPTION_KEY_NAME")+" = " +tempMap.get("OPTION_VALUE") );
				output.newLine();
				
			}else {
				logger.info("["+OptionFileUtil.class.getSimpleName()+"] [createFile] 7. 옵션 KEY - VALUE  : " + tempMap.get("OPTION_KEY_NAME")+" = " +tempMap.get("OPTION_DEFAULT_VALUE"));
				output.write(tempMap.get("OPTION_KEY_NAME")+" = " +tempMap.get("OPTION_DEFAULT_VALUE") );
				output.newLine();
			}
			
		}
		if (output != null)
			try { output.close(); }catch(Exception ex) {}
			
		logger.debug("["+OptionFileUtil.class.getSimpleName()+"] [createFile] end");

		/*zip 파일 생성*/
		createZip(filePath, fileList);
		return filePath;

	}

	public void createZip(String filePath , List<String>fileList) throws Exception{
		
		logger.info("["+getClass().getSimpleName()+"] [createZip] start");
		logger.info("["+getClass().getSimpleName()+"] [createZip] 파일 경로 : " +  filePath );
		
		FileOutputStream fos = new FileOutputStream(new File(filePath+".zip"));
		ZipOutputStream zos = new ZipOutputStream(fos);
		FileInputStream fis = null;
		
		byte[] buffer = new byte[1024];
		
		ZipEntry ze = null;
		int c= 0;

		for(String fileName : fileList){
			
			logger.info("make zip File : " + filePath + fileName);
			fis = new FileInputStream(new File(filePath, fileName));
			ze = new ZipEntry(fileName);
			zos.putNextEntry(ze);
			while((c=fis.read(buffer,0,1024))!= -1){
				zos.write(buffer, 0, c);
			}
			if (fis != null)
				try { fis.close(); }catch(Exception ex) {}
		}
		if (zos != null)
			try { zos.close(); }catch(Exception ex) {}
		
		if (fos != null)
			try { fos.close(); }catch(Exception ex) {}

	}

}
