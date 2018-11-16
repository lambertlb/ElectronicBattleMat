package per.lambert.ebattleMat.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

import per.lambert.ebattleMat.client.controls.dungeonSelectControl.DungeonSelectControl;
import per.lambert.ebattleMat.client.controls.loginControl.LoginControl;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.maindisplay.ShellLayout;
import per.lambert.ebattleMat.client.services.ServiceManagement;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ElectronicBattleMat implements EntryPoint {
	private RootLayoutPanel rootLayoutPanel;
	ShellLayout layout;
	public static String DUNGEON_DATA_LOCATION = "dungeonData/";
	public static String DUNGEONS_FOLDER = "dungeons/";
	public static String DUNGEONS_LOCATION = DUNGEON_DATA_LOCATION + DUNGEONS_FOLDER;
	public static String DUNGEON_RESOURCE_LOCATION = "resources/";
	public static String DUNGEON_PCPOG_LOCATION = DUNGEON_RESOURCE_LOCATION + "pcPogs/";
	public static String DUNGEON_DATA_FILENAME = "/dungeonData.json";

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
				if (event.getReasonForAction() == ReasonForAction.DungeonDataChanged) {
					layout.dungeonDataChanged();
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
		layout = new ShellLayout();
		rootLayoutPanel.add(layout);
	}
}
