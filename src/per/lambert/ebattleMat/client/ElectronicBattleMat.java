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
	private RootLayoutPanel rootLayoutPanel;
	private BattleMatLayout layout;
	private DungeonSelectControl dungeonSelectControl;
	private Timer taskTimer;

	public static String DUNGEON_DATA_LOCATION = "dungeonData/";
	public static String DUNGEONS_FOLDER = "dungeons/";
	public static String SESSIONS_FOLDER = "/sessions/";
	public static String DUNGEONS_LOCATION = DUNGEON_DATA_LOCATION + DUNGEONS_FOLDER;
	public static String DUNGEON_RESOURCE_LOCATION = "resources/";
	public static String DUNGEON_PCPOG_LOCATION = DUNGEON_RESOURCE_LOCATION + "pcPogs/";
	public static String DUNGEON_MONSTER_LOCATION = DUNGEON_RESOURCE_LOCATION + "monsters/";
	public static String DUNGEON_ROOMOBJECT_LOCATION = DUNGEON_RESOURCE_LOCATION + "roomObjects/";
	public static String DUNGEON_DATA_FILENAME = "/dungeonData.json";
	public static String POG_TYPE_MONSTER = "MONSTER";
	public static String POG_TYPE_PLAYER = "PLAYER";
	public static String POG_TYPE_ROOMOBJECT = "ROOMOBJECT";

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

	private void setupEventHandler() {
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.Login) {
					selectDungeon();
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
					selectDungeon();
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

	private void selectDungeon() {
		if (dungeonSelectControl == null) {
			dungeonSelectControl = new DungeonSelectControl();
		}
		dungeonSelectControl.setupAndShow();
	}
}
