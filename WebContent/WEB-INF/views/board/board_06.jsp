<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="정보공유"/>
<c:set var="two_depth" value="사내제안"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="board_01.isnet">정보공유</a> / <strong>사내제안</strong></div>
			<table id="list"></table>
			<div id="pager"></div>
			
			<!-- QnA 등록 및 수정 팝업 -->
			<div id="dialog-add-sug" class="faq-dialog" title="사내제안 등록">
				<form>
					<p class="contact-method">
						<span>제안분야</span>
						<label><input  name="CATEGORY_NAME" type="radio"  value="제품" checked="checked"/> 제품</label>
						<label><input  name="CATEGORY_NAME" type="radio"  value="업무프로세스" /> 업무프로세스</label>
						<label><input  name="CATEGORY_NAME" type="radio"  value="고객서비스" /> 고객서비스</label>
						<label><input  name="CATEGORY_NAME" type="radio"  value="마케팅" /> 마케팅</label>
						<label><input  name="CATEGORY_NAME" type="radio"  value="기타" /> 기타</label>
					</p>
					<p>
						<label for="add_suggestion_title">제목</label>
						<input type="text" id="add_suggestion_title" name="SUGGESTION_TITLE" style="width: 600px;"/>
					</p>
					<p>
						<label for="add_suggestion_content">내용</label>
						<textarea id="add_suggestion_content" name="SUGGESTION_CONTENT" style="height: 340px; width: 600px;" ></textarea>
					</p>
					<p style="text-align: center;">
						<strong>*제안내용은 1. 제안배경 및 목적, 2. 내용 및 추진방안, 3. 기대효과 및 예상비용 등으로 구분해서 작성요망.</strong>
					</p>
				</form>
			</div>			
			
			<div id="dialog-modify-sug" class="faq-dialog" title="사내제안 수정">
				<form>
					<input type="hidden" id="modify_suggestion_no" name="SUGGESTION_NO" value=""/>
					<p class="contact-method">
						<span>제안분야</span>
						<label><input  name="CATEGORY_NAME" type="radio"  value="제품" checked="checked"/> 제품</label>
						<label><input  name="CATEGORY_NAME" type="radio"  value="업무프로세스" /> 업무프로세스</label>
						<label><input  name="CATEGORY_NAME" type="radio"  value="고객서비스" /> 고객서비스</label>
						<label><input  name="CATEGORY_NAME" type="radio"  value="마케팅" /> 마케팅</label>
						<label><input  name="CATEGORY_NAME" type="radio"  value="기타" /> 기타</label>
					</p>
					<p>
						<label for="modify_suggestion_title">제목</label>
						<input type="text" id="modify_suggestion_title" name="SUGGESTION_TITLE" style="width: 600px;"/>
					</p>
					<p>
						<label for="modify_suggestion_content">제안내용</label>
						<textarea id="modify_suggestion_content" name="SUGGESTION_CONTENT" style="height: 340px; width: 600px;" ></textarea>
					</p>
					<p style="text-align: center; ">
						<strong>*제안내용은 1. 제안배경 및 목적, 2. 내용 및 추진방안, 3. 기대효과 및 예상비용 등으로 구분해서 작성요망.</strong>
					</p>
				</form>
			</div>				
			
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	var job_gubun = "";
	
	// 수정/삭제 링크 처리
	$("#list a").live("click", function(event){
		event.preventDefault(); 
		var url = $(this).attr("href"); 
		job_gubun = /update|del/.exec(url);
		
		if(job_gubun){
			var suggestion_no = url.substring(url.indexOf("=") +1);
			var rowData = $("#list").jqGrid("getRowData", suggestion_no);
			var suggestion_writer = rowData["SUGGESTION_WRITER"];
			var employee_no = '${LOGIN_USER.EMPLOYEE_NO}';
			if(suggestion_writer !== employee_no){
				simple_popup("안내", "본인이 작성한 글만 수정/삭제 가능합니다.");
				return false;
			}
			
			if(job_gubun == "del"){
				var comments_cnt = rowData["COMMENTS_CNT"];
				if(comments_cnt == 0){
					confirm_popup(
						"안내", 
						"게시물을 삭제하시겠습니까?", 
						function(){
							$.ajax({
								type:"GET",
								url:url,
								dataType:"json",
								success:function(data, status){
									if(data && data.isSuccess){
										simple_popup("안내", "["+suggestion_no+"]번 게시물이 삭제되었습니다.");
										$("#list").jqGrid().trigger("reloadGrid");
									}else{
										simple_popup("안내", "게시물 삭제 중 오류가 발생하였습니다.");
									}
								},
								error:function(){
									simple_popup("안내", "게시물 삭제 중 오류가 발생하였습니다.");
								}
							});
							$(this).dialog("close");
						},
						function(){
							$(this).dialog("close");
						}
					);
				}else{
					simple_popup("안내", "답글이 있는 게시물은 삭제할 수 없습니다.");
				}
			}else if(job_gubun == "update"){
				$("#modify_suggestion_no").val(suggestion_no);
				$("#dialog-modify-sug").dialog("open");
			}
		}			
	});
	
	// 사내제안 등록 팝업
	$("#dialog-add-sug").dialog({
		autoOpen: false,
		height:580,
		width: 750,
		resizable:false,
		modal: true,
		open:function(){
			$("#dialog-add-sug :radio :first").attr("checked", "checked");
			$("#add_suggestion_no").val("");
			$("#add_suggestion_title").val("");
			$("#add_suggestion_content").val("");
		},
		buttons:[
			{
				text:"저장",
				click:function(){
					if($("#add_suggestion_title").val() == ""){
						simple_popup("안내", "제목을 입력하세요");
						$("#add_suggestion_title").focus();
						return false;
					}
					if($("#add_suggestion_content").val() == ""){
						simple_popup("안내", "내용을 입력하세요");
						$("#add_suggestion_content").focus();
						return false;
					}
					$.ajax({
						type:"POST",
						url:"add_board_05.isnet",
						dataType:"json",
						data:$("#dialog-add-sug form").serialize(),
						success:function(data, status){
							if(data && data.isSuccess){
								simple_popup("안내", "사내제안이 등록되었습니다.");
								$("#list").jqGrid().trigger("reloadGrid");
							}else{
								simple_popup("안내", "사내제안 등록 중 오류가 발생하였습니다.");
							}
						},
						error:function(){
							simple_popup("안내", "사내제안 등록 중 오류가 발생하였습니다.");
						}
					});
					$(this).dialog("close");
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
	
	// 사내제안 수정 팝업
	$("#dialog-modify-sug").dialog({
		autoOpen: false,
		height:580,
		width: 750,
		resizable: false,
		modal: true,
		open:function(){
			// 사내제안 정보 조회
			var suggestion_no = $("#modify_suggestion_no").val();
			$.ajax({
				type:"GET",
				url:"detail_board_02.isnet",
				dataType:"json",
				data:{SUGGESTION_NO:suggestion_no},
				success:function(data, status){
					if(data && data.isSuccess){
						var suggestion = data.suggestion;
						$("#modify_suggestion_no").val(suggestion.SUGGESTION_NO);
						$("#dialog-modify-sug :radio[value='"+suggestion.CATEGORY_NAME+"']").attr("checked", "checked");
						$("#modify_suggestion_title").val(suggestion.SUGGESTION_TITLE);
						$("#modify_suggestion_content").val(suggestion.SUGGESTION_CONTENT);
					}else{
						simple_popup("안내", "수정할 정보 조회 중 오류가 발생하였습니다.");
						$(this).dialog("close");
					}
				},
				error:function(){
					simple_popup("안내", "수정할 정보 조회 중 오류가 발생하였습니다.");
					$(this).dialog("close");
				}
			});
			
		},
		buttons:[
			{
				text:"수정",
				click:function(){
					if($("#modify_suggestion_title").val() == ""){
						simple_popup("안내", "제목을 입력하세요");
						$("#modify_suggestion_title").focus();
						return false;
					}
					if($("#modify_suggestion_content").val() == ""){
						simple_popup("안내", "내용을 입력하세요");
						$("#modify_suggestion_content").focus();
						return false;
					}
					$.ajax({
						type:"POST",
						url:"update_board_04.isnet",
						dataType:"json",
						data:$("#dialog-modify-sug form").serialize(),
						success:function(data, status){
							if(data && data.isSuccess){
								simple_popup("안내", "수정이 완료되었습니다.");
								$("#list").jqGrid().trigger("reloadGrid");
							}else{
								simple_popup("안내", "수정 중 오류가 발생하였습니다.");
							}
						},
						error:function(){
							simple_popup("안내", "수정 중 오류가 발생하였습니다.");
						}
					});
					$(this).dialog("close");
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
	
	// 사내제안 리스트
	$("#list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		url:"list_05.isnet",
		datatype:"json",
		mtype:"POST",
		colNames:['번호', 'COMMENTS_CNT', 'SUGGESTION_WRITER', '제안분야', '제목',  '작성자', '등록일', '변경'],						
	   	colModel:[	
			{name:'SUGGESTION_NO', index:'SUGGESTION_NO', width:70, align:"center", sortable:false, search:false},	
	   		{name:'COMMENTS_CNT',index:'COMMENTS_CNT', hidden:true },
	   		{name:'SUGGESTION_WRITER',index:'SUGGESTION_WRITER', hidden:true },
			{name:'CATEGORY_NAME', index:'CATEGORY_NAME', width:120, align:"center", sortable:false, search:false},	 
	   		{name:'SUGGESTION_TITLE',index:'SUGGESTION_TITLE', width:670, align:"left", sortable:false, search:true, searchoptions:{sopt:['cn', 'eq']}},
	   		{name:'EMPLOYEE_NAME',index:'EMPLOYEE_NAME',  width:105, align:"center",sortable:false, search:true, searchoptions:{sopt:['eq']} },
	   		{name:'CREATE_DATE',index:'CREATE_DATE', width:100, align:"center", sortable:false, search:false },
	   		{name:'URL_LINK',index:'URL_LINK', width:100, align:"center", sortable:false, search:false }
	   	],
	   	pager:"pager",
		page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="20"/>',
        rowList: [20, 40, 60],
        viewrecords: true,
		height:$(window).height() - 250,
		loadComplete:function(data){
	   		var records = data["records"];
	   		if(records == 0){
	   			simple_popup("안내", "검색조건에 해당하는 데이터가 존재하지 않습니다.");
	   		}
	   	},
	   	onSelectRow:function(rowid, status, e){
			var rowData = $(this).jqGrid("getRowData", rowid);
			var suggestion_no = rowData["SUGGESTION_NO"];
			var page = $(this).jqGrid("getGridParam", "page");
	    	var rowNum = $(this).jqGrid("getGridParam", "rowNum");
			
			location.href = "board_07.isnet?SUGGESTION_NO="+suggestion_no+"&page="+page+"&rowNum="+rowNum;
		}
	}).navGrid('#pager',{edit:false,add:false,del:false}, {}, {}, {}, {closeOnEscape:true, closeAfterSearch:true}, {})
	.navButtonAdd('#pager',
		{
			caption:"등록", 
		 	buttonicon:"ui-icon-plus", 
		 	onClickButton: function(){ 
		 		$("#dialog-add-sug").dialog("open");
		 	}, 
	 		id: "user_add_bt"
	 	}
	);

});
</script>		
<%@ include file="../common/bottom.jsp" %>