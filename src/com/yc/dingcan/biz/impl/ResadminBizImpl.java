package com.yc.dingcan.biz.impl;

import java.util.List;

import com.yc.dingcan.bean.Resadmin;
import com.yc.dingcan.biz.ResadminBiz;
import com.yc.dingcan.dao.ResadminDao;
import com.yc.dingcan.dao.impl.ResadminDaoImpl;

public class ResadminBizImpl implements ResadminBiz {
	ResadminDao dao=new ResadminDaoImpl();
	
	public List<Resadmin> findall() {
		
		return dao.selectall();
	}

}
