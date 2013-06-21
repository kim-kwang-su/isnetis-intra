<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="버전관리"/>
<c:set var="two_depth" value="옵션리스트"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div style="text-align:right" id="breadcrumb"><a href="#">버젼관리</a> / <a href="option_02.isnet">옵션리스트</a> / <strong>옵션조회</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			
			<!-- 검색 조건 -->
			<%-- <div id="searchbox" style="padding:5px 30px;margin:10px 0 0 0;border:1px solid #cecece;">
				<strong>제품 : </strong>
				<select id="product" name="product">
					<option value="">선택하여 주세요.</option>
					<c:choose>
						<c:when test="${productSize eq  0}">
						</c:when>
						<c:otherwise>
							<c:forEach items="${product }" var="product" varStatus="i">
								<option value="${product.PRODUCT_NO}">${product.PRODUCT_NAME}</option>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<strong>옵션파일 : </strong>
				<select id="optionfile" name="optionfile">
					<option value="">선택하여 주세요.</option>
					<c:choose>
						<c:when test="${productSize eq  0}">
						</c:when>
						<c:otherwise>
							<c:forEach items="${product }" var="product" varStatus="i">
								<option value="${product.PRODUCT_NO}">${product.PRODUCT_NAME}</option>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<strong>옵션APP명 : </strong>
				<select id="optionApp" name="optionApp">
					<option value="">선택하여 주세요.</option>
					<c:choose>
						<c:when test="${productSize eq  0}">
						</c:when>
						<c:otherwise>
							<c:forEach items="${product }" var="product" varStatus="i">
								<option value="${product.PRODUCT_NO}">${product.PRODUCT_NAME}</option>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</select>
				&nbsp;&nbsp;
				<button style="padding:2px 7px" class="icon" id="search_btn">조 회</button>
				<button style="padding:2px 7px" class="icon" id="search_btn">등 록</button>
			</div> --%>
			<!-- 검색 조건 -->
			<div style="margin-top:5px">
				<table id="option_list"></table>
				<div id="option_pager"></div>
			</div>
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	var product_categories = '${productValues}';
	var optionfile_categories = '${optionFileValues}';

	$("#option_list").jqGrid({
		jsonReader: {
			repeatitems : false															
		},
		height:350,
		mtype:"POST",
		datatype: "json",
		url:"option_list_01.isnet",
	   	colNames:['OPTION_NO','제품명','옵션FILE명','옵션_APP명','옵션_KEY명', '옵션_KEY명(한글)', '기본값','주석'],						
	   	colModel:[	
	   	    {name:'OPTION_NO', index:'OPTION_NO', hidden:true},
			{name:'PRODUCT_NAME',index:'D.PRODUCT_NO', width:120, align:"center",  sortable:false,stype:'select', searchoptions:{sopt:['eq'], value:product_categories}},
			{name:'OPTION_FILE_NAME',index:'C.OPTION_FILE_NO', width:150, align:"left",  sortable:false,stype:'select', searchoptions:{sopt:['eq'], value:optionfile_categories}},
			{name:'OPTION_APP_NAME',index:'OPTION_APP_NAME', width:120, align:"center",  sortable:false,searchoptions:{sopt:['eq', 'cn']}},
			{name:'OPTION_KEY_NAME',index:'OPTION_KEY_NAME', width:150, align:"left",  sortable:false,searchoptions:{sopt:['eq', 'cn']}},
	   		{name:'OPTION_COMMENT',index:'OPTION_COMMENT', width:320, align:"left",  sortable:false,searchoptions:{sopt:['eq', 'cn']}},
	   		{name:'OPTION_DEFAULT_VALUE',index:'OPTION_DEFAULT_VALUE', width:80, align:"right", sortable:false, search:false},
	   		{name:'COMMENT',index:'COMMENT', width:223, align:"left", sortable:false, search:false}
	   	],	
	    viewrecords: true,
	    // 제목
	    caption:"옵션조회",
	    // 페이지 네비게이션 설정
	    pager:"option_pager",
	   	page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="20"/>',
	   	rowList:[20,40,60],
	   	sortname:'<c:out value="${param.sidx}"/>',
	   	sortorder:'<c:out value="${param.sord}"/>',

	    onSelectRow:function(rowid){
	    	var rowData = $(this).jqGrid('getRowData', rowid);
	    	var option_no = rowData["OPTION_NO"];
	    	
	    	var sortname = $(this).jqGrid("getGridParam", "sortname");
	    	var sortorder = $(this).jqGrid("getGridParam", "sortorder");
	    	var page = $(this).jqGrid("getGridParam", "page");
	    	var rowNum = $(this).jqGrid("getGridParam", "rowNum");
	    	
	    	location.href = "option_04.isnet?option_no=" + option_no +"&page="+page + "&rows=" + rowNum + "&sidx=" + sortname + "&sord=" + sortorder;
	    },
	    loadError:function(xhr, status, error){
	    	alert("옵션 조회 중 오류가 발생하였습니다.");
	    }
	}).navGrid("#option_pager",{edit:false,add:false,del:false}, {}, {}, {}, {closeOnEscape:true, closeAfterSearch:true}, {});
	
	$("#option_list").jqGrid("navButtonAdd", "#option_pager", {
							caption:"", title: "등록",
							buttonicon: "ui-icon-plus",
							onClickButton: function() {
								location.href="option_05.isnet";
							}
	});
	
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>