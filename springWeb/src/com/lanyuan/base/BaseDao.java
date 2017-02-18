package com.lanyuan.base;

import java.util.List;

import com.lanyuan.util.PageView;

/**
 * 集合了持久层的公用的增、删、 改、 查的接口
 * <T> 表示传入的实体类
 * @author linn
 *
 */
public interface BaseDao<T> {
	
	/**
	 * 返回分页后的数据
	 * 
	 */
	public List<T> query(PageView pageView,T t);

    public List<T> queryAll(T t);//返回所有的数据
    
    public void add(T t);
    
    public void delete(int id);
    
    public void modify(T t);
    
    public T getById(int id);
	
}
