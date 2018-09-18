package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class ServiceRequestData extends JavaScriptObject {
	protected ServiceRequestData() {
	}

	public final native void setServiceRequest(String serviceRequest) /*-{
		this.serviceRequest = serviceRequest;
	}-*/;

	public final native String getServiceRequest() /*-{
		return this.serviceRequest;
	}-*/;

	public final native int getToken() /*-{
		return this.token;
	}-*/;

	public final native void setToken(int token) /*-{
		this.token = token;
	}-*/;
}
