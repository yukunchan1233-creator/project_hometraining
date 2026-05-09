package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Command;
import model.MemberDao;
import model.ProductDao;

public class MyDeleteSelected implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String wishBnos = request.getParameter("wishBnos");
		String type = request.getParameter("type");  // "blog" 또는 "product"
		
		if (wishBnos == null || wishBnos.trim().isEmpty()) {
			response.getWriter().write("fail");
			return;
		}
		
		boolean result = false;
		
		if("product".equals(type)) {
			// 제품 찜 삭제
			result = new ProductDao().deleteProductWishSelected(wishBnos);
		} else {
			// 블로그 찜 삭제 (기존)
			result = new MemberDao().deleteWishSelected(wishBnos);
		}
		
		response.getWriter().write(result ? "success" : "fail");
	}

}
