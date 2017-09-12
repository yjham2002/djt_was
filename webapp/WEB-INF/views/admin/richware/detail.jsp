<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"	    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML>
<html>
	<head>
		<title>:: Richware ::</title>
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="${properties.resources_path }/css/main.css" />
				<!-- Scripts -->
			<script src="${properties.resources_path }/js/skel.min.js"></script>
			<script src="${properties.resources_path }/js/jquery.min.js"></script>
			<script src="${properties.resources_path }/js/jquery.scrollex.min.js"></script>
			<script src="${properties.resources_path }/js/util.js"></script>
			<script src="${properties.resources_path }/js/cookieUtil.js"></script>
			<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
			<script src="${properties.resources_path }/js/main.js"></script>
			<script>
				$(document).ready(function(){
										
					var pathRoot = "/NaeKkot/admin";
					var userInfo = getCookie("rich_name");
					if(userInfo == "" || userInfo == undefined) {
						alert("Unauthorized Access");
						location.replace(pathRoot + "/login");
					}
					
					$("#user_signout").click(function(e){
						e.preventDefault();
						setCookie("rich_name", "");
						location.replace(pathRoot + "/login");
					});
					
					$("#submit_comment").click(function(e){
						if($("#content").val() == ""){
							alert("Please fill the comment!");
							return;
						}
						$.ajax({
							type : "POST" , 
							url : pathRoot + "/commentAction",
							dataType : "json",
							data : $("#comment_form").serialize(), 
							success : function(data) {
								//alert(data.returnMessage);
								if(data.returnCode == 1){
									location.reload();
								}
							},
							error : function(data){
								alert(data.returnMessage + " : Failed");
							}
						});
					});
					
				});
			</script>
	</head>
	<body>
		<div id="page-wrapper">
					<header id="header" class="alt">
						<h1><a href="board">RICHWARE</a></h1>
						<nav>
							<a href="#menu">Menu</a>
						</nav>
					</header>
					<nav id="menu">
						<div class="inner">
							<h2>Menu</h2>
							<ul class="links">
								<li><a href="mypage">MY PAGE</a></li>
								<li><a href="#" id="user_signout">Log Out</a></li>
							</ul>
							<a href="#" class="close">Close</a>
						</div>
					</nav>

				<!-- Banner -->
					<section id="banner">
						<div class="inner">
							<h2>${retData.title }</h2>
								<h3>${retData.userName }</h3>
								<br>
								<p>
								${retData.content }
								</p>
								<br>
								<p align="right">${retData.date }</p>
								<hr>
								<h4>Comments</h4>
								
								<table>
								<tr>
									<td colspan=3 align="right">
									<form id="comment_form" onsubmit="return false;" >
									<input type="hidden" name="bid" value=${retData.id } />
									<input type="text" placeholder="React on it! :)" name="content" id="content" />
									<br>
									<ul class="actions">
									<li><a href="#" class="button" id="submit_comment">SUBMIT</a></li>
									</ul>
									</form>									
									</td>
								</tr>
								
								<c:forEach var="row" items="${retData.comments}" varStatus="status">
								<tr>
									<th>${row.author }</th> <td>${row.content }</td> <td>${row.date}</td>
								</tr>
								</c:forEach>
								
								</table>
								
								<ul class="actions">
									<li><a href="board" id="form_submit" class="button"> BACK </a></li>
								</ul>
						</div>
					</section>
					

				<!-- Footer -->
					<section id="footer">
						<div class="inner">
							<ul class="copyright">
								<li>&copy; RICHWARE. All rights reserved.</li>
							</ul>
						</div>
					</section>

			</div>

	</body>
</html>