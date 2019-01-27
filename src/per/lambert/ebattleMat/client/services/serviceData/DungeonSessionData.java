package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonSessionData extends JavaScriptObject {

	protected DungeonSessionData() {
	}

	public final native int getVersion() /*-{
		if (this.version === undefined) {
			this.version = 0;
		}
		return (this.version);
	}-*/;

	public final native String getSessionName() /*-{
		if (this.sessionName === undefined) {
			this.sessionName = "";
		}
		return (this.sessionName);
	}-*/;

	public final native void setSessionName(String sessionName) /*-{
		this.sessionName = sessionName;
	}-*/;

	public final native String getDungeonUUID() /*-{
		if (this.dungeonUUID === undefined) {
			this.dungeonUUID = "";
		}
		return (this.dungeonUUID);
	}-*/;

	public final native void setDungeonUUID(String dungeonUUID) /*-{
		this.dungeonUUID = dungeonUUID;
	}-*/;

	public final native String getSessionUUID() /*-{
		if (this.sessionUUID === undefined) {
			this.sessionUUID = "";
		}
		return (this.sessionUUID);
	}-*/;

	public final native PogData[] getPlayers() /*-{
		if (this.players === undefined) {
			this.players = [];
		}
		return (this.players);
	}-*/;

	public final void addPlayer(PogData player) {
		getPlayers();
		addPlayerNative(player);
	}

	public final native void addPlayerNative(PogData player) /*-{
		this.players.push(player);
	}-*/;

	public final native DungeonSessionLevel[] getSessionLevels() /*-{
		if (this.sessionLevels === undefined) {
			this.sessionLevels = [];
		}
		return this.sessionLevels;
	}-*/;
}
