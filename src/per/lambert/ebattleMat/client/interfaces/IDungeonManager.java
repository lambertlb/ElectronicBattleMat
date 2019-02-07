package per.lambert.ebattleMat.client.interfaces;

import java.util.Map;

import per.lambert.ebattleMat.client.services.serviceData.DungeonData;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
import per.lambert.ebattleMat.client.services.serviceData.DungeonSessionData;
import per.lambert.ebattleMat.client.services.serviceData.PogData;
import per.lambert.ebattleMat.client.services.serviceData.SessionListData;

/**
 * @author LLambert Interface to user management services
 */
public interface IDungeonManager extends IPogManager {
	/**
	 * get last error that occured.
	 * 
	 * @return last error
	 */
	DungeonServerError getLastError();

	/**
	 * Log in user.
	 * 
	 * @param username user name.
	 * @param password of user
	 * @param callback when complete
	 */
	void login(String username, String password, IUserCallback callback);

	/**
	 * Is logged in as dungeon master.
	 * 
	 * @return true if dungeon master.
	 */
	boolean isDungeonMaster();

	/**
	 * set true if dungeon master.
	 * 
	 * @param isDungeonMaster true if dungeon master
	 */
	void setDungeonMaster(boolean isDungeonMaster);

	/**
	 * Is current dungeon in edit mode.
	 * 
	 * @return true if in edit mode.
	 */
	boolean isEditMode();

	/**
	 * Set edit mode of current dungeon.
	 * 
	 * @param editMode true if in edit mode.
	 */
	void setEditMode(boolean editMode);

	/**
	 * Select a dungeon UUID for future operations.
	 * 
	 * @param dungeonUUID to select
	 */
	void selectDungeon(String dungeonUUID);

	/**
	 * Edit the selected dungeon based on selected UUID.
	 */
	void editSelectedDungeonUUID();

	/**
	 * get dungeon data for selected dungeon.
	 * 
	 * @return dungeon data for selected dungeon.
	 */
	DungeonData getSelectedDungeon();

	/**
	 * Is there a selected dungeon.
	 * 
	 * @return true if there is.
	 */
	boolean isThereASelectedDungeon();

	/**
	 * Get map of dungeon names to UUIDs.
	 * 
	 * @return map of dungeon names to UUIDs.
	 */
	Map<String, String> getDungeonToUUIDMap();

	/**
	 * get current level of selected dungeon.
	 * 
	 * @return current level
	 */
	int getCurrentLevel();

	/**
	 * Set current level for selected dungeon.
	 * 
	 * @param currentLevel for selected dungeon
	 */
	void setCurrentLevel(int currentLevel);

	/**
	 * get dungeon data for current level.
	 * 
	 * @return dungeon data for current level.
	 */
	DungeonLevel getCurrentLevelData();

	/**
	 * Save dungeon data to server.
	 */
	void saveDungeonData();

	/**
	 * Set session level size in columns and rows.
	 * 
	 * @param columns in session level
	 * @param rows in session level
	 */
	void setSessionLevelSize(int columns, int rows);

	/**
	 * Save fog of war to server.
	 */
	void saveFow();

	/**
	 * Is fog of war set for this cell?
	 * 
	 * @param columns of cell
	 * @param rows of cell
	 * @return true if set
	 */
	boolean isFowSet(int columns, int rows);

	/**
	 * Set fog of war for this cell.
	 * 
	 * @param columns of cell
	 * @param rows of cell
	 * @param value true if set
	 */
	void setFow(int columns, int rows, boolean value);

	/**
	 * get toggle for fog of war.
	 * 
	 * @return true if we are changing fog of war.
	 */
	boolean getFowToggle();

	/**
	 * Set toggle for fog of war.
	 * 
	 * @param fowToggle true if changing.
	 */
	void setFowToggle(boolean fowToggle);

	/**
	 * Create a new dungeon based on template dungeon.
	 * 
	 * @param dungeonUUID UUID of template dungeon
	 * @param newDungeonName new dungeon name
	 */
	void createNewDungeon(String dungeonUUID, String newDungeonName);

	/**
	 * Get URL to this resource item.
	 * 
	 * @param resourceItem needing URL
	 * @return URL to resource
	 */
	String getUrlToDungeonResource(String resourceItem);

