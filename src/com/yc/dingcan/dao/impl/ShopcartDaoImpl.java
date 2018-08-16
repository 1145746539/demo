package com.yc.dingcan.dao.impl;

import java.util.List;
import java.util.Map;

import com.yc.dingcan.bean.Shopcart;
import com.yc.dingcan.dao.ShopcartDao;
import com.yc.dingcan.util.DBHelper;

public class ShopcartDaoImpl implements ShopcartDao {

	public int insert(Shopcart t) {
		String sql = "insert into shopcart values(?,?,?,?,?)";
		int w = DBHelper.doUpdate(sql, t.getUserid(), t.getShid(), t.getShname(), t.getShprice(), t.getShnumber());
		return w;
	}

	/**
	 * 查询全部并且把一种菜品的钱算出来
	 */
	public Shopcart selectById(Integer id) {
		return null;
	}

	@Override
	public long count(Shopcart t) {
		// TODO Auto-generated method stub
		return 0;
	}

	// 查询某用户的购物车，算钱
	public List<Shopcart> selectallById(Integer userid) {
		String sql = "select * from shopcart where userid=?";
		List<Shopcart> list = DBHelper.find(sql, Shopcart.class, userid);

		for (int i = 0; i < list.size(); i++) {
			double l = list.get(i).getShprice() * list.get(i).getShnumber();
			list.get(i).setCountshprice(l);
		}

		return list;
	}

	public int number(Integer userid, Integer shid) {
		String sql = "select shnumber from shopcart where userid=? and shid=?";
		List<Map<String, Object>> list = DBHelper.findAll(sql, userid, shid);
		if (list.size() <= 0) {
			return 0;
		}
		String t = list.get(0).get("shnumber").toString();
		int t1 = Integer.parseInt(t);
		return t1;
	}

	@Override
	public int delete(Shopcart t) {

		return 0;
	}

	@Override
	public int deleteByUserid(Integer userid) {
		String sql = "delete from shopcart where userid=?";
		return DBHelper.doUpdate(sql, userid);
	}

	@Override
	public int updata(Shopcart t) {
		String sql = "update shopcart set shnumber=? where userid=? and shid=?";

		return DBHelper.doUpdate(sql, t.getShnumber(), t.getUserid(), t.getShid());
	}

	// public static void main(String[] args) {
	// ShopcartDao dao=new ShopcartDaoImpl();
	//
	// }
	@Override
	public List<Shopcart> select(Shopcart t, int page, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deletByShid(Integer userid, Integer shid) {
		String sql = "delete from shopcart where userid=? and shid=?";
		return DBHelper.doUpdate(sql, userid, shid);
	}

	@Override
	public Shopcart selectOneById(Integer userid, Integer shid) {
		String sql = "select * from shopcart where userid=? and shid=?";
		List<Shopcart> list = DBHelper.find(sql, Shopcart.class, userid, shid);
		double l = list.get(0).getShprice() * list.get(0).getShnumber();
		list.get(0).setCountshprice(l);

		return list.get(0);
	}

}
