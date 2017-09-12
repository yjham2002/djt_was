package com.appg.djTalk.common.bean;

/**
 * 페이징계산 위한 엔티티
 * @author nukiboy
 *
 */
public class ListBean{
	
	/** 현재 페이지 **/
	private int page = 0 ;
	/** 현재 블럭 **/
	private int pageBlock = 0 ;
	/** 한페이당 보여질 로우 **/
	private int rowPerPage = 10 ;
	/** 한블럭당 보여질 페이지 수 **/
	private int pagePerBlock = 10 ;
	/** 총 로우수 **/
	private long totalRow = 0 	;
	/** 가상 번호 **/
	private long virtualNum = 0 	;
	/** 총 페이지 수 **/
	private int totalPage = 0 ;
	/** 총 블럭 수 **/
	private int totalBlock = 0 ;
	/** 현재 블럭에서 시작 페이지 **/
	private int startPage = 0 ;
	/** 현재 블럭에서 끝 페이지 **/
	private int endPage = 0 ;
	/** 이전 블럭 여부 **/
	private int isPrevBlock = 0 ;
	/** 다음 블럭 여부 **/
	private int isNextBlock = 0 ;
	
	
	/**
	 * 기본 10개 
	 * @param page
	 */
	public ListBean(int page)	{
		
		this(10,10,page) ;
	}	
	
	public ListBean(int rowPerPage ,int page)	{
		
		this(10,rowPerPage,page) ;
	}		
	
	public ListBean(int pagePerBlock,int rowPerPage ,int page)	{
		
		this.pagePerBlock	= pagePerBlock	;
		this.rowPerPage		= rowPerPage	;		
		this.page				= page			;
	}	
	
	/**
	 * 주어진 총 로우 수를 가지고 값을 세팅한다.
	 * @param totalRow
	 */
	public void commit(long totalRow)
	{
		this.totalRow = totalRow ;
		
		// 전체페이지수
		this.totalPage = (int)( totalRow / (long)rowPerPage ) ;
		this.totalPage += ( (totalRow % rowPerPage) != 0 ) ? 1 : 0 ;		
		
		// 페이지계산
		this.page = ( page > totalPage )	? 1 : page ;
		this.page = ( page < 1 )			? 1 : page ;		
		
		int na		= this.page % pagePerBlock ;
		int hae		= this.page / pagePerBlock ;
		
		// 블럭계산
		this.pageBlock	= ( na != 0 ) ? hae + 1 : hae ;
		
		this.virtualNum = this.totalRow - ( rowPerPage * (this.page - 1) ) ;		
		
//		System.out.println(" na = " + na + " hae = " + hae + "this.page  =" + this.page  + ":  pagePerBlock = " + pagePerBlock + "rowPerPage = " + rowPerPage  + "v = " + virtualNum ) ;
				
		// 전체블럭계산
		this.totalBlock	= totalPage / pagePerBlock ; 
		this.totalBlock += ( (totalPage % pagePerBlock) != 0 ) ? 1: 0 ;
						
		this.startPage = (pagePerBlock * (pageBlock - 1)) + 1 ;
		
//		System.out.println("pagePerBlock = " + pagePerBlock + ":: pageBlock = " + pageBlock) ;

		this.endPage = ( pageBlock == totalBlock ) ? totalPage : ( pagePerBlock * pageBlock ) ;
		
		if( totalBlock == 0 ) this.endPage = 0 ;
		
		this.isPrevBlock = ( this.pageBlock == 1 ) ? 0 : 1 ;
		this.isNextBlock = ( this.pageBlock != this.totalBlock && this.totalBlock != 0 ) ? 1 : 0 ;
				
		
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageBlock() {
		return pageBlock;
	}

	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public int getPagePerBlock() {
		return pagePerBlock;
	}

	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}

	public long getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(long totalRow) {
		this.totalRow = totalRow;
	}

	public long getVirtualNum() {
		return virtualNum;
	}

	public void setVirtualNum(long virtualNum) {
		this.virtualNum = virtualNum;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalBlock() {
		return totalBlock;
	}

	public void setTotalBlock(int totalBlock) {
		this.totalBlock = totalBlock;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getIsPrevBlock() {
		return isPrevBlock;
	}

	public void setIsPrevBlock(int isPrevBlock) {
		this.isPrevBlock = isPrevBlock;
	}

	public int getIsNextBlock() {
		return isNextBlock;
	}

	public void setIsNextBlock(int isNextBlock) {
		this.isNextBlock = isNextBlock;
	}

}
