package com.isnet.mgr.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.isnet.mgr.common.CommonCodeBean;
import com.isnet.mgr.common.OptionFileUtil;
import com.isnet.mgr.common.StringUtil;
import com.isnet.mgr.service.OptionService;
import com.isnet.mgr.view.FileDownloadView;


@Controller
@RequestMapping("/option")
public class OptionController {

	Logger logger = Logger.getLogger(getClass());

	@Value("#{config['option.savedpath']}")
	private String saved_path;

	@Autowired
	private OptionService optionService;

	@Autowired
	private MappingJacksonJsonView mappingJacksonJsonView;

	@Autowired
	private FileDownloadView fileDownloadView;

	@Autowired
	private CommonCodeBean commonCodeMap;
	
	@RequestMapping(value="/option_01.isnet")
	public ModelAndView option_01(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/option/option_01");
		mav.addObject("customerList", commonCodeMap.getCustomerList());
		mav.addObject("productList", commonCodeMap.getProductList());
		return mav;
	}

	// 고객사별 제품별 옵션파일조회
	@RequestMapping(value="/option_download_01.isnet")
	public ModelAndView option_download_01(@RequestParam(value="customer_no")int customer_no,
			@RequestParam(value="product_no")int product_no){

		logger.debug("["+getClass().getSimpleName()+"] [option_download_01] start");

		ModelAndView mav = new ModelAndView();

		logger.info("["+getClass().getSimpleName()+"] [option_download_01] customer_no = " + customer_no);
		logger.info("["+getClass().getSimpleName()+"] [option_download_01] product_no = " + product_no);

		try{
			Map<String, Object> paramMap = new HashMap<String, Object> ();
			paramMap.put("CUSTOMER_NO", customer_no);
			paramMap.put("PRODUCT_NO", product_no);
			
			List<Map<String, Object>> optionList = optionService.getOptionList(paramMap);
			List<Map<String, Object>> optionFileList = optionService.getOptionFileList(paramMap);

			mav.addObject("optionFileList", optionFileList);
			mav.addObject("optionFileListSize", optionFileList.size());
			
			mav.addObject("optionList", optionList);
			mav.addObject("optionListSize", optionList.size());
			mav.addObject("customerList", commonCodeMap.getCustomerList());
			mav.addObject("productList", commonCodeMap.getProductList());
			
			mav.setViewName("/option/option_01");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [option_download_01] 조회 오류");
			mav.addObject("error_msg", "옵션 조회중  오류가 발생하였습니다.");
			mav.setViewName("error");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [option_download_01] end");
		}

		return mav;
	}

	// 옵션 파일을 다운로드 합니다.
	@RequestMapping(value="/option_download_02.isnet")
	public ModelAndView option_download_02(HttpServletRequest request,  
			@RequestParam(value="customer_no")int customer_no,
			@RequestParam(value="product_no")int  product_no){

		logger.debug("["+getClass().getSimpleName()+"] [option_download_02] start");

		//조건맵
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("CUSTOMER_NO", customer_no);
		paramMap.put("PRODUCT_NO", product_no);

		logger.info("["+getClass().getSimpleName()+"] [option_download_02] customer_no = " + customer_no);
		logger.info("["+getClass().getSimpleName()+"] [option_download_02] product_no = " + product_no);

		//파일 다운로드 View
		ModelAndView mav = new ModelAndView(fileDownloadView);

		String dirName = "isnet_" +System.currentTimeMillis();
		String filePath = saved_path;
		try{

			// 옵션파일  작성할 리스트
			List<Map<String, Object>> resultList= optionService.getOptionList(paramMap);

			logger.info("["+getClass().getSimpleName()+"] [option_download_02] filePath = " + filePath+dirName);	

			// 옵션파일 작성
			OptionFileUtil optionFileUtil = new OptionFileUtil();
			String returnPath = optionFileUtil.createFile(filePath+dirName ,resultList);
			logger.info("["+getClass().getSimpleName()+"] [option_download_02] returnPath =" + returnPath);

			//File file = new File("C:/BMGuardIIForFA/BMGuardIIForFA-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp3/wtpwebapps/isnet/isnet_1358995814031.zip");
			File file = new File (returnPath + ".zip");
			mav.addObject("downloadFile", file);
			mav.addObject("isTimeIncluded", false);
		}catch(Exception e){

			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [option_download_02] 파일 생성중 오류 발생");

		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [option_download_02] end");
		}

		return mav;

	}

