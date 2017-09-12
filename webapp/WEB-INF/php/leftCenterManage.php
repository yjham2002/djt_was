<script>
$(document).ready(function(){
	$("ul.sidemenu li[matchUrl='" + _fullUrlPath + "']").parents("ul.sidemenu").addClass("selected");
});
</script>

<div class="leftWrap">
	<!--<ul  class="selected" >-->

	<ul class="sidemenu">
		<a href="/admin/centerManage/qnaList.php">
			<h1>고객센터</h1>
		</a>

		<li matchUrl="/admin/centerManage">
			<a href="/admin/centerManage/qnaList.php">문의상담</a>
		</li>
		<li matchUrl="/admin/centerManage">
			<a href="/admin/centerManage/serviceList.php">서비스신청</a>
		</li>


	</ul>


	<div class="left_blank">

	</div>

</div>