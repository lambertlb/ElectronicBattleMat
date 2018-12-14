package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.ElectronicBattleMat;

public class PogData extends PogDataLite {
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

	public final PogData clone() {
		String pogJson = JsonUtils.stringify(this);
		PogData theClone = JsonUtils.<PogData>safeEval(pogJson);
		theClone.setUUID(generateUUID());
		return (theClone);
	}

	public final boolean isThisAPlayer() {
		String type = getPogType();
		return(type.equals(ElectronicBattleMat.POG_TYPE_PLAYER));
	}
}
