package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.ProductAdd;
import service.ProductDelete;
import service.ProductEdit;
import service.ProductUpdate;

/**
 * [관리자 상품 관리 컨트롤러 - AdminProductController]
 * 설명: 관리자가 상품을 추가/수정/삭제하는 컨트롤러입니다.
 * 
 * URL 매핑:
 * - /admin/product/add.do : 상품 추가 폼
 * - /admin/product/addpro.do : 상품 추가 처리
 * - /admin/product/edit.do : 상품 수정 폼
 * - /admin/product/updatepro.do : 상품 수정 처리
 * - /admin/product/delete.do : 상품 삭제 처리
 */
@WebServlet("/admin/product/*")
@MultipartConfig(
    maxFileSize = 10 * 1024 * 1024,      // 10MB
    maxRequestSize = 50 * 1024 * 1024     // 50MB
)
public class AdminProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AdminProductController() {
        super();
    }

	/**
	 * GET 요청 처리
	 * - add.do: 상품 추가 폼 표시
	 * - edit.do: 상품 수정 폼 표시
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 관리자 권한 체크
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		
		if(!"admin".equals(userid)) {
			response.sendRedirect(request.getContextPath() + "/mem/login.do");
			return;
		}
		
		String path = request.getPathInfo(); // /add.do 또는 /edit.do 등
		
		if("/add.do".equals(path)) {
			// 상품 추가 폼
			String subcategory = request.getParameter("sub");
			// subcategory 공백 제거
			if(subcategory != null) {
				subcategory = subcategory.trim();
			}
			request.setAttribute("subcategory", subcategory);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/product_form.jsp");
			dispatcher.forward(request, response);
			
		} else if("/edit.do".equals(path)) {
			// 상품 수정 폼
			new ProductEdit().doCommand(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/product_form.jsp");
			dispatcher.forward(request, response);
			
		} else if("/delete.do".equals(path)) {
			// 상품 삭제 처리
			new ProductDelete().doCommand(request, response);
		}
	}

	/**
	 * POST 요청 처리
	 * - addpro.do: 상품 추가 처리
	 * - updatepro.do: 상품 수정 처리
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 관리자 권한 체크
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		
		if(!"admin".equals(userid)) {
			response.sendRedirect(request.getContextPath() + "/mem/login.do");
			return;
		}
		
		String path = request.getPathInfo(); // /addpro.do 또는 /updatepro.do
		
		if("/addpro.do".equals(path)) {
			// 상품 추가 처리
			new ProductAdd().doCommand(request, response);
			
		} else if("/updatepro.do".equals(path)) {
			// 상품 수정 처리
			new ProductUpdate().doCommand(request, response);
		}
	}

}
