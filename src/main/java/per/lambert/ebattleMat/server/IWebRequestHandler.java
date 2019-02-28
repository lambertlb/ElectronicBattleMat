package per.lambert.ebattleMat.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * interface that all web requests must inherit from.
 * 
 * @author LLambert
 * 
 */
public interface IWebRequestHandler {
	/**
	 * Handle request.
	 * @param request to handle
	 * @param response servlet response
	 * @param servlet servlet data
	 * @param jsonData JSON Data
	 * @throws ServletException Servlet Exception
	 * @throws IOException if there is an error
	 */
	void handleRequest(HttpServletRequest request, HttpServletResponse response, HttpServlet servlet, String jsonData)
			throws ServletException, IOException;
}
