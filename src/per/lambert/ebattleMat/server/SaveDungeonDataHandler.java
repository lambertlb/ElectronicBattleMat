package per.lambert.ebattleMat.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class SaveDungeonDataHandler implements IWebRequestHandler {
	public class SaveDungeonRequestData extends ServiceRequestData {
		private DungeonData dungeonData;

		public DungeonData getDungeonData() {
			return dungeonData;
		}

		public void setDungeonData(DungeonData dungeonData) {
			this.dungeonData = dungeonData;
		}
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse resp, HttpServlet servlet,
			String jsonData) throws ServletException, IOException {
		Gson gson = new Gson();
		ServiceResponseData serviceResponseData = new ServiceResponseData();
		try {
			SaveDungeonRequestData requestData = gson.fromJson(jsonData, SaveDungeonRequestData.class);
			DungeonsManager.saveDungeonData(servlet, requestData.getDungeonData());
		} catch (Exception ex) {
			serviceResponseData.setError(-1);
		}
		String responseDataString = gson.toJson(serviceResponseData);
		PrintWriter out = resp.getWriter();
		out.print(responseDataString);
		out.flush();
	}

}
