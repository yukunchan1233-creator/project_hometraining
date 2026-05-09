package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class BlogDao {
	public void blogInsert(BlogDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql ="insert into htm_blog (bno,name,title,content,imgfile) "
	            + " values (htm_blog_seq.nextval,?,?,?,?)";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getImgfile());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * [포트폴리오 전체 조회 메서드 - getAll()]
	 * 설명: DB에서 모든 포트폴리오를 최신순(bno desc)으로 조회하여 반환합니다.
	 * 
	 * 흐름:
	 * 1. BlogSelectIndex 서비스에서 호출됨
	 * 2. DB 연결 (DBManager.getInstance())
	 * 3. SQL 실행: "select * from htm_blog order by bno desc"
	 * 4. 결과를 BlogDto 객체로 변환하여 List에 저장
	 * 5. List 반환
	 * 
	 * @return List<BlogDto> 포트폴리오 목록
	 */
	public List<BlogDto> getAll() {
		Connection conn = null;        // DB 연결 객체
		PreparedStatement pstmt = null; // SQL 실행 객체
		ResultSet rs = null;            // 조회 결과 객체
		
		// SQL: 모든 포트폴리오를 게시글 번호(bno) 내림차순으로 조회
		String sql = "select * from htm_blog order by bno desc";
		
		// 검색된 결과 여러개 리턴 하려면 dto 저장하는 가변배열(ArrayList) 만든다.
		List<BlogDto> list = new ArrayList<BlogDto>();
		
		try {
			// 1단계: DB 연결 (DBManager를 통해 Connection 객체 획득)
			conn = DBManager.getInstance();
			
			// 2단계: SQL 문 준비
			pstmt = conn.prepareStatement(sql);
			
			// 3단계: SQL 실행 및 결과 받기
			rs = pstmt.executeQuery();
			
			// 4단계: 조회 결과를 반복하면서 BlogDto 객체로 변환
			while(rs.next()) {
				BlogDto dto = new BlogDto();
				dto.setBno(rs.getInt("bno"));                    // 게시글 번호
				dto.setName(rs.getString("name"));               // 작성자명
				dto.setTitle(rs.getString("title"));              // 제목
				dto.setContent(rs.getString("content"));          // 내용
				dto.setImgfile(rs.getString("imgfile"));          // 이미지 파일명
				dto.setViews(rs.getInt("views"));                  // 조회수
				dto.setRegdate(rs.getString("regdate").substring(0,10)); // 등록일 (앞 10자만)
				list.add(dto); // List에 추가
			}
			
		} catch (Exception e) {
			e.printStackTrace(); // 에러 발생 시 콘솔에 출력
		} finally {
			// 5단계: 리소스 해제 (반드시 실행됨)
			// DB 연결은 제한된 리소스이므로 사용 후 반드시 닫아야 합니다.
			try {
				if(rs != null) rs.close();      // ResultSet 닫기
				if(pstmt != null) pstmt.close(); // PreparedStatement 닫기
				if(conn != null) conn.close();    // Connection 닫기
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list; // 조회된 포트폴리오 목록 반환
	}

	//view
	public BlogDto getSelectByBno(int bno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from htm_blog where bno=?";
		BlogDto dto = new BlogDto();
		
		try {
			conn= DBManager.getInstance();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs= pstmt.executeQuery();
			while(rs.next()) {
				dto.setBno(rs.getInt("bno"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate").substring(0,10));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}
	
	//조회수 증가
	public void viewCount(int bno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "update htm_blog set views = views + 1 where bno = ?";
		
		try {
			conn= DBManager.getInstance();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	//블로그 삭제
	public void blogDelete(int bno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "delete htm_blog where bno = ?";
		
		try {
			conn= DBManager.getInstance();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	//키워드로 글검색
	public List<BlogDto> getSearchAndPaging(String keyword, int page, int pageSize) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from (\n"
				+ "    select rownum rn, aaa.* from\n"
				+ "        (select * from htm_blog where title like ? or content like ? order by bno desc) aaa\n"
				+ "        where rownum <= ?)\n"
				+ "    where rn > ?";
		
		//검색된 결과 여러개 리턴 하려면 dto저장하는 가변배열 만든다.
		List<BlogDto> list = new ArrayList<BlogDto>();
		int offset = (page-1) * pageSize;
		try {
			conn= DBManager.getInstance();
			pstmt=conn.prepareStatement(sql);
			String searchKeyword = "%" + keyword + "%";
			pstmt.setString(1, searchKeyword);
			pstmt.setString(2, searchKeyword);
			pstmt.setInt(3, offset+pageSize);
			pstmt.setInt(4, offset);
			rs= pstmt.executeQuery();
			while(rs.next()) {
				BlogDto dto = new BlogDto();
				dto.setBno(rs.getInt("bno"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate").substring(0,10));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}

	//검색 결과 개수 조회
	public int getSearchCount(String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select count(*) from htm_blog where title like ? or content like ?";
		int count = 0;
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			String searchKeyword = "%" + keyword + "%";
			pstmt.setString(1, searchKeyword);
			pstmt.setString(2, searchKeyword);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { 
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return count;
	}

	//블로그 글 수정
	public void blogUpdate(BlogDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "update htm_blog set title=?, content=?, imgfile=? where bno=?";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getImgfile());
			pstmt.setInt(4, dto.getBno());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	//찜 추가 (1:성공, -1:이미있음, 0:실패)
	public int mywishInsert(String userid, int blogbno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 먼저 중복 체크
		String checkSql = "select count(*) from htm_mywish where userid=? and blog_bno=?";
		String insertSql = "insert into htm_mywish (wish_bno, userid, blog_bno) values (htm_mywish_seq.nextval, ?, ?)";
		
		try {
			conn = DBManager.getInstance();
			
			// 중복 체크
			pstmt = conn.prepareStatement(checkSql);
			pstmt.setString(1, userid);
			pstmt.setInt(2, blogbno);
			rs = pstmt.executeQuery();
			
			if(rs.next() && rs.getInt(1) > 0) {
				// 이미 찜한 경우
				return -1;
			}
			
			// 중복이 아니면 추가
			if(pstmt != null) pstmt.close();
			pstmt = conn.prepareStatement(insertSql);
			pstmt.setString(1, userid);
			pstmt.setInt(2, blogbno);
			int result = pstmt.executeUpdate();
			
			return result; // 1: 성공, 0: 실패
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	//찜 목록 조회
	public List<WishDto> myWishList(String userid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select w.wish_bno, b.bno, b.name, b.title, b.content, b.imgfile, b.views, b.regdate " +
				     "from htm_mywish w join htm_blog b on w.blog_bno = b.bno " +
				     "where w.userid = ? order by w.wish_bno desc";
		
		List<WishDto> list = new ArrayList<WishDto>();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				WishDto dto = new WishDto();
				dto.setWish_bno(rs.getInt("wish_bno"));
				dto.setBno(rs.getInt("bno"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate").substring(0, 10));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}

}




