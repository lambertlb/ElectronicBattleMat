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
package per.lambert.ebattleMat.client.mocks;

import java.util.Map;

import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;

/**
 * Mock Data requester.
 * 
 * @author LLambert
 *
 */
public class MockDataRequester implements IDataRequester {

	/**
	 * Callback to test.
	 */
	private DataRequesterTestCallback testCallback;

	/**
	 * Get test callback.
	 * 
	 * @return test callback
	 */
	public DataRequesterTestCallback getTestCallback() {
		return testCallback;
	}

	/**
	 * set test callback.
	 * 
	 * @param testCallback test callback
	 */
	public void setTestCallback(final DataRequesterTestCallback testCallback) {
		this.testCallback = testCallback;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void requestData(final String requestData, final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
		if (testCallback != null) {
			testCallback.onCall(requestData, requestType, parameters, callback);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getWebPath() {
		return "http://ElectronicBattleMat/";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String buildUrl(final String requestType, final Map<String, String> parameters) {
		return "http://ElectronicBattleMat/";
	}

	/**
	 * Handle giving back proper mock data for request.
	 * 
	 * @param requestType request type
	 * @param parameters for request
	 * @param callback call back
	 */
	public void handleMockDataRequest(final String requestType, final Map<String, String> parameters, final IUserCallback callback) {
		if (requestType == "LOADJSONFILE") {
			handleLoadJsonFile(parameters, callback);
		} else if (requestType == "LOADSESSION") {
			callback.onSuccess(null, MockResponseData.LOADSESSIONDATARESPONSE);
		}
	}

	/**
	 * Handle Mock load json file.
	 * 
	 * @param parameters for request
	 * @param callback call back
	 */
	private void handleLoadJsonFile(final Map<String, String> parameters, final IUserCallback callback) {
		String fileName = parameters.get("fileName");
		String dungeonUUID = parameters.get("dungeonUUID");
		if (dungeonUUID != null) {
			callback.onSuccess(null, MockResponseData.LOADDUNGEON1TEMPLATE);
		} else {
			if (fileName.contains("monsters")) {
				callback.onSuccess(null, MockResponseData.LOADMONSTERPOGTEMPLATES);
			} else {
				callback.onSuccess(null, MockResponseData.LOADROOMOBJECTPOGTEMPLATES);
			}
		}
	}

}
