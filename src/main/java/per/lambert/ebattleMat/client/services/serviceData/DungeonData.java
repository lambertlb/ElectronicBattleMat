package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Java script class to manage Dungeon data from server.
 * 
 * @author LLambert
 *
 */
public class DungeonData extends JavaScriptObject {

	/**
	 * Constructor for dungeon data.
	 */
	protected DungeonData() {
	}

	/**
	 * Get UUID for this dungeon.
	 * 
	 * @return uuid for dungeon
	 */
	public final native String getUUID() /*-{
		if (this.uuid === undefined) {
			this.uuid = "";
		}
		return (this.uuid);
	}-*/;

	/**
	 * Set UUID for this dungeon.
	 * 
	 * @param uuid for dungeon
	 */
	public final native void setUUID(String uuid) /*-{
		this.uuid = uuid;
	}-*/;

	/**
	 * Get name of dungeon.
	 * 
	 * @return name of dungeon
	 */
	public final native String getDungeonName() /*-{
		if (this.dungeonName === undefined) {
			this.dungeonName = "";
		}
		return (this.dungeonName);
	}-*/;

	/**
	 * Set dungeon name.
	 * 
	 * @param dungeonName to set
	 */
	public final native void setDungeonName(String dungeonName) /*-{
		this.dungeonName = dungeonName;
	}-*/;

	/**
	 * get information about dungeon levels.
	 * 
	 * @return dungeon levels
	 */
	public final native DungeonLevel[] getDungeonlevels() /*-{
		if (this.dungeonLevels === undefined) {
			dungeonLevels = null;
		}
		return this.dungeonLevels;
	}-*/;

	/**
	 * Add a new dungeon level.
	 * 
	 * @param newLevel new level to add
	 */
	public final native void addDungeonlevel(DungeonLevel newLevel) /*-{
		this.dungeonLevels.push(newLevel);
	}-*/;

	/**
	 * Show grid on dungeon levels.
	 * 
	 * @param showGrid true to show
	 */
	public final native void setShowGrid(boolean showGrid) /*-{
		if (this.showGrid === undefined) {
			this.showGrid = false;
		}
		this.isDirty = showGrid != this.showGrid;
		this.showGrid = showGrid;
	}-*/;

	/**
	 * Get show grid flag.
	 * 
	 * @return true if grid is to be shown
	 */
	public final native boolean getShowGrid() /*-{
		if (this.showGrid === undefined) {
			this.showGrid = false;
		}
		return (this.showGrid);
	}-*/;

}
