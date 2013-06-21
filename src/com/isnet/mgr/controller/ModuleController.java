package com.isnet.mgr.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.isnet.mgr.common.CommonCodeBean;
import com.isnet.mgr.common.CommonConstant;
import com.isnet.mgr.common.DateUtil;
import com.isnet.mgr.common.SearchUtil;
import com.isnet.mgr.common.StringUtil;
import com.isnet.mgr.domain.Rule;
import com.isnet.mgr.service.ModuleMailServiceImp;
import com.isnet.mgr.service.ModuleService;
import com.isnet.mgr.view.FileDownloadView;

@Controller
@RequestMapping("/module")
public class ModuleController {

	Logger logger = Logger.getLogger(getClass());
	
	@Value("#{config['module.savedpath']}")
	private String saved_path;
	
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private CommonCodeBean commonCodeMap;
	
	@Autowired
	private ModuleMailServiceImp mailService;
	@Autowired
	private MappingJacksonJsonView jsonView;
	@Autowired
	private FileDownloadView fileDownloadView;
	
	// 모듈 수정이력을 조회합니다.
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list_01.isnet")
	public ModelAndView list_01(@RequestParam(value="last", required=false, defaultValue="false")boolean isLast,
								@RequestParam(value="page", defaultValue="1")int page,
								@RequestParam(value="rows", defaultValue="10")int rows,
								@RequestParam(value="sidx", required=false)String sidx,
								@RequestParam(value="sord", required=false)String sord,
								@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
								@RequestParam(value="filters", required=false)String filters){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_01] start");
		
		logger.info("["+getClass().getSimpleName()+"] [list_01] page ["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] rows ["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] sidx ["+sidx+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] sord ["+sord+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] _search ["+_search+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] filters ["+filters+"]");
		

		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("PAGE", page);
			paramMap.put("ROWS", rows);
			paramMap.put("SIDX", sidx);
			paramMap.put("SORD", sord);
			paramMap.put("_SEARCH", _search);
			
			if(_search && filters != null){
				
				Map<String, Object> map = SearchUtil.getQueryMap(filters);
				List<Rule> rules = (List<Rule>)map.get("param");	
				logger.info("["+getClass().getSimpleName()+"] [list_01] rules ["+rules+"]");
				if(!rules.isEmpty()){
					paramMap.put("rules", rules);
					paramMap.put("groupOp", map.get("groupOp"));
				}
			}
			
