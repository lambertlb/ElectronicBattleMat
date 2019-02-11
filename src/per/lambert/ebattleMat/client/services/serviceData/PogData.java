package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.ElectronicBattleMat;
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
	public final native String getPogName() /*-{
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
	public final native void setPogName(String pogName) /*-{
		this.pogName = pogName;
	}-*/;

	/**
	 * get pog image URL.
	 * 
	 * If image is part of the dungeon data then the URL starts with "dungeonData/" for example "dungeonData/resources/roomObjects/SecretDoorTop.png"
	 * 
	 * @return pog image URL.
	 */
	public final native String getPogImageUrl() /*-{
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
	public final native void setPogImageUrl(String pogImageUrl) /*-{
		this.pogImageUrl = pogImageUrl;
	}-*/;

	/**
	 * Get pog type.
	 * 
	 * @return pog type
	 */
	public final native String getPogType() /*-{
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
	public final native void setPogType(String pogType) /*-{
		this.pogType = pogType;
	}-*/;

	/**
	 * Get pog size in grid squares.
	 * 
	 * @return pog size in grid squares.
	 */
	public final native int getPogSize() /*-{
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
	public final native void setPogSize(int pogSize) /*-{
		this.pogSize = pogSize;
	}-*/;

	/**
	 * Is this a player pog.
	 * 
	 * @return true if player
	 */
	public final boolean isThisAPlayer() {
		String type = getPogType();
		return (type.equals(ElectronicBattleMat.POG_TYPE_PLAYER));
	}

	/**
	 * Is this a monster pog.
	 * 
	 * @return true if monster
	 */
	public final boolean isThisAMonster() {
		String type = getPogType();
		return (type.equals(ElectronicBattleMat.POG_TYPE_MONSTER));
	}

	/**
	 * Is this a room object pog.
	 * 
	 * @return true if room object.
	 */
	public final boolean isThisARoomObject() {
		String type = getPogType();
		return (type.equals(ElectronicBattleMat.POG_TYPE_ROOMOBJECT));
	}

	/**
	 * get column pog is in.
	 * 
	 * @return column pog is in.
	 */
	public final native int getPogColumn() /*-{
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
	public final native void setPogColumn(int pogColumn) /*-{
		this.pogColumn = pogColumn;
	}-*/;

	/**
	 * get row pog is in.
	 * 
	 * @return row pog is in.
	 */
	public final native int getPogRow() /*-{
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
	public final native void setPogRow(int pogRow) /*-{
		this.pogRow = pogRow;
	}-*/;

	/**
	 * Generate UUID.
	 * 
	 * @return UUID.
	 */
	public static final native String generateUUID() /*-{
		var d = new Date().getTime();
		if (typeof performance !== 'undefined'
				&& typeof performance.now === 'function') {
			d += performance.now(); //use high-precision timer if available
		}
		return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g,
				function(c) {
					var r = (d + Math.random() * 16) % 16 | 0;
					d = Math.floor(d / 16);
					return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
				});
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
	 * get UUID of template pog.
	 * 
	 * All instances of the same kind of pog will have the same template UUID.
	 * 
	 * @return UUID of template pog.
	 */
	public final native String getTemplateUUID() /*-{
		if (this.templateUUID === undefined) {
			return ("");
		}
		return (this.templateUUID);
	}-*/;

	/**
	 * set UUID of template pog.
	 * @param templateUUID UUID of template pog.
	 */
	public final native void setTemplateUUID(String templateUUID) /*-{
		this.templateUUID = templateUUID;
	}-*/;

	/**
	 * Get dungeon level this pog is on.
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
	 * @param dungeonLevel dungeon level this pog is on.
	 */
	public final native void setDungeonLevel(int dungeonLevel) /*-{
		this.dungeonLevel = dungeonLevel;
	}-*/;

	/**
	 * Is this dungeon master flag set.
	 * @param flagToTest flag to test.
	 * @return true if set.
	 */
	public final boolean isFlagSet(final DungeonMasterFlag flagToTest) {
		return ((getDungeonMasterFlags() & flagToTest.getValue()) != 0);
	}

	/**
	 * Is this player master flag set.
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
	 * @param flags to set
	 */
	public final native void setPlayerFlagsNative(int flags) /*-{
		this.playerFlags = flags;
	}-*/;

	/**
	 * Set dungeon master flag flag.
	 * @param flags to set
	 */
	public final native void setDungeonMasterFlagsNative(int flags) /*-{
		this.dungeonMasterFlags = flags;
	}-*/;

	/**
	 * Get player flags.
	 * @return player flags.
	 */
	@SuppressWarnings("RedundantModifier")
	public final native int getPlayerFlags() /*-{
		if (this.playerFlags === undefined) {
			this.playerFlags = 0;
		}
		return (this.playerFlags);
	}-*/;

	/**
	 * Get dungeon master flags.
	 * @return dungeon master flags.
	 */
	@SuppressWarnings("RedundantModifier")
	public final native int getDungeonMasterFlags() /*-{
		if (this.dungeonMasterFlags === undefined) {
			this.dungeonMasterFlags = 0;
		}
		return (this.dungeonMasterFlags);
	}-*/;

	/**
	 * Set player flag.
	 * @param flagToSet player flag to set
	 */
	public final void setFlags(final PlayerFlag flagToSet) {
		int flags = getPlayerFlags();
		flags |= flagToSet.getValue();
		setPlayerFlagsNative(flags);
	}

	/**
	 * Set dungeon master flag.
	 * @param flagToSet dungeon master flag to set
	 */
	public final void setFlags(final DungeonMasterFlag flagToSet) {
		int flags = getDungeonMasterFlags();
		flags |= flagToSet.getValue();
		setDungeonMasterFlagsNative(flags);
	}

	/**
	 * Clear player flags.
	 * @param flagToClear player flags to clear.
	 */
	public final void clearFlags(final PlayerFlag flagToClear) {
		int flags = getPlayerFlags();
		flags &= ~flagToClear.getValue();
		setPlayerFlagsNative(flags);
	}

	/**
	 * Clear dungeon master flags.
	 * @param flagToClear dungeon master flags to clear.
	 */
	public final void clearFlags(final DungeonMasterFlag flagToClear) {
		int flags = getDungeonMasterFlags();
		flags &= ~flagToClear.getValue();
		setDungeonMasterFlagsNative(flags);
	}

	/**
	 * Get pog class.
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
	 * @param pogClass to set
	 */
	public final native void setPogClass(String pogClass) /*-{
		this.pogClass = pogClass;
	}-*/;

	/**
	 * get pog race.
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
	 * @param race pog race.
	 */
	public final native void setRace(String race) /*-{
		this.race = race;
	}-*/;

	/**
	 * {@inheritDoc}
	 */
	public final PogData clone() {
		String pogJson = JsonUtils.stringify(this);
		PogData theClone = JsonUtils.<PogData>safeEval(pogJson);
		theClone.setUUID(generateUUID());
		return (theClone);
	}

	/**
	 * Are these the same?
	 * 
	 * @param toCompare to compare
	 * @return true if same uuid
	 */
	public final boolean isEquals(final PogData toCompare) {
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
		setPogName(pogData.getPogName());
		setPogImageUrl(pogData.getPogImageUrl());
		setPogType(pogData.getPogType());
		setRace(pogData.getRace());
		setPogClass(pogData.getPogClass());
		setPogSize(pogData.getPogSize());
	}

	/**
	 * Update pog with new data.
	 * 
	 * @param withUpdates with updates
	 */
	public final void updatePog(final PogData withUpdates) {
		setPogColumn(withUpdates.getPogColumn());
		setPogRow(withUpdates.getPogRow());
		setDungeonLevel(withUpdates.getDungeonLevel());
	}

}
