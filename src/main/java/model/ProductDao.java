package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

// ProductDao : 운동기구 제품 관련 데이터베이스 작업을 수행하는 DAO 클래스
public class ProductDao {
	
	// 카테고리별 하위카테고리 목록 조회 (예: 가슴 -> 벤치프레스, 딥스 등)
	public List<String> selectSubcategories(String category) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT DISTINCT subcategory FROM TBL_PRODUCT WHERE category = ? ORDER BY subcategory";
		List<String> list = new ArrayList<String>();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(rs.getString("subcategory"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 하위카테고리별 제품 목록 조회 (예: 벤치프레스 -> 모든 벤치프레스 제품)
	public List<ProductDto> selectProducts(String category, String subcategory) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT pno, site_name, product_name, price, review_count, image_path, buy_link "
				   + "FROM TBL_PRODUCT "
				   + "WHERE category = ? AND subcategory = ? "
				   + "ORDER BY price";
		
		List<ProductDto> list = new ArrayList<ProductDto>();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setString(2, subcategory);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductDto dto = new ProductDto();
				dto.setPno(rs.getInt("pno"));
				dto.setSite_name(rs.getString("site_name"));
				dto.setProduct_name(rs.getString("product_name"));
				dto.setPrice(rs.getInt("price"));
				dto.setReview_count(rs.getInt("review_count"));
				dto.setImage_path(rs.getString("image_path"));
				dto.setBuy_link(rs.getString("buy_link"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 제품 상세 정보 조회 (제품번호로)
	public ProductDto selectProduct(int pno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM TBL_PRODUCT WHERE pno = ?";
		ProductDto dto = null;
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pno);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ProductDto();
				dto.setPno(rs.getInt("pno"));
				dto.setCategory(rs.getString("category"));
				dto.setSubcategory(rs.getString("subcategory"));
				dto.setSite_name(rs.getString("site_name"));
				dto.setProduct_name(rs.getString("product_name"));
				dto.setPrice(rs.getInt("price"));
				dto.setReview_count(rs.getInt("review_count"));
				dto.setImage_path(rs.getString("image_path"));
				dto.setDetail_images(rs.getString("detail_images"));
				dto.setBuy_link(rs.getString("buy_link"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
	
	// admin 계정이 등록한 제품 목록 조회 (정렬 옵션 포함)
	public List<ProductDto> selectProductsByAdmin(String subcategory, String sortType) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 정렬 옵션에 따른 ORDER BY 절 설정
		String orderBy = "ORDER BY ";
		if("popular".equals(sortType)) {
			// 인기상품순: 후기 개수 내림차순
			orderBy += "review_count DESC";
		} else if("price_low".equals(sortType)) {
			// 낮은가격순: 가격 오름차순
			orderBy += "price ASC";
		} else if("price_high".equals(sortType)) {
			// 높은가격순: 가격 내림차순
			orderBy += "price DESC";
		} else {
			// 기본값: 인기상품순
			orderBy += "review_count DESC";
		}
		
		String sql = "SELECT pno, category, subcategory, site_name, product_name, price, review_count, image_path, buy_link, userid, TO_CHAR(regdate, 'YYYY.MM.DD') as regdate "
				   + "FROM TBL_PRODUCT "
				   + "WHERE userid = 'admin' AND subcategory = ? "
				   + orderBy;
		
		List<ProductDto> list = new ArrayList<ProductDto>();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subcategory);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductDto dto = new ProductDto();
				dto.setPno(rs.getInt("pno"));
				dto.setCategory(rs.getString("category"));
				dto.setSubcategory(rs.getString("subcategory"));
				dto.setSite_name(rs.getString("site_name"));
				dto.setProduct_name(rs.getString("product_name"));
				dto.setPrice(rs.getInt("price"));
				dto.setReview_count(rs.getInt("review_count"));
				dto.setImage_path(rs.getString("image_path"));
				dto.setBuy_link(rs.getString("buy_link"));
				dto.setUserid(rs.getString("userid"));
				dto.setRegdate(rs.getString("regdate"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 상품 추가 (INSERT)
	public int insertProduct(ProductDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "INSERT INTO TBL_PRODUCT (pno, category, subcategory, site_name, product_name, price, review_count, image_path, detail_images, buy_link, userid, regdate) "
				   + "VALUES (SEQ_PRODUCT.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCategory());
			pstmt.setString(2, dto.getSubcategory());
			pstmt.setString(3, dto.getSite_name());
			pstmt.setString(4, dto.getProduct_name());
			pstmt.setInt(5, dto.getPrice());
			pstmt.setInt(6, dto.getReview_count());
			pstmt.setString(7, dto.getImage_path());
			pstmt.setString(8, dto.getDetail_images());
			pstmt.setString(9, dto.getBuy_link());
			pstmt.setString(10, dto.getUserid());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 상품 수정 (UPDATE)
	public int updateProduct(ProductDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "UPDATE TBL_PRODUCT SET category=?, subcategory=?, site_name=?, product_name=?, price=?, review_count=?, image_path=?, detail_images=?, buy_link=? WHERE pno=?";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCategory());
			pstmt.setString(2, dto.getSubcategory());
			pstmt.setString(3, dto.getSite_name());
			pstmt.setString(4, dto.getProduct_name());
			pstmt.setInt(5, dto.getPrice());
			pstmt.setInt(6, dto.getReview_count());
			pstmt.setString(7, dto.getImage_path());
			pstmt.setString(8, dto.getDetail_images());
			pstmt.setString(9, dto.getBuy_link());
			pstmt.setInt(10, dto.getPno());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 상품 삭제 (DELETE)
	public int deleteProduct(int pno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "DELETE FROM TBL_PRODUCT WHERE pno=?";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pno);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 제품 찜 추가 (INSERT)
	// 반환값: 1=성공, -1=이미 찜한 제품, 0=실패
	public int productWishInsert(String userid, int pno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// TBL_PRODUCT_WISH 테이블 사용 (별도 제품 찜 테이블)
		String checkSql = "SELECT COUNT(*) FROM TBL_PRODUCT_WISH WHERE userid=? AND pno=?";
		String insertSql = "INSERT INTO TBL_PRODUCT_WISH (wish_pno, pno, userid, regdate) VALUES (SEQ_PRODUCT_WISH.NEXTVAL, ?, ?, SYSDATE)";
		
		try {
			conn = DBManager.getInstance();
			
			// 중복 체크
			pstmt = conn.prepareStatement(checkSql);
			pstmt.setString(1, userid);
			pstmt.setInt(2, pno);
			rs = pstmt.executeQuery();
			
			if(rs.next() && rs.getInt(1) > 0) {
				// 이미 찜한 경우
				return -1;
			}
			
			// 중복이 아니면 찜 추가
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			
			pstmt = conn.prepareStatement(insertSql);
			pstmt.setInt(1, pno);
			pstmt.setString(2, userid);
			
			int result = pstmt.executeUpdate();
			if(result > 0) {
				System.out.println("제품 찜 추가 성공 - userid: " + userid + ", pno: " + pno);
				return 1;
			} else {
				System.err.println("제품 찜 추가 실패: executeUpdate 결과가 0 - userid: " + userid + ", pno: " + pno);
				return 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("========================================");
			System.err.println("제품 찜 추가 실패 - userid: " + userid + ", pno: " + pno);
			System.err.println("에러 클래스: " + e.getClass().getName());
			System.err.println("에러 메시지: " + e.getMessage());
			if(e.getCause() != null) {
				System.err.println("원인: " + e.getCause().getMessage());
			}
			System.err.println("========================================");
			return 0;
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 사용자의 제품 찜 목록 조회 (WishDto 형태로 반환)
	public List<model.WishDto> getProductWishListAsWishDto(String userid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT w.wish_pno, w.regdate as wish_regdate, p.pno, p.product_name, p.site_name, p.price, p.image_path, p.subcategory " +
				     "FROM TBL_PRODUCT_WISH w " +
				     "JOIN TBL_PRODUCT p ON w.pno = p.pno " +
				     "WHERE w.userid = ? " +
				     "ORDER BY w.regdate DESC";
		
		List<model.WishDto> list = new ArrayList<model.WishDto>();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				model.WishDto dto = new model.WishDto();
				dto.setWish_bno(rs.getInt("wish_pno"));  // 제품 찜 번호
				dto.setPno(rs.getInt("pno"));
				dto.setType("product");  // 제품 찜임을 표시
				dto.setProduct_name(rs.getString("product_name"));
				dto.setSite_name(rs.getString("site_name"));
				dto.setPrice(rs.getInt("price"));
				
				// 이미지 경로 처리 (null 체크 및 경로 정규화)
				String imagePath = rs.getString("image_path");
				if(imagePath != null && !imagePath.trim().isEmpty()) {
					// 경로가 /로 시작하지 않으면 / 추가
					if(!imagePath.startsWith("/")) {
						imagePath = "/" + imagePath;
					}
					dto.setImgfile(imagePath);
				} else {
					// 이미지가 없으면 기본 이미지 경로
					dto.setImgfile("/images/image_1.jpg");
				}
				
				dto.setSubcategory(rs.getString("subcategory"));
				dto.setTitle(rs.getString("product_name"));  // 제목으로 제품명 사용
				String regdateStr = rs.getString("wish_regdate");
				if(regdateStr != null && regdateStr.length() >= 10) {
					dto.setRegdate(regdateStr.substring(0, 10));
				} else {
					dto.setRegdate(regdateStr);
				}
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 사용자의 제품 찜 목록 조회 (pno만)
	public List<Integer> getProductWishList(String userid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT pno FROM TBL_PRODUCT_WISH WHERE userid=? ORDER BY regdate DESC";
		List<Integer> list = new ArrayList<Integer>();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(rs.getInt("pno"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 제품 찜 삭제
	public boolean deleteProductWish(int wishPno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "DELETE FROM TBL_PRODUCT_WISH WHERE wish_pno = ?";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, wishPno);
			
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 제품 찜 여러 개 삭제
	public boolean deleteProductWishSelected(String wishPnos) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		if(wishPnos == null || wishPnos.trim().isEmpty()) {
			return false;
		}
		
		String[] pnoArray = wishPnos.split(",");
		StringBuilder placeholders = new StringBuilder();
		for(int i = 0; i < pnoArray.length; i++) {
			if(i > 0) placeholders.append(",");
			placeholders.append("?");
		}
		
		String sql = "DELETE FROM TBL_PRODUCT_WISH WHERE wish_pno IN (" + placeholders.toString() + ")";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			for(int i = 0; i < pnoArray.length; i++) {
				pstmt.setInt(i + 1, Integer.parseInt(pnoArray[i].trim()));
			}
			
			result = pstmt.executeUpdate();
			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}







