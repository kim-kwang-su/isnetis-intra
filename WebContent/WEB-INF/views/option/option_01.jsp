<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="버전관리"/>
<c:set var="two_depth" value="옵션다운로드"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div style="text-align:right" id="breadcrumb"><a href="#">버전관리</a> / <a href="#">옵션다운로드</a> / <strong>옵션다운로드</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<div id="pager">
				<!-- 검색 조건 -->
				<div id="searchbox" style="padding:5px 30px;margin:10px 0 0 0;border:1px solid #cecece;">
					<strong>고객사 : </strong>
					<select id="customer_no" name="customer_no">
						<option value="">선택하여 주세요.</option>
						<c:forEach items="${customerList }" var="customer" varStatus="i">
							<c:choose>
								<c:when test="${param.customer_no eq customer.CUSTOMER_NO}">
									<option value="${customer.CUSTOMER_NO}" selected="selected">${customer.CUSTOMER_NAME}</option>
								</c:when>
								<c:otherwise>
									<option value="${customer.CUSTOMER_NO}">${customer.CUSTOMER_NAME}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<strong>제품 : </strong>
					<select id="product_no" name="product_no">
						<option value="">선택하여 주세요.</option>
						<c:forEach items="${productList }" var="product" varStatus="i">
							<c:choose>
								<c:when test="${param.product_no eq product.PRODUCT_NO}">
									<option value="${product.PRODUCT_NO}" selected="selected">${product.PRODUCT_NAME}</option>
								</c:when>
								<c:otherwise>
									<option value="${product.PRODUCT_NO}">${product.PRODUCT_NAME}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
					&nbsp;
					<button style="padding:0px 5px" class="icon" id="search_btn">조 회</button>					
					<button style="padding:0px 5px" class="icon" id="update_btn">수 정</button>					
					<button style="padding:0px 5px" class="icon" id="file_btn">파일 다운로드</button>
					<div id="spin" style="display:none;"></div>
				</div>
				<!-- 검색 조건 -->

				<!-- 옵션 별  탭 -->	
				<c:choose>
					<c:when test="${optionFileListSize eq  0}">
						<script>
							simple_popup("안내", "옵션 정보가 존재하지 않습니다.");
						</script>
					</c:when>
					<c:otherwise>
					<!-- 탭 영역 -->
					<div id="tabs" style="margin-top:5px" class="ui-tabs ui-widget ui-widget-content ui-corner-all">  
						<!-- 탭 리스트  시작-->
						<ul> 
						<c:forEach items="${optionFileList }" var="optionFile" varStatus="i">
							<li><a href="#tabs-${optionFile.OPTION_FILE_NO}">${optionFile.OPTION_FILE_NAME}</a></li>    
						</c:forEach>
						</ul>
						<!-- 탭 리스트  끝-->
						
						<c:choose>
							<c:when test="${optionListSize eq  0}">
								<script>
									simple_popup("안내", "옵션 정보가 존재하지 않습니다.");
								</script>
							</c:when>
							<c:otherwise>
							    <c:set var="optionFileNo">0</c:set>
								<c:set var="optionAppNo">0</c:set>
									
								<c:forEach items="${optionList }" var="option" varStatus="k">
										
									<c:if test="${k.first}">
									<!--  첫번째 값으로 파 일 세팅 ${optionDetail.OPTION_FILE_NO} : ${optionDetail.OPTION_APP_NO} -->
										<c:set var="optionFileNo" value="${option.OPTION_FILE_NO}" />
										<c:set var="optionAppNo" value="${option.OPTION_APP_NO}" />
											
									<!-- 첫번째 값으로 파 일 세팅 : 탭 영역  시작 [  ${optionDetail.OPTION_APP_NAME}]  -->
										<div id="tabs-${optionFileNo}">
												<div id="option_form">
														<form id="updateForm" name="updateForm">
														
															<fieldset class="first">
																<legend>${option.OPTION_APP_NAME}</legend>
										</c:if>
										
										<c:choose>
											<c:when test="${optionFileNo eq option.OPTION_FILE_NO}">
												<c:if test="${optionAppNo ne option.OPTION_APP_NO}">
													
													<!-- 옵션파일 번호는 동일하나 옵션앱이 동일하지 않다면  [ ${optionDetail.OPTION_APP_NAME} ]-->
													<!-- ${optionAppNo} :  ${optionDetail.OPTION_APP_NO} -->
													
													<c:set var="optionAppNo" value="${option.OPTION_APP_NO}" />
													</fieldset>
													<fieldset> 
													 <legend>${option.OPTION_APP_NAME}</legend>
												</c:if>
													
													<p>											
														<label for="">${option.OPTION_KEY_NAME}</label>
														
														<c:choose>
															<c:when test="${option.OPTION_VALUE eq null }">
																<input id="${option.OPTION_NO}" name="${option.OPTION_NO}" type="text" value="${option.OPTION_DEFAULT_VALUE}"/>
																<input id="old_${option.OPTION_NO}" name="old_${option.OPTION_NO}" type="hidden" value="${option.OPTION_DEFAULT_VALUE}"/>
															</c:when>
															<c:otherwise>
																<input id="${option.OPTION_NO}" name="${option.OPTION_NO}" type="text" value="${option.OPTION_VALUE}"/>
																<input id="old_${option.OPTION_NO}" name="old_${option.OPTION_NO}" type="hidden" value="${option.OPTION_DEFAULT_VALUE}"/>
															</c:otherwise>
														</c:choose>
														<span>${option.OPTION_COMMENT}</span>
													    <c:if test="${option.COMMENT ne null}">
													    	<br/><span>${option.COMMENT}</span>
													    </c:if>
													</p>
										   </c:when>
										   	<c:otherwise>
										   	
										   		<c:if test="${optionAppNo ne option.OPTION_APP_NO}">
													
													<!-- 옵션파일 번호는 동일하나 옵션앱이 동일하지 않다면  [ ${optionDetail.OPTION_APP_NAME} ]-->
													<!-- ${optionAppNo} :  ${optionDetail.OPTION_APP_NO} -->
													
													<c:set var="optionAppNo" value="${option.OPTION_APP_NO}" />
												</c:if>
																						   	
										   			<c:set var="optionFileNo" value="${option.OPTION_FILE_NO}" />
										   			</fieldset>
										   			</form>
										   			</div>
										   			</div>
										   			<!-- 탭 영역  완료1-->
										   			
										   			<!-- 탭 영역  시작 : tabs-${optionFileNo} : 1 -->
										   			<div id="tabs-${optionFileNo}">
													<div id="option_form">
														<form>
															<fieldset>
																<legend>${option.OPTION_APP_NAME}</legend>
																	<p>
																    <label for="">${option.OPTION_KEY_NAME}</label>
																	<c:choose>
																		<c:when test="${option.OPTION_VALUE eq null }">
																		<!-- 어디 값때문인가 1 -->
																			<input id="${option.OPTION_NO}" name="${option.OPTION_NO}" type="text" value="${option.OPTION_DEFAULT_VALUE}"/>
																			<input id="old_${option.OPTION_NO}" name="old_${option.OPTION_NO}" type="hidden" value="${option.OPTION_DEFAULT_VALUE}"/>
																		</c:when>
																		<c:otherwise>
																		<!-- 어디 값때문인가 2 -->
																			<input id="${option.OPTION_NO}" name="${option.OPTION_NO}" type="text" value="${option.OPTION_VALUE}"/>
																			<input id="old_${option.OPTION_NO}" name="old_${option.OPTION_NO}" type="hidden" value="${option.OPTION_VALUE}"/>
																		</c:otherwise>
																	</c:choose>
																	<!-- 어디 값때문인가 3 -->
																    <span>${option.OPTION_COMMENT}</span>
													    				<c:if test="${option.COMMENT ne null}">
													    					<br/><span>${option.COMMENT}</span>
													    				</c:if>
																	</p>

										   </c:otherwise>
									</c:choose>
									
									<c:if test="${k.last}">
																								
											</fieldset>
											</form>
											</div>
											<!-- 탭 영역  완료 2-->
											</div>
										</c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						</div>
					</c:otherwise>
				</c:choose>	
				<!-- 전체 choose -->
			</div>
			<!-- 컨텐츠 표시부 끝 -->
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->

