package per.lambert.ebattleMat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet Utilities
 * 
 * @author LLambert
 * 
 */
public final class ServletUtils {
	/**
	 * Hide constructor
	 */
	private ServletUtils() {

	}

	/**
	 * Build a map of request parameters
	 * 
	 * @param request
	 *            request
	 * @return Map of parameters
	 */
	public static Map<String, String> getParameters(final HttpServletRequest request) {
		Map<String, String> parameters = new HashMap<String, String>();
		@SuppressWarnings("rawtypes")
		Enumeration values = request.getParameterNames();
		while (values.hasMoreElements()) {
			String name = (String) values.nextElement();
			String value = request.getParameterValues(name)[0];
			parameters.put(name, value);
		}
		return (parameters);
	}

	/**
	 * Common method for distributing web requests
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param webServices
	 *            map of supported services
	 * @param servlet
	 *            servlet making the request
	 * @throws ServletException
	 *             exception
	 * @throws IOException
	 *             exception
	 */
	public static void handleGetRequest(final HttpServletRequest request, final HttpServletResponse response,
			final Map<String, IWebRequestHandler> webServices, final HttpServlet servlet)
			throws ServletException, IOException {
	}

	public static void handlePostRequest(final HttpServletRequest request, final HttpServletResponse response,
			final Map<String, IWebRequestHandler> webServices, final HttpServlet servlet)
			throws ServletException, IOException {
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			/* report an error */
		}

		Gson gson = new Gson();
		ServiceRequestData requestData = gson.fromJson(jb.toString(), ServiceRequestData.class);

		String command = requestData.getServiceRequest();
		if (command == null) {
			throw new ServletException();
		}
		if (!command.equalsIgnoreCase("LOGIN")) {
			validateToken(requestData);
		}
		IWebRequestHandler handler = webServices.get(command);
		if (handler != null) {
			handler.handleRequest(request, response, servlet, jb.toString());
			return;
		}
	}

	private static void validateToken(ServiceRequestData requestData) throws ServletException {
		if (requestData.getToken() == 0) {
			throw new ServletException();
		}
	}

	/**
	 * MAke sure legal user
	 * 
	 * @param token
	 *            user token
	 * @throws ServletException
	 *             exception
	 */
	public static void validateUser(final String token) throws ServletException {
		if (token == null || token.isEmpty()) {
			throw new ServletException();
		}
	}
}
