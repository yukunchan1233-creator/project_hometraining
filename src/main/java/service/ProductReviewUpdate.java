package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Command;
import model.ProductReviewDao;
import model.ProductReviewDto;
import util.FileUploadUtil;

/**
 * [제품 후기 수정 서비스 - ProductReviewUpdate]
 * 설명: 제품 후기를 수정하는 서비스입니다.
 */
public class ProductReviewUpdate implements Command {

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
		String ratingStr = request.getParameter("rating");
		String review_text = request.getParameter("review_text");
		String subcategory = request.getParameter("sub");
		
		// review_text null 체크
		if(review_text == null) {
			review_text = "";
		}
		
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
			sendJsonError(response, "수정 권한이 없습니다.");
			return;
		}
		
		// 평점 처리 (0.0~5.0, 0.5 단위)
		double ratingDouble = 0.0;
		if(ratingStr != null && !ratingStr.isEmpty()) {
			try {
				ratingDouble = Double.parseDouble(ratingStr);
				// 범위 체크 (0.0~5.0)
				if(ratingDouble < 0.0) ratingDouble = 0.0;
				if(ratingDouble > 5.0) ratingDouble = 5.0;
			} catch (NumberFormatException e) {
				ratingDouble = existingReview.getRating(); // 기본값: 기존 평점
			}
		} else {
			ratingDouble = existingReview.getRating(); // 기본값: 기존 평점
		}
		
		// DB에 저장할 때는 소수점을 반올림하여 정수로 변환
		int rating = 0;
		if(ratingDouble == 0.0) {
			rating = 1;
		} else {
			rating = (int) Math.round(ratingDouble);
			if(rating < 1) rating = 1;
			if(rating > 5) rating = 5;
		}
		
		// 이미지 파일 업로드 처리 (새 이미지가 업로드된 경우에만)
		String review_image = existingReview.getReview_image(); // 기존 이미지 유지
		
		try {
			javax.servlet.http.Part filePart = request.getPart("review_image");
			if(filePart != null && filePart.getSize() > 0) {
				String newImage = FileUploadUtil.uploadFile(request, "review_image");
				if(newImage != null && !newImage.isEmpty()) {
					review_image = newImage;
				}
			}
		} catch (Exception e) {
			// 파일 업로드 오류 시 간단히 처리 (기존 이미지 유지)
			e.printStackTrace();
		}
		
		// DTO 생성
		ProductReviewDto dto = new ProductReviewDto();
		dto.setReview_no(review_no);
		dto.setRating(rating);
		dto.setReview_text(review_text);
		dto.setReview_image(review_image);
		
		// 후기 수정
		int result = dao.updateReview(dto);
		
		if(result > 0) {
			dao.updateProductReviewStats(existingReview.getPno());
			sendJsonSuccess(response, "후기가 수정되었습니다.");
		} else {
			sendJsonError(response, "후기 수정에 실패했습니다.");
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
