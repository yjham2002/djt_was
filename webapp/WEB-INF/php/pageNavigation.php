<div class="pageNumber">
<?
    $obj->req["page"] = ($obj->req["page"] == "") ? "1" : $obj->req["page"];
	if($pager->startPage - 1 < 0){
		$pager->startPage = 1;
	}
?>
    <?
    if( $pager->isPrevBlock)
    {
		// echo '<a href="#" ><img class="jPage" page="1" src="/admin/inc/images/paging_first.gif" alt="처음페이지"></a>';
        echo '<a href="#" ><img class="jPage" page="' . ($pager->startPage - 1) . '" src="/admin/inc/images/paging_prev.gif" alt="이전"></a>';
    }

    for($i = $pager->startPage ; $i <= $pager->endPage  ; $i++ )
    {
        if( $obj->req["page"] == $i )
            echo '<a href="#" ><strong>'.$i.'</strong></a>' ;
        else
            echo '<a href="#" class="jPage" page="'.$i.'">'.$i.'</a>' ;
    }

    if( $pager->isNextBlock)
    {
        // $obj->endPage = ($obj->endPage == 0) ? 10 : $obj->endPage ;

        echo '<a href="#" ><img class="jPage" page="' . ($pager->endPage + 1) . '" src="/admin/inc/images/paging_next.gif" alt="다음"></a>';
		// echo '<a href="#" ><img class="jPage" page="' . ($pager->totalPage) . '" src="/admin/inc/images/paging_last.gif" alt="마지막페이지"></a>';
    }
    ?>
</div>
