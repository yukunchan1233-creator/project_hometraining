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

/**
 * [메인 페이지 포트폴리오 조회 서비스 - BlogSelectIndex]
 * 설명: 메인 페이지에 표시할 최신 포트폴리오 목록을 조회하는 서비스입니다.
 * 
 * 흐름:
 * 1. MainController에서 호출됨
 * 2. BlogDao를 통해 DB에서 포트폴리오 목록 조회
 * 3. 조회된 목록을 request 속성에 저장하여 JSP로 전달
 */
public class BlogSelectIndex implements Command {

	/**
	 * 메인 페이지용 포트폴리오 목록 조회 메서드
	 * 설명: DB에서 모든 포트폴리오를 조회하여 request에 저장합니다.
	 */
	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		// 1단계: BlogDao 객체 생성
		BlogDao dao = new BlogDao();
		
		// 2단계: getAll() 메서드를 호출하여 DB에서 포트폴리오 목록 조회
		// getAll()은 "select * from htm_blog order by bno desc" SQL을 실행합니다.
		List<BlogDto> list = dao.getAll();
		
		// 3단계: 조회된 목록을 request 속성에 저장
		// 공식: 출력하는 모든 값은 request 속성에 담아서 forward한다
		// request 속성에 담겨진 객체는 다른 페이지로 이동하면 사용할 수 없다.
		// session 속성과 request 속성 차이점:
		//   - request: 한 번의 요청-응답 사이클 동안만 유지 (forward 시에만 전달됨)
		//   - session: 사용자가 로그아웃하거나 세션이 만료될 때까지 유지
		request.setAttribute("list", list);
	}

}




