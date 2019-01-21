package per.lambert.ebattleMat.server.serviceData;

public class PogData {
	public int pogSize;
	public int pogColumn;
	public int pogRow;
	public int playerFlags;
	public int dungeonMasterFlags;
	public int dungeonLevel;
	public String pogName;
	public String pogImageUrl;
	public String pogType;
	public String uuid;
	public String templateUUID;
	public String pogClass;
	public String race;

	public PogData() {
	}

	public PogData(PogData pogData) {
		pogSize = pogData.pogSize;
		pogColumn = pogData.pogColumn;
		pogRow = pogData.pogRow;
		playerFlags = pogData.playerFlags;
		dungeonMasterFlags = pogData.dungeonMasterFlags;
		dungeonLevel = pogData.dungeonLevel;
		pogName = pogData.pogName;
		pogImageUrl = pogData.pogImageUrl;
		pogType = pogData.pogType;
		uuid = pogData.uuid;
		templateUUID = pogData.templateUUID;
		pogClass = pogData.pogClass;
		race = pogData.race;
	}

	public PogData clone() {
		PogData clone = new PogData();
		clone.pogSize = pogSize;
		clone.pogColumn = pogColumn;
		clone.pogRow = pogRow;
		clone.playerFlags = playerFlags;
		clone.dungeonMasterFlags = dungeonMasterFlags;
		clone.dungeonLevel = dungeonLevel;
		clone.pogName = pogName;
		clone.pogImageUrl = pogImageUrl;
		clone.pogType = pogType;
		clone.uuid = uuid;
		clone.templateUUID = templateUUID;
		clone.pogClass = pogClass;
		clone.race = race;
		return (clone);
	}

}
