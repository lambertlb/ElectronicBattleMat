package per.lambert.ebattleMat.server.serviceData;

public class DungeonSessionData {
	public String sessionName;
	public String dungeonUUID;
	public String sessionUUID;
	public DungeonSessionLevel[] sessionLevels;

	public DungeonSessionData(String newSessionName, String dungeonUUID, String sessionUUID) {
		sessionName = newSessionName;
		this.dungeonUUID = dungeonUUID;
		this.sessionUUID = sessionUUID;
	}
}
