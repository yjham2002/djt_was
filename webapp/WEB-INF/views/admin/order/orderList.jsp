<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_admin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_admin.jsp"/>

<script type = "text/javascript">

	var sec = 60;
	var processCode = "${param.processCode}";

	$(document).ready(function(){
		
		if(getCookie("isSuperAdmin") == "1") {	//슈퍼 관리자일때
			$(".jStatus").prop("disabled", false);
		}
		
		//=====프레입웤 변수들==================================================================================//
		var FORM_TARGET_CLS_NM		= "#jData"	;		// 폼을 동적 wrap 할 타겟 ID이름
		var FORM_NAME				= "nf"		;		// 폼이름
		var FORM_METHOD				= "GET"		;		// 폼 메쏘드
		var FORM_USE_FILE			= false		;		// 파일폼 사용 여부
		var FORM_ACTION				= document.URL ; 

		var NEXT_CMD				= ""			;		// 다은 수행 할 cmd
		//=====================================================================================================//

		var jForm = $(FORM_TARGET_CLS_NM).mf({ "name" : FORM_NAME , "method" : FORM_METHOD , "action" : FORM_ACTION , "useFile" : FORM_USE_FILE }) ;
		
		for(var i=0; i<'${retData.pager.virtualNum}'; i++){
			setScaleViewEvent(i);
		}
		
		$(".datepicker").datepicker({
			dateFormat: 'yy-mm-dd'
		});
		
		$(document).on("click", ".jPage", function(e){
			e.preventDefault();
			jForm.ci();
			jForm.mi("cpage", $(this).attr("page"));
			jForm.submit();
		});
		
		$(document).on("click", ".jSearch", function(){
			jForm.ci();
			jForm.submit();
		});
		
		// 검색 버튼 enter 허용
		$("input[type='text']").keyup(function(e){
			if (e.keyCode == 13) $(".jSearch").trigger("click");
		});
		
		// checkbox 전체 선택 n 해제
		$(".jCheck_All").click2(function(){
			if(this.checked){
				$(".jCheck_Each").prop("checked", true);
			}
			else{
				$(".jCheck_Each").prop("checked", false);
			}
		});

		// checkbox 단일 선택시 전체 checkbox 상태 변화
		$(".jCheck_Each").click2(function(){
			if($("[name='orderNumbers[]']:checkbox:checked").length==$("[name='orderNumbers[]']").length){
				$(".jCheck_All").prop("checked", true);
			}else {
				$(".jCheck_All").prop("checked", false);
			}
		});
		
		// 선택된 항목들 삭제
		$(document).on("click", ".jDelete", function(){
			var jDel = $(".jCheck_Each:checkbox:checked").length;
			if(jDel <= 0){
				alert("선택된 항목이 없습니다.");
			}else {
				if(!confirm("삭제하시겠습니까?"))	return false;
				
				var orderNumbers = new Array();
				$(".jCheck_Each:checked").each(function(i){
					orderNumbers[i] = $(this).val();
				});
				
				$.ajax({
					url : rootPath + "/superOrder/deleteOrder",
					type : "POST",
					dataType : "json" ,
					data : {
						orderNumbers : orderNumbers,
					},
					success : function(data){
						alert(data.returnMessage);
						if(data.returnCode == 1) {
							location.reload();
						}
					}
				});

			}
		});
		
		
		$(document).on("click", ".jView", function(){
			var number = $(this).attr("_no");
			location.href=rootPath+"/superOrder/orderView?number="+number+"&pageType="+${param.pageType}+"&rurl="+encryptHexStr(document.URL);
		});
		
		$(document).on("change", ".jStatus", function(){
			
			var no = $(this).attr("_no");
			var status = $(this).val();
			$.ajax({
				url : rootPath + "/superOrder/updateOrderStatus",
				type : "POST",
				dataType : "json" ,
				data : {
					orderNumber : no,
					processCode : status
				},
				success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == 1) {
						if(status == 'PYMT0060'){
							location.href=rootPath+"/superOrder/orderView?number="+no+"&rurl="+encryptHexStr(document.URL);
						}else{
							location.reload();
						}
					}
				}
			});			
		});

		setInterval(function(){
			tictoc();
		}, 1000);
		
		
		$(".jExcel").click2(function(){
			var pageType = "${param.pageType}";
			var userNumber = "${param.userNumber}";
			location.href = rootPath + "/order/downExcel?pageType="+pageType+"&userNumber="+userNumber;
		});
		
		$("[name=rowPerPage]").change(function(){
			jForm.mi("pageType", "${param.pageType}");
			jForm.mi("rowPerPage", $(this).val());
			jForm.submit();
		});
		
		
	});
	
	function tictoc(){
		var msg = "갱신주기 : (" + sec + " / 60 초)";
		sec--;
		if(sec < 0)	{
			sec = 60;
			$("#jData").ajaxSubmit({
				url : rootPath+"/order/refreshOrderList",
				type : "POST",
				forceSync : true,
				dataType : "HTML",
				success : function(data){
					$(".jReloadArea").html(data);
				}
			});
		}
		$(".tictoc").text(msg);
	}
	
	
	function setScaleViewEvent(index)
	{
		$('#previewFile' + index).unbind("hover");
		$('#previewFile' + index).hover(
			function()
			{
				// var left = event.clientX + 10;
				// var top = event.clientY;
				var top = $(this).offset().top - ($('#priviewFileScale' + index).height() / 2);
				var left = $(this).offset().left + $(this).width() - 300;
				$('#priviewFileScale' + index)
					.css("top", top)
					.css("left", left)
					.show();
			},
			function()
			{
				$('#priviewFileScale' + index).hide();
			}
		);

	}
	
