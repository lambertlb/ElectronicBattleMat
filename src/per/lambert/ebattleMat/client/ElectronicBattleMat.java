package per.lambert.ebattleMat.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import per.lambert.ebattleMat.client.controls.DungeonSelectControl.DungeonSelectControl;
import per.lambert.ebattleMat.client.controls.LoginControl.LoginControl;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.maindisplay.ScalableImage;
import per.lambert.ebattleMat.client.maindisplay.ShellLayout;
import per.lambert.ebattleMat.client.services.ServiceManagement;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ElectronicBattleMat implements EntryPoint {
	private RootLayoutPanel rootLayoutPanel;
	private static SimpleLayoutPanel simplePanel = new SimpleLayoutPanel();
	private static ScalableImage scaleImage = new ScalableImage();
	int pictureCount = 1;

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
					scaleImage.setShowGrid(ServiceManagement.getDungeonManagment().getSelectedDungeon().getShowGrid());
					scaleImage.mainDraw();
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
		final ShellLayout layout = new ShellLayout();
		final SimplePanel hideImagePanel = new SimplePanel();
		final Image image = new Image();
		LoadImage(image);
		RootPanel loginPanel = RootPanel.get("loginControls");
		loginPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		loginPanel.add(errorLabel);
		loginPanel.add(hideImagePanel, 73, 70);
		hideImagePanel.setSize("10px", "10px");
		hideImagePanel.add(image);
		hideImagePanel.setVisible(false);
		setupDungeonView();
		layout.mainPanel.add(simplePanel);
		rootLayoutPanel.add(layout);
		simplePanel.clear();
		simplePanel.add(scaleImage);
	}

	private void LoadImage(final Image image) {
		DungeonLevel dungeonLevel = ServiceManagement.getDungeonManagment().getCurrentLevelData();
		String dungeonNameForUrl = ServiceManagement.getDungeonManagment().getDungeonNameForUrl();
		String dungeonPicture = dungeonLevel.getLevelDrawing();
		String imageUrl = "usr/dungeonData/" + dungeonNameForUrl + "/" + dungeonPicture + "?" + pictureCount++;
		image.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				scaleImage.setImage(image, simplePanel.getOffsetWidth(), simplePanel.getOffsetHeight(), 51, 10 , 10);
			}
		});
		image.setUrl(imageUrl);
	}

	private void setupDungeonView() {
	}
}
