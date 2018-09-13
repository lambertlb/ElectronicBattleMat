package per.lambert.ebattleMat.server;

public class ServiceRequestData {

	private String serviceRequest;
	protected ServiceRequestData() {
	}

	public final void setServiceRequest(String serviceRequest) {
		this.serviceRequest = serviceRequest;
	};

	public final  String getServiceRequest() {
		return this.serviceRequest;
	};
}
