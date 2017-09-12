<script>
$(document).ready(function(){
	$("ul.sidemenu li[matchUrl='" + _fullUrlPath + "']").parents("ul.sidemenu").addClass("selected");
});
</script>

<div class="leftWrap">
	<!--<ul  class="selected" >-->

	<ul class="sidemenu">
		<a href="/admin/statisticsManage/statistics.php">
			<h1>통계</h1>
		</a>

		<li matchUrl="/admin/statisticsManage">
			<a href="/admin/statisticsManage/statistics.php?pageType=1">회원별</a>
		</li>
		<li matchUrl="/admin/statisticsManage">
			<a href="/admin/statisticsManage/statistics.php?pageType=2">일자별</a>
		</li>

		<a href="/admin/statisticsManage/cms.php">
			<h1>CMS</h1>
		</a>
		<li matchUrl="/admin/statisticsManage">
			<a href="/admin/statisticsManage/cms.php">CMS 등록</a>
		</li>
		<li matchUrl="/admin/statisticsManage">
			<a href="/admin/statisticsManage/cmsList.php">CMS 관리</a>
		</li>


	</ul>

	<div class="left_blank">

	</div>

</div>