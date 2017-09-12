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
			var productNumber = $(this).attr("_no");
			window.opener.responseProductNumber(productNumber);
			self.close();
		});
		
	});

</script>



	<div id="Contents"  class="notice" style="width: 500px;">
		<div style="clear:both">
			
			<fieldset class="search" style="margin-top:20px; text-align: center;">
				<div class="jSearchArea">
					<label>
						<span>제품명</span>
						<input type = "text" name = "productName" value = "${param.productName }" style = "width:120px;"/>
					</label>
					<label>
						<span>제품번호</span>
						<input type = "text" name = "productCode" value = "${param.productCode }" style = "width:120px;"/>
					</label>
				</div>
				<div class="jSearchArea">
					<label>
						<span>가맹점명</span>
						<input type = "text" name = "franchiseName" value = "${param.franchiseName }" style = "width:120px;"/>
					</label>
					<label>
						<input type="button" class="button medium white jSearch" value="검색">
					</label>				
				</div>
			</fieldset>
				
			<table class = "datacList" style="margin-top: 15px; width: 100%;">
				<thead>
					<tr>
						<th>사진</th>
						<th>제품명</th>
						<th>재품번호</th>
						<th>가맹점명</th>
						<th>확인</th>
					</tr>
				</thead>
				<tbody style = "text-align:center;">
					<c:forEach var="row" items="${retData.list }" varStatus="status">
						<tr>
							<td><img src="${properties.img_path}/${row.img}" width="60px" height="60px"></td>
							<td>${row.productName}</td>
							<td>${row.productCode}</td>
							<td>${row.franchiseName}</td>
							<td><input type="button" class="button medium white jAddProduct" value="추가" _no="${row.productNumber }"></td>
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