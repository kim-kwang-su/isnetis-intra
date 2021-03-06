<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="CRM관리"/>
<c:set var="two_depth" value="고객 관리"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="crm_00.isnet">CRM관리</a> / <a href="crm_01.isnet">고객 관리</a> / 
			<c:choose>
				<c:when test="${param.job eq 'view' }">고객 상세정보</c:when>
				<c:when test="${param.job eq 'modify' }">고객정보 수정</c:when>
			</c:choose>
			<strong></strong>
			</div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>모듈 상세 정보</h3> -->
			<div id="detail_form">
			<form id="crm_form">
				<div id="customer_box">
				<fieldset>
					<legend>기본정보</legend>
					<p>
						<label for="company_no"><span style="color: red;">*</span> 고객사</label>
						<select id="company_no" name="COMPANY_NO" >
							<option value="">-- 고객사를 선택하세요 --</option>
							<c:forEach var="company" items="${crmCompanyList }">
								<c:choose>
									<c:when test="${customerInfo.COMPANY_NO eq company.COMPANY_NO}">
										<option value="${company.COMPANY_NO }" selected="selected">${company.COMPANY_NAME }</option>
									</c:when>
									<c:otherwise>
										<option value="${company.COMPANY_NO }">${company.COMPANY_NAME }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					    </select>
					    <label for="customer_dept"><span style="color: red;">*</span> 부서</label>
						<input id="customer_dept" name="CUSTOMER_DEPT" type="text" value="${customerInfo.CUSTOMER_DEPT }"/>
						<label for="customer_position"><span style="color: red;">*</span> 직급</label>
						<input id="customer_position" name="CUSTOMER_POSITION" type="text" value="${customerInfo.CUSTOMER_POSITION }" />
						<input type="hidden" name="CUSTOMER_NO" value="${param.CUSTOMER_NO }" />
					</p>
					<p>
						<label for="customer_name"><span style="color: red;">*</span> 고객명</label>
						<input id="customer_name" name="CUSTOMER_NAME" type="text" value="${customerInfo.CUSTOMER_NAME }" />
						<label for="customer_job">업무</label>
						<input id="customer_job" name="CUSTOMER_JOB" type="text" value="${customerInfo.CUSTOMER_JOB }"/>
						<label for="isnet_impression">당사 호감도</label>
						<input id="isnet_impression" name="ISNET_IMPRESSION" type="text" value="${customerInfo.ISNET_IMPRESSION }" />
					</p>
					<p>
						<label for="customer_phone"><span style="color: red;">*</span> 연락처</label>
						<input id="customer_phone" name="CUSTOMER_PHONE" type="text" value="${customerInfo.CUSTOMER_PHONE }" />
						<label for="customer_email"><span style="color: red;">*</span> 메일</label>
						<input id="customer_email" name="CUSTOMER_EMAIL" type="text"  value="${customerInfo.CUSTOMER_EMAIL }"/>
						<label for="customer_company_tel"><span style="color: red;">*</span> 회사 전화번호</label>
						<input id="customer_company_tel" name="CUSTOMER_COMPANY_TEL" type="text" value="${customerInfo.CUSTOMER_COMPANY_TEL }"/>
					</p>
					<p>
						<label for="customer_age">나이</label>
						<input id="customer_age" name="CUSTOMER_AGE" type="text" value="${customerInfo.CUSTOMER_AGE }"/>
						<label for="customer_birth">생년월일</label>
						<input id="customer_birth" name="CUSTOMER_BIRTH" type="text" value="${customerInfo.CUSTOMER_BIRTH }" />							
						<!-- <label for="expected_developed_date"></label> -->
						<c:choose>
							<c:when test="${not empty customerInfo.IS_LUNAR_DATE and customerInfo.IS_LUNAR_DATE eq 'Y' }">
								<input id="lunar_date" type="checkbox"  name="IS_LUNAR_DATE" value="Y" checked="checked" style="width: 20px; margin-top: 5px"/><label style="display: inline; width: auto;">음력</label>
							</c:when>
							<c:otherwise>
								<input id="lunar_date" type="checkbox"  name="IS_LUNAR_DATE" value="Y"  style="width: 20px; margin-top: 5px"/><label style="display: inline; width: auto;">음력</label>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${not empty customerInfo.IS_LEAP_MONTH and customerInfo.IS_LEAP_MONTH eq 'Y' }">
								<input id="leap_month" type="checkbox" name="IS_LEAP_MONTH" checked="checked" value="Y" style="width: 20px; margin-top: 5px"/><label style="display: inline; width: auto">윤달</label>
							</c:when>
							<c:otherwise>
								<input id="leap_month" type="checkbox" name="IS_LEAP_MONTH" value="Y" style="width: 20px; margin-top: 5px"/><label style="display: inline; width: auto">윤달</label>
							</c:otherwise>
						</c:choose>
					</p>
					<p>
						<label for="customer_blood">혈액형</label>
						<input id="customer_blood" name="CUSTOMER_BLOOD" type="text" value="${customerInfo.CUSTOMER_BLOOD }" />
						<label for="customer_height">키</label>
						<input id="customer_height" name="CUSTOMER_HEIGHT" type="text" value="${customerInfo.CUSTOMER_HEIGHT }"/>
						<label for="customer_weight">체중</label>
						<input id="customer_weight" name="CUSTOMER_WEIGHT" type="text" value="${customerInfo.CUSTOMER_WEIGHT }"/>
					</p>
					
					<p>
						<label for="customer_home_tel">집전화</label>
						<input id="customer_home_tel" name="CUSTOMER_HOME_TEL" type="text" value="${customerInfo.CUSTOMER_HOME_TEL }"/>
						<label for="customer_home_addr">자택주소</label>
						<input id="customer_home_addr" name="CUSTOMER_HOME_ADDR" type="text" style="width: 573px;" value="${customerInfo.CUSTOMER_HOME_ADDR }" />
					</p>
					<p>
						<label for="married_yn">결혼여부</label>
						<c:choose>
							<c:when test="${not empty customerInfo.MARRIED_YN and customerInfo.MARRIED_YN eq 'Y' }">
								<input id="married_yn" type="checkbox" name="MARRIED_YN" checked="checked" value="Y" style="width: 20px; margin-top: 5px;" /><label style="display: inline; width: 168px; text-align: left;">결혼</label>
							</c:when>
							<c:otherwise>
								<input id="married_yn" type="checkbox" name="MARRIED_YN" value="Y" style="width: 20px; margin-top: 5px;" /><label style="display: inline; width: 168px; text-align: left;">결혼</label>
							</c:otherwise>
						</c:choose>
						<label for="wedding_day">결혼기념일</label>
						<input id="wedding_day" name="WEDDING_DAY" type="text" value="${customerInfo.WEDDING_DAY }" />
					</p>
					<p>
						<label for="customer_hometown">고향</label>
						<input type="text"id="customer_hometown"  name="CUSTOMER_HOMETOWN"  value="${customerInfo.CUSTOMER_HOMETOWN }"/>		
						<label for="customer_car">자동차</label>
						<input type="text"id="customer_car"  name="CUSTOMER_CAR"  value="${customerInfo.CUSTOMER_CAR }"/>		
					</p>
				</fieldset>
				<fieldset style="margin-top: 15px;">
					<legend>경력 및 학력</legend>
					<p>
						<label for="first_company_name">전 직장</label>
						<input id="first_company_name" name="FIRST_COMPANY_NAME" type="text" value="${customerInfo.FIRST_COMPANY_NAME }"/>
						<label for="second_company_name">전전 직장</label>
						<input id="second_company_name" name="SECOND_COMPANY_NAME" type="text" value="${customerInfo.SECOND_COMPANY_NAME }"/>
						<label for="third_company_name">전전전 직장</label>
						<input id="third_company_name" name="THIRD_COMPANY_NAME" type="text" value="${customerInfo.THIRD_COMPANY_NAME }"/>
					</p>
					<p>
						<label for="graduate_school_name">대학원</label>
						<input id="graduate_school_name" name="GRADUATE_SCHOOL_NAME" type="text" value="${customerInfo.GRADUATE_SCHOOL_NAME }" />
						<label for="university_name">대학교</label>
						<input id="university_name" name="UNIVERSITY_NAME" type="text" value="${customerInfo.UNIVERSITY_NAME }" />
						<label for="major_name">전공</label>
						<input id="major_name" name="MAJOR_NAME" type="text" value="${customerInfo.MAJOR_NAME }"/>
					</p>
					<p>
						<label for="high_school_name">고등학교</label>
						<input id="high_school_name" name="HIGH_SCHOOL_NAME" type="text" value="${customerInfo.HIGH_SCHOOL_NAME }" />
						<label for="middle_school_name">중학교</label>
						<input id="middle_school_name" name="MIDDLE_SCHOOL_NAME" type="text" value="${customerInfo.MIDDLE_SCHOOL_NAME }"/>
						<label for="primary_school_name">초등학교</label>
						<input id="primary_school_name" name="PRIMARY_SCHOOL_NAME" type="text" value="${customerInfo.PRIMARY_SCHOOL_NAME }" />
					</p>
				</fieldset>
				<fieldset style="margin-top: 15px;">
					<legend>부가정보</legend>
					<p id="developers">
						<label for="hobby">취미</label>
						<input type="text" id="hobby" name="HOBBY" value="${customerInfo.HOBBY }" />
						<label for="hobby_grade">취미실력</label>
						<input type="text" id="hobby_grade" name="HOBBY_GRADE" value="${customerInfo.HOBBY_GRADE }" />					
					</p>
					<p class="mt10">
						<label for="customer_etc">기타</label>
						<textarea id="customer_etc" name="CUSTOMER_ETC" rows="3">${customerInfo.CUSTOMER_ETC }</textarea>
					</p>
					<p style="clear:both;">
						<button style="float: right; margin-top: 10px; margin-right: 70px;" id="cancel_crm_bt">목록</button>
						<c:if test="${param.job eq 'modify' }">
						<button style="float: right; margin-top: 10px; margin-right: 35px;" id="save_crm_bt">수정</button>
						</c:if>
					</p>	
				</fieldset>
				</div>
			</form>
			</div>
			
			
			<%@ include file="../common/footer.jsp" %>
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
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
			$("#dialog-support_type").dialog("open");
		});
	
	// 취소 버튼
	$("#cancel_crm_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			location.href = "crm_01.isnet";
		});
	
	// 저장 버튼 
	$("#save_crm_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			//$(this).dialog("close");
			
			if($("#company_no").val() == ""){
				simple_popup("안내", "고객사를 선택하세요.");
				$("#company_no").focus();
				return false;
			}
			if($("#customer_dept").val() == ""){
				simple_popup("안내", "부서명을 입력하세요");
				$("#customer_dept").focus();
				return false;
			}
			if($("#customer_position").val() == ""){
				simple_popup("안내", "직급을 입력하세요");
				$("#customer_position").focus();
				return false;
			}
			if($("#customer_name").val() == ""){
				simple_popup("안내", "고객명을 입력하세요");
				$("#customer_name").focus();
				return false;
			}
			if($("#customer_phone").val() == ""){
				simple_popup("안내", "연락처를 입력하세요");
				$("#customer_phone").focus();
				return false;
			}
			if($("#customer_email").val() == ""){
				simple_popup("안내", "e-메일주소를 입력하세요");
				$("#customer_emal").focus();
				return false;
			}
			if($("#customer_company_tel").val() == ""){
				simple_popup("안내", "회사전화번호를 입력하세요");
				$("#customer_company_tel").focus();
				return false;
			}
			
			$.ajax({
				type:"POST",
				url:"update_crm_02.isnet",
				dataType:"json",
				data:$("#crm_form").serialize(),
				success:function(data, status){
					if(data && data.isSuccess){
						simple_popup("안내", "고객정보가 수정되었습니다.");
						//location.href = "crm_01.isnet";
					}else{
						simple_popup("안내", "고객정보 수정 중 오류가 발생하였습니다.");
						location.reload();
					}
				},
				error:function(){
					alert("고객정보 수정 중 오류가 발생하였습니다.");
				}
			});
			
		});
});
</script>		
<%@ include file="../common/bottom.jsp" %>