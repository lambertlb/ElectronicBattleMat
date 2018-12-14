package per.lambert.ebattleMat.server;

import java.io.IOException;

import com.google.gson.Gson;

import per.lambert.ebattleMat.server.serviceData.DungeonSessionData;
import per.lambert.ebattleMat.server.serviceData.DungeonSessionLevel;
import per.lambert.ebattleMat.server.serviceData.PogData;
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

	public SessionInformation() {
		version = 1;
		this.sessionData = null;
		this.sessionPath = null;
		this.sessionDirectory = null;
	}

	public SessionInformation(DungeonSessionData sessionData, String sessionPath, String sessionDirectory) {
		this();
		this.sessionData = sessionData;
		this.sessionPath = sessionPath;
		this.sessionDirectory = sessionDirectory;
	}

	public SessionInformation(String sessionPath, String sessionDirectory) {
		this();
		this.sessionPath = sessionPath;
		this.sessionDirectory = sessionDirectory;
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
		Gson gson = new Gson();
		String sessionJson = gson.toJson(sessionData);
		DungeonsManager.saveJsonFile(sessionJson, sessionPath);
		dirty = false;
	}

	public void saveMonsterPog(PogDataLite pogData, int currentLevel, boolean needToAdd) {
		DungeonSessionLevel sessionLevel = sessionData.sessionLevels[currentLevel];
		sessionLevel.monsters = savePogToProperCollection(pogData, needToAdd, sessionLevel.monsters);
	}

	public void savePlayerPog(PogData pogData, int currentLevel, boolean needToAdd) {
		DungeonSessionLevel sessionLevel = sessionData.sessionLevels[currentLevel];
		++version;
		dirty = true;
		if (!needToAdd) {
			updatePogCollection(sessionLevel.players, pogData);
			return;
		}
		PogData[] newPogs = new PogData[sessionLevel.players.length + 1];
		for (int i = 0; i < sessionLevel.players.length; ++i) {
			newPogs[i] = sessionLevel.players[i];
		}
		newPogs[newPogs.length - 1] = pogData;
		sessionLevel.players = newPogs;
	}

	public PogDataLite[] savePogToProperCollection(PogDataLite pogData, boolean needToAdd, PogDataLite[] pogCollection) {
		++version;
		dirty = true;
		if (!needToAdd) {
			updatePogCollection(pogCollection, pogData);
			return(pogCollection);
		}
		PogDataLite[] newPogs = expandCollectionAndAddPog(pogCollection, pogData);
		return(newPogs);
	}

	private PogDataLite[] expandCollectionAndAddPog(PogDataLite[] pogCollection, PogDataLite pogData) {
		PogDataLite[] newPogs = new PogDataLite[pogCollection.length + 1];
		for (int i = 0; i < pogCollection.length; ++i) {
			newPogs[i] = pogCollection[i];
		}
		newPogs[newPogs.length - 1] = pogData;
		return newPogs;
	}

	private void updatePogCollection(PogDataLite[] pogCollection, PogDataLite pogData) {
		for (PogDataLite pog : pogCollection) {
			if (pog.uuid.equals(pogData.uuid)) {
				pog.pogColumn = pogData.pogColumn;
				pog.pogRow = pogData.pogRow;
				break;
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
