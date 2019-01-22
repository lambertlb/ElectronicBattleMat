package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.ElectronicBattleMat;
import per.lambert.ebattleMat.client.interfaces.DungeonMasterFlag;
import per.lambert.ebattleMat.client.interfaces.PlayerFlag;

public class PogData extends JavaScriptObject {
	protected PogData() {
	}

	public final native String getPogName() /*-{
		if (this.pogName === undefined) {
			this.pogName = "";
		}
		return (this.pogName);
	}-*/;

	public final native void setPogName(String pogName) /*-{
		this.pogName = pogName;
	}-*/;

	public final native String getPogImageUrl() /*-{
		if (this.pogImageUrl === undefined) {
			this.pogImageUrl = "";
		}
		return (this.pogImageUrl);
	}-*/;

	public final native void setPogImageUrl(String pogImageUrl) /*-{
		this.pogImageUrl = pogImageUrl;
	}-*/;

	public final native String getPogType() /*-{
		if (this.pogType === undefined) {
			this.pogType = "";
		}
		return (this.pogType);
	}-*/;

	public final native void setPogType(String pogType) /*-{
		this.pogType = pogType;
	}-*/;

	public final native int getPogSize() /*-{
		if (this.pogSize === undefined) {
			this.pogSize = 1;
		}
		return (this.pogSize);
	}-*/;

	public final native void setPogSize(int pogSize) /*-{
		this.pogSize = pogSize;
	}-*/;

	public final boolean isThisAPlayer() {
		String type = getPogType();
		return (type.equals(ElectronicBattleMat.POG_TYPE_PLAYER));
	}

	public final boolean isThisAMonster() {
		String type = getPogType();
		return (type.equals(ElectronicBattleMat.POG_TYPE_MONSTER));
	}

	public final boolean isThisARoomObject() {
		String type = getPogType();
		return (type.equals(ElectronicBattleMat.POG_TYPE_ROOMOBJECT));
	}

	public final native int getPogColumn() /*-{
		if (this.pogColumn === undefined) {
			this.pogColumn = -1;
		}
		return (this.pogColumn);
	}-*/;

	public final native void setPogColumn(int pogColumn) /*-{
		this.pogColumn = pogColumn;
	}-*/;

	public final native int getPogRow() /*-{
		if (this.pogRow === undefined) {
			this.pogRow = -1;
		}
		return (this.pogRow);
	}-*/;

	public final native void setPogRow(int pogRow) /*-{
		this.pogRow = pogRow;
	}-*/;

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

	public final native String getUUID() /*-{
		if (this.uuid === undefined) {
			return ("");
		}
		return (this.uuid);
	}-*/;

	public final native void setUUID(String uuid) /*-{
		this.uuid = uuid;
	}-*/;

	public final native String getTemplateUUID() /*-{
		if (this.templateUUID === undefined) {
			return ("");
		}
		return (this.templateUUID);
	}-*/;

	public final native void setTemplateUUID(String templateUUID) /*-{
		this.templateUUID = templateUUID;
	}-*/;

	public final native int getDungeonLevel() /*-{
		if (this.dungeonLevel === undefined) {
			this.dungeonLevel = 0;
		}
		return (this.dungeonLevel);
	}-*/;

	public final native void setDungeonLevel(int dungeonLevel) /*-{
		this.dungeonLevel = dungeonLevel;
	}-*/;

	public final boolean isFlagSet(DungeonMasterFlag flagToTest) {
		return ((getDungeonMasterFlags() & flagToTest.getValue()) != 0);
	}

	public final boolean isFlagSet(PlayerFlag flagToTest) {
		return ((getPlayerFlags() & flagToTest.getValue()) != 0);
	}

	public final native void clearPlayerFlags() /*-{
		this.playerFlags = 0;
	}-*/;

	public final native void clearDungeonMasterFlags() /*-{
		this.dungeonMasterFlags = 0;
	}-*/;

	public final native void setPlayerFlagsNative(int flags) /*-{
		this.playerFlags = flags;
	}-*/;

	public final native void setDungeonMasterFlagsNative(int flags) /*-{
		this.dungeonMasterFlags = flags;
	}-*/;

	private final native int getPlayerFlags() /*-{
		if (this.playerFlags === undefined) {
			this.playerFlags = 0;
		}
		return (this.playerFlags);
	}-*/;

	private final native int getDungeonMasterFlags() /*-{
		if (this.dungeonMasterFlags === undefined) {
			this.dungeonMasterFlags = 0;
		}
		return (this.dungeonMasterFlags);
	}-*/;

	public final void setFlags(PlayerFlag flagToSet) {
		int flags = getPlayerFlags();
		flags |= flagToSet.getValue();
		setPlayerFlagsNative(flags);
	}

	public final void setFlags(DungeonMasterFlag flagToSet) {
		int flags = getDungeonMasterFlags();
		flags |= flagToSet.getValue();
		setDungeonMasterFlagsNative(flags);
	}

	public final void clearFlags(PlayerFlag flagToClear) {
		int flags = getPlayerFlags();
		flags &= ~flagToClear.getValue();
		setPlayerFlagsNative(flags);
	}

	public final void clearFlags(DungeonMasterFlag flagToClear) {
		int flags = getDungeonMasterFlags();
		flags &= ~flagToClear.getValue();
		setDungeonMasterFlagsNative(flags);
	}

	public final native String getPogClass() /*-{
		if (this.pogClass === undefined) {
			this.pogClass = "";
		}
		return (this.pogClass);
	}-*/;

	public final native void setPogClass(String pogClass) /*-{
		this.pogClass = pogClass;
	}-*/;

	public final native String getRace() /*-{
		if (this.race === undefined) {
			this.race = "";
		}
		return (this.race);
	}-*/;

	public final native void setRace(String race) /*-{
		this.race = race;
	}-*/;

	public final PogData clone() {
		String pogJson = JsonUtils.stringify(this);
		PogData theClone = JsonUtils.<PogData>safeEval(pogJson);
		theClone.setUUID(generateUUID());
		return (theClone);
	}

}
