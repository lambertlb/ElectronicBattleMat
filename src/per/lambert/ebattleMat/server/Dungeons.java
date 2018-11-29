package per.lambert.ebattleMat.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.lambert.ebattleMat.server.handlers.CreateNewDungeonHandler;
import per.lambert.ebattleMat.server.handlers.DeleteDungeonHandler;
import per.lambert.ebattleMat.server.handlers.DungeonListHandler;
import per.lambert.ebattleMat.server.handlers.LoadJsonDataHandler;
import per.lambert.ebattleMat.server.handlers.LoginHandler;
import per.lambert.ebattleMat.server.handlers.SaveJsonDataHandler;
import per.lambert.ebattleMat.server.handlers.SessionListHandler;

public class Dungeons extends HttpServlet {
	/**
	 * Constructor
	 */
	public Dungeons() {
		webServices = new HashMap<String, IWebRequestHandler>();
		webServices.put("LOGIN", new LoginHandler());
		webServices.put("LOADJSONFILE", new LoadJsonDataHandler());
		webServices.put("SAVEJSONFILE", new SaveJsonDataHandler());
		webServices.put("GETDUNGEONLIST", new DungeonListHandler());
		webServices.put("CREATENEWDUNGEON", new CreateNewDungeonHandler());
		webServices.put("DELETEDUNGEON", new DeleteDungeonHandler());
		webServices.put("GETSESSIONLIST", new SessionListHandler());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// first, set the "content type" header of the response
		response.setContentType("application/xml");
		ServletUtils.handlePostRequest(request, response, webServices, this);
	}

	private static final long serialVersionUID = 1L;
	private Map<String, IWebRequestHandler> webServices;

}
