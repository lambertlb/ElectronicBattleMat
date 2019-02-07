package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * client side session level.
 * @author LLambert
 *
 */
public class DungeonSessionLevel extends JavaScriptObject {
	/**
	 * constructor for session level.
	 */
	protected DungeonSessionLevel() {
	}

	/**
	 * Get fog of War.
	 * @return fog of War.
	 */
	public final native boolean[][] getFOW() /*-{
		if (this.fogOfWar === undefined) {
			return (null);
		}
		return (this.fogOfWar);
	}-*/;

	/**
	 * set fog of War.
	 * @param fogOfWar fog of War.
	 */
	public final native void setFOW(boolean[][] fogOfWar) /*-{
		this.fogOfWar = fogOfWar;
	}-*/;

	/**
	 * update fog of War.
	 * @param columns number of columns
	 * @param rows number of rows
	 * @param value value for cell
	 */
	public final void updateFOW(final int columns, final int rows, final boolean value) {
		boolean[][] fowGrid = getFOW();
		if (fowGrid == null) {
			return;
		}
		fowGrid[columns][rows] = value;
	}

	/**
	 * is fog of War set for this cell.
	 * @param column to check
	 * @param row to check
	 * @return value of cell
	 */
	public final boolean isFowSet(final int column, final int row) {
		boolean[][] fowGrid = getFOW();
		if (fowGrid == null) {
			return (false);
		}
		return (fowGrid[column][row]);
	}

	/**
	 * Get monsters on level.
	 * @return monsters on level.
	 */
	public final native PogList getMonsters() /*-{
		if (this.monsters === undefined) {
			this.monsters = {"pogList": []};
		}
		return (this.monsters);
	}-*/;

	/**
	 * get room objects.
	 * @return room objects
	 */
	public final native PogList getRoomObjects() /*-{
		if (this.roomObjects === undefined) {
			this.roomObjects = {"pogList": []};
		}
		return (this.roomObjects);
	}-*/;
}
