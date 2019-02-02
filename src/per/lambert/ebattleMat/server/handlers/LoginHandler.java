package per.lambert.ebattleMat.server.handlers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import per.lambert.ebattleMat.server.IWebRequestHandler;
import per.lambert.ebattleMat.server.serviceData.ServiceResponseData;

/**
 * Login handler.
 * 
 * @author LLambert
 *
 */
public class LoginHandler implements IWebRequestHandler {

	/**
	 * Response data for login.
	 * @author LLambert
	 *
	 */
	public class LoginResponseData extends ServiceResponseData {
		/**
		 * Token for user.
		 */
		private int token;

		/**
		 * get token.
		 * @return token
		 */
		public int getToken() {
			return token;
		}

		/**
		 * Set token.
		 * @param token to set
		 */
		public void setToken(final int token) {
			this.token = token;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void handleRequest(final HttpServletRequest request, final HttpServletResponse resp,
			final HttpServlet servlet, final String jsonData) throws ServletException, IOException {
		LoginResponseData responseData = new LoginResponseData();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username == null || username == "" || password == null || password == "") {
			responseData.setError(1);
		} else {
			responseData.setToken(username.hashCode());
		}
		Gson gson = new Gson();
		String responseDataString = gson.toJson(responseData);

		PrintWriter out = resp.getWriter();
		out.print(responseDataString);
		out.flush();

	}

}
