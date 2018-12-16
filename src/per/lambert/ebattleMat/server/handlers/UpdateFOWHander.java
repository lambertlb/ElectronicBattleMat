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

public class UpdateFOWHander implements IWebRequestHandler {

	public class FogOfWarData {
		public boolean fogOfWar[][];
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse resp, HttpServlet servlet, String jsonData) throws ServletException, IOException {
		String sessionUUID = request.getParameter("sessionUUID");
		int currentLevel = Integer.parseInt(request.getParameter("currentLevel"));
		Gson gson = new Gson();
		FogOfWarData fowData = gson.fromJson(jsonData, FogOfWarData.class);
		DungeonsManager.updateFOW(servlet, sessionUUID, currentLevel, fowData.fogOfWar);
		PrintWriter out = resp.getWriter();
		out.print("");
		out.flush();
	}

}
