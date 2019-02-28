package per.lambert.ebattleMat.server.handlers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.lambert.ebattleMat.server.DungeonsManager;
import per.lambert.ebattleMat.server.IWebRequestHandler;

/**
 * Load session data to client.
 * @author LLambert
 *
 */
public class LoadSessionHandler implements IWebRequestHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(final HttpServletRequest request, final HttpServletResponse resp, final HttpServlet servlet, final String jsonData) throws ServletException, IOException {
		String dungeonUUID = request.getParameter("dungeonUUID");
		String sessionUUID = request.getParameter("sessionUUID");
		int version = Integer.parseInt(request.getParameter("version"));
		String sessionJson = DungeonsManager.getSessionDataAsString(servlet, dungeonUUID, sessionUUID, version);
		PrintWriter out = resp.getWriter();
		out.print(sessionJson);
		out.flush();
	}

}
