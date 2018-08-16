package com.yc.dingcan.biz.impl;

import java.util.List;

import com.yc.dingcan.bean.Resfood;
import com.yc.dingcan.biz.BizException;
import com.yc.dingcan.biz.ResfoodBiz;
import com.yc.dingcan.dao.ResfoodDao;
import com.yc.dingcan.dao.impl.ResfoodDaoImpl;
import com.yc.dingcan.util.ValidUtils;

public class ResfoodBizImpl implements ResfoodBiz {
	private ResfoodDao dao = new ResfoodDaoImpl();
	
	public List<Resfood> find(Resfood resfood, int page, int rows) {
		return dao.select(resfood, page, rows);
	}

	public long count(Resfood resfood) {
		return dao.count(resfood);
	}

	
	public Resfood findById(Integer fid) {
		return dao.selectById(fid);
	}

	@Override
	public long create(Resfood resfood) throws BizException {
		ValidUtils.check(resfood.getFname(), "菜品名称不能为空！");
		ValidUtils.check(resfood.getNormprice(), "菜品价格不能为空！");
		ValidUtils.check(resfood.getFphoto(), "菜品图片不能为空！");
		ValidUtils.check(resfood.getDetail(), "菜品描述不能为空！");
		return dao.insert(resfood);
	}

	@Override
	public long modify(Resfood resfood) throws BizException {
		ValidUtils.check(resfood.getFid(), "菜品编号不能为空！");
		ValidUtils.check(resfood.getFname(), "菜品名称不能为空！");
		ValidUtils.check(resfood.getNormprice(), "菜品价格不能为空！");
		ValidUtils.check(resfood.getFphoto(), "菜品图片不能为空！");
		ValidUtils.check(resfood.getDetail(), "菜品描述不能为空！");
		return dao.updata(resfood);
	}

	@Override
	public long remove(Resfood resfood) throws BizException {
		ValidUtils.check(resfood.getFid(), "菜品编号不能为空！");
		if(dao.isUsed(resfood.getFid())) {
			throw new BizException("该菜品已经有人点过了，不能删除");
		}
		return dao.delete(resfood);
	}

}
