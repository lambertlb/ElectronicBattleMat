package per.lambert.ebattleMat.server.serviceData;

public class ServiceResponseData {
	int error;

	protected ServiceResponseData(int error) {
		this.error = error;
	}

	protected ServiceResponseData() {
		this.error = 0;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

}
