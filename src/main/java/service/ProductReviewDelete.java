package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Command;
import model.ProductReviewDao;
import model.ProductReviewDto;

/**
 * [제품 후기 삭제 서비스 - ProductReviewDelete]
 * 설명: 제품 후기를 삭제하는 서비스입니다.
 */
public class ProductReviewDelete implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 세션에서 로그인 정보 확인
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		
		// 로그인 체크
		if(userid == null || userid.isEmpty()) {
			sendJsonError(response, "로그인이 필요합니다.");
			return;
		}
		
		// 파라미터 받기
		String review_noStr = request.getParameter("review_no");
		String subcategory = request.getParameter("sub");
		
		// subcategory 공백 제거 및 기본값 설정
		if(subcategory != null) {
			subcategory = subcategory.trim();
		}
		if(subcategory == null || subcategory.isEmpty()) {
			subcategory = "런닝머신";
		}
		
		// review_no 유효성 검증
		if(review_noStr == null || review_noStr.isEmpty()) {
			sendJsonError(response, "후기 번호가 없습니다.");
			return;
		}
		
		int review_no = 0;
		try {
			review_no = Integer.parseInt(review_noStr);
		} catch (NumberFormatException e) {
			sendJsonError(response, "잘못된 후기 번호입니다.");
			return;
		}
		
		// 후기 작성자 확인
		ProductReviewDao dao = new ProductReviewDao();
		ProductReviewDto existingReview = dao.selectReview(review_no);
		
		if(existingReview == null) {
			sendJsonError(response, "후기를 찾을 수 없습니다.");
			return;
		}
		
		// 권한 체크
		if(!userid.equals(existingReview.getUserid()) && !"admin".equals(userid)) {
			sendJsonError(response, "삭제 권한이 없습니다.");
			return;
		}
		
		// 후기 삭제
		int result = dao.deleteReview(review_no);
		
		if(result > 0) {
			dao.updateProductReviewStats(existingReview.getPno());
			sendJsonSuccess(response, "후기가 삭제되었습니다.");
		} else {
			sendJsonError(response, "후기 삭제에 실패했습니다.");
		}
	}
	
	// JSON 에러 응답 헬퍼 메서드
	private void sendJsonError(HttpServletResponse response, String message) throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print("{\"success\":false,\"message\":\"" + message + "\"}");
	}
	
	// JSON 성공 응답 헬퍼 메서드
	private void sendJsonSuccess(HttpServletResponse response, String message) throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print("{\"success\":true,\"message\":\"" + message + "\"}");
	}
}
