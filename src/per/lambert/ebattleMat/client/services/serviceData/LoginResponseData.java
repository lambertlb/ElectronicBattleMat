package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class LoginResponseData extends JavaScriptObject {
	protected LoginResponseData() {
	}

	public final native int getError() /*-{
		return this.error;
	}-*/; // (3)

	public final native int getToken() /*-{
		return this.token;
	}-*/; // (3)
}
