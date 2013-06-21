<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="일정관리"/>
<c:set var="two_depth" value="내 일정관리"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="issue_01.isnet">일정관리</a> / <strong>내 일정관리</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->			
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
			url: "list_schedule_01.isnet",
			data: param,
			dataType: "JSON",
			success: function(result, state){
				
				if(result && result.isSuccess){
					
					var scheduleList = result.scheduleList;
					$.each(scheduleList, function(index, schedule){
						display_schedule(schedule);
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
		// 일정 등록하기
		dayClick: function(selected_date, allDay, jsEvent, view){
			
			// 일정등록 팝업 열기
			$("#dialog-add-schedule").dialog({
				open: function(){
					// 일정등록 팝업의 화면 초기화
					$("#schedule_no").val("");
					$("#schedule_title").val("");
					$("#schedule_memo").val("");
					$(":radio[name='ALLDAY'][value='N']").attr("checked", "checekd").trigger("click");
					
					var date = new Date();
					var hour_now = date.getHours();
					
					$("#start_date").val($.fullCalendar.formatDate(selected_date, "yyyy-MM-dd"));
					$("#end_date").val($.fullCalendar.formatDate(selected_date, "yyyy-MM-dd"));
					
					$("#start_hour_box option:contains('"+hour_now+"')").attr("selected", "selected");
					$("#start_minute_box option:eq(0)").attr("selected", "selected");
					
					$("#end_hour_box option:contains('"+(hour_now + 1)+"')").attr("selected", "selected");
					$("#end_minute_box option:eq(0)").attr("selected", "selected");
				},
				// 버튼 등록
				buttons:[
				   {
					text:"등록",
					click:function(){
						var schedule = make_schedule();
						
						// 새로운 일정 정보 서버로 전송
						$.ajax({
							type: "POST",
							url: "add_schedule.isnet",
							data: schedule,
							dataType: "json",
							success: function(result, state){
								
								if(result && result.isSuccess){
									
									// 화면에 새로 등록된 스케줄 표시									
									schedule["SCHEDULE_NO"] = result.SCHEDULE_NO;
									display_schedule(schedule, 'new');
								}else{
									simple_popup("안내", "일정 등록 중 오류가 발생하였습니다.");
								}
								$("#dialog-add-schedule").dialog("close");
							}, 
							error: function(){
								simple_popup("안내", "일정 등록 중 오류가 발생하였습니다.");
								$("#dialog-add-schedule").dialog("close");		
							}
						});
					 }
				   },
				   {
					text:"취소",
					click:function(){
							$("#dialog-add-schedule").dialog("close");
						}
				   }
				]
			});
			
			$("#dialog-add-schedule").dialog("open");			
		},
		// 화면 전환시
		viewDisplay: function(view){
			// 화면 변경시 호출됨
			// 현재 화면에 보이는 일정표상의 첫날과 마지막날 정보를 조회한다.(달력모드인 경우에는 현재달의 1일과 다음달의 1일이 선택된다.)
			var start = view.start;
			var end = view.end;		
			
			var param = {
				START_YEAR: start.getFullYear(),
				START_MONTH: start.getMonth(),
				START_DAY: start.getDate(),
				END_YEAR: end.getFullYear(),
				END_MONTH: end.getMonth(),
				END_DAY: end.getDate(),
				EMPLOYEE_NO: '${LOGIN_USER.EMPLOYEE_NO}'
			};
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
						text:'삭제',
						click: function(event){
							// 일정 삭제하기
							event.preventDefault();
							
							confirm_popup("안내", "삭제하시겠습니까?", 
								function(){
									$(this).dialog("close");
									var schedule_no = $("#schedule_no").val();
									$.ajax({
										type: "GET",
										url: "delete_schedule.isnet",
										data: {SCHEDULE_NO: schedule_no},
										dataType: "json",
										success: function(result, state){
											if(result && result.isSuccess){
												// 일정표에서 일정 삭제
												$('#calendar').fullCalendar( 'removeEvents', schedule_no);
											}else{
												simple_popup("안내", "일정 삭제 중 오류가 발생하였습니다.");
											}
											$("#dialog-add-schedule").dialog("close");
										},
										error: function(){
											simple_popup("안내", "일정 삭제 중 오류가 발생하였습니다.");
										}
									});
								},
								function(){
									$(this).dialog("close");
								}
							);							
						}
					},
					{
						text:"변경",
						click: function(event){
							event.preventDefault();
							
							// 일정 수정하기
							var schedule = make_schedule();
							schedule["SCHEDULE_NO"] = $("#schedule_no").val();
							
							$.ajax({
								type: "POST",
								url: "update_schedule.isnet",
								data: schedule,
								dataType: "json",
								success: function(result, state){
									if(result && result.isSuccess){
										
										// 변경 전 일정을 화면에서 지웁니다.
										$('#calendar').fullCalendar( 'removeEvents', $("#schedule_no").val());
										// 변경된 일정을 화면에 표시합니다.
										schedule["SCHEDULE_NO"] = $("#schedule_no").val();
										display_schedule(schedule, 'new');
										$("#dialog-add-schedule").dialog("close");
									}else{
										simple_popup("안내", "일정변경 중 오류가 발생하였습니다.");
									}
								},
								error: function(){
									simple_popup("안내", "일정변경 중 오류가 발생하였습니다.");
								}
							});							
						}
					},
					{
						text:'취소',
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