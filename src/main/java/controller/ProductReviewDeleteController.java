package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ProductReviewDelete;

/**
 * [제품 후기 삭제 컨트롤러 - ProductReviewDeleteController]
 * 설명: 제품 후기 삭제 요청을 처리하는 컨트롤러입니다.
 */
@WebServlet("/review/delete.do")
public class ProductReviewDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductReviewDeleteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 후기 삭제 서비스 호출
			new ProductReviewDelete().doCommand(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// 예외 발생 시 포트폴리오로 리다이렉트
			String subcategory = request.getParameter("sub");
			if(subcategory == null || subcategory.isEmpty()) {
				subcategory = "런닝머신";
			} else {
				subcategory = subcategory.trim();
			}
			String encodedSub = java.net.URLEncoder.encode(subcategory, "UTF-8");
			response.sendRedirect(request.getContextPath() + "/portfolio.do?sub=" + encodedSub + "&error=review_error");
		}
	}

}
