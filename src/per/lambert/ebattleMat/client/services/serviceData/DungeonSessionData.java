package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonSessionData extends JavaScriptObject {

	protected DungeonSessionData() {
	}

	public final native String getSessionName() /*-{
		if (this.sessionName === undefined) {
			this.sessionName = "";
		}
		return (this.sessionName);
	}-*/;

	public final native void setSessionName(String sessionName) /*-{
		this.sessionName = sessionName;
	}-*/;

	public final native String getDungeonUUID() /*-{
		if (this.dungeonUUID === undefined) {
			this.dungeonUUID = "";
		}
		return (this.dungeonUUID);
	}-*/;

	public final native void setDungeonUUID(String dungeonUUID) /*-{
		this.dungeonUUID = dungeonUUID;
	}-*/;

	public final native String getSessionUUID() /*-{
		if (this.sessionUUID === undefined) {
			this.sessionUUID = "";
		}
		return (this.sessionUUID);
	}-*/;

	public final native DungeonSessionLevel[] getSessionLevels() /*-{
		if (this.sessionLevels === undefined) {
			sessionLevels = null;
		}
		return this.sessionLevels;
	}-*/;

	public final native boolean getIsDirty() /*-{
		if (this.isDirty === undefined) {
			this.isDirty = false;
		}
		return this.isDirty;
	}-*/;

}
