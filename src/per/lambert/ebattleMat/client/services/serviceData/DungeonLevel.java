package per.lambert.ebattleMat.client.services.serviceData;

public class DungeonLevel extends DungeonSessionLevel {
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

	public final native void setLevelName(String levelName) /*-{
		this.levelName = levelName;
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

	public final native int getColumns() /*-{
		if (this.columns === undefined) {
			return (0);
		}
		return this.columns;
	}-*/;

	public final native void setColumns(int columns) /*-{
		this.columns = columns;
	}-*/;

	public final native int getRows() /*-{
		if (this.rows === undefined) {
			return (0);
		}
		return this.rows;
	}-*/;

	public final native void setRows(int rows) /*-{
		this.rows = rows;
	}-*/;
}
