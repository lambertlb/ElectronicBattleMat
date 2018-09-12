package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class LoginRequestData extends JavaScriptObject {
	protected LoginRequestData() {
	}

	public final native void setUsername(String username) /*-{
		this.username = username;
	}-*/;

	public final native String getUsername() /*-{
		return this.username;
	}-*/;

	public final native void setPassword(String password) /*-{
		this.password = password;
	}-*/;

	public final native String setPassword() /*-{
		return this.password;
	}-*/;

}
