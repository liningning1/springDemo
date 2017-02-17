package com.lanyuan.util;

/**
 * 页码的索引类
 * @author linn
 *
 */
public class PageIndex {

	private long startindex;
	private long endindex;
	public PageIndex(long startindex, long endindex) {
		super();
		this.startindex = startindex;
		this.endindex = endindex;
	}
	public long getStartindex() {
		return startindex;
	}
	public void setStartindex(long startindex) {
		this.startindex = startindex;
	}
	public long getEndindex() {
		return endindex;
	}
	public void setEndindex(long endindex) {
		this.endindex = endindex;
	}
}