<script type="text/javascript">
	var changeFlag = false;
	$(document).ready(function(){
		
		$("#customer option[value=${customer_no}]").attr("selected","true");
		$("#product option[value=${product_no}]").attr("selected","true");
		
		//탭부분
		$( "#tabs" ).tabs();
		
		//조회 다운로드
		$("#search_btn").click(function(event) {
			event.preventDefault();
			var customer_no = $("#customer_no").val();
			if(customer_no == ""){
				simple_popup("안내", "고객사를 선택하세요");
				$("#customer_no").focus();
				return false;
			}
			var product_no = $("#product_no").val();
			if(product_no == ""){
				simple_popup("안내", "제품을 선택하세요");
				$("#product_no").focus();
				return false;
			}
	    	location.href = "option_download_01.isnet?customer_no=" + customer_no + "&product_no=" + product_no;
		});
		
		//입력폼의 수정이 발생되면 
		$("input[type=text]").change(function(event){
			
			var inputs = document.getElementsByTagName('input'); 

			for(var i = 0; i < inputs.length; i+=2) 
			{
				if($(inputs[i]).val() != $(inputs[i+1]).val()) {
					changeFlag = true;
					$("#file_btn").attr("disabled","disabled");
					break;
				}else {
					changeFlag = false;
					$("#file_btn").attr("disabled",false);
				}
			}
		});
		
		//파일 다운로드
		$("#file_btn").click(function(event) {
			event.preventDefault();
			var customer_no = $("#customer_no").val();
			if(customer_no == ""){
				simple_popup("안내", "고객사를 선택하세요");
				$("#customer_no").focus();
				return false;
			}
			var product_no = $("#product_no").val();
			if(product_no == ""){
				simple_popup("안내", "제품을 선택하세요");
				$("#product_no").focus();
				return false;
			}
			
			if(changeFlag) {
				alert("옵션의 값이 변경되었습니다. 수정버튼을 눌러서 서버에 저장한 후 다운로드하시기 바랍니다.");
				return;
			}else {
				location.href = "option_download_02.isnet?customer_no=" + customer_no + "&product_no=" + product_no;

			}				
		});
		
		// 수정버튼 클릭시
		$("#update_btn").click(function(event){			
			if(confirm("수정 하시겠습니까?")) {			
				
				var customer_no = $("#customer_no").val();
				var product_no = $("#product_no").val();
				var param = JSON.stringify($("input[type=text]").serializeArray());
				//alert(param);
				$.post("update_option_01.isnet", 
						{"param" : param,
					     "customer" : customer_no,
					     "product" : product_no
						}, 
						function(result){
								if(result.result == 1) {
									alert("수정이 완료 되었습니다.");
									changeFlag = false;
									$("#file_btn").attr("disabled",false);
								}else {
									alert("수정중 오류가  되었습니다.");
									changeFlag = true;
									$("#file_btn").attr("disabled",true);
								}
				}, "json");
			}
		});
		
	});
</script>		
<%@ include file="../common/bottom.jsp" %>