package per.lambert.ebattleMat.client.interfaces;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Interface to error manager.
 * 
 * @author LLambert
 *
 */
public interface IEventManager {
	/**
	 * Adds a handler.
	 * 
	 * @param <H> The type of handler
	 * @param eventType the event type associated with this handler
	 * @param handler the handler
	 * @return the handler registration, can be stored in order to remove the handler later
	 */
	<H extends EventHandler> HandlerRegistration addHandler(GwtEvent.Type<H> eventType, H handler);

	  /**
	   * Fires the given event to the handlers listening to the event's type.
	   * <p>
	   * Any exceptions thrown by handlers will be bundled into a
	   * {@link UmbrellaException} and then re-thrown after all handlers have
	   * completed. An exception thrown by a handler will not prevent other handlers
	   * from executing.
	   * <p>
	   * Note, any subclass should be very careful about overriding this method, as
	   * adds/removes of handlers will not be safe except within this
	   * implementation.
	   * 
	   * @param event the event
	   */
	void fireEvent(GwtEvent<?> event);
}
