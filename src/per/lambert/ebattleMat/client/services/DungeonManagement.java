package per.lambert.ebattleMat.client.services;

import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.interfaces.DungeonServerError;
import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IDungeonManagement;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.services.serviceData.LoginResponseData;
import per.lambert.ebattleMat.client.services.serviceData.ServiceRequestData;

public class DungeonManagement implements IDungeonManagement {

	@Override
	public void login(ServiceRequestData requestData, IUserCallback callback) {
		lastError = DungeonServerError.Succsess;
		String request = JsonUtils.stringify(requestData);
		IDataRequester dataRequester = ServiceManagement.getDataRequester();
		dataRequester.requestData(requestData, "LOGIN", new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				LoginResponseData loginResponseData = JsonUtils.<LoginResponseData>safeEval((String) data);
				token = loginResponseData.getToken();
				lastError = DungeonServerError.fromInt(loginResponseData.getError());
				if (lastError == DungeonServerError.Succsess) {
					callback.onSuccess(requestData, null);
					return;
				}
				callback.onError(requestData, new IErrorInformation() {

					@Override
					public Throwable getException() {
						return null;
					}

					@Override
					public DungeonServerError getError() {
						return lastError;
					}

					@Override
					public String getErrorMessage() {
						return null;
					}

				});
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
				lastError = error.getError();
				callback.onError(requestData, error);
			}
		});
	}

	private DungeonServerError lastError;

	@Override
	public DungeonServerError getLastError() {
		return (lastError);
	}

	private int token;
}
