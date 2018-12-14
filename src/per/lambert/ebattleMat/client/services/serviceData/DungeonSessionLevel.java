package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonSessionLevel extends JavaScriptObject {
	protected DungeonSessionLevel() {
	}

	public final native boolean[][] getFOW() /*-{
		if (this.fogOfWar === undefined) {
			return (null);
		}
		return (this.fogOfWar);
	}-*/;

	public final native void setFOW(boolean[][] fogOfWar) /*-{
		this.fogOfWar = fogOfWar;
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

	public final native PogDataLite[] getMonsters() /*-{
		if (this.monsters === undefined) {
			this.monsters = [];
		}
		return (this.monsters);
	}-*/;

	public final void addMonster(PogDataLite monster) {
		getMonsters();
		addMonsterNative(monster);
	}

	public final native void addMonsterNative(PogDataLite monster) /*-{
		this.monsters.push(monster);
	}-*/;

	public final native PogDataLite[] getRoomObjects() /*-{
		if (this.roomObjects === undefined) {
			this.roomObjects = [];
		}
		return (this.roomObjects);
	}-*/;

	public final void addRoomObject(PogDataLite roomObject) {
		getMonsters();
		addRoomObjectsNative(roomObject);
	}

	public final native void addRoomObjectsNative(PogDataLite roomObject) /*-{
		this.roomObjects.push(roomObject);
	}-*/;
}
