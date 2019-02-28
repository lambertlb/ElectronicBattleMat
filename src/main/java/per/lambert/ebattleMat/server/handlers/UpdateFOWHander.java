package per.lambert.ebattleMat.server.handlers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import per.lambert.ebattleMat.server.DungeonsManager;
import per.lambert.ebattleMat.server.IWebRequestHandler;

/**
 * Handle update fog of war reuqest.
 * @author LLambert
 *
 */
public class UpdateFOWHander implements IWebRequestHandler {

	/**
	 * Worker class for converting fog of war JSON data.
	 * @author LLambert
	 *
	 */
	public class FogOfWarData {
		/**
		 * Fog of war data.
		 */
		private boolean[][] fogOfWar;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(final HttpServletRequest request, final HttpServletResponse resp, final HttpServlet servlet, final String jsonData) throws ServletException, IOException {
		String sessionUUID = request.getParameter("sessionUUID");
		int currentLevel = Integer.parseInt(request.getParameter("currentLevel"));
		Gson gson = new Gson();
		FogOfWarData fowData = gson.fromJson(jsonData, FogOfWarData.class);
		DungeonsManager.updateFOW(servlet, sessionUUID, currentLevel, fowData.fogOfWar);
		PrintWriter out = resp.getWriter();
		out.print("");
		out.flush();
	}

}
