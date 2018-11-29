package per.lambert.ebattleMat.client.services;

import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IDungeonManager;
import per.lambert.ebattleMat.client.interfaces.IEventManager;

public class ServiceManager {
	private static IEventManager eventManager;

	public static IEventManager getEventManager() {
		if (eventManager == null) {
			eventManager = new EventManager();
		}
		return (eventManager);
	}

	private static IDungeonManager dungeonManager;

	public static IDungeonManager getDungeonManager() {
		if (dungeonManager == null) {
			dungeonManager = new DungeonManager();
		}
		return (dungeonManager);
	}

	private static IDataRequester dataRequester;

	public static IDataRequester getDataRequester() {
		if (dataRequester == null) {
			dataRequester = new DataRequester();
		}
		return (dataRequester);
	}
}
