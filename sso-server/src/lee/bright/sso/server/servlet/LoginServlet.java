package lee.bright.sso.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lee.bright.sso.server.config.UserInfoConfig;
import lee.bright.sso.server.model.UserInfo;

/**
 * @author Bright Lee
 */
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		if (username != null) {
			username = username.trim();
		}
		if (username == null || username.length() == 0) {
			request.setAttribute("errorMessage", "Username is necessary.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		String password = request.getParameter("password");
		if (password == null) {
			request.setAttribute("errorMessage", "Password is necessary.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		UserInfo userInfo = UserInfoConfig.getInstance().getUserInfoByUsername(username);
		if (userInfo == null) {
			request.setAttribute("errorMessage", "Username not exists.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		if (!password.equals(userInfo.getPassword())) {
			request.setAttribute("errorMessage", "Password is wrong.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		String url = request.getParameter("url");
		String token = UserInfoConfig.getInstance().generateToken(userInfo);
		url = getUrl(url, token);
		response.sendRedirect(url);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String getUrl(String url, String token) {
		if (url.contains("?")) {
			url = url + "&token=" + token;
		} else {
			url = url + "?token=" + token;
		}
		return url;
	}

}