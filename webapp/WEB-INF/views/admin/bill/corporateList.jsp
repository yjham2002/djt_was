<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_admin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_bill.jsp"/>

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
		

	});
</script>
	<div id="Contents"  class="notice">
	    <h1>정산관리</h1>
		<h2 style="width:98%;">
			정산 관리 ><span> 가맹점별 정산</span>
		</h2>
		
		<div class = "data">

			<div class = "btngroupright">
				<select class="" name="">
					<option value="1">100개씩 보기</option>
				</select>
			</div>
			
			<table class = "datacList">
				<thead>
					<tr>
						<th width="5%">No</th>
						<th width="8%">생성일</th>
						<th width="7%">가맹점명</th>
						<th width="7%">발주액</th>
						<th width="7%">수주액</th>
						<th width="7%">총선택옵션</th>
						<th width="7%">인센티브율(%)</th>
						<th width="7%">수수료율(%)</th>
						<th width="7%">총 수익 수수료</th>
						<th width="7%">총 공제액</th>
						<th width="7%">세금계산서 발행금액<br/>(VAT포함)</th>
						<th width="7%">보유예치금</th>
						<th width="7%">정산액</th>
						<th width="7%">전체완료</th>
					</tr>
				</thead>
				<tbody style = "text-align:center;">
					<c:set var="vnum" value="${retData.pager.virtualNum}"/>
					<c:forEach var="row" items="${retData.list }" varStatus="status">
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
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.userID }</td>
							<td>${row.personName }</td>
							<td>${row.corporate}</td>
							<td><input type="button" class="button searchsmall white jBill" value="정산"  _no = "${row.userNumber }"/></td>
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

