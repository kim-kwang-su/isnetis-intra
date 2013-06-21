package com.isnet.mgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isnet.mgr.dao.CustomerDao;
import com.isnet.mgr.dao.OptionDao;
import com.isnet.mgr.dao.ProductDao;
import com.isnet.mgr.domain.TempBean;
	
@Service
public class OptionServiceImp implements OptionService{
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired 
	OptionDao optionDao;
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	ProductDao productDao;

	@Override
	public List<Map<String, Object>> getOptionList(Map<String, Object> paramMap ) {
		List<Map<String, Object>> optionList = optionDao.select_option_file_03(paramMap);
		
		List<Map<String, Object>> commentList = optionDao.select_option_01();
		Map<Integer, Object> commentMap = new HashMap<Integer, Object>();
		for(Map<String, Object> map : commentList){
			commentMap.put((Integer)map.get("OPTION_NO"), map.get("COMMENT"));
		}
		for(Map<String, Object> option : optionList){
			if(commentMap.containsKey(option.get("OPTION_NO"))){
				option.put("COMMENT", commentMap.get(option.get("OPTION_NO")));
			}
		}
		return optionList;
	}

	@Override
	public List<Map<String, Object>> getOptionFileList(Map<String, Object> paramMap) {
		return  optionDao.select_option_file_01(paramMap);
	}

	@Override
	public Map<String, Object> update_option_01(Map<String, Object> paramMap) throws Exception{
		logger.debug("["+getClass().getSimpleName()+"] [update_option_01] start");
		
		Map<String, Object> resultMap = new HashMap <String, Object>();
		
		int result = optionDao.delete_option_01(paramMap);
		logger.info(" ["+getClass().getSimpleName()+"] [update_option_01] delete result = " + result);
		
		//example param = [{"name":"1","value":"0"},{"name":"2","value":""},{"name":"3","value":"11"},{"name":"4","value":"30"}]
		//JSON array 타입으로 넘어 옴으로  Parsing 작업이 필요하다.
		//Jackson 이용해서 데이터 파싱한다.
		
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory jFac = new JsonFactory();
		JsonParser jParser = jFac.createJsonParser((String)paramMap.get("PARAM"));
		
		jParser.nextToken();
		while(jParser.nextToken() == JsonToken.START_OBJECT) {
			TempBean tempBean = mapper.readValue(jParser, TempBean.class);
			
			Map<String, Object> tempMap = new HashMap<String, Object>();
			
			tempMap.put("CUSTOMER_NO", (Integer)paramMap.get("CUSTOMER_NO"));
			tempMap.put("PRODUCT_NO", (Integer)paramMap.get("PRODUCT_NO"));
			
			tempMap.put("VALUE1", tempBean.getName());
			logger.info("["+getClass().getSimpleName()+"] [update_option_01] VALUE1 = " + tempBean.getName());
			
			tempMap.put("VALUE2", tempBean.getValue() );
			logger.info("["+getClass().getSimpleName()+"] [update_option_01] VALUE2 = " +  tempBean.getValue());
			
			result = optionDao.insert_option_01(tempMap);
			logger.info("["+getClass().getSimpleName()+"] [update_option_01] insert result = " + result);
		}
		
		resultMap.put("result", 1);
		logger.debug("["+getClass().getSimpleName()+"] [update_option_01] end");
		
		return resultMap;
	}

	@Override
	public Map<String, Object> getFullOptionList(Map<String, Object> paramMap) {
		Map<String, Object> resultMap = new HashMap <String, Object>();
		
		//  페이지번호에 해당하는 ROW 조회
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		
		paramMap.put("BEGIN_INDEX", begin_index);
		List<Map<String, Object>> optionList = optionDao.select_option_02(paramMap);
		int total_rows = optionDao.select_option_03(paramMap);
		
		List<Map<String, Object>> commentList = optionDao.select_option_01();
		Map<Integer, Object> commentMap = new HashMap<Integer, Object>();
		for(Map<String, Object> map : commentList){
			commentMap.put((Integer)map.get("OPTION_NO"), map.get("COMMENT"));
		}
		
		for(Map<String, Object> option : optionList){
			if(commentMap.containsKey(option.get("OPTION_NO"))){
				option.put("COMMENT", commentMap.get(option.get("OPTION_NO")));
			}
		}
		
		logger.info("["+getClass().getSimpleName()+"] [getSecondOptionList] total_rows ["+total_rows+"]");
		
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getSecondOptionList] total_pages ["+total_pages+"]");
		
