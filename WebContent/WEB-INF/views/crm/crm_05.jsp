<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="CRM관리"/>
<c:set var="two_depth" value="캘린더"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="crm_00.isnet">CRM관리</a> / <strong>캘린더</strong></div>
			
			<!-- 스케줄러 -->
			<div id="calendar" class="mt10"></div>
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->

<script type="text/javascript">
$(document).ready(function() {
	
	// 일정 전체 갱신하기
	var reload_schedule = function(param){
		$('#calendar').fullCalendar( 'removeEvents');
		
		$.ajax({
			type: "GET",
			url: "list_crm_05.isnet",
			data: param,
			dataType: "JSON",
			success: function(result, state){
				
				if(result && result.isSuccess){
					
					var memorialDayList = result.memorialDayList;
					$.each(memorialDayList, function(index, memorialDay){
						display_memorialDay(memorialDay, param["YEAR"]);
					});					
				}else{
					simple_popup("안내", "일정 조회 중 오류가 발생하였습니다.");
				}
			},
			error: function(){
				simple_popup("안내", "일정 조회 중 오류가 발생하였습니다.");
			}
		});
		
	};
	
	// 일정표 표시하기
	$('#calendar').fullCalendar({
		theme: true,
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek,agendaDay'
		},
		editable: true,
		weekends: true,
		weekMode: 'liquid',
		// 화면 전환시
		viewDisplay: function(view){
			// 화면 변경시 호출됨
			// 현재 화면에 보이는 일정표상의 첫날과 마지막날 정보를 조회한다.(달력모드인 경우에는 현재달의 1일과 다음달의 1일이 선택된다.)
			var start = view.start;
			var param = {MONTH:(start.getMonth() + 1), YEAR:start.getFullYear()};
			// 스케줄 리로드
			reload_schedule(param);
		},
		events: []
	});
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>