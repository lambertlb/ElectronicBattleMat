package per.lambert.ebattleMat.server.handlers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.lambert.ebattleMat.server.DungeonsManager;
import per.lambert.ebattleMat.server.IWebRequestHandler;

/**
 * Delete file on server.
 * @author llambert
 *
 */
public class DeleteFile implements IWebRequestHandler {

	/**
	 * Delete file on server.
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(final HttpServletRequest request, final HttpServletResponse response, final HttpServlet servlet, final String jsonData) throws ServletException, IOException {
		DungeonsManager.deleteFile(request, request.getParameter("path"));
		PrintWriter out = response.getWriter();
		out.print("");
		out.flush();
	}

}
