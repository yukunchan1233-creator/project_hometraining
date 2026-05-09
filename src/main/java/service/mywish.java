package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BlogDao;
import model.Command;

public class mywish implements Command {

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
		
		int blogbno = Integer.parseInt(request.getParameter("blogbno"));
		
		int result = new BlogDao().mywishInsert(userid, blogbno);
		
		String msg = "";
		
		try {
			if(result == 1) {
				msg = "찜 성공";
			} else if(result == -1) {
				msg = "찜 이미 했어요";
			} else {
				msg = "찜 실패";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "찜 실패";
		}
		
		response.getWriter().write(msg);
	}

}




