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
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import per.lambert.ebattleMat.client.ElectronicBattleMat;
import per.lambert.ebattleMat.server.handlers.DungeonListHandler;
import per.lambert.ebattleMat.server.serviceData.DungeonData;
import per.lambert.ebattleMat.server.serviceData.DungeonSessionData;
import per.lambert.ebattleMat.server.serviceData.DungeonSessionLevel;
import per.lambert.ebattleMat.server.serviceData.PogData;

public class DungeonsManager {
	private static ReentrantLock lock = new ReentrantLock();
	private final static String fileLocation = "/" + ElectronicBattleMat.DUNGEON_DATA_LOCATION;
	private final static String dungeonLocation = "/" + ElectronicBattleMat.DUNGEONS_LOCATION;
	private static Map<String, String> uuidTemplatePathMap = new HashMap<String, String>();
	private static Map<String, SessionInformation> sessionCache = new HashMap<String, SessionInformation>();
	@SuppressWarnings("unused")
	private static boolean initialized = initializeDungeonManager();
	private static Timer timer;

	public static Map<String, String> getUuidTemplatePathMap() {
		return uuidTemplatePathMap;
	}

	private static Map<String, String> dungeonNameToUUIDMap = new HashMap<String, String>();

	public static Map<String, String> getDungeonNameToUUIDMap() {
		return dungeonNameToUUIDMap;
	}

	private static boolean initializeDungeonManager() {
		TimerTask repeatedTask = new TimerTask() {
			public void run() {
				DungeonsManager.periodicTimer();
			}
		};
		timer = new Timer("Timer");

		long delay = 5000L;
		long period = 5000L;
		timer.scheduleAtFixedRate(repeatedTask, delay, period);
		return true;
	}

	protected static void periodicTimer() {
		lock.lock();
		try {
			checkIfTimeToSaveSessionData();
		} finally {
			lock.unlock();
		}
	}

	private static void checkIfTimeToSaveSessionData() {
		for (SessionInformation sessionInformation : sessionCache.values()) {
			sessionInformation.saveIfDirty();
		}
	}

	public static void saveDungeonData(final HttpServletRequest request, final HttpServlet servlet, final String dataToWrite) throws IOException {
		String dungeonUUID = request.getParameter("dungeonUUID");
		if (dungeonUUID == null || dungeonUUID.isEmpty()) {
			return;
		}
		lock.lock();
		try {
			saveDungeonData(servlet, dataToWrite, dungeonUUID);
		} finally {
			lock.unlock();
		}
	}

	private static void saveDungeonData(final HttpServlet servlet, final String dataToWrite, String dungeonUUID) throws IOException {
		String filePath = uuidTemplatePathMap.get(dungeonUUID) + "/dungeonData.json";
		URL servletPath = servlet.getServletContext().getResource("/");
		saveJsonFile(dataToWrite, servletPath.getPath() + filePath);
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
		addToDungeonCache(directoryDirectoryName, dungeonName, uuid);
	}

	private static void addToDungeonCache(String directoryDirectoryName, String dungeonName, String uuid) {
		lock.lock();
		try {
			uuidTemplatePathMap.put(uuid, dungeonLocation + directoryDirectoryName);
			dungeonNameToUUIDMap.put(uuid, dungeonName);
		} finally {
			lock.unlock();
		}
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
		lock.lock();
		try {
			URL servletPath = servlet.getServletContext().getResource("/");
			String dstDirectory = newDungeonName.replaceAll("\\s+", "_");
			File srcDir = new File(servletPath.getPath() + uuidTemplatePathMap.get(dungeonUUID));
			File destDir = new File(servletPath.getPath() + dungeonLocation + dstDirectory);
			FileUtils.copyDirectory(srcDir, destDir);
			deleteAnyOldSessions(destDir);
			DungeonData dungeonData = getDungeonData(servlet, dstDirectory);
			dungeonData.dungeonName = newDungeonName;
			UUID uuid = UUID.randomUUID();
			String uuidString = uuid.toString();
			dungeonData.uuid = uuidString;
			addToDungeonCache(dstDirectory, newDungeonName, uuidString);
			Gson gson = new Gson();
			String jsonData = gson.toJson(dungeonData);
			saveDungeonData(servlet, jsonData, uuidString);
		} finally {
			lock.unlock();
		}
	}

	public static void deleteDungeon(HttpServlet servlet, String dungeonUUID) throws IOException {
		lock.lock();
		try {
			URL servletPath = servlet.getServletContext().getResource("/");
			File srcDir = new File(servletPath.getPath() + uuidTemplatePathMap.get(dungeonUUID));
			FileUtils.deleteDirectory(srcDir);
			rebuildDungeonList(servlet);
		} finally {
			lock.unlock();
		}
	}

