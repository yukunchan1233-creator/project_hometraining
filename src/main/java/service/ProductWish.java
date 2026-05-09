package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Command;
import model.ProductDao;

public class ProductWish implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain; charset=utf-8");
		
		// 세션에서 userid 가져오기
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("userid");
		
		if(userid == null) {
			response.getWriter().write("로그인 필요");
			return;
		}
		
		String pnoStr = request.getParameter("pno");
		if(pnoStr == null || pnoStr.trim().isEmpty()) {
			response.getWriter().write("제품 번호가 필요합니다");
			return;
		}
		
		int pno = 0;
		try {
			pno = Integer.parseInt(pnoStr);
		} catch (NumberFormatException e) {
			response.getWriter().write("잘못된 제품 번호입니다");
			return;
		}
		
		int result = new ProductDao().productWishInsert(userid, pno);
		
		String msg = "";
		
		if(result == 1) {
			msg = "찜 성공";
		} else if(result == -1) {
			msg = "찜 이미 했어요";
		} else {
			msg = "찜 실패: 서버 콘솔을 확인하세요.";
			System.err.println("ProductWish 서비스 - 제품 찜 실패 - userid: " + userid + ", pno: " + pno);
		}
		
		response.getWriter().write(msg);
	}

}
