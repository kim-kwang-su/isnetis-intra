<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="버전관리"/>
<c:set var="two_depth" value="최신 모듈변경이력"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="module_01.isnet">버전관리</a> / <strong>최신 모듈변경이력</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>최근 모듈변경 내역</h3> -->
			<table id="module_list"></table>
			<div id="module_pager"></div>
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	var customer_categories = '${customerValues}';
	var product_categories = '${productValues}';
	var employee_categories = '${employeeValues}';
	
	$("#module_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:$(window).height() - 250,
		mtype:"POST",
		datatype: "json",
		url:"list_01.isnet",
	   	colNames:['MODULE_HISTORY_NO', 'MODULE_NO','변경일자','제품명', '모듈명', '설치경로', '변경내용', '적용사이트','최종 버젼', '담당자명'],						
	   	colModel:[	
			{name:'MODULE_HISTORY_NO', index:'MODULE_HISTORY_NO', hidden:true},	 
			{name:'MODULE_NO', index:'MODULE_NO', hidden:true},	   	          		
	   		{name:'UPDATE_DATE',index:'UPDATE_DATE', width:75, align:"center", search:false},
	   		{name:'PRODUCT_NAME',index:'B.PRODUCT_NO', width:75, align:"center",  stype:'select', searchoptions:{sopt:['eq'], value:product_categories}},
	   		{name:'MODULE_NAME',index:'B.MODULE_NAME', width:150, align:"left", searchoptions:{sopt:['eq', 'cn']}},
	   		{name:'FOLDER_PATH',index:'E.FOLDER_PATH', width:150, align:"left", searchoptions:{sopt:['eq']}},
	   		{name:'UPDATE_MEMO',index:'UPDATE_MEMO', width:400, align:"left", sortable:false, searchoptions:{sopt:['cn']}},
	   		{name:'CUSTOMER_NAME',index:'CUSTOMER_NO', width:150, align:"center", stype:'select', searchoptions:{sopt:['eq'], value:customer_categories}},
	   		{name:'FILE_VERSION',index:'FILE_VERSION', width:80, align:"center", sortable:false, search:false},
	   		{name:'EMPLOYEE_NAME',index:'A.EMPLOYEE_NO', width:75, align:"center", sortable:true, stype:'select', searchoptions:{sopt:['eq'], value:employee_categories}}	
	   	],	
	    viewrecords: true,
	    // 제목
	    caption:"모듈 변경 이력",
	    // 페이지 네비게이션 설정
	    pager:"module_pager",
	   	page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="10"/>',
	   	rowList:[10,20,30],
	   	sortname:'<c:out value="${param.sidx}"/>',
	   	sortorder:'<c:out value="${param.sord}"/>',
		loadComplete:function(data){
	   		var records = data["records"];
	   		if(records == 0){
	   			simple_popup("안내", "검색조건에 해당하는 데이터가 존재하지 않습니다.");
	   		}
	   	},
	    onSelectRow:function(rowid){
	    	// 모듈 상세보기 화면으로 이동합니다. 상세보기에 필요한 모듈번호, 모듈이력번호를 조회합니다.
	    	var rowData = $(this).jqGrid('getRowData', rowid);
	    	var module_no = rowData["MODULE_NO"];
	    	
	    	// 상세보기화면에서 리스트로 돌아왔을때 리스트의 상태를 이전상태로 유지하기위해 필요한 값들을 조회합니다.
	    	var sortname = $(this).jqGrid("getGridParam", "sortname");
	    	var sortorder = $(this).jqGrid("getGridParam", "sortorder");
	    	var page = $(this).jqGrid("getGridParam", "page");
	    	var rowNum = $(this).jqGrid("getGridParam", "rowNum");
	    	
	    	
	    	location.href = "module_02.isnet?mn=" + module_no + "&page="+page + "&rows=" + rowNum + "&sidx=" + sortname + "&sord=" + sortorder;
	    },
	    loadError:function(xhr, status, error){
	    	alert("최근 모듈 변경 내역 조회 중 오류가 발생하였습니다.");
	    }
	}).navGrid("#module_pager",{edit:false,add:false,del:false}, {}, {}, {}, {multipleSearch:true, closeOnEscape:true, closeAfterSearch:true}, {});
	
	
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>