	// 엑셀다운로드
	@RequestMapping(value="/option_excel.isnet")
	public ModelAndView option_excel(HttpServletRequest request,  
			@RequestParam(value="excelExpert", required=false)String excelExpert){
		logger.debug("["+getClass().getSimpleName()+"] [option_excel] start");

		ModelAndView mav = new ModelAndView();

		try{
			mav.addObject("excelExpert", excelExpert);
			mav.setViewName("/option/option_excel");
		}catch(Exception e){
			e.printStackTrace();

		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [option_excel] end");
		}

		return mav;
	}

	// 옵션다운로드 페이지에서 옵션값 수정하기
	@RequestMapping(value="/update_option_01.isnet")
	public ModelAndView update_option_01(
			@RequestParam(value="customer", required=false)Integer customer,
			@RequestParam(value="product", required=false)Integer  product,
			@RequestParam(value="param", required=false)String param){
		logger.debug("["+getClass().getSimpleName()+"] [update_option_01] start");

		//조건맵
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("CUSTOMER_NO", customer);
		paramMap.put("PRODUCT_NO", product);
		paramMap.put("PARAM", param);

		ModelAndView mav = new ModelAndView(mappingJacksonJsonView);
		try{
			Map<String, Object> resultMap = optionService.update_option_01(paramMap);
			mav.addAllObjects(resultMap);

		}catch(Exception e) {
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [file_01] 파일 생성중 오류 발생");

		}
		logger.debug("["+getClass().getSimpleName()+"] [update_option_01] end");
		return mav;
	}

	// 옵션 리스트 페이지로 이동하기
	@RequestMapping(value="/option_02.isnet")
	public ModelAndView option_02(){

		ModelAndView mav = new ModelAndView();
		List<Map<String, Object>> productList = commonCodeMap.getProductList();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Map<String, Object>> optionFileList = optionService.getOptionFileList(paramMap);
		String productListString = StringUtil.listToString(productList, "PRODUCT_NAME", "PRODUCT_NO");
		String optionFileListString = StringUtil.listToString(optionFileList, "OPTION_FILE_NAME", "OPTION_FILE_NO");
		mav.addObject("productValues", productListString);
		mav.addObject("optionFileValues", optionFileListString);
		
		mav.setViewName("/option/option_02");
		return mav;
	}


