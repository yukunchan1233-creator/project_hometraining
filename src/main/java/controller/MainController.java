package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
	
import service.BlogSelectIndex;
import service.ReviewCarousel;

/**
 * [메인 컨트롤러 - MainController]
 * 설명: 메인 페이지("/main.do") 요청을 처리하는 컨트롤러입니다.
 * 
 * 흐름:
 * 1. 사용자가 "/" 또는 "/main.do" 접속
 * 2. MainController.doGet() 실행
 * 3. BlogSelectIndex 서비스 호출하여 최신 포트폴리오 목록 조회
 * 4. 조회된 데이터를 request 속성에 저장
 * 5. index.jsp로 forward하여 화면 출력
 */
@WebServlet("/main.do")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MainController() {
        super();   // 부모클래스(HttpServlet)의 생성자를 호출
        			/* 상속 관계에서 자식 클래스는 부모 클래스의 모든 기능을 사용할 수 있습니다. 
        			 * 하지만 부모 클래스가 제대로 초기화되지 않으면 자식 클래스도 제대로 작동할 수 없습니다. 
        			 * 따라서 생성자에서 부모 클래스의 생성자를 먼저 호출하여 부모 클래스의 초기화를 보장해야 합니다. */
    }

	/**
	 * GET 요청 처리 메서드
	 * 설명: 메인 페이지 요청 시 실행됩니다.
	 * 
	 * 처리 순서:
	 * 1. BlogSelectIndex 서비스를 호출하여 포트폴리오 목록을 조회하고 request에 저장
	 * 2. index.jsp로 forward하여 화면을 출력
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1단계: BlogSelectIndex 서비스 호출
		// 이 서비스는 DB에서 포트폴리오 목록을 조회하여 request.setAttribute("list", list)로 저장합니다.
		new BlogSelectIndex().doCommand(request, response);
		
		// 2단계: ReviewCarousel 서비스 호출 (후기 캐러셀용)
		new ReviewCarousel().doCommand(request, response);
		
		// 디버깅용: request에 저장된 list 확인
		System.out.println("컨트롤러 직후: " + request.getAttribute("list"));
		
		// 3단계: index.jsp로 forward
		// RequestDispatcher: 서버 내부에서 다른 페이지로 넘기는 도구
		// getRequestDispatcher("index.jsp"): "다음 목적지는 index.jsp야" 라는 티켓을 끊는 느낌
		// 브라우저가 index.jsp를 새로 요청하는 게 아니라, 서버 내부에서 index.jsp를 호출하는 것입니다.
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * POST 요청 처리 메서드
	 * 설명: POST 요청도 GET과 동일하게 처리합니다.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}




