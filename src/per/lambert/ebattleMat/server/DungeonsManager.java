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

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import per.lambert.ebattleMat.client.ElectronicBattleMat;
import per.lambert.ebattleMat.server.serviceData.DungeonData;
import per.lambert.ebattleMat.server.serviceData.DungeonSessionData;
import per.lambert.ebattleMat.server.serviceData.DungeonSessionLevel;

public class DungeonsManager {
	private static ReentrantLock lock = new ReentrantLock();
	private final static String fileLocation = "/" + ElectronicBattleMat.DUNGEON_DATA_LOCATION;
	private final static String dungeonLocation = "/" + ElectronicBattleMat.DUNGEONS_LOCATION;

	public static void saveDungeonData(final HttpServletRequest request, final HttpServlet servlet, final String dataToWrite) throws IOException {
		String dungeonDirectoryName = request.getParameter("dungeonName");
		if (dungeonDirectoryName == null || dungeonDirectoryName.isEmpty()) {
			return;
		}
		saveDungeonData(servlet, dataToWrite, dungeonDirectoryName);
	}

	private static void saveDungeonData(final HttpServlet servlet, final String dataToWrite, String dungeonDirectoryName) throws MalformedURLException, IOException {
		URL servletPath = servlet.getServletContext().getResource("/");
		String directoryPath = servletPath.getPath() + dungeonLocation + dungeonDirectoryName;
		makeSureDirectoryExists(directoryPath);
		String filePath = directoryPath + "/dungeonData.json";
		saveJsonFile(dataToWrite, filePath);
	}

	private static void saveJsonFile(final String dataToWrite, String filePath) throws IOException {
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
		DungeonData dungeonData = getDungeonData(servlet, directoryDirectoryName);
		String dungeonName = dungeonData.dungeonName;
		dungeonListData.put(dungeonName, directoryDirectoryName);
	}

	private static DungeonData getDungeonData(final HttpServlet servlet, String directoryDirectoryName) {
		String directoryPath = dungeonLocation + directoryDirectoryName;
		String filePath = directoryPath + "/dungeonData.json";

		String jsonData = readJsonDataFromFile(servlet, filePath);
		Gson gson = new Gson();
		DungeonData dungeonData = gson.fromJson(jsonData, DungeonData.class);
		return (dungeonData);
	}

	public static void copyDungeon(HttpServlet servlet, String sourceDirectory, String newDungeonName) throws IOException {
		URL servletPath = servlet.getServletContext().getResource("/");
		String dstDirectory = newDungeonName.replaceAll("\\s+", "_");
		File srcDir = new File(servletPath.getPath() + dungeonLocation + sourceDirectory);
		File destDir = new File(servletPath.getPath() + dungeonLocation + dstDirectory);
		FileUtils.copyDirectory(srcDir, destDir);
		DungeonData dungeonData = getDungeonData(servlet, dstDirectory);
		dungeonData.dungeonName = newDungeonName;
		Gson gson = new Gson();
		String jsonData = gson.toJson(dungeonData);
		saveDungeonData(servlet, jsonData, dstDirectory);
	}

	public static void deleteDungeon(HttpServlet servlet, String templateName) throws IOException {
		URL servletPath = servlet.getServletContext().getResource("/");
		File srcDir = new File(servletPath.getPath() + dungeonLocation + templateName);
		FileUtils.deleteDirectory(srcDir);
	}

	public static Map<String, String> getSessionListData(HttpServlet servlet, String dungeonName) {
		Map<String, String> sessionListData = new HashMap<String, String>();
		lock.lock();
		try {
			URL servletPath = servlet.getServletContext().getResource("/");
			String sessionsPath = dungeonLocation + dungeonName + ElectronicBattleMat.SESSIONS_FOLDER;
			String directoryPath = servletPath.getPath() + sessionsPath;
			makeSureDirectoryExists(directoryPath);
			File directory = new File(directoryPath);
			for (File possibleSession : directory.listFiles()) {
				if (possibleSession.isDirectory()) {
					getSessionName(servlet, sessionsPath, possibleSession, sessionListData);
				}
			}
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		} finally {
			lock.unlock();
		}
		return sessionListData;
	}

	private static void getSessionName(final HttpServlet servlet, String sessionsPath, File possibleSession, Map<String, String> sessionListData) throws IOException {
		String resourcePath = sessionsPath + possibleSession.getName();
		DungeonSessionData dungeonData = getSessionData(servlet, resourcePath);
		String dungeonName = dungeonData.sessionName;
		sessionListData.put(dungeonName, possibleSession.getName());
	}

	private static DungeonSessionData getSessionData(final HttpServlet servlet, String resourcePath) {
		String filePath = resourcePath + "/sessionData.json";
		String jsonData = readJsonDataFromFile(servlet, filePath);
		Gson gson = new Gson();
		DungeonSessionData sessionData = gson.fromJson(jsonData, DungeonSessionData.class);
		return sessionData;
	}

	private static String readJsonDataFromFile(final HttpServlet servlet, String filePath) {
		BufferedReader br = null;
		StringBuilder builder = new StringBuilder();
		lock.lock();
		try {
			InputStream is = servlet.getServletContext().getResourceAsStream(filePath);
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();
		} catch (IOException e) {
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
			lock.unlock();
		}
		return ("");
	}

	public static String getFileAsString(final HttpServlet servlet, final String fileName) {
		String filePath = fileLocation + fileName;
		return (readJsonDataFromFile(servlet, filePath));
	}

	public static void createSession(HttpServlet servlet, String templateName, String newSessionName) throws IOException {
		URL servletPath = servlet.getServletContext().getResource("/");
		String templateDirectory = servletPath.getPath() + dungeonLocation + templateName.replaceAll("\\s+", "_");
		String sessionDirectory = templateDirectory + ElectronicBattleMat.SESSIONS_FOLDER + newSessionName.replaceAll("\\s+", "_");
		makeSureDirectoryExists(sessionDirectory);
		DungeonData dungeonData = getDungeonData(servlet, templateName);
		DungeonSessionData sessionData = createSessionData(servlet, sessionDirectory, templateName, newSessionName, dungeonData);
		Gson gson = new Gson();
		String sessionJson = gson.toJson(sessionData);
		String filePath = sessionDirectory + "/" + "sessionData.json";
		saveJsonFile(sessionJson, filePath);
	}

	private static DungeonSessionData createSessionData(HttpServlet servlet, String sessionDirectory, String templateName, String newSessionName, DungeonData dungeonData) {
		DungeonSessionData newSessionData = new DungeonSessionData(newSessionName, templateName);
		newSessionData.sessionLevels = new DungeonSessionLevel[dungeonData.dungeonLevels.length];
		for (int i = 0; i < dungeonData.dungeonLevels.length; ++i) {
			newSessionData.sessionLevels[i] = getSessionLevel(i, dungeonData, newSessionData);
		}
		return (newSessionData);
	}

	private static DungeonSessionLevel getSessionLevel(int i, DungeonData dungeonData, DungeonSessionData newSessionData) {
		DungeonSessionLevel sessionLevel = new DungeonSessionLevel(dungeonData.dungeonLevels[i]);
		return (sessionLevel);
	}

	public static void deleteSession(HttpServlet servlet, String templateName, String sessionName) throws IOException {
		URL servletPath = servlet.getServletContext().getResource("/");
		String templateDirectory = servletPath.getPath() + dungeonLocation + templateName;
		String sessionDirectory = templateDirectory + ElectronicBattleMat.SESSIONS_FOLDER + sessionName;
		File srcDir = new File(sessionDirectory);
		FileUtils.deleteDirectory(srcDir);
	}
}
