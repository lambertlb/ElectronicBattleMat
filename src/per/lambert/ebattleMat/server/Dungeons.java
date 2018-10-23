package per.lambert.ebattleMat.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dungeons extends HttpServlet {
	/**
	 * Constructor
	 */
	public Dungeons() {
		webServices = new HashMap<String, IWebRequestHandler>();
		webServices.put("LOGIN", new LoginHandler());
		webServices.put("DUNGEONLIST", new DungeonListHandler());
		webServices.put("DUNGEONDATA", new DungeonDataHandler());
		webServices.put("SAVEDUNGEONDATA", new SaveDungeonDataHandler());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtils.handleGetRequest(request, response, webServices, this);
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
