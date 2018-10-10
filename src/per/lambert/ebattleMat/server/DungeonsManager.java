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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServlet;

import com.google.gson.Gson;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManagement;

public class DungeonsManager {
	private static List<String> dungeonNames = new ArrayList<String>();
	private static List<DungeonData> dungeonData;
	private static ReentrantLock lock = new ReentrantLock();
	private final static String fileLocation = "/usr/dungeonData/";

	public static String[] getDungeonList(HttpServlet servlet) {
		lock.lock();
		try {
			checkIfNeedToLoadDungeons(servlet);
			return ((String[]) dungeonNames.toArray(new String[0]));
		} finally {
			lock.unlock();
		}
	}

	public static List<DungeonData> getDungeonData(HttpServlet servlet) {
		lock.lock();
		try {
			checkIfNeedToLoadDungeonData(servlet);
			return (dungeonData);
		} finally {
			lock.unlock();
		}
	}

	public class DungeonListJsonData {
		public String[] dungeonList;

		public String[] getDungeons() {
			return dungeonList;
		}

	}

	private static void checkIfNeedToLoadDungeons(HttpServlet servlet) {
		if (dungeonNames.isEmpty()) {
			Gson gson = new Gson();
			BufferedReader br = null;
			try {
				InputStream is = servlet.getServletContext().getResourceAsStream(fileLocation + "dungeonList.json");
				InputStreamReader isr = new InputStreamReader(is);
				br = new BufferedReader(isr);
				DungeonListJsonData result = gson.fromJson(br, DungeonListJsonData.class);
				dungeonNames.clear();
				if (result != null) {
					for (String dungeonName : result.getDungeons()) {
						dungeonNames.add(dungeonName);
					}
				}
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	private static void checkIfNeedToLoadDungeonData(HttpServlet servlet) {
		if (dungeonData == null && dungeonNames != null && !dungeonNames.isEmpty()) {
			dungeonData = new ArrayList<>();
			for (String dungeonName : dungeonNames) {
				loadDungeonData(servlet, dungeonName);
			}
		}
	}

	private static void loadDungeonData(HttpServlet servlet, String dungeonName) {
		String name = dungeonName.toLowerCase();
		Gson gson = new Gson();
		BufferedReader br = null;
		try {

			String filePath = fileLocation + name + "/dungeonData.json";
			InputStream is = servlet.getServletContext().getResourceAsStream(filePath);
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			DungeonData result = gson.fromJson(br, DungeonData.class);
			if (result != null) {
				dungeonData.add(result);
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static void saveDungeonData(final HttpServlet servlet, final DungeonData dungeonDataToSave)
			throws IOException {
		String dungeonDirectoryName = getDirectoryName(dungeonDataToSave.dungeonName);
		URL servletPath = servlet.getServletContext().getResource("/");
		String directoryPath = servletPath.getPath() + fileLocation + dungeonDirectoryName;
		makeSureDirectoryExists(directoryPath);
		String filePath = directoryPath + "/dungeonData.json";
		Gson gson = new Gson();
		String dataToWrite = gson.toJson(dungeonDataToSave);
		BufferedWriter output = null;
		try {
			lock.lock();
			File file = new File(filePath);
			file.delete();
			output = new BufferedWriter(new FileWriter(file));
			output.write(dataToWrite);
		}
		finally {
			if (output != null) {
				output.close();
			}
			lock.unlock();
		}
		dungeonData = null;
		checkIfNeedToLoadDungeonData(servlet);
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
}
