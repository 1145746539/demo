package com.yc.dingcan.dao;

import java.util.List;

import com.yc.dingcan.bean.Resuser;

public interface ResuserDao extends BaseDao<Resuser> {
	/**
	 * 查找全部用户
	 * @return List<Resuser>
	 * @author 张胜
	 */
	public List<Resuser> selectall();
	
	public List<Resuser> selectByName(String name);
	
	public int updatebymanage(Resuser resuser);

}
