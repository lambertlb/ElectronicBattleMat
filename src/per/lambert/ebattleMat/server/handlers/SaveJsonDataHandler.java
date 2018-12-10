package per.lambert.ebattleMat.server.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.lambert.ebattleMat.server.DungeonsManager;
import per.lambert.ebattleMat.server.IWebRequestHandler;

public class SaveJsonDataHandler implements IWebRequestHandler {

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse resp, HttpServlet servlet, String jsonData) throws ServletException, IOException {
		String sessionUUID = request.getParameter("sessionUUID");
		if (sessionUUID == null) {
			DungeonsManager.saveDungeonData(request, servlet, jsonData);
		} else {
			DungeonsManager.saveSessionDataData(request, servlet, jsonData, sessionUUID);
		}
	}

}
