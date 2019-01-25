package per.lambert.ebattleMat.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import per.lambert.ebattleMat.client.battleMatDisplay.BattleMatLayout;
import per.lambert.ebattleMat.client.controls.dungeonSelectControl.DungeonSelectControl;
import per.lambert.ebattleMat.client.controls.loginControl.LoginControl;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ElectronicBattleMat implements EntryPoint {
	/**
	 * Root layout panel.
	 */
	private RootLayoutPanel rootLayoutPanel;
	/**
	 * layout panel for battle mat.
	 */
	private BattleMatLayout layout;
	/**
	 * dungeon select control.
	 */
	private DungeonSelectControl dungeonSelectControl;
	/**
	 * timer to run tasks periodically.
	 */
	private Timer taskTimer;

	/**
	 * Location of dungeon data.
	 */
	public static final String DUNGEON_DATA_LOCATION = "dungeonData/";
	/**
	 * sub-folder with dungeons in it.
	 */
	public static final String DUNGEONS_FOLDER = "dungeons/";
	/**
	 * sub-folder with session data in it.
	 */
	public static final String SESSIONS_FOLDER = "/sessions/";
	/**
	 * full path to dungeons folder.
	 */
	public static final String DUNGEONS_LOCATION = DUNGEON_DATA_LOCATION + DUNGEONS_FOLDER;
	/**
	 * sub-folder for resources.
	 */
	public static final String DUNGEON_RESOURCE_LOCATION = "resources/";
	/**
	 * full path to resources folder.
	 */
	public static final String RESOURCE_LOCATION = DUNGEON_DATA_LOCATION + DUNGEON_RESOURCE_LOCATION;
	/**
	 * sub-folder for monster resources.
	 */
	public static final String DUNGEON_MONSTER_LOCATION = DUNGEON_RESOURCE_LOCATION + "monsters/";
	/**
	 * sub-folder for room object resources.
	 */
	public static final String DUNGEON_ROOMOBJECT_LOCATION = DUNGEON_RESOURCE_LOCATION + "roomObjects/";
	/**
	 * Monster type pog.
	 */
	public static final String POG_TYPE_MONSTER = "MONSTER";
	/**
	 * Player type pog.
	 */
	public static final String POG_TYPE_PLAYER = "PLAYER";
	/**
	 * Room Object type pog.
	 */
	public static final String POG_TYPE_ROOMOBJECT = "ROOMOBJECT";

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		rootLayoutPanel = RootLayoutPanel.get();
		setupEventHandler();
		layout = new BattleMatLayout();
		rootLayoutPanel.add(layout);
		LoginControl lc = new LoginControl();
		lc.getElement().getStyle().setZIndex(400);
		lc.show();

		taskTimer = new Timer() {
			@Override
			public void run() {
				ServiceManager.getDungeonManager().doTimedTasks();
			}
		};

		taskTimer.scheduleRepeating(1000);
	}

	/**
	 * Setup event handlers.
	 */
	private void setupEventHandler() {
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.Login) {
					showDungeonManagerDialog();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonDataReadyToEdit) {
					layout.dungeonDataChanged();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonDataReadyToJoin) {
					layout.dungeonDataChanged();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.SelectNewDungeon) {
					showDungeonManagerDialog();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.SessionDataChanged) {
					layout.dungeonDataUpdated();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonDataSaved) {
					layout.dungeonDataUpdated();
					return;
				}
			}
		});
	}

	/**
	 * Show dungeon manager dialog.
	 */
	private void showDungeonManagerDialog() {
		if (dungeonSelectControl == null) {
			dungeonSelectControl = new DungeonSelectControl();
			dungeonSelectControl.enableCancel(false);
		}
		dungeonSelectControl.show();
	}
}
