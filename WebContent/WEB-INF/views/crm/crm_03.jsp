<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="CRM관리"/>
<c:set var="two_depth" value="고객관리"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="crm_01.isnet">CRM관리</a> / <strong>고객사 관리</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<table id="crm_list"></table>
			<div id="crm_pager"></div>
			
			<!-- 고객사 정보 등록 및 수정 팝업 -->
			<div id="dialog-add-company" class="crm-dialog" title="고객사 등록 및 수정">
				<form>
					<input type="hidden" id="popup_company_no" name="COMPANY_NO"/>
					<p>
						<label for="popup_company_name">고객사</label>
						<input type="text" id="popup_company_name" name="COMPANY_NAME"/>
					</p>
					<p>
						<label for="popup_company_tel">전화번호</label>
						<input type="text" id="popup_company_tel" name="COMPANY_TEL"/>
					</p>
					<p>
						<label for="popup_company_fax">팩스번호</label>
						<input type="text" id="popup_company_fax" name="COMPANY_FAX"/>
					</p>
					<p>
						<label for="popup_company_addr">주소</label>
						<input type="text" id="popup_company_addr" style="width: 400px;" name="COMPANY_ADDR" />
					</p>
				</form>
			</div>	
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){	
	
	$("#dialog-add-company").dialog({
		autoOpen: false,
		height:270,
		width: 600,
		resizable: false,
		modal: true,
		open: function(){
			if($("#popup_company_no").val() == ""){
				// 고객사정보 등록인 경우
				$("#popup_company_name").val("");
				$("#popup_company_tel").val("");
				$("#popup_company_fax").val("");
				$("#popup_company_addr").val("");
			}
		},
		buttons:{
			"Save": function(){
				
				if($("#popup_company_name").val() == ""){
					simple_popup("안내", "고객사명을 입력하세요.");
					$("#popup_company_name").focus();
					return false;
				}
				// 등록
				if($("#popup_company_no").val() == ""){
					$.ajax({
						type:"POST",
						url:"add_crm_03.isnet",
						dataType:"json",
						data:$("#dialog-add-company form").serialize(),
						success:function(data, status){
							if(data && data.isSuccess){
								simple_popup("안내", "고객사 정보가 등록되었습니다.");
								$("#crm_list").jqGrid().trigger("reloadGrid");
								$("#dialog-add-company").dialog("close");
								$("#popup_company_no").val("");	
							}else{
								simple_popup("안내", "고객사정보 등록 중 오류가 발생하였습니다.");
								$("#dialog-add-company").dialog("close");
								$("#popup_company_no").val("");	
							}
						},
						error:function(){
							simple_popup("안내", "고객사정보 등록 중 오류가 발생하였습니다.");
							$("#dialog-add-company").dialog("close");
							$("#popup_company_no").val("");	
						}
					});
				}else{
					// 수정
					$.ajax({
						type:"POST",
						url:"update_crm_03.isnet",
						dataType:"json",
						data:$("#dialog-add-company form").serialize(),
						success:function(data, status){
							if(data && data.isSuccess){
								simple_popup("안내", "고객사정보가 수정되었습니다.");
								$("#crm_list").jqGrid().trigger("reloadGrid");
								$("#dialog-add-company").dialog("close");
								$("#popup_company_no").val("");	
							}else{
								simple_popup("안내", "고객사정보 수정 중 오류가 발생하였습니다.");
								$("#dialog-add-company").dialog("close");
								$("#popup_company_no").val("");	
							}
						},
						error:function(){
							simple_popup("안내", "고객사정보 수정 중 오류가 발생하였습니다.");
							$("#dialog-add-company").dialog("close");
							$("#popup_company_no").val("");	
						}
					});	
				}				
			},
			Cancel: function(){
				$(this).dialog("close");
				$("#popup_company_no").val("");	
			}
		}
	});
	
	$("#crm_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:$(window).height() - 250,
		mtype:"POST",
		datatype: "json",
		url:"list_crm_04.isnet",
	   	colNames:['COMPANY_NO','고객사명', '전화번호', '팩스번호','주소'],						
	   	colModel:[	
			{name:'COMPANY_NO', index:'CUSTOMER_NO', hidden:true},	   	          		
	   		{name:'COMPANY_NAME',index:'COMPANY_NAME', width:200, align:"center", sortable:true, search:true, searchoptions:{sopt:['eq','cn']}},
	   		{name:'COMPANY_TEL',index:'COMPANY_TEL', width:200, align:"center", sortable:true, search:true, searchoptions:{sopt:['eq','cn']}},
	   		{name:'COMPANY_FAX',index:'COMPANY_FAX', width:200, align:"center",sortable:true, search:true, searchoptions:{sopt:['eq','cn']}},
	   		{name:'COMPANY_ADDR',index:'COMPANY_ADDR', width:580, align:"center", sortable:true, search:true, searchoptions:{sopt:['eq', 'cn']}}
	   		//{name:'IS_USED',index:'IS_USED', width:80, align:"center", sortable:true, search:false}
	   	],	
	    viewrecords: true,
	    // 제목
	    caption:"고객 리스트",
	    // 페이지 네비게이션 설정
	    pager:"crm_pager",
	   	page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="20"/>',
	   	rowList:[20,40,60],
	   	sortname:'<c:out value="${param.sidx}"/>',
	   	sortorder:'<c:out value="${param.sord}"/>',
	   	
	    loadError:function(xhr, status, error){
	    	alert("고객사 리스트 조회 중 오류가 발생하였습니다.");
	    }
	}).navGrid("#crm_pager",{edit:false,add:false,del:false}, {}, {}, {}, {closeOnEscape:true, closeAfterSearch:true}, {})
	.navButtonAdd('#crm_pager',
		{
			caption:"등록", 
		 	buttonicon:"ui-icon-plus", 
		 	onClickButton: function(){ 
		 		$("#dialog-add-company").dialog("open");
		 	}, 
	 		id: "user_add_bt"
	 	}
	).navButtonAdd('#crm_pager',
		{
			caption:"수정", 
	 		buttonicon:"ui-icon-pencil", 
	 		onClickButton: function(){
	 			var rowid = $("#crm_list").jqGrid("getGridParam", "selrow");
	 			if(rowid){
		 			var rowData = $("#crm_list").jqGrid("getRowData", rowid);
		 			$("#popup_company_no").val(rowid);
		 			$("#popup_company_name").val(rowData["COMPANY_NAME"]);
		 			$("#popup_company_tel").val(rowData["COMPANY_TEL"]);
		 			$("#popup_company_fax").val(rowData["COMPANY_FAX"]);
		 			$("#popup_company_addr").val(rowData["COMPANY_ADDR"]);

		 			$("#dialog-add-company").dialog("open");
	 			}else{
	 				simple_popup("안내", "그리드에서 수정할 행을 선택하세요");
	 			}
	 			
	 		}, 
 			id: "user_modify_bt"
 		}
	).navButtonAdd('#crm_pager',
		{
			caption:"삭제", 
	 		buttonicon:"ui-icon-trash", 
	 		onClickButton: function(){ 
	 			var rowid = $("#crm_list").jqGrid("getGridParam", "selrow");
	 			if(rowid){
		 			confirm_popup(
		 				"안내", 
		 				"선택한 행을 삭제하시겠습니까?",
		 				function(){
		 					$.ajax({
		 						type:"POST",
		 						url:"update_crm_03.isnet",
		 						dataType:"json",
		 						data:{COMPANY_NO:rowid, IS_USED:'N'},
		 						success:function(data, status){
		 							if(data && data.isSuccess){
			   							simple_popup("안내", "선택한 행이 삭제되었습니다.");
			   							$("#crm_list").jqGrid().trigger("reloadGrid");
			   						}else{
			   							simple_popup("안내", "고객사정보 삭제 중 오류가 발생하였습니다.");
			   						}
		 						},
		 						error:function(){
		 							simple_popup("안내", "고객사정보 삭제 중 오류가 발생하였습니다.");
		 						}
		 					});
		 					$(this).dialog("close");	
		 				},
		 				function(){
		 					$(this).dialog("close");	
		 				}
		 			);
	 			}else{
	 				simple_popup("안내", "그리드에서 삭제할 행을 선택하세요");
	 			}
	 		}, 
 			id: "user_del_bt"
 		}
	);	
});
</script>		
<%@ include file="../common/bottom.jsp" %>