package com.lanyuan.pulgin.jdbc.dialect;

/**
 * 分页
 * @author linn
 *
 */
public class Dialect {

	public boolean supportsLimit()
	{
		return false;
	}
	
	public boolean supportsLimitOffset()
	{
		return supportsLimit();
	}
	
	/**
	* 将sql变成分页sql语句,直接使用limit,offset的值作为占位符.
	 * 
	 */
	public String getLimitString(String sql,int offset,int limit)
	{
		
		return getLimitString(sql,offset,Integer.toString(offset),limit,Integer.toString(offset));
		
	}

	/**
     * 将sql变成分页sql语句,提供将offset及limit使用占位符(placeholder)替换.
     * <pre>
     * 如mysql
     * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 将返回
     * select * from user limit :offset,:limit
     * </pre>
     * @return 包含占位符的分页sql
     */
	private String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {

		throw new UnsupportedOperationException("paged queries not supported");
	}
}
