package per.lambert.ebattleMat.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import per.lambert.ebattleMat.client.controls.DungeonSelectControl.DungeonSelectControl;
import per.lambert.ebattleMat.client.controls.LoginControl.LoginControl;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManagement;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ElectronicBattleMat implements EntryPoint {
	private RootLayoutPanel rootLayoutPanel;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		rootLayoutPanel = RootLayoutPanel.get();
		setupEventHandler();
		LoginControl lc = new LoginControl();
		lc.getElement().getStyle().setZIndex(400);
		lc.show();
	}

	private void setupEventHandler() {
		IEventManager eventManager = ServiceManagement.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.Login) {
					selectDungeon();
					startServices();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonSelected) {
					dungeonSelected();
					return;
				}
			}
		});
	}

	private void startServices() {
	}

	private void selectDungeon() {
		DungeonSelectControl dungeonSelectControl = new DungeonSelectControl();
		dungeonSelectControl.show();
	}
	
	/**
	 * Setup main display with selected dungeon
	 */
	private void dungeonSelected() {
		final Label errorLabel = new Label("Error Label");
		RootPanel loginPanel = RootPanel.get("loginControls");
		loginPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		loginPanel.add(errorLabel);
	}
}
