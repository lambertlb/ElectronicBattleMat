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
		monsters = new PogData[dungeonLevel.getMonsters().length];
		for (int i = 0; i < dungeonLevel.getMonsters().length; ++i) {
			monsters[i] = dungeonLevel.getMonsters()[i].clone();
		}
	}

	private void copyRoomObjects(DungeonLevel dungeonLevel) {
		roomObjects = new PogData[dungeonLevel.getRoomObjects().length];
		for (int i = 0; i < dungeonLevel.getRoomObjects().length; ++i) {
			roomObjects[i] = dungeonLevel.getRoomObjects()[i].clone();
		}
	}

	private void creatFogOfWar(DungeonLevel dungeonLevel) {
		fogOfWar = new boolean[dungeonLevel.getColumns()][dungeonLevel.getRows()];
		for (int i = 0; i < dungeonLevel.getColumns() ;++i) {
			for (int j= 0; j < dungeonLevel.getRows() ;++j) {
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
