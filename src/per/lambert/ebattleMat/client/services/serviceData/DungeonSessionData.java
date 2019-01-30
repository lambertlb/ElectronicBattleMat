package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Client side session data.
 * 
 * @author LLambert
 *
 */
public class DungeonSessionData extends JavaScriptObject {

	/**
	 * Constructor for session data.
	 */
	protected DungeonSessionData() {
	}

	/**
	 * get session version.
	 * 
	 * @return session version
	 */
	public final native int getVersion() /*-{
		if (this.version === undefined) {
			this.version = 0;
		}
		return (this.version);
	}-*/;

	/**
	 * Get session name.
	 * 
	 * @return session name
	 */
	public final native String getSessionName() /*-{
		if (this.sessionName === undefined) {
			this.sessionName = "";
		}
		return (this.sessionName);
	}-*/;

	/**
	 * Set session name.
	 * 
	 * @param sessionName session name.
	 */
	public final native void setSessionName(String sessionName) /*-{
		this.sessionName = sessionName;
	}-*/;

	/**
	 * get dungeon UUID.
	 * 
	 * @return dungeon UUID.
	 */
	public final native String getDungeonUUID() /*-{
		if (this.dungeonUUID === undefined) {
			this.dungeonUUID = "";
		}
		return (this.dungeonUUID);
	}-*/;

	/**
	 * set dungeon UUID.
	 * 
	 * @param dungeonUUID dungeon UUID.
	 */
	public final native void setDungeonUUID(String dungeonUUID) /*-{
		this.dungeonUUID = dungeonUUID;
	}-*/;

	/**
	 * set session UUID.
	 * 
	 * @return session UUID.
	 */
	public final native String getSessionUUID() /*-{
		if (this.sessionUUID === undefined) {
			this.sessionUUID = "";
		}
		return (this.sessionUUID);
	}-*/;

	/**
	 * Get list of players.
	 * 
	 * @return list of players.
	 */
	public final native PogData[] getPlayers() /*-{
		if (this.players === undefined) {
			this.players = [];
		}
		return (this.players);
	}-*/;

	/**
	 * Add player.
	 * 
	 * @param player to add
	 */
	public final void addPlayer(final PogData player) {
		getPlayers();
		addPlayerNative(player);
	}

	/**
	 * Add player.
	 * 
	 * @param player to add
	 */
	public final native void addPlayerNative(PogData player) /*-{
		this.players.push(player);
	}-*/;

	/**
	 * get levels for session.
	 * 
	 * @return levels for session
	 */
	public final native DungeonSessionLevel[] getSessionLevels() /*-{
		if (this.sessionLevels === undefined) {
			this.sessionLevels = [];
		}
		return this.sessionLevels;
	}-*/;
}
