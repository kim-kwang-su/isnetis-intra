<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="업무일지"/>
<c:set var="two_depth" value="내 일지 보기"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="sheet_01.isnet">업무일지</a> / <strong>내 일지 보기</strong></div>			
			<!-- 컨텐츠 표시부 시작 -->
			
			<!-- 검색 조건 -->
			<div id="searchbox" style="margin-bottom:5px;padding:5px 30px;margin:10px 0 0 0;border:1px solid #cecece;">
				<strong>작성일자 : </strong>
				<input style="width:100px;" type="text" id="start_date" name="START_DATE" readonly="readonly"/> ~ 
				<input style="width:100px;" type="text" id="end_date" name="END_DATE" readonly="readonly"/>
				<button style="padding:0px 5px" class="icon" id="search_bt">조 회</button>
			</div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>모듈 상세 정보</h3> -->
			<div id="detail_form" class="mt10">
			<form id="worksheet_form"></form>
			</div>				
				
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">

$(document).ready(function(){
	setDatepicker("#start_date", null, null);
	$("#start_date").change(function(event){
		event.preventDefault();
		setDatepicker("#end_date", $("#start_date").val(), null);
	});
	
	var now = new Date();
	$("#end_date").val($.datepicker.formatDate('yy-mm-dd', now));
	var prev_date = now.getDate() - 7;
	now.setDate(prev_date);
	$("#start_date").val($.datepicker.formatDate('yy-mm-dd', now));
	
	$("#search_bt").click(function(event){
		event.preventDefault();
		$("#worksheet_form").html("");
		if($("#start_date").val() == ""){
			simple_popup("안내", "시작일을 지정하세요.");
			$("#start_date").focus();
			return false;
		}
		if($("#end_date").val() == ""){
			simple_popup("안내", "종료일을 지정하세요");
			$("#end_date").focus();
			return false;
		}
		
		$.ajax({
			type:"GET",
			url:"list_07.isnet",
			dataType:"json",
			data:{START_DATE:$("#start_date").val(), END_DATE:$("#end_date").val()},
			success:function(data, status){
				if(data && data.isSuccess){
					var sheetList = data["sheetList"];
					var html = "";
					$.each(sheetList, function(index, sheet){
						
						html += "<fieldset class='mt10'>";
						html += "<legend>"+sheet["WRITE_DATE"]+"</legend>";
						html += "<p>";
						html += "<label>고객명</label><input readonly='readonly' type='text' value='"+sheet["CUSTOMER_NAME"]+"'>";
						html += "</p>";
						html += "<p>";
						html += "<label>제목</label><input readonly='readonly' type='text' style='width:400px;' value='"+sheet["SHEET_CONTENT"]+"'>";
						html += "</p>";
						html += "<p>";
						html += "<label>내용</label><textarea rows='5' readonly='readonly' >"+sheet["SHEET_CONTENT"]+"</textarea>";
						html += "</p>";
						html += "</fieldset>";
					});					
					$("#worksheet_form").append(html);
					
				}else{
					simple_popup("안내", "내 일지 조회 중 오류가 발생하였습니다.");
				}
			},
			error:function(){
				simple_popup("안내", "내 일지 조회 중 오류가 발생하였습니다.");
			}
		});
		
	});
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>