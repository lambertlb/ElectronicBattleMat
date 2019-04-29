package per.lambert.ebattleMat.client;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.junit.client.GWTTestCase;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.Constants;
import per.lambert.ebattleMat.client.interfaces.DungeonServerError;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.interfaces.PogPlace;
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
import per.lambert.ebattleMat.client.services.serviceData.DungeonSessionData;
import per.lambert.ebattleMat.client.services.serviceData.DungeonSessionLevel;
import per.lambert.ebattleMat.client.services.serviceData.FogOfWarData;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class ElectronicBattleMatTest extends GWTTestCase {
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
	 * test login function..
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
				assertTrue(true);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
				assertTrue(false);
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
		String[] races = dungeonManager.getCommonRaces(Constants.POG_TYPE_MONSTER);
		assertTrue(races != null && races.length == 2);
		String[] classes = dungeonManager.getCommonClasses(Constants.POG_TYPE_MONSTER);
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
		String[] classes = dungeonManager.getCommonClasses(Constants.POG_TYPE_ROOMOBJECT);
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
		assertTrue(dungeonManager.getNextAvailableLevelNumber() == 4);
		assertTrue(dungeonManager.isDungeonMaster());
		assertTrue(dungeonManager.isEditMode());
		checkDungeon1TemplateData(dungeonData);
	}

	/**
	 * Test saving dungeon data after editing.
	 */
	public void testSaveSelectedDungeonTemplate() {
		DungeonManager dungeonManager = new DungeonManager();
		populateDungeonList(dungeonManager);
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {
			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				dataRequesterForTest.handleMockDataRequest(requestType, parameters, callback);
			}
		});
		dungeonManager.editSelectedDungeonUUID("dungeon1-template");
		assertTrue(dungeonManager.isThereASelectedDungeon());
		eventManagerForTest.setEventManagerTestCallback(new EventManagerTestCallback() {
			@Override
			public void onEvent(final GwtEvent<?> event) {
				setEventResults(event);
			}
		});
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {
			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				assertTrue(requestType == "SAVEJSONFILE");
				assertTrue(parameters.containsKey("dungeonUUID"));
				assertTrue(parameters.get("dungeonUUID") == "dungeon1-template");
				dataRequesterForTest.setTestCallback(null);
				callback.onSuccess(null, null);
			}
		});
		dungeonManager.saveDungeonData();
		hadEvent(ReasonForAction.DungeonDataSaved);
	}

	/**
	 * Test setting session level size.
	 */
	public void testSetSessionLevelSize() {
		DungeonManager dungeonManager = new DungeonManager();
		populateDungeonList(dungeonManager);
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {
			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				dataRequesterForTest.handleMockDataRequest(requestType, parameters, callback);
			}
		});
		dungeonManager.editSelectedDungeonUUID("dungeon1-template");
		assertTrue(dungeonManager.isThereASelectedDungeon());
		eventManagerForTest.setEventManagerTestCallback(new EventManagerTestCallback() {
			@Override
			public void onEvent(final GwtEvent<?> event) {
				setEventResults(event);
			}
		});
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {
			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				callback.onSuccess(null, null);
			}
		});
		dungeonManager.setSessionLevelSize(50, 60);
		DungeonLevel dungeonLevel = dungeonManager.getCurrentDungeonLevelData();
		assertTrue(dungeonLevel.getColumns() == 50);
		assertTrue(dungeonLevel.getRows() == 60);
		hadEvent(ReasonForAction.DungeonDataSaved);
	}

	/**
	 * Test creating new dungeon.
	 */
	public void testCreateNewDungeon() {
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
				assertTrue(requestType == "CREATENEWDUNGEON");
				assertTrue(parameters.containsKey("dungeonUUID"));
				assertTrue(parameters.get("dungeonUUID") == "dungeon1-template");
				assertTrue(parameters.containsKey("newDungeonName"));
				assertTrue(parameters.get("newDungeonName") == "New Dungeon");

				// Create new dungeon callback will call get dungeon list so we have to fake return one.
				dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {
					@Override
					public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
						dataRequesterForTest.setTestCallback(null);
						callback.onSuccess(null, MockResponseData.GETDUNGEONLISTRESPONSE);
					}
				});
				callback.onSuccess(null, null);
			}
		});
		dungeonManager.createNewDungeon("dungeon1-template", "New Dungeon");
		hadEvent(ReasonForAction.DungeonDataCreated);
	}

	/**
	 * Test if OK to delete a dungeon.
	 */
	public void testOkToDeleteThisTemplate() {
		DungeonManager dungeonManager = new DungeonManager();
		populateDungeonList(dungeonManager);
		assertTrue(dungeonManager.okToDeleteThisTemplate("dungeon1-template"));
		assertFalse(dungeonManager.okToDeleteThisTemplate("template-dungeon"));
	}

	/**
	 * Test dungeon template.
	 */
	public void testDeleteTemplate() {
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
				assertTrue(requestType == "DELETEDUNGEON");
				assertTrue(parameters.containsKey("dungeonUUID"));
				assertTrue(parameters.get("dungeonUUID") == "dungeon1-template");

				// delete dungeon callback will call get dungeon list so we have to fake return one.
				dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {
					@Override
					public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
						dataRequesterForTest.setTestCallback(null);
						callback.onSuccess(null, MockResponseData.GETDUNGEONLISTRESPONSE);
					}
				});
				callback.onSuccess(null, null);
			}
		});
		dungeonManager.deleteTemplate("dungeon1-template");
		hadEvent(ReasonForAction.DungeonDataDeleted);
	}

	/**
	 * Test get session list.
	 */
	public void testGetSessionList() {
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
				assertTrue(requestType == "GETSESSIONLIST");
				assertTrue(parameters.containsKey("dungeonUUID"));
				assertTrue(parameters.get("dungeonUUID") == "dungeon1-template");
				callback.onSuccess(null, MockResponseData.GETSESSIONLISTRESPONSE);
			}
		});
		dungeonManager.getSessionList("dungeon1-template");
		hadEvent(ReasonForAction.SessionListChanged);
	}

	/**
	 * Test create new session.
	 */
	public void testCreateNewSession() {
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
				assertTrue(requestType == "CREATENEWSESSION");
				assertTrue(parameters.containsKey("dungeonUUID"));
				assertTrue(parameters.get("dungeonUUID") == "dungeon1-template");
				assertTrue(parameters.containsKey("newSessionName"));
				assertTrue(parameters.get("newSessionName") == "New Session");

				// creating new session will call get session list
				dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {
					@Override
					public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
						callback.onSuccess(null, MockResponseData.GETSESSIONLISTRESPONSE);
					}
				});
				callback.onSuccess(null, null);
			}
		});
		dungeonManager.createNewSession("dungeon1-template", "New Session");
		hadEvent(ReasonForAction.SessionListChanged);
	}

	/**
	 * Test deleting a session.
	 */
	public void testDeleteSession() {
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
				assertTrue(requestType == "DELETESESSION");
				assertTrue(parameters.containsKey("dungeonUUID"));
				assertTrue(parameters.get("dungeonUUID") == "dungeon1-template");
				assertTrue(parameters.containsKey("sessionUUID"));
				assertTrue(parameters.get("sessionUUID") == "e3eb2220-2d31-4b5a-ad9f-063624ac209c");

				// deleting session will call get session list
				dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {
					@Override
					public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
						callback.onSuccess(null, MockResponseData.GETSESSIONLISTRESPONSE);
					}
				});
				callback.onSuccess(null, null);
			}
		});
		dungeonManager.deleteSession("dungeon1-template", "e3eb2220-2d31-4b5a-ad9f-063624ac209c");
		hadEvent(ReasonForAction.SessionListChanged);
	}

	/**
	 * Test DMing a session.
	 * 
	 * This is complicated to setup because it requires the following. 1) loaded dungeon list. 2) loaded and selected dungeon. 3) loaded session list for dungeon.
	 */
	public void testDmSession() {
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
				if (requestType != "LOADSESSION") {
					// defer all other requests to generic mock request handler.
					dataRequesterForTest.handleMockDataRequest(requestType, parameters, callback);
					return;
				}
				assertTrue(parameters.containsKey("dungeonUUID"));
				assertTrue(parameters.get("dungeonUUID") == "dungeon1-template");
				assertTrue(parameters.containsKey("sessionUUID"));
				assertTrue(parameters.get("sessionUUID") == "e3eb2220-2d31-4b5a-ad9f-063624ac209c");
				assertTrue(parameters.containsKey("version"));
				assertTrue(parameters.get("version") == "-1");
				callback.onSuccess(null, MockResponseData.LOADSESSIONDATARESPONSE);
			}
		});
		dungeonManager.dmSession("dungeon1-template", "e3eb2220-2d31-4b5a-ad9f-063624ac209c");
		hadEvent(ReasonForAction.DungeonDataReadyToJoin);
		assertTrue(dungeonManager.getSelectedSession() != null);
		assertTrue(dungeonManager.isDungeonMaster());
		assertFalse(dungeonManager.isEditMode());
		spotCheckSessionData(dungeonManager.getSelectedSession());
	}

	/**
	 * Test Joining a session.
	 * 
	 * This is complicated to setup because it requires the following. 1) loaded dungeon list. 2) loaded and selected dungeon. 3) loaded session list for dungeon.
	 */
	public void testJoinSession() {
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
				if (requestType != "LOADSESSION") {
					// defer all other requests to generic mock request handler.
					dataRequesterForTest.handleMockDataRequest(requestType, parameters, callback);
					return;
				}
				assertTrue(parameters.containsKey("dungeonUUID"));
				assertTrue(parameters.get("dungeonUUID") == "dungeon1-template");
				assertTrue(parameters.containsKey("sessionUUID"));
				assertTrue(parameters.get("sessionUUID") == "e3eb2220-2d31-4b5a-ad9f-063624ac209c");
				assertTrue(parameters.containsKey("version"));
				assertTrue(parameters.get("version") == "-1");
				callback.onSuccess(null, MockResponseData.LOADSESSIONDATARESPONSE);
			}
		});
		dungeonManager.joinSession("dungeon1-template", "e3eb2220-2d31-4b5a-ad9f-063624ac209c");
		hadEvent(ReasonForAction.DungeonDataReadyToJoin);
		assertTrue(dungeonManager.getSelectedSession() != null);
		assertFalse(dungeonManager.isDungeonMaster());
		assertFalse(dungeonManager.isEditMode());
		spotCheckSessionData(dungeonManager.getSelectedSession());
	}

	/**
	 * Test setting fog of war change.
	 */
	public void testSetFow() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		assertFalse(dungeonManager.isFowDirty());
		assertTrue(dungeonManager.isFowSet(1, 1));
		dungeonManager.setFow(1, 1, false);
		assertTrue(dungeonManager.isFowDirty());
		assertFalse(dungeonManager.isFowSet(1, 1));
	}

	/**
	 * Test saving fog of war data to server.
	 */
	public void testSaveFow() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		assertFalse(dungeonManager.isFowDirty());
		assertTrue(dungeonManager.isFowSet(1, 1));
		dungeonManager.setFow(1, 1, false);
		assertTrue(dungeonManager.isFowDirty());
		assertFalse(dungeonManager.isFowSet(1, 1));
		eventManagerForTest.setEventManagerTestCallback(new EventManagerTestCallback() {
			@Override
			public void onEvent(final GwtEvent<?> event) {
				setEventResults(event);
			}
		});
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {
			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				assertTrue(requestType == "UPDATEFOW");
				assertTrue(parameters.containsKey("sessionUUID"));
				assertTrue(parameters.get("sessionUUID") == "362dd584-3449-4687-aeed-d1a2ac2f10bd");
				FogOfWarData fogOfWarData = JsonUtils.<FogOfWarData>safeEval(requestData);
				assertFalse(fogOfWarData.getFOW()[1][1]);
				callback.onSuccess(null, null);
			}
		});
		dungeonManager.saveFow();
		hadEvent(ReasonForAction.SessionDataSaved);
		assertFalse(dungeonManager.isFowDirty());
	}

	/**
	 * test getting directory to current dungeon.
	 */
	public void testGetDirectoryForCurrentDungeon() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		String directoryPath = dungeonManager.getDirectoryForCurrentDungeon();
		assertTrue(directoryPath == "/dungeonData/dungeons/dungeon1");
	}

	/**
	 * test getting url to current dungeon.
	 */
	public void testGetUrlToDungeonData() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		String url = dungeonManager.getUrlToDungeonData();
		assertTrue(url == "http://ElectronicBattleMat//dungeonData/dungeons/dungeon1/");
	}

	/**
	 * test getting url to current dungeon resource.
	 */
	public void testGetUrlToDungeonResource() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		String url = dungeonManager.getUrlToDungeonResource("item1");
		assertTrue(url.startsWith("http://ElectronicBattleMat//dungeonData/dungeons/dungeon1/item1?"));
	}

	/**
	 * test is Name Valid For New Session.
	 */
	public void testIsNameValidForNewSession() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		assertFalse(dungeonManager.isNameValidForNewSession("Session 1"));
		assertTrue(dungeonManager.isNameValidForNewSession("Session 2"));
	}

	/**
	 * test is Name Valid For New character name.
	 */
	public void testIsValidNewCharacterName() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		assertFalse(dungeonManager.isValidNewCharacterName("Enter name"));
		assertTrue(dungeonManager.isValidNewCharacterName("Fred"));
	}

	/**
	 * test is Name Valid For New monster name.
	 */
	public void testIsValidNewMonsterName() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		assertFalse(dungeonManager.isValidNewMonsterName("Enter name"));
		assertTrue(dungeonManager.isValidNewMonsterName("BARF"));
	}

	/**
	 * test Get Monsters For Current Level.
	 */
	public void testGetMonstersForCurrentLevel() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		PogData[] monstersInLevel = dungeonManager.getMonstersForCurrentLevel();
		assertTrue(monstersInLevel != null);
		assertTrue(monstersInLevel.length == 5);
	}

	/**
	 * test Get Room objects For Current Level.
	 */
	public void testGetRoomObjectsForCurrentLevel() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		PogData[] roomObjectsInLevel = dungeonManager.getRoomObjectsForCurrentLevel();
		assertTrue(roomObjectsInLevel != null);
		assertTrue(roomObjectsInLevel.length == 2);
	}

	/**
	 * test Get Players For Current Session.
	 */
	public void testGetPlayersForCurrentSession() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		PogData[] players = dungeonManager.getPlayersForCurrentSession();
		assertTrue(players != null);
		assertTrue(players.length == 2);
	}

	/**
	 * test get Dungeon Level Names.
	 */
	public void testGetDungeonLevelNames() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		String[] levelNames = dungeonManager.getDungeonLevelNames();
		assertTrue(levelNames != null);
		assertTrue(levelNames.length == 3);
		assertTrue(levelNames[0] == "Entrance");
		assertTrue(levelNames[1] == "Deep Dark (2)");
		assertTrue(levelNames[2] == "Flooded Area (3)");
	}

	/**
	 * test find player via UUID.
	 */
	public void testFindCharacterPog() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		PogData player = dungeonManager.findCharacterPog("3bdce6d8-09a6-4af6-b362-0c375ee5cc5c");
		assertTrue(player != null);
		assertTrue(player.getName() == "Zorak");
	}

	/**
	 * test compute place pog should be stored.
	 */
	public void testComputePlace() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		PogData player = dungeonManager.findCharacterPog("3bdce6d8-09a6-4af6-b362-0c375ee5cc5c");
		PogPlace place = dungeonManager.computePlace(player);
		assertTrue(place == PogPlace.SESSION_RESOURCE);
		PogData monsterTemplate = dungeonManager.getMonsterTemplatePogs()[0];
		assertTrue(monsterTemplate != null);
		place = dungeonManager.computePlace(monsterTemplate);
		assertTrue(place == PogPlace.COMMON_RESOURCE);
		PogData levelMonster = dungeonManager.getMonstersForCurrentLevel()[0];
		dungeonManager.setEditModeForUnitTest(true);
		dungeonManager.setDungeonMasterForUnitTest(true);
		assertTrue(dungeonManager.computePlace(levelMonster) == PogPlace.DUNGEON_INSTANCE);
		dungeonManager.setEditModeForUnitTest(false);
		assertTrue(dungeonManager.computePlace(levelMonster) == PogPlace.SESSION_INSTANCE);
		dungeonManager.setDungeonMasterForUnitTest(false);
		assertTrue(dungeonManager.computePlace(levelMonster) == PogPlace.INVALID);
	}

	/**
	 * test add Or Update Pog.
	 */
	public void tesAddOrUpdatePog() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		eventManagerForTest.setEventManagerTestCallback(new EventManagerTestCallback() {
			@Override
			public void onEvent(final GwtEvent<?> event) {
				setEventResults(event);
			}
		});
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {
			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				assertTrue(requestType == "ADDORUPDATEPOG");
				assertTrue(parameters.containsKey("dungeonUUID"));
				assertTrue(parameters.get("dungeonUUID") == "dungeon1-template");
				assertTrue(parameters.containsKey("sessionUUID"));
				assertTrue(parameters.get("sessionUUID") == "362dd584-3449-4687-aeed-d1a2ac2f10bd");
				assertTrue(parameters.containsKey("currentLevel"));
				assertTrue(parameters.get("currentLevel") == "0");
				assertTrue(parameters.containsKey("place"));
				assertTrue(parameters.get("place") == "COMMON_RESOURCE");
				callback.onSuccess(null, null);
			}
		});
		PogData monsterTemplate = dungeonManager.getMonsterTemplatePogs()[0];
		dungeonManager.addOrUpdatePog(monsterTemplate);
		hadEvent(ReasonForAction.SessionDataSaved);
	}

	/**
	 * test add Or Update Pog.
	 */
	public void tesAddOrUpdatePog2() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		eventManagerForTest.setEventManagerTestCallback(new EventManagerTestCallback() {
			@Override
			public void onEvent(final GwtEvent<?> event) {
				setEventResults(event);
			}
		});
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {
			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				assertTrue(requestType == "ADDORUPDATEPOG");
				assertTrue(parameters.containsKey("dungeonUUID"));
				assertTrue(parameters.get("dungeonUUID") == "dungeon1-template");
				assertTrue(parameters.containsKey("sessionUUID"));
				assertTrue(parameters.get("sessionUUID") == "");
				assertTrue(parameters.containsKey("currentLevel"));
				assertTrue(parameters.get("currentLevel") == "0");
				assertTrue(parameters.containsKey("place"));
				assertTrue(parameters.get("place") == "DUNGEON_INSTANCE");
				callback.onSuccess(null, null);
			}
		});
		PogData monsterTemplate = dungeonManager.getMonsterTemplatePogs()[0];
		dungeonManager.setEditModeForUnitTest(true);
		dungeonManager.setDungeonMasterForUnitTest(true);
		dungeonManager.addOrUpdatePog(monsterTemplate);
	}

	/**
	 * test add Or Update Pog.
	 */
	public void tesAddOrUpdatePog3() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		eventManagerForTest.setEventManagerTestCallback(new EventManagerTestCallback() {
			@Override
			public void onEvent(final GwtEvent<?> event) {
				setEventResults(event);
			}
		});
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {
			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				assertTrue(requestType == "ADDORUPDATEPOG");
				assertTrue(parameters.containsKey("dungeonUUID"));
				assertTrue(parameters.get("dungeonUUID") == "dungeon1-template");
				assertTrue(parameters.containsKey("sessionUUID"));
				assertTrue(parameters.get("sessionUUID") == "");
				assertTrue(parameters.containsKey("currentLevel"));
				assertTrue(parameters.get("currentLevel") == "0");
				assertTrue(parameters.containsKey("place"));
				assertTrue(parameters.get("place") == "INVALID");
				callback.onSuccess(null, null);
			}
		});
		PogData monsterTemplate = dungeonManager.getMonsterTemplatePogs()[0];
		dungeonManager.setEditModeForUnitTest(false);
		dungeonManager.setDungeonMasterForUnitTest(false);
		dungeonManager.addOrUpdatePog(monsterTemplate);
	}

	/**
	 * test get Filtered Templates.
	 */
	public void testGetFilteredTemplates() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		ArrayList<PogData> templates = dungeonManager.getFilteredTemplates(PogPlace.COMMON_RESOURCE, Constants.POG_TYPE_MONSTER, "Kobold", null, null);
		assertTrue(templates.size() == 2);
		assertTrue(templates.get(0).getName() == "Male Kobold");
		assertTrue(templates.get(1).getName() == "Female Kobold");
	}

	/**
	 * test get Filtered Templates.
	 */
	public void testGetFilteredTemplates2() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		ArrayList<PogData> templates = dungeonManager.getFilteredTemplates(PogPlace.COMMON_RESOURCE, Constants.POG_TYPE_MONSTER, null, null, "Female");
		assertTrue(templates.size() == 1);
		assertTrue(templates.get(0).getName() == "Female Kobold");
	}

	/**
	 * test get Filtered Templates.
	 */
	public void testGetFilteredTemplates3() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		ArrayList<PogData> templates = dungeonManager.getFilteredTemplates(PogPlace.COMMON_RESOURCE, Constants.POG_TYPE_MONSTER, null, "Fighter", null);
		assertTrue(templates.size() == 3);
		assertTrue(templates.get(0).getName() == "Male Kobold");
		assertTrue(templates.get(1).getName() == "Female Kobold");
		assertTrue(templates.get(2).getName() == "Orc Fighter");
	}

	/**
	 * test get Filtered Templates.
	 */
	public void testGetFilteredTemplates4() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		ArrayList<PogData> templates = dungeonManager.getFilteredTemplates(PogPlace.COMMON_RESOURCE, Constants.POG_TYPE_MONSTER, null, null, null);
		assertTrue(templates.size() == 4);
		assertTrue(templates.get(0).getName() == "Male Kobold");
		assertTrue(templates.get(1).getName() == "Female Kobold");
		assertTrue(templates.get(2).getName() == "Orc Fighter");
		assertTrue(templates.get(3).getName() == "Orc Shaman");
	}

	/**
	 * test set Selected Template.
	 */
	public void testSetSelectedTemplate() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		eventManagerForTest.setEventManagerTestCallback(new EventManagerTestCallback() {
			@Override
			public void onEvent(final GwtEvent<?> event) {
				setEventResults(event);
			}
		});
		assertTrue(dungeonManager.getSelectedPog() == null);
		dungeonManager.setSelectedTemplate(PogPlace.COMMON_RESOURCE, Constants.POG_TYPE_MONSTER, "Male-Kobold");
		hadEvent(ReasonForAction.PogWasSelected);
		assertTrue(dungeonManager.getSelectedPog() != null);
		assertTrue(dungeonManager.getSelectedPog().getName() == "Male Kobold");
	}

	/**
	 * test get Template Classes.
	 */
	public void testGetTemplateClasses() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		String[] templates = dungeonManager.getTemplateClasses(PogPlace.COMMON_RESOURCE, Constants.POG_TYPE_MONSTER);
		assertTrue(templates.length == 2);
		assertTrue(templates[0] == "Fighter");
		assertTrue(templates[1] == "Shaman");
	}

	/**
	 * test get Template Races.
	 */
	public void testGetTemplateRaces() {
		DungeonManager dungeonManager = new DungeonManager();
		populateSession(dungeonManager);
		String[] templates = dungeonManager.getTemplateRaces(PogPlace.COMMON_RESOURCE, Constants.POG_TYPE_MONSTER);
		assertTrue(templates.length == 2);
		assertTrue(templates[0] == "Kobold");
		assertTrue(templates[1] == "Orc");
	}

	/**
	 * Spot check session data.
	 * 
	 * @param selectedSession to check
	 */
	private void spotCheckSessionData(final DungeonSessionData selectedSession) {
		assertTrue(selectedSession.getDungeonUUID() == "dungeon1-template");
		assertTrue(selectedSession.getSessionUUID() == "362dd584-3449-4687-aeed-d1a2ac2f10bd");
		assertTrue(selectedSession.getSessionName() == "Session 1");
		assertTrue(selectedSession.getVersion() == 1);
		assertTrue(selectedSession.getSessionLevels().length == 3);
		spotCheckSessionLevel(selectedSession.getSessionLevels()[0]);
	}

	/**
	 * Spot check a dungeon level.
	 * 
	 * @param dungeonSessionLevel to check
	 */
	private void spotCheckSessionLevel(final DungeonSessionLevel dungeonSessionLevel) {
		assertTrue(dungeonSessionLevel.getFOW() != null);
		assertTrue(dungeonSessionLevel.getFOW().length != 0);
		assertTrue(dungeonSessionLevel.getMonsters() != null);
		assertTrue(dungeonSessionLevel.getMonsters().getPogList().length != 0);
		assertTrue(dungeonSessionLevel.getRoomObjects() != null);
		assertTrue(dungeonSessionLevel.getRoomObjects().getPogList().length != 0);
	}

	/**
	 * Do some spot checks on dungeon 1 data.
	 * 
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
	 * 
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

	/**
	 * populate session data for tests.
	 * 
	 * @param dungeonManager to populate
	 */
	private void populateSession(final DungeonManager dungeonManager) {
		populateDungeonList(dungeonManager);
		dungeonManager.handleSuccessfulSessionList(MockResponseData.GETSESSIONLISTRESPONSE);
		dataRequesterForTest.setTestCallback(new DataRequesterTestCallback() {
			@Override
			public void onCall(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
				dataRequesterForTest.handleMockDataRequest(requestType, parameters, callback);
			}
		});
		dungeonManager.dmSession("dungeon1-template", "e3eb2220-2d31-4b5a-ad9f-063624ac209c");
		dataRequesterForTest.setTestCallback(null);
		assertTrue(dungeonManager.getSelectedSession() != null);
	}
}
