package per.lambert.ebattleMat.client.inject;

import per.lambert.ebattleMat.client.interfaces.IEventManager;

/**
 * This interface provides the mechanism for dependency injection of the implementing
 * interface classes. IMPORTANT: When a new getXXX method is defined in this interface
 * make sure to update the getAllObjects method in the InjectionManager to add
 * a call to the new getter.
 *
 */
public interface ElectronicBattleMatInjector {
	/**
	 * Get event manager.
	 * 
	 * @return Event Manager
	 */
	IEventManager getEventManager();

}
