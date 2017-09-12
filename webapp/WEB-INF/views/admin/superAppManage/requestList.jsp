<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_appManage.jsp"/>


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
		
		$(".jSearch").click2(function(){
			jForm.ci();
			jForm.submit();
		});
		
		$("[name=status]").change(function(){
			
			var no = $(this).attr("_no");
			var status = $(this).val();
			
			$.ajax({
				url : rootPath + "/superAppManage/updateReqStatus",
				data : "POST",
				dataType : "json",
				data : {
					requestNumber : no,
					status : status
				}, success : function(data){
					alert(data.returnMessage)
					if(data.returnCode == 1){
						location.reload();
					}
				}
			});
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
			if($("[name='gids[]']:checkbox:checked").length==$("[name='gids[]']").length){
				$(".jCheck_All").prop("checked", true);
			}else {
				$(".jCheck_All").prop("checked", false);
			}
		});
		
		// 선택된 항목들 삭제
		$(".jDelete").click2(function(){
			var jDel = $(".jCheck_Each:checkbox:checked").length;
			if(jDel <= 0){
				alert("선택된 항목이 없습니다.");
			}else {
				if(!confirm("삭제하시겠습니까?"))	return false;
				
				var requestNumbers = new Array();
				$(".jCheck_Each:checked").each(function(i){
					requestNumbers[i] = $(this).val();
				});
				
				$.ajax({
					url : rootPath + "/superAppManage/deleteReqFranchise",
					type : "POST",
					dataType : "json" ,
					data : {
						requestNumbers : requestNumbers,
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
		
		
	});
	
</script>
<form id="jData" method="post" enctype="multipart/form-data">
	<div id="Contents"  class="notice">
	    <h1>가맹점 신청 내역</h1>
		<h2 style="width:98%;">
		</h2>
		
		<div class = "data">
			<div style="clear:both">
			
				<fieldset class="search" style="margin-top:20px; text-align: center;">
					<div class="jSearchArea">
						<label>
							<span>이름</span>
							<input type = "text" name = "name" value = "${param.name }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>연락처</span>
							<input type = "text" name = "tel" value = "${param.tel }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>이메일</span>
							<input type = "text" name = "email" value = "${param.email }" style = "width:120px;"/>
						</label>
					</div>
					
					<div class="jSearchArea" style="margin-top: 10px;">
						<label>
							<span>가맹점명</span>
							<input type = "text" name = "company" value = "${param.company }" style = "width:120px;"/>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<span>확인</span>
							<select class="" name="">
								<option value="">전체</option> 
								<option value="0">미확인</option> 
								<option value="1">확인</option> 
								<option value="-1">보류</option> 
							</select>
						</label>
						&nbsp;&nbsp;&nbsp;
						<label>
							<input type="button" class="button medium white jSearch" value="검색">
						</label>					
					</div>
				</fieldset>
			</div>
			
			<div class = "jSearchArea btngroupright">
				<input type="button" class="button medium white jDelete" value="선택삭제" />&nbsp;&nbsp;&nbsp;
				<select class="" name="">
					<option value="1">100개씩 보기</option>
				</select>
			</div>
			 
			<table class = "datacList" style="margin-top: 15px;">
				<thead>
					<tr>
						<th><input type="checkbox" class="jCheck_All"/></th>
						<th>No</th>
						<th>신청일</th>
						<th>이름</th>
						<th>가맹점명</th>
						<th>연락처</th>
						<th>이메일</th>
						<th>홈페이지</th>
						<th>확인</th>
					</tr>
				</thead>
				<tbody style = "text-align:center;">
					<c:set var="vnum" value="${retData.pager.virtualNum}"/>
					<c:forEach var="row" items="${retData.list }" varStatus="status">
						<tr>
							<td><input type = "checkbox" class="jCheck_Each" name = "gids[]" value = "${row.requestNumber}"/></td>
							<td>
								${vnum - status.index}
							</td>
							<td><%-- <fmt:formatDate value="${row.regDate}" pattern="yyyy-MM-dd"/>--%> ${row.regDate }</td>
							<td>${row.name}</td>
							<td>${row.company}</td>
							<td>${row.tel}</td>
							<td>${row.email}</td>
							<td>${row.homepage}</td>
							<td>
								<select class="jStatus" name="status" _no=${row.requestNumber }>
									<option value="0" ${row.status == 0 ? 'selected' : '' }>미확인</option> 
									<option value="1" ${row.status == 1 ? 'selected' : '' }>확인</option> 
									<option value="-1" ${row.status == -1 ? 'selected' : '' }>보류</option> 
								</select>
							</td>
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
</form>
<c:import url="${properties.include_path}/footer_admin.jsp"/>