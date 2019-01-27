package per.lambert.ebattleMat.server.serviceData;

/**
 * Dungeon data server side.
 * @author LLambert
 *
 */
public class DungeonData {
	/**
	 * UUID for dungeon.
	 */
	private String uuid;
	/**
	 * get uuid for dungeon.
	 * @return uuid for dungeon
	 */
	public String getUuid() {
		return uuid;
	}
	/**
	 * set uuid for dungeon.
	 * @param uuid for dungeon
	 */
	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}
	/**
	 * Name of dungeon.
	 */
	private String dungeonName;
	/**
	 * Get dungeon name.
	 * @return dungeon name
	 */
	public String getDungeonName() {
		return dungeonName;
	}
	/**
	 * Set dungeon name.
	 * @param dungeonName dungeon name.

	 */
	public void setDungeonName(final String dungeonName) {
		this.dungeonName = dungeonName;
	}
	/**
	 * dungeon level information.
	 */
	private DungeonLevel[] dungeonLevels;
	/**
	 * get dungeon levels.
	 * @return dungeon levels
	 */
	public DungeonLevel[] getDungeonLevels() {
		return dungeonLevels;
	}
	/**
	 * set dungeon levels.
	 * @param dungeonLevels dungeon Levels
	 */
	public void setDungeonLevels(final DungeonLevel[] dungeonLevels) {
		this.dungeonLevels = dungeonLevels;
	}
	/**
	 * Show grid on levels.
	 */
	private boolean showGrid;
	/**
	 * get show grid.
	 * @return true if shown
	 */
	public boolean isShowGrid() {
		return showGrid;
	}
	/**
	 * Set show grid.
	 * @param showGrid true if shown
	 */
	public void setShowGrid(final boolean showGrid) {
		this.showGrid = showGrid;
	}
}
