<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="버전관리"/>
<c:set var="two_depth" value="신규 모듈등록"/>

<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div style="text-align:right" id="breadcrumb"><a href="#">Home</a> / <a href="#">버전관리</a> / <strong>신규 모듈등록</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<div id="detail_form">
				<form id="module_form" method="post" action="add_module01.isnet" enctype="multipart/form-data">
					<fieldset>
						<legend>신규 모듈 등록</legend>
						<p>
							<label for="product_no">제품명</label>
							<select id="product_no" name="PRODUCT_NO">
								<option value="">제품을 선택하세요</option>
								<c:forEach var="product" items="${productList }">
									<option value="${product.PRODUCT_NO }">${product.PRODUCT_NAME }</option>
								</c:forEach>
						    </select>
						    
						 	<label for="location_no">설치경로</label>
							<select id="location_no" name="LOCATION_NO">
								<option value="">설치경로를 선택하세요</option>
								<c:forEach var="filePath" items="${filePathList }">
									<option value="${filePath.LOCATION_NO }">${filePath.FOLDER_PATH }</option>
								</c:forEach>
						    </select>
						</p>
						<p>    
						    <label for="product_no">모듈명</label>
						    <input type="text" id="module_name" name="MODULE_NAME" />

							<label for="file_version">파일버전</label>
							<input type="text" id="file_version" name="FILE_VERSION" />
						</p>						
						<p>
							<label>설명</label>
							<textarea id="module_desc" name="MODULE_DESC" rows="5"></textarea>
						</p>
						<p id="customer_check">
							<label for="">적용사이트</label>
							<button id="add_customer_bt" class="icon_button">적용사이트를 등록하세요.</button>
						</p>
						<p>
							<label>파일첨부</label>
						</p>
						<ul class="fileBox" style="margin-top:-30px;margin-left:160px;">
								<li><div>X86 릴리즈 : <input type="file" id='x86_release_file' name="x86_release_file"></div></li>
								<li><div>X64 릴리즈 : <input type="file" id='x64_release_file' name="x64_release_file"></div></li>
								<li><div>X86 디버그 : <input type="file" id='x86_debug_file' name="x86_debug_file"></div></li>
								<li><div>X64 디버그 : <input type="file" id='x64_debug_file' name="x64_debug_file"></div></li>
						</ul>
						<p>
							<label>수정내용</label>
							<textarea id="update_memo" name="UPDATE_MEMO" rows="5"></textarea>
						</p>
						<p style="clear:both;">
							<button style="float: right; margin-top: 10px; margin-right: 70px;" id="cancel_issue_bt">취소</button>
							<button style="float: right; margin-top: 10px; margin-right: 35px;" id="save_issue_bt">저장</button>
						</p>	
					</fieldset>
				</form>
			</div>
			<!-- 컨텐츠 표시부 끝 -->

			<!-- 적용사이트 등록 팝업 -->			
			<div id="dialog-modify-applied-customer" title="적용사이트 등록" style="height:50px;">
				<p class=""><strong>적용사이트를 지정하세요.</strong></p>
				<div id="customer_checkbox">
					<c:set var="title" value=""/>
					<c:set var="count" value="0" scope="page" />
					<c:forEach var="titleCustomerList" items="${titleCustomerList }" varStatus="num">
						<c:if test="${num.first}">
							<c:set var="title" value="${titleCustomerList.TITLE}" />
							<strong>- ${titleCustomerList.TITLE} -</strong><br/>	
						</c:if>
						<c:choose>
							<c:when test="${title eq titleCustomerList.TITLE}">
								<c:set var="count" value="${count + 1}" scope="page"/>
								<c:if test="${count eq 6 }"><br /></c:if>
								<input style="width:10px;" type="checkbox" id="checkbox${num.count }" name="CUSTOMER" value="${titleCustomerList.CUSTOMER_NO}"> 
								<label for="checkbox${num.count }">${titleCustomerList.CUSTOMER_NAME }</label>					
							</c:when>
							<c:otherwise>
								<c:set var="title" value="${titleCustomerList.TITLE}" />
								<c:set var="count" value="0" scope="page"/>
								<br/>
								<strong>-${titleCustomerList.TITLE}-</strong><br/>	
								<input type="checkbox" id="checkbox${num.count }" name="CUSTOMER" value="${titleCustomerList.CUSTOMER_NO}"> 
								<label for="checkbox${num.count }">${titleCustomerList.CUSTOMER_NAME }</label>								
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</div>
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
<script src="/common/jquery-fileuploader/jquery.form.js" type="text/javascript"></script>	
<script type="text/javascript">
	$(document).ready(function(){
		
		// 취소 버튼
		$("#cancel_issue_bt")
			.button()
			.click(function(event){
				event.preventDefault();
				location.href = "module_01.isnet";
			});
		
		// 저장 버튼 
		$("#save_issue_bt")
			.button()
			.click(function(event){
				event.preventDefault();
				
				// 제품명을 체크합니다.
				if($("#product_no").val() == ""){
					simple_popup("안내", "제품명을 선택해주세요.");
					$("#product_no").focus();
					return;
				}
				
				// 설치경로를 체크합니다.
				if($("#location_no").val() == ""){
					simple_popup("안내", "설치경로를 선택해주세요");
					$("#location_no").focus();
					return;
				}
				
				// 모듈명을 체크합니다.
				if($("#module_name").val() == ""){
					simple_popup("안내", "모듈명을 입력하세요.");
					$("#module_name").focus();
					return;
				}
				
				// 파일 버젼을 체크합니다.
				if($("#file_version").val() == ""){
					simple_popup("안내", "파일버젼을 입력하세요");
					$("#file_version").focus();
					return;
				}
				
				// 적용사이트를 체크합니다.
				if($(":hidden[name='CUSTOMER_NO']").size() == 0) {
					simple_popup("안내", "적용사이트를 지정하세요.");
					$("#add_customer_bt").focus();
					return;
				}
				
				// 수정내용을 체크합니다.
				if($("#update_memo").val() == ""){
					simple_popup("안내", "수정내용을 입력해주세요");
					$("#update_memo").focus();
					return;
				}
				
				// 파일 첨부여부 체크
				if($("#x86_release_file").val() == "" && $("#x64_release_file").val() == "" &&
				   $("#x86_debug_file").val() == "" &&  $("#x64_debug_file").val() == ""){
					  simple_popup("안내", "파일을 첨부하세요.");
					  $("#x86_release_file").focus();
					  return false;
				}
				
				$("#module_form").ajaxSubmit(function(data, state){
				    data = data.replace(/[<][^>]*[>]/gi, '');
				    var jData = JSON.parse(data);

					if(jData && jData.isSuccess){
						location.href = "module_01.isnet";
					}else{
						simple_popup("안내", "새로운 모듈 등록 중 오류가 발생하였습니다.");
					}
				}); 
			});
		
		// 적용사이트 등록용 팝업
		$("#dialog-modify-applied-customer").dialog({
			autoOpen: false,
			height: 400,
			width: 665,
			modal: true,
			create: function(){
				//$("#employee_checkbox").buttonset();
			},
			buttons: {
				"Save": function(){		
					var ischecked = false;
					//지원형태의 체크되어 있는지
					$("#dialog-modify-applied-customer :checkbox").each(function(index, checkbox){
						if(checkbox.checked){
							ischecked = true;
						}
					});
					
					if(ischecked){
						// 기존에 등록되어있는 아이콘 삭제$("#module_form :input[name='CUSTOMER_NO'] ")
						$("#customer_check .icon").remove();
						$("#module_form :input[name='CUSTOMER_NO'] ").remove();
						
						// 지원형태을 화면에 표시합니다.
						$("#dialog-modify-applied-customer :checkbox").each(function(index, checkbox){
							if(checkbox.checked){
								var customer_name = $(this).next().text();
								$('<span class="icon" >'+customer_name+'</span><input type="hidden" name="CUSTOMER_NO" value="'+$(this).val()+'"/>').insertBefore("#add_customer_bt");
							}
						});
						
						$(this).dialog("close");
					}else{
						simple_popup("안내", "지원 형태를 선택해주세요.");
					}
					
				},
				Cancel: function(){
					$("#dialog-modify-applied-customer :checkbox").removeAttr("checked");
					$(this).dialog("close");
				}
			},
			close:function(){
				//$("#dialog-modify-applied-customer :checkbox").removeAttr("checked");
			}
		});
		
		//적용사이트 버튼 
		$("#add_customer_bt")
			.button({
				icons: {
					primary: "ui-icon-circle-plus"
				},
				text: false
			})
			.click(function(event){
				event.preventDefault();
				// 지원형태용 팝업 열기
				$("#dialog-modify-applied-customer").dialog("open");
			});

	});
</script>		
<%@ include file="../common/bottom.jsp" %>