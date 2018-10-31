package per.lambert.ebattleMat.client.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.DungeonServerError;
import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IDungeonManagement;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.serviceData.DungeonData;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
import per.lambert.ebattleMat.client.services.serviceData.DungeonListResponseData;
import per.lambert.ebattleMat.client.services.serviceData.LoginResponseData;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

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

	DungeonData selectedDungeon;

	@Override
	public boolean dungeonSelected() {
		return (selectedDungeon != null);
	}

	@Override
	public DungeonData getSelectedDungeon() {
		return selectedDungeon;
	}

	private String selectedDungeonsName;
	
	public String getSelectedDungeonsName() {
		return selectedDungeonsName;
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
	public void login(final String username, final String password, IUserCallback callback) {
		lastError = DungeonServerError.Succsess;
		IDataRequester dataRequester = ServiceManagement.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", username);
		parameters.put("password", password);
		dataRequester.requestData("", token, "LOGIN", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				handleSuccessfulLogin(username, callback, data);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
				lastError = error.getError();
				callback.onError(username, error);
			}
		});
	}

	private void handleSuccessfulLogin(Object requestData, IUserCallback callback, Object data) {
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

	private void getDungeonList(Object requestData, IUserCallback callback) {
		IDataRequester dataRequester = ServiceManagement.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("fileName", "dungeonList.json");
		dataRequester.requestData("", token, "LOADJSONFILE", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				handleSuccessfulDungeonList("", callback, data);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
				lastError = error.getError();
				callback.onError(requestData, error);
			}
		});
	}

	private void handleSuccessfulDungeonList(Object requestData, IUserCallback callback, Object data) {
		DungeonListResponseData dungeonListResponseData = JsonUtils.<DungeonListResponseData>safeEval((String) data);
		dungeonList.clear();
		for (String dungeon : dungeonListResponseData.getDungeons()) {
			dungeonList.add(dungeon);
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
		updateDungeonNameForUrl(dungeonsName);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("fileName", dungeonNameForUrl + "/dungeonData.json");
		IDataRequester dataRequester = ServiceManagement.getDataRequester();
		dataRequester.requestData("", token, "LOADJSONFILE", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				handleDungeonData(data);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
			}
		});
	}

	private void handleDungeonData(Object data) {
		selectedDungeon = JsonUtils.<DungeonData>safeEval((String) data);
		ServiceManagement.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonSelected, null));
		dungeonDataChanged();
	}

	private void updateDungeonNameForUrl(String dungeonsName) {
		dungeonNameForUrl = dungeonsName.toLowerCase();
	}

	@Override
	public void dungeonDataChanged() {
		ServiceManagement.getEventManager()
				.fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataChanged, null));
	}
	@Override
	public void saveDungeonData() {
		saveCurrentDungeonData();
		dungeonDataChanged();
	}

	private void saveCurrentDungeonData() {
		if (selectedDungeon != null) {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("dungeonName", selectedDungeon.getDungeonName());
			IDataRequester dataRequester = ServiceManagement.getDataRequester();
			String dungeonDataString = JsonUtils.stringify(selectedDungeon);
			dataRequester.requestData(dungeonDataString, token, "SAVEJSONFILE", parameters, new IUserCallback() {

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
