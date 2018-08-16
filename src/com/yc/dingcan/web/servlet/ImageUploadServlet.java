package com.yc.dingcan.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUploadException;
import com.yc.dingcan.util.UploadUtil;

@WebServlet("/uploadImg.s")
public class ImageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UploadUtil uu = new UploadUtil("/images", "jpg,gif,png,bmp", "", 20 * 1024 * 1024, 5 * 1024 * 1024);
		String ret = "";
		
			Map<String, String> params;
			try {
				params = uu.uploadFiles(getServletConfig(), request, response);
				ret = params.get("picPath1");
			} catch (SmartUploadException | SQLException e) {
				e.printStackTrace();
			}
		response.getWriter().print(ret);
	}

}
