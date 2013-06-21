<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="정보공유"/>
<c:set var="two_depth" value="규정"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="board_01.isnet">정보공유</a> / <strong>규정</strong></div>
			<table id="list"></table>
			<div id="pager"></div>
			
			<!-- QnA 등록 및 수정 팝업 -->
			<div id="dialog-add-form" class="issue-dialog" title="규정 등록">
				<form method="POST" action="add_board_04.isnet" enctype="multipart/form-data">
					<input type="hidden" name="IS_FORM" value="N"/>
					<p>
						<label for="add_form_name">규정명</label>
						<input type="text" id="add_form_name" name="FORM_NAME" style="width: 300px;"/>
					</p>
					<p>
						<label for="add_create_date">시행일</label>
						<input type="text" id="add_create_date" name="CREATE_DATE" readonly="readonly"/>
					</p>
					<p>
						<label for="add_form_file">첨부파일</label>
						<input type="file" id="add_form_file" name="FORM_FILE" style="width: 300px;" />
					</p>
				</form>
			</div>			
			
			
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->

<script src="/common/jquery-fileuploader/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	var is_sec = '${LOGIN_USER.IS_SEC}';
	
	setDatepicker("#add_create_date", null, null);
	
	// 규정 등록 팝업
	$("#dialog-add-form").dialog({
		autoOpen: false,
		height:240,
		width: 500,
		resizable: false,
		modal: true,
		open:function(){
			$("#add_form_name").val("");
			$("#add_form_desc").val("");
		},
		buttons:[
			{
				text:"저장",
				click:function(){
					if($("#add_form_name").val() == ""){
						simple_popup("안내", "규정명을 입력하세요");
						$("#add_form_name").focus();
						return false;
					}
					
					if($("#add_create_date").val() == ""){
						simple_popup("안내", "시행일을 입력하세요");
						$("#add_create_date").focus();
						return false;
					}
					
					if($("#add_form_file").val() == ""){
						simple_popup("안내", "규정 파일을 등록하세요");
						$("#add_form_file").focus();
						return false;
					}
					
					$(this).dialog("close");
					
					$("#dialog-add-form form").ajaxSubmit(function(data, state){
					    data = data.replace(/[<][^>]*[>]/gi, '');
					    var jData = JSON.parse(data);
						if(jData && jData.isSuccess){
							simple_popup("안내", "규정이 등록되었습니다..");
							$("#list").trigger("reloadGrid");
						}else{
							simple_popup("안내", "규정 등록중 오류가 발생하였습니다.");
						}
					});
				}
			},
			{
				text:"닫기",
				click:function(){
					$(this).dialog("close");
				}
			}       
		]
	});
	
	
	// QnA 리스트
	$("#list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		url:"list_04.isnet?IS_FORM=N",	// 규정인 경우에는 IS_FORM을 N으로 설정한다.
		datatype:"json",
		mtype:"POST",
		colNames:['FORM_NO', 'FILE_NAME','번호', '규정명', '시행일(최종개정일)', '규정열람', ''],						
	   	colModel:[	
			{name:'FORM_NO', index:'FORM_NO', hidden:true},	
			{name:'FILE_NAME', index:'FILE_NAME', hidden:true},	
			{name:'NO', index:'NO', width:80, align:"center", sortable:false},
			{name:'FORM_NAME', index:'FORM_NAME', width:250, align:"left", sortable:false},	 
	   		{name:'CREATE_DATE',index:'CREATE_DATE',  width:150, align:"center",sortable:false},
	   		{name:'URL_LINK',index:'URL_LINK', width:100, align:"center", sortable:false },
	   		{name:'FORM_DESC',index:'FORM_DESC', width:590, align:"left", sortable:false}
	   	],
	   	pager:"pager",
		page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="20"/>',
        rowList: [20, 40, 60],
        viewrecords: true,
		height:$(window).height() - 250,
		beforeProcessing: function(data, status, xhr){
			
	   		if(data && data.isSuccess){
	    		$.each(data.rows, function(index, row){
	    			row["FORM_NAME"] = "&nbsp;&nbsp;" + row["FORM_NAME"];
	    			row["NO"] = index + 1;
	    			row["URL_LINK"] = "<a href='../pdf/"+row["FILE_NAME"]+"'><img src='../common/images/pdf.png' style='border: 0;'/></a>";
	    		});
	    	}
	   	},
		loadComplete:function(data){
	   		var records = data["records"];
	   		if(records == 0){
	   			//simple_popup("안내", "검색조건에 해당하는 데이터가 존재하지 않습니다.");
	   		}
	   	},
	   	ondblClickRow:function(rowid, iRow, iCol, e){
	   		var rowData = $("#list").jqGrid("getRowData", rowid);
	   		location.href = "../pdf/"+ rowData['FILE_NAME'];
	   	}
	}).navGrid('#pager',{edit:false,add:false,del:false, search:false}, {}, {}, {}, {closeOnEscape:true, closeAfterSearch:true}, {});
	
	if(is_sec == 'Y'){
		$("#list")
		.navButtonAdd('#pager',
			{
				caption:"등록", 
			 	buttonicon:"ui-icon-plus", 
			 	onClickButton: function(){ 
			 		$("#dialog-add-form").dialog("open");
			 	}, 
		 		id: "form_add_bt"
		 	}
		).navButtonAdd('#pager',
				{
			caption:"삭제", 
		 	buttonicon:"ui-icon-trash", 
		 	onClickButton: function(){ 
		 		var rowid = $("#list").jqGrid("getGridParam", "selrow");
		 		if(rowid){
		 			var rowData = $("#list").jqGrid("getRowData", rowid);
		 			confirm_popup("안내", rowData["FORM_NAME"] + '[을/를] 삭제하시겠습니까?',
		 					function(){
		 						$(this).dialog("close");
				 				$.ajax({
					 				type:"GET",
					 				url:"del_board_03.isnet",
					 				dataType:"json",
					 				data:{FORM_NO:rowid},
					 				success:function(data, status){
					 					if(data && data.isSuccess){
					 						simple_popup("안내", "양식이 삭제되었습니다.");
					 						$("#list").trigger("reloadGrid");
					 					}else{
					 						simple_popup("안내", "규정 삭제 중 오류가 발생하였습니다.");
					 					}
					 				},
					 				error:function(){
					 					simple_popup("안내", "규정 삭제 중 오류가 발생하였습니다.");
					 				}
					 			});
		 					},
		 					function(){
		 						$(this).dialog("close");
		 					}
		 			);			
		 		}else{
		 			simple_popup("안내", "삭제할 행을 선택하세요.");
		 		}
		 	}, 
	 		id: "form_del_bt"
		 	}
		);
	}

});
</script>		
<%@ include file="../common/bottom.jsp" %>