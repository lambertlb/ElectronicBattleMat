package per.lambert.ebattleMat.client;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.junit.client.GWTTestCase;

import per.lambert.ebattleMat.client.interfaces.DungeonServerError;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.services.DungeonManager;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.DungeonListData;
import per.lambert.ebattleMat.client.services.serviceData.LoginResponseData;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class ElectronicBattleMatTest extends GWTTestCase {

	/**
	 * Data requester used for test.
	 */
	private MockDataRequester dataRequesterForTest = new MockDataRequester();

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
				LoginResponseData loginResponse = (LoginResponseData) JavaScriptObject.createObject().cast();
				loginResponse.setError(0);
				loginResponse.setToken(355);
				dataRequesterForTest.setTestCallback(null);
				callback.onSuccess(null, JsonUtils.stringify(loginResponse));
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
				DungeonListData dungeonListData = (DungeonListData) JavaScriptObject.createObject().cast();
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
}
