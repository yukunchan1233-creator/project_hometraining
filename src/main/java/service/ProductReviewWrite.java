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
 * [제품 후기 작성 서비스 - ProductReviewWrite]
 * 설명: 제품 후기를 작성하고 제품의 후기 통계를 업데이트하는 서비스입니다.
 */
public class ProductReviewWrite implements Command {

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
			try {
				response.sendRedirect(request.getContextPath() + "/mem/login.do");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		
		// 파라미터 받기
		String pnoStr = request.getParameter("pno");
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
		
		// pno 유효성 검증
		if(pnoStr == null || pnoStr.isEmpty()) {
			try {
				String encodedSub = java.net.URLEncoder.encode(subcategory, "UTF-8");
				response.sendRedirect(request.getContextPath() + "/portfolio.do?sub=" + encodedSub);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		
		int pno = 0;
		try {
			pno = Integer.parseInt(pnoStr);
		} catch (NumberFormatException e) {
			try {
				String encodedSub = java.net.URLEncoder.encode(subcategory, "UTF-8");
				response.sendRedirect(request.getContextPath() + "/portfolio.do?sub=" + encodedSub);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
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
				ratingDouble = 5.0; // 기본값
			}
		} else {
			ratingDouble = 5.0; // 기본값
		}
		
		// DB에 저장할 때는 소수점을 반올림하여 정수로 변환 (0.0~0.5는 1, 0.6~1.5는 2, ..., 4.6~5.0은 5)
		// 하지만 0.0은 특별히 처리: 0.0은 1로 저장 (최소 평점)
		int rating = 0;
		if(ratingDouble == 0.0) {
			rating = 1;
		} else {
			rating = (int) Math.round(ratingDouble);
			if(rating < 1) rating = 1;
			if(rating > 5) rating = 5;
		}
		
		// 이미지 파일 업로드 처리
		String review_image = null;
		try {
			review_image = FileUploadUtil.uploadFile(request, "review_image");
			if(review_image != null && !review_image.isEmpty()) {
				System.out.println("[ProductReviewWrite] 후기 이미지 업로드 성공: " + review_image);
			} else {
				System.out.println("[ProductReviewWrite] 후기 이미지가 업로드되지 않았습니다.");
			}
		} catch (Exception e) {
			System.err.println("[ProductReviewWrite] 후기 이미지 업로드 중 오류 발생:");
			e.printStackTrace();
		}
		
		// DTO 생성
		ProductReviewDto dto = new ProductReviewDto();
		dto.setPno(pno);
		dto.setUserid(userid);
		dto.setRating(rating);
		dto.setReview_text(review_text);
		dto.setReview_image(review_image);
		
		// DAO 호출
		ProductReviewDao dao = new ProductReviewDao();
		
		// 후기 작성 (개수 제한 없음)
		int result = 0;
		try {
			result = dao.insertReview(dto);
		} catch (Exception e) {
			e.printStackTrace();
			// 예외 발생 시 포트폴리오로 리다이렉트
			try {
				String encodedSub = java.net.URLEncoder.encode(subcategory, "UTF-8");
				response.sendRedirect(request.getContextPath() + "/portfolio.do?sub=" + encodedSub + "&error=review_failed");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return;
		}
		
		if(result > 0) {
			// 후기 작성 성공 시 제품의 후기 통계 업데이트
			try {
				dao.updateProductReviewStats(pno);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// 후기 상세 페이지로 리다이렉트 (URL 인코딩)
			try {
				String encodedSub = java.net.URLEncoder.encode(subcategory, "UTF-8");
				response.sendRedirect(request.getContextPath() + "/review/view.do?pno=" + pno + "&sub=" + encodedSub);
			} catch (Exception e) {
				e.printStackTrace();
				// 리다이렉트 실패 시 포트폴리오로
				try {
					String encodedSub = java.net.URLEncoder.encode(subcategory, "UTF-8");
					response.sendRedirect(request.getContextPath() + "/portfolio.do?sub=" + encodedSub);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} else {
			// 실패 시 에러 메시지와 함께 리다이렉트
			try {
				String encodedSub = java.net.URLEncoder.encode(subcategory, "UTF-8");
				response.sendRedirect(request.getContextPath() + "/review/view.do?pno=" + pno + "&sub=" + encodedSub + "&error=review_failed");
			} catch (Exception e) {
				e.printStackTrace();
				// 리다이렉트 실패 시 포트폴리오로
				try {
					String encodedSub = java.net.URLEncoder.encode(subcategory, "UTF-8");
					response.sendRedirect(request.getContextPath() + "/portfolio.do?sub=" + encodedSub);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}
