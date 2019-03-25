package per.lambert.ebattleMat.client;

import java.util.Map;

import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;

/**
 * Mock Data requester.
 * @author LLambert
 *
 */
public class MockDataRequester implements IDataRequester {

	/**
	 * Callback to test.
	 */
	private DataRequesterTestCallback testCallback;
	
	/**
	 * Get test callback.
	 * @return test callback
	 */
	public DataRequesterTestCallback getTestCallback() {
		return testCallback;
	}

	/**
	 * set test callback.
	 * @param testCallback test callback
	 */
	public void setTestCallback(final DataRequesterTestCallback testCallback) {
		this.testCallback = testCallback;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void requestData(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
		if (testCallback != null) {
			testCallback.onCall(requestData, requestType, parameters, callback);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getWebPath() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String buildUrl(final String requestType, final Map<String, String> parameters) {
		return null;
	}

}
