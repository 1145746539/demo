package com.yc.dingcan.dao;

import com.yc.dingcan.bean.Resfood;

public interface ResfoodDao extends BaseDao<Resfood> {
	
	public boolean isUsed(Integer fid);
}
