package com.yc.dingcan.biz;

import java.util.List;

import com.yc.dingcan.bean.Shopcart;

public interface ShopcartBiz {
	
	/**
	 * //查询某用户的购物车，算钱
	 * @return
	 */
	public List<Shopcart> findallById(Integer userid);
	
	/**
	 * 用来查找购物车中某个菜品的钱,通过用户id和菜品id
	 * @param userid  用户id
	 * @param shid  菜品id
	 * @return List
	 * @author 张胜
	 */
	public Shopcart findOneById(Integer userid,Integer shid);
	
	/**
	 * 用来查找购物车中这个菜品数量,通过用户id和菜品id
	 */
	public int findnumber(Integer userid,Integer shid);
	
	/**
	 * 插入购物车
	 * @param shopcart
	 * @return
	 */
	public int insert(Shopcart shopcart);
	
	/**
	 * 修改菜的数量
	 * @param shopcart
	 * @return
	 */
	public int updateByShid(Shopcart shopcart);
	
	/**
	 * 根据用户id来清除购物车
	 * @param shopcart
	 * @return
	 */
	public int deleteByUserid(Integer userid);
	
	/**
	 * 通过用户的id和菜品的id来删除购物车中的商品
	 * @param shopcart
	 * @return
	 */
	public int deletByShid(Integer userid,Integer shid);


}
