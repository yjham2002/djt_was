<? include $_SERVER["DOCUMENT_ROOT"] . "/common/classes/Admin.php" ;?>
<?

	$headObj = new Admin($_REQUEST);

	$loginInfo = LoginUtil::getAdminUser();

// 	echo $loginInfo['name'] . "aaa" ;

	if($loginInfo["gid"] == "-1"){
		echo "<script>alert('로그인 후 이용가능합니다.'); location.href = '/admin'; </script>";
		return;
	}

	// $professionalUrl 접근 방지
	if($loginInfo["id"] != "superadmin" && strpos($_SERVER["REQUEST_URI"], "professionalManage")) {
		echo "<script>alert('슈퍼관리자 로그인 후 이용 가능합니다.'); location.href = '/admin'; </script>";
		return;
	}
	// $professionalUrl 접근 방지


// 	echo json_encode($loginInfo);
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Application Back Office</title>
<? include $_SERVER["DOCUMENT_ROOT"] . "/admin/inc/php/metaData.php" ?>

<script type="text/javascript">
	$(document).ready(function(){
		$("div.nav").find("a").removeClass("selected");
		$("div.nav").find("a[matchUrl='" + _urlPath + "']").addClass("selected");
	});
</script>

</head>

<body>


<!-- 로딩 이미지 시작 -->
<style>
.LoadWrap{ /*화면 전체를 어둡게 합니다.*/
    position: fixed;
    left:0;
    right:0;
    top:0;
    bottom:0;
    background: rgba(0,0,0,0.3); /*not in ie */
    filter: progid:DXImageTransform.Microsoft.Gradient(startColorstr='#20000000', endColorstr='#20000000');    /* ie */

}

.LoadWrap div{ /*로딩 이미지*/
	position: fixed;
	top:50%;
	left:50%;
	margin-left: -40px;
	margin-top: -40px;
}

.display-none{ /*감추기*/
	display:none;
}

</style>
<div class="LoadWrap display-none" style ="z-index:99999;">
<div><img src="/admin/inc/images/load.gif" style="width:40%"/></div>
</div>
<!-- 로딩 이미지 종료 -->


<div id="Wrap">


	<div class="conHeight">
		<!-- contents start-->
		<div class="contWrap">

