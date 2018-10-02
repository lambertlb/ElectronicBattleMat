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
}
