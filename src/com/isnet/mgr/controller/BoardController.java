package com.isnet.mgr.controller;

import java.io.File;
import java.io.FileOutputStream;
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
import com.isnet.mgr.domain.Rule;
import com.isnet.mgr.service.BoardService;
import com.isnet.mgr.view.FileDownloadView;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	Logger logger = Logger.getLogger(getClass());

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MappingJacksonJsonView jsonview;
	@Autowired
	private FileDownloadView fileDownloadView;
	
	@Autowired
	private CommonCodeBean commonCodeBean;
	
	@Value("#{config['form.savedpath']}")
	private String form_saved_path;
	
	@Value("#{config['pdf.savedpath']}")
	private String pdf_saved_path;
	
	@Value("#{config['board.savedpath']}")
	private String board_saved_path;
	
	@RequestMapping("/board_01.isnet")
	public ModelAndView board_01(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/board_01");
		
		List<Map<String, Object>> productList = commonCodeBean.getProductList();
		mav.addObject("productList", productList);
		
		return mav;
	}
	
	@RequestMapping("/board_02.isnet")
	public ModelAndView board_02(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/board_02");
		
		List<Map<String, Object>> productList = commonCodeBean.getProductList();
		mav.addObject("productList", productList);
		
		return mav;
	}
	
	@RequestMapping("/board_03.isnet")
	public ModelAndView board_03(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/board_03");
		return mav;
	}
	
	@RequestMapping("/board_04.isnet")
	public ModelAndView board_04(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/board_04");
		return mav;
	}
	
	@RequestMapping("/board_05.isnet")
	public ModelAndView board_05(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/board_05");
		return mav;
	}
	
	@RequestMapping("/board_06.isnet")
	public ModelAndView board_06(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/board_06");
		return mav;
	}
	
	@RequestMapping("/board_07.isnet")
	public ModelAndView board_07(){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/board_07");
		return mav;
	}
	
	@RequestMapping(value="/add_board_01.isnet", method=RequestMethod.POST)
	@ResponseBody
	public String add_board_01(@RequestParam(value="PRODUCT_NO")int product_no,
									@RequestParam(value="FAQ_QUESTION")String faq_question,
									@RequestParam(value="FAQ_ANSWER")String faq_answer,
									@RequestParam(value="FAQ_FILE", required=false)MultipartFile faq_file){
		logger.debug("["+getClass().getSimpleName()+"] [add_board_01] start");
		logger.info("["+getClass().getSimpleName()+"] [add_board_01] product_no["+product_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_board_01] faq_question["+faq_question+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_board_01] faq_answer["+faq_answer+"]");
		
		boolean isSuccess = false;
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("PRODUCT_NO", product_no);
			paramMap.put("FAQ_QUESTION", faq_question);
			paramMap.put("FAQ_ANSWER", faq_answer);
			paramMap.put("FAQ_HIT", 0);
			paramMap.put("CREATE_DATE", DateUtil.dateToString(CommonConstant.DATE_FORMAT_02));
			
			if(faq_file != null && !faq_file.isEmpty()){
				String originalFilename = faq_file.getOriginalFilename();
				String newFilename = System.currentTimeMillis() + "_"+ originalFilename;
				
				FileCopyUtils.copy(faq_file.getInputStream(), new FileOutputStream(new File(board_saved_path, newFilename)));
				paramMap.put("FILE_NAME", newFilename);
			}
			
			boardService.insertFAQ(paramMap);
			isSuccess = true;
			logger.info("["+getClass().getSimpleName()+"] [add_board_01] faq 등록 완료");
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_board_01] faq 등록 오류");
			isSuccess = false;
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_board_01] end");
		}
		
		return "{\"isSuccess\": "+isSuccess+"}";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add_board_02.isnet", method=RequestMethod.POST)
	public ModelAndView add_board_02(@RequestParam(value="PRODUCT_NO")int product_no,
									@RequestParam(value="QUESTION_TITLE")String question_title,
									@RequestParam(value="QUESTION_CONTENT")String question_content,
									HttpSession session){
		logger.debug("["+getClass().getSimpleName()+"] [add_board_02] start");
		logger.info("["+getClass().getSimpleName()+"] [add_board_02] product_no["+product_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_board_02] question_title["+question_title+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_board_02] question_content["+question_content+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("PRODUCT_NO", product_no);
			paramMap.put("QUESTION_TITLE", question_title);
			paramMap.put("QUESTION_CONTENT", question_content);
			paramMap.put("QUESTION_HIT", 0);
			paramMap.put("CREATE_DATE", DateUtil.dateToString(CommonConstant.DATE_FORMAT_02));
			
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			paramMap.put("QUESTION_WRITER", employee_no);
			
			boardService.insertQuestion(paramMap);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [add_board_02] qna 등록 완료");
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_board_02] qna 등록 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_board_02] end");
		}
	
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add_board_03.isnet", method=RequestMethod.POST)
	public ModelAndView add_board_03(@RequestParam(value="QUESTION_NO")int question_no,
									@RequestParam(value="ANSWER_CONTENT")String answer_content,
									HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_board_03] start");
		logger.info("["+getClass().getSimpleName()+"] [add_board_03] question_no["+question_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_board_03] answer_content["+answer_content+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("QUESTION_NO", question_no);
			paramMap.put("ANSWER_CONTENT", answer_content);
			paramMap.put("CREATE_DATE", DateUtil.dateToString(CommonConstant.DATE_FORMAT_02));
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			paramMap.put("ANSWER_WRITER", employee_no);
			
			boardService.insertAnswer(paramMap);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [add_board_03] question_no["+question_no+"] 답글 등록 완료");
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_board_03] question_no["+question_no+"] 답글 등록 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_board_03] end");
		}
		return mav;
	}

	@RequestMapping(value="/add_board_04.isnet", method=RequestMethod.POST)
	@ResponseBody
	public String add_board_04(@RequestParam("FORM_NAME")String form_name,
								@RequestParam(value="CREATE_DATE", required=false)String create_date,
								@RequestParam(value="FORM_DESC", required=false)String form_desc,
								@RequestParam("FORM_FILE")MultipartFile form_file,
								@RequestParam("IS_FORM")String is_form){
		logger.debug("["+getClass().getSimpleName()+"] [add_board_04] start");
		logger.info("["+getClass().getSimpleName()+"] [add_board_04] form_name["+form_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_board_04] create_date["+create_date+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_board_04] form_desc["+form_desc+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_board_04] is_form["+is_form+"]");
		boolean isSuccess = false;
		
		try{
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("FORM_NAME", form_name);
			paramMap.put("FORM_DESC", form_desc);
			paramMap.put("IS_FORM", is_form);
			paramMap.put("IS_USED", "Y");
			if("Y".equals(is_form)){
				paramMap.put("CREATE_DATE", DateUtil.dateToString("yyyy-MM-dd"));
			}else{
				paramMap.put("CREATE_DATE", create_date);
			}
			
			if(form_file != null && !form_file.isEmpty()){
				String originalFilename = form_file.getOriginalFilename();
				String newFilename = System.currentTimeMillis() + "_"+ originalFilename;

				if("Y".equals(is_form)){
					FileCopyUtils.copy(form_file.getInputStream(), new FileOutputStream(new File(form_saved_path, newFilename)));
					paramMap.put("FILE_NAME", newFilename);
				
				}else{
					FileCopyUtils.copy(form_file.getInputStream(), new FileOutputStream(new File(pdf_saved_path, originalFilename)));
					paramMap.put("FILE_NAME", originalFilename);
				}
				
			}
			
			boardService.insertForm(paramMap);
			isSuccess = true;
			
			logger.info("["+getClass().getSimpleName()+"] [add_board_04] 서식 등록 완료");
		}catch(Exception e){
			e.printStackTrace();
			isSuccess = false;
			logger.error("["+getClass().getSimpleName()+"] [add_board_04] 서식 등록 오류");
		}finally{
			
		}
		return "{\"isSuccess\": "+isSuccess+"}";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add_board_05.isnet", method=RequestMethod.POST)
	public ModelAndView add_board_05(@RequestParam(value="CATEGORY_NAME")String category_name,
									@RequestParam(value="SUGGESTION_TITLE")String suggestion_title,
									@RequestParam(value="SUGGESTION_CONTENT")String suggestion_content,
									HttpSession session){
		logger.debug("["+getClass().getSimpleName()+"] [add_board_05] start");
		logger.info("["+getClass().getSimpleName()+"] [add_board_05] category_name["+category_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_board_05] suggestion_title["+suggestion_title+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_board_05] suggestion_content["+suggestion_content+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("CATEGORY_NAME", category_name);
			paramMap.put("SUGGESTION_TITLE", suggestion_title);
			paramMap.put("SUGGESTION_CONTENT", suggestion_content);
			paramMap.put("CREATE_DATE", DateUtil.dateToString(CommonConstant.DATE_FORMAT_02));
			
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			paramMap.put("SUGGESTION_WRITER", employee_no);
			
			boardService.insertSuggestion(paramMap);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [add_board_05] 제안 등록 완료");
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_board_05] 제안 등록 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_board_05] end");
		}
	
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/add_board_06.isnet", method=RequestMethod.POST)
	public ModelAndView add_board_06(@RequestParam(value="SUGGESTION_NO")int suggestion_no,
									@RequestParam(value="COMMENT_CONTENT")String comment_content,
									HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [add_board_06] start");
		logger.info("["+getClass().getSimpleName()+"] [add_board_06] suggestion_no["+suggestion_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [add_board_06] comment_content["+comment_content+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("SUGGESTION_NO", suggestion_no);
			paramMap.put("COMMENT_CONTENT", comment_content);
			paramMap.put("CREATE_DATE", DateUtil.dateToString(CommonConstant.DATE_FORMAT_02));
			Map<String, Object> sessionUser = (Map<String, Object>)session.getAttribute("LOGIN_USER");
			int employee_no = Integer.parseInt(String.valueOf(sessionUser.get("EMPLOYEE_NO")));
			paramMap.put("COMMENT_WRITER", employee_no);
			
			boardService.insertComment(paramMap);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [add_board_06] suggestion_no["+suggestion_no+"] 의견 등록 완료");
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [add_board_06] suggestion_no["+suggestion_no+"] 의견 등록 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [add_board_06] end");
		}
		return mav;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list_01.isnet", method=RequestMethod.POST)
	public ModelAndView list_01(@RequestParam(value="page", defaultValue="1")int page,
								@RequestParam(value="rows", defaultValue="10")int rows,
								@RequestParam(value="sidx", required=false)String sidx,
								@RequestParam(value="sord", required=false)String sord,
								@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
								@RequestParam(value="filters", required=false)String filters){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_01] start");
		logger.info("["+getClass().getSimpleName()+"] [list_01] page["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] rows["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] sidx["+sidx+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] sord["+sord+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] _search["+_search+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_01] filters["+filters+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
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
			
			Map<String, Object> resultData = boardService.getFAQList(paramMap);
			mav.addAllObjects(resultData);
			mav.addObject("isSuccess", true);
		}catch (Exception e) {
			e.printStackTrace();
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_01] stop");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/list_02.isnet", method=RequestMethod.POST)
	public ModelAndView list_02(@RequestParam(value="page", defaultValue="1")int page,
								@RequestParam(value="rows", defaultValue="10")int rows,
								@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
								@RequestParam(value="searchField", required=false)String searchField,
								@RequestParam(value="searchString", required=false)String searchString,
								@RequestParam(value="searchOper", required=false)String searchOper,
								HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_02] start");
		logger.info("["+getClass().getSimpleName()+"] [list_02] page["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_02] rows["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_02] _search["+_search+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_02] searchField["+searchField+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_02] searchString["+searchString+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_02] searchOper["+searchOper+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("PAGE", page);
			paramMap.put("ROWS", rows);
			if(_search){
				paramMap.put("_SEARCH", _search);
				paramMap.put("searchField", searchField);
				paramMap.put("searchString", searchString);
				paramMap.put("searchOper", searchOper);				
			}			
			 
			Map<String, Object> resultData = boardService.getQnaQuestionList(paramMap);
			mav.addAllObjects(resultData);
			mav.addObject("isSuccess", true);
			
			logger.info("["+getClass().getSimpleName()+"] [list_02] qna 조회 완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_02] qna 조회 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_02] end");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/list_03.isnet", method=RequestMethod.GET)
	public ModelAndView list_03(@RequestParam(value="QUESTION_NO")int question_no){
		logger.debug("["+getClass().getSimpleName()+"] [list_03] start");
		logger.info("["+getClass().getSimpleName()+"] [list_03] question_no["+question_no  +"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		try{
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("QUESTION_NO", question_no);
			
			List<Map<String, Object>> answerList = boardService.getAnswerList(paramMap);
			for(Map<String, Object> answer : answerList){
				String content = (String)answer.get("ANSWER_CONTENT");
				content = content.replaceAll("\n", "<br />");
				answer.put("ANSWER_CONTENT_NEW_LINE", content);
			}
			mav.addObject("answerList", answerList);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [list_03] question_no["+question_no+"] answer 조회 완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_03] question_no["+question_no+"] answer 조회 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_03] end");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/list_04.isnet", method=RequestMethod.POST)
	public ModelAndView list_04(@RequestParam(value="page", defaultValue="1")int page,
								@RequestParam(value="rows", defaultValue="10")int rows,
								@RequestParam(value="IS_FORM", defaultValue="Y")String is_form){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_04] start");
		logger.info("["+getClass().getSimpleName()+"] [list_04] page["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] rows["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_04] is_form["+is_form+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("PAGE", page);
			paramMap.put("ROWS", rows);
			paramMap.put("IS_FORM", is_form);
			 
			Map<String, Object> resultData = boardService.getFormList(paramMap);
			mav.addAllObjects(resultData);
			mav.addObject("isSuccess", true);
			
			logger.info("["+getClass().getSimpleName()+"] [list_04] form 조회 완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_04] form 조회 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_04] form");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/list_05.isnet", method=RequestMethod.POST)
	public ModelAndView list_05(@RequestParam(value="page", defaultValue="1")int page,
								@RequestParam(value="rows", defaultValue="10")int rows,
								@RequestParam(value="_search", required=false, defaultValue="false")boolean _search,
								@RequestParam(value="searchField", required=false)String searchField,
								@RequestParam(value="searchString", required=false)String searchString,
								@RequestParam(value="searchOper", required=false)String searchOper,
								HttpSession session){
		
		logger.debug("["+getClass().getSimpleName()+"] [list_05] start");
		logger.info("["+getClass().getSimpleName()+"] [list_05] page["+page+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_05] rows["+rows+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_05] _search["+_search+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_05] searchField["+searchField+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_05] searchString["+searchString+"]");
		logger.info("["+getClass().getSimpleName()+"] [list_05] searchOper["+searchOper+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("PAGE", page);
			paramMap.put("ROWS", rows);
			if(_search){
				paramMap.put("_SEARCH", _search);
				paramMap.put("searchField", searchField);
				paramMap.put("searchString", searchString);
				paramMap.put("searchOper", searchOper);				
			}			
			 
			Map<String, Object> resultData = boardService.getSuggestionList(paramMap);
			mav.addAllObjects(resultData);
			mav.addObject("isSuccess", true);
			
			logger.info("["+getClass().getSimpleName()+"] [list_05] 제안 조회 완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_05] 제안 조회 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_05] end");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/list_06.isnet", method=RequestMethod.GET)
	public ModelAndView list_06(@RequestParam(value="SUGGESTION_NO")int suggestion_no){
		logger.debug("["+getClass().getSimpleName()+"] [list_06] start");
		logger.info("["+getClass().getSimpleName()+"] [list_06] suggestion_no["+suggestion_no  +"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		try{
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("SUGGESTION_NO", suggestion_no);
			
			List<Map<String, Object>> commentList = boardService.getCommentList(paramMap);
			for(Map<String, Object> comment : commentList){
				String content = (String)comment.get("COMMENT_CONTENT");
				content = content.replaceAll("\n", "<br />");
				comment.put("COMMENT_CONTENT_NEW_LINE", content);
			}
			mav.addObject("commentList", commentList);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [list_06] suggestion_no["+suggestion_no+"] answer 조회 완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [list_06] suggestion_no["+suggestion_no+"] answer 조회 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [list_06] end");
		}
		
		return mav;
	}
	
	@RequestMapping("/detail_board_01.isnet")
	public ModelAndView detail_board_01(@RequestParam(value="QUESTION_NO")int question_no){
		logger.debug("["+getClass().getSimpleName()+"] [detail_board_01] start");
		logger.info("["+getClass().getSimpleName()+"] [detail_board_01] question_no["+question_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("QUESTION_NO", question_no);
			
			Map<String, Object> question = boardService.getQuestion(paramMap);
			String content = (String)question.get("QUESTION_CONTENT");
			content = content.replaceAll("\n", "<br />");
			question.put("QUESTION_CONTENT_NEW_LINE", content);
			mav.addObject("question", question);
			mav.addObject("isSuccess", true);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [detail_board_01] qna 상세  조회 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [detail_board_01] end");
		}
		
		return mav;
	}
	
	@RequestMapping("/detail_board_02.isnet")
	public ModelAndView detail_board_02(@RequestParam(value="SUGGESTION_NO")int suggestion_no){
		logger.debug("["+getClass().getSimpleName()+"] [detail_board_02] start");
		logger.info("["+getClass().getSimpleName()+"] [detail_board_02] suggestion_no["+suggestion_no+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("SUGGESTION_NO", suggestion_no);
			
			Map<String, Object> suggestion = boardService.getSuggestion(paramMap);
			String content = (String)suggestion.get("SUGGESTION_CONTENT");
			content = content.replaceAll("\n", "<br />");
			suggestion.put("SUGGESTION_CONTENT_NEW_LINE", content);
			mav.addObject("suggestion", suggestion);
			mav.addObject("isSuccess", true);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [detail_board_02] 사내제안  조회 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [detail_board_02] end");
		}
		
		return mav;
	}
	
	
	@RequestMapping(value="/update_board_01.isnet", method=RequestMethod.POST)
	@ResponseBody
	public String update_board_01(@RequestParam(value="FAQ_NO")int faq_no,
										@RequestParam(value="PRODUCT_NO")int product_no,
										@RequestParam(value="FAQ_QUESTION")String faq_question,
										@RequestParam(value="FAQ_ANSWER")String faq_answer,
										@RequestParam(value="FAQ_FILE", required=false)MultipartFile faq_file){
		logger.debug("["+getClass().getSimpleName()+"] [update_board_01] start");
		logger.info("["+getClass().getSimpleName()+"] [update_board_01] faq_no["+faq_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_board_01] product_no["+product_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_board_01] faq_question["+faq_question+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_board_01] fag_answer["+faq_answer+"]");
		
		boolean isSuccess = false;
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("FAQ_NO", faq_no);
			paramMap.put("PRODUCT_NO", product_no);
			paramMap.put("FAQ_QUESTION", faq_question);
			paramMap.put("FAQ_ANSWER", faq_answer);
			
			if(faq_file != null && !faq_file.isEmpty()){
				String originalFilename = faq_file.getOriginalFilename();
				String newFilename = System.currentTimeMillis() + "_"+ originalFilename;
				
				FileCopyUtils.copy(faq_file.getInputStream(), new FileOutputStream(new File(board_saved_path, newFilename)));
				paramMap.put("FILE_NAME", newFilename);
			}
			
			boardService.updateFAQ(paramMap);
			isSuccess = true;
			logger.info("["+getClass().getSimpleName()+"] [update_board_01] faq_no["+faq_no+"] faq 수정완료");
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_board_01] faq_no["+faq_no+"] faq 수정 오류");
			isSuccess = false;
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_board_01] end");
		}
		
		return "{\"isSuccess\": "+isSuccess+"}";
	}
	
	@RequestMapping(value="/update_board_02.isnet", method=RequestMethod.POST)
	public ModelAndView update_board_02(@RequestParam(value="QUESTION_NO")int question_no,
										@RequestParam(value="PRODUCT_NO")int product_no,
										@RequestParam(value="QUESTION_TITLE")String question_title,
										@RequestParam(value="QUESTION_CONTENT")String question_content){
		logger.debug("["+getClass().getSimpleName()+"] [update_board_02] start");
		logger.info("["+getClass().getSimpleName()+"] [update_board_02] question_no["+question_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_board_02] product_no["+product_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_board_02] question_title["+question_title+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_board_02] question_content["+question_content+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("QUESTION_NO", question_no);
			paramMap.put("PRODUCT_NO", product_no);
			paramMap.put("QUESTION_TITLE", question_title);
			paramMap.put("QUESTION_CONTENT", question_content);
			
			boardService.updateQuestion(paramMap);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [update_board_02] question_no["+question_no+"] qna 수정완료");
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_board_02] question_no["+question_no+"] qna 수정 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_board_02] end");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/update_board_03.isnet", method=RequestMethod.POST)
	public ModelAndView update_board_03(@RequestParam(value="ANSWER_NO")int answer_no,
										@RequestParam(value="ANSWER_CONTENT")String answer_content){
		logger.debug("["+getClass().getSimpleName()+"] [update_board_03] start");
		logger.info("["+getClass().getSimpleName()+"] [update_board_03] answer_no["+answer_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_board_03] answer_content["+answer_content+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ANSWER_NO", answer_no);
			paramMap.put("ANSWER_CONTENT", answer_content);
			
			boardService.updateAnswer(paramMap);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [update_board_03] answer_no["+answer_no+"] qna 수정완료");
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_board_03] answer_no["+answer_no+"] qna 수정 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_board_03] end");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/update_board_04.isnet", method=RequestMethod.POST)
	public ModelAndView update_board_04(@RequestParam(value="SUGGESTION_NO")int suggestion_no,
										@RequestParam(value="CATEGORY_NAME")String category_name,
										@RequestParam(value="SUGGESTION_TITLE")String suggestion_title,
										@RequestParam(value="SUGGESTION_CONTENT")String suggestion_content){
		logger.debug("["+getClass().getSimpleName()+"] [update_board_04] start");
		logger.info("["+getClass().getSimpleName()+"] [update_board_04] suggestion_no["+suggestion_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_board_04] category_name["+category_name+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_board_04] suggestion_title["+suggestion_title+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_board_04] suggestion_content["+suggestion_content+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("SUGGESTION_NO", suggestion_no);
			paramMap.put("CATEGORY_NAME", category_name);
			paramMap.put("SUGGESTION_TITLE", suggestion_title);
			paramMap.put("SUGGESTION_CONTENT", suggestion_content);
			
			boardService.updateSuggestion(paramMap);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [update_board_04] suggestion_no["+suggestion_no+"] 사내제안 수정완료");
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_board_04] suggestion_no["+suggestion_no+"] 사내제안 수정 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_board_04] end");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/update_board_05.isnet", method=RequestMethod.POST)
	public ModelAndView update_board_05(@RequestParam(value="COMMENT_NO")int comment_no,
										@RequestParam(value="COMMENT_CONTENT")String comment_content){
		logger.debug("["+getClass().getSimpleName()+"] [update_board_05] start");
		logger.info("["+getClass().getSimpleName()+"] [update_board_05] comment_no["+comment_no+"]");
		logger.info("["+getClass().getSimpleName()+"] [update_board_05] comment_content["+comment_content+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("COMMENT_NO", comment_no);
			paramMap.put("COMMENT_CONTENT", comment_content);
			
			boardService.updateComment(paramMap);
			mav.addObject("isSuccess", true);
			logger.info("["+getClass().getSimpleName()+"] [update_board_05] comment_no["+comment_no+"] 의견 수정완료");
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [update_board_05] comment_no["+comment_no+"] 의견 수정 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [update_board_05] end");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/del_board_01.isnet")
	public ModelAndView del_board_01(@RequestParam("QUESTION_NO")int question_no){
		logger.debug("["+getClass().getSimpleName()+"] [del_board_01] start");
		logger.info("["+getClass().getSimpleName()+"] [del_board_01] question_no["+question_no+"]");
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("QUESTION_NO", question_no);
			boardService.deleteQuestion(paramMap);
			mav.addObject("isSuccess", true);
			
			logger.info("["+getClass().getSimpleName()+"] [del_board_01] question_no["+question_no+"] 삭제완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [del_board_01] question_no["+question_no+"] 삭제 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [del_board_01] end");
		}
		
		return mav;
	}

	@RequestMapping(value="/del_board_02.isnet")
	public ModelAndView del_board_02(@RequestParam("ANSWER_NO")int answer_no){
		logger.debug("["+getClass().getSimpleName()+"] [del_board_02] start");
		logger.info("["+getClass().getSimpleName()+"] [del_board_02] answer_no["+answer_no+"]");
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ANSWER_NO", answer_no);
			boardService.deleteAnswer(paramMap);
			mav.addObject("isSuccess", true);
			
			logger.info("["+getClass().getSimpleName()+"] [del_board_02] answer_no["+answer_no+"] 삭제완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [del_board_02] answer_no["+answer_no+"] 삭제 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [del_board_02] end");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/del_board_03.isnet")
	public ModelAndView del_board_03(@RequestParam("FORM_NO")int form_no){
		logger.debug("["+getClass().getSimpleName()+"] [del_board_03] start");
		logger.info("["+getClass().getSimpleName()+"] [del_board_03] form_no["+form_no+"]");
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("FORM_NO", form_no);
			paramMap.put("IS_USED", "N");
			boardService.deleteForm(paramMap);
			mav.addObject("isSuccess", true);
			
			logger.info("["+getClass().getSimpleName()+"] [del_board_03] form_no["+form_no+"] 서식 삭제완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [del_board_03] form_no["+form_no+"] 서식 삭제 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [del_board_03] end");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/del_board_04.isnet")
	public ModelAndView del_board_04(@RequestParam("SUGGESTION_NO")int suggestion_no){
		logger.debug("["+getClass().getSimpleName()+"] [del_board_04] start");
		logger.info("["+getClass().getSimpleName()+"] [del_board_04] suggestion_no["+suggestion_no+"]");
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("SUGGESTION_NO", suggestion_no);
			boardService.deleteSuggestion(paramMap);
			mav.addObject("isSuccess", true);
			
			logger.info("["+getClass().getSimpleName()+"] [del_board_04] suggestion_no["+suggestion_no+"] 사내제안 삭제완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [del_board_04] suggestion_no["+suggestion_no+"] 사내제안 삭제 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [del_board_04] end");
		}
		
		return mav;
	}

	@RequestMapping(value="/del_board_05.isnet")
	public ModelAndView del_board_05(@RequestParam("COMMENT_NO")int comment_no){
		logger.debug("["+getClass().getSimpleName()+"] [del_board_05] start");
		logger.info("["+getClass().getSimpleName()+"] [del_board_05] comment_no["+comment_no+"]");
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonview);
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("COMMENT_NO", comment_no);
			boardService.deleteComment(paramMap);
			mav.addObject("isSuccess", true);
			
			logger.info("["+getClass().getSimpleName()+"] [del_board_05] comment_no["+comment_no+"] 의견 삭제완료");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [del_board_05] comment_no["+comment_no+"] 의견 삭제 오류");
			mav.addObject("isSuccess", false);
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [del_board_05] end");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/download_file.isnet", method=RequestMethod.GET)
	public ModelAndView download_file(@RequestParam("NO")int no,
									@RequestParam("gubun")String gubun){
		logger.debug("["+getClass().getSimpleName()+"] [download_file] start");
		logger.info("["+getClass().getSimpleName()+"] [download_file] no["+no+"]");
		logger.info("["+getClass().getSimpleName()+"] [download_file] gubun["+gubun+"]");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(fileDownloadView);
		try{
			if("faq".equals(gubun)){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("FAQ_NO", no);
				Map<String, Object> faq = boardService.getFAQ(paramMap);
				String file_name = (String)faq.get("FILE_NAME");
				
				mav.addObject("downloadFile", new File(board_saved_path, file_name));
				mav.addObject("isTimeIncluded", true);
				
			}else if("form".equals(gubun)){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("FORM_NO", no);
				Map<String, Object> formInfo = boardService.getFormInfo(paramMap);
				String file_name = (String)formInfo.get("FILE_NAME");
				
				mav.addObject("downloadFile", new File(form_saved_path, file_name));
				mav.addObject("isTimeIncluded", true);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("["+getClass().getSimpleName()+"] [downolad_file] 번호["+no+"]번 파일 다운로드 중 오류 발생");
		}finally{
			logger.debug("["+getClass().getSimpleName()+"] [down_issuefile] end");
		}
		return mav;
	}
}
