package com.yc.dingcan.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class LoginFilter
 */
@WebServlet("/LoginFilter")
public class LoginFilter implements Filter {
	private static Logger logger = Logger.getLogger(LoginFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();
		// 获取访问的资源名
		String servletPath = ((HttpServletRequest) request).getServletPath();
		// System.out.println(servletPath.endsWith("login.jsp"));
		// System.out.println(servletPath.endsWith("index.jsp"));
		// if(servletPath.endsWith("login.jsp")==false &&
		// servletPath.endsWith("index.jsp")==false ){
		// if(session.getAttribute("resuser")==null){
		// ((HttpServletResponse)response).sendRedirect("login.jsp");
		// return;
		// }
		// }
		// // pass the request along the filter chain
		// chain.doFilter(request, response);

		// 简单判断缓存中是否有用户
//		System.out.println(servletPath.endsWith("VerifyCode.Servlet"));
//		System.out.println(servletPath);
		if (session.getAttribute("resuser") == null && session.getAttribute("resadmin")==null) {// 没有用户
//			System.out.println("标志");
			// 判断用户是否是选择跳到登录界面
			if (servletPath.endsWith("login.jsp") 
					||servletPath.endsWith("index.jsp")
						||servletPath.endsWith("login.s")
						||servletPath.endsWith("404.jsp")
						||servletPath.endsWith("404.jpg")
						||servletPath.endsWith("500.jsp")
						||servletPath.endsWith("500.jpg")
						||servletPath.endsWith("register.jsp")
						||servletPath.endsWith("reg.s")
						||servletPath.endsWith("forget.jsp")
						||servletPath.endsWith("forget.s")
							||servletPath.endsWith("VerifyCode.Servlet")) {
				chain.doFilter(request, response);
			} else {
				((HttpServletResponse) response).sendRedirect("login.jsp");
			}
		} else {// 有用户
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
