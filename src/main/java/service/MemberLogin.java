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

/**
 * [로그인 서비스 - MemberLogin]
 * 설명: 사용자가 입력한 아이디와 비밀번호를 검증하여 로그인을 처리하는 서비스입니다.
 * 
 * 흐름:
 * 1. login.jsp에서 "로그인" 버튼 클릭 → AJAX로 /mem/loginpro.do 요청
 * 2. MemberController에서 MemberLogin.doCommand() 호출
 * 3. DB에서 아이디로 회원 정보 조회
 * 4. 비밀번호 검증 (Bcrypt로 암호화된 비밀번호와 비교)
 * 5. 검증 성공 시 세션에 userid 저장
 * 6. AJAX 응답으로 "success" 반환
 */
public class MemberLogin implements Command {

	/**
	 * 로그인 처리 메서드
	 * 설명: 아이디와 비밀번호를 검증하여 로그인을 처리합니다.
	 */
	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 1단계: 요청 인코딩 설정
		request.setCharacterEncoding("utf-8");
		
		// 2단계: 폼에서 전달받은 파라미터 추출
		String userid = request.getParameter("userid");     // 아이디
		String password = request.getParameter("password");   // 비밀번호
		
		// 3단계: MemberDao를 통해 DB에서 아이디로 회원 정보 조회
		MemberDao dao = new MemberDao();
		MemberDto dto = dao.searchUserId(userid);
		
		// 4단계: 아이디가 존재하고 비밀번호가 일치하면 로그인 성공
		// PasswordBcrypt.checkPassword(): 평문 비밀번호와 해시화된 비밀번호를 비교
		if(dto != null && PasswordBcrypt.checkPassword(password, dto.getPassword())) {
			// 5단계: 세션 생성
			HttpSession session = request.getSession();
			
			// 6단계: userid를 세션 속성에 저장
			// 세션에 저장된 userid는 다른 페이지에서 ${sessionScope.userid}로 접근 가능합니다.
			session.setAttribute("userid", dto.getUserid());
			
			// 7단계: AJAX 응답으로 "success" 반환
			// JavaScript에서 이 값을 받아 로그인 성공 처리를 합니다.
			response.getWriter().print("success");
		}
		// 로그인 실패 시 아무것도 반환하지 않음 (JavaScript에서 처리)
	}

}




