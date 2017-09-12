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
		
		
		$("[name=check_all]").click2(function(e){
			e.stopPropagation();
			if(this.checked)
				$("[name='check_row[]']").prop("checked", true);
			else
				$("[name='check_row[]']").prop("checked", false);
		});
		
		$("[name='check_row[]']").click2(function(e){
			e.stopPropagation();
			if($("input:checkbox[name='check_row[]']").length == $("input:checkbox[name='check_row[]']:checked").length) {
				$("[name=check_all]").prop("checked", true);
			} else {
				$("[name=check_all]").prop("checked", false);
			}
		});
		
		$(".jView").click2(function(e){
			e.preventDefault();
			var no = $(this).attr("_no");
			
			location.href = rootPath + "/superUser/userView?userNumber=" + no+"&rurl="+encryptHexStr(document.URL);
			
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
		
		var cnt = 0;
		$(".cBill").on("click", ".jBill", function(e) {
			e.preventDefault();
			var no = $(this).attr("_no");
			var val = $(this).attr("val");
			
			var idx = $(".jBill").index(this);
			
			$.ajax({
				type : "POST" , 
				url : "/NaeKkot/admin/updatePurchaseBill" , 
				dataType : "json" , 
				data : {
					orderNumber : no , 
					val : val
				} , 
				success : function(data) {
					alert(data.returnMessage);
					
					if(data.returnCode == 1) {
						if(val == "Y") {
							$(".cBill").eq(idx).html("<span class = 'jBill' _no = '"+no+"' val = 'N' style = 'cursor:pointer;'>완료</span>");
						} else {
							$(".cBill").eq(idx).html("<input type='button' class='button searchsmall white jBill' value='대기'  _no = '"+no+"' val = 'Y'/>");
						}
					}
				} 
			});
			
		});

		$(".jBite").click2(function(e){
			e.preventDefault();
			
			var type = $(this).attr("type");
			var no = $(this).attr("no");
			var bite = 0;
			
			var obj = $(this);
			
			$('#jDialogArea').dialog({
				modal : true,
				title : "공제금액설정",
				open : function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
				width : 470,
				height : 150,
				resizable : false,
				open : function(){
					var data = {};
					$("#jDialogArea").show();
				},
				buttons: [
					{
						text: "저장",
						click: function() {
							bite = $("[name=bite]").val();
							
							if(bite == "") {
								alert("공제금액을 입력해주세요.");
								return;
							}
							
							$.ajax({
								type : "POST" , 
								url : "/NaeKkot/admin/updatePurchaseBite" , 
								dataType : "json" , 
								data : {
									orderNumber : no , 
									type : type , 
									bite : bite
								} , 
								success : function(data) {
									alert(data.returnMessage);
									if(data.returnCode == 1)
										location.reload();
									
									//obj.text(bite);
								}
							});
						}						
					},
					{
						text: "닫기",
						click: function() {
							$( this ).dialog( "close" );
						}
					}
				],
				closeOnEscape: false
			});			
			
		});
		
		$(".jCollect").click2(function(e){
			e.preventDefault();
			
			var no = $(this).attr("no");
			
			if(confirm("해당 구매내역을 정산하시겠습니까?")) {
				$.ajax({
					type : "POST" , 
					dataType : "json" , 
					url : "/NaeKkot/admin/setPurchaseCalcurate" , 
					data : {
						orderNumber : no , 
						type : 1
					} , 
					success : function(data) {
						alert(data.returnMessage);
						if(data.returnCode == 1)
							location.reload();
					}
				});
			}
			
		});
		
		$(".jDeposit").click2(function(e){
			e.preventDefault();
			var no = $(this).attr("no"); 
			
			if(confirm("해당 구매내역을 정산하시겠습니까?")) {
				$.ajax({
					type : "POST" , 
					dataType : "json" , 
					url : "/NaeKkot/admin/setPurchaseCalcurate" , 
					data : {
						orderNumber : no , 
						type : 2
					} , 
					success : function(data) {
						alert(data.returnMessage);
						if(data.returnCode == 1)
							location.reload();
					}
				});
			}
			
		});
		
		$(".jAllCalcurate").click2(function(e){
			e.preventDefault();
			
			if($("input:checkbox[name='check_row[]']:checked").length == 0) {
				alert("선택된 데이터가 없습니다.");
				return;
			}
			
			$.ajax({
				type : "POST" , 
				dataType : "json" , 
				url : "/NaeKkot/admin/setPurchaseCalcurateBulk" , 
				data : $("#checkForm").serialize() , 
				success : function(data) {
					alert(data.returnMessage);
					if(data.returnCode == 1)
						location.reload();
				}
			});
			
		});
		
		$(".jExcel").click2(function(){
			location.href = "/NaeKkot/admin/getPurchaseCalcurateExcel";
		});
		
		$(".conHeight").css({"height" : $(document).height() + "px"});

		$("input").keyup(function(e){
			
	        if(e.keyCode == 13){
	            $(".jSearch").trigger("click");
	        }
			
		});
	});
