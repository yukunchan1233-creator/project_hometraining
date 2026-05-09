package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

// ProductReviewDao : 제품 후기 관련 데이터베이스 작업을 수행하는 DAO 클래스
public class ProductReviewDao {
	
	// 제품별 후기 목록 조회
	public List<ProductReviewDto> selectReviewsByProduct(int pno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT review_no, pno, userid, rating, review_text, review_image, TO_CHAR(regdate, 'YYYY.MM.DD') as regdate "
				   + "FROM TBL_PRODUCT_REVIEW "
				   + "WHERE pno = ? "
				   + "ORDER BY regdate DESC";
		
		List<ProductReviewDto> list = new ArrayList<ProductReviewDto>();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductReviewDto dto = new ProductReviewDto();
				dto.setReview_no(rs.getInt("review_no"));
				dto.setPno(rs.getInt("pno"));
				dto.setUserid(rs.getString("userid"));
				dto.setRating(rs.getInt("rating"));
				dto.setReview_text(rs.getString("review_text"));
				dto.setReview_image(rs.getString("review_image"));
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
	
	// 제품별 평균 별점 조회
	public double selectAverageRating(int pno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT NVL(AVG(rating), 0) as avg_rating FROM TBL_PRODUCT_REVIEW WHERE pno = ?";
		double avgRating = 0.0;
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pno);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				avgRating = rs.getDouble("avg_rating");
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
		return avgRating;
	}
	
	// 제품별 후기 개수 조회
	public int selectReviewCount(int pno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT COUNT(*) as cnt FROM TBL_PRODUCT_REVIEW WHERE pno = ?";
		int count = 0;
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pno);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("cnt");
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
		return count;
	}
	
	// 후기 작성
	public int insertReview(ProductReviewDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "INSERT INTO TBL_PRODUCT_REVIEW (review_no, pno, userid, rating, review_text, review_image, regdate) "
				   + "VALUES (SEQ_PRODUCT_REVIEW.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getPno());
			pstmt.setString(2, dto.getUserid());
			pstmt.setInt(3, dto.getRating());
			pstmt.setString(4, dto.getReview_text());
			pstmt.setString(5, dto.getReview_image());
			
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
	
	// 후기 작성 후 제품의 후기 개수 및 평균 별점 업데이트
	public void updateProductReviewStats(int pno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 제품의 후기 개수와 평균 별점을 계산하여 업데이트
		String sql = "UPDATE TBL_PRODUCT SET review_count = (SELECT COUNT(*) FROM TBL_PRODUCT_REVIEW WHERE pno = ?) "
				   + "WHERE pno = ?";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pno);
			pstmt.setInt(2, pno);
			pstmt.executeUpdate();
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
	}
	
	// 사용자가 이미 해당 제품에 후기를 작성했는지 확인
	public boolean checkReviewExists(int pno, String userid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT COUNT(*) as cnt FROM TBL_PRODUCT_REVIEW WHERE pno = ? AND userid = ?";
		boolean exists = false;
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pno);
			pstmt.setString(2, userid);
			rs = pstmt.executeQuery();
			
			if(rs.next() && rs.getInt("cnt") > 0) {
				exists = true;
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
		return exists;
	}
	
	// 후기 번호로 후기 조회
	public ProductReviewDto selectReview(int review_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT review_no, pno, userid, rating, review_text, review_image, TO_CHAR(regdate, 'YYYY.MM.DD') as regdate "
				   + "FROM TBL_PRODUCT_REVIEW "
				   + "WHERE review_no = ?";
		
		ProductReviewDto dto = null;
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, review_no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ProductReviewDto();
				dto.setReview_no(rs.getInt("review_no"));
				dto.setPno(rs.getInt("pno"));
				dto.setUserid(rs.getString("userid"));
				dto.setRating(rs.getInt("rating"));
				dto.setReview_text(rs.getString("review_text"));
				dto.setReview_image(rs.getString("review_image"));
				dto.setRegdate(rs.getString("regdate"));
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
	
	// 후기 수정
	public int updateReview(ProductReviewDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "UPDATE TBL_PRODUCT_REVIEW SET rating = ?, review_text = ?, review_image = ? "
				   + "WHERE review_no = ?";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getRating());
			pstmt.setString(2, dto.getReview_text());
			pstmt.setString(3, dto.getReview_image());
			pstmt.setInt(4, dto.getReview_no());
			
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
	
	// 후기 삭제
	public int deleteReview(int review_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "DELETE FROM TBL_PRODUCT_REVIEW WHERE review_no = ?";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, review_no);
			
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
	
	// 전체 후기 목록 조회 (제품 정보 포함, 최신순)
	public List<ProductReviewDto> selectAllReviewsWithProduct(int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT r.review_no, r.pno, r.userid, r.rating, r.review_text, r.review_image, "
				   + "       TO_CHAR(r.regdate, 'YYYY.MM.DD') as regdate, "
				   + "       NVL(p.product_name, '제품 정보 없음') as product_name, "
				   + "       p.image_path, "
				   + "       NVL(p.subcategory, '') as subcategory "
				   + "FROM TBL_PRODUCT_REVIEW r "
				   + "LEFT JOIN TBL_PRODUCT p ON r.pno = p.pno "
				   + "ORDER BY r.regdate DESC";
		
		List<ProductReviewDto> list = new ArrayList<ProductReviewDto>();
		
		try {
			conn = DBManager.getInstance();
			if(limit > 0) {
				// Oracle 12c 이상: FETCH FIRST N ROWS ONLY
				sql = "SELECT * FROM (" + sql + ") WHERE ROWNUM <= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, limit);
			} else {
				pstmt = conn.prepareStatement(sql);
			}
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductReviewDto dto = new ProductReviewDto();
				dto.setReview_no(rs.getInt("review_no"));
				dto.setPno(rs.getInt("pno"));
				dto.setUserid(rs.getString("userid"));
				dto.setRating(rs.getInt("rating"));
				dto.setReview_text(rs.getString("review_text"));
				dto.setReview_image(rs.getString("review_image"));
				dto.setRegdate(rs.getString("regdate"));
				// 제품 정보
				dto.setProduct_name(rs.getString("product_name"));
				dto.setProduct_image(rs.getString("image_path"));
				dto.setSubcategory(rs.getString("subcategory"));
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
}
