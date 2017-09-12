<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_statistics.jsp"/>

<script type = "text/javascript">
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
		
		
		$(".jPage").click2(function(e){
			e.preventDefault();
			jForm.ci();
			jForm.mi("cpage", $(this).attr("page"));
			jForm.submit();
		});
		
		$(".jSearch").click2(function(){
			jForm.ci();
			jForm.submit();
		});
		

	});
	
	
</script>

	<div id="Contents"  class="notice">
	    <h1>접속 통계</h1>
		<h2 style="width:98%;">
			통계 ><span> 접속통계 </span>
		</h2>
		
		<div class = "data">
			<div style="clear:both">
			
				<fieldset class="search" style="margin-top:20px;">
					<div class="jSearchArea">
						<label>
							<span>가맹점명</span>
							<input type = "text" name = "searchName" value = "${param.searchName }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<input type="button" class="button medium white jSearch" value="검색">
						</label>
					</div>
				</fieldset>
			</div>
			
			<table class = "datacList">
				<thead>
					<tr>
						<th>No</th>
						<th>접속일</th>
						<th>아이디</th>
						<th>가맹점명</th>
						<th>접속IP</th>
					</tr>
				</thead>
				<tbody style = "text-align:center;">
					<c:set var="vnum" value="${retData.pager.virtualNum}"/>
					<c:forEach var="row" items="${retData.list }" varStatus="status">
						<tr>
							<td>${vnum - status.index}</td>
							<td>${row.loginDate }</td>
							<td>${row.userID }</td>
							<td>${row.franchiseName}</td>
							<td>${row.loginIP }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table> 

			<div class="pageNumber">
				<c:set var="pager" value="${retData.paging}"/>
				${pager }
			</div>
			

		</div>
	</div>
	
<c:import url="${properties.include_path}/footer_admin.jsp"/>

