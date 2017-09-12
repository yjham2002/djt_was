<script>
$(document).ready(function(){
	$("ul.sidemenu li[matchUrl='" + _fullUrlPath + "']").parents("ul.sidemenu").addClass("selected");
});
</script>

<div class="leftWrap">
	<!--<ul  class="selected" >-->
	
	<ul class="sidemenu">
		<a href="/admin/itemManage/itemList.php">
			<h1>아이템관리</h1>
		</a>

		<li matchUrl="/admin/itemManage">
			<a href="/admin/itemManage/itemList.php">아이템관리</a>
		</li>


	</ul>


	<div class="left_blank">

	</div>

</div>