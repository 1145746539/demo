package com.yc.dingcan.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ShowPageTag extends TagSupport{

	private static final long serialVersionUID = 1L;
	private long total;   
	private int rows;
	private String href;

	@Override
	public int doStartTag() throws JspException {
		if(total<=0 || rows<=0){
			return SKIP_BODY;
		}
		
		//总页数  做尾页
		long allPage;
		if(total%rows==0){
			allPage=total/rows;
		}else{
			allPage =total/rows + 1;
		}
		
		String pageIndex=pageContext.getRequest().getParameter("pageIndex");  //获取地址栏页码
		pageContext.setAttribute("allPage", allPage);
		
		if(pageIndex==null || "".equals(pageIndex) || Integer.parseInt(pageIndex)<=0){
			pageIndex="1";
		}else if(Integer.parseInt(pageIndex)>allPage){
			pageIndex=allPage+"";
		}
		
		int pageNum=Integer.parseInt(pageIndex);
		//可变长度字符串
		String wen= href.contains("?") ? "&" : "?";
		
		
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<a href='%s%spageIndex=%s&rows=%s'>%s</a>", href,wen,1,rows,"首页")+"&nbsp");
		builder.append(String.format("<a href='%s%spageIndex=%s&rows=%s'>%s</a>", href,wen,pageNum-1==0?1:pageNum-1,rows,"上一页")+"&nbsp");
		builder.append(String.format("<a href='%s%spageIndex=%s&rows=%s'>%s</a>", href,wen,pageNum+1<allPage?pageNum+1:allPage,rows,"下一页")+"&nbsp");
		builder.append(String.format("<a href='%s%spageIndex=%s&rows=%s'>%s</a>", href,wen,allPage,rows,"尾页"));
		
		//输出
		try {
			pageContext.getOut().print("共"+allPage+"页|  ");
			pageContext.getOut().print(builder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return EVAL_BODY_INCLUDE;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
}
