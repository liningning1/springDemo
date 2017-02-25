package com.lanyuan.pulgin.mybatis.plugin;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.PropertyException;

import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.lanyuan.pulgin.jdbc.dialect.Dialect;
import com.lanyuan.util.Common;
import com.lanyuan.util.PageView;
import com.mysql.jdbc.Connection;

/**
 * mybatis的分页查询插件,通过拦截StatementHandler的prepare的方法实现
 * 只有在参数列表中包含Page类型的参数时才进行分页查询
 * 在多参数的情况下,只对第一个类型的Page参数生效
 * 另外,在参数列表中Page类型的参数无需用@param来标注
 * @author linn
 * 接口 Interceptor 中的方法介绍
 * plugin方法主要是用于封装目标对象，通过该方法我们可以决定是否要进行拦截进而决定返回什么样的目标对象。
 * intercept方法就是要进行拦截的时候执行的方法。
 * setProperties主要用于在配置文件中指定属性,
 * 这个方法在Configuration初始化当前的Interceptor时就会执行.
 * 在mybatis中有一个plugin类，该类包括静态方法wrap,通过该方法可以决定需要返回的对象是目标对象还是代理。
 */
/**
 * getSignatureMap(Interceptor interceptor)
 * :该方法的第一句话就是获得Intercepts注解，这种方法应该很容易理解。
 * 那么接下来将获得在Intercepts里面的参数@Signature注解内容，在该注解中包含三个参数，
 * 分别是type，method，args。Type指定要拦截的类对象，method是指明要拦截该类的哪个方法，
 * 第三个是指明要拦截的方法参数集合。在Intercepts中可以配置多个@Signature。那么便对这写值进行遍历，
 * 已获得对应的type、method以及args。最终是获得一个HashMap对象，这些对象里面的键是类对象，
 * 而值是指定的类中方法对象。执行该端程序之后，更具target的classLoader和接口，来创建一个代理，
 * 并且，InvocationHandler是创建一个新的Plugin对象，
 * 同时将target，interceptor以及signatureMap传递给Plugin对象，
 * 当然，这里的Plugin也实现了Invocation接口。
 * 那么target对象所有的方法调用都会触发Plugin中的invoke方法，那么这里将执行开发者所有插入的操作
 * 
 *
 *首先，开发者需要实现MyBatis的Interceptor接口，
 *并主要要实现interceptor和plugin方法，
 *而setProperties()当你配置了property就需要实现，没有，那么可以不用实现。
 *实现接口后，那么就要将插件配置到MyBatis中去，然后通过XMLConfigBuilder来实例化插件对象，
 *并将他们放到Configuration对象的InterceptChain对象的List集合中，
 *然后在Configuration各种new的方法中调用InterceptChain的pluginAll方法，
 *这里面将调用各个插件的plugin方法，这个方法里面则就调用Plugin的wrap方法，
 *这个方法将要传入target和this(也就是插件自身对象)。
 *那么在Plugin对象里面将创建一个代理对象，并且为这个代理对象创建一个InvocationHandler对象，
 *这里将拦截代理对象的所有方法执行过程，及触发invoke方法，
 *这里将执行实现的插件行为。这就是MyBatis的插件实现以及执行的过程。
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PagePlugin  implements Interceptor{

	private static Dialect dialectObject =null;//数据库方言
	private static String pageSqlId = "";//mybatis的数据库xml映射文件中需要拦截的ID(正则匹配)
	@Override
	public Object intercept(Invocation ivk) throws Throwable {
		
		// Object instanceof class :Object 是否是 class的实例，若是则返回true,反之亦然。
		if(ivk.getTarget() instanceof RoutingStatementHandler)
		{
			RoutingStatementHandler statementHandler = (RoutingStatementHandler)ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
		    MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
		    /**
			 * 方法1：通过id来区分是否需要分页．.*query.*
			 * 方法2：传入的参数是否有page参数，如果有，则分页，
			 */
		    BoundSql boundSql = delegate.getBoundSql();
		    //分页SQL <select>中parameterType属性对应的实体参数，
		    //即Mapper接口中执行分页方法的参数,该参数不得为空
		    Object parameterObject = boundSql.getParameterObject();
		    if(parameterObject ==null)
		    {
		    	return ivk.proceed();
		    }
		    else
		    {
		    	PageView pageView =null;
		    	if(parameterObject instanceof PageView)
		    	{
		    		pageView = (PageView)parameterObject;//参数就是pages实体
		    	}
		    	else if(parameterObject instanceof Map)
			    {
		    		for(Entry entry:(Set<Entry>)((Map)parameterObject).entrySet())
		    		{
		    			if(entry.getValue() instanceof PageView)
		    			{
		    				pageView = (PageView) entry.getValue();
		    			}
		    			break;
		    		}
			    } 
		    	else
		    	{//参数为某个实体,该实体拥有某个Pages属性
		    		pageView = ReflectHelper.getValueByFieldType(parameterObject, PageView.class);
		    	    if(pageView == null)
		    	    {
		    	      return ivk.proceed();	
		    	    }
		    	    
		    	    
		    	}
		    	String sql = boundSql.getSql();
		    	PreparedStatement preparedStatement = null;
		    	ResultSet rs = null;
		    	try
		    	{
		    		Connection connection = (Connection)ivk.getArgs()[0];
		    		String countSql = "select count(1) from ("+sql+")tmp_count";//记录统计
		    		preparedStatement = connection.prepareStatement(countSql);
		    		ReflectHelper.setValueByFieldName(boundSql, "sql",countSql);
		    		DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement,parameterObject,boundSql);
		    	    parameterHandler.setParameters(preparedStatement);
		    	    rs = preparedStatement.executeQuery();
		    	    int count = 0;
		    	    if(rs.next())
		    	    {
		    	    	count = ((Number)rs.getObject(1)).intValue();
		    	    }
		    	    pageView.setRowCount(count);
		    	}finally
		    	{
		    		try{
		    			rs.close();
		    		}catch(Exception e)
		    		{
		    			
		    		}
		    		try
		    		{
		    			preparedStatement.close();
		    		}catch(Exception e)
		    		{
		    			
		    		}
		    	}
		    
		    	String pageSql = generatePagesSql(sql,pageView);
		    	ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);
		    	
		    	
		    }
		}
		
		return ivk.proceed();
	}
	
	/**
	 * 根据数据库方言,产生特定的分页sql 
	 */
	private String generatePagesSql(String sql, PageView page)
	{
		if(page !=null && dialectObject !=null)
		{
			//pageView 默认是从1,而数据库是从0开始计算 所以(page.getPageNow()-1)
			int pageNow = page.getPageNow();
			return dialectObject.getLimitString(sql, (pageNow<=0?0:pageNow-1)*page.getPageSize(), page.getPageSize());
		}
		return sql;
	}
	

	@Override
	public Object plugin(Object target) {
		
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties p) {
		String dialect="";//数据库方言
		dialect = p.getProperty("dialect");
		if (Common.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		} else {
			
			try {
				dialectObject = (Dialect)Class.forName("dialect").getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new RuntimeException(dialect + ", init fail!\n" + e);
			}
		}
		
		pageSqlId = p.getProperty("pageSqlId");//根据id来区分是否需要分页
		if (Common.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}

}
