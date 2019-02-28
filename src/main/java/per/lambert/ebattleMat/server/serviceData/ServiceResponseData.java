package per.lambert.ebattleMat.server.serviceData;

/**
 * Response data for service request.
 * @author LLambert
 *
 */
public class ServiceResponseData {
	/**
	 * Error if there was one.
	 */
	private int error;

	/**
	 * Constructor.
	 * @param error response error.
	 */
	protected ServiceResponseData(final int error) {
		this.error = error;
	}

	/**
	 * Constructor.
	 */
	protected ServiceResponseData() {
		this.error = 0;
	}

	/**
	 * Get error.
	 * @return error
	 */
	public int getError() {
		return error;
	}

	/**
	 * Set error.
	 * @param error to set
	 */
	public void setError(final int error) {
		this.error = error;
	}

}
