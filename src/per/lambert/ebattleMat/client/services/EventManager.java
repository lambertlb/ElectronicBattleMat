package per.lambert.ebattleMat.client.services;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;

import per.lambert.ebattleMat.client.interfaces.IEventManager;

public class EventManager implements IEventManager {

	private final HandlerManager eventBus;

	@Inject
	public EventManager(final HandlerManager eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public <H extends EventHandler> void addHandler(Type<H> eventType, H handler) {
		eventBus.addHandler(eventType, handler);
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		eventBus.fireEvent(event);
	}

}
