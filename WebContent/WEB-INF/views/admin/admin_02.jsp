<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="관리자"/>
<c:set var="two_depth" value="고객사 관리"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="admin_01.isnet">관리자</a> / <strong>고객사 관리</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<table id="customer_list"></table>
			<div id="customer_pager"></div>
			
			<!-- 고객사 등록 팝업 -->
			<div id="dialog-customer" title="고객사 등록">
				<form>
					<p>
						<label>고객사 명</label>
						<input type="hidden" id="customer_no" name="CUSTOMER_NO" value="0" />
						<input type="text" id="customer_name" name="CUSTOMER_NAME" maxlength="40" />
					</p>
					<p>
						<label>주소</label>
						<input style="width:400px;" type="text" id="addr" name="ADDR" maxlength="64" />

					</p>
					<p>
						<label>전화번호</label>
						<ul style="margin-left:100px; margin-top:-35px;" class="telBox">
							<li><input type="text" id="tel1" name="TEL" maxlength="4"/> - </li>
							<li><input type="text" id="tel2" name="TEL" maxlength="4"/> - </li>
							<li><input type="text" id="tel3" name="TEL" maxlength="4"/></li>
						</ul>
					</p>
					<p> 
						<label>사용여부</label>
						<select style="margin-top:5px; width:100px" id="is_used" name="IS_USED"> 
							<option value="Y" selected>사용</option>
							<option value="N">미사용</option>
						</select>
					</p>
				</form>
			</div>		
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	var customer_categories = '${customerValues}';
	
	$("#customer_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:400,
		mtype:"POST",
		datatype: "json",
		url:"list_customer.isnet",
	   	colNames:['번호','고객사명','고객사 주소','고객사 전화번호','사용여부'],						
	   	colModel:[	
			{name:'CUSTOMER_NO', index:'CUSTOMER_NO',width:60,hidden:true,search:false, editable:false},	   	          		
	   		{name:'CUSTOMER_NAME',index:'CUSTOMER_NAME', width:200, align:"left", stype:'text', searchoptions:{sopt:['cn']}},
	   		{name:'CUSTOMER_ADDRESS',index:'CUSTOMER_ADDRESS', width:680, align:"left",search:false},
	   		{name:'CUSTOMER_TEL',index:'CUSTOMER_TEL', width:150, align:"center",search:false},
	   		{name:'IS_USED',index:'IS_USED', width:150, align:"center",search:false}
	   	],	
	    viewrecords: true,
	    // 제목
	    caption:"고객사 리스트",
	    	    
	    // 페이지 네비게이션 설정
	    pager:"customer_pager",
	   	page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="20"/>',
	   	rowList:[20,40,60],
	   	sortname:'<c:out value="${param.sidx}"/>',
	   	sortorder:'<c:out value="${param.sord}"/>',
	    onSelectRow:function(rowid){
	    	// 그리드의 row를 선택하면 상세보기 화면으로 이동합니다.
	    	var rowData = $(this).jqGrid('getRowData', rowid);
	    	var CUSTOMER_NO = rowData["CUSTOMER_NO"];
	    	var CUSTOMER_NAME = rowData["CUSTOMER_NAME"];
	    	var CUSTOMER_ADDRESS = rowData["CUSTOMER_ADDRESS"];
	    	var CUSTOMER_TEL = rowData["CUSTOMER_TEL"];
	    	var IS_USED = rowData["IS_USED"];
	    	
	    	$("#dialog-customer").dialog({
	    		open: function(){
	    			$(this).dialog( "option", "title", "고객사 수정");
	    			$("#customer_no").val(CUSTOMER_NO);
	    			$("#customer_name").val(CUSTOMER_NAME);
	    			$("#addr").val(CUSTOMER_ADDRESS);
	    			if(CUSTOMER_TEL == "") {
		    			$("#tel1").val("");
		    			$("#tel2").val("");
		    			$("#tel3").val("");
	    			}else {
	    				var tel = CUSTOMER_TEL.split("-");
	    				for(var i = 0; i <tel.length;i++) {
	    					if(tel[i]!="") {
	    						$("#tel"+(i+1)).val(tel[i]);
	    					}
	    				}
	    			}
	    			if(IS_USED == '사용') {
	    				$("#is_used").val('Y');
	    			}else {
	    				$("#is_used").val('N');	
	    			}
	    			$("#customer_name").focus();
	    		}
	    	});
	    	$("#dialog-customer").dialog("open");
	    },
	    loadError:function(xhr, status, error){
	    	alert("고객사 리스트 조회 중 오류가 발생하였습니다.");
	    }
	   		
	   		
	}).navGrid("#customer_pager",{edit:false,add:false,del:false}, {}, {}, {}, {closeOnEscape:true, closeAfterSearch:true}, {})
	.navButtonAdd('#customer_pager',{
		caption:"등록", 
		buttonicon:"ui-icon-plus", 
		onClickButton: function(){ 
			
			
	    	$("#dialog-customer").dialog({
	    		open: function(){
	    			$(this).dialog( "option", "title", "고객사 등록");
	    			$("#customer_no").val("0");
	    			$("#customer_name").val("");
	    			$("#addr").val("");

	    			$("#tel1").val("");
	    			$("#tel2").val("");
	    			$("#tel3").val("");

	    			$("#customer_name").focus();
	    		}
	    		});
			$("#dialog-customer").dialog("open");
		}, 
	 	id: "customer_add_bt",
	 	title: "고객사를 등록합니다."
	});
		
	$("#dialog-customer").dialog({
		autoOpen: false,
		height: 270,
		width: 600,
		resizable: false,
		modal: true,
		open: function(){
			$(this).dialog( "option", "title", "고객사 등록");
			$("#customer_name").val("");
			$("#addr").val("");
			$("#tel1").val("");
			$("#tel2").val("");
			$("#tel3").val("");

			$("#customer_name").focus();
		},
		buttons:{
			"Save": function(){
				if($.trim($("#customer_name").val())==""){
					alert("고객사 명을 입력해 주세요.");
					$("#employee_name").focus();
					return false;
				}
				
				$.ajax({
					url: "add_customer.isnet",
					type: "POST",
					data: $("#dialog-customer form").serialize(),
					dataType: "json",
					success: function(result, status){
						if(result && result.isSuccess){
							simple_popup("안내",result.success_msg);
						}else{
							simple_popup("안내", result.error_msg);
						}
						$("#dialog-customer").dialog("close");
						$("#customer_list").trigger("reloadGrid");
					},
					error: function(){
						alert("고객사 등록 중 오류가 발생하였습니다.");
						$("#dialog-customer").dialog("close");
					}
				});
			},
			Cancel: function(){
				$(this).dialog("close");
			}
		}
	}); 
	
	$("#customer_add_bt")
	.click(function(event){
		event.preventDefault();		
		$("#dialog-customer").dialog("open");
	}); 
	
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>