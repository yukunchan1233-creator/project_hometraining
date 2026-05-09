package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Command;
import model.MemberDao;

public class UseridCheck implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String userid = request.getParameter("userid");
		
		System.out.println("ajax로 요청된 아이디 :"+userid);
		
		MemberDao mdao = new MemberDao();
		
		int result = mdao.userIdCheck(userid);
		
		//클라이언트로 결과 전송
		response.getWriter().print(result);
	}

}




