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
			
			<!-- 컨텐츠 표시부 -->
			<div id="detail_form" class="mt10">
				<form>
				<fieldset>
					<legend>질문</legend>
					<div id="question_box" class="board_box" style="width: 95%; margin: 0 auto;"></div>
				</fieldset>
				<p style="width: 200px; margin: 0 auto">
					<button id="add_bt" title="답글을 등록합니다." >답글 쓰기</button>
					<button id="list_bt" title="목록으로 이동합니다.">목록</button>
				</p>
				</form>
				<div id="answerlist" class="board_list" style="width: 95%; margin: 0 auto;"></div>
			</div>				
			<!-- 컨텐츠 표시부 -->			
			<!-- QnA 등록 및 수정 팝업 -->
			<div id="dialog-add-answer" class="faq-dialog" title="답글 등록">
				<form>
					<p>
						<label for="add_answer_content">내용</label>
						<textarea id="add_answer_content" name="ANSWER_CONTENT" style="height: 190px;" ></textarea>
					</p>
				</form>
			</div>				
						
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	var reload_answerList = function(){
		$("#answerlist").html("");
		$.ajax({
			type:"GET",
			url:"list_03.isnet?QUESTION_NO=${param.QUESTION_NO}",
			dataType:"json",
			success:function(data, status){
				if(data && data.isSuccess){
					var answerList = data.answerList;
					
					var html = "<dl>";
					$.each(answerList, function(index, answer){
						html += "<dt>"+answer.CREATE_DATE+" ["+answer.EMPLOYEE_NAME+"]</dt>";
						html += "<dd>";
						html += answer.ANSWER_CONTENT_NEW_LINE;
						html += "<button class='icon_button2' id='answer_"+answer.ANSWER_NO+"_"+answer.ANSWER_WRITER+"'>삭제</button>";
						html += "</dd>";
						html += "<hr>";
					});
					html += "</dl>";
					$("#answerlist").html(html);
				}else{
					simple_popup("안내", "답글 조회 중 오류가 발생하였습니다.");
				}
				
				// 답글 삭제
				$(".icon_button2")
				.button({
					icons: {
						primary: "ui-icon-trash"
					},
					text: false
				}).click(function(event){
					event.preventDefault();
					var arr = $(this).attr("id").replace("answer_", "").split("_");
					var answer_no = arr[0];
					var answer_writer = arr[1];
					var employee_no = '${LOGIN_USER.EMPLOYEE_NO}';
					if(answer_writer !== employee_no){
						simple_popup("안내", "본인이 작성한 글만 수정/삭제 가능합니다.");
						return false;
					}
					confirm_popup(
						"안내", 
						"삭제하시겠습니까?", 
						function(){
							$(this).dialog("close");
							$.ajax({
								type:"GET",
								url:"del_board_02.isnet",
								dataType:"json",
								data:{ANSWER_NO:answer_no},
								success:function(data, status){
									if(data && data.isSuccess){
										reload_answerList();
									}else{
										simple_popup("안내", "답글 삭제 중 오류가 발생하였습니다.");
									}
								},
								error:function(){
									simple_popup("안내", "답글 삭제 중 오류가 발생하였습니다.");
								}
							});
						},
						function(){
							$(this).dialog("close");
						}					
					);					
				});
			},
			error:function(){
				simple_popup("안내", "답글 조회 중 오류가 발생하였습니다.");
			}
		});
	};
	// 질문글 표시
	$.ajax({
		type:"GET",
		url:"detail_board_01.isnet",
		dataType:"json",
		data:{QUESTION_NO:'${param.QUESTION_NO}'},
		success:function(data, status){
			if(data && data.isSuccess){
				var question = data.question;
				var html = "";
				html += '<dl>';
				html += '<dt>'+question.QUESTION_TITLE+'</dt>';
				html += '<dd class="desc">'+question.QUESTION_CONTENT_NEW_LINE+'</dd>';
				html += '<dd class="writer">'+question.EMPLOYEE_NAME+'</dd>';
				html += '<dd class="date">'+question.CREATE_DATE+'</dd>';
				html += '</dl>';
				$("#question_box").html(html);
			}else{
				simple_popup("안내", "QnA 정보 조회 중 오류가 발생하였습니다. 목록 페이지로 이동합니다.");
				var page = '<c:out value="${param.page}" default="1"/>';
				var rows = '<c:out value="${param.rows}" default="20"/>';
				location.href = "board_02.isnet?page=" + page + "&rows=" + rows;	
			}
		},
		error:function(){
			simple_popup("안내", "QnA 정보 조회 중 오류가 발생하였습니다. 목록 페이지로 이동합니다.");
			var page = '<c:out value="${param.page}" default="1"/>';
			var rows = '<c:out value="${param.rows}" default="20"/>';
			location.href = "board_02.isnet?page=" + page + "&rows=" + rows;	
		}
	});
	
	// 답글 표시
	reload_answerList();	
	
	// 버튼 처리
	$("#list_bt")
	.button()
	.click(function(event){
		event.preventDefault();
		
		var page = '<c:out value="${param.page}" default="1"/>';
		var rows = '<c:out value="${param.rows}" default="20"/>';
		location.href = "board_02.isnet?page=" + page + "&rows=" + rows;		
	});

	$("#add_bt")
	.button()
	.click(function(event){
		event.preventDefault();
		$("#dialog-add-answer").dialog("open");
	});
	
	$("#dialog-add-answer").dialog({
		autoOpen: false,
		height:350,
		width: 650,
		resizable: false,
		modal: true,
		open:function(){
			$("#add_answer_content").val("");
		},
		close:function(){
			$("#add_answer_content").val("");
		},
		buttons:[
			{
				text:"저장",
				click:function(){
					if($("#add_answer_content").val() == ""){
						simple_popup("안내", "내용을 입력하세요");
						$("#add_answer_content").focus();
						return false;
					}
					
					$.ajax({
						type:"POST",
						url:"add_board_03.isnet",
						dataType:"json",
						data:{QUESTION_NO:'${param.QUESTION_NO}', ANSWER_CONTENT:$("#add_answer_content").val()},
						success:function(data, status){
							if(data && data.isSuccess){
								reload_answerList();
							}else{
								simple_popup("안내", "답글 등록 중 오류가 발생하였습니다.");
							}
						},
						error:function(){
							simple_popup("안내", "답글 등록 중 오류가 발생하였습니다.");
						}
					});
					$(this).dialog("close");
				}
			},
			{
				text:"취소",
				click:function(){
					$(this).dialog("close");
				}
			}
		]
	});
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>