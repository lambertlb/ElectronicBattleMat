package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class SessionListData extends JavaScriptObject {
	protected SessionListData() {
	}

	public final native String getDungeonName() /*-{
		if (this.dungeonName === undefined) {
			this.dungeonName = "";
		}
		return (this.dungeonName);
	}-*/;

	public final native void setDungeonName(String dungeonName) /*-{
		this.dungeonName = dungeonName;
	}-*/;

	public final native String[] getSessionNames() /*-{
		return this.sessionNames;
	}-*/;

	public final native String[] getSessionDirectories() /*-{
		return this.sessionDirectories;
	}-*/;
}
