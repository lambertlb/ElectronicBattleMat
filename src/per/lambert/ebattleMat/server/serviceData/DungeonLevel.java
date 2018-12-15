package per.lambert.ebattleMat.server.serviceData;

public class DungeonLevel {
	public String levelDrawing;
	public String levelName;
	public int gridSize;
	public int gridOffsetX;
	public int gridOffsetY;
	public int columns;
	public int rows;
	public PogDataLite[] monsters = new PogDataLite[0];
	public PogDataLite[] roomObjects = new PogDataLite[0];
}
