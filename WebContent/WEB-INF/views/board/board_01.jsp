<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="정보공유"/>
<c:set var="two_depth" value="FAQ"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="board_01.isnet">정보공유</a> / <strong>FAQ</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<div id="tabs">
				<ul>
					<c:forEach var="product" items="${productList }" varStatus="num">
						<li><a href="#tabs-${num.count}" title="${product.PRODUCT_NAME }">${product.PRODUCT_NAME }</a></li>
					</c:forEach>
				</ul>
				<c:forEach var="product" items="${productList }" varStatus="num">
					<div id="tabs-${num.count }">
						<input type="hidden" name="product_no" value="${product.PRODUCT_NO }"/>
						<table id="list${num.count }"></table>
						<div id="pager${num.count }"></div>
					</div>
				</c:forEach>
			</div>
			
			<!-- FAQ 등록 및 수정 팝업 -->
			<div id="dialog-add-faq" class="faq-dialog" title="FAQ 등록 및 수정">
				<form method="post" action="add_board_01.isnet" enctype="multipart/form-data">
					<input type="hidden" id="add_faq_no" name="FAQ_NO" value=""/>
					<p>
						<label for="add_product_no_box">제품</label>
						<select id="add_product_no_box" name="PRODUCT_NO" class="mr20">
							<c:forEach var="product" items="${productList }">
								<option value="${product.PRODUCT_NO }">${product.PRODUCT_NAME }</option>
							</c:forEach>
						</select> 
					</p>
					<p>
						<label for="add_faq_question">질문</label>
						<textarea id="add_faq_question" name="FAQ_QUESTION" style="height: 75px;"></textarea>
					</p>
					<p>
						<label for="add_faq_answer">답변</label>
						<textarea id="add_faq_answer" name="FAQ_ANSWER" style="height: 125px;"></textarea>
					</p>
					<p>
						<label for="add_faq_file">첨부파일</label>
						<input type="file" id="add_faq_file" name="FAQ_FILE" />
					</p>
				</form>
			</div>	
			
			<!-- FAQ 조회 팝업-->
			<div id="dialog-view-faq" class="faq-dialog" title="FAQ 보기">
				<form>
					<p>
						<label for="view_product_no_box">제품</label>
						<select id="view_product_no_box" name="PRODUCT_NO" class="mr20">
							<c:forEach var="product" items="${productList }">
								<option value="${product.PRODUCT_NO }">${product.PRODUCT_NAME }</option>
							</c:forEach>
						</select> 
					</p>
					<p>
						<label for="view_faq_question">질문</label>
						<textarea id="view_faq_question" name="FAQ_QUESTION" readonly="readonly" style="height: 75px;"></textarea>
					</p>
					<p>
						<label for="view_faq_answer">답변</label>
						<textarea id="view_faq_answer" name="FAQ_ANSWER" readonly="readonly" style="height: 125px;"></textarea>
					</p>
					<p>
						<label for="view_file_name">첨부파일</label>
						<span id="download_link"></span>
					<p>
				</form>
			</div>	
		
			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->

