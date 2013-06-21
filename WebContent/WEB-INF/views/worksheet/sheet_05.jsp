<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="one_depth" value="업무일지"/>
<c:set var="two_depth" value="등록"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="sheet_01.isnet">업무일지</a> / <strong>등록</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>모듈 상세 정보</h3> -->
			<div id="tabs" style="margin-top:5px" class="ui-tabs ui-widget ui-widget-content ui-corner-all">  
				<!-- 탭 리스트  시작-->
				<ul> 
					<li><a href="#tabs-1">업무일지</a></li>
					<li><a href="#tabs-2">유선 및 원격지원</a></li>    
				</ul>
				<div id="tabs-1" style="magin-top:20px;">	
					<div style="margin:10px 0 0 0;" id="detail_form">
						<form id="sheet_form">
							<fieldset >
								<legend>업무일지 등록</legend>
								<p class="mt10">
									<label for="write_date">작성일자</label>
									<input type="text" id="write_date" name="WRITE_DATE" style="width: 200px;" readonly="readonly"/>
									<label for="employee_no">작성자</label>
									<select id="employee_no" disabled>
									<c:forEach var="employee" items="${employeeList }">
										<option value="${employee.EMPLOYEE_NO }">${employee.EMPLOYEE_NAME }</option>
									</c:forEach>
								    </select>
								</p>
								<p class="mt5">
									<label for="customer_no">고객사명</label>
									<input type="hidden" id="customer_no0" name="CUSTOMER_NO" value="">
									<input type="text" id="customer_name0" value="">
									
									
								    <label for="client_name">담당자명</label>
								    <input id="client_name" name="CLIENT_NAME" type="text" style="width: 200px;" value=""/>
								</p>
								<p class="mt5">
									<label for="start_time">시작시간</label>
									<select id="start_time" name="START_TIME" style="text-align:right; width:60px;"> 
										<c:forEach var="st" begin="7" end="23" step="1">
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
								    <label style="padding-left:85px;" for="end_time">종료시간</label>
									<select id="end_time" name="END_TIME" style="text-align:right; width:60px;"> 
										<c:forEach var="st" begin="7" end="23" step="1">
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
								<p id="support_types">
									<label for="">지원형태</label>
									<button id="add_support_bt" class="icon_button">지원형태를 등록하세요</button>
								</p>
								
								<p class="mt5">
									<label for="contents" >지원내용</label>
									<input type="text" id="title" name="SHEET_TITLE" style="margin-bottom:5px; margin-left:5px; width:600px;" />
									<!-- <button style="margin-top:1px;" id="add_contens_bt" class="icon_button">지원내용를 등록하세요</button> -->
									<textarea style="margin-left:5px;" id="contents" name="SHEET_CONTENT" rows="10"></textarea>
									
								</p>
									
								<!-- 목록으로 이동하기 버튼 -->
								<p style="clear:both;">
									<button style="float: right; margin-top: 10px; margin-right: 70px;" id="cancel_sheet_bt">취소</button>
									<button style="float: right; margin-top: 10px; margin-right: 35px;" id="save_sheet_bt">저장</button>
								</p>
							</fieldset>
						</form>
					</div>
				</div>
				<div id="tabs-2">
				<div style="margin:10px 0 0 0;" id="detail_form">
					<form id="remote_form">
					<fieldset>
						<legend>원격 및 유선지원 등록</legend>
						<p class="mt10">
							<label for="write_date1">작성일자</label>
							<input type="text" id="write_date1" name="WRITE_DATE1" style="width: 200px;" readonly="readonly"/>
							<label for="employee_no1">작성자</label>
							<select id="employee_no1" NAME="" disabled="disabled">
							<c:forEach var="employee" items="${employeeList }">
								<option value="${employee.EMPLOYEE_NO }">${employee.EMPLOYEE_NAME }</option>
							</c:forEach>
						    </select>
						</p>
						<p class="mt5">
							<label for="customer_no1">고객사명</label>
						    <input type="hidden" id="customer_no1" name="CUSTOMER_NO1">
						    <input type="text" id="customer_name1">
						    
						    <label for="client_name1">담당자명</label>
						    <input id="client_name1" name="CLIENT_NAME1" type="text" style="width: 200px;" value=""/>
						</p>
						<p class="mt5">
							<label for="start_time1">접수시간</label>
							<select id="start_time1" name="START_TIME1" style="text-align:right; width:60px;"> 
								<c:forEach var="st" begin="7" end="23" step="1">
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
							<select id="start_min1" name="START_MIN1" style="margin-left:5px; text-align:right; width:60px;"> 
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
							<select id="end_time1" name="END_TIME1" style="text-align:right; width:60px;"> 
								<c:forEach var="st" begin="7" end="23" step="1">
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
							<select id="end_min1" name="END_MIN1" style="margin-left:5px; text-align:right; width:60px;"> 
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
							<textarea id="contents1" name="CONTENTS1" rows="5"></textarea>
						</p>
						
						<div id="sheet_content" ></div>	
							
						<!-- 목록으로 이동하기 버튼 -->
						<p style="clear:both;">
							<button style="float: right; margin-top: 10px; margin-right: 70px;" id="cancel_remote_bt">취소</button>
							<button style="float: right; margin-top: 10px; margin-right: 35px;" id="save_remote_bt">저장</button>
						</p>
					</fieldset>
				</form>
				</div>				
					</div>
					
				</div>	
	
				<div id="dialog-support-type" title="지원형태 등록" style="height:50px;">
					<p class=""><strong>지원형태를 지정하세요.</strong></p>
					<div id="support_checkbox">
						<c:forEach var="supportList" items="${supportList }" varStatus="num">
								<input type="checkbox" id="checkbox${num.count }" name="suppotType" value="${supportList.SUPPORT_TYPE_NO}"> <label for="checkbox${num.count }">${supportList.SUPPORT_TYPE_NAME }</label>
						</c:forEach>
					</div>
				</div>
			
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">

