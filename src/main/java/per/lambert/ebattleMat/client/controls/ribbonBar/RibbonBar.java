package per.lambert.ebattleMat.client.controls.ribbonBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.controls.CharacterCreateDialog;
import per.lambert.ebattleMat.client.controls.FlagBitsDialog;
import per.lambert.ebattleMat.client.controls.LevelOptionsDialog;
import per.lambert.ebattleMat.client.controls.NotesFloatingWindow;
import per.lambert.ebattleMat.client.controls.SelectedPogFloatingWindow;
import per.lambert.ebattleMat.client.controls.TemplateManageDialog;
import per.lambert.ebattleMat.client.controls.dungeonSelectDialog.DungeonSelectDialog;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.Constants;
import per.lambert.ebattleMat.client.interfaces.DungeonMasterFlag;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.PlayerFlag;
import per.lambert.ebattleMat.client.interfaces.PogPlace;
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
	private TemplateManageDialog monsterManage;
	/**
	 * Room objects manager button.
	 */
	private Button roomObjectsManageButton;
	/**
	 * Monster manage dialog.
	 */
	private TemplateManageDialog roomObjectsManage;
	/**
	 * Button for player flags.
	 */
	private Button playerFlagsButton;
	/**
	 * Dialog for player flags.
	 */
	private FlagBitsDialog playerFlagDialog;
	/**
	 * Button for DM flags.
	 */
	private Button dmFlagsButton;
	/**
	 * Dialog for DM flags.
	 */
	private FlagBitsDialog dmFlagDialog;
	/**
	 * show selected pog.
	 */
	private CheckBox showSelectedPog;
	/**
	 * Selected pog floating window.
	 */
	private SelectedPogFloatingWindow pogWindow;
	/**
	 * show selected pog.
	 */
	private CheckBox showPogNotes;
	/**
	 * Selected pog notes floating window.
	 */
	private NotesFloatingWindow pogNotes;
	/**
	 * Link to help.
	 */
	private Anchor helpLink;

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
		helpLink = new Anchor("Help", "/help.html", "_blank");
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
		selectedPog = new SelectedPog(panel);
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

		monsterManageButton = new Button("Monster Editor...");
		monsterManageButton.addStyleName("ribbonBarLabel");
		monsterManageButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				monsterManage.show();
			}
		});
		monsterManage = new TemplateManageDialog(PogPlace.COMMON_RESOURCE, Constants.POG_TYPE_MONSTER);

		roomObjectsManageButton = new Button("Room Object Editor...");
		roomObjectsManageButton.addStyleName("ribbonBarLabel");
		roomObjectsManageButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				roomObjectsManage.show();
			}
		});
		roomObjectsManage = new TemplateManageDialog(PogPlace.COMMON_RESOURCE, Constants.POG_TYPE_ROOMOBJECT);

		playerFlagsButton = new Button("Player Controlled Properties...");
		playerFlagsButton.setStyleName("ribbonBarLabel");
		playerFlagsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				playerFlagDialog.setBits(ServiceManager.getDungeonManager().getSelectedPog().getPlayerFlags());
				playerFlagDialog.show();
			}
		});

		playerFlagDialog = new FlagBitsDialog("Player Controlled Properties", PlayerFlag.getValues());
		playerFlagDialog.addOkClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				PogData selectedPog = ServiceManager.getDungeonManager().getSelectedPog();
				selectedPog.setPlayerFlagsNative(playerFlagDialog.getBits());
				ServiceManager.getDungeonManager().addOrUpdatePog(selectedPog);
			}
		});

		dmFlagsButton = new Button("DM Controlled Properties..");
		dmFlagsButton.setStyleName("ribbonBarLabel");
		dmFlagsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				dmFlagDialog.setBits(ServiceManager.getDungeonManager().getSelectedPog().getDungeonMasterFlags());
				dmFlagDialog.show();
			}
		});

		dmFlagDialog = new FlagBitsDialog("Dungeon Master Controlled Properties", DungeonMasterFlag.getValues());
		dmFlagDialog.addOkClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				PogData selectedPog = ServiceManager.getDungeonManager().getSelectedPog();
				selectedPog.setDungeonMasterFlagsNative(dmFlagDialog.getBits());
				ServiceManager.getDungeonManager().addOrUpdatePog(selectedPog);
			}
		});
		
		showSelectedPog = new CheckBox("Show Selected Pog");
		showSelectedPog.setStyleName("ribbonBarLabel");
		showSelectedPog.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				if (showSelectedPog.getValue()) {
					pogWindow.show();
				} else {
					pogWindow.hide();
				}
			}
		});
		pogWindow = new SelectedPogFloatingWindow();

		showPogNotes = new CheckBox("Show Pog Notes");
		showPogNotes.setStyleName("ribbonBarLabel");
		showPogNotes.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				if (showPogNotes.getValue()) {
					pogNotes.show();
				} else {
					pogNotes.hide();
				}
			}
		});
		pogNotes = new NotesFloatingWindow();
		pogSelection();
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
				if (event.getReasonForAction() == ReasonForAction.PogWasSelected) {
					pogSelection();
					return;
				}
			}
		});
	}

	/**
	 * New pog was selected so adjust controls.
	 */
	protected void pogSelection() {
		PogData selectedPog = ServiceManager.getDungeonManager().getSelectedPog();
		boolean enabled = selectedPog != null ? !ServiceManager.getDungeonManager().isTemplate(selectedPog) : false;
		DungeonSelectDialog.enableWidget(playerFlagsButton, enabled);
		DungeonSelectDialog.enableWidget(dmFlagsButton, enabled);
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
		setupForCommonDMControls();
		if (ServiceManager.getDungeonManager().isEditMode()) {
			ribbonGrid.setWidget(0, 5, helpLink);
		} else {
			ribbonGrid.setWidget(1, 4, fowToggle);
			ribbonGrid.setWidget(0, 6, characterSelect);
			ribbonGrid.setWidget(1, 6, createCharacter);
			ribbonGrid.setWidget(0, 7, helpLink);
		}
	}

	/**
	 * Setup controls for all DM modes.
	 */
	private void setupForCommonDMControls() {
		ribbonGrid.setWidget(0, 0, levelSelect);
		ribbonGrid.setWidget(1, 0, levelOptions);
		ribbonGrid.setWidget(0, 1, roomObjectsManageButton);
		ribbonGrid.setWidget(1, 1, monsterManageButton);
		ribbonGrid.setWidget(0, 2, playerFlagsButton);
		ribbonGrid.setWidget(1, 2, dmFlagsButton);
		ribbonGrid.setWidget(0, 3, showSelectedPog);
		ribbonGrid.setWidget(1, 3, showPogNotes);
		ribbonGrid.setWidget(0, 4, manageDungeonsButton);
	}

	/**
	 * Setup for player.
	 */
	private void setupForPlayer() {
		ribbonGrid.setWidget(0, 0, levelSelect);
		ribbonGrid.setWidget(1, 0, playerFlagsButton);
		ribbonGrid.setWidget(0, 1, characterSelect);
		ribbonGrid.setWidget(1, 1, createCharacter);
		ribbonGrid.setWidget(0, 2, showSelectedPog);
		ribbonGrid.setWidget(1, 2, showPogNotes);
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
		levelSelect.setSelectedIndex(ServiceManager.getDungeonManager().getCurrentLevelIndex());
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
			characterSelect.addItem(pogData.getName(), pogData.getUUID());
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
}
