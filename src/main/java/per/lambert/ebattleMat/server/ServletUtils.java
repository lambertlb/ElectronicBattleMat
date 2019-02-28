package per.lambert.ebattleMat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Utilities.
 * 
 * @author LLambert
 * 
 */
public final class ServletUtils {
	/**
	 * Hide constructor.
	 */
	private ServletUtils() {
	}

	/**
	 * Handle a service request.
	 * @param request to process
	 * @param response to return
	 * @param webServices handler to call
	 * @param servlet servlet data
	 * @throws ServletException if error
	 * @throws IOException if error
	 */
	public static void handlePostRequest(final HttpServletRequest request, final HttpServletResponse response, final Map<String, IWebRequestHandler> webServices, final HttpServlet servlet) throws ServletException, IOException {
		String contentType = request.getContentType();
		StringBuffer jb = new StringBuffer();
		if (contentType.startsWith("text")) {
			String line = null;
			try {
				BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null) {
					jb.append(line);
				}
			} catch (Exception e) {
				throw new ServletException();
			}
		}

		String command = request.getParameter("request");
		if (command == null) {
			throw new ServletException();
		}
		if (!command.equalsIgnoreCase("LOGIN")) {
			validateToken(request);
		}
		IWebRequestHandler handler = webServices.get(command);
		if (handler != null) {
			handler.handleRequest(request, response, servlet, jb.toString());
			return;
		}
	}

	/**
	 * Validate user token.
	 * 
	 * TODO finish validation
	 * @param request with token
	 * @throws ServletException if error
	 */
	private static void validateToken(final HttpServletRequest request) throws ServletException {
		int token = Integer.parseUnsignedInt(request.getParameter("token"));
		if (token == 0) {
			throw new ServletException();
		}
	}
}
