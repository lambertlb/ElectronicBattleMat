package per.lambert.ebattleMat.server;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.lambert.ebattleMat.server.handlers.CreateNewDungeonHandler;
import per.lambert.ebattleMat.server.handlers.CreateNewSessionHandler;
import per.lambert.ebattleMat.server.handlers.DeleteDungeonHandler;
import per.lambert.ebattleMat.server.handlers.DeleteSessionHandler;
import per.lambert.ebattleMat.server.handlers.DungeonListHandler;
import per.lambert.ebattleMat.server.handlers.FileUploadHandler;
import per.lambert.ebattleMat.server.handlers.LoadJsonDataHandler;
import per.lambert.ebattleMat.server.handlers.LoadSessionHandler;
import per.lambert.ebattleMat.server.handlers.LoginHandler;
import per.lambert.ebattleMat.server.handlers.SaveJsonDataHandler;
import per.lambert.ebattleMat.server.handlers.SaveJsonResourceHandler;
import per.lambert.ebattleMat.server.handlers.SavePogHandler;
import per.lambert.ebattleMat.server.handlers.SessionListHandler;
import per.lambert.ebattleMat.server.handlers.UpdateFOWHander;

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
		webServices.put("CREATENEWSESSION", new CreateNewSessionHandler());
		webServices.put("DELETESESSION", new DeleteSessionHandler());
		webServices.put("LOADSESSION", new LoadSessionHandler());
		webServices.put("SAVEPOGTOSESSION", new SavePogHandler());
		webServices.put("UPDATEFOW", new UpdateFOWHander());
		webServices.put("FILEUPLOAD", new FileUploadHandler());
		webServices.put("SAVEJSONRESOURCE", new SaveJsonResourceHandler());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// first, set the "content type" header of the response
		response.setContentType("application/xml");
		ServletUtils.handlePostRequest(request, response, webServices, this);
	}

	private static final long serialVersionUID = 1L;
	private Map<String, IWebRequestHandler> webServices;

}
