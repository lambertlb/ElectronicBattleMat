package per.lambert.ebattleMat.server;

public class ServiceRequestData {

	private String serviceRequest;

	protected ServiceRequestData() {
	}

	public final void setServiceRequest(String serviceRequest) {
		this.serviceRequest = serviceRequest;
	};

	public final String getServiceRequest() {
		return this.serviceRequest;
	};

	private int token;

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}
}
