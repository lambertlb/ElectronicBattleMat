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

	public final PogDataLite[] getPlayers() {
		PogDataLite[] players = getgetPlayersNative();
		if (players == null) {
			players = new PogDataLite[0];
			setPlayersNative(players);
		}
		return (players);
	};

	private final native PogDataLite[] getgetPlayersNative() /*-{
		if (this.players === undefined) {
			return (null);
		}
		return (this.players);
	}-*/;

	private final native void setPlayersNative(PogDataLite[] players) /*-{
		this.players = players;
	}-*/;

	public final void addPlayer(PogDataLite player) {
		getPlayers();
		addPlayerNative(player);
	}

	public final native void addPlayerNative(PogDataLite player) /*-{
		this.players.push(player);
	}-*/;

	public final PogDataLite[] getMonsters() {
		PogDataLite[] monsters = getMonstersNative();
		if (monsters == null) {
			monsters = new PogDataLite[0];
			setMonstersNative(monsters);
		}
		return (monsters);
	};

	public final native PogDataLite[] getMonstersNative() /*-{
		if (this.monsters === undefined) {
			return (null);
		}
		return (this.monsters);
	}-*/;

	private final native void setMonstersNative(PogDataLite[] monsters) /*-{
		this.monsters = monsters;
	}-*/;

	public final void addMonster(PogDataLite monster) {
		getMonsters();
		addMonsterNative(monster);
	}

	public final native void addMonsterNative(PogDataLite monster) /*-{
		this.monsters.push(monster);
	}-*/;

	public final PogDataLite[] getRoomObjects() {
		PogDataLite[] roomObjects = getMonstersNative();
		if (roomObjects == null) {
			roomObjects = new PogDataLite[0];
			setMonstersNative(roomObjects);
		}
		return (roomObjects);
	};

	public final native PogDataLite[] getRoomObjectsNative() /*-{
		if (this.roomObjects === undefined) {
			return (null);
		}
		return (this.roomObjects);
	}-*/;

	private final native void setRoomObjectsNative(PogDataLite[] roomObjects) /*-{
		this.roomObjects = roomObjects;
	}-*/;

	public final void addRoomObject(PogDataLite roomObject) {
		getMonsters();
		addRoomObjectsNative(roomObject);
	}

	public final native void addRoomObjectsNative(PogDataLite roomObject) /*-{
		this.roomObjects.push(roomObject);
	}-*/;
}
