package per.lambert.ebattleMat.server.handlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import per.lambert.ebattleMat.server.IWebRequestHandler;

/**
 * File upload handler.
 * @author LLambert
 *
 */
public class FileUploadHandler implements IWebRequestHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(final HttpServletRequest request, final HttpServletResponse response, final HttpServlet servlet, final String jsonData) throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload();
		response.setContentType("text/plain");
		String filePath = request.getParameter("filePath");
		URL servletPath = servlet.getServletContext().getResource("/");
		String serverPath = servletPath.getPath();
		FileOutputStream fileOutSt = null;
		try {
			FileItemIterator iter = upload.getItemIterator(request);

			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				InputStream stream = item.openStream();

				String name = serverPath + filePath;
				File file = new File(name);
				file.delete();
				fileOutSt = new FileOutputStream(name);
				int len;
				byte[] buffer = new byte[8192];
				while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
					fileOutSt.write(buffer, 0, len);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (fileOutSt != null) {
				fileOutSt.close();
			}
		}

	}

}
