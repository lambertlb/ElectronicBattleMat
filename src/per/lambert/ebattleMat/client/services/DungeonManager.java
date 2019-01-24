package per.lambert.ebattleMat.client.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;

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
import per.lambert.ebattleMat.client.services.serviceData.DungeonSessionData;
import per.lambert.ebattleMat.client.services.serviceData.DungeonSessionLevel;
import per.lambert.ebattleMat.client.services.serviceData.FogOfWarData;
import per.lambert.ebattleMat.client.services.serviceData.LoginResponseData;
import per.lambert.ebattleMat.client.services.serviceData.PogData;
import per.lambert.ebattleMat.client.services.serviceData.SessionListData;

public class DungeonManager extends PogManager implements IDungeonManager {
	private DungeonServerError lastError;

	@Override
	public DungeonServerError getLastError() {
		return (lastError);
	}

	public DungeonManager() {
	}

	private int token;

	@Override
	public int getToken() {
		return token;
	}

	private SessionListData sessionListData;

	@Override
	public SessionListData getSessionListData() {
		return sessionListData;
	}

	private String selectedDungeonUUID;
	private String selectedSessionUUID;

	private DungeonData selectedDungeon;

	@Override
	public boolean dungeonSelected() {
		return (selectedDungeon != null);
	}

	@Override
	public DungeonData getSelectedDungeon() {
		return selectedDungeon;
	}

	private DungeonSessionData selectedSession;

	@Override
	public DungeonSessionData getSelectedSession() {
		return selectedSession;
	}

	private int currentLevel;

	@Override
	public int getCurrentLevel() {
		return currentLevel;
	}

	@Override
	public int getNextLevelNumber() {
		return (selectedDungeon.getDungeonlevels().length + 1);
	}

