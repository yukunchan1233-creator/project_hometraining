package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Command;
import util.RedirectUtil;

public class MemberLogout implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//세션삭제
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession(false);
		//기존 세션이 있으면 가져오고(userid) 없으면 null반환
		//false를 주면 새로운 세션을 생성하지 않는다.
		if(session != null) {
			session.invalidate(); //세션 무효화
		}
		// hometraining context path를 고려한 리다이렉트
		// jslhrd가 ROOT(/)로 설정되어 있어서 hometraining으로 명확히 리다이렉트
		RedirectUtil.redirect(request, response, "/main.do");
	}

}




