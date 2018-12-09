package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class SessionListData extends JavaScriptObject {
	protected SessionListData() {
	}

	public final native String getDungeonUUID() /*-{
		if (this.dungeonUUID === undefined) {
			this.dungeonUUID = "";
		}
		return (this.dungeonUUID);
	}-*/;

	public final native void setDungeonUUID(String dungeonUUID) /*-{
		this.dungeonUUID = dungeonUUID;
	}-*/;

	public final native String[] getSessionNames() /*-{
		return this.sessionNames;
	}-*/;

	public final native String[] getSessionUUIDs() /*-{
		return this.sessionUUIDs;
	}-*/;
}
