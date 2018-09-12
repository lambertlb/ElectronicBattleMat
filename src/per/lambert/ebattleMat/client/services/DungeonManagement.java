package per.lambert.ebattleMat.client.services;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.interfaces.DungeonData;
import per.lambert.ebattleMat.client.interfaces.IDungeonManagement;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;

public class DungeonManagement implements IDungeonManagement {
	
	@Override
	public void login(JavaScriptObject requestData, IUserCallback callback) {
		String request = JsonUtils.stringify(requestData);
	}

	@Override
	public DungeonData getDungeonData() {
		return null;
	}

	@Override
	public String getToken() {
		return null;
	}

}
