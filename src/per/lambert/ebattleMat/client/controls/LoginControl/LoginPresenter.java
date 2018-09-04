package per.lambert.ebattleMat.client.controls.LoginControl;

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
/*		final IUserManagement userManagement = InjectionManager.getUserManagement();
		userManagement.login(model.getUserName(), model.getPassword(), new IUserCallback() {
					public void onError(final Object sender,
							final IErrorInformation error) {
						loginComplete(InjectionManager.getLocalizeManager().getString("login_Fail"));
					}

					public void onSuccess(final Object sender,
							final Object data) {
						if(userManagement.getCurrentUser().getErrorCode() == 0) {
							view.close();
							InjectionManager.getEventManager().fireEvent
								(new ReasonForActionEvent(ReasonForAction.Login, null));
						}
						else
							loginComplete(InjectionManager.getLocalizeManager().getString("login_Fail"));
					}
				});
*/	}

	void loginComplete(String message) {
		model.setMessage(message);
		model.setIsEnabled(true);
		view.update();
	}
}
