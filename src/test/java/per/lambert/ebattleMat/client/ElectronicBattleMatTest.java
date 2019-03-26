package per.lambert.ebattleMat.client;

import java.util.Map;

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
	}

	/**
	 * See if we can access resource files for test.
	 */
	public void testLogin() {
		DungeonManager dungeonManager = new DungeonManager();
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
	 * whether we got a successful event callback.
	 */
	private boolean gotSuccessfulCallback;
	/**
	 * Test loading monster pogs.
	 */
	public void testLoadingMonsterPogs() {
		gotSuccessfulCallback = false;
		DungeonManager dungeonManager = new DungeonManager();
		eventManagerForTest.setEventManagerTestCallback(new EventManagerTestCallback() {
			
			@Override
			public void onEvent(final GwtEvent<?> event) {
				if (event instanceof ReasonForActionEvent) {
					ReasonForActionEvent actionEvent = (ReasonForActionEvent)event;
					gotSuccessfulCallback = actionEvent.getReasonForAction() == ReasonForAction.MonsterPogsLoaded;
				}
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
		assertTrue(gotSuccessfulCallback);
		PogData pogData = dungeonManager.findMonsterPog("Male-Kobold");
		assertTrue(pogData != null);
		String[] races = dungeonManager.getCommonRaces(ElectronicBattleMat.POG_TYPE_MONSTER);
		assertTrue(races != null && races.length == 2);
		String[] classes = dungeonManager.getCommonClasses(ElectronicBattleMat.POG_TYPE_MONSTER);
		assertTrue(classes != null && classes.length == 2);
		PogData[] monsterPogs = dungeonManager.getMonsterTemplatePogs();
		assertTrue(monsterPogs != null && monsterPogs.length == 4);
	}
}
