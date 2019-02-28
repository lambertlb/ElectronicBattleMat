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
 * Load data from JSON string.
 * @author LLambert
 *
 */
public class LoadJsonDataHandler implements IWebRequestHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(final HttpServletRequest request, final HttpServletResponse resp, final HttpServlet servlet, final String jsonData) throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		String dungeonUUID = request.getParameter("dungeonUUID");
		PrintWriter out = resp.getWriter();
		if (dungeonUUID != null) {
			out.print(DungeonsManager.getDungeonDataAsString(servlet, dungeonUUID));

		} else {
			out.print(DungeonsManager.getFileAsString(servlet, fileName));
		}
		out.flush();
	}

}
