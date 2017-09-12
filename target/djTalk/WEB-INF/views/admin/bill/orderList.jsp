<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_admin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_adminBill.jsp"/>

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
		
	});
</script>
	<div id="Contents"  class="notice">
	    <h1>정산관리</h1>
		<h2 style="width:98%;">
			정산 관리 ><span> 주문별 정산</span>
		</h2>
		
		<div class = "data" style="overflow-x: scroll; overflow-y: hidden">
			<div style="clear:both">
			
				<fieldset class="search" style="margin-top:20px;">
					<div class="jSearchArea">
						<label>
							<span>수주가맹점명</span>
							<input type = "text" name = "" value = "${param.searchTel }" style = "width:100px;"/>
						</label>
						<label>
							<span>상품명</span>
							<input type = "text" name = "" value = "${param.searchTel }" style = "width:100px;"/>
						</label>
						<label>
							<span>수금액완료</span>
							<select class="" name="" style = "width:80px;">
								<option value="">전체</option>
								<option value="">대기</option>
								<option value="">완료</option>
							</select>
						</label>
						<label>
							<span>입금액완료</span>
							<select class="" name="" style = "width:80px;">
								<option value="">전체</option>
								<option value="">대기</option>
								<option value="">완료</option>
							</select>
						</label>
					</div>
					
					<div class="jSearchArea" style = "margin-top:10px;">
						<label>
							<span>포인트</span>
							<select class="" name="" style = "width:80px;">
								<option value="">전체</option>
								<option value="">사용</option>
								<option value="">미사용</option>
							</select>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>주문자명</span>
							<input type = "text" name = "" value = "${param.searchTel }" style = "width:100px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>기간</span>
							<input type = "text" name = "" value = "${param.searchTel }" style = "width:100px;"/>&nbsp;&nbsp;&nbsp;~
							<input type = "text" name = "" value = "${param.searchTel }" style = "width:100px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>결제방법</span>
							<select class="" name="" style = "width:100px;">
								<option value="">전체</option>
								<option value="">사용</option>
								<option value="">미사용</option>
							</select>
						</label>
					</div>
					
					<div class = "btngroupcenter">
						<input type="button" class="button medium white jSearch" value="검색">
					</div>
					
				</fieldset>
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
			
			<table class = "datacList">
				<thead>
					<tr>
						<th>No</th>
						<th>제품번호</th>
						<th>주문자이름</th>
						<th>상품명</th>
						<th>발주매장명</th>
						<th>발주업체<br/> 보유예치금</th>
						<th>수주매장명</th>
						<th>사용매장명</th>
						<th>사용포인트</th>
						<th>수수료(%)</th>
						<th>도매가</th>
						<th>소매가</th>
						<th>적용금액</th>
						<th>선택옵션</th>
						<th>공제금액</th>
						<th>공제금액</th>
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
					<c:set var="vnum" value="${retData.pager.virtualNum}"/>
					<c:forEach var="row" items="${retData.list }" varStatus="status">
						<tr>
							<td><input type = "checkbox" class="jCheck_Each" name = "gids[]" value = "${row.userNumber}"/></td>
							<td>
								${vnum - status.index}
							</td>
							<td><%-- <fmt:formatDate value="${row.regDate}" pattern="yyyy-MM-dd"/>--%> ${row.regDate }</td>
							<td>${row.userType == 1 ? '일반' : '기업'}</td>
							<td>${row.userName }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.aaa }</td>
							<td>${row.userID }</td>
							<td>${row.personName }</td>
							<td>${row.corporate}</td>
							<td><input type="button" class="button searchsmall white jBill" value="정산"  _no = "${row.userNumber }"/></td>
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
	
<c:import url="${properties.include_path}/footer_admin.jsp"/>

