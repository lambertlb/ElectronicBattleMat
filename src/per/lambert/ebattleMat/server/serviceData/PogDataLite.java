package per.lambert.ebattleMat.server.serviceData;

public class PogDataLite {
	public int pogColumn;
	public int pogRow;
	public String uuid;
	public String templateUUID;
	public int pogFlags;

	public PogDataLite() {
	}

	public PogDataLite(PogDataLite pogData) {
		pogColumn = pogData.pogColumn;
		pogRow = pogData.pogRow;
		uuid = pogData.uuid;
		templateUUID = pogData.templateUUID;
		pogFlags = pogData.pogFlags;
	}
	public PogDataLite minimalClone() {
		PogDataLite clone = new PogDataLite();
		clone.uuid = uuid;
		clone.pogColumn = pogColumn;
		clone.pogRow = pogRow;
		clone.templateUUID = templateUUID;
		clone.pogFlags = pogFlags;
		return (clone);
	}
}
