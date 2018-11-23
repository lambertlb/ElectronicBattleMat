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

public class DungeonManager implements IDungeonManagement {
	private DungeonServerError lastError;

	@Override
	public DungeonServerError getLastError() {
		return (lastError);
	}

	public DungeonManager() {
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

	Map<String, PogData> pcTemplateMap = new HashMap<String, PogData>();
	private PogList pcTemplatePogs;

	@Override
	public PogData[] getPcTemplatePogs() {
		return pcTemplatePogs.getPogList();
	}

	private PogList monsterTemplatePogs;

	@Override
	public PogData[] getMonsterTemplatePogs() {
		return monsterTemplatePogs.getPogList();
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

	private boolean isDungeonMaster;

	@Override
	public boolean isDungeonMaster() {
		return isDungeonMaster;
	}

	@Override
	public void setDungeonMaster(boolean isDungeonMaster) {
		this.isDungeonMaster = isDungeonMaster;
	}

	private boolean editMode;

	@Override
	public boolean isEditMode() {
		return editMode;
	}

	@Override
	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	@Override
	public void login(final String username, final String password, IUserCallback callback) {
		lastError = DungeonServerError.Succsess;
		IDataRequester dataRequester = ServiceManager.getDataRequester();
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
		IDataRequester dataRequester = ServiceManager.getDataRequester();
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

	@Override
	public void selectDungeon(String dungeonsName) {
		initializeDungeonData();
		String dungeonDirectory = getDirectoryNameForDungeon(dungeonsName);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("fileName", ElectronicBattleMat.DUNGEONS_FOLDER + dungeonDirectory + ElectronicBattleMat.DUNGEON_DATA_FILENAME);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
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

	private void initializeDungeonData() {
		setCurrentLevel(0);
		isDungeonMaster = false;
		editMode = false;
	}

	private void handleDungeonData(Object data) {
		selectedDungeon = JsonUtils.<DungeonData>safeEval((String) data);
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonSelected, null));
		dungeonDataChanged();
	}

	private String getDirectoryNameForDungeon(String dungeonsName) {
		for (int i = 0; i < dungeonListData.getDungeonNames().length; ++i) {
			if (dungeonListData.getDungeonNames()[i].equals(dungeonsName)) {
				return (dungeonListData.getDungeonDirectories()[i]);
			}
		}
		return (null);
	}

	private String getDirectoryForCurrentDungeon() {
		return (getDirectoryNameForDungeon(selectedDungeon.getDungeonName()));
	}

	@Override
	public void dungeonDataChanged() {
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataChanged, null));
	}

	@Override
	public void saveDungeonData() {
		saveCurrentDungeonData();
		dungeonDataChanged();
	}

	private void saveCurrentDungeonData() {
		if (selectedDungeon != null) {
			Map<String, String> parameters = new HashMap<String, String>();
			getDirectoryNameForDungeon(selectedDungeon.getDungeonName());
			parameters.put("dungeonName", getDirectoryForCurrentDungeon());
			IDataRequester dataRequester = ServiceManager.getDataRequester();
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
		loadMonsterPogs();
	}

	private void loadCharacterPogs() {
		pcTemplatePogs = null;
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("fileName", ElectronicBattleMat.DUNGEON_PCPOG_LOCATION + "characterPogs.json");
		dataRequester.requestData("", token, "LOADJSONFILE", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				loadCharacterPogTemplates(data);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
			}
		});
	}

	private void loadCharacterPogTemplates(Object data) {
		pcTemplateMap.clear();
		pcTemplatePogs = JsonUtils.<PogList>safeEval((String) data);
		for (PogData pcTemplate : pcTemplatePogs.getPogList()) {
			pcTemplateMap.put(pcTemplate.getUUID(), pcTemplate);
		}
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.CharacterPogsLoaded, null));
	}

	private void loadMonsterPogs() {
		monsterTemplatePogs = null;
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("fileName", ElectronicBattleMat.DUNGEON_MONSTER_LOCATION + "monsterPogs.json");
		dataRequester.requestData("", token, "LOADJSONFILE", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				monsterTemplatePogs = JsonUtils.<PogList>safeEval((String) data);
				ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.MonsterPogsLoaded, null));
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
		return (editMode ? false : fowGrid[columns][rows]);
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
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.ToggleFowSelected, null));
	}

	private int resourceCount = 1;

	@Override
	public String getUrlToDungeonResource(String resourceItem) {
		if (resourceItem.startsWith("http")) {
			return resourceItem;
		}
		DungeonLevel dungeonLevel = ServiceManager.getDungeonManagment().getCurrentLevelData();
		String directoryForDungeon = getDirectoryForCurrentDungeon();
		String resourceUrl = ElectronicBattleMat.DUNGEONS_LOCATION + directoryForDungeon + "/" + resourceItem + "?" + resourceCount++;
		return (resourceUrl);
	}

	@Override
	public void createNewDungeon(final String templateName, final String newDungeonName) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("templateName", getDirectoryNameForDungeon(templateName));
		parameters.put("newDungeonName", newDungeonName);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", token, "CREATENEWDUNGEON", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				handlerNewDungeonCreated(newDungeonName);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
				lastError = error.getError();
			}
		});
	}

	private void handlerNewDungeonCreated(String newDungeonName) {
		getDungeonList(null, new IUserCallback() {

			@Override
			public void onError(Object sender, IErrorInformation error) {
			}

			@Override
			public void onSuccess(Object sender, Object data) {
				selectDungeon(newDungeonName);
			}
		});
	}

	@Override
	public boolean okToDeleteThisTemplate(String dungeonsName) {
		return !dungeonsName.equals("Template Dungeon");
	}

	@Override
	public void deleteTemplate(String selectedTemplate) {
		if (!okToDeleteThisTemplate(selectedTemplate)) {
			return;
		}
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("templateName", getDirectoryNameForDungeon(selectedTemplate));
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", token, "DELETEDUNGEON", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				handlerDungeonDeleted();
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
				lastError = error.getError();
			}
		});
	}

	private void handlerDungeonDeleted() {
		getDungeonList("", new IUserCallback() {

			@Override
			public void onError(Object sender, IErrorInformation error) {
			}

			@Override
			public void onSuccess(Object sender, Object data) {
				dungeonDataChanged();
			}
		});
	}

	@Override
	public PogData createMonsterFromTemplate(PogData pogData) {
		PogData template = findMonsterTemplate(pogData);
		return null;
	}

	@Override
	public PogData findMonsterTemplate(PogData pogData) {
		if (monsterTemplatePogs == null) {
			return (null);
		}
		for (PogData template : monsterTemplatePogs.getPogList()) {
			if (template.getPogName() == pogData.getPogName()) {
				PogData clone = pogData.clone();
				clone.setPogImageUrl(template.getPogImageUrl());
				return (clone);
			}
		}
		return (null);
	}

	@Override
	public PogData findCharacterPog(String pogUUID) {
		return (pcTemplateMap.get(pogUUID));
	}
}
