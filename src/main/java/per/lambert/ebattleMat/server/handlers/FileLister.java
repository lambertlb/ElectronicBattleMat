/*
 * Copyright (C) 2023 Leon Lambert.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package per.lambert.ebattleMat.server.handlers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

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
		String folderPath = request.getSession().getServletContext().getRealPath(request.getParameter("folder"));
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
		File file = new File(folderPath);
		String[] pathnames = file.list();
		return (pathnames);
	}

}
