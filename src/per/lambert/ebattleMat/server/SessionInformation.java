package per.lambert.ebattleMat.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import per.lambert.ebattleMat.server.serviceData.DungeonSessionData;
import per.lambert.ebattleMat.server.serviceData.DungeonSessionLevel;
import per.lambert.ebattleMat.server.serviceData.PogDataLite;

public class SessionInformation {
	private int version;

	public int getVersion() {
		return version;
	}

	private boolean dirty;

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	private String sessionDirectory;

	public String getSessionDirectory() {
		return sessionDirectory;
	}

	public void setSessionDirectory(String sessionDirectory) {
		this.sessionDirectory = sessionDirectory;
	}

	private String sessionPath;

	public String getSessionPath() {
		return sessionPath;
	}

	public void setSessionPath(String sessionPath) {
		this.sessionPath = sessionPath;
	}

	private DungeonSessionData sessionData;

	public DungeonSessionData getSessionData() {
		return sessionData;
	}

	public void setSessionData(DungeonSessionData sessionData) {
		this.sessionData = sessionData;
	}

	public String getUUID() {
		if (sessionData != null) {
			return (sessionData.sessionUUID);
		}
		return (null);
	}

	public SessionInformation(DungeonSessionData sessionData, String sessionPath, String sessionDirectory) {
		version = 1;
		this.sessionData = sessionData;
		this.sessionPath = sessionPath;
		this.sessionDirectory = sessionDirectory;
	}

	public SessionInformation(String sessionPath, String sessionDirectory) {
		version = 1;
		this.sessionData = null;
		;
		this.sessionPath = sessionPath;
		this.sessionDirectory = sessionDirectory;
	}

	public SessionInformation() {
		version = 1;
		this.sessionData = null;
		this.sessionPath = null;
		this.sessionDirectory = null;
	}

	public String toJson() {
		if (sessionData != null) {
			Gson gson = new Gson();
			String sessionJson = gson.toJson(sessionData);
			return (sessionJson);
		}
		return ("");
	}

	public void load(String sessionPath, String sessionDirectory) throws IOException {
		this.sessionPath = sessionPath;
		this.sessionDirectory = sessionDirectory;
		String jsonData = DungeonsManager.readJsonFile(sessionPath);
		fromJson(jsonData);
		++version;
	}

	public void fromJson(String jsonData) {
		sessionData = null;
		if (jsonData != null && !jsonData.isEmpty()) {
			Gson gson = new Gson();
			sessionData = gson.fromJson(jsonData, DungeonSessionData.class);
		}
	}

	public void save() throws IOException {
		dirty = false;
		Gson gson = new Gson();
		String sessionJson = gson.toJson(sessionData);
		DungeonsManager.saveJsonFile(sessionJson, sessionPath);
	}

	public void savePog(PogDataLite pogData, int currentLevel, boolean needToAdd) {
		++version;
		dirty = true;
		DungeonSessionLevel sessionLevel = sessionData.sessionLevels[currentLevel];
		if (!needToAdd) {
			updatePog(sessionLevel, pogData, currentLevel);
			return;
		}
		PogDataLite[] newPogs = new PogDataLite[sessionLevel.monsters.length + 1];
		for (int i = 0; i < sessionLevel.monsters.length; ++i) {
			newPogs[i] = sessionLevel.monsters[i];
		}
		newPogs[newPogs.length - 1] = pogData;
		sessionLevel.monsters = newPogs;
	}

	private void updatePog(DungeonSessionLevel sessionLevel, PogDataLite pogData, int currentLevel) {
		for (PogDataLite pog : sessionLevel.monsters) {
			if (pog.uuid.equals(pogData.uuid)) {
				pog.pogColumn = pogData.pogColumn;
				pog.pogRow = pogData.pogRow;
			}
		}
	}

	public void saveIfDirty() {
		if (dirty) {
			try {
				save();
			} catch (IOException e) {
			}
		}
	}
}
