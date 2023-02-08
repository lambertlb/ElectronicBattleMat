/*
 * Copyright (C) 2019 Leon Lambert.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package per.lambert.ebattleMat.client.interfaces;

import java.util.ArrayList;
import java.util.Collection;

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
	 * @param fromRibbonBar was dragged from ribbon bar
	 */
	void setPogBeingDragged(PogData pogBeingDragged, boolean fromRibbonBar);

	/**
	 * get pog being dragged.
	 * 
	 * @return pog being dragged.
	 */
	PogData getPogBeingDragged();

	/**
	 * Pog being dragged is from ribbon bar.
	 * 
	 * @return true if from ribbon bar
	 */
	boolean isFromRibbonBar();

	/**
	 * Create player pog.
	 * 
	 * @return player pog
	 */
	PogData createPlayer();

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
	 * Get list of monster genders.
	 * 
	 * @return list of monster genders.
	 */
	Collection<Gender> getTemplateGenders();

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
	 * 
	 * @param pogData pog to check
	 * @return true if template
	 */
	boolean isTemplate(PogData pogData);
	/**
	 * Update number of selected pog.
	 * @param newPogNumber to update
	 */
	void	updateNumberOfSelectedPog(int newPogNumber);
	/**
	 * Toggle player Flag of selected pog.
	 * @param flag to toggle
	 */
	void	toggleFlagOfSelectedPog(PlayerFlag flag);
	/**
	 * Toggle DM Flag of selected pog.
	 * @param flag to toggle
	 */
	void	toggleFlagOfSelectedPog(DungeonMasterFlag flag);

}
