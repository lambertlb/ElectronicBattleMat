package per.lambert.ebattleMat.client.interfaces;

import per.lambert.ebattleMat.client.services.serviceData.DungeonData;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

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

	String[] getDungeonNames();

	void selectDungeon(String dungeonsName);

	DungeonData getSelectedDungeon();

	boolean dungeonSelected();

	int getCurrentLevel();

	void setCurrentLevel(int currentLevel);

	DungeonLevel getCurrentLevelData();

	void dungeonDataChanged();

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

	void createNewDungeon(String templateName, String newDungeonName);

	String getUrlToDungeonResource(String resourceItem);

	boolean okToDeleteThisTemplate(String dungeonsName);

	void deleteTemplate(String selectedTemplate);

	PogData createMonsterFromTemplate(PogData pogData);

	PogData findMonsterTemplate(PogData pogData);

	PogData findCharacterPog(String pogUUID);

	String[] getSessionNames();

	void getSessionList(String dungeonName);
}