	@Override
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonSelectedLevelChanged, null));
	}

	@Override
	public DungeonLevel getCurrentLevelData() {
		if (selectedDungeon != null && currentLevel < selectedDungeon.getDungeonlevels().length) {
			return (selectedDungeon.getDungeonlevels()[currentLevel]);
		}
		return null;
	}

	public DungeonSessionLevel getCurrentSessionLevelData() {
		if (selectedSession != null && currentLevel < selectedSession.getSessionLevels().length) {
			return (selectedSession.getSessionLevels()[currentLevel]);
		}
		return null;
	}

	private boolean isDungeonMaster;

	@Override
	public boolean isDungeonMaster() {
		return isDungeonMaster;
	}

	@Override
	public void setDungeonMaster(boolean isDungeonMaster) {
		this.isDungeonMaster = isDungeonMaster;
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DMStateChange, null));
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
	private String serverPath;

	public String getServerPath() {
		return serverPath;
	}

	@Override
	public void login(final String username, final String password, IUserCallback callback) {
		lastError = DungeonServerError.Succsess;
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", username);
		parameters.put("password", password);
		dataRequester.requestData("", "LOGIN", parameters, new IUserCallback() {

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
		dataRequester.requestData("", "GETDUNGEONLIST", parameters, new IUserCallback() {

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
		selectedDungeonUUID = dungeonUUID;
	}

	@Override
	public void editSelectedDungeon() {
		loadSelectedDungeon();
	}

	private void loadSelectedDungeon() {
		initializeDungeonData();
		loadInResourceData();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", selectedDungeonUUID);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", "LOADJSONFILE", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				selectedDungeon = JsonUtils.<DungeonData>safeEval((String) data);
				ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonSelected, null));
				ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataLoaded, null));
				if (!editMode) {
					loadSessionData(-1);
				} else {
					ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataReadyToEdit, null));
				}
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
			}
		});
	}

	private void initializeDungeonData() {
		currentLevel = 0;
		selectedDungeon = null;
		selectedSession = null;
		setSelectedPog(null);
		setPogBeingDragged(null);
	}

	@Override
	public void saveDungeonData() {
		if (selectedDungeon != null) {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("dungeonUUID", selectedDungeon.getUUID());
			IDataRequester dataRequester = ServiceManager.getDataRequester();
			String dungeonDataString = JsonUtils.stringify(selectedDungeon);
			dataRequester.requestData(dungeonDataString, "SAVEJSONFILE", parameters, new IUserCallback() {

				@Override
				public void onSuccess(Object sender, Object data) {
					ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataSaved, null));
				}

				@Override
				public void onError(Object sender, IErrorInformation error) {
					lastError = error.getError();
				}
			});
		}
	}

	private void loadInResourceData() {
		loadMonsterPogs();
		loadRoomObjectPogs();
	}

	@Override
	public void setSessionLevelSize(int columns, int rows) {
		DungeonLevel dungeonLevel = getCurrentLevelData();
		if (!isDungeonMaster || dungeonLevel == null) {
			return;
		}
		if (dungeonLevel.getColumns() == columns && dungeonLevel.getRows() == rows) {
			return;
		}
		dungeonLevel.setColumns(columns);
		dungeonLevel.setRows(rows);
		saveDungeonData();
	}

	@Override
	public boolean isFowSet(int columns, int rows) {
		if (editMode) {
			return (false);
		}
		DungeonSessionLevel sessionLevel = getCurrentSessionLevelData();
		if (sessionLevel == null) {
			return (false);
		}
		return (sessionLevel.isFowSet(columns, rows));
	}

	private boolean fowDirty;

	@Override
	public void setFow(int columns, int rows, boolean value) {
		DungeonSessionLevel sessionLevel = getCurrentSessionLevelData();
		if (isDungeonMaster && sessionLevel != null) {
			sessionLevel.updateFOW(columns, rows, value);
			fowDirty = true;
		}
	}

	@Override
	public void saveFow() {
		if (isDungeonMaster && fowDirty) {
			updateFogOfWar();
		}
		fowDirty = false;
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

	@Override
	public String getUrlToDungeonResource(String resourceItem) {
		if (resourceItem.startsWith("http")) {
			return resourceItem;
		}
		String resourceUrl = getUrlToDungeonData() + resourceItem + "?" + resourceCount++;
		return (resourceUrl);
	}

	@Override
	public String getUrlToDungeonData() {
		String directoryForDungeon = getDirectoryForCurrentDungeon();
		String resourceUrl = directoryForDungeon + "/";
		return (resourceUrl);
	}

	@Override
	public void createNewDungeon(final String dungeonUUID, final String newDungeonName) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", dungeonUUID);
		parameters.put("newDungeonName", newDungeonName);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", "CREATENEWDUNGEON", parameters, new IUserCallback() {

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
				ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataCreated, null));
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
		dataRequester.requestData("", "DELETEDUNGEON", parameters, new IUserCallback() {

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
				ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataDeleted, null));
			}
		});
	}

	public void getSessionList(String dungeonUUID) {
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", dungeonUUID);
		dataRequester.requestData("", "GETSESSIONLIST", parameters, new IUserCallback() {

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
		}
		return isValidSessionName && !isInCurrentSessionNames && !isInCurrentSessionDirectories;
	}

	@Override
	public boolean isValidNewCharacterName(String characterName) {
		boolean isValid = !characterName.startsWith("Enter ") && characterName.length() > 3;
		return isValid;
	}

	@Override
	public boolean isValidNewMonsterName(String monsterName) {
		boolean isValid = !monsterName.startsWith("Enter ") && monsterName.length() > 3;
		return isValid;
	}

	private boolean isInCurrentSessionNames(String newSessionName) {
		for (String sessionName : sessionListData.getSessionNames()) {
			if (sessionName.equals(newSessionName)) {
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
		dataRequester.requestData("", "CREATENEWSESSION", parameters, new IUserCallback() {

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
	public void deleteSession(String dungeonUUID, String sessionUUID) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", dungeonUUID);
		parameters.put("sessionUUID", sessionUUID);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", "DELETESESSION", parameters, new IUserCallback() {

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

	private void loadSessionData(final int versionToTest) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", selectedDungeonUUID);
		parameters.put("sessionUUID", selectedSessionUUID);
		parameters.put("version", "" + versionToTest);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", "LOADSESSION", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				String jsonData = (String) data;
				if (!jsonData.isEmpty()) {
					selectedSession = JsonUtils.<DungeonSessionData>safeEval(jsonData);
					if (versionToTest == -1) {
						ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataReadyToJoin, null));
					} else {
						ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.SessionDataChanged, null));
					}
				}
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
			}
		});
	}

	@Override
	public void joinSession(String sessionUUID) {
		selectedSessionUUID = sessionUUID;
		loadSelectedDungeon();
	}

	@Override
	public void updatePogDataOnLevel(PogData pog) {
		if (editMode) {
			updatePogDataInTemplateLevel(pog);
		}
		if (isDungeonMaster) {
			updatePodDataInSessionLevel(pog);
		}
	}

	private void updatePogDataInTemplateLevel(PogData pog) {
		DungeonLevel dungeonLevel = getCurrentLevelData();
		if (dungeonLevel == null) {
			return;
		}
		if (pog.isThisAMonster()) {
			updatePogListInLevel(pog, dungeonLevel.getMonsters());
		}
		if (pog.isThisARoomObject()) {
			updatePogListInLevel(pog, dungeonLevel.getRoomObjects());
		}
	}

	private void updatePogListInLevel(PogData pog, PogData[] poglist) {
		for (PogData pogData : poglist) {
			if (pog.getUUID().equals(pogData.getUUID())) {
				pogData.setPogColumn(pog.getPogColumn());
				pogData.setPogRow(pog.getPogRow());
				saveDungeonData();
				break;
			}
		}
	}

	private void updatePodDataInSessionLevel(PogData pog) {
		DungeonSessionLevel sessionLevel = getCurrentSessionLevelData();
		if (sessionLevel == null) {
			return;
		}
		if (pog.isThisAPlayer()) {
			updatePogInSession(pog, selectedSession.getPlayers());
		} else if (pog.isThisAMonster()) {
			updatePogInSession(pog, sessionLevel.getMonsters());
		} else if (pog.isThisARoomObject()) {
			updatePogInSession(pog, sessionLevel.getRoomObjects());
		}
	}

	private void updatePogInSession(PogData pog, PogData[] poglist) {
		for (PogData pogData : poglist) {
			if (pog.getUUID().equals(pogData.getUUID())) {
				pogData.setPogColumn(pog.getPogColumn());
				pogData.setPogRow(pog.getPogRow());
				savePogToSessionData(pog, false);
				break;
			}
		}
	}

	private void updateFogOfWar() {
		if (selectedDungeon != null) {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("sessionUUID", selectedSession.getSessionUUID());
			parameters.put("currentLevel", "" + currentLevel);
			DungeonSessionLevel sessionLevel = getCurrentSessionLevelData();
			FogOfWarData fogOfWarData = (FogOfWarData) JavaScriptObject.createObject().cast();
			fogOfWarData.setFOW(sessionLevel.getFOW());
			String fowDataString = JsonUtils.stringify(fogOfWarData);
			IDataRequester dataRequester = ServiceManager.getDataRequester();
			dataRequester.requestData(fowDataString, "UPDATEFOW", parameters, new IUserCallback() {

				@Override
				public void onSuccess(Object sender, Object data) {
					ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.SessionDataSaved, null));
				}

				@Override
				public void onError(Object sender, IErrorInformation error) {
					lastError = error.getError();
				}
			});
		}
	}

	private void savePogToSessionData(PogData pogData, boolean needToAdd) {
		if (selectedDungeon != null) {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("dungeonUUID", selectedDungeon.getUUID());
			parameters.put("sessionUUID", selectedSession.getSessionUUID());
			parameters.put("currentLevel", "" + currentLevel);
			parameters.put("needToAdd", "" + needToAdd);
			IDataRequester dataRequester = ServiceManager.getDataRequester();
			String pogDataString = JsonUtils.stringify(pogData);
			dataRequester.requestData(pogDataString, "SAVEPOGTOSESSION", parameters, new IUserCallback() {

				@Override
				public void onSuccess(Object sender, Object data) {
					ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.SessionDataSaved, null));
				}

				@Override
				public void onError(Object sender, IErrorInformation error) {
					lastError = error.getError();
				}
			});
		}
	}

	@Override
	public void addOrUpdatePogData(PogData pog) {
		if (editMode) {
			addPogToTemplate(pog);
		}
		if (isDungeonMaster || pog.isThisAPlayer()) {
			addPogToSession(pog);
		}
	}

	private void addPogToTemplate(PogData pog) {
		DungeonLevel dungeonLevel = getCurrentLevelData();
		if (dungeonLevel == null) {
			return;
		}
		if (pog.isThisAMonster()) {
			if (findMonsterPog(pog.getUUID()) == null) {
				dungeonLevel.addMonster(pog);
			}
		} else {
			if (findRoomObjectPog(pog.getUUID()) == null) {
				dungeonLevel.addRoomObject(pog);
			}
		}
		saveDungeonData();
	}

	private void addPogToSession(PogData pog) {
		DungeonSessionData sessionData = selectedSession;
		boolean needToAdd = true;
		if (sessionData != null) {
			if (pog.isThisAPlayer()) {
				needToAdd = addPogToSessionIfNotThere(pog, sessionData);
			} else {
				DungeonSessionLevel sessionLevel = getCurrentSessionLevelData();
				if (sessionLevel != null) {
					if (pog.isThisAMonster()) {
						if (findMonsterPog(pog.getUUID()) == null) {
							sessionLevel.addMonster(pog);
						}
					} else {
						if (findRoomObjectPog(pog.getUUID()) == null) {
							sessionLevel.addRoomObject(pog);
						}
					}
				}
			}
			savePogToSessionData(pog, needToAdd);
		}
	}

	private boolean addPogToSessionIfNotThere(PogData pog, DungeonSessionData sessionData) {
		pog.setDungeonLevel(currentLevel);
		for (PogData player : sessionData.getPlayers()) {
			if (player.getTemplateUUID().equals(pog.getTemplateUUID())) {
				return (false);
			}
		}
		pog.setDungeonLevel(-1);
		sessionData.addPlayer(pog);
		return (true);
	}

	@Override
	public PogData[] getMonstersForCurrentLevel() {
		if (selectedDungeon == null) {
			return null;
		}
		if (editMode) {
			DungeonLevel currentLevel = getCurrentLevelData();
			if (currentLevel == null) {
				return null;
			}
			return (currentLevel.getMonsters());
		}
		DungeonSessionLevel sessionLevel = getCurrentSessionLevelData();
		if (sessionLevel == null) {
			return null;
		}
		return sessionLevel.getMonsters();
	}

	@Override
	public PogData[] getRoomObjectsForCurrentLevel() {
		if (selectedDungeon == null) {
			return null;
		}
		if (editMode) {
			DungeonLevel currentLevel = getCurrentLevelData();
			if (currentLevel == null) {
				return null;
			}
			return (currentLevel.getRoomObjects());
		}
		DungeonSessionLevel sessionLevel = getCurrentSessionLevelData();
		if (sessionLevel == null) {
			return null;
		}
		return sessionLevel.getRoomObjects();
	}

	@Override
	public PogData[] getPlayersForCurrentSession() {
		if (editMode || selectedDungeon == null || selectedSession == null) {
			return null;
		}
		int currentLevel = getCurrentLevel();
		ArrayList<PogData> playersOnLevel = new ArrayList<PogData>();
		for (PogData player : selectedSession.getPlayers()) {
			if (player.getDungeonLevel() == currentLevel) {
				playersOnLevel.add(player);
			}
		}
		return (PogData[]) playersOnLevel.toArray(new PogData[playersOnLevel.size()]);
	}

	@Override
	public String[] getDungeonLevelNames() {
		if (selectedDungeon == null) {
			return (new String[0]);
		}
		DungeonLevel[] levels = selectedDungeon.getDungeonlevels();
		String[] levelNames = new String[levels.length];
		for (int i = 0; i < levels.length; ++i) {
			levelNames[i] = levels[i].getLevelName();
		}
		return (levelNames);
	}

	@Override
	public void doTimedTasks() {
		if (selectedSession != null) {
			loadSessionData(selectedSession.getVersion());
		}
	}

	@Override
	public void downloadDungeonFile(String fileName) {
		String url = getUrlToDungeonData() + fileName;
		makeSureLoaderExists();
		downloadFile(fileName, url);
	}

	public native void makeSureLoaderExists() /*-{
		var aLink = document.getElementById('downloader');
		if (aLink == null) {
			aLink = document.createElement('a');
			aLink.setAttribute('id', 'downloader');
			document.body.appendChild(aLink);
		}
	}-*/;

	public native void downloadFile(String fileName, String urlData) /*-{
		//		var aLink = document.createElement('a');
		var aLink = document.getElementById('downloader');
		aLink.download = fileName;
		aLink.href = encodeURI(urlData);
		var event = new MouseEvent('click');
		aLink.dispatchEvent(event);
	}-*/;

	@Override
	public boolean isLegalDungeonName(String nameToCheck) {
		if (nameToCheck == null || nameToCheck.isEmpty() || nameToCheck.length() < 4) {
			return (false);
		}
		return (true);
	}

	@Override
	public void createNewLevel(DungeonLevel newLevel) {
		selectedDungeon.addDungeonlevel(newLevel);
	}

	@Override
	public PogData findCharacterPog(String uuid) {
		if (selectedSession == null) {
			return null;
		}
		for (PogData pog : selectedSession.getPlayers()) {
			if (pog.getUUID().equals(uuid)) {
				return (pog);
			}
		}
		return null;
	}
}
