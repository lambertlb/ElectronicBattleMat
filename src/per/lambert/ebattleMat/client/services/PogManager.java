package per.lambert.ebattleMat.client.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.ElectronicBattleMat;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IPogManager;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.interfaces.PlayerFlag;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.serviceData.PogData;
import per.lambert.ebattleMat.client.services.serviceData.PogList;

public class PogManager implements IPogManager {
	Map<String, PogData> monsterTemplateMap = new HashMap<String, PogData>();
	private PogList monsterTemplatePogs;

	@Override
	public PogData[] getMonsterTemplatePogs() {
		return monsterTemplatePogs.getPogList();
	}

	Map<String, PogData> roomObjectTemplateMap = new HashMap<String, PogData>();
	private PogList roomObjectTemplatePogs;

	@Override
	public PogData[] getRoomObjectTemplatePogs() {
		return roomObjectTemplatePogs.getPogList();
	}

	private PogData selectedPog;

	@Override
	public PogData getSelectedPog() {
		return selectedPog;
	}

	@Override
	public void setSelectedPog(PogData selectedPog) {
		this.selectedPog = selectedPog;
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.PogWasSelected, null));
	}

	@Override
	public void setSelectedMonster(String monsterUUID) {
		this.selectedPog = findMonsterPog(monsterUUID);
		;
	}

	private PogData pogBeingDragged;

	@Override
	public void setPogBeingDragged(PogData pogBeingDragged) {
		this.pogBeingDragged = pogBeingDragged;
	}

	@Override
	public PogData getPogBeingDragged() {
		return (pogBeingDragged);
	}

	@Override
	public PogData findMonsterPog(String pogUUID) {
		return (monsterTemplateMap.get(pogUUID));
	}

	@Override
	public PogData findRoomObjectPog(String pogUUID) {
		return (roomObjectTemplateMap.get(pogUUID));
	}

	@Override
	public PogData createPogInstance(PogData template) {
		PogData pog = template.clone();
		return (pog);
	}

	@Override
	public PogData createPlayer() {
		PogData pogData = createTemplatePog(ElectronicBattleMat.POG_TYPE_PLAYER);
		return (pogData);
	}

	@Override
	public PogData createMonster() {
		PogData pogData = createTemplatePog(ElectronicBattleMat.POG_TYPE_MONSTER);
		return (pogData);
	}

	private PogData createTemplatePog(String type) {
		PogData pogData = (PogData) JavaScriptObject.createObject().cast();
		pogData.setTemplateUUID(PogData.generateUUID());
		pogData.setUUID(pogData.getTemplateUUID());
		pogData.setPogType(type);
		return (pogData);
	}

	protected void loadMonsterPogs() {
		monsterTemplatePogs = null;
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("fileName", ElectronicBattleMat.DUNGEON_MONSTER_LOCATION + "monsterPogs.json");
		dataRequester.requestData("", "LOADJSONFILE", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				loadMonsterPogTemplates(data);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
			}
		});
	}

	protected void loadMonsterPogTemplates(Object data) {
		monsterTemplateMap.clear();
		monsterTemplatePogs = JsonUtils.<PogList>safeEval((String) data);
		for (PogData monsterTemplate : monsterTemplatePogs.getPogList()) {
			monsterTemplateMap.put(monsterTemplate.getUUID(), monsterTemplate);
		}
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.MonsterPogsLoaded, null));
	}

	protected void loadRoomObjectPogs() {
		roomObjectTemplatePogs = null;
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("fileName", ElectronicBattleMat.DUNGEON_ROOMOBJECT_LOCATION + "roomPogs.json");
		dataRequester.requestData("", "LOADJSONFILE", parameters, new IUserCallback() {

			@Override
			public void onSuccess(Object sender, Object data) {
				loadRoomPogTemplates(data);
			}

			@Override
			public void onError(Object sender, IErrorInformation error) {
			}
		});
	}

	protected void loadRoomPogTemplates(Object data) {
		roomObjectTemplateMap.clear();
		roomObjectTemplatePogs = JsonUtils.<PogList>safeEval((String) data);
		for (PogData roomObjectTemplate : roomObjectTemplatePogs.getPogList()) {
			roomObjectTemplateMap.put(roomObjectTemplate.getUUID(), roomObjectTemplate);
		}
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.RoomObjectPogsLoaded, null));
	}
	@Override
	public ArrayList<PogData> getFilteredMonsters(String raceFilter, String classFilter, String genderFilter) {
		boolean needGenderFilter = false;
		PlayerFlag genderFlag = PlayerFlag.NONE;
		if (genderFilter != null && !genderFilter.isEmpty()) {
			needGenderFilter = true;
			if (genderFilter.equalsIgnoreCase("Neutral")) {
				genderFlag = PlayerFlag.HAS_NO_SEX;
			} else if (genderFilter.equalsIgnoreCase("Female")) {
				genderFlag = PlayerFlag.IS_FEMALE;
			}
		}
		ArrayList<PogData> filteredMonsters = new ArrayList<PogData>();
		for (PogData monster : getMonsterTemplatePogs()) {
			if (raceFilter != null && !raceFilter.isEmpty()) {
				if (!monster.getRace().equalsIgnoreCase(raceFilter)) {
					continue;
				}
			}
			if (classFilter != null && !classFilter.isEmpty()) {
				if (!monster.getPogClass().equalsIgnoreCase(classFilter)) {
					continue;
				}
			}
			if (needGenderFilter) {
				if (genderFlag == PlayerFlag.NONE) {
					boolean isFemale = monster.isFlagSet(PlayerFlag.IS_FEMALE);
					boolean neutral = monster.isFlagSet(PlayerFlag.HAS_NO_SEX);
					if (isFemale || neutral) {
						continue;
					}
				} else if (!monster.isFlagSet(genderFlag)) {
					continue;
				}
			}
			filteredMonsters.add(monster);
		}
		return (filteredMonsters);
	}
	@Override
	public void addOrUpdatePogResource(PogData pog) {
		if (pog.isThisAMonster()) {
			addOrUpdateMonster(pog);
			return;
		}
		addOrUpdateRoomObject(pog);
	}

	private void addOrUpdateMonster(PogData pog) {
		if (findMonsterPog(pog.getUUID()) == null) {
			monsterTemplatePogs.addPog(pog);
		}
		saveMonsterResources();
	}

	private void saveMonsterResources() {
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.MonsterPogsLoaded, null));
	}

	private void addOrUpdateRoomObject(PogData pog) {
		if (findRoomObjectPog(pog.getUUID()) == null) {
			roomObjectTemplatePogs.addPog(pog);
		}
		saveRoomObjectResources();
	}

	private void saveRoomObjectResources() {
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.RoomObjectPogsLoaded, null));
	}
}
