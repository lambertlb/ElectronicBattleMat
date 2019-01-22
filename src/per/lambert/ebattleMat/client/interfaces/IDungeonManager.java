package per.lambert.ebattleMat.client.interfaces;

import java.util.ArrayList;
import java.util.Map;

import per.lambert.ebattleMat.client.services.serviceData.DungeonData;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
import per.lambert.ebattleMat.client.services.serviceData.DungeonSessionData;
import per.lambert.ebattleMat.client.services.serviceData.PogData;
import per.lambert.ebattleMat.client.services.serviceData.SessionListData;

/**
 * @author LLambert Interface to user management services
 */
public interface IDungeonManager {
	void login(final String username, final String password, IUserCallback callback);

	boolean isDungeonMaster();

	boolean isEditMode();

	void setEditMode(boolean editMode);

	void setDungeonMaster(boolean isDungeonMaster);

	DungeonServerError getLastError();

	Map<String, String> getDungeonToUUIDMap();

	void selectDungeon(String dungeonUUID);

	void editSelectedDungeon();

	DungeonData getSelectedDungeon();

	boolean dungeonSelected();

	int getCurrentLevel();

	void setCurrentLevel(int currentLevel);

	DungeonLevel getCurrentLevelData();

	void saveDungeonData();

	PogData[] getMonsterTemplatePogs();

	PogData getSelectedPog();

	void setSelectedPog(PogData selectedPog);

	void setPogBeingDragged(PogData pogBeingDragged);

	PogData getPogBeingDragged();

	PogData createPogInstance(PogData template);

	void setSessionLevelSize(int columns, int rows);

	void saveFow();

	boolean isFowSet(int columns, int rows);

	void setFow(int columns, int rows, boolean value);

	boolean getFowToggle();

	void setFowToggle(boolean fowToggle);

	void createNewDungeon(String dungeonUUID, String newDungeonName);

	String getUrlToDungeonResource(String resourceItem);

	boolean okToDeleteThisTemplate(String dungeonsName);

	void deleteTemplate(String dungeonUUID);

	PogData findMonsterPog(String pogUUID);

	SessionListData getSessionListData();

	void getSessionList(String dungeonName);

	boolean isNameValidForNewSession(String newSessionName);

	void createNewSession(String selectedTemplate, String newSessionName);

	void joinSession(String newSessionName);

	void deleteSession(String selectedTemplate, String newSessionName2);

	void updatePogDataOnLevel(PogData pog);

	void addPogDataToLevel(PogData clonePog);

	PogData[] getMonstersForCurrentLevel();

	PogData[] getPlayersForCurrentSession();

	String[] getDungeonLevelNames();

	void doTimedTasks();

	PogData findRoomObjectPog(String selectedValue);

	PogData[] getRoomObjectTemplatePogs();

	PogData[] getRoomObjectsForCurrentLevel();

	void downloadDungeonFile(String fileName);

	String getUrlToDungeonData();

	int getToken();

	boolean isLegalDungeonName(String nameToCheck);

	int getNextLevelNumber();

	void createNewLevel(DungeonLevel currentLevel);

	boolean isValidNewCharacterName(String characterName);

	PogData createPlayer();

	DungeonSessionData getSelectedSession();

	PogData findCharacterPog(String uuid);

	PogData createMonster();

	String[] getMonsterRaces();

	String[] getMonsterClasses();

	String[] getMonsterGenders();

	boolean isValidNewMonsterName(String monsterName);

	void setSelectedMonster(String monsterUUID);

	ArrayList<PogData> getFilteredMonsters(String raceFilter, String classFilter, String genderFilter);

}
