<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Object obj = session.getAttribute("LOGIN_USER");
	if (obj == null) {
		response.sendRedirect("/login.isnet?status=fail");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/common/css/main.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/common/css/print.css" media="print" />
<!-- 스케줄러 -->
<link rel='stylesheet' type='text/css' href='/common/fullcalendar/fullcalendar.css' />
<link rel='stylesheet' type='text/css' href='/common/fullcalendar/fullcalendar.print.css' media='print' />
<link rel="stylesheet" type="text/css" media="screen" href="/common/jquery-ui-1.9.2/css/blitzer/jquery-ui-1.9.2.custom.css" />
<!-- jqGrid -->
<link rel="stylesheet" type="text/css" media="screen" href="/common/jquery.jqGrid-4.4.1/css/ui.jqgrid.css" />
<!-- 파일 업로드 팝업 -->
<link href="/common/jquery-fileuploader/fileUploader.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 6]>
<link rel="stylesheet" type="text/css" href="css/ie6_or_less.css" />
<![endif]-->
<script type="text/javascript" src="/common/jquery-1.8.3/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/common/jquery.jqGrid-4.4.1/js/i18n/grid.locale-kr.js"></script>
<script type="text/javascript" src="/common/jquery.jqGrid-4.4.1/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="/common/jquery-ui-1.9.2/js/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="/common/jquery-fileuploader/jquery.fileUploader.js"></script>
<script type='text/javascript' src='/common/fullcalendar/fullcalendar.min.js'></script>
<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript">
	var win = null;
	var console = window.console || {
		log : function() {
		}
	};

	$(document).ready(function() {

		// 메뉴 설정
		var one_depth = '${one_depth}';
		var two_depth = '${two_depth}';
		//console.log(one_depth + ', ' + two_depth);

		$("#nav li").removeClass("active");
		$("#nav > li:contains('" + one_depth + "')").addClass("active");
		if (two_depth != null) {
			$("#nav ul>li:contains('" + two_depth + "')").addClass("active");
		}

		// 로그아웃
		$("#logout_bt").click(function(event) {
			event.preventDefault();
			location.href = "/";
		});

	});
</script>
<title>isnet</title>
</head>
<body id="type-a">
	<div id="wrap">

		<div id="header">
			<div id="site-name">isnetis.com</div>
			<div id="search">
				<label for="searchsite">${LOGIN_USER.EMPLOYEE_NAME } (${LOGIN_USER.EMAIL })</label>
				<!-- <button id="modify_bt"><span>정보수정</span></button> -->
				<button id="logout_bt">
					<span>로그아웃</span>
				</button>
			</div>
			<ul id="nav">
				<li class="first"><a href="/home.isnet">Home</a></li>
				<li><a href="/schedule/schedule_01.isnet">일정관리</a>
					<ul>
						<li class="first"><a href="/schedule/schedule_01.isnet">내 일정관리</a></li>
						<li><a href="/schedule/schedule_02.isnet">직원별 조회</a></li>
						<li class="last"><a href="/schedule/schedule_03.isnet">부서별 조회</a></li>
					</ul></li>
				<c:if
					test="${not empty LOGIN_USER and (LOGIN_USER.IS_ADMIN eq 'Y' or LOGIN_USER.DEPT_NO eq 3 or LOGIN_USER.DEPT_NO eq 4 )}">
					<li><a href="/crm/crm_00.isnet">CRM관리</a>
						<ul>
							<li class="first"><a href="/crm/crm_00.isnet">불견일수관리</a></li>
							<li><a href="/crm/crm_01.isnet">고객 관리</a></li>
							<li class="last"><a href="/crm/crm_03.isnet">고객사 관리</a></li>
							<!-- <li class="last"><a href="#">고객일정조회</a></li> -->
						</ul></li>
				</c:if>
				<li><a href="/worksheet/sheet_01.isnet">업무일지</a>
					<ul>
						<li class="first"><a href="/worksheet/sheet_01.isnet">조회</a></li>
						<li><a href="/worksheet/sheet_05.isnet">등록</a></li>
						<li><a href="/worksheet/sheet_07.isnet">내 일지 보기</a></li>
						<li class="last"><a href="/worksheet/sheet_06.isnet">작성현황</a></li>
					</ul></li>
				<li><a href="/issue/issue_01.isnet">이슈관리</a>
					<ul>
						<li class="first"><a href="/issue/issue_01.isnet">이슈리스트</a></li>
						<li><a href="/issue/issue_03.isnet">이슈등록</a></li>
						<li><a href="/issue/issue_05.isnet">진행상황조회</a></li>
						<li class="last"><a href="/issue/issue_06.isnet">QA</a></li>
					</ul></li>
				<li><a href="/module/module_01.isnet">버전관리</a>
					<ul>
						<li class="first"><a href="/module/module_01.isnet">최신 모듈변경이력</a></li>
						<li><a href="/module/module_06.isnet">모듈찾기</a></li>
						<li><a href="/module/module_03.isnet">신규 모듈등록</a></li>
						<li><a href="/module/module_04.isnet">모듈별 최종버전</a></li>
						<li><a href="/module/module_05.isnet">고객사별 최종버전</a></li>
						<li><a href="/option/option_01.isnet">옵션다운로드</a></li>
						<li><a href="/option/option_02.isnet">옵션리스트</a></li>
						<li class="last"><a href="/option/option_03.isnet">옵션비교</a></li>
					</ul></li>
				<li><a href="/board/board_01.isnet">정보공유</a>
					<ul>
						<li class="first"><a href="/board/board_01.isnet">FAQ</a></li>
						<li ><a href="/board/board_02.isnet">Q&A</a></li>
						<li ><a href="/board/board_06.isnet">사내제안</a></li>
						<li ><a href="/board/board_04.isnet">서식</a></li>
						<li class="last"><a href="/board/board_05.isnet">규정</a></li>
					</ul></li>
				<c:choose>
					<c:when
						test="${not empty LOGIN_USER and LOGIN_USER.IS_ADMIN eq 'Y' }">
						<li class="last"><a href="/admin/admin_01.isnet">관리자</a>
							<ul>
								<li class="first"><a href="/admin/admin_01.isnet">사용자 관리</a></li>
								<li><a href="/admin/admin_02.isnet">고객사 관리</a></li>
								<li><a href="/admin/admin_03.isnet">고객사 담당자관리</a></li>
								<li class="last"><a href="/admin/admin_04.isnet">데이타베이스 관리</a></li>
							</ul></li>
					</c:when>
					<c:otherwise>
						<li class="last"><a href="/employee/employee_01.isnet">사용자</a>
							<ul>
								<li class="first"><a href="/employee/employee_01.isnet">내정보 보기</a></li>
							</ul></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>


		<div id="content-wrap">