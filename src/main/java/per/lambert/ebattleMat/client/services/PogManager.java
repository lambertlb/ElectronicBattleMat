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
package per.lambert.ebattleMat.client.services;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.core.client.JavaScriptObject;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.Constants;
import per.lambert.ebattleMat.client.interfaces.DungeonMasterFlag;
import per.lambert.ebattleMat.client.interfaces.Gender;
import per.lambert.ebattleMat.client.interfaces.IPogManager;
import per.lambert.ebattleMat.client.interfaces.PlayerFlag;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

/**
 * Pog manager.
 * 
 * @author LLambert
 *
 */
public abstract class PogManager implements IPogManager {
	/**
	 * Collection of monster templates.
	 */
	private PogCollection monsterCollection = new PogCollection(ReasonForAction.MonsterPogsLoaded);

	/**
	 * Collection of room templates.
	 */
	private PogCollection roomCollection = new PogCollection(ReasonForAction.RoomObjectPogsLoaded);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData[] getMonsterTemplatePogs() {
		return monsterCollection.getPogList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData[] getRoomObjectTemplatePogs() {
		return roomCollection.getPogList();
	}

	/**
	 * Currently selected Pog.
	 */
	private PogData selectedPog;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData getSelectedPog() {
		return selectedPog;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSelectedPog(final PogData selectedPog) {
		this.selectedPog = selectedPog;
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.PogWasSelected, null));
	}

	/**
	 * So sub-classes can set.
	 * 
	 * @param selectedPog selected pog.
	 */
	protected void setSelectedPogInternal(final PogData selectedPog) {
		this.selectedPog = selectedPog;
	}

	/**
	 * Set selected pog.
	 * 
	 * @param pogType type of pog
	 * @param templateUUID uuid of pog
	 */
	protected void setCommonTemplate(final String pogType, final String templateUUID) {
		if (pogType.equals(Constants.POG_TYPE_MONSTER)) {
			setSelectedMonster(templateUUID);
		} else {
			setSelectedRoomObject(templateUUID);
		}
	}

	/**
	 * Lookup monster with this UUID and set as selected pog.
	 * 
	 * @param monsterUUID to look up
	 */
	private void setSelectedMonster(final String monsterUUID) {
		setSelectedPog(findMonsterPog(monsterUUID));
	}

	/**
	 * Lookup room object with this UUID and set as selected pog.
	 * 
	 * @param roomObjectUUID to look up
	 */
	private void setSelectedRoomObject(final String roomObjectUUID) {
		setSelectedPog(findRoomObjectPog(roomObjectUUID));
	}

	/**
	 * Pog being dragged.
	 */
	private PogData pogBeingDragged;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPogBeingDragged(final PogData pogBeingDragged, final boolean fromRibbonBar) {
		this.pogBeingDragged = pogBeingDragged;
		this.fromRibbonBar = fromRibbonBar;
	}

	/**
	 * Pog was dragged from ribbon bar.
	 */
	private boolean fromRibbonBar;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFromRibbonBar() {
		return fromRibbonBar;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData getPogBeingDragged() {
		return (pogBeingDragged);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData findMonsterPog(final String pogUUID) {
		return (monsterCollection.findPog(pogUUID));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData findRoomObjectPog(final String pogUUID) {
		return (roomCollection.findPog(pogUUID));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData createPlayer() {
		return (createTemplatePog(Constants.POG_TYPE_PLAYER));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData createTemplatePog(final String type) {
		PogData pogData = (PogData) JavaScriptObject.createObject().cast();
		pogData.setUUID(Constants.generateUUID());
		pogData.setType(type);
		return (pogData);
	}

	/**
	 * Load in monster Pogs from resource file.
	 */
	public void loadMonsterPogs() {
		monsterCollection.loadFromServer(Constants.DUNGEON_MONSTER_LOCATION);
	}

	/**
	 * Load in room object Pogs from resource file.
	 */
	public void loadRoomObjectPogs() {
		roomCollection.loadFromServer(Constants.DUNGEON_ROOMOBJECT_LOCATION);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<PogData> getFilteredCommonTemplates(final String pogType, final String raceFilter, final String classFilter, final String genderFilter) {
		if (pogType.equals(Constants.POG_TYPE_MONSTER)) {
			return (monsterCollection.getFilteredPogs(raceFilter, classFilter, genderFilter));
		}
		return (roomCollection.getFilteredPogs(raceFilter, classFilter, genderFilter));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<PogData> getSortedCommonTemplates(final String pogType) {
		if (pogType.equals(Constants.POG_TYPE_MONSTER)) {
			return (monsterCollection.getSortedPogs());
		}
		return (roomCollection.getSortedPogs());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCommonClasses(final String pogType) {
		if (pogType.equals(Constants.POG_TYPE_MONSTER)) {
			return (monsterCollection.getClassses());
		}
		return (roomCollection.getClassses());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getCommonRaces(final String pogType) {
		if (pogType.equals(Constants.POG_TYPE_MONSTER)) {
			return (monsterCollection.getRaces());
		}
		return (roomCollection.getClassses());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Gender> getTemplateGenders() {
		return (Gender.getValues());
	}

	/**
	 * Get pog sizes.
	 * 
	 * @return pog sizes.
	 */
	@Override
	public String[] getPogSizes() {
		return (new String[] {"Normal", "Large", "Huge", "Gargantuan" });
	}

	/**
	 * Add pog to common resource area.
	 * 
	 * @param pog to add
	 */
	protected void addOrUpdatePogToCommonResource(final PogData pog) {
		if (pog.isThisAMonster()) {
			monsterCollection.addOrUpdatePogToCommonResource(pog);
		} else {
			roomCollection.addOrUpdatePogToCommonResource(pog);
		}
	}

	/**
	 * Is this Pog a template.
	 * 
	 * @param pogData pog to check
	 * @return true if template
	 */
	@Override
	public boolean isTemplate(final PogData pogData) {
		if (pogData.isThisAMonster()) {
			return (monsterCollection.isTemplate(pogData));
		} else if (pogData.isThisARoomObject()) {
			return (roomCollection.isTemplate(pogData));
		}
		return (false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateNumberOfSelectedPog(final int newPogNumber) {
		if (selectedPog != null) {
			selectedPog.setPogNumber(newPogNumber);
			ServiceManager.getDungeonManager().addOrUpdatePog(selectedPog);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void toggleFlagOfSelectedPog(final PlayerFlag flag) {
		if (selectedPog != null) {
			if (selectedPog.isFlagSet(flag)) {
				selectedPog.clearFlags(flag);
			} else {
				selectedPog.setFlags(flag);
			}
			ServiceManager.getDungeonManager().addOrUpdatePog(selectedPog);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void toggleFlagOfSelectedPog(final DungeonMasterFlag flag) {
		if (selectedPog != null) {
			if (selectedPog.isFlagSet(flag)) {
				selectedPog.clearFlags(flag);
			} else {
				selectedPog.setFlags(flag);
			}
			ServiceManager.getDungeonManager().addOrUpdatePog(selectedPog);
		}
	}
	
	/**
	 * remove this pog.
	 * @param pog
	 */
	protected void removeMonster(final PogData pog) {
		monsterCollection.remove(pog);
	}

	/**
	 * remove this pog.
	 * @param pog
	 */
	protected void removeRoomObject(final PogData pog) {
		roomCollection.remove(pog);
	}
}
