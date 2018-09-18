package per.lambert.ebattleMat.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

import per.lambert.ebattleMat.client.interfaces.DungeonServerError;
import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.services.serviceData.ServiceRequestData;

public class DataRequester implements IDataRequester {

	@Override
	public void requestData(final ServiceRequestData postDataOject, final String requestType, IUserCallback callback) {
		requestData(postDataOject, 0, requestType, callback);
	}

	@Override
	public void requestData(final ServiceRequestData postDataOject, final int token, final String requestType, IUserCallback callback) {
		postDataOject.setToken(token);
		postDataOject.setServiceRequest(requestType);
		String requestData = JsonUtils.stringify(postDataOject);
		String url = GWT.getModuleBaseURL() + "dungeons";
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		try {

			Request request = builder.sendRequest(requestData, new RequestCallback() {

				// (i) callback handler when there is an error
				public void onError(Request request, Throwable exception) {
					handleCallbackError(DungeonServerError.Undefined1, null, callback);
				}

				@Override
				public void onResponseReceived(Request request, Response response) {
					if (Response.SC_OK == response.getStatusCode()) {
						callback.onSuccess(this, response.getText());
					} else {
						handleCallbackError(DungeonServerError.Undefined1, null, callback);
					}
				}
			});

		} catch (RequestException e) {
			handleCallbackError(DungeonServerError.Undefined1, e, callback);
		}

	}
	protected void handleCallbackError(final DungeonServerError dungeonError, final Throwable exception,
			IUserCallback userCallback) {
		userCallback.onError(this, new IErrorInformation() {

			@Override
			public Throwable getException() {
				return exception;
			}

			@Override
			public DungeonServerError getError() {
				return dungeonError;
			}

			@Override
			public String getErrorMessage() {
				if (exception != null) {
					return (exception.getMessage());
				}
				return "";
			}
		});
	}

}
