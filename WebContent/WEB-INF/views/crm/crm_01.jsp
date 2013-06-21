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
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="crm_00.isnet">CRM관리</a> / <a href="crm_01.isnet">고객 관리</a> / <strong>고객 리스트</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<table id="crm_list"></table>
			<div id="crm_pager"></div>
			
			
			<div id="dialog-add-family" class="crm-dialog" title="가족정보 등록">
				<div id="searchbox" style="padding:5px 30px;margin:10px 0 5px 0;border:1px solid #cecece;">
					<strong>가족관계 : </strong>
					<select id="family_gubun_box" class="mr20">
						<option value="배우자" selected="selected">배우자</option>
						<option value="자녀">자녀</option>
						<option value="아버지">아버지</option>
						<option value="어머니">어머니</option>
						<option value="형제">형제</option>
					</select> 
	
					<button style="padding:0px 5px" class="icon mr20" id="add_family_box">추가</button>
				</div>
				
				<div id="crm_add_popup_form" class="crm_popup_form">
				</div>
			</div>
			
			<div id="dialog-view-family" class="crm-dialog" title="가족정보 조회 및 수정">
				<div id="crm_view_popup_form" class="crm_popup_form">
				</div>
			</div>
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){	
	
	var company_categories = '${companyValues}';
	
	// 가족정보 등록 팝업
	$("#dialog-add-family").dialog({
		autoOpen: false,
		height:465,
		width: 900,
		resizable: false,
		modal: true,
		open:function(){
			$("#crm_add_popup_form").html("");
		},
		buttons:[
			{
			text:"저장",
			click:function(){
				var isSpaceName = false;
				$("input[name='FAMILY_NAME']").each(function(index, field){
					var field_value = $(field).val();
					if(field_value == ""){
						isSpaceName = true;
					}
				});
				if(isSpaceName){
					simple_popup("안내", "이름이 입력되지 않은 가족정보가 존재합니다.");
					return false;
				}
				
				$("#dialog-add-family form").each(function(index, form){
					$.ajax({
						type:"POST",
						url:"add_crm_04.isnet",
						dataType:"json",
						data:$(form).serialize(),
						success:function(data, status){
							if(data && data.isSuccess){
								$(form).remove();
							}else{
								simple_popup("안내", "가족정보 등록 중 오류가 발생하였습니다.");
							}
						},
						error:function(){
							simple_popup("안내", "가족정보 등록 중 오류가 발생하였습니다.");
						}
					});
				});
			}
		},
		{
			text:"취소",
			click:function(){
				$(this).dialog("close");
				$("#crm_add_popup_form").html("");
			}
		}]
	});	
	
	// 가족정보 조회 변경 팝업
	$("#dialog-view-family").dialog({
		autoOpen: false,
		height:465,
		width: 900,
		resizable: false,
		modal: true,
		open:function(){
			$("#crm_view_popup_form").html("");
			var rowid = $("#crm_list").jqGrid("getGridParam", "selrow");
			// 고객의 가족 정보를 서버에서 조회한다.
			$.ajax({
				type:"GET",
				url:"detail_crm_02.isnet",
				dataType:"json",
				data:{CUSTOMER_NO:rowid},
				success:function(data, status){
					if(data && data.isSuccess){
						var familyList = data["familyList"];
						if(familyList.length == 0){
							$("#dialog-view-family").dialog("close");
							simple_popup("안내", "등록된 가족정보가 없습니다.");
						}else{
							$.each(familyList, function(index, family){
								var html = add_family_box(rowid, family["CUSTOMER_NO"], family["CUSTOMER_GUBUN"]);
								$("#crm_view_popup_form").append(html);
								
								$("#family_no_"+family["CUSTOMER_NO"]+" :input[name='CUSTOMER_NAME']").val(family["CUSTOMER_NAME"]);
								$("#family_no_"+family["CUSTOMER_NO"]+" :input[name='CUSTOMER_JOB']").val(family["CUSTOMER_JOB"]);
								$("#family_no_"+family["CUSTOMER_NO"]+" :input[name='CUSTOMER_AGE']").val(family["CUSTOMER_AGE"]);
								$("#family_no_"+family["CUSTOMER_NO"]+" :input[name='CUSTOMER_BIRTH']").val(family["CUSTOMER_BIRTH"]);
								if(family["IS_LUNAR_DATE"] == "Y"){
									$("#family_no_"+family["CUSTOMER_NO"]+" :input[name='IS_LUNAR_DATE']").attr("checked", "checked");
								}
								if(family["IS_LEAP_MONTH"] == "Y"){
									$("#family_no_"+family["CUSTOMER_NO"]+" :input[name='IS_LEAP_MONTH']").attr("checked", "checked");
								}
								
								// 수정버튼 클릭
								$("button[id^=modify_bt]")
								.button({
									icons: {
										primary: "ui-icon-pencil"
									},
									text: false
								}).click(function(event){
									event.preventDefault();
									// 고객 정보를 수정합니다.
									var family_no = $(this).attr("id").replace("modify_bt_", "");
									$.ajax({
										type:"POST",
										url:"update_crm_04.isnet",
										dataType:"json",
										data:$("#dialog-view-family #family_no_"+family_no).serialize(),
										success:function(data, status){
											if(data && data.isSuccess){
												simple_popup("안내", "가족정보가 수정되었습니다.");
											}else{
												simple_popup("안내", "가족정보 수정 중 오류가 발생하였습니다.");
											}
										},
										error:function(){
											simple_popup("안내", "가족정보 수정 중 오류가 발생하였습니다.");
										}
									});
								}); 
			
								// 삭제버튼 클릭
								$("button[id^=del_bt]")
								.button({
									icons: {
										primary: "ui-icon-trash"
									},
									text: false
								}).click(function(event){
									event.preventDefault();
									// 고객 정보를 삭제합니다.
									var family_no = $(this).attr("id").replace("del_bt_", "");
									$.ajax({
										type:"GET",
										url:"del_crm_03.isnet",
										dataType:"json",
										data:{CUSTOMER_NO:family_no},
										success:function(data, status){
											if(data && data.isSuccess){
												simple_popup("안내", "가족정보가 삭제되었습니다.");
											}else{
												simple_popup("안내", "가족정보 삭제 중 오류가 발생하였습니다.");
											}
										},
										error:function(){
											simple_popup("안내", "가족정보 삭제 중 오류가 발생하였습니다.");
										}
									});
									$(this).parents("form").remove();
								});
							});
						}
					}else{
						simple_popup("안내", "가족정보 조회 중 오류가 발생하였습니다.");
					}
				}, 
				error:function(){
					simple_popup("안내", "가족정보 조회 중 오류가 발생하였습니다.");
				}
			});
			
		},
		buttons:[
		/*{
		    text:"수정",
		    click:function(){
				var isSpaceName = false;
				$("input[name='FAMILY_NAME']").each(function(index, field){
					var field_value = $(field).val();
					if(field_value == ""){
						isSpaceName = true;
					}
				});
				if(isSpaceName){
					simple_popup("안내", "이름이 입력되지 않은 가족정보가 존재합니다.");
					return false;
				}
				
				$("#dialog-view-family form").each(function(index, form){
					$.ajax({
						type:"POST",
						url:"add_crm_04.isnet",
						dataType:"json",
						data:$(form).serialize(),
						success:function(data, status){
							if(data && data.isSuccess){
								$(form).remove();
							}else{
								simple_popup("안내", "가족정보 등록 중 오류가 발생하였습니다.");
							}
						},
						error:function(){
							simple_popup("안내", "가족정보 등록 중 오류가 발생하였습니다.");
						}
					});
				});
			}
		}, */
		{
		 	text:"닫기",
		 	click:function(){
				$(this).dialog("close");
				$("#crm_view_popup_form").html("");
			}
		}]
	});	
	
	$("#crm_list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:$(window).height() - 250,
		mtype:"POST",
		datatype: "json",
		url:"list_crm_01.isnet",
	   	colNames:['CUSTOMER_NO','고객명', '고객사', '부서','직위','사무실','핸드폰', '팩스', '이메일', '마지막방문일'],						
	   	colModel:[	
			{name:'CUSTOMER_NO', index:'CUSTOMER_NO', hidden:true},	   	          		
	   		{name:'CUSTOMER_NAME',index:'CUSTOMER_NAME', width:90, align:"center", sortable:true, search:true, searchoptions:{sopt:['eq','cn']}},
	   		{name:'COMPANY_NAME',index:'B.COMPANY_NO', width:180, align:"center", sortable:true, search:true, stype:'select', searchoptions:{sopt:['eq'], value:company_categories}},
	   		{name:'CUSTOMER_DEPT',index:'CUSTOMER_DEPT', width:160, align:"center",sortable:true, search:true, searchoptions:{sopt:['eq', 'cm']}},
	   		{name:'CUSTOMER_POSITION',index:'CUSTOMER_POSITION', width:85, align:"center", sortable:true, search:true, searchoptions:{sopt:[ 'eq','cn']}},
	   		{name:'CUSTOMER_COMPANY_TEL',index:'CUSTOMER_COMPANY_TEL', width:120, align:"center", sortable:true, search:false},
	   		{name:'CUSTOMER_PHONE',index:'CUSTOMER_PHONE', width:120, align:"center", sortable:true, search:false},
	   		{name:'COMPANY_FAX',index:'COMPANY_FAX', width:120, align:"center", sortable:true, search:false},
	   		{name:'CUSTOMER_EMAIL',index:'CUSTOMER_EMAIL', width:180, align:"center", sortable:true, search:false},
	   		{name:'LAST_VISITED_DATE',index:'LAST_VISITED_DATE', width:100, align:"center", sortable:false, search:false}
	   	],	
	    viewrecords: true,
	    // 제목
	    caption:"고객 리스트",
	    // 페이지 네비게이션 설정
	    pager:"crm_pager",
	   	page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="20"/>',
	   	rowList:[20,40,60],
	   	sortname:'<c:out value="${param.sidx}"/>',
	   	sortorder:'<c:out value="${param.sord}"/>',
	   	
	   	ondblClickRow:function(rowid, iRow, iCol, e){
	   		location.href = "detail_crm_01.isnet?CUSTOMER_NO=" + rowid + "&job=view";
	   	},
	    loadError:function(xhr, status, error){
	    	alert("고객 리스트 조회 중 오류가 발생하였습니다.");
	    }
	}).navGrid("#crm_pager",{edit:false,add:false,del:false}, {}, {}, {}, {closeOnEscape:true, closeAfterSearch:true}, {})
	.navButtonAdd('#crm_pager',
		{
			caption:"등록", 
		 	buttonicon:"ui-icon-plus", 
		 	onClickButton: function(){ 
		 		location.href = "crm_02.isnet";
		 	}, 
	 		id: "user_add_bt"
	 	}
	).navButtonAdd('#crm_pager',
		{
			caption:"수정", 
	 		buttonicon:"ui-icon-pencil", 
	 		onClickButton: function(){
	 			var rowid = $("#crm_list").jqGrid("getGridParam", "selrow");
	 			if(rowid){
	 				location.href = "detail_crm_01.isnet?CUSTOMER_NO=" + rowid + "&job=modify";
	 			}else{
	 				simple_popup("안내", "수정할 행을 선택하세요");
	 			}
	 		}, 
 			id: "user_modify_bt"
 		}
	).navButtonAdd('#crm_pager',
		{
			caption:"삭제", 
	 		buttonicon:"ui-icon-trash", 
	 		onClickButton: function(){ 
	 			var rowid = $("#crm_list").jqGrid("getGridParam", "selrow");
	 			if(rowid){
	 				confirm_popup(
	 					"안내",
	 					"선택한 행을 삭제하시겠습니까?",
	 					function(){
	 						$.ajax({
	 							type:"GET",
	 							url:"del_crm_02.isnet",
	 							dataType:"json",
	 							data:{CUSTOMER_NO:rowid},
	 							success:function(data, status){
	 								if(data && data.isSuccess){
	 									simple_popup("안내", "선택한 행이 삭제되었습니다.");
			   							$("#crm_list").jqGrid().trigger("reloadGrid");
	 								}else{
	 									simple_popup("안내", "고객정보 삭제 중 오류가 발생하였습니다.");
	 								}
	 							},
	 							error:function(){
	 								simple_popup("안내", "고객정보 삭제 중 오류가 발생하였습니다.");
	 							}
	 						});
	 						$(this).dialog("close");
	 					},
	 					function(){
	 						$(this).dialog("close");
	 					}
	 				);
	 			}else{
	 				simple_popup("안내", "삭제할 행을 선택하세요");
	 			}
	 		}, 
 			id: "user_del_bt"
 		}
	).navButtonAdd('#crm_pager',
		{
			caption:"가족정보등록", 
	 		buttonicon:"ui-icon-plus", 
	 		onClickButton: function(){
	 			var rowid = $("#crm_list").jqGrid("getGridParam", "selrow");
	 			if(rowid){
	 				$("#dialog-add-family").dialog('open');
	 			}else{
	 				simple_popup("안내", "가족을 등록할 고객을 선택하세요.");
	 			}
	 		}, 
				id: "family_add_bt"
		}
	).navButtonAdd('#crm_pager',
		{
			caption:"가족정보조회", 
 			buttonicon:"ui-icon-pencil", 
 			onClickButton: function(){
 				var rowid = $("#crm_list").jqGrid("getGridParam", "selrow");
 				if(rowid){
 					$("#dialog-view-family").dialog('open');
 				}else{
 					simple_popup("안내", "가족을 등록할 고객을 선택하세요.");
 				}
 			}, 
			id: "family_modify_bt"
		}
	);	
	
	
	var add_family_box = function(customer_no, family_no, family_gubun){
		
		var html = "";
		if(family_no != ""){
			// 가족정보 조회 수정 화면에서 각각의 form을 구분하기 위해 id를 부여한다.
			html += '<form id="family_no_'+family_no+'">';
		}else{
			html += '<form>';
		}
		html += '<input type="hidden" name="REF_CUSTOMER_NO" value="'+customer_no+'"/>';
		html += '<input type="hidden" name="CUSTOMER_NO" value="'+family_no+'"/>';
		html += '<fieldset>';
		html += '<legend>'+family_gubun+'정보</legend>';
		html += '<p>';
		html += '<label for="family_name">이름</label>';
		html += '<input type="text" id="family_name" name="CUSTOMER_NAME" value=""/>';	
		html += '<input type="hidden" id="family_name" name="CUSTOMER_GUBUN" value="'+family_gubun+'"/>';
		if(family_gubun == "자녀"){
			html += '<label for="family_job">학년</label>';
		}else{
			html += '<label for="family_job">직업</label>';
		}
		html += '<input type="text" id="family_job" name="CUSTOMER_JOB" value="" />';								
		html += '</p>';
		html += '<p>';
		html += '<label for="family_age">나이</label>';
		html += '<input id="family_age" name="CUSTOMER_AGE" type="text" />';
		html += '<label for="family_birth">생년월일</label>';
		html += '<input id="family_birth" name="CUSTOMER_BIRTH" type="text" />';	
		html += '<input id="lunar_date" type="checkbox"  name="IS_LUNAR_DATE" value="Y" style="width: 20px; margin-top: 5px"/><label style="display: inline; width: auto;">음력</label>';
		html += '<input id="leap_month" type="checkbox" name="IS_LEAP_MONTH" value="Y" style="width: 20px; margin-top: 5px"/><label style="display: inline; width: auto">윤달</label>';
		
		if(family_no != ""){
			// 가족정보 조회 수정 화면에서 특정인의 정보를 수저 삭제할 수 있는 버튼을 덧붙인다.
			html += '<button class="icon_button" id="modify_bt_'+family_no+'">수정</button>';
			html += '<button class="icon_button ml5" id="del_bt_'+family_no+'">삭제</button>';
		}else{
			html += '<button class="icon_button">삭제</button>';
		}
		html += '</p>';
		html += '</fieldset>';
		html += '</form>';
		
		return html;
	};
	
	// 추가버튼 클릭시
	$("#add_family_box").click(function(event){
		event.preventDefault();		
		if($("#family_gubun_box").val() != ""){
			var family_gubun = $("#family_gubun_box").val();
			var rowid = $("#crm_list").jqGrid("getGridParam", "selrow");
			var html = add_family_box(rowid, "",family_gubun);	
			$("#crm_add_popup_form").append(html);
			
			// 삭제버튼 클릭
			$(".icon_button")
			.button({
				icons: {
					primary: "ui-icon-circle-minus"
				},
				text: false
			}).click(function(event){
				event.preventDefault();
				$(this).parents("form").remove();
			});
		}
	});
	
});
</script>		
<%@ include file="../common/bottom.jsp" %>