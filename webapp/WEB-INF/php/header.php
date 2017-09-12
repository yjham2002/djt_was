<? include $_SERVER["DOCUMENT_ROOT"] . "/common/classes/Admin.php" ;?>
<?

	$headObj = new Admin($_REQUEST);

	$loginInfo = LoginUtil::getAdminUser();

// 	echo json_encode($loginInfo);

	if($loginInfo["gid"] == "-1"){
		echo "<script>alert('로그인 후 이용가능합니다.'); location.href = '/admin'; </script>";
		return;
	}


?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>KIWI - Back Office</title>
<? include $_SERVER["DOCUMENT_ROOT"] . "/admin/inc/php/metaData.php" ?>

<script type="text/javascript">
	$(document).ready(function(){
		$("div.nav").find("a").removeClass("selected");
		$("div.nav").find("a[matchUrl='" + _urlPath + "']").addClass("selected");

		$(".jLanguage").change(function(){
			var loc = $(this).val() ;

			if(confirm("언어 변환시 관리자 초기화면으로 이동됩니다.\n\n변환하시겠습니까?"))
			{
				location.href="/action_front.php?cmd=AdminUser.setChangeLoc&flow=ToS&loc=" + loc

			}
		});
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
	<!-- header -->
	<div id="Header">

		<!-- topmenu area -->
		<div class="hgroup">
			<table>
				<tr>
					<td class="logo">KIWI   <span>관리자</span></td>
					<td class="logo">
						<select class="jLanguage" name="">
							<option value="ko" <?=($_SESSION["admin_loc"] == "ko") ? "SELECTED" : ""?> >한국어 버전</option>
							<option value="en" <?=($_SESSION["admin_loc"] == "en") ? "SELECTED" : ""?> >영어 버전</option>
							<option value="zh" <?=($_SESSION["admin_loc"] == "zh") ? "SELECTED" : ""?> >중국어 버전</option>
						</select>
					</td>
					<td class="log">




						<strong><?=$loginInfo["name"]?></strong>님이 로그인하였습니다.
						<input type="hidden" class="jUserId" value="<?=$loginInfo["id"] ?>" />

						<a href="/action_front.php?cmd=Admin.logout&flow=ToS"><img src="/admin/inc/images/btn_logout.gif"  alt="로그아웃" width="48" height="12" align="absmiddle"></a>
					</td>
				</tr>
			</table>
		</div>

		<!-- topmenu area -->

		<!-- 메인메뉴요 -->
		<div class="nav">

			<ul>
				<li>
					<a matchUrl="/admin/userManage" href="/admin/userManage/userList.php">회원관리</a>
				</li>
				<li>
					<a matchUrl="/admin/storeManage" href="/admin/storeManage/storeList.php">매장관리</a>
				</li>
				<li>
					<a matchUrl="/admin/itemManage" href="/admin/itemManage/itemList.php">아이템관리</a>
				</li>
				<li>
					<a matchUrl="/admin/keywordManage" href="/admin/keywordManage/modifyKeyword.php">검색엔진</a>
				</li>
				<li>
					<a matchUrl="/admin/memberManage" href="/admin/memberManage/membersclub.php">멤버스클럽</a>
				</li>
				<li>
					<a matchUrl="/admin/centerManage" href="/admin/centerManage/qnaList.php">고객센터</a>
				</li>
				<li>
					<a matchUrl="/admin/boardManage" href="/admin/boardManage/noticeList.php">게시물관리</a>
				</li>
				<li>
					<a matchUrl="/admin/statisticsManage" href="/admin/statisticsManage/statistics.php?pageType=1">통계</a>
				</li>
				<li>
					<a matchUrl="/admin/etcManage" href="/admin/etcManage/pushList.php">기타관리</a>
				</li>
				<li>
					<a matchUrl="/admin/accountManage" href="/admin/accountManage/accountView.php">계정관리</a>
				</li>

			</ul>
		</div>
		<!-- //메인메뉴요 -->

	</div>
	<!-- // header -->

	<div class="conHeight">
		<!-- contents start-->
		<div class="contWrap">

