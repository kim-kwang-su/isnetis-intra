<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="사용자"/>
<c:set var="two_depth" value="내정보 보기"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="employee_01.isnet">사용자</a> / <strong>내정보 보기</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<div id="detail_form">
			<form>
				<fieldset>
					<legend>내 정보</legend>
					<p>
						<label for="employee_name">사용자명</label>
						<input id="employee_name" name="" type="text" disabled="disabled" value="<c:out value='${LOGIN_USER.EMPLOYEE_NAME }'/>"/>
					</p>
					<p>
						<label for="dept_name">소속부서</label>
						<input id="dept_name" name="" type="text" disabled="disabled" value="<c:out value='${LOGIN_USER.DEPT_NAME }'/>"/>
					</p>
					<p>
						<label for="dept_position">직위</label>
						<input id="dept_position" name="" type="text" disabled="disabled" value="<c:out value='${LOGIN_USER.DEPT_POSITION }'/>"/>
					</p>
					<p>
						<label for="tel_no">내선번호</label>
						<input id="tel_no" name="" type="text" disabled="disabled" value="<c:out value='${LOGIN_USER.TEL_NO }'/>"/>
					</p>
					<p>
						<label for="phone_no">연락처</label>
						<input id="phone_no" name="" type="text" disabled="disabled" value="<c:out value='${LOGIN_USER.PHONE_NO }'/>"/>
					</p>
					<p>
						<label for="mail">메일</label>
						<input id="mail" name="" type="text" disabled="disabled" value="<c:out value='${LOGIN_USER.EMAIL }'/>"/>
					</p>
					<p>
						<label for="last_login_time">마지막 로그인 시간</label>
						<span class="icon"><c:out value='${LOGIN_USER.LAST_LOGIN_TIME }'/></span>
					</p>
					
					<!-- 목록으로 이동하기 버튼 -->
					<p >
						<label for="last_login_time">내정보 변경</label>
						<button id="modify_bt">비밀번호 변경</button>
					</p>
				</fieldset>
				
			</form>
			</div>
			
		
			<!-- 사용자 등록 팝업 -->
			<div id="dialog-modify-employee" title="사용자 수정">
				<form>
					<p>
						<label>이름</label>
						<input type="text" id="modify_employee_name" name="EMPLOYEE_NAME"  value="${LOGIN_USER.EMPLOYEE_NAME }" disabled="disabled"/>
					</p>
					<p>
						<label>메일</label>
						<input type="text" id="modify_email" name="EMAIL" value="${LOGIN_USER.EMAIL }" disabled="disabled"/>
					</p>
					<p>
						<label>비밀번호</label>
						<input type="password" id="modify_pwd" name="LOGIN_PASSWORD" maxlength="12" title="비밀번호는 영문(대,소문자), 숫자, 특수문자(!@#$%_)를 섞어서 7글자이상 입력해주세요"/>
					</p>
					<p>
						<label>비밀번호 확인</label>
						<input type="password" id="modify_re_pwd" name="re_LOGIN_PASSWORD" maxlength="12"/>
					</p>
					<p>
						<label for="dept_combobox_popup">부서명</label>
						<select id="modify_dept_combobox_popup" name="DEPT_NO" >
						<option value="" selected="selected">소속 부서를 선택하세요</option>
						<c:forEach var="dept" items="${deptList }">
							<c:choose>
								<c:when test="${LOGIN_USER.DEPT_NO eq dept.DEPT_NO }">
									<option value="${dept.DEPT_NO }" selected="selected">${dept.DEPT_NAME }</option>
								</c:when>
								<c:otherwise>
									<option value="${dept.DEPT_NO }">${dept.DEPT_NAME }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					    </select>
					</p>
				</form>
			</div>		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->

<script type="text/javascript">
$(document).ready(function() {
	
	$("#modify_bt")
	.button()
	.click(function(event){
		event.preventDefault();
		$("#dialog-modify-employee").dialog("open");
	});
	
	
	
	$("#dialog-modify-employee").dialog({
		autoOpen: false,
		height: 300,
		width: 500,
		resizable: false,
		modal: true,
		open: function(){
			$("#modify_pwd").val("");
			$("#modify_re_pwd").val("");
			$("#modify_pwd").focus();
		},
		buttons:{
			"Save": function(){
				
				var param = {};
				
				if($("#modify_pwd").val() != ""){
					
					if(!is_valid_value("password", $("#modify_pwd").val()) || !is_valid_value("password", $("#modify_re_pwd").val())){
						alert("유효한 비밀번호가 아닙니다.");
						$("#modify_pwd").val("");
						$("#modify_re_pwd").val("");
						$("#modify_pwd").focus();
						return false;
					}
					
					if($("#modify_pwd").val() != $("#modify_re_pwd").val()){
						alert("비밀번호가 일치하지 않습니다..");
						$("#modify_pwd").val("");
						$("#modify_re_pwd").val("");
						$("#modify_pwd").focus();
						return false;
					}
					
					param["LOGIN_PASSWORD"] = $("#modify_pwd").val();
				}
				
				if($("#modify_dept_combobox_popup").val() == ""){
					alert("소속 부서를 선택하세요.");
					$("#modify_dept_combobox_popup").focus();
					return false;
				}
				param["DEPT_NO"] = $("#modify_dept_combobox_popup").val();
				
				
				$.ajax({
					url: "update_01.isnet",
					type: "POST",
					data: param,
					dataType: "json",
					success: function(result, status){
						if(result && result.isSuccess){
							alert("사용자 정보 수정이 완료되었습니다. 로그인 페이지로 이동합니다.");
						}else{
							alert(result.error_msg);
						}
						$("#dialog-modify-employee").dialog("close");
						location.href = "/login.isnet";
					},
					error: function(){
						alert("사용자 등록 중 오류가 발생하였습니다.");
						$("#dialog-modify-employee").dialog("close");
					}
				});
			},
			Cancel: function(){
				$(this).dialog("close");
			}
		}
	}); 
});
</script>		
<%@ include file="../common/bottom.jsp" %>