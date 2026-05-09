package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BlogDao;
import model.BlogDto;
import model.Command;

public class BlogSelectAll implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		BlogDao dao = new BlogDao();        // 1단계: BlogDao 객체 생성
		String keyword = request.getParameter("keyword");
		if(keyword == null || keyword.trim().isEmpty()) {		//실기에 잘나오니까 기억해두기.
			keyword = "";
		}
		int page =request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")): 1;
		int pageSize = 5;
		
		//List<BlogDto> list = dao.getAll();   // 2단계: getAll() 메서드 호출
		List<BlogDto> list = dao.getSearchAndPaging(keyword, page, pageSize);   
		
		

		
		//		HttpSession session = request.getSession();
//		session.setAttribute("list", list);
		//공식: 출력하는 모든 값은 request 속성에 담아서 forward한다
		//request 속성에 담겨진 객체는 다른 페이지로 이동하면 사용할 수 없다.
		//session 속성과 request속성 차이점을 생각해 보자
		
		int totalResults = new BlogDao().getSearchCount(keyword); //조건에 만족하는 총 레코드 개수
		int totalPages = (int)Math.ceil((double)totalResults / pageSize); // 총페이지수
		int pageBlock = 5;  // 1블럭에 출력 페이지수
		int startPage = ((page-1)/pageBlock)*pageBlock + 1;
		int endPage = Math.min(startPage+pageBlock-1, totalPages); //끝 페이지 번호
		
		//request 속성에 담아서 forward 한다.
		request.setAttribute("list", list);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("totalPgaes", totalPages);
		request.setAttribute("currentPage", page);
		request.setAttribute("startPage", startPage);
		request.setAttribute("totalCount", totalResults);

	}

}




