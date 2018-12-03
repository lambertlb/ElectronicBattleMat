package per.lambert.ebattleMat.server.serviceData;

public class DungeonSessionData {
	public String sessionName;
	public String templateDungeonName;
	public DungeonSessionLevel[] sessionLevels;

	public DungeonSessionData(String newSessionName, String templateName) {
		sessionName = newSessionName;
		templateDungeonName = templateName;
	}
}
