package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Message from server with a list of dungeons.
 * @author LLambert
 *
 */
public class DungeonListData extends JavaScriptObject {
	/**
	 * constructor for list of dungeons.
	 */
	protected DungeonListData() {
	}
	/**
	 * get array of dungeon names.
	 * @return array of dungeon names
	 */
	public final native String[] getDungeonNames() /*-{
		return this.dungeonNames;
	}-*/;
	/**
	 * get array of UUIDs associated with the dungeon names.
	 * @return array of UUIDs associated with the dungeon names.
	 */
	public final native String[] getDungeonUUIDS() /*-{
		return this.dungeonUUIDS;
	}-*/;
	/**
	 * get array of directory names associated with the dungeon names.
	 * @return array of directory names associated with the dungeon names.
	 */
	public final native String[] getDungeonDirectories() /*-{
		return this.dungeonDirectories;
	}-*/;
}
