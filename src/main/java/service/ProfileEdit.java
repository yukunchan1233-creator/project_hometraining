package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Command;
import model.MemberDao;
import model.MemberDto;

/**
 * 개인정보 수정 페이지로 이동하는 서비스
 * 현재 로그인한 사용자의 정보를 조회해서 수정 페이지에 전달
 */
public class ProfileEdit implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("userid");
		
		if(userid == null) {
			// 로그인 안 되어 있으면 로그인 페이지로
			response.sendRedirect(request.getContextPath() + "/mem/login.do");
			return;
		}
		
		// DB에서 회원 정보 조회
		MemberDao dao = new MemberDao();
		MemberDto member = dao.searchUserId(userid);
		
		if(member != null) {
			request.setAttribute("member", member);
		}
	}

}
