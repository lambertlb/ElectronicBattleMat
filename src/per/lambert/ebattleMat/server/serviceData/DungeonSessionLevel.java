package per.lambert.ebattleMat.server.serviceData;

public class DungeonSessionLevel {
	public boolean fogOfWar[][] = new boolean[0][0];
	public PogData[] monsters = new PogData[0];
	public PogData[] roomObjects = new PogData[0];

	public DungeonSessionLevel(DungeonLevel dungeonLevel) {
		copyMonsters(dungeonLevel);
		copyRoomObjects(dungeonLevel);
		creatFogOfWar(dungeonLevel);
	}

	private void copyMonsters(DungeonLevel dungeonLevel) {
		monsters = new PogData[dungeonLevel.monsters.length];
		for (int i = 0; i < dungeonLevel.monsters.length; ++i) {
			monsters[i] = dungeonLevel.monsters[i].clone();
		}
	}

	private void copyRoomObjects(DungeonLevel dungeonLevel) {
		roomObjects = new PogData[dungeonLevel.roomObjects.length];
		for (int i = 0; i < dungeonLevel.roomObjects.length; ++i) {
			roomObjects[i] = dungeonLevel.roomObjects[i].clone();
		}
	}

	private void creatFogOfWar(DungeonLevel dungeonLevel) {
		fogOfWar = new boolean[dungeonLevel.columns][dungeonLevel.rows];
		for (int i = 0; i < dungeonLevel.columns ;++i) {
			for (int j= 0; j < dungeonLevel.rows ;++j) {
				fogOfWar[i][j] = true;
			}
		}
	}
	public void updateFOW(boolean[][] fowData) {
		if (fowData != null) {
			fogOfWar = fowData;
		}
	}
}
