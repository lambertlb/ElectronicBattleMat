package per.lambert.ebattleMat.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

/**
 * Login handler
 * 
 * @author LLambert
 *
 */
public class LoginHandler implements IWebRequestHandler {

	private ResponseUtils responder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.honeywell.prototypes.server.interfaces.IWebRequestHandler#handleRequest
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.http.HttpServlet,
	 * java.util.Map)
	 */
	@Override	public final void handleRequest(final HttpServletRequest request, final HttpServletResponse resp,
			final HttpServlet servlet, final Document dom,final String fileContents) throws ServletException {
		if(responder == null) {
			responder = new ResponseUtils("loginHandler", servlet);
		}
		try {
			PrintWriter out = resp.getWriter();
			out.print(responder.getNextResponse());
			out.flush();
		} catch (IOException ie) {
			throw new ServletException();
		}
	}

}


