package com.appg.djTalk.common.util;

public class PagingUtil
{

	private int	startPageNum	= 0;
	private int	endPageNum		= 0;
	private int	curPage			= 0;
	private int	rowPerPage		= 0;

	public PagingUtil(int curPage, int rowPerPage)
	{
		this.curPage = curPage;
		this.rowPerPage = (rowPerPage == 0) ? 20 : rowPerPage;
		this.startPageNum = ((curPage - 1) * rowPerPage);
		this.endPageNum = this.startPageNum + rowPerPage - 1;
	}

	public PagingUtil(int curPage)
	{
		this.curPage = curPage;
		this.rowPerPage = 20;
		this.startPageNum = ((curPage - 1) * rowPerPage);
		this.endPageNum = this.startPageNum + rowPerPage;
	}

	public int getStartPageNum()
	{
		return startPageNum;
	}

	public void setStartPageNum(int startPageNum)
	{
		this.startPageNum = startPageNum;
	}

	public int getEndPageNum()
	{
		return endPageNum;
	}

	public void setEndPageNum(int endPageNum)
	{
		this.endPageNum = endPageNum;
	}

	public int getCurPage()
	{
		return curPage;
	}

	public void setCurPage(int curPage)
	{
		this.curPage = curPage;
	}

	public int getRowPerPage()
	{
		return rowPerPage;
	}

	public void setRowPerPage(int rowPerPage)
	{
		this.rowPerPage = rowPerPage;
	}

}
