<%@page import="com.appg.djTalk.common.bean.DataMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
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
		
		$(".jView").click2(function(e){
			e.preventDefault();
			var no = $(this).attr("franchiseNumber");
			
			location.href = rootPath + "/superBill/depositView?franchiseNumber=" + no+"&rurl="+encryptHexStr(document.URL);
			
		});
		
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
		
		$(".jMinus").click2(function(){
			
			var deposit = $(this).siblings(".jDepositList").val();
			$(this).siblings(".jDepositList").val(deposit * -1);
			
		});
		
		$(".jSubmit").click2(function(){
			
			var deposit = $(this).siblings(".jDepositList").val();
			var userNumber = $(this).attr("userNumber");
			var franchiseNumber = $(this).attr("franchiseNumber");
			var incentive = $(this).attr("incentive");
			
			if(deposit == "") {
				alert("예치금을 입력해주세요.");
				return;
			}
			
			$.ajax({
				url : "/NaeKkot/admin/superBill/setBalance",
				type : "POST",
				dataType : "json",
				data : {
					userNumber : userNumber ,
					franchiseNumber : franchiseNumber ,
					deposit : deposit ,
					incentive : incentive , 
					balanceType : "I"
				}, success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == 1){
						location.reload();
					}
				}
				
			});
			
		});
		
		$(".jExcel").click2(function(){
			
			location.href = rootPath + "/superBill/downDepositListExcel";
			
		});

	});
</script>
	<div id="Contents"  class="notice">
	    <h1>예치금 리스트</h1>
		<h2 style="width:98%;">
			정산 ><span> 예치금 리스트</span>
		</h2>
		
		<div class = "data">
			<div style="clear:both">
			
				<fieldset class="search" style="margin-top:20px;">
					<div class="jSearchArea" align="center">
						<label>
							<span>가맹점명</span>
							<input type = "text" name = "searchName" value = "${param.searchName }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>대표자명</span>
							<input type = "text" style = "width:120px;" name = "searchOwner" value = "${param.searchOwner }"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<input type="button" class="button medium white jSearch" value="검색">
						</label>
					</div>
				</fieldset>
			</div>
			
			<div class = "jSearchArea btngroupcenter">
				<input type="button" class="button bigrounded2 white jExcel" value="엑셀다운">
			</div>
			<div class = "jSearchArea btngroupright">
				<select class="" name="">
					<option value="">전체보기</option>
					<option value="">100개씩 보기</option>
				</select>
			</div>
			
			<table class = "datacList">
				<thead>
					<tr>
						<th>No.</th>
						<th>마지막입력일</th>
						<th>대표자명</th>
						<th>가맹점명</th>
						<th>보유예치금</th>
						<th>인센티브율</th>
						<th>예치금입력</th>
						<th>상세보기</th>
					</tr>
				</thead>
				<tbody style = "text-align:center;">
					<c:set var="vnum" value="${data.pager.virtualNum}"/>
					<c:forEach var="row" items="${data.list }" varStatus="status">
						<tr>
							<td>	${vnum - status.index}</td>
							<td><%-- <fmt:formatDate value="${row.regDate}" pattern="yyyy-MM-dd"/>--%> ${row.lastDate }</td>
							<td>${row.owner }</td>
							<td>${row.franchiseName }</td>
							<td><fmt:formatNumber value="${row.totalBalance }" pattern="#,###.##" />원</td>
							<td>${row.incentive }%</td>
							<td>
								<input type="button" class="button medium white jMinus" value="ㅡ">
								<input type="text" class="jDepositList" name="balance" style="width: 80px"><span>원</span>
								<input type="button" class="button medium white jSubmit" value="완료" userNumber = "${row.userNumber }" franchiseNumber = "${row.franchiseNumber }" incentive = "${row.incentive }">
							</td>
							<td><input type="button" class="button searchsmall white jView" value="보기"  userNumber = "${row.userNumber }" franchiseNumber = "${row.franchiseNumber }"/></td>
						</tr>
					</c:forEach>
					<c:if test = "${fn:length(data.list) == 0}">
						<tr>
							<td colspan = "8">No Data.</td>
						</tr>
					</c:if>
				</tbody>
			</table> 

			<div class="pageNumber">
				<c:set var="pager" value="${data.paging}"/>
				${pager }
			</div>
			

		</div>
	</div>
	
<c:import url="${properties.include_path}/footer_admin.jsp"/>

