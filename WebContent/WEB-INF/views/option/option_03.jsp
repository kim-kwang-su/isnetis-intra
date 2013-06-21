<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="버전관리"/>
<c:set var="two_depth" value="옵션비교"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="#">버젼관리</a> / <a href="option_03.isnet">옵션비교</a> / <strong>옵션정보 비교</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>모듈 상세 정보</h3> -->
			<div id="detail_form">
			<form id="search_form">
				<fieldset>
					<legend>옵션정보 비교</legend>
					<p>
						<label for="product">제품명</label>
						<select id="product" name="product">
						<c:forEach items="${productList }" var="product" varStatus="pi">
						<option value="${product.PRODUCT_NO }"
						>${product.PRODUCT_NAME }</option>
						</c:forEach>
						</select>
					</p>
					<p>
						<label for="option_file">옵션 FILE명</label>
						<ul id="option_file" style="margin-left:160px; margin-top:-30px;" class="checkingBox">
						<c:forEach items="${optionFile }" var="files" varStatus="i">
						<li><input type="checkbox" id="fileCheck_${files.OPTION_FILE_NO }" name='fileChk' value="${files.OPTION_FILE_NO }"><div>${files.OPTION_FILE_NAME }</div></li>
						</c:forEach>
						</ul>
					</p>
					<p>
						<label for="customer_name">고객명</label>

						<select id="customer_no1" name="customer_no1">
							<option value="">업체를 선택해주세요.</option>
							<c:forEach items="${customerList }" var="customer" varStatus="i">
							<option value="${customer.CUSTOMER_NO }">${customer.CUSTOMER_NAME }</option>
							</c:forEach>
						</select>

						<select id="customer_no2" name="customer_no2">
							<option value="">업체를 선택해주세요.</option>
							<c:forEach items="${customerList }" var="customer" varStatus="i">
							<option value="${customer.CUSTOMER_NO }">${customer.CUSTOMER_NAME }</option>
							</c:forEach>
						</select>

						<select id="customer_no3" name="customer_no3">
							<option value="">업체를 선택해주세요.</option>
							<c:forEach items="${customerList }" var="customer" varStatus="i">
							<option value="${customer.CUSTOMER_NO }">${customer.CUSTOMER_NAME }</option>
							</c:forEach>
						</select>

						<select id="customer_no4" name="customer_no4">
							<option value="">업체를 선택해주세요.</option>
							<c:forEach items="${customerList }" var="customer" varStatus="i">
							<option value="${customer.CUSTOMER_NO }">${customer.CUSTOMER_NAME }</option>
							</c:forEach>
						</select>
						<!-- <input type="hidden" id="customer1" name="customer1" value="">
						<input type="text" id="customer_name1" value="">
						<input type="hidden" id="customer1" name="customer2" value="">
						<input type="text" id="customer_name2" value="">
						<input type="hidden" id="customer1" name="customer3" value="">
						<input type="text" id="customer_name3" value="">
						<input type="hidden" id="customer1" name="customer4" value="">
						<input type="text" id="customer_name4" value=""> -->
						
						
					</p>
					
					<!-- 목록으로 이동하기 버튼 -->
					<p>
					   <button style="float: right;padding:0px 5px" class="icon" id="compare_bt">비 교</button>
					</p>
				</fieldset>
			</form>
			</div>
			<form id="excelExpoertForm" action="option_excel.isnet" method="post">
				<input type="hidden" id="excelExpert" name="excelExpert" value="">
			</form>
			
			<div id="dialog-list-popup" title="" style="height:600px;">
				<div id="contents">
				<!-- 컨텐츠 표시부 시작 -->
				</div>
			</div>

			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
		
