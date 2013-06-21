<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="이슈관리"/>
<c:set var="two_depth" value="이슈리스트"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="issue_01.isnet">이슈관리</a> / <strong>이슈수정</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>모듈 상세 정보</h3> -->
			<div id="detail_form">
			<form id="issue_form">
				<fieldset>
					<legend>이슈 수정</legend>
					<p>
						<label for="accept_date">접수일자</label>
						<input id="accept_date" name="ACCEPT_DATE" type="text"   value="<c:out value='${issueInfo.ACCEPT_DATE }'/>" readonly="readonly"/>
						<input  name="ISSUE_NO" type="hidden"   value="<c:out value='${issueInfo.ISSUE_NO }'/>"/>
						<label for="customer_request_date">고객 처리요청일</label>
						<input id="customer_request_date" name="CUSTOMER_REQUEST_DATE" type="text"   value="<c:out value='${issueInfo.CUSTOMER_REQUEST_DATE }'/>"  readonly="readonly"/>
						<label for="expected_complete_date">처리완료 예정일</label>
						<input id="expected_complete_date" name="EXPECTED_COMPLETE_DATE" type="text"  value="<c:out value='${issueInfo.EXPECTED_COMPLETE_DATE }'/>"  readonly="readonly"/>
					</p>
					<p>
						<label for="expected_developed_date">개발완료 예정일</label>
						<input id="expected_developed_date" name="EXPECTED_DEVELOPED_DATE" type="text"  value="<c:out value='${issueInfo.EXPECTED_DEVELOPED_DATE }'/>"  readonly="readonly"/>
						<label for="developed_period">개발기간(일)</label>
						<input id="developed_period" name="DEVELOPED_PERIOD" type="text"  value="<c:out value='${issueInfo.DEVELOPED_PERIOD }'/>"  readonly="readonly"/>
					</p>
					<p class="mt10">
						<label for="customer_combobox">고객사명</label>
						<select id="customer_combobox" name="CUSTOMER_NO">
						<c:forEach var="customer" items="${customerList }">
							<option value="${customer.CUSTOMER_NO }">${customer.CUSTOMER_NAME }</option>
						</c:forEach>
					    </select>
						<label for="product_combobox">제품명</label>
						<select id="product_combobox" name="PRODUCT_NO">
						<c:forEach var="product" items="${productList }">
							<option value="${product.PRODUCT_NO }">${product.PRODUCT_NAME }</option>
						</c:forEach>
					    </select>
					</p>
					<p>
						<label for="issue_kind_combobox">고객요청 분류</label>
						<select id="issue_kind_combobox" name="KIND_NO">
						<c:forEach var="kind" items="${kindList }">
							<option value="${kind.KIND_NO }">${kind.KIND_NAME }</option>
						</c:forEach>
						</select>
						<label for="issue_priority_combobox">우선순위</label>
						<select id="issue_priority_combobox" name="ISSUE_PRIORITY">
							<option value="상">상</option>
							<option value="중">중</option>
							<option value="하">하</option>
					    </select>
					</p>
					<p id="developers">
						<label for="employee_no">접수자</label>
						<input type="text" value="${LOGIN_USER.EMPLOYEE_NAME }" disabled="disabled" />					    
						<label for="se_combox">담당 SE</label>
						<select id="se_combox" name="SE_NO" >
						<c:forEach var="employee" items="${employeeList }">
						<c:if test="${employee.DEPT_NO eq 1 }">
							<c:choose>
								<c:when test="${employee.EMPLOYEE_NO eq seList[0].EMPLOYEE_NO }">
									<option value="${employee.EMPLOYEE_NO }" selected="selected">${employee.EMPLOYEE_NAME }</option>								
								</c:when>
								<c:otherwise>
									<option value="${employee.EMPLOYEE_NO }">${employee.EMPLOYEE_NAME }</option>
								</c:otherwise>
							</c:choose>
						</c:if>
						</c:forEach>
					    </select>						
						
						<label for="developer_name">담당 개발자</label>
						<c:forEach var="developer" items="${developerList }">
							<span class="icon" id="developer_${developer.EMPLOYEE_NO }">${developer.EMPLOYEE_NAME }</span>
							<input type="hidden" name="DEVELOPER_NO" value="${developer.EMPLOYEE_NO }"/>
						</c:forEach>
					</p>
					<p class="mt10">
						<label for="issue_memo">접수내용</label>
						<textarea id="issue_memo" name="ISSUE_MEMO" rows="15" ><c:out value="${issueInfo.ISSUE_MEMO }"/></textarea>
					</p>
					<p class="mt5">
						<label for="issue_remark">비고</label>
						<textarea id="issue_remark" name="ISSUE_REMARK" rows="5" ><c:out value="${issueInfo.ISSUE_REMARK }"/></textarea>
					</p>
					<!-- 목록으로 이동하기 버튼 -->
					<p style="clear:both;">
						<button style="float: right; margin-top: 10px; margin-right: 70px;" id="cancel_issue_bt">취소</button>
						<button style="float: right; margin-top: 10px; margin-right: 30px;" id="modify_issue_bt">수정</button>
					</p>
				</fieldset>
			</form>
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
	
	$("#customer_combobox option[value='${issueInfo.CUSTOMER_NO }']").attr("selected", "selected");
	$("#product_combobox option[value='${issueInfo.PRODUCT_NO }']").attr("selected", "selected");
	$("#issue_kind_combobox option[value='${issueInfo.KIND_NO }']").attr("selected", "selected");
	$("#issue_priority_combobox option[value='${issueInfo.ISSUE_PRIORITY }']").attr("selected", "selected");
	
	
	// 취소 버튼
	$("#cancel_issue_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			var page = '<c:out value="${param.page}" default="1"/>';
			var rows = '<c:out value="${param.rows}" default="20"/>';
			var sidx = '<c:out value="${param.sidx}" />';
			var sord = '<c:out value="${param.sord}" />';
			var issue_no = '<c:out value="${param.in}"/>';
		
			location.href = "issue_02.isnet?in="+issue_no+"&page=" + page + "&rows=" + rows  + "&sidx=" + sidx + "&sord=" + sord + "&from=detail";		
		});
	
	// 저장 버튼 
	$("#modify_issue_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			
			// 장애내용 등록여부를 체크합니다.
			if($("#issue_memo").val() == ""){
				simple_popup("안내", "장애내용을 입력해주세요");
				$("#issue_memo").focus();
				return;
			}
			
			// 서버로 전송합니다.
			$.ajax(
				{type:"POST",
				 url:"modify_issue01.isnet", 
				 data:$("#issue_form").serialize(), 
				 dataType:"json",
				 success:function(result, status){
					if(status == "success" && result.isSuccess){
						var page = '<c:out value="${param.page}" default="1"/>';
						var rows = '<c:out value="${param.rows}" default="20"/>';
						var sidx = '<c:out value="${param.sidx}" />';
						var sord = '<c:out value="${param.sord}" />';
						var issue_no = '<c:out value="${param.in}"/>';
					
						location.href = "issue_02.isnet?in="+issue_no+"&page=" + page + "&rows=" + rows  + "&sidx=" + sidx + "&sord=" + sord + "&from=detail";		
					}else{
						simple_popup("안내", "수정 중 오류가 발생하였습니다.");
					}
				},
			    error:function(){
				  	simple_popup("안내", "수정 중 오류가 발생하였습니다.");
			    }
			}); 
			
		});	
});
</script>		
<%@ include file="../common/bottom.jsp" %>