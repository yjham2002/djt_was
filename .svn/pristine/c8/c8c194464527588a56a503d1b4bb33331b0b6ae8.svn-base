<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${accessLevelCode ne 2 }">
	<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
	<c:import url="${properties.include_path}/leftMenu_adminBill.jsp"/>
</c:if>
<c:if test="${accessLevelCode eq 2 }">
	<c:import url="${properties.include_path}/header_admin.jsp"/>
	<c:import url="${properties.include_path}/leftMenu_adminBill.jsp"/>
</c:if>

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
		
		$(".jToggle").click2(function(){
			
			var no = $(this).attr("no");
			var isBill = $(this).attr("isBill");
			
			$.ajax({
				url : "/NaeKkot/admin/superBill/updateBalanceBill",
				type : "POST",
				dataType : "json",
				data : {
					balanceNumber : no , 
					isBill : isBill
				}, success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == 1){
						location.reload();
					}
				}
				
			});
			
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
				<c:set var="vnum" value="${data.pager.virtualNum}"/>
				<c:forEach var="row" items="${data.list }" varStatus="status">
					<tr>
						<td>
							${vnum - status.index}
						</td>
						<td><%-- <fmt:formatDate value="${row.regDate}" pattern="yyyy-MM-dd"/>--%> ${row.regDate }</td>
						<td>${row.owner}</td>
						<td>${row.franchiseName }</td>
						<td><fmt:formatNumber value="${row.deposit }" pattern="#,###.##" /></td>
						<td><fmt:formatNumber value="${row.incentivePrice }" pattern="#,###.##" /></td>
						<td>${row.incentive }%</td>
						<td><fmt:formatNumber value="${row.balance }" pattern="#,###.##" /></td>
						<td><fmt:formatNumber value="${row.totalBalance }" pattern="#,###.##" /></td>
						<td>
							<c:if test="${accessLevelCode ne 2 }">
								<c:if test = "${row.isBill == 'Y' }">
									<span class = "jToggle" no = "${row.balanceNumber }" isBill = "N">완료</span>
									<%--<input type="button" class="button searchsmall white jToggle" value="완료"  no = "${row.balanceNumber }"/> --%>
								</c:if>
								<c:if test = "${row.isBill == 'N' }">
									<input type="button" class="button searchsmall white jToggle" value="대기"  no = "${row.balanceNumber }" isBill = "Y"/>
								</c:if>
							</c:if>
							<c:if test="${accessLevelCode eq 2 }">
								${row.isBill == 'Y' ? '완료' : '대기'}
							</c:if>
						</td>
					</tr>
				</c:forEach>
				<c:if test = "${fn:length(data.list) == 0}">
					<tr>
						<td colspan = "10">No Data.</td>
					</tr>
				</c:if>
			</tbody>
		</table> 

		<div class="pageNumber">
			<c:set var="pager" value="${data.paging}"/>
			${pager }
		</div>
		

	</div>
	
<c:import url="${properties.include_path}/footer_admin.jsp"/>

