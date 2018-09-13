package per.lambert.ebattleMat.client.services.serviceData;

public class LoginRequestData extends ServiceRequestData {
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
