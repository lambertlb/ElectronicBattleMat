package per.lambert.ebattleMat.client.mocks;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Callback to unit test when eent is fired from event manager.
 * @author LLambert
 *
 */
public interface EventManagerTestCallback {

	/**
	 * Tell unit test about event.
	 * @param event that fired
	 */
	void onEvent(GwtEvent<?> event);
}
