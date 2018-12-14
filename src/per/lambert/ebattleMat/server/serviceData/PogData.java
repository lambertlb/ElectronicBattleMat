package per.lambert.ebattleMat.server.serviceData;

public class PogData extends PogDataLite {
	public String pogName;
	public String pogImageUrl;
	public String pogType;
	public int pogSize;

	public PogData() {
	}

	public PogData(PogData pogData) {
		super(pogData);
		pogName = pogData.pogName;
		pogImageUrl = pogData.pogImageUrl;
		pogType = pogData.pogType;
		pogSize = pogData.pogSize;
	}

}
