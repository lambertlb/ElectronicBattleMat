package per.lambert.ebattleMat.client.interfaces;

import com.google.gwt.core.client.JavaScriptObject;

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
	void login(JavaScriptObject requestData, IUserCallback callback);

	/**
	 * Get current user data
	 * 
	 * @return user data
	 */
	DungeonData getDungeonData();

	/**
	 * Get token for current user
	 * 
	 * @return Token as string
	 */
	String getToken();
}
