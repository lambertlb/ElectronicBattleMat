package per.lambert.ebattleMat.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateNewDungeonHandler implements IWebRequestHandler {

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse resp, HttpServlet servlet, String jsonData) throws ServletException, IOException {
		String templateName = request.getParameter("templateName");
		String newDungeonName = request.getParameter("newDungeonName");
		DungeonsManager.copyDungeon(servlet, templateName, newDungeonName);
		PrintWriter out = resp.getWriter();
		out.print("");
		out.flush();
	}
}
