package com.isnet.mgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isnet.mgr.dao.SheetDao;


@Service
public class SheetServiceImp implements SheetService {
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private SheetDao sheetDao;
	
	@Override
	public Map<String, Object> getSheetList(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [getFirstList] start");
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		// TB_WorkSheet의  페이지번호에 해당하는 ROW 조회
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		
		paramMap.put("BEGIN_INDEX", begin_index);
		List<Map<String, Object>> list = null;

		// TB_WorkSheet의 전체 행 갯수 조회
		int total_rows = sheetDao.select_sheet_02(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getFirstList] total_rows ["+total_rows+"]");
		
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getFirstList] total_pages ["+total_pages+"]");
		
		list = sheetDao.select_sheet_01(paramMap);
		//logger.debug("["+getClass().getSimpleName()+"] [getFirstList] rows ["+list+"]");
		
		int records = list.size();
		logger.info("["+getClass().getSimpleName()+"] [getFirstList] records ["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", list);
		resultData.put("total", total_pages);

		logger.debug("["+getClass().getSimpleName()+"] [getFirstList] end");
		
		return resultData;
	}

	@Override
	public Map<String, Object> getSheetByDate(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [getDetailSheet] start");
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		List<Map<String, Object>> sheets = sheetDao.select_sheet_03(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getDetailSheet] sheet_no ["+sheets.size()+"]");

		List<Map<String, Object>> remote = sheetDao.select_sheet_08(paramMap);

		resultData.put("worksheet", sheets);
		resultData.put("remotesheet", remote);
		resultData.put("remote_size", remote.size());
		
		logger.debug("["+getClass().getSimpleName()+"] [getDetailSheet] end");
		
		return resultData;
	}
	@Override
	public Map<String, Object> getSheetByNo(Map<String, Object> paramMap) {
		
		Map<String, Object> sheet = sheetDao.select_sheet_04(paramMap);
		List<Map<String, Object>> supportList = sheetDao.select_sheet_05(paramMap);
		
		sheet.put("supportList", supportList);
		
		return sheet;
	}

	@Override
	public void addSheet(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [add_sheet01] start");
		
		//TB_WORK_SHEET
		sheetDao.add_sheet01(paramMap);		
		logger.info("["+getClass().getSimpleName()+"] [add_sheet02] sheet_no ["+paramMap.get("SHEET_NO")+"]");
		
		//TB_SUPPORT_TYPE_LIST
		String [] support_type = (String [])paramMap.get("SUPPORT_TYPE_NO");
		
		for(int i = 0; i < support_type.length; i++) {
			Map<String, Object> param= new HashMap<String, Object>();
			param.put("SHEET_NO", paramMap.get("SHEET_NO"));
			param.put("SUPPORT_TYPE_NO", support_type[i]);
			
			sheetDao.add_sheet03(param);
		}
		
		logger.debug("["+getClass().getSimpleName()+"] [add_sheet01] end");
	}

	@Override
	public void addRemoteSheet(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [add_sheet02] start");
		sheetDao.add_sheet02(paramMap);
		logger.debug("["+getClass().getSimpleName()+"] [add_sheet02] end");
	}

	@Override
	public void deleteSheet(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [del_Sheet01] start");
		
		sheetDao.del_Sheet02(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [del_Sheet01] 지원형태정보 "+paramMap.get("SHEET_NO")+"를 삭제 합니다.");
		sheetDao.del_Sheet01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [del_Sheet01] 업무일지 "+paramMap.get("SHEET_NO")+"를 삭제 합니다.");
		
		logger.debug("["+getClass().getSimpleName()+"] [del_Sheet01] end");
	}

	@Override
	public void deleteRemoteSheet(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [del_Remote01] start");
		
		sheetDao.del_Remote01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [del_Remote01] 업무일지 "+paramMap.get("REMOTE_NO")+"를 삭제 합니다.");
		
		logger.debug("["+getClass().getSimpleName()+"] [del_Remote01] end");
		
	}


	@Override
	public void updateSheet(Map<String, Object> paramMap) {
		
		// TB_SHEET 수정
		sheetDao.update_sheet01(paramMap);
		
		// TB_SHEET_SUPPORT_LIST 삭제
		sheetDao.del_Sheet02(paramMap);
		// TB_SHEET_SUPPORT_LIST 추가
		
		String [] support_type = (String [])paramMap.get("SUPPORT_TYPE_NO");
		
		for(int i = 0; i < support_type.length; i++) {
			Map<String, Object> param= new HashMap<String, Object>();
			param.put("SHEET_NO", paramMap.get("SHEET_NO"));
			param.put("SUPPORT_TYPE_NO", support_type[i]);
			
			sheetDao.add_sheet03(param);
		}
		
	}

	@Override
	public Map<String, Object> getRemoteSheet(Map<String, Object> paramMap) {
		return sheetDao.select_sheet_10(paramMap);
	}

	@Override
	public void updateRemoteSheet(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [update_sheet02] start");
		sheetDao.update_sheet02(paramMap);
		logger.debug("["+getClass().getSimpleName()+"] [update_sheet02] end");
	}

	@Override
	public Map<String, Object> getSheetListByDept(Map<String, Object> paramMap) {
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		List<Map<String, Object>> sheetList = sheetDao.select_sheet_11(paramMap);
		
		resultData.put("page", 1);
		resultData.put("records", sheetList.size());
		resultData.put("rows", sheetList);
		resultData.put("total", 1);
		
		return resultData;
	}

	@Override
	public List<Map<String, Object>> getMyWorkSheetList(Map<String, Object> paramMap) {
		return sheetDao.select_sheet_12(paramMap);
	}
}
