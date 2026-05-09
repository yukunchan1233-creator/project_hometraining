package service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Command;
import model.MemberDao;
import model.MemberDto;
import model.ProductDao;
import model.ProductDto;
import model.ProductReviewDao;
import java.util.HashMap;
import java.util.Map;

/**
 * [제품 후기 상세 페이지 서비스 - ProductReviewView]
 * 설명: 제품의 후기 목록과 평균 별점을 조회하여 후기 상세 페이지에 표시합니다.
 */
public class ProductReviewView implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		// 1단계: 파라미터 받기
		String pnoStr = request.getParameter("pno");
		String subcategory = request.getParameter("sub");
		
		// subcategory 공백 제거 및 기본값 설정
		if(subcategory != null) {
			subcategory = subcategory.trim();
		}
		if(subcategory == null || subcategory.isEmpty()) {
			subcategory = "런닝머신";
		}
		
		if(pnoStr == null || pnoStr.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/portfolio.do?sub=" + java.net.URLEncoder.encode(subcategory, "UTF-8"));
			return;
		}
		
		int pno = Integer.parseInt(pnoStr);
		
		// 2단계: 제품 정보 조회
		ProductDao productDao = new ProductDao();
		ProductDto product = productDao.selectProduct(pno);
		
		if(product == null) {
			response.sendRedirect(request.getContextPath() + "/portfolio.do?sub=" + java.net.URLEncoder.encode(subcategory, "UTF-8"));
			return;
		}
		
		// 3단계: 후기 목록 조회
		ProductReviewDao reviewDao = new ProductReviewDao();
		java.util.List<model.ProductReviewDto> reviewList = reviewDao.selectReviewsByProduct(pno);
		
		// 4단계: 각 후기 작성자의 프로필 사진 조회
		MemberDao memberDao = new MemberDao();
		Map<String, String> userProfileMap = new HashMap<String, String>();
		for(model.ProductReviewDto review : reviewList) {
			if(review.getUserid() != null && !userProfileMap.containsKey(review.getUserid())) {
				MemberDto member = memberDao.searchUserId(review.getUserid());
				if(member != null && member.getProfile_image() != null) {
					userProfileMap.put(review.getUserid(), member.getProfile_image());
				} else {
					userProfileMap.put(review.getUserid(), null);
				}
			}
		}
		
		// 5단계: 평균 별점 조회
		double avgRating = reviewDao.selectAverageRating(pno);
		
		// 6단계: request에 저장
		request.setAttribute("product", product);
		request.setAttribute("reviewList", reviewList);
		request.setAttribute("userProfileMap", userProfileMap);
		request.setAttribute("avgRating", avgRating);
		request.setAttribute("subcategory", subcategory);
		
		// 6단계: JSP로 포워드
		RequestDispatcher dispatcher = request.getRequestDispatcher("/review_detail.jsp");
		dispatcher.forward(request, response);
	}

}
