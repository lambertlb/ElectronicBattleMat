package per.lambert.ebattleMat.client.controls.LoginControl;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.DungeonServerError;
import per.lambert.ebattleMat.client.interfaces.IDungeonManagement;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManagement;

public class LoginPresenter {

	private ILoginView view;

	String message = "";

	public String getMessage() {
		return message;
	}

	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	boolean isEnabled;

	public boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setView(ILoginView view) {
		this.view = view;
	}

	public void ok() {
		setIsEnabled(false);
		view.update();
		final IDungeonManagement dungeonManagement = ServiceManagement.getDungeonManagment();

		dungeonManagement.login(username, password, new IUserCallback() {
			public void onError(final Object sender, final IErrorInformation error) {
				loginComplete("Login Fail");
			}

			public void onSuccess(final Object sender, final Object data) {
				if (dungeonManagement.getLastError() == DungeonServerError.Succsess) {
					view.close();
					ServiceManagement.getEventManager()
							.fireEvent(new ReasonForActionEvent(ReasonForAction.Login, null));
				} else
					loginComplete("Login Fail");
			}
		});
	}

	void loginComplete(String message) {
		setMessage(message);
		setIsEnabled(true);
		view.update();
	}
}
