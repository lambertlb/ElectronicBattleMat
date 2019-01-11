package per.lambert.ebattleMat.client.services;

import java.util.Iterator;
import java.util.Map;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;

import per.lambert.ebattleMat.client.interfaces.DungeonServerError;
import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;

public class DataRequester implements IDataRequester {

	@Override
	public void requestData(final String requestData, final String requestType,
			final Map<String, String> parameters, final IUserCallback callback) {
		String url = buildUrl(requestType, parameters);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		try {
			builder.sendRequest(requestData, new RequestCallback() {

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

	public static String buildUrl(final String requestType, final Map<String, String> parameters) {
		parameters.put("token", "" + ServiceManager.getDungeonManager().getToken());
		parameters.put("request", "" + requestType);
		UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
		urlBuilder.setPath("electronicbattlemat/dungeons");
		addParametersToURL(parameters, urlBuilder);
		String url = urlBuilder.buildString();
		return url;
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

	public static void addParametersToURL(final Map<String, String> parameters, final UrlBuilder urlBuilder) {
		if (parameters != null) {
			Iterator<Map.Entry<String, String>> contactIterator = parameters.entrySet().iterator();
			while (contactIterator.hasNext()) {
				Map.Entry<String, String> anEntry = contactIterator.next();
				String parameter = anEntry.getKey();
				String value = anEntry.getValue();
				urlBuilder.setParameter(parameter, value);
			}
		}
	}

}
