package per.lambert.ebattleMat.client.interfaces;

import com.google.gwt.core.client.JavaScriptObject;

public interface IDataRequester {
	/**
	 * Request data from server
	 * @param callback to notify user
	 */
	void requestData(final JavaScriptObject postDataOject,  final IUserCallback callback);
}
