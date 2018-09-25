package per.lambert.ebattleMat.client.interfaces;

import java.util.List;

import per.lambert.ebattleMat.client.services.serviceData.DungeonData;
import per.lambert.ebattleMat.client.services.serviceData.ServiceRequestData;

/**
 * @author LLambert Interface to user management services
 */
public interface IDungeonManagement {
	/**
	 * Login
	 * 
	 * @param requestData
	 *            request data
	 * @param callback
	 *            callback
	 */
	void login(ServiceRequestData requestData, IUserCallback callback);

	DungeonServerError getLastError();

	List<String> getDungeonList();

	List<DungeonData> getDungeonData();

	void selectDungeon(String dungeonsName);
}
