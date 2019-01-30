package per.lambert.ebattleMat.server.serviceData;

/**
 * Session Level.
 * 
 * @author LLambert
 *
 */
public class DungeonSessionLevel {
	/**
	 * Array for Fog of War.
	 */
	private boolean[][] fogOfWar = new boolean[0][0];

	/**
	 * get Array for Fog of War.
	 * 
	 * @return Array for Fog of War.
	 */
	public boolean[][] getFogOfWar() {
		return fogOfWar;
	}

	/**
	 * set Array for Fog of War.
	 * 
	 * @param fogOfWar Array for Fog of War.
	 */
	public void setFogOfWar(final boolean[][] fogOfWar) {
		this.fogOfWar = fogOfWar;
	}

	/**
	 * list of monsters.
	 */
	private PogData[] monsters = new PogData[0];

	/**
	 * get list of monsters.
	 * 
	 * @return list of monsters.
	 */
	public PogData[] getMonsters() {
		return monsters;
	}

	/**
	 * set list of monsters.
	 * 
	 * @param monsters list of monsters.
	 */
	public void setMonsters(final PogData[] monsters) {
		this.monsters = monsters;
	}

	/**
	 * list of room objects.
	 */
	private PogData[] roomObjects = new PogData[0];

	/**
	 * get list of room objects.
	 * 
	 * @return list of room objects.
	 */
	public PogData[] getRoomObjects() {
		return roomObjects;
	}

	/**
	 * set list of room objects.
	 * 
	 * @param roomObjects list of room objects.
	 */
	public void setRoomObjects(final PogData[] roomObjects) {
		this.roomObjects = roomObjects;
	}

	/**
	 * constructor for session level.
	 * 
	 * @param dungeonLevel dungeon level to copy
	 */
	public DungeonSessionLevel(final DungeonLevel dungeonLevel) {
		copyMonsters(dungeonLevel);
		copyRoomObjects(dungeonLevel);
		creatFogOfWar(dungeonLevel);
	}

	/**
	 * Copy monsters from dungeon level.
	 * 
	 * @param dungeonLevel dungeon level with monsters
	 */
	private void copyMonsters(final DungeonLevel dungeonLevel) {
		monsters = new PogData[dungeonLevel.getMonsters().length];
		for (int i = 0; i < dungeonLevel.getMonsters().length; ++i) {
			monsters[i] = dungeonLevel.getMonsters()[i].clone();
		}
	}

	/**
	 * Copy room objects from dungeon level.
	 * 
	 * @param dungeonLevel dungeon level with room objects
	 */
	private void copyRoomObjects(final DungeonLevel dungeonLevel) {
		roomObjects = new PogData[dungeonLevel.getRoomObjects().length];
		for (int i = 0; i < dungeonLevel.getRoomObjects().length; ++i) {
			roomObjects[i] = dungeonLevel.getRoomObjects()[i].clone();
		}
	}

	/**
	 * Create fog of war array.
	 * 
	 * @param dungeonLevel dungeon level with needed information
	 */
	private void creatFogOfWar(final DungeonLevel dungeonLevel) {
		fogOfWar = new boolean[dungeonLevel.getColumns()][dungeonLevel.getRows()];
		for (int i = 0; i < dungeonLevel.getColumns(); ++i) {
			for (int j = 0; j < dungeonLevel.getRows(); ++j) {
				fogOfWar[i][j] = true;
			}
		}
	}

	/**
	 * update Fog of war.
	 * 
	 * @param fowData fog of war
	 */
	public void updateFOW(final boolean[][] fowData) {
		this.fogOfWar = fowData;
	}
}
