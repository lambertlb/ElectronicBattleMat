package per.lambert.ebattleMat.client.controls.ribbonBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.controls.CharacterCreateDialog;
import per.lambert.ebattleMat.client.controls.LevelOptionsDialog;
import per.lambert.ebattleMat.client.controls.MonsterManageDialog;
import per.lambert.ebattleMat.client.controls.dungeonSelectDialog.DungeonSelectDialog;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.DungeonSessionData;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

/**
 * Ribbon bar on top of view.
 * 
 * This breaks down into columns and rows of bar items.
 * 
 * @author LLambert
 *
 */
public class RibbonBar extends Composite {

	/**
	 * UI Binder.
	 */
	private static RibbonBarUiBinder uiBinder = GWT.create(RibbonBarUiBinder.class);

	/**
	 * Interface for ui binder.
	 * 
	 * @author LLambert
	 *
	 */
	interface RibbonBarUiBinder extends UiBinder<Widget, RibbonBar> {
	}

	/**
	 * Panel for bar items.
	 */
	@SuppressWarnings("VisibilityModifier")
	@UiField
	HorizontalPanel panel;
	/**
	 * Grid to hold bar items.
	 */
	private Grid ribbonGrid;
	/**
	 * Control to show selected pog.
	 */
	private SelectedPog selectedPog;
	/**
	 * Fog of war toggle.
	 */
	private CheckBox fowToggle;
	/**
	 * List of levels to select.
	 */
	private ListBox levelSelect;
	/**
	 * Show option for level.
	 */
	private Button levelOptions;
	/**
	 * Dungeon selection dialog button.
	 */
	private Button manageDungeonsButton;
	/**
	 * Dialog for level options.
	 */
	private LevelOptionsDialog levelOptionsDialog;
	/**
	 * Manage dungeon dialog.
	 */
	private DungeonSelectDialog manageDungeons;
	/**
	 * List of character to select.
	 */
	private ListBox characterSelect;
	/**
	 * Create character button.
	 */
	private Button createCharacter;
	/**
	 * character create dialog.
	 */
	private CharacterCreateDialog characterCreate;
	/**
	 * Monster manager button.
	 */
	private Button monsterManageButton;
	/**
	 * Monster manage dialog.
	 */
	private MonsterManageDialog monsterManage;

	/**
	 * Constructor.
	 */
	public RibbonBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onLoad() {
		super.onLoad();
		createControls();
		setupEventHandler();
		setupView();
	}

	/**
	 * Create controls.
	 */
	private void createControls() {
		createCommonControls();
		createDMControls();
		createPlayerControls();
	}

