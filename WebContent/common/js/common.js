/*
son of suckerfish menu script from:
http://www.htmldog.com/articles/suckerfish/dropdowns/
 */

sfHover = function() {
	try{
		var sfEls = document.getElementById("nav").getElementsByTagName("LI");
		for (var i=0; i<sfEls.length; i++) {
			sfEls[i].onmouseover=function() {
				this.className+=" sfhover";
				this.style.zIndex=200; //this line added to force flyout to be above relatively positioned stuff in IE
			};
			sfEls[i].onmouseout=function() {
				this.className=this.className.replace(new RegExp(" sfhover\\b"), "");
			};
		}
	}catch(ee){}
};
try{
	if (window.attachEvent) window.attachEvent("onload", sfHover);
}catch(ee){
	
};
// 백스페이스, 엔터키 차단
$(document).unbind('keydown').bind('keydown', function (event) {
    var doPrevent = false;
    
    // 읽기전용, 비활성화된 입력필드에서의 백스페이스 차단
    if (event.keyCode === 8) {
        var d = event.srcElement || event.target;
        if ((d.tagName.toUpperCase() === 'INPUT' && (d.type.toUpperCase() === 'TEXT' || d.type.toUpperCase() === 'PASSWORD')) 
             || d.tagName.toUpperCase() === 'TEXTAREA') {
            doPrevent = d.readOnly || d.disabled;
        }
        else {
            doPrevent = true;
        }
    }
    
    // 활성화된 입력필드에서의 엔터키 차단
    if (event.keyCode === 13) {
        var d = event.srcElement || event.target;
        if ((d.tagName.toUpperCase() === 'INPUT' && (d.type.toUpperCase() === 'TEXT' || d.type.toUpperCase() === 'PASSWORD')) ) {
        	doPrevent = true;
        }
        else {
        	doPrevent = d.readOnly || d.disabled;
        }
    }

    if (doPrevent) {
        event.preventDefault();
    }
});

/*
 * 기본 팝업
 */
simple_popup = function(title, message){
	$("<div title='"+title+"'><p><span class='ui-icon ui-icon-circle-check' style='float: left; margin: 0 7px 30px 0;'></span>"+message+"</p></div>").dialog({
		modal: true,
		buttons: {
			Ok:function(){
				$(this).dialog("close");
			}
		}
	});
};

var confirm_popup = function(title, message, yes_callback, cancel_callback, button_label1, button_label2){
	var html = "";
	html += '<div title="'+title+'">';
	html += '<p><span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 30px 0;"></span>'+message+'</p>';
	html += '</div>';
	
	var yes_text = (button_label1 || "예");
	var no_text = (button_label2 || "아니오");
	
	$(html).dialog({
	      resizable: false,
	      height:200,
	      modal: true,
	      buttons:  [{text:yes_text, click:yes_callback}, {text:no_text, click:cancel_callback}]
	    });
};

// 달력 표시하기
var setDatepicker = function(elementid, minDate, maxDate){
	$(elementid).datepicker({
		monthNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
		yearSuffix: "년",
		dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
		showMonthAfterYear: true,
		dateFormat: "yy-mm-dd"
	});
	
	if(minDate == 0 || minDate){
		$(elementid).datepicker("option", "minDate", minDate);
	}
	if(maxDate == 0 || maxDate){
		$(elementid).datepicker("option", "maxDate", maxDate);
	}
};

