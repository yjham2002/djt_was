<script>
$(document).ready(function(){
	$("ul.sidemenu li[matchUrl='" + _fullUrlPath + "']").parents("ul.sidemenu").addClass("selected");
});
</script>

<div class="leftWrap">
	<!--<ul  class="selected" >-->
	
	<ul class="sidemenu">
		<a href="/admin/etcManage/pushList.php">
			<h1>기타관리</h1>
		</a>

		<li matchUrl="/admin/etcManage">
			<a href="/admin/etcManage/pushList.php">푸시관리</a>
		</li>
		<li matchUrl="/admin/etcManage">
			<a href="/admin/etcManage/clause.php">약관관리</a>
		</li>


	</ul>


	<div class="left_blank">

	</div>

</div>