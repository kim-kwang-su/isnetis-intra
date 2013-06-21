<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="이슈관리"/>
<c:set var="two_depth" value="QA"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="issue_01.isnet">이슈관리</a> / <strong>QA</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<table id="req_list"></table>
			<div id="req_pager"></div>
			
			<!-- 상세정보 보기 팝업 -->
			<div id="dialog-view-request" class="issue-dialog" title="테스트 요청 보기">
				<form>
					<input type="hidden" id="view_request_no"/>
					<p>
						<label for="view_module_name">모듈명</label>
						<input type="text" id="view_module_name" readonly="readonly"  style="width: 400px;">
					</p>
					<p>
						<label for="view_module_file">모듈첨부</label>
						<span id="download_link1"></span>
					</p>
					<p class="mt10">
						<label for="view_modify_summary">수정내용</label>
						<textarea id="view_modify_summary" readonly="readonly" style="height: 100px; width: 500px;"></textarea>
					</p>
					<p class="mt10">
						<label for="view_effect_summary">영향도</label>
						<textarea id="view_effect_summary" readonly="readonly" style="height: 100px; width: 500px;"></textarea>
					</p>
					<div id="box1" style="display:none;">
					<p class="mt10">
						<label for="view_test_summary">테스트내용</label>
						<textarea id="view_test_summary" readonly="readonly" style="height: 100px; width: 500px;"></textarea>
					</p>
					<p>
						<label for="view_result_file">결과첨부</label>
						<span id="download_link2"></span>
					</p>
					</div>
				</form>
			</div>
			
			<!-- 테스트 결과 등록 팝업 -->
			<div id="dialog-update-request" class="issue-dialog" title="테스트 결과 등록">
				<form method="post" action="modify_issue02.isnet" enctype="multipart/form-data">
					<input type="hidden" id="update_request_no" name="REQUEST_NO" value=""/>
					<p >
						<label for="update_test_result_box">Test 결과</label>
						<select id="update_test_result_box" name="TEST_RESULT">
							<option value="">-- 선택하세요 --</option>
							<option value="실패">실패</option>
							<option value="오류">오류</option>
							<option value="진행중">진행중</option>
							<option value="완료">완료</option>
						</select>
					</p>
					<p class="mt5">
						<label for="update_test_summary">Test 내용</label>
						<textarea id="update_test_summary" name="TEST_SUMMARY" style="height: 150px; width: 500px;"></textarea>
					</p>
					<p class="mt10">
						<label for="result_file">첨부 파일</label>
						<input type="file" id="result_file" name="RESULT_FILE"/>
					</p>
				</form>
			</div>
			
			<!-- 버그 리포트 등록 팝업 -->
			<div id="dialog-add-report" class="issue-dialog" title="버그 리포트 등록">
				<form method="POST" action="add_issue_06.isnet" enctype="multipart/form-data">
					<input type="hidden" id="report_request_no" name="REQUEST_NO" value=""/>
					<p>
						<label for="report_bug_summary">버그 요약</label>
						<textarea style="height: 150px; width: 700px;" id="report_bug_summary" name="BUG_SUMMARY"></textarea>
					</p>
					<p class="mt10">
						<label for="report_file">첨부 파일</label>
						<input type="file" id="report_file" name="REPORT_FILE"/>
					</p>
					<p class="mt5">
						<label for="report_qa_date">QA 일자</label>
						<input type="text" id="report_qa_date" name="QA_DATE" readonly="readonly"/>
						<label for="report_test_count">Test Count</label>
						<input type="text" id="report_test_count" name="TEST_COUNT" value="1" /><span>&nbsp;(회)</span>
					</p>
					<p>
						<label for="report_bug_frequency_box">버그빈도</label>
						<select style="width: 150px;" id="report_bug_frequency_box" name="BUG_FREQUENCY">
							<option value="항상">항상</option>
							<option value="자주" selected="selected">자주</option>
							<option value="가끔">가끔</option>
						</select>
						<label for="report_bug_importance_box">중요도</label>
						<select style="width: 150px;" id="report_bug_importance_box" name="BUG_IMPORTANCE">
							<option value="심각">심각</option>
							<option value="중요">중요</option>
							<option value="보통" selected="selected">보통</option>
						</select>
						<label for="report_resolve_yn_box">해결 여부</label>
						<select style="width: 150px;" id="report_resolve_yn_box" name="RESOLVE_YN">
							<option value="Y" selected="selected">완결</option>
							<option value="N">미결</option>
						</select>
					</p>
				</form>
			</div>
			
			<!-- 테스트요청 등록 팝업 -->
			<div id="dialog-add-request" class="issue-dialog" title="QA 테스트 요청">
				<form method="post" action="add_issue_05.isnet" enctype="multipart/form-data">
					<input type="hidden" id="qa_issue_no" name="ISSUE_NO" value="0"/>
					<p>
						<label for="qa_module_name">모듈명</label>
						<input type="text" id="qa_module_name" name="MODULE_NAME" style="width: 400px;">
					</p>
					<p>
						<label for="qa_module_file">모듈첨부</label>
						<input type="file" id="qa_module_file" name="MODULE_FILE">
					</p>
					<p class="mt10">
						<label for="qa_modify_summary">수정내용</label>
						<textarea id="qa_modify_summary" name="MODIFY_SUMMARY" style="height: 160px; width: 500px;"></textarea>
					</p>
					<p class="mt10">
						<label for="qa_effect_summary">영향도</label>
						<textarea id="qa_effect_summary" name="EFFECT_SUMMARY" style="height: 160px; width: 500px;"></textarea>
					</p>
				</form>
			</div>
			
			<!-- 버그 리포트 보기 팝업 -->
			<div id="dialog-view-report" title="QA Process(Bug Tracking Process)">
				<span id="bug_module_name" style="display: block; float: left;"></span>
				<span id="bug_test_count" style="display: block; float: right;"></span>
				<table style='width: 780px; clear: both;'>
					<tr>
						<td class='summary' colspan='6'><textarea id="bug_bug_summary" style="width: 95%; height: 95%;" readonly="readonly"></textarea></td>
					</tr>
					<tr>
						<th>Bug 빈도</th><th>중요도</th><th>QA 일자</th><th>개발 담당자</th><th>통보 일자</th><th>해결여부</th>
					</tr>
					<tr>
						<td><span id="bug_bug_frequency"></span></td>
						<td><span id="bug_bug_importance"></span></td>
						<td><span id="bug_qa_date"></span></td>
						<td><span id="bug_employee_name"></span></td>
						<td><span id="bug_notice_date"></span></td>
						<td><span id="bug_resolve_yn"></span></td>
					</tr>
					<tr class="mt5">
						<th>첨부파일</th><td colspan="5" style="text-align: left; border: 0px; padding-left: 10px;"><span id="download_link2"></span></td>
					</tr>
				</table>
			</div>
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->

