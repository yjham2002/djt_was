<script>
$(document).ready(function(){
	$("ul.sidemenu li[matchUrl='" + _fullUrlPath + "']").parents("ul.sidemenu").addClass("selected");
});
</script>

<div class="leftWrap">
	<!--<ul  class="selected" >-->

	<ul class="sidemenu">
		<a href="/admin/memberManage/membersclub.php">
			<h1>멤버스클럽</h1>
		</a>

		<li matchUrl="/admin/memberManage">
			<a href="/admin/memberManage/membersclub.php">멤버스클럽</a>
		</li>
		
		<li matchUrl="/admin/memberManage">
			<a href="/admin/memberManage/coupon.php">쿠폰관리</a>
		</li>

	</ul>


	<div class="left_blank">

	</div>

</div>