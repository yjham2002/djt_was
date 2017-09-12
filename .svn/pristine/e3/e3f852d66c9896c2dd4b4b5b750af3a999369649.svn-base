package com.appg.djTalk.common.util;

public class ListPage {
	
	public int pagePerBlock	= 0 ;	//네비게이터에 표시할 페이지 개수 수 
	public int rowPerPage		= 0 ;	//한페이지에 표시할 게시글 갯수
	public int totalRows		= 0 ;	//총 로우수
	public int virtualNum		= 0 ;
	public int totalPage		= 0 ;
	public int totalBlock		= 0 ;
	public int startPage		= 0 ;
	public int endPage			= 0 ;
	public int cntBlock			= 0 ;
	public	int cntPage			= 0 ;	//현재페이지
	public int starNum			= 0 ;	// 시작번호
	public int endNum			= 0 ;	// 끝번호
	
	public int skipRow		= 0 ;		// skip limit 기반에서 사용될 수 있는 변수
	
	public ListPage(int pagePerBlock,int rowPerPage ,int cntPage,int totalRows)	{
		
		this.pagePerBlock	= pagePerBlock	;
		this.rowPerPage		= rowPerPage	;		
		this.cntPage			= cntPage		;
		this.totalRows		= totalRows		;
		
		init() ;

	}


	public void init()
	{		
		initTotalPage();
		initCntPage() ;
		initBlock() ;
		initTotalBlock() ;
		initStartPage();
		initEndPage() ;
		initStartNum(); 
		initEndNum() ;
		initVirtualNum() ;
				
		this.skipRow		= (cntPage - 1) * rowPerPage ;
	}
	
	private void initCntPage()
	{
		cntPage = ( cntPage > totalPage )	? 1 : cntPage ;
		cntPage = ( cntPage < 1 )			? 1 : cntPage ;		
	}
	
	/**
	 * 현재 블락을 계산한다.
	 */
	private void initBlock()
	{			
		int na		= cntPage % pagePerBlock ;
		int hae		= cntPage / pagePerBlock ;
		int block	= ( na != 0 ) ? hae + 1 : hae ;
		
		cntBlock = block ;
	}	
	
	/**
	 * 전체 블락수를 계산한다.
	 */
	private void initTotalBlock()	{
				
		this.totalBlock	= totalPage / pagePerBlock ; 
		this.totalBlock += ( (totalPage % pagePerBlock) != 0 ) ? 1: 0 ;
	}
	
	private void initTotalPage()	{
		
		this.totalPage = (totalRows / rowPerPage) ;
		this.totalPage += ( (totalRows % rowPerPage) != 0 ) ? 1 : 0 ;		
	}	
	
	private void initStartPage() {
		
		this.startPage = (pagePerBlock * (cntBlock - 1)) + 1 ;
	}
	
	private void initEndPage() {
		this.endPage = ( cntBlock == totalBlock ) ? totalPage : ( pagePerBlock * cntBlock ) ;
	}
	
	private void initStartNum()	{
		
		this.starNum = ( rowPerPage * (cntPage - 1) )  ;
	}	
	
	private void initVirtualNum()
	{
		this.virtualNum = this.totalRows - this.starNum ;
	}	
		private void initEndNum()	
	{						
		endNum = ( cntPage == totalPage ) ? totalRows : ( rowPerPage * cntPage ) ;	
	}

}
