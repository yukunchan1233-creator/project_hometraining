package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Command;
import model.ProductReviewDao;

public class ReviewCarousel implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		// 전체 후기 목록 조회 (최신순, 충분히 많이)
		ProductReviewDao reviewDao = new ProductReviewDao();
		List<model.ProductReviewDto> reviewList = reviewDao.selectAllReviewsWithProduct(50);
		
		// 디버깅: 후기 개수 확인
		System.out.println("[ReviewCarousel] 조회된 후기 개수: " + (reviewList != null ? reviewList.size() : 0));
		
		// 빈 리스트라도 항상 설정 (JSP에서 조건 체크)
		if(reviewList == null) {
			reviewList = new java.util.ArrayList<model.ProductReviewDto>();
		}
		
		request.setAttribute("reviewCarouselList", reviewList);
	}

}
