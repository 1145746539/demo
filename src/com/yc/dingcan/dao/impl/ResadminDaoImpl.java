package com.yc.dingcan.dao.impl;

import java.util.List;

import com.yc.dingcan.bean.Resadmin;
import com.yc.dingcan.dao.ResadminDao;
import com.yc.dingcan.util.DBHelper;

public class ResadminDaoImpl implements ResadminDao {

	@Override
	public int insert(Resadmin t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Resadmin t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updata(Resadmin t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Resadmin> select(Resadmin t, int page, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resadmin selectById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count(Resadmin t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Resadmin> selectall() {
		String sql="select * from resadmin";
		return DBHelper.find(sql, Resadmin.class);
	}

}
