package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

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
}
