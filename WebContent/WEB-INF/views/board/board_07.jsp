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
			
			<!-- 컨텐츠 표시부 -->
			<div id="detail_form" class="mt10">
				<form>
				<fieldset>
					<legend>제안</legend>
					<div id="suggestion_box" class="board_box" style="width: 95%; margin: 0 auto;"></div>
				</fieldset>
				<p style="width: 200px; margin: 0 auto">
					<button id="add_bt" title="의견을 등록합니다." >의견 쓰기</button>
					<button id="list_bt" title="목록으로 이동합니다.">목록</button>
				</p>
				</form>
				<div id="commentlist" class="board_list" style="width: 95%; margin: 0 auto;"></div>
			</div>				
			<!-- 컨텐츠 표시부 -->			
			<!-- QnA 등록 및 수정 팝업 -->
			<div id="dialog-add-comment" class="faq-dialog" title="의견 등록">
				<form>
					<p>
						<label for="add_comment_content">의견</label>
						<textarea id="add_comment_content" name="COMMENT_CONTENT" style="height: 190px;" ></textarea>
					</p>
				</form>
			</div>				
						
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	var reload_commentList = function(){
		$("#answerlist").html("");
		$.ajax({
			type:"GET",
			url:"list_06.isnet?SUGGESTION_NO=${param.SUGGESTION_NO}",
			dataType:"json",
			success:function(data, status){
				if(data && data.isSuccess){
					var commentList = data.commentList;
					
					var html = "<dl>";
					$.each(commentList, function(index, comment){
						html += "<dt>"+comment.CREATE_DATE+" ["+comment.EMPLOYEE_NAME+"]</dt>";
						html += "<dd>";
						html += comment.COMMENT_CONTENT_NEW_LINE;
						html += "<button class='icon_button2' id='comment_"+comment.COMMENT_NO+"_"+comment.COMMENT_WRITER+"'>삭제</button>";
						html += "</dd>";
						html += "<hr>";
					});
					html += "</dl>";
					$("#commentlist").html(html);
				}else{
					simple_popup("안내", "의견 조회 중 오류가 발생하였습니다.");
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
					var arr = $(this).attr("id").replace("comment_", "").split("_");
					var comment_no = arr[0];
					var comment_writer = arr[1];
					var employee_no = '${LOGIN_USER.EMPLOYEE_NO}';
					if(comment_writer !== employee_no){
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
								url:"del_board_05.isnet",
								dataType:"json",
								data:{COMMENT_NO:comment_no},
								success:function(data, status){
									if(data && data.isSuccess){
										reload_commentList();
									}else{
										simple_popup("안내", "의견 삭제 중 오류가 발생하였습니다.");
									}
								},
								error:function(){
									simple_popup("안내", "의견 삭제 중 오류가 발생하였습니다.");
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
				simple_popup("안내", "의견 조회 중 오류가 발생하였습니다.");
			}
		});
	};
	// 사내제안글 표시
	$.ajax({
		type:"GET",
		url:"detail_board_02.isnet",
		dataType:"json",
		data:{SUGGESTION_NO:'${param.SUGGESTION_NO}'},
		success:function(data, status){
			if(data && data.isSuccess){
				var suggestion = data.suggestion;
				var html = "";
				html += '<dl>';
				html += '<dt>'+suggestion.SUGGESTION_TITLE+'</dt>';
				html += '<dd class="desc">'+suggestion.SUGGESTION_CONTENT_NEW_LINE+'</dd>';
				html += '<dd class="writer">'+suggestion.EMPLOYEE_NAME+'</dd>';
				html += '<dd class="date">'+suggestion.CREATE_DATE+'</dd>';
				html += '</dl>';
				$("#suggestion_box").html(html);
			}else{
				simple_popup("안내", "사내제안 정보 조회 중 오류가 발생하였습니다. 목록 페이지로 이동합니다.");
				var page = '<c:out value="${param.page}" default="1"/>';
				var rows = '<c:out value="${param.rows}" default="20"/>';
				//location.href = "board_06.isnet?page=" + page + "&rows=" + rows;	
			}
		},
		error:function(){
			simple_popup("안내", "사내제안 정보 조회 중 오류가 발생하였습니다. 목록 페이지로 이동합니다.");
			var page = '<c:out value="${param.page}" default="1"/>';
			var rows = '<c:out value="${param.rows}" default="20"/>';
			//location.href = "board_06.isnet?page=" + page + "&rows=" + rows;	
		}
	});
	
	// 답글 표시
	reload_commentList();	
	
	// 버튼 처리
	$("#list_bt")
	.button()
	.click(function(event){
		event.preventDefault();
		
		var page = '<c:out value="${param.page}" default="1"/>';
		var rows = '<c:out value="${param.rows}" default="20"/>';
		location.href = "board_06.isnet?page=" + page + "&rows=" + rows;		
	});

	$("#add_bt")
	.button()
	.click(function(event){
		event.preventDefault();
		$("#dialog-add-comment").dialog("open");
	});
	
	$("#dialog-add-comment").dialog({
		autoOpen: false,
		height:350,
		width: 650,
		resizable: false,
		modal: true,
		open:function(){
			$("#add_comment_content").val("");
		},
		close:function(){
			$("#add_comment_content").val("");
		},
		buttons:[
			{
				text:"저장",
				click:function(){
					if($("#add_comment_content").val() == ""){
						simple_popup("안내", "내용을 입력하세요");
						$("#add_comment_content").focus();
						return false;
					}
					
					$.ajax({
						type:"POST",
						url:"add_board_06.isnet",
						dataType:"json",
						data:{SUGGESTION_NO:'${param.SUGGESTION_NO}', COMMENT_CONTENT:$("#add_comment_content").val()},
						success:function(data, status){
							if(data && data.isSuccess){
								reload_commentList();
							}else{
								simple_popup("안내", "의견 등록 중 오류가 발생하였습니다.");
							}
						},
						error:function(){
							simple_popup("안내", "의견 등록 중 오류가 발생하였습니다.");
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