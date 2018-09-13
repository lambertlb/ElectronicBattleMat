package per.lambert.ebattleMat.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import per.lambert.ebattleMat.client.services.serviceData.ServiceRequestData;

/**
 * Login handler
 * 
 * @author LLambert
 *
 */
public class LoginHandler implements IWebRequestHandler {
	public class LoginRequestData extends ServiceRequestData {
		public LoginRequestData() {
		}

		private String username;

		public final void setUsername(String username) {
			this.username = username;
		};

		public final String getUsername() {
			return this.username;
		};

		private String password;

		public final void setPassword(String password) {
			this.password = password;
		};

		public final String setPassword() {
			return this.password;
		};

	}

	@Override
	public final void handleRequest(final HttpServletRequest request, final HttpServletResponse resp,
			final HttpServlet servlet, final String jsonData) throws ServletException {
		Gson gson = new Gson();
		LoginRequestData requestData = gson.fromJson(jsonData, LoginRequestData.class);

		/*
		 * PrintWriter out = resp.getWriter(); out.print(responder.getNextResponse());
		 * out.flush();
		 */
	}

}
