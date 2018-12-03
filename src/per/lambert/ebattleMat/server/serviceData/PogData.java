package per.lambert.ebattleMat.server.serviceData;

public class PogData {
	public String pogName;
	public int pogColumn;
	public int pogRow;
	public String uuid;

	public PogData(PogData pogData) {
		pogName = pogData.pogName;
		pogColumn = pogData.pogColumn;
		pogRow = pogData.pogRow;
		uuid = pogData.uuid;
	}
}
