<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_admin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_admin.jsp"/>

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
		
		// 검색 버튼 enter 허용
		$("input[type='text']").keyup(function(e){
			if (e.keyCode == 13) $(".jSearch").trigger("click");
		});
		
		$(".jSearch").click2(function(e){
			e.preventDefault();
			jForm.ci();
			jForm.submit();
		});
		
		$(".jPage").click2(function(e){
			e.preventDefault();
			jForm.ci();
			jForm.mi("cpage", $(this).attr("page"));
			jForm.submit();
		});
			
		
		$(".jSearch").click2(function(e){
			e.preventDefault();
			jForm.ci();
			jForm.submit();
		});
		
		$(".jModify").click2(function(){
			
			var no = $(this).attr("_no");
			location.href=rootPath+"/superProduct/productView?productNumber="+no+"&rurl="+encryptHexStr(document.URL);
			
		});
		
		
		$(".jDelete").click2(function(){
			
			var no = $(this).attr("_no");
			$.ajax({
				url : rootPath + "/product/deleteProduct",
				data : "POST",
				dataType : "json",
				data : {
					orderNumber : no
				}, success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == 1){
						location.reload();
					}
				}
			});
			
		});
		
		$(".jSubmit").click2(function(){
			
			location.href=rootPath+"/superProduct/productView?userNumber=${param.userNumber}&rurl="+encryptHexStr(document.URL);
			
		});
		
		
	});
	
</script>

<style>

	.datacList tr td{ width: 33%; height: 190px;}
	.jProduct {height: 150px; width: 39%; float: left;}
	.pRight {height:170px; width: 59%; float:right;}
	.pRight p{font-size: 11px; margin: 6px 10px;}
	.jProduct img{width: 100%; height: 100%;}
	.b {font-weight: bolder; }
	.Ellipsis{
	  text-align:left;
	  overflow: hidden;
	  text-overflow: ellipsis;
	  display: -webkit-box;
	  -webkit-line-clamp: 3;
	  -webkit-box-orient: vertical;
	  word-wrap:break-word; 
	  line-height: 1.2em;
	  height: 3.6em; 
}
</style>

<div id="Contents"  class="notice">
	<input type="hidden" name="userNumber" value="${param.userNumber }">
	    <h1>상품 리스트</h1>
		<h2 style="width:98%;">
		</h2>
		
		<div class = "data" style="margin-bottom: 30px;">
			<div style="clear:both">
			
				<fieldset class="search" style="margin-top:20px; text-align: center;">
					<div class="jSearchArea">
						<label>
							<span>카테고리</span>
							<select name="category" style="width: 120px;">
								<option value="">전체</option>
								<c:forEach var="row" items="${retData.categoryList }">
									<option value="${row.categoryNumber}" ${row.categoryNumber eq param.category ? 'selected' : '' }>${row.categoryName}</option>
								</c:forEach>
							</select>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>상품명</span>
							<input type = "text" name = "productName" value = "${param.productName }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>제품번호</span>
							<input type = "text" name = "productCode" value = "${param.productCode }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<input type="button" class="button medium white jSearch" value="검색">
						</label>					
					</div>
				</fieldset>
			</div>
			
			<div class = "btngroupright">
				<input type="button" class="button bigrounded2 blue jSubmit" value="등록하기">
				<select class="" name="rowPerPage">
					<option value="${param.rowPerPage eq 12 ? 'selected' : '' }">12개씩 보기</option>
				</select>
			</div>
			 
			<div style="margin: 50px 0px; width: 1043px; padding-top: 10px;" align="center">
				<c:set var="vnum" value="${retData.pager.virtualNum}"/>
				<ul> 
					<c:forEach var="row" items="${retData.list }" varStatus="status" >
						<li style="float: left; width: 30%; margin: 5px; padding: 5px; border: 1px solid #c0c7cc;">
							<div class="jProduct" style="border: 1px solid #c0c7cc;">
								<img src="${properties.img_path }/480/${row.img}" onerror="this.style.visibility='hidden'">
							</div>
							<div class="pRight" align="left">
								<p>${row.productCode }</p>
								<p>${row.productName }</p>
								<p class="b">도매가 : <fmt:formatNumber value="${row.wholesalePrice}" pattern="#,###" />원</p>
								<p class="b">소매가 : <fmt:formatNumber value="${row.retailPrice}" pattern="#,###" />원 </p>
								<p class="b">${row.franchiseName }</p>
								<p class="b">${row.sidoName } ${row.gugunName }</p>
								<p class="Ellipsis">${row.detailDesc }</p>
							</div>
							<input type="button" class="button medium white jModify" value="수정하기" _no="${row.productNumber }" style="width: 50%">
							<input type="button" class="button medium white jDelete" value="선택삭제" _no="${row.productNumber }" style="width: 40%">
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		
		<div class="pageNumber" style="margin-top: 30px;">
			<c:set var="pager" value="${retData.paging}"/>
			${pager }
		</div>
		
	</div>
<c:import url="${properties.include_path}/footer_admin.jsp"/>