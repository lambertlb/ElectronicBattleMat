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
import per.lambert.ebattleMat.client.interfaces.PogPlace;
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

/**
 * Manager for handling dungeon data.
 * 
 * @author LLambert
 *
 */
public class DungeonManager extends PogManager implements IDungeonManager {
	/**
	 * Last error from server.
	 */
	private DungeonServerError lastError;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DungeonServerError getLastError() {
		return (lastError);
	}

	/**
	 * construct a dungeon manager.
	 */
	public DungeonManager() {
	}

	/**
	 * Token from login credentials.
	 */
	private int token;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getToken() {
		return token;
	}

	/**
	 * List of session for this dungeon.
	 */
	private SessionListData sessionListData;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SessionListData getSessionListData() {
		return sessionListData;
	}

	/**
	 * UUID for selected dungeon.
	 */
	private String selectedDungeonUUID;
	/**
	 * UUID for selected session.
	 */
	private String selectedSessionUUID;
	/**
	 * Selected dungeon.
	 */
	private DungeonData selectedDungeon;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isThereASelectedDungeon() {
		return (selectedDungeon != null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DungeonData getSelectedDungeon() {
		return selectedDungeon;
	}

	/**
	 * selected session.
	 */
	private DungeonSessionData selectedSession;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DungeonSessionData getSelectedSession() {
		return selectedSession;
	}

	/**
	 * current level index.
	 */
	private int currentLevel;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNextLevelNumber() {
		return (selectedDungeon.getDungeonlevels().length + 1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentLevel(final int currentLevel) {
		this.currentLevel = currentLevel;
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonSelectedLevelChanged, null));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DungeonLevel getCurrentLevelData() {
		if (selectedDungeon != null && currentLevel < selectedDungeon.getDungeonlevels().length) {
			return (selectedDungeon.getDungeonlevels()[currentLevel]);
		}
		return null;
	}

	/**
	 * get the session data for the current level index.
	 * 
	 * @return session data for the current level index.
	 */
	private DungeonSessionLevel getCurrentSessionLevelData() {
		if (selectedSession != null && currentLevel < selectedSession.getSessionLevels().length) {
			return (selectedSession.getSessionLevels()[currentLevel]);
		}
		return null;
	}

	/**
	 * is the currently logged in person a dungeon master.
	 */
	private boolean isDungeonMaster;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDungeonMaster() {
		return isDungeonMaster;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDungeonMaster(final boolean isDungeonMaster) {
		this.isDungeonMaster = isDungeonMaster;
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DMStateChange, null));
	}

	/**
	 * Are we in edit mode on the dungeon.
	 */
	private boolean editMode;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEditMode() {
		return editMode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEditMode(final boolean editMode) {
		this.editMode = editMode;
	}

	/**
	 * Dungeon to uuid map.
	 */
	private Map<String, String> dungeonToUUIDMap = new HashMap<String, String>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getDungeonToUUIDMap() {
		return dungeonToUUIDMap;
	}

	/**
	 * Map of UUIDs to dungeon templates.
	 */
	private Map<String, String> uuidTemplatePathMap = new HashMap<String, String>();
	/**
	 * UUID of master template.
	 */
	private String uuidOfMasterTemplate;
	/**
	 * Path to server resources.
	 */
	private String serverPath;

	/**
	 * get Path to server resources.
	 * 
	 * @return Path to server resources.
	 */
	public String getServerPath() {
		return serverPath;
	}

	/**
	 * are we in fog of war toggle mode.
	 */
	private boolean fowToggle;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean getFowToggle() {
		return (fowToggle);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFowToggle(final boolean fowToggle) {
		this.fowToggle = fowToggle;
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.ToggleFowSelected, null));
	}

	/**
	 * counter to use for retrieving resources. By appending this number onto URL you are guaranteed to get from server vs cache.
	 */
	private int resourceCount = 1;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void login(final String username, final String password, final IUserCallback callback) {
		lastError = DungeonServerError.Succsess;
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("username", username);
		parameters.put("password", password);
		dataRequester.requestData("", "LOGIN", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
				handleSuccessfulLogin(username, callback, data);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
				lastError = error.getError();
				callback.onError(username, error);
			}
		});
	}

