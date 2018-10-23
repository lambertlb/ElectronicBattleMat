package per.lambert.ebattleMat.client.controls.LoginControl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginControl extends PopupPanel implements ILoginView
{
  interface MyStyle extends CssResource {
    String hidden();
    String borderEmpty();
    String borderOk();
    String borderError();
  }
 
  interface MyBinder extends UiBinder<Widget, LoginControl> {}
  private static MyBinder binder = GWT.create(MyBinder.class);

  @UiTemplate("LoginControl.ui.xml")
  interface MyAltBinder extends UiBinder<Widget, LoginControl> {}
  private static MyAltBinder altBinder = GWT.create(MyAltBinder.class);

  @UiField Button btnLogin;
  @UiField TextBox txtUserName;
  @UiField(provided = true) TextBox txtPassword;
  @UiField LabelElement labelUserName;
  @UiField LabelElement labelPassword;
  @UiField LabelElement loginError;
  @UiField HeadingElement headerTitle;
  @UiField MyStyle myStyle;
 
  private LoginPresenter presenter;

  public LoginControl() {
    this(false);
  }

  public LoginControl(boolean useAltLayout) {
    // using this to override the styles included in the theme.
    // if we turned off the theme we would not need this.
    setStyleName("");

    txtPassword = new PasswordTextBox();

    if (useAltLayout) {
      add(altBinder.createAndBindUi(this));
    }
    else {
      add(binder.createAndBindUi(this));
    }
    txtUserName.setFocus(true);
    this.setGlassEnabled(true);
    // we want the login form to appear on the center
    // of the page when it is displayed, and want it to
    // adjust if the browser is resized.
    centerPopupOnPage();
    Window.addResizeHandler(repositionOnResize);
  }

  @Override public void onLoad() {
	  super.onLoad();
	  setLocalizedStrings();  
	  //TODO - need to use dependency injection;
	  presenter = new LoginPresenter();
	  presenter.setView(this);
  }
  
  /**
 * This method sets all of the localized text for the widget. Note that it MUST
 * be called after the widget is loaded otherwise the controls will be null.
 */
void setLocalizedStrings() {
	  btnLogin.setText("OK");
	  labelUserName.setInnerText("UserName");
	  labelPassword.setInnerText("Password");
	  headerTitle.setInnerText("Title");
  }
 
  
  // *************  UTILITY FUNCTIONS *************** 
  // *
  // * These methods are used by other methods within the
  // * class.  Most of these are called by event handlers.
  // * 
  
  
  private void setBorderStyle(TextBox textBox, String styleName) {
    textBox.removeStyleName(myStyle.borderOk());
    textBox.removeStyleName(myStyle.borderError());
    textBox.removeStyleName(myStyle.borderEmpty());
    textBox.addStyleName(styleName);
  }

  
  // * *************  EVENT HANDLERS *************** 
  // *
  // * These are the handlers for events within the
  // * widget.  Things like focus/blur events and button
  // * clicks.
  // * 
  
  @UiHandler("txtUserName")
  void userNameChanged(ChangeEvent event) {
	  presenter.getRequestData().setUsername(txtUserName.getText());
  }

  @UiHandler("txtUserName")
  void emailHasFocus (FocusEvent event) {
    setBorderStyle(txtUserName, myStyle.borderOk());
  }
  
  @UiHandler("txtUserName")
  void emailBlur (BlurEvent event) {
//    if (validateEmail()) {
//      eEmailError.addClassName(myStyle.hidden());
//      setBorderStyle(txtEmail, myStyle.borderOk());
//    }
//    else {
//      eEmailErrorText.setInnerHTML("Email is not valid");
//      eEmailError.removeClassName(myStyle.hidden());
//      setBorderStyle(txtEmail, myStyle.borderError());
//    }
  }

  @UiHandler("txtPassword")
  void passwordChanged(ChangeEvent event) {
	  presenter.getRequestData().setPassword(txtPassword.getText());
  }
  
  @UiHandler("txtPassword")
  void passwordHasFocus (FocusEvent event) {
    setBorderStyle(txtPassword, myStyle.borderOk());
  }

  @UiHandler("txtPassword")
  void passwordBlur (BlurEvent event) {
//    if (validatePassword()) {
//      ePassError.addClassName(myStyle.hidden());
//      setBorderStyle(txtPassword, myStyle.borderOk());
//    }
//    else {
//      ePassErrorText.setInnerHTML("Password is not valid");
//      ePassError.removeClassName(myStyle.hidden());
//      setBorderStyle(txtPassword, myStyle.borderError());
//    }
  }

  @UiHandler("btnLogin")
  void login (ClickEvent event)
  {
	  presenter.ok();
  }

	@Override
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
		if(!message.isEmpty()) {
			loginError.getStyle().setProperty("visibility", "visible");		
		}
		else {
			loginError.getStyle().setProperty("visibility", "hidden");		
		}
	}
	
	@Override
	public void close() {
		hide();
	}

  
  // * *************  WIDGET CENTERING CODE *************** 
  // *
  // * These routines are used to keep the widget centered on
  // * the web page, even if the user resizes the browser window.
  // * This isn't covered in the book in the UIBuilder chapter
  // * because it is out of scope.  We included it just so that
  // * the widget would look nice if you ran the code :)
  // *  

  private ResizeHandler repositionOnResize = new ResizeHandler() {
    public void onResize(ResizeEvent event) {
      centerPopupOnPage();
    }
  };

  // **
  // * A position callback to position the dialog box in the center
  // * of the screen, but a little high.  If the browser area is too 
  // * small, we use a min left/top offset of 10px.
  // *
  private void centerPopupOnPage() {
    int minOffset = 10; //px
    int knownDialogWidth = 400; // this is in the CSS
    int heightAboveCenter = 200; // will set the top to 200px above center
    
    int left = Math.max(minOffset, (Window.getClientWidth() / 2) - (knownDialogWidth / 2));
    int top = Math.max(minOffset, (Window.getClientHeight() / 2) - heightAboveCenter);
    setPopupPosition(left, top); 
  }

}


