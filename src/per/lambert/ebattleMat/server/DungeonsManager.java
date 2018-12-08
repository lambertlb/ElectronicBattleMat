package per.lambert.ebattleMat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
	private static Map<String, String> uuidTemplatePathMap = new HashMap<String, String>();

	public static Map<String, String> getUuidTemplatePathMap() {
		return uuidTemplatePathMap;
	}

	private static Map<String, String> dungeonNameToUUIDMap = new HashMap<String, String>();

	public static Map<String, String> getDungeonNameToUUIDMap() {
		return dungeonNameToUUIDMap;
	}

	public static void saveDungeonData(final HttpServletRequest request, final HttpServlet servlet, final String dataToWrite) throws IOException {
		String dungeonUUID = request.getParameter("dungeonUUID");
		if (dungeonUUID == null || dungeonUUID.isEmpty()) {
			return;
		}
		saveDungeonData(servlet, dataToWrite, dungeonUUID);
	}

	private static void saveDungeonData(final HttpServlet servlet, final String dataToWrite, String dungeonUUID) throws IOException {
		String filePath = uuidTemplatePathMap.get(dungeonUUID) + "/dungeonData.json";
		URL servletPath = servlet.getServletContext().getResource("/");
		saveJsonFile(dataToWrite, servletPath.getPath() + filePath);
	}

	private static void saveJsonFile(final String dataToWrite, String filePath) throws IOException {
		BufferedWriter output = null;
		try {
			lock.lock();
			File file = new File(filePath);
			file.delete();
			output = new BufferedWriter(new FileWriter(file));
			output.write(dataToWrite);
		} catch (IOException e) {
			if (output != null) {
				output.close();
			}
		} finally {
			if (output != null) {
				output.close();
			}
			lock.unlock();
		}
	}

	private static String readJsonFile(String filePath) {
		StringBuilder builder = new StringBuilder();
		BufferedReader input = null;
		try {
			lock.lock();
			File file = new File(filePath);
			input = new BufferedReader(new FileReader(file));
			String line;
			while ((line = input.readLine()) != null) {
				builder.append(line);
			}
		} catch (IOException e) {
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
			lock.unlock();
		}
		return builder.toString();
	}

	private static void makeSureDirectoryExists(String dungeonDirectoryPath) {
		File path = new File(dungeonDirectoryPath);
		if (!path.exists()) {
			path.mkdir();
		}
	}

	private static void rebuildDungeonList(final HttpServlet servlet) {
		uuidTemplatePathMap.clear();
		dungeonNameToUUIDMap.clear();
		getDungeonListData(servlet);
	}

	public static void getDungeonListData(final HttpServlet servlet) {
		lock.lock();
		if (uuidTemplatePathMap.size() != 0) {
			lock.unlock();
			return;
		}
		try {
			URL servletPath = servlet.getServletContext().getResource("/");
			String directoryPath = servletPath.getPath() + dungeonLocation;
			File directory = new File(directoryPath);
			for (File possibleDungeon : directory.listFiles()) {
				if (possibleDungeon.isDirectory()) {
					getDungeonName(servlet, possibleDungeon);
				}
			}
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		} finally {
			lock.unlock();
		}
	}

	private static void getDungeonName(final HttpServlet servlet, File possibleDungeon) throws IOException {
		String directoryDirectoryName = possibleDungeon.getName();
		DungeonData dungeonData = getDungeonData(servlet, directoryDirectoryName);
		String dungeonName = dungeonData.dungeonName;
		String uuid = dungeonData.uuid;
		uuidTemplatePathMap.put(uuid, dungeonLocation + directoryDirectoryName);
		dungeonNameToUUIDMap.put(uuid, dungeonName);
	}

	private static DungeonData getDungeonData(final HttpServlet servlet, String directoryDirectoryName) throws IOException {
		String directoryPath = dungeonLocation + directoryDirectoryName;
		return getDungeonDataFromPath(servlet, directoryPath);
	}

	private static DungeonData getDungeonDataFromUUID(final HttpServlet servlet, String dungeonUUID) throws IOException {
		String directoryPath = uuidTemplatePathMap.get(dungeonUUID);
		return getDungeonDataFromPath(servlet, directoryPath);
	}

	private static DungeonData getDungeonDataFromPath(final HttpServlet servlet, String directoryPath) throws IOException {
		URL servletPath = servlet.getServletContext().getResource("/");
		String filePath = servletPath.getPath() + directoryPath + "/dungeonData.json";
		String jsonData = readJsonFile(filePath);
		Gson gson = new Gson();
		DungeonData dungeonData = gson.fromJson(jsonData, DungeonData.class);
		return (dungeonData);
	}

	public static void copyDungeon(HttpServlet servlet, String dungeonUUID, String newDungeonName) throws IOException {
		URL servletPath = servlet.getServletContext().getResource("/");
		String dstDirectory = newDungeonName.replaceAll("\\s+", "_");
		File srcDir = new File(servletPath.getPath() + uuidTemplatePathMap.get(dungeonUUID));
		File destDir = new File(servletPath.getPath() + dungeonLocation + dstDirectory);
		FileUtils.copyDirectory(srcDir, destDir);
		DungeonData dungeonData = getDungeonData(servlet, dstDirectory);
		dungeonData.dungeonName = newDungeonName;
		UUID uuid = UUID.randomUUID();
		String uuidString = uuid.toString();
		dungeonData.uuid = uuidString;
		uuidTemplatePathMap.put(uuidString, dungeonLocation + dstDirectory);
		dungeonNameToUUIDMap.put(uuidString, newDungeonName);
		Gson gson = new Gson();
		String jsonData = gson.toJson(dungeonData);
		saveDungeonData(servlet, jsonData, uuidString);
	}

	public static void deleteDungeon(HttpServlet servlet, String dungeonUUID) throws IOException {
		URL servletPath = servlet.getServletContext().getResource("/");
		File srcDir = new File(servletPath.getPath() + uuidTemplatePathMap.get(dungeonUUID));
		FileUtils.deleteDirectory(srcDir);
		rebuildDungeonList(servlet);
	}

	public static Map<String, String> getSessionListData(HttpServlet servlet, String dungeonUUID) {
		Map<String, String> sessionListData = new HashMap<String, String>();
		lock.lock();
		try {
			URL servletPath = servlet.getServletContext().getResource("/");
			String sessionsPath = uuidTemplatePathMap.get(dungeonUUID) + ElectronicBattleMat.SESSIONS_FOLDER;
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
		DungeonSessionData sessionData = getSessionData(servlet, possibleSession.getPath());
		sessionListData.put(sessionData.sessionName, sessionsPath + possibleSession.getName());
	}

	private static DungeonSessionData getSessionData(final HttpServlet servlet, String resourcePath) throws IOException {
		String filePath = resourcePath + "/sessionData.json";
		String jsonData = readJsonFile(filePath);
		Gson gson = new Gson();
		DungeonSessionData sessionData = gson.fromJson(jsonData, DungeonSessionData.class);
		return sessionData;
	}

	/*
	 * private static String readJsonDataFromFile(final HttpServlet servlet, String filePath) { BufferedReader br = null; StringBuilder builder = new StringBuilder(); lock.lock(); try { InputStream is =
	 * servlet.getServletContext().getResourceAsStream(filePath); InputStreamReader isr = new InputStreamReader(is); br = new BufferedReader(isr); String line; while ((line = br.readLine()) != null) { builder.append(line); } return builder.toString(); } catch
	 * (IOException e) { } finally { if (br != null) { try { br.close(); } catch (IOException e) { } } lock.unlock(); } return (""); }
	 */
	public static String getFileAsString(final HttpServlet servlet, final String fileName) throws MalformedURLException {
		URL servletPath = servlet.getServletContext().getResource("/");
		String filePath = servletPath.getPath() + fileLocation + fileName;
		return (readJsonFile(filePath));
	}

	public static String getDungeonDataAsString(HttpServlet servlet, String dungeonUUID) throws IOException {
		if (!uuidTemplatePathMap.containsKey(dungeonUUID)) {
			return (null);
		}
		URL servletPath = servlet.getServletContext().getResource("/");
		String filePath = servletPath.getPath() + uuidTemplatePathMap.get(dungeonUUID) + "/dungeonData.json";
		return (readJsonFile(filePath));
	}

	public static void createSession(HttpServlet servlet, String dungeonUUID, String newSessionName) throws IOException {
		URL servletPath = servlet.getServletContext().getResource("/");
		String templateDirectory = servletPath.getPath() + uuidTemplatePathMap.get(dungeonUUID);
		String sessionDirectory = templateDirectory + ElectronicBattleMat.SESSIONS_FOLDER + newSessionName.replaceAll("\\s+", "_");
		makeSureDirectoryExists(sessionDirectory);
		DungeonData dungeonData = getDungeonDataFromUUID(servlet, dungeonUUID);
		DungeonSessionData sessionData = createSessionData(servlet, sessionDirectory, dungeonUUID, newSessionName, dungeonData);
		Gson gson = new Gson();
		String sessionJson = gson.toJson(sessionData);
		String filePath = sessionDirectory + "/" + "sessionData.json";
		saveJsonFile(sessionJson, filePath);
	}

	private static DungeonSessionData createSessionData(HttpServlet servlet, String sessionDirectory, String dungeonUUID, String newSessionName, DungeonData dungeonData) {
		DungeonSessionData newSessionData = new DungeonSessionData(newSessionName, dungeonUUID);
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
