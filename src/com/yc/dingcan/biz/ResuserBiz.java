package com.yc.dingcan.biz;

import java.util.List;

import com.yc.dingcan.bean.Resuser;

public interface ResuserBiz {
	
	/**
	 * 查找全部用户
	 * @return
	 * @author 张胜
	 */
	public List<Resuser> find();
	
	/**
	 * 通过名字查找数据库
	 * @param name
	 * @return List<Resuser>
	 * @author 张胜
	 */
	public List<Resuser> findByName(String name);
	
	/**
	 * 存注册用户
	 * @param resuser
	 * @return
	 * @author 张胜
	 */
	public int insert(Resuser resuser);
	
	/**
	 * 通过验证用户名和邮箱修改密码
	 * @param resuser
	 * @return
	 */
	public int update(Resuser resuser);
	
	/**
	 * 后台查询用户
	 * @param resuser
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Resuser> select(Resuser resuser, int page, int rows);
	
	public long total(Resuser resuser);
	
	public int updatebymanage(Resuser resuser);
	
	public int remove(Resuser resuser);

}