<script src="/common/jquery-fileuploader/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	// faq 조회팝업
	$("#dialog-view-faq").dialog({
		autoOpen: false,
		height:440,
		width: 650,
		resizable: false,
		modal: true,
		open:function(){
			var product_no = $("#tabs-"+($( "#tabs" ).tabs( "option", "active" ) + 1)+ " input[name='product_no']").val();
			$("#view_product_no_box option[value='"+product_no+"']").attr("selected", "selected");
		},
		buttons:[
			{
				text:"닫기",
				click:function(){
					$(this).dialog("close");
				}
			}       
		]
	});
	
	// faq 등록팝업
	$("#dialog-add-faq").dialog({
		autoOpen: false,
		height:440,
		width: 650,
		resizable: false,
		modal: true,
		open:function(){
			var product_no = $("#tabs-"+($( "#tabs" ).tabs( "option", "active" ) + 1)+ " input[name='product_no']").val();
			$("#add_product_no_box option[value='"+product_no+"']").attr("selected", "selected");
		},
		close:function(){
			$("#add_faq_no").val("");
			$("#add_faq_question").val("");
 			$("#add_faq_answer").val("");
 			$("#add_faq_file").val("");
		},
		buttons:[
			{
				text:"저장",
				click:function(){
					if($("#add_faq_question").val() == ""){
						simple_popup("안내", "질문을 입력하세요");
						$("#add_faq_question").focus();
						return false;
					}
					if($("#add_faq_answer").val() == ""){
						simple_popup("안내", "답변을 입력하세요");
						$("#add_faq_answer").focus();
						return false;
					}
					
					if($("#add_faq_no").val() == ""){
						
						$("#dialog-add-faq form").attr("action", "add_board_01.isnet");
						// 서버로 전송합니다.
						$("#dialog-add-faq form").ajaxSubmit(function(data, state){
							$("#dialog-add-faq").dialog("close");
						    data = data.replace(/[<][^>]*[>]/gi, '');
						    var jData = JSON.parse(data);
							if(jData && jData.isSuccess){
								simple_popup("안내", "FAQ가 등록되었습니다.");
								$("#list" + ($( "#tabs" ).tabs( "option", "active" )  + 1)).jqGrid().trigger("reloadGrid");
							}else{
								simple_popup("안내", "FAQ 저장 중 오류가 발생하였습니다.");
							}
						});
						
						/* $.ajax({
							type:"POST",
							url:"add_board_01.isnet",
							dataType:"json",
							data:$("#dialog-add-faq form").serialize(),
							success:function(data, status){
								if(data && data.isSuccess){
									$("#list" + ($( "#tabs" ).tabs( "option", "active" )  + 1)).jqGrid().trigger("reloadGrid");
									simple_popup("안내", "FAQ가 저장되었습니다.");
								}else{
									simple_popup("안내", "FAQ 저장 중 오류가 발생하였습니다.");
								}
							},
							error:function(){
								simple_popup("안내", "FAQ 저장 중 오류가 발생하였습니다.");
							}
						}); */
					}else{
						$("#dialog-add-faq form").attr("action", "update_board_01.isnet");
						// 서버로 전송합니다.
						$("#dialog-add-faq form").ajaxSubmit(function(data, state){
							$("#dialog-add-faq").dialog("close");
						    data = data.replace(/[<][^>]*[>]/gi, '');
						    var jData = JSON.parse(data);
							if(jData && jData.isSuccess){
								$("#list" + ($( "#tabs" ).tabs( "option", "active" )  + 1)).jqGrid().trigger("reloadGrid");
								simple_popup("안내", "FAQ가 수정되었습니다.");
							}else{
								simple_popup("안내", "FAQ 수정 중 오류가 발생하였습니다.");
							}
						});
						/* $.ajax({
							type:"POST",
							url:"update_board_01.isnet",
							dataType:"json",
							data:$("#dialog-add-faq form").serialize(),
							success:function(data, status){
								if(data && data.isSuccess){
									$("#list" + ($( "#tabs" ).tabs( "option", "active" )  + 1)).jqGrid().trigger("reloadGrid");
									simple_popup("안내", "FAQ가 수정되었습니다.");
								}else{
									simple_popup("안내", "FAQ 수정 중 오류가 발생하였습니다.");
								}
							},
							error:function(){
								simple_popup("안내", "FAQ 수정 중 오류가 발생하였습니다.");
							}
						}); */
					}
					$(this).dialog("close");
				}
			},
			{
				text:"취소",
				click:function(){
					$(this).dialog("close");
				}
			}
		],
		
	});
	
	// 각 제품별 jqGrid가 초기화되었는지 여부를 저장하는 배열입니다.
	var initGrid = new Array($("#tabs li").size());		
	$("#tabs").tabs({		
		show:function(event, ui){
			if(! initGrid[ui.index]){
				
				var product_no = $("#tabs-"+($( "#tabs" ).tabs( "option", "active" ) + 1)+ " input[name='product_no']").val();
				
				$("#list" + (ui.index+1)).jqGrid({
					jsonReader: {
						repeatitems : false,															
						id: "0"																			
					},
					url:"list_01.isnet",
					datatype:"json",
					mtype:"POST",
					colNames:['번호', 'PRODUCT_NO', 'FILE_NAME', '구분', '질문', '답변', '첨부파일', '등록일'],						
				   	colModel:[	
						{name:'FAQ_NO', index:'FAQ_NO', width:100, align:"center", sortable:false, search:false},	 
						{name:'PRODUCT_NO', index:'PRODUCT_NO', hidden:true},
						{name:'FILE_NAME', index:'FILE_NAME', hidden:true},
						{name:'PRODUCT_NAME', index:'PRODUCT_NAME', width:200, align:"center", sortable:false, search:false},	 
				   		{name:'FAQ_QUESTION',index:'FAQ_QUESTION', width:550, align:"left", sortable:false, search:true, searchoptions:{sopt:['cn', 'eq']}},
				   		{name:'FAQ_ANSWER',index:'FAQ_ANSWER', hidden:true},
						{name:'URL_LINK', index:'URL_LINK', width:150, align:"center",sortable:false, search:false},
				   		{name:'CREATE_DATE',index:'CREATE_DATE', width:150, align:"center",sortable:false, search:false }
				   		//{name:'FAQ_HIT',index:'FAQ_HIT', width:100, align:"center", sortable:false, search:false }
				   	],	
					pager: '#pager'+ (ui.index +1),
	                rowNum: 10,
	                rowList: [20, 40, 60],
	                viewrecords: true,
	      			height:$(window).height() - 250,
	      			beforeRequest:function(){
	      				var postData = $(this).jqGrid("getGridParam", "postData");
	      				// 제품별로 조회가 되도록 검색조건을 생성한다.
	      				var filters = {
					   			groupOp:"AND", 
					   			rules:[
					   				{field:"PRODUCT_NO", op:"eq", data:product_no}
					   			]
					   		};
	      				// 검색창을 띄워서 검색을 입력하는 경 검색조건을 filter에 추가한다.
	      				if(postData["_search"]){
	      					filters.rules.push({field:postData["searchField"], op:postData["searchOper"], data:postData["searchString"]});
	      				}
      					// 서버요청전에 검색조건의 값을 postData에 설정한다.
      			   		postData["_search"] = true;
      			   		postData["filters"] = JSON.stringify(filters);
      			   		
      			   		$(this).jqGrid("setGridParam", "postData", postData);
	      			},
	      			beforeProcessing: function(data, status, xhr){
	      		   		if(data && data.isSuccess){
	      		    		$.each(data.rows, function(index, row){
	      		    			if(row["FILE_NAME"]){
	      		    				row["URL_LINK"] = "<a href='download_file.isnet?NO="+row["FAQ_NO"]+"&gubun=faq'>"+row["FILE_NAME"].substring(14)+"</a>";
	      		    			}
	      		    		});
	      		    	}
	      		   	},
	      			loadComplete:function(data){
	      		   		var records = data["records"];
	      		   		if(records == 0){
	      		   			simple_popup("안내", "검색조건에 해당하는 데이터가 존재하지 않습니다.");
	      		   		}
	      		   	},
					ondblClickRow:function(rowid, iRow, iCol, e){
						var rowData = $(this).jqGrid("getRowData", rowid);
						$("#view_faq_question").val(rowData["FAQ_QUESTION"]);
						$("#view_faq_answer").val(rowData["FAQ_ANSWER"]);
						if(rowData["FILE_NAME"]){
							$("#download_link").html("<strong><a href='download_file.isnet?NO="+rowid+"&gubun=faq'>"+rowData["FILE_NAME"].substring(14)+"</a></strong>");
						}else{
							$("#download_link").html("<strong>없음</strong>");
						}
						$( "#dialog-view-faq" ).dialog( "open" );
					}
				}).navGrid('#pager'+ (ui.index +1),{edit:false,add:false,del:false}, {}, {}, {}, {closeOnEscape:true, closeAfterSearch:true}, {})
				.navButtonAdd('#pager'+ (ui.index +1),
					{
						caption:"등록", 
					 	buttonicon:"ui-icon-plus", 
					 	onClickButton: function(){ 
					 		$("#dialog-add-faq").dialog("open");
					 	}, 
				 		id: "user_add_bt"
				 	}
				).navButtonAdd('#pager'+ (ui.index +1),
					{
						caption:"수정", 
					 	buttonicon:"ui-icon-pencil", 
					 	onClickButton: function(){ 
					 		var rowid = $(this).jqGrid("getGridParam", "selrow");
					 		if(rowid){
						 		var rowData = $(this).jqGrid("getRowData", rowid);
					 			$("#add_faq_no").val(rowData["FAQ_NO"]);
					 			$("#add_faq_question").val(rowData["FAQ_QUESTION"]);
					 			$("#add_faq_answer").val(rowData["FAQ_ANSWER"]);
						 		$("#dialog-add-faq").dialog("open");
					 		}else{
					 			simple_popup("안내", "수정할 행을 선택하세요");
					 		}
					 		
					 	}, 
				 		id: "user_modify_bt"
				 	}
				);
				initGrid[ui.index] = true;
			}
		}
	});
});
</script>		
<%@ include file="../common/bottom.jsp" %>