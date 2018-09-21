package per.lambert.ebattleMat.client.services;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.interfaces.DungeonServerError;
import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IDungeonManagement;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.services.serviceData.DungeonListResponseData;
import per.lambert.ebattleMat.client.services.serviceData.LoginResponseData;
import per.lambert.ebattleMat.client.services.serviceData.ServiceRequestData;

public class DungeonManagement implements IDungeonManagement {
	private DungeonServerError lastError;

	@Override
	public DungeonServerError getLastError() {
		return (lastError);
	}

	private int token;

	private List<String> dungeonList = new ArrayList<String>();
	
	@Override
	public List<String> getDungeonList() {
		return dungeonList;
	}

	@Override
	public void login(ServiceRequestData requestData, IUserCallback callback) {
		lastError = DungeonServerError.Succsess;
		IDataRequester dataRequester = ServiceManagement.getDataRequester();
		dataRequester.requestData(requestData, "LOGIN", new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				handleSuccessfulLogin(requestData, callback, data);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
				lastError = error.getError();
				callback.onError(requestData, error);
			}
		});
	}

	private void handleSuccessfulLogin(ServiceRequestData requestData, IUserCallback callback, Object data) {
		LoginResponseData loginResponseData = JsonUtils.<LoginResponseData>safeEval((String) data);
		token = loginResponseData.getToken();
		lastError = DungeonServerError.fromInt(loginResponseData.getError());
		if (lastError == DungeonServerError.Succsess) {
			getDungeonList(requestData, callback);
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

	
	private void getDungeonList(ServiceRequestData requestData, IUserCallback callback) {
		ServiceRequestData serviceRequest = (ServiceRequestData)JavaScriptObject.createObject().cast();
		IDataRequester dataRequester = ServiceManagement.getDataRequester();
		dataRequester.requestData(serviceRequest, token, "DUNGEONLIST", new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				handleSuccessfulDungeonList(requestData, callback, data);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
				lastError = error.getError();
				callback.onError(requestData, error);
			}
		});
	}

	private void handleSuccessfulDungeonList(ServiceRequestData requestData, IUserCallback callback, Object data) {
		DungeonListResponseData dungeonListResponseData = JsonUtils.<DungeonListResponseData>safeEval((String) data);
		dungeonList.clear();
		for (String dungeon : dungeonListResponseData.getDungeons()) {
			dungeonList.add(dungeon);
		}
		callback.onSuccess(requestData, null);
	}

}
