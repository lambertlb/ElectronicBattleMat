package per.lambert.ebattleMat.client.controls.LoginControl;

import com.google.gwt.core.client.JavaScriptObject;

import per.lambert.ebattleMat.client.services.serviceData.LoginRequestData;

public class LoginModel {
	String message = "";
	boolean isEnabled;
	
	LoginRequestData requestData = (LoginRequestData)JavaScriptObject.createObject().cast();
	
	public LoginRequestData getRequestData() {
		return requestData;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}
