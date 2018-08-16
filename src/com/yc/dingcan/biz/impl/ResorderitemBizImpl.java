package com.yc.dingcan.biz.impl;

import com.yc.dingcan.bean.Resorderitem;
import com.yc.dingcan.biz.ResorderitemBiz;
import com.yc.dingcan.dao.ResorderitemDao;
import com.yc.dingcan.dao.impl.ResorderitemDaoImpl;

public class ResorderitemBizImpl implements ResorderitemBiz {
	private ResorderitemDao dao=new ResorderitemDaoImpl();

	public int insert(Resorderitem resorderitem) {
		
		return dao.insert(resorderitem);
	}

}
