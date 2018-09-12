package per.lambert.ebattleMat.client.services;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;

public class DataRequester implements IDataRequester{

	@Override
	public void requestData(final JavaScriptObject postDataOject, IUserCallback callback) {
		String request = JsonUtils.stringify(postDataOject);
	}

}
