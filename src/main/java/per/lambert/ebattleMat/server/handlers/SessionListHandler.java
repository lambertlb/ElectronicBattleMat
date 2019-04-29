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

import java.util.Map;

/**
 * Handler for session list request.
 * @author LLambert
 *
 */
public class SessionListHandler implements IWebRequestHandler {
	/**
	 * response data for session list.
	 * @author LLambert
	 *
	 */
	public class SessionListResponseData {
		/**
		 * dungeon UUID.
		 */
		@SuppressWarnings("unused")
		private String dungeonUUID;
		/**
		 * Session names.
		 */
		private String[] sessionNames;
		/**
		 * UUID for session names.
		 */
		private String[] sessionUUIDs;
		/**
		 * Constructor.
		 * @param sessionListData collection of session information
		 * @param dungeonUUID for session
		 */
		public SessionListResponseData(final Map<String,String> sessionListData, final String dungeonUUID) {
			this.dungeonUUID = dungeonUUID;
			sessionNames = new String[sessionListData.size()];
			sessionUUIDs = new String[sessionListData.size()];
			int i = 0;
			for (Map.Entry<String, String> entry : sessionListData.entrySet()) {
				sessionNames[i] = entry.getKey();
				sessionUUIDs[i] =  entry.getValue();
				++i;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(final HttpServletRequest request, final HttpServletResponse resp, final HttpServlet servlet, final String jsonData) throws ServletException, IOException {
		String dungeonUUID = request.getParameter("dungeonUUID");
		SessionListResponseData sessionListResponseData = new SessionListResponseData(DungeonsManager.getSessionListData(servlet, dungeonUUID), dungeonUUID);
		Gson gson = new Gson();
		String responseDataString = gson.toJson(sessionListResponseData);

		PrintWriter out = resp.getWriter();
		out.print(responseDataString);
		out.flush();
	}

}
