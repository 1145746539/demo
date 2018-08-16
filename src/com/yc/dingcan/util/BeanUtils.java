package com.yc.dingcan.util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletRequest;

public class BeanUtils {

	static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将请求参数转换设置到bean的各个字段中
	 * @param request
	 * @param t
	 * @return
	 */
	public static <T> T toBean(ServletRequest request, T t) {
		//输入的值对象t为空则退出
		if (t == null)
			return t;
		for (Field f : t.getClass().getDeclaredFields()) {
			//获取参数值
			String svalue = request.getParameter(f.getName());
			//空值跳过
			if (svalue == null)
				continue;
			try {
				//声明转换后的值变量
				Object ovalue = cast(svalue, f.getType());
				f.setAccessible(true);
				f.set(t, ovalue);
			} catch (Exception e) {
				System.err.println("请求参数解析错误：参数名=" + f.getName() + "，字段类型=" + f.getType() + "，参数值=" + svalue);
			}
		}

		return t;

	}

	@SuppressWarnings("unchecked")
	/**
	 * 将字符串转成指定的类型
	 * @param svalue
	 * @param cls
	 * @return
	 */
	public static <T> T cast(String svalue, Class<T> cls) throws ParseException {
		//空值跳过
		if (svalue == null)
			return null;
		//声明转换后的值变量
		T ovalue = null;
		//判断字段的类型
		switch (cls.getName().toLowerCase()) {
		case "java.lang.string":
			ovalue = (T) svalue;
			break;
		case "java.lang.byte":
			ovalue = (T) Byte.valueOf(Byte.parseByte(svalue));
			break;
		case "java.lang.short":
			ovalue = (T) Short.valueOf(Short.parseShort(svalue));
			break;
		case "java.lang.integer":
			ovalue = (T) Integer.valueOf(Integer.parseInt(svalue));
			break;
		case "java.lang.long":
			ovalue = (T) Long.valueOf(Long.parseLong(svalue));
			break;
		case "java.lang.float":
			ovalue = (T) Float.valueOf(Float.parseFloat(svalue));
			break;
		case "java.lang.double":
			ovalue = (T) Double.valueOf(Double.parseDouble(svalue));
			break;
		case "java.lang.boolean":
			ovalue = (T) Boolean.valueOf(Boolean.parseBoolean(svalue));
			break;
		case "java.util.date":
			ovalue = (T) new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(svalue);
			break;
		case "java.sql.date":
			ovalue = (T) new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(svalue);
			ovalue = (T) new java.sql.Date(((Date) ovalue).getTime());
			break;
		case "java.sql.timestamp":
			ovalue = (T) new SimpleDateFormat(DEFAULT_DATETIME_FORMAT).parse(svalue);
			ovalue = (T) new Timestamp(((Date) ovalue).getTime());
			break;
		}
		return ovalue;
	}

}
