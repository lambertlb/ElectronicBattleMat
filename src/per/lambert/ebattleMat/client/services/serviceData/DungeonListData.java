package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonListData extends JavaScriptObject {
	protected DungeonListData() {
	}

	public final native String[] getDungeons() /*-{
		return this.dungeonList;
	}-*/;
}
