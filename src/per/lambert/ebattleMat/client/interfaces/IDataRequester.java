package per.lambert.ebattleMat.client.interfaces;

import java.util.Map;

/**
 * interface for handling a web request.
 * @author LLambert
 *
 */
public interface IDataRequester {
	/**
	 * handle web request.
	 * @param requestData request data
	 * @param requestType request type
	 * @param parameters for request
	 * @param callback user callback.
	 */
	void requestData(String requestData, String requestType, Map<String, String> parameters, IUserCallback callback);
}
