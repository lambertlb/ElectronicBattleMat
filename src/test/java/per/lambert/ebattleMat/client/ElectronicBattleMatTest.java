package per.lambert.ebattleMat.client;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.junit.client.GWTTestCase;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.DungeonServerError;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.mocks.DataRequesterTestCallback;
import per.lambert.ebattleMat.client.mocks.EventManagerTestCallback;
import per.lambert.ebattleMat.client.mocks.MockDataRequester;
import per.lambert.ebattleMat.client.mocks.MockEventManager;
import per.lambert.ebattleMat.client.mocks.MockResponseData;
import per.lambert.ebattleMat.client.services.DungeonManager;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.DungeonData;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class ElectronicBattleMatTest extends GWTTestCase {
	/**
	 * Used for logging from test.
	 */
	private Logger logger = Logger.getLogger("NameOfYourLogger");

	/**
	 * Data requester used for test.
	 */
	private MockDataRequester dataRequesterForTest = new MockDataRequester();
	/**
	 * Event manager for unit tests.
	 */
	private MockEventManager eventManagerForTest = new MockEventManager();

	/**
	 * keep track of event states.
	 */
	private boolean[] eventStates;

	/**
	 * Must refer to a valid module that sources this class.
	 * 
	 * @return module name
	 */
	public String getModuleName() {
		return "per.lambert.ebattleMat.ElectronicBattleMatJUnit";
	}

	/**
	 * Set proper bit based on reason.
	 * 
	 * @param event with reason
	 */
	private void setEventResults(final GwtEvent<?> event) {
		if (event instanceof ReasonForActionEvent) {
			eventStates[((ReasonForActionEvent) event).getReasonForAction().ordinal()] = true;
		}
	}

	/**
	 * Make sure we had this event.
	 * 
	 * @param reason for event
	 */
	private void hadEvent(final ReasonForAction reason) {
		assertTrue(eventStates[reason.ordinal()]);
	}

	/**
	 * Run before test. {@inheritDoc}
	 */
	public void gwtSetUp() {
		ServiceManager.setDataRequesterForUnitTest(dataRequesterForTest);
		dataRequesterForTest.setTestCallback(null);
		ServiceManager.setEventManagerForUnitTest(eventManagerForTest);
		eventManagerForTest.setEventManagerTestCallback(null);
		eventStates = new boolean[ReasonForAction.LastReason.ordinal()];
	}

	/**
	 * See if we can access resource files for test.
	 */
	public void testLogin() {
		DungeonManager dungeonManager = new DungeonManager();
		eventManagerForTest.setEventManagerTestCallback(new EventManagerTestCallback() {

			@Override
			public void onEvent(final GwtEvent<?> event) {
				setEventResults(event);
			}
		});
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {

			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				assertTrue(requestType == "LOGIN");
				assertTrue(parameters.containsKey("username"));
				assertTrue(parameters.containsKey("password"));
				assertTrue(parameters.get("username") == "username");
				assertTrue(parameters.get("password") == "password");
				dataRequesterForTest.setTestCallback(null);
				callback.onSuccess(null, MockResponseData.LOGIN_RESPONSE);
				assertTrue(dungeonManager.getToken() == 355);
				assertTrue(dungeonManager.getLastError() == DungeonServerError.Succsess);
			}
		});
		dungeonManager.login("username", "password", new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
			}
		});
	}

	/**
	 * Test dungeon manager getting dungeon list.
	 */
	public void testGettingDungeonList() {
		DungeonManager dungeonManager = new DungeonManager();
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {

			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				assertTrue(requestType == "GETDUNGEONLIST");
				assertTrue(parameters.size() == 0);
				dataRequesterForTest.setTestCallback(null);
				callback.onSuccess(null, MockResponseData.GETDUNGEONLISTRESPONSE);
				String uuidOfMasterTemplate = dungeonManager.getUuidOfMasterTemplate();
				assertTrue(uuidOfMasterTemplate != null && uuidOfMasterTemplate == "template-dungeon");

				Map<String, String> dungeonToUUIDMap = dungeonManager.getDungeonToUUIDMap();
				assertTrue(dungeonToUUIDMap != null);
				assertTrue(dungeonToUUIDMap.size() == 3);

				String entry = dungeonToUUIDMap.get("Template Dungeon");
				assertTrue(entry != null && entry == "template-dungeon");
				String path = dungeonManager.getUuidTemplatePathMapForUnitTest().get("template-dungeon");
				assertTrue(path != null && path == "/dungeonData/dungeons/TemplateDungeon");

				entry = dungeonToUUIDMap.get("Dungeon 1");
				assertTrue(entry != null && entry == "dungeon1-template");
				path = dungeonManager.getUuidTemplatePathMapForUnitTest().get("dungeon1-template");
				assertTrue(path != null && path == "/dungeonData/dungeons/dungeon1");

				entry = dungeonToUUIDMap.get("Dungeon 2");
				assertTrue(entry != null && entry == "3c46b116-dd50-4b33-bfd8-d1a400f35292");
				path = dungeonManager.getUuidTemplatePathMapForUnitTest().get("3c46b116-dd50-4b33-bfd8-d1a400f35292");
				assertTrue(path != null && path == "/dungeonData/dungeons/dungeon2");
			}
		});
		dungeonManager.getDungeonList("", new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
				assertTrue(true);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
				assertTrue(false);
			}
		});
	}

	/**
	 * Test loading monster pogs.
	 */
	public void testLoadingMonsterPogs() {
		DungeonManager dungeonManager = new DungeonManager();
		eventManagerForTest.setEventManagerTestCallback(new EventManagerTestCallback() {

			@Override
			public void onEvent(final GwtEvent<?> event) {
				setEventResults(event);
			}
		});
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {

			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				assertTrue(requestType == "LOADJSONFILE");
				assertTrue(parameters.get("fileName") == "resources/monsters/pogs.json");
				dataRequesterForTest.setTestCallback(null);
				callback.onSuccess(null, MockResponseData.LOADMONSTERPOGTEMPLATES);
			}
		});
		dungeonManager.loadMonsterPogs();
		hadEvent(ReasonForAction.MonsterPogsLoaded);
		PogData pogData = dungeonManager.findMonsterPog("Male-Kobold");
		assertTrue(pogData != null);
		String[] races = dungeonManager.getCommonRaces(ElectronicBattleMat.POG_TYPE_MONSTER);
		assertTrue(races != null && races.length == 2);
		String[] classes = dungeonManager.getCommonClasses(ElectronicBattleMat.POG_TYPE_MONSTER);
		assertTrue(classes != null && classes.length == 2);
		PogData[] monsterPogs = dungeonManager.getMonsterTemplatePogs();
		assertTrue(monsterPogs != null && monsterPogs.length == 4);
	}

	/**
	 * Test loading room objects pogs.
	 */
	public void testLoadingRoomObjectsPogs() {
		DungeonManager dungeonManager = new DungeonManager();
		eventManagerForTest.setEventManagerTestCallback(new EventManagerTestCallback() {

			@Override
			public void onEvent(final GwtEvent<?> event) {
				setEventResults(event);
			}
		});
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {

			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				assertTrue(requestType == "LOADJSONFILE");
				assertTrue(parameters.get("fileName") == "resources/roomObjects/pogs.json");
				dataRequesterForTest.setTestCallback(null);
				callback.onSuccess(null, MockResponseData.LOADROOMOBJECTPOGTEMPLATES);
			}
		});
		dungeonManager.loadRoomObjectPogs();
		hadEvent(ReasonForAction.RoomObjectPogsLoaded);
		PogData pogData = dungeonManager.findRoomObjectPog("Secret_Door_Top");
		assertTrue(pogData != null);
		String[] classes = dungeonManager.getCommonClasses(ElectronicBattleMat.POG_TYPE_ROOMOBJECT);
		assertTrue(classes != null && classes.length == 3);
		PogData[] roomObjectPogs = dungeonManager.getRoomObjectTemplatePogs();
		assertTrue(roomObjectPogs != null && roomObjectPogs.length == 4);
	}

	/**
	 * Test loading to selected dungeon for editing.
	 */
	public void testEditSelectedDungeon() {
		DungeonManager dungeonManager = new DungeonManager();
		populateDungeonList(dungeonManager);
		eventManagerForTest.setEventManagerTestCallback(new EventManagerTestCallback() {

			@Override
			public void onEvent(final GwtEvent<?> event) {
				setEventResults(event);
			}
		});
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {

			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				dataRequesterForTest.handleMockDataRequest(requestType, parameters, callback);
			}
		});
		dungeonManager.editSelectedDungeonUUID("dungeon1-template");
		hadEvent(ReasonForAction.MonsterPogsLoaded);
		hadEvent(ReasonForAction.RoomObjectPogsLoaded);
		hadEvent(ReasonForAction.DMStateChange);
		hadEvent(ReasonForAction.DungeonSelected);
		hadEvent(ReasonForAction.DungeonDataLoaded);
		hadEvent(ReasonForAction.DungeonDataReadyToEdit);
		assertTrue(dungeonManager.isThereASelectedDungeon());
		DungeonData dungeonData = dungeonManager.getSelectedDungeon();
		assertTrue(dungeonData != null);
		assertTrue(dungeonManager.getCurrentLevel() == 0);
		assertTrue(dungeonManager.getNextLevelNumber() == 4);
		assertTrue(dungeonManager.isDungeonMaster());
		assertTrue(dungeonManager.isEditMode());
		checkDungeon1TemplateData(dungeonData);
	}

	/**
	 * Do some spot checks on dungeon 1 data.
	 * @param dungeonData to check
	 */
	private void checkDungeon1TemplateData(final DungeonData dungeonData) {
		assertTrue(dungeonData.getUUID() == "dungeon1-template");
		assertTrue(dungeonData.getDungeonName() == "Dungeon1");
		assertTrue(dungeonData.getShowGrid());
		assertTrue(dungeonData.getDungeonlevels().length == 3);
		spotCheckDungeon1Level1(dungeonData.getDungeonlevels()[0]);
	}

	/**
	 * Spot check dungeon 1 level 1.
	 * @param dungeonLevel to check
	 */
	private void spotCheckDungeon1Level1(final DungeonLevel dungeonLevel) {
		assertTrue(dungeonLevel.getLevelDrawing() == "level1.jpg");
		assertTrue(dungeonLevel.getLevelName() == "Entrance");
		assertTrue(dungeonLevel.getColumns() == 31);
		assertTrue(dungeonLevel.getRows() == 31);
		assertTrue(dungeonLevel.getGridSize() == 50.0);
		assertTrue(dungeonLevel.getGridOffsetX() == 11.0);
		assertTrue(dungeonLevel.getGridOffsetY() == 11.0);
		assertTrue(dungeonLevel.getMonsters().getPogList().length == 5);
		assertTrue(dungeonLevel.getRoomObjects().getPogList().length == 2);
	}

	/**
	 * Populate dungeon list.
	 * 
	 * @param dungeonManager to populate
	 */
	private void populateDungeonList(final DungeonManager dungeonManager) {
		dungeonManager.handleSuccessfulDungeonList("", new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
				assertTrue(true);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
				assertTrue(false);
			}
		}, MockResponseData.GETDUNGEONLISTRESPONSE);
	}
}
