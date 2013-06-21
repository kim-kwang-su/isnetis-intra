package com.isnet.mgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isnet.mgr.dao.ManagerDao;

@Service
public class ManagerServiceImp implements ManagerService {
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	ManagerDao managerDao;
	

	@Override
	public Map<String, Object> getManagerList(Map<String, Object> paramMap) {
			logger.debug("["+getClass().getSimpleName()+"] [getManagerList] start");
			
			Map<String, Object> resultData = new HashMap<String, Object>();
			
			// TB_WorkSheet의  페이지번호에 해당하는 ROW 조회
			int page = (Integer)paramMap.get("PAGE");
			int rows = (Integer)paramMap.get("ROWS");
			int begin_index = (page - 1) * rows;
			
			paramMap.put("BEGIN_INDEX", begin_index);
			List<Map<String, Object>> list = null;

			// TB_WorkSheet의 전체 행 갯수 조회
			int total_rows = managerDao.select_manager_01(paramMap);
			logger.info("["+getClass().getSimpleName()+"] [getManagerList] total_rows ["+total_rows+"]");
			
			int total_pages = (int)(Math.ceil((double)total_rows/rows));
			logger.info("["+getClass().getSimpleName()+"] [getManagerList] total_pages ["+total_pages+"]");
			
			list = managerDao.select_manager_02(paramMap);
			logger.info("["+getClass().getSimpleName()+"] [getManagerList] rows ["+list+"]");
			
			int records = list.size();
			logger.info("["+getClass().getSimpleName()+"] [getManagerList] records ["+records+"]");
			
			resultData.put("page", page);
			resultData.put("records", records);	
			resultData.put("rows", list);
			resultData.put("total", total_pages);

			logger.debug("["+getClass().getSimpleName()+"] [getManagerList] end");
			
			return resultData;
		}

	@Override
	public boolean addManager(Map<String, Object> paramMap) {
		boolean isSuccess = false;
		
		List<Map<String, Object>> employeeList = managerDao.select_manager_03(paramMap);
			if(employeeList.size() == 0){
				managerDao.insert_manager_01(paramMap);
				isSuccess = true;
			}
		return isSuccess;
	}

	@Override
	public void updateManager(Map<String, Object> paramMap) {
		managerDao.update_manager_01(paramMap);
	}
}
