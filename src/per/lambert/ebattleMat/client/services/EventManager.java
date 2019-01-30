package per.lambert.ebattleMat.client.services;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;

import per.lambert.ebattleMat.client.interfaces.IEventManager;

/**
 * Event buss wrapper.
 * @author LLambert
 *
 */
public class EventManager implements IEventManager {

	/**
	 * GWT event bus.
	 */
	private final HandlerManager eventBus;

	/**
	 * constructor for event manager.
	 */
	@Inject
	public EventManager() {
		this.eventBus = new HandlerManager(new Object());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <H extends EventHandler> HandlerRegistration addHandler(final Type<H> eventType, final H handler) {
		return (eventBus.addHandler(eventType, handler));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireEvent(final GwtEvent<?> event) {
		eventBus.fireEvent(event);
	}

}
