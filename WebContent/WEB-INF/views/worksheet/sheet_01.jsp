<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="업무일지"/>
<c:set var="two_depth" value="조회"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="sheet_01.isnet">업무일지</a> / <a href="sheet_01.isnet">조회</a> / <strong>일지 리스트</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<table id="sheet_list"></table>
			<div id="sheet_pager"></div>
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">

var customer_categories = '${customerValues}';
var employee_categories = '${employeeValues}';
var support_categories =  '${supportValues}';

$(document).ready(function(){
	$("#sheet_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:$(window).height() - 250,
		mtype:"POST",
		datatype: "json",
		url:"list_01.isnet",
	   	colNames:['SHEET_NO','EMPLOYEE_NO2', '일자', '작성자', '고객사','고객명','시작시간','종료시간','지원형태','지원내용'],						
	   	colModel:[	
			{name:'SHEET_NO', index:'SHEET_NO', hidden:true},
			{name:'EMPLOYEE_NO2', index:'EMPLOYEE_NO2', hidden:true},
	   		{name:'WRITE_DATE',index:'WRITE_DATE', width:120, align:"center", sortable:false, searchoptions:{sopt: ['eq'],dataInit:function(el){
	   																					setDatepicker($(el), null, 0);
	   																			}
			}},
	   		{name:'EMPLOYEE_NAME',index:'EMPLOYEE_NO', width:140, align:"left", sortable:false, stype:'select',searchoptions:{sopt:['eq'], value:employee_categories}},
	   		{name:'CUSTOMER_NAME',index:'CUSTOMER_NO', width:160, align:"left", sortable:false, stype:'select',searchoptions:{sopt:['eq', 'cn'], value:customer_categories}},
	   		{name:'CLIENT_NAME',index:'CLIENT_NAME', width:140, align:"left", sortable:false, search:false},
	   		{name:'START_TIME',index:'START_TIME', width:102, align:"center", sortable:false, search:false},
	   		{name:'END_TIME',index:'END_TIME', width:102, align:"center", sortable:false, search:false},	
	   		{name:'SUPPORT_TYPE_NAME',index:'SUPPORT_TYPE_NAME', width:150, align:"left" , sortable:false, search:false, },
	   		{name:'SHEET_TITLE',index:'SHEET_TITLE', width:250, align:"left" , sortable:false, search:false}
	   	],	
	    viewrecords: true,
	    // 제목
	    caption:" 업무일지 리스트",
	    // 페이지 네비게이션 설정
	    pager:"sheet_pager",
	   	page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="20"/>',
	   	rowList:[20,40,60],
	   	sortname:'<c:out value="${param.sidx}"/>',
	   	sortorder:'<c:out value="${param.sord}"/>',
		beforeProcessing: function(data, status, xhr){
	   		if(data && data.isSuccess){
	    		$.each(data.rows, function(index, row){
	    			row["EMPLOYEE_NO2"] = row["EMPLOYEE_NO"];
	    		});
	    	}
	   	},
	    onSelectRow:function(rowid){
	    	// 그리드의 row를 선택하면 상세보기 화면으로 이동합니다.
	    	var rowData = $(this).jqGrid('getRowData', rowid);
	    	var write_date = rowData["WRITE_DATE"];
	    	var employee_no = rowData["EMPLOYEE_NO2"];
	    	
	    	// 상세보기 화면에서 목록화면으로 돌아왔을때 리스트의 상태를 유지하기 위하여 필요한 값을 전달합니다.
	    	var sortname = $(this).jqGrid("getGridParam", "sortname");
	    	var sortorder = $(this).jqGrid("getGridParam", "sortorder");
	    	var page = $(this).jqGrid("getGridParam", "page");
	    	var rowNum = $(this).jqGrid("getGridParam", "rowNum");
	    	
	    	location.href = "sheet_02.isnet?write_date=" + write_date +"&employee_no="  + employee_no
	    					+ "&page="+page + "&rows=" + rowNum + "&sidx=" + sortname + "&sord=" + sortorder;
	    },
	    loadError:function(xhr, status, error){
	    	alert("업무일지를 조회 중 오류가 발생하였습니다.");
	    }
	}).navGrid("#sheet_pager",{edit:false,add:false,del:false}, {}, {}, {}, 
			   {multipleSearch:false, 
		        showQuery: false, closeOnEscape:true, closeAfterSearch:true}, {});	
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>