<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${accessLevelCode ne 2 }">
	<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
	<c:import url="${properties.include_path}/leftMenu_appManage.jsp"/>
</c:if>
<c:if test="${accessLevelCode eq 2 }">
	<c:import url="${properties.include_path}/header_admin.jsp"/>
	<c:import url="${properties.include_path}/leftMenu_admin.jsp"/>
</c:if>

<c:set var="data" value="${retData.data }" />

<c:set var="companyRate" value="${retData.discountInfo.companyDiscountRate }" />
<c:set var="userRate" value="${retData.discountInfo.userDiscountRate }" />


<script>

	var _rurl = "${rurl}";
	var obj = new Array();
	var type = 0;
	
	obj = {
			fn_init : function(){
				for(var i=0; i<6; i++){
					initFileUpload(100+i);
				}
				obj.fn_setGugun();
			},
			
			fn_setGugun : function(){
				var no = $("[name=sido] option:selected").val();

				$.ajax({
					url : rootPath + "/superUser/getGugunList",
					type : "POST" , 
					dataType : "json" , 
					data : {
						sidoNumber : no
					} , 
					success : function(data){
						$("[name=gugun]").empty();
						var html = "<option value = ''>구·군을 선택해주세요.</option>";
						for(var i = 0; i < data.entity.length; i++){
							var selected = "";
							if(data.entity[i].gugunNumber == "${data.gugun}"){
								selected = "selected";
							}
							html += "<option value = '"+data.entity[i].gugunNumber+"' "+selected+">"+data.entity[i].description+"</option>";
						}
						
						$("[name=gugun]").html(html);
					}
				});
				
			}
	}
	
	
	$(document).ready(function(){
		obj.fn_init();

		direct();
		
		$(".jBack").click2(function(){
			location.href = _rurl;
		});
		
		$(".jFranchiseAdd").click2(function(){
			$(".jDivAddReview").show();
		});
		
		$(".jAddReview").click2(function(){
			
			var no = $(this).attr("_no");
			var html = "<tr>";
			html += "<td width='10%''></td>";
			html += "<td colspan = '3'><input type='text' name='addReview' style='width: 45%; margin-right: 50px;'>";
			html += "<input type='button' class='button medium white jCreateReview' value='등록'  _no='" + no + "' _type = '2'></td>";
			html +="</tr>";
		
			var idx = $(".jAddReview").index(this);
			$("[id=addReview]").eq(idx).html(html);
			
			
			//$(this).parent().parent().after(html);
		});
		
		$(document).on("click", ".jCreateReview", function(){
			
			var text = $(this).siblings("input").val();
			var parentNumber = $(this).attr("_no");
			var type = $(this).attr("_type");
			
			$.ajax({
				url : rootPath + "/product/insertReview",
				data : "POST",
				dataType : "json",
				data : {
					contents : text,
					productNumber : '${param.productNumber}',
					userNumber : '${userNumber}',
					parentNumber : parentNumber , 
					type : type
				}, success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == 1){
						location.reload();						
					}
				}
			});
		});
			
		$(".jDeleteReview").click2(function(){
			
			var no = $(this).attr("_no");
			$.ajax({
				url : rootPath + "/product/deleteReview",
				data : "POST",
				dataType : "json",
				data : {
					reviewNumber : no
				}, success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == 1){
						location.reload();						
					}
				}
			});
		});
		
		$(".jModify").click2(function(){
			
			if($("[name=productName]").val() ==  ''){
				alert("상품명을 입력해주세요.");
				$("[name=productName]").fucus();
				return ;
			}

			if($("[name=gugun").val() ==  ''){
				alert("구·군을 선택해주세요");
				return ;
			}
			
			if(confirm("저장 하시겠 습니까?")){
				$("#jData").ajaxSubmit({
					url : rootPath + "/product/updateProduct",
					type : "post",
					forceSync : true,
					dataType : "json",
					success : function(data){
						alert(data.returnMessage);
						if(data.returnCode == "1") {
							location.replace(_rurl);
						}
					}
				});
				
			}
			
		});
		
		$(".jDirect").click2(function(){
			direct();
		});
			
		$(".jSido").change(function(){
			obj.fn_setGugun();
		});
			
		
	});
		
	
	function direct(){
		if($(".jDirect").is(":checked") == true){
			$(".jChangeTypeSelect").hide();
			$(".jChangeTypeMessage").show();
			$(".jMsgType").attr("disabled", true);
		} else {
			$(".jChangeTypeSelect").show();
			$(".jChangeTypeMessage").hide();
			$(".jMsgType").attr("disabled", false);
		}
			
	}
	 

