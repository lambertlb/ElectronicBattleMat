package per.lambert.ebattleMat.client.services;

import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.interfaces.DungeonData;
import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IDungeonManagement;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.services.serviceData.ServiceRequestData;

public class DungeonManagement implements IDungeonManagement {

	@Override
	public void login(ServiceRequestData requestData, IUserCallback callback) {
		String request = JsonUtils.stringify(requestData);
		IDataRequester dataRequester = ServiceManagement.getDataRequester();
		dataRequester.requestData(requestData, "LOGIN", new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				dungeonData = new DungeonData();
				callback.onSuccess(requestData, null);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
				callback.onError(requestData, error);
			}
		});
	}

	DungeonData dungeonData;

	@Override
	public DungeonData getDungeonData() {
		return dungeonData;
	}

	@Override
	public String getToken() {
		return null;
	}

}
