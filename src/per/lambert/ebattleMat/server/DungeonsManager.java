package per.lambert.ebattleMat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import per.lambert.ebattleMat.client.ElectronicBattleMat;

public class DungeonsManager {
	private static ReentrantLock lock = new ReentrantLock();
	private final static String fileLocation = "/" + ElectronicBattleMat.DUNGEON_DATA_LOCATION;

	public static void saveDungeonData(final HttpServletRequest request, final HttpServlet servlet,
			final String dataToWrite) throws IOException {
		String dungeonDirectoryName = getDirectoryName(request.getParameter("dungeonName"));
		if (dungeonDirectoryName == null || dungeonDirectoryName.isEmpty()) {
			return;
		}
		URL servletPath = servlet.getServletContext().getResource("/");
		String directoryPath = servletPath.getPath() + fileLocation + dungeonDirectoryName;
		makeSureDirectoryExists(directoryPath);
		String filePath = directoryPath + "/dungeonData.json";
		BufferedWriter output = null;
		try {
			lock.lock();
			File file = new File(filePath);
			file.delete();
			output = new BufferedWriter(new FileWriter(file));
			output.write(dataToWrite);
		} finally {
			if (output != null) {
				output.close();
			}
			lock.unlock();
		}
	}

	private static void makeSureDirectoryExists(String dungeonDirectoryPath) {
		File path = new File(dungeonDirectoryPath);
		if (!path.exists()) {
			path.mkdir();
		}
	}

	private static String getDirectoryName(String dungeonName) {
		String directoryName = dungeonName.toLowerCase().replaceAll("\\s+", "_");
		return directoryName;
	}

	public static String getFileAsString(final HttpServlet servlet, final String fileName) {
		BufferedReader br = null;
		lock.lock();
		try {
			StringBuilder builder = new StringBuilder();
			String filePath = fileLocation + fileName;
			InputStream is = servlet.getServletContext().getResourceAsStream(filePath);
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			return (builder.toString());
		} catch (IOException e) {
			return ("");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
			lock.unlock();
		}
	}
}
