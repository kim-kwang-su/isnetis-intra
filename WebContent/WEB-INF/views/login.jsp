<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:remove var="LOGIN_USER" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="stylesheet" type="text/css" href="/common/css/main.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/common/css/print.css" media="print" />
<link rel="stylesheet" type="text/css" media="screen" href="/common/jquery-ui-1.9.2/css/blitzer/jquery-ui-1.9.2.custom.css" />
<!--[if lte IE 6]>
<link rel="stylesheet" type="text/css" href="css/ie6_or_less.css" />
<![endif]-->
<script type="text/javascript" src="/common/jquery-1.8.3/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/common/jquery-ui-1.9.2/js/jquery-ui-1.9.2.custom.js" ></script>
<title>isnet</title>
</head>
<body>
	<div id="login_form">
		<form id="form">
			<fieldset>
				<legend>로그인</legend>
				<!-- <p>
					<label style="color: red;">긴급공지</label>
					<strong>비밀번호관련 메일을 발송하였습니다.<br />확인바랍니다.</strong>
				</p> -->
				<p>
					<label for="product_no">아이디</label>
					<input type="text" id="login_email" name="EMAIL" value="li.eungsu@gmail.com">
				</p>
				<p>
					<label for="product_no">비밀번호</label>
					<input type="password" id="login_pwd" name="LOGIN_PASSWORD" value="zxcv1234!">
				</p>		
				<p style="clear:both;">
					<button style="float: right; margin-top: 10px; margin-right: 65px;" id="login_bt" title="로그인합니다.">로그인</button>
					<!-- <button style="float: right; margin-top: 10px; margin-right: 35px;" id="reg_bt" title="사용자를 등록합니다.">사용자 등록</button> -->
				</p>
			</fieldset>
		</form>
	</div>
	
	<!-- 사용자 등록 팝업 -->
	<%-- <div id="dialog-add-employee" title="사용자 등록">
		<form>
			<p>
				<label>이름</label>
				<input type="text" id="employee_name" name="EMPLOYEE_NAME" maxlength="16" title="이름은 한글로 2글자이상 입력해주세요"/>
			</p>
			<p>
				<label>메일</label>
				<input type="text" id="email" name="EMAIL" maxlength="64" title="이메일 주소를 입력해주세요"/>
			</p>
			<p>
				<label>비밀번호</label>
				<input type="password" id="pwd" name="LOGIN_PASSWORD" maxlength="12" title="비밀번호는 영문(대,소문자), 숫자, 특수문자(!@#$%_)를 섞어서 7글자이상 입력해주세요"/>
			</p>
			<p>
				<label>비밀번호 확인</label>
				<input type="password" id="re_pwd" name="re_LOGIN_PASSWORD" maxlength="12"/>
			</p>
			<p>
				<label for="dept_combobox_popup">부서명</label>
				<select id="dept_combobox_popup" name="DEPT_NO" >
				<option value="" selected="selected">소속 부서를 선택하세요</option>
				<c:forEach var="dept" items="${deptList }">
					<option value="${dept.DEPT_NO }">${dept.DEPT_NAME }</option>
				</c:forEach>
			    </select>
			</p>
		</form>
	</div>
	 --%>
</body>
<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	$("#login_pwd").keydown(function(event){
		if(event.keyCode == 13){
			$("#login_bt").trigger("click");
		}
	});
	
	$("#login_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			
			if($.trim($("#login_email").val()) == ""){
				alert("아이디를 입력하세요.");
				$("#login_email").focus();
				return false;
			}
			if($.trim($("#login_pwd").val()) == ""){
				alert("비밀번호를 입력하세요.");
				$("#login_pwd").focus();
				return false;
			}
			
			$.ajax({
				url: "/employee/login.isnet",
				type: "POST",
				data: $("#form").serialize(),
				dataType: "json",
				success: function(result, status){
					if(result && result.isSuccess ){
						location.href = "/home.isnet";
					}else{
						alert(result.error_msg);
					}
				}, 
				error: function(){
					alert("로그인 중 오류가 발생하였습니다. 잠시 후 다시 로그인해주세요.");
				}
			});
		});
	});
</script>
</html>
