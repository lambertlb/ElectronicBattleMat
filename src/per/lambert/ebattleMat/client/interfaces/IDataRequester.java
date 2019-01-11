package per.lambert.ebattleMat.client.interfaces;

import java.util.Map;

public interface IDataRequester {
	void requestData(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback);
}
