package com.lanyuan.util;

import java.util.List;

/**
 * 分页封装函数
 * @author linn
 *
 */
public class PageView {
	
	private List records;//分页数据
	//页码的索引类,startindex,开始索引;endindex,结束索引。这个数是计算出来的
	private PageIndex pageIndex;
    private long pageCount;//总页数,是计算出来的
    private int pageSize = 10;//每页显示的记录数 --每页显示10条记录
    private int pageNow = 1;//默认当前页为第一页,这个是计算出来的
    private long rowCount;//总的记录数
    private int startPage;//从第几条记录开始
    private int pagecode = 5;//规定显示五个页码
    
    public PageView(){}
    
    //要获得记录的开始索引 第一页就是 从 0 - 9 第二页就是 10 - 19 (每页10条记录)
    public int getFirstResult()
    {
    	return (this.pageNow - 1)*pageSize;
    }
    
    public int getPagecode() {
		return pagecode;
	}

	public void setPagecode(int pagecode) {
		this.pagecode = pagecode;
	}
    
	//每页显示的记录数, 当前页
	public PageView(int pageSize,int pageNow)
	{
		this.pageSize = pageSize;
		this.pageNow = pageNow;
	}
	
	public PageView(int pageNow)
	{
		this.pageNow = pageNow;
		startPage = (this.pageNow - 1) * this.pageSize;	
	}
	
	/*
	 * rowCount : 总的记录数
	 * records : 结果集合(分页数据)
	 */
	public void setQueryResult(long rowCount,List records)
	{
		setRowCount(rowCount);
		setRecords(records);
	}
	
	public void setRowCount(long rowCount)
	{
		this.rowCount = rowCount;//总的记录数
		setPageCount(this.rowCount % this.pageSize == 0?
				    this.rowCount/this.pageSize:
				    this.rowCount/this.pageSize+1);
	}
	
	//设置总的页数
	//  在这个方法中存在一个问题，每页显示数量　和　当前页、、不能为空
	//  必需输入
	public void setPageCount(long pageCount)
	{
		this.pageCount = pageCount;
		//页码索引类
		this.pageIndex = WebTool.getPageIndex(pagecode, pageNow, pageCount);	
	}

	public List getRecords() {
		return records;
	}

	public void setRecords(List records) {
		this.records = records;
	}

	public PageIndex getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(PageIndex pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public long getPageCount() {
		return pageCount;
	}

	public long getRowCount() {
		return rowCount;
	}
	
	
}
