<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="버전관리"/>
<c:set var="two_depth" value="버전관리"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="module_01.isnet">모듈관리</a> / <strong>모듈상세정보</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>모듈 상세 정보</h3> -->
			<div id="detail_form">
			<form>
				<fieldset>
					<legend>모듈 기본정보</legend>
					<p>
						<label for="product_name">제품명</label>
						<input id="product_name" name="" type="text" disabled="disabled" value="<c:out value='${moduleInfo.PRODUCT_NAME }'/>"/>
						<label for="module_name">모듈명</label>
						<span style="display: block; float: left; margin-top: 5px; margin-right: 10px;" id="module_name"><c:out value='${moduleInfo.MODULE_NAME }'/></span>
					</p>
					<p>
						<label for="update_date">수정일</label>
						<input id="update_date" name="" type="text" disabled="disabled" value="<c:out value='${moduleInfo.UPDATE_DATE }'/>"/>
						<label for="employee_name">모듈 수정자</label>
						<input id="employee_name" name="" type="text" disabled="disabled" value="<c:out value='${moduleInfo.EMPLOYEE_NAME }'/>"/>
					</p>
					<p>
						<label for="folder_path">설치 위치</label>
						<input id="folder_path" name="" type="text" disabled="disabled" value="<c:out value='${moduleInfo.FOLDER_PATH }'/>"/>
						<label for="last_file_version">최종 파일버전</label>
						<input id="last_file_version" name="" type="text" disabled="disabled" value="<c:out value='${moduleInfo.FILE_VERSION }'/>" />
					</p>
					<p>
						<label for="module_desc">설명</label>
						<textarea id="module_desc" name="" style="width: 855px;" rows="5">${moduleInfo.MODULE_DESC }</textarea>
						<button style="float: right; margin-right: 15px;" id="modify_bt" title="모듈정보를 수정합니다.">기본정보 수정</button>
					</p>
				</fieldset>	
				<fieldset style="margin-top: 15px;">
					<legend>수정이력 정보</legend>				
					<div id="process_box">
						<table id="progress_list"></table>
						<div id="pager"></div>
					</div>					
					<p style="clear:both;">
						<button style="float: right; margin-top: 10px; margin-right: 10px;" id="list_bt" title="최근 모듈 리스트로 이동합니다.">목록</button>
						<button style="float: right; margin-top: 10px; margin-right: 10px;" id="modify_history_bt" title="선택한 행의 모듈이력정보를 수정합니다.">수정</button>
						<button style="float: right; margin-top: 10px; margin-right: 10px;" id="view_history_bt" title="선택한 행의 모듈이력정보를 조회합니다.">보기</button>
					</p>
				</fieldset>
				
			</form>
			</div>
			
			<!-- 적용사이트 추가 팝업을 생성합니다. -->
			<div id="dialog-modify-applied-customer" title="적용사이트 수정" style="height:50px;">
			<form id="customer_form">
				<input type="hidden" id="hidden_mhn" name="hidden_mhn"  value="">
				<input id="hidden_mn" type="hidden" name="hidden_mn" value="<c:out value='${moduleInfo.MODULE_NO }'/>">
				<input id="product_no" type="hidden" name="PRODUCT_NO" value="<c:out value='${moduleInfo.PRODUCT_NO }'/>">

				<p>
					<strong>모듈이 적용된 사이트는 아래와 갔습니다.</strong>
					<br />
					<span id="div_customer" style="color: red; font-weight: bold;"></span>
				</p>
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
								<input type="checkbox" id="check_box${titleCustomerList.CUSTOMER_NO}" name="CUSTOMER_NO" value="${titleCustomerList.CUSTOMER_NO}"> 
								<label for="checkbox${num.count }">${titleCustomerList.CUSTOMER_NAME }</label>
							</c:when>
							<c:otherwise>
								<c:set var="title" value="${titleCustomerList.TITLE}" />
								<c:set var="count" value="0" scope="page"/>
								<br/>
								<strong>-${titleCustomerList.TITLE}-</strong><br/>	
								<input type="checkbox" id="check_box${titleCustomerList.CUSTOMER_NO}" name="CUSTOMER_NO" value="${titleCustomerList.CUSTOMER_NO}"> 
								<label for="checkbox${num.count }">${titleCustomerList.CUSTOMER_NAME }</label>					
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</form>
			</div>
			
			<!-- 수정이력 등록 팝업을 생성합니다. -->
			<div id="dialog-add-module-history" class="module-dialog" title="수정이력 등록">
				<form method="post" action="add_module02.isnet" enctype="multipart/form-data" >
					<input type="hidden" name="MODULE_NO" value="${moduleInfo.MODULE_NO }"/>
					<input type="hidden" name="PRODUCT_NO" value="${moduleInfo.PRODUCT_NO }"/>
					<p>
						<label for="file_version">파일버전</label>
						<input type="text" id="file_version" name="FILE_VERSION" />
					</p>
					<p id="customer_check">
							<label for="">적용사이트</label>
							<button id="add_customer_bt" class="icon_button">적용사이트를 등록하세요.</button>
						</p>
					<p>
						<label>수정내용</label>
						<textarea id="update_memo" name="UPDATE_MEMO" style="width:600px ;height:120px "></textarea>
					</p>
					<p>
						<label>X86 릴리즈</label>
						<input type="file" id='x86_release_file' name="x86_release_file">
						<label>X64 릴리즈</label>
						<input type="file" id='x64_release_file' name="x64_release_file">
					</p>
					<p>
						<label>X86 디버그</label>
						<input type="file" id='x86_debug_file' name="x86_debug_file">
						<label>X64 디버그</label>
						<input type="file" id='x64_debug_file' name="x64_debug_file">
					</p>
				</form>
			</div>
			
			<!-- 수정이력 수정 팝업을 생성합니다. -->
			<div id="dialog-modify-module-history" class="module-dialog"  title="수정이력 수정">
				<form method="post" action="update_module_02.isnet" enctype="multipart/form-data" >
					<input type="hidden" name="MODULE_HISTORY_NO" value="${moduleInfo.MODULE_HISTORY_NO }"/>
 					<p>
						<label for="modify_file_version">파일버전</label>
						<input type="text" id="modify_file_version" name="FILE_VERSION" />
					</p>
					<p>
						<label for="modify_update_memo">수정내용</label>
						<textarea id="modify_update_memo" name="UPDATE_MEMO" style="width:600px ;height:120px "></textarea>
					</p>
					<p>
						<label>X86 릴리즈</label>
						<input type="file" id='modify_x86_release_file' name="x86_release_file">
						<label>X64 릴리즈</label>
						<input type="file" id='modify_x64_release_file' name="x64_release_file">
					</p>
					<p>
						<label>X86 디버그</label>
						<input type="file" id='modify_x86_debug_file' name="x86_debug_file">
						<label>X64 디버그</label>
						<input type="file" id='modify_x64_debug_file' name="x64_debug_file">
					</p>
				</form>
			</div>
			
			<!-- 모듈 수정이력 상세보기 팝업 -->
			<div id="dialog-view-module-history" class="module-dialog"  title="수정이력 상세정보 보기 ">
				<form >
					<p>
						<label for="view_file_version">파일버전</label>
						<input type="text" id="view_file_version" name="FILE_VERSION" />
					</p>
					<p>
						<label for="view_customer_name">적용 사이트</label>
						<span style="display: block; float: left; margin-top: 5px; margin-right: 10px;" id="view_customer_name"></span>
					</p>
					<p>
						<label for="view_update_memo">수정내용</label>
						<textarea id="view_update_memo" style="width:600px ;height:120px "></textarea>
					</p>
					<p>
						<label>X86 릴리즈</label>
						<input type="text" id='view_x86_release_file' >
						<label>X64 릴리즈</label>
						<input type="text" id='view_x64_release_file' >
					</p>
					<p>
						<label>X86 디버그</label>
						<input type="text" id='view_x86_debug_file' >
						<label>X64 디버그</label>
						<input type="text" id='view_x64_debug_file'>
					</p>
				</form>
			</div>
			
			<!-- 모듈 기본정보 수정 팝업을 생성합니다. -->
			<div id="dialog-modify-module"  class="module-dialog"  title="모듈 정보 수정">
				<form id="module_form">
					<input type="hidden" name="MODULE_NO" value="${moduleInfo.MODULE_NO }"/>
					<p>
						<label for="modify_product_no">제품명</label>
						<select id="modify_product_no" name="PRODUCT_NO">
							<option value="">제품을 선택하세요</option>
							<c:forEach var="product" items="${productList }">
								<c:choose>
									<c:when test="${product.PRODUCT_NO eq moduleInfo.PRODUCT_NO }">
										<option value="${product.PRODUCT_NO }" selected="selected">${product.PRODUCT_NAME }</option>
									</c:when>
									<c:otherwise>
										<option value="${product.PRODUCT_NO }">${product.PRODUCT_NAME }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					    </select>
					</p>
					<p>
						<label for="modify_module_name">모듈명</label>
						<input type="text" id="modify_module_name" name="MODULE_NAME" value='${moduleInfo.MODULE_NAME }'/>
					</p>
					<p>
						<label for="modify_location_no">설치경로</label>
						<select id="modify_location_no" name="LOCATION_NO">
							<option value="">설치경로를 선택하세요</option>
							<c:forEach var="filePath" items="${filePathList }">
								<c:choose>
									<c:when test="${filePath.LOCATION_NO  eq moduleInfo.LOCATION_NO}">
										<option value="${filePath.LOCATION_NO }" selected="selected">${filePath.FOLDER_PATH }</option>
									</c:when>
									<c:otherwise>
										<option value="${filePath.LOCATION_NO }">${filePath.FOLDER_PATH }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					    </select>
					</p>
					<p>
						<label for="modify_module_desc">설명</label>
						<textarea id="modify_module_desc" name="MODULE_DESC" style="width: 855px;" rows="5">${moduleInfo.MODULE_DESC }</textarea>
					</p>				
				</form>
			</div>
			
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->

