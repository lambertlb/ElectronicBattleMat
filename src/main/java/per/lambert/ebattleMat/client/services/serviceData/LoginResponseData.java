package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Login response from server.
 * 
 * @author LLambert
 *
 */
public class LoginResponseData extends JavaScriptObject {
	/**
	 * COnstructor.
	 */
	protected LoginResponseData() {
	}

	/**
	 * get error from login.
	 * 
	 * @return non-zero if error
	 */
	public final native int getError() /*-{
		return this.error;
	}-*/; // (3)

	/**
	 * set error code.
	 * 
	 * @param error code
	 */
	public final native void setError(int error) /*-{
		this.error = error;
	}-*/; // (3)

	/**
	 * get user token.
	 * 
	 * This needs to be used in all future requests.
	 * 
	 * @return user token.
	 */
	public final native int getToken() /*-{
		return this.token;
	}-*/; // (3)

	/**
	 * set token code.
	 * 
	 * @param token code
	 */
	public final native void setToken(int token) /*-{
		this.token = token;
	}-*/; // (3)
}
