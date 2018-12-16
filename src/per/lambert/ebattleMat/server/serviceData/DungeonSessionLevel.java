package per.lambert.ebattleMat.server.serviceData;

public class DungeonSessionLevel {
	public boolean fogOfWar[][] = new boolean[0][0];
	public PogDataLite[] monsters = new PogDataLite[0];
	public PogDataLite[] roomObjects = new PogDataLite[0];

	public DungeonSessionLevel(DungeonLevel dungeonLevel) {
		copyMonsters(dungeonLevel);
		copyRoomObjects(dungeonLevel);
		creatFogOfWar(dungeonLevel);
	}

	private void copyMonsters(DungeonLevel dungeonLevel) {
		monsters = new PogDataLite[dungeonLevel.monsters.length];
		for (int i = 0; i < dungeonLevel.monsters.length; ++i) {
			monsters[i] = dungeonLevel.monsters[i].minimalClone();
		}
	}

	private void copyRoomObjects(DungeonLevel dungeonLevel) {
		roomObjects = new PogDataLite[dungeonLevel.roomObjects.length];
		for (int i = 0; i < dungeonLevel.roomObjects.length; ++i) {
			monsters[i] = dungeonLevel.roomObjects[i].minimalClone();
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