<script src="/common/jquery-fileuploader/jquery.form.js" type="text/javascript"></script>		
<script type="text/javascript">
	
$(document).ready(function(){	
	
	// 모듈 기본정보 수정 팝업 실행
	$("#modify_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			$("#dialog-modify-module").dialog("open");
		});
	
	// 모듈 수정이력 상세보기 및 수정하기 버튼 클릭
	$("#modify_history_bt, #view_history_bt")
	.button()
	.click(function(event){
		event.preventDefault();
		
		var rowid = $("#progress_list").jqGrid("getGridParam", "selrow");
		if(rowid){
	   		var rowData = $("#progress_list").jqGrid("getRowData", rowid);
			if(event.target.textContent == "보기"){
				$("#dialog-view-module-history").dialog("open");
				$("#view_file_version").val(rowData["FILE_VERSION"]);
		   		$("#view_customer_name").text(rowData["CUSTOMER_NAME"]);
				$("#view_update_memo").val(rowData["UPDATE_MEMO"]);
		   		
		   		if(rowData["X86_RELEASE"] != ""){
		   			$("#view_x86_release_file").val(rowData["X86_RELEASE"]);
		   		}
		   		if(rowData["X86_DEBUG"] != ""){
		   			$("#view_x86_debug_file").val(rowData["X86_DEBUG"]);
		   		}
		   		if(rowData["X64_RELEASE"] != ""){
		   			$("#view_x64_release_file").val(rowData["X64_RELEASE"]);
		   		}
		   		if(rowData["X64_DEBUG"] != ""){
		   			$("#view_x64_debug_file").val(rowData["X64_DEBUG"]);
		   		}
		   		
			}else{
				$("#dialog-modify-module-history").dialog("open");
				$("#modify_file_version").val(rowData["FILE_VERSION"]);
		   		$("#modify_update_memo").val(rowData["UPDATE_MEMO"]);
			}
		}else{
			simple_popup("안내","그리드에서 수정할 행을 선택하세요.");
		}
	});
	
	// 적용사이트 추가하기 버튼 - 신규등록인 경우에는 해당 모듈의 마지막 수정이력의 적용사이트를 불러와서 표시한다.
	$("#add_customer_bt")
		.button({
			icons: {
				primary: "ui-icon-circle-plus"
			},
			text: false
		})
		.click(function(event){
			event.preventDefault();
			$("#dialog-modify-applied-customer").dialog("open");
		});
	
	// ,적용사이트 등록용 팝업창
	$("#dialog-modify-applied-customer").dialog({
		autoOpen: false,
		height: 400,
		width: 665,
		modal: true,
		open: function(){
			var module_history_no  = "";
			if($("#hidden_mhn").val() != ""){
				// 기존 적용사이트를 수정하는 경우
				module_history_no = $("#hidden_mhn").val();
			
			}else{
				// 수정이력을 새로 등록하는 경우 - 해당 모듈의 마지막 수정이력번호를 module_history_no로 설정한다.
				module_history_no = '${moduleInfo.MODULE_HISTORY_NO}';
			}			
			$.ajax({
				url: "select_customer_apply.isnet",
				type: "POST",
				data: {"mhn": module_history_no},
				dataType: "json",
				success:function(rtnObj){
					$("#div_customer").html("");
					if(rtnObj!=null && rtnObj.size > 0) {
						$.each(rtnObj.titleCustomerList, function(index, obj){
							 var customer_no = obj.CUSTOMER_NO;
							 $("#check_box"+customer_no).attr("checked","checked")
							 							.next().css({"color":"red", "font-weight":"bold"});;
							 $("#div_customer").append(" ["+ obj.CUSTOMER_NAME + "]");
						});
					}
				}
			});
			
		},
		buttons: {
			"Save": function(){	
				
				var isSelectedSite = false;
				$("#dialog-modify-applied-customer :checkbox").each(function() {
	   				var result = $(this).attr("checked");
	   				if (result == "checked") {
	   					isSelectedSite = true;
	   				}
	   			});
				if(!isSelectedSite){
					simple_popup("안내","적용 사이트를 선택하세요.");
					return false;
				}
				
				if($("#hidden_mhn").val() != ""){
					// 적용사이트를 수정하는 경우
					$.ajax({
						url: "add_customer_apply.isnet",
						type: "POST",
						data: $("#dialog-modify-applied-customer form").serialize(),
						dataType: "json",
						success: function(result, status){
							if(result && result.isSuccess){
								simple_popup("안내","적용사이트  등록이 완료되었습니다");
							}else{
								simple_popup("안내",result.error_msg);
							}
							$("#dialog-modify-applied-customer").dialog("close");
							$("#progress_list").jqGrid().trigger("reloadGrid");
						},
						error: function(){
							simple_popup("안내", "적용사이트 등록 중 오류가 발생하였습니다.");
							$("#dialog-modify-applied-customer").dialog("close");
						}
					});
				}else{
					// 수정이력을 신규로 등록하는 경우
					
					// 기존에 등록되어있는 아이콘 삭제$("#module_form :input[name='CUSTOMER_NO'] ")
					$("#customer_check .icon").remove();
					$("#dialog-add-module-history :input[name='CUSTOMER_NO'] ").remove();
					
					$("#dialog-modify-applied-customer :checkbox").each(function(index, checkbox){
						if(checkbox.checked){
							var customer_name = $(this).next().text();
							$('<span class="icon" >'+customer_name+'</span><input type="hidden" name="CUSTOMER_NO" value="'+$(this).val()+'"/>').insertBefore("#add_customer_bt");
						}
					});
					$("#dialog-modify-applied-customer").dialog("close");
				}				
			},
			Cancel: function(){
				$("#hidden_mhn").val("");
				$("#dialog-modify-applied-customer :checkbox")
					.removeAttr("checked")
					.each(function(index, checkbox){
						$(checkbox).next().css({"font-weight":"normal", "color":"black"});
					});
				$("#div_customer").append("");
				$(this).dialog("close");
			}
		},
		close:function(){
			$("#hidden_mhn").val("");
			$("#dialog-modify-applied-customer :checkbox")
				.removeAttr("checked")
				.each(function(index, checkbox){
					$(checkbox).next().css({"font-weight":"normal", "color":"black"});
				});
			$("#div_customer").append("");
		}
	});

	// 모듈이력정보 수정 팝업
	$("#dialog-modify-module-history").dialog({
		autoOpen: false,
		height: 380,
		width: 800,
		resizable: false,
		modal: true,
		crate:function(){
			
		},
		buttons:[
			{
				text:'수정', 
				click:function(){
					// 파일버전을 체크합니다.
					if($("#modify_file_version").val() == ""){
						simple_popup("안내", "파일버전을 입력하세요.");
						$("#modify_file_version").focus();
						return false;
					} 
					// 수정내용을 체크합니다.
					if($("#modify_update_memo").val() == ""){
						simple_popup("안내", "수정내용을 입력하세요.");
						$("#modify_update_memo").focus();
						return false;
					}
					
					// 서버로 전송합니다.
					$("#dialog-modify-module-history form").ajaxSubmit(function(data, state){
						$("#dialog-modify-module-history").dialog("close");
					    data = data.replace(/[<][^>]*[>]/gi, '');
					    var jData = JSON.parse(data);
						if(jData && jData.isSuccess){
							$("#progress_list").jqGrid().trigger("reloadGrid");
						}else{
							simple_popup("안내", "모듈 수정이력 등록 중 오류가 발생하였습니다.");
						}
					});
				}
			},
			{
				text:"닫기", 
				click:function(){
					$(this).dialog("close");
				}
			}
		]
	});
	
	// 모듈이력정보 상세 팝업
	$("#dialog-view-module-history").dialog({
		autoOpen: false,
		height: 410,
		width: 800,
		resizable: false,
		modal: true,
		buttons:[
			{
				text:"닫기", 
				click:function(){
					$(this).dialog("close");
				}
			}
		]
	});
	
	// 모듈기본정보 수정 팝업
	$("#dialog-modify-module").dialog({
		autoOpen: false,
		height: 340,
		width: 1035,
		resizable: false,
		modal: true,
		crate:function(){
			
		},
		buttons:{
			'Save':function(){
				// 제품명을 체크합니다.
				if($("#modify_product_no").val() == ""){
					simple_popup("안내", "제품명을 입력하세요");
					$("#modify_product_no").focus();
					return false;
				}
				// 모듈명을 체크합니다.
				if($("#modify_module_name").val() == ""){
					simple_popup("모듈명을 입력하세요.");
					$("#modify_module_name").focus();
					return false;
				}
				// 설치경로를 체크합니다.
				if($("#modify_location_no").val() == ""){
					simple_popup("안내", "설치경로를 선택하세요");
					$("#modify_location_no").focus();
					return false;
				}
				
				// 수정 정보를 서버로 전송합니다.
				$.ajax({
					type:"POST",
					url:"update_module_01.isnet",
					dataType:"json",
					data:$("#module_form").serialize(),
					success:function(data, status){
						$("#dialog-modify-module").dialog("close");
						if(data && data.isSuccess){
							simple_popup("안내", "모듈기본정보가 수정되었습니다.");
							// 변경정보 화면 표시
							$("#product_name").val($("#modify_product_no :selected").text());
							$("#module_name").text($("#modify_module_name").val());
							$("#module_desc").val($("#modify_module_desc").val());
							$("#folder_path").val($("#modify_location_no :selected").text());
						}else{
							simple_popup("안내", "모듈기본정보 수정 중 오류가 발생하였습니다.");
						}
					},
					error:function(){
						$("#dialog-modify-module").dialog("close");
						simple_popup("안내", "모듈기본정보 수정 중 오류가 발생하였습니다.");
					}
				});
			},
			Cancel: function(){
				$(this).dialog("close");
			}
		}
	});

	
	// 수정이력 등록 팝업
	$("#dialog-add-module-history").dialog({
		autoOpen: false,
		height: 400,
		width: 800,
		resizable: false,
		modal: true,
		create: function(){
			$("#file_version").val("");
			$("#customer_check .icon").remove();
			$("#dialog-add-module-history :input[name='CUSTOMER_NO'] ").remove();
			$("#update_memo").val("");
			$("#x86_release_file").val(""); 
			$("#x64_release_file").val("");
			$("#x86_debug_file").val("");
			$("#x64_debug_file").val("");
		},
		buttons:{
			"Save": function(){
				
				// 파일버전 입력여부 체크
				if($("#file_version").val() == ""){
					simple_popup("안내", "파일버전을 입력하세요.");
					$("$file_version").focus();
					return false;
				}
				
				// 수정내용 입력여부 체크
				if($("#update_memo").val() == ""){
					simple_popup("안내", "수정내용을 입력하세요.");
					$("$update_memo").focus();
					return false;
				}
				
				// 파일 첨부여부 체크
				if($("#x86_release_file").val() == "" && $("#x64_release_file").val() == "" &&
				   $("#x86_debug_file").val() == "" &&  $("#x64_debug_file").val() == ""){
					  simple_popup("안내", "수정된 파일을 첨부하세요.");
					  $("#x86_release_file").focus();
					  return false;
				}
				
				// 서버로 전송합니다.
				$("#dialog-add-module-history form").ajaxSubmit(function(data, state){
					$("#dialog-add-module-history").dialog("close");
				    data = data.replace(/[<][^>]*[>]/gi, '');
				    var jData = JSON.parse(data);
					if(jData && jData.isSuccess){
						$("#progress_list").jqGrid().trigger("reloadGrid");
					}else{
						simple_popup("안내", "모듈 수정이력 등록 중 오류가 발생하였습니다.");
					}
				});
				
			},
			Cancel: function(){
				$(this).dialog("close");
			}
		}
	});
 
	//수정이력 표시
	$("#progress_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:300,
		datatype: "json",
		url:"list_02.isnet?mn=${param.mn}",
	   	colNames:['MODULE_HISTORY_NO', 'MODULE_NO', 'X86_RELEASE','X86_DEBUG','X64_RELEASE','X64_DEBUG', '적용고객사', '수정일', '버전', '담당자', '수정내용','적용사이트','X86_릴리즈', 'X86_디버그','X64_릴리즈', 'X64_디버그'],						
	   	colModel:[	
			{name:'MODULE_HISTORY_NO', index:'MODULE_HISTORY_NO', hidden:true},	  
			{name:'MODULE_NO', index:'MODULE_NO', hidden:true},	 
	  		{name:'X86_RELEASE',index:'X86_RELEASE',hidden:true},
	   		{name:'X86_DEBUG',index:'X86_DEBUG',hidden:true},
	   		{name:'X64_RELEASE',index:'X64_RELEASE', hidden:true},
	   		{name:'X64_DEBUG',index:'X64_DEBUG', hidden:true},
	   		{name:'CUSTOMER_NAME',index:'CUSTOMER_NAME', hidden:true},
	   		{name:'UPDATE_DATE',index:'UPDATE_DATE', width:80, sortable:false,align:"center"},
	   		{name:'FILE_VERSION',index:'FILE_VERSION', width:100, sortable:false,align:"center"},
	   		{name:'EMPLOYEE_NAME',index:'EMPLOYEE_NAME', width:60, sortable:false,align:"center"},
	   		{name:'UPDATE_MEMO',index:'UPDATE_MEMO', width:320, sortable:false,align:"left"},
	   		{name:'CUSTOMERS',index:'CUSTOMERS', width:80, sortable:false, align:"center", formatter: 'link'},
	   		{name:'X86_RELEASE_LINK',index:'X86_RELEASE_LINK', width:80, sortable:false, align:"center", formatter: 'link'},
	   		{name:'X86_DEBUG_LINK',index:'X86_DEBUG_LINK', width:80, sortable:false, align:"center", formatter: 'link'},
	   		{name:'X64_RELEASE_LINK',index:'X64_RELEASE_LINK', width:80, sortable:false, align:"center", formatter: 'link'},
	   		{name:'X64_DEBUG_LINK',index:'X64_DEBUG_LINK', width:80, sortable:false, align:"center", formatter: 'link'}
	 
	   	],	
	    pager:"pager",
	   	rowList:[],
	   	pgbuttons:false,
	   	pginput:false,
	   	rowNum:50,
	   	loadError:function(xhr, status, error){
	    	alert("최근 모듈수정이력을 조회 중 오류가 발생하였습니다.");
	    }
	})
	.navGrid('#pager',{edit:false,add:false,del:false,search:false, refresh:false})
	.navButtonAdd('#pager',{
		caption:"등록", 
		buttonicon:"ui-icon-plus", 
		onClickButton: function(){ 
			$("#dialog-add-module-history").dialog("open");
		}, 
	 	position:"last",
	 	id: "process_add_bt",
	 	title: "수정이력을 등록합니다."
	});
	
	// 목록으로 이동하기
	$("#list_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			
			var page = '<c:out value="${param.page}" default="1"/>';
			var rows = '<c:out value="${param.rows}" default="10"/>';
			var sidx = '<c:out value="${param.sidx}" />';
			var sord = '<c:out value="${param.sord}" />';
			
			location.href = "module_01.isnet?page=" + page + "&rows=" + rows  + "&sidx=" + sidx + "&sord=" + sord;	
	});
	
});

function getCustomerSite(module_history_no) {
	$("#hidden_mhn").val(module_history_no);
	$("#dialog-modify-applied-customer").dialog("open");
	
}
</script>		
<%@ include file="../common/bottom.jsp" %>