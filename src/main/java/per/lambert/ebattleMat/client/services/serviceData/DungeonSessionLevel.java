/*
 * Copyright (C) 2019 Leon Lambert.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * client side session level.
 * 
 * This needs to match per.lambert.ebattleMat.server.serviceData.DungeonSessionLevel
 * 
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
	 * Get fog of War version.
	 * 
	 * @return fog of War version.
	 */
	public final native int getFOWVersion() /*-{
		if (this.fogOfWarVersion === undefined) {
			this.fogOfWarVersion = 0;
		}
		return (this.fogOfWarVersion);
	}-*/;

	/**
	 * Set fow version.
	 * 
	 * @param fowVersion to set
	 */
	public final native void setFOWVersion(int fowVersion) /*-{
		this.fogOfWarVersion = fowVersion;
	}-*/;

	/**
	 * Get fog of War.
	 * 
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
	 * 
	 * @param fogOfWar fog of War.
	 */
	public final native void setFOW(boolean[][] fogOfWar) /*-{
		this.fogOfWar = fogOfWar;
	}-*/;

	/**
	 * Get fog of War.
	 * 
	 * @return fog of War.
	 */
	public final native int[] getFOWData() /*-{
		if (this.fogOfWarData === undefined) {
			return (null);
		}
		return (this.fogOfWarData);
	}-*/;

	/**
	 * set fog of War.
	 * 
	 * @param fogOfWarData fog of War.
	 */
	public final native void setFOWData(int[] fogOfWarData) /*-{
		this.fogOfWarData = fogOfWarData;
	}-*/;

	/**
	 * update fog of War.
	 * 
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
	 * 
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
	 * 
	 * @return monsters on level.
	 */
	public final native PogList getMonsters() /*-{
		if (this.monsters === undefined) {
			this.monsters = {
				"pogList" : []
			};
		}
		return (this.monsters);
	}-*/;

	/**
	 * get room objects.
	 * 
	 * @return room objects
	 */
	public final native PogList getRoomObjects() /*-{
		if (this.roomObjects === undefined) {
			this.roomObjects = {
				"pogList" : []
			};
		}
		return (this.roomObjects);
	}-*/;

	/**
	 * Migrate needed data.
	 * 
	 * @param dungeonLevel
	 * @return true if migrated
	 */
	public final boolean migrateSession(final DungeonLevel dungeonLevel) {
		setBitsPerColumn(dungeonLevel.getColumns());
		return (migrateFOW(dungeonLevel));
	}

	/**
	 * Migrate fog of war data.
	 * 
	 * @param dungeonLevel
	 * @return true if migrated
	 */
	private boolean migrateFOW(final DungeonLevel dungeonLevel) {
		boolean[][] oldData = getFOW();
		if (oldData == null) {
			return (false);
		}
		int[] newData = new int[((getBitsPerColumn() * dungeonLevel.getRows()) / 32) + 1];
		for (int row = 0; row < dungeonLevel.getRows(); ++row) {
			for (int column = 0; column < dungeonLevel.getColumns(); ++column) {
				int bitIndex = (row * getBitsPerColumn()) + column;
				int arrayIndex = bitIndex / 32;
				int bitShift = bitIndex % 32;
				int bitMask = 1 << bitShift;
				if (isFowSet(column, row)) {
					newData[arrayIndex] |= bitMask;
				} else {
					newData[arrayIndex] &= bitMask;
				}
			}
		}
		setFOWData(newData);
		return (true);
	}

	/**
	 * get amount of bits per fow column.
	 * 
	 * @return amount of bits
	 */
	public final native int getBitsPerColumn() /*-{
		if (this.bitsPerColumn === undefined) {
			this.bitsPerColumn = 0;
		}
		return (this.bitsPerColumn);
	}-*/;

	/**
	 * set amount of bits per fow column.
	 * 
	 * @param bitsPerColumn
	 */
	public final native void setBitsPerColumn(int bitsPerColumn) /*-{
		this.bitsPerColumn = bitsPerColumn;
	}-*/;

}
