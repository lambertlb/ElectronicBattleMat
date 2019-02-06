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
	private PogList monsters = new PogList();

	/**
	 * get list of monsters.
	 * 
	 * @return list of monsters.
	 */
	public PogList getMonsters() {
		return monsters;
	}

	/**
	 * set list of monsters.
	 * 
	 * @param monsters list of monsters.
	 */
	public void setMonsters(final PogList monsters) {
		this.monsters = monsters;
	}

	/**
	 * list of room objects.
	 */
	private PogList roomObjects = new PogList();

	/**
	 * get list of room objects.
	 * 
	 * @return list of room objects.
	 */
	public PogList getRoomObjects() {
		return roomObjects;
	}

	/**
	 * set list of room objects.
	 * 
	 * @param roomObjects list of room objects.
	 */
	public void setRoomObjects(final PogList roomObjects) {
		this.roomObjects = roomObjects;
	}

	/**
	 * constructor for session level.
	 * 
	 * @param dungeonLevel dungeon level to copy
	 */
	public DungeonSessionLevel(final DungeonLevel dungeonLevel) {
		monsters = dungeonLevel.getMonsters().clone();
		monsters = dungeonLevel.getRoomObjects().clone();
		creatFogOfWar(dungeonLevel);
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
