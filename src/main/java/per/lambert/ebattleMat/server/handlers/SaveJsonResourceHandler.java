package per.lambert.ebattleMat.server.handlers;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.lambert.ebattleMat.server.DungeonsManager;
import per.lambert.ebattleMat.server.IWebRequestHandler;

/**
 * Handler for saving pog resource.
 * @author LLambert
 *
 */
public class SaveJsonResourceHandler implements IWebRequestHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(final HttpServletRequest request, final HttpServletResponse resp, final HttpServlet servlet, final String jsonData) throws ServletException, IOException {
		String resourceName = request.getParameter("resourceName");
		URL servletPath = servlet.getServletContext().getResource("/");
		String directory = servletPath.getPath() + DungeonsManager.RESOURCE_LOCATION + "/";
		String resourcePath;
		if (resourceName.equalsIgnoreCase("monsters")) {
			resourcePath = "monsters/pogs.json";
		} else {
			resourcePath = "roomObjects/pogs.json";
		}
		String filePath = directory + resourcePath;
		DungeonsManager.saveJsonFile(jsonData, filePath);
	}

}
