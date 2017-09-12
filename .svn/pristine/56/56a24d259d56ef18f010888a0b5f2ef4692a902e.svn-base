<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_admin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_adminBill.jsp"/>

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
	    <h1>예치금 상세보기</h1>
		<h2 style="width:98%;">
			정산 ><span> 예치금 상세</span>
		</h2>
		
		<div class = "btngroupright">
			<select class="" name="">
				<option value="1">100개씩 보기</option>
			</select>
		</div>
			
		<table class = "datacList" style="margin: 15px 0px;">
			<thead>
				<tr>
					<th>계산식</th>
					<th>인센티브 금액 = {입금액 × (인센티브율 / 100)}</th>
					<th>적용예치금 = {입금액 × (인센티브율 / 100) + 입금액}</th>
				</tr>
			</thead>
		</table>
		
		<table class = "datacList">
			<thead>
				<tr>
					<th>No</th>
					<th>입력일</th>
					<th>대표자명</th>
					<th>가맹점명</th>
					<th>입금액</th>
					<th>인센티브금액</th>
					<th>인센티브율</th>
					<th>적용 예치금</th>
					<th>보유예치금</th>
					<th>세금계산서</th>
				</tr>
			</thead>
			<tbody style = "text-align:center;">
				<c:set var="vnum" value="${retData.pager.virtualNum}"/>
				<c:forEach var="row" items="${retData.list }" varStatus="status">
				</c:forEach>
					<tr>
						<td>
							${vnum - status.index}
						</td>
						<td><%-- <fmt:formatDate value="${row.regDate}" pattern="yyyy-MM-dd"/>--%> ${row.regDate }</td>
						<td>${row.userType == 1 ? '일반' : '기업'}</td>
						<td>${row.userName }</td>
						<td>${row.aaa }</td>
						<td>${row.aaa }</td>
						<td>${row.aaa }</td>
						<td>${row.aaa }</td>
						<td>${row.userID }</td>
						<td><input type="button" class="button searchsmall white jToggle" value="대기"  _no = "${row.userNumber }"/></td>
					</tr>
			</tbody>
		</table> 

		<div class="pageNumber">
			<c:set var="pager" value="${retData.paging}"/>
			${pager }
		</div>
		

	</div>
</div>
	
<c:import url="${properties.include_path}/footer_admin.jsp"/>

