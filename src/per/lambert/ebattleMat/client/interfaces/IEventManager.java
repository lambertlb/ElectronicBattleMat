package per.lambert.ebattleMat.client.interfaces;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public interface IEventManager {
	<H extends EventHandler> HandlerRegistration addHandler(GwtEvent.Type<H> eventType, H handler);

	void fireEvent(GwtEvent<?> event);
}
