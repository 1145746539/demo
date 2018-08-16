package com.yc.dingcan.bean;

import java.io.Serializable;

public class Shopcart implements Serializable{
	
	private Integer userid;
	
	private Integer shid;
	
	private String shname;
	
	private double shprice;
	
	private Integer shnumber;
	
	private double countshprice;
	
	

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public double getCountshprice() {
		return countshprice;
	}

	public void setCountshprice(double countshprice) {
		this.countshprice = countshprice;
	}

	public Integer getShid() {
		return shid;
	}

	public void setShid(Integer shid) {
		this.shid = shid;
	}

	public String getShname() {
		return shname;
	}

	public void setShname(String shname) {
		this.shname = shname;
	}

	public double getShprice() {
		return shprice;
	}

	public void setShprice(double shprice) {
		this.shprice = shprice;
	}

	public Integer getShnumber() {
		return shnumber;
	}

	public void setShnumber(Integer shnumber) {
		this.shnumber = shnumber;
	}
	
	
	

}