<script type="text/javascript">
$(document).ready(function(){
	
	//사이트 오픈
	$("#compare_bt").button().click(function(event){
		event.preventDefault();
		var htmls="";
		var firstFile ="";
		var product = $("#product option:selected").text();
		var file_count = $("input[name=fileChk]:checked").length;
		var customer_no1 = $("#customer_no1").val();
		var customer_no2 = $("#customer_no2").val();
		var customer_no3 = $("#customer_no3").val();
		var customer_no4 = $("#customer_no4").val();
		
		var customer_name1 = customer_no1 =="" ? "" : $("#customer_no1 :selected").text();
		var customer_name2 = customer_no2 =="" ? "" : $("#customer_no2 :selected").text();
		var customer_name3 = customer_no3 =="" ? "" : $("#customer_no3 :selected").text();
		var customer_name4 = customer_no4 =="" ? "" : $("#customer_no4 :selected").text();
		
		if(product =="") {
			alert("제품을 선택하여 주세요.");
			return;
		}

		if(file_count == 0) {
			alert("옵션 File 명을 선택하여 주세요.");
			return;
		}
		if(customer_no1 =="" && customer_no2 =="" && customer_no3 =="" && customer_no4 =="") {
			alert("고객을 선택하여 주세요.");
			return;
		}
		
		$.ajax({
			type:"POST"
			,url:"option_compare.isnet"
			,data:$("#search_form").serialize()
			,dataType:"json"
			,success:function(rtnObj){
				if(rtnObj!=null && rtnObj.size > 0) {
					
					$.each(rtnObj.compareList, function(index, obj){
						if(index ==0) {
							firstFile  = obj.OPTION_FILE_NAME;
							htmls += makeTableHeader(firstFile ,customer_name1, customer_name2, customer_name3, customer_name4 );
							
						}else {
							if(firstFile != obj.OPTION_FILE_NAME) {
								firstFile  = obj.OPTION_FILE_NAME;
								htmls += "</table><br/>";
								htmls += makeTableHeader(firstFile ,customer_name1, customer_name2, customer_name3, customer_name4 );
							}
						}
						
						htmls += "<tr>";
						htmls += "<td style='word-break:break-all;'>"+ obj.OPTION_APP_NAME +"</td>";
						htmls += "<td style='word-break:break-all;'>"+ obj.OPTION_KEY_NAME +"</td>";
						htmls += "<td style='word-break:break-all;'>"+ obj.OPTION_DEFAULT_VALUE +"</td>";
						
						if(customer_no1 != "")
						htmls += "<td style='word-break:break-all;'>"+ obj.OPTION_VALUE1  +"</td>";
						
						if(customer_no2 != "")
							htmls += "<td style='word-break:break-all;'>"+ obj.OPTION_VALUE2  +"</td>";
							
						if(customer_no3 != "")
							htmls += "<td style='word-break:break-all;'>"+ obj.OPTION_VALUE3  +"</td>";
						
						if(customer_no4 != "")
							htmls += "<td style='word-break:break-all;'>"+ obj.OPTION_VALUE4  +"</td>";
						
						htmls += "<td style='word-break:break-all;'>"+ obj.OPTION_COMMENT+"</td>";
						htmls += "</tr>";		
						
						
					});
					
					$("#dialog-list-popup").html(htmls);
					$("#dialog-list-popup").dialog("open");
					
				}else {
					simple_popup("안내", "비교할 파일이 존재하지 않습니다.");
					return;
				}
			}
		});
		
		// 지원형태용 팝업 열기
		
	});
	
	
	/* //고객사 자동완성
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
			$("#customer" + no).val(ui.item.id);
			
		}
	}); */
	
	
	//제품선택시 옵션파일 변경
	$("#product").change(function(event) {
		event.preventDefault();
		
		$('#option_file li').each(function() {
			if( $(this).index() != 0 )
				$(this).remove();
		});
		
		var htmls="";
		$.ajax({
			type:"POST"
			,url:"select_option_03_01.isnet"
			,data:{"product":$("#product").val()}
			,dataType:"json"
			,success:function(rtnObj){
				if(rtnObj!=null) {
					$.each(rtnObj.optionFile, function(index, obj){
						htmls +="<li><input type='checkbox' id='fileCheck_"+obj.OPTION_FILE_NO+"' name='fileChk' value='"+ obj.OPTION_FILE_NO +"'><div>"+obj.OPTION_FILE_NAME+"</div></li>";
					});
					$("#option_file").append (htmls);
				}
			}
		});
	});
		
	// 옵션리스트 비교 팝업 
	$("#dialog-list-popup").dialog({
		autoOpen: false,
		height: 600,
		width: 1000,
		modal: true,
		open: function(){
			$(this).dialog( "option", "title", $("#product option:selected").text() + " 옵션 비교"  );
			
		},
		buttons: {
			"엑셀 다운로드": function(event){
						$("#excelExpert").val($("#dialog-list-popup").html());
						$("#excelExpoertForm").submit();
			},
			"닫기": function(){
				$("#dialog-list-popup").dialog("close");

			}
			
		}
	});	


	//옵션파일 전체 체크
	//checkedAllOrNothing("fileCheckAll","fileChk");
	//옵션파일 전체체크 해제
	//checkedAllCheckbox("fileCheckAll","fileChk");

});

function makeTableHeader(fileName, customer1, customer2, customer3, customer4) {
	var makeHtml ="";
	
	makeHtml += "<strong>"+ fileName +"</strong>";
	makeHtml += "<br/>";
	makeHtml += "<table border='1' class='popTable' id='option_list'>";
	makeHtml += "<tr>";
	makeHtml += "<th style='width:100px;word-break:break-all;'>옵션 APP명</th>";
	makeHtml += "<th style='width:100px;word-break:break-all;'>옵션 KEY명</th>";
	makeHtml += "<th style='width:50px;word-break:break-all;'>기본값</th>";
	
	if(customer1 != "")
		makeHtml += "<th style='width:80px;word-break:break-all;'>" + customer1 + "</th>";
	if(customer2 != "")
		makeHtml += "<th style='width:80px;word-break:break-all;'>" + customer2 + "</th>";
	if(customer3 != "")
		makeHtml += "<th style='width:80px;word-break:break-all;'>" + customer3 + "</th>";
	if(customer4 != "")
		makeHtml += "<th style='width:80px;word-break:break-all;'>" + customer4 + "</th>";
					
	makeHtml += "<th style='width:300px;word-break:break-all;'>설명</th>";
	makeHtml += "</tr>";
	
	return makeHtml;
	


}



</script>		
<%@ include file="../common/bottom.jsp" %>