<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="업무일지"/>
<c:set var="two_depth" value="작성현황"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="sheet_01.isnet">업무일지</a> / <strong>작성현황</strong></div>			
			<!-- 컨텐츠 표시부 시작 -->
			
			<!-- 검색 조건 -->
			<div id="searchbox" style="margin-bottom:5px;padding:5px 30px;margin:10px 0 0 0;border:1px solid #cecece;">
				<strong>부서명 : </strong>
				<select id="dept_name" name="DEPT_NAME" style="margin-right: 20px;">
					<option value="">선택하여 주세요.</option>
					<c:forEach var="dept" items="${deptList }">
						<option value="${dept.DEPT_NO}">${dept.DEPT_NAME}</option>
					</c:forEach>
				</select>
			
				<strong>작성일자 : </strong>
				<input style="width:100px;" type="text" id="start_date" name="START_DATE" readonly="readonly"/> ~ 
				<input style="width:100px;" type="text" id="end_date" name="END_DATE" readonly="readonly"/>
				
				<button style="padding:0px 5px" class="icon" id="search_bt">조 회</button>
			</div>
			<!-- 검색 조건 -->
			<div id="grid_wrap">
				<table id="sheet_list"></table>
			</div>
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">

$(document).ready(function(){
	setDatepicker("#start_date", null, null);
	$("#start_date").change(function(){
		setDatepicker("#end_date", $("#start_date").val(),null);
	});
	
	
	$("#search_bt").click(function(event) {
		event.preventDefault();
		if($("#dept_name").val() =="") {
			simple_popup("안내","부서명을 선택하여 주세요.");
			return;
		}
		if($("#start_date").val() =="") {
			simple_popup("안내"," 작성일의 시작일을 입력하여 주세요.");
			return;
		}

		if($("#end_date").val() =="") {
			simple_popup("안내","작성일의 완료일을 입력하여 주세요.");
			return;
		}
		
		
		$.ajax({
            type: "POST",
            url: "list_03.isnet",
            data: {"dept_no":$("#dept_name").val(),"start_date" : $("#start_date").val(), "end_date" : $("#end_date").val()},
            dataType: "json",
			success: function(result, status){
            	$("#grid_wrap").html('<table id="sheet_list"></table>');	 
			
				var employeeNames = result.employeeNames;
            	var colNames = new Array(employeeNames.length + 2);
            	var colModel = new Array(employeeNames.length + 2);
            	
            	colNames[0] = "SEQ";
            	colNames[1] = "작성일자";
            	colModel[0] = {name:'SEQ',index:'SEQ', hidden:true};
            	colModel[1] = {name:'WRITE_DATE',index:'WRITE_DATE', width:80, align:"center", sortable:false, search:false};
            	$.each(employeeNames, function(index, employeeName){
            		colNames[index+2] = employeeName;
            		colModel[index+2] = {name:'MEMBER'+(index+1) ,index:'MEMBER'+(index+1), width:80, align:"center", sortable:false, search:false};
            	});				
				
            	$("#sheet_list").jqGrid({
            		jsonReader: {
            			repeatitems : false,															
            			id: "0"																			
            		},
            		height:400,
            		mtype:"POST",
            		datatype: "local",
            	   	colNames:colNames,						
            	   	colModel:colModel,
            	   	viewrecords: true,
            	    // 제목
            	    caption:" 업무일지 작성현황",
            	    loadError:function(xhr, status, error){
            	    	alert("작성현황  조회 중 오류가 발생하였습니다.");
            	    }
            	});	
            	
            	$.each(result.rows, function(index, obj){
        			$("#sheet_list").jqGrid('addRowData',index, obj);
        		});
             }
		 });		
	});
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>