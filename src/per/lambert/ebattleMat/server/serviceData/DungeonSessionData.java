package per.lambert.ebattleMat.server.serviceData;

public class DungeonSessionData {
	public String sessionName;
	public String dungeonUUID;
	public DungeonSessionLevel[] sessionLevels;

	public DungeonSessionData(String newSessionName, String dungeonUUID) {
		sessionName = newSessionName;
		this.dungeonUUID = dungeonUUID;
	}
}
