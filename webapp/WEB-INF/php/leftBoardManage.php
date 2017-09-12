<script>
$(document).ready(function(){
	$("ul.sidemenu li[matchUrl='" + _fullUrlPath + "']").parents("ul.sidemenu").addClass("selected");
});
</script>

<div class="leftWrap">
	<!--<ul  class="selected" >-->
	
	<ul class="sidemenu">
		<a href="/admin/boardManage/noticeList.php">
			<h1>게시물관리</h1>
		</a>

		<li matchUrl="/admin/boardManage">
			<a href="/admin/boardManage/noticeList.php">공지사항</a>
		</li>
		<li matchUrl="/admin/boardManage">
			<a href="/admin/boardManage/eventList.php">이벤트</a>
		</li>
		<li matchUrl="/admin/boardManage">
			<a href="/admin/boardManage/mainManage.php">메인관리</a>
		</li>
		<li matchUrl="/admin/boardManage">
			<a href="/admin/boardManage/bannerList.php">배너관리</a>
		</li>
		<li matchUrl="/admin/boardManage">
			<a href="/admin/boardManage/requestList.php">서비스신청관리</a>
		</li>


	</ul>


	<div class="left_blank">

	</div>

</div>