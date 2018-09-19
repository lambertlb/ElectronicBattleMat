package per.lambert.ebattleMat.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServlet;

import com.google.gson.Gson;

public class DungeonsManager {
	private static List<String> dungeonNames = new ArrayList<String>();
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
}