var contens_no = 0;
var contens_count = 0;

$(document).ready(function(){
	
	//탭부분
	$( "#tabs" ).tabs();
	
	// 달력표시하기
	setDatepicker("#write_date", -5, 0);
	setDatepicker("#write_date1", -5, 0);
	
	//작성일자 표시
	$("#write_date").val($.datepicker.formatDate('yy-mm-dd', new Date()));
	$("#write_date1").val($.datepicker.formatDate('yy-mm-dd', new Date()));
	
	//시작, 완료시간표시
	var time= new Date();
	var h = time.getHours();
	var m = time.getMinutes();
	
	if(h <10) h= "0"+h;
	if(m <10) m= "00";
	if(m >=10 && m <20) m= "10";
	if(m >=20 && m <30) m= "20";
	if(m >=30 && m <40) m= "30";
	if(m >=40 && m <50) m= "40";
	if(m >=50 && m <=59) m= "50";
	
	
	$("#start_time").val(h);
	$("#end_time").val(h);
	$("#start_min").val(m);
	$("#end_min").val(m);
	
	$("#start_time1").val(h);
	$("#end_time1").val(h);
	$("#start_min1").val(m);
	$("#end_min1").val(m);
		
	
	// 로그인한 사용자로작성자 지정
	$("#employee_no option").each(function(){ 
		if($(this).val() == '${LOGIN_USER.EMPLOYEE_NO}'){ 
			$(this).attr("selected", "selected");
		}
	});
	
	// 로그인한 사용자로작성자 지정
	$("#employee_no1 option").each(function(){ 
		if($(this).val() == '${LOGIN_USER.EMPLOYEE_NO}'){ 
			$(this).attr("selected", "selected");
		}
	});
	//지원형태 
	$("#add_support_bt")
		.button({
			icons: {
				primary: "ui-icon-circle-plus"
			},
			text: false
		})
		.click(function(event){
			event.preventDefault();
			// 지원형태용 팝업 열기
			$("#dialog-support-type").dialog("open");
		});
	
	// 취소 버튼
	$("#cancel_sheet_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			location.href = "sheet_01.isnet";
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
			if($("#customer_no0").val() == ""){
				simple_popup("안내", "고객사를 선택해주세요");
				$("#customer_name0").focus();
				return;
			}
			
			var ischecked = false;
			$("#dialog-support-type :checkbox").each(function(index, checkbox){
				if(checkbox.checked){
					ischecked = true;
				}
			});
			
			if(!ischecked) {
				simple_popup("안내", "지원형태를 선택해주세요");
				return;
			}

			if($.trim($("#title").val()) == "") {
				simple_popup("안내","지원내용 제목을 입력해 주세요.");
				return;
			}

			if($.trim($("#contents").val()) == "") {
				simple_popup("안내","지원내용을 입력해 주세요.");
				return;
			}	
			
			// 서버로 전송합니다.
			$.ajax(
				{type:"POST",
				 url:"add_sheet01.isnet", 
				 data:$("#sheet_form").serialize(), 
				 dataType:"json",
				 success:function(result, status){
					if(status == "success" && result.isSuccess){
						simple_popup("안내", "일지등록이 완료 되었습니다.");
						location.href = "sheet_01.isnet";
								
					}else{
						simple_popup("안내", "등록 중 오류가 발생하였습니다.");
					}
				},
			    error:function(){
				  	simple_popup("안내", "등록 중 오류가 발생하였습니다.");
			    }
			}); 
			
		});
	
	// 지원형태 등록용 팝업창
	$("#dialog-support-type").dialog({
		autoOpen: false,
		height: 250,
		width: 500,
		resizable:false,
		modal: true,
		create: function(){
			//$("#employee_checkbox").buttonset();
		},
		buttons: {
			"Save": function(){		
				var ischecked = false;
				//지원형태의 체크되어 있는지
				$("#dialog-support-type :checkbox").each(function(index, checkbox){
					if(checkbox.checked){
						ischecked = true;
					}
				});
				
				if(ischecked){
					// 기존에 등록되어있는 아이콘 삭제
					$("#support_types .icon, #support_types input[name='SUPPORT_TYPE_NO']").remove();
					
					// 지원형태을 화면에 표시합니다.
					$("#dialog-support-type :checkbox").each(function(index, checkbox){
						if(checkbox.checked){
							var developername = $(this).next().text();
							$('<span class="icon" >['+developername+']</span><input type="hidden" name="SUPPORT_TYPE_NO" value="'+$(this).val()+'"/>').insertBefore("#add_support_bt");
						}
					});
					
					$(this).dialog("close");
				}else{
					simple_popup("안내", "지원 형태를 선택해주세요.");
				}
				
			},
			Cancel: function(){
				$(this).dialog("close");
			}
		}
	});
	
	
	
	// 취소 버튼
	$("#cancel_remote_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			location.href = "sheet_01.isnet";
		});
	
	// 저장 버튼 
	$("#save_remote_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			
			// 작성일자 체크합니다.
			if($("#write_date1").val() == ""){
				simple_popup("안내", "작성 일자를 지정해주세요");
				return;
			}
			
			// 고객사명을 체크합니다.
			if($("#customer_no1").val() == ""){
				simple_popup("안내", "고객사를 선택해주세요");
				$("#customer_name1").focus();
				return;
			}
			
			// 고객명을 체크합니다.
			if($.trim($("#client_name1").val()) == ""){
				simple_popup("안내", "고객명을 선택해주세요");
				$("#client_name").focus();
				return;
			}
			// 주요 내용 및 결과 를 체크합니다.
			if($.trim($("#contents1").val()) == ""){
				simple_popup("안내", "주요 내용 및 결과를 입력해 주세요.");
				$("#contents").focus();
				return;
			}
			
			$.ajax({
				type:"POST"
				,url:"add_sheet02.isnet"
				,data:$("#remote_form").serialize()
				,dataType:"json"
				,success:function(rtnObj){
					if(rtnObj!=null) {
						if( rtnObj.isSuccess){
							simple_popup("안내", "등록이 완료 되었습니다.");
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
 	$("input[id^=customer_name]").autocomplete({
 		
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

			var no = $(this).attr("id").replace("customer_name","");
			$("#customer_name" + no).val(ui.item.label);
			$("#customer_no" + no).val(ui.item.id);
			
		}
	});
	
});

//옵션값 삭제
function delContents(rtnNum) {
	contens_count -=1;
	$("#del_btn_"+rtnNum).parent().remove();
} 
</script>		
<%@ include file="../common/bottom.jsp" %>