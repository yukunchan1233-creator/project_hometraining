package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BlogDao;
import model.Command;

public class BlogDelete implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("utf-8");
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		new BlogDao().blogDelete(bno);
		
		// hometraining context path를 고려한 리다이렉트
		response.sendRedirect(request.getContextPath() + "/port/list.do");

	}

}




