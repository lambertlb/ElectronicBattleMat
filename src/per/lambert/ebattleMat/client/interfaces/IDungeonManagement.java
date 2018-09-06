package per.lambert.ebattleMat.client.interfaces;

/**
 * @author LLambert Interface to user management services
 */
public interface IDungeonManagement {
	/**
	 * Login
	 * 
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @param callback
	 *            callback
	 */
	void login(String username, String password, IUserCallback callback);
	/**
	 * Get current user data
	 * @return user data
	 */
	DungeonData	getDungeonData();
	/**
	 * Get token for current user
	 * 
	 * @return Token as string
	 */
	String getToken();
}
