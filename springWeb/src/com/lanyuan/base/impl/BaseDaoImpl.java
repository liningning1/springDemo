package com.lanyuan.base.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.lanyuan.base.BaseDao;
import com.lanyuan.util.PageView;

/**
 * 集合了持久层的公用的增删改查的接口
 * @author linn
 *
 */

public  class BaseDaoImpl<T> extends SqlSessionDaoSupport implements BaseDao{

	//获取传递的泛型类的类名
	public String getClassName()
	{
		//在父类中得到子类声明的父类的泛型信息
		ParameterizedType pt =(ParameterizedType)this.getClass().getGenericSuperclass();
		Class<T> clazz= (Class)pt.getActualTypeArguments()[0];	
		return clazz.getSimpleName().toString().toLowerCase();	
	}
	
	@Override
	public List<T> query(PageView pageView, Object t) {
		
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("paging", pageView);
		map.put("t", t);
		
		return this.getSqlSession().selectList(this.getClassName()+".query",map);
	}

	@Override
	public void delete(Integer id) {
		
		this.getSqlSession().delete(this.getClassName()+".deletdById", id);
	}

	public Object getById(Integer id) {
		
		return this.getSqlSession().selectOne(this.getClassName()+".getById", id);
	}

	@Override
	public List queryAll(Object t) {
		return this.getSqlSession().selectList(this.getClassName()+".queryAll",t);
	}

	@Override
	public void add(Object t) {
		this.getSqlSession().insert(this.getClassName()+".add", t);
		
	}

	@Override
	public void modify(Object t) {
		this.getSqlSession().update(this.getClassName()+".update", t);
		
	}

}
