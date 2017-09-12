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
					$("#form_submit").click(function(){
						if($("#board_title").val() == "") {
							alert("Please fill the title!");
							return;
						}
						if($("#board_text").val() == "") {
							alert("Please fill the contents!");
							return;
						}
						
						$.ajax({
							type : "POST" , 
							url : pathRoot + "/postAction",
							dataType : "json",
							data : $("#board_form").serialize(), 
							success : function(data) {
								//alert(data.returnMessage);
								if(data.returnCode == 1){
									location.replace(pathRoot+"/board");
								}
							},
							error : function(data){
								alert(data.returnMessage + " : Failed");
							}
						});
					});
					$("#user_signout").click(function(e){
						e.preventDefault();
						setCookie("rich_name", "");
						location.replace(pathRoot + "/login");
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
							<h2>Hello, ${cookieData.name }!</h2>
							<form method="POST" action="postAction" id="board_form" name="board_form">
								<input type="text" placeholder="Title" id="board_title" name="board_title" required />
								<br>
								<textarea placeholder="What's on your mind?" id="board_text" name="board_text" required></textarea>
								<br>
								<ul class="actions">
									<li><input type="file" id="board_file" accept="image/*" /></li>
									<li><input type="reset" value=" clear " /></li>
									<li><a href="#" id="form_submit" class="button"> Submit </a></li>
								</ul>
							</form>
						</div>
					</section>
					<section id="wrapper">
					<c:if test="${retData.boardCount == 0}">
						<div class = "inner">
							<center>
							<h2>No Entry :( Be the first to post something!</h2>
							</center>
						</div>
					</c:if>
				<c:set var="vnum" value="${retData.boardCount}"/>
				
				<c:forEach var="row" items="${retData.list}" varStatus="status">
						
 						<c:if test="${(vnum - status.index) % 2 == 0 }">
 						<section id="one" class="wrapper spotlight style1">
 						</c:if> 
 						<c:if test="${(vnum - status.index) % 2 != 0 }">
 						<section id="two" class="wrapper alt spotlight style3">
 						</c:if> 
							
								<div class="inner">
									<a href="#" class="image">
									<img src="${properties.resources_path }/images/pic02.jpg" alt="" />
									</a>
									<div class="content">
										<h2 class="major">${row.userName }</h2>
										<h3>${row.title }</h3>
										<p>${row.content }</p>
										<font size="2pt">${row.date }</font>
										<br><br>
										<a href="detail?id=${row.id }" class="special">More</a>
									</div>
								</div>
							</section>

				</c:forEach>
					<center>	
					<c:forEach var="i" begin="1" end="${retData.pCount }" step="1">
					<c:if test="${retData.currentPage == i }">
						&nbsp;<a href="board?page=${i }"><b>${i }</b></a>&nbsp;
					</c:if>
   					<c:if test="${retData.currentPage != i }">
						&nbsp;<a href="board?page=${i }">${i }</a>&nbsp;
					</c:if>	
   					</c:forEach>
					</center>

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