	public static String getDungeonDataAsString(HttpServlet servlet, String dungeonUUID) throws IOException {
		lock.lock();
		try {
			if (!uuidTemplatePathMap.containsKey(dungeonUUID)) {
				return (null);
			}
			URL servletPath = servlet.getServletContext().getResource("/");
			String filePath = servletPath.getPath() + uuidTemplatePathMap.get(dungeonUUID) + "/dungeonData.json";
			return (readJsonFile(filePath));
		} finally {
			lock.unlock();
		}
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
					putSessionNameInCache(servlet, sessionsPath, possibleSession, sessionListData);
				}
			}
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		} finally {
			lock.unlock();
		}
		return sessionListData;
	}

	private static void putSessionNameInCache(final HttpServlet servlet, String sessionsPath, File possibleSession, Map<String, String> sessionListData) throws IOException {
		SessionInformation sessionInformation = loadSessionInformation(possibleSession);
		DungeonSessionData sessionData = sessionInformation.getSessionData();
		sessionListData.put(sessionData.sessionName, sessionData.sessionUUID);
	}

	public static void createSession(HttpServlet servlet, String dungeonUUID, String newSessionName) throws IOException {
		lock.lock();
		try {
			URL servletPath = servlet.getServletContext().getResource("/");
			String templateDirectory = servletPath.getPath() + uuidTemplatePathMap.get(dungeonUUID);
			String sessionDirectory = templateDirectory + ElectronicBattleMat.SESSIONS_FOLDER + newSessionName.replaceAll("\\s+", "_");
			makeSureDirectoryExists(sessionDirectory);
			DungeonData dungeonData = getDungeonDataFromUUID(servlet, dungeonUUID);
			DungeonSessionData sessionData = createSessionData(servlet, sessionDirectory, dungeonUUID, newSessionName, dungeonData);
			String filePath = sessionDirectory + "/" + "sessionData.json";
			SessionInformation sessionInformation = new SessionInformation(sessionData, filePath, sessionDirectory);
			sessionInformation.save();
		} finally {
			lock.unlock();
		}
	}

	private static DungeonSessionData createSessionData(HttpServlet servlet, String sessionDirectory, String dungeonUUID, String newSessionName, DungeonData dungeonData) {
		UUID uuid = UUID.randomUUID();
		String uuidString = uuid.toString();
		DungeonSessionData newSessionData = new DungeonSessionData(newSessionName, dungeonUUID, uuidString);
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

	public static void deleteSession(HttpServlet servlet, String dungeonUUID, String sessionUUID) throws IOException {
		lock.lock();
		try {
			SessionInformation sessionInformation = getSessionInformation(servlet, dungeonUUID, sessionUUID);
			if (sessionInformation != null) {
				removeSessionFromCache(sessionUUID);
				File possibleSession = new File(sessionInformation.getSessionDirectory());
				FileUtils.deleteDirectory(possibleSession);
			}
		} finally {
			lock.unlock();
		}
	}

	private static SessionInformation getSessionInformation(HttpServlet servlet, String dungeonUUID, String sessionUUID) throws IOException {
		SessionInformation sessionInformation = getSessionFromCache(sessionUUID);
		if (sessionInformation != null) {
			return (sessionInformation);
		}
		lock.lock();
		try {
			URL servletPath = servlet.getServletContext().getResource("/");
			String sessionsPath = uuidTemplatePathMap.get(dungeonUUID) + ElectronicBattleMat.SESSIONS_FOLDER;
			String directoryPath = servletPath.getPath() + sessionsPath;
			File sessionsDirectory = new File(directoryPath);
			for (File possibleSession : sessionsDirectory.listFiles()) {
				if (possibleSession.isDirectory()) {
					SessionInformation possibleSessionInformation = loadSessionInformation(possibleSession);
					if (sessionUUID.equals(possibleSessionInformation.getUUID())) {
						addSessionToCache(possibleSessionInformation);
						return (possibleSessionInformation);
					}
				}
			}
		} finally {
			lock.unlock();
		}
		return null;
	}

	private static SessionInformation loadSessionInformation(File possibleSession) throws IOException {
		SessionInformation possibleSessionInformation = new SessionInformation();
		possibleSessionInformation.load(possibleSession.getPath() + "/sessionData.json", possibleSession.getPath());
		return possibleSessionInformation;
	}

	private static SessionInformation getSessionFromCache(String sessionUUID) {
		lock.lock();
		try {
			return (sessionCache.get(sessionUUID));
		} finally {
			lock.unlock();
		}
	}

	private static void removeSessionFromCache(String sessionUUID) {
		lock.lock();
		try {
			if (sessionCache.containsKey(sessionUUID)) {
				sessionCache.remove(sessionUUID);
			}
		} finally {
			lock.unlock();
		}
	}

	private static void addSessionToCache(SessionInformation sessionInformation) {
		lock.lock();
		try {
			if (sessionCache.containsKey(sessionInformation.getUUID())) {
				sessionCache.remove(sessionInformation.getUUID());
			}
			sessionCache.put(sessionInformation.getUUID(), sessionInformation);
		} finally {
			lock.unlock();
		}
	}

	public static String getSessionDataAsString(HttpServlet servlet, String dungeonUUID, String sessionUUID, int version) throws IOException {
		lock.lock();
		try {
			SessionInformation sessionInformation;
			if (version != -1) {
				sessionInformation = getSessionFromCache(sessionUUID);
			} else {
				sessionInformation = getSessionInformation(servlet, dungeonUUID, sessionUUID);
			}
			if (sessionInformation != null && sessionInformation.getSessionData().getVersion() != version) {
				return (sessionInformation.toJson());
			}
			return ("");
		} finally {
			lock.unlock();
		}
	}

	public static void saveSessionDataData(HttpServletRequest request, HttpServlet servlet, String jsonData, String sessionUUID) throws IOException {
		String dungeonUUID = request.getParameter("dungeonUUID");
		if (dungeonUUID == null || dungeonUUID.isEmpty()) {
			return;
		}
		lock.lock();
		try {
			SessionInformation sessionInformation = getSessionInformation(servlet, dungeonUUID, sessionUUID);
			if (sessionInformation != null) {
				sessionInformation.fromJson(jsonData);
				sessionInformation.save();
			}
		} finally {
			lock.unlock();
		}
	}

	public static void savePog(HttpServlet servlet, String dungeonUUID, String sessionUUID, int currentLevel, boolean needToAdd, String pogJsonData) throws IOException {
		lock.lock();
		try {
			SessionInformation sessionInformation = getSessionInformation(servlet, dungeonUUID, sessionUUID);
			if (sessionInformation == null) {
				return;
			}
			Gson gson = new Gson();
			PogData pogData = gson.fromJson(pogJsonData, PogData.class);
			if (pogData.pogType.equals(ElectronicBattleMat.POG_TYPE_MONSTER)) {
				sessionInformation.saveMonsterPog(pogData, currentLevel, needToAdd);
			} else if (pogData.pogType.equals(ElectronicBattleMat.POG_TYPE_ROOMOBJECT)) {
				sessionInformation.saveRoomObjectPog(pogData, currentLevel, needToAdd);
			} else if (pogData.pogType.equals(ElectronicBattleMat.POG_TYPE_PLAYER)) {
				sessionInformation.savePlayerPog(pogData, currentLevel, needToAdd);
			}
		} finally {
			lock.unlock();
		}
	}

	public static void updateFOW(HttpServlet servlet, String sessionUUID, int currentLevel, boolean[][] fogOfWar) {
		lock.lock();
		try {
			SessionInformation sessionInformation = getSessionFromCache(sessionUUID);
			if (sessionInformation == null) {
				return;
			}
			sessionInformation.updateFOW(fogOfWar, currentLevel);
		} finally {
			lock.unlock();
		}
	}

	public static String getFileAsString(final HttpServlet servlet, final String fileName) throws IOException {
		lock.lock();
		try {
			URL servletPath = servlet.getServletContext().getResource("/");
			String filePath = servletPath.getPath() + fileLocation + fileName;
			return (readJsonFile(filePath));
		} finally {
			lock.unlock();
		}
	}

	public static void saveJsonFile(final String dataToWrite, String filePath) throws IOException {
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

	public static String readJsonFile(String filePath) {
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

	private static void deleteAnyOldSessions(File destDir) throws IOException {
		String sessionsPath = destDir.getPath() + "/" + ElectronicBattleMat.SESSIONS_FOLDER;
		File sessions = new File(sessionsPath);
		lock.lock();
		try {
			FileUtils.deleteDirectory(sessions);
		} finally {
			lock.unlock();
		}
	}

	private static void makeSureDirectoryExists(String dungeonDirectoryPath) {
		File path = new File(dungeonDirectoryPath);
		if (!path.exists()) {
			path.mkdir();
		}
	}
}
