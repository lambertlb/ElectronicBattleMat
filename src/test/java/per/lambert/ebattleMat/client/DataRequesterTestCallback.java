package per.lambert.ebattleMat.client;

import java.util.Map;

import per.lambert.ebattleMat.client.interfaces.IUserCallback;

/**
 * interface Mock data requester can use to callback to test.
 * @author LLambert
 *
 */
public interface DataRequesterTestCallback {
	/**
	 * When requester is called.
	 * @param requestData request data
	 * @param requestType request type
	 * @param parameters request parameters
	 * @param callback callback to users.
	 */
	void onCall(String requestData, String requestType, Map<String, String> parameters, IUserCallback callback);
}
