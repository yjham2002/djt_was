<script>
$(document).ready(function(){
	$("ul.sidemenu li[matchUrl='" + _fullUrlPath + "']").parents("ul.sidemenu").addClass("selected");
});
</script>

<div class="leftWrap">
	<!--<ul  class="selected" >-->

	<ul class="sidemenu">
		<a href="/admin/keywordManage/modifyKeyword.php">
			<h1>히든키워드</h1>
		</a>

		<li matchUrl="/admin/keywordManage">
			<a href="/admin/keywordManage/modifyKeyword.php">히든키워드 추가/변경신청</a>
		</li>
		<li matchUrl="/admin/keywordManage">
			<a href="/admin/keywordManage/searchWordList.php">검색/등록어 키워드관리</a>
		</li>
		<li matchUrl="/admin/keywordManage">
			<a href="/admin/keywordManage/keywordList.php">히든 키워드 관리</a>
		</li>
		<li matchUrl="/admin/keywordManage">
			<a href="/admin/keywordManage/imageKeyword.php">이미지 키워드 관리</a>
		</li>


		<a href="/admin/keywordManage/orgKeyword.php">
			<h1>통합데이터관리</h1>
		</a>

		<li matchUrl="/admin/keywordManage">
			<a href="/admin/keywordManage/orgKeyword.php">원자키워드</a>
		</li>
		<li matchUrl="/admin/keywordManage">
			<a href="/admin/keywordManage/follow.php">리더-팔로우테이블</a>
		</li>
		<li matchUrl="/admin/keywordManage">
			<a href="/admin/keywordManage/sameMean.php">이음동의어</a>
		</li>
		<li matchUrl="/admin/keywordManage">
			<a href="/admin/keywordManage/sameWord.php">동음이의어</a>
		</li>
		<li matchUrl="/admin/keywordManage">
			<a href="/admin/keywordManage/symbiosis.php">공생관계</a>
		</li>
		<li matchUrl="/admin/keywordManage">
			<a href="/admin/keywordManage/intimate.php">밀접관계</a>
		</li>
		<li matchUrl="/admin/keywordManage">
			<a href="/admin/keywordManage/weighting.php">가중치부여</a>
		</li>

		<a href="/admin/keywordManage/searchKeywordWithPPS.php">
			<h1>PPS 확인</h1>
		</a>

		<li matchUrl="/admin/keywordManage">
			<a href="/admin/keywordManage/searchKeywordWithPPS.php">PPS 확인</a>
		</li>


	</ul>


	<div class="left_blank">

	</div>

</div>