</script>

<div id="Contents"  class="notice">
    <h1>
		<c:choose>
			<c:when test="${param.pageType eq 1 }">
				수주내역
			</c:when>
			<c:when test="${param.pageType eq 2 }">
				발주내역
			</c:when>
		</c:choose>
	</h1>
		
	<div class = "data">
		<form id="jData" method="post" enctype="multipart/form-data">
			<div style="clear:both">
				
				<input type="hidden" name="pageType" value="${param.pageType }">
				<input type="hidden" name="userNumber" value="${userNumber }">
				<fieldset class="search" style="margin-top:20px; text-align: center;">
					<div class="jSearchArea">
						<label>
							<span>발주자</span>
							<input type = "text" name = "userName" value = "${param.userName }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>수주자</span>
							<input type = "text" name = "franchiseName" value = "${param.franchiseName }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>받는분</span>
							<input type = "text" name = "receiveName" value = "${param.receiveName }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>제품번호</span>
							<input type = "text" name = "productCode" value = "${param.productCode }" style = "width:120px;"/>
						</label>				
					</div>
						
					<div class = "jSearchArea" style = "margin-top:10px; ">
						<c:if test="${param.pageType eq 1 }">
							<label>
								<span>주문상태</span>
								<select name = "processCode" style="width: 120px;">
									<option value = "">전체</option>
									<option value = "PYMT0010" ${param.processCode == 'PYMT0010' ? 'selected' : '' }>미확인</option>
									<option value = "PYMT0020" ${param.processCode == 'PYMT0020' ? 'selected' : ''}>입금대기</option>
									<!--option value = "0030" ${param.isApplyID == 0030 ? 'selected' : ''}>주문확인중</option-->
									<option value = "PYMT0040" ${param.processCode == 'PYMT0040' ? 'selected' : ''}>주문접수</option>
									<option value = "PYMT0050" ${param.processCode == 'PYMT0050' ? 'selected' : ''}>배송중</option>
									<option value = "PYMT0060" ${param.processCode == 'PYMT0060' ? 'selected' : ''}>배송완료</option>
									<option value = "PYMT0070" ${param.processCode == 'PYMT0070' ? 'selected' : ''}>결제취소</option>
									<%-- <option value = "PYMT0090" ${param.processCode == 'PYMT0090' ? 'selected' : ''}>보류</option> --%>
								</select>
							</label>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</c:if>
						<label>
							<span>주문일</span>
							<input type="text" class="datepicker" name = "sDate" value = "${param.sDate }" style="width:120px;"/>&nbsp;&nbsp;~&nbsp;&nbsp;
							<input type="text" class="datepicker" name = "eDate" value = "${param.eDate }" style="width:120px;"/>
						</label>
					</div>
						
					<div class = "jSearchArea btngroupcenter">
						<input type="button" class="button medium white jSearch" value="검색">
					</div>
				</fieldset>
			</div>
		</form>
	</div>			
	
	<div class = "jSearchArea btngroupright">
		<input type="button" class="button medium white jExcel" value="엑셀" />&nbsp;&nbsp;&nbsp;
		<input type="button" class="button medium white jDelete" value="선택삭제" />&nbsp;&nbsp;&nbsp;
		<select class="" name="rowPerPage">
			<option value="100" ${param.rowPerPage eq 100 ?  'selected' : '' }>100개씩 보기</option>
			<option value="9999" ${param.rowPerPage eq 9999 ? 'selected' : '' }>전체보기</option>
		</select>
	</div>
			 
	<span class="tictoc" style=" font-size: 15px;">갱신주기 : (60 / 60 초)</span>
			
	<div class="jReloadArea">
		<table class = "datacList" style="margin-top: 15px;">
			<thead>
				<tr>
					<th><input type="checkbox" class="jCheck_All"/></th>
					<th>No</th>
					<th width="8%">주문접수일</th>
					<th>제품번호</th>
					<th>발주자</th>
					<th>수주자</th>
					<th width="8%">배송일</th>
					<th>받는분</th>
					<th width="6%">배송지</th>
					<th width="10%">금액</th>
					<th>적용금액</th>
					<th>배송사진</th>
					<th>주문상태</th>
				</tr>
			</thead>
			<tbody style = "text-align:center;">
				<c:set var="vnum" value="${retData.pager.virtualNum}"/>
				<c:forEach var="row" items="${retData.list }" varStatus="status">
					<c:if test="${row.processCode ne 'PYMT0020' or param.pageType ne 1}">
					<tr>
						<td><input type = "checkbox" class="jCheck_Each" name = "orderNumbers[]" value = "${row.orderNumber}"/></td>
						<td>
							${vnum - status.index}
						</td>
						<td><%-- <fmt:formatDate value="${row.regDate}" pattern="yyyy-MM-dd"/>--%> ${row.regDate }</td>
						<td class="jView" _no="${row.orderNumber }"><a href="#">${row.productCode}</a><br/><a href="#">${ row.productName}</a></td>
						<td>${row.FsidoName}&nbsp;${row.FgugunName}<br/>${row.franchiseName eq null ? row.userName : row.franchiseName }</td>
						<c:if test="${param.pageType eq 1 }">
							<td>${row.sidoName}&nbsp;${row.gugunName}<br/>${row.sujuName }</td>
						</c:if>
						<c:if test="${param.pageType eq 2 }">
							<td>${row.sidoName}&nbsp;${row.gugunName}<br/>${row.userName }</td>
						</c:if>
						<td>${row.hopeDate } ${row.hopeTime }</td>
						<td>${row.receiveName }</td>
						<td>${row.sidoName} ${row.gugunName}${row.receiveAddress }</td>
						<td>
							<p style="${row.accessLevelCode eq '1' ? 'color: blue' : ''}">소매 : <fmt:formatNumber value="${row.retailPrice}" pattern="#,###" />원</p>
							<p style="${row.accessLevelCode eq '2' ? 'color: red' : ''}">도매 : <fmt:formatNumber value="${row.wholesalePrice}" pattern="#,###" />원</p>
						</td>
						<td><fmt:formatNumber value="${row.payment}" pattern="#,###" /></td>
						<td>
						<div id="priviewFileScale${status.index}" style="position: absolute; background-image: url('${properties.img_path }/100/${row.purchaseImg }'); width: 300px; height: 200px; background-repeat: no-repeat; background-size: contain; background-position: center center; display: none;"></div>
						<img class="imgSample" src="${properties.img_path}/100/${row.purchaseImg }" width="50" height="50"	id="previewFile${status.index}" onerror="this.style.visibility='hidden'" />
						</td>
						<td>
							<select class="jStatus" name="status" _no="${row.orderNumber }" ${param.pageType eq 2 ? 'disabled' : ''} >
								<option value="PYMT0010" ${row.processCode eq 'PYMT0010' ? 'selected' : '' }>미확인</option>
								<option value="PYMT0020" ${row.processCode eq 'PYMT0020' ? 'selected' : '' }>입금대기</option>
								<option value="PYMT0040" ${row.processCode eq 'PYMT0040' ? 'selected' : '' }>주문접수</option>
								<option value="PYMT0050" ${row.processCode eq 'PYMT0050' ? 'selected' : '' }>배송중</option>
								<option value="PYMT0060" ${row.processCode eq 'PYMT0060' ? 'selected' : '' }>배송완료</option>
								<option value="PYMT0070" ${row.processCode eq 'PYMT0070' ? 'selected' : '' }>결제취소</option>
								<%-- <option value="PYMT0090" ${row.processCode eq 'PYMT0090' ? 'selected' : '' }>보류</option> --%>
							</select>
							<c:if test="${row.processCode eq 'PYMT0060'}">
								<p>${row.acceptorName }</p>
								<p>${row.deliveryDate }</p>
							</c:if>
						</td>
					</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table> 

		<div class="pageNumber">
			<c:set var="pager" value="${retData.paging}"/>
			${pager }
		</div>

		</div>
	</div>
<c:import url="${properties.include_path}/footer_admin.jsp"/>