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
		DungeonData dungeonData = getDungeonData(servlet, directoryDirectoryName);
		String dungeonName = dungeonData.dungeonName;
		dungeonListData.put(dungeonName, directoryDirectoryName);
	}

	private static DungeonData getDungeonData(final HttpServlet servlet, String directoryDirectoryName) {
		String directoryPath = dungeonLocation + directoryDirectoryName;
		String filePath = directoryPath + "/dungeonData.json";
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
			Gson gson = new Gson();
			DungeonData dungeonData = gson.fromJson(builder.toString(), DungeonData.class);
			return dungeonData;
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
		return(null);
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
		saveDungeonData(servlet,jsonData, dstDirectory);
	}

	public static void deleteDungeon(HttpServlet servlet, String templateName) throws IOException {
		deleteSessionOfTemplate(servlet, templateName);
		URL servletPath = servlet.getServletContext().getResource("/");
		File srcDir = new File(servletPath.getPath() + dungeonLocation + templateName);
		FileUtils.deleteDirectory(srcDir);
	}

	private static void deleteSessionOfTemplate(HttpServlet servlet, String templateName) {
		// TODO delete session of this template
		
	}
}
