package com.yc.dingcan.dao;

import java.util.List;

import com.yc.dingcan.bean.Resadmin;

public interface ResadminDao extends BaseDao<Resadmin>{
	public List<Resadmin> selectall();
	
}
