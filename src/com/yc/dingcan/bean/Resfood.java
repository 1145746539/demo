package com.yc.dingcan.bean;
/**
 * 菜品类，对应菜品表
 * @author zs
 *
 */
public class Resfood {
	//商品号
	private Integer fid;
	//商品名
	private String fname;
	//normprice:原价
	private Double normprice; 
	//realprice:现价
	private Double realprice; 
	//description:简介 //detail详细的
	private String detail;
	//detail详细的
	private String fphoto;

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public Double getNormprice() {
		return normprice;
	}

	public void setNormprice(double normprice) {
		this.normprice = normprice;
	}

	public Double getRealprice() {
		return realprice;
	}

	public void setRealprice(double realprice) {
		this.realprice = realprice;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getFphoto() {
		return fphoto;
	}

	public void setFphoto(String fphoto) {
		this.fphoto = fphoto;
	}

	@Override
	public String toString() {
		return "Resfood [fid=" + fid + ", fname=" + fname + ", normprice=" + normprice + ", realprice=" + realprice
				+ ", detail=" + detail + ", fphoto=" + fphoto + "]\r\n";
	}
	
	

}
