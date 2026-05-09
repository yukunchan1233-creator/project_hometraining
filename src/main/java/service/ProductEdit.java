package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Command;
import model.ProductDao;
import model.ProductDto;
import util.RedirectUtil;

/**
 * [상품 수정 폼 조회 서비스 - ProductEdit]
 * 설명: 수정할 상품 정보를 조회하여 폼에 표시합니다.
 */
public class ProductEdit implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		// 1단계: 파라미터 받기
		String pnoStr = request.getParameter("pno");
		String subcategory = request.getParameter("sub");
		
		if(pnoStr == null || pnoStr.isEmpty()) {
			RedirectUtil.redirect(request, response, "/portfolio.do?sub=" + subcategory);
			return;
		}
		
		int pno = Integer.parseInt(pnoStr);
		
		// 2단계: ProductDao를 통해 상품 정보 조회
		ProductDao dao = new ProductDao();
		ProductDto product = dao.selectProduct(pno);
		
		// 3단계: request에 저장
		request.setAttribute("product", product);
		request.setAttribute("subcategory", subcategory);
	}

}
