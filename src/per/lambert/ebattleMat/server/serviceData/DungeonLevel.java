package per.lambert.ebattleMat.server.serviceData;

/**
 * Server side dungeon level information.
 * 
 * @author LLambert
 *
 */
public class DungeonLevel {
	/**
	 * URL to level picture.
	 */
	private String levelDrawing;

	/**
	 * get URL to level picture.
	 * 
	 * @return URL to picture
	 */
	public String getLevelDrawing() {
		return levelDrawing;
	}

	/**
	 * Set URL to level picture.
	 * 
	 * @param levelDrawing URL to set
	 */
	public void setLevelDrawing(final String levelDrawing) {
		this.levelDrawing = levelDrawing;
	}

	/**
	 * Level name.
	 */
	private String levelName;

	/**
	 * get level name.
	 * 
	 * @return level name
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * Set level name.
	 * 
	 * @param levelName to set
	 */
	public void setLevelName(final String levelName) {
		this.levelName = levelName;
	}

	/**
	 * size of grid cell.
	 */
	private int gridSize;

	/**
	 * get size of grid cell.
	 * 
	 * @return size of grid cell
	 */
	public int getGridSize() {
		return gridSize;
	}

	/**
	 * set size of grid cell.
	 * 
	 * @param gridSize size of grid cell
	 */
	public void setGridSize(final int gridSize) {
		this.gridSize = gridSize;
	}

	/**
	 * X offset for top left of grid.
	 */
	private int gridOffsetX;

	/**
	 * get X offset for top left of grid.
	 * 
	 * @return X offset for top left of grid.
	 */
	public int getGridOffsetX() {
		return gridOffsetX;
	}

	/**
	 * set X offset for top left of grid.
	 * 
	 * @param gridOffsetX X offset for top left of grid.
	 */
	public void setGridOffsetX(final int gridOffsetX) {
		this.gridOffsetX = gridOffsetX;
	}

	/**
	 * Y offset for top left of grid.
	 */
	private int gridOffsetY;

	/**
	 * get Y offset for top left of grid.
	 * 
	 * @return Y offset for top left of grid.
	 */
	public int getGridOffsetY() {
		return gridOffsetY;
	}

	/**
	 * set Y offset for top left of grid.
	 * 
	 * @param gridOffsetY Y offset for top left of grid.
	 */
	public void setGridOffsetY(final int gridOffsetY) {
		this.gridOffsetY = gridOffsetY;
	}

	/**
	 * # of columns in level.
	 */
	private int columns;

	/**
	 * get # of columns in level.
	 * 
	 * @return # of columns in level.
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * set # of columns in level.
	 * 
	 * @param columns # of columns in level.
	 */
	public void setColumns(final int columns) {
		this.columns = columns;
	}

	/**
	 * # of rows in level.
	 */
	private int rows;

	/**
	 * get # of rows in level.
	 * 
	 * @return # of rows in level.
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * set # of rows in level.
	 * 
	 * @param rows # of rows in level.
	 */
	public void setRows(final int rows) {
		this.rows = rows;
	}

	/**
	 * List of monsters on level.
	 */
	private PogList monsters = new PogList();

	/**
	 * get List of monsters on level.
	 * @return List of monsters on level.
	 */
	public PogList getMonsters() {
		return monsters;
	}

	/**
	 * set List of monsters on level.
	 * @param monsters List of monsters on level.
	 */
	public void setMonsters(final PogList monsters) {
		this.monsters = monsters;
	}

	/**
	 * List of room objects on level.
	 */
	private PogList roomObjects = new PogList();

	/**
	 * get List of room objects on level.
	 * @return List of room objects on level.
	 */
	public PogList getRoomObjects() {
		return roomObjects;
	}

	/**
	 * set List of room objects on level.
	 * @param roomObjects List of room objects on level.
	 */
	public void setRoomObjects(final PogList roomObjects) {
		this.roomObjects = roomObjects;
	}
}
