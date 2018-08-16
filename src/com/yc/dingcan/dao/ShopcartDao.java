package com.yc.dingcan.dao;

import java.util.List;

import com.yc.dingcan.bean.Resfood;
import com.yc.dingcan.bean.Shopcart;

public interface ShopcartDao extends BaseDao<Shopcart> {
	
	public List<Shopcart> selectallById(Integer userid);
	
	public int number(Integer userid,Integer shid);
	
	public int deleteByUserid(Integer userid);
	
	public int deletByShid(Integer userid,Integer shid);
	
	public Shopcart selectOneById(Integer userid,Integer shid);
	

}
