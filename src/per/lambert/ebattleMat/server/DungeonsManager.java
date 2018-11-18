package per.lambert.ebattleMat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import per.lambert.ebattleMat.client.ElectronicBattleMat;

public class DungeonsManager {
	private static ReentrantLock lock = new ReentrantLock();
	private final static String fileLocation = "/" + ElectronicBattleMat.DUNGEON_DATA_LOCATION;
	private final static String dungeonLocation = "/" + ElectronicBattleMat.DUNGEONS_LOCATION;

	public static void saveDungeonData(final HttpServletRequest request, final HttpServlet servlet, final String dataToWrite) throws IOException {
		String dungeonDirectoryName = request.getParameter("dungeonName");
		if (dungeonDirectoryName == null || dungeonDirectoryName.isEmpty()) {
			return;
		}
		URL servletPath = servlet.getServletContext().getResource("/");
		String directoryPath = servletPath.getPath() + dungeonLocation + dungeonDirectoryName;
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

	public static Map<String, String> getDungeonListData(final HttpServlet servlet) {
		Map<String, String> dungeonListData = new HashMap<String, String>();
		lock.lock();
		try {
			URL servletPath = servlet.getServletContext().getResource("/");
			String directoryPath = servletPath.getPath() + dungeonLocation;
			File directory = new File(directoryPath);
			for (File possibleDungeon : directory.listFiles()) {
				if (possibleDungeon.isDirectory()) {
					getDungeonName(servlet, possibleDungeon, dungeonListData);
				}
			}
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		} finally {
			lock.unlock();
		}
		return dungeonListData;
	}

	private static void getDungeonName(final HttpServlet servlet, File possibleDungeon, Map<String, String> dungeonListData) throws IOException {
		String directoryDirectoryName = possibleDungeon.getName();
		String directoryPath = dungeonLocation + directoryDirectoryName;
		String filePath = directoryPath + "/dungeonData.json";
		BufferedReader br = null;
		StringBuilder builder = new StringBuilder();
		InputStream is = servlet.getServletContext().getResourceAsStream(filePath);
		InputStreamReader isr = new InputStreamReader(is);
		br = new BufferedReader(isr);
		String line;
		while ((line = br.readLine()) != null) {
			builder.append(line);
		}
		Gson gson = new Gson();
		DungeonData dungeonData = gson.fromJson(builder.toString(), DungeonData.class);
		String dungeonName = dungeonData.dungeonName;
		dungeonListData.put(dungeonName, directoryDirectoryName);
	}
}
