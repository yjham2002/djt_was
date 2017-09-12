<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${properties.resources_path}/css/admin/base.css" rel="stylesheet" type="text/css" />
<link href="${properties.resources_path}/css/admin/btn.css" rel="stylesheet" type="text/css" />
<link href="${properties.resources_path}/css/admin/common.css" rel="stylesheet" type="text/css" />
<link href="${properties.resources_path}/css/admin/layout.css" rel="stylesheet" type="text/css" />
<c:import url="${properties.include_path}/metaData.jsp"/>


<script>

	$(document).ready(function(){
		
		$(".jChange").click2( function(){
			var franchiseNumber = $(this).attr("_no");
			var orderNumber = "${param.orderNumber}";
			$.ajax({
				url : "/NaeKkot/admin/superOrder/chanheOwner",
				type : "POST",
				dataType : "json",
				data : {
					franchiseNumber : franchiseNumber,
					orderNumber : orderNumber
				}
				,success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == 1){
						opener.location.reload();
						window.close();				
					}
				}
			});
		});	
		
		$("input[type='text']").keyup(function(e){
			
			if(e.keyCode == 13) $(".jSearch").trigger("click"); 
			
		});
		
		$(".jSearch").click2(function(){
			var franchiseName = $("[name=franchiseName]").val();
			location.href = "/NaeKkot/admin/superOrder/popup?franchiseName="+franchiseName;
			
		});
		
	});

</script>

<div id="Contents"  class="">

	<div class = "data" style="width: 600px;">
		<div style="clear:both">
			<fieldset class="search" style="margin-top:20px; text-align: center;">
				<div class="jSearchArea">
					<label>
						<span>업체명</span>
						<input type = "text" name = "franchiseName" value = "${param.franchiseName }" style = "width:120px;"/>
					</label>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label>
						<input type="button" class="button medium white jSearch" value="검색">
					</label>
				</div>
			</fieldset>
		</div>
	
		<table class = "datacList" style="margin-top: 15px; width: 100%;">
			<thead>
				<tr>
					<th>업체명</th>
					<th>대표자명</th>
					<th>주소</th>
				</tr>
			</thead>
			<tbody style = "text-align:center;">
				<c:forEach var="row" items="${retData.list }" varStatus="status">
					<tr>
						<td class="jChange" _no="${row.franchiseNumber}"><a href="#">${row.franchiseName}</a></td>
						<td>${row.owner }</td>
						<td>${row.sido } ${row.gugun }${row.address }</td>
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