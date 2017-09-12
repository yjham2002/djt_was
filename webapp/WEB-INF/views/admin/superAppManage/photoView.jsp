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

<script>

	var _rurl = "${rurl}";
	var balance = ${balance};
	
	$(document).ready(function(){
		//alert(balance);
		setPayment();
		checkType();
		setMessage($(".jMsgType").val());
		
		$(".jBack").click2(function(){
			location.href = _rurl;
		});
		
		$(".jMinus").click2(function(){
			var amount = parseInt($(".jAmount").val());

			$(".jAmount").val(amount - 1) ;
			
			if($(".jAmount").val() < 0) {
				$(".jAmount").val(0);
			}
			
			setPayment();
			
		});		

		$(".jPlus").click2(function(){
			var amount = parseInt($(".jAmount").val());
			
			$(".jAmount").val(++amount) ;
			
			setPayment();
		});		
		
		
		$("[name=type]").click2(function(){
			checkType();
		});
		
		$("[name=msgType]").change(function(){
			setMessage($(this).val());
		});
		
		$(".jOptionCheck").click2(function(){
			setPayment();
		});
		
		$(".jDirect").click2(function(){
			if($(this).is(":checked") == true){
				$(".jChangeType").toggle();
				$(".jMsgType").attr("disabled", true);
			} else {
				$(".jChangeType").toggle();
				$(".jMsgType").attr("disabled", false);
			}
				
		});
				
		$(".jBuy").click2(function(e){
			e.preventDefault();
			
			if(confirm("구매하시겠습니까?")) {
				
				$.ajax({
					type : "POST" , 
					dataType : "json" , 
					url : "/NaeKkot/admin/superOrder/savePurchase" , 
					data : $("#jData").serialize() , 
					success : function(data) {
						alert(data.returnMessage);
						if(data.returnCode == 1)
							location.replace(_rurl);
					}
				});
				
			}
			
		});
		
		
	});
	
	function setPayment(){
		
		var amount = parseInt($(".jAmount").val());
		var wholesalePrice = parseInt($(".jAmount").attr("pay"));
		var optionPrice = 0;
		
		$("[name='optionNumbers[]']:checked").each(function(){
			var price = $(this).attr("_p");
			optionPrice += parseInt(price);
		});
		
		var payment = wholesalePrice * amount + optionPrice;
				
		$("[name=payment]").val(payment);
	}

	
	function setMessage(message){
		
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
	
	
	function checkType(){
		if($("[name=type]:checked").val() == 1) {
			$(".jRibbon").show();
			$(".jCard").hide();
			$(".jTypeCard").prop("disabled", true);
			$(".jTypeRibbon").prop("disabled", false);
		} else {
			$(".jRibbon").hide();
			$(".jCard").show();
			$(".jTypeRibbon").prop("disabled", true);
			$(".jTypeCard").prop("disabled", false);
		}
		$("[name=message]").val("");
	}
	
</script>



<div id="Contents"  class="">
	<form id="jData" method="post" enctype="multipart/form-data">

	<h1>사진방[구매하기]</h1>
	<!-- location area -->
	<h2 style="width:98%;">
	</h2>

	<input type = "hidden" name = "franchiseNumber" value = "${data.franchiseNumber }"	/>
	<input type = "hidden" name = "userNumber" value = "${adminNumber }" />
	<input type = "hidden" name = "productNumber" value = "${data.productNumber }" />
	<input type = "hidden" name = "balance" value = "${balance }" />
	<input type = "hidden" name = "receiveSido" value = "${data.sido }" />
	<input type = "hidden" name = "receiveGugun" value = "${data.gugun }" />
	
	<div class="data" style="width:100%;">
		<table class="datacList" style="width:100%; margin-bottom: 50px;">
			<thead align="left">
				<c:set var="zero" value = "0" />
				<tr>
					<th width="10%">발주자</th>
					<td width="40%">${franchiseName }</td>
					<th width="10%">희망 배송일</th>
					<td width="40%">
						<select class="" name="hopeYear">
							<c:forEach begin="2016" end="2099" var="i">
								<option value="${i }" ${data.sHolidayYear == i ? 'selected' : '' }>${i }</option>
							</c:forEach>
						</select>&nbsp;년
						<select class="" name="hopeMon">
							<c:forEach begin="1" end="12" var="i">
								<c:if test = "${i < 10 }"><option value="0${i }" ${data.sHolidayMon == i ? 'selected' : '' }>0${i }</option></c:if>
								<c:if test = "${i >= 10 }"><option value="${i }" ${data.sHolidayMon == i ? 'selected' : '' }>${i }</option></c:if>								
							</c:forEach>
						</select>&nbsp;월
						<select class="" name="hopeDay">
							<c:forEach begin="1" end="31" var="i">
								<c:if test = "${i < 10 }"><option value="0${i }" ${data.sHolidayDay == i ? 'selected' : '' }>0${i }</option></c:if>
								<c:if test = "${i >= 10 }"><option value="${i }" ${data.sHolidayDay == i ? 'selected' : '' }>${i }</option></c:if>
							</c:forEach>
						</select>&nbsp;일 
						<select class="" name="hopeHour">
							<c:forEach begin="00" end="23" var="i">
								<c:if test = "${i < 10 }"><option value="0${i }" ${data.eHolidayYear == i ? 'selected' : '' }>0${i }</option></c:if>
								<c:if test = "${i >= 10 }"><option value="${i }" ${data.eHolidayYear == i ? 'selected' : '' }>${i }</option></c:if>
							</c:forEach>
						</select>&nbsp;시
						<select class="" name="hopeMin">
							<c:forEach begin="0" end="59" var="i">
								<c:if test = "${i < 10 }"><option value="0${i }" ${data.eHolidayMon == i ? 'selected' : '' }>0${i }</option></c:if>
								<c:if test = "${i >= 10 }"><option value="${i }" ${data.eHolidayMon == i ? 'selected' : '' }>${i }</option></c:if>
							</c:forEach>
						</select>&nbsp;분
					</td>
				</tr>
			
				<tr>
					<th>제품번호/주문자</th>
					<td>
						${data.productCode}/
						<input type="text" name="senderName" value="${adminName}" placeholder="이름" style="width: 80px;"> / 
						<input type="text" name="senderTel" value="${userTel}" style="width: 120px;" placeholder="전화번호"></td>
					<th>받는분</th>
					<td align="left">
						<input type="text" name="receiveName" value="${data.receiveName }" style="width: 45%;">
					</td>
				</tr>
			
				<tr>
					<th>수주자</th>
					<td>${data.franchiseName }</td>
					<th>배송지</th>
					<td>
						${data.sidoName}&nbsp;&nbsp;&nbsp;${data.gugunName }&nbsp;&nbsp;&nbsp;
						<input type="text" name="receiveAddress" value="${data.address }" style="width: 77.5%">
					</td>
				</tr>
			
				<tr>
					<th>상품지역</th>
					<td>${data.sidoName}&nbsp;&nbsp;&nbsp;${data.gugunName }&nbsp;&nbsp;&nbsp;</td>
					<th>받는분 연락처</th>
					<td><input type="text" name = "receiveTel" class="" value="" style="width: 95%"></td>
				</tr>
			
				 
				
			</thead>
		</table>
		
		<table class="datacList" style="width:100%; margin-bottom: 50px;">
			<thead align="left">
				<tr>
					<th width="10%">상품명</th>
					<td width="40%">${data.productName }</td>
					<th width="10%"></th>
					<td width="40%"></td>
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
								<td style = "border-style:none; "><input type="checkbox" class="jOptionCheck"  name="optionNumbers[]" value="${row.optionNumber }" _p = "${row.optionPrice }"></td>
							</tr>
						</c:forEach>
						</table>
					</td>
				</tr>
			
				<tr>
					<th>수량</th>
					<td>
						<input type="button" class="button medium white jMinus" value="-" >
						<input type="text" class="jAmount" name="amount" value="1" pay="${data.wholesalePrice }" readonly>
						<input type="button" class="button medium white jPlus" value="+" >
					</td>
					<th>총 결제 금액</th>
					<td><input type="text" name="payment" value="">원</td>
				</tr>
				
				<tr>
					<th>경조사어 종류</th>
					<td>
						<span>리본</span><input type="radio" class="" name="type" value="1" ${data.type eq 1 ? 'checked' : ''}>
						<span>카드</span><input type="radio" class="" name="type" value="2" ${data.type eq 2 ? 'checked' : ''}>
					</td>
					<th></th>
					<td></td>
				</tr>
				
				<tr>
					<th rowspan="2">경조사어 문구</th>
					<td class="jRibbon">
						<select class="jMsgType jTypeRibbon" name="msgType" style="float: right; min-width: 200px;">
							<c:forEach var="item" items="${msgTypeList}">
								<option value = "${item }" ${data.msgType eq item ? 'selected' : '' }>${item }</option>
							</c:forEach>
						</select>
						<br/>
						<div class="jChangeType">
							<select class="jMsgType jTypeRibbon" name="msgTypeMessage" style="float: right; min-width: 200px;">
								<option value="">경조사어 문구를 선택해 주세요.</option>
							</select>
						</div>
						<div class="jChangeType" style="display: none;">
							<input type="text" class="jTypeRibbon" name="msgTypeMessage" style="float: right; min-width: 200px;">
						</div>
					</td>
					<td class="jCard" style="display: none;">
						<textarea class="jTypeCard" name="message" style="width: 95%; height: 60px; resize:none;">${data.message }</textarea>
					</td>
					<th rowspan="2">배송시 요청사항</th>
					<td rowspan="2"><textarea name="requestTerm" style="width: 95%; height: 60px; resize:none;">${data.requestTerm }</textarea></td>
				</tr>
				<tr>
					<td class="jRibbon">
						<span style="float: left;">
							<input type="checkbox" class="jDirect" name="direct" value="Y" ${data.direct eq 'Y' ?'checked' : '' }><span>직접입력</span>
						</span>
						<input type="text" class="jTypeRibbon" name="message" value = "${data.message }" style="min-width: 200px; float: right;">
					</td>
				</tr>
				
			</thead>
		</table>
		
		<div class="btngroupcenter">		
			<input type="button" class="button medium white jBack" value="목록으로">
			<c:if test="${accessLevelCode eq 2 }">
				<input type="button" class="button medium white jBuy" value="구매하기">
			</c:if>
		</div>
		
	</div>
	</form>
</div>
<%@ include file="/WEB-INF/views/include/footer_admin.jsp"%>
