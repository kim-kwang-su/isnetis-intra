<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="정보공유"/>
<c:set var="two_depth" value="Q&A"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="board_01.isnet">정보공유</a> / <strong>Q&amp;A</strong></div>
			<table id="list"></table>
			<div id="pager"></div>
			
			<!-- QnA 등록 및 수정 팝업 -->
			<div id="dialog-add-qna" class="faq-dialog" title="Q&A 등록">
				<form>
					<p>
						<label for="add_product_no_box">제품</label>
						<select id="add_product_no_box" name="PRODUCT_NO" class="mr20">
							<c:forEach var="product" items="${productList }">
								<option value="${product.PRODUCT_NO }">${product.PRODUCT_NAME }</option>
							</c:forEach>
						</select> 
					</p>
					<p>
						<label for="add_question_title">제목</label>
						<input type="text" id="add_question_title" name="QUESTION_TITLE"/>
					</p>
					<p>
						<label for="add_question_content">내용</label>
						<textarea id="add_question_content" name="QUESTION_CONTENT" style="height: 190px;" ></textarea>
					</p>
				</form>
			</div>			
			
			<div id="dialog-modify-qna" class="faq-dialog" title="FAQ 수정">
				<form>
					<input type="hidden" id="modify_question_no" name="QUESTION_NO" value=""/>
					<p>
						<label for="modify_product_no_box">제품</label>
						<select id="modify_product_no_box" name="PRODUCT_NO" class="mr20">
							<c:forEach var="product" items="${productList }">
								<option value="${product.PRODUCT_NO }">${product.PRODUCT_NAME }</option>
							</c:forEach>
						</select> 
					</p>
					<p>
						<label for="modify_question_title">제목</label>
						<input type="text" id="modify_question_title" name="QUESTION_TITLE"/>
					</p>
					<p>
						<label for="modify_question_content">내용</label>
						<textarea id="modify_question_content" name="QUESTION_CONTENT" style="height: 190px;" ></textarea>
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
			var question_no = url.substring(url.indexOf("=") +1);
			var rowData = $("#list").jqGrid("getRowData", question_no);
			var question_writer = rowData["QUESTION_WRITER"];
			var employee_no = '${LOGIN_USER.EMPLOYEE_NO}';
			if(question_writer !== employee_no){
				simple_popup("안내", "본인이 작성한 글만 수정/삭제 가능합니다.");
				return false;
			}
			
			if(job_gubun == "del"){
				var answer_cnt = rowData["ANSWER_CNT"];
				if(answer_cnt == 0){
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
										simple_popup("안내", "["+question_no+"]번 게시물이 삭제되었습니다.");
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
				$("#modify_question_no").val(question_no);
				$("#dialog-modify-qna").dialog("open");
			}
		}			
	});
	
	// QnA 등록 팝업
	$("#dialog-add-qna").dialog({
		autoOpen: false,
		height:410,
		width: 650,
		resizable: false,
		modal: true,
		open:function(){
			$("#add_product_no_box option :last").attr("selected", "selected");
			$("#add_question_no").val("");
			$("#add_question_title").val("");
			$("#add_question_content").val("");
		},
		buttons:[
			{
				text:"저장",
				click:function(){
					if($("#add_question_title").val() == ""){
						simple_popup("안내", "제목을 입력하세요");
						$("#add_question_title").focus();
						return false;
					}
					if($("#add_quesion_content").val() == ""){
						simple_popup("안내", "내용을 입력하세요");
						$("#add_question_content").focus();
						return false;
					}
					$.ajax({
						type:"POST",
						url:"add_board_02.isnet",
						dataType:"json",
						data:$("#dialog-add-qna form").serialize(),
						success:function(data, status){
							if(data && data.isSuccess){
								simple_popup("안내", "새로운 QnA가 등록되었습니다.");
								$("#list").jqGrid().trigger("reloadGrid");
							}else{
								simple_popup("안내", "QnA 등록 중 오류가 발생하였습니다.");
							}
						},
						error:function(){
							simple_popup("안내", "QnA 등록 중 오류가 발생하였습니다.");
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
	
	// QnA 등록 팝업
	$("#dialog-modify-qna").dialog({
		autoOpen: false,
		height:410,
		width: 650,
		resizable: false,
		modal: true,
		open:function(){
			// 질문글 정보 조회
			var question_no = $("#modify_question_no").val();
			$.ajax({
				type:"GET",
				url:"detail_board_01.isnet",
				dataType:"json",
				data:{QUESTION_NO:question_no},
				success:function(data, status){
					if(data && data.isSuccess){
						var question = data.question;
						$("#modify_question_no").val(question.QUESTION_NO);
						$("#modify_product_no_box option[value='"+question.PRODUCT_NO+"']").attr("selected", "selected");
						$("#modify_question_title").val(question.QUESTION_TITLE);
						$("#modify_question_content").val(question.QUESTION_CONTENT);
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
					if($("#modify_question_title").val() == ""){
						simple_popup("안내", "제목을 입력하세요");
						$("#modify_question_title").focus();
						return false;
					}
					if($("#modify_quesion_content").val() == ""){
						simple_popup("안내", "내용을 입력하세요");
						$("#modify_question_content").focus();
						return false;
					}
					$.ajax({
						type:"POST",
						url:"update_board_02.isnet",
						dataType:"json",
						data:$("#dialog-modify-qna form").serialize(),
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
	
	// QnA 리스트
	$("#list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		url:"list_02.isnet",
		datatype:"json",
		mtype:"POST",
		colNames:['번호', 'PRODUCT_NO', 'ANSWER_CNT', 'QUESTION_WRITER', '제품명', '제목',  '작성자', '등록일', '변경'],						
	   	colModel:[	
			{name:'QUESTION_NO', index:'QUESTION_NO', width:70, align:"center", sortable:false, search:false},	 
			{name:'PRODUCT_NO', index:'PRODUCT_NO', hidden:true},	 
	   		{name:'ANSWER_CNT',index:'ANSWER_CNT', hidden:true },
	   		{name:'QUESTION_WRITER',index:'QUESTION_WRITER', hidden:true },
			{name:'PRODUCT_NAME', index:'PRODUCT_NAME', width:120, align:"center", sortable:false, search:false},	 
	   		{name:'QUESTION_TITLE',index:'QUESTION_TITLE', width:670, align:"left", sortable:false, search:true, searchoptions:{sopt:['cn', 'eq']}},
	   		{name:'EMPLOYEE_NAME',index:'EMPLOYEE_NAME',  width:105, align:"center",sortable:false, search:true, searchoptions:{sopt:['eq']} },
	   		//{name:'QUESTION_HIT',index:'QUESTION_HIT', width:80, align:"center",sortable:false, search:false },
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
			var question_no = rowData["QUESTION_NO"];
			var page = $(this).jqGrid("getGridParam", "page");
	    	var rowNum = $(this).jqGrid("getGridParam", "rowNum");
			
			location.href = "board_03.isnet?QUESTION_NO="+question_no+"&page="+page+"&rowNum="+rowNum;
		}
	}).navGrid('#pager',{edit:false,add:false,del:false}, {}, {}, {}, {closeOnEscape:true, closeAfterSearch:true}, {})
	.navButtonAdd('#pager',
		{
			caption:"등록", 
		 	buttonicon:"ui-icon-plus", 
		 	onClickButton: function(){ 
		 		$("#dialog-add-qna").dialog("open");
		 	}, 
	 		id: "user_add_bt"
	 	}
	);

});
</script>		
<%@ include file="../common/bottom.jsp" %>