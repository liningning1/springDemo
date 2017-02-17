package com.lanyuan.util;

/**
 * 这是一个分页工具类
 * 主要用于显示记录数   --当前页码的index范围 从 startPage 到 endPage
 * @author linn
 * pagecode : 规定显示几个页码
 * pageNow : 当前页
 * pageCount : 总页数
 */
public class WebTool {

	public static PageIndex getPageIndex(long pagecode,int pageNow,long pageCount)
	{
		long startPage = pageNow -(pagecode%2==0?pagecode/2-1:pagecode/2);
		long endPage = pageNow + pagecode/2;
		if(startPage<1)
		{
			startPage = 1;
			if(pageCount >= pagecode) 
				endPage = pagecode;
			else
				endPage = pageCount;	
		}
		return new PageIndex(startPage,endPage);
	}
	
}