			Map<String, Object> resultData = moduleService.getRecentModuleList(paramMap);
			mav.addAllObjects(resultData);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_01] 최근 모듈변경 내역 조회 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_01] end");
		}
		
		return mav;
	}
	
	// 모듈번호에 해당하는 수정이력정보를 조회합니다.
	@RequestMapping(value="/list_02.isnet", method=RequestMethod.GET)
	public ModelAndView list_02(@RequestParam(value="mn")int module_no){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_02] start");
		logger.info("["+getClass().getSimpleName()+"] [list_02] module_no ["+module_no+"]");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("MODULE_NO", module_no);
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> resultData = moduleService.getModuleHistory(paramMap);
			mav.addAllObjects(resultData);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_02] 모듈수정 이력 조회 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_02] end");
		}
		return mav;
	}
	
	// 제품별 최종버전 정보 조회
	@RequestMapping(value="/list_03.isnet", method=RequestMethod.GET)
	public ModelAndView list_03(@RequestParam(value="page", defaultValue="1")int page,
								@RequestParam(value="rows", defaultValue="10")int rows,
								@RequestParam(value="sidx", required=false)String sidx,
								@RequestParam(value="sord", required=false)String sord,
								@RequestParam(value="cn", required=false, defaultValue="0")int customer_no,
								@RequestParam(value="pn")int product_no,
								@RequestParam(value="ln", required=false, defaultValue="0")int location_no){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_03] start");
		logger.info("["+getClass().getSimpleName()+"] [list_03] page ["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_03] rows ["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_03] sidx ["+sidx+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_03] sord ["+sord+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_03] customer ["+customer_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_03] product_no ["+product_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_03] location_no ["+location_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("PAGE", page);
			paramMap.put("ROWS", rows);
			paramMap.put("SIDX", sidx);
			paramMap.put("SORD", sord);
			paramMap.put("PRODUCT_NO", product_no);
			
			if(customer_no != 0){
				paramMap.put("CUSTOMER_NO", customer_no);
				Map<String, Object> resultData = moduleService.getCustomerLastVersionList(paramMap);
				mav.addAllObjects(resultData);
			}else{
				paramMap.put("LOCATION_NO", location_no);
				Map<String, Object> resultData = moduleService.getProductLastVersionList(paramMap);
				mav.addAllObjects(resultData);
			}
				
			mav.addObject("isSuccess", false);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_03] 제품별 최종버전 조회중 오류 발생");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_03] end");
		}
		
		return mav;
	}
	
	// 제품별 최종버전 정보 조회
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list_04.isnet", method=RequestMethod.POST)
	public ModelAndView list_04(@RequestParam(value="page", defaultValue="1")int page,
								@RequestParam(value="rows", defaultValue="20")int rows,
								@RequestParam(value="sidx", required=false)String sidx,
								@RequestParam(value="sord", required=false)String sord,
								@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
								@RequestParam(value="filters", required=false)String filters){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_04] start");
		logger.info("["+getClass().getSimpleName()+"] [list_04] page ["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] rows ["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] sidx ["+sidx+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] sord ["+sord+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] _search ["+_search+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] filters ["+filters+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("PAGE", page);
			paramMap.put("ROWS", rows);
			paramMap.put("SIDX", sidx);
			paramMap.put("SORD", sord);
			paramMap.put("_SEARCH", _search);
			if(_search && filters != null){
				
				Map<String, Object> map = SearchUtil.getQueryMap(filters);
				List<Rule> rules = (List<Rule>)map.get("param");	
				logger.info("["+getClass().getSimpleName()+"] [list_04] rules ["+rules+"]");
				if(!rules.isEmpty()){
					paramMap.put("rules", rules);
					paramMap.put("groupOp", map.get("groupOp"));
				}
			}
			
			Map<String, Object> resultData = moduleService.getModuleList(paramMap);
			mav.addAllObjects(resultData);				
			mav.addObject("isSuccess", false);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_03] 제품별 최종버전 조회중 오류 발생");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_03] end");
		}
		
		return mav;
	}
	

	// 모듈리스트 페이지로 이동합니다.
	@RequestMapping(value="/module_01.isnet", method=RequestMethod.GET)
	public ModelAndView module_01(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/module/module_01");
		
		// 검색조건에 사용될 고객정보, 제품정보, 진행상태 정보를 조회합니다.
		List<Map<String, Object>> customerList = commonCodeMap.getCustomerList();
		List<Map<String, Object>> productList = commonCodeMap.getProductList();
		List<Map<String, Object>> employeeList = commonCodeMap.getEmployeeList();
		
		
		// 검색조건의 selectbox에 적합한 데이터형태로 값을 가공합니다.
		// "1:가고객사;2:나고객사;3:다고객사;4:라고객사"의 형태로 값을 가공한다.
		// jqGrid columnModel에서 searchoption의 value로 사용됨
		String productListString = StringUtil.listToString(productList, "PRODUCT_NAME", "PRODUCT_NO");
		String employeeListString = StringUtil.listToString(employeeList, "EMPLOYEE_NAME", "EMPLOYEE_NO");
		String customerListString = StringUtil.listToString(customerList, "CUSTOMER_NAME", "CUSTOMER_NO");
		
		mav.addObject("productValues", productListString);
		mav.addObject("employeeValues", employeeListString);
		mav.addObject("customerValues", customerListString);
		
		return mav;
	}
	
	// 모듈상세보기 페이지로 이동합니다.
	@RequestMapping(value="/module_02.isnet", method=RequestMethod.GET)
	public ModelAndView module_02(@RequestParam("mn")int module_no){
		
		logger.debug("["+getClass().getSimpleName()+"] [module_02] start");
		
		logger.info("["+getClass().getSimpleName()+"] [module_02] module_no ["+module_no+"]");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("MODULE_NO", module_no);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/module/module_02");

		try{
			List<Map<String, Object>> titleCustomerList = commonCodeMap.getTitleCustomerList();
			mav.addObject("titleCustomerList", titleCustomerList);
			
			List<Map<String, Object>> productList = commonCodeMap.getProductList();
			mav.addObject("productList", productList);
			
			List<Map<String, Object>> filePathList = commonCodeMap.getFilePathList();
			mav.addObject("filePathList", filePathList);
			
			Map<String, Object> resultData = moduleService.getModuleInfo(paramMap);
			
			mav.addAllObjects(resultData);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [module_02] module_no["+module_no+"]의 모듈상세정보 조회 중 오류발생");
			mav.setViewName("/module/module_01");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [module_02] end");
			
		}
		
		return mav;
	}
	
	
	// 신규모듈 등록 페이지로 이동합니다.
	@RequestMapping(value="/module_03.isnet", method=RequestMethod.GET)
	public ModelAndView module_03(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/module/module_03");
		try{
			Map<String, Object> resultData = commonCodeMap.getCommonCodeMap();
			mav.addAllObjects(resultData);		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [issue_01] end");
		}		
		
		return mav;
	}
	
	// 모듈별 최종버전 페이지로 이동합니다.
	@RequestMapping(value="/module_04.isnet", method=RequestMethod.GET)
	public ModelAndView module_04(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/module/module_04");
		Map<String, Object> resultData = commonCodeMap.getCommonCodeMap();
		mav.addAllObjects(resultData);
		
		return mav;
	}
	
	// 모듈별 최종버전 페이지로 이동합니다.
	@RequestMapping(value="/module_05.isnet", method=RequestMethod.GET)
	public ModelAndView module_05(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/module/module_05");
		Map<String, Object> resultData = commonCodeMap.getCommonCodeMap();
		mav.addAllObjects(resultData);
		
		return mav;
	}
	
	// 모듈별 최종버전 페이지로 이동합니다.
	@RequestMapping(value="/module_06.isnet", method=RequestMethod.GET)
	public ModelAndView module_06(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/module/module_06");
		Map<String, Object> resultData = commonCodeMap.getCommonCodeMap();
		mav.addAllObjects(resultData);
		
		return mav;
	}
	
	// 신규 모듈을 등록합니다.
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add_module01.isnet", method=RequestMethod.POST)
	@ResponseBody
	public String add_module01(@RequestParam("PRODUCT_NO")int product_no,
									@RequestParam("LOCATION_NO")int location_no,
									@RequestParam(value="MODULE_NAME", defaultValue="")String module_name,
									@RequestParam("FILE_VERSION")String file_version,
									@RequestParam(value="MODULE_DESC", required=false)String module_desc,
									@RequestParam("UPDATE_MEMO")String update_memo,
									@RequestParam(value="CUSTOMER_NO" ,required=false)int [] customers,
									@RequestParam(value="x86_release_file", required=false)MultipartFile x86_release_file,
									@RequestParam(value="x64_release_file", required=false)MultipartFile x64_release_file,
									@RequestParam(value="x86_debug_file", required=false)MultipartFile x86_debug_file,
									@RequestParam(value="x64_debug_file", required=false)MultipartFile x64_debug_file,
									HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_module01] start");
		
		logger.info("["+getClass().getSimpleName()+"] [add_module01] product_no ["+product_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module01] location_no ["+location_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module01] module_name ["+module_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module01] file_version ["+file_version+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module01] module_desc ["+module_desc+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module01] update_memo ["+update_memo+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module01] customers ["+Arrays.toString(customers)+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module01] x86_release_file ["+x86_release_file+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module01] x64_release_file ["+x86_release_file+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module01] x86_debug_file ["+x86_release_file+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module01] x64_debug_file ["+x86_release_file+"]");

		boolean isSuccess = false;
		
		try{
			
			if(x86_release_file != null || x64_release_file != null || x86_debug_file != null || x64_debug_file != null){
				String directory = "module_" + DateUtil.dateToString(CommonConstant.DATE_FORMAT_02);
				File folder = new File(saved_path,directory);
				if(!folder.exists()){
					folder.mkdir();
				}
				
				logger.info("["+getClass().getSimpleName()+"] [add_module01] folder.getPath() ["+folder.getPath()+"]");
				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("PRODUCT_NO", product_no);
				paramMap.put("LOCATION_NO", location_no);
				paramMap.put("MODULE_NAME", module_name);
				paramMap.put("UPPER_MODULE_NAME", module_name.toUpperCase());
				paramMap.put("FILE_VERSION", file_version);
				paramMap.put("MODULE_DESC", module_desc);
				paramMap.put("UPDATE_MEMO", update_memo);
				paramMap.put("UPDATE_DATE", DateUtil.dateToString(CommonConstant.DATE_FORMAT_02));
				paramMap.put("SAVE_PATH", directory);
				paramMap.put("CUSTOMERS", customers);
				
				Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
				int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
				logger.info("["+getClass().getSimpleName()+"] [add_module01] employee_no ["+employee_no+"]");
				paramMap.put("EMPLOYEE_NO", employee_no);
				
				// paramMap 파일 명 추가
				if(x86_release_file != null && !x86_release_file.isEmpty()) {
					String x86_release = x86_release_file.getOriginalFilename();
					saveFile(x86_release_file, folder, "x86_release", file_version);
					logger.info("["+getClass().getSimpleName()+"] [upload_module_02] x86_release file["+x86_release+"] upload complete");
					paramMap.put("X86_RELEASE", x86_release);
				}
				
				if(x64_release_file != null && !x64_release_file.isEmpty()) {
					String x64_release = x64_release_file.getOriginalFilename();
					saveFile(x64_release_file, folder, "x64_release", file_version);
					logger.info("["+getClass().getSimpleName()+"] [upload_module_02] x64_release file["+x64_release+"] upload complete");
					paramMap.put("X64_RELEASE", x64_release);
				}
				if(x86_debug_file != null && !x86_debug_file.isEmpty()) {
					String x86_debug = x86_debug_file.getOriginalFilename();
					saveFile(x86_debug_file, folder, "x86_debug", file_version);
					logger.info("["+getClass().getSimpleName()+"] [upload_module_02] x86_debug file["+x86_debug+"] upload complete");
					paramMap.put("X86_DEBUG", x86_debug);
				}
				if(x64_debug_file != null && !x64_debug_file.isEmpty()) {
					String x64_debug = x64_debug_file.getOriginalFilename();
					saveFile(x64_debug_file, folder, "x64_debug", file_version);
					logger.info("["+getClass().getSimpleName()+"] [upload_module_02] x64_debug file["+x64_debug+"] upload complete");
					paramMap.put("X64_DEBUG", x64_debug);
				}
				
				moduleService.addModule(paramMap);
				logger.info("["+getClass().getSimpleName()+"] [add_module01] MODULE_HISTORY_NO ["+ paramMap.get("MODULE_HISTORY_NO") +"]");
				
				/*if (customers!= null && customers.length > 0) {
					paramMap.put("CUSTOMERS", customers);
					moduleService.addCustomerApplyModule(paramMap);
				}*/
				isSuccess = true;
				
				mailService.sendMail(paramMap);
				logger.info("["+getClass().getSimpleName()+"] [add_module01] isSuccess [true]");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_module01] 신규 모듈 등록 중 오류");
			isSuccess = false;
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_module01] end");
		}
		
		return "{\"isSuccess\": "+isSuccess+"}";

	}

	// 모듈 수정이력을 등록합니다.
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add_module02.isnet", method=RequestMethod.POST)
	@ResponseBody
	public String add_module02(@RequestParam("MODULE_NO")int module_no,
									@RequestParam("PRODUCT_NO")int product_no,
									@RequestParam("FILE_VERSION")String file_version,
									@RequestParam("UPDATE_MEMO")String update_memo,
									@RequestParam("CUSTOMER_NO")int [] customers,
									@RequestParam(value="x86_release_file", required=false)MultipartFile x86_release_file,
									@RequestParam(value="x64_release_file", required=false)MultipartFile x64_release_file,
									@RequestParam(value="x86_debug_file", required=false)MultipartFile x86_debug_file,
									@RequestParam(value="x64_debug_file", required=false)MultipartFile x64_debug_file,
									HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_module02] start");
		
		logger.info("["+getClass().getSimpleName()+"] [add_module02] product_no ["+product_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module02] module_no ["+module_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module02] file_version ["+file_version+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module02] customers ["+Arrays.toString(customers)+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_module02] update_memo ["+update_memo+"]");

		boolean isSuccess = false;
		
		try{
			
			if(x86_release_file != null || x64_release_file != null || x86_debug_file != null || x64_debug_file != null){
				String directory = "module_" + DateUtil.dateToString(CommonConstant.DATE_FORMAT_02);
				File folder = new File(saved_path,directory);
				if(!folder.exists()){
					folder.mkdir();
				}
				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("FILE_VERSION", file_version);
				paramMap.put("PRODUCT_NO", product_no);
				paramMap.put("MODULE_NO", module_no);
				paramMap.put("UPDATE_MEMO", update_memo);
				paramMap.put("UPDATE_DATE", DateUtil.dateToString(CommonConstant.DATE_FORMAT_02));
				paramMap.put("SAVE_PATH", directory);
				paramMap.put("CUSTOMERS", customers);
				
				//첨부된 파일명
				// paramMap 파일 명 추가
				if(x86_release_file != null && !x86_release_file.isEmpty()) {
					String x86_release = x86_release_file.getOriginalFilename();
					saveFile(x86_release_file, folder, "x86_release", file_version);
					logger.info("["+getClass().getSimpleName()+"] [upload_module_02] x86_release file["+x86_release+"] upload complete");
					paramMap.put("X86_RELEASE", x86_release);
				}
				
				if(x64_release_file != null && !x64_release_file.isEmpty()) {
					String x64_release = x64_release_file.getOriginalFilename();
					saveFile(x64_release_file, folder, "x64_release", file_version);
					logger.info("["+getClass().getSimpleName()+"] [upload_module_02] x64_release file["+x64_release+"] upload complete");
					paramMap.put("X64_RELEASE", x64_release);
				}
				if(x86_debug_file != null && !x86_debug_file.isEmpty()) {
					String x86_debug = x86_debug_file.getOriginalFilename();
					saveFile(x86_debug_file, folder, "x86_debug", file_version);
					logger.info("["+getClass().getSimpleName()+"] [upload_module_02] x86_debug file["+x86_debug+"] upload complete");
					paramMap.put("X86_DEBUG", x86_debug);
				}
				if(x64_debug_file != null && !x64_debug_file.isEmpty()) {
					String x64_debug = x64_debug_file.getOriginalFilename();
					saveFile(x64_debug_file, folder, "x64_debug", file_version);
					logger.info("["+getClass().getSimpleName()+"] [upload_module_02] x64_debug file["+x64_debug+"] upload complete");
					paramMap.put("X64_DEBUG", x64_debug);
				}
				
				Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
				int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
				logger.info("["+getClass().getSimpleName()+"] [add_module02] employee_no ["+employee_no+"]");
				paramMap.put("EMPLOYEE_NO", employee_no);
				
				moduleService.addModuleHistory(paramMap);
				moduleService.updateLastModuleHistoryNo(paramMap);
				
				mailService.sendMail(paramMap);
				isSuccess = true;
				
			}else{
				isSuccess = true;
			}
					
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_module02] 모듈변호["+module_no+"] 수정이력 등록 중 오류 발생");
			isSuccess = false;
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_module02] end");
		}
		
		return "{\"isSuccess\": "+isSuccess+"}";
	}	
	
	// 모듈 적용사이트 등록합니다.
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add_customer_apply.isnet", method=RequestMethod.POST)
	public ModelAndView add_customer_apply(@RequestParam("hidden_mhn")int module_history_no,
									@RequestParam("hidden_mn")int module_no,
									@RequestParam("PRODUCT_NO")int product_no,
									@RequestParam("CUSTOMER_NO")int [] customers,
									HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_customer_apply] start");
		logger.info("["+getClass().getSimpleName()+"] [add_customer_apply] module_no ["+module_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_customer_apply] module_history_no ["+module_history_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_customer_apply] product_no ["+product_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_customer_apply] customers ["+customers.length+"]");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("MODULE_NO", module_no);
		paramMap.put("MODULE_HISTORY_NO", module_history_no);
		paramMap.put("PRODUCT_NO", product_no);
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			logger.info("["+getClass().getSimpleName()+"] [add_module02] employee_no ["+employee_no+"]");
			paramMap.put("EMPLOYEE_NO", employee_no);
			
			if (customers!= null && customers.length > 0) {
				paramMap.put("CUSTOMERS", customers);
				moduleService.addCustomerApplyModule(paramMap);
			}
				
			
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			e.printStackTrace();
			mav.addObject("isSuccess", false);
			mav.addObject("error_msg", "적용사이트 등록중 오류가 발생하였습니다.");
			logger.error("["+getClass().getSimpleName()+"] [add_customer_apply] 고객사 등록 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_customer_apply] end");
		}
		return mav;
	}
	
	// 모듈을 다운로드합니다.
	@RequestMapping(value="/down.isnet")
	public ModelAndView download_module(@RequestParam("mhn")int mhn ,
			                 @RequestParam("module_gubun")String module_gubun){
		
		logger.debug("["+getClass().getSimpleName()+"] [down] start");
		logger.info("["+getClass().getSimpleName()+"] [down] module_history_no ["+mhn+"]");
		logger.info("["+getClass().getSimpleName()+"] [down] module_gubun ["+module_gubun+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(fileDownloadView);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("MODULE_HISTORY_NO", mhn);
		try{
			
			Map<String, Object> fileInfo = moduleService.getModuleHistoryInfo(paramMap);
			File saved_directory = new File(saved_path +"/"+ (String)fileInfo.get("SAVE_PATH") + "/" +module_gubun, (String)fileInfo.get("FILE_VERSION"));
			logger.info("["+getClass().getSimpleName()+"] [down] saved_directory ["+saved_directory+"]");
			
			mav.addObject("downloadFile", new File(saved_directory, (String)fileInfo.get(module_gubun.toUpperCase())));
			mav.addObject("isTimeIncluded", false);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [down] module_history_no["+mhn + "/" +"]의 파일 다운로드 중 오류");
			return null;
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [down] end");
		}
		
		return mav;
	}
	
	// 모듈번호에 해당하는 적용사이트를 조회합니다.
	@RequestMapping(value="/select_customer_apply.isnet")
	public ModelAndView customer_apply(@RequestParam(value="mhn")int module_history_no){
		
		logger.debug("["+getClass().getSimpleName()+"] [customer_apply] start");
		logger.info("["+getClass().getSimpleName()+"] [customer_apply] module_history_no ["+module_history_no+"]");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("MODULE_HISTORY_NO", module_history_no);
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> resultData = moduleService.getCustomerApple(paramMap);
			mav.addAllObjects(resultData);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_02] 모듈수정 이력 조회 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_02] end");
		}
		return mav;
	}
	
	@RequestMapping(value="/update_module_01.isnet", method=RequestMethod.POST)
	public ModelAndView update_module_01(@RequestParam("MODULE_NO")int module_no,
										@RequestParam("PRODUCT_NO")int product_no,
										@RequestParam(value="MODULE_NAME", defaultValue="")String module_name,
										@RequestParam(value="MODULE_DESC", required=false)String module_desc,
										@RequestParam("LOCATION_NO")int location_no){
		
		logger.debug("["+getClass().getSimpleName()+"] [update_module_01] start");
		logger.info("["+getClass().getSimpleName()+"] [update_module_01] module_no["+module_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_module_01] product_no["+product_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_module_01] module_name["+module_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_module_01] module_desc["+module_desc+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_module_01] location_no["+location_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("MODULE_NO", module_no);
			paramMap.put("PRODUCT_NO", product_no);
			paramMap.put("MODULE_NAME", module_name);
			paramMap.put("UPPER_MODULE_NAME", module_name.toUpperCase());
			paramMap.put("MODULE_DESC", module_desc);
			paramMap.put("LOCATION_NO", location_no);
			moduleService.updateModuleInfo(paramMap);
			
			mav.addObject("isSuccess", true);
		}catch(Exception e){
			logger.error("["+getClass().getSimpleName()+"] [update_module_01] module_no["+module_no+"] 모듈기본정보 수정 오류");
			e.printStackTrace();
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_module_01] end");
		}
		return mav;
	}
	
	@RequestMapping(value="/update_module_02.isnet", method=RequestMethod.POST)
	@ResponseBody
	public String update_module_02(@RequestParam("MODULE_HISTORY_NO")int module_history_no,
										@RequestParam("FILE_VERSION")String new_file_version,
										@RequestParam("UPDATE_MEMO")String update_memo,
										@RequestParam(value="x86_release_file", required=false)MultipartFile x86_release_file,
										@RequestParam(value="x64_release_file", required=false)MultipartFile x64_release_file,
										@RequestParam(value="x86_debug_file", required=false)MultipartFile x86_debug_file,
										@RequestParam(value="x64_debug_file", required=false)MultipartFile x64_debug_file){
		logger.debug("["+getClass().getSimpleName()+"] [updae_module_03] start");
		
		boolean isSuccess = false;
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("MODULE_HISTORY_NO", module_history_no);
			paramMap.put("FILE_VERSION", new_file_version);
			paramMap.put("UPDATE_MEMO", update_memo);
			
			
			Map<String, Object> fileInfo = moduleService.getModuleHistoryInfo(paramMap);
			File module_folder = new File(saved_path +"/"+ (String)fileInfo.get("SAVE_PATH"));
			String old_file_version = (String)fileInfo.get("FILE_VERSION");
			
			renameFolder(module_folder, old_file_version, new_file_version);
			
			if(x86_release_file != null && !x86_release_file.isEmpty()) {
				String x86_release = x86_release_file.getOriginalFilename();
				saveFile(x86_release_file, module_folder, "x86_release", new_file_version);
				logger.info("["+getClass().getSimpleName()+"] [upload_module_02] x86_release file["+x86_release+"] upload complete");
				paramMap.put("X86_RELEASE", x86_release);
			}
			if(x64_release_file != null && !x64_release_file.isEmpty()) {
				String x64_release = x64_release_file.getOriginalFilename();
				saveFile(x64_release_file, module_folder, "x64_release", new_file_version);
				logger.info("["+getClass().getSimpleName()+"] [upload_module_02] x64_release file["+x64_release+"] upload complete");
				paramMap.put("X64_RELEASE", x64_release);
			}
			
			if(x86_debug_file != null && !x86_debug_file.isEmpty()) {
				String x86_debug = x86_debug_file.getOriginalFilename();
				saveFile(x86_debug_file, module_folder, "x86_debug", new_file_version);
				logger.info("["+getClass().getSimpleName()+"] [upload_module_02] x86_debug file["+x86_debug+"] upload complete");
				paramMap.put("X86_DEBUG", x86_debug);
			}
			
			if(x64_debug_file != null && !x64_debug_file.isEmpty()) {
				String x64_debug = x64_debug_file.getOriginalFilename();
				saveFile(x64_debug_file, module_folder, "x64_debug", new_file_version);
				logger.info("["+getClass().getSimpleName()+"] [upload_module_02] x64_debug file["+x64_debug+"] upload complete");
				paramMap.put("X64_DEBUG", x64_debug);
			}
			
			moduleService.updateModuleHistoryInfo(paramMap);
			
			isSuccess = true;
		}catch(Exception e){
			e.printStackTrace();
			isSuccess = false;
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_module_02] end");
		}
		
		return "{\"isSuccess\": "+String.valueOf(isSuccess)+"}";
	}
	
	private void renameFolder(File date_folder, String old_file_version, String new_file_version){
		String[] gubunList = new String[]{"x86_release", "x64_release", "x86_debug", "x64_debug"};
		for(String gubun : gubunList){
			File gubun_folder = new File(date_folder.getPath(), gubun);
			File version_folder =  new File (gubun_folder, old_file_version);
			if(version_folder.exists()){
				version_folder.renameTo(new File(gubun_folder, new_file_version));
			}
		}
	}
	
	private void saveFile(MultipartFile file, File date_folder, String gubun, String version) throws IOException, FileNotFoundException {
		File gubun_folder = new File(date_folder.getPath(), gubun);
		if(!gubun_folder.exists()){
			gubun_folder.mkdir();
		}
		
		File version_folder =  new File (gubun_folder,version);
		if(!version_folder.exists()){
			version_folder.mkdir();
		}
		
		FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(new File(version_folder, file.getOriginalFilename())));
	}
	
}
