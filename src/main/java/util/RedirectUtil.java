package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * [리다이렉트 유틸리티]
 * jslhrd가 ROOT(/)로 설정되어 있어서 hometraining으로 강제 리다이렉트
 */
public class RedirectUtil {
	
	public static void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		// 컨텍스트 패스 사용
		String contextPath = request.getContextPath();
		if(contextPath == null || contextPath.isEmpty()) {
			contextPath = "/hometraining";
		}
		response.sendRedirect(contextPath + path);
	}
}