		resultMap.put("page", page);
		resultMap.put("records", optionList.size());	
		resultMap.put("rows", optionList);
		resultMap.put("total", total_pages);
		
		return resultMap;
		
	}

	/*@Override
	public Map<String, Object> getThirdOptionList(Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap <String, Object>();
		
		List<Map<String, Object>> productList = productDao.select_product_02(paramMap);
		List<Map<String, Object>> fileList = optionDao.select_option_03_01(paramMap);
		List<Map<String, Object>> appList = optionDao.select_option_03_02(paramMap);
		List<Map<String, Object>> optionList = optionDao.select_option_03_03(paramMap);
		List<Map<String, Object>> commentList = optionDao.select_option_03_04(paramMap);
		
		resultMap.put("products", productList);
		resultMap.put("files", fileList);
		resultMap.put("apps", appList);
		resultMap.put("options", optionList);
		
		resultMap.put("commentList", commentList);
		resultMap.put("commentListSize", commentList.size());
		
		return resultMap;
	}*/

	@Override
	public List<Map<String, Object>> select_option_03_01(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = optionDao.select_option_03_01(paramMap);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> select_option_03_02(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = optionDao.select_option_03_02(paramMap);
		return list;
	}
	@Override
	public List<Map<String, Object>> select_option_03_03(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = optionDao.select_option_03_03(paramMap);
		return list;
	}
	@Override
	public List<Map<String, Object>> select_option_03_04(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = optionDao.select_option_03_04(paramMap);
		return list;
	}

	@Override
	public int add_file(Map<String, Object> paramMap) {
		optionDao.add_file(paramMap);
		int result = optionDao.select_option_04_02();
		return result;
	}

	@Override
	public int add_app(Map<String, Object> paramMap) {
		optionDao.add_app(paramMap);
		int result = optionDao.select_option_04_03();
		return result;
	}
	
	@Override
	public int add_option(Map<String, Object> paramMap) {
		optionDao.add_option(paramMap);
		int result = optionDao.select_option_04_04();
		return result;

	}
	
	@Override
	public int add_comment(Map<String, Object> paramMap) {
		optionDao.add_comment(paramMap);
		int result = optionDao.select_option_04_05();
		return result;
	}

	@Override
	public int delete_comment(Map<String, Object> paramMap) {
		int result = optionDao.delete_comment(paramMap);
		return result;
	}

	@Override
	public List<Map<String, Object>> select_option_03_05() {
		List<Map<String, Object>> list = optionDao.select_option_03_05();
		return list;
	}

	@Override
	public Map<String, Object> getForthOptionList(Map<String, Object> paramMap) {
		Map<String, Object> resultMap = new HashMap <String, Object>();
		
		List<Map<String, Object>> list = optionDao.select_option_05_01(paramMap);
		resultMap.put("compareList", list);
		resultMap.put("size", list.size());
		
		return resultMap;
	}

	@Override
	public void update_file(Map<String, Object> paramMap) {
		optionDao.update_file(paramMap);
	}
	
	@Override
	public void update_app(Map<String, Object> paramMap) {
		optionDao.update_app(paramMap);
	}
	
	@Override
	public void update_option(Map<String, Object> paramMap) {
		optionDao.update_option(paramMap);
	}
	
	@Override
	public Map<String, Object> getOptionInfo(Map<String, Object> paramMap) {
		return optionDao.select_option_06(paramMap);
	}
}
