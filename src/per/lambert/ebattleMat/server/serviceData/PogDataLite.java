package per.lambert.ebattleMat.server.serviceData;

public class PogDataLite {
	public int pogColumn;
	public int pogRow;
	public String uuid;

	public PogDataLite() {
	}

	public PogDataLite(PogDataLite pogData) {
		pogColumn = pogData.pogColumn;
		pogRow = pogData.pogRow;
		uuid = pogData.uuid;
	}
	public PogDataLite minimalClone() {
		PogDataLite clone = new PogDataLite();
		clone.uuid = uuid;
		clone.pogColumn = pogColumn;
		clone.pogRow = pogRow;
		return (clone);
	}
}
