package per.lambert.ebattleMat.server.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import per.lambert.ebattleMat.server.Dungeons;
import per.lambert.ebattleMat.server.DungeonsManager;
import per.lambert.ebattleMat.server.IWebRequestHandler;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DungeonListHandler implements IWebRequestHandler{

	public class DungeonListResponseData {
		public String[] dungeonNames;
		public String[] dungeonUUIDS;
		public String[] dungeonDirectories;
		public String serverPath;
		public DungeonListResponseData(Map<String,String> dungeonListData, Map<String,String> dungeonDirectoryData, String serverPath) {
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
	
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse resp, HttpServlet servlet, String jsonData) throws ServletException, IOException {
		Dungeons.logToFile("Start DungeonListHandler");
		DungeonsManager.getDungeonListData(servlet);
		URL servletPath = servlet.getServletContext().getResource("/");
		Dungeons.logToFile("Convert DungeonListResponseData");
		DungeonListResponseData dungeonListResponseData = new DungeonListResponseData(DungeonsManager.getDungeonNameToUUIDMap(), DungeonsManager.getUuidTemplatePathMap(), servletPath.getPath());
		Gson gson = new Gson();
		Dungeons.logToFile("Convert DungeonListResponseData to String");
		String responseDataString = gson.toJson(dungeonListResponseData);
		Dungeons.logToFile("DungeonListResponseData = " + responseDataString);

		PrintWriter out = resp.getWriter();
		out.print(responseDataString);
		out.flush();
	}

}
