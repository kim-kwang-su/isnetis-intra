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
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="issue_01.isnet">이슈관리</a> / <strong>이슈상세정보</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- <h3>모듈 상세 정보</h3> -->
			<div id="detail_form">
			<form>
				<fieldset>
					<legend>이슈 상세정보</legend>
					<p>
						<label for="accept_date">접수일자</label>
						<input id="accept_date" name="" type="text"  disabled="disabled" value="<c:out value='${issueInfo.ACCEPT_DATE }'/>"/>
						<label for="customer_request_date">고객 처리요청일</label>
						<input id="customer_request_date" name="" type="text"  disabled="disabled" value="<c:out value='${issueInfo.CUSTOMER_REQUEST_DATE }'/>"/>
						<label for="expected_complete_date">처리완료 예정일</label>
						<input id="expected_complete_date" name="EXPECTED_COMPLETE_DATE" type="text"  disabled="disabled" value="<c:out value='${issueInfo.EXPECTED_COMPLETE_DATE }'/>" />
					</p>
					<p>
						<label for="expected_developed_date">개발완료 예정일</label>
						<input id="expected_developed_date" name="EXPECTED_DEVELOPED_DATE" type="text"  disabled="disabled" value="<c:out value='${issueInfo.EXPECTED_DEVELOPED_DATE }'/>" />
						<label for="developed_period">개발기간(일)</label>
						<input id="developed_period" name="DEVELOPED_PERIOD" type="text"  disabled="disabled" value="<c:out value='${issueInfo.DEVELOPED_PERIOD }'/>" />
					</p>
					<p>
						<label for="developed_complete_date">개발완료일</label>
						<input id="developed_complete_date" name="DEVELOPED_COMPLETE_DATE" type="text" disabled="disabled" value="<c:out value='${issueInfo.DEVELOPED_COMPLETE_DATE }'/>" />
						<label for="test_complete_date">SE 테스트 완료일</label>
						<input id="test_complete_date" name="" type="text"  disabled="disabled" value="<c:out value='${issueInfo.TEST_COMPLETE_DATE }'/>" />
						<label for="release_date">고객 릴리즈일</label>
						<input id="release_date" name="" type="text"  disabled="disabled" value="<c:out value='${issueInfo.RELEASE_DATE }'/>" />
					</p>
					<p class="mt10">
						<label for="customer_combobox">고객사명</label>
						<select id="customer_combobox" name="" disabled="disabled">
						<c:forEach var="customer" items="${customerList }">
							<option value="${customer.CUSTOMER_NO }">${customer.CUSTOMER_NAME }</option>
						</c:forEach>
					    </select>
						<label for="product_combobox">제품명</label>
						<select id="product_combobox" name="" disabled="disabled">
						<c:forEach var="product" items="${productList }">
							<option value="${product.PRODUCT_NO }">${product.PRODUCT_NAME }</option>
						</c:forEach>
					    </select>
					</p>
					<p>
						<label for="issue_kind_combobox">요청분류</label>
						<select id="issue_kind_combobox" name="" disabled="disabled">
						<option value="">고객요청 분류를 선택하세요</option>
						<c:forEach var="kind" items="${kindList }">
							<option value="${kind.KIND_NO }">${kind.KIND_NAME }</option>
						</c:forEach>
						</select>
						<label for="issue_priority_combobox">우선순위</label>
						<select id="issue_priority_combobox" name="" disabled="disabled">
							<option value="상">상</option>
							<option value="중">중</option>
							<option value="하">하</option>
					    </select>
						<label for="state_combobox">진행상태</label>
						<select id="state_combobox" name="" disabled="disabled">
						<c:forEach var="state" items="${stateList }">
							<option value="${state.STATE_NO }">${state.STATE_NAME }</option>
						</c:forEach>
					    </select>
					</p>
					<p id="developers">
						<label for="employee_name">접수자</label>
						<input id="employee_name" name=""  type="text" disabled="disabled" value="<c:out value='${issueInfo.EMPLOYEE_NAME }'/>"/>
						
						<label for="se_name">담당 SE</label>
						<input id="se_name" name=""  type="text" disabled="disabled" value="<c:out value='${seList[0].EMPLOYEE_NAME }'/>"/>
						
						<label for="employee_name">담당 개발자</label>
						<c:forEach var="developer" items="${developerList }">
							<span class="icon" id="developer_${developer.EMPLOYEE_NO }">${developer.EMPLOYEE_NAME }</span>
						</c:forEach>
						<!-- <button id="add_dev_bt" class="icon_button" disabled="disabled">담당 개발자를 등록하세요.</button>		 -->				
					</p>
					<p>
						<label for="employee_name">첨부파일</label>
						<c:forEach var="file" items="${fileList }">
							<span class="icon"><a href="download_file.isnet?no=${file.FILE_NO }&gubun=issue"><c:out value="${file.FILE_NAME2 }"/></a></span>
						</c:forEach>
						<button id="add_file_bt" class="icon_button" disabled="disabled">첨부파일을 등록하세요</button>
					</p>
					<p class="mt10">
						<label for="issue_memo">접수내용</label>
						<textarea id="issue_memo" name="" rows="15" readonly="readonly"><c:out value="${issueInfo.ISSUE_MEMO }"/></textarea>
					</p>
					<p class="mt5">
						<label for="issue_remark">비고</label>
						<textarea id="issue_remark" name="" rows="5" readonly="readonly"><c:out value="${issueInfo.ISSUE_REMARK }"/></textarea>
					</p>
					<p style="clear:both;">
						<button style="float: right; margin-top: 10px; margin-right: 70px;" id="list_bt" title="최근 이슈 목록으로 이동합니다.">목록</button>
						<button style="float: right; margin-top: 10px; margin-right: 30px;" id="modify_bt" title="수정 페이지로 이동합니다." >수정</button>
					</p>
				</fieldset>
				<fieldset >
					<legend>진행상황</legend>
					<div id="process_box">
						<table id="progress_list"></table>
						<div id="pager"></div>
					</div>
					<p id="button_box" style="clear:both; display: none;" >
						<button style="float: right; margin-top: 3px; margin-right: 70px;" id="progress_deatil_bt" title="선택한 이슈에 대한 상세정보를 표시합니다." >상세보기</button>
					</p>
				</fieldset>
			</form>
			
			</div>		
			
			<div id="dialog-issue-history" class="issue-dialog" title="이슈 진행상황 등록">
				<form>
					<input type="hidden" name="ISSUE_NO" value="${issueInfo.ISSUE_NO }"/>
					<p>
						<label for="state_combobox_popup">진행상태</label>
						<select id="state_combobox_popup" name="STATE_NO" >
						<option value="" selected="selected">진행상태를 선택하세요.</option>
						<c:forEach var="state" items="${stateList }">
							<c:if test="${state.STATE_NO ge issueInfo.STATE_NO }">
								<option value="${state.STATE_NO }">${state.STATE_NAME }</option>
							</c:if>
						</c:forEach>
					    </select>
					</p>
					<div id="box1">
						<p id="release_date_p">
							<label>고객 릴리즈일</label>
							<input type="text" id="release_date_popup" name="RELEASE_DATE" disabled="disabled" />
						</p>
						<p>
							<label>처리내용</label>
							<textarea id="process_memo" name="PROCESS_MEMO" style="height: 120px; width: 500px;"></textarea>
						</p>
					</div>
					<div id="box2" style="display: none">
						<p id="">
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
					
					</div>
				</form>
			</div>
			
			<!-- 담당개발자 등록용 팝업창을 생성합니다. -->
			<%-- <div id="dialog-developers" title="담당 개발자 등록">
				<p class=""><strong>담당 개발자를 지정하세요.</strong></p>
				<form>
				<input type="hidden" name="STATE_NO" id="state_no_popup"/>
				<input type="hidden" name="ISSUE_NO" value="${issueInfo.ISSUE_NO }"/>
				<div id="employee_checkbox">
					<c:forEach var="employee" items="${employeeList }" varStatus="num">
						<c:if test="${employee.DEPT_NO eq 2 or employee.DEPT_NO eq 5 }">
							<input type="checkbox" id="checkbox${num.count }" name="EMPLOYEE_NO" value="${employee.EMPLOYEE_NO }"> <label for="checkbox${num.count }">${employee.EMPLOYEE_NAME }</label>
						</c:if>
					</c:forEach>
				</div>
				</form>
			</div> --%>
			
			<!-- 첨부파일 등록 팝업을 생성합니다. -->
			<div id="dialog-files" title="첨부파일 등록">
				<div id="main_container">
					<form action="add_issue04.isnet" method="post" enctype="multipart/form-data">
						<input type="file" name="file" class="fileUpload" multiple>
						<input type="hidden" name="in" id="hidden_issue_no"/> 
						<button id="px-submit" type="submit">Upload</button>
						<button id="px-clear" type="reset">Clear</button>
					</form>
				</div>
			</div>
			
			<!-- 이슈진행상황 상세정보 보기 팝업 -->
			<div id="dialog-view-detail" class="issue-dialog" title="이슈진행상황 조회 및 수정">
				<form enctype="application/x-www-form-urlencoded">
					<p>
						<label for="detail_popup_update_date">등록일</label>
						<input type="text" id="detail_popup_update_date" readonly="readonly">
					</p>
					<p>
						<label for="detail_popup_state">진행상태</label>
						<input type="text" id="detail_popup_state" readonly="readonly">
					</p>
					<p>
						<label for="detail_popup_memo">처리내용</label>
						<textarea id="detail_popup_memo" style="height: 185px; width: 730px;" readonly="readonly"></textarea>
					</p>
				</form>
			</div>
			
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
<script src="/common/jquery-fileuploader/jquery.form.js" type="text/javascript"></script>		
<script type="text/javascript">
$(document).ready(function(){
	
	var myLocalStorage = {};
	
	// myLocalStorage에 저장된 값과 비교하기
	var isChangedValue = function(key){
		var prev_value = myLocalStorage[key];
		var new_value = $("#"+key).val();
		if(prev_value != new_value){
			return true;
		}else{
			return false;
		}
	};
	
	// 처리완료 예정일, 개발완료 예정일, 개발기간을 변경하는 경우 진행상황내역에 추가한다.
	var updateDateField = function(field_id, param_name, memo){
		
		var param = {
						ISSUE_NO: '${issueInfo.ISSUE_NO}',  
						STATE_NO: $("#state_combobox").val(), 
				 		PROCESS_MEMO: memo
				 	};
		param[param_name] = $(field_id).val();
		
		$.ajax(
				{url:"add_issue03.isnet", 
				 type:"POST",
				 data: param, 
				 dataType: "json",
				 success:function(result){
					if(result && result.isSuccess){
						location.reload(true);						
					}else{
						simple_popup("안내", "이슈 진행상황 등록 중 오류가 발생하였습니다.");
					}
				},
				error:function(){
					simple_popup("안내", "이슈 진행상황 등록 중 오류가 발생하였습니다.");
				}}
		);		
	};
	
	// 로그인 사용자가 해당 이슈의 담당자로 지정된 사용자인지 체크
	// 담당자로 지정된 사용자인 경우에 진행상황등록, 완료예정일, 개발완료예정일, 개발기간등을 변경할 수 있다.
	var setEnableFormfield = function(){
		
		// 로그인된 사원의 사원번호
		var isExist = false;
		
		if('${LOGIN_USER.EMPLOYEE_NO}' == '${seList[0].EMPLOYEE_NO }'){
			isExist = true;
		}		
		$("#developers span.icon").each(function(){
			var no = $(this).attr("id").replace("developer_", "");
			if('${LOGIN_USER.EMPLOYEE_NO}' == no){
				isExist = true;
			}
		});
		
		if(isExist){			
			// 날짜 입력필드 활성화
			$("#expected_complete_date").removeAttr("disabled");
			$("#expected_developed_date").removeAttr("disabled");
			$("#developed_period").removeAttr("disabled");
			
			// 달력 위젯 등록
			setDatepicker("#expected_complete_date", 0, null);
			setDatepicker("#expected_developed_date", 0, null);	
			
			// 진행상황 등록버튼을 활성화시킵니다.
			$("#process_add_bt").show();
			$("#add_file_bt").show();
		}else{
			// 날짜 입력필드 비활성화
			$("#expected_complete_date").attr("disabled", "disabled");
			$("#expected_developed_date").attr("disabled", "disabled");
			$("#developed_period").attr("disabled", "disabled");
			
			// 진행상황 등록버튼을 비활성화시킨다.
			$("#process_add_bt").hide();
			$("#add_file_bt").hide();
		}
		
	};		
	
	// 이슈진행상황 표시
	$("#progress_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:250,
		datatype: "json",
		url:"list_02.isnet?in=${param.in}",
	   	colNames:['ISSUE_HISTORY_NO','등록일', '진행상태', '처리내용','담당자'],						
	   	colModel:[	
			{name:'ISSUE_HISTORY_NO', index:'ISSUE_HISTORY_NO', hidden:true},	   	          		
	   		{name:'REGISTER_DATE',index:'REGISTER_DATE', width:100, align:"center"},
	   		{name:'STATE_NAME',index:'REGISTER_DATE', width:120, align:"center"},
	   		{name:'PROCESS_MEMO',index:'PROCESS_MEMO', width:625, align:"left"},
	   		{name:'EMPLOYEE_NAME',index:'EMPLOYEE_NAME', width:75, align:"center"},
	   	],	
	    pager:"pager",
	   	rowList:[],
	   	pgbuttons:false,
	   	pginput:false,
	   	rowNum:50,
	    loadError:function(xhr, status, error){
	    	alert("최근 이슈 접수내역을 조회 중 오류가 발생하였습니다.");
	    },
	    loadComplete:function(data){
	    	if(data.records > 0){
	    		$("#button_box").show();
	    	}
	    }
	})
	.navGrid('#pager',{edit:false,add:false,del:false,search:false, refresh:false})
	.navButtonAdd('#pager',{
		caption:"등록", 
		buttonicon:"ui-icon-plus", 
		onClickButton: function(){ 
			$("#dialog-issue-history").dialog("open");
		}, 
	 	position:"last",
	 	id: "process_add_bt",
	 	title: "진행상황을 등록합니다."
	});
		
	// combox를 제어합니다.
	$("#customer_combobox option[value='${issueInfo.CUSTOMER_NO }']").attr("selected", "selected");
	$("#product_combobox option[value='${issueInfo.PRODUCT_NO }']").attr("selected", "selected");
	$("#issue_kind_combobox option[value='${issueInfo.KIND_NO }']").attr("selected", "selected");
	$("#issue_priority_combobox option[value='${issueInfo.ISSUE_PRIORITY }']").attr("selected", "selected");
	$("#state_combobox option[value='${issueInfo.STATE_NO }']").attr("selected", "selected");
	
	// 진행상태가 처리완료 상태일때는 비고란을 제외하는 모든 입력필드 잠금.
	if($("#state_combobox :selected").val() != '33' || $("#state_combobox :selected").val() != '34'){
		// 로그인 사용자가 담당 SE나 개발자인 경우 formfield를 활성화한다.
		setEnableFormfield();
		
		// 담당 SE 및 개발자 등록버튼 활성화
		$("#add_dev_bt").removeAttr("disabled");
		$("#add_file_bt").removeAttr("disabled");
	}else{
		// 담당 SE 및 개발자 등록버튼 비활성화
		$("#add_dev_bt").attr("disabled", "disabled");
		$("#add_file_bt").attr("disabled");
		
		// 진행상황 등록버튼 비활성화
		$("#process_add_bt").hide();
	}	
	
	// myLocalStorage에 처리완료 예정일 저장하기
	myLocalStorage["expected_complete_date"] = $("#expected_complete_date").val();
	myLocalStorage["expected_developed_date"] = $("#expected_developed_date").val();
	myLocalStorage["developed_period"] = $("#developed_period").val();
	
	// 처리완료 예정일, 개발완료예정일, 개발기간을 변경하는 경우 진행상황에 내역을 추가합니다.
	$("#expected_complete_date, #expected_developed_date, #developed_period").change(function(event){

		var key = $(this).attr("id");
		var value = $("#"+key).val();
		var msg = "";
		if(key == "expected_complete_date"){
			msg = "처리완료 예정일을 ["+value+"]로 변경하였습니다.";
		}else if(key == "expected_developed_date"){
			msg = "개발완료 예정일을 ["+value+"]로 변경하였습니다.";
		}else if(key == "developed_complete_date"){
			msg = "개발완료 일을 ["+value+"]로 변경하였습니다.";
		}else if(key == "developed_period"){
			msg = "개발기간을 ["+value+"]로 변경하였습니다.";
		}
		
		if(isChangedValue(key)){
			updateDateField("#"+key, key.toUpperCase(), msg);
			myLocalStorage[key] = value;
		}
	});	
	
	// 이슈 진행상황 등록 팝업에서 진행상태로 고객 릴리즈를 선택하는 경우 고객릴리즈일을 입력필드를 표시한다.
	$("#state_combobox_popup").change(function(){
		
		var selected_option = $("#state_combobox_popup :selected").val();
		
		if(selected_option == "23"){ // 개발완료 및 테스트요청 선택시
			
			$("#box1").hide().attr("disabled", "disabled");
			$("#box2").show();
			$("#dialog-issue-history form").attr({method:"POST", action:"add_issue_05.isnet", enctype:"multipart/form-data"});
			$("#dialog-issue-history").dialog({height:600}).parent().position({my:"center", at:"center", of:window});
		}else{			
			$("#box1").show();
			$("#box2").hide();
			$("#dialog-issue-history form").attr({method:"POST", action:"", enctype:"application/x-www-form-urlencoded"});
			// 다이얼로그 창 크기 변경
			$("#dialog-issue-history").dialog({height:330}).parent().position({my:"center", at:"center", of:window});
			
			if(selected_option == "33"){// 처리완료 선택시
				$("#release_date_p").show();
				$("#release_date_popup").removeAttr("disabled");
				
				// 고객 릴리즈 필드에 달력 필드를 등록합니다.
				// 개발완료일이나 SE테스트 완료일이 입력되어 있는 경우, 고객 릴리즈 일자는 동일하거나 그보다 이후이어야 합니다.
				var developed_complete_date = "${issueInfo.DEVELOPED_COMPLETE_DATE}";
				var test_complete_date = '${issueInfo.TEST_COMPLETE_DATE}';
				
				if(!(developed_complete_date || test_complete_date)){
					setDatepicker("#release_date_popup", -7, null);
				}else{
					if(developed_complete_date < test_complete_date){
						setDatepicker("#release_date_popup", test_complete_date, null);
					}else{
						setDatepicker("#release_date_popup", developed_complete_date, null);
					}
				}
			}else{
				$("#release_date_p").hide();
				$("#release_date_popup").attr("disabled", "disabled");
			}		
		}
		/*
		$("#box1").show();
		$("#box2").hide();
		$("#dialog-issue-history form").attr({method:"POST", action:"", enctype:"application/x-www-form-urlencoded"});
		// 다이얼로그 창 크기 변경
		$("#dialog-issue-history").dialog({height:300}).parent().position({my:"center", at:"center", of:window});
		
		if(selected_option == "33"){// 처리완료 선택시
			$("#release_date_p").show();
			$("#release_date_popup").removeAttr("disabled");
			
			// 고객 릴리즈 필드에 달력 필드를 등록합니다.
			// 개발완료일이나 SE테스트 완료일이 입력되어 있는 경우, 고객 릴리즈 일자는 동일하거나 그보다 이후이어야 합니다.
			var developed_complete_date = "${issueInfo.DEVELOPED_COMPLETE_DATE}";
			var test_complete_date = '${issueInfo.TEST_COMPLETE_DATE}';
			
			if(!(developed_complete_date || test_complete_date)){
				setDatepicker("#release_date_popup", -7, null);
			}else{
				if(developed_complete_date < test_complete_date){
					setDatepicker("#release_date_popup", test_complete_date, null);
				}else{
					setDatepicker("#release_date_popup", developed_complete_date, null);
				}
			}
		}else{
			$("#release_date_p").hide();
			$("#release_date_popup").attr("disabled", "disabled");
		}		
		*/
	});
	
	// 이슈 진행상황 등록 팝업
	$("#dialog-issue-history").dialog({
		autoOpen: false,
		height: 300,
		width: 700,
		resizable: false,
		modal: true,
		create: function(){
			$("#release_date_p").hide();
		},
		resize:function(event, ui){
			console.log(ui);
		},
		buttons:{
			"Save": function(event){
				var selected_option = $("#state_combobox_popup :selected").val();
				if(selected_option == "23"){
					event.preventDefault();
					// 입력값 체크
					if($("#qa_module_name").val() == ""){
						simple_popup("안내", "모듈명을 입력하세요.");
						$("#qa_module_name").focus();
						return false;
					}
					// if($("#qa_module_file").val() == ""){
					//	simple_popup("안내", "테스트할 모듈을 첨부하세요.");
					//	$("#qa_module_file").focus();
					//	return false;
					//} 
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
					// 서버로 전송합니다.
					$(this).dialog("close");
					$("#dialog-issue-history form").ajaxSubmit(function(data, state){
					    data = data.replace(/[<][^>]*[>]/gi, '');
					    var jData = JSON.parse(data);
						if(jData && jData.isSuccess){
							location.reload(true);		
						}else{
							simple_popup("안내", "모듈 수정이력 등록 중 오류가 발생하였습니다.");
						}
					});
				}else{
					// 진행상태 체크
					if($("#state_combobox_popup").val() == ""){
						simple_popup("안내", "진행상태를 선택해주세요.");
						$("#state_combobox_popup").focus();
						return;
					}
					
					// 처리내용 체크
					if($.trim($("#process_memo").val()) == ""){
						simple_popup("안내", "처리내용을 입력해주세요");
						$("#process_memo").focus();
						return;
					}
					
					$(this).dialog("close");					
					// 입력된 이슈 진행상황정보를 서버로 전송한다.
					$.ajax({
						type:"post",
						url:"add_issue03.isnet",
						data:$("#dialog-issue-history form").serialize(),
						dataType:"json",
						success:function(result, status){
							if(result && result.isSuccess){
								location.reload(true);						
							}else{
								simple_popup("안내", "이슈 진행상황 등록 중 오류가 발생하였습니다.");
							}
						},
						error:function(){
							simple_popup("안내", "이슈 진행상황 등록 중 오류가 발생하였습니다.");
						}
					});
										
				}
				
				// 진행상태 체크
				/* if($("#state_combobox_popup").val() == ""){
					simple_popup("안내", "진행상태를 선택해주세요.");
					$("#state_combobox_popup").focus();
					return;
				}
				
				// 처리내용 체크
				if($.trim($("#process_memo").val()) == ""){
					simple_popup("안내", "처리내용을 입력해주세요");
					$("#process_memo").focus();
					return;
				}
				
				$(this).dialog("close");					
				// 입력된 이슈 진행상황정보를 서버로 전송한다.
				$.post("add_issue03.isnet", $("#dialog-issue-history form").serialize(), function(result){
					if(result && result.isSuccess){
						location.reload(true);						
					}else{
						simple_popup("안내", "이슈 진행상황 등록 중 오류가 발생하였습니다.");
					}
				});	 */		
				
			},
			Cancel: function(){
				$(this).dialog("close");
			}
		}		
	});
	
	// 담당개발자 등록 팝업 
	/* $("#dialog-developers").dialog({
		autoOpen: false,
		height: 250,
		width: 500,
		resizable: false,
		modal: true,
		buttons: {
			"Save": function(){		
				
				// 진행상태가 고객접수 상태인 경우에만 진행상태를 담당자지정 상태로 변경한다.
				$("#state_no_popup").val($("#state_combobox :selected").val());
				
				// 선택된 담당 SE 및 개발자가 있는지 체크
				var ischecked = false;
				$("#dialog-developers :checkbox").each(function(index, checkbox){
					if(checkbox.checked){
						ischecked = true;
					}
				});
				
				if(ischecked){
					// 담당 개발자 정보 저장
					$.ajax(
						{url: "add_issue02.isnet", 
						 type: "POST",
						 data: $("#dialog-developers").find("form").serialize(), 
						 dataType: "json",
						 success: function(result){
							if(result && result.isSuccess){
								// 담당 개발자등록용 버튼 제거
								$("#developers .icon").remove();
								
								// 등록된 담당개발자명을 화면에 표시합니다.
								$("#dialog-developers :checkbox").each(function(index, checkbox){
									if(checkbox.checked){
										var developername = $(this).next().text();
										$('<span class="icon" id="developer_'+$(this).val()+'" >'+developername+'</span>').insertBefore("#add_dev_bt");
									}
								});
								
								if($("#state_combobox :selected").text() == "고객접수"){
									location.reload(true);
								}else{
									// 사용자가 입력가능하도록 화면의 form field 속성을 변경합니다.
									setEnableFormfield();
								}
							}
						},
						error: function(){
							simple_popup("안내", "담당 개발자 등록 중 오류가 발생하였습니다.");
						}
						});	
					
					$(this).dialog("close");
				}else{
					simple_popup("안내", "담당 개발자를 선택해주세요.");
				}
				
			},
			Cancel: function(){
				$(this).dialog("close");
			}
		}
	});	 */
	
	// 첨부파일 등록
	$("#dialog-files").dialog({
		autoOpen: false,
		height: 470,
		width: 500,
		resizable: false,
		modal: true,
		create:function(){
			$("#hidden_issue_no").val('${param.in}');
			jQuery(function($){
				$('.fileUpload').fileUploader();
			});
		},
		buttons: {
			"닫기": function(){
				$("#dialog-files").dialog("close");
				location.reload(true);
			}
		},
		close: function(){
			location.reload(true);
		}
	}); 
	
	// 진행상황 상세보기 팝업
	$("#dialog-view-detail").dialog({
		autoOpen: false,
		height: 400,
		width: 900,
		resizable: false,
		modal: true,
		
		buttons: {
			"닫기": function(){
				$("#dialog-view-detail").dialog("close");
			}
		}
		
	}); 
	

	// 담당 개발자 등록하기, 첨부파일 등록 버튼 등록
	$("#add_file_bt")
	.button({
		icons: {
			primary: "ui-icon-circle-plus"
		},
		text: false
	})
	.click(function(event){
		event.preventDefault();
		$("#dialog-files").dialog("open");
	});
	/* $("#add_dev_bt, #add_file_bt")
		.button({
			icons: {
				primary: "ui-icon-circle-plus"
			},
			text: false
		})
		.click(function(event){
			event.preventDefault();
			// 담당개발자 등록용 팝업 열기
			if($(this).attr("id") == "add_dev_bt"){
				$("#dialog-developers").dialog("open");
			}else if($(this).attr("id") == "add_file_bt"){
				$("#dialog-files").dialog("open");
			}
	}); */
	
	
	
	// 수정버튼 처리
	if('${issueInfo.EMPLOYEE_NO }' == '${LOGIN_USER.EMPLOYEE_NO}'){
		var today = $.datepicker.formatDate('yy-mm-dd', new Date());
		if(today == '${issueInfo.CREATE_DATE }'){
			$("#modify_bt").removeAttr("disabled");
		}
	}
	
	// 수정화면으로 이동 버튼 등록
	$("#modify_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			
			var page = '<c:out value="${param.page}" default="1"/>';
			var rows = '<c:out value="${param.rows}" default="20"/>';
			var sidx = '<c:out value="${param.sidx}" />';
			var sord = '<c:out value="${param.sord}" />';
			var issue_no = '<c:out value="${param.in}"/>';
		
			location.href = "issue_04.isnet?in="+issue_no+"&page=" + page + "&rows=" + rows  + "&sidx=" + sidx + "&sord=" + sord;		
		});	
	
	// 진행상황 상세보기
	$("#progress_deatil_bt")
	.button()
	.click(function(event){
		event.preventDefault();
		var rowid = $("#progress_list").jqGrid("getGridParam", "selrow");
		if(rowid){
			var rowData = $("#progress_list").jqGrid("getRowData", rowid);
			$("#dialog-view-detail").dialog("open");
			
			$("#detail_popup_update_date").val(rowData["REGISTER_DATE"]);
			$("#detail_popup_state").val(rowData["STATE_NAME"]);
			$("#detail_popup_memo").val(rowData["PROCESS_MEMO"]);
		}else{
			simple_popup("안내","상세정보를 조회할 그리드의 행을 선택하세요");
		}
		
	});	
	
	// 목록으로 이동 버튼 등록
	$("#list_bt")
		.button()
		.click(function(event){
			event.preventDefault();
			
			var page = '<c:out value="${param.page}" default="1"/>';
			var rows = '<c:out value="${param.rows}" default="20"/>';
			var sidx = '<c:out value="${param.sidx}" />';
			var sord = '<c:out value="${param.sord}" />';
			var returnURL = '${param.returnURL}';
			location.href = (returnURL || 'issue_01') + ".isnet?page=" + page + "&rows=" + rows  + "&sidx=" + sidx + "&sord=" + sord + "&from=detail";		
		
	});
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>