	// 옵션 리스트를 조회합니다.
	@RequestMapping(value="/option_list_01.isnet")
	public ModelAndView option_list_01(@RequestParam(value="page", defaultValue="1")int page,
			@RequestParam(value="rows", defaultValue="10")int rows,
			@RequestParam(value="sidx", required=false)String sidx,
			@RequestParam(value="sord", required=false)String sord,
			@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
			@RequestParam(value="searchField", required=false)String searchField,
			@RequestParam(value="searchString", required=false)String searchString,
			@RequestParam(value="searchOper", required=false)String searchOper,
			@RequestParam(value="product", required=false)String product,
			@RequestParam(value="optionfile", required=false)String file,
			@RequestParam(value="optioonApp", required=false)String app
			){

		logger.debug("["+getClass().getSimpleName()+"] [option_list_01] start");

		logger.info("["+getClass().getSimpleName()+"] [option_list_01] product ["+product+"]");
		logger.info("["+getClass().getSimpleName()+"] [option_list_01] file ["+file+"]");
		logger.info("["+getClass().getSimpleName()+"] [option_list_01] app ["+app+"]");

		logger.info("["+getClass().getSimpleName()+"] [option_list_01] page ["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [option_list_01] rows ["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [option_list_01] sidx ["+sidx+"]");
		logger.info("["+getClass().getSimpleName()+"] [option_list_01] sord ["+sord+"]");
		logger.info("["+getClass().getSimpleName()+"] [option_list_01] _search ["+_search+"]");

		//조건맵
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("PRODUCT_NO", product);
		paramMap.put("OPTION_FILE_NO", product);
		paramMap.put("OPTION_APP_NO", file);

		paramMap.put("PRODUCT_NO", app);
		paramMap.put("PAGE", page);
		paramMap.put("ROWS", rows);
		paramMap.put("SIDX", sidx);
		paramMap.put("SORD", sord);
		paramMap.put("_SEARCH", _search);

		ModelAndView mav = new ModelAndView();
		mav.setView(mappingJacksonJsonView);

		if(_search){

			logger.info("searchField ["+searchField+"]");
			logger.info("searchString ["+searchString+"]");
			logger.info("searchOper ["+searchOper+"]");

			paramMap.put("SEARCH_FIELD", searchField);
			paramMap.put("SEARCH_STRING", searchString);
			paramMap.put("SEARCH_OPER", searchOper);
		}
		
		try{
			Map<String, Object> resultMap = optionService.getFullOptionList(paramMap);
			mav.addAllObjects(resultMap);
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [option_list_01] 옵션 리스트 조회 오류");

		}
		logger.debug("["+getClass().getSimpleName()+"] [option_list_01] end");

		return mav;
	}
	
	// 옵션 비교페이지로 이동합니다.
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/option_03.isnet")
	public ModelAndView option_03(){
	
		logger.debug("["+getClass().getSimpleName()+"] [option_03] start");
	
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/option/option_03");
	
		try{
			Map<String, Object> resultData = commonCodeMap.getCommonCodeMap();
	
			List<Map<String, Object>> customerList = (List<Map<String, Object>>)resultData.get("customerList");
			List<Map<String, Object>> productList = (List<Map<String, Object>>)resultData.get("productList");
			//List<Map<String, Object>> optionFile = optionService.select_option_03_05();
	
			mav.addObject("customerList", customerList);
			mav.addObject("productList", productList);
			//mav.addObject("optionFile", optionFile);
	
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [option_03] 조회 오류");
			mav.addObject("error_msg", "옵션 조회중  오류가 발생하였습니다.");
			mav.setViewName("error");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [option_03] end");
		}
	
		return mav;
	}
	
	@RequestMapping(value="/option_compare.isnet")
	public ModelAndView option_compare(   @RequestParam(value="product", required=false)int product,
			@RequestParam(value="fileChk", required=false)int [] file,
			@RequestParam(value="customer_no1", required=false)String customer_no1,
			@RequestParam(value="customer_no2", required=false)String customer_no2,
			@RequestParam(value="customer_no3", required=false)String customer_no3,
			@RequestParam(value="customer_no4", required=false)String customer_no4
			){

		logger.debug("["+getClass().getSimpleName()+"] [list_02] start");
		logger.info("["+getClass().getSimpleName()+"] product ["+ product + "]");
		logger.info("["+getClass().getSimpleName()+"] file ["+ file.length + "]");
		logger.info("["+getClass().getSimpleName()+"] customer1 ["+ customer_no1 + "]");
		logger.info("["+getClass().getSimpleName()+"] customer2 ["+ customer_no2 + "]");
		logger.info("["+getClass().getSimpleName()+"] customer3 ["+ customer_no3 + "]");
		logger.info("["+getClass().getSimpleName()+"] customer4 ["+ customer_no4 + "]");

		String file_query=" AND OPTION_FILE_NO IN (";
		for (int i = 0; i < file.length; i++) {
			if(i !=  file.length -1 ) 
				file_query+= file[i] + "," ;
			else
				file_query+= file[i] +") ";
		}
		logger.info("["+getClass().getSimpleName()+"] file ["+ file_query + "]");

		ModelAndView mav = new ModelAndView();
		mav.setView(mappingJacksonJsonView);

		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("PRODUCT_NO", product);
		paramMap.put("OPTION_FILE_QUERY", file_query);
		paramMap.put("CUSTOMER_NO1", customer_no1);
		paramMap.put("CUSTOMER_NO2", customer_no2);
		paramMap.put("CUSTOMER_NO3", customer_no3);
		paramMap.put("CUSTOMER_NO4", customer_no4);

		try{

			Map<String, Object> resultMap = optionService.getForthOptionList(paramMap);	
			mav.addAllObjects(resultMap);

		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_02] 조회 오류");
			mav.addObject("error_msg", "옵션 조회중  오류가 발생하였습니다.");
			mav.setViewName("error");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_02] end");
		}

