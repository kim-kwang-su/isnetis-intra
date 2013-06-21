package com.isnet.mgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isnet.mgr.dao.ModuleDao;

@Service
public class ModuleServiceImp implements ModuleService{

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ModuleDao moduleDao;
	
	@Override
	public Map<String, Object> getRecentModuleList(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [getRecentModuleList] start");
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		// TB_MODULE에서 페이지번호에 해당하는 ROW 조회
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		
		paramMap.put("BEGIN_INDEX", begin_index);
		
		//boolean _search = (Boolean)map.get("_SEARCH");
		//String search_field = (String)map.get("SEARCH_FIELD");

		List<Map<String, Object>> list = null;
		//if(_search && search_field.equals("UPDATE_MEMO")){
			
		//}else{
		//}
		
		// TB_MODULE의 전체 행 갯수 조회
		int total_rows = moduleDao.select_module_01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getRecentModuleList] total_rows ["+total_rows+"]");
		
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getRecentModuleList] total_pages ["+total_pages+"]");
		
		list = moduleDao.select_module_02(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getRecentModuleList] rows ["+list+"]");
		
		int records = list.size();
		logger.info("["+getClass().getSimpleName()+"] [getRecentModuleList] records ["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", list);
		resultData.put("total", total_pages);

		logger.debug("["+getClass().getSimpleName()+"] [getRecentModuleList] end");
		
		return resultData;
	}
	
	@Override
	public Map<String, Object> getModuleList(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [getModuleList] start");

		Map<String, Object> resultData = new HashMap<String, Object>();
		
		// TB_MODULE에서 페이지번호에 해당하는 ROW 조회
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		
		paramMap.put("BEGIN_INDEX", begin_index);
		
		// TB_MODULE의 전체 행 갯수 조회
		int total_rows = moduleDao.select_module_14(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getModuleList] total_rows ["+total_rows+"]");
		
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getModuleList] total_pages ["+total_pages+"]");
		
		List<Map<String, Object>> list = moduleDao.select_module_15(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getModuleList] rows ["+list+"]");
		
		int records = list.size();
		logger.info("["+getClass().getSimpleName()+"] [getModuleList] records ["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", list);
		resultData.put("total", total_pages);

		logger.debug("["+getClass().getSimpleName()+"] [getModuleList] end");
		
		return resultData;
	}
	
	@Override
	public Map<String, Object> getModuleHistory(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [getModuleHistory] start");
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		List<Map<String, Object>> historyList = moduleDao.select_module_05(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getModuleHistory] historyList.size ["+historyList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getModuleHistory] historyList ["+historyList+"]");
		
		for(Map<String, Object> map : historyList){
			if(map.get("X86_RELEASE") != null && !map.get("X86_RELEASE").equals("")) {
				map.put("X86_RELEASE_LINK", "<a href='down.isnet?mhn="+map.get("MODULE_HISTORY_NO")+ "&module_gubun=x86_release'>다운로드</a>");
			}
			
			if(map.get("X86_DEBUG") != null && !map.get("X86_DEBUG").equals("")) {
				map.put("X86_DEBUG_LINK", "<a href='down.isnet?mhn="+map.get("MODULE_HISTORY_NO")+ "&module_gubun=x86_debug'>다운로드</a>");
			}
			
			if(map.get("X64_RELEASE") != null && !map.get("X64_RELEASE").equals("")) {
				map.put("X64_RELEASE_LINK", "<a href='down.isnet?mhn="+map.get("MODULE_HISTORY_NO")+ "&module_gubun=x64_release'>다운로드</a>");
			}
			
			if(map.get("X64_DEBUG") != null && !map.get("X64_DEBUG").equals("")) {
				map.put("X64_DEBUG_LINK", "<a href='down.isnet?mhn="+map.get("MODULE_HISTORY_NO")+ "&module_gubun=x64_debug'>다운로드</a>");
			}
			
			String customer_site = "<a href='javascript:getCustomerSite("+String.valueOf(map.get("MODULE_HISTORY_NO"))+")'>수정</a>";
			map.put("CUSTOMERS", customer_site);
			
		}
		
		resultData.put("page", 1);
		resultData.put("recores", historyList.size());
		resultData.put("rows", historyList);
		resultData.put("total", 1);
		
		logger.debug("["+getClass().getSimpleName()+"] [getModuleHistory] end");
		return resultData;
	}
	
	@Override
	public Map<String, Object> getModuleInfo(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [getModuleInfo] start");
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		// 모듈의 상세정보를 조회합니다.
		Map<String, Object> moduleInfo = moduleDao.select_module_03(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getModuleInfo] moduleInfo.size ["+moduleInfo.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getModuleInfo] moduleInfo ["+moduleInfo+"]");
		
		// 모듈이 적용된 고객사 정보를 조회합니다.
		List<Map<String, Object>> appliedCustomerList =  moduleDao.select_module_04(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getModuleInfo] appliedCustomerList.size ["+appliedCustomerList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getModuleInfo] appliedCustomerList ["+appliedCustomerList+"]");
		
		// 태그 정보를 조회합니다.
		List<Map<String, Object>> tagList = moduleDao.select_module_06(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getModuleInfo] tagList.size ["+tagList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getModuleInfo] tagList ["+tagList+"]");
		
		//List<Map<String, Object>> customerList = moduleDao.select_module_07();
		//logger.info("["+getClass().getSimpleName()+"] [getModuleInfo] customerList.size ["+customerList.size()+"]");
		//logger.debug("["+getClass().getSimpleName()+"] [getModuleInfo] customerList ["+customerList+"]");
		
		
		resultData.put("moduleInfo", moduleInfo);
		resultData.put("appliedCustomerList", appliedCustomerList);
		//resultData.put("customerList", customerList);
		resultData.put("tagList", tagList);
		
		logger.debug("["+getClass().getSimpleName()+"] [getModuleInof] end");
		
		return resultData;
	}
	
	@Override
	public void addModuleHistory(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [addModuleHistory] start");
		
		moduleDao.insert_module_02(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [addModuleHistory] module_no["+paramMap.get("MODULE_NO")+"]의 모듈이력 정보 저장");
		
		int [] customers = (int [])paramMap.get("CUSTOMERS");
		for (int i = 0; i < customers.length;i++) {
			paramMap.put("CUSTOMER_NO", customers[i]);
			moduleDao.insert_module_03(paramMap);
		}
		logger.info("["+getClass().getSimpleName()+"] [addModuleHistory] module_no["+paramMap.get("MODULE_NO")+"]의 적용사이트 정보 저장");
		
		logger.debug("["+getClass().getSimpleName()+"] [addModuleHistory] end");
		
	}
	
	@Override
	public void addModule(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [addModule] start");
		
		moduleDao.insert_module_01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [addModuleHistory] module_no["+paramMap.get("MODULE_NO")+"]의 신규 정보 저장");
		
		addModuleHistory(paramMap);	
		logger.debug("["+getClass().getSimpleName()+"] [addModuleHistory] module_no["+paramMap.get("MODULE_NO")+"]의 신규 모듈이력정보 저장");
		
		moduleDao.update_module_01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [addModuleHistory] module_no["+paramMap.get("MODULE_NO")+"]의 모듈이력변호 변경 저장");
		
		logger.debug("["+getClass().getSimpleName()+"] [addModule] end");
	}
	
	@Override
	public Map<String, Object> getModuleHistoryInfo(Map<String, Object> paramMap) {
		return moduleDao.select_module_07(paramMap);
	}
	
	@Override
	public Map<String, Object> getProductLastVersionList(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [getProductLastVersionList] start");
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		// TB_MODULE에서 페이지번호에 해당하는 ROW 조회
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		
		paramMap.put("BEGIN_INDEX", begin_index);
		
		int total_rows = moduleDao.select_module_10(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getProductLastVersionList] total_rows ["+total_rows+"]");
		// 전체 페이지 갯수를 계산
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getProductLastVersionList] total_pages ["+total_pages+"]");
		
		
		List<Map<String, Object>> moduleList = moduleDao.select_module_09(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getProductLastVersionList] moduleList.size ["+moduleList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getProductLastVersionList] moduleList ["+moduleList+"]");
		for(Map<String, Object> map : moduleList){

			if(map.get("X86_RELEASE") != null && !map.get("X86_RELEASE").equals("")) {
				map.put("X86_RELEASE", "<a href='down.isnet?mhn="+map.get("MODULE_HISTORY_NO")+ "&module_gubun=x86_release'>다운로드</a>");
			}
			
			if(map.get("X86_DEBUG") != null && !map.get("X86_DEBUG").equals("")) {
				map.put("X86_DEBUG", "<a href='down.isnet?mhn="+map.get("MODULE_HISTORY_NO")+ "&module_gubun=x86_debug'>다운로드</a>");
			}
			
			if(map.get("X64_RELEASE") != null && !map.get("X64_RELEASE").equals("")) {
				map.put("X64_RELEASE", "<a href='down.isnet?mhn="+map.get("MODULE_HISTORY_NO")+ "&module_gubun=x64_release'>다운로드</a>");
			}
			
			if(map.get("X64_DEBUG") != null && !map.get("X64_DEBUG").equals("")) {
				map.put("X64_DEBUG", "<a href='down.isnet?mhn="+map.get("MODULE_HISTORY_NO")+ "&module_gubun=x64_debug'>다운로드</a>");
			}
		}
		
		
		int records = moduleList.size();
		logger.info("["+getClass().getSimpleName()+"] [getProductLastVersionList] records ["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", moduleList);
		resultData.put("total", total_pages);	
		
		logger.debug("["+getClass().getSimpleName()+"] [getProductLastVersionList] end");
		return resultData;
	}
	
	@Override
	public Map<String, Object> getCustomerLastVersionList(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [getCustomerLastVersionList] start");
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		// TB_MODULE에서 페이지번호에 해당하는 ROW 조회
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		
		paramMap.put("BEGIN_INDEX", begin_index);
		
		int total_rows = moduleDao.select_module_12(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getCustomerLastVersionList] total_rows ["+total_rows+"]");
		// 전체 페이지 갯수를 계산
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getCustomerLastVersionList] total_pages ["+total_pages+"]");
		
		
		List<Map<String, Object>> moduleList = moduleDao.select_module_11(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getCustomerLastVersionList] moduleList.size ["+moduleList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getCustomerLastVersionList] moduleList ["+moduleList+"]");
		for(Map<String, Object> map : moduleList){

			if(map.get("X86_RELEASE") != null && !map.get("X86_RELEASE").equals("")) {
				map.put("X86_RELEASE", "<a href='down.isnet?mhn="+map.get("MODULE_HISTORY_NO")+ "&module_gubun=x86_release'>다운로드</a>");
			}
			
			if(map.get("X86_DEBUG") != null && !map.get("X86_DEBUG").equals("")) {
				map.put("X86_DEBUG", "<a href='down.isnet?mhn="+map.get("MODULE_HISTORY_NO")+ "&module_gubun=x86_debug'>다운로드</a>");
			}
			
			if(map.get("X64_RELEASE") != null && !map.get("X64_RELEASE").equals("")) {
				map.put("X64_RELEASE", "<a href='down.isnet?mhn="+map.get("MODULE_HISTORY_NO")+ "&module_gubun=x64_release'>다운로드</a>");
			}
			
			if(map.get("X64_DEBUG") != null && !map.get("X64_DEBUG").equals("")) {
				map.put("X64_DEBUG", "<a href='down.isnet?mhn="+map.get("MODULE_HISTORY_NO")+ "&module_gubun=x64_debug'>다운로드</a>");
			}
		
		}
		
		
		int records = moduleList.size();
		logger.info("["+getClass().getSimpleName()+"] [getCustomerLastVersionList] records ["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", moduleList);
		resultData.put("total", total_pages);	
		
		logger.debug("["+getClass().getSimpleName()+"] [getCustomerLastVersionList] end");
		return resultData;
	}

	@Override
	public void updateLastModuleHistoryNo(Map<String, Object> paramMap) {
		moduleDao.update_module_01(paramMap);
	}

	@Override
	public void addCustomerApplyModule(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [addCustomerApplyModule] start");
		
		moduleDao.delete_module_01(paramMap);
		
		int [] customers = (int [])paramMap.get("CUSTOMERS");
		for (int i = 0; i < customers.length;i++) {
			paramMap.put("CUSTOMER_NO", customers[i]);
			moduleDao.insert_module_03(paramMap);
		}
		logger.debug("["+getClass().getSimpleName()+"] [addCustomerApplyModule] end");
	}

	@Override
	public Map<String, Object> getCustomerApple(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [getCustomerApple] start");
		Map<String, Object> resultData = new HashMap<String, Object>();
		List<Map<String, Object>> list= moduleDao.select_module_13(paramMap);
		resultData.put("titleCustomerList", list);
		resultData.put("size", list.size());
		
		logger.debug("["+getClass().getSimpleName()+"] [getCustomerApple] end");
		
		return resultData;
	}
	
	@Override
	public void updateModuleInfo(Map<String, Object> paramMap) {
		moduleDao.update_module_02(paramMap);
	}
	
	@Override
	public void updateModuleHistoryInfo(Map<String, Object> paramMap) {
		moduleDao.update_module_03(paramMap);
	}
}
