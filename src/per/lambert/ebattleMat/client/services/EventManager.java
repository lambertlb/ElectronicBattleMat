package per.lambert.ebattleMat.client.services;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;

import per.lambert.ebattleMat.client.interfaces.IEventManager;

public class EventManager implements IEventManager {

	private final HandlerManager eventBus;

	@Inject
	public EventManager() {
		this.eventBus = new HandlerManager(new Object());
	}

	@Override
	public <H extends EventHandler> HandlerRegistration addHandler(Type<H> eventType, H handler) {
		return(eventBus.addHandler(eventType, handler));
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		eventBus.fireEvent(event);
	}

}
