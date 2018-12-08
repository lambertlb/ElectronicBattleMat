package per.lambert.ebattleMat.client.services;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.ElectronicBattleMat;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.DungeonServerError;
import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IDungeonManager;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.serviceData.DungeonData;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
import per.lambert.ebattleMat.client.services.serviceData.DungeonListData;
import per.lambert.ebattleMat.client.services.serviceData.LoginResponseData;
import per.lambert.ebattleMat.client.services.serviceData.PogData;
import per.lambert.ebattleMat.client.services.serviceData.PogList;
import per.lambert.ebattleMat.client.services.serviceData.SessionListData;

public class DungeonManager implements IDungeonManager {
	private DungeonServerError lastError;

	@Override
	public DungeonServerError getLastError() {
		return (lastError);
	}

	public DungeonManager() {
		players = (PogList) JavaScriptObject.createObject().cast();
	}

	private int token;

	SessionListData sessionListData;

	@Override
	public String[] getSessionNames() {
		if (sessionListData != null) {
			return (sessionListData.getSessionNames());
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
		if (selectedDungeon != null && currentLevel < selectedDungeon.getDungeonlevels().length) {
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

	Map<String, PogData> monsterTemplateMap = new HashMap<String, PogData>();
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

	private Map<String, String> dungeonToUUIDMap = new HashMap<String, String>();

	@Override
	public Map<String, String> getDungeonToUUIDMap() {
		return dungeonToUUIDMap;
	}

	private Map<String, String> uuidTemplatePathMap = new HashMap<String, String>();

	private String uuidOfMasterTemplate;
	
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

	private String serverPath;
	
	public String getServerPath() {
		return serverPath;
	}

	private void handleSuccessfulDungeonList(Object requestData, IUserCallback callback, Object data) {
		dungeonToUUIDMap.clear();
		uuidTemplatePathMap.clear();
		uuidOfMasterTemplate = null;
		DungeonListData dungeonListData = JsonUtils.<DungeonListData>safeEval((String) data);
		serverPath = dungeonListData.getServerPath();
		for (int i = 0; i < dungeonListData.getDungeonNames().length; ++i) {
			dungeonToUUIDMap.put(dungeonListData.getDungeonNames()[i], dungeonListData.getDungeonUUIDS()[i]);
			uuidTemplatePathMap.put(dungeonListData.getDungeonUUIDS()[i], dungeonListData.getDungeonDirectories()[i]);
			if (dungeonListData.getDungeonNames()[i].equals("Template Dungeon")) {
				uuidOfMasterTemplate = dungeonListData.getDungeonUUIDS()[i];
			}
		}
		callback.onSuccess(requestData, null);
	}

	@Override
	public void selectDungeon(String dungeonUUID) {
		initializeDungeonData();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", dungeonUUID);
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

	private String getDirectoryNameForSession(String newSessionName) {
		for (int i = 0; i < sessionListData.getSessionNames().length; ++i) {
			if (sessionListData.getSessionNames()[i].equals(newSessionName)) {
				return (sessionListData.getSessionDirectories()[i]);
			}
		}
		return (null);
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
			parameters.put("dungeonUUID", selectedDungeon.getUUID());
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
				loadMonsterPogTemplates(data);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
			}
		});
	}

	private void loadMonsterPogTemplates(Object data) {
		monsterTemplateMap.clear();
		monsterTemplatePogs = JsonUtils.<PogList>safeEval((String) data);
		for (PogData monsterTemplate : monsterTemplatePogs.getPogList()) {
			monsterTemplateMap.put(monsterTemplate.getUUID(), monsterTemplate);
		}
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.MonsterPogsLoaded, null));
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

	private String getDirectoryForCurrentDungeon() {
		return (uuidTemplatePathMap.get(selectedDungeon.getUUID()));
	}

	private String getDirectoryNameForDungeon(String dungeonUUID) {
		return (uuidTemplatePathMap.get(dungeonUUID));
	}

