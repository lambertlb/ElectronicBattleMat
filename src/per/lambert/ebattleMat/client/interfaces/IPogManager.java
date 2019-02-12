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
	 * Add Pog to proper resource list.
	 * 
	 * @param pog to add
	 */
	void addOrUpdatePogResource(PogData pog);

	/**
	 * Get list of monster genders.
	 * 
	 * @return list of monster genders.
	 */
	String[] getTemplateGenders();

	/**
	 * Get pog sizes.
	 * 
	 * @return pog sizes.
	 */
	String[] getPogSizes();

	/**
	 * Create template Pog based on this type.
	 * 
	 * @param type to create.
	 * @return Pog of type
	 */
	PogData createTemplatePog(String type);

	/**
	 * Get a list of filtered common resources.
	 * 
	 * @param pogType type of resource
	 * @param raceFilter race filter
	 * @param classFilter class filter
	 * @param genderFilter gender filter
	 * @return list of filtered pogs
	 */
	ArrayList<PogData> getFilteredCommonTemplates(String pogType, String raceFilter, String classFilter, String genderFilter);

	/**
	 * Set selected pog.
	 * 
	 * @param pogType type of pog
	 * @param templateUUID uuid of pog
	 */
	void setCommonTemplate(String pogType, String templateUUID);

	/**
	 * get common template classes.
	 * 
	 * @param pogType to get
	 * @return list of classes
	 */
	String[] getCommonClasses(String pogType);

	/**
	 * get common template races.
	 * 
	 * @param pogType to get
	 * @return list of races
	 */
	String[] getCommonRaces(String pogType);

	/**
	 * Is this Pog a template.
	 * @param pogData pog to check
	 * @return true if template
	 */
	boolean isTemplate(PogData pogData);
}
