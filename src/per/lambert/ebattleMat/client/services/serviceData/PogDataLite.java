package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

import per.lambert.ebattleMat.client.interfaces.DungeonMasterFlag;
import per.lambert.ebattleMat.client.interfaces.PlayerFlag;

public class PogDataLite extends JavaScriptObject {
	protected PogDataLite() {
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

	public final PogDataLite cloneLite() {
		PogDataLite clone = (PogDataLite) JavaScriptObject.createObject().cast();
		getRequiredData(clone);
		clone.setTemplateUUID(getTemplateUUID());
		return (clone);
	}

	public final void getRequiredData(PogDataLite clone) {
		clone.setUUID(getUUID());
		clone.setPogColumn(getPogColumn());
		clone.setPogRow(getPogRow());
		clone.setPlayerFlagsNative(getPlayerFlags());
		clone.setDungeonMasterFlagsNative(getDungeonMasterFlags());
	}

	public final boolean isFlagSet(DungeonMasterFlag transparent) {
		return ((getDungeonMasterFlags() & transparent.getValue()) != 0);
	}

	public final boolean isFlagSet(PlayerFlag transparent) {
		return ((getPlayerFlags() & transparent.getValue()) != 0);
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
}
