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

public class DungeonListHandler implements IWebRequestHandler{

	public class DungeonListResponseData {
		public String[] dungeonNames;
		public String[] dungeonDirectories;
		public DungeonListResponseData(Map<String,String> dungeonListData) {
			dungeonNames = new String[dungeonListData.size()];
			dungeonDirectories = new String[dungeonListData.size()];
			int i = 0;
			for (Map.Entry<String, String> entry : dungeonListData.entrySet()) {
				dungeonNames[i] = entry.getKey();
				dungeonDirectories[i] =  entry.getValue();
				++i;
			}
		}
	}
	
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse resp, HttpServlet servlet, String jsonData) throws ServletException, IOException {
		DungeonListResponseData dungeonListResponseData = new DungeonListResponseData(DungeonsManager.getDungeonListData(servlet));
		Gson gson = new Gson();
		String responseDataString = gson.toJson(dungeonListResponseData);

		PrintWriter out = resp.getWriter();
		out.print(responseDataString);
		out.flush();
	}

}
