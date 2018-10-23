package per.lambert.ebattleMat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class DungeonDataHandler implements IWebRequestHandler {
	public class DungeonDataResponseData extends ServiceResponseData {
		private List<DungeonData> dungeonData;

		public void setDungeonData(List<DungeonData> dungeonData) {
			this.dungeonData = dungeonData;
		}

		public List<DungeonData> getDungeons() {
			return dungeonData;
		}

	}
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse resp, HttpServlet servlet,
			String jsonData) throws ServletException, IOException {
		Gson gson = new Gson();
		DungeonDataResponseData dungeonDataResponseData = new DungeonDataResponseData();
		dungeonDataResponseData.setDungeonData(DungeonsManager.getDungeonData(servlet));
		String responseDataString = gson.toJson(dungeonDataResponseData);

		PrintWriter out = resp.getWriter();
		out.print(responseDataString);
		out.flush();
	}

}
