package per.lambert.ebattleMat.server.serviceData;

public class PogData {
	public String pogName;
	public int pogColumn;
	public int pogRow;
	public String uuid;

	public PogData() {
	}

	public PogData(PogData pogData) {
		pogName = pogData.pogName;
		pogColumn = pogData.pogColumn;
		pogRow = pogData.pogRow;
		uuid = pogData.uuid;
	}

	public PogData minimalClone() {
		PogData clone = new PogData();
		clone.uuid = uuid;
		clone.pogColumn = pogColumn;
		clone.pogRow = pogRow;
		return (clone);
	}
}
