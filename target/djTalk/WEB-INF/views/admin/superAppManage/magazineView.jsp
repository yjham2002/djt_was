<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_appManage.jsp"/>

<c:set var="data" value="${ retData.data}" />

<script type = "text/javascript">

	var _rurl = "${rurl}";

	$(document).ready(function(){
		initFileUpload(100);
		initFileUpload(101);
		initFileUpload(102);
		initFileUpload(103);
		
		//=====프레입웤 변수들==================================================================================//
		var FORM_TARGET_CLS_NM		= "DIV#Contents"	;		// 폼을 동적 wrap 할 타겟 ID이름
		var FORM_NAME				= "nf"		;		// 폼이름
		var FORM_METHOD				= "GET"		;		// 폼 메쏘드
		var FORM_USE_FILE			= false		;		// 파일폼 사용 여부
		var FORM_ACTION				= document.URL ; 

		var NEXT_CMD				= ""			;		// 다은 수행 할 cmd
		//=====================================================================================================//

		var jForm = $(FORM_TARGET_CLS_NM).mf({ "name" : FORM_NAME , "method" : FORM_METHOD , "action" : FORM_ACTION , "useFile" : FORM_USE_FILE }) ;
		
		
		$(".jBack").click2(function(){
			location.href=_rurl;
		});
		
		$(".jSave").click2(function(){

			$("#jData").ajaxSubmit({
				url : rootPath + "/superAppManage/updateAppManage",
				type : "post",
				forceSync : true,
				dataType : "json",
				success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == "1") {
						//location.reload();
						location.href=_rurl;
					}
				}
			});
			
		});
		
		$(".jAdd").click2(function(){
			window.open(rootPath + "/superAppManage/addProduct", "addProduct", "width=523, height=700,top=50, left=0, scrollbars=yes") ;
		});
		
		$(document).on("click", ".jDelete", function(){
			
			if("${param.boardNumber}" > 0){	//수정상황
				if(!confirm("삭제하시겠습니까?"))
					return;
				
				var no = $(this).attr("_no");
				
				$.ajax({
					url : rootPath + "/superAppManage/deleteBoardProduct",
					data : "POST",
					dataType : "json",
					data : {
						no : no,
					}, success : function(data){
						alert(data.returnMessage);
						if(data.returnCode == 1)
							location.reload();
					}
					
				});
			} else {	//등록상황
				
				$(this).closest("tr").html("");
			
			}
			
			 
		});
		
	});
	
	function responseProductNumber(productNumber){

		$.ajax({
			url : rootPath + "/getProductData",
			data : "POST",
			dataType : "json",
			data : {
				productNumber : productNumber,
			}
			, success : function(data){
				
				var row = data.entity;
				
				var html = "";
				html += '<tr><th>관련상품</th><td><input type="hidden" name="productNumbers[]" value="'+row.productNumber+'" />';
				html += '<span>제품명 : '+row.productName+'&nbsp; 제품번호 : '+row.productCode+'&nbsp; 가맹점명 : '+row.franchiseName+'</span>';
				html += '<input type="button" class="button medium white jDelete" value="삭제" _no="'+row.productNumber+'" style="float: right; margin-right: 30px;">'
				html += '</td></tr>';
				
				$(".productList").append(html);
			}
			
		});

	}
	