	/**
	 * Handle successful login.
	 * 
	 * @param requestData request data
	 * @param callback callback to user
	 * @param data from request
	 */
	private void handleSuccessfulLogin(final Object requestData, final IUserCallback callback, final Object data) {
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

	/**
	 * get list of dungeon data.
	 * 
	 * @param requestData request data
	 * @param callback user callback
	 */
	private void getDungeonList(final Object requestData, final IUserCallback callback) {
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		dataRequester.requestData("", "GETDUNGEONLIST", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
				handleSuccessfulDungeonList("", callback, data);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
				lastError = error.getError();
				callback.onError(requestData, error);
			}
		});
	}

	/**
	 * Handle successful read of dungeon list.
	 * 
	 * @param requestData request data
	 * @param callback user callback
	 * @param data data from response
	 */
	private void handleSuccessfulDungeonList(final Object requestData, final IUserCallback callback, final Object data) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void selectDungeon(final String dungeonUUID) {
		selectedDungeonUUID = dungeonUUID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editSelectedDungeonUUID() {
		loadSelectedDungeon();
	}

	/**
	 * load selected dungeon.
	 */
	private void loadSelectedDungeon() {
		initializeDungeonData();
		loadInResourceData();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", selectedDungeonUUID);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", "LOADJSONFILE", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
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
			public void onError(final Object sender, final IErrorInformation error) {
			}
		});
	}

	/**
	 * initialize dungeon data.
	 */
	private void initializeDungeonData() {
		currentLevel = 0;
		selectedDungeon = null;
		selectedSession = null;
		setSelectedPog(null);
		setPogBeingDragged(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDungeonData() {
		if (selectedDungeon != null) {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("dungeonUUID", selectedDungeon.getUUID());
			IDataRequester dataRequester = ServiceManager.getDataRequester();
			String dungeonDataString = JsonUtils.stringify(selectedDungeon);
			dataRequester.requestData(dungeonDataString, "SAVEJSONFILE", parameters, new IUserCallback() {

				@Override
				public void onSuccess(final Object sender, final Object data) {
					ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataSaved, null));
				}

				@Override
				public void onError(final Object sender, final IErrorInformation error) {
					lastError = error.getError();
				}
			});
		}
	}

	/**
	 * Load in resource data.
	 */
	private void loadInResourceData() {
		loadMonsterPogs();
		loadRoomObjectPogs();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSessionLevelSize(final int columns, final int rows) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFowSet(final int columns, final int rows) {
		if (editMode) {
			return (false);
		}
		DungeonSessionLevel sessionLevel = getCurrentSessionLevelData();
		if (sessionLevel == null) {
			return (false);
		}
		return (sessionLevel.isFowSet(columns, rows));
	}

	/**
	 * Fog of war data is dirty.
	 */
	private boolean fowDirty;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFow(final int columns, final int rows, final boolean value) {
		DungeonSessionLevel sessionLevel = getCurrentSessionLevelData();
		if (isDungeonMaster && sessionLevel != null) {
			sessionLevel.updateFOW(columns, rows, value);
			fowDirty = true;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveFow() {
		if (isDungeonMaster && fowDirty) {
			updateFogOfWar();
		}
		fowDirty = false;
	}

	/**
	 * Get directory for the current dungeon.
	 * 
	 * @return directory for the current dungeon.
	 */
	private String getDirectoryForCurrentDungeon() {
		return (uuidTemplatePathMap.get(selectedDungeon.getUUID()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUrlToDungeonResource(final String resourceItem) {
		if (resourceItem.startsWith("http")) {
			return resourceItem;
		}
		String resourceUrl = getUrlToDungeonData() + resourceItem + "?" + resourceCount++;
		return (resourceUrl);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUrlToDungeonData() {
		String directoryForDungeon = getDirectoryForCurrentDungeon();
		String resourceUrl = directoryForDungeon + "/";
		return (resourceUrl);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createNewDungeon(final String dungeonUUID, final String newDungeonName) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", dungeonUUID);
		parameters.put("newDungeonName", newDungeonName);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", "CREATENEWDUNGEON", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
				handlerNewDungeonCreated(newDungeonName);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
				lastError = error.getError();
			}
		});
	}

	/**
	 * Handle successful new dungeon created callback.
	 * 
	 * @param newDungeonName name of new dungeon
	 */
	private void handlerNewDungeonCreated(final String newDungeonName) {
		getDungeonList(null, new IUserCallback() {

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
			}

			@Override
			public void onSuccess(final Object sender, final Object data) {
				ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataCreated, null));
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean okToDeleteThisTemplate(final String dungeonUUID) {
		return !dungeonUUID.equals(uuidOfMasterTemplate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteTemplate(final String dungeonUUID) {
		if (!okToDeleteThisTemplate(dungeonUUID)) {
			return;
		}
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", dungeonUUID);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", "DELETEDUNGEON", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
				handlerDungeonDeleted();
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
				lastError = error.getError();
			}
		});
	}

	/**
	 * handle successful delete dungeon request.
	 */
	private void handlerDungeonDeleted() {
		getDungeonList("", new IUserCallback() {

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
			}

			@Override
			public void onSuccess(final Object sender, final Object data) {
				ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataDeleted, null));
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	public void getSessionList(final String dungeonUUID) {
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", dungeonUUID);
		dataRequester.requestData("", "GETSESSIONLIST", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
				handleSuccessfulSessionList(data);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
				lastError = error.getError();
			}
		});
	}

	/**
	 * handle successful session list request.
	 * 
	 * @param data from response
	 */
	private void handleSuccessfulSessionList(final Object data) {
		sessionListData = JsonUtils.<SessionListData>safeEval((String) data);
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.SessionListChanged, null));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isNameValidForNewSession(final String newSessionName) {
		boolean isValidSessionName = !newSessionName.startsWith("Enter ") && newSessionName.length() > 4;
		boolean isInCurrentSessionNames = false;
		boolean isInCurrentSessionDirectories = false;
		if (isValidSessionName) {
			isInCurrentSessionNames = isInCurrentSessionNames(newSessionName);
		}
		return isValidSessionName && !isInCurrentSessionNames && !isInCurrentSessionDirectories;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValidNewCharacterName(final String characterName) {
		boolean isValid = !characterName.startsWith("Enter ") && characterName.length() > 3;
		return isValid;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValidNewMonsterName(final String monsterName) {
		boolean isValid = !monsterName.startsWith("Enter ") && monsterName.length() > 3;
		return isValid;
	}

	/**
	 * Is this session name in the current list.
	 * 
	 * @param newSessionName new session name
	 * @return true if in list
	 */
	private boolean isInCurrentSessionNames(final String newSessionName) {
		for (String sessionName : sessionListData.getSessionNames()) {
			if (sessionName.equals(newSessionName)) {
				return (true);
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createNewSession(final String dungeonUUID, final String newSessionName) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", dungeonUUID);
		parameters.put("newSessionName", newSessionName);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", "CREATENEWSESSION", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
				getSessionList(dungeonUUID);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
				lastError = error.getError();
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteSession(final String dungeonUUID, final String sessionUUID) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", dungeonUUID);
		parameters.put("sessionUUID", sessionUUID);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", "DELETESESSION", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
				getSessionList(dungeonUUID);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
				lastError = error.getError();
			}
		});
	}

	/**
	 * Load in data for session. This will do a version test. If the version hasn't changed then nothing is returned.
	 * 
	 * @param versionToTest see if it is this version.
	 */
	private void loadSessionData(final int versionToTest) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", selectedDungeonUUID);
		parameters.put("sessionUUID", selectedSessionUUID);
		parameters.put("version", "" + versionToTest);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData("", "LOADSESSION", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
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
			public void onError(final Object sender, final IErrorInformation error) {
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void joinSession(final String sessionUUID) {
		selectedSessionUUID = sessionUUID;
		loadSelectedDungeon();
	}

	/**
	 * update fog of war.
	 */
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
				public void onSuccess(final Object sender, final Object data) {
					ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.SessionDataSaved, null));
				}

				@Override
				public void onError(final Object sender, final IErrorInformation error) {
					lastError = error.getError();
				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doTimedTasks() {
		if (selectedSession != null) {
			loadSessionData(selectedSession.getVersion());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void downloadDungeonFile(final String fileName) {
		String url = getUrlToDungeonData() + fileName;
		makeSureLoaderExists();
		downloadFile(fileName, url);
	}

	/**
	 * Make sure download element exists.
	 */
	public native void makeSureLoaderExists() /*-{
		var aLink = document.getElementById('downloader');
		if (aLink == null) {
			aLink = document.createElement('a');
			aLink.setAttribute('id', 'downloader');
			document.body.appendChild(aLink);
		}
	}-*/;

	/**
	 * Download file using loader.
	 * 
	 * @param fileName to download
	 * @param urlData URL to resource
	 */
	public native void downloadFile(String fileName, String urlData) /*-{
		//		var aLink = document.createElement('a');
		var aLink = document.getElementById('downloader');
		aLink.download = fileName;
		aLink.href = encodeURI(urlData);
		var event = new MouseEvent('click');
		aLink.dispatchEvent(event);
	}-*/;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLegalDungeonName(final String nameToCheck) {
		if (nameToCheck == null || nameToCheck.isEmpty() || nameToCheck.length() < 4) {
			return (false);
		}
		return (true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNewLevel(final DungeonLevel newLevel) {
		selectedDungeon.addDungeonlevel(newLevel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData findCharacterPog(final String uuid) {
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

	/////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updatePogDataOnLevel(final PogData pog) {
		if (editMode) {
			updatePogDataInTemplateLevel(pog);
		}
		if (isDungeonMaster) {
			updatePodDataInSessionLevel(pog);
		}
	}

	/**
	 * Update pog data in dungeon data.
	 * 
	 * @param pog to update
	 */
	private void updatePogDataInTemplateLevel(final PogData pog) {
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

	/**
	 * Update pog list for this level.
	 * 
	 * @param pog to update
	 * @param poglist pog list to update
	 */
	private void updatePogListInLevel(final PogData pog, final PogData[] poglist) {
		for (PogData pogData : poglist) {
			if (pog.getUUID().equals(pogData.getUUID())) {
				pogData.setPogColumn(pog.getPogColumn());
				pogData.setPogRow(pog.getPogRow());
				saveDungeonData();
				break;
			}
		}
	}

	/**
	 * Update pog in session data.
	 * 
	 * @param pog to update
	 */
	private void updatePodDataInSessionLevel(final PogData pog) {
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

	/**
	 * Update a pog within a list.
	 * 
	 * @param pog to update
	 * @param poglist pog list
	 */
	private void updatePogInSession(final PogData pog, final PogData[] poglist) {
		for (PogData pogData : poglist) {
			if (pog.getUUID().equals(pogData.getUUID())) {
				pogData.setPogColumn(pog.getPogColumn());
				pogData.setPogRow(pog.getPogRow());
				savePogToSessionData(pog, false);
				break;
			}
		}
	}

	/**
	 * Save pog to session data.
	 * 
	 * @param pogData to upsate
	 * @param needToAdd true if needs tro be added verus updated
	 */
	private void savePogToSessionData(final PogData pogData, final boolean needToAdd) {
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
				public void onSuccess(final Object sender, final Object data) {
					ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.SessionDataSaved, null));
				}

				@Override
				public void onError(final Object sender, final IErrorInformation error) {
					lastError = error.getError();
				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addOrUpdatePogData(final PogData pog) {
		if (editMode) {
			addPogToTemplate(pog);
		}
		if (isDungeonMaster || pog.isThisAPlayer()) {
			addPogToSession(pog);
		}
	}

	/**
	 * Add pog to dungeon template.
	 * 
	 * @param pog to add
	 */
	private void addPogToTemplate(final PogData pog) {
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

	/**
	 * Add pog to session.
	 * 
	 * @param pog to add
	 */
	private void addPogToSession(final PogData pog) {
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

	/**
	 * Add pog to session if not there already.
	 * 
	 * @param pog to add
	 * @param sessionData session data
	 * @return true if added
	 */
	private boolean addPogToSessionIfNotThere(final PogData pog, final DungeonSessionData sessionData) {
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

	/**
	 * Add or update Pog.
	 * 
	 * @param pog to add
	 * @param place where to add
	 */
	@Override
	public void addOrUpdatePog(final PogData pog, final PogPlace place) {
		if (place == PogPlace.COMMON_RESOURCE) {
			addOrUpdatePogToCommonResource(pog);
		} else if (place == PogPlace.DUNGEON_RESOURCE) {
			addOrUpdatePogToDungeonResource(pog);
		} else if (place == PogPlace.DUNGEON_INSTANCE) {
			addOrUpdatePogToDungeonInstance(pog);
		} else if (place == PogPlace.SESSION_RESOURCE) {
			addOrUpdatePogToSessionResource(pog);
		} else if (place == PogPlace.SESSION_INSTANCE) {
			addOrUpdatePogToSessionInstance(pog);
		}
		addOrUpdatePogToServer(pog, place);
	}

	/**
	 * Add pog to dungeon resource area.
	 * 
	 * @param pog to add
	 */
	private void addOrUpdatePogToDungeonResource(final PogData pog) {
	}

	/**
	 * Add pog to dungeon instance area.
	 * 
	 * @param pog to add
	 */
	private void addOrUpdatePogToDungeonInstance(final PogData pog) {
	}

	/**
	 * Add pog to session instance area.
	 * 
	 * @param pog to add
	 */
	private void addOrUpdatePogToSessionInstance(final PogData pog) {
	}

	/**
	 * Add pog to session resource area.
	 * 
	 * @param pog to add
	 */
	private void addOrUpdatePogToSessionResource(final PogData pog) {
	}

	/**
	 * Save Pog to server.
	 * 
	 * @param pog to save
	 * @param place to save
	 */
	private void addOrUpdatePogToServer(final PogData pog, final PogPlace place) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dungeonUUID", selectedDungeon.getUUID());
		if (selectedSession != null) {
			parameters.put("sessionUUID", selectedSession.getSessionUUID());
		} else {
			parameters.put("sessionUUID", "");
		}
		parameters.put("currentLevel", "" + currentLevel);
		parameters.put("place", place.name());
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		String pogDataString = JsonUtils.stringify(pog);
		dataRequester.requestData(pogDataString, "ADDORUPDATEPOG", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
				ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.SessionDataSaved, null));
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
				lastError = error.getError();
			}
		});
	}
}
