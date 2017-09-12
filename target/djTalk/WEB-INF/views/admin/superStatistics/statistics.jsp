<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test = "${accessLevelCode == 4 }" >
<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_statistics.jsp"/>
</c:if>
<c:if test = "${accessLevelCode != 4 }" >
<c:import url="${properties.include_path}/header_admin.jsp"/>
</c:if>

<jsp:useBean id="toDay" class="java.util.Date" />

<c:set var="data" value="${retData }" />

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
		
		$("[name=searchYear], [name=searchMonth]").change(function(){
			jForm.ci();
			jForm.submit();
		});
		
		$(".jExcel").click2(function(e){
			e.preventDefault();
			
			location.href = "/NaeKkot/admin/exportStatisticExcel?searchYear=${param.searchYear}&searchMonth=${param.searchMonth}";
		});

	});

</script>

	<div id="Contents"  class="notice" style = "${accessLevelCode != 4 ? 'width:1200px;' : ''}">
	    <h1>[${adminName }] 월별 사용내역</h1>
		<h2 style="width:98%;">
			통계 ><span> 사용내역</span>
		</h2>
		
		<div class = "btngroupright">
			<input type="button" class="button bigrounded2 blue jExcel" value="엑셀다운">
		</div>
		
		<div class = "btngroupleft" style = "width:100%;">
			<select class="jSelectYear" name="searchYear" style="width: 80px;">
				<c:forEach var="i" begin="2016" end="2099" step="1">
					<option value = "${i }" ${i == param.searchYear ? 'selected' : '' }>${i }</option>
				</c:forEach>
			</select>년
			<c:if test = "${accessLevelCode == 4 }">
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
			 </c:if>
		</div>
		
		<table class = "datacList" style="margin: 15px 0px; width:100%;">
			<c:if test = "${accessLevelCode == 4 }" >
				<colgroup>
					<col width = "20%" />
					<col width = "10%" />
					<col width = "8%" />
					<col width = "8%" />
					<col width = "18%" />
					<col width = "18%" />
					<col width = "18%" />
				</colgroup>
			</c:if>
			<c:if test = "${accessLevelCode == 4 }" >
				<colgroup>
					<col width = "23%" />
					<col width = "13%" />
					<col width = "11%" />
					<col width = "11%" />
					<col width = "21%" />
					<col width = "21%" />
				</colgroup>			
			</c:if>
			<thead>
				<tr>
					<th >
						<c:if test = "${empty param.searchYear}">
							<fmt:formatDate value="${toDay}" pattern="yyyy" />년 합계
						</c:if>
						<c:if test = "${not empty param.searchYear }">
							${param.searchYear }년 합계
						</c:if>
					</th>
					<th ></th>
					<th >${data.totalCount }건</th>
					<th >${data.cancelCount }건</th>
					<th ><fmt:formatNumber value="${data.totalPayment }" pattern="#,###.##"/>원</th>
					<th ><fmt:formatNumber value="${data.cancelPayment }" pattern="#,###.##"/>원</th>
					<c:if test = "${accessLevelCode == 4 }" >
					<th >${data.loginCount }명</th>
					</c:if>
				</tr>
			</thead>
		</table>
		
		<table class = "datacList" style = "width:100%;">
			<c:if test = "${accessLevelCode == 4 }" >
				<colgroup>
					<col width = "20%" />
					<col width = "10%" />
					<col width = "8%" />
					<col width = "8%" />
					<col width = "18%" />
					<col width = "18%" />
					<col width = "18%" />
				</colgroup>
			</c:if>
			<c:if test = "${accessLevelCode == 4 }" >
				<colgroup>
					<col width = "23%" />
					<col width = "13%" />
					<col width = "11%" />
					<col width = "11%" />
					<col width = "21%" />
					<col width = "21%" />
				</colgroup>			
			</c:if>
			<thead>
				<tr>
					<th >월/일별</th>
					<th >요일</th>
					<th >총 배송건</th>
					<th >총 취소건</th>
					<th >총 배송액</th>
					<th >총취소액</th>
					<c:if test = "${accessLevelCode == 4 }" >
					<th >앱 접속자수</th>
					</c:if>
				</tr>
			</thead>
			<tbody style = "text-align:center;">
				<%--<c:set var="vnum" value="${retData.pager.virtualNum}"/> --%>
				<c:forEach var="row" items="${data.paymentList }" varStatus="status">
					
					<tr>
						<td>${row.date }</td>
						<td>
							<c:choose>
								<c:when test = "${row.dayofweek == 1}">일요일</c:when>
								<c:when test = "${row.dayofweek == 2}">월요일</c:when>
								<c:when test = "${row.dayofweek == 3}">화요일</c:when>
								<c:when test = "${row.dayofweek == 4}">수요일</c:when>
								<c:when test = "${row.dayofweek == 5}">목요일</c:when>
								<c:when test = "${row.dayofweek == 6}">금요일</c:when>
								<c:when test = "${row.dayofweek == 7}">토요일</c:when>
							</c:choose>
						</td>
						<td>${row.totalCount }</td>
						<td>${data.cancelList[status.index].totalCount }</td>
						<td><fmt:formatNumber value="${row.totalPayment }" pattern="#,###.##"/></td>
						<td><fmt:formatNumber value="${data.cancelList[status.index].totalPayment }" pattern="#,###.##"/></td>
						<c:if test = "${accessLevelCode == 4 }" >
						<td>${data.loginList[status.index].loginCount }</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table> 

<%--
		<div class="pageNumber">
			<c:set var="pager" value="${retData.paging}"/>
			${pager }
		</div>
--%>
		

	</div>
	
<c:import url="${properties.include_path}/footer_admin.jsp"/>

