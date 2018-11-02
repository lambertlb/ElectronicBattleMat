package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class PogList extends JavaScriptObject {
	protected PogList() {
	}

	public final native PogData[] getPogList() /*-{
		return this.pogList;
	}-*/;

}
