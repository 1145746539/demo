package com.yc.dingcan.biz.impl;

import java.util.List;

import com.yc.dingcan.bean.Resuser;
import com.yc.dingcan.biz.ResuserBiz;
import com.yc.dingcan.dao.ResuserDao;
import com.yc.dingcan.dao.impl.ResuserDaoImpl;

public class ResuserBizImpl implements ResuserBiz {
	ResuserDao dao=new ResuserDaoImpl();

	@Override
	public List<Resuser> find() {
		return dao.selectall();
	}

	@Override
	public List<Resuser> findByName(String name) {
		return dao.selectByName(name);
	}

	@Override
	public int insert(Resuser resuser) {
		return dao.insert(resuser);
	}

	@Override
	public int update(Resuser resuser) {
		
		return dao.updata(resuser);
	}

	@Override
	public List<Resuser> select(Resuser resuser, int page, int rows) {
		return dao.select(resuser, page, rows);
	}

	@Override
	public long total(Resuser resuser) {
		
		return dao.count(resuser);
	}

	@Override
	public int updatebymanage(Resuser resuser) {
		
		return dao.updatebymanage(resuser);
	}

	@Override
	public int remove(Resuser resuser) {
		return dao.delete(resuser);
	}
	

}

