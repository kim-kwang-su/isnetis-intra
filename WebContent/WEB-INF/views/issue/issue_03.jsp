<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="이슈관리"/>
<c:set var="two_depth" value="이슈등록"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="issue_01.isnet">이슈관리</a> / <strong>이슈등록</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>모듈 상세 정보</h3> -->
			<div id="detail_form">
			<form id="issue_form">
				<fieldset>
					<legend>이슈 등록</legend>
					<p>
						<label for="accept_date">접수일자</label>
						<input type="text" id="accept_date" name="ACCEPT_DATE"  readonly="readonly"/>
						<label for="customer_request_date">고객 처리요청일</label>
						<input type="text" id="customer_request_date" name="CUSTOMER_REQUEST_DATE"  readonly="readonly"/>
						<label for="expected_complete_date">완료예정일</label>
						<input type="text" id="expected_complete_date" name="EXPECTED_COMPLETE_DATE"  readonly="readonly"/>
					</p>
					<p>
						<label for="expected_developed_date">개발완료 예정일</label>
						<input id="expected_developed_date" name="EXPECTED_DEVELOPED_DATE" type="text" readonly="readonly"/>
						<label for="developed_period">개발기간</label>
						<input type="text" id="developed_period" name="DEVELOPED_PERIOD"  />
					</p>
					<p class="mt10">
						<label for="customer_no">고객사명</label>
						<select id="customer_no" name="CUSTOMER_NO">
						<option value="">고객사를 선택하세요</option>
						<c:forEach var="customer" items="${customerList }">
							<option value="${customer.CUSTOMER_NO }">${customer.CUSTOMER_NAME }</option>
						</c:forEach>
					    </select>
						<label for="product_no">제품명</label>
						<select id="product_no" name="PRODUCT_NO">
						<option value="">제품을 선택하세요</option>
						<c:forEach var="product" items="${productList }">
							<option value="${product.PRODUCT_NO }">${product.PRODUCT_NAME }</option>
						</c:forEach>
					    </select>
					</p>
					<p>
						<label for="issue_kind">고객요청 분류</label>
						<select id="kind_no" name="KIND_NO">
						<option value="">고객요청 분류를 선택하세요</option>
						<c:forEach var="kind" items="${kindList }">
							<option value="${kind.KIND_NO }">${kind.KIND_NAME }</option>
						</c:forEach>
						</select>
						<label for="issue_priority">우선순위</label>
						<select id="issue_priority" name="ISSUE_PRIORITY">
							<option value="상">상</option>
							<option value="중" selected="selected">중</option>
							<option value="하">하</option>
					    </select>
					</p>
					<p id="developers">
						<label for="employee_no">접수자</label>
						<input type="text" value="${LOGIN_USER.EMPLOYEE_NAME }" disabled="disabled" />
						<label for="se_no">담당 SE</label>
						<select id="se_no" name="SE_NO" >
						<option value="">담당 SE를 선택하세요</option>
						<c:forEach var="employee" items="${employeeList }">
							<c:if test="${employee.DEPT_NO eq 1 }">
							<option value="${employee.EMPLOYEE_NO }">${employee.EMPLOYEE_NAME }</option>
							</c:if>
						</c:forEach>
					    </select>						
						
						<label for="developer_name">담당 개발자</label>
						<select id="developer_no" name="DEVELOPER_NO">
							<option value="">담당 개발자를 선택하세요</option>
							<c:forEach var="employee" items="${employeeList }">
								<c:if test="${employee.DEPT_NO eq 2 }">
								<option value="${employee.EMPLOYEE_NO }">${employee.EMPLOYEE_NAME }</option>
								</c:if>
							</c:forEach>
						</select>
						<!-- <button id="add_dev_bt" class="icon_button">담당 개발자 등록를 등록하세요</button>	 -->						
					</p>
					<p class="mt10">
						<label for="issue_memo">접수내용</label>
						<textarea id="issue_memo" name="ISSUE_MEMO" rows="15"></textarea>
					</p>
					<p class="mt5">
						<label for="issue_remark">비고</label>
						<textarea id="issue_remark" name="ISSUE_REMARK" rows="5"></textarea>
					</p>
					<!-- 목록으로 이동하기 버튼 -->
					<p style="clear:both;">
						<button style="float: right; margin-top: 10px; margin-right: 70px;" id="cancel_issue_bt">취소</button>
						<button style="float: right; margin-top: 10px; margin-right: 35px;" id="save_issue_bt">저장</button>
					</p>
				</fieldset>
			</form>
			</div>
			
			<%-- <div id="dialog-developers" title="담당 개발자 등록">
				<p class=""><strong>담당 개발자를 지정하세요.</strong></p>
				<div id="employee_checkbox">
					<c:forEach var="employee" items="${employeeList }" varStatus="num">
						<c:if test="${employee.DEPT_NO eq 2 or employee.DEPT_NO eq 5}">
							<input type="checkbox" id="checkbox${num.count }" name="en" value="${employee.EMPLOYEE_NO }"> <label class="mr10" for="checkbox${num.count }">${employee.EMPLOYEE_NAME }</label>
						</c:if>
					</c:forEach>
				</div>
			</div> --%>
			
			<div id="dialog-files" title="첨부파일 등록">
				<div id="main_container">
					<form action="add_issue04.isnet" method="post" enctype="multipart/form-data">
						<input type="file" name="file" class="fileUpload" multiple>
						<input type="hidden" name="in" id="hidden_issue_no"/> 
						<button id="px-submit" type="submit">Upload</button>
						<button id="px-clear" type="reset">Clear</button>
					</form>
				</div>
			</div>
			
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	// 달력표시하기
	setDatepicker("#accept_date", null, 0);
	setDatepicker("#customer_request_date", 0, null);
	setDatepicker("#expected_complete_date", 0, null);
	setDatepicker("#expected_developed_date", 0, null);
	
	// 로그인한 사용자로 접수자 지정
	$("#employee_no option").each(function(){ 
		if($(this).val() == '${LOGIN_USER.EMPLOYEE_NO}'){ 
			$(this).attr("selected", "selected");
		}
	});
	
	// 담당 개발자 등록 버튼
	/* $("#add_dev_bt")
		.button({
			icons: {
				primary: "ui-icon-circle-plus"
			},
			text: false
		})
		.click(function(event){
			event.preventDefault();
			// 담당개발자 등록용 팝업 열기
			$("#dialog-developers").dialog("open");
		}); */
	
	// 취소 버튼
	$("#cancel_issue_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			location.href = "issue_01.isnet";
		});
	
	// 저장 버튼 
	$("#save_issue_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			
			// 접수일자를 체크합니다.
			if($("#accept_date").val() == ""){
				simple_popup("안내", "접수일자를 지정해주세요");
				return;
			}
			
			// 고객사명을 체크합니다.
			if($("#customer_no").val() == ""){
				simple_popup("안내", "고객사를 선택해주세요");
				$("#customer_no").focus();
				return;
			}
			
			// 제품명을 체크합니다.
			if($("#product_no").val() == ""){
				simple_popup("안내", "제품을 선택해주세요");
				$("#product_no").focus();
				return;
			}
			
			// 고객요청 분류를 체크합니다.
			if($("#kind_no").val() == ""){
				simple_popup("안내", "요청 분류를 선택해주세요.");
				$("#kind_no").focus();
				return;
			}
			
			// 접수자를 체크합니다.
			if($("#employee_no").val() == ""){
				simple_popup("안내", "접수자를 선택해주세요");
				$("#employee_no").focus();
				return;
			}
			
			// 담당SE를 체크합니다.
			if($("#se_no").val() == ""){
				simple_popup("안내", "담당SE를 선택하세요");
				$("#se_no").focus();
				return;
			}
			
			// 담당개발자를 체크합니다.
			if($("#developer_no").val() == ""){
				simple_popup("안내", "담당개발자를 선택하세요");
				$("#developer_no").focus();
				return;
			}
			
			// 장애내용 등록여부를 체크합니다.
			if($("#issue_memo").val() == ""){
				simple_popup("안내", "장애내용을 입력해주세요");
				$("#issue_memo").focus();
				return;
			}
			
			// 서버로 전송합니다.
			$.ajax(
				{type:"POST",
				 url:"add_issue01.isnet", 
				 data:$("#issue_form").serialize(), 
				 dataType:"json",
				 success:function(result, status){
					if(status == "success" && result.isSuccess){
						var issue_no = result.issue_no;
						confirm_popup("안내", "<strong>이슈등록</strong>이 <strong>완료</strong>되었습니다.<br /><strong>첨부파일</strong>을 등록하시겠습니까?", 
								function(){
									
									$( this ).dialog( "close" );
									// 첨부파일 등록 팝업을 오픈합니다.
									$("#dialog-files").dialog("open");
									// 이슈번호를 전달합니다.
									$("#hidden_issue_no").val(issue_no);
									//window.open("issue_popup.isnet?in="+issue_no, "popup", "menubar=0,resizabel=0,scrollbars=1,status=0,toolbar=0,width=600,height=300");
								}, 
								function(){
									location.href = "issue_01.isnet";
								});
					}else{
						simple_popup("안내", "등록 중 오류가 발생하였습니다.");
					}
				},
			    error:function(){
				  	simple_popup("안내", "등록 중 오류가 발생하였습니다.");
			    }
			}); 
			
		});
	
	// 첨부파일 등록용 팝업창
	$("#dialog-files").dialog({
		autoOpen: false,
		height: 470,
		width: 500,
		resizable: false,
		modal: true,
		create:function(){
			jQuery(function($){
				$('.fileUpload').fileUploader();
			});
		},
		buttons: {
			"닫기": function(){
				$("#dialog-files").dialog("close");
				location.href = "issue_01.isnet";
			}
		},
		close: function(){
			location.href = "issue_01.isnet";
		}
	}); 
	
	// 담당개발자 등록용 팝업창
	$("#dialog-developers").dialog({
		autoOpen: false,
		height: 200,
		width: 500,
		resizable: false,
		modal: true,
		create: function(){
			//$("#employee_checkbox").buttonset();
		},
		buttons: {
			"Save": function(){		
				// 선택된 담당개발자가 있는지 체크
				var ischecked = false;
				$("#dialog-developers :checkbox").each(function(index, checkbox){
					if(checkbox.checked){
						ischecked = true;
					}
				});
				if(ischecked){
					// 기존에 등록되어있는 아이콘 삭제
					$("#developers .icon, #developers input[name='DEVELOPER_NO']").remove();
					// 등록된 담당개발자명을 화면에 표시합니다.
					$("#dialog-developers :checkbox").each(function(index, checkbox){
						if(checkbox.checked){
							var developername = $(this).next().text();
							$('<span class="icon" >'+developername+'</span><input type="hidden" name="DEVELOPER_NO" value="'+$(this).val()+'"/>').insertBefore("#add_dev_bt");
						}
					});
					$(this).dialog("close");
				}else{
					simple_popup("안내", "담당 개발자를 선택해주세요.");
				}
			},
			Cancel: function(){
				$(this).dialog("close");
			}
		}
	});
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>