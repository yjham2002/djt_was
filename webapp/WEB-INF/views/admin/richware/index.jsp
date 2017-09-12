<%@ page contentType = "text/html;charset=utf-8" %>
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
					
					$("#admID").focus();
					
					$("#signup_bt").click(function(e){
						location.replace(pathRoot + "/apply");
					});
					
					$("#signin_bt").click(function(e){
						
						if($("#userId").val() == "") {
							alert("Please enter your ID.");
							return;
						}
						if($("#userPw").val() == "") {
							alert("Please enter your password.");
							return;
						}
						
						$.ajax({
							type : "POST" , 
							url : pathRoot + "/loginAction" ,
							dataType : "json" , 
							data : $("#login_form").serialize() , 
							success : function(data) {
								alert(data.returnMessage);
								var userID = data.entity.userId;
								if(data.returnCode == 1){
									setCookie('rich_id', data.entity.id, 0);
									setCookie('rich_userId', data.entity.userId, 0);
									setCookie('rich_name', data.entity.name, 0);
									location.replace(pathRoot+"/board");
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
				<!-- Banner -->
					<section id="banner">
						<div class="inner">
						<form id="login_form" name="login_form">
							<div class="logo"><span class="icon fa-diamond"></span></div>
							<h2>RICHWARE </h2>
							<p>Member Login</p>
							<input type="text" id="userId" name="userId" placeholder="ID" />
							<br>
							<input type="password" id="userPw" name="userPw" placeholder="PW" />
							<br>
							<div align="right">
								<a href="#" id="signin_bt" name="signin_bt" class="button"> Sign In </a>
								&nbsp;
								<a href="#" id="signup_bt" name="signup_bt" class="button"> Sign Up </a>
							</div>
						</form>
						</div>
					</section>

			</div> 

	</body>
</html>