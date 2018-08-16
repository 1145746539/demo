package com.yc.dingcan.dao;

import java.util.List;


public interface BaseDao <T>{
	
	public int insert(T t);
	
	public int delete(T t);
	
	public int updata(T t);
	
	public List<T> select(T t, int page, int rows);
	
	public T selectById(Integer id);
	
	public long count(T t);
}
