<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="one_depth" value="관리자"/>
<c:set var="two_depth" value="데이타베이스 관리"/>
<c:out value="" default=""></c:out>
<%@ include file="../common/header.jsp" %>
		<!-- 컨텐츠영역 시작 -->
		<div id="content">
			
			<!-- 네비게이션 -->
			<div id="breadcrumb"><a href="../home.isnet">Home</a> / <a href="admin_01.isnet">관리자</a> / <strong>데이타베이스 관리</strong></div>
			
			<!-- 컨텐츠 표시부 시작 -->
			<!-- 컨텐츠 표시부 시작 -->
			<table id="list"></table>
			<div id="pager"></div>

			<!-- footer -->
			<%@ include file="../common/footer.jsp" %>
		
		</div>
		<!-- 컨텐츠영역 끝 -->
		
<script type="text/javascript">
$(document).ready(function(){
	
	$("#list").jqGrid({
		jsonReader: {
			repeatitems : false,															
			id: "0"																			
		},
		height:400,
		mtype:"POST",
		datatype: "json",
		url:"list_02.isnet",
	   	colNames:['DATE', '백업날짜', '파일명', '백업'],						
	   	colModel:[	
			{name:'DATE_NO', index:'DATE_NO', hidden:true},	   	
			{name:'BACKUP_DATE', index:'BACKUP_DATE', width:380, align:"center",sortable:false},	   	
	   		{name:'FILE_NAME',index:'FILE_NAME', width:600, align:"center", sortable:false},
	   		{name:'LINK',index:'LINK', width:203, align:"center", sortable:false}
	   	
	   	],	
	    viewrecords: true,
	    // 제목
	    caption:"데이타베이스 백업 목록",
	    // 페이지 네비게이션 설정
	    pager:"pager",
	   	page:'<c:out value="${param.page}" default="1"/>',
	   	rowNum:'<c:out value="${param.rows}" default="10"/>',
	   	rowList:[10, 20, 50],
	    loadError:function(xhr, status, error){
	    	alert("백업파일 조회 중 오류가 발생하였습니다.");
	    },
	    beforeProcessing:function(data, status, xhr){
	    	if(data && data.isSuccess){
	    		$.each(data.rows, function(index, row){
	    			var backup_date = row["BACKUP_DATE"];
	    			backup_date = $.datepicker.formatDate("yy년 mm월 dd일", $.datepicker.parseDate('yymmdd', backup_date.substring(0, 8)));
					row["BACKUP_DATE"] = backup_date;	    			

					row["LINK"] = "<span id='test_"+backup_date+"' style='cursor:pointer;' title='"+backup_date+"로 복원'>복구</span>";
	    			
	    		});
	    	}
	    }
	}).navGrid('#pager',{edit:false,add:false,del:false,search:false, refresh:false});
	
	// 복구
	$("#list span[id^=test]").live("click", function(event){
		event.preventDefault();
		var date_no = $(this).attr("id").replace("test_", "");
		//console.log(date_no);
		$.ajax({
			type:"GET",
			url:"restore.isnet",
			dataType:"json",
			data:{DATE_NO:date_no},
			success:function(data, status){
				if(data && data.isSuccess){
					simple_popup("안내", "데이타베이스가 복구되었습니다.");
				}else{
					simple_popup("안내", "데이타베이스 복구 중 오류가 발생하였습니다.");
				}
			},
			error:function(){
				simple_popup("안내", "데이타베이스 복구 중 오류가 발생하였습니다.");
			}
		});
	});
	
});

</script>		
<%@ include file="../common/bottom.jsp" %>