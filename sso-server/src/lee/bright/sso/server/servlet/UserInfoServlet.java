package lee.bright.sso.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import lee.bright.sso.server.config.UserInfoConfig;
import lee.bright.sso.server.model.UserInfo;

/**
 * @author Bright Lee
 */
public class UserInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public UserInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		UserInfo userInfo = UserInfoConfig.getInstance().getUserInfoByToken(token);
		if (userInfo == null) {
			response.getWriter().write("{}");
		} else {
			JSONObject json = JSONObject.fromObject(userInfo);
			response.getWriter().write(json.toString());
		}
		response.getWriter().flush();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
