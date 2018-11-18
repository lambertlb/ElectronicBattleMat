package per.lambert.ebattleMat.client.services;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.ElectronicBattleMat;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.DungeonServerError;
import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IDungeonManagement;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.serviceData.DungeonData;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
import per.lambert.ebattleMat.client.services.serviceData.DungeonListData;
import per.lambert.ebattleMat.client.services.serviceData.LoginResponseData;
import per.lambert.ebattleMat.client.services.serviceData.PogData;
import per.lambert.ebattleMat.client.services.serviceData.PogList;

public class DungeonManagement implements IDungeonManagement {
	private DungeonServerError lastError;

	@Override
	public DungeonServerError getLastError() {
		return (lastError);
	}

	public DungeonManagement() {
		players = (PogList) JavaScriptObject.createObject().cast();
	}

	private int token;

	private DungeonListData dungeonListData;

	@Override
	public String[] getDungeonNames() {
		if (dungeonListData != null) {
			return (dungeonListData.getDungeonNames());
		}
		return null;
	}

	private DungeonData selectedDungeon;

	@Override
	public boolean dungeonSelected() {
		return (selectedDungeon != null);
	}

	@Override
	public DungeonData getSelectedDungeon() {
		return selectedDungeon;
	}

	private int currentLevel;

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

	private PogList pcPogs;

	@Override
	public PogData[] getPcPogs() {
		return pcPogs.getPogList();
	}

	private PogList players;

	public PogData[] getPlayers() {
		return players.getPogList();
	}

	public void addPlayer(PogData player) {
		players.addPog(player);
	}

	private PogData selectedPog;

	@Override
	public PogData getSelectedPog() {
		return selectedPog;
	}

	@Override
	public void setSelectedPog(PogData selectedPog) {
		this.selectedPog = selectedPog;
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
		dataRequester.requestData("", token, "GETDUNGEONLIST", parameters, new IUserCallback() {

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
		dungeonListData = JsonUtils.<DungeonListData>safeEval((String) data);
		callback.onSuccess(requestData, null);
	}

	private String dungeonNameForUrl;

	@Override
	public String getDungeonNameForUrl() {
		return dungeonNameForUrl;
	}

	@Override
	public void selectDungeon(String dungeonsName) {
		updateDungeonNameForUrl(dungeonsName);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("fileName", ElectronicBattleMat.DUNGEONS_FOLDER + dungeonNameForUrl + ElectronicBattleMat.DUNGEON_DATA_FILENAME);
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
		loadInResourceData();
	}

	private void handleDungeonData(Object data) {
		selectedDungeon = JsonUtils.<DungeonData>safeEval((String) data);
		ServiceManagement.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonSelected, null));
		dungeonDataChanged();
	}

	private void updateDungeonNameForUrl(String dungeonsName) {
		dungeonNameForUrl = null;
		for (int i = 0; i < dungeonListData.getDungeonNames().length;++i ) {
			if (dungeonListData.getDungeonNames()[i].equals(dungeonsName)) {
				dungeonNameForUrl = dungeonListData.getDungeonDirectories()[i];
			}
		}
	}

	@Override
	public void dungeonDataChanged() {
		ServiceManagement.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataChanged, null));
	}

	@Override
	public void saveDungeonData() {
		saveCurrentDungeonData();
		dungeonDataChanged();
	}

	private void saveCurrentDungeonData() {
		if (selectedDungeon != null) {
			Map<String, String> parameters = new HashMap<String, String>();
			updateDungeonNameForUrl(selectedDungeon.getDungeonName());
			parameters.put("dungeonName", dungeonNameForUrl);
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

	private void loadInResourceData() {
		loadCharacterPogs();
	}

	private void loadCharacterPogs() {
		IDataRequester dataRequester = ServiceManagement.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("fileName", ElectronicBattleMat.DUNGEON_PCPOG_LOCATION + "characterPogs.json");
		dataRequester.requestData("", token, "LOADJSONFILE", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				pcPogs = JsonUtils.<PogList>safeEval((String) data);
				ServiceManagement.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.CharacterPogsLoaded, null));
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
			}
		});
	}

	@Override
	public PogData createPlayerInstance(PogData template) {
		PogData player = template.clone();
		player.setThisAPlayer(true);
		addPlayer(player);
		return (player);
	}

	private boolean[][] fowGrid = new boolean[0][0];

	@Override
	public void setFowSize(int columns, int rows) {
		if (fowGrid.length <= 0) {
			fowGrid = new boolean[columns + 1][rows + 1];
			for (int i = 0; i <= columns; ++i) {
				for (int j = 0; j <= rows; ++j) {
					fowGrid[i][j] = true;
				}
			}
		}
	}

	@Override
	public boolean isFowSet(int columns, int rows) {
		return (fowGrid[columns][rows]);
	}

	@Override
	public void setFow(int columns, int rows, boolean value) {
		fowGrid[columns][rows] = value;
	}

	private boolean fowToggle;

	@Override
	public boolean getFowToggle() {
		return (fowToggle);
	}

	@Override
	public void setFowToggle(boolean fowToggle) {
		this.fowToggle = fowToggle;
		ServiceManagement.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.ToggleFowSelected, null));
	}
}
