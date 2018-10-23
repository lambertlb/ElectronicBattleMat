package per.lambert.ebattleMat.client.interfaces;

/**
 * @author LLambert
 * Main interface for error information
 */
public interface IErrorInformation {
	/**
	 * Get any exception thrown while executing
	 * @return exception if there was one else null
	 */
	Throwable	getException();
	/**
	 * Get error from execution
	 * @return GatewayErrors error
	 */
	DungeonServerError getError();
	
	String getErrorMessage();
}
