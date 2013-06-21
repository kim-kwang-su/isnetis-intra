<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="업무일지"/>
<c:set var="two_depth" value="조회"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="sheet_01.isnet">업무일지</a> / <strong>원격 및 유선지원수정</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>모듈 상세 정보</h3> -->
			<div id="detail_form">
			<form id="sheet_form">
				<input type="hidden" name="REMOTE_NO" value="${remote.REMOTE_NO }">
				<fieldset>
					<legend>원격 및 유선지원 수정</legend>
					<p class="mt10">
						<label for="accept_date">작성일자</label>
						<input type="text" id="write_date" name="WRITE_DATE" style="width: 200px;" value="${remote.WRITE_DATE }" readonly="readonly"/>
						<label for="employee_no">작성자</label>
						<select id="employee_no" NAME="" disabled="disabled">
						<c:forEach var="employee" items="${employeeList }">
							<c:choose>
								<c:when test="${employee.EMPLOYEE_NO eq sheet.EMPLOYEE_NO }">
									<option value="${employee.EMPLOYEE_NO }" selected="selected">${employee.EMPLOYEE_NAME }</option>
								</c:when>
								<c:otherwise>
									<option value="${employee.EMPLOYEE_NO }">${employee.EMPLOYEE_NAME }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					    </select>
					</p>
					<p class="mt5">
						<label for="customer_no">고객사명</label>
						<input type="hidden" id="customer_no" name="CUSTOMER_NO" value="${remote.CUSTOMER_NO}">
						<input type="text" id="customer_name" name="CUSTOMER_NAME" value="${remote.CUSTOMER_NAME}">
						
					    <label for="client_name">고객명</label>
					    <input id="client_name" name="CLIENT_NAME" type="text" style="width: 200px;" value="${remote.CLIENT_NAME }"/>
					</p>
					<p class="mt5">
						<label for="start_time">접수시간</label>
						<select id="start_time" name="START_TIME" style="text-align:right; width:60px;"> 
							<c:forEach var="st" begin="0" end="23" step="1">
								<c:choose>
									 <c:when test="${st<= 9 }">
										<option value="0${st}">0${st}</option>
									</c:when>
									<c:otherwise>
										<option value="${st}">${st}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					    </select>
						<select id="start_min" name="START_MIN" style="margin-left:5px; text-align:right; width:60px;"> 
							<c:forEach var="sm" begin="0" end="50" step="10">
									<c:choose>
										 <c:when test="${sm<= 9 }">
											<option value="0${sm}">0${sm}</option>
										</c:when>
										<c:otherwise>
											<option value="${sm}">${sm}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
					    </select>
					    <label style="padding-left:85px;" for="end_time">완료시간</label>
						<select id="end_time" name="END_TIME" style="text-align:right; width:60px;"> 
							<c:forEach var="st" begin="0" end="23" step="1">
								<c:choose>
									 <c:when test="${st<= 9 }">
										<option value="0${st}">0${st}</option>
									</c:when>
									<c:otherwise>
										<option value="${st}">${st}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					    </select>
						<select id="end_min" name="END_MIN" style="margin-left:5px; text-align:right; width:60px;"> 
							<c:forEach var="sm" begin="0" end="50" step="10">
								<c:choose>
									 <c:when test="${sm<= 9 }">
										<option value="0${sm}">0${sm}</option>
									</c:when>
									<c:otherwise>
										<option value="${sm}">${sm}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					    </select>
					</p>
					<p class="mt5">
						<label for="contents" >주요 내용 및 결과</label>
						<textarea id="contents" name="REMOTE_CONTENT" rows="5">${remote.REMOTE_CONTENT }</textarea>
					</p>
					
					<div id="sheet_content" ></div>	
						
					<!-- 목록으로 이동하기 버튼 -->
					<p style="clear:both;">
						<button style="float: right; margin-top: 10px; margin-right: 70px;" id="cancel_sheet_bt">취소</button>
						<button style="float: right; margin-top: 10px; margin-right: 35px;" id="save_sheet_bt">저장</button>
					</p>
				</fieldset>
			</form>
			</div>
			
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">

$(document).ready(function(){
	
	// 달력표시하기
	setDatepicker("#write_date", -5, 0);
	
	var start_time='${remote.ACCEPTED_TIME}';
	var end_time='${remote.COMPLETED_TIME}';
	
	$("#start_time").val(start_time.substring(0,2));
	$("#end_time").val(end_time.substring(0,2));
	$("#start_min").val(start_time.substring(3,5));
	$("#end_min").val(end_time.substring(3,5));
	
	
	// 로그인한 사용자로작성자 지정
	$("#employee_no option").each(function(){ 
		if($(this).val() == '${LOGIN_USER.EMPLOYEE_NO}'){ 
			$(this).attr("selected", "selected");
		}
	});
	
	// 취소 버튼
	$("#cancel_sheet_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			location.href = "sheet_02.isnet?write_date=" + $("#write_date").val() + "&employee_no=" +$("#employee_no option:checked").val() ;
		});
	
	// 저장 버튼 
	$("#save_sheet_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			
			// 작성일자 체크합니다.
			if($("#write_date").val() == ""){
				simple_popup("안내", "작성 일자를 지정해주세요");
				return;
			}
			
			// 고객사명을 체크합니다.
			if($("#customer_no").val() == ""){
				simple_popup("안내", "고객사를 선택해주세요");
				$("#customer_name").focus();
				return;
			}
			
			// 고객명을 체크합니다.
			if($.trim($("#client_name").val()) == ""){
				simple_popup("안내", "고객명을 선택해주세요");
				$("#client_name").focus();
				return;
			}
			// 주요 내용 및 결과 를 체크합니다.
			if($.trim($("#contents").val()) == ""){
				simple_popup("안내", "주요 내용 및 결과를 입력해 주세요.");
				$("#contents").focus();
				return;
			}
			
			$.ajax({
				type:"POST"
				,url:"update_sheet02.isnet"
				,data:$("#sheet_form").serialize()
				,dataType:"json"
				,success:function(rtnObj){
					if(rtnObj!=null) {
						if( rtnObj.isSuccess){
							simple_popup("안내", "등록이 완료 되었습니다.");
							location.href = "sheet_02.isnet?write_date=" + $("#write_date").val() + "&employee_no=" +$("#employee_no option:checked").val() ;
						}else {
							simple_popup("안내", "등록 중 오류가 발생하였습니다.");
						}
					}
				},
			    error:function(){
				  	simple_popup("안내", "등록 중 오류가 발생하였습니다.");
			    }
			}); 
			
		});
	
	//업무일지 고객사 자동완성
 	$("#customer_name").autocomplete({
 		
		source:function( request, response ) {
		    $.ajax({
		    	type:"POST",
		        url: "../admin/search_customer.isnet",
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

/* //옵션값 삭제
function delContents(rtnNum) {
	contens_count -=1;
	$("#del_btn_"+rtnNum).parent().remove();
}  */
</script>		
<%@ include file="../common/bottom.jsp" %>