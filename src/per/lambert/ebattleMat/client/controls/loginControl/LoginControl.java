package per.lambert.ebattleMat.client.controls.loginControl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginControl extends PopupPanel {
	interface MyStyle extends CssResource {
		String sessionLabel();
	}

	interface MyBinder extends UiBinder<Widget, LoginControl> {
	}

	private static MyBinder binder = GWT.create(MyBinder.class);

	@UiField
	Button btnLogin;
	@UiField
	TextBox txtUserName;
	@UiField(provided = true)
	TextBox txtPassword;
	@UiField
	LabelElement labelUserName;
	@UiField
	LabelElement labelPassword;
	@UiField
	LabelElement loginError;
	@UiField
	HeadingElement headerTitle;

	private LoginPresenter presenter;

	public LoginControl() {
		setStyleName("");

		txtPassword = new PasswordTextBox();

		add(binder.createAndBindUi(this));
		txtUserName.setFocus(true);
		this.setGlassEnabled(true);
		center();
		Window.addResizeHandler(repositionOnResize);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		setLocalizedStrings();
		presenter = new LoginPresenter();
		presenter.setView(this);
	}

	/**
	 * This method sets all of the localized text for the widget. Note that it MUST be called after the widget is loaded otherwise the controls will be null.
	 */
	void setLocalizedStrings() {
		btnLogin.setText("OK");
		labelUserName.setInnerText("UserName");
		labelPassword.setInnerText("Password");
		headerTitle.setInnerText("Title");
	}

	@UiHandler("txtUserName")
	void userNameChanged(ChangeEvent event) {
		presenter.setUsername(txtUserName.getText());
	}

	@UiHandler("txtPassword")
	void passwordChanged(ChangeEvent event) {
		presenter.setPassword(txtPassword.getText());
	}

	@UiHandler("btnLogin")
	void login(ClickEvent event) {
		presenter.ok();
	}

	public void update() {
		displayMessage(presenter.getMessage());
		setEnable(presenter.getIsEnabled());
	}

	void setEnable(boolean enable) {
		btnLogin.setEnabled(enable);
		txtUserName.setEnabled(enable);
		txtPassword.setEnabled(enable);
	}

	void displayMessage(String message) {
		loginError.setInnerHTML(message);
		if (!message.isEmpty()) {
			loginError.getStyle().setProperty("visibility", "visible");
		} else {
			loginError.getStyle().setProperty("visibility", "hidden");
		}
	}

	public void close() {
		hide();
	}

	private ResizeHandler repositionOnResize = new ResizeHandler() {
		public void onResize(ResizeEvent event) {
			if (isShowing()) {
				center();
			}
		}
	};
}
