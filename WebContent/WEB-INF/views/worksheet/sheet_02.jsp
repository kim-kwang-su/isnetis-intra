<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="now" class="java.util.Date"/>

<c:set var="one_depth" value="업무일지"/>
<c:set var="two_depth" value="조회"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="sheet_01.isnet">업무일지</a> / <a href="sheet_01.isnet">조회</a> / <strong>업무일지 상세조회</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>모듈 상세 정보</h3> -->
			<div id="detail_form">

			<c:forEach items="${worksheet }" var="sheet" varStatus="i1">
			<c:if test="${i1.first}">
			
			<form>
				<fieldset>
					<legend>업무일지 상세</legend>
					<p style="margin-bottom:5px;" class="mt5">
						<label for="employee_name">작성자</label>
						<input id="employee_name" name="" type="text" style="width: 200px;" disabled="disabled" value="${sheet.EMPLOYEE_NAME}"/>
						<label for="write_date">작성일자</label>
						<input id="write_date" name="" type="text" style="width: 200px;" disabled="disabled" value="${sheet.WRITE_DATE}"/>
						<button style="float: right;padding:0px 5px" class="icon" id="list_bt">목 록</button>
					</p>
					</fieldset>
			</form>
			
			</c:if>
			<form>
				<fieldset>
					<p>
						<label for="employee_name">사이트명</label>
						<input id="employee_name" name="" type="text" style="width: 200px;" disabled="disabled" value="${sheet.CUSTOMER_NAME }"/>
						<label for="write_date">고객명</label>
						<input id="write_date" name="" type="text" style="width: 200px;" disabled="disabled" value="${sheet.CLIENT_NAME }"/>
						<button style="float: right;padding:0px 3px" class="icon" id="del_sheet_bt_${sheet.SHEET_NO}">삭제</button>
						<button style="float: right;padding:0px 3px" class="icon" id="update_sheet_bt_${sheet.SHEET_NO}">수정</button>
					</p>
					<p>
						<label for="write_date">시작시간</label>
						<input id="write_date" name="" type="text" style="width: 200px;" disabled="disabled" value="${sheet.START_TIME }"/>
						<label for="write_date">종료시간</label>
						<input id="write_date" name="" type="text" style="width: 200px;" disabled="disabled" value="${sheet.END_TIME }"/>
					</p>
					<p>
						<label for="employee_name">지원형태</label>
						<strong>${sheet.SUPPORT_TYPE_NAME }</strong>
					</p>
					<p>
						<label for="employee_name">지원내용</label>
						<div>
							<input type="text" style='margin-top:-30px;margin-left:160px;margin-bottom:5px; width:600px;' id="" value="${sheet.SHEET_TITLE}" disabled>
							<textarea style="margin-top:0px; margin-bottom:5px; margin-left:160px; width:800px;" id="contents1" name="CONTENTS1" rows="10" readOnly>${sheet.SHEET_CONTENT}</textarea>
						</div>
					</p>
				</fieldset>
			</form>
			</c:forEach>
			
			<c:if test="${remote_size ne 0}">
					<c:forEach items="${remotesheet}" var="remote" varStatus="num">
						<form>
						<fieldset>
							<c:if test="${num.first}"><legend>유선 및 원격 지원</legend></c:if>
							<p>
								<label for="employee_name">사이트명</label>
								<input id="employee_name" name="" type="text" style="width: 200px;" disabled="disabled" value="${remote.CUSTOMER_NAME }"/>
								<label for="write_date">고객명</label>
								<input id="write_date" name="" type="text" style="width: 200px;" disabled="disabled" value="${remote.CLIENT_NAME }"/>
								<c:if test="${remote.EMPLOYEE_NO == LOGIN_USER.EMPLOYEE_NO}">
									<button style="float: right;padding:0px 3px" class="icon" id="del_remote_bt_${remote.REMOTE_NO}">삭제</button>
									<button style="float: right;padding:0px 3px" class="icon" id="update_remote_bt_${remote.REMOTE_NO}">수정</button>
								</c:if>
							</p>
							<p>
								<label for="employee_name">접수시간</label>
								<input id="employee_name" name="" type="text" style="width: 200px;" disabled="disabled" value="${remote.ACCEPTED_TIME }"/>
								<label for="write_date">완료시간</label>
								<input id="write_date" name="" type="text" style="width: 200px;" disabled="disabled" value="${remote.COMPLETED_TIME }"/>
							</p>
							<p>
								<label for="employee_name">주요 내용 및 결과 </label>
								<textarea style="width:800px;" name="CONTENTS1" rows="5" readOnly>${remote.REMOTE_CONTENT}</textarea>
							</p>
						</fieldset>
						</form>
					</c:forEach>
					
				</c:if>
			</div>		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->

