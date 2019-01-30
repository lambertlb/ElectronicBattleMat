package per.lambert.ebattleMat.server;

import java.io.IOException;

import com.google.gson.Gson;

import per.lambert.ebattleMat.server.serviceData.DungeonSessionData;
import per.lambert.ebattleMat.server.serviceData.DungeonSessionLevel;
import per.lambert.ebattleMat.server.serviceData.PogData;

public class SessionInformation {
	private boolean dirty;

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
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
			return (sessionData.getSessionUUID());
		}
		return (null);
	}

	public SessionInformation() {
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

	public DungeonSessionLevel getSessionLevel(int currentLevel) {
		if (currentLevel < 0 || currentLevel >= sessionData.getSessionLevels().length) {
			return (null);
		}
		return (sessionData.getSessionLevels()[currentLevel]);
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
		dirty = false;
	}

	public void saveMonsterPog(PogData pogData, int currentLevel, boolean needToAdd) {
		DungeonSessionLevel sessionLevel = getSessionLevel(currentLevel);
		if (sessionLevel != null) {
			sessionLevel.setMonsters(savePogToProperCollection(pogData, needToAdd, sessionLevel.getMonsters()));
		}
	}
	public void saveRoomObjectPog(PogData pogData, int currentLevel, boolean needToAdd) {
		DungeonSessionLevel sessionLevel = getSessionLevel(currentLevel);
		if (sessionLevel != null) {
			sessionLevel.setRoomObjects(savePogToProperCollection(pogData, needToAdd, sessionLevel.getRoomObjects()));
		}
	}

	public void savePlayerPog(PogData pogData, int currentLevel, boolean needToAdd) {
		sessionData.increamentVersion();
		dirty = true;
		if (!needToAdd) {
			updatePogCollection(sessionData.getPlayers(), pogData);
			return;
		}
		PogData[] newPogs = new PogData[sessionData.getPlayers().length + 1];
		for (int i = 0; i < sessionData.getPlayers().length; ++i) {
			newPogs[i] = sessionData.getPlayers()[i];
		}
		newPogs[newPogs.length - 1] = pogData;
		sessionData.setPlayers(newPogs);
	}

	public PogData[] savePogToProperCollection(PogData pogData, boolean needToAdd, PogData[] pogCollection) {
		sessionData.increamentVersion();
		dirty = true;
		if (!needToAdd) {
			updatePogCollection(pogCollection, pogData);
			return (pogCollection);
		}
		PogData[] newPogs = expandCollectionAndAddPog(pogCollection, pogData);
		return (newPogs);
	}

	private PogData[] expandCollectionAndAddPog(PogData[] pogCollection, PogData pogData) {
		PogData[] newPogs = new PogData[pogCollection.length + 1];
		for (int i = 0; i < pogCollection.length; ++i) {
			newPogs[i] = pogCollection[i];
		}
		newPogs[newPogs.length - 1] = pogData;
		return newPogs;
	}

	private void updatePogCollection(PogData[] pogCollection, PogData pogData) {
		for (PogData pog : pogCollection) {
			if (pog.uuid.equals(pogData.uuid)) {
				pog.pogColumn = pogData.pogColumn;
				pog.pogRow = pogData.pogRow;
				pog.dungeonLevel = pogData.dungeonLevel;
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

	public void updateFOW(boolean[][] fowData, int currentLevel) {
		DungeonSessionLevel sessionLevel = getSessionLevel(currentLevel);
		if (sessionLevel == null) {
			return;
		}
		sessionLevel.updateFOW(fowData);
		sessionData.increamentVersion();
		dirty = true;
	}
}
