package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Command;
import model.MemberDao;
import model.MemberDto;
import util.PasswordBcrypt;
import util.FileUploadUtil;

/**
 * 개인정보 수정 처리 서비스
 * 사용자가 입력한 정보로 DB 업데이트
 */
public class ProfileUpdate implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("userid");
		
		if(userid == null) {
			response.getWriter().print("<script>alert('로그인이 필요합니다.'); location.href='" + 
				request.getContextPath() + "/mem/login.do';</script>");
			return;
		}
		
		// 기존 회원 정보 조회
		MemberDao dao = new MemberDao();
		MemberDto existingMember = dao.searchUserId(userid);
		
		if(existingMember == null) {
			response.getWriter().print("<script>alert('회원 정보를 찾을 수 없습니다.'); history.back();</script>");
			return;
		}
		
		// 프로필 사진 업로드 처리
		String profileImagePath = null;
		try {
			profileImagePath = FileUploadUtil.uploadFile(request, "profile_image");
			// 파일이 업로드되지 않았으면 null이 반환됨
		} catch (Exception e) {
			System.err.println("프로필 사진 업로드 중 오류: " + e.getMessage());
		}
		
		// 폼에서 전달받은 값들
		String writer = request.getParameter("writer");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		// 수정할 정보 설정
		MemberDto dto = new MemberDto();
		dto.setUserid(userid);
		dto.setWriter(writer);
		dto.setPhone(phone);
		dto.setEmail(email);
		
		// 프로필 사진 처리
		if(profileImagePath != null && !profileImagePath.isEmpty()) {
			// 새 프로필 사진이 업로드된 경우
			// 기존 프로필 사진 삭제 (선택사항)
			if(existingMember.getProfile_image() != null && !existingMember.getProfile_image().isEmpty()) {
				try {
					FileUploadUtil.deleteFile(request, existingMember.getProfile_image());
				} catch (Exception e) {
					System.err.println("기존 프로필 사진 삭제 중 오류: " + e.getMessage());
				}
			}
			dto.setProfile_image(profileImagePath);
		} else {
			// 프로필 사진 변경 안 하면 기존 프로필 사진 유지
			dto.setProfile_image(existingMember.getProfile_image());
		}
		
		// 비밀번호 변경 처리
		if(password != null && !password.trim().isEmpty()) {
			// 구글 로그인 사용자는 비밀번호가 null일 수 있음
			if(existingMember.getPassword() == null) {
				// 구글 로그인 사용자는 비밀번호 변경 불가
				response.getWriter().print("<script>alert('구글 로그인 사용자는 비밀번호를 변경할 수 없습니다.'); history.back();</script>");
				return;
			}
			// 비밀번호 암호화
			String hashedPassword = PasswordBcrypt.hashPassword(password);
			dto.setPassword(hashedPassword);
		} else {
			// 비밀번호 변경 안 하면 기존 비밀번호 유지
			dto.setPassword(existingMember.getPassword());
		}
		
		// DB 업데이트
		boolean result = dao.updateMember(dto);
		
		if(result) {
			// 세션 정보도 업데이트
			session.setAttribute("username", writer);
			
			response.getWriter().print("<script>alert('개인정보가 수정되었습니다.'); location.href='" + 
				request.getContextPath() + "/mem/mypageMain.do';</script>");
		} else {
			response.getWriter().print("<script>alert('개인정보 수정에 실패했습니다.'); history.back();</script>");
		}
	}

}