		return mav;
	}

	// 옵션 상세정보 조회
	@RequestMapping(value="/option_04.isnet")
	public ModelAndView option_04(@RequestParam("option_no")int option_no){
		logger.debug("["+getClass().getSimpleName()+"] [option_04] start");
		logger.info("["+getClass().getSimpleName()+"] [option_04] option_no["+option_no+"]");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/option/option_04");

		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("OPTION_NO", option_no);
			
			Map<String, Object> optionInfo = optionService.getOptionInfo(paramMap);
			mav.addObject("optionInfo", optionInfo);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [option_04] 옵션상세정보 조회 중 오류");
			
		}

		return mav;
	}
	
	// 옵션 등록페이지로 이동합니다.
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/option_05.isnet")
	public ModelAndView option_05(){

		Map<String, Object> resultData = commonCodeMap.getCommonCodeMap();
		List<Map<String,Object>> product = (List<Map<String, Object>>)resultData.get("productList");

		ModelAndView mav = new ModelAndView();

		mav.setViewName("/option/option_05");
		mav.addObject("product", product);
		mav.addObject("productSize", product.size());

		return mav;
	}	
	
	

	/*@RequestMapping(value="/list_01.isnet")
	public ModelAndView list_01( @RequestParam(value="page", defaultValue="1")int page,
			@RequestParam(value="rows", defaultValue="10")int rows,
			@RequestParam(value="sidx", required=false)String sidx,
			@RequestParam(value="sord", required=false)String sord,
			@RequestParam(value="product", required=false)String product,
			@RequestParam(value="optionFile", required=false)String file,
			@RequestParam(value="optionApp", required=false)String app,
			@RequestParam(value="option", required=false)String option){

		logger.debug("["+getClass().getSimpleName()+"] [list_01] start");

		ModelAndView mav = new ModelAndView();
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("page", page);
		paramMap.put("rows", rows);
		paramMap.put("sidx", sidx);
		paramMap.put("sord", sord);
		paramMap.put("PRODUCT_NO", product);
		paramMap.put("OPTION_FILE_NO", file);
		paramMap.put("OPTION_APP_NO", app);
		paramMap.put("OPTION_NO", option);

		try{

			Map<String, Object> resultMap = optionService.getThirdOptionList(paramMap);	
			mav.addAllObjects(resultMap);

		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_01] 조회 오류");
			mav.addObject("error_msg", "옵션 조회중  오류가 발생하였습니다.");
			mav.setViewName("error");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_01] end");
		}

		return mav;
	}*/



	

	@RequestMapping(value="/select_option_03_01.isnet")
	public ModelAndView select_option_03_01(@RequestParam(value="product", required=false)String product){
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("PRODUCT_NO", product);

		List<Map<String,Object>> optionFile = optionService.select_option_03_01(paramMap);

		ModelAndView mav = new ModelAndView();
		mav.setView(mappingJacksonJsonView);

		mav.addObject("optionFile", optionFile);
		mav.addObject("optionFileSize", optionFile.size());

		return mav;
	}

	@RequestMapping(value="/select_option_03_02.isnet")
	public ModelAndView select_option_03_02(@RequestParam(value="optionFile", required=false)String file){
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("OPTION_FILE_NO", file);

		List<Map<String,Object>> optionApp = optionService.select_option_03_02(paramMap);

		ModelAndView mav = new ModelAndView();
		mav.setView(mappingJacksonJsonView);

		mav.addObject("optionApp", optionApp);
		mav.addObject("optionAppSize", optionApp.size());

		return mav;
	}	


	@RequestMapping(value="/select_option_03_03.isnet")
	public ModelAndView select_option_03_03(@RequestParam(value="optionApp", required=false)String app){
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("OPTION_APP_NO", app);

		List<Map<String,Object>> option = optionService.select_option_03_03(paramMap);

		ModelAndView mav = new ModelAndView();
		mav.setView(mappingJacksonJsonView);

		mav.addObject("option", option);
		mav.addObject("optionSize", option.size());

		return mav;
	}

	@RequestMapping(value="/select_option_03_04.isnet")
	public ModelAndView select_option_03_04(@RequestParam(value="optionApp", required=false)String app,
			@RequestParam(value="option", required=false)String option){
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("OPTION_APP_NO", app);
		paramMap.put("OPTION_NO", option);

		List<Map<String,Object>> optionList = optionService.select_option_03_03(paramMap);
		List<Map<String,Object>> comment = optionService.select_option_03_04(paramMap);

		ModelAndView mav = new ModelAndView();
		mav.setView(mappingJacksonJsonView);

		mav.addObject("comment", comment);
		mav.addObject("commentSize", comment.size());
		mav.addObject("optionList", optionList);
		mav.addObject("optionListSize", optionList.size());

		return mav;
	}

	/*@RequestMapping(value="/add_product.isnet")
	public ModelAndView add_product(@RequestParam(value="product_name", required=false)String product_name,
			@RequestParam(value="product_code", required=false)String product_code){
		logger.debug("["+getClass().getSimpleName()+"] [add_product] start");
		logger.info("[add_product] : product_name [" + product_name+ "] end");
		logger.info("[add_product] : product_code [" + product_code+ "] end");

		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("PRODUCT_NAME", product_name);
		paramMap.put("PRODUCT_CODE", product_code);

		ModelAndView mav = new ModelAndView();
		mav.setView(mappingJacksonJsonView);

		try {
			productService.add_product(paramMap);
			mav.addObject("isSuccess", true);

		}catch(Exception e) {
			e.printStackTrace();
			mav.addObject("isSuccess", false);
		}

		logger.debug("["+getClass().getSimpleName()+"] [add_product] end");
		return mav;
	}	*/

	@RequestMapping(value="/add_file.isnet")
	public ModelAndView add_file(@RequestParam(value="product_no", required=false)int product_no,
			@RequestParam(value="file_name", required=false)String file_name,
			@RequestParam(value="file_no", required=false)String file_no
			){
		logger.debug("["+getClass().getSimpleName()+"] [add_file] start");
		logger.info("["+getClass().getSimpleName()+"] [add_file] : product_no [" + product_no+ "]");
		logger.info("["+getClass().getSimpleName()+"] [add_file] : file_name [" + file_name+ "]");
		logger.info("["+getClass().getSimpleName()+"] [add_file] : file_no [" + file_no+ "]");

		ModelAndView mav = new ModelAndView();
		mav.setView(mappingJacksonJsonView);

		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("PRODUCT_NO", product_no);
		paramMap.put("OPTION_FILE_NAME", file_name);


		try {
			int result_no =0;
			if (file_no.equals("")) {
				result_no = optionService.add_file(paramMap);
			}else {

				result_no = Integer.parseInt(file_no);
				paramMap.put("OPTION_FILE_NO", result_no);
				optionService.update_file(paramMap);
			}
			mav.addObject("isSuccess", true);
			mav.addObject("result_no",result_no);

		}catch(Exception e) {
			e.printStackTrace();
			mav.addObject("isSuccess", false);
		}

		logger.debug("["+getClass().getSimpleName()+"] [add_file] end");
		return mav;
	}

	@RequestMapping(value="/add_app.isnet")
	public ModelAndView add_app(@RequestParam(value="file_no", required=true)int file_no,
			@RequestParam(value="app_name", required=false)String app_name,
			@RequestParam(value="app_no", required=false)String app_no
			){
		logger.debug("["+getClass().getSimpleName()+"] [add_app] start");
		logger.info("["+getClass().getSimpleName()+"] [add_app] : file_no [" + file_no+ "] end");
		logger.info("["+getClass().getSimpleName()+"] [add_app] : app_name [" + app_name+ "] end");
		logger.info("["+getClass().getSimpleName()+"] [add_app] : app_no [" + app_no+ "] end");

		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("OPTION_FILE_NO", file_no);
		paramMap.put("OPTION_APP_NAME", app_name);

		ModelAndView mav = new ModelAndView();
		mav.setView(mappingJacksonJsonView);

		try {
			int result_no =0;
			if (app_no.equals("")) {
				result_no = optionService.add_app(paramMap);
			}else {

				result_no = Integer.parseInt(app_no);
				paramMap.put("OPTION_APP_NO", result_no);
				optionService.update_app(paramMap);
			}
			mav.addObject("isSuccess", true);
			mav.addObject("result_no",result_no);

		}catch(Exception e) {
			e.printStackTrace();
			mav.addObject("isSuccess", false);
		}

		logger.debug("["+getClass().getSimpleName()+"] [add_app] end");
		return mav;
	}


	@RequestMapping(value="/add_option.isnet")
	public ModelAndView add_option(@RequestParam(value="app_no", required=false)String app_no,
			@RequestParam(value="option_name", required=false)String option_name,
			@RequestParam(value="option_no", required=false)String option_no,
			@RequestParam(value="default_value", required=false)String default_value,
			@RequestParam(value="option_comment", required=false)String option_comment){

		logger.debug("["+getClass().getSimpleName()+"] [add_option] start");
		logger.info("["+getClass().getSimpleName()+"] [add_option]  : app_no [" + app_no+ "] end");
		logger.info("["+getClass().getSimpleName()+"] [add_option]  : option_name [" + option_name+ "] end");
		logger.info("["+getClass().getSimpleName()+"] [add_option]  : option_no [" + option_no+ "] end");
		logger.info("["+getClass().getSimpleName()+"] [add_option]  : default_value [" + default_value+ "] end");
		logger.info("["+getClass().getSimpleName()+"] [add_option]  : option_comment [" + option_comment+ "] end");


		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("OPTION_APP_NO", app_no);
		paramMap.put("OPTION_KEY_NAME", option_name);
		paramMap.put("OPTION_DEFAULT_VALUE", default_value);
		paramMap.put("OPTION_COMMENT", option_comment);


		ModelAndView mav = new ModelAndView();
		mav.setView(mappingJacksonJsonView);
		int result_no =0;
		try {
			if(option_no.equals("")) {
				result_no = optionService.add_option(paramMap);
			}else {

				result_no = Integer.parseInt(option_no);
				paramMap.put("OPTION_NO", result_no);
				optionService.update_option(paramMap);
			}

			mav.addObject("result_no",result_no);
			mav.addObject("isSuccess", true);

		}catch(Exception e) {
			e.printStackTrace();
			mav.addObject("isSuccess", false);
		}

		logger.debug("["+getClass().getSimpleName()+"] [add_option] end");
		return mav;
	}

	@RequestMapping(value="/add_comment.isnet")
	public ModelAndView add_comment(@RequestParam(value="option_no", required=false)int option_no,
			@RequestParam(value="comment_value", required=false)String comment_value,
			@RequestParam(value="comment_memo", required=false)String comment_memo){

		logger.debug("["+getClass().getSimpleName()+"] [add_comment] start");
		logger.debug("["+getClass().getSimpleName()+"] [add_comment] : app_no [" + option_no+ "] end");
		logger.debug("["+getClass().getSimpleName()+"] [add_comment] : comment_value [" + comment_value+ "] end");
		logger.debug("["+getClass().getSimpleName()+"] [add_comment] : comment_memo [" + comment_memo+ "] end");


		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("OPTION_NO", option_no);
		paramMap.put("COMMENT_VALUE", comment_value);
		paramMap.put("COMMENT_MEMO", comment_memo);


		ModelAndView mav = new ModelAndView();
		mav.setView(mappingJacksonJsonView);

		try {
			int result_no = optionService.add_comment(paramMap);
			mav.addObject("result_no",result_no);
			mav.addObject("isSuccess", true);

		}catch(Exception e) {
			e.printStackTrace();
			mav.addObject("isSuccess", false);
		}

		logger.debug("["+getClass().getSimpleName()+"] [add_comment] end");
		return mav;
	}

	@RequestMapping(value="/delete_option_04_01.isnet")
	public ModelAndView delete_option_04_01(@RequestParam(value="option_no", required=false)int option_no,
			@RequestParam(value="comment_no", required=false)int comment_no){

		logger.debug("["+getClass().getSimpleName()+"] [delete_option_04_01] start");
		logger.info("["+getClass().getSimpleName()+"] [delete_option_04_01] : option_no [" + option_no+ "] end");
		logger.info("["+getClass().getSimpleName()+"] [delete_option_04_01] : comment_no [" + comment_no+ "] end");



		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("OPTION_NO", option_no);
		paramMap.put("COMMENT_NO", comment_no);

		ModelAndView mav = new ModelAndView();
		mav.setView(mappingJacksonJsonView);

		try {
			int result_no = optionService.delete_comment(paramMap);
			mav.addObject("result_no",result_no);
			mav.addObject("isSuccess", true);

		}catch(Exception e) {
			e.printStackTrace();
			mav.addObject("isSuccess", false);
		}

		logger.debug("["+getClass().getSimpleName()+"] [add_comment] end");
		return mav;
	}
	
}