	@Override
	public String getUrlToDungeonResource(String resourceItem) {
		if (resourceItem.startsWith("http")) {
			return resourceItem;
		}
		String directoryForDungeon = getDirectoryForCurrentDungeon();
		String resourceUrl = directoryForDungeon + "/" + resourceItem + "?" + resourceCount++;
		return (resourceUrl);
	}

	@Override
	public void createNewDungeon(final String dungeonUUID, final String newDungeonName) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", dungeonUUID);
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
				dungeonDataChanged();
			}
		});
	}

	@Override
	public boolean okToDeleteThisTemplate(String dungeonUUID) {
		return !dungeonUUID.equals(uuidOfMasterTemplate);
	}

	@Override
	public void deleteTemplate(String dungeonUUID) {
		if (!okToDeleteThisTemplate(dungeonUUID)) {
			return;
		}
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", dungeonUUID);
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

	public PogData findMonsterTemplate(String pogUUID) {
		return (monsterTemplateMap.get(pogUUID));
	}

	@Override
	public PogData fullCLoneMonster(PogData pogData) {
		PogData template = findMonsterTemplate(pogData.getUUID());
		if (template == null) {
			return (null);
		}
		PogData clone = template.clone();
		clone.setPogColumn(pogData.getPogColumn());
		clone.setPogRow(pogData.getPogRow());
		return (clone);
	}

	@Override
	public PogData findCharacterPog(String pogUUID) {
		return (pcTemplateMap.get(pogUUID));
	}

	public void getSessionList(String dungeonUUID) {
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", dungeonUUID);
		dataRequester.requestData("", token, "GETSESSIONLIST", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				handleSuccessfulSessionList("", data);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
				lastError = error.getError();
			}
		});
	}

	private void handleSuccessfulSessionList(String string, Object data) {
		sessionListData = JsonUtils.<SessionListData>safeEval((String) data);
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.SessionListChanged, null));
	}

	@Override
	public boolean isNameValidForNewSession(String newSessionName) {
		boolean isValidSessionName = !newSessionName.startsWith("Enter ") && newSessionName.length() > 4;
		boolean isInCurrentSessionNames = false;
		boolean isInCurrentSessionDirectories = false;
		if (isValidSessionName) {
			isInCurrentSessionNames = isInCurrentSessionNames(newSessionName);
			isInCurrentSessionDirectories = isInCurrentSessionDirectories(newSessionName);
		}
		return isValidSessionName && !isInCurrentSessionNames && !isInCurrentSessionDirectories;
	}

	private boolean isInCurrentSessionDirectories(String newSessionName) {
		for (String sessionName : sessionListData.getSessionNames()) {
			if (sessionName.equals(newSessionName)) {
				return (true);
			}
		}
		return false;
	}

	private boolean isInCurrentSessionNames(String newSessionName) {
		String directoryName = newSessionName.replaceAll("\\s+", "_");
		for (String directory : sessionListData.getSessionDirectories()) {
			if (directory.equals(directoryName)) {
				return (true);
			}
		}
		return false;
	}

	@Override
	public void createNewSession(String dungeonUUID, String newSessionName) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", dungeonUUID);
		parameters.put("newSessionName", newSessionName);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", token, "CREATENEWSESSION", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				getSessionList(dungeonUUID);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
				lastError = error.getError();
			}
		});
	}

	@Override
	public void deleteSession(String selectedTemplate, String newSessionName) {
		Map<String, String> parameters = new HashMap<String, String>();
		String dungeonDirectory = getDirectoryNameForDungeon(selectedTemplate);
		parameters.put("templateName", dungeonDirectory);
		parameters.put("sessionName", getDirectoryNameForSession(newSessionName));
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", token, "DELETESESSION", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				getSessionList(selectedTemplate);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
				lastError = error.getError();
			}
		});
	}

	@Override
	public void joinSession(String newSessionName) {
	}
}
