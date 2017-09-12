<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link href="${properties.resources_path}/css/admin/base.css" rel="stylesheet" type="text/css" />
<link href="${properties.resources_path}/css/admin/btn.css" rel="stylesheet" type="text/css" />
<link href="${properties.resources_path}/css/admin/common.css" rel="stylesheet" type="text/css" />
<link href="${properties.resources_path}/css/admin/layout.css" rel="stylesheet" type="text/css" />
<c:import url="${properties.include_path}/metaData.jsp"/>

<script>

	$(document).ready(function(){
		
		//=====프레입웤 변수들==================================================================================//
		var FORM_TARGET_CLS_NM		= "DIV#Contents"	;		// 폼을 동적 wrap 할 타겟 ID이름
		var FORM_NAME				= "nf"		;		// 폼이름
		var FORM_METHOD				= "GET"		;		// 폼 메쏘드
		var FORM_USE_FILE			= false		;		// 파일폼 사용 여부
		var FORM_ACTION				= document.URL ; 
	
		var NEXT_CMD				= ""			;		// 다은 수행 할 cmd
		//=====================================================================================================//
	
		var jForm = $(FORM_TARGET_CLS_NM).mf({ "name" : FORM_NAME , "method" : FORM_METHOD , "action" : FORM_ACTION , "useFile" : FORM_USE_FILE }) ;
		
		$(".datepicker").datepicker({
			dateFormat:'yy-mm-dd'
		});
		
		// 검색 버튼 enter 허용
		$("input[type='text']").keyup(function(e){
			if (e.keyCode == 13) $(".jSearch").trigger("click");
		});
		
		$(".jSearch").click2(function(){
			jForm.ci();
			jForm.submit();			
		});
		
		$(".jPage").click2(function(e){
			e.preventDefault();
			jForm.ci();
			jForm.mi("cpage", $(this).attr("page"));
			jForm.submit();
		});
		
		$(".jAddProduct").click2(function(){
			var no = $(this).attr("_no");
			window.opener.responseProductNumber(no);
			self.close();
		});
		
	});

</script>



	<div id="Contents"  class="notice" style="width: 500px;">
		<div style="clear:both">
			<input type="hidden" name="btnType" value="${param.btnType }">
			<c:choose>
				<c:when test="${param.btnType eq 2 }">
					<fieldset class="search" style="margin-top:20px; text-align: center;">
						<div class="jSearchArea">
							<label>
								<span></span>
								<input type = "text" class="datepicker" name = "sDate" value = "${param.sDate }" style = "width:120px;"/>
							</label>
							<label>
								<span>~&nbsp;&nbsp;&nbsp;&nbsp;</span>
								<input type = "text" class="datepicker" name = "eDate" value = "${param.eDate }" style = "width:120px;"/>
							</label>
						</div>
						<div class="jSearchArea">
							<label>
								<span>가맹점명</span>
								<input type = "text" name = "franchiseName" value = "${param.franchiseName }" style = "width:120px;"/>
							</label>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<label>
								<input type="button" class="button medium white jSearch" value="검색">
							</label>				
						</div>
					</fieldset>
				
					<table class = "datacList" style="margin-top: 15px; width: 100%;">
						<thead>
							<tr>
								<th width="20%">마지막접속일</th>
								<th width="20%">업체명</th>
								<th width="30%">주소</th>
								<th width="10%">현재 접속 유무</th>
								<th width="20%">아이피</th>
							</tr>
						</thead>
						<tbody style = "text-align:center;">
							<c:forEach var="row" items="${retData.list }" varStatus="status">
								<tr>
									<td>${row.loginDate}</td>
									<td>${row.franchiseName}</td>
									<td>${row.sido}${row.gugun}</td>
									<td>${row.isLogin}접속유무 </td>
									<td>${row.loginIP}</td>
								</tr>
							</c:forEach>
					
						</tbody>
					</table>
					
					<div class="pageNumber">
						<c:set var="pager" value="${retData.paging}"/>
						${pager }
					</div>
				</c:when>
				<c:when test="${param.btnType eq 1 }">
					<h1 align="center">관리자 페이지 접속 로그</h1>
					<table class = "datacList" style="margin-top: 15px; width: 100%;">
						<thead>
							<tr>
								<th>접속일</th>
								<th>아이디</th>
								<th>접속아이피</th>
							</tr>
						</thead>
						<tbody style = "text-align:center;">
							<c:forEach var="row" items="${retData.list }" varStatus="status">
								<tr>
									<td>${row.loginDate}</td>
									<td>${row.adminID}</td>
									<td>${row.loginIP}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<div class="pageNumber">
						<c:set var="pager" value="${retData.paging}"/>
						${pager }
					</div>
				</c:when>
				
			</c:choose>
				
			
		</div>
	</div>