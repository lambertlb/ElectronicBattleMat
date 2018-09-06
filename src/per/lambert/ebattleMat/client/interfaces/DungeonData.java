package per.lambert.ebattleMat.client.interfaces;

public class DungeonData {
	String token;

	/**
	 * get token
	 * 
	 * @return token
	 */
	public String getToken() {
		return (token);
	}

	public void parse(String jsonData) {
	}

	int errorCode;

	public int getErrorCode() {
		return errorCode;
	}
}
