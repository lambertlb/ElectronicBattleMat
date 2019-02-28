package per.lambert.ebattleMat.client.services;

import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IDungeonManager;
import per.lambert.ebattleMat.client.interfaces.IEventManager;

/**
 * Service manager.
 * 
 * Used to gain access to all available services.
 * @author LLambert
 *
 */
public final class ServiceManager {
	/**
	 * Event manager.
	 */
	private static IEventManager eventManager;

	/**
	 * get instance of event manager.
	 * @return event manager
	 */
	public static IEventManager getEventManager() {
		if (eventManager == null) {
			eventManager = new EventManager();
		}
		return (eventManager);
	}

	/**
	 * dungeon manager.
	 */
	private static IDungeonManager dungeonManager;

	/**
	 * Get instance of dungeon manager.
	 * @return dungeon manager.
	 */
	public static IDungeonManager getDungeonManager() {
		if (dungeonManager == null) {
			dungeonManager = new DungeonManager();
		}
		return (dungeonManager);
	}

	/**
	 * data requester.
	 */
	private static IDataRequester dataRequester;

	/**
	 * get instance of data requester.
	 * @return data requester.
	 */
	public static IDataRequester getDataRequester() {
		if (dataRequester == null) {
			dataRequester = new DataRequester();
		}
		return (dataRequester);
	}
	
	/**
	 * Constructor.
	 */
	private ServiceManager() {
		
	}
}