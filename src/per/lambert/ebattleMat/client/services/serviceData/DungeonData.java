package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonData extends JavaScriptObject {
	protected DungeonData() {
	}

	public final native String[] getDungeons() /*-{
		return this.dungeons;
	}-*/; // (3)
}
