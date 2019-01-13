package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonData extends JavaScriptObject {

	protected DungeonData() {
	}

	public final native String getUUID() /*-{
		if (this.uuid === undefined) {
			this.uuid = "";
		}
		return (this.uuid);
	}-*/;

	public final native void setUUID(String uuid) /*-{
		this.uuid = uuid;
	}-*/;

	public final native String getDungeonName() /*-{
		if (this.dungeonName === undefined) {
			this.dungeonName = "";
		}
		return (this.dungeonName);
	}-*/;

	public final native void setDungeonName(String dungeonName) /*-{
		this.dungeonName = dungeonName;
	}-*/;

	public final native DungeonLevel[] getDungeonlevels() /*-{
		if (this.dungeonLevels === undefined) {
			dungeonLevels = null;
		}
		return this.dungeonLevels;
	}-*/;

	public final native void addDungeonlevel(DungeonLevel newLevel) /*-{
		this.dungeonLevels.push(newLevel);
	}-*/;

	public final native void setShowGrid(boolean showGrid) /*-{
		if (this.showGrid === undefined) {
			this.showGrid = false;
		}
		this.isDirty = showGrid != this.showGrid;
		this.showGrid = showGrid;
	}-*/;

	public final native boolean getShowGrid() /*-{
		if (this.showGrid === undefined) {
			this.showGrid = false;
		}
		return (this.showGrid);
	}-*/;

	public final native boolean getIsDirty() /*-{
		if (this.isDirty === undefined) {
			this.isDirty = false;
		}
		return this.isDirty;
	}-*/;

}
