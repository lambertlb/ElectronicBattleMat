package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonData extends JavaScriptObject {

	protected DungeonData() {
	}

	public final native boolean getIsDirty() /*-{
		return this.isDirty;
	}-*/;

	public final native DungeonLevel[] getDungeonlevels() /*-{
		return this.dungeonLevels;
	}-*/;

	public final native void setShowGrid(boolean showGrid) /*-{
		this.isDirty = showGrid != (this.showGrid === undefined ? false : true);
		this.showGrid = showGrid;
	}-*/;

	public final native boolean getShowGrid() /*-{
		if (this.showGrid === undefined) {
			return (false);
		}
		return (this.showGrid);
	}-*/;

	public final native String getDungeonName() /*-{
		return (this.dungeonName);
	}-*/;

	public final native void setDungeonName(String dungeonName) /*-{
		this.dungeonName = dungeonName;
	}-*/;
}
