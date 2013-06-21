package com.isnet.mgr.common;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.isnet.mgr.dao.CustomerDao;
import com.isnet.mgr.dao.DeptDao;
import com.isnet.mgr.dao.EmployeeDao;
import com.isnet.mgr.dao.IssueDao;
import com.isnet.mgr.dao.ModuleDao;
import com.isnet.mgr.dao.ProductDao;
import com.isnet.mgr.dao.SheetDao;

/**
 * 공통코드 정보를 저장합니다.
 * @author biz
 *
 */
public class CommonCodeBean {

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private IssueDao issueDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SheetDao sheetDao;
	@Autowired
	private ModuleDao moduleDao;
	@Autowired
	private DeptDao deptDao;
	
	private Map<String, Object> commonCodeMap = new HashMap<String, Object>();
	
	private List<Map<String, Object>> customerList;
	private List<Map<String, Object>> productList;
	private List<Map<String, Object>> stateList;
	private List<Map<String, Object>> employeeList;
	private List<Map<String, Object>> kindList;
	private List<Map<String, Object>> supportTypeList;
	private List<Map<String, Object>> filePathList;
	private List<Map<String, Object>> deptList;
	private List<Map<String, Object>> titleCustomerList;
	
	public void init(){
		
		logger.debug("["+getClass().getSimpleName()+"] [init] start");		
		
		// 고객사 목록을 조회합니다.
		customerList = customerDao.select_customer_01();
		logger.info("["+getClass().getSimpleName()+"] [init] customer size["+customerList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [init] customer data["+customerList+"]");
		
		
		//가나다순 고객사 목록  조회합니다.
		titleCustomerList = customerDao.select_customer_06();
		logger.info("["+getClass().getSimpleName()+"] [init] customer size["+titleCustomerList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [init] customer data["+titleCustomerList+"]");
		
		
		// 제품구분 코드를 조회합니다.
		productList = productDao.select_product_01();
		logger.info("["+getClass().getSimpleName()+"] [init] productList size["+productList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [init] productList data["+productList+"]");
		
		// 진행상태 코드를 조회합니다.
		stateList = issueDao.select_issue_06();
		logger.info("["+getClass().getSimpleName()+"] [init] stateList size["+stateList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [init] stateList data["+stateList+"]");
		
		// 사원정보를 조회합니다.
		employeeList = employeeDao.select_employee_01(new HashMap<String, Object>());
		logger.info("["+getClass().getSimpleName()+"] [init] employeeList size["+employeeList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [init] employeeList data["+employeeList+"]");
		
		// 장애분류 정보를 조회합니다.
		kindList = issueDao.select_issue_07();
		logger.info("["+getClass().getSimpleName()+"] [init] kindList size["+kindList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [init] kindList data["+kindList+"]");
		
		// 지원분류 정보를 조회합니다.
		supportTypeList = sheetDao.select_support_type();
		logger.info("["+getClass().getSimpleName()+"] [init] supportTypeList size["+supportTypeList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [init] supportTypeList data["+supportTypeList+"]");
		
		// 파일설치 경로를 조회합니다.
		filePathList = moduleDao.select_module_08();
		logger.info("["+getClass().getSimpleName()+"] [init] filePathList size["+filePathList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [init] filePathList data["+filePathList+"]");
		
		//부서
		deptList = deptDao.select_dept_01();
		logger.info("["+getClass().getSimpleName()+"] [init] deptList size["+deptList.size()+"]");
		logger.debug("["+getClass().getSimpleName()+"] [init] deptList data["+deptList+"]");
		
		logger.debug("["+getClass().getSimpleName()+"] [init] end");
		
		commonCodeMap.put("customerList", customerList);
		commonCodeMap.put("productList", productList);
		commonCodeMap.put("stateList", stateList);
		commonCodeMap.put("employeeList", employeeList);
		commonCodeMap.put("kindList", kindList);
		commonCodeMap.put("supportTypeList", supportTypeList);
		commonCodeMap.put("filePathList", filePathList);
		commonCodeMap.put("deptList", deptList);
		commonCodeMap.put("titleCustomerList", titleCustomerList);
		
	}

	public List<Map<String, Object>> getCustomerList() {
		return customerList;
	}

	public List<Map<String, Object>> getProductList() {
		return productList;
	}

	public List<Map<String, Object>> getStateList() {
		return stateList;
	}

	public List<Map<String, Object>> getEmployeeList() {
		return employeeList;
	}

	public List<Map<String, Object>> getKindList() {
		return kindList;
	}
	public List<Map<String, Object>> getSupportTypeList() {
		return supportTypeList;
	}
	public List<Map<String, Object>> getFilePathList() {
		return filePathList;
	}
	public List<Map<String, Object>> getDeptList() {
		return deptList;
	}

	public List<Map<String, Object>> getTitleCustomerList() {
		return titleCustomerList;
	}
	
	
	public Map<String, Object> getCommonCodeMap() {
		return commonCodeMap;
	}
}
