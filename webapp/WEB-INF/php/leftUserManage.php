<script>
$(document).ready(function(){
	$("ul.sidemenu li[matchUrl='" + _fullUrlPath + "']").parents("ul.sidemenu").addClass("selected");
});
</script>

<div class="leftWrap">
	<!--<ul  class="selected" >-->

	<ul class="sidemenu">
		<a href="/admin/userManage/userList.php">
			<h1>회원 관리</h1>
		</a>

		<li matchUrl="/admin/userManage">
			<a href="/admin/userManage/userList.php">회원관리</a>
		</li>

		<li matchUrl="/admin/userManage">
			<a href="/admin/userManage/withdrawalList.php">탈퇴회원</a>
		</li>
		
	</ul>

	<div class="left_blank">

	</div>

</div>