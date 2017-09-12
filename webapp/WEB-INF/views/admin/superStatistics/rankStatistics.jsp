<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_statistics.jsp"/>

<jsp:useBean id="toDay" class="java.util.Date" />

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
		
		$("[name=orderType]").change(function(){
			jForm.ci();
			jForm.submit();
		});
	});
	
	function init(){
		/*
		var html = "<option value=''>전체</option>";
		for(var i=0; i<10; i++){
			html += "<option value='"+i+"'>"+i+"</option>";
		}
		$(".jSelectYear").html(html);
	
		html = "<option value=''>전체</option>";
		for(var i=1; i<=12; i++){
			html += "<option value='"+i+"'>"+i+"</option>";
		}
		$(".jMonthYear").html(html);
		*/
	}
	
</script>

	<div id="Contents"  class="notice">
	    <h1>전체수발주 순위</h1>
		<h2 style="width:98%;">
			통계 ><span> 전체 수발수 순위 </span>
		</h2>
		
		<div class = "data">
			<div style="clear:both">
			
				<fieldset class="search" style="margin-top:20px;">
					<div class="jSearchArea">
						<label>
							<span>가맹점명</span>
							<input type = "text" name = "searchText" value = "${param.searchText }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<input type="button" class="button medium white jSearch" value="검색">
						</label>
					</div>
					
				</fieldset>
			</div>
			
			<div class = "btngroupleft">
				<select class="jSelectYear" name="searchYear" style="width: 80px;">
					<c:forEach var="i" begin="2016" end="2099" step="1">
						<option value = "${i }" ${i == param.searchYear ? 'selected' : '' }>${i }</option>
					</c:forEach>
				</select>년
				<select class="jMonthYear" name="searchMonth" style="width: 80px;">
					<option value = "">전체</option>
					<c:forEach var="i" begin="1" end="12" step="1">
						<c:if test = "${i < 10 }">
							<option value = "0${i }" ${i == param.searchMonth ? 'selected' : '' }>0${i }</option>	
						</c:if>
						<c:if test = "${i >= 10 }">
							<option value = "${i }" ${i == param.searchMonth ? 'selected' : '' }>${i }</option>
						</c:if>
					</c:forEach>
				</select>월
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select name="orderType" style="width: 120px; ">
					<option value="S" ${param.orderType == 'S' ? 'selected' : '' }>발주순</option>
					<option value="R" ${param.orderType == 'R' ? 'selected' : '' }>수주순</option>
				</select>
			</div>

			<div class = "btngroupright">
				<select class="" name="" style="width: 120px;">
					<option value="1">100개씩 보기</option>
				</select>
				<input type="button" class="button bigrounded2 white jExcel" value="엑셀다운">
			</div>
				
			<table class = "datacList">
				<thead>
					<tr>
						<th>순위</th>
						<th>지역</th>
						<th>가맹점명</th>
						<th>발주건</th>
						<th>발주액</th>
						<th>수주건</th>
						<th>수주액</th>
						<th>정산액</th>
					</tr>
				</thead>
				<tbody style = "text-align:center;">
					<c:set var="vnum" value="${data.pager.virtualNum}"/>
					<c:forEach var="row" items="${data.list }" varStatus="status">
						<tr>
							<td>
								${status.index + 1}
							</td>
							<td>${row.sidoName } ${row.gugunName }</td>
							<td>${row.franchiseName}</td>
							<td><fmt:formatNumber value="${row.sendCount }" pattern="#,###.##"/></td>
							<td><fmt:formatNumber value="${row.sendPayment }" pattern="#,###.##"/></td>
							<td><fmt:formatNumber value="${row.receiveCount }" pattern="#,###.##"/></td>
							<td><fmt:formatNumber value="${row.receivePayment }" pattern="#,###.##"/></td>
							<td></td>
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

