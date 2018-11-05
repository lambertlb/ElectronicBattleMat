package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;

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

	public final native int getPogSize() /*-{
		if (this.pogSize === undefined) {
			this.pogSize = 1;
		}
		return (this.pogSize);
	}-*/;

	public final native void setPogSize(int pogSize) /*-{
		this.pogSize = pogSize;
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
			this.uuid = generateUUID();
		}
		return (this.uuid);
	}-*/;

	public final native void setUUID(String uuid) /*-{
		this.uuid = uuid;
	}-*/;

	public final PogData clone() {
		String pogJson = JsonUtils.stringify(this);
		PogData theClone = JsonUtils.<PogData>safeEval(pogJson);
		theClone.setUUID(generateUUID());
		return (theClone);
	}
}
