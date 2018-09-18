package per.lambert.ebattleMat.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class DungeonListHandler implements IWebRequestHandler {
	public class DungeonListResponseData extends ServiceResponseData {
		private String[] dungeons;

		public String[] getDungeons() {
			return dungeons;
		}

		public void setDungeons(String[] dungeons) {
			this.dungeons = dungeons;
		}
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse resp, HttpServlet servlet,
			String jsonData) throws ServletException, IOException {
		DungeonListResponseData responseData = new DungeonListResponseData();
		responseData.setDungeons(new String[] { "Dungeon1", "Dungeon2", "Dungeon3" });
		Gson gson = new Gson();
		String responseDataString = gson.toJson(responseData);

		PrintWriter out = resp.getWriter();
		out.print(responseDataString);
		out.flush();

	}

}
