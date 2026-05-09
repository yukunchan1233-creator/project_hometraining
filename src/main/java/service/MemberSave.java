package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Command;
import model.MemberDao;
import model.MemberDto;
import util.PasswordBcrypt;
import util.RedirectUtil;

/**
 * [회원가입 서비스 - MemberSave]
 * 설명: 회원가입 폼에서 입력된 정보를 받아 DB에 저장하는 서비스입니다.
 * 
 * 흐름:
 * 1. join.jsp에서 "회원가입" 버튼 클릭 → /mem/membersave.do로 POST 요청
 * 2. MemberController에서 MemberSave.doCommand() 호출
 * 3. 폼에서 전달받은 파라미터를 MemberDto 객체에 저장
 * 4. 비밀번호를 Bcrypt로 암호화
 * 5. MemberDao.memberSave()를 호출하여 DB에 저장
 * 6. 메인 페이지로 리다이렉트
 */
public class MemberSave implements Command {

	/**
	 * 회원가입 처리 메서드
	 * 설명: 폼에서 전달받은 회원 정보를 DB에 저장합니다.
	 */
	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 1단계: 요청 인코딩 설정 (한글 깨짐 방지)
		request.setCharacterEncoding("utf-8");
		
		// 2단계: 폼에서 전달받은 파라미터 추출
		String writer = request.getParameter("writer");      // 사용자명
		String userid = request.getParameter("userid");       // 아이디
		String password = request.getParameter("password");    // 비밀번호
		String email = request.getParameter("email");          // 이메일
		String phone = request.getParameter("phone");          // 전화번호
		
		// 3단계: MemberDto 객체 생성 및 데이터 설정
		MemberDto dto = new MemberDto();
		dto.setEmail(email);
		dto.setWriter(writer);
		
		// 4단계: 패스워드 암호화 처리 (보안을 위해 Bcrypt 사용)
		// 평문 비밀번호를 해시화하여 저장합니다.
		String hashpassword = PasswordBcrypt.hashPassword(password);
		dto.setPassword(hashpassword);
		
		dto.setUserid(userid);
		dto.setPhone(phone);
		
		// 5단계: MemberDao를 통해 DB에 회원 정보 저장
		MemberDao dao = new MemberDao();
		dao.memberSave(dto);
		
		// 6단계: 회원가입 완료 후 메인 페이지로 리다이렉트
		// jslhrd가 ROOT(/)로 설정되어 있어서 hometraining으로 명확히 리다이렉트
		RedirectUtil.redirect(request, response, "/main.do");
	}

}




