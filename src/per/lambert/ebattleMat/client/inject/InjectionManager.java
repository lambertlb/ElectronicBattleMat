package per.lambert.ebattleMat.client.inject;

import com.google.gwt.core.client.GWT;

import per.lambert.ebattleMat.client.interfaces.IEventManager;

/**
 * This class manages injection. It will maintain a static instance of an
 * injector. It will expose getters for all injected instances
 * 
 * @author LLambert
 */
public final class InjectionManager {
	/**
	 * GIN injector.
	 */
	private static ElectronicBattleMatGinInjector injector;
	/**
	 * Injector for unit testing.
	 */
	private static ElectronicBattleMatInjector testInjector;

	/**
	 * Hide public constructor.
	 */
	private InjectionManager() {
	}

	/**
	 * Set injector for unit testing.
	 * 
	 * @param newInjector
	 *            new injector
	 */
	public static void setInjectorForTest(final ElectronicBattleMatInjector newInjector) {
		testInjector = newInjector;
	}
	/**
	 * Get injector if not doing unit testing.
	 * 
	 * @return true if doing unit tests
	 */
	private static Boolean getInjector() {
		if (testInjector == null && injector == null) {
			injector = GWT.create(ElectronicBattleMatGinInjector.class);
		}
		return (testInjector != null);
	}
	/**
	 * Get injector.
	 * 
	 * @return instance of injector
	 */
	public static ElectronicBattleMatGinInjector getElectronicBattleMatGinInjector() {
		return (injector);
	}
	/**
	 * Get instance of event manager.
	 * 
	 * @return Event Manager
	 */
	public static IEventManager getEventManager() {
		if (getInjector()) {
			return (testInjector.getEventManager());
		}
		return (injector.getEventManager());
	}
}
