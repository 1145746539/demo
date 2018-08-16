package com.yc.dingcan.bean;

import java.sql.Date;

public class Resorderitemtemp {
	
	private Integer roitid;
	//商品号 
	private Integer fid;
	//数量
	private Integer num;
	
	private Date quittime;
	//用户ID
	private Integer userid;

	public Integer getRoitid() {
		return roitid;
	}

	public void setRoitid(Integer roitid) {
		this.roitid = roitid;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Date getQuittime() {
		return quittime;
	}

	public void setQuittime(Date quittime) {
		this.quittime = quittime;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	

}
