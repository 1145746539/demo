package com.yc.dingcan.biz;

import java.util.List;

import com.yc.dingcan.bean.Resfood;

public interface ResfoodBiz {
	/**
	 * 分页查询  可按条件
	 * @param resfood
	 * @param page
	 * @param rows
	 * @return 张胜
	 */
	public List<Resfood> find(Resfood resfood, int page, int rows);
	
	/**
	 * 统计总数  可按条件
	 * @param resfood
	 * @return 张胜
	 */
	public long count(Resfood resfood);
	
	public Resfood findById(Integer fid);

	public long create(Resfood resfood) throws BizException;
	
	public long modify(Resfood resfood) throws BizException;
	
	public long remove(Resfood resfood) throws BizException;
	
	
	
}
