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
import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.interfaces.Constants;
import per.lambert.ebattleMat.client.interfaces.DungeonMasterFlag;
import per.lambert.ebattleMat.client.interfaces.PlayerFlag;

/**
 * pog data.
 * 
 * This contains all data pertaining to a pog regardless of type.
 * 
 * @author LLambert
 *
 */
public class PogData extends JavaScriptObject {
	/**
	 * Constructor.
	 */
	protected PogData() {
	}

	/**
	 * Get pog name.
	 * 
	 * @return pog name.
	 */
	public final native String getName() /*-{
		if (this.pogName === undefined) {
			this.pogName = "";
		}
		return (this.pogName);
	}-*/;

	/**
	 * Set pog name.
	 * 
	 * @param pogName pog name.
	 */
	public final native void setName(String pogName) /*-{
		this.pogName = pogName;
	}-*/;

	/**
	 * get pog image URL.
	 * 
	 * If image is part of the dungeon data then the URL starts with "dungeonData/" for example "dungeonData/resources/roomObjects/SecretDoorTop.png"
	 * 
	 * @return pog image URL.
	 */
	public final native String getImageUrl() /*-{
		if (this.pogImageUrl === undefined) {
			this.pogImageUrl = "";
		}
		return (this.pogImageUrl);
	}-*/;

	/**
	 * set pog image URL.
	 * 
	 * @param pogImageUrl to set
	 */
	public final native void setImageUrl(String pogImageUrl) /*-{
		this.pogImageUrl = pogImageUrl;
	}-*/;

	/**
	 * Get pog type.
	 * 
	 * @return pog type
	 */
	public final native String getType() /*-{
		if (this.pogType === undefined) {
			this.pogType = "";
		}
		return (this.pogType);
	}-*/;

	/**
	 * set pog type.
	 * 
	 * @param pogType to set
	 */
	public final native void setType(String pogType) /*-{
		this.pogType = pogType;
	}-*/;

	/**
	 * Get pog size in grid squares.
	 * 
	 * @return pog size in grid squares.
	 */
	public final native int getSize() /*-{
		if (this.pogSize === undefined) {
			this.pogSize = 1;
		}
		return (this.pogSize);
	}-*/;

	/**
	 * Set pog size in grid squares.
	 * 
	 * @param pogSize pog size in grid squares.
	 */
	public final native void setSize(int pogSize) /*-{
		this.pogSize = pogSize;
	}-*/;

	/**
	 * Is this a player pog.
	 * 
	 * @return true if player
	 */
	public final boolean isThisAPlayer() {
		String type = getType();
		return (type.equals(Constants.POG_TYPE_PLAYER));
	}

	/**
	 * Is this a monster pog.
	 * 
	 * @return true if monster
	 */
	public final boolean isThisAMonster() {
		String type = getType();
		return (type.equals(Constants.POG_TYPE_MONSTER));
	}

	/**
	 * Is this a room object pog.
	 * 
	 * @return true if room object.
	 */
	public final boolean isThisARoomObject() {
		String type = getType();
		return (type.equals(Constants.POG_TYPE_ROOMOBJECT));
	}

	/**
	 * get column pog is in.
	 * 
	 * @return column pog is in.
	 */
	public final native int getColumn() /*-{
		if (this.pogColumn === undefined) {
			this.pogColumn = -1;
		}
		return (this.pogColumn);
	}-*/;

	/**
	 * Set column pog is in.
	 * 
	 * @param pogColumn column pog is in.
	 */
	public final native void setColumn(int pogColumn) /*-{
		this.pogColumn = pogColumn;
	}-*/;

	/**
	 * get row pog is in.
	 * 
	 * @return row pog is in.
	 */
	public final native int getRow() /*-{
		if (this.pogRow === undefined) {
			this.pogRow = -1;
		}
		return (this.pogRow);
	}-*/;

	/**
	 * set row pog is in.
	 * 
	 * @param pogRow row pog is in.
	 */
	public final native void setRow(int pogRow) /*-{
		this.pogRow = pogRow;
	}-*/;

