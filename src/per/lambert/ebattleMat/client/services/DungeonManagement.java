package per.lambert.ebattleMat.client.services;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.DungeonServerError;
import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IDungeonManagement;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.serviceData.DungeonData;
import per.lambert.ebattleMat.client.services.serviceData.DungeonDataResponseData;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
import per.lambert.ebattleMat.client.services.serviceData.DungeonListResponseData;
import per.lambert.ebattleMat.client.services.serviceData.LoginResponseData;
import per.lambert.ebattleMat.client.services.serviceData.PogData;
import per.lambert.ebattleMat.client.services.serviceData.SaveDungeonDataRequest;
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

	private List<DungeonData> dungeonData = new ArrayList<>();

	@Override
	public List<DungeonData> getDungeonData() {
		return dungeonData;
	}

	DungeonData selectedDungeon;

	@Override
	public boolean dungeonSelected() {
		return (selectedDungeon != null);
	}

	@Override
	public DungeonData getSelectedDungeon() {
		return selectedDungeon;
	}

	int currentLevel;

	@Override
	public int getCurrentLevel() {
		return currentLevel;
	}

	@Override
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	@Override
	public DungeonLevel getCurrentLevelData() {
		if (currentLevel < selectedDungeon.getDungeonlevels().length) {
			return (selectedDungeon.getDungeonlevels()[currentLevel]);
		}
		return null;
	}

	private PogData pogBeingDragged;

	public void setPogBeingDragged(PogData pogBeingDragged) {
		this.pogBeingDragged = pogBeingDragged;
	}

	public PogData getPogBeingDragged() {
		return (pogBeingDragged);
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
		ServiceRequestData serviceRequest = (ServiceRequestData) JavaScriptObject.createObject().cast();
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
		getDungeonData(requestData, callback);
	}

	private void getDungeonData(ServiceRequestData requestData, IUserCallback callback) {
		ServiceRequestData serviceRequest = (ServiceRequestData) JavaScriptObject.createObject().cast();
		IDataRequester dataRequester = ServiceManagement.getDataRequester();
		dataRequester.requestData(serviceRequest, token, "DUNGEONDATA", new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				handleSuccessfulDungeonData(requestData, callback, data);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
				lastError = error.getError();
				callback.onError(requestData, error);
			}
		});
	}

	private void handleSuccessfulDungeonData(ServiceRequestData requestData, IUserCallback callback, Object data) {
		DungeonDataResponseData dungeonDataResponseData = JsonUtils.<DungeonDataResponseData>safeEval((String) data);
		dungeonData.clear();
		for (DungeonData dungeon : dungeonDataResponseData.getDungeonData()) {
			dungeonData.add(dungeon);
		}
		callback.onSuccess(requestData, null);
	}

	String dungeonNameForUrl;

	@Override
	public String getDungeonNameForUrl() {
		return dungeonNameForUrl;
	}

	@Override
	public void selectDungeon(String dungeonsName) {
		int index = dungeonList.indexOf(dungeonsName);
		selectedDungeon = dungeonData.get(index);
		updateDungeonNameForUrl(dungeonsName);
		ServiceManagement.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonSelected, null));
	}

	private void updateDungeonNameForUrl(String dungeonsName) {
		dungeonNameForUrl = dungeonsName.toLowerCase();
	}

	@Override
	public void dungeonDataChanged() {
		saveCurrentDungeonData();
		ServiceManagement.getEventManager()
				.fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataChanged, null));
	}

	private void saveCurrentDungeonData() {
		if (selectedDungeon != null) {
			SaveDungeonDataRequest saveDungeonDataRequest = (SaveDungeonDataRequest) JavaScriptObject.createObject()
					.cast();
			saveDungeonDataRequest.setDungeonData(selectedDungeon);
			IDataRequester dataRequester = ServiceManagement.getDataRequester();
			dataRequester.requestData(saveDungeonDataRequest, token, "SAVEDUNGEONDATA", new IUserCallback() {

				@Override
				public void onSuccess(Object sender, Object data) {
				}

				@Override
				public void onError(Object sender, IErrorInformation error) {
					lastError = error.getError();
				}
			});
		}
	}
}