	/**
	 * Is it ok to delete this template dungeon.
	 * 
	 * The master template dungeon is not ok to delete.
	 * 
	 * @param dungeonUUID to test.
	 * @return true if ok to delete
	 */
	boolean okToDeleteThisTemplate(String dungeonUUID);

	/**
	 * Delete this dungeon.
	 * 
	 * @param dungeonUUID of dungeon to delete
	 */
	void deleteTemplate(String dungeonUUID);

	/**
	 * Get the list of session for this dungeon.
	 * 
	 * @return list of sessions.
	 */
	SessionListData getSessionListData();

	/**
	 * get session list for dungeon from server.
	 * 
	 * @param dungeonUUID UUID of dungeon
	 */
	void getSessionList(String dungeonUUID);

	/**
	 * Is this a valid session name?
	 * 
	 * @param newSessionName name to check
	 * @return true if valid
	 */
	boolean isNameValidForNewSession(String newSessionName);

	/**
	 * get selected session.
	 * 
	 * @return selected session.
	 */
	DungeonSessionData getSelectedSession();

	/**
	 * Create a new session for this dungeon.
	 * 
	 * @param dungeonUUID dungeon needing session
	 * @param newSessionName new session name
	 */
	void createNewSession(String dungeonUUID, String newSessionName);

	/**
	 * Join this session as a player.
	 * 
	 * @param sessionUUID UUID of session
	 */
	void joinSession(String sessionUUID);

	/**
	 * Delete the specified session in the dungeon.
	 * 
	 * @param dungeonUUID with sessions
	 * @param sessionUUID to delete
	 */
	void deleteSession(String dungeonUUID, String sessionUUID);

	/**
	 * Get list of monster on current level.
	 * 
	 * if in edit mode this will be list of monster in template else it will be a list of monster in session.
	 * 
	 * @return list of monsters.
	 */
	PogData[] getMonstersForCurrentLevel();

	/**
	 * Get list of player in this session.
	 * 
	 * @return list of player in this session.
	 */
	PogData[] getPlayersForCurrentSession();

	/**
	 * get list room objects on current level.
	 * 
	 * if in edit mode this will be list of room objects in template else it will be a list of room objects in session.
	 * 
	 * @return list room objects on current level.
	 */
	PogData[] getRoomObjectsForCurrentLevel();

	/**
	 * get list of level names.
	 * 
	 * @return list of level names
	 */
	String[] getDungeonLevelNames();

	/**
	 * Do periodic tasks.
	 */
	void doTimedTasks();

	/**
	 * Download this dungeon related file.
	 * 
	 * @param fileName to download.
	 */
	void downloadDungeonFile(String fileName);

	/**
	 * get URL to dungeon data.
	 * 
	 * @return URL to dungeon data.
	 */
	String getUrlToDungeonData();

	/**
	 * get login token.
	 * 
	 * @return login token.
	 */
	int getToken();

	/**
	 * is this a legal dungeon name?
	 * 
	 * @param nameToCheck name to check.
	 * @return true if legal
	 */
	boolean isLegalDungeonName(String nameToCheck);

	/**
	 * get next available dungeon level number.
	 * 
	 * @return next available dungeon level number.
	 */
	int getNextLevelNumber();

	/**
	 * Add this new level to dungeon.
	 * 
	 * @param newLevelToAdd new level to add
	 */
	void addNewLevel(DungeonLevel newLevelToAdd);

	/**
	 * Is this a valid character name.
	 * 
	 * @param characterName to check
	 * @return true if valid
	 */
	boolean isValidNewCharacterName(String characterName);

	/**
	 * FInd character pog in dungeon.
	 * @param uuid of character
	 * @return pog data if found
	 */
	PogData findCharacterPog(String uuid);

	/**
	 * is this valid monster name.
	 * @param monsterName to check
	 * @return true if valid
	 */
	boolean isValidNewMonsterName(String monsterName);

	/**
	 * Add or update pog to proper place.
	 * @param pog to add
	 * @param place to add
	 */
	void addOrUpdatePog(PogData pog, PogPlace place);

	/**
	 * Compute place pog should go.
	 * 
	 * @param pog to put
	 * @return Pog Place
	 */
	PogPlace computePlace(PogData pog);

	/**
	 * Add or update Pog.
	 * 
	 * @param pog to add
	 */
	void addOrUpdatePog(PogData pog);

}
