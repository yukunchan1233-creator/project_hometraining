package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BlogDao;
import model.Command;
import model.ProductDao;
import model.WishDto;

public class MyWishList implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession(false);
		String userid = (String)session.getAttribute("userid");
		
		if(userid == null) {
			// hometraining context path를 고려한 리다이렉트
			response.sendRedirect(request.getContextPath() + "/port/list.do");
			return;
		}
		
		// 블로그 찜 목록 조회
		List<WishDto> blogWishList = new BlogDao().myWishList(userid);
		// 블로그 찜에 타입 설정
		for(WishDto dto : blogWishList) {
			dto.setType("blog");
		}
		
		// 제품 찜 목록 조회
		List<WishDto> productWishList = new ProductDao().getProductWishListAsWishDto(userid);
		
		// 두 목록 합치기
		List<WishDto> allWishList = new ArrayList<WishDto>();
		allWishList.addAll(blogWishList);
		allWishList.addAll(productWishList);
		
		// 최신순으로 정렬 (regdate 기준)
		allWishList.sort((a, b) -> {
			if(a.getRegdate() == null) return 1;
			if(b.getRegdate() == null) return -1;
			return b.getRegdate().compareTo(a.getRegdate());
		});
		
		request.setAttribute("wishlist", allWishList);
	}

}




