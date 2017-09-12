<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_user.jsp"/>
<c:set var="data" value="${retData.data}"/>

<script>
	var _rurl = "${rurl}";

	$(document).ready(function(){
		
		$(".jBack").click2(function(){
			location.href = _rurl;
		});
		
		$(".jSave").click2(function(){

			$("#jData").ajaxSubmit({
				url : rootPath + "/superUser/updateUser",
				type : "post",
				forceSync : true,
				dataType : "json",
				success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == "1") {
						location.href=_rurl;
					}
				}
			});
		});
			
	});

</script>


<div id="Contents"  class="">
	<form id="jData" method="post" enctype="multipart/form-data">

	<h1> 사용자 </h1>
	<!-- location area -->
	<h2 style="width:98%;">
	</h2>
	
	<div class="data" style="width:100%;">
		<input type="hidden" name="userNumber" value="${param.userNumber}">
		<input type="hidden" name="userType" value="${data.userType}">
		<table class="datacList" style="width:100%; margin-bottom: 50px;">
			<thead align="left">
				<tr>
					<th width="10%">아이디</th>
					<td width="40%">${data.userID }</td>
					<th width="10%">비밀번호</th>
					<td width="40%"><input type="text" class="" name="password" value="" style="width: 95%"></td>
				</tr>
			
				<tr>
					<th width="10%">이름 / 회사명</th>
					<td width="40%"><input type="text" class="" name="userName" value="${data.userName }" style="width: 95%"></td>
					<th width="10%">연락처</th>
					<td width="40%"><input type="text" class="" name="userTel" value="${data.userTel }" style="width: 95%"></td>
				</tr>
			
				<c:if test="${data.userType eq 2 }">
					<tr>
						<th width="10%">담당자이름</th>
						<td width="40%"><input type="text" class="" name="personName" value="${data.personName }" style="width: 95%"></td>
						<th width="10%"><!-- 이메일 --></th>
						<td width="40%"><%-- <input type="text" class="" name="email" value="${data.email }" style="width: 95%"> --%></td>
 					</tr>
				
					<tr>
						<th width="10%">사업자등록번호</th>
						<td width="40%"><input type="text" class="" name="corporate" value="${data.corporate }" style="width: 95%"></td>
						<th width="10%">구분</th>
						<td width="40%">${data.userType == 1 ? "일반" : "기업" }</td>
					</tr>
				
					<tr>
						<th width="10%">메모</th>
						<td width="40%"><input type="text" class="" name="memo" value="${data.memo }" style="width: 95%"></td>
						<th width="10%">보유포인트</th>
						<td width="40%">${data.point }포인트</td>
					</tr>
				</c:if>
			
			</thead>
		</table>
		
		<div class="btngroupcenter">		
			<input type="button" class="button medium white jBack" value="목록으로">
			<input type="button" class="button medium white jSave" value="저장">
		</div>
		
	</div>
	</form>
</div>

<c:import url="${properties.include_path}/footer_admin.jsp"/>