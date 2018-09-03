package per.lambert.ebattleMat.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

/**
 * interface that all web requests must inherit from
 * 
 * @author LLambert
 * 
 */
public interface IWebRequestHandler {
	/**
	 * Handle a web request
	 * 
	 * @param request
	 *            request
	 * @param resp
	 *            response
	 * @param servlet
	 *            servlet
	 * @param parameters
	 *            parameters
	 * @throws ServletException
	 *             exception
	 * @throws IOException
	 *             IOException
	 */
	void handleRequest(HttpServletRequest request, HttpServletResponse resp, HttpServlet servlet,
			Document dom, String fileContents) throws ServletException, IOException;
}
