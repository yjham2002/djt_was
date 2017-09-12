<script>
$(document).ready(function(){
	$("ul.sidemenu li[matchUrl='" + _fullUrlPath + "']").parents("ul.sidemenu").addClass("selected");
});
</script>

<div class="leftWrap">
	<!--<ul  class="selected" >-->

	<ul class="sidemenu">
		<a href="/admin/accountManage/accountList.php">
			<h1>계정 관리</h1>
		</a>

		<li matchUrl="/admin/accountManage">
			<a href="/admin/accountManage/accountView.php">계정관리</a>
		</li>
		<li matchUrl="/admin/accountManage">
			<a href="/admin/accountManage/accountList.php">서브계정관리</a>
		</li>


	</ul>

	<div class="left_blank">

	</div>

</div>