<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="이슈관리"/>
<c:set var="two_depth" value="이슈리스트"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="issue_01.isnet">이슈관리</a> / <strong>이슈 리스트</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<table id="issue_list"></table>
			<div id="issue_pager"></div>
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	var isUsePrevCondition = true;
	
	var customer_categories = '${customerValues}';
	var product_categories = '${productValues}';
	var state_categories = '${stateValues}';
	var kind_categories = '${kindValues}';
	var se_categories = '${seValues}';
	var developer_categories = '${developerValues}';
	
	$("#issue_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:$(window).height() - 250,
		mtype:"POST",
		datatype: "json",
		url:"list_01.isnet",
	   	colNames:['ISSUE_NO', '이슈번호', '접수일','고객사', '제품구분','요청구분','우선순위','진행상태', 'SE', '개발자', '접수내용', '완료일'],						
	   	colModel:[	
			{name:'ISSUE_NO', index:'ISSUE_NO', hidden:true},	
			{name:'ISSUE_NO2', index:'ISSUE_NO2',  width:60, align:"center", sortable:true, search:false},
	   		{name:'ACCEPT_DATE',index:'ACCEPT_DATE', width:75, align:"center", sortable:true, search:false},
	   		{name:'CUSTOMER_NAME',index:'CUSTOMER_NO', width:100, align:"center", stype:'select', searchoptions:{sopt:['eq'], value:customer_categories}},
	   		{name:'PRODUCT_NAME',index:'PRODUCT_NO', width:100, align:"center", stype:'select', searchoptions:{sopt:['eq'], value:product_categories}},
	   		{name:'KIND_NAME',index:'KIND_NO', width:70, align:"center", stype:'select', searchoptions:{sopt:['eq'], value:kind_categories}},
	   		{name:'ISSUE_PRIORITY',index:'ISSUE_PRIORITY', width:60, align:"center", stype:'select',  searchoptions:{sopt:['eq'], value:"상:상;중:중;하:하"} },
	   		{name:'STATE_NAME',index:'STATE_NO', width:120, align:"center", stype:'select', searchoptions:{sopt:['eq', 'ne'], value:state_categories}},
	   		{name:'SE_NAME', index:'SE_NO', width:60, align:"center", sortable:false, stype:'select', searchoptions:{sopt:['eq'], value:se_categories}},
	   		{name:'DEVELOPER_NAME', index:'DEVELOPER_NO', width:130, align:"center", sortable:false, stype:'select', searchoptions:{sopt:['eq'], value:developer_categories}},
	   		{name:'ISSUE_MEMO',index:'ISSUE_MEMO', width:310, align:"left", sortable:false, searchoptions:{sopt:['cn', 'eq']}},
	   		{name:'RELEASE_DATE',index:'RELEASE_DATE', width:60, align:"center"}
	   	],	
	    viewrecords: true,
	    // 제목
	    caption:"이슈 접수 이력",
	    // 페이지 네비게이션 설정
	    pager:"issue_pager",
	   	page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="20"/>',
	   	rowList:[20,40,60],
	   	sortname:'<c:out value="${param.sidx}"/>',
	   	sortorder:'<c:out value="${param.sord}"/>',
	   	
	   	beforeRequest : function(){
	   		
			if('${param.from}' == 'detail'){
					var filters = '${sessionScope.filters}';
					if(filters != "" && isUsePrevCondition){
						
						var postData = $("#issue_list").jqGrid("getGridParam", "postData");
						
						postData["_search"] = true;
						postData["filters"] = filters;
						
						$("#issue_list").jqGrid("setGridParam", "postData", postData);
						isUsePrevCondition = false;
					}
			}	   		
	   		
	   	},
	   	beforeProcessing: function(data, status, xhr){
	   		if(data && data.isSuccess){
	    		$.each(data.rows, function(index, row){
	    			row["ISSUE_NO2"] = row["ISSUE_NO"];
	    		});
	    	}
	   	},
		loadComplete:function(data){
	   		var records = data["records"];
	   		if(records == 0){
	   			simple_popup("안내", "검색조건에 해당하는 데이터가 존재하지 않습니다.");
	   		}
	   	},
	    onSelectRow:function(rowid){
	    	// 그리드의 row를 선택하면 상세보기 화면으로 이동합니다.
	    	var rowData = $(this).jqGrid('getRowData', rowid);
	    	var issue_no = rowData["ISSUE_NO"];
	    	
	    	// 상세보기 화면에서 목록화면으로 돌아왔을때 리스트의 상태를 유지하기 위하여 필요한 값을 전달합니다.	    	
	    	var sortname = $(this).jqGrid("getGridParam", "sortname");
	    	var sortorder = $(this).jqGrid("getGridParam", "sortorder");
	    	var page = $(this).jqGrid("getGridParam", "page");
	    	var rowNum = $(this).jqGrid("getGridParam", "rowNum");
	    	
	    	location.href = "issue_02.isnet?in=" + issue_no 
	    					+ "&page="+page + "&rows=" + rowNum + "&sidx=" + sortname + "&sord=" + sortorder + "&returnURL=issue_01";
	    },
	    loadError:function(xhr, status, error){
	    	alert("최근 이슈 접수내역을 조회 중 오류가 발생하였습니다.");
	    }
	}).navGrid("#issue_pager",{edit:false,add:false,del:false}, {}, {}, {}, {multipleSearch:true, closeOnEscape:true, closeAfterSearch:true}, {});	
});
</script>		
<%@ include file="../common/bottom.jsp" %>