	/**
	 * get pog UUID.
	 * 
	 * @return pog UUID.
	 */
	public final native String getUUID() /*-{
		if (this.uuid === undefined) {
			return ("");
		}
		return (this.uuid);
	}-*/;

	/**
	 * Set pog UUID.
	 * 
	 * This is unique for every pog instance.
	 * 
	 * @param uuid pog UUID.
	 */
	public final native void setUUID(String uuid) /*-{
		this.uuid = uuid;
	}-*/;

	/**
	 * Get dungeon level this pog is on.
	 * 
	 * @return dungeon level this pog is on.
	 */
	public final native int getDungeonLevel() /*-{
		if (this.dungeonLevel === undefined) {
			this.dungeonLevel = 0;
		}
		return (this.dungeonLevel);
	}-*/;

	/**
	 * set dungeon level this pog is on.
	 * 
	 * @param dungeonLevel dungeon level this pog is on.
	 */
	public final native void setDungeonLevel(int dungeonLevel) /*-{
		this.dungeonLevel = dungeonLevel;
	}-*/;

	/**
	 * Is this dungeon master flag set.
	 * 
	 * @param flagToTest flag to test.
	 * @return true if set.
	 */
	public final boolean isFlagSet(final DungeonMasterFlag flagToTest) {
		return ((getDungeonMasterFlags() & flagToTest.getValue()) != 0);
	}

	/**
	 * Is this player master flag set.
	 * 
	 * @param flagToTest flag to test.
	 * @return true if set.
	 */
	public final boolean isFlagSet(final PlayerFlag flagToTest) {
		return ((getPlayerFlags() & flagToTest.getValue()) != 0);
	}

	/**
	 * Clear player flags.
	 */
	public final native void clearPlayerFlags() /*-{
		this.playerFlags = 0;
	}-*/;

	/**
	 * Clear dungeon master flags.
	 */
	public final native void clearDungeonMasterFlags() /*-{
		this.dungeonMasterFlags = 0;
	}-*/;

	/**
	 * Set player flag.
	 * 
	 * @param flags to set
	 */
	public final native void setPlayerFlagsNative(int flags) /*-{
		this.playerFlags = flags;
	}-*/;

	/**
	 * Set dungeon master flag flag.
	 * 
	 * @param flags to set
	 */
	public final native void setDungeonMasterFlagsNative(int flags) /*-{
		this.dungeonMasterFlags = flags;
	}-*/;

	/**
	 * Get player flags.
	 * 
	 * @return player flags.
	 */
	public final native int getPlayerFlags() /*-{
		if (this.playerFlags === undefined) {
			this.playerFlags = 0;
		}
		return (this.playerFlags);
	}-*/;

	/**
	 * Get dungeon master flags.
	 * 
	 * @return dungeon master flags.
	 */
	public final native int getDungeonMasterFlags() /*-{
		if (this.dungeonMasterFlags === undefined) {
			this.dungeonMasterFlags = 0;
		}
		return (this.dungeonMasterFlags);
	}-*/;

	/**
	 * Set player flag.
	 * 
	 * @param flagToSet player flag to set
	 */
	public final void setFlags(final PlayerFlag flagToSet) {
		int flags = getPlayerFlags();
		flags |= flagToSet.getValue();
		setPlayerFlagsNative(flags);
	}

	/**
	 * Set dungeon master flag.
	 * 
	 * @param flagToSet dungeon master flag to set
	 */
	public final void setFlags(final DungeonMasterFlag flagToSet) {
		int flags = getDungeonMasterFlags();
		flags |= flagToSet.getValue();
		setDungeonMasterFlagsNative(flags);
	}

	/**
	 * Clear player flags.
	 * 
	 * @param flagToClear player flags to clear.
	 */
	public final void clearFlags(final PlayerFlag flagToClear) {
		int flags = getPlayerFlags();
		flags &= ~flagToClear.getValue();
		setPlayerFlagsNative(flags);
	}

