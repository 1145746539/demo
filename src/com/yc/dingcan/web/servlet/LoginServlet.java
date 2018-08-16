package com.yc.dingcan.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.yc.dingcan.bean.Resadmin;
import com.yc.dingcan.bean.Resuser;
import com.yc.dingcan.biz.BizException;
import com.yc.dingcan.biz.ResadminBiz;
import com.yc.dingcan.biz.ResuserBiz;
import com.yc.dingcan.biz.impl.ResadminBizImpl;
import com.yc.dingcan.biz.impl.ResuserBizImpl;
import com.yc.dingcan.util.BeanUtils;
import com.yc.dingcan.vo.Result;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.s")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ResuserBiz biz = new ResuserBizImpl();
	private ResadminBiz adminbiz = new ResadminBizImpl();
	private static Logger logger = Logger.getLogger(LoginServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String dl = request.getParameter("dl");
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		
		logger.debug("用户名：" + name);
		logger.debug("密码：" + pwd);

		System.out.println("dl"+dl);
		// 原本的login
		if ("login".equals(dl)) {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");

			String uname = request.getParameter("loginName");
			String upwd = request.getParameter("loginPass");

			logger.debug("用户名：" + uname);
			logger.debug("密码：" + upwd);
			// 获取会话
			HttpSession session = request.getSession();
			// 获取生成的验证码
			String vscode = (String) session.getAttribute("vscode");
			logger.debug("原本验证码" + vscode);

			if (vscode != null) {
				// 取出用户输入的验证码
				String inputVscode = request.getParameter("vcode");
				logger.debug(inputVscode);
				if (vscode.equalsIgnoreCase(inputVscode)) {
					/**
					 * 调用用户登录服务方法
					 * @author lenovo88
					 */
					List<Resuser> list = biz.find();
					for (Resuser m : list) {
						System.out.println(m.getUsername() + "===" + m.getPwd());
						if (m.getUsername().equals(uname) && m.getPwd().equals(upwd)) {
							request.getSession().setAttribute("resuser", m);
							request.getRequestDispatcher("food.s?op=query").forward(request, response);
							return;
						}
					}
					request.setAttribute("msg", "用户名或者密码错误");
					request.getRequestDispatcher("login.jsp").forward(request, response);
					return;
				} else {
					request.setAttribute("msg", "验证码错误");
					request.getRequestDispatcher("login.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("msg", "请刷新验证码");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}

		}

		// 普通用户
		if ("user".equals(dl)) {
			List<Resuser> list = biz.find();
			for (Resuser m : list) {
				if (m.getUsername().equals(name) && m.getPwd().equals(pwd)) {
					request.getSession().setAttribute("resuser", m);
					request.getRequestDispatcher("food.s?op=query").forward(request, response);
					return;
				}
			}
			request.setAttribute("msg", "用户名或者密码错误");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		// 系统用户
		if ("admin".equals(dl)) {
			List<Resadmin> list = adminbiz.findall();
			for (Resadmin m : list) {
				System.out.println(m.getRaname()+"="+m.getRapwd());
				if (m.getRaname().equals(name) && m.getRapwd().equals(pwd)) {
					System.out.println("biaozhi");
					request.getSession().setAttribute("resadmin", m);
					response.sendRedirect("/resfood44/back/manage.jsp");
//					request.getRequestDispatcher("/back/manage.jsp").forward(request,response);
					return;
				}
			}
			System.out.println("第二次");
			request.setAttribute("msg", "用户名或者密码错误");
			request.getRequestDispatcher("/back/login.jsp").forward(request, response);	
		}
		else if("ajax".equals(dl)){
			//查询所有用户                                               
			try {
				ajax(request, response);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("registerAjax".equals(dl)){
			//注册用户
			registerAjax(request, response);
		}else if("modifyAjax".equals(dl)){
			//修改用户
			modifyAjax(request, response);
		}else if("del".equals(dl)){
			//删除用户
			del(request, response);
		}

	}

	private void del(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resuser resuser=BeanUtils.toBean(request, new Resuser());
		String data;
		biz.remove(resuser);
		System.out.println(resuser.getUserid()+"====");
		data=toJson(Result.getSuccess("删除成功!"));
		response.getWriter().print(data);
		
	}

	private void modifyAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resuser resuser=BeanUtils.toBean(request, new Resuser());
		String data = null;
		biz.updatebymanage(resuser);
		data=toJson(Result.getSuccess("修改成功!"));
		response.getWriter().print(data);
		
	}

	private void registerAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resuser resuser=BeanUtils.toBean(request, new Resuser());
		String data = null;
		if(biz.findByName(resuser.getUsername())==null){
			if(biz.insert(resuser)>0){
				data=toJson(Result.getSuccess("注册成功!"));
			}
		}else{
			data=toJson(Result.getFailure("用户名已经存在,请重新选一个"));
		}
		response.getWriter().print(data);
		
	}

	private String toJson(Object data){
		return new Gson().toJson(data);
	}

	private void ajax(HttpServletRequest request, HttpServletResponse response) throws java.text.ParseException {
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		int intPage;
		int intRows;
		if(page==null){
			intPage=1;
		}else{
			intPage=BeanUtils.cast(page, Integer.class);
		}
		if(rows==null){
			intRows=1;
		}else{
			intRows=BeanUtils.cast(rows, Integer.class);
		}
		List<Resuser> list=biz.select(null, intPage,intRows);
		long count=biz.total(new Resuser());
		//定义分页数据Map
		Map<String,Object> data=new HashMap<String,Object>();
		data.put("rows", list);
		data.put("total", count);

		Gson gson=new Gson();
		String s=gson.toJson(data);
		try {
			response.getWriter().println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
