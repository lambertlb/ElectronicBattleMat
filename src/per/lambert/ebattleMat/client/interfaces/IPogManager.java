package per.lambert.ebattleMat.client.interfaces;

import java.util.ArrayList;

import per.lambert.ebattleMat.client.services.serviceData.PogData;

public interface IPogManager {
	PogData[] getMonsterTemplatePogs();

	PogData getSelectedPog();

	void setSelectedPog(PogData selectedPog);

	void setPogBeingDragged(PogData pogBeingDragged);

	PogData getPogBeingDragged();

	PogData createPogInstance(PogData template);

	PogData createPlayer();

	PogData createMonster();

	void setSelectedMonster(String monsterUUID);

	PogData[] getRoomObjectTemplatePogs();

	PogData findMonsterPog(String pogUUID);

	PogData findRoomObjectPog(String selectedValue);

	ArrayList<PogData> getFilteredMonsters(String raceFilter, String classFilter, String genderFilter);

	void addOrUpdatePogResource(PogData pog);

}
