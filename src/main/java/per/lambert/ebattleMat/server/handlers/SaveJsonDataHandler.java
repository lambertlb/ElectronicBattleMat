package per.lambert.ebattleMat.server.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.lambert.ebattleMat.server.DungeonsManager;
import per.lambert.ebattleMat.server.IWebRequestHandler;

/**
 * Handler for saving JSON data.
 * @author LLambert
 *
 */
public class SaveJsonDataHandler implements IWebRequestHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(final HttpServletRequest request, final HttpServletResponse resp, final HttpServlet servlet, final String jsonData) throws ServletException, IOException {
		String sessionUUID = request.getParameter("sessionUUID");
		if (sessionUUID == null) {
			DungeonsManager.saveDungeonData(request, servlet, jsonData);
		} else {
			DungeonsManager.saveSessionData(request, servlet, jsonData, sessionUUID);
		}
	}

}
