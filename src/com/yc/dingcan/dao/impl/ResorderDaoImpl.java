package com.yc.dingcan.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yc.dingcan.bean.Resfood;
import com.yc.dingcan.bean.Resorder;
import com.yc.dingcan.dao.ResorderDao;
import com.yc.dingcan.util.DBHelper;

public class ResorderDaoImpl implements ResorderDao {
	private static Logger logger=Logger.getLogger(ResorderDaoImpl.class);

	@Override
	public int insert(Resorder t) {
		String sql="insert into resorder(userid,address,tel,ordertime,deliverytime,ps,status) "
				+ "values(?,?,?,now(),date_add(now(),interval 1 hour),?,0)";
//		String sql="insert into resorder "
//				+ "values(null,?,?,?,now(),date_add(now(),interval 1 hour),?,0)";
		int d=DBHelper.doInsert(sql, t.getUserid(),t.getAddress(),t.getTel(),t.getPs());
		return d;
	}

	public static void main(String[] args) {
		String sql="insert into resorder(userid,address,tel,ordertime,deliverytime,ps,status) "
				+ "values(1,'衡阳','1839771',now(),date_add(now(),interval 1 hour),'快点',0)";
		
		int d=DBHelper.doInsert(sql);
		logger.debug(d);
	}
	@Override
	public int delete(Resorder t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updata(Resorder t) {
		String sql="update resorder set status=1 where roid=?";
		return DBHelper.doUpdate(sql, t.getRoid());
	}

	@Override
	public List<Resorder> select(Resorder t, int page, int rows) {
		String sql="select * from resorder";
		//创建参数集合
		List<Object> params = new ArrayList<Object>();
		//计算分页参数，偏移量
		int offset=(page-1)*rows;
		sql +=" limit ?,?";
		params.add(offset);
		params.add(rows);
		return DBHelper.find(sql, Resorder.class, params);
	}

	@Override
	public Resorder selectById(Integer id) {
		
		return null;
	}

	@Override
	public long count(Resorder t) {
		String sql="select count(*) total from resorder";
		List<Map<String, Object>> list=DBHelper.findAll(sql);
		Object o=list.get(0).get("total");
		long value=Long.parseLong(""+o);
		return value;
	}

	@Override
	public List<Resorder> selectall(Integer userid) {
		String sql="select * from resorder where userid=?";
		return DBHelper.find(sql, Resorder.class, userid);
	}

	

}
