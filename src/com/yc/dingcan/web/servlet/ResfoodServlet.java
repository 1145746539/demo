package com.yc.dingcan.web.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import com.google.gson.Gson;
import com.yc.dingcan.bean.Resfood;
import com.yc.dingcan.bean.Resorder;
import com.yc.dingcan.bean.Resorderitem;
import com.yc.dingcan.bean.Resuser;
import com.yc.dingcan.bean.Shopcart;
import com.yc.dingcan.biz.BizException;
import com.yc.dingcan.biz.ResfoodBiz;
import com.yc.dingcan.biz.ResorderBiz;
import com.yc.dingcan.biz.ResorderitemBiz;
import com.yc.dingcan.biz.ShopcartBiz;
import com.yc.dingcan.biz.impl.ResfoodBizImpl;
import com.yc.dingcan.biz.impl.ResorderBizImpl;
import com.yc.dingcan.biz.impl.ResorderitemBizImpl;
import com.yc.dingcan.biz.impl.ShopcartBizImpl;
import com.yc.dingcan.tag.ShowPageTag;
import com.yc.dingcan.util.BeanUtils;
import com.yc.dingcan.vo.Result;

@WebServlet("/food.s")
public class ResfoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ResfoodBiz biz = new ResfoodBizImpl();
	private ShopcartBiz b = new ShopcartBizImpl();
	private ResorderBiz orderbiz = new ResorderBizImpl();
	private ResorderitemBiz itembiz = new ResorderitemBizImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		// 操作参数
		String op = req.getParameter("op");
		String pp = req.getParameter("pp");
		String check = req.getParameter("check");

		// 将请求的参数放置到实体对象中去
		Resfood resfood = BeanUtils.toBean(req, new Resfood());

		if ("query".equals(op)) {
			if (req.getParameter("ajax") == null) {
				String pageIndex = req.getParameter("pageIndex"); // 第一次进来有空值
				if (pageIndex == null || "".equals(pageIndex) || Integer.parseInt(pageIndex) <= 0) {
					pageIndex = "1";
				}
				int page = Integer.parseInt(pageIndex); // 哪页
				int rows = 4; // 每页数
				// 暂时查询所有的记录，将结果设置到请求属性中去
				long total = biz.count(resfood);
				req.setAttribute("pageIndex", pageIndex);
				req.setAttribute("total", total);
				req.setAttribute("rows", rows);
				req.setAttribute("list", biz.find(resfood, page, rows));
				// 页面跳转
				req.getRequestDispatcher("show.jsp").forward(req, resp);
			} else {
				String page = req.getParameter("page");
				String rows = req.getParameter("rows");
				int intPage;
				int intRows;
				if (page == null) {
					intPage = 1;
				} else {
					try {
						intPage = BeanUtils.cast(page, Integer.class);
					} catch (ParseException e) {
						intPage = 1;
						e.printStackTrace();
					}
				}
				if (rows == null) {
					intRows = 5;
				} else {
					try {
						intRows = BeanUtils.cast(rows, Integer.class);
					} catch (ParseException e) {
						intRows = 5;
						e.printStackTrace();
					}
				}
				// 查询结果
				List<Resfood> list = biz.find(resfood, intPage, intRows);
				// 查询总行数
				long count = biz.count(resfood);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("rows", list);
				data.put("total", count);

				// 将查询结果转成 json 字符串
				Gson gson = new Gson();
				String s = gson.toJson(data);
				// 输出到页面
				resp.setContentType("text/html;charset=utf-8");
				resp.getWriter().print(s);
			}
		}

		if ("find".equals(op)) {
			// 查询菜品
			req.setAttribute("resfood", biz.findById(resfood.getFid()));
			// 页面跳转
			req.getRequestDispatcher("details.jsp").forward(req, resp);
		}
		
		if("add".equals(op)){
			// 新增菜品
			add(resfood, req, resp);
		}else if ("del".equals(op)) {
			// 新增菜品
			del(resfood, req, resp);
		} else if ("mod".equals(op)) {
			// 新增菜品
			mod(resfood, req, resp);
		}

		// 购物车 带着fid跳过来的
		if ("shopcart".equals(op)) {
			Shopcart shopcart = BeanUtils.toBean(req, new Shopcart());
			Resuser resuser = (Resuser) req.getSession().getAttribute("resuser");
			shopcart.setUserid(resuser.getUserid()); // 用户ID

			String jia = req.getParameter("jia");
			String sp = req.getParameter("sp");
			System.out.println("什么号：" + sp);
			// 购物车的加减
			if ("jian".equals(jia)) {
				int shid = Integer.parseInt(req.getParameter("shid"));
				System.out.println("菜品id：" + shid);
				// 菜品数量
				int number = b.findnumber(shopcart.getUserid(), shid);
				// 大于1还可减，
				Map<String, Object> data = new HashMap<String, Object>();
				if ("red".equals(sp)) {
					if(number>1){
						number = number - 1;
						shopcart.setShnumber(number);
						b.updateByShid(shopcart);
						// 查找这个用户的 这个菜品的金额
						Shopcart ct = b.findOneById(shopcart.getUserid(), shid);
						data.put("countprice", ct.getCountshprice());
					}else{
						//数据库中删除这个用户的这个菜品
						b.deletByShid(shopcart.getUserid(), shid);
						number=0;
					}
				}
				// 这是加号
				if ("add".equals(sp)) {
					number = number + 1;
					shopcart.setShnumber(number);
					b.updateByShid(shopcart);
					// 查找这个用户的 这个菜品的金额
					Shopcart ct = b.findOneById(shopcart.getUserid(), shid);
					data.put("countprice", ct.getCountshprice());
				}

				List<Shopcart> lr = b.findallById(resuser.getUserid());
				// 算合计金额，所有菜品
				double tot = 0;
				for (int i = 0; i < lr.size(); i++) {
					lr.get(i).getCountshprice();
					tot = tot + lr.get(i).getCountshprice();
				}
				data.put("tot", tot);
				data.put("number", number);
				// 将查询结果转成 json 字符串
				Gson gson = new Gson();
				String s = gson.toJson(data);
				resp.setContentType("text/html;charset=utf-8");
				resp.getWriter().print(s);
				return;

			}
			// 清除购物车
			if ("delete".equals(pp)) {
				b.deleteByUserid(shopcart.getUserid());
			}
			// 纯粹的看购物车
			else if ("show".equals(pp)) {
				// 查询并且跳转
				List<Shopcart> list1 = b.findallById(resuser.getUserid());
				// 算合计金额
				double total1 = 0;
				for (int i = 0; i < list1.size(); i++) {
					list1.get(i).getCountshprice();
					total1 = total1 + list1.get(i).getCountshprice();
				}
				// 存session
				req.getSession().setAttribute("tot", total1);
				req.getSession().setAttribute("ShopCart", b.findallById(resuser.getUserid()));

				req.setAttribute("total", total1);
				req.setAttribute("shopcart", b.findallById(resuser.getUserid()));
				req.getRequestDispatcher("shopCart.jsp").forward(req, resp);
				return;
			}

			else {
				// 获取菜编号
				int fid = Integer.parseInt(req.getParameter("fid"));
				resfood = biz.findById(fid);
				shopcart.setShid(fid); // 菜编号
				shopcart.setShname(resfood.getFname()); // 菜名
				shopcart.setShprice(resfood.getRealprice()); // 菜价
				// 菜品数量
				int number = b.findnumber(shopcart.getUserid(), shopcart.getShid());
				if (number > 0) {
					number = number + 1;
					shopcart.setShnumber(number); // 菜数.有这个菜我们改
					b.updateByShid(shopcart);
				} else {
					shopcart.setShnumber(1); // 菜数。没这个菜我们存
					// 存入shopcart数据库
					b.insert(shopcart);
				}

			}

			// 查询并且跳转
			List<Shopcart> list = b.findallById(resuser.getUserid());
			// 算合计金额
			double total = 0;
			for (int i = 0; i < list.size(); i++) {
				list.get(i).getCountshprice();
				total = total + list.get(i).getCountshprice();
			}
			// 存session
			req.getSession().setAttribute("tot", total);
			req.getSession().setAttribute("ShopCart", b.findallById(resuser.getUserid()));

			req.setAttribute("total", total);
			req.setAttribute("shopcart", b.findallById(resuser.getUserid()));
			req.getRequestDispatcher("shopCart.jsp").forward(req, resp);

		}

		// 判断购物车有没有东西，有，返回去。没有。跳转页面
		if ("out".equals(check)) {
			double r = (double) req.getSession().getAttribute("tot");
			int r1 = (int) r;
			System.out.println("这个数字是：" + r1);
			if (r1 <= 0) {
				PrintWriter out = resp.getWriter();
				out.print("<script>alert('购物车为空，快来下单吧！');window.location.href='food.s?op=query'</script>");
			} else {
				req.getRequestDispatcher("checkOut.jsp").forward(req, resp);
			}
		}

		// 生成订单
		if ("checkout".equals(op)) {
			Resorder resorder = BeanUtils.toBean(req, new Resorder());
			Resuser resuser = (Resuser) req.getSession().getAttribute("resuser");
			System.out.println("测试bean的：" + resorder.getPs() + "还有" + resorder.getTel() + "还有" + resorder.getAddress());
			resorder.setUserid(resuser.getUserid());
			// 存入订单表
			int roid = orderbiz.insert(resorder);

			if (roid > 0) {
				// 存入进去了
				// 1.取得订单的roid和购物车表里面的数据，存入resorderitem，最后删除这个用户的购物车
				List<Shopcart> shopcart = (List<Shopcart>) req.getSession().getAttribute("ShopCart");
				// 存入resorderitem，暂时不知道干嘛用
				Resorderitem resorderitem = BeanUtils.toBean(req, new Resorderitem());
				for (int i = 0; i < shopcart.size(); i++) {
					resorderitem.setRoid(roid);
					resorderitem.setFid(shopcart.get(i).getShid());
					resorderitem.setDealprice(shopcart.get(i).getShprice());
					resorderitem.setNum(shopcart.get(i).getShnumber());
					// 存
					itembiz.insert(resorderitem);
				}
				// 删
				b.deleteByUserid(resuser.getUserid());
			}
			// 转去订单页面
			List<Resorder> list = orderbiz.findByuserid(resuser.getUserid());
			req.setAttribute("resorder", list);
			req.getRequestDispatcher("resorder.jsp").forward(req, resp);
		}

		if ("order".equals(op)) {
			Resuser resuser = (Resuser) req.getSession().getAttribute("resuser");
			// 转去订单页面
			List<Resorder> list = orderbiz.findByuserid(resuser.getUserid());
			req.setAttribute("resorder", list);
			req.getRequestDispatcher("resorder.jsp").forward(req, resp);
		}
		else if("ajax".equals(op)){
			//订单确认送达
			ajaxorder(req,resp);
		}else if("orderpage".equals(op)){
			//后台传来的order分页事件事件
			System.out.println("标志");
			orderpage(req,resp);
		}

	}


	private void orderpage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		if(page==null){
			page="1";
		}
		if(rows==null){
			rows="1";
		}
		List<Resorder> list=orderbiz.select(null, Integer.parseInt(page), Integer.parseInt(rows));
		long total=orderbiz.total();
		//定义分页数据Map
		Map<String,Object> data=new HashMap<String,Object>();
		data.put("rows", list);
		data.put("total", total);
		Gson gson=new Gson();
		String s=gson.toJson(data);
		resp.getWriter().println(s);
		
	}


	private void ajaxorder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Resorder resorder = BeanUtils.toBean(req, new Resorder());
		if(req.getParameter("manageajax")==null || req.getParameter("manageajax")==""){
			String confirm = req.getParameter("confirm");
			resorder.setRoid(Integer.parseInt(confirm));
			System.out.println("这是啥" + resorder.getRoid());
			int t = orderbiz.update(resorder);
			// 状态修改成功
			if (t > 0) {
				resp.getWriter().print(true);
			} else {
				resp.getWriter().print(false);
			}
		}else{
			//后台的ajax
			String data;
			orderbiz.update(resorder);
			data=toJson(Result.getSuccess("成功送达!"));
			resp.getWriter().print(data);
		}
	}


	private void add(Resfood resfood, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String data;
		try {
			biz.create(resfood);
			data = toJson(Result.getSuccess("菜品新增成功！"));
		} catch (BizException e) {
			e.printStackTrace();
			data = toJson(Result.getFailure(e.getMessage()));
		}
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().print(data);	
	
	}

	private void mod(Resfood resfood, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String data;
		try {
			biz.modify(resfood);
			data = toJson(Result.getSuccess("菜品修改成功！"));
		} catch (BizException e) {
			e.printStackTrace();
			data = toJson(Result.getFailure(e.getMessage()));
		}
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().print(data);
		
	}

	private void del(Resfood resfood, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String data;
		try {
			biz.remove(resfood);
			data = toJson(Result.getSuccess("菜品删除成功！"));
		} catch (BizException e) {
			e.printStackTrace();
			data = toJson(Result.getFailure(e.getMessage()));
		}
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().print(data);
		
	}

	private String toJson(Object data) {
		return new Gson().toJson(data);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
