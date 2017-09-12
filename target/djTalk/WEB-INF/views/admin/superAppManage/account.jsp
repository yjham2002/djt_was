<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="${properties.include_path}/header_superAdmin.jsp"/>
<c:import url="${properties.include_path}/leftMenu_appManage.jsp"/>


<script>
	
	$(document).ready(function(){
		
		$(".jModifyAdminPW").click2(function(){
			
			if($("[name=oldPwd]").val() == ""){
				alert("기존 비밀번호를 입력해 주세요.");
				$("[name=oldPwd]").focus();
				return;
			}
			if($("[name=newPwd]").val() == ""){
				alert("변경할 비밀번호를 입력해 주세요.");
				$("[name=newPwd]").focus();
				return;
			}
			if($("[name=newPwd2]").val() == ""){
				alert("비밀번호 확인을 입력해 주세요.");
				$("[name=newPwd2]").focus();
				return;
			}
			
			if($("[name=newPwd]").val() != $("[name=newPwd2]").val()){
				alert("변경할 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
				$("[name=newPwd2]").focus();
				return;
			}
			
			var newPwd = $("[name=newPwd]").val();
			var oldPwd = $("[name=oldPwd]").val();
			
		 	$.ajax({
				url : rootPath + "/account/modifyAdminPW",
				type : "POST",
				dataType : "json",
				data : {
					type : 1,
					oldPwd : oldPwd,
					newPwd : newPwd
				},
				success : function(data){
					alert(data.returnMessage);
					
					if(data.returnCode == 1){
						location.reload();
					}
				}
			});
		});
		
		$(".jDelete").click2(function(){
			
			var userNumber = $(this).attr("_no");
			
			$.ajax({
				url : rootPath + "/account/deleteSubAdmin",
				type : "POST",
				dataType : "json",
				data : {
					type : 1,
					userNumber : userNumber 
				},
				success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == 1){
						location.reload();
					}
				}
			});
			
		});
		
		$(".jSubModifyPW").click2(function(){
			
			var userNumber = $(this).attr("_no");
			var newPwd = $(this).parent().parent().siblings(".jSubPwd").find(".jSubAdminPW").val();
			
			$.ajax({
				url : rootPath + "/account/modifyAdminPW",
				type : "POST",
				dataType : "json",
				data : {
					type : 2,
					userNumber : userNumber ,
					newPwd : newPwd
				},
				success : function(data){
					alert(data.returnMessage);
					if(data.returnCode == 1){
						location.reload();
					}
				}
			});
			
		});
		
		$(document).on("click", ".jRemoveHtml", function(){

			$(this).closest(".datav").html("");
			
		});
		
		$(document).on("click", ".jAddAdminSubmit", function(){

			var id = $(this).closest(".datav").find("[name=id]").val();
			var pw = $(this).closest(".datav").find("[name=pw]").val();
			
			var pattern = /^[\wㄱ-ㅎㅏ-ㅣ가-힣]{2,15}$/;
			
			if(pattern.test(id)){
				
				if(pw == ''){
					alert("비밀번호를 입력하세요.");
					return
				}

				$.ajax({
					url : rootPath + "/account/insertSubAdmin",
					type : "POST",
					dataType : "json",
					data : {
						id : id,
						pw : pw
					}
					, success : function(data){
						alert(data.returnMessage);
						if(data.returnCode == 1){
							location.reload();
						}
					}
				});

			} else {
				alert("아이디를 2~15자 이내로 입력해 주세요.");
			}
			
			 
		});
		
		$(".jAddAdmin").click2(function(){
			
			var html = "";
			html += '<table class="datav" style="width:70%; margin: 10px 0px;"><tr>';
            html += '<th class="b">ID</th><td><input type="text" style="width:200px;"  name="id" value=""></td>';
            html += '<th rowspan="2"><input type="button" class="button medium white jRemoveHtml" value="삭제">';
            html += '<input type="button" class="button medium white jAddAdminSubmit" value="저장">';
			html += '</th></tr><tr class="jSubPwd"><th class="b">PW</th>';
            html += '<td><input type="text" style="width:200px;" class="jSubAdminPW" name="pw" value=""></td>'
            html += '</tr></table>';
			
            $("#jData").append(html);
			
		});
		
		
		$(".jLog").click2(function(){
			var btnType = $(this).attr("btnType");
			window.open(rootPath + "/superAppManage/connectionLogs?btnType="+btnType, "connectionLogs", "width=523, height=700,top=50, left=0, scrollbars=yes") ;
		});
		
	});

</script>


<div id="Contents"  class="notice">

	<h1>계정</h1>
	<h2 style="width:98%;">
		계정 > <span>계정 설정</span>
	</h2>


    <div class="data" style="width:100%;">
		<form id="jData">
			<h3>슈퍼 관리자 계정</h3>
	        <table class="datav" style="width:70%;">
	            <tr>
	                <th class="b" style="width:15%;">ID</th>
	                <td>${adminName }</td>
	            </tr>
	            <tr>
	                <th class="b">기존 PW</th>
	                <td>
	                	<input type="text" style="width:200px;" name="oldPwd">
	                </td>
	            </tr>
				<tr>
	                <th class="b">새로운 PW</th>
	                <td>
	                	<input type="text" style="width:200px;" name="newPwd">
	                </td>
	            </tr>
	            <tr>
	                <th class="b">새로운 PW 확인</th>
	                <td>
	                	<input type="text" style="width:200px;" name="newPwd2">
	                </td>
	            </tr>
	        </table>
	        <div class="btngroupcenter">
				<input type="button" class="button medium white jModifyAdminPW" value="비밀번호 변경" />
			</div>
	        
	        <h3>서브 관리자 계정</h3>
	        <c:forEach var="row" items="${retData.subAdminList }">
		        <table class="datav jSubAdmin" style="width:70%; margin: 10px 0px;">
		            <tr>
		                <th class="b">ID</th>
		                <td><input type="text" style="width:200px;"  name="id" value="${row.userID }"></td>
		                <th rowspan="2">
		                	<input type="button" class="button medium white jDelete" value="삭제" _no=${row.userNumber }>
							<input type="button" class="button medium white jSubModifyPW" value="비밀번호 수정" _no=${row.userNumber }>
						</th>
		            </tr>
					<tr class="jSubPwd">
		                <th class="b">PW</th>
		                <td><input type="text" style="width:200px;" class="jSubAdminPW" name="pw" value="${row.userPWD }"></td>
		            </tr>
		        </table>
	        </c:forEach>
	        
        </form>
        
		<div class="btngroupcenter">
			<input type="button" class="button medium white jAddAdmin" value="추가하기"  />
		</div>
        
        <br/>
		<div class="btngroupcenter">
			<!-- input type="button" class="button medium white " value="목록으로" /-->
			<input type="button" class="button medium white jLog" value="로그내역" btnType="1"/>
			<input type="button" class="button medium white jLog" value="가맹점 로그내역" btnType="2" />
		</div>
    </div>

</div>






<c:import url="${properties.include_path}/footer_admin.jsp"/>