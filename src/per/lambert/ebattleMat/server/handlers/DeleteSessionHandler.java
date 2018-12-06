package per.lambert.ebattleMat.server.handlers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.lambert.ebattleMat.server.DungeonsManager;
import per.lambert.ebattleMat.server.IWebRequestHandler;

public class DeleteSessionHandler implements IWebRequestHandler {

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse resp, HttpServlet servlet, String jsonData) throws ServletException, IOException {
		String templateName = request.getParameter("templateName");
		String sessionName = request.getParameter("sessionName");
		PrintWriter out = resp.getWriter();
		DungeonsManager.deleteSession(servlet, templateName, sessionName);
		out.print("");
		out.flush();
	}

}
