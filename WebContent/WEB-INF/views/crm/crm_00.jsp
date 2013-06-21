<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="CRM관리"/>
<c:set var="two_depth" value="불견일수관리"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="crm_00.isnet">CRM관리</a> / <strong>불견일수관리</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- 검색 조건 -->
			<div id="searchbox" style="padding:5px 30px;margin:10px 0 5px 0;border:1px solid #cecece;">
				<strong>고객사 : </strong>
				<select id="company_no_box" name="COMPANY_NO" class="mr20">
					<option value="-1" selected="selected">전체</option>
					<c:forEach var="company" items="${crmCompanyList }">
						<option value="${company.COMPANY_NO }">${company.COMPANY_NAME }</option>
					</c:forEach>
				</select> 
			
				<strong>방문자 : </strong>
				<select id="employee_no_box" name="EMPLOYEE_NO" class="mr20">
					<option value="-1" selected="selected">전체</option>
					<c:forEach var="employee" items="${employeeList }">
						<c:if test="${employee.DEPT_NO eq 3 }">
							<c:choose>
							<c:when test="${LOGIN_USER.EMPLOYEE_NO eq employee.EMPLOYEE_NO }">
								<option value="${employee.EMPLOYEE_NO }" selected="selected" >${employee.EMPLOYEE_NAME }</option>
							</c:when>
							<c:otherwise>
								<option value="${employee.EMPLOYEE_NO }">${employee.EMPLOYEE_NAME }</option>
							</c:otherwise>
						</c:choose>
						</c:if>
					</c:forEach>
			    </select>
			    
			    <strong>방문일자 : </strong>
				<input style="width:100px;" type="text" id="start_date" name="START_DATE" readonly="readonly"/> ~ 
				<input style="width:100px;" type="text" id="end_date" name="END_DATE" readonly="readonly"/>
				<button style="padding:0px 5px" class="icon " id="search_bt">조회</button>
				<button style="padding:0px 5px" class="icon mr20" id="init_bt">초기화</button>
				<button style="padding:0px 5px" class="icon" id="save_excel_bt">엑셀저장</button>
			</div>
			<!-- 검색 조건 -->
			
			<!-- 검색결과 표시 -->
			<table id="crm_list"></table>
			<div id="crm_pager"></div>
			
			<!-- 관리내역 등록 및 수정 팝업 -->
			<div id="dialog-add-sheet" class="crm-dialog" title="관리내역 등록 및 수정">
				<form>
					<input type="hidden" id="popup_sheet_no" name="SHEET_NO"/>
					<p>
						<label for="popup_company_no_box">고객사</label>
						<select id="popup_company_no_box" name="COMPANY_NO" class="mr20">
							<option value="" selected="selected">-- 고객사를 선택하세요 --</option>
							<c:forEach var="company" items="${crmCompanyList }">
								<option value="${company.COMPANY_NO }">${company.COMPANY_NAME }</option>
							</c:forEach>
						</select> 
					</p>
					<p>
						<label for="popup_customer_no_box">고객사 담당자</label>
						<select id="popup_customer_no_box" name="CUSTOMER_NO" class="mr20">
							<option value="" selected="selected">-- 먼저 고객사를 선택하세요 --</option>
						</select>
					</p>
					<p>
						<label for="popup_employee_no_box">방문자</label>
						<c:forEach var="employee" items="${employeeList }">
							<c:if test="${employee.DEPT_NO eq 3 }">
								<c:choose>
									<c:when test="${LOGIN_USER.EMPLOYEE_NO eq employee.EMPLOYEE_NO }">
										<input type="checkbox"  name="EMPLOYEE_NO" value="${employee.EMPLOYEE_NO }" style="width: 20px; margin-top: 5px" checked="checked"/><label style="display: inline; width: auto;">${employee.EMPLOYEE_NAME }</label>
									</c:when>
									<c:otherwise>
										<input type="checkbox"  name="EMPLOYEE_NO" value="${employee.EMPLOYEE_NO }" style="width: 20px; margin-top: 5px" /><label style="display: inline; width: auto;">${employee.EMPLOYEE_NAME }</label>
									</c:otherwise>					
								</c:choose>
							</c:if>
						</c:forEach>
					</p>
					<p>
						<label for="popup_visited_date">방문일자</label>
						<input type="text" id="popup_visited_date" name="VISITED_DATE" readonly="readonly"/>
					</p>
					<p>
						<label for="popup_visited_gubun_box">처리구분</label>
						<input type="radio"  name="VISITED_GUBUN" value="방문" style="width: 20px; margin-top: 5px" checked="checked"/><label style="display: inline; width: auto;">방문</label>
						<input type="radio"  name="VISITED_GUBUN" value="전화" style="width: 20px; margin-top: 5px"/><label style="display: inline; width: auto;">전화</label>
						<input type="radio"  name="VISITED_GUBUN" value="메일" style="width: 20px; margin-top: 5px"/><label style="display: inline; width: auto;">메일</label>
					</p>
					<p>
						<label for="popup_visited_memo">처리내용</label>
						<textarea id="popup_visited_memo" name="VISITED_MEMO" rows="5"></textarea>
					</p>
				</form>
			</div>	
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<form id="excel_form" method="post" action="list_crm_03.isnet"></form>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	// 날짜 입력관련 필드를 초기화한다.
	setDatepicker("#popup_visited_date", null, 0);
	setDatepicker("#start_date", null, null);
	$("#start_date").change(function(){
		setDatepicker("#end_date", $("#start_date").val(),null);
	});
	
	// 검색조건에서 최초 검색시작일을 해당월의 1일로 지정한다.
	var now = new Date();
	$("#end_date").val($.datepicker.formatDate('yy-mm-dd', now));
	//var prev_date = now.getDate() - 30;
	now.setDate(1);
	$("#start_date").val($.datepicker.formatDate('yy-mm-dd', now));
	
	// 검색조건 초기화 버튼
	$("#init_bt").click(function(event){
		event.preventDefault();
		
		// 고객사 초기화
		$("#company_no_box option :eq(0)").attr("selected", "selected");
		// 방문자 초기화
		$("#employee_no_box option :eq(0)").attr("selected", "selected");
		
		// 검색기간 초기화
		var now = new Date();
		$("#end_date").val($.datepicker.formatDate('yy-mm-dd', now));
		//var prev_date = now.getDate() - 30;
		now.setDate(1);
		$("#start_date").val($.datepicker.formatDate('yy-mm-dd', now));
		
		// 그리드 clear
		$("#crm_list").jqGrid("clearGridData");
	});
	
	// 엑셀저장
	$("#save_excel_bt").click(function(event){
		event.preventDefault();
		var html = "";
		var postData = $("#crm_list").jqGrid("getGridParam", "postData");
		postData["RES_TYPE"] = "excel";
		$.each(postData, function(key, value){
			html += "<input type='hidden' name='"+key+"' value='"+value+"'/>";
		});
   		$("#excel_form").html(html);
   		$("#excel_form").submit();
	});
	
	// 조회버튼 클릭시
	$("#search_bt").click(function(event){
		event.preventDefault();
		if($("#start_date").val() == ""){
			simple_popup("안내", "시작일을 지정하세요.");
			$("#start_date").focus();
			return false;
		}
		if($("#end_date").val() == ""){
			simple_popup("안내", "종료일을 지정하세요");
			$("#end_date").focus();
			return false;
		}
		
		$("#crm_list").jqGrid().trigger("reloadGrid");
	});
	
	// 관리내역 등록 팝업
	$("#dialog-add-sheet").dialog({
		autoOpen: false,
		height:410,
		width: 650,
		resizable: false,
		modal: true,
		open: function(){
			if($("#popup_sheet_no").val() == ""){
				// 관리내역 등록인 경우
				//$("#popup_company_no_box option:eq(0)").attr("selected", "selected");
				//$("#popup_customer_no_box").html('<option value="" selected="selected">-- 먼저 고객사를 선택하세요 --</option>');
				$("#popup_visited_date").val("");
				$(":radio[name='VISITED_GUBUN'] :eq(0)").attr("checked", "checked");
				$("#popup_visited_memo").val("");
			}else{
				// 관리내역 수정인 경우
				var rowid = $("#crm_list").jqGrid("getGridParam", "selrow");
				var rowData = $("#crm_list").jqGrid("getRowData", rowid);
				$("#popup_customer_no_box option").filter("[value='"+rowData["CUSTOMER_NO"]+"']").attr("selected", "selected");
			}
		},
		buttons:{
			"Save": function(){
				if($("#popup_company_no_box").val() == ""){
					simple_popup("안내", "고객사를 선택하세요");
					$("#popup_company_no_box").focus();
					return false;
				}
				if($("#popup_customer_no_box").val() == ""){
					simple_popup("안내", "고객사 담당자를 선택하세요");
					$("#popup_customer_no_box").focus();
					return false;
				}
				if($(":checkbox[name='EMPLOYEE_NO']:checked").size() == 0){
					simple_popup("안내", "방문자를 선택하세요");
					$("#popup_employee_no_box").focus();
					return false;
				}
				if($("#popup_visited_date").val() == ""){
					simple_popup("안내", "처리일자를 입력하세요");
					$("#popup_visited_date").focus();
					return false;
				}
				if($("#popup_visited_memo").val() == ""){
					simple_popup("안내", "처리내용을 입력하세요");
					$("#popup_visited_memo").focus();
					return false;
				}
				
				// 등록
				if($("#popup_sheet_no").val() == ""){
					$.ajax({
						type:"POST",
						url:"add_crm_02.isnet",
						dataType:"json",
						data:$("#dialog-add-sheet form").serialize(),
						success:function(data, status){
							if(data && data.isSuccess){
								simple_popup("안내", "관리내역이 등록되었습니다.");
								$("#crm_list").jqGrid().trigger("reloadGrid");
								$("#dialog-add-sheet").dialog("close");
								$("#popup_sheet_no").val("");	
							}else{
								simple_popup("안내", "관리내역 등록 중 오류가 발생하였습니다.");
								$("#dialog-add-sheet").dialog("close");
								$("#popup_sheet_no").val("");	
							}
						},
						error:function(){
							simple_popup("안내", "관리내역 등록 중 오류가 발생하였습니다.");
							$("#dialog-add-sheet").dialog("close");
							$("#popup_sheet_no").val("");	
						}
					});
				}else{
					// 수정
					$.ajax({
						type:"POST",
						url:"update_crm_01.isnet",
						dataType:"json",
						data:$("#dialog-add-sheet form").serialize(),
						success:function(data, status){
							if(data && data.isSuccess){
								simple_popup("안내", "관리내역이 수정되었습니다.");
								$("#crm_list").jqGrid().trigger("reloadGrid");
								$("#dialog-add-sheet").dialog("close");
								$("#popup_sheet_no").val("");	
							}else{
								simple_popup("안내", "관리내역 수정 중 오류가 발생하였습니다.");
								$("#dialog-add-sheet").dialog("close");
								$("#popup_sheet_no").val("");	
							}
						},
						error:function(){
							simple_popup("안내", "관리내역 수정 중 오류가 발생하였습니다.");
							$("#dialog-add-sheet").dialog("close");
							$("#popup_sheet_no").val("");	
						}
					});	
				}				
			},
			Cancel: function(){
				$(this).dialog("close");
				$("#popup_sheet_no").val("");	
			}
		}
	}); 
	
	// 팝업에서 고객사 콤보박스 변경할 때, 해당 고객사의 담당자정보를 가져옴.
	$("#popup_company_no_box").change(function(event){
		event.preventDefault();
		var selected_company_no = $("#popup_company_no_box").val();
		if(selected_company_no != ""){
			$("#popup_customer_no_box").html("");
			$.ajax({
				type:"GET",
				url:"list_crm_02.isnet",
				dataType:"json",
				data:{COMPANY_NO:selected_company_no},
				success:function(data, status){
					if(data && data.isSuccess){
						
						var customerList = data["crmCustomerList"];
						if(customerList.length > 0){
							var html = "";
							$.each(customerList, function(index, customer){
								html += "<option value='"+customer.CUSTOMER_NO+"'>"+customer.CUSTOMER_NAME+"</option>";
							});
							$("#popup_customer_no_box").html(html);
							
						}else{
							//simple_popup("안내", "선택한 고객사에는 담당자가 등록되어 있지 않습니다.");
							$("#popup_customer_no_box").html('<option value="1" selected="selected">없음</option>');
						}
					}else{
						simple_popup("안내", "고객사별 고객을 조회 중 오류가 발생하였습니다.");
						$("#popup_customer_no_box").html('<option value="" selected="selected">-- 다른 고객사를 선택하세요 --</option>');
					}
				},
				error:function(){
					simple_popup("안내", "고객사별 고객을 조회 중 오류가 발생하였습니다.");
					$("#popup_customer_no_box").html('<option value="" selected="selected">-- 다른 고객사를 선택하세요 --</option>');
				}
			});
		}
	});
	
	$("#crm_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:$(window).height() - 250,
		mtype:"POST",
		datatype: "json",
		url:"list_crm_03.isnet",
	   	colNames:['SHEET_NO','COMPANY_NO','CUSTOMER_NO','고객사','담당자', '방문자', '마지막 방문일', '불견일수', '처리구분', '처리내용'],						
	   	colModel:[	
			{name:'SHEET_NO', index:'SHEET_NO', hidden:true},
			{name:'COMPANY_NO', index:'COMPANY_NO', hidden:true},
			{name:'CUSTOMER_NO', index:'CUSTOMER_NO', hidden:true},
	   		{name:'COMPANY_NAME',index:'COMPANY_NAME', width:150, align:"center", sortable:true, search:false},
	   		{name:'CUSTOMER_NAME',index:'CUSTOMER_NAME', width:75, align:"center", sortable:true, search:false},
	   		{name:'EMPLOYEE_NAME',index:'EMPLOYEE_NAME', width:225, align:"center", sortable:false, search:false},
	   		{name:'VISITED_DATE',index:'VISITED_DATE', width:100, align:"center", sortable:true, search:false},
	   		{name:'DATE_GAP',index:'DATE_GAP', width:75, align:"center", sortable:false, search:false},
	   		{name:'VISITED_GUBUN',index:'VISITED_GUBUN', width:75, align:"center", sortable:true, search:false},
	   		{name:'VISITED_MEMO',index:'VISITED_MEMO', width:465, align:"left", sortable:false, search:false},
	   	],	
	    viewrecords: true,
	    // 제목
	    caption:"불견일수관리",
	    // 페이지 네비게이션 설정
	    pager:"crm_pager",
	   	page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="20"/>',
	   	rowList:[20,40,60],
	   	sortname:'<c:out value="${param.sidx}"/>',
	   	sortorder:'<c:out value="${param.sord}"/>',
	   	
	   	beforeRequest : function(){
	   		// 서버요청전에 검색조건의 값을 postData에 설정한다.
	   		var postData = $("#crm_list").jqGrid("getGridParam", "postData");
	   		
	   		postData["COMPANY_NO"] = $("#company_no_box").val();
	   		postData["EMPLOYEE_NO"] = $("#employee_no_box").val();
	   		postData["START_DATE"] = $("#start_date").val();
	   		postData["END_DATE"] = $("#end_date").val();
	   		postData["RES_TYPE"] = 'json';
	   		
	   		$("#crm_list").jqGrid("setGridParam", "postData", postData);
	   	},
	   	loadComplete:function(data){
	   		var records = data["records"];
	   		if(records == 0){
	   			simple_popup("안내", "검색조건에 해당하는 데이터가 존재하지 않습니다.");
	   		}
	   	},
	    onSelectRow:function(rowid){
	    	
	    },
	    loadError:function(xhr, status, error){
	    	alert("고객 리스트 조회 중 오류가 발생하였습니다.");
	    }
	}).navGrid('#crm_pager',{edit:false,add:false,del:false,search:false, refresh:false})
	.navButtonAdd('#crm_pager',
		{
			caption:"등록", 
		 	buttonicon:"ui-icon-plus", 
		 	onClickButton: function(){ 
		 			if($("#company_no_box").val() != "-1"){
			 			$("#popup_company_no_box option").filter("[value='"+$("#company_no_box").val()+"']").attr("selected", "selected").trigger("change");
		 			}else{
		 				$("#popup_company_no_box option:eq(0)").attr("selected", "selected");
		 				$("#popup_customer_no_box").html('<option value="" selected="selected">-- 먼저 고객사를 선택하세요 --</option>');
		 			}
					$("#dialog-add-sheet").dialog("open");
		 		}, 
	 		id: "user_add_bt"
	 	}
	).navButtonAdd('#crm_pager',
		{
			caption:"수정", 
		 	buttonicon:"ui-icon-pencil", 
		 	onClickButton: function(){ 
		 		var rowid = $("#crm_list").jqGrid("getGridParam", "selrow");
		 		if(rowid){
			   		var rowData = $("#crm_list").jqGrid("getRowData", rowid);
					$("#popup_sheet_no").val(rowData["SHEET_NO"]);			
					$("#popup_company_no_box option").filter("[value='"+rowData["COMPANY_NO"]+"']").attr("selected", "selected").trigger("change");
					$("#dialog-add-sheet :checkbox[name='EMPLOYEE_NO']").removeAttr("checked");
					var employeeArr = rowData["EMPLOYEE_NAME"].split(",");
					$.each(employeeArr, function(index, employee_name){
						$("#dialog-add-sheet label").filter(":contains('"+employee_name+"')").prev().attr("checked", "checked");
					});
					$("#dialog-add-sheet :radio[value='"+rowData["VISITED_GUBUN"]+"']").attr("checked", "checked");
					$("#popup_visited_date").val(rowData["VISITED_DATE"]);
					$("#popup_visited_memo").val(rowData["VISITED_MEMO"]);
					$("#dialog-add-sheet").dialog("open");
				}else{
					simple_popup("안내","그리드에서 수정할 행을 선택하세요.");
				}
		 	}, 
	 		id: "user_modify_bt"
	 	}
	).navButtonAdd('#crm_pager',
		{
		caption:"삭제", 
	 	buttonicon:"ui-icon-trash", 
	 	onClickButton: function(){ 
	 		var rowid = $("#crm_list").jqGrid("getGridParam", "selrow");
	 		if(rowid){
	 			confirm_popup("안내", "선택한 행을 삭제하겠습니까?",
		   			function(){
		   				$(this).dialog("close");
		   				$.ajax({
		   					type:"GET",
		   					url:"del_crm_01.isnet",
		   					dataType:"json",
		   					data:{SHEET_NO:rowid},
		   					success:function(data, status){
		   						if(data && data.isSuccess){
		   							simple_popup("안내", "선택한 행이 삭제되었습니다.");
		   							$("#crm_list").jqGrid().trigger("reloadGrid");
		   						}else{
		   							simple_popup("안내", "관리이력 삭제 중 오류가 발생하였습니다.");
		   						}
		   					},
		   					error:function(){
		   						simple_popup("안내", "관리이력 삭제 중 오류가 발생하였습니다.");
		   					}
		   				}); 
		   			},
		   			function(){
		   				$(this).dialog("close");
		   			}
		   		);
			}else{
				simple_popup("안내","그리드에서 삭제할 행을 선택하세요.");
			}
	 		}, 
 		id: "user_del_bt"
 		}
	);	
});
</script>		
<%@ include file="../common/bottom.jsp" %>