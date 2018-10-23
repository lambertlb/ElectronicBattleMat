package per.lambert.ebattleMat.client.services.serviceData;

public class SaveDungeonDataRequest extends ServiceRequestData {
	protected SaveDungeonDataRequest() {
	}

	public final native DungeonData getDungeonData() /*-{
		return this.dungeonData;
	}-*/;

	public final native void setDungeonData(DungeonData dungeonData) /*-{
		this.dungeonData = dungeonData;
	}-*/;
}
