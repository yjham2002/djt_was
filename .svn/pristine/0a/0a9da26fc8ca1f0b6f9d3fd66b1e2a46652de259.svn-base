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
						location.replace(pathRoot + "/board");
					});
					
					$("#signin_bt").click(function(e){
						
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
							url : pathRoot + "/modifyAction",
							dataType : "json",
							data : $("#login_form").serialize(), 
							success : function(data) {
								alert(data.returnMessage);
								if(data.returnCode == 1){
									location.replace(pathRoot+"/login");
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
							<h2>My Page</h2>
							<p>Change your profile!</p>
							<input type="text" id="name" name="name" placeholder="Name" value="${profile.name }" required/>
							<br>
							<input type="text" id="userId" name="userId" placeholder="ID" value="${profile.userId }" required readonly/>
							<br>
							<input type="password" id="userPw" name="userPw" placeholder="Current Password" required/>
							<br>
							<input type="password" id="userPw_new" name="userPw_new" placeholder="New Password"/>
							<br>
							<div align="right">
								<a href="#" id="signin_bt" name="signin_bt" class="button"> SUBMIT </a>
								&nbsp;
								<a href="#" id="signup_bt" name="signup_bt" class="button"> BACK </a>
							</div>
						</form>
						</div>
					</section>

			</div> 

	</body>
</html>