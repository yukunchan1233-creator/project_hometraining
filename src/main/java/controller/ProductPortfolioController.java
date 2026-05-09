package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ProductPortfolio;
import service.ProductWish;

/**
 * [포트폴리오 컨트롤러 - ProductPortfolioController]
 * 설명: 포트폴리오 페이지("/portfolio.do") 요청을 처리하는 컨트롤러입니다.
 * 
 * 흐름:
 * 1. 사용자가 "/portfolio.do?sub=런닝머신&sort=popular" 접속
 * 2. ProductPortfolioController.doGet() 실행
 * 3. ProductPortfolio 서비스 호출하여 admin 계정의 제품 목록 조회
 * 4. 조회된 데이터를 request 속성에 저장
 * 5. portfolio.jsp로 forward하여 화면 출력
 */
@WebServlet({"/portfolio.do", "/port/productWish.do"})
public class ProductPortfolioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductPortfolioController() {
        super();
    }

	/**
	 * GET 요청 처리 메서드
	 * 설명: 포트폴리오 페이지 요청 시 실행됩니다.
	 * 
	 * 처리 순서:
	 * 1. ProductPortfolio 서비스를 호출하여 제품 목록을 조회하고 request에 저장
	 * 2. portfolio.jsp로 forward하여 화면을 출력
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		
		if("/port/productWish.do".equals(path)) {
			// 제품 찜 추가 요청
			new ProductWish().doCommand(request, response);
			return;
		}
		
		// 1단계: ProductPortfolio 서비스 호출
		// 이 서비스는 DB에서 admin 계정의 제품 목록을 조회하여 request.setAttribute("list", list)로 저장합니다.
		new ProductPortfolio().doCommand(request, response);
		
		// 2단계: portfolio.jsp로 forward
		RequestDispatcher dispatcher = request.getRequestDispatcher("/portfolio.jsp");
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