</script>
	<div id="Contents"  class="notice">
	    <h1>정산관리</h1>
		<h2 style="width:98%;">
			정산 관리 ><span> 주문별 정산</span>
		</h2>
		
		<div class = "data" style="overflow-y: hidden">
			<div style="clear:both">
			
				<fieldset class="search" style="margin-top:20px;">
					<div class="jSearchArea">
						<c:if test = "${accessLevelCode != 2 }">
						<label>
							<span>발주가맹점명</span>
							<input type = "text" name = "searchOrderFranchise" value = "${param.searchOrderFranchise }" style = "width:100px;"/>
						</label>
						</c:if>
						<label>
							<span>수주가맹점명</span>
							<input type = "text" name = "searchReceiptFranchise" value = "${param.searchReceiptFranchise }" style = "width:100px;"/>
						</label>
						<label>
							<span>상품명</span>
							<input type = "text" name = "searchProduct" value = "${param.searchProduct }" style = "width:100px;"/>
						</label>
						<label>
							<span>수금액완료</span>
							<select class="" name="isSend" style = "width:80px;">
								<option value="">전체</option>
								<option value="N" ${param.isSend eq 'N' ? 'selected' : '' }>대기</option>
								<option value="Y" ${param.isSend eq 'Y' ? 'selected' : '' }>완료</option>
							</select>
						</label>
						<label>
							<span>입금액완료</span>
							<select class="" name="isReceipt" style = "width:80px;">
								<option value="">전체</option>
								<option value="N" ${param.isReceipt eq 'N' ? 'selected' : '' }>대기</option>
								<option value="Y" ${param.isReceipt eq 'Y' ? 'selected' : '' }>완료</option>
							</select>
						</label>
					</div>
					
					<div class="jSearchArea" style = "margin-top:10px;">
						<label>
							<span>포인트</span>
							<select class="" name="usePoint" style = "width:80px;">
								<option value="">전체</option>
								<option value="Y" ${param.usePoint eq 'Y' ? 'selected' : '' }>사용</option>
								<option value="N" ${param.usePoint eq 'N' ? 'selected' : '' }>미사용</option>
							</select>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>주문자명</span>
							<input type = "text" name = "searchName" value = "${param.searchName }" style = "width:100px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>기간</span>
							<input type = "text" name = "sdate" value = "${param.sdate }" style = "width:100px;"/>&nbsp;&nbsp;&nbsp;~
							<input type = "text" name = "edate" value = "${param.edate }" style = "width:100px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>결제방법</span>
							<select class="" name="paymentType" style = "width:100px;">
								<option value="">전체</option>
								<option value="card" ${param.paymentType == 'card' ? 'selected' : '' }>간편카드결제</option>
								<option value="vcnt" ${param.paymentType == 'vcnt' ? 'selected' : '' }>가상계좌</option>
								<option value="acnt" ${param.paymentType == 'acnt' ? 'selected' : '' }>계좌이체</option>
								<option value="BLNC" ${param.paymentType == 'BLNC' ? 'selected' : '' }>웹발주</option>
							</select>
						</label>
					</div>
					
					<div class = "btngroupcenter">
						<input type="button" class="button medium white jSearch" value="검색">
					</div>
					
				</fieldset>
			</div>
			
			<div class = "btngroupcenter" style="margin-top: 20px;">
				<input type="button" class="button bigrounded2 white jExcel" value="엑셀다운">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="button medium white jAllCalcurate" value="선택 전체 정산">
			</div>

			<div class = "btngroupright">
				<select class="" name="">
					<option value="1">100개씩 보기</option>
				</select>
			</div>
			
			<table class = "datacList" style="margin: 15px 0px;">
				<thead>
					<tr>
						<th>계산식</th>
						<th>수익(수수료) = 적용금액 × 수수료율</th>
						<th>총공제금액 = 공제금액1 + 공제금액2 + 수익수수료</th>
						<th>수금액 = 적용금액 + 추가옵션 - 포인트</th>
						<th>입금액은 = 적용금액 + 추가옵션 - 총공제금액</th>
					</tr>
				</thead>
			</table>
			<form id = "checkForm">
			<table class = "datacList" style = "position:absolute; width:1200px; margin-left:-200px;">
				<thead>
					<tr>
						<th><input type="checkbox" class="jCheck_All" name = "check_all"/></th>
						<th>No</th>
						<th>주문일</th>
						<th>제품코드</th>
						<th>주문자이름</th>
						<th>상품명</th>
						<th>발주매장명</th>
						<th>발주업체<br/> 보유예치금</th>
						<th>수주매장명</th>
						<th>사용포인트</th>
						<th>수수료(%)</th>
						<th>도매가</th>
						<th>소매가</th>
						<th>적용금액</th>
						<th>선택옵션</th>
						<th>공제금액1</th>
						<th>공제금액2</th>
						<th>수익(수수료)</th>
						<th>총 공제금액</th>
						<th>결제방법</th>
						<th>수금액</th>
						<th>수금액 정산완료</th>
						<th>입금액</th>
						<th>입금액 정산완료</th>
						<th>세금계산서</th>
					</tr>
				</thead>
				<tbody style = "text-align:center;">
					<c:set var="vnum" value="${data.pager.virtualNum}"/>
					<c:forEach var="row" items="${data.list }" varStatus="status">
						<c:set var="totalBite" value = "${row.commissionPrice + row.bite1 + row.bite2 }" />
						<tr>
							<td><input type = "checkbox" class="jCheck_Each" name = "check_row[]" value = "${row.orderNumber}"/></td>
							<td>
								${status.index + 1}
							</td>
							<td>${row.regDate }</td>
							<td>${row.productCode }</td>
							<td>${row.orderFranchiseName }</td>
							<td>${row.productName }</td>
							<td>${row.orderFranchiseName }</td>
							<td><fmt:formatNumber value="${row.orderFranchiseBalance}" pattern="#,###.##"/></td>
							<td>${row.franchiseName }</td>
							<td><fmt:formatNumber value="${row.usePoint }" pattern="#,###.##"/></td>
							<td>${row.commission }%</td>
							<td><fmt:formatNumber value="${row.wholesalePrice }" pattern="#,###.##"/></td>
							<td><fmt:formatNumber value="${row.retailPrice }" pattern="#,###.##"/></td>
							<td><fmt:formatNumber value="${row.price }" pattern="#,###.##"/></td>
							<td><fmt:formatNumber value="${row.optionPrice }" pattern="#,###.##"/></td>
							<td class = "${accessLevelCode == 2 ? '' : 'jBite' }" type = "1" no = "${row.orderNumber }"><fmt:formatNumber value="${row.bite1 }" pattern="#,###.##"/></td>
							<td class = "${accessLevelCode == 2 ? '' : 'jBite' }" type = "2" no = "${row.orderNumber }"><fmt:formatNumber value="${row.bite2 }" pattern="#,###.##"/></td>
							<td><fmt:formatNumber value="${row.commissionPrice }" pattern="#,###.##"/></td>
							<td><fmt:formatNumber value="${totalBite }" pattern="#,###.##"/></td>
							<td>
								<c:choose>
									<c:when test = "${row.paymentType == 'card'}">간편카드결제</c:when>
									<c:when test = "${row.paymentType == 'vcnt'}">가상계좌</c:when>
									<c:when test = "${row.paymentType == 'acnt'}">무통장</c:when>
									<c:when test = "${row.paymentType == 'BLNC'}">예치금</c:when>
								</c:choose>							
							</td>
							<td>
								<c:if test = "${accessLevelCode != 2 }">
									<c:if test = "${row.collectDate eq null }">
										<a href = "#" class = "button searchsmall white jCollect" no = "${row.orderNumber }">
											<fmt:formatNumber value="${row.price + row.optionPrice - row.usePoint }" pattern="#,###.##"/><br/>대기
										</a>
									</c:if>
									<c:if test = "${row.collectDate ne null }">
										<a href = "#" class = "button searchsmall white" no = "${row.orderNumber }">
											<fmt:formatNumber value="${row.price + row.optionPrice - row.usePoint }" pattern="#,###.##"/><br/>완료
										</a>
									</c:if>
								</c:if>
								<c:if test = "${accessLevelCode == 2 }">
									<b>
									<c:if test = "${row.collectDate eq null }">
										<fmt:formatNumber value="${row.price + row.optionPrice - row.usePoint }" pattern="#,###.##"/><br/>대기
									</c:if>
									<c:if test = "${row.collectDate ne null }">
										<fmt:formatNumber value="${row.price + row.optionPrice - row.usePoint }" pattern="#,###.##"/><br/>완료
									</c:if>
									</b>
								</c:if>
							</td>
							<td>${row.collectDate }</td>
							<td>
								<c:if test = "${accessLevelCode != 2 }">
									<c:if test = "${row.depositDate eq null }">
										<a href = "#" class = "button searchsmall white jDeposit" no = "${row.orderNumber }">
											<fmt:formatNumber value="${row.price + row.optionPrice - totalBite }" pattern="#,###.##"/><br/>대기
										</a>
									</c:if>
									<c:if test = "${row.depositDate ne null }">
										<a href = "#" class = "button searchsmall white" no = "${row.orderNumber }">
											<fmt:formatNumber value="${row.price + row.optionPrice - totalBite }" pattern="#,###.##"/><br/>완료
										</a>								
									</c:if>
								</c:if>
								<c:if test = "${accessLevelCode == 2 }">
									<b>
									<c:if test = "${row.depositDate eq null }">
										<fmt:formatNumber value="${row.price + row.optionPrice - totalBite }" pattern="#,###.##"/><br/>대기
									</c:if>
									<c:if test = "${row.depositDate ne null }">
										<fmt:formatNumber value="${row.price + row.optionPrice - totalBite }" pattern="#,###.##"/><br/>완료
									</c:if>
									</b>
								</c:if>
							</td>
							<td>${row.depositDate}</td>
							<td class = "cBill">
								<c:if test = "${accessLevelCode != 2 }">
									<c:if test = "${row.isBill == 'N' }">
										<input type="button" class="button searchsmall white jBill" value="대기"  _no = "${row.orderNumber }" val = "Y"/>
									</c:if>
									<c:if test = "${row.isBill == 'Y' }">
										<span class = "jBill" _no = "${row.orderNumber }" val = "N" style = "cursor:pointer;">완료</span>
									</c:if>
								</c:if>
								<c:if test = "${accessLevelCode == 2 }">
									<c:if test = "${row.isBill == 'N' }">
										대기
									</c:if>
									<c:if test = "${row.isBill == 'Y' }">
										완료
									</c:if>								
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</form> 

			<div class="pageNumber">
				<c:set var="pager" value="${retData.paging}"/>
				${pager }
			</div>
			
			<div id="jDialogArea" style = "display:none;">
				<table class = "datav" style = "width:450px;">
					<tr>
						<th class = "b">공제금액</th>
						<td class = "l"><input type = "text" name = "bite" /></td>
					</tr>
				</table>
			</div>

		</div>
	</div>
	
<c:import url="${properties.include_path}/footer_admin.jsp"/>