<script type="text/javascript">
$(document).ready(function(){
	
	// 목록으로 이동 버튼 등록
	$("#list_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			
			var page = '<c:out value="${param.page}" default="1"/>';
			var rows = '<c:out value="${param.rows}" default="20"/>';
			var sidx = '<c:out value="${param.sidx}" />';
			var sord = '<c:out value="${param.sord}" />';
		
			location.href = "sheet_01.isnet?page=" + page + "&rows=" + rows  + "&sidx=" + sidx + "&sord=" + sord;		
	});
	//업무일지 수정 페이지 이동
	$("button[id^=update_sheet_bt_]")
	.button()
	.click(function(event){
		event.preventDefault();
		var no = $(this).attr("id").replace("update_sheet_bt_", "");
		if(confirm("수정 하시겠습니까?")) {
			location.href="sheet_03.isnet?sheet_no="+no;
		}
	});

	//업무일지 삭제
	$("button[id^=del_sheet_bt_]")
	.button()
	.click(function(event){
		event.preventDefault();
		var btn = $(this);
		var no = $(this).attr("id").replace("del_sheet_bt_", "");
		var cnt1 =$("button[id^=del_sheet_bt_]").length;
		
		if(confirm("삭제 하시겠습니까?")) {
			
			$.ajax(
					{url:"del_sheet01.isnet", 
					 type:"POST",
					 data:{"sheet_no":no}, 
					 dataType: "json",
					 success:function(result){
						if(result.isSuccess){
							simple_popup("안내","업무일지가 삭제 되었습니다.");
							if(cnt1 == 1) {
								location.href="sheet_01.isnet";
							}else {
								btn.parent().parent().parent().remove();
							}
						}else{
							simple_popup("안내", "업무일지 삭제 중 오류가 발생하였습니다.");
						}
					},
					error:function(){
						simple_popup("안내", "업무일지 삭제 중 오류가 발생하였습니다.");
					}
			});
			
		}
	});
	
	//원격 일지 수정 페이지 이동
	$("button[id^=update_remote_bt_]")
	.button()
	.click(function(event){
		event.preventDefault();
		var no = $(this).attr("id").replace("update_remote_bt_", "");
		if(confirm("수정 하시겠습니까?")) {
			location.href="sheet_04.isnet?remote_no="+no;
		}
	});
	
	//원격 일지 삭제
	$("button[id^=del_remote_bt_]")
	.button()
	.click(function(event){
		event.preventDefault();
		var btn = $(this);
		var no = $(this).attr("id").replace("del_remote_bt_", "");
		var cnt1 =$("button[id^=del_remote_bt_]").length;
		var resultFlag =false;
		
		if(confirm("삭제 하시겠습니까?")) {
			
			$.ajax(
					{url:"del_remote01.isnet", 
					 type:"POST",
					 data:{"remote_no":no}, 
					 dataType: "json",
					 success:function(result){
						if(result.isSuccess){
							simple_popup("안내","유선 및원격지원 일지가 삭제 되었습니다.");
							btn.parent().parent().parent().remove();
						}else{
							simple_popup("안내", "업무일지 삭제 중 오류가 발생하였습니다.");
						}
					},
					error:function(){
						simple_popup("안내", "업무일지 삭제 중 오류가 발생하였습니다.");
					}
			});
			
		}
	});
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>