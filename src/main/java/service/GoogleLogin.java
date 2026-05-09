package service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import model.Command;
import model.MemberDao;
import model.MemberDto;

public class GoogleLogin implements Command {

    private static final String CLIENT_ID = "888481044220-hncob4mhcr6t027h49p85vdd2u1aamoe.apps.googleusercontent.com";
    
    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        String credential = request.getParameter("credential");
        
        if (credential == null || credential.isEmpty()) {
            response.getWriter().print("error: credential이 없습니다.");
            return;
        }
        
        try {
            System.out.println("구글 로그인 요청 받음");
            // Google ID 토큰 검증 및 사용자 정보 가져오기
            String userInfo = verifyGoogleToken(credential);
            
            if (userInfo == null) {
                System.out.println("토큰 검증 실패");
                response.getWriter().print("error: 토큰 검증 실패");
                return;
            }
            
            System.out.println("토큰 검증 성공");
            
            // JSON 파싱
            JSONObject jsonObject = new JSONObject(userInfo);
            String email = jsonObject.getString("email");
            String name = jsonObject.getString("name");
            String googleId = jsonObject.getString("sub");
            String picture = jsonObject.has("picture") ? jsonObject.getString("picture") : "";
            
            MemberDao dao = new MemberDao();
            
            // 이메일로 기존 회원 확인
            MemberDto dto = dao.searchUserByEmail(email);
            
            if (dto == null) {
                // 구글 로그인 사용자가 없으면 자동 회원가입
                dto = new MemberDto();
                // userid 길이 제한 (50자)
                String userId = "google_" + googleId;
                if (userId.length() > 50) {
                    userId = userId.substring(0, 50);
                }

                dto.setUserid(userId);

                dto.setWriter(name != null && name.length() > 20 ? name.substring(0, 20) : name);
                dto.setEmail(email != null && email.length() > 50 ? email.substring(0, 50) : email);
                dto.setPassword(null); // 구글 로그인은 비밀번호 없음 (NULL)
                dto.setPhone(null);

            	System.out.println(dto);
                try {
                    dao.memberSave(dto);
                    System.out.println("구글 로그인 회원가입 성공: " + email);
                } catch (Exception saveEx) {
                    System.out.println("회원가입 실패: " + saveEx.getMessage());
                    saveEx.printStackTrace();
                    response.getWriter().print("error: 회원가입 실패 - " + saveEx.getMessage());
                    return;
                }
                
                // 저장 후 다시 조회
                dto = dao.searchUserByEmail(email);
            }
            
            if (dto != null) {
                // 세션 생성
                HttpSession session = request.getSession();
                session.setAttribute("userid", dto.getUserid());
                session.setAttribute("username", dto.getWriter());
                
                System.out.println("구글 로그인 성공: " + dto.getUserid());
                response.getWriter().print("success");
            } else {
                System.out.println("구글 로그인 실패: dto가 null입니다.");
                response.getWriter().print("error: 로그인 처리 실패 - 회원 정보를 찾을 수 없습니다.");
            }
            
        } catch (Exception e) {
            System.out.println("구글 로그인 예외 발생: " + e.getMessage());
            e.printStackTrace();
            response.getWriter().print("error: " + e.getMessage());
        }
    }
    
    // Google ID 토큰 검증 및 사용자 정보 가져오기
    private String verifyGoogleToken(String credential) {
        try {
            // Google의 토큰 검증 엔드포인트 호출
            String urlString = "https://oauth2.googleapis.com/tokeninfo?id_token=" + credential;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                Scanner scanner = new Scanner(new InputStreamReader(inputStream, "UTF-8"));
                String response = scanner.useDelimiter("\\A").next();
                scanner.close();
                
                // CLIENT_ID 검증
                JSONObject jsonObject = new JSONObject(response);
                String aud = jsonObject.getString("aud");
                
                if (!aud.equals(CLIENT_ID)) {
                    System.out.println("CLIENT_ID 불일치");
                    return null;
                }
                
                return response;
            } else {
                System.out.println("토큰 검증 실패: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}




