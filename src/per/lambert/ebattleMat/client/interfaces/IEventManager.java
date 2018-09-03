package per.lambert.ebattleMat.client.interfaces;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public interface IEventManager {
	<H extends EventHandler> void addHandler(GwtEvent.Type<H> eventType, H handler);

	void fireEvent(GwtEvent<?> event);
}
