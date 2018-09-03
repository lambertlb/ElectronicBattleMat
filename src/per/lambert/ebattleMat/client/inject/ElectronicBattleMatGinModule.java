package per.lambert.ebattleMat.client.inject;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.services.EventManager;

public class ElectronicBattleMatGinModule extends AbstractGinModule{

	@Override
	protected void configure() {
		bind(IEventManager.class).to(EventManager.class).in(Singleton.class);
	}
	@Provides
	protected final HandlerManager provideHandlerManager() {
		return new HandlerManager(null);
	}

}