</script>



<div id="Contents"  class="">

	<h1>상세보기</h1>
	<!-- location area -->
	<h2 style="width:98%;">
	</h2>
	
	<div class="data" style="width:100%;">
		
		<form id="jData" method="post" enctype="multipart/form-data">
		<input type="hidden" name="productNumber" value="${param.productNumber }">
		<input type="hidden" name="userNumber" value="${param.userNumber }">
		<table class="datacList" style="width:100%;">
			<thead align="left">
				<tr>
					<th width="10%">카테고리</th>
					<td width="40%">
						<select class="" name="category" style="width: 120px;">
						<c:forEach var="row" items="${retData.categoryList }">
							<option value="${row.categoryNumber}" ${row.categoryNumber == data.categoryNumber ? 'selected' : '' }>${row.categoryName}</option>
						</c:forEach>
						</select>
					</td>
					<th width="10%">제품번호</th>
					<td width="40%">
						<input type="text" name="productCode" value="${retData.productCode eq '' ? data.productCode : retData.productCode}" readonly="readonly" style="border: none;" />
					</td>
				</tr>
				<tr>
					<th>상품명</th>
					<td><input type="text" class="" name="productName" value="${data.productName }" style = "width:180px;"></td>
					<c:if test = "${param.productNumber ne null }">
					<th>적립포인트</th>
					<td>
						<span id = "companyPoint">기업 <fmt:formatNumber value="${data.retailPrice * companyRate / 100}" pattern="#,###.##"/> 포인트 </span>
						<span id = "userPoint">일반 <fmt:formatNumber value="${data.retailPrice * userRate / 100}" pattern="#,###.##"/>포인트</span> 
					</td>
					</c:if>
				</tr>
				<tr>
					<th>상품 이미지</th>
					<td>
						<c:forEach begin="0" end="5" var="i" >
							<c:set var="doneLoop" value="false"/>
								<c:forEach var="row" items="${retData.imgList}">
									<c:if test="${ not doneLoop}">
										<c:set var="filePath" value=""/>
										<c:set var="fileNumber" value="0"/>
										<c:if test="${i eq  row.ord }">
											<c:set var="filePath" value="${row.filePath }"/>
											<c:set var="fileNumber" value="${row.fileNumber }"/>
											<c:set var="doneLoop" value="true"/>
										</c:if>
									</c:if>
								</c:forEach>
							<input type="hidden" name="fileNumbers[]" value="${fileNumber}">
							<c:import url="${properties.include_path}/fileUpload.jsp">
								<c:param name="fileIndex" value="${100+ i }" />
								<c:param name="fileName" value="images[${i }]" />
								<c:param name="filePath" value="${ filePath}"/>
								<c:param name="includePath" value="${properties.img_path }"/>
							</c:import>
						</c:forEach>
					</td>
					<th>선택옵션</th>
					<td>
						<table style = "border-spacing:0px; border-style:none; padding:0px;">
						<c:forEach var="row" items="${retData.optionList }">
							<tr>
								<td style = "border-style:none; ">${ row.optionName}</td>
								<td style = "border-style:none; "><fmt:formatNumber value="${ row.optionPrice }" pattern="#,###.##원"/></td>
							</tr>
						</c:forEach>
						</table>
					</td>
				</tr>
			
				<tr>
					<th>도매금액</th>
					<td><input type="text" class="" name="wholesalePrice" value="${data.wholesalePrice }" style="text-align: right;"> 원</td>
					<th>소매금액</th>
					<td><input type="text" class="" name="retailPrice" value="${data.retailPrice }" style="text-align: right;"> 원</td>
				</tr>
				
				<tr>
					<th>타이틀제목</th>
					<td colspan="3"><input type="text" class="" name="productTitle" value="${data.productTitle }" style="width: 95%"></td>
				</tr>
				
				<tr>
					<th>지역</th>
					<td>
						<select class="jSido" name="sido" style="width: 120px;">
							<c:forEach var="sidoList" items="${retData.sidoList }">
								<option value="${sidoList.sidoNumber }" ${data.sido == sidoList.sidoNumber ? 'selected' : '' }>${sidoList.description }</option>
							</c:forEach>
						</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<select class="" name="gugun" style="width: 140px;">
						</select>
						
						
					</td>
					<th>해쉬태그</th>
					<td>
						<input type="text" class="" name="hashTag" value="${data.hashTag }" style="width: 150px;">
						<span>예시) #축결혼,#상조 복수 가능 , 로 구분</span>
					</td>
				</tr>
				
				<tr>
					<th height="200px">상세 설명</th>
					<td>
						<textarea name="detailDesc" style="width: 95%; height: auto; resize:none;">${data.detailDesc }</textarea>
					</td>
					<th height="200px">기타 내용</th>
					<td>
						<textarea name="contents" style="width: 95%; height: auto; resize:none;">${data.contents }</textarea>
					</td>
				</tr>
			</thead>
		</table>
		</form>
		<div class="btngroupcenter">		
			<input type="button" class="button bigrounded2 blue jBack" value="목록으로">
			<input type="button" class="button bigrounded2 blue jModify" value="저장">
		</div>
		
		<%--
		<div class="btngroupcenter" style="margin-bottom: 50px;">		
			<input type="button" class="button medium white jModify" value="정보 수정">
		</div>
		--%>
		<c:if test = "${param.productNumber ne null}">
		<h3>댓글리스트</h3>
		<div class="btngroupleft">		
			<input type="button" class="button medium white jFranchiseAdd" value="가맹점 댓글달기">
			<c:if test="${ accessLevelCode ne 2}">
				<input type="button" class="button medium white jSuperAdd" value="슈퍼관리자 댓글">
			</c:if>
		</div>
		<table class="datacList jDivAddReview" style="width:100%; display: none;">
			<thead align="left">
				<tr>
					<td width="10%"></td>
					<td>
						<input type="text" class="" name="addReview" value="" style="width: 55%">
						<input type="button" class="button medium white jCreateReview" value="작성완료" _type = "2">
					</td>
				</tr>
			</thead>
		</table>
		
		<table class="datacList" style="width:100%; margin-bottom: 50px;">
			<thead align="left">
			<c:forEach var="row" items="${retData.reviewList }">
				<c:choose>
					<c:when test="${row.reviewNumber eq row.parentReviewNumber }">
						<tr>
							<th width="10%">${row.writerID }</th>
							<td width="90%" colspan="8">
								<input type="text" class="" name="contents" value="${row.contents }" style="width: 55%">
								<span style="margin-left: 50px;">별점 : 
									<c:forEach begin="0" end="5" varStatus="status">
										<c:if test="${row.grade > status.index}">★</c:if>
										<c:if test="${row.grade < status.index}">☆</c:if>
									</c:forEach>
								</span>
								<input type="button" class="button medium white jDeleteReview" value="삭제" _no="${row.reviewNumber }" style="float: right;" >
								<input type="button" class="button medium white jAddReview" value="답글쓰기" _no="${row.reviewNumber }" style="margin-right: 50px;float: right;">
							</td>
						</tr>
						<tbody id = "addReview">
							
						</tbody>
					</c:when>
					
					<c:when test="${row.reviewNumber ne row.parentReviewNumber }">
						<tr>
							<td width="10%" align="center">└</td>
							<th width="10%">${row.writerID }</th>
							<td width="80%">
								<input type="text" class="" name="contents" value="${row.contents }" style="width: 55%">
								<input type="button" class="button medium white jDeleteReview" value="삭제" _no="${row.reviewNumber }" style="float: right;">
							</td>
						</tr>
					</c:when>
				</c:choose>
			</c:forEach>
			</thead>
		</table>
		</c:if>
		
	</div>
	
</div>
<%@ include file="/WEB-INF/views/include/footer_admin.jsp"%>
