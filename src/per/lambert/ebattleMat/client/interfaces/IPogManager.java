package per.lambert.ebattleMat.client.interfaces;

import java.util.ArrayList;

import per.lambert.ebattleMat.client.services.serviceData.PogData;

/**
 * Pog Manager.
 * 
 * @author LLambert
 *
 */
public interface IPogManager {
	/**
	 * Get list of monster template pogs.
	 * 
	 * @return list of monster template pogs.
	 */
	PogData[] getMonsterTemplatePogs();

	/**
	 * Get list of room object template pogs.
	 * 
	 * @return list of room object template pogs.
	 */
	PogData[] getRoomObjectTemplatePogs();

	/**
	 * Get selected Pog.
	 * 
	 * @return selected Pog
	 */
	PogData getSelectedPog();

	/**
	 * Set selected Pog.
	 * 
	 * @param selectedPog to set
	 */
	void setSelectedPog(PogData selectedPog);

	/**
	 * Lookup monster with this UUID and set as selected pog.
	 * 
	 * @param monsterUUID to look up
	 */
	void setSelectedMonster(String monsterUUID);

	/**
	 * Lookup room object with this UUID and set as selected pog.
	 * 
	 * @param roomObjectUUID to look up
	 */
	void setSelectedRoomObject(String roomObjectUUID);

	/**
	 * Set pog being dragged.
	 * 
	 * @param pogBeingDragged pog being dragged.
	 */
	void setPogBeingDragged(PogData pogBeingDragged);

	/**
	 * get pog being dragged.
	 * 
	 * @return pog being dragged.
	 */
	PogData getPogBeingDragged();

	/**
	 * Create player pog.
	 * 
	 * @return player pog
	 */
	PogData createPlayer();

	/**
	 * Create monster Pog.
	 * 
	 * @return monster Pog.
	 */
	PogData createMonster();

	/**
	 * Find monster pog with this UUID.
	 * 
	 * @param pogUUID to find
	 * @return monster pog with this UUID.
	 */
	PogData findMonsterPog(String pogUUID);

	/**
	 * Find room object pog with this UUID.
	 * 
	 * @param pogUUID to find
	 * @return room object pog with this UUID.
	 */
	PogData findRoomObjectPog(String pogUUID);

	/**
	 * Get list of filtered monsters.
	 * 
	 * @param raceFilter race filter
	 * @param classFilter class filter
	 * @param genderFilter gender filter
	 * @return list of monsters that meet the filters.
	 */
	ArrayList<PogData> getFilteredMonsters(String raceFilter, String classFilter, String genderFilter);

	/**
	 * Add Pog to proper resource list.
	 * 
	 * @param pog to add
	 */
	void addOrUpdatePogResource(PogData pog);

	/**
	 * Get list of monster races.
	 * 
	 * @return list of monster races.
	 */
	String[] getMonsterRaces();

	/**
	 * Get list of monster classes.
	 * 
	 * @return list of monster classes.
	 */
	String[] getMonsterClasses();

	/**
	 * Get list of monster genders.
	 * 
	 * @return list of monster genders.
	 */
	String[] getMonsterGenders();

}
