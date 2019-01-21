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

	public final void updateFOW(int columns, int rows, boolean value) {
		boolean[][] fowGrid = getFOW();
		if (fowGrid == null) {
			return;
		}
		fowGrid[columns][rows] = value;
	}

	public final boolean isFowSet(int columns, int rows) {
		boolean[][] fowGrid = getFOW();
		if (fowGrid == null) {
			return (false);
		}
		return (fowGrid[columns][rows]);
	}

	public final native PogData[] getMonsters() /*-{
		if (this.monsters === undefined) {
			this.monsters = [];
		}
		return (this.monsters);
	}-*/;

	public final void addMonster(PogData monster) {
		getMonsters();
		addMonsterNative(monster);
	}

	public final native void addMonsterNative(PogData monster) /*-{
		this.monsters.push(monster);
	}-*/;

	public final native PogData[] getRoomObjects() /*-{
		if (this.roomObjects === undefined) {
			this.roomObjects = [];
		}
		return (this.roomObjects);
	}-*/;

	public final void addRoomObject(PogData roomObject) {
		getRoomObjects();
		addRoomObjectsNative(roomObject);
	}

	public final native void addRoomObjectsNative(PogData roomObject) /*-{
		this.roomObjects.push(roomObject);
	}-*/;
}
