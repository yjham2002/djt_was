<script>
$(document).ready(function(){
	$("ul.sidemenu li[matchUrl='" + _fullUrlPath + "']").parents("ul.sidemenu").addClass("selected");
});
</script>

<div class="leftWrap">
	<!--<ul  class="selected" >-->

	<ul class="sidemenu">
		<a href="/admin/storeManage/storeList.php">
			<h1>매장관리</h1>
		</a>

		<li matchUrl="/admin/storeManage">
			<a href="/admin/storeManage/storeList.php">매장관리</a>
		</li>
		<li matchUrl="/admin/storeManage">
			<a href="/admin/storeManage/requestList.php">매장관리 신청</a>
		</li>
		<li matchUrl="/admin/storeManage">
			<a href="/admin/storeManage/addAddressList.php">매장주소 추가 신청</a>
		</li>
		<li matchUrl="/admin/storeManage">
			<a href="/admin/storeManage/modifyAddressList.php">매장주소 변경 신청</a>
		</li>
		<li matchUrl="/admin/storeManage">
			<a href="/admin/storeManage/modifyNameList.php">매장명 변경 신청</a>
		</li>
		<li matchUrl="/admin/storeManage">
			<a href="/admin/storeManage/reportList.php">신고매장</a>
		</li>

	</ul>



	<div class="left_blank">

	</div>

</div>