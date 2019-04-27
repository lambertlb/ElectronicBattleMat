package per.lambert.ebattleMat.client.controls.loginControl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Control for handling login.
 * 
 * @author LLambert
 *
 */
public class LoginControl extends PopupPanel {
	/**
	 * interface to binder.
	 * 
	 * @author LLambert
	 *
	 */
	interface MyBinder extends UiBinder<Widget, LoginControl> {
	}

	/**
	 * UI binder.
	 */
	private static MyBinder binder = GWT.create(MyBinder.class);

	/**
	 * Login button.
	 */
	@SuppressWarnings("VisibilityModifier")
	@UiField
	Button btnLogin;
	/**
	 * User name.
	 */
	@SuppressWarnings("VisibilityModifier")
	@UiField
	TextBox txtUserName;
	/**
	 * Password.
	 */
	@SuppressWarnings("VisibilityModifier")
	@UiField(provided = true)
	PasswordTextBox txtPassword;
	/**
	 * label for user name.
	 */
	@SuppressWarnings("VisibilityModifier")
	@UiField
	LabelElement labelUserName;
	/**
	 * Label for password.
	 */
	@SuppressWarnings("VisibilityModifier")
	@UiField
	LabelElement labelPassword;
	/**
	 * Label for errors.
	 */
	@SuppressWarnings("VisibilityModifier")
	@UiField
	LabelElement loginError;
	/**
	 * title.
	 */
	@SuppressWarnings("VisibilityModifier")
	@UiField
	HeadingElement headerTitle;
	/**
	 * Presenter for view.
	 */
	private LoginPresenter presenter;

	/**
	 * Constructor for login control.
	 */
	public LoginControl() {
		setStyleName("");
		this.setGlassEnabled(true);
		txtPassword = new PasswordTextBox();
		add(binder.createAndBindUi(this));
		center();
		Window.addResizeHandler(repositionOnResize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onLoad() {
		super.onLoad();
		setLocalizedStrings();
		presenter = new LoginPresenter();
		presenter.setView(this);
		txtUserName.setFocus(true);
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

	/**
	 * User name has changed.
	 * @param event data
	 */
	@SuppressWarnings("VisibilityModifier")
	@UiHandler("txtUserName")
	void userNameChanged(final ChangeEvent event) {
		presenter.setUsername(txtUserName.getText());
	}

	/**
	 * Pasword changed.
	 * @param event data.
	 */
	@SuppressWarnings("VisibilityModifier")
	@UiHandler("txtPassword")
	void passwordChanged(final ChangeEvent event) {
		presenter.setPassword(txtPassword.getText());
	}

	/**
	 * Ok button pressed.
	 * @param event data
	 */
	@SuppressWarnings("VisibilityModifier")
	@UiHandler("btnLogin")
	void login(final ClickEvent event) {
		presenter.ok();
	}

	/**
	 * Update view.
	 */
	public void update() {
		displayMessage(presenter.getMessage());
		setEnable(presenter.getIsEnabled());
	}

	/**
	 * Enable or disable controls.
	 * @param enable data
	 */
	void setEnable(final boolean enable) {
		btnLogin.setEnabled(enable);
		txtUserName.setEnabled(enable);
		txtPassword.setEnabled(enable);
	}

	/**
	 * display a message.
	 * @param message to display
	 */
	void displayMessage(final String message) {
		loginError.setInnerHTML(message);
		if (!message.isEmpty()) {
			loginError.getStyle().setProperty("visibility", "visible");
		} else {
			loginError.getStyle().setProperty("visibility", "hidden");
		}
	}

	/**
	 * Close view.
	 */
	public void close() {
		hide();
	}

	/**
	 * add re-size handler.
	 */
	private ResizeHandler repositionOnResize = new ResizeHandler() {
		public void onResize(final ResizeEvent event) {
			if (isShowing()) {
				center();
			}
		}
	};
}
