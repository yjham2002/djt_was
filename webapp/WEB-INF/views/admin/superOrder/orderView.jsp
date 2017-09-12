<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${accessLevelCode ne 2 }">
	<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
	<c:import url="${properties.include_path}/leftMenu_admin.jsp"/>
</c:if>
<c:if test="${accessLevelCode eq 2 }">
	<c:import url="${properties.include_path}/header_admin.jsp"/>
	<c:import url="${properties.include_path}/leftMenu_admin.jsp"/>
</c:if>

<c:set var="data" value="${retData.data }" />

<script>

	var _rurl = "${rurl}";
	
	$(document).ready(function(){
		
		if(getCookie("isSuperAdmin") != "1" && "${accessLevelCode}" == 2) {	//일반관리자일때
			$("input[type='text']").prop("disabled", true);
			$("input[type='radio']").prop("disabled", true);
			$("input[type='checkbox']").prop("disabled", true);
			$("textarea").prop("disabled", true);
			$("select").prop("disabled", true);
			if("${param.pageType}" == 1) {
				$("[name='senderTel']").hide();
			}
			
			$("[name='processCode']").prop("disabled", false);
			$("[name='acceptorName']").prop("disabled", false);
			$("[name='companyMemo']").prop("disabled", false);
		} else {
			$(".jSecret").show();
		}
		
		
		
		direct();
		
		checkType();
		initFileUpload(100);
		
		
		setMessage($(".jMsgType").val());
		
		if(getCookie("isSuperAdmin") == "1") {
			$(".jTransfer").show();
		}
		
		$(".jBack").click2(function(){
			location.href = _rurl;
		});
		
		$(".jSave").click2(function(){
			
			$("select").prop("disabled", false);
			$("input").prop("disabled", false);
			$("textarea").prop("disabled", false);
			
			if(confirm("저장 하시겠습니까?")){
				$("#jData").ajaxSubmit({
					url : rootPath + "/superUser/updateOrder",
					type : "post",
					forceSync : true,
					dataType : "json",
					success : function(data){
						alert(data.returnMessage);
						if(data.returnCode == "1") {
	//						location.href=_rurl;
							location.reload();
						}
					}
				});
			}
		});
		

		$(".jMsgType").change(function(){
			setMessage($(this).val());
		});
		
		$(".jStatus").change(function(){
			
			if($(this).val() == 'PYMT0060'){
				$(".jComplete").show();
			} else {
				$(".jComplete").hide();
			}
			
		});
		
		$(".jTransfer").click2(function(){
			var orderNumber = $(this).attr("_no");
			window.open(rootPath + "/superOrder/popup?orderNumber="+orderNumber, "expand", "width=600, height=500,top=150, left=250, scrollbars=yes") ;
		});
		
		$("[name=pType]").click2(function(){
			checkType();
		});
		
		$(".jDirect").click2(function(){
			direct();
		});
		
		$(".jSestore").click2(function(){
			
		});
		
		$(".jPrint").click2(function(){
			window.print();
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
	
	
	function setMessage(message){
		
		if(message != ''){
			
			$.ajax({
				url : rootPath + "/superOrder/getMessage",
				type : "POST",
				dataType : "json",
				data : {
					msgType : message
				}
				, success : function(data){
	
					$("[name=msgTypeMessage]").empty();
					var html = "<option value = ''>문구를 선택해주세요.</option>";
					for(var i = 0; i < data.entity.length; i++){
						var selected = "";
						if(data.entity[i].message == "${data.msgTypeMessage}"){
							selected = "selected";
						}
						html += "<option value = '"+data.entity[i].message+"' "+selected+">"+data.entity[i].message+"</option>";
					}
					
					$("[name=msgTypeMessage]").html(html);
	
				}
			});
			
		}
		
	}
	
	function checkType(){
		if($("[name=pType]:checked").val() == 1) {
			$(".jRibbon").show();
			$(".jCard").hide();
		} else {
			$(".jRibbon").hide();
			$(".jCard").show();
		}
	}
	
	
</script>



<div id="Contents"  class="">

	<h1>상세보기</h1>
	<!-- location area -->
	<h2 style="width:98%;">
	</h2>
	
	<form id="jData" method="post" enctype="multipart/form-data">
	<div class="data" style="width:100%;">
	<input type="hidden" name="orderNumber" value="${param.number }">
		<table class="datacList" style="width:100%; margin-bottom: 50px;">
			<thead align="left">
			
				<tr>
					<th width="10%">주문상태</th>
					<td colspan="3">
						<select class="jStatus" name="processCode" _no="${data.orderNumber }">
							<option value="PYMT0010" ${data.processCode eq 'PYMT0010' ? 'selected' : '' }>미확인</option>
							<option value="PYMT0040" ${data.processCode eq 'PYMT0040' ? 'selected' : '' }>주문접수</option>
							<option value="PYMT0050" ${data.processCode eq 'PYMT0050' ? 'selected' : '' }>배송중</option>
							<option value="PYMT0060" ${data.processCode eq 'PYMT0060' ? 'selected' : '' }>배송완료</option>
							<option value="PYMT0070" ${data.processCode eq 'PYMT0070' ? 'selected' : '' }>결제취소</option>
							<option value="" ${row.processCode eq '' ? 'selected' : '' }>보류</option>
						</select>
					</td>
					<th width="10%">제품번호/주문자</th>
					<td width="40%">${data.productCode}
						/ <input type="text" class="jSecret" name="senderName" value="${data.senderName}" style="width: 80px; ${data.secret eq 'Y' ? 'display:none' : '' }"> / <input type="text" class="jSecret" name="senderTel" value="${data.senderTel}" style="width: 120px; ${data.secret eq 'Y' ? 'display:none' : '' }">
						<%-- <c:if test="${data.secret eq 'Y' and accessLevelCode ne 2}">
							/ <input type="text" name="senderName" value="${data.senderName}" style="width: 80px;"> / <input type="text" name="senderTel" value="${data.senderTel}" style="width: 120px;">
						</c:if> --%>
					</td>
				</tr>
				<tr class="jComplete" style="display: ${data.processCode eq 'PYMT0060' ? '' : 'none' }">
					<th>배송사진등록</th>
					<td colspan="3">
						<c:import url="${properties.include_path}/fileUpload.jsp">
							<c:param name="fileIndex" value="100" />
							<c:param name="fileName" value="images[0]" />
							<c:param name="filePath" value="${data.purchaseImg }" />
							<c:param name="includePath" value="${param.includePath}" />
							
						</c:import>
					</td>
					<th>인수자명</th>
					<td><input type="text" class="" name="acceptorName" value="${data.acceptorName }" style="width: 90%"></td>
				</tr>
				<tr>
					<th>주문확인일</th>
					<td  colspan="3">${data.confirmDate }</td>
					<th>수주자</th>
					<td>
						${data.franchiseName }&nbsp;&nbsp;
						<input type="button" class="button medium white jTransfer" value="인수인계" _no="${data.orderNumber }" style="${accessLevelCode eq 4 ? '' : 'display: none;'}">
						<c:if test="${data.takeoverDate ne '' and data.takeoverDate ne null}">
							<span>인수인계 날짜 : ${data.takeoverDate }</span>
						</c:if>
					</td>
				</tr>
			
				<tr>
					<th>발주자</th>
					<td colspan="3">${data.userName }</td>
					<th>희망배송일</th>
					<td>
						<select class="" name="hopeYear">
							<c:forEach begin="2016" end="2099" var="i">
								<option value="${i }" ${data.hopeYear == i ? 'selected' : '' }>${i }</option>
							</c:forEach>
						</select>&nbsp;년
						<select class="" name="hopeMon">
							<c:forEach begin="1" end="12" var="i">
								<option value="${i }" ${data.hopeMon == i ? 'selected' : '' }>${i }</option>
							</c:forEach>
						</select>&nbsp;월
						<select class="" name="hopeDay">
							<c:forEach begin="1" end="31" var="i">
								<option value="${i }" ${data.hopeDay == i ? 'selected' : '' }>${i }</option>
							</c:forEach>
						</select>&nbsp;일 
						<select class="" name="hopeHour">
							<c:forEach begin="0" end="23" var="i">
								<option value="${i }" ${data.hopeHour == i ? 'selected' : '' }>${i }</option>
							</c:forEach>
						</select>&nbsp;시
						<select class="" name="hopeMin">
							<c:forEach begin="0" end="59" var="i">
								<option value="${i }" ${data.hopeMin == i ? 'selected' : '' }>${i }</option>
							</c:forEach>
						</select>&nbsp;분 
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					
					</td>
				</tr>
				
				<tr>
					<th>주문접수일</th>
					<td>${data.regDate }</td>
					<th>배송완료일</th>
					<td><input type="text" name="deliveryDate" value="${data.deliveryDate }" readonly="readonly" style="border: none;"/></td>
					<th>받는분</th>
					<td>${data.receiveName }</td>
				</tr>
				
				<tr>
					<th>금액</th>
					<td>
						<p style="${data.accessLevelCode eq '1' ? 'color: blue' : ''}">소매가 : <fmt:formatNumber value="${data.retailPrice}" pattern="#,###" />원</p>
						<p style="${data.accessLevelCode eq '2' ? 'color: red' : ''}">도매가 : <fmt:formatNumber value="${data.wholesalePrice}" pattern="#,###" />원</p>
					</td>
					<th>적용금액</th>
					<td><fmt:formatNumber value="${data.payment}" pattern="#,###" /> 원</td>
					<th>배송지</th>
					<td>${data.sidoName }&nbsp; ${data.gugunName }&nbsp; ${data.receiveAddress }</td>
				</tr>
				
				<tr>
					<th>상품지역</th>
					<td colspan="3">${data.PDsidoName }&nbsp; ${data.PDgugunName }</td>
					<th>받는분 연락처</th>
					<td>${data.receiveTel }</td>
				</tr>
				
			</thead>
		</table>
		
		<table class="datacList" style="width:100%; margin-bottom: 50px;">
			<thead align="left">
			
				<tr>
					<th width="10%">상품명</th>
					<td width="40%">${data.productTitle }</td>
					<th width="10%">적립포인트</th>
					<td width="40%">
						<span id = "companyPoint">기업 <fmt:formatNumber value="${data.retailPrice * companyRate / 100}" pattern="#,###.##"/> 포인트 </span>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span id = "userPoint">일반 <fmt:formatNumber value="${data.retailPrice * userRate / 100}" pattern="#,###.##"/>포인트</span>
					</td>
				</tr>
			
				<tr>
					<th>상품 이미지</th>
					<td>
						<ul style = "padding:5px;">
						<c:forEach var="imgList" items="${retData.imgList }">
							<li style = "float:left; width:30%; padding:5px;"><img src="${properties.img_path}/100/${imgList.filePath}" width="90px" height="75px"></li>
						</c:forEach>
						</ul>
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
					<th>수량</th>
					<td>${data.amount }</td>
					<th>사용 포인트</th>
					<td>${data.usePoint }</td>
				</tr>
				
				<tr>
					<th>결제방법</th>
					<td>
						<c:choose>
							<c:when test = "${data.paymentType == 'card' }">간편 카드결제</c:when>
							<c:when test = "${data.paymentType == 'mobx' }">핸드폰 결제</c:when>
							<c:when test = "${data.paymentType == 'vcnt' }">가상계좌</c:when>
							<c:when test = "${data.paymentType == 'acnt' }">무통장 입금</c:when>
						</c:choose>
					</td>
					<th>총 결제금액</th>
					<td><fmt:formatNumber value="${data.payment }" pattern="#,###.##"/>원</td>
				</tr>
				
				<tr>
					<th>경조사어 종류</th>
					<td>
						<span>리본</span><input type="radio" class="" name="pType" value="1" ${data.type eq 1 ? 'checked' : ''}>
						<span>카드</span><input type="radio" class="" name="pType" value="2" ${data.type eq 2 ? 'checked' : ''}>
					</td>
					<th>비밀배송</th>
					<td><input type="checkbox" name="secret" value="Y" ${data.secret eq 'Y' ? 'checked' : '' }><span>보내는 사람 정보 비밀로 배송하기</span></td>
				</tr>
				
				<tr>
					<th rowspan="2">경조사어 문구</th>
					<td class="jRibbon">
						<select class="jMsgType" name="msgType" style="float: right; min-width: 200px;">
							<option value=""></option>
							<c:forEach var="item" items="${msgTypeList}">
								<option value = "${item }" ${data.msgType eq item ? 'selected' : '' }>${item }</option>
							</c:forEach>
						</select>
						<br/>
						<div class="jChangeTypeSelect">
							<select class="" name="msgTypeMessage" style="float: right; min-width: 200px;">
								<option value="">경조사어 문구를 선택해 주세요.</option>
							</select>
						</div>
						<div class="jChangeTypeMessage" style="display: none;">
							<input type="text" name="msgTypeMessage" value="" style="float: right; min-width: 200px;">
						</div>
					</td>
					<td class="jCard" style="display: none;">
						<textarea name="message" style="width: 95%; height: 60px; resize:none;">${data.message }</textarea>
					</td>
					<th rowspan="2">배송시 요청사항</th>
					<td rowspan="2"><textarea name="requestTerm" style="width: 95%; height: 60px; resize:none;">${data.requestTerm }</textarea></td>
				</tr>
				<tr>
					<td class="jRibbon">
						<span style="float: left;">
							<input type="checkbox" class="jDirect" name="direct" value="Y" ${data.direct eq 'Y' ?'checked' : '' }><span>직접입력</span>
						</span>
						<input type="text" name="message" value = "${data.message }" style="min-width: 200px; float: right;">
					</td>
					<td></td>
				</tr>
				
			</thead>
		</table>
		
		<table class="datacList" style="width:100%; margin-bottom: 50px;">
			<thead align="left">
				<tr>
				<c:if test="${accessLevelCode ne 2 }">
					<th width="10%">관리자 메모</th>
					<td width="40%">
						<input type="text" class="" name="adminMemo" value="${data.adminMemo }" style="width: 95%">
					</td>
				</c:if>
					<th width="10%">가맹점메모</th>
					<td width="40%">
						<input type="text" class="" name="companyMemo" value="${data.companyMemo }" style="width: 95%">
					</td>
				</tr>
			
			</thead>
		</table>
		
		<div class="btngroupcenter">		
			<input type="button" class="button medium white jBack" value="목록으로">
			<%-- <c:if test="${accessLevelCode ne 2}"> --%>
			<input type="button" class="button medium white jSave" value="저장">
			<%-- </c:if> --%>
			<c:choose>
				<c:when test="${data.processCode eq 'PYMT0070'}">
					<input type="button" class="button medium white jSestore" value="복구">
				</c:when>
				<c:when test="${data.processCode ne 'PYMT0070'}">
					<input type="button" class="button medium white jPrint" value="인쇄">
				</c:when>
			</c:choose>
	</div>
	</form>	
</div>
<%@ include file="/WEB-INF/views/include/footer_admin.jsp"%>
