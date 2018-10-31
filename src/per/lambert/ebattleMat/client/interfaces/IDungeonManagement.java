package per.lambert.ebattleMat.client.interfaces;

import java.util.List;

import per.lambert.ebattleMat.client.services.serviceData.DungeonData;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

/**
 * @author LLambert Interface to user management services
 */
public interface IDungeonManagement {
	void login(final String username, final String password, IUserCallback callback);

	DungeonServerError getLastError();

	List<String> getDungeonList();

	void selectDungeon(String dungeonsName);

	DungeonData getSelectedDungeon();

	boolean dungeonSelected();

	int getCurrentLevel();

	void setCurrentLevel(int currentLevel);

	DungeonLevel getCurrentLevelData();

	String getDungeonNameForUrl();

	void dungeonDataChanged();
	void saveDungeonData();

	void setPogBeingDragged(PogData pogBeingDragged);

	PogData getPogBeingDragged();
}
