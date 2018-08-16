package com.yc.dingcan.dao.impl;

import java.util.List;

import com.yc.dingcan.bean.Resorderitem;
import com.yc.dingcan.dao.ResorderitemDao;
import com.yc.dingcan.util.DBHelper;

public class ResorderitemDaoImpl implements ResorderitemDao {

	@Override
	public int insert(Resorderitem t) {
		String sql="insert into resorderitem values(null,?,?,?,?)";
		int w=DBHelper.doUpdate(sql, t.getRoid(),t.getFid(),t.getDealprice(),t.getNum());
		return w;
	}

	@Override
	public int delete(Resorderitem t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updata(Resorderitem t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Resorderitem> select(Resorderitem t, int page, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resorderitem selectById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count(Resorderitem t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
