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
				<legend>�α���</legend>
				<!-- <p>
					<label style="color: red;">��ް���</label>
					<strong>��й�ȣ���� ������ �߼��Ͽ����ϴ�.<br />Ȯ�ιٶ��ϴ�.</strong>
				</p> -->
				<p>
					<label for="product_no">���̵�</label>
					<input type="text" id="login_email" name="EMAIL" value="li.eungsu@gmail.com">
				</p>
				<p>
					<label for="product_no">��й�ȣ</label>
					<input type="password" id="login_pwd" name="LOGIN_PASSWORD" value="zxcv1234!">
				</p>		
				<p style="clear:both;">
					<button style="float: right; margin-top: 10px; margin-right: 65px;" id="login_bt" title="�α����մϴ�.">�α���</button>
					<!-- <button style="float: right; margin-top: 10px; margin-right: 35px;" id="reg_bt" title="����ڸ� ����մϴ�.">����� ���</button> -->
				</p>
			</fieldset>
		</form>
	</div>
	
	<!-- ����� ��� �˾� -->
	<%-- <div id="dialog-add-employee" title="����� ���">
		<form>
			<p>
				<label>�̸�</label>
				<input type="text" id="employee_name" name="EMPLOYEE_NAME" maxlength="16" title="�̸��� �ѱ۷� 2�����̻� �Է����ּ���"/>
			</p>
			<p>
				<label>����</label>
				<input type="text" id="email" name="EMAIL" maxlength="64" title="�̸��� �ּҸ� �Է����ּ���"/>
			</p>
			<p>
				<label>��й�ȣ</label>
				<input type="password" id="pwd" name="LOGIN_PASSWORD" maxlength="12" title="��й�ȣ�� ����(��,�ҹ���), ����, Ư������(!@#$%_)�� ��� 7�����̻� �Է����ּ���"/>
			</p>
			<p>
				<label>��й�ȣ Ȯ��</label>
				<input type="password" id="re_pwd" name="re_LOGIN_PASSWORD" maxlength="12"/>
			</p>
			<p>
				<label for="dept_combobox_popup">�μ���</label>
				<select id="dept_combobox_popup" name="DEPT_NO" >
				<option value="" selected="selected">�Ҽ� �μ��� �����ϼ���</option>
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
				alert("���̵� �Է��ϼ���.");
				$("#login_email").focus();
				return false;
			}
			if($.trim($("#login_pwd").val()) == ""){
				alert("��й�ȣ�� �Է��ϼ���.");
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
					alert("�α��� �� ������ �߻��Ͽ����ϴ�. ��� �� �ٽ� �α������ּ���.");
				}
			});
		});
	});
</script>
</html>
