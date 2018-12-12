package per.lambert.ebattleMat.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import per.lambert.ebattleMat.server.serviceData.DungeonSessionData;

public class SessionInformation {
	private int version;

	public int getVersion() {
		return version;
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
	}

	public void fromJson(String jsonData) {
		sessionData = null;
		if (jsonData != null && !jsonData.isEmpty()) {
			Gson gson = new Gson();
			sessionData = gson.fromJson(jsonData, DungeonSessionData.class);
		}
	}

	public void save() throws IOException {
		Gson gson = new Gson();
		String sessionJson = gson.toJson(sessionData);
		DungeonsManager.saveJsonFile(sessionJson, sessionPath);
	}
}
