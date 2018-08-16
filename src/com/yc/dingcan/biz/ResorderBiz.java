package com.yc.dingcan.biz;

import java.util.List;

import com.yc.dingcan.bean.Resorder;

public interface ResorderBiz {
	
	/**
	 * 插入Resorder表数据
	 * @param resorder
	 * @return
	 * @author 张胜
	 */
	public int insert(Resorder resorder);
	
	/**
	 * 通过用户id查询他的订单
	 * @param userid
	 * @return List<Resorder>
	 * @author 张胜
	 */
	public List<Resorder> findByuserid(Integer userid);
	
	/**
	 *修改送货状态 
	 * @param resorder
	 * @return
	 * @author 张胜
	 */
	public int update(Resorder resorder);

	/**
	 * 后台查询所有的用户的订单
	 * @param resorder
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Resorder> select(Resorder resorder, int page, int rows);
	
	public long total();
}
