package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonData extends JavaScriptObject {

	protected DungeonData() {
	}

	public final native boolean getIsDirty() /*-{
		return isDirty;
	}-*/;

	public final native DungeonLevel[] getDungeonlevels() /*-{
		return this.dungeonLevels;
	}-*/;

	public final native void setShowGrid(Boolean showGrid) /*-{
		isDirty = showGrid != this.showGrid;
		this.showGrid = showGrid;
	}-*/;

	public final native Boolean getShowGrid() /*-{
		return (this.showGrid);
	}-*/;
}