<script src="/common/jquery-fileuploader/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	var developer_categories = '${developerValues}';
	var result_categories = '실패:실패;오류:오류;진행중:진행중;완료:완료';
	
	$("#report_qa_date").val($.datepicker.formatDate('yy-mm-dd', new Date()));
	$("#report_qa_date").mousedown(function(){
		setDatepicker("#report_qa_date", null, null);
	});
	
	$("#req_list span[id^='report_']").live("click", function(event){
		event.preventDefault();
		var request_no = $(this).attr("id").replace("report_", "");
		$.ajax({
			type:"GET",
			url:"detail_issue02.isnet",
			dataType:"json",
			data:{REQUEST_NO:request_no},
			success:function(data, status){
				if(data && data.isSuccess){
					var report = data.bugReport;
					$("#bug_module_name").html("<strong>모듈명 : </strong>" + report.MODULE_NAME);
					$("#bug_test_count").html("<strong>Test Count : </strong>" + report.TEST_COUNT + " 회");
					$("#bug_bug_summary").val(report.BUG_SUMMARY);
					$("#bug_bug_frequency").text(report.BUG_FREQUENCY);
					$("#bug_bug_importance").text(report.BUG_IMPORTANCE);
					$("#bug_qa_date").text(report.QA_DATE);
					$("#bug_employee_name").text(report.EMPLOYEE_NAME);
					$("#bug_notice_date").text(report.NOTICE_DATE);
					$("#bug_resolve_yn").text(report.RESOLVE_YN);
					if(report["FILE_NAME"]){
						$("#download_link2").html("<strong><a href='download_file.isnet?no="+report['REQUEST_NO']+"&gubun=report'>"+report["FILE_NAME"].substring(14)+"</a></strong>");
					}else{
						$("#download_link2").html("<strong>없음</strong>");
					}
					$("#dialog-view-report").dialog("open");
				}else{
					simple_popup("안내", "버그 리포트 조회 중 오류가 발생하였습니다.");
				}
			},
			error:function(){
				simple_popup("안내", "버그 리포트 조회 중 오류가 발생하였습니다.");
			}
		});
	});
	
	// QA 테스트 요청 팝업 
	$("#dialog-add-request").dialog({
		autoOpen:false,
		height:570,
		width:700,
		resizable:false,
		modal:true,
		buttons:[
			{
				text:"요청",
				click:function(event){
					event.preventDefault();
					// 입력값 체크
					if($("#qa_module_name").val() == ""){
						simple_popup("안내", "모듈명을 입력하세요.");
						$("#qa_module_name").focus();
						return false;
					}
					/* if($("#qa_module_file").val() == ""){
						simple_popup("안내", "테스트할 모듈을 첨부하세요.");
						$("#qa_module_file").focus();
						return false;
					} */
					if($("#qa_modify_summary").val() == ""){
						simple_popup("안내", "수정내용을 입력하세요.");
						$("#qa_modify_summary").focus();
						return false;
					}
					if($("#qa_effect_summary").val() == ""){
						simple_popup("안내", "영향도를 입력하세요.");
						$("#qa_effect_summary").focus();
						return false;
					}
					
					$(this).dialog("close");
					
					// 서버로 전송합니다.
					$("#dialog-add-request form").ajaxSubmit(function(data, state){
						$("#dialog-add-request").dialog("close");
					    data = data.replace(/[<][^>]*[>]/gi, '');
					    var jData = JSON.parse(data);
						if(jData && jData.isSuccess){
							simple_popup("안내", "QA 테스트 요청이 등록되었습니다.");
							$("#req_list").trigger("reloadGrid");
						}else{
							simple_popup("안내", "QA 테스트 요청 등록 중 오류가 발생하였습니다.");
						}
					});
				}
			},
			{
				text:"닫기",
				click:function(event){
					event.preventDefault();
					$(this).dialog("close");
				}
			}
		]
	});
	
	
	// 테스트요청 내용 보기 팝업
	$("#dialog-view-request").dialog({
		autoOpen:false,
		height:500,
		width:700,
		resizable:false,
		modal:true,
		open:function(){
			var request_no = $("#view_request_no").val();
			$.ajax({
				type:"GET",
				url:"detail_issue01.isnet",
				dataType:"json",
				data:{REQUEST_NO:request_no},
				success:function(data, status){
					if(data && data.isSuccess){
						var requestInfo = data.requestInfo;
						
						$("#view_module_name").val(requestInfo["MODULE_NAME"]);
						if(requestInfo["MODULE_FILE_NAME"]){
				   			$("#download_link1").html("<strong><a href='download_file.isnet?no="+requestInfo['REQUEST_NO']+"&gubun=module'>"+requestInfo["MODULE_FILE_NAME"].substring(14)+"</a></strong>");
						}else{
							$("#download_link1").html("");
						}
				   		$("#view_modify_summary").val(requestInfo["MODIFY_SUMMARY"]);
				   		$("#view_effect_summary").val(requestInfo['EFFECT_SUMMARY']);
						
						if(requestInfo.TEST_RESULT){
							if(requestInfo["RESULT_FILE_NAME"]){
					   			$("#download_link2").html("<strong><a href='download_file.isnet?no="+requestInfo['REQUEST_NO']+"&gubun=result'>"+requestInfo["RESULT_FILE_NAME"].substring(14)+"</a></strong>");
							}else{
								$("#download_link2").html("");
							}
							$("#box1").show();
							$("#view_test_summary").val(requestInfo["TEST_SUMMARY"]);
							$("#dialog-view-request").dialog({height:570}).parent().position({my:"center", at:"center", of:window});
						}else{
							$("#box1").hide();
							$("#dialog-view-request").dialog({height:450}).parent().position({my:"center", at:"center", of:window});
							$("#view_test_summary").val("");
						}
					}else{
						simple_popup("안내", "조회 중 오류가 발생하였습니다.");
						$("#dialog-view-request").dialog("close");
					}
				},
				error:function(){
					simple_popup("안내", "조회 중 오류가 발생하였습니다.");
					$("#dialog-view-request").dialog("close");
				}
			});
		},
		buttons:[{
			text:"닫기",
			click:function(){
				$(this).dialog("close");
			}
		}]
	});
	
	// 버그리포트 등록 팝업
	$("#dialog-add-report").dialog({
		autoOpen:false,
		height:410,
		width:900,
		resizable:false,
		modal:true,
		open:function(){
			
			$("#report_bug_frequency_box option :eq(1)").attr("selected", "selected ");
			$("#report_bug_importance_box option :eq(1)").attr("selected", "selected ");
			$("#report_resolve_yn_box option :eq(0)").attr("selected", "selected ");
			$("#report_bug_summary").val("");
			$("#report_test_count").val("1").trigger("focusin");
			
		},
		close:function(){
			$("#report_qa_date").datepicker( "destroy" );
		},
		buttons:[
			{
				text:"등록",
				click:function(){
					// 입력값 체크
					if($("#report_test_count").val() == ""){
						simple_popup("안내", "테스트횟수를 입력하세요.");
						$("#report_test_count").focus();
						return false;
					}
					if($("#report_bug_summary").val() == ""){
						simple_popup("안내", "버그내용을 입력하세요.");
						$("#report_bug_summary").focus();
						return false;
					}
					$(this).dialog("close");
					
					$("#dialog-add-report form").ajaxSubmit(function(data, state){
					    data = data.replace(/[<][^>]*[>]/gi, '');
					    var jData = JSON.parse(data);
						if(jData && jData.isSuccess){
							simple_popup("안내", "버그리포트가 등록되었습니다.");
							$("#req_list").trigger("reloadGrid");
						}else{
							simple_popup("안내", "버그리포트 작성 중 오류가 발생하였습니다.");
						}
					});

					/* $.ajax({
						type:"POST",
						url:"add_issue_06.isnet",
						dataType:"json",
						data:$("#dialog-add-report form").serialize(),
						success:function(data, status){
							if(data && data.isSuccess){
								simple_popup("안내", "버그리포트가 등록되었습니다.");
								$("#req_list").trigger("reloadGrid");
							}else{
								simple_popup("안내", "버그리포트 작성 중 오류가 발생하였습니다.");
							}
						},
						error:function(){
							simple_popup("안내", "버그리포트 작성 중 오류가 발생하였습니다.");
						}
					}); */
				}
			},
			{
				text:"취소",
				click:function(){
					$(this).dialog("close");
				}
			}
		]
	});
	
	// 테스트 결과 등록 팝업
	$("#dialog-update-request").dialog({
		autoOpen:false,
		height:380,
		width:700,
		resizable:false,
		modal:true,
		open:function(){
			$("#update_test_summary").val("");
			$("#update_test_result_box option :eq(0)").attr("selected", "selected");
		},
		buttons:[{
				text:"등록",
				click:function(){
					if($("#update_test_result_box").val() == ""){
						simple_popup("안내", "Test 결과를 선택하세요.");
						$("#update_test_result_box").focus();
						return false;
					}
					if($("#update_test_summary").val() == ""){
						simple_popup("안내", "Test 내용을 입력하세요.");
						$("#update_test_summary").focus();
						return false;
					}
					
					$(this).dialog("close");
					/* $.ajax({
						type:"POST",
						url:"modify_issue02.isnet",
						dataType:"json",
						data:$("#dialog-update-request form").serialize(),
						success:function(data, status){
							if(data && data.isSuccess){
								simple_popup("안내", "테스트 결과가 등록되었습니다.");
								$("#req_list").trigger("reloadGrid");
							}else{
								simple_popup("안내", "테스트 결과 등록 중 오류가 발생하였습니다.");
							}
						},
						error:function(){
							simple_popup("안내", "테스트 결과 등록 중 오류가 발생하였습니다.");
						}
					}); */
					$("#dialog-update-request form").ajaxSubmit(function(data, state){
					    data = data.replace(/[<][^>]*[>]/gi, '');
					    var jData = JSON.parse(data);
						if(jData && jData.isSuccess){
							simple_popup("안내", "테스트 결과가 등록되었습니다.");
							$("#req_list").trigger("reloadGrid");
						}else{
							simple_popup("안내", "테스트 결과 등록 중 오류가 발생하였습니다.");
						}
					});
				}
			},
		  	{
				text:"닫기",
				click:function(){
					$(this).dialog("close");
				}
		}]
	});
	
	// 버그 리포트 보기
	$("#dialog-view-report").dialog({
		autoOpen:false,
		height:520,
		width:810,
		resizable:false,
		modal:true,
		buttons:[
			{
				text:"닫기",
				click:function(){
					$(this).dialog("close");
				}
			}
		]
	});
	
	$("#req_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:$(window).height() - 250,
		mtype:"POST",
		datatype: "json",
		url:"list_04.isnet",
	   	colNames:['REQUEST_NO', 'ISSUE_NO', '요청번호', '요청날짜', '모듈명', '요청자', '수정내용', '결과', '테스트날짜', '버그리포트'],						
	   	colModel:[	
			{name:'REQUEST_NO', index:'REQUEST_NO', hidden:true},
			{name:'ISSUE_NO', index:'ISSUE_NO', hidden:true},
			{name:'REQUEST_NO2', index:'REQUEST_NO2', width:90, align:"center", sortable:false, search:false},
	   		{name:'REQUEST_DATE',index:'REQUEST_DATE', width:90, align:"center", sortable:false, search:false},
	   		{name:'MODULE_NAME',index:'MODULE_NO', width:200, align:"center", sortable:false, search:true, searchoptions:{sopt:['eq']}},
	   		{name:"EMPLOYEE_NAME",index:'DEVELOPER_NO', width:80, align:"center", sortable:false, search:true , stype:'select', searchoptions:{sopt:['eq'], value:developer_categories}},
	   		{name:'MODIFY_SUMMARY',index:'MODIFY_SUMMARY', width:430, align:"left", sortable:false, search:false },
	   		{name:'TEST_RESULT', index:'TEST_RESULT', width:110, align:"center", sortable:false, search:true, stype:'select', searchoptions:{sopt:['eq'], value:result_categories}},
	   		{name:'TEST_DATE', index:'TEST_DATE', width:80, align:"center", sortable:false, search:false},
	   		{name:'LINK', index:'LINK', width:80, align:"center", sortable:false, search:false}
	   	],	
	    viewrecords: true,
	    // 제목
	    caption:"QA Request List",
	    // 페이지 네비게이션 설정
	    pager:"req_pager",
	   	page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="20"/>',
	   	rowList:[20,40,60],
	   	sortname:'<c:out value="${param.sidx}"/>',
	   	sortorder:'<c:out value="${param.sord}"/>',	   	
	   	beforeRequest : function(){
	   		
	   	},
	   	beforeProcessing: function(data, status, xhr){
	   		if(data && data.isSuccess){
	    		$.each(data.rows, function(index, row){
	    			row["REQUEST_NO2"] = row["REQUEST_NO"];
	    			var is_reported = row["IS_REPORTED"];
	    			if(is_reported == "Y"){
	    				row["LINK"] = "<span id='report_"+row["REQUEST_NO"]+"' style='cursor:pointer;' title='버그 리포트 보기'>보기</span>";
	    			}
	    		});
	    	}
	   	},
	   	ondblClickRow:function(rowid, iRow, iCol, e){
	   		$("#view_request_no").val(rowid);
	   		$("#dialog-view-request").dialog("open");
	   	},
		loadComplete:function(data){
	   		var records = data["records"];
	   		if(records == 0){
	   			simple_popup("안내", "검색조건에 해당하는 데이터가 존재하지 않습니다.");
	   		}
	   	},
	    loadError:function(xhr, status, error){
	    	alert("QA 테스트 요청 목록 조회 중 오류가 발생하였습니다.");
	    }
	}).navGrid("#req_pager",{edit:false,add:false,del:false}, {}, {}, {}, {multipleSearch:false, closeOnEscape:true, closeAfterSearch:true}, {})
	.navButtonAdd('#req_pager',
		{
			caption:"테스트 요청", 
		 	buttonicon:"ui-icon-circle-plus", 
		 	onClickButton: function(){ 
			 	$("#dialog-add-request").dialog("open");
		 	}, 
	 		id: "add_req_bt"
	 	}
	)
	.navButtonAdd('#req_pager',
		{
			caption:"테스트결과 등록", 
		 	buttonicon:"ui-icon-check", 
		 	onClickButton: function(){ 
		 		var rowid = $("#req_list").jqGrid("getGridParam", "selrow");
		 		if(rowid){
			 		$("#update_request_no").val(rowid);
			 		$("#dialog-update-request").dialog("open");
		 		}else{
					simple_popup("안내", "테스트결과를 등록할 행을 선택하세요.");		 			
		 		}
		 	}, 
	 		id: "add_test_bt"
	 	}
	).navButtonAdd('#req_pager',
		{
			caption:"버그리포트 등록", 
		 	buttonicon:"ui-icon-document", 
		 	onClickButton: function(){
		 		var rowid = $("#req_list").jqGrid("getGridParam", "selrow");
		 		if(rowid){
		 			var rowData = $("#req_list").jqGrid("getRowData", rowid);
		 			if(rowData['TEST_RESULT'] && (rowData['TEST_RESULT'] == '오류' || rowData['TEST_RESULT'] == '실패')){
		 				$("#report_request_no").val(rowid);
				 		$("#dialog-add-report").dialog("open");
		 			}else{
		 				simple_popup("안내", "선택한 행은 버그리포트를 등록할 수 없습니다.");	
		 			}
			 		
		 		}else{
					simple_popup("안내", "버그리포트를 등록할 행을 선택하세요.");		 			
		 		}
		 	}, 
	 		id: "add_bug_bt"
 	}
);	
});
</script>		
<%@ include file="../common/bottom.jsp" %>