<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="버전관리"/>
<c:set var="two_depth" value="옵션리스트"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="#">버전관리</a> / <a href="option_02.isnet">옵션리스트</a> / <strong>옵션상세정보</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>모듈 상세 정보</h3> -->
			<div id="detail_form">
			<form>
				<fieldset>
					<legend>옵션 상세정보</legend>
					<p>
						<label for="product_name">제품명</label>
						<input type="text" readonly="readonly" value="${optionInfo.PRODUCT_NAME }"/>
						<%-- <select style="width:300px;" id="product" name="product">
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
					</select> --%>
					<!-- <button style="margin-top:2px;" class="icon_button" id="product_btn"></button> -->
					</p>
					<p>
						<label for="option_file">옵션 FILE명</label>
						<input type="text" readonly="readonly" value="${optionInfo.OPTION_FILE_NAME }">
						<!-- <select style="width:300px;" id="optionFile" name="optionFile">
						<option value="">선택하여 주세요.</option>
						</select>
						<button style="margin-top:2px;" class="icon_button" id="file_btn"></button> -->
					</p>
					
					<p>
						<label for="option_app">옵션 APP명</label>
						<input type="text" readonly="readonly" value="${optionInfo.OPTION_APP_NAME }">
						<!-- <select style="width:300px;" id="optionApp" name="optionApp">
						<option value="">선택하여 주세요.</option>
						</select>
						<button style="margin-top:2px;" class="icon_button" id="app_btn"></button> -->
					</p>
					
					<p>
						<label for="option_key">옵션 KEY명</label>
						<input type="text" readonly="readonly" value="${optionInfo.OPTION_KEY_NAME }">
						<!-- <select style="width:300px;" id="option" name="option">
						<option value="">선택하여 주세요.</option>
						</select>
						<button style="margin-top:2px;" class="icon_button" id="option_btn"></button> -->
					</p>
					
					<p>
						<label for="folder_path">기본값</label>
						<input type="text" readonly="readonly" value="${optionInfo.OPTION_DEFAULT_VALUE }"/>
					</p>
					<p>
						<label for="file_version">옵션 KEY명(한글)</label>
						<input style="width:400px;" type="text" readonly="readonly"  value="${optionInfo.OPTION_COMMENT }" />
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
	
	//목록으로 가기
	$("#list_bt").button().click(function(event){
		event.preventDefault();
		
		var page = '<c:out value="${param.page}" default="1"/>';
		var rows = '<c:out value="${param.rows}" default="20"/>';
		var sidx = '<c:out value="${param.sidx}" />';
		var sord = '<c:out value="${param.sord}" />';
		
		location.href = "option_02.isnet?page=" + page + "&rows=" + rows  + "&sidx=" + sidx + "&sord=" + sord;
		
	});	
});
</script>		
<%@ include file="../common/bottom.jsp" %>