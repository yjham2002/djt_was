<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*"
	import="java.text.SimpleDateFormat"
	%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="en-us" />
<meta name="description" content="Back Office" />
<meta http-equiv="imagetoolbar" content="no" />

<title>관리자 로그인 </title>

<link rel="stylesheet" type="text/css" href="${properties.resources_path }/css/admin/base.css"/>
<link rel="stylesheet" type="text/css" href="${properties.resources_path }/css/admin/login.css"/>

<script type="text/javascript" src="${properties.resources_path }/js/jquery-1.9.0.min.js"></script>

<!-- 리치 js -->
<script src="${properties.resources_path }/js/jqeury_rich/RichBaseExtends.js"></script>
<script src="${properties.resources_path }/js/jqeury_rich/RichFramework-1.0.js"></script>
<script src="${properties.resources_path }/js/jqeury_rich/RichElement-1.0.js"></script>
<script src="${properties.resources_path }/js/jqeury_rich/RichBaseElementObject-1.0.js"></script>
<script src="${properties.resources_path }/js/jqeury_rich/RHForm-1.0.js"></script>

<jsp:useBean id="toDay" class="java.util.Date" />

<%
	/*
	Date d = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy년MM월dd일 HH:mm");
	String nowDate = df.format(d);
	*/
%> 
<script type="text/javascript">

	$(function(){
		var pathRoot = "/NaeKkot/admin";
		
		$("#admID").focus();
		
		$("#admPWD, #admID").on("keydown", function(e){
			var keycode = e.keyCode || e.which;
			if(keycode == 13){
				e.preventDefault();
				
				$("#login_btn").trigger("click");
				
			}
		});
		
		$("#login_btn").on("click", function(e){
			e.preventDefault();
			
			if($("#admID").val() == "") {
				alert("아이디를 입력해주세요.");
				return;
			}
			
			if($("#admPWD").val() == "") {
				alert("패스워드를 입력해주세요.");
				return;
			}
			
			$.ajax({
				type : "POST" , 
				url : pathRoot + "/loginAction" , 
				dataType : "json" , 
				data : $("#form").serialize() , 
				success : function(data) {
					alert(data.returnMessage);
					var userID = data.entity.userID;
					var aclv = data.entity.accessLevelCode;
					
					if(userID.toLowerCase() == "five"){
						location.replace(pathRoot+"/user/companyForm");
						return;
					}
					
					if(data.returnCode == 1){
						if(aclv == 4 || aclv == 3){
							location.replace(pathRoot+"/superOrder/orderList?processCode=");
						} else if(aclv == 2){
							location.replace(pathRoot+"/order/orderList?pageType=1&franchiseNumber=" + data.addData);
						}
					}
				}
			});
						
		});
	});
	
</script>
</head>
<body id="base_form">
	<form id="form" name="form" enctype="multipart/form-data" method="post">
		<div class="header">
			 내꽃 <span>ADMIN</span>
		</div>
	
		<div class="m">
			<div role="main" class="container">
				<div class="hgroup">
					<h1>
						<img src="${properties.resources_path }/imgs/admin/icon_member.gif" width="40" height="40" alt="로고" align="middle" /> Log-in 
							<span>
								<fmt:formatDate value="${toDay}" pattern="yyyy-MM-dd hh:mm" />
								에 <c:out value="${pageContext.request.remoteAddr}" /> 로 접속하셨습니다.
							</span>
					</h1>
				</div>
	
				<div class="data">
					<table class="data">
						<tr>
							<th>아이디</th>
							<td><input type="text" autocomplete="on" class="login"
								id="admID" name="admID" /></td>
						</tr>
						<tr>
							<th>패스워드</th>
							<td><input type="password" class="login" id="admPWD" name="admPWD" /></td>
						</tr>
						<tr>
							<th>&nbsp;</th>
							<td><a href="#" class="button navy" id="login_btn"> 로그인 </a></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="footer">
			<%--개발사 <span> ㈜리치웨어시스템즈</span> | 담당자 <span>김병수 책임</span> | 개발사 대표번호 <span>1670 – 6842 </span> --%>
		</div>
	</form>
</body>
</html>