</script>
	<div id="Contents"  class="notice">
	    <h1>
	    	<c:choose>
				<c:when test="${param.pageType eq 1 }">
					매거진
				</c:when>
				<c:when test="${param.pageType eq 2 }">
					공지
				</c:when>
				<c:when test="${param.pageType eq 3 }">
					이벤트
				</c:when>
			</c:choose>
	    </h1>
		<h2 style="width:98%;">
		</h2>
		
		<div class = "data">
			 
		<form id="jData" method="post" enctype="multipart/form-data">
			<input type="hidden" name="boardNumber" value="${param.boardNumber}">
			<input type="hidden" name="pageType" value="${param.pageType}">
				<table class = "datacList" style="margin-top: 15px;">
					<thead>
						<tr>
							<th width="20%">제목</th>
								<td><input type="text" class="" name="title" value="${data.title }" style="width: 95%"></td>
							<c:if test="${param.pageType eq 2 }">
							<th width="15%">메인노출</th>
							<td width="20%">
								<select name="order" style="width: 120px;">
									<option value="0" ${data.ord eq 0 ? 'selected' : '' }>노출안함</option>
									<option value="1" ${data.ord eq 1 ? 'selected' : '' }>1</option>
									<option value="2" ${data.ord eq 2 ? 'selected' : '' }>2</option>
									<option value="3" ${data.ord eq 3 ? 'selected' : '' }>3</option>
									<option value="4" ${data.ord eq 4 ? 'selected' : '' }>4</option>
									<option value="5" ${data.ord eq 5 ? 'selected' : '' }>5</option>
								</select>
							</td>
						</c:if>
					</tr>
					<c:if test="${param.pageType eq 1 }">
						<th>타이틀이미지</th>
						<td>
							<c:import url="${properties.include_path}/fileUpload.jsp">
								<c:param name="fileIndex" value="100" />
								<c:param name="fileName" value="images[0]" />
								<c:param name="filePath" value="${data.img }" />
								<c:param name="includePath" value="${properties.img_path }" />
							</c:import>
						</td>
					</c:if>
				</thead>
			</table>
			
			<c:if test="${param.pageType eq 1 }">
				<table class="datacList" style="margin: 50px 0px;">
					<thead>
						<tr>
							<th>내용 텍스트</th>
							<td><input type="text" class="" name="contents[]" value="${retData.magazineList[0].contents }" style="width: 95%"></td>
						</tr>
						<tr>
							<th width="20%">내용 이미지</th>
							<td>
								<input type="hidden" name="no[]" value="${retData.magazineList[0].no }" />
								<c:import url="${properties.include_path}/fileUpload.jsp">
									<c:param name="fileIndex" value="101" />
									<c:param name="fileName" value="images[1]" />
									<c:param name="filePath" value="${retData.magazineList[0].img }" />
									<c:param name="includePath" value="${properties.img_path }" />
								</c:import>
							</td>
						</tr>
						<tr>
							<th>내용 텍스트</th>
							<td><input type="text" class="" name="contents[]" value="${retData.magazineList[1].contents }" style="width: 95%"></td>
						</tr>
						<tr>
							<th width="20%">내용 이미지</th>
							<td>
								<input type="hidden" name="no[]" value="${retData.magazineList[1].no }" />
								<c:import url="${properties.include_path}/fileUpload.jsp">
									<c:param name="fileIndex" value="102" />
									<c:param name="fileName" value="images[2]" />
									<c:param name="filePath" value="${retData.magazineList[1].img }" />
									<c:param name="includePath" value="${properties.img_path }" />
								</c:import>
							</td>
						</tr>
						<tr>
							<th>내용 텍스트</th>
							<td><input type="text" class="" name="contents[]" value="${retData.magazineList[2].contents }" style="width: 95%"></td>
						</tr>
						<tr>
							<th width="20%">내용 이미지</th>
							<td>
								<input type="hidden" name="no[]" value="${retData.magazineList[2].no }" />
								<c:import url="${properties.include_path}/fileUpload.jsp">
									<c:param name="fileIndex" value="103" />
									<c:param name="fileName" value="images[3]" />
									<c:param name="filePath" value="${retData.magazineList[2].img }" />
									<c:param name="includePath" value="${properties.img_path }" />
								</c:import>
							</td>
						</tr>
					</thead>
				</table>
			</c:if>
			
			<c:if test="${param.pageType ne 1 }">
				<table class = "datacList" style="margin: 50px 0px;">
					<thead>
						<tr>
							<th width="20%">내용 이미지</th>
							<td>
								<c:import url="${properties.include_path}/fileUpload.jsp">
									<c:param name="fileIndex" value="100" />
									<c:param name="fileName" value="image" />
									<c:param name="filePath" value="${data.img }" />
									<c:param name="includePath" value="${properties.img_path }" />
								</c:import>
							</td>
						</tr>
						<tr>
							<th>내용텍스트</th>
							<td><input type="text" class="" name="contents[]" value="${data.contents }" style="width: 95%"></td>
						</tr>
					</thead>
				</table>
			</c:if>
		
			
			<c:if test="${param.pageType eq 1 }">
				<table class = "datacList" style="margin: 55px 0px;">
					<thead class="productList">
						<tr>
							<th width="20%">관련상품추가</th>
							<td><input type="button" class="button medium white jAdd" value="추가"></td>
						</tr>
						
						<c:forEach var="row" items="${retData.productList }" varStatus="status">
							<tr>
								<th>관련상품</th>
								<td>
									<input type="hidden" name="productNumber" value="${row.no }" />
									<span>제품명 : ${row.productName }&nbsp; 제품번호 : ${row.productCode } &nbsp; 가맹점명 : ${row.franchiseName }</span>
									<input type="button" class="button medium white jDelete" value="삭제" _no="${row.no }" style="float: right; margin-right: 30px;">
								</td>
							</tr>
						</c:forEach>
					</thead>
				</table> 
			</c:if>
		
			</form>	
			<div class="btngroupcenter">
				<input type="button" class="button medium white jBack" value="목록으로">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="button medium white jSave" value="저장">
			</div>
		
		</div>
		
	</div>

<c:import url="${properties.include_path}/footer_admin.jsp"/>