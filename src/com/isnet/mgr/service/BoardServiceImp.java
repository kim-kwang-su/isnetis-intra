package com.isnet.mgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isnet.mgr.dao.BoardDao;

@Service
public class BoardServiceImp implements BoardService{

	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public void insertFAQ(Map<String, Object> paramMap) {
		boardDao.insert_faq_01(paramMap);
	}
	
	@Override
	public void insertQuestion(Map<String, Object> paramMap) {
		boardDao.insert_qna_01(paramMap);
	}
	
	@Override
	public void insertAnswer(Map<String, Object> paramMap) {
		boardDao.insert_qna_02(paramMap);
	}
	
	@Override
	public void insertForm(Map<String, Object> paramMap) {
		boardDao.insert_form_01(paramMap);
	}
	
	@Override
	public void deleteQuestion(Map<String, Object> paramMap) {
		boardDao.delete_qna_01(paramMap);
	}
	
	@Override
	public void deleteAnswer(Map<String, Object> paramMap) {
		boardDao.delete_qna_02(paramMap);
	}
	
	@Override
	public void deleteForm(Map<String, Object> paramMap) {
		boardDao.update_form_01(paramMap);
	}
	
	
	@Override
	public Map<String, Object> getFAQList(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [getFAQList] start");
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		paramMap.put("BEGIN_INDEX", begin_index);
		int total_rows = boardDao.select_faq_01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getFAQList] total_rows ["+total_rows+"]");
		
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getFAQList] total_pages ["+total_pages+"]");
		
		List<Map<String, Object>> list = boardDao.select_faq_02(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getFAQList] rows ["+list+"]");
		
		int records = list.size();
		logger.info("["+getClass().getSimpleName()+"] [getFAQList] records ["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", list);
		resultData.put("total", total_pages);

		
		logger.debug("["+getClass().getSimpleName()+"] [getFAQList] start");
		return resultData;
	}
	
	@Override
	public Map<String, Object> getFAQ(Map<String, Object> paramMap) {
		return boardDao.select_faq_03(paramMap);
	}
	
	@Override
	public Map<String, Object> getQnaQuestionList(Map<String, Object> paramMap) {
		
		logger.debug("["+getClass().getSimpleName()+"] [getQnaQuestionList] start");
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		paramMap.put("BEGIN_INDEX", begin_index);
		int total_rows = boardDao.select_qna_01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getQnaQuestionList] total_rows ["+total_rows+"]");
		
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getQnaQuestionList] total_pages ["+total_pages+"]");
		
		List<Map<String, Object>> list = boardDao.select_qna_02(paramMap);
		for(Map<String, Object> question : list){
			String question_title = (String)question.get("QUESTION_TITLE");
			String answer_cnt = String.valueOf(question.get("ANSWER_CNT"));
			question.put("QUESTION_TITLE", question_title + " ("+answer_cnt+")");
			int question_no = (Integer)question.get("QUESTION_NO");
			String link = "<a href='update_board_02.isnet?QUESTION_NO="+question_no+"'>수정</a> / <a href='del_board_01.isnet?QUESTION_NO="+question_no+"'>삭제</a>";
			question.put("URL_LINK", link);
		}
		logger.info("["+getClass().getSimpleName()+"] [getQnaQuestionList] rows ["+list+"]");
		
		int records = list.size();
		logger.info("["+getClass().getSimpleName()+"] [getQnaQuestionList] records ["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", list);
		resultData.put("total", total_pages);

		
		logger.debug("["+getClass().getSimpleName()+"] [getQnaQuestionList] end");
		return resultData;
	}
	
	@Override
	public Map<String, Object> getFormList(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [getFormList] start");
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		paramMap.put("BEGIN_INDEX", begin_index);
		int total_rows = boardDao.select_form_01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getFormList] total_rows ["+total_rows+"]");
		
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getFormList] total_pages ["+total_pages+"]");
		
		List<Map<String, Object>> list = boardDao.select_form_02(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getFormList] rows ["+list+"]");
		
		int records = list.size();
		logger.info("["+getClass().getSimpleName()+"] [getFormList] records ["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", list);
		resultData.put("total", total_pages);

		
		logger.debug("["+getClass().getSimpleName()+"] [getFormList] start");
		return resultData;
	}
	
	@Override
	public Map<String, Object> getFormInfo(Map<String, Object> paramMap) {
		return boardDao.select_form_03(paramMap);
	}
	
	@Override
	public Map<String, Object> getQuestion(Map<String, Object> paramMap) {
		return  boardDao.select_qna_03(paramMap);
	}
	
	@Override
	public List<Map<String, Object>> getAnswerList(Map<String, Object> paramMap) {
		return boardDao.select_qna_04(paramMap);
	}
	
	@Override
	public void updateFAQ(Map<String, Object> paramMap) {
		boardDao.update_faq_01(paramMap);
	}
	
	@Override
	public void updateQuestion(Map<String, Object> paramMap) {
		boardDao.update_qna_01(paramMap);
	}
	
	@Override
	public void updateAnswer(Map<String, Object> paramMap) {
		boardDao.update_qna_02(paramMap);
	}

	@Override
	public void insertSuggestion(Map<String, Object> paramMap) {
		boardDao.insert_sug_01(paramMap);
	}

	@Override
	public void insertComment(Map<String, Object> paramMap) {
		boardDao.insert_sug_02(paramMap);
	}

	@Override
	public void deleteSuggestion(Map<String, Object> paramMap) {
		boardDao.delete_sug_01(paramMap);
	}

	@Override
	public void deleteComment(Map<String, Object> paramMap) {
		boardDao.delete_sug_02(paramMap);
	}

	@Override
	public void updateSuggestion(Map<String, Object> paramMap) {
		boardDao.update_sug_01(paramMap);
	}

	@Override
	public void updateComment(Map<String, Object> paramMap) {
		boardDao.update_sug_02(paramMap);
	}

	@Override
	public Map<String, Object> getSuggestionList(Map<String, Object> paramMap) {
		logger.debug("["+getClass().getSimpleName()+"] [getSuggestionList] start");
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		int page = (Integer)paramMap.get("PAGE");
		int rows = (Integer)paramMap.get("ROWS");
		int begin_index = (page - 1) * rows;
		paramMap.put("BEGIN_INDEX", begin_index);
		int total_rows = boardDao.select_sug_01(paramMap);
		logger.info("["+getClass().getSimpleName()+"] [getSuggestionList] total_rows ["+total_rows+"]");
		
		int total_pages = (int)(Math.ceil((double)total_rows/rows));
		logger.info("["+getClass().getSimpleName()+"] [getSuggestionList] total_pages ["+total_pages+"]");
		
		List<Map<String, Object>> list = boardDao.select_sug_02(paramMap);
		for(Map<String, Object> question : list){
			String question_title = (String)question.get("SUGGESTION_TITLE");
			String answer_cnt = String.valueOf(question.get("COMMENTS_CNT"));
			question.put("SUGGESTION_TITLE", question_title + " ("+answer_cnt+")");
			int suggestion_no = (Integer)question.get("SUGGESTION_NO");
			String link = "<a href='update_board_04.isnet?SUGGESTION_NO="+suggestion_no+"'>수정</a> / <a href='del_board_04.isnet?SUGGESTION_NO="+suggestion_no+"'>삭제</a>";
			question.put("URL_LINK", link);
		}
		logger.info("["+getClass().getSimpleName()+"] [getSuggestionList] rows ["+list+"]");
		
		int records = list.size();
		logger.info("["+getClass().getSimpleName()+"] [getSuggestionList] records ["+records+"]");
		
		resultData.put("page", page);
		resultData.put("records", records);	
		resultData.put("rows", list);
		resultData.put("total", total_pages);

		
		logger.debug("["+getClass().getSimpleName()+"] [getSuggestionList] end");
		return resultData;
	}

	@Override
	public Map<String, Object> getSuggestion(Map<String, Object> paramMap) {
		return boardDao.select_sug_03(paramMap);
	}

	@Override
	public List<Map<String, Object>> getCommentList(Map<String, Object> paramMap) {
		return boardDao.select_sug_04(paramMap);
	}
	
	
	
	
}
