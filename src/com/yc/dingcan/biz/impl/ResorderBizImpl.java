package com.yc.dingcan.biz.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.yc.dingcan.bean.Resfood;
import com.yc.dingcan.bean.Resorder;
import com.yc.dingcan.biz.ResorderBiz;
import com.yc.dingcan.dao.ResorderDao;
import com.yc.dingcan.dao.impl.ResorderDaoImpl;

public class ResorderBizImpl implements ResorderBiz {
	ResorderDao dao=new ResorderDaoImpl();

	@Override
	public int insert(Resorder resorder) {
		return dao.insert(resorder);
	}

	@Override
	public List<Resorder> findByuserid(Integer userid) {
		return dao.selectall(userid);
	}

	@Override
	public int update(Resorder resorder) {
		return dao.updata(resorder);
	}

	@Override
	public List<Resorder> select(Resorder resorder, int page, int rows) {
		
		return dao.select(resorder, page, rows);
	}

	@Override
	public long total() {
		
		return dao.count(null);
	}
}
