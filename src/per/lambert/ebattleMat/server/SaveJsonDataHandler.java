package per.lambert.ebattleMat.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveJsonDataHandler implements IWebRequestHandler {

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse resp, HttpServlet servlet,
			String jsonData) throws ServletException, IOException {
			DungeonsManager.saveDungeonData(request, servlet, jsonData);
	}

}
