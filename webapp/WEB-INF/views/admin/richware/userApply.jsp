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
			<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
			<script src="${properties.resources_path }/js/main.js"></script>
			
			<script>
				$(document).ready(function(){
					var pathRoot = "/NaeKkot/admin";
					
					$("#admID").focus();
					
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
							url : pathRoot + "/loginAction" , 
							dataType : "json" , 
							data : $("#login_form").serialize() , 
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
	<body>
		<div id="page-wrapper">
				<!-- Banner -->
					<section id="banner">
						<div class="inner">
						<form id="login_form" name="login_form">
							<div class="logo"><span class="icon fa-diamond"></span></div>
							<h2>RICHWARE </h2>
							<input type="text" id="admID" name="admID" placeholder="ID" />
							<br>
							<input type="password" id="admID" name="admPWD" placeholder="PW" />
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