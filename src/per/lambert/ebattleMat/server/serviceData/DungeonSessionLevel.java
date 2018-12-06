package per.lambert.ebattleMat.server.serviceData;

public class DungeonSessionLevel {
	boolean fogOfWar[][] = new boolean[0][0];
	public PogData[] players;
	public PogData[] monsters = new PogData[0];
	public PogData[] roomObjects = new PogData[0];
	public DungeonSessionLevel(DungeonLevel dungeonLevel) {
		copyMonsters(dungeonLevel);
		copyRoomObjects(dungeonLevel);
	}
	private void copyMonsters(DungeonLevel dungeonLevel) {
		monsters = new PogData[dungeonLevel.monsters.length];
		for (int i = 0; i < dungeonLevel.monsters.length ; ++i) {
			monsters[i] = dungeonLevel.monsters[i].minimalClone();
		}
	}
	private void copyRoomObjects(DungeonLevel dungeonLevel) {
		roomObjects = new PogData[dungeonLevel.roomObjects.length];
		for (int i = 0; i < dungeonLevel.roomObjects.length ; ++i) {
			monsters[i] = dungeonLevel.roomObjects[i].minimalClone();
		}
	}
}
