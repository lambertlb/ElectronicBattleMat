package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonLevel extends JavaScriptObject {
	protected DungeonLevel() {
	}
	public final native String getLevelDrawing() /*-{
		return this.levelDrawing;
	}-*/;

	public final native void setLevelDrawing(String levelDrawing) /*-{
		this.levelDrawing = levelDrawing;
	}-*/;
}
