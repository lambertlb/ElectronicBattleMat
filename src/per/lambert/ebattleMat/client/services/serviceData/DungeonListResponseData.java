package per.lambert.ebattleMat.client.services.serviceData;

public class DungeonListResponseData extends ServiceRequestData {
	protected DungeonListResponseData() {
	}

	public final native String[] getDungeons() /*-{
		return this.dungeons;
	}-*/;

}
