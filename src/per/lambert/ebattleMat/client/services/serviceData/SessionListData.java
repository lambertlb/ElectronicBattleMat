package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Session list.
 * 
 * @author LLambert
 *
 */
public class SessionListData extends JavaScriptObject {
	/**
	 * Constructor.
	 */
	protected SessionListData() {
	}

	/**
	 * get UUID of dungeon.
	 * 
	 * @return UUID of dungeon
	 */
	public final native String getDungeonUUID() /*-{
		if (this.dungeonUUID === undefined) {
			this.dungeonUUID = "";
		}
		return (this.dungeonUUID);
	}-*/;

	/**
	 * Set UUID of dungeon.
	 * 
	 * @param dungeonUUID to set
	 */
	public final native void setDungeonUUID(String dungeonUUID) /*-{
		this.dungeonUUID = dungeonUUID;
	}-*/;

	/**
	 * get Session names.
	 * 
	 * @return Session names.
	 */
	public final native String[] getSessionNames() /*-{
		return this.sessionNames;
	}-*/;

	/**
	 * Get UUIDs for session names.
	 * @return UUIDs for session names
	 */
	public final native String[] getSessionUUIDs() /*-{
		return this.sessionUUIDs;
	}-*/;
}
