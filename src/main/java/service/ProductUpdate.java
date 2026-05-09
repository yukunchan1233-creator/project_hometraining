package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Command;
import model.ProductDao;
import model.ProductDto;
import util.FileUploadUtil;
import util.RedirectUtil;

/**
 * [상품 수정 서비스 - ProductUpdate]
 * 설명: 관리자가 수정한 상품 정보를 DB에 업데이트합니다.
 */
public class ProductUpdate implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		// 1단계: 파라미터 받기
		String pnoStr = request.getParameter("pno");
		String category = request.getParameter("category");
		String subcategory = request.getParameter("subcategory");
		String site_name = request.getParameter("site_name");
		String product_name = request.getParameter("product_name");
		String priceStr = request.getParameter("price");
		String review_countStr = request.getParameter("review_count");
		String image_path = request.getParameter("image_path");
		String buy_link = request.getParameter("buy_link");
		
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
		
		// 2단계: ProductDto 객체 생성 및 값 설정
		ProductDto dto = new ProductDto();
		dto.setPno(pno);
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
		ProductDao tempDao = new ProductDao();
		ProductDto existingProduct = tempDao.selectProduct(pno);
		String existingImagePath = null;
		if(existingProduct != null) {
			existingImagePath = existingProduct.getImage_path();
		}
		
		System.out.println("[ProductUpdate] 기존 이미지 경로: " + existingImagePath);
		System.out.println("[ProductUpdate] 전달받은 image_path 파라미터: " + image_path);
		
		String uploadedImagePath = FileUploadUtil.uploadFile(request, "image_file");
		System.out.println("[ProductUpdate] 업로드된 새 이미지 경로: " + uploadedImagePath);
		
		if(uploadedImagePath != null && !uploadedImagePath.isEmpty()) {
			// 새 이미지가 업로드된 경우
			System.out.println("[ProductUpdate] 새 이미지가 업로드되었습니다. 기존 이미지 삭제 시도...");
			// 기존 이미지 삭제
			if(existingImagePath != null && !existingImagePath.isEmpty()) {
				FileUploadUtil.deleteFile(request, existingImagePath);
				System.out.println("[ProductUpdate] 기존 이미지 삭제 완료: " + existingImagePath);
			}
			image_path = uploadedImagePath;
			System.out.println("[ProductUpdate] 새 이미지 경로로 설정: " + image_path);
		} else {
			// 새 이미지가 업로드되지 않은 경우
			if(image_path != null && !image_path.isEmpty()) {
				// hidden input으로 전달된 기존 이미지 경로 사용
				System.out.println("[ProductUpdate] 전달받은 기존 이미지 경로 사용: " + image_path);
			} else if(existingImagePath != null && !existingImagePath.isEmpty()) {
				// DB에서 조회한 기존 이미지 경로 사용
				image_path = existingImagePath;
				System.out.println("[ProductUpdate] DB에서 조회한 기존 이미지 경로 사용: " + image_path);
			} else {
				// 기본 이미지 사용
				image_path = "images/default.jpg";
				System.out.println("[ProductUpdate] 기본 이미지 사용: " + image_path);
			}
		}
		
		dto.setImage_path(image_path);
		dto.setDetail_images(image_path); // 상세 이미지는 대표 이미지와 동일하게
		System.out.println("[ProductUpdate] 최종 이미지 경로: " + image_path);
		
		dto.setBuy_link(buy_link);
		
		// 3단계: ProductDao를 통해 DB 업데이트
		ProductDao dao = new ProductDao();
		int result = dao.updateProduct(dto);
		
		// 4단계: 결과에 따라 리다이렉트
		if(result > 0) {
			// 성공: 상품 목록 페이지로 이동
			// subcategory URL 인코딩
			String encodedSub = java.net.URLEncoder.encode(subcategory, "UTF-8");
			response.sendRedirect(request.getContextPath() + "/portfolio.do?sub=" + encodedSub);
		} else {
			// 실패: 에러 메시지와 함께 다시 수정 폼으로
			request.setAttribute("error", "상품 수정에 실패했습니다.");
			request.setAttribute("product", dto);
			request.setAttribute("subcategory", subcategory);
			request.getRequestDispatcher("/admin/product_form.jsp").forward(request, response);
		}
	}

}
