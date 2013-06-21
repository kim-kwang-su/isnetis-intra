<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="관리자"/>
<c:set var="two_depth" value="사용자관리"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="admin_01.isnet">관리자</a> / <strong>사용자관리</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- 컨텐츠 표시부 시작 -->
			<table id="user_list"></table>
			<div id="user_pager"></div>

			<!-- 사용자 등록 팝업 -->
			<div id="dialog-add-employee" title="사용자 등록">
				<form>
					<p>
						<label for="dept_combobox_popup">부서명</label>
						<select id="dept_combobox_popup" name="DEPT_NO" >
						<option value="" selected="selected">소속 부서를 선택하세요</option>
						<c:forEach var="dept" items="${deptList }">
							<option value="${dept.DEPT_NO }">${dept.DEPT_NAME }</option>
						</c:forEach>
					    </select>
					</p>
					<p>
						<label>직위</label>
						<input type="text" id="dept_position" name="DEPT_POSITION" maxlength="2"/>
					</p>
					<p>
						<label>이름</label>
						<input type="text" id="employee_name" name="EMPLOYEE_NAME" maxlength="16" title="이름은 한글로 2글자이상 입력해주세요"/>
					</p>
					<p>
						<label>메일</label>
						<input type="text" id="email" name="EMAIL" maxlength="64" title="이메일 주소를 입력해주세요"/>
					</p>
					<p>
						<label>이슈메일 참조</label>
						<input type="radio"  name="IS_MAIL_CC" value="Y" style="width: 20px; margin-top: 5px" /><label style="display: inline; width: auto;">수신</label>
						<input type="radio"  name="IS_MAIL_CC" value="N" style="width: 20px; margin-top: 5px" checked="checked"/><label style="display: inline; width: auto;">미수신</label>
					</p>
					<p>
						<label>비밀번호</label>
						<input type="password" id="pwd" name="LOGIN_PASSWORD" maxlength="12" title="비밀번호는 영문(대,소문자), 숫자, 특수문자(!@#$%_)를 섞어서 7글자이상 입력해주세요"/>
					</p>
					<p>
						<label>비밀번호 확인</label>
						<input type="password" id="re_pwd" name="re_LOGIN_PASSWORD" maxlength="12"/>
					</p>
					
				</form>
			</div>		
			
			<!-- 사용자 수정 팝업 -->
			<div id="dialog-modify-employee" title="사용자 수정">
				<form>
					<p>
						<label for="dept_combobox_popup">부서명</label>
						<select id="modify_dept_combobox_popup" name="DEPT_NO" >
						<option value="" selected="selected">소속 부서를 선택하세요</option>
						<c:forEach var="dept" items="${deptList }">
							<option value="${dept.DEPT_NO }">${dept.DEPT_NAME }</option>
						</c:forEach>
					    </select>
					</p>
					<p>
						<label>직위</label>
						<input type="text" id="modify_dept_position" name="DEPT_POSITION" maxlength="2"/>
					</p>
					<p>
						<label>이름</label>
						<input type="text" id="modify_employee_name" name="EMPLOYEE_NAME"  value="" />
						<input type="hidden" id="modify_employee_no" name="EMPLOYEE_NO"  value="" />
					</p>
					<p>
						<label>메일</label>
						<input type="text" id="modify_email" name="EMAIL" value="" />
					</p>
					<p>
						<label>이슈메일 참조</label>
						<input type="radio"  name="IS_MAIL_CC" value="Y" style="width: 20px; margin-top: 5px" /><label style="display: inline; width: auto;">수신</label>
						<input type="radio"  name="IS_MAIL_CC" value="N" style="width: 20px; margin-top: 5px" checked="checked"/><label style="display: inline; width: auto;">미수신</label>
					</p>
					<p>
						<label>비밀번호</label>
						<input type="password" id="modify_pwd" name="LOGIN_PASSWORD" maxlength="12" title="비밀번호는 영문(대,소문자), 숫자, 특수문자(!@#$%_)를 섞어서 7글자이상 입력해주세요"/>
					</p>
					<p>
						<label>비밀번호 확인</label>
						<input type="password" id="modify_re_pwd" name="re_LOGIN_PASSWORD" maxlength="12"/>
					</p>
				</form>
			</div>		
			
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	$("#user_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:400,
		mtype:"POST",
		datatype: "json",
		url:"list_01.isnet",
	   	colNames:['EMPLOYEE_NO', 'DEPT_NO', '부서명', '직위','사용자명', '이메일', '등록일', '사용여부', '변경', '비밀번호 초기화', '메일참조여부'],						
	   	colModel:[	
			{name:'EMPLOYEE_NO', index:'EMPLOYEE_NO', hidden:true},	   	
			{name:'DEPT_NO', index:'DEPT_NO', hidden:true},	   	
	   		{name:'DEPT_NAME',index:'DEPT_NAME', width:130, align:"center", sortable:false},
	   		{name:'DEPT_POSITION',index:'DEPT_POSITION', width:70, align:"center", sortable:false},
	   		{name:'EMPLOYEE_NAME',index:'EMPLOYEE_NAME', width:100, align:"center", sortable:false},
	   		{name:'EMAIL',index:'EMAIL', width:200, align:"center", sortable:false},
	   		{name:'CREATE_DATE',index:'CREATE_DATE', width:200, align:"center", sortable:false},
	   		{name:'IS_USED',index:'IS_USED', width:100, align:"center", sortable:false},
	   		{name:'UPDATE_URL',index:'UPDATE_URL', width:100, align:"center", formatter: 'link', sortable:false},
	   		{name:'PWD_RESET', index:'PWD_RESET', width:150, align:"center", sortable:false},
	   		{name:'IS_MAIL_CC', index:'IS_MAIL_CC', width:100, align:"center", sortable:false}
	   	],	
	    viewrecords: true,
	    // 제목
	    caption:"사용자 목록",
	    // 페이지 네비게이션 설정
	    pager:"user_pager",
	   	page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="10"/>',
	   	rowList:[10, 20, 50],
	   	sortname:'<c:out value="${param.sidx}"/>',
	   	sortorder:'<c:out value="${param.sord}"/>',
	    loadError:function(xhr, status, error){
	    	alert("사용자 목록을 조회 중 오류가 발생하였습니다.");
	    }/* ,
	    gridComplete:function(){
	    	$("#user_add_bt .ui-button-text").css("padding", '');
	    } */
	}).navGrid('#user_pager',{edit:false,add:false,del:false,search:false, refresh:false})
	.navButtonAdd('#user_pager',
		{
			caption:"등록", 
		 	buttonicon:"ui-icon-plus", 
		 	onClickButton: function(){ 
					$("#dialog-add-employee").dialog("open");
		 		}, 
	 		id: "user_add_bt",
	 		title: "사용자를 등록합니다."
	 	}
	).navButtonAdd('#user_pager',
		 	{
		 		caption:"수정",
		 		buttonicon:"ui-icon-pencil",
		 		onClickButton: function(){
		 			var selected_id = $("#user_list").jqGrid("getGridParam", "selrow");
		 			if(selected_id){
			 			$("#dialog-modify-employee").dialog("open");
		 			}else{
		 				simple_popup("안내", "수정할 사용자를 선택하세요");
		 			}
		 		},
		 		id:"user_modify_bt",
		 		title: "사용자정보를 수정합니다."
		 	}
	);
	
	$("#dialog-add-employee").dialog({
		autoOpen: false,
		height: 350,
		width: 500,
		resizable: false,
		modal: true,
		open: function(){
			$("#employee_name").val("");
			$("#email").val("");
			$("#pwd").val("");
			$("#re_pwd").val("");
			$("#dept_combobox_popup").focus();
			$("#dept_combobox_popup option:eq(0)").attr("selected", "selected");
			$("#employee_name").focus();
		},
		buttons:{
			"Save": function(){
				
				if($("#dept_combobox_popup").val() == ""){
					alert("소속 부서를 선택하세요.");
					$("#dept_combobox_popup").focus();
					return false;
				}
				if($("#dept_position").val() == ""){
					simple_popup("안내","해당 직위를 입력하세요.");
					$("#dept_position").focus();
					return false;
				}
				if(!is_valid_value("username", $("#employee_name").val() )){
					alert("유효한 사용자 이름이 아닙니다.");
					$("#employee_name").focus();
					return false;
				}
				if(!is_valid_value("email", $("#email").val() )){
					alert("유효한 이메일이 아닙니다.");
					$("#email").focus();
					return false;
				}
				if(!is_valid_value("password", $("#pwd").val()) || !is_valid_value("password", $("#re_pwd").val())){
					alert("유효한 비밀번호가 아닙니다.");
					$("#pwd").val("");
					$("#re_pwd").val("");
					$("#pwd").focus();
					return false;
				}
				
				if($("#pwd").val() != $("#re_pwd").val()){
					alert("비밀번호가 일치하지 않습니다..");
					$("#pwd").val("");
					$("#re_pwd").val("");
					$("#pwd").focus();
					return false;
				}
				
				$.ajax({
					url: "add_employee.isnet",
					type: "POST",
					data: $("#dialog-add-employee form").serialize(),
					dataType: "json",
					success: function(result, status){
						if(result && result.isSuccess){
							simple_popup("안내","사용자 등록이 완료되었습니다");
						}else{
							simple_popup("안내",result.error_msg);
						}
						$("#dialog-add-employee").dialog("close");
						$("#user_list").trigger("reloadGrid");
					},
					error: function(){
						simple_popup("안내","사용자 등록 중 오류가 발생하였습니다.");
						$("#dialog-add-employee").dialog("close");
					}
				});
			},
			Cancel: function(){
				$(this).dialog("close");
			}
		}
	}); 
	
	$("#dialog-modify-employee").dialog({
		autoOpen: false,
		height: 350,
		width: 500,
		resizable: false,
		modal: true,
		open: function(){
			var selected_row_id = $("#user_list").jqGrid("getGridParam", "selrow");
			var employee = $("#user_list").jqGrid("getRowData", selected_row_id);
			
			$("#modify_dept_combobox_popup option").filter("[value='"+employee.DEPT_NO+"']").attr("selected", "selected");
			$("#modify_employee_no").val(employee.EMPLOYEE_NO);
			$("#modify_dept_position").val(employee.DEPT_POSITION);
			$("#modify_employee_name").val(employee.EMPLOYEE_NAME);
			$("#modify_email").val(employee.EMAIL);
			$("#dialog-modify-employee :radio").filter("[value='"+employee.IS_MAIL_CC+"']").attr("checked", "checked");
		},
		buttons:{
			"Save": function(){
				
				var param = {};
				param["EMPLOYEE_NO"] = $("#modify_employee_no").val();
				if($("#modify_dept_combobox_popup").val() == ""){
					simple_popup("안내","소속 부서를 선택하세요.");
					$("#modify_dept_combobox_popup").focus();
					return false;
				}
				param["DEPT_NO"] = $("#modify_dept_combobox_popup").val();
				
				if($("#modify_dept_position").val() == ""){
					simple_popup("안내","해당 직위를 입력하세요.");
					$("#modify_dept_position").focus();
					return false;
				}
				param["DEPT_POSITION"] = $("#modify_dept_position").val();
				
				if($("#modify_employee_name").val() == ""){
					simple_popup("안내","이름을 입력하세요.");
					$("#modify_employee_name").focus();
					return false;
				}
				param["EMPLOYEE_NAME"] = $("#modify_employee_name").val();
				
				if($("#modify_email").val() == ""){
					simple_popup("안내","이름을 입력하세요.");
					$("#modify_employee_name").focus();
					return false;
				}
				param["EMAIL"] = $("#modify_email").val();
				param["IS_MAIL_CC"] = $("#dialog-modify-employee :radio:checked").val();
				
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
				
				$.ajax({
					url: "update_03.isnet",
					type: "POST",
					data: param,
					dataType: "json",
					success: function(result, status){
						if(result && result.isSuccess){
							alert("사용자 정보 수정이 완료되었습니다.");
							$("#user_list").jqGrid().trigger("reloadGrid");
						}else{
							alert(result.error_msg);
						}
						$("#dialog-modify-employee").dialog("close");
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
function passwordReset(employee_no) {
	confirm_popup("안내", "비밀번호를 초기화 하시겠습니까?", 
			function(){
		  	   	$.ajax(
						{type:"POST",
						 url:"update_02.isnet", 
						 data:{"en":employee_no}, 
						 dataType:"json",
						 success:function(result){
							if(result.isSuccess){
								simple_popup("안내", result.success_msg);
							}
						 }
				});
		  	  $(this).dialog("close");
				},
				function(){
					$(this).dialog("close");//취소
			});

}

</script>		
<%@ include file="../common/bottom.jsp" %>