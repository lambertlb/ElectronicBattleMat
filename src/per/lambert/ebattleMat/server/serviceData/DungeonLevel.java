package per.lambert.ebattleMat.server.serviceData;

public class DungeonLevel {
	public String levelDrawing;
	public int gridSize;
	public int gridOffsetX;
	public int gridOffsetY;
	public PogDataLite[] monsters = new PogDataLite[0];
	public PogDataLite[] roomObjects = new PogDataLite[0];
}
