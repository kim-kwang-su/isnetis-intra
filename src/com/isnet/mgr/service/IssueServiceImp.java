package com.isnet.mgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isnet.mgr.common.CommonConstant;
import com.isnet.mgr.dao.IssueDao;

@Service
public class IssueServiceImp implements IssueService{

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private IssueDao issueDao;
	
	@Override
	public Map<String, Object> getRecentIssueList(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [getRecentIssueList] start");
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		// TB_MODULE에서 페이지번호에 해당하는 ROW 조회
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		
		paramMap.put("BEGIN_INDEX", begin_index);
		
		//boolean _search = (Boolean)map.get("_SEARCH");
		//String search_field = (String)map.get("SEARCH_FIELD");

		//if(_search && search_field.equals("UPDATE_MEMO")){
			
		//}else{
		//}
		
		// TB_ISSUE에서 조건에 만족하는 전체 행 갯수 조회
		int total_rows = issueDao.select_issue_01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getRecentIssueList] total_rows ["+total_rows+"]");
		
		// 전체 페이지 갯수를 계산
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getRecentIssueList] total_pages ["+total_pages+"]");
		
		// 이슈 접수내역을 조회
		List<Map<String, Object>> issueList = issueDao.select_issue_02(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getRecentIssueList] issueList.size ["+issueList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getRecentIssueList] rows ["+issueList+"]");
		
		/*for(Map<String, Object> map : issueList){
			List<Map<String, Object>> developers = issueDao.select_issue_04(map);
			String str = "";
			for(Map<String, Object> developer : developers){
				String is_se = (String)developer.get("IS_SE");
				if(is_se.equals("Y")){
					map.put("SE_NO", developer.get("EMPLOYEE_NO"));
					map.put("SE_NAME", developer.get("EMPLOYEE_NAME"));
				}else{
					str += developer.get("EMPLOYEE_NAME")+",";
				}
			}
			if(!str.isEmpty()){
				map.put("DEVELOPER_NAME", str.substring(0, str.length()-1));
			}
		}*/
		
		int records = issueList.size();
		logger.info("["+getClass().getSimpleName()+"] [getRecentIssueList] records ["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", issueList);
		resultData.put("total", total_pages);

		logger.debug("["+getClass().getSimpleName()+"] [getRecentIssueList] end");
		
		return resultData;
	}
	
	@Override
	public Map<String, Object> getIssueInfo(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [getIssueInfo] start");
		logger.info("issue_no ["+(Integer)(paramMap.get("ISSUE_NO"))+"]");
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		// 이슈 상세정보 조회
		Map<String, Object> issueInfo = issueDao.select_issue_03(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getIssueInfo] issueInfo.size ["+issueInfo.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getIssueInfo] issueInfo ["+issueInfo+"]");
		
		// 이슈의 첨부파일 조회
		List<Map<String, Object>> fileList = issueDao.select_issue_08(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getIssueInfo] fileList.size ["+fileList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getIssueInfo] fileList ["+fileList+"]");
		
		// 해당 이슈의 담당개발자 조회
		paramMap.put("IS_SE", "N");
		List<Map<String, Object>> developerList = issueDao.select_issue_04(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getIssueInfo] developerList.size ["+developerList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getIssueInfo] developerList ["+developerList+"]");
		
		// 해당 이슈의 SE 조회
		paramMap.put("IS_SE", "Y");
		List<Map<String, Object>> seList = issueDao.select_issue_04(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getIssueInfo] seList.size ["+developerList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getIssueInfo] seList ["+developerList+"]");
		
		resultData.put("issueInfo", issueInfo);
		resultData.put("fileList", fileList);
		resultData.put("developerList", developerList);
		resultData.put("seList", seList);
		
		logger.debug("["+getClass().getSimpleName()+"] [getIssueInfo] end");
		
		return resultData;
	}
	
	@Override
	public Map<String, Object> getIssueProgressList(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [getIssueProgressList] start");
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		// 해당 이슈의 진행이력을 조회합니다.
		List<Map<String, Object>> progressList = issueDao.select_issue_05(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getIssueProgressList] progressList.size ["+progressList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getIssueProgressList] progressList ["+progressList+"]");
		
		resultData.put("page", 1);
		resultData.put("records", progressList.size());	
		resultData.put("rows", progressList);
		resultData.put("total", 1);
		
		logger.debug("["+getClass().getSimpleName()+"] [getIssueProgressList] end");
		
		return resultData;
	}
	
	@Override
	public List<Map<String, Object>> getIssueFiles(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [getIssueFiles] start");
		
		List<Map<String, Object>> fileList = issueDao.select_issue_08(paramMap);
		
		logger.debug("["+getClass().getSimpleName()+"] [getIssueFiles] end");
		
		return fileList;
	}
	
	@Override
	public Map<String, Object> getStateIssueList(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [getStateIssueList] start");
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		
		paramMap.put("BEGIN_INDEX", begin_index);
		
		// TB_ISSUE에서 조건에 만족하는 전체 행 갯수 조회
		int total_rows = issueDao.select_issue_09(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getStateIssueList] total_rows ["+total_rows+"]");
		
		// 전체 페이지 갯수를 계산
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getStateIssueList] total_pages ["+total_pages+"]");
		
		// 이슈 접수내역을 조회
		List<Map<String, Object>> issueList = issueDao.select_issue_10(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getStateIssueList] issueList.size ["+issueList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getStateIssueList] rows ["+issueList+"]");
		
		int records = issueList.size();
		logger.info("["+getClass().getSimpleName()+"] [getStateIssueList] records ["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", issueList);
		resultData.put("total", total_pages);
	
		logger.debug("["+getClass().getSimpleName()+"] [getStateIssueList] end");
		
		return resultData;
	}

	@Override
	public Map<String, Object> getQaRequestList(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [getQaRequestList] start");
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		
		paramMap.put("BEGIN_INDEX", begin_index);
		
		// TB_ISSUE에서 조건에 만족하는 전체 행 갯수 조회
		int total_rows = issueDao.select_issue_11(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getQaRequestList] total_rows ["+total_rows+"]");
		
		// 전체 페이지 갯수를 계산
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getQaRequestList] total_pages ["+total_pages+"]");
		
		// 이슈 접수내역을 조회
		List<Map<String, Object>> requestList = issueDao.select_issue_12(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getQaRequestList] requestList.size ["+requestList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [getQaRequestList] rows ["+requestList+"]");
		
		int records = requestList.size();
		logger.info("["+getClass().getSimpleName()+"] [getQaRequestList] records ["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", requestList);
		resultData.put("total", total_pages);
	
		logger.debug("["+getClass().getSimpleName()+"] [getQaRequestList] end");
		
		return resultData;
	}
	
	@Override
	public Map<String, Object> getRequestInfo(Map<String, Object> paramMap) {
		return issueDao.select_issue_13(paramMap);
	}
	
	@Override
	public Map<String, Object> getBugReport(Map<String, Object> paramMap) {
		return issueDao.select_issue_14(paramMap);
	}
	
	@Override
	public void insert_developer(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [insert_developer] start");
		int issue_no = (Integer)paramMap.get("ISSUE_NO");
		
		// 해당 이슈의 담당개발자를 삭제합니다.
		paramMap.put("IS_SE", "N");
		issueDao.delete_issue_01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [insert_developer] 이슈번호["+issue_no+"]의 담당개발자를 삭제하였습니다.");

		// 해당 이슈의 담당개발자를 새로 등록합니다.
		int[] employee_no_array = (int[])paramMap.get("EMPLOYEE_NO_ARRAY");
		for(int employee_no : employee_no_array){
			paramMap.put("EMPLOYEE_NO", employee_no);
			issueDao.insert_issue_01(paramMap);
			logger.info("["+getClass().getSimpleName()+"] [insert_developer] 이슈번호["+issue_no+"]의 담당개발자로 사원["+employee_no+"]을 등록하였습니다.");
		}
		// 해당 이슈의 진행상태를 담당자지정 상태로 변경합니다.
		int state_no = (Integer)paramMap.get("STATE_NO");
		if(state_no == CommonConstant.ISSUE_STATE_NO_01){
			paramMap.put("STATE_NO", CommonConstant.ISSUE_STATE_NO_02);
			issueDao.update_issue_01(paramMap);	
			logger.info("["+getClass().getSimpleName()+"] [insert_developer] 이슈번호["+issue_no+"]의 진행상태를 담당자지정으로 변경하였습니다.");
		}
		logger.debug("["+getClass().getSimpleName()+"] [insert_developer] end");
	}
	
	@Override
	public int add_issue(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [add_issue] start");
		
		// TB_ISSUE에서 INDEX값을 조회합니다.
		Map<String, Object> table_status = issueDao.select_issue_00();
		int issue_no = Integer.parseInt(String.valueOf(table_status.get("Auto_increment")));
		logger.info("["+getClass().getSimpleName()+"] [add_issue] 새로운 issue_no["+issue_no+"]");
		
		// 이슈 정보를 TB_ISSUE에 저장합니다.
		paramMap.put("ISSUE_NO", issue_no);
		paramMap.put("STATE_NO", CommonConstant.ISSUE_STATE_NO_01);
		issueDao.insert_issue_00(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [add_issue] issue_no["+issue_no+"]의 이슈정보를 저장하였습니다.");
		
		
		// 이슈 담당 개발자 정보를 TB_DEVELOPER에 저장합니다.
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ISSUE_NO", issue_no);
		param.put("EMPLOYEE_NO", paramMap.get("DEVELOPER_NO"));
		param.put("IS_SE", "N");
		
		issueDao.insert_issue_01(param);
		logger.debug("["+getClass().getSimpleName()+"] [add_issue] issue_no["+issue_no+"] employee_no["+paramMap.get("DEVELOPER_NO")+"] 저장");
		/*if(paramMap.containsKey("DEVELOPER_NO")){
			int[] developers = (int[])paramMap.get("DEVELOPER_NO");
			for(int develoer_no : developers){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("ISSUE_NO", issue_no);
				param.put("EMPLOYEE_NO", develoer_no);
				param.put("IS_SE", "N");
				
				issueDao.insert_issue_01(param);
				logger.debug("["+getClass().getSimpleName()+"] [add_issue] issue_no["+issue_no+"] employee_no["+develoer_no+"] 저장");
			}
			logger.info("["+getClass().getSimpleName()+"] [add_issue] issue_no["+issue_no+"]의 개발담당자를 저장하였습니다.");
			
			paramMap.put("STATE_NO", CommonConstant.ISSUE_STATE_NO_02);
			issueDao.update_issue_01(paramMap);
			logger.info("["+getClass().getSimpleName()+"] [add_issue] issue_no["+issue_no+"] state_no["+CommonConstant.ISSUE_STATE_NO_02+"]로 변경");
		}*/
		
		// SE를 저장합니다.
		param = new HashMap<String, Object>();
		param.put("ISSUE_NO", issue_no);
		param.put("EMPLOYEE_NO", paramMap.get("SE_NO"));
		param.put("IS_SE", "Y");
		issueDao.insert_issue_01(param);
		
		logger.debug("["+getClass().getSimpleName()+"] [add_issue] end");
		
		return issue_no;
	}
	
	@Override
	public void add_issue_history(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [issue_history] start");
		
		// 이슈의 진행 상태를 변경합니다.
		issueDao.update_issue_01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [add_issue_history] issune_no["+paramMap.get("ISSUE_NO")+"]의 진행상태 state_no["+paramMap.get("STATE_NO")+"] 변경하였습니다.");
		
		// 이슈의 진행상황을 등록합니다.
		issueDao.insert_issue_02(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [add_issue_history] issune_no["+paramMap.get("ISSUE_NO")+"]의 진행상황 추가하였습니다.");
		
		logger.debug("["+getClass().getSimpleName()+"] [issue_history] end");
	}
	
	@Override
	public void add_qa_request(Map<String, Object> paramMap) {		
		issueDao.insert_issue_04(paramMap);
	}
	
	@Override
	public void update_qa_request(Map<String, Object> paramMap) {
		issueDao.update_issue_02(paramMap);
	}
	
	@Override
	public void add_bug_report(Map<String, Object> paramMap) {
		// 버그 리포트를 등록합니다.
		issueDao.insert_issue_05(paramMap);
		
		// 버그 리포트 등록여부를 갱신합니다.
		paramMap.put("IS_REPORTED", "Y");
		issueDao.update_issue_02(paramMap);
	}

	@Override
	public void add_file(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [add_file] start");
		
		issueDao.insert_issue_03(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [add_file] 파일["+paramMap.get("FILE_NAME")+"]을 저장하였습니다.");
		
		logger.debug("["+getClass().getSimpleName()+"] [add_file] end");
	}
	
	@Override
	public void update_issue(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [update_issue] start");
		
		issueDao.update_issue_01(paramMap);
		if(paramMap.containsKey("SE_NO")){
			paramMap.put("IS_SE", "Y");
			issueDao.delete_issue_01(paramMap);
			
			paramMap.put("EMPLOYEE_NO", paramMap.get("SE_NO"));
			issueDao.insert_issue_01(paramMap);
		}
		
		logger.debug("["+getClass().getSimpleName()+"] [update_issue] start");
	}
}
