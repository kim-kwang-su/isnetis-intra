<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="일정관리"/>
<c:set var="two_depth" value="부서별 조회"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="issue_01.isnet">일정관리</a> / <strong>부서별 조회</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- 검색 조건 -->
			<div id="searchbox" style="padding:5px 30px;margin:10px 0 0 0;border:1px solid #cecece;">
				<strong>부서명 : </strong>
				<select id="dept_no_box" name="DEPT_NO" class="mr5">
					<c:forEach var="dept" items="${deptList }">
						<option value="${dept.DEPT_NO }">${dept.DEPT_NAME }</option>
					</c:forEach>
				</select>
				<button style="padding:0px 5px" class="icon mr20" id="search_bt">조회</button>
			</div>
			<!-- 검색 조건 -->
			
			<!-- 스케줄러 -->
			<div id="calendar" class="mt10"></div>
			
			<!-- 일정등록 팝업 -->
			<div id="dialog-add-schedule" title="일정등록 화면">
				<form>
					<p>
						<label for="schedule_title">제목</label>
						<input type="text" id="schedule_title" class='title' name="SCHEDULE_DATE"/>
						<input type="hidden" id="schedule_no" />
					</p>
					<p id="start_date_block">
						<label for="">시작</label>
						<input type="text" class="date" id="start_date" name="START_DATE"/>
						<select id="start_hour_box" class="time ml15" name="START_HOUR" >
						<c:forEach begin="0" end="23" varStatus="num">
							<c:choose>
								<c:when test="${num.index lt 10 }">
									<option value="0${num.index }">0${num.index }</option>								
								</c:when>
								<c:otherwise>
									<option value="${num.index }">${num.index }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					    </select>
					    <label for="schedule_title" class="time">시</label>
					    <select id="start_minute_box" class="time" name="START_MINUTE" >
						<c:forEach begin="0" end="5" varStatus="num">
							<c:choose>
								<c:when test="${num.index eq 0 }">
									<option value="0${num.index }">0${num.index }</option>								
								</c:when>
								<c:otherwise>
									<option value="${num.index * 10 }">${num.index * 10 }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					    </select>
					    <label for="schedule_title" class="time">분</label>
					</p>
					<p id="end_date_block">
						<label for="">종료</label>
						<input type="text" class="date" id="end_date" name="END_DATE"/>
						<select id="end_hour_box" class="time ml15" name="END_HOUR" >
						<c:forEach begin="0" end="23" varStatus="num">
							<c:choose>
								<c:when test="${num.index lt 10 }">
									<option value="0${num.index }">0${num.index }</option>								
								</c:when>
								<c:otherwise>
									<option value="${num.index }">${num.index }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					    </select>
					    <label for="" class="time">시</label>
					    <select id="end_minute_box" class="time" name="END_MINUTE" >
						<c:forEach begin="0" end="5" varStatus="num">
							<c:choose>
								<c:when test="${num.index eq 0 }">
									<option value="0${num.index }">0${num.index }</option>								
								</c:when>
								<c:otherwise>
									<option value="${num.index * 10 }">${num.index * 10 }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					    </select>
					    <label for="" class="time">분</label>
					</p>
					<p class="contact-method">
						<span>하루 종일</span>
						<label><input  name="ALLDAY" type="radio"  value="Y"/> ON</label>
						<label><input  name="ALLDAY" type="radio"  value="N" checked="checked"/> OFF</label>
					</p>
					<p>
						<label>내용</label>
						<textarea id="schedule_memo" name="SCHEDULE_MEMO" rows="4"></textarea>
					</p>
				</form>
			</div>
		
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
			url: "list_schedule_02.isnet",
			data: param,
			dataType: "JSON",
			success: function(result, state){
				
				if(result && result.isSuccess){
					
					var scheduleList = result.scheduleList;
					$.each(scheduleList, function(index, schedule){
						display_schedule(schedule, 'deptview');
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

	
	// 부서별 조회
	$("#search_bt").click(function(event){
		event.preventDefault();
		$('#calendar').fullCalendar( 'today' );
	});
	
	$("#dialog-add-schedule").dialog({
		autoOpen: false,
		height: 340,
		width: 550,
		resizzble: false,
		modal: true,
		create: function(){
			init_schedule_popup();
		}
	});	
	
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
			var end = view.end;		
			
			var dept_no = $("#dept_no_box").val();
			var param = {
				START_YEAR: start.getFullYear(),
				START_MONTH: start.getMonth(),
				START_DAY: start.getDate(),
				END_YEAR: end.getFullYear(),
				END_MONTH: end.getMonth(),
				END_DAY: end.getDate()
			};
			if(dept_no != ""){
				param["DEPT_NO"] = dept_no;
			}
			// 스케줄 리로드
			reload_schedule(param);
		},
		// 일정을 선택하는 경우 - 수정 및 삭제
		eventClick: function(event, jsEvent, view){
			
			$("#dialog-add-schedule").dialog({
				open: function(){					
					// 일정수정 팝업을 초기화합니다.	
					view_schedule_popup(event, jsEvent, view);
				},
				buttons:[
					{
						text:'닫기',
						click: function(){
							$("#dialog-add-schedule").dialog("close");
						}
					}	
				]
			});
			$("#dialog-add-schedule").dialog("open");
			
		},
		events: []
	});
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>