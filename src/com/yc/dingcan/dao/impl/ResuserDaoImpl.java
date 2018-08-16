package com.yc.dingcan.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.dingcan.bean.Resuser;
import com.yc.dingcan.dao.ResuserDao;
import com.yc.dingcan.util.DBHelper;

public class ResuserDaoImpl implements ResuserDao {

	@Override
	public int insert(Resuser t) {
		String sql="insert into resuser( username, pwd,email) values(?, ?,?)";
		
		return DBHelper.doUpdate(sql, t.getUsername(),t.getPwd(),t.getEmail());
	}

	@Override
	public int delete(Resuser t) {
		if(t.getUserid()!=null){
			String sql="delete from resuser where userid=?";
			return DBHelper.doUpdate(sql, t.getUserid());
		}
		return 0;
	}

	@Override
	public int updata(Resuser t) {
		String sql="update resuser set pwd=? where username=? and email=?";
		return DBHelper.doUpdate(sql, t.getPwd(),t.getUsername(),t.getEmail());
	}

	@Override
	public List<Resuser> select(Resuser t, int page, int rows) {
		String sql="select * from resuser order by userid limit ?,?";
		return DBHelper.find(sql, Resuser.class, (page-1)*rows,rows);
	}

	@Override
	public Resuser selectById(Integer id) {
		return null;
	}

	@Override
	public long count(Resuser t) {
		String sql="select count(*) cnt from resuser";
		List<Map<String,Object>> list=DBHelper.findAll(sql);
		Object value=list.get(0).get("cnt");
		return Long.parseLong(""+value);	//将结果转为long类型
	}

	//查找全部用户
	public List<Resuser> selectall() {
		String sql="select * from resuser ";
		return DBHelper.find(sql, Resuser.class);
	}

	//checkName
	public List<Resuser> selectByName(String name) {
		String sql="select * from resuser where username=?";
		return DBHelper.find(sql, Resuser.class, name);
	}

	@Override
	public int updatebymanage(Resuser t) {
		String sql="update resuser set";
		ArrayList<Object> params=new ArrayList<Object>();
		if(t.getUsername()!=null){
			sql+=" username=?,";
			params.add(t.getUsername());
		}
		if(t.getEmail()!=null){
			sql+=" email=?,";
			params.add(t.getEmail());
		}
		if(t.getPwd()!=null){
			sql+=" pwd=?,";
			params.add(t.getPwd());
		}
		//去掉最后一个逗号
		sql=sql.substring(0,sql.length()-1);
		sql+=" where userid=?";
		params.add(t.getUserid());
		return DBHelper.doUpdate(sql, params);
	}

}
