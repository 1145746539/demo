package com.yc.dingcan.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yc.dingcan.bean.Resuser;
import com.yc.dingcan.bean.Shopcart;
import com.yc.dingcan.biz.ResuserBiz;
import com.yc.dingcan.biz.impl.ResuserBizImpl;
import com.yc.dingcan.util.BeanUtils;
import com.yc.dingcan.util.Email;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/reg.s")
public class RegisterServlet extends HttpServlet {
	private static Logger logger=Logger.getLogger(RegisterServlet.class);
	private static final long serialVersionUID = 1L;
	private ResuserBiz biz=new ResuserBizImpl();
//	private String sysver;
//	private boolean yu=false;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String bf=request.getParameter("bf");
		String username=request.getParameter("username");
		String name=username.trim();
		String pwd=request.getParameter("pwd");
		String againpwd=request.getParameter("againpwd");
		String email=request.getParameter("email");
		
		if("checkname".equals(bf)){
			
			if(name.length()>0){
				List<Resuser> list=biz.findByName(name);
				if(list.size()>0){
					response.getWriter().print("该用户名已注册");
				}else{
//					yu=true;
				}	
			}else{
				response.getWriter().print("用户名不能为空");
			}
		}
		
//		if("veremail".equals(bf)){
			
//			//输入了邮箱就发送验证码
//			if(email.length()>0){
//				logger.debug("测试");
//				Email e=new Email("18397716367@163.com","zs111111", "smtp.163.com", email);
//				e.email();
////				//验证码
//				sysver=e.ver;
//				logger.debug(e.ver);
//				response.getWriter().print("验证码已发送，如未接收到请确认邮箱是否正确");
//			}
//		}
		//按下提交就注册
		if("register".equals(bf)){
//			if(name.length()==0){
//				request.setAttribute("msg", "同户名不能为空");
//				request.getRequestDispatcher("register.jsp").forward(request, response);
//			}
//			if(!yu){
//				request.setAttribute("msg", "该用户名已注册");
//				request.getRequestDispatcher("register.jsp").forward(request, response);
//			}
			
			//判断密码
			if(pwd.equals(againpwd)){
				logger.debug(username+"=="+email+"=="+pwd);
				Resuser resuser=BeanUtils.toBean(request, new Resuser());
				logger.debug(resuser.getUsername()+"++"+resuser.getEmail()+"++"+resuser.getPwd());
				//存
				int b=biz.insert(resuser);
				//成功
				if(b>0){
					response.getWriter().print("注册成功快去登录把");
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
				
			}
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
