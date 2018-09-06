package per.lambert.ebattleMat.client.controls.LoginControl;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.IDungeonManagement;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.DungeonManagement;
import per.lambert.ebattleMat.client.services.ServiceManagement;

public class LoginPresenter {

	private ILoginView view;
	LoginModel model;

	public void setView(ILoginView view) {
		this.view = view;
		model = new LoginModel();
		view.setModel(model);
	}

	public void ok() {
		model.setIsEnabled(false);
		view.update();
		final IDungeonManagement dungeonManagement = ServiceManagement.getDungeonManagment();

		dungeonManagement.login(model.getUserName(), model.getPassword(), new IUserCallback() {
			public void onError(final Object sender, final IErrorInformation error) {
				loginComplete("Login Fail");
			}

			public void onSuccess(final Object sender, final Object data) {
				if (dungeonManagement.getDungeonData().getErrorCode() == 0) {
					view.close();
					ServiceManagement.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.Login, null));
				} else
					loginComplete("Login Fail");
			}
		});
	}

	void loginComplete(String message) {
		model.setMessage(message);
		model.setIsEnabled(true);
		view.update();
	}
}
