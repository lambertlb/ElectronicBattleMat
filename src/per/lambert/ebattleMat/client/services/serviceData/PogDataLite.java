package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

import per.lambert.ebattleMat.client.interfaces.PogFlag;

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
		clone.setPogFlagsNative(getPogFlags());
	}

	public final boolean isPogFlagSet(PogFlag flagToCheck) {
		return ((getPogFlags() & flagToCheck.getValue()) != 0);
	}

	private final native int getPogFlags() /*-{
		if (this.pogFlags === undefined) {
			this.pogFlags = 0;
		}
		return (this.pogFlags);
	}-*/;

	public final void setPogFlags(PogFlag flagToSet) {
		int flags = getPogFlags();
		flags |= flagToSet.getValue();
		setPogFlagsNative(flags);
	}

	public final void clearPogFlags(PogFlag flagToClear) {
		int flags = getPogFlags();
		flags &= ~flagToClear.getValue();
		setPogFlagsNative(flags);
	}

	public final native void setPogFlagsNative(int flags) /*-{
		this.pogFlags = flags;
	}-*/;
}
