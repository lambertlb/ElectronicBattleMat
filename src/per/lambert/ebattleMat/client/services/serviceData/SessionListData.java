package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class SessionListData extends JavaScriptObject {
	protected SessionListData() {
	}

	public final native String[] getSessionNames() /*-{
		return this.sessionNames;
	}-*/;

	public final native String[] getSessionDirectories() /*-{
		return this.sessionDirectories;
	}-*/;
}
