<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script>

	
	
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
						<td>${row.FsidoName}&nbsp;${row.FgugunName}<br/>${row.franchiseName }</td>
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
								<option value="PYMT0090" ${row.processCode eq 'PYMT0090' ? 'selected' : '' }>보류</option>
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