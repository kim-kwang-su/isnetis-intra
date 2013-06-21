<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="이슈관리"/>
<c:set var="two_depth" value="진행상황조회"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="issue_01.isnet">이슈관리</a> / <strong>진행상황조회</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- 검색 조건 -->
			<div id="searchbox" style="padding:5px 30px;margin:10px 0 5px 0;border:1px solid #cecece;">
				
				<strong>검색조건 : </strong>
				<select id="option_box" name="SEARCH_OPTION" class="mr5">
					<option value="EMPLOYEE_NO" selected="selected">개발자</option>
					<option value="CUSTOMER_NO">고객사</option>
				</select> 
				
				<select id="employee_value_box" name="EMPLOYEE_NO" class="mr20">
					<option value="0">전체</option>
					<c:forEach var="employee" items="${employeeList }">
						<c:if test="${employee.DEPT_NO eq '2' }">
							<option value="${employee.EMPLOYEE_NO }">${employee.EMPLOYEE_NAME }</option>
						</c:if>
					</c:forEach>
				</select>
				<select id="customer_value_box" name="CUSTOMER_NO" class="mr20" style="display: none;">
					<option value="0">전체</option>
					<c:forEach var="customer" items="${customerList }">
						<option value="${customer.CUSTOMER_NO }">${customer.CUSTOMER_NAME }</option>
					</c:forEach>
				</select>
				
				<strong>진행상태 : </strong>
				<select id="state_box" name="STATE_NO">
					<c:forEach var="state" items="${stateList }">
						<option value="${state.STATE_NO }">${state.STATE_NAME }</option>
					</c:forEach>
				</select> 
				<!-- <input type="checkbox" id="state_checkbox" value="le" style="width: 15px;"/><label>일치</label> -->
			    
			    <strong class="ml20">등록일자 : </strong>
				<input style="width:100px;" type="text" id="start_date" name="START_DATE" readonly="readonly"/> ~ 
				<input style="width:100px;" type="text" id="end_date" name="END_DATE" readonly="readonly"/>
				<button style="padding:0px 5px" class="icon mr20" id="search_bt">조회</button>
				<button style="padding:0px 5px" class="icon" id="init_bt">초기화</button>
			</div>
			<!-- 검색 조건 -->
			
			<!-- 검색결과 표시 -->
			<table id="issue_list"></table>
			<div id="issue_pager"></div>			
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	// 상세페이지로부터 전달된 검색조건을 사용할 것인지를 설정한다.
	var isUsePrevCondition = true;
	
	var init_search_condition = function(){		
		$("#option_box :eq(0)").attr("selected", "selected").trigger("change");
		$("#state_box :eq(1)").attr("selected", "selected");
		
		setDatepicker("#start_date", null, 0);
		setDatepicker("#end_date", null, 0);
		
		$("#start_date").datepicker("option", "onClose", function(selectedDate){
			$("#end_date").datepicker("option", "minDate", selectedDate);
		});
		
		$("#end_date").datepicker("option", "onClose", function(selectedDate){
			$("#start_date").datepicker("option", "maxDate", selectedDate);
		});		
		
		$("#start_date").val("");
		$("#end_date").val("");
	};
	init_search_condition();
	
	$("#option_box").change(function(event){
		event.preventDefault();		
		var value = $("#option_box").val();
		$("#customer_value_box :eq(0)").attr("selected", "selected");
		$("#employee_value_box :eq(0)").attr("selected", "selected");
		$("#customer_value_box, #employee_value_box").hide();
		$("select[name='"+value+"']").show();
	});
	
	$("#search_bt").click(function(event){
		event.preventDefault();
		
		var value = $("#option_box").val();
		if($("select[name='"+value+"']").val() == ""){
			simple_popup("안내", $("#option_box :selected").text() + "를 선택하세요");
			$("select[name='"+value+"']").focus();
			return false;
		}	
		
		if($("#start_date").val() == ""){
			simple_popup("안내", "시작일을 지정하세요");
			$("#start_date").focus();
			return false;
		}
		
		if($("#end_date").val() == ""){
			simple_popup("안내", "종료일을 지정하세요");
			$("#end_date").focus();
			return false;
		}
		
		$("#issue_list").jqGrid().trigger("reloadGrid");
	});
	
	$("#init_bt").click(function(event){
		event.preventDefault();
		init_search_condition();
		isUsePrevCondition = false;
		$("#issue_list").jqGrid("clearGridData");
	});
	
	$("#issue_list").jqGrid({
		jsonReader:{
			repeatitems:false,
			id:"0"
		},
		height:$(window).height()-250,
		mtype:"POST",
		datatype:"local",
		url:"list_03.isnet",
		colNames:["ISSUE_NO", "등록일", "진행상태", "고객사", "개발자", "담당SE", "처리내용"],
		colModel:[
			{name:"ISSUE_NO", index:"ISSUE_NO", hidden:true},
			{name:"ACCEPT_DATE", index:"ACCEPT_DATE", width:120, align:"center", sortable:false, search:false},
			{name:"STATE_NAME", index:"STATE_NAME", width:120, align:"center", sortable:false, search:false},
			{name:"CUSTOMER_NAME", index:"CUSTOMER_NAME", width:150, align:"center", sortable:false, search:false},
			{name:"DEVELOPER_NAME", index:"DEVELOPER_NAME", width:200, align:"center", sortable:false, search:false},
			{name:"SE_NAME", index:"SE_NAME", width:100, align:"center", sortable:false, search:false},
			{name:"ISSUE_MEMO", index:"ISSUE_MEMO", width:480, align:"left", sortable:false, search:false}
		],
		viewrecords: true,
		caption:"진행상황",
		pager:"issue_pager",
		page:'<c:out value="${param.page}" default="1"/>',
		rowNum:'<c:out value="${param.rows}" default="20"/>',
		rowList:[20,40,60],
		beforeRequest:function(){
			
			// 검색 후 상세페이지에서 되돌아왔을 때 검색결과를 유지하기 처리
			if('${param.from}' == 'detail'){
				// 세션에 저장해둔 검색조건을 조회한다.
				var filters = '${sessionScope.filters}';
				if(filters != "" && isUsePrevCondition){
					// 조회된 검색 조건을 화면에 표시한다.
					var filtersObj = JSON.parse(filters);
					var rules = filtersObj.rules;
					$.each(rules, function(index, rule){
						if(rule.field == "EMPLOYEE_NO" || rule.field == "CUSTOMER_NO"){
							$("#customer_value_box, #employee_value_box").hide();
							$("select[name='"+rule.field+"']").show();
							$("select[name='"+rule.field+"'] option").filter("[value='"+rule.data+"']").attr("selected", "selected");
						}
						if(rule.field == "STATE_NO"){
							$("select[name='"+rule.field+"'] option").filter("[value='"+rule.data+"']").attr("selected", "selected");
						}
						if(rule.field == "START_DATE"){
							$("#start_date").val(rule.data);
						}
						if(rule.field == "END_DATE"){
							$("#end_date").val(rule.data);
						}
					});
					
					isUsePrevCondition = false;
				}
			}
			if($("#start_date").val() != "" && $("#end_date").val() != ""){
				
				$("#issue_list").jqGrid("setGridParam", {datatype: "json"});
				var postData = $("#issue_list").jqGrid("getGridParam", "postData");
				var option_value = $("#option_box").val();
				var state_op = "eq";
				
				var filters = {
		   			groupOp:"AND", 
		   			rules:[
		   				{field:option_value, op:"eq", data:$("select[name='"+option_value+"']").val()}, 
		   			    {field:"STATE_NO", op:state_op, data:$("#state_box").val()},
		   			    {field:"START_DATE", op:"eq", data:$("#start_date").val()},
		   				{field:"END_DATE", op:"eq", data:$("#end_date").val()}
		   			]
		   		};
				
				// 서버요청전에 검색조건의 값을 postData에 설정한다.
		   		postData["_search"] = true;
		   		postData["filters"] = JSON.stringify(filters);
		   		
		   		$("#issue_list").jqGrid("setGridParam", "postData", postData);
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
	    					+ "&page="+page + "&rows=" + rowNum + "&sidx=" + sortname + "&sord=" + sortorder + "&returnURL=issue_05";
	    },
		
	}).navGrid("#issue_pager",{edit:false,add:false,del:false}, {}, {}, {}, {}, {});	
});
</script>		
<%@ include file="../common/bottom.jsp" %>