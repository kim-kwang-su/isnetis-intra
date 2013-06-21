<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="one_depth" value="업무일지"/>
<c:set var="two_depth" value="조회"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="sheet_01.isnet">업무일지</a> / <strong>수정</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>모듈 상세 정보</h3> -->
			<div id="detail_form">
		
			<form id="sheet_form">
				<fieldset>
					<legend>업무일지 수정</legend>
					<p class="mt10">
						<label for="accept_date">작성일자</label>
						<input type="hidden" name="SHEET_NO" value="${sheet.SHEET_NO }">
						<input type="text" id="write_date" name="WRITE_DATE" style="width: 200px;" value="${sheet.WRITE_DATE }" readonly="readonly"/>
						<label for="employee_no">작성자</label>
						<select id="employee_no" disabled="disabled">
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
						<label for="customer_no">고객사</label>
						<input type="hidden" id="customer_no" name="CUSTOMER_NO" value="${sheet.CUSTOMER_NO}">
						<c:forEach var="customer" items="${customerList }">
							<c:if test="${customer.CUSTOMER_NO  eq sheet.CUSTOMER_NO}">
									<input type="text" id="customer_name" name="CUSTOMER_NAME" value="${customer.CUSTOMER_NAME}">
							</c:if>
						</c:forEach>
						
					    <label for="client_name">담당자</label>
					    <input id="client_name" name="CLIENT_NAME" type="text" style="width: 200px;" value="${sheet.CLIENT_NAME }"/>
					</p>
					<p class="mt5">
						<label for="start_time">시작시간</label>
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
					    <label style="padding-left:85px;" for="end_time">종료시간</label>
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
					<p id="support_types">
						<label for="">지원형태</label>
						<c:forEach var="supportType" items="${sheet.supportList }">
							<span class='icon'>[${supportType.SUPPORT_TYPE_NAME }]</span>
							<input type="hidden" name="SUPPORT_TYPE_NO" value="${supportType.SUPPORT_TYPE_NO }">
						</c:forEach>
						<button id="add_support_bt" class="icon_button">지원형태를 등록하세요</button>
					</p>
					
					<p class="mt5">
						<label for="content" >지원내용</label>
						<input type="text" id="sheet_title" name="SHEET_TITLE" value="${sheet.SHEET_TITLE }" style="margin-bottom:5px; margin-left:5px; width:600px;" />
						<!-- <button style="margin-top:1px;" id="add_contens_bt" class="icon_button">지원내용를 등록하세요</button> -->
						<textarea style="margin-left:5px;" id="sheet_content" name="SHEET_CONTENT" rows="10">${sheet.SHEET_CONTENT }</textarea>
						
					</p>
					
					<!-- 목록으로 이동하기 버튼 -->
					<p style="clear:both;">
						<button style="float: right; margin-top: 10px; margin-right: 15px;" id="list_sheet_bt">목록</button>
						<button style="float: right; margin-top: 10px; margin-right: 15px;" id="cancel_sheet_bt">취소</button>
						<button style="float: right; margin-top: 10px; margin-right: 15px;" id="save_sheet_bt">저장</button>
					</p>
				</fieldset>
			</form>
			</div>
			
			<div id="dialog-support-type" title="지원형태 등록" style="height:50px;">
				<p class=""><strong>지원형태를 지정하세요.</strong></p>
				<div id="support_checkbox">
					<c:forEach var="supportList" items="${supportList }" varStatus="num">
						<input type="checkbox" id="checkbox${num.count }" name="suppotType" value="${supportList.SUPPORT_TYPE_NO}">
							<%-- <c:forEach var="supportType" items="${sheet.supportList}">
								<c:if test="${supportList.SUPPORT_TYPE_NO eq supportType.SUPPORT_TYPE_NO}">
									checked="checked"
								</c:if>
							</c:forEach>
							> --%> 
							<label for="checkbox${num.count }">${supportList.SUPPORT_TYPE_NAME }</label>
					</c:forEach>
				</div>
			</div>
			
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">

var contens_no = 0;
var contens_count = 0;

$(document).ready(function(){
	
	// 달력표시하기
	setDatepicker("#write_date", -5, 0);
	
	
	//시작, 완료시간표시
	var start_time='${sheet.START_TIME}';
	var end_time='${sheet.END_TIME}';
	$("#start_time").val(start_time.substring(0,2));
	$("#end_time").val(end_time.substring(0,2));
	$("#start_min").val(start_time.substring(3,5));
	$("#end_min").val(end_time.substring(3,5));
	
	// 고객사명 지정
	$("#customer_no option").each(function(){ 
		if($(this).val() == '${sheet.CUSTOMER_NO}'){ 
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
			location.href = "sheet_02.isnet?write_date=" + $("#write_date").val() + "&employee_no=" +$("#employee_no option:checked").val() ;
		});
	
	// 목록 버튼
	$("#list_sheet_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			location.href = "sheet_01.isnet" ;
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
/* 			if($.trim($("#client_name").val()) == ""){
				simple_popup("안내", "고객명을 선택해주세요");
				$("#client_name").focus();
				return;
			} */
			
			if($(":hidden[name='SUPPORT_TYPE_NO']").size() == 0){
				simple_popup("안내", "지원형태를 선택하세요");
				return;
			}
			
			
			var title = $.trim($("#sheet_title").val());
			var content = $.trim($("#sheet_content").val());
			if(title == "") {
				simple_popup("안내", "지원내용 제목을 입력해 주세요.");
				return;
			}
			if(content == "") {
				simple_popup("안내", "지원내용을 입력해 주세요.");
				return;
			}	
			
			// 서버로 전송합니다.
			$.ajax(
				{type:"POST",
				 url:"update_sheet01.isnet", 
				 data:$("#sheet_form").serialize(), 
				 dataType:"json",
				 success:function(result, status){
					if(status == "success" && result.isSuccess){
						simple_popup("안내", "업무일지 수정이  완료되었습니다."); 
						location.href = "sheet_02.isnet?write_date=" + $("#write_date").val() + "&employee_no=" +$("#employee_no option:checked").val() ;
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
		height: 230,
		width: 600,
		modal: true,
		open: function(){
			$("#dialog-support-type :checkbox").removeAttr("checked");
			$("input:hidden[name='SUPPORT_TYPE_NO']").each(function(){ 
				$("#checkbox"+$(this).val()).attr("checked", "checked");
			});
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