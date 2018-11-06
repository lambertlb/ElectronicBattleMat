package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class PogList extends JavaScriptObject {
	protected PogList() {
	}

	public final native PogData[] getPogList() /*-{
		if (this.pogList === undefined) {
			this.pogList = [];
		}
		return this.pogList;
	}-*/;

	public final native void addPog(PogData pogToAdd) /*-{
		if (this.pogList === undefined) {
			this.pogList = [];
		}
		this.pogList.push(pogToAdd);
	}-*/;
}
