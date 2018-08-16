package com.yc.dingcan.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yc.dingcan.bean.Resuser;
import com.yc.dingcan.biz.ResuserBiz;
import com.yc.dingcan.biz.impl.ResuserBizImpl;
import com.yc.dingcan.util.BeanUtils;

/**
 * Servlet implementation class ForgetServlet
 */
@WebServlet("/forget.s")
public class ForgetServlet extends HttpServlet {
	private static Logger logger=Logger.getLogger(RegisterServlet.class);
	private static final long serialVersionUID = 1L;
	private ResuserBiz biz=new ResuserBizImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		Resuser resuser=BeanUtils.toBean(request, new Resuser());
		logger.debug(resuser.getUsername()+"++"+resuser.getEmail()+"++"+resuser.getPwd());
		List<Resuser> list=biz.findByName(resuser.getUsername());
		if(list.size()>0){
			if((list.get(0).getEmail()).equals(resuser.getEmail())){
				int b=biz.update(resuser);
				if(b>0){
					//修改成功快去登录
					PrintWriter out = response.getWriter();
					out.print("<script>alert('修改成功快去登录吧！');window.location.href='login.jsp'</script>");
				}
			}else{
				request.setAttribute("msg", "用户名或者邮箱错误");
				request.getRequestDispatcher("forget.jsp").forward(request, response);
				return;
			}
			
		}else{
			request.setAttribute("msg", "用户名或者邮箱错误");
			request.getRequestDispatcher("forget.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