// 유효성 검증하기
var is_valid_value = function(type, value){
	
	
	var pattern = {
			username: /^[가-힝]{2,}$/,
			email: /^[\w]{3,}@[\w]+(\.[\w-]+){1,3}$/,
			password: /((?=.*\d)(?=.*[a-z])(?=.*[!@#$%_]).{7,})/
	};
	try{
		return pattern[type].test(value);
	}catch(ee){
		return false;
	}
};

//전체 선택/해제 처리
function checkedAllOrNothing(all_checkbox_id, checkboxes_name){
	$("#" + all_checkbox_id).click( function() { 

		var result = $("#" + all_checkbox_id).attr("checked");
		if(result == "checked") {
			$("input[name="+checkboxes_name+"]:checkbox").each(function() {
				$(this).attr("checked", true);
			});
		}else {
			$("input[name="+checkboxes_name+"]:checkbox").each(function() {
				$(this).attr("checked", false);
			});
		}
	});
};

// 전체 선택/해제 처리2 - 체크박스 선택시 전체선택버튼의 체크여부를 조절한다.
function checkedAllCheckbox(all_checkbox_id, checkboxes_name){
	$("input[name="+checkboxes_name+"]:checkbox").live("click",function() {
   		var result = $(this).attr("checked");
   		var cnt = 0;
   		if (result != "checked") {
   			$("#" + all_checkbox_id).attr("checked", false);
   		}else {
   			$("input[name="+checkboxes_name+"]:checkbox").each(function() {
   				var result2 = $(this).attr("checked");
   				if (result2 == "checked") {
   					cnt++;
   				}
   			});
   			if (cnt == $("input[name="+checkboxes_name+"]").length) {
   				$("#" + all_checkbox_id).attr("checked", "checked");
   			}
   		}
   		
   	});
};


var getParameterByName = function(name){
  name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
  var regexS = "[\\?&]" + name + "=([^&#]*)";
  var regex = new RegExp(regexS);
  var results = regex.exec(window.location.search);
  if(results == null)
    return "";
  else
    return decodeURIComponent(results[1].replace(/\+/g, " "));
};

var getQueryString = function(){
	return window.location.search;
};

/*
 * 스케줄 관련 함수
 */
var init_schedule_popup = function(){
	setDatepicker("#start_date");
	setDatepicker("#end_date");
	
	var date = new Date();
	var hour_now = date.getHours();
	
	$("#start_date").change(function(){
		$( "#end_date" ).datepicker( "option", "minDate", $(this).datepicker("getDate") );	
	});
	$("#end_date").change(function(){
		$( "#start_date" ).datepicker( "option", "maxDate", $(this).datepicker("getDate") );	
	});
	
	$(":radio[name='ALLDAY']").click(function(){
		if($(this).val() == "Y"){
			$("#end_date_block").hide("slow");
			$("#start_hour_box option:eq('0')").attr("selected", "selected");
			$("#start_minute_box option:eq(0)").attr("selected", "selected");
			
		}else if($(this).val() == "N"){
			$("#end_date_block").show("slow");
			$("#start_hour_box option:contains('"+hour_now+"')").attr("selected", "selected");
			$("#start_minute_box option:eq(0)").attr("selected", "selected");
		}
	});
};


var view_schedule_popup = function(event, jsEvent, view){
	// 일정수정 팝업을 초기화합니다.	
	$("#dialog-add-schedule").dialog( "option", "title", "일정등록 화면" );
	$("#dialog-add-schedule").dialog("open");
	$("#schedule_no").val(event.id);
	$("#schedule_title").val(event.title);
	$("#schedule_memo").val(event.memo);
	$("#start_date").val($.fullCalendar.formatDate(event.start, "yyyy-MM-dd"));

	if(!event.allDay || event.longDay){
		$(":radio[name='ALLDAY'][value='N']").attr("checked", "checekd").trigger("click");

		$("#end_date").val($.fullCalendar.formatDate(event.end, "yyyy-MM-dd"));
		
		$("#start_hour_box option:contains('"+$.fullCalendar.formatDate(event.start, "HH")+"')").attr("selected", "selected");
		$("#start_minute_box option:contains('"+$.fullCalendar.formatDate(event.start, "mm")+"')").attr("selected", "selected");
		
		$("#end_hour_box option:contains('"+$.fullCalendar.formatDate(event.end, "HH")+"')").attr("selected", "selected");
		$("#end_minute_box option:contains('"+$.fullCalendar.formatDate(event.end, "mm")+"')").attr("selected", "selected");
	}else{
		$(":radio[name='ALLDAY'][value='Y']").attr("checked", "checekd").trigger("click");
	}
};

// 일정데이터 생성(일정등록, 일정수정에서 사용함)
var make_schedule = function(){
	var title = $("#schedule_title").val();
	if($.trim(title) == ""){
		simple_popup("안내", "제목을 입력하세요");
		$("#schedule_title").focus();
		return false;
	}
	
	var param = {};
	param["SCHEDULE_TITLE"] = title;
	param["SCHEDULE_MEMO"] = $("#schedule_memo").val();
	
	var start_date = $("#start_date").datepicker("getDate");
	param["START_YEAR"] = start_date.getFullYear();
	param["START_MONTH"] = start_date.getMonth();
	param["START_DAY"] = start_date.getDate();
	
	var allday_mode = $(":radio[name='ALLDAY']:checked").val();
	param["ALLDAY"] = allday_mode;
	if(allday_mode == "N"){
		param["START_HOUR"] = $("#start_hour_box").val();
		param["START_MINUTE"] = $("#start_minute_box").val();
		
		var	end_date = $("#end_date").datepicker("getDate");
		param["END_YEAR"] = end_date.getFullYear();
		param["END_MONTH"] = end_date.getMonth();
		param["END_DAY"] = end_date.getDate();
		param["END_HOUR"] = $("#end_hour_box").val();
		param["END_MINUTE"] = $("#end_minute_box").val();
		
	}
	return param;
};

var display_memorialDay = function(memorialDay, year){
	var memorialDay_event = {};
	memorialDay_event["id"] = memorialDay.CUSTOMER_NO;
	var date_gubun = "";
	switch(memorialDay.MEMORIAL_DATE_GUBUN){
	case 'B':
		date_gubun = "생일"; break;
	case 'W':
		date_gubun = "결혼"; break;
	}
	memorialDay_event["title"] = "("+date_gubun + ")" + memorialDay.CUSTOMER_NAME +" "+(memorialDay.IS_LUNAR_DATE == 'Y' ? " (음력 "+memorialDay.MEMORIAL_DATE+")" : "");
	memorialDay_event["allDay"] = "Y";
	memorialDay_event["start"] = $.fullCalendar.parseDate(year + "-" + memorialDay.MEMORIAL_DATE);
	memorialDay_event["longDay"] = false;
	
	$('#calendar').fullCalendar('renderEvent', memorialDay_event);
};

var display_schedule = function(schedule, viewmode){
	var schedule_event = {};
	schedule_event["id"] = schedule.SCHEDULE_NO;
	if(viewmode == 'deptview'){
		schedule_event["title"] = "("+schedule.EMPLOYEE_NAME+")" + schedule.SCHEDULE_TITLE;
	}else{
		schedule_event["title"] = schedule.SCHEDULE_TITLE;
	}
	schedule_event["memo"] = schedule.SCHEDULE_MEMO;
	schedule_event["allDay"] = (schedule.ALLDAY == "Y" ? true : false);
	
	if(viewmode == "new"){
		if(schedule.ALLDAY == "N"){
			if($("#start_date").val() != $("#end_date").val()){
				schedule_event["allDay"] = true;
				schedule_event["longDay"] = true;
			}
			schedule_event["start"] = $.fullCalendar.parseDate( $("#start_date").val() + " " +$("#start_hour_box").val() + ":" + $("#start_minute_box").val());
			schedule_event["end"] = $.fullCalendar.parseDate( $("#end_date").val() + " " +$("#end_hour_box").val() + ":" + $("#end_minute_box").val());
		}else{
			schedule_event["start"] = $.fullCalendar.parseDate( $("#start_date").val());
			schedule_event["longDay"] = false;
		}		
	}else{
		if(schedule.ALLDAY == "Y"){
			var start = new Date();
			start.setTime(schedule.START_SCHEDULE);
			schedule_event["start"] = start;
			schedule_event["longDay"] = false;
			
		}else{
			var start = new Date();
			var end = new Date();
			start.setTime(schedule.START_SCHEDULE);
			end.setTime(schedule.END_SCHEDULE);
			
			var start_date = $.fullCalendar.formatDate(start, 'yy-MM-dd');
			var end_date = $.fullCalendar.formatDate(end, 'yy-MM-dd');
			if(start_date != end_date){
				schedule_event["allDay"] = true;
				schedule_event["longDay"] = true;
			}
			
			schedule_event["start"] = start;
			schedule_event["end"] = end;
		}
	}
	
	
	$('#calendar').fullCalendar('renderEvent', schedule_event);
};