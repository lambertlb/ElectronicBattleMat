package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonListData extends JavaScriptObject {
	protected DungeonListData() {
	}

	public final native String getServerPath() /*-{
		return this.serverPath;
	}-*/;

	public final native String[] getDungeonNames() /*-{
		return this.dungeonNames;
	}-*/;

	public final native String[] getDungeonUUIDS() /*-{
		return this.dungeonUUIDS;
	}-*/;

	public final native String[] getDungeonDirectories() /*-{
		return this.dungeonDirectories;
	}-*/;
}
