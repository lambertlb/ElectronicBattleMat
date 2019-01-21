package per.lambert.ebattleMat.server.serviceData;

public class DungeonLevel {
	public String levelDrawing;
	public String levelName;
	public int gridSize;
	public int gridOffsetX;
	public int gridOffsetY;
	public int columns;
	public int rows;
	public PogData[] monsters = new PogData[0];
	public PogData[] roomObjects = new PogData[0];
}
