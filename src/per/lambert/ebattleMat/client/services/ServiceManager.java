package per.lambert.ebattleMat.client.services;

import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IDungeonManagement;
import per.lambert.ebattleMat.client.interfaces.IEventManager;

public class ServiceManager {
	private static IEventManager eventManager;

	public static IEventManager getEventManager() {
		if (eventManager == null) {
			eventManager = new EventManager();
		}
		return (eventManager);
	}

	private static IDungeonManagement dungeonManagement;

	public static IDungeonManagement getDungeonManagment() {
		if (dungeonManagement == null) {
			dungeonManagement = new DungeonManager();
		}
		return (dungeonManagement);
	}

	private static IDataRequester dataRequester;

	public static IDataRequester getDataRequester() {
		if (dataRequester == null) {
			dataRequester = new DataRequester();
		}
		return (dataRequester);
	}
}
