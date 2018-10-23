package per.lambert.ebattleMat.client.interfaces;

import per.lambert.ebattleMat.client.services.serviceData.ServiceRequestData;

public interface IDataRequester {
	/**
	 * Request data from server
	 * 
	 * @param callback
	 *            to notify user
	 */
	void requestData(final ServiceRequestData postDataOject, final String requestType, final IUserCallback callback);

	void requestData(final ServiceRequestData postDataOject, final int token, final String requestType,
			final IUserCallback callback);
}
