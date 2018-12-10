package per.lambert.ebattleMat.server.serviceData;

public class PogData extends PogDataLite {
	public String pogName;
	public String pogImageUrl;
	public int pogSize;
	public boolean isPlayer;

	public PogData() {
	}

	public PogData(PogData pogData) {
		super(pogData);
		pogName = pogData.pogName;
		pogImageUrl = pogData.pogImageUrl;
		pogSize = pogData.pogSize;
		isPlayer = pogData.isPlayer;
	}

}
