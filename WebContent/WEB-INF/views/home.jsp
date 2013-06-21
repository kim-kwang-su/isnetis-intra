<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="Home"/>
<c:set var="two_depth" value=""/>
<%@ include file="common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><strong>Home</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			
			<!-- <h3>최근 Issue 내역</h3> -->
			<table id="issue_list"></table>
			<p style="text-align: right;" class="mb10"><a href="issue/issue_01.isnet" class="morelink" title="이슈 이력페이지로 이동합니다. ">More <span>이슈 이력페이지로 이동합니다.</span></a></p>
			
			<!-- <h3>최근 모듈변경 내역</h3> -->
			<table id="module_list"></table>
			<p style="text-align: right;"><a href="module/module_01.isnet" class="morelink" title="모듈변경 이력페이지로 이동합니다. ">More <span>모듈변경 이력페이지로 이동합니다.</span></a></p>
			
			<!-- 컨텐츠 표시부 끝 -->
		
			<!-- footer -->
			<%@ include file="common/footer.jsp" %>
			
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	$("#issue_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:($(window).height() - 265)/2,
		datatype: "json",
		url:"/issue/list_01.isnet",
	   /* 	colNames:['ISSUE_NO','접수일','고객사','제품구분','요청구분','우선순위','진행상태','접수내용','처리완료일'],						
	   	colModel:[	
			{name:'ISSUE_NO', index:'ISSUE_NO', hidden:true},	   	          		
	   		{name:'ACCEPT_DATE',index:'ACCEPT_DATE', width:100, align:"center", sortable:false},
	   		{name:'CUSTOMER_NAME',index:'CUSTOMER_NAME', width:150, align:"center", sortable:false},
	   		{name:'PRODUCT_NAME',index:'PRODUCT_NAME', width:150, align:"center", sortable:false},
	   		{name:'KIND_NAME',index:'KIND_NAME', width:70, align:"center", sortable:false},
	   		{name:'ISSUE_PRIORITY',index:'ISSUE_PRIORITY', width:68, align:"center" , sortable:false },
	   		{name:'STATE_NAME',index:'STATE_NAME', width:120, align:"center", sortable:false},
	   		{name:'ISSUE_MEMO',index:'ISSUE_MEMO', width:400, align:"left", sortable:false},
	   		{name:'RELEASE_DATE',index:'RELEASE_DATE', width:100, align:"center", sortable:false}	
	   	],	 */
	   	colNames:['ISSUE_NO','접수일','고객사', '제품구분','요청구분','우선순위','진행상태', 'SE', '개발자', '접수내용', '처리완료일'],						
	   	colModel:[	
			{name:'ISSUE_NO', index:'ISSUE_NO', hidden:true},	   	          		
	   		{name:'ACCEPT_DATE',index:'ACCEPT_DATE', width:75, align:"center", sortable:false},
	   		//{name:'CUSTOMER_REQUEST_DATE',index:'CUSTOMER_REQUEST_DATE', width:80, align:"center", sortable:false, search:false},
	   		//{name:'EXPECTED_COMPLETE_DATE',index:'EXPECTED_COMPLETE_DATE', width:80, align:"center"},
	   		{name:'CUSTOMER_NAME',index:'CUSTOMER_NO', width:100, align:"center", sortable:false},
	   		{name:'PRODUCT_NAME',index:'PRODUCT_NO', width:100, align:"center", sortable:false},
	   		{name:'KIND_NAME',index:'KIND_NO', width:80, align:"center", sortable:false},
	   		{name:'ISSUE_PRIORITY',index:'ISSUE_PRIORITY', width:68, align:"center", sortable:false },
	   		{name:'STATE_NAME',index:'STATE_NO', width:100, align:"center", sortable:false},
	   		{name:'SE_NAME', index:'SE_NO', width:60, align:"center", sortable:false},
	   		{name:'DEVELOPER_NAME', index:'DEVELOPER_NO', width:158, align:"center", sortable:false},
	   		{name:'ISSUE_MEMO',index:'ISSUE_MEMO', width:330, align:"left", sortable:false},
	   		{name:'RELEASE_DATE',index:'RELEASE_DATE', width:80, align:"center"}
	   	],	
	    viewrecords: true,
	    caption:"최근 이슈접수 이력",
	   	rowNum:8,
	   	
	    onSelectRow:function(rowid){
	    	var rowData = $(this).jqGrid('getRowData', rowid);
	    	var issue_no = rowData["ISSUE_NO"];
	    	
	    	location.href = "issue/issue_02.isnet?in=" + issue_no;
	    },
	    loadError:function(xhr, status, error){
	    	alert("최근 이슈 접수내역을 조회 중 오류가 발생하였습니다.");
	    }
	});
	
	
	jQuery("#module_list").jqGrid({
		
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:($(window).height() - 265)/2,
	   	url:'/module/list_01.isnet',														
		datatype: "json",																	
	   	colNames:['MODULE_HISTORY_NO', 'MODULE_NO','변경일자','제품명', '모듈명', '변경내용','적용사이트','최종 버젼'],						
	   	colModel:[		
	   	    {name:'MODULE_HISTORY_NO', index:'MODULE_HISTORY_NO', hidden:true},	 
			{name:'MODULE_NO', index:'MODULE_NO', hidden:true},	   	          																		
	   		{name:'UPDATE_DATE',index:'UPDATE_DATE', width:100, align:"center"},
	   		{name:'PRODUCT_NAME',index:'PRODUCT_NAME', width:150, align:"center"},
	   		{name:'MODULE_NAME',index:'MODULE_NAME', width:200, align:"center"},
	   		{name:'UPDATE_MEMO',index:'UPDATE_MEMO', width:400, align:"left", sortable:false},
	   		{name:'CUSTOMER_NAME',index:'CUSTOMER_NAME', width:200, align:"left", sortable:false},
	   		{name:'FILE_VERSION',index:'FILE_VERSION', width:122, align:"center"}	
	   	],		
	   	rowNum:8,
	    viewrecords: true,
	    caption:"최근 모듈변경 내역",
	    onSelectRow:function(rowid){
	    	var rowData = $(this).jqGrid('getRowData', rowid);
	    	var module_no = rowData["MODULE_NO"];
	    	location.href = "module/module_02.isnet?mn=" + module_no;
	    },
	    loadError:function(xhr, status, error){
	    	$("<div title='오류창'><p>최근 모듈변경 내역을 조회하는 중 오류가 발생하였습니다.</p></div>").dialog();
	    }
	});	
});
</script>		
<%@ include file="common/bottom.jsp" %>