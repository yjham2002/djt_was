<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_admin.jsp"/>

<script type = "text/javascript">

	$(document).ready(function(){
		init();
		$(".jSelectYear").val("${param.year}");
		
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
		
		$(".jSelectYear").change(function(){
			jForm.ci();
			jForm.submit();
		});
		

	});
	
	function init(){
		
		var html = "<option value=''>전체</option>";
		for(var i=0; i<10; i++){
			html += "<option value='"+i+"'>"+i+"</option>";
		}
		$(".jSelectYear").html(html);
	
		
	}
	
</script>

	<div id="Contents"  class="notice">
	    <h1>월별 사용내역</h1>
		<h2 style="width:98%;">
			통계 ><span> 사용내역</span>
		</h2>
		
		<div class = "btngroupleft">
			<select class="jSelectYear" name="year" style="width: 120px;"></select>년
		</div>
			
		<table class = "datacList" style="margin: 15px 0px;">
			<thead>
				<tr>
					<th>년 합계</th>
					<th></th>
					<th>건</th>
					<th>건</th>
					<th>원</th>
					<th>원</th>
					<th>명</th>
				</tr>
			</thead>
		</table>
		
		<table class = "datacList">
			<thead>
				<tr>
					<th>월/일별</th>
					<th>요일</th>
					<th>총 배송건</th>
					<th>총 취소건</th>
					<th>총 배송액</th>
					<th>총취소액</th>
					<th>앱 접속자수</th>
				</tr>
			</thead>
			<tbody style = "text-align:center;">
				<c:set var="vnum" value="${retData.pager.virtualNum}"/>
				<c:forEach var="row" items="${retData.list }" varStatus="status">
				</c:forEach>
					<tr>
						<td><%-- <fmt:formatDate value="${row.regDate}" pattern="yyyy-MM-dd"/>--%> ${row.regDate }</td>
						<td>${row.userType == 1 ? '일반' : '기업'}</td>
						<td>${row.userName }</td>
						<td>${row.aaa }</td>
						<td>${row.aaa }</td>
						<td>${row.aaa }</td>
						<td>${row.aaa }</td>
					</tr>
			</tbody>
		</table> 

		<div class="pageNumber">
			<c:set var="pager" value="${retData.paging}"/>
			${pager }
		</div>
		

	</div>
	
<c:import url="${properties.include_path}/footer_admin.jsp"/>

