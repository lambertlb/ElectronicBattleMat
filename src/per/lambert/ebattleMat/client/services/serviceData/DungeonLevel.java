package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonLevel extends JavaScriptObject {
	protected DungeonLevel() {
	}

	public final native String getLevelDrawing() /*-{
		return this.levelDrawing;
	}-*/;

	public final native void setLevelDrawing(String levelDrawing) /*-{
		this.levelDrawing = levelDrawing;
	}-*/;

	public final native double getGridSize() /*-{
		if (this.gridSize === undefined) {
			return (30);
		}
		return this.gridSize;
	}-*/;

	public final native String getLevelName() /*-{
		return this.levelName;
	}-*/;

	public final native void setGridSize(double gridSize) /*-{
		this.gridSize = gridSize;
	}-*/;

	public final native double getGridOffsetX() /*-{
		if (this.gridOffsetX === undefined) {
			return (0);
		}
		return this.gridOffsetX;
	}-*/;

	public final native void setGridOffsetX(double offset) /*-{
		this.gridOffsetX = offset;
	}-*/;

	public final native double getGridOffsetY() /*-{
		if (this.gridOffsetY === undefined) {
			return (0);
		}
		return this.gridOffsetY;
	}-*/;

	public final native void setGridOffsetY(double offset) /*-{
		this.gridOffsetY = offset;
	}-*/;

	public final PogDataLite[] getMonsters() {
		PogDataLite[] monsters = getMonstersNative();
		if (monsters == null) {
			setMonstersNative(new PogDataLite[0]);
		}
		return (monsters);
	};

	private final native PogDataLite[] getMonstersNative() /*-{
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
	};

	private final native void addMonsterNative(PogDataLite monster) /*-{
		this.monsters.push(monster);
	}-*/;

	public final PogDataLite[] getRoomObjects() {
		PogDataLite[] roomObjects = getMonstersNative();
		if (roomObjects == null) {
			setMonstersNative(new PogDataLite[0]);
		}
		return (roomObjects);
	};

	private final native PogDataLite[] getRoomObjectsNative() /*-{
		if (this.roomObjects === undefined) {
			return (null);
		}
		return (this.roomObjects);
	}-*/;

	private final native void setRoomObjects(PogDataLite[] roomObjects) /*-{
		this.roomObjects = roomObjects;
	}-*/;

}
