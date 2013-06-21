package com.isnet.mgr.view;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class CrmExcelView extends AbstractExcelView{

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 응답 컨텐츠 설정
		response.setHeader("Pragma", "no-cache");  
		response.setHeader("Cache-control", "private");  
		response.setDateHeader("Expires", 0);  
		response.setContentType("application/octet-stream");  
		response.setHeader("Content-Disposition", "filename="+URLEncoder.encode("방문이력.xls", "utf-8")); 	
		
		// 엑셀시트 생성
		HSSFSheet sheet = workbook.createSheet("방문이력");
		// title_row 생성
		HSSFRow header_row = sheet.createRow(1);
		HSSFCell header_cell_00 = header_row.createCell(0);
		HSSFCell header_cell_01 = header_row.createCell(1);
		HSSFCell header_cell_02 = header_row.createCell(2);
		HSSFCell header_cell_03 = header_row.createCell(3);
		HSSFCell header_cell_04 = header_row.createCell(4);
		HSSFCell header_cell_05 = header_row.createCell(5);
		HSSFCell header_cell_06 = header_row.createCell(6);
		header_cell_00.setCellValue("고객사");
		header_cell_01.setCellValue("고객명");
		header_cell_02.setCellValue("방문자");
		header_cell_03.setCellValue("마지막 방문일");
		header_cell_04.setCellValue("불견일수");
		header_cell_05.setCellValue("처리구분");
		header_cell_06.setCellValue("처리내용");
		
		List<Map<String, Object>> crmList = (List<Map<String, Object>>)model.get("rows");
		int loop = crmList.size();
		for(int index=0; index<loop; index++){
			Map<String, Object> crm = crmList.get(index);
			HSSFRow row = sheet.createRow(index+2);
			HSSFCell cell_00 = row.createCell(0);
			HSSFCell cell_01 = row.createCell(1);
			HSSFCell cell_02 = row.createCell(2);
			HSSFCell cell_03 = row.createCell(3);
			HSSFCell cell_04 = row.createCell(4);
			HSSFCell cell_05 = row.createCell(5);
			HSSFCell cell_06 = row.createCell(6);
			
			cell_00.setCellValue((String)crm.get("COMPANY_NAME"));
			cell_01.setCellValue((String)crm.get("CUSTOMER_NAME"));
			cell_02.setCellValue((String)crm.get("EMPLOYEE_NAME"));
			cell_03.setCellValue((String)crm.get("VISITED_DATE"));
			cell_04.setCellValue((String)crm.get("DATE_GAP"));
			cell_05.setCellValue((String)crm.get("VISITED_GUBUN"));
			cell_06.setCellValue((String)crm.get("VISITED_MEMO"));
			
		}		
	}
}