	/**
	 * Clear dungeon master flags.
	 * 
	 * @param flagToClear dungeon master flags to clear.
	 */
	public final void clearFlags(final DungeonMasterFlag flagToClear) {
		int flags = getDungeonMasterFlags();
		flags &= ~flagToClear.getValue();
		setDungeonMasterFlagsNative(flags);
	}

	/**
	 * Get pog class.
	 * 
	 * @return pog class.
	 */
	public final native String getPogClass() /*-{
		if (this.pogClass === undefined) {
			this.pogClass = "";
		}
		return (this.pogClass);
	}-*/;

	/**
	 * set pog class.
	 * 
	 * @param pogClass to set
	 */
	public final native void setPogClass(String pogClass) /*-{
		this.pogClass = pogClass;
	}-*/;

	/**
	 * get pog race.
	 * 
	 * @return pog race.
	 */
	public final native String getRace() /*-{
		if (this.race === undefined) {
			this.race = "";
		}
		return (this.race);
	}-*/;

	/**
	 * Set pog race.
	 * 
	 * @param race pog race.
	 */
	public final native void setRace(String race) /*-{
		this.race = race;
	}-*/;

	/**
	 * Set pog gender.
	 * 
	 * @param gender pog gender.
	 */
	public final native void setGender(String gender) /*-{
		this.gender = gender;
	}-*/;

	/**
	 * get pog gender.
	 * 
	 * @return pog gender.
	 */
	public final native String getGender() /*-{
		if (this.gender === undefined) {
			this.gender = "Male";
		}
		return (this.gender);
	}-*/;

	/**
	 * get pog notes.
	 * 
	 * @return pog notes.
	 */
	public final native String getNotes() /*-{
		if (this.notes === undefined) {
			this.notes = "";
		}
		return (this.notes);
	}-*/;

	/**
	 * Set pog notes.
	 * 
	 * @param notes pog notes.
	 */
	public final native void setNotes(String notes) /*-{
		this.notes = notes;
	}-*/;

	/**
	 * get pog dmNotes.
	 * 
	 * @return pog dmNotes.
	 */
	public final native String getDmNotes() /*-{
		if (this.dmNotes === undefined) {
			this.dmNotes = "";
		}
		return (this.dmNotes);
	}-*/;

	/**
	 * Set pog dmNotes.
	 * 
	 * @param dmNotes pog dmNotes.
	 */
	public final native void setDmNotes(String dmNotes) /*-{
		this.dmNotes = dmNotes;
	}-*/;

	/**
	 * {@inheritDoc}
	 */
	public final PogData clone() {
		String pogJson = JsonUtils.stringify(this);
		PogData theClone = JsonUtils.<PogData>safeEval(pogJson);
		theClone.setUUID(Constants.generateUUID());
		return (theClone);
	}

	/**
	 * Are these the same?
	 * 
	 * @param toCompare to compare
	 * @return true if same uuid
	 */
	public final boolean isEqual(final PogData toCompare) {
		if (toCompare == null) {
			return (false);
		}
		return (getUUID().equals(toCompare.getUUID()));
	}

	/**
	 * Update everything but UUIDs.
	 * 
	 * @param pogData with data
	 */
	public final void fullUpdate(final PogData pogData) {
		updatePog(pogData);
		setPlayerFlagsNative(pogData.getPlayerFlags());
		setDungeonMasterFlagsNative(pogData.getDungeonMasterFlags());
		setName(pogData.getName());
		setImageUrl(pogData.getImageUrl());
		setType(pogData.getType());
		setRace(pogData.getRace());
		setPogClass(pogData.getPogClass());
		setSize(pogData.getSize());
		setGender(pogData.getGender());
	}

	/**
	 * Update pog with new data.
	 * 
	 * @param withUpdates with updates
	 */
	public final void updatePog(final PogData withUpdates) {
		setColumn(withUpdates.getColumn());
		setRow(withUpdates.getRow());
		setDungeonLevel(withUpdates.getDungeonLevel());
		setNotes(withUpdates.getNotes());
		setDmNotes(withUpdates.getDmNotes());
	}
}
