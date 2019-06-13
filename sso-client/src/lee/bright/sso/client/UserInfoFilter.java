package lee.bright.sso.client;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author Bright Lee
 */
public class UserInfoFilter implements Filter {
	
	private static final String SSO_LOGIN_PAGE_URL = "http://127.0.0.1:8080/sso-server/login.jsp?url=http://127.0.0.1:7005/sso-client";

	public UserInfoFilter() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		Object sessionToken = session.getAttribute("token");
		if (sessionToken != null) {
			chain.doFilter(request, response);
			return;
		}
		String token = request.getParameter("token");
		if (token == null) {
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			httpServletResponse.sendRedirect(SSO_LOGIN_PAGE_URL);
			return;
		}
		String userInfo;
		try {
			userInfo = loadUserInfo(token);
		} catch (Exception e) {
			userInfo = "{}";
		}
		JSONObject userInfoJson = null;
		try {
			userInfoJson = JSONObject.fromObject(userInfo);
			if (userInfoJson.containsKey("username")) {
				session.setAttribute("token", token);
				chain.doFilter(request, response);
				return;
			} else {
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				httpServletResponse.sendRedirect(SSO_LOGIN_PAGE_URL);
				return;
			}
		} catch (Exception e) {
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			httpServletResponse.sendRedirect(SSO_LOGIN_PAGE_URL);
			return;
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}
	
	private String loadUserInfo(String token) throws Exception {
		String url = "http://127.0.0.1:8080/sso-server/userInfo?token=" + token;
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-type" , "text/html; charset=utf-8");
        CloseableHttpClient client = HttpClients.custom().build();
        CloseableHttpResponse httpResponse = client.execute(httpGet);
        String result = null;
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
        	result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        }
        httpResponse.close();
        client.close();
		return result;
	}

}