package com.isnet.mgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isnet.mgr.dao.CustomerDao;

@Service
public class CustomerServiceImp implements CustomerService {
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	CustomerDao customerDao;
	
	@Override
	public List<Map<String, Object>> getCustomerList() {
		List<Map<String, Object>> list = customerDao.select_customer_01();
		return list;
	}

	@Override
	public Map<String, Object> getCustomerList(Map<String, Object> paramMap) {
			logger.debug("["+getClass().getSimpleName()+"] [getCustomerList] start");
			
			Map<String, Object> resultData = new HashMap<String, Object>();
			
			// TB_WorkSheet의  페이지번호에 해당하는 ROW 조회
			int page = (Integer)paramMap.get("PAGE");
			int rows = (Integer)paramMap.get("ROWS");
			int begin_index = (page - 1) * rows;
			
			paramMap.put("BEGIN_INDEX", begin_index);
			List<Map<String, Object>> list = null;

			// TB_WorkSheet의 전체 행 갯수 조회
			int total_rows = customerDao.select_customer_02(paramMap);
			logger.info("["+getClass().getSimpleName()+"] [getCustomerList] total_rows ["+total_rows+"]");
			
			int total_pages = (int)(Math.ceil((double)total_rows/rows));
			logger.info("["+getClass().getSimpleName()+"] [getCustomerList] total_pages ["+total_pages+"]");
			
			list = customerDao.select_customer_03(paramMap);
			logger.info("["+getClass().getSimpleName()+"] [getCustomerList] rows ["+list+"]");
			
			int records = list.size();
			logger.info("["+getClass().getSimpleName()+"] [getCustomerList] records ["+records+"]");
			
			resultData.put("page", page);
			resultData.put("records", records);	
			resultData.put("rows", list);
			resultData.put("total", total_pages);

			logger.debug("["+getClass().getSimpleName()+"] [getCustomerList] end");
			
			return resultData;
		}

	@Override
	public boolean addCustomer(Map<String, Object> paramMap) {
		boolean isSuccess = false;
		
		List<Map<String, Object>> employeeList = customerDao.select_customer_04(paramMap);
			if(employeeList.size() == 0){
				customerDao.insert_customer_01(paramMap);
				isSuccess = true;
			}
		return isSuccess;
	}

	@Override
	public void updateCustomer(Map<String, Object> paramMap) {
		customerDao.update_customer_01(paramMap);
	}

	@Override
	public Map<String, Object> search_customer(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = customerDao.select_customer_05(paramMap);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list",list);
		
		return resultMap;
		
	}
	
	@Override
	public List<Map<String, Object>> getTitleCustomer() {
		List<Map<String, Object>> list = customerDao.select_customer_06();
		return list;
	}
}
