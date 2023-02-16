package per.lambert.ebattleMat.server.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import per.lambert.ebattleMat.client.interfaces.Constants;
import per.lambert.ebattleMat.server.IWebRequestHandler;

/**
 * Used to get a list of files in a server folder.
 * 
 * @author llambert
 *
 */
public class FileLister implements IWebRequestHandler {
	/**
	 * Response for getting a dungeon list.
	 * 
	 * @author LLambert
	 *
	 */
	public class FileListResponse {
		/**
		 * Folder path of files.
		 */
		private String filePath;

		/**
		 * Get file path.
		 * 
		 * @return file path
		 */
		public String getFilePath() {
			return (filePath);
		}

		/**
		 * array of dungeon names.
		 */
		private String[] fileNames;

		/**
		 * get File names.
		 * 
		 * @return file names
		 */
		public String[] getFileNames() {
			return (fileNames);
		}

		/**
		 * Set filenames.
		 * 
		 * @param fileNames
		 */
		public void setFilenames(final String[] fileNames) {
			this.fileNames = fileNames;
		}

		/**
		 * Handle the request.
		 * 
		 * @param filePath path of files
		 */
		public FileListResponse(final String filePath) {
			fileNames = null;
			this.filePath = filePath;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(final HttpServletRequest request, final HttpServletResponse resp, final HttpServlet servlet, final String jsonData) throws ServletException, IOException {
		URL servletPath = servlet.getServletContext().getResource("/");
		String folderPath = servletPath.getPath() + request.getParameter("folder");
		FileListResponse response = new FileListResponse(request.getParameter("folder"));
		response.setFilenames(getFilenamesInPath(folderPath));
		Gson gson = new Gson();
		String responseDataString = gson.toJson(response);
		PrintWriter out = resp.getWriter();
		out.print(responseDataString);
		out.flush();
	}

	/**
	 * get list of filenames in this path.
	 * 
	 * @param folderPath path
	 * @return array of filenames.
	 */
	private String[] getFilenamesInPath(final String folderPath) {
		List<String> list = new ArrayList<String>();
		Path path = Path.of(folderPath);
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for (Path file : stream) {
				if (!file.toFile().isDirectory()) {
					list.add(file.toFile().getName());
				}
			}
		} catch (IOException | DirectoryIteratorException x) {
			throw new RuntimeException(x);
		}
		return (String[]) list.toArray(new String[0]);
	}

}
