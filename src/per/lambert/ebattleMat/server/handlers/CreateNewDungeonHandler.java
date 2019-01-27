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
 * Handler for creating a new dungeon.
 * It expects two html parameters as follows.
 * dungeonUUID is the template dungeon that gets copied and renamed.
 * newDungeonName is the name of the new dungeon
 * @author LLambert
 *
 */
public class CreateNewDungeonHandler implements IWebRequestHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(final HttpServletRequest request, final HttpServletResponse resp, final HttpServlet servlet, final String jsonData) throws ServletException, IOException {
		String dungeonUUID = request.getParameter("dungeonUUID");
		String newDungeonName = request.getParameter("newDungeonName");
		DungeonsManager.copyDungeon(servlet, dungeonUUID, newDungeonName);
		PrintWriter out = resp.getWriter();
		out.print("");
		out.flush();
	}
}
