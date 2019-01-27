package per.lambert.ebattleMat.server.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import per.lambert.ebattleMat.server.DungeonsManager;
import per.lambert.ebattleMat.server.IWebRequestHandler;

/**
 * Handler for getting a dungeon list.
 * @author LLambert
 *
 */
public class DungeonListHandler implements IWebRequestHandler {

	/**
	 * Response for getting a dungeon list.
	 * @author LLambert
	 *
	 */
	public class DungeonListResponseData {
		/**
		 * array of dungeon names.
		 */
		private String[] dungeonNames;
		/**
		 * array of uuids.
		 */
		private String[] dungeonUUIDS;
		/**
		 * array of directories the dungeons are in.
		 */
		private String[] dungeonDirectories;
		/**
		 * path to server resources.
		 */
		@SuppressWarnings("unused")
		private String serverPath;
		/**
		 * Handle the request.
		 * @param dungeonListData map of dungeons and UUIDS
		 * @param dungeonDirectoryData map of dungeon and directory names
		 * @param serverPath path to server resources
		 */
		public DungeonListResponseData(final Map<String,String> dungeonListData, final Map<String,String> dungeonDirectoryData, final String serverPath) {
			this.serverPath = serverPath;
			dungeonNames = new String[dungeonListData.size()];
			dungeonUUIDS = new String[dungeonListData.size()];
			dungeonDirectories = new String[dungeonListData.size()];
			int i = 0;
			for (Map.Entry<String, String> entry : dungeonListData.entrySet()) {
				dungeonNames[i] = entry.getValue();
				dungeonUUIDS[i] =  entry.getKey();
				++i;
			}
			i = 0;
			for (Map.Entry<String, String> entry : dungeonDirectoryData.entrySet()) {
				dungeonDirectories[i] = entry.getValue();
				++i;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(final HttpServletRequest request, final HttpServletResponse resp, final HttpServlet servlet, final String jsonData) throws ServletException, IOException {
		DungeonsManager.getDungeonListData(servlet);
		URL servletPath = servlet.getServletContext().getResource("/");
		DungeonListResponseData dungeonListResponseData = new DungeonListResponseData(DungeonsManager.getDungeonNameToUUIDMap(), DungeonsManager.getUuidTemplatePathMap(), servletPath.getPath());
		Gson gson = new Gson();
		String responseDataString = gson.toJson(dungeonListResponseData);

		PrintWriter out = resp.getWriter();
		out.print(responseDataString);
		out.flush();
	}

}
