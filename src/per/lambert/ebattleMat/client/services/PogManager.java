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

/**
 * Pog manager.
 * 
 * @author LLambert
 *
 */
public abstract class PogManager implements IPogManager {
	/**
	 * Map of monster pogs.
	 */
	private Map<String, PogData> monsterTemplateMap = new HashMap<String, PogData>();
	/**
	 * Map of monster races.
	 */
	private Map<String, String> monsterRaces = new HashMap<String, String>();
	/**
	 * Map of monster classes.
	 */
	private Map<String, String> monsterClasses = new HashMap<String, String>();
	/**
	 * List of monster pogs.
	 */
	private PogList monsterTemplatePogs;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData[] getMonsterTemplatePogs() {
		return monsterTemplatePogs.getPogList();
	}

	/**
	 * Map of room object pogs.
	 */
	private Map<String, PogData> roomObjectTemplateMap = new HashMap<String, PogData>();
	/**
	 * Map of room object races.
	 */
	private Map<String, String> roomObjectRaces = new HashMap<String, String>();
	/**
	 * Map of room object classes.
	 */
	private Map<String, String> roomObjectClasses = new HashMap<String, String>();
	/**
	 * List of room objects.
	 */
	private PogList roomObjectTemplatePogs;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData[] getRoomObjectTemplatePogs() {
		return roomObjectTemplatePogs.getPogList();
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
	 * {@inheritDoc}
	 */
	@Override
	public void setSelectedMonster(final String monsterUUID) {
		this.selectedPog = findMonsterPog(monsterUUID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSelectedRoomObject(final String roomObjectUUID) {
		this.selectedPog = findRoomObjectPog(roomObjectUUID);
	}

	/**
	 * Pog bein draggede.
	 */
	private PogData pogBeingDragged;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPogBeingDragged(final PogData pogBeingDragged) {
		this.pogBeingDragged = pogBeingDragged;
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
		return (monsterTemplateMap.get(pogUUID));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData findRoomObjectPog(final String pogUUID) {
		return (roomObjectTemplateMap.get(pogUUID));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData createPlayer() {
		PogData pogData = createTemplatePog(ElectronicBattleMat.POG_TYPE_PLAYER);
		return (pogData);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PogData createMonster() {
		PogData pogData = createTemplatePog(ElectronicBattleMat.POG_TYPE_MONSTER);
		return (pogData);
	}

	/**
	 * Create template Pog based on this type.
	 * 
	 * @param type to create.
	 * @return Pog of type
	 */
	private PogData createTemplatePog(final String type) {
		PogData pogData = (PogData) JavaScriptObject.createObject().cast();
		pogData.setTemplateUUID(PogData.generateUUID());
		pogData.setUUID(pogData.getTemplateUUID());
		pogData.setPogType(type);
		return (pogData);
	}

	/**
	 * Load in monster Pogs from resource file.
	 */
	protected void loadMonsterPogs() {
		monsterTemplatePogs = null;
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("fileName", ElectronicBattleMat.DUNGEON_MONSTER_LOCATION + "monsterPogs.json");
		dataRequester.requestData("", "LOADJSONFILE", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
				loadMonsterPogTemplates(data);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
			}
		});
	}

	/**
	 * Load in monster Pog templates from data.
	 * 
	 * @param data with Pogs.
	 */
	protected void loadMonsterPogTemplates(final Object data) {
		monsterTemplateMap.clear();
		monsterClasses.clear();
		monsterRaces.clear();
		monsterTemplatePogs = JsonUtils.<PogList>safeEval((String) data);
		for (PogData monsterTemplate : monsterTemplatePogs.getPogList()) {
			addMonsterToCollections(monsterTemplate);
		}
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.MonsterPogsLoaded, null));
	}

	/**
	 * Add Monster pog to collections.
	 * 
	 * @param monsterTemplate to add.
	 */
	private void addMonsterToCollections(final PogData monsterTemplate) {
		if (!monsterTemplate.getPogClass().isEmpty()) {
			if (!monsterClasses.containsKey(monsterTemplate.getPogClass())) {
				monsterClasses.put(monsterTemplate.getPogClass(), monsterTemplate.getPogClass());
			}
		}
		if (!monsterTemplate.getRace().isEmpty()) {
			if (!monsterRaces.containsKey(monsterTemplate.getRace())) {
				monsterRaces.put(monsterTemplate.getRace(), monsterTemplate.getRace());
			}
		}
		monsterTemplateMap.put(monsterTemplate.getUUID(), monsterTemplate);
	}

	/**
	 * Load in room object Pogs from resource file.
	 */
	protected void loadRoomObjectPogs() {
		roomObjectTemplatePogs = null;
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("fileName", ElectronicBattleMat.DUNGEON_ROOMOBJECT_LOCATION + "roomPogs.json");
		dataRequester.requestData("", "LOADJSONFILE", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
				loadRoomPogTemplates(data);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
			}
		});
	}

