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
 * Handler for creating a new session for a dungeon.
 * @author LLambert
 *
 */
public class CreateNewSessionHandler implements IWebRequestHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(final HttpServletRequest request, final HttpServletResponse resp, final HttpServlet servlet, final String jsonData) throws ServletException, IOException {
		String dungeonUUID = request.getParameter("dungeonUUID");
		String newSessionName = request.getParameter("newSessionName");
		DungeonsManager.createSession(servlet, dungeonUUID, newSessionName);
		PrintWriter out = resp.getWriter();
		out.print("");
		out.flush();
	}

}
