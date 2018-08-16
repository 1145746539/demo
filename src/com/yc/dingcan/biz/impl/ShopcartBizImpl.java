package com.yc.dingcan.biz.impl;


import java.util.List;

import com.yc.dingcan.bean.Shopcart;
import com.yc.dingcan.biz.ShopcartBiz;
import com.yc.dingcan.dao.ShopcartDao;
import com.yc.dingcan.dao.impl.ShopcartDaoImpl;

public class ShopcartBizImpl implements ShopcartBiz {
	private ShopcartDao dao=new ShopcartDaoImpl();

	public List<Shopcart> findallById(Integer userid) {
		return dao.selectallById(userid);
	}

	@Override
	public int findnumber(Integer userid,Integer shid) {
		return dao.number(userid,shid);
	}

	@Override
	public int insert(Shopcart shopcart) {
		return dao.insert(shopcart);
	}

	@Override
	public int updateByShid(Shopcart shopcart) {
		return dao.updata(shopcart);
	}

	@Override
	public int deleteByUserid(Integer userid) {
		return dao.deleteByUserid(userid);
	}

	@Override
	public int deletByShid(Integer userid,Integer shid) {
		return dao.deletByShid(userid,shid);
	}

	@Override
	public Shopcart findOneById(Integer userid, Integer shid) {
		
		return dao.selectOneById(userid, shid);
	}


}
