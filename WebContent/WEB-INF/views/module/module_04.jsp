<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="버전관리"/>
<c:set var="two_depth" value="모듈별 최종버전"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="module_01.isnet">버전관리</a> / <strong>모듈별 최종버전</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- 검색 조건 -->
			<div id="searchbox" style="padding:5px 30px;margin:10px 0 0 0;border:1px solid #cecece;">
				<strong>제품명 : </strong>
				<select id="product_no" name="PRODUCT_NO" style="margin-right: 20px;">
					<option value="">선택하여 주세요.</option>
					<c:forEach var="product" items="${productList }">
						<option value="${product.PRODUCT_NO }">${product.PRODUCT_NAME }</option>
					</c:forEach>
				</select>
			
				<strong>설치경로 : </strong>
				<select id="location_no" name="LOCATION_NO" style="margin-right: 20px;">
					<option value="">설치경로를 선택하세요</option>
					<c:forEach var="filePath" items="${filePathList }">
						<option value="${filePath.LOCATION_NO }">${filePath.FOLDER_PATH }</option>
					</c:forEach>
			    </select>
				<button style="padding:0px 5px" class="icon" id="search_bt">조 회</button>
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
	
	// 그리드의 데이터 지우기
	$("#product_no, #location_no").change(function(){
		$("#module_list").jqGrid('clearGridData');
	});
	
	// 조회 버튼 클릭시
	$("#search_bt").click(function(event){
		event.preventDefault();
		
		if($("#product_no").val() == ""){
			simple_popup("안내", "제품명을 선택해주세요.");
			return false;
		}
		if($("#location_no").val() == ""){
			simple_popup("안내", "설치경로를 선택하세요");
			return false;
		}
		// 조회 조건에 만족하는 jqGrid를 그립니다.		
		var param = {url:"list_03.isnet?pn="+$("#product_no").val() + "&ln="+$("#location_no").val(),datatype:"json"};
		jQuery("#module_list").jqGrid('setGridParam',param).trigger("reloadGrid");
		
	});
	
	$("#module_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:400,
		mtype:"GET",
		datatype: "local",
		//url:"list_01.isnet",
	   	colNames:['MODULE_HISTORY_NO', 'MODULE_NO','모듈명','최종 파일버전', '최종수정일자', '담당자', '변경내용', 'X86_릴리즈', 'X86_디버그','X64_릴리즈', 'X64_디버그' ],						
	   	colModel:[	
			{name:'MODULE_HISTORY_NO', index:'MODULE_HISTORY_NO', hidden:true},	 
			{name:'MODULE_NO', index:'MODULE_NO', hidden:true},	   	          		
	   		{name:'MODULE_NAME',index:'MODULE_NAME', width:152, align:"center", sortable:false, search:false},
	   		{name:'FILE_VERSION',index:'FILE_VERSION', width:100, align:"center", sortable:false, search:false},
	   		{name:'UPDATE_DATE',index:'UPDATE_DATE', width:100, align:"center", sortable:false, search:false},
	   		{name:'EMPLOYEE_NAME',index:'EMPLOYEE_NAME', width:100, align:"center", sortable:false, search:false},
	   		{name:'UPDATE_MEMO',index:'UPDATE_MEMO', width:380, align:"left", sortable:false, search:false},
	   		{name:'X86_RELEASE',index:'X86_RELEASE', width:80, sortable:false, align:"center", formatter: 'link'},
	   		{name:'X86_DEBUG',index:'X86_DEBUG', width:80, sortable:false, align:"center", formatter: 'link'},
	   		{name:'X64_RELEASE',index:'X64_RELEASE', width:80, sortable:false, align:"center", formatter: 'link'},
	   		{name:'X64_DEBUG',index:'X64_DEBUG', width:80, sortable:false, align:"center", formatter: 'link'}
	   	],	
	    viewrecords: true,
	    // 제목
	    caption: "최종버전",
	    // 페이지 네비게이션 설정
	    pager:"module_pager",
	   	page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="10"/>',
	   	rowList:[10,20,30],
	   	sortname:'<c:out value="${param.sidx}"/>',
	   	sortorder:'<c:out value="${param.sord}"/>',

	    onSelectRow:function(rowid){
	    	// 모듈 상세보기 화면으로 이동합니다. 상세보기에 필요한 모듈번호, 모듈이력번호를 조회합니다.
	    	var rowData = $(this).jqGrid('getRowData', rowid);
	    	var module_no = rowData["MODULE_NO"];
	    	
	    	// 상세보기화면에서 리스트로 돌아왔을때 리스트의 상태를 이전상태로 유지하기위해 필요한 값들을 조회합니다.
	    	var sortname = $(this).jqGrid("getGridParam", "sortname");
	    	var sortorder = $(this).jqGrid("getGridParam", "sortorder");
	    	var page = $(this).jqGrid("getGridParam", "page");
	    	var rowNum = $(this).jqGrid("getGridParam", "rowNum");
	    	
	    	//console.log("모듈번호["+module_no+"] 모듈이력번호["+module_history_no+"]");
	    	//console.log("page["+page+"] rows["+rowNum+"] sortname["+sortname+"] sortorder["+sortorder+"]");
	    	
	    	location.href = "module_02.isnet?mn=" + module_no + "&page="+page + "&rows=" + rowNum + "&sidx=" + sortname + "&sord=" + sortorder;
	    },
	    loadError:function(xhr, status, error){
	    	alert("조회 중 오류가 발생하였습니다.");
	    }
	});
	
	
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>