	/**
	 * Create DM controls.
	 */
	private void createDMControls() {
		fowToggle = new CheckBox("Toggle FOW");
		fowToggle.addStyleName("ribbonBarLabel");
		fowToggle.getElement().getStyle().setBackgroundColor("white");
		fowToggle.setTitle("Toggle Fog of War");
		fowToggle.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				ServiceManager.getDungeonManager().setFowToggle(fowToggle.getValue());
			}
		});
		levelOptions = new Button("Level Options");
		levelOptions.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				if (levelOptionsDialog == null) {
					levelOptionsDialog = new LevelOptionsDialog();
				}
				levelOptionsDialog.show();
			}
		});
		levelOptions.addStyleName("ribbonBarLabel");
		manageDungeonsButton = new Button("Manage Dungeons");
		manageDungeonsButton.addStyleName("ribbonBarLabel");
		manageDungeonsButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				if (manageDungeons == null) {
					manageDungeons = new DungeonSelectDialog();
				}
				manageDungeons.enableCancel(true);
				manageDungeons.show();
			}
		});
	}

	/**
	 * Create cpommon controls.
	 */
	private void createCommonControls() {
		ribbonGrid = new Grid();
		ribbonGrid.resize(2, 10);
		ribbonGrid.setCellPadding(0);
		ribbonGrid.setCellSpacing(0);
		ribbonGrid.addStyleName("ribbonBarLabel");
		selectedPog = new SelectedPog();
		levelSelect = new ListBox();
		levelSelect.setVisibleItemCount(1);
		levelSelect.addStyleName("ribbonBarLabel");
		levelSelect.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(final ChangeEvent event) {
				ServiceManager.getDungeonManager().setCurrentLevel(levelSelect.getSelectedIndex());
			}
		});
		characterSelect = new ListBox();
		characterSelect.setVisibleItemCount(1);
		characterSelect.addStyleName("ribbonBarLabel");
		characterSelect.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(final ChangeEvent event) {
				characterWasSelected();
			}
		});
		createCharacter = new Button("Create Character");
		createCharacter.addStyleName("ribbonBarLabel");
		createCharacter.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				characterCreate.show();
			}
		});
		characterCreate = new CharacterCreateDialog();
		monsterManageButton = new Button("Manage Monster");
		monsterManageButton.addStyleName("ribbonBarLabel");
		monsterManageButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				monsterManage.show();
			}
		});
		monsterManage = new MonsterManageDialog();
	}

	/**
	 * Setup event handlers.
	 */
	private void setupEventHandler() {
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.DMStateChange) {
					setupView();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonDataLoaded) {
					dungeonDataLoaded();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonDataSaved) {
					dungeonDataLoaded();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.SessionDataChanged) {
					characterPogsLoaded();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonDataReadyToJoin) {
					characterPogsLoaded();
					return;
				}
			}
		});
	}

	/**
	 * Setup view.
	 */
	protected void setupView() {
		setupViewCommon();
		if (ServiceManager.getDungeonManager().isDungeonMaster()) {
			setupForDungeonMaster();
		} else {
			setupForPlayer();
		}
	}

	/**
	 * Setup common portion of view.
	 * 
	 * These are items the same between player and DM.
	 */
	private void setupViewCommon() {
		panel.clear();
		ribbonGrid.clear();
		panel.getElement().getStyle().setBackgroundColor("grey");
		panel.add(selectedPog);
		panel.add(ribbonGrid);
	}

	/**
	 * Setup for dungeon master.
	 */
	private void setupForDungeonMaster() {
		if (ServiceManager.getDungeonManager().isEditMode()) {
			setupForEditDungeon();
			return;
		}
		setupForSession();
		ribbonGrid.setWidget(0, 2, fowToggle);
	}

	/**
	 * Setup for editing dungeons.
	 */
	private void setupForEditDungeon() {
		ribbonGrid.setWidget(0, 0, levelSelect);
		ribbonGrid.setWidget(0, 1, levelOptions);
		ribbonGrid.setWidget(1, 0, manageDungeonsButton);
		ribbonGrid.setWidget(1, 1, monsterManageButton);
	}

	/**
	 * Setyp session section.
	 */
	private void setupForSession() {
		ribbonGrid.setWidget(0, 0, levelSelect);
		ribbonGrid.setWidget(1, 0, manageDungeonsButton);
		ribbonGrid.setWidget(0, 1, characterSelect);
		ribbonGrid.setWidget(1, 1, createCharacter);
	}

	/**
	 * Setup for player.
	 */
	private void setupForPlayer() {
		setupForSession();
	}

	/**
	 * Dungeon data loaded.
	 */
	private void dungeonDataLoaded() {
		levelSelect.clear();
		String[] levelNames = ServiceManager.getDungeonManager().getDungeonLevelNames();
		for (String levelName : levelNames) {
			levelSelect.addItem(levelName);
		}
	}

	/**
	 * Character pogs loaded.
	 */
	private void characterPogsLoaded() {
		characterSelect.clear();
		characterSelect.addItem("Select Character Pog", "");
		DungeonSessionData sessionData = ServiceManager.getDungeonManager().getSelectedSession();
		if (sessionData == null) {
			return;
		}
		PogData[] pogList = sessionData.getPlayers().getPogList();
		for (PogData pogData : pogList) {
			characterSelect.addItem(pogData.getPogName(), pogData.getUUID());
		}
	}

	/**
	 * Character was selected.
	 */
	private void characterWasSelected() {
		String uuid = characterSelect.getSelectedValue();
		if (uuid == null || uuid.isEmpty()) {
			return;
		}
		PogData characterPog = ServiceManager.getDungeonManager().findCharacterPog(uuid);
		if (characterPog != null) {
			ServiceManager.getDungeonManager().setSelectedPog(characterPog);
		}
	}

	/**
	 * Create player controls.
	 */
	private void createPlayerControls() {
	}

}
