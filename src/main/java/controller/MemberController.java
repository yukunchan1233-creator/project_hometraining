package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberDao;
import service.MemberLogin;
import service.MemberLogout;
import service.MemberSave;
import service.MyDelete;
import service.MyWishList;
import service.UseridCheck;
import service.GoogleLogin;
import service.ProfileEdit;
import service.ProfileUpdate;
import service.MypageMain;
import service.MyDeleteSelected;

@MultipartConfig(
	maxFileSize = 10 * 1024 * 1024, // 10MB
	maxRequestSize = 10 * 1024 * 1024
)
@WebServlet("/mem/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MemberController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String uri = request.getPathInfo();
		
		String page = null;
		
		switch(uri) {
		case "/login.do":
			page = "/member/login.jsp";
			break;
		case "/join.do":
			page = "/member/join.jsp";
			break;
		case "/useridCheck.do":
			new UseridCheck().doCommand(request, response);
			break;
		case "/membersave.do":
			new MemberSave().doCommand(request, response);
			break;
		case "/loginpro.do":
			new MemberLogin().doCommand(request, response);
			break;
		case "/googleLogin.do":  // 추가
			new GoogleLogin().doCommand(request, response);
			break;
		case "/logout.do":
			new MemberLogout().doCommand(request, response);
			break;
		case "/mypageMain.do":
			new MypageMain().doCommand(request, response);
			page = "/member/mypage.jsp";
			break;
		case "/mypage.do":
			new MyWishList().doCommand(request, response);
			page = "/member/wishlist.jsp";
			break;
		case "/profileEdit.do":
			new ProfileEdit().doCommand(request, response);
			page = "/member/profileEdit.jsp";
			break;
		case "/profileUpdate.do":
			new ProfileUpdate().doCommand(request, response);
			break;
		case "/mywishDelete.do":
			new MyDelete().doCommand(request, response);
			break;
		case "/mywishDeleteSelected.do":
			new MyDeleteSelected().doCommand(request, response);
			break;
		default:
			System.out.println("잘못된 요청입니다.");
			break;
		}
		
		if(page != null) {
			RequestDispatcher rs = request.getRequestDispatcher(page);
			rs.forward(request, response);
		}
	}
}




