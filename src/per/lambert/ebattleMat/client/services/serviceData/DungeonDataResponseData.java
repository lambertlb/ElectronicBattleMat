package per.lambert.ebattleMat.client.services.serviceData;

public class DungeonDataResponseData extends ServiceRequestData {
	protected DungeonDataResponseData() {
	}

	public final native DungeonData[] getDungeonData() /*-{
		if (this.dungeonData === undefined) {
			this.dungeonData = null;
		}
		return this.dungeonData;
	}-*/;

}
