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

	/**
	 * Get path to service.
	 * 
	 * @return path to service
	 */
	String getWebPath();

	/**
	 * Build the URL for this request type. This will add the following additional parameters. token is authorization token for this login request is the request to the server
	 * 
	 * @param requestType request type to server
	 * @param parameters parameters for the request
	 * @return URL for server
	 */
	String buildUrl(String requestType, Map<String, String> parameters);
}
