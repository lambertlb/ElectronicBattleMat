package per.lambert.ebattleMat.server.serviceData;

public class PogDataLite {
	public int pogColumn;
	public int pogRow;
	public String uuid;
	public String templateUUID;
	public int playerFlags;
	public int dungeonMasterFlags;
	public int dungeonLevel;

	public PogDataLite() {
	}

	public PogDataLite(PogDataLite pogData) {
		pogColumn = pogData.pogColumn;
		pogRow = pogData.pogRow;
		uuid = pogData.uuid;
		templateUUID = pogData.templateUUID;
		playerFlags = pogData.playerFlags;
		dungeonMasterFlags = pogData.dungeonMasterFlags;
		dungeonLevel = pogData.dungeonLevel;
	}

	public PogDataLite minimalClone() {
		PogDataLite clone = new PogDataLite();
		clone.uuid = uuid;
		clone.pogColumn = pogColumn;
		clone.pogRow = pogRow;
		clone.dungeonLevel = dungeonLevel;
		clone.templateUUID = templateUUID;
		clone.playerFlags = playerFlags;
		clone.dungeonMasterFlags = dungeonMasterFlags;
		return (clone);
	}
}
