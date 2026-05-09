package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ProductReviewView;

/**
 * [제품 후기 상세 페이지 컨트롤러 - ProductReviewViewController]
 * 설명: 제품 후기 상세 페이지 요청을 처리하는 컨트롤러입니다.
 */
@WebServlet("/review/view.do")
public class ProductReviewViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductReviewViewController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 후기 상세 페이지 서비스 호출
		new ProductReviewView().doCommand(request, response);
	}

}
