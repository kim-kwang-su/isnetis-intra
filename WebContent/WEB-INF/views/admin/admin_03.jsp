<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="관리자"/>
<c:set var="two_depth" value="고객사 담당자관리"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="admin_01.isnet">관리자</a> / <strong>고객사 담당자관리</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<table id="manager_list"></table>
			<div id="manager_pager"></div>
			
			<!-- 고객사 등록 팝업 -->
			<div id="dialog-manager" title="담당자 등록">
				<form>
					<p>
						<label>고객사</label>
						<input type="hidden" id="customer_no" name="CUSTOMER_NO" value="0" />
						<input style="width:200px;" type="text" id="customer_name" name="CUSTOMER_NAME" maxlength="40" />
					</p>
				
					<p>
						<label>성명</label>
						<input type="hidden" id="manager_no" name="MANAGER_NO" value="0" />
						<input style="width:100px;" type="text" id="manager_name" name="MANAGER_NAME" maxlength="6" />
					</p>
					<p>
						<label>이메일</label>
						<ul style="margin-left:100px; margin-top:-35px;" class="emailBox">
							<li><input style="width:100px;" type="text" id="email1" name="EMAIL" maxlength="20"/>@ </li>
							<li><input style="width:200px;" type="text" id="email2" name="EMAIL" maxlength="20"/></li>
						</ul>
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
	
	$("#manager_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:400,
		mtype:"POST",
		datatype: "json",
		url:"list_manager.isnet",
	   	colNames:['CUSTOMER_NO','고객사명','MANAGER_NO', '담당자명','담당자 전화번호','담당자 이메일','사용여부'],						
	   	colModel:[	
			{name:'CUSTOMER_NO', index:'CUSTOMER_NO',width:60,hidden:true,search:false, editable:false},	   	          		
	   		{name:'CUSTOMER_NAME',index:'CUSTOMER_NAME', width:160, align:"left", stype:'text', searchoptions:{sopt:['cn']}},
			{name:'MANAGER_NO', index:'MANAGER_NO',width:60,hidden:true,search:false, editable:false},	   	          		
	   		{name:'MANAGER_NAME',index:'MANAGER_NAME', width:160, align:"center",stype:'text', searchoptions:{sopt:['cn']}},
	   		{name:'MANAGER_TEL',index:'MANAGER_TEL', width:150, align:"center",search:false},
	   		{name:'MANAGER_EMAIL',index:'MANAGER_EMAIL', width:150, align:"center",search:false},
	   		{name:'IS_USED',index:'IS_USED', width:150, align:"center",search:false}
	   	],	
	    viewrecords: true,
	    // 제목
	    caption:"고객사 담당자 리스트",
	    	    
	    // 페이지 네비게이션 설정
	    pager:"manager_pager",
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
	    	
	    	var MANAGER_NO = rowData["MANAGER_NO"];
	    	var MANAGER_NAME = rowData["MANAGER_NAME"];
	    	var MANAGER_EMAIL = rowData["MANAGER_EMAIL"];
	    	var MANAGER_TEL = rowData["MANAGER_TEL"];
	    	var IS_USED = rowData["IS_USED"];
	    	
	    	$("#dialog-manager").dialog({
	    		open: function(){
	    			$(this).dialog( "option", "title", "담당자 수정");
	    			
	    			$("#customer_no").val(CUSTOMER_NO);
	    			$("#customer_name").val(CUSTOMER_NAME);
	    			$("#manager_no").val(MANAGER_NO);
	    			$("#manager_name").val(MANAGER_NAME);
	    		
	    			if(MANAGER_EMAIL =="") {
	    				$("#email1").val("");
		    			$("#email2").val("");
	    			}else {
	    				var email = MANAGER_EMAIL.split("@");
	    				for(var i = 0; i <email.length;i++) {
	    					if(email[i]!="") {
	    						$("#email"+(i+1)).val(email[i]);
	    					}
	    				}
	    			}
	    			
	    			if(MANAGER_TEL == "") {
		    			$("#tel1").val("");
		    			$("#tel2").val("");
		    			$("#tel3").val("");
	    			}else {
	    				var tel = MANAGER_TEL.split("-");
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
	    	$("#dialog-manager").dialog("open");
	    },
	    loadError:function(xhr, status, error){
	    	alert("고객사 리스트 조회 중 오류가 발생하였습니다.");
	    }
	   		
	   		
	}).navGrid("#manager_pager",{edit:false,add:false,del:false}, {}, {}, {}, {closeOnEscape:true, closeAfterSearch:true}, {})
	.navButtonAdd('#manager_pager',{
		caption:"등록", 
		buttonicon:"ui-icon-plus", 
		onClickButton: function(){ 
			
			
	    	$("#dialog-manager").dialog({
	    		open: function(){
	    			$(this).dialog( "option", "title", "담당자 등록");
	    			$("#customer_no").val("0");
	    			$("#customer_name").val("");
	    			
	    			$("#manager_no").val("0");
	    			$("#manager_name").val("");
	    			
	    			$("#email1").val("");
	    			$("#email2").val("");
	    			
	    			$("#tel1").val("");
	    			$("#tel2").val("");
	    			$("#tel3").val("");

	    			$("#customer_name").focus();
	    		}
	    		});
			$("#dialog-manager").dialog("open");
		}, 
	 	id: "manager_add_bt",
	 	title: "고객사를 등록합니다."
	});
		
	$("#dialog-manager").dialog({
		autoOpen: false,
		height: 300,
		width: 600,
		resizable: false,
		modal: true,
		buttons:{
			"Save": function(){
				if($.trim($("#customer_name").val())==""){
					alert("고객사 명을 입력해 주세요.");
					$("#customer_name").focus();
					return false;
				}
				
				if($.trim($("#manager_name").val())==""){
					alert("담당자 성명을 입력해 주세요.");
					$("#manager_name").focus();
					return false;
				}
				
				$.ajax({
					url: "add_manager.isnet",
					type: "POST",
					data: $("#dialog-manager form").serialize(),
					dataType: "json",
					success: function(result, status){
						if(result && result.isSuccess){
							simple_popup("안내",result.success_msg);
						}else{
							simple_popup("안내", result.error_msg);
						}
						$("#dialog-manager").dialog("close");
						$("#manager_list").trigger("reloadGrid");
					},
					error: function(){
						alert("담당자  등록 중 오류가 발생하였습니다.");
						$("#dialog-manager").dialog("close");
					}
				});
			},
			Cancel: function(){
				$(this).dialog("close");
			}
		}
	}); 
	
	$("#manager_add_bt")
	.click(function(event){
		event.preventDefault();		
		$("#dialog-manager").dialog("open");
	}); 
	
	$("#customer_name").autocomplete({
		
		source:function( request, response ) {
		    $.ajax({
		    	type:"POST",
		        url: "search_customer.isnet",
		        data: {term: request.term},
		        dataType: "json",
		        success: function( data ) {
		            response( $.map( data.list, function( item ) {
		                return {
		                    label: item.CUSTOMER_NAME,
		                    value: item.CUSTOMER_NAME,
		                    id : item.CUSTOMER_NO
		                }
		            }));
		        }
		    });
		},
		select:function(event, ui) {
			$("#customer_name").val(ui.item.label);
			$("#customer_no").val(ui.item.id);
		}
	});
	
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>