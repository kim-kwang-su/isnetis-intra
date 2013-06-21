<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="버전관리"/>
<c:set var="two_depth" value="옵션등록"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="#">버전관리</a> / <a href="option_02.isnet">옵션리스트</a> / <strong>옵션등록</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>모듈 상세 정보</h3> -->
			<div id="detail_form">
			<form>
				<fieldset>
					<legend>옵션 상세정보</legend>
					<p>
						<label for="product_name">제품명</label>
						<select style="width:300px;" id="product" name="product">
						<option value="">선택하여 주세요.</option>
						<c:choose>
							<c:when test="${productSize eq  0}">
							</c:when>
							<c:otherwise>
								<c:forEach items="${product }" var="product" varStatus="i">
									<option value="${product.PRODUCT_NO}">${product.PRODUCT_NAME}</option>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</select>
					<!-- <button style="margin-top:2px;" class="icon_button" id="product_btn"></button> -->
					</p>
					<p>
						<label for="option_file">옵션 FILE명</label>
						<select style="width:300px;" id="optionFile" name="optionFile">
						<option value="">선택하여 주세요.</option>
						</select>
						<button style="margin-top:2px;" class="icon_button" id="file_btn"></button>
					</p>
					
					<p>
						<label for="option_app">옵션 APP명</label>
						<select style="width:300px;" id="optionApp" name="optionApp">
						<option value="">선택하여 주세요.</option>
						</select>
						<button style="margin-top:2px;" class="icon_button" id="app_btn"></button>
					</p>
					
					<p>
						<label for="option_key">옵션 KEY명</label>
						<select style="width:300px;" id="option" name="option">
						<option value="">선택하여 주세요.</option>
						</select>
						<button style="margin-top:2px;" class="icon_button" id="option_btn"></button>
					</p>
					
					<p>
						<label for="folder_path">기본값</label>
						<input id="default" name="" type="text" disabled="disabled" value="${options.OPTION_DEFAULT_VALUE }"/>
					</p>
					<p>
						<label for="file_version">옵션 KEY명(한글)</label>
						<input id="comment_title" name="" type="text" disabled="disabled" value="${options.OPTION_COMMENT }" />
					</p>
					<p> <label>옵션 값</label>
						<input id="comment_value_0" name="" type="text" style="width:100px" value="" />
						<input id="comment_memo_0" name="" type="text"  style="margin-left:3px; width:567px;" value="" />
						<button style="margin-top:2px;" class="icon_button" id="comment_btn"></button>
					</p>
					<div id="comment_div">
					</div>
					
					<!-- 목록으로 이동하기 버튼 -->
					<p>
					   <button style="float: right;padding:0px 5px" class="icon" id="list_bt">목 록</button>
					</p>
				</fieldset>
			</form>
			</div>
			
			<!-- 제품등록 팝업창을 생성합니다. -->
			<div id="dialog-form" title="옵션 등록">
				<div id="basic-div">
				</div>
			</div>


			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	//제품 추가 버튼
	/*	$("#product_btn").button({
	      icons: {
	          primary: "ui-icon-circle-plus"
	        },
	        text: false}
								).click(function(event){
		event.preventDefault();
		
		var htmls = "<form>";
		htmls+="<input type='hidden' id='add_type' value='product'>";
		htmls+="<strong>제품명</strong>";
		htmls+="<input style='margin-left:21px;' type='text' id='product_name' name=''>";
		htmls+="<br>";
		htmls+="<strong style='margin-top:5px'>제품코드</strong>";
		htmls+="<input style='margin-top:5px; margin-left:5px;' type='text' id='product_code' name=''>";
		htmls+="</form>";
		
		$("#basic-div").html(htmls);

		$("#dialog-form").dialog("open");
	}); */
	
	//파일 추가 버튼
	$("#file_btn").button({
		      icons: {
		          primary: "ui-icon-circle-plus"
		        },
		        text: false}
		).click(function(event){
			event.preventDefault();
			if($("#product").val() =="") {
			alert("제품명을 선택하여 주세요.");
			return;
		}
		
		var product_name = $("#product option:selected").text();
		
		var htmls = "<form>";
		htmls+="<input type='hidden' id='add_type' value='file'>";
		htmls+="<strong style='padding-left:36px;'>제품명</strong>";
		htmls+="<input style='margin-left:5px;' type='text' disabled='disabled' id='product_name' value='"+ product_name +"'>";
		htmls+="<br>";
		htmls+="<strong style='margin-top:5px'>옵션 파일명</strong>";
		htmls+="<input style='margin-top:5px; margin-left:5px;' type='text' id='file_name' name=''>";
		htmls+="</form>";
		
		$("#basic-div").html(htmls);
		
		$("#dialog-form").dialog("open");
		
	 });
	
	
	//앱 추가 버튼
	$("#app_btn").button({
	      icons: {
	          primary: "ui-icon-circle-plus"
	        },
	        text: false}		
		).click(function(event){
			event.preventDefault();
			if($("#optionFile").val() == "") {
				alert("옵션 FILE명을 선택하여 주세요.");
			return;
		}
		
		var file_name = $("#optionFile option:selected").text();
		
		var htmls = "<form>";
		htmls+="<input type='hidden' id='add_type' value='app'>";
		htmls+="<strong>옵션 FILE명</strong>";
		htmls+="<input style='margin-left:5px;' type='text' disabled='disabled' id='file_name' value='"+ file_name +"'>";
		htmls+="<br>";
		htmls+="<strong style='margin-left:2px;'>옵션  APP명</strong>";
		htmls+="<input style='margin-top:5px; margin-left:5px;' type='text' id='app_name' name=''>";
		htmls+="</form>";
		
		$("#basic-div").html(htmls);
		
		$("#dialog-form").dialog("open");
		
		
	 });
	
	//옵션 추가 버튼
	$("#option_btn").button({
	      icons: {
	          primary: "ui-icon-circle-plus"
	        },
	      text: false})
	     .click(function(event){
			event.preventDefault();
		
			$("#dialog-form").dialog({width:700, height: 235});
		
			if($("#optionApp").val() =="") {
				alert("옵션 APP명을 선택하여 주세요.");
				return;			
			}
		
		var app_name = $("#optionApp option:selected").text();
		
		var htmls = "<form>";
		htmls+="<input type='hidden' id='add_type' value='option'>";
		htmls+="<strong style='margin-left:42px;'>옵션 APP명</strong>";
		htmls+="<input style='margin-left:5px;' type='text' disabled='disabled' id='app_name' value='"+ app_name +"'>";
		htmls+="<br>";
		htmls+="<strong style='margin-left:40px;'>옵션  KEY명</strong>";
		htmls+="<input style='margin-top:5px; margin-left:5px;' type='text' id='option_name' name=''>";
		htmls+="<br>";
		htmls+="<strong style='margin-left:76px;'>기본값</strong>";
		htmls+="<input style='margin-top:5px; margin-left:5px;' type='text' id='default_value' name=''>";
		htmls+="<br>";
		htmls+="<strong>옵션  KEY명(한글)</strong>";
		htmls+="<input style='margin-top:5px; margin-left:5px; width:500px;' type='text' id='option_comment' name=''>";
		htmls+="</form>";
		
		$("#basic-div").html(htmls);
		
		$("#dialog-form").dialog("open");
	 });	
	
	//옵션값 추가 버튼
	$("#comment_btn")
	.button({
	      icons: {
	          primary: "ui-icon-circle-plus"
	        },
	        text: false})
	.click(function(event){
		event.preventDefault();
		
		var option = $("#option").val();
		var comment_value = $("#comment_value_0").val();
		var comment_memo = $("#comment_memo_0").val();
		
		if(option == "") {
			alert("옵션 KEY명을 선택하여 주세요.");
			return;
		}
		
		if(comment_value == "") {
			alert("옵션값을 입력해 주세요.");
			return;
		}
		if(comment_memo == "") {
			alert("옵션 설명을 입력해 주세요.");
			return;
		}		
		
		$.ajax({
			type:"POST"
			,url:"add_comment.isnet"
			,data:{"option_no":option, "comment_value":comment_value ,"comment_memo" :comment_memo}
			,dataType:"json"
			,success:function(rtnObj){
				if(rtnObj!=null) {
					if(rtnObj.isSucess && rtnObj.result_no != null) {
						var htmls ="";
						htmls +="<p> <label></label>";
						htmls +="<input id='comment_value_"+ rtnObj.result_no +"' name='' type='text' disabled='disabled' style='width:100px' value='"+ comment_value +"' />";
						htmls +="<input id='comment_memo_"+ rtnObj.result_no +"' name='' type='text' disabled='disabled' style='margin-left:3px; width:567px;' value='"+ comment_memo +"' />";
						htmls +="<button style='margin-top:2px;' class='icon_button' id='del_btn_"+ rtnObj.result_no +"'></button>";
						htmls +="</p>";
						htmls +="<script>";
						htmls +="$().ready(function() {";
						htmls +="    $('#del_btn_"+ rtnObj.result_no +"').button({";
						htmls +="             icons: {";
						htmls +="             primary: 'ui-icon-circle-minus'";
						htmls +="             },";
						htmls +="              text: false}	).click(function(event){";
						htmls +="  	    event.preventDefault(); ";
						htmls +="		delComment('"+ rtnObj.result_no +"')";
					    htmls +="    });";
					    htmls +="}); ";
					    htmls +="<\/script>";
						
						$("#comment_div").append(htmls);
						
						$("#comment_value_0").val("");
						$("#comment_memo_0").val("");
						$("#comment_value_0").focus();
					
					}
				}else {
					alert("옵션값 저장 중 오류가 발생하였습니다.");
				}
			}
		});
	 });
	
	//팝업
	$("#dialog-form").dialog({
		autoOpen: false,
		height: 180,
		width: 400,
		resizable: false,
		modal: true,
		create: function(){
			$("#dialog-form input[type='text']:first").focus();
		},
		buttons: {
			"등록": function(event){
					event.preventDefault();
					
					var add_type= $("#add_type").val();
					
					//제품 등록
					//if(add_type == 'product') {
					//	var product_name = $("#product_name").val();
					//	var product_code = $("#product_code").val();
					//	$.ajax({
					//		type:"POST"
					//		,url:"add_product.isnet"
					//		,data:{"product_name":product_name, "product_code":product_code}
					//		,dataType:"json"
					//		,success:function(rtnObj){
					//		}
					//	});
					//	location.href ="option_04.isnet";
					//	}
					
					//파일등록
					if(add_type =='file') {
					
					var product_no = $("#product").val();
					var file_name = $("#file_name").val();
					console.log("product_no : " + product_no + "file_name : " + file_name);
					$.ajax({
						type:"POST"
						,url:"add_file.isnet"
						,data:{"product_no":product_no, "file_name":file_name}
						,dataType:"json"
						,success:function(rtnObj){
							if(rtnObj.isSucess) {
								$("#optionFile").append("<option value ='"+ rtnObj.result_no + "' selected>"+file_name+"</option>");								
						
							}
						}
					});
					//APP 등록
					}else if(add_type=='app') {
						
						var file_no = $("#optionFile").val();
						var app_name = $("#app_name").val();
						console.log("file_no : " + file_no + "app_name : " + app_name);
						$.ajax({
							type:"POST"
							,url:"add_app.isnet"
							,data:{"file_no":file_no, "app_name":app_name}
							,dataType:"json"
							,success:function(rtnObj){
								if(rtnObj.isSucess) {
									$("#optionApp").append("<option value ='"+ rtnObj.result_no + "' selected>"+app_name+"</option>");								
							
								}
							}
						});
					//옵션등록
					}else if(add_type=='option') {
						
						var app_no = $("#optionApp").val();
						var option_name = $("#option_name").val();
						var default_value = $("#default_value").val();
						var option_comment = $("#option_comment").val();
						
						
						console.log("app_no : " + app_no + "option_name : "+ option_name);
						console.log("default_value : " + default_value + "option_comment : " + option_comment);
						
						$.ajax({
							type:"POST"
							,url:"add_option.isnet"
							,data:{"app_no":app_no, "option_name":option_name, "default_value" : default_value, "option_comment" : option_comment }
							,dataType:"json"
							,success:function(rtnObj){
								if(rtnObj.isSucess) {
									$("#option").append("<option value ='"+ rtnObj.result_no + "' selected>"+option_name+"</option>");								
									$("#default").val(default_value);
									$("#comment_title").val(option_comment);
								}
							}
						});
					
					}
			},
			"취소": function(){
				$(this).dialog("close");
			}
		},
		close: function(){
			$(this).dialog("close");
		}
	});
	
	
	//목록으로 가기
	$("#list_bt").button().click(function(event){
		event.preventDefault();
		
		var page = '<c:out value="${param.page}" default="1"/>';
		var rows = '<c:out value="${param.rows}" default="20"/>';
		var sidx = '<c:out value="${param.sidx}" />';
		var sord = '<c:out value="${param.sord}" />';
		
		location.href = "option_02.isnet?page=" + page + "&rows=" + rows  + "&sidx=" + sidx + "&sord=" + sord;
		
	});
	
	//제품선택시 옵션파일 변경
	$("#product").change(function(event) {
		event.preventDefault();
		
		var product = $("#product").val();
		$.ajax({
			type:"POST"
			,url:"select_option_03_01.isnet"
			,data:{"product":product}
			,dataType:"json"
			,success:function(rtnObj){
				if(rtnObj!=null) {
					$("#optionFile").html("<option value=''>선택하여 주세요.</option>");
					
					$.each(rtnObj.optionFile, function(index, obj){
						$("#optionFile").append("<option value='"+ obj.OPTION_FILE_NO +"'>"+ obj.OPTION_FILE_NAME + "</option>");
					});
				}
			}
		});
	});
	
	// 옵션파일 선택시 APP 변경
	$("#optionFile").change(function(event) {
		event.preventDefault();
		var file = $("#optionFile").val();
		$.ajax({
			type:"POST"
			,url:"select_option_03_02.isnet"
			,data:{"optionFile":file}
			,dataType:"json"
			,success:function(rtnObj){
				if(rtnObj!=null) {
					$("#optionApp").html("<option value=''>선택하여 주세요.</option>");
					
					$.each(rtnObj.optionApp, function(index, obj){
						$("#optionApp").append("<option value='"+ obj.OPTION_APP_NO +"'>"+ obj.OPTION_APP_NAME + "</option>");
					});
				}
			}
		});
	});
	
	// 옵션APP 선택시 옵션 변경
	$("#optionApp").change(function(event) {
		event.preventDefault();
		var app = $("#optionApp").val();
		$.ajax({
			type:"POST"
			,url:"select_option_03_03.isnet"
			,data:{"optionApp":app}
			,dataType:"json"
			,success:function(rtnObj){
				if(rtnObj!=null) {
					$("#option").html("<option value=''>선택하여 주세요.</option>");
					
					$.each(rtnObj.option, function(index, obj){
						$("#option").append("<option value='"+ obj.OPTION_NO +"'>"+ obj.OPTION_KEY_NAME + "</option>");
					});
				}
			}
		});
	});
	
	// 옵션키 선택시 옵션 key명과 기본값  comment 변경
	$("#option").change(function(event) {
		event.preventDefault();
		var app = $("#optionApp").val();
		var option = $("#option").val();
		$.ajax({
			type:"POST"
			,url:"select_option_03_04.isnet"
			,data:{"optionApp":app, "option":option}
			,dataType:"json"
			,success:function(rtnObj){
				if(rtnObj!=null) {
					
					$.each(rtnObj.optionList, function(index, obj){
						$("#default").val(obj.OPTION_DEFAULT_VALUE);
						$("#comment_title").val(obj.OPTION_COMMENT);
					});
					
					var htmls ="";
					$.each(rtnObj.comment, function(index, obj){
						htmls +="<p> <label></label>";
						htmls +="<input id='comment_value_"+ obj.COMMENT_NO +"' name='' type='text' disabled='disabled' style='width:100px' value='"+ obj.COMMENT_VALUE +"' />";
						htmls +="<input id='comment_memo_"+ obj.COMMENT_NO +"' name='' type='text' disabled='disabled' style='margin-left:3px; width:567px;' value='"+ obj.COMMENT_MEMO +"' />";
						htmls +="<button style='margin-top:2px;' class='icon_button' id='del_btn_"+ obj.COMMENT_NO +"'></button>";
						htmls +="</p>";
						htmls +="<script>";
						htmls +="$().ready(function() {";
						htmls +="    $('#del_btn_"+ obj.COMMENT_NO +"').button({";
						htmls +="             icons: {";
						htmls +="             primary: 'ui-icon-circle-minus'";
						htmls +="             },";
						htmls +="              text: false}	).click(function(event){";
						htmls +="  	    event.preventDefault(); ";
						htmls +="		delComment('"+ obj.COMMENT_NO +"')";
					    htmls +="    });";
					    htmls +="}); ";
					    htmls +="<\/script>";
					});
					
					$("#comment_div").html(htmls);
					
				}
			}
		});
		
	});
	
});

//옵션값 삭제
function delComment(rtnNum) {
	var option_no = $("#option").val();
	var comment_no = rtnNum;
	 $.ajax({
		type:"POST"
		,url:"delete_option_04_01.isnet"
		,data:{"option_no":option_no, "comment_no": comment_no}
		,dataType:"json"
		,success:function(rtnObj){
			if(rtnObj!=null && rtnObj.isSucess) {
				$("#del_btn_"+rtnNum).parent().remove();
			}
		}
	}); 
	
	//$("#del_btn_"+rtnNum).parent().remove();
}
</script>		
<%@ include file="../common/bottom.jsp" %>