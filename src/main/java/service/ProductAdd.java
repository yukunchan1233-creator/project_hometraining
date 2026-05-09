package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Command;
import model.ProductDao;
import model.ProductDto;
import util.FileUploadUtil;
import util.RedirectUtil;

/**
 * [상품 추가 서비스 - ProductAdd]
 * 설명: 관리자가 입력한 상품 정보를 DB에 저장합니다.
 */
public class ProductAdd implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		// 1단계: 파라미터 받기
		String category = request.getParameter("category");
		String subcategory = request.getParameter("subcategory");
		String site_name = request.getParameter("site_name");
		String product_name = request.getParameter("product_name");
		String priceStr = request.getParameter("price");
		String review_countStr = request.getParameter("review_count");
		String image_path = request.getParameter("image_path");
		String buy_link = request.getParameter("buy_link");
		
		// subcategory 공백 제거
		if(subcategory != null) {
			subcategory = subcategory.trim();
		}
		
		// 필수 필드 검증
		if(category == null || category.isEmpty()) {
			request.setAttribute("error", "카테고리를 선택해주세요.");
			request.setAttribute("subcategory", subcategory);
			request.getRequestDispatcher("/admin/product_form.jsp").forward(request, response);
			return;
		}
		
		if(site_name == null || site_name.isEmpty()) {
			// site_name이 없으면 product_name에서 추출 시도
			if(product_name != null && product_name.contains(" ")) {
				site_name = product_name.split(" ")[0];
			} else {
				site_name = "기타";
			}
		}
		
		// 세션에서 관리자 아이디 가져오기
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		
		// 2단계: ProductDto 객체 생성 및 값 설정
		ProductDto dto = new ProductDto();
		dto.setCategory(category);
		dto.setSubcategory(subcategory);
		dto.setSite_name(site_name);
		dto.setProduct_name(product_name);
		
		if(priceStr != null && !priceStr.isEmpty()) {
			dto.setPrice(Integer.parseInt(priceStr));
		} else {
			dto.setPrice(0);
		}
		
		if(review_countStr != null && !review_countStr.isEmpty()) {
			dto.setReview_count(Integer.parseInt(review_countStr));
		} else {
			dto.setReview_count(0);
		}
		
		// 이미지 파일 업로드 처리
		String uploadedImagePath = FileUploadUtil.uploadFile(request, "image_file");
		if(uploadedImagePath != null && !uploadedImagePath.isEmpty()) {
			image_path = uploadedImagePath;
		} else if(image_path == null || image_path.isEmpty()) {
			image_path = "images/default.jpg"; // 기본 이미지
		}
		dto.setImage_path(image_path);
		dto.setDetail_images(image_path); // 상세 이미지는 대표 이미지와 동일하게
		
		dto.setBuy_link(buy_link);
		dto.setUserid(userid != null ? userid : "admin");
		
		// 3단계: ProductDao를 통해 DB에 저장
		ProductDao dao = new ProductDao();
		int result = dao.insertProduct(dto);
		
		// 4단계: 결과에 따라 리다이렉트
		if(result > 0) {
			// 성공: 상품 목록 페이지로 이동
			// subcategory URL 인코딩
			String encodedSub = java.net.URLEncoder.encode(subcategory, "UTF-8");
			response.sendRedirect(request.getContextPath() + "/portfolio.do?sub=" + encodedSub);
		} else {
			// 실패: 에러 메시지와 함께 다시 추가 폼으로
			request.setAttribute("error", "상품 추가에 실패했습니다.");
			request.setAttribute("subcategory", subcategory);
			request.getRequestDispatcher("/admin/product_form.jsp").forward(request, response);
		}
	}

}
