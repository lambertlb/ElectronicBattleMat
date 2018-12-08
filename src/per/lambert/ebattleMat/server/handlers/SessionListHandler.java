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

public class SessionListHandler implements IWebRequestHandler{

	public class SessionListResponseData {
		public String dungeonUUID;
		public String[] sessionNames;
		public String[] sessionDirectories;
		public SessionListResponseData(Map<String,String> dungeonListData, String dungeonUUID) {
			this.dungeonUUID = dungeonUUID;
			sessionNames = new String[dungeonListData.size()];
			sessionDirectories = new String[dungeonListData.size()];
			int i = 0;
			for (Map.Entry<String, String> entry : dungeonListData.entrySet()) {
				sessionNames[i] = entry.getKey();
				sessionDirectories[i] =  entry.getValue();
				++i;
			}
		}
	}
	
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse resp, HttpServlet servlet, String jsonData) throws ServletException, IOException {
		String dungeonUUID = request.getParameter("dungeonUUID");
		SessionListResponseData sessionListResponseData = new SessionListResponseData(DungeonsManager.getSessionListData(servlet, dungeonUUID), dungeonUUID);
		Gson gson = new Gson();
		String responseDataString = gson.toJson(sessionListResponseData);

		PrintWriter out = resp.getWriter();
		out.print(responseDataString);
		out.flush();
	}

}
