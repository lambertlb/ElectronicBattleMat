package per.lambert.ebattleMat.server.serviceData;

public class DungeonSessionData {
	public int version;
	public String sessionName;
	public String dungeonUUID;
	public String sessionUUID;
	public PogData[] players = new PogData[0];
	public DungeonSessionLevel[] sessionLevels;

	public DungeonSessionData(String newSessionName, String dungeonUUID, String sessionUUID) {
		version = 1;
		sessionName = newSessionName;
		this.dungeonUUID = dungeonUUID;
		this.sessionUUID = sessionUUID;
	}

	public int getVersion() {
		return version;
	}
	public void increamentVersion() {
		if (++version == Integer.MAX_VALUE) {
			version = 1;
		}
	}
}
