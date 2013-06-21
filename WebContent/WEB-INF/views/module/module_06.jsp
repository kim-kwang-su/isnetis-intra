<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="버전관리"/>
<c:set var="two_depth" value="모듈찾기"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="module_01.isnet">버전관리</a> / <strong>모듈찾기</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- 검색 조건 -->
			<div id="searchbox" style="padding:5px 30px;margin:10px 0 5px 0;border:1px solid #cecece;">
				<strong>제품명 : </strong>
				<select id="product_no_box" name="PRODUCT_NO" class="mr20">
					<option value="" selected="selected">-- 제품을 선택하세요 --</option>
					<c:forEach var="product" items="${productList }">
						<option value="${product.PRODUCT_NO }">${product.PRODUCT_NAME }</option>
					</c:forEach>
				</select> 
			
				<strong>모듈명 : </strong>
				<input type="text" id="module_name_field" style="width: 300px;" name="MODULE_NAME" />
					
				<button style="padding:0px 5px" class="icon " id="search_bt">조회</button>
			</div>
			<!-- 검색 조건 -->
			<!-- <h3>최근 모듈변경 내역</h3> -->
			<table id="module_list"></table>
			<div id="module_pager"></div>
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	$("#module_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:$(window).height() - 250,
		mtype:"POST",
		datatype: "local",
		url:"list_04.isnet",
	   	colNames:['MODULE_HISTORY_NO', 'MODULE_NO','제품명', '모듈명', '설치경로', '최근 수정내용', '최종 버젼', '변경일자',],						
	   	colModel:[	
			{name:'MODULE_NO', index:'MODULE_NO', hidden:true},	   	          		
			{name:'MODULE_HISTORY_NO', index:'MODULE_HISTORY_NO', hidden:true},	 
	   		{name:'PRODUCT_NAME',index:'B.PRODUCT_NO', width:150, align:"center", sortable:false, search:false},
	   		{name:'MODULE_NAME',index:'B.MODULE_NAME', width:150, align:"center", sortable:false, search:false},
	   		{name:'FOLDER_PATH',index:'E.FOLDER_PATH', width:150, align:"left", sortable:false, search:false},
	   		{name:'UPDATE_MEMO',index:'UPDATE_MEMO', width:420, align:"left", sortable:false, search:false},	
	   		{name:'FILE_VERSION',index:'FILE_VERSION', width:150, align:"center", sortable:false, search:false},
	   		{name:'UPDATE_DATE',index:'UPDATE_DATE', width:150, align:"center", sortable:false, search:false}	
	   	],	
	    viewrecords: true,
	    // 제목
	    caption:"모듈 리스트",
	    // 페이지 네비게이션 설정
	    pager:"module_pager",
	   	page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="10"/>',
	   	rowList:[10,20,30],
	   	sortname:'<c:out value="${param.sidx}"/>',
	   	sortorder:'<c:out value="${param.sord}"/>',
	   	beforeRequest : function(){
	   		if($("#product_no_box").val() != "" && $("#module_name_field").val() != ""){
	   			$("#module_list").jqGrid("setGridParam", {datatype: "json"});
		   		var postData = $("#module_list").jqGrid("getGridParam", "postData");
		   		var filters = {
			   			groupOp:"AND", 
			   			rules:[
			   			       {field:"A.PRODUCT_NO", op:"eq", data:$("#product_no_box").val()}, 
			   			       {field:"A.MODULE_NAME", op:"eq", data:$("#module_name_field").val()}
			   			      ]
			   		};
		   		// 서버요청전에 검색조건의 값을 postData에 설정한다.
		   		postData["_search"] = true;
		   		postData["filters"] = JSON.stringify(filters);
		   		
		   		$("#module_list").jqGrid("setGridParam", "postData", postData);
	   		}
	   	},
		loadComplete:function(data){
			if($("#product_no_box").val() != "" && $("#module_name_field").val() != ""){
		   		var records = data["records"];
		   		if(records == 0){
		   			simple_popup("안내", "검색조건에 해당하는 데이터가 존재하지 않습니다.");
		   		}
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
	}).navGrid("#module_pager",{edit:false,add:false,del:false, search:false}, {}, {}, {}, {}, {});
	
	$("#module_name_field").keydown(function(event){
		if(event.keyCode == 13){
			$("#search_bt").trigger("click");
		}
	});
	
	// 조회버튼 클릭시
	$("#search_bt").click(function(event){
		event.preventDefault();
		
		if($("#product_no_box").val() == ""){
			simple_popup("안내", "제품을 선택하세요.");
			$("#product_no_box").focus();
			return false;
		}
		if($("#module_name_field").val() == ""){
			simple_popup("안내", "모듈명을 입력하세요");
			$("#module_name_field").focus();
			return false;
		}		
		
		$("#module_list").jqGrid().trigger("reloadGrid");
	});
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>