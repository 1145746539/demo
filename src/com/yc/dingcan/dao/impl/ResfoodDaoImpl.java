package com.yc.dingcan.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.dingcan.bean.Resfood;
import com.yc.dingcan.dao.ResfoodDao;
import com.yc.dingcan.util.DBHelper;

public class ResfoodDaoImpl implements ResfoodDao {

	@Override
	public int insert(Resfood t) {
		String sql = "insert into resfood (fname,normprice,realprice,detail,fphoto)" + " values (?,?,?,?,?)";
		// 只要保存文件名就可以了，所以去掉文件名前面的路径
		if(t.getFphoto()==null) {
			t.setFphoto("noimg.gif");
		} else {
			t.setFphoto(t.getFphoto().substring(t.getFphoto().lastIndexOf("/") + 1));
		}
		return DBHelper.doInsert(sql, t.getFname(), t.getNormprice(), t.getRealprice(), t.getDetail(), t.getFphoto());
	}

	@Override
	public int delete(Resfood t) {
		String sql = "delete from resfood where fid=?";
		return DBHelper.doUpdate(sql, t.getFid());
	}

	@Override
	public int updata(Resfood t) {
		String sql = "update resfood set ";
		ArrayList<Object> params = new ArrayList<Object>();
		if (t.getFname() != null) {
			sql += " fname=?,";
			params.add(t.getFname());
		}
		if (t.getRealprice() !=null) {
			sql += " realprice=?,";
			params.add(t.getRealprice());
		}
		if (t.getDetail() != null) {
			sql += " detail=?,";
			params.add(t.getDetail());
		}
		if (t.getFphoto() != null) {
			// 只要保存文件名就可以了，所以去掉文件名前面的路径
			t.setFphoto(t.getFphoto().substring(t.getFphoto().lastIndexOf("/") + 1));
			sql += " fphoto=?,";
			params.add(t.getFphoto());
		}
		// 去掉最后一个逗号（,）
		sql = sql.substring(0, sql.length() - 1);
		sql += " where fid=?";
		params.add(t.getFid());
		return DBHelper.doUpdate(sql, params);
	}

	@Override
	public List<Resfood> select(Resfood t, int page, int rows) {
		String sql="select * from resfood where 1=1";
		//创建参数集合
		List<Object> params = new ArrayList<Object>();
		
		if(t!=null){
			//判断菜品名
			if(t.getFname()!=null&&t.getFname().trim().length()>0){
				params.add(t.getFname());
				sql +=" and fname=?";
			}
			if(t.getDetail()!=null&&t.getDetail().trim().length()>0){
				params.add(t.getDetail());
				sql +=" and detail=?";
			}
		}
		//计算分页参数，偏移量
		int offset=(page-1)*rows;
		//设置分页参数
		sql +=" limit ?,?";
		params.add(offset);
		params.add(rows);
		return DBHelper.find(sql, Resfood.class, params);
	}

	
	public Resfood selectById(Integer id) {
		String sql="select * from resfood where fid=?";
		List<Resfood> list=DBHelper.find(sql, Resfood.class, id);
		if(list.size()>0){
			//有值则返回值。获取第一条值
			return list.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public long count(Resfood t) {
		String sql="select count(*) cnt from resfood where 1=1";
		//创建参数集合
		List<Object> params = new ArrayList<Object>();
		
		if(t!=null){
			//判断菜品名
			if(t.getFname()!=null&&t.getFname().trim().length()>0){
				params.add(t.getFname());
				sql +=" and fname=?";
			}
			if(t.getDetail()!=null&&t.getDetail().trim().length()>0){
				params.add(t.getDetail());
				sql +=" and detail=?";
			}
		}
		List<Map<String, Object>> list=DBHelper.findAll(sql, params);
		Object o=list.get(0).get("cnt");
		long value=Long.parseLong(""+o);
		return value;
	}
	
	public boolean isUsed(Integer fid) {
		String sql = "select count(*) cnt from resorderitem where fid=?";
		long count = (long) DBHelper.findAll(sql, fid).get(0).get("cnt");
		return count > 0;
	}

}
