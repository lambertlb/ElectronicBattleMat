package per.lambert.ebattleMat.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteDungeonHandler implements IWebRequestHandler{

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse resp, HttpServlet servlet, String jsonData) throws ServletException, IOException {
		String templateName = request.getParameter("templateName");
		PrintWriter out = resp.getWriter();
		DungeonsManager.deleteDungeon(servlet, templateName);
		out.print("");
		out.flush();
	}

}
