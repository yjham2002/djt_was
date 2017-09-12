<? include $_SERVER["DOCUMENT_ROOT"] . "/common/classes/AdminBase.php" ?>
<?
	$headObj = new AdminBase($_REQUEST, "");
	$webRoot = $headObj->webRoot;
	$loginInfo = LoginUtil::getAdminUser();

	if($loginInfo["adminNumber"] == "-1")
	{
//		echo "<script>alert('로그인 후 이용가능합니다.'); location.href = '/admin'; </script>";
//		return;
	}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title><?= $headObj->productName ?> Application Back Office</title>
<? include $_SERVER["DOCUMENT_ROOT"] . "/admin/inc/php/metaData.php" ?>

</head>

<body>

<div id="Wrap">

	<div class="conHeight">
		<!-- contents start-->
		<div class="contWrap">

	