package com.lanyuan.pulgin.mybatis.plugin;

import java.lang.reflect.Field;

/*
 * 反射工具
 */
public class ReflectHelper {

	/**
	 * 获取obj的FieldName的Field
	 */
	public static Field getFieldByFieldName(Object obj,String fieldName)
	{
		if(obj == null || fieldName == null)
		{	
			return null;
		}
		
		for(Class<?> superClass = obj.getClass();superClass!=Object.class;superClass = superClass.getSuperclass())
		{
			try
			{
				return superClass.getDeclaredField(fieldName);
			}
			catch(Exception e)
			{
				
			}
		}
		return null;
		
	}
    /**
     * 返回obj对象的fieldName的属性值
     */
	public static Object getValueByFieldName(Object obj,String fieldName)
	{
		Object value = null;
		try
		{
			Field field = getFieldByFieldName(obj, fieldName);
			if(field != null)
			{
				if(field.isAccessible())
				{
					value = field.get(obj);
				}
				else
				{
					field.setAccessible(true);
					value = field.get(obj);
					field.setAccessible(false);
				}
			}
			
		}
		catch(Exception e)
		{
		}
		return value;
	}

	public static <T> T getValueByFieldType(Object obj, Class<T> fieldType) {
		Object value = null;
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {

				Field[] fields = superClass.getDeclaredFields();
				for (Field field : fields) {
					if (field.getType() == fieldType) {
						if (field.isAccessible()) {
							value = field.get(obj);
							break;
						}
						else
						{
							field.setAccessible(true);
							value = field.get(obj);
							field.setAccessible(false);
							break;
						}
						
					}
				}
				if(value!=null)
				{
					break;
				}
			} catch (Exception e) {
			
			}
		}
      return (T) value;
	}
	
	public static boolean setValueByFieldName(Object obj,String filedName,Object value)
	{
		try
		{
			Field field = getFieldByFieldName(obj, filedName);
			if(field.isAccessible())
			{
				field.set(obj, value);
			}
			else
			{
				field.setAccessible(true);
				field.set(obj, value);
				field.setAccessible(false);
			}
			return true;
		}
		catch(Exception e){
		}
		return false;
	}
	
}
