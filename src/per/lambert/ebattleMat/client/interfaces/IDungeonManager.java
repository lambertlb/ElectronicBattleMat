package per.lambert.ebattleMat.client.interfaces;

import java.util.Map;

import per.lambert.ebattleMat.client.services.serviceData.DungeonData;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
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

	PogData[] getPcTemplatePogs();

	PogData[] getMonsterTemplatePogs();

	PogData getSelectedPog();

	void setSelectedPog(PogData selectedPog);

	void setPogBeingDragged(PogData pogBeingDragged);

	PogData getPogBeingDragged();

	PogData createPlayerInstance(PogData template);

	void setFowSize(int columns, int rows);

	boolean isFowSet(int columns, int rows);

	void setFow(int columns, int rows, boolean value);

	boolean getFowToggle();

	void setFowToggle(boolean fowToggle);

	void createNewDungeon(String dungeonUUID, String newDungeonName);

	String getUrlToDungeonResource(String resourceItem);

	boolean okToDeleteThisTemplate(String dungeonsName);

	void deleteTemplate(String dungeonUUID);

	PogData fullCLoneMonster(PogData pogData);

	PogData findCharacterPog(String pogUUID);

	SessionListData getSessionListData();

	void getSessionList(String dungeonName);

	boolean isNameValidForNewSession(String newSessionName);

	void createNewSession(String selectedTemplate, String newSessionName);

	void joinSession(String newSessionName);

	void deleteSession(String selectedTemplate, String newSessionName2);
}