	/**
	 * Load in room object Pog templates from data.
	 * 
	 * @param data with Pogs.
	 */
	protected void loadRoomPogTemplates(final Object data) {
		roomObjectTemplateMap.clear();
		roomObjectClasses.clear();
		roomObjectRaces.clear();
		roomObjectTemplatePogs = JsonUtils.<PogList>safeEval((String) data);
		for (PogData roomObjectTemplate : roomObjectTemplatePogs.getPogList()) {
			addRoomObjectToCollections(roomObjectTemplate);
		}
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.RoomObjectPogsLoaded, null));
	}

	/**
	 * Add Room Object pog to collections.
	 * 
	 * @param roomObjectTemplate to add.
	 */
	private void addRoomObjectToCollections(final PogData roomObjectTemplate) {
		if (!roomObjectTemplate.getPogClass().isEmpty()) {
			if (!roomObjectClasses.containsKey(roomObjectTemplate.getPogClass())) {
				roomObjectClasses.put(roomObjectTemplate.getPogClass(), roomObjectTemplate.getPogClass());
			}
		}
		if (!roomObjectTemplate.getRace().isEmpty()) {
			if (!roomObjectRaces.containsKey(roomObjectTemplate.getRace())) {
				roomObjectRaces.put(roomObjectTemplate.getRace(), roomObjectTemplate.getRace());
			}
		}
		roomObjectTemplateMap.put(roomObjectTemplate.getUUID(), roomObjectTemplate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<PogData> getFilteredMonsters(final String raceFilter, final String classFilter, final String genderFilter) {
		boolean needGenderFilter = false;
		PlayerFlag genderFlag = PlayerFlag.NONE;
		if (genderFilter != null && !genderFilter.isEmpty()) {
			needGenderFilter = true;
			if (genderFilter.equalsIgnoreCase("Neutral")) {
				genderFlag = PlayerFlag.HAS_NO_GENDER;
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
					boolean neutral = monster.isFlagSet(PlayerFlag.HAS_NO_GENDER);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addOrUpdatePogResource(final PogData pog) {
		if (pog.isThisAMonster()) {
			addOrUpdateMonster(pog);
			return;
		}
		addOrUpdateRoomObject(pog);
	}

	/**
	 * Add or update Pog.
	 * 
	 * @param pog to add
	 */
	private void addOrUpdateMonster(final PogData pog) {
		if (findMonsterPog(pog.getUUID()) == null) {
			monsterTemplatePogs.addPog(pog);
			addMonsterToCollections(pog);
		}
		saveMonsterResources();
	}

	/**
	 * Save Monster resources.
	 */
	private void saveMonsterResources() {
		String jsonData = JsonUtils.stringify(monsterTemplatePogs);
		saveResources("monsters", jsonData);
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.MonsterPogsLoaded, null));
	}

	/**
	 * Add or update room object.
	 * 
	 * @param pog to update.
	 */
	private void addOrUpdateRoomObject(final PogData pog) {
		if (findRoomObjectPog(pog.getUUID()) == null) {
			roomObjectTemplatePogs.addPog(pog);
			addRoomObjectToCollections(pog);
		}
		saveRoomObjectResources();
	}

	/**
	 * Save Room Object resources.
	 */
	private void saveRoomObjectResources() {
		String jsonData = JsonUtils.stringify(roomObjectTemplatePogs);
		saveResources("roomObjects", jsonData);
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.RoomObjectPogsLoaded, null));
	}

	/**
	 * Save resources.
	 * 
	 * @param resourceName to save
	 * @param jsonData JSON Data
	 */
	private void saveResources(final String resourceName, final String jsonData) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("resourceName", resourceName);
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		dataRequester.requestData(jsonData, "SAVEJSONRESOURCE", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getMonsterRaces() {
		String[] rtn = new String[0];
		return (monsterRaces.values().toArray(rtn));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getMonsterClasses() {
		String[] rtn = new String[0];
		return (monsterClasses.values().toArray(rtn));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getMonsterGenders() {
		return (new String[] {"Male", "Female", "Neutral" });
	}
}
