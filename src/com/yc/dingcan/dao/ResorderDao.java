package com.yc.dingcan.dao;

import java.util.List;

import com.yc.dingcan.bean.Resorder;

public interface ResorderDao extends BaseDao<Resorder> {
	
	/**
	 * 查询某用户的全部订单
	 * @param userid 用户id
	 * @return
	 * @author 张胜
	 */
	public List<Resorder> selectall(Integer userid);
	
}
