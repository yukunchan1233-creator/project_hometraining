package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ProductReviewUpdate;

/**
 * [제품 후기 수정 컨트롤러 - ProductReviewUpdateController]
 * 설명: 제품 후기 수정 요청을 처리하는 컨트롤러입니다.
 */
@MultipartConfig(
    maxFileSize = 10 * 1024 * 1024,      // 10MB
    maxRequestSize = 10 * 1024 * 1024    // 10MB
)
@WebServlet("/review/update.do")
public class ProductReviewUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductReviewUpdateController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 후기 수정 서비스 호출
			new ProductReviewUpdate().doCommand(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// 예외 발생 시 JSON 에러 응답 반환 (AJAX 요청이므로)
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print("{\"success\":false,\"message\":\"서버 오류가 발생했습니다: " + e.getMessage() + "\"}");
		}
	}

}
