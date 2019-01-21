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
import per.lambert.ebattleMat.client.controls.LevelOptionsControl;
import per.lambert.ebattleMat.client.controls.MonsterManageDialog;
import per.lambert.ebattleMat.client.controls.dungeonSelectControl.DungeonSelectControl;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.DungeonSessionData;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

public class RibbonBar extends Composite {

	private static RibbonBarUiBinder uiBinder = GWT.create(RibbonBarUiBinder.class);

	interface RibbonBarUiBinder extends UiBinder<Widget, RibbonBar> {
	}

	@UiField
	HorizontalPanel panel;
	private Grid ribbonGrid;
	private SelectedPog selectedPog;
	private CheckBox fowToggle;
	private ListBox levelSelect;
	private Button levelOptions;
	private Button manageDungeonsButton;
	private LevelOptionsControl levelOptionsControl;
	private DungeonSelectControl manageDungeons;
	private ListBox characterSelect;
	private Button createCharacter;
	private CharacterCreateDialog characterCreate;
	private Button monsterManageButton;
	private MonsterManageDialog monsterManage;

	public RibbonBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		createControls();
		setupEventHandler();
		setupView();
	}

	private void createControls() {
		createCommonControls();
		createDMControls();
		createPlayerControls();
	}

	private void createPlayerControls() {
	}

	private void createDMControls() {
		fowToggle = new CheckBox("Toggle FOW");
		fowToggle.addStyleName("ribbonBarLabel");
		fowToggle.getElement().getStyle().setBackgroundColor("white");
		fowToggle.setTitle("Toggle Fog of War");
		fowToggle.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ServiceManager.getDungeonManager().setFowToggle(fowToggle.getValue());
			}
		});
		levelOptions = new Button("Level Options");
		levelOptions.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (levelOptionsControl == null) {
					levelOptionsControl = new LevelOptionsControl();
				}
				levelOptionsControl.show();
			}
		});
		levelOptions.addStyleName("ribbonBarLabel");
		manageDungeonsButton = new Button("Manage Dungeons");
		manageDungeonsButton.addStyleName("ribbonBarLabel");
		manageDungeonsButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (manageDungeons == null) {
					manageDungeons = new DungeonSelectControl();
				}
				manageDungeons.enableCancel(true);
				manageDungeons.show();
			}
		});
	}

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
			public void onChange(ChangeEvent event) {
				ServiceManager.getDungeonManager().setCurrentLevel(levelSelect.getSelectedIndex());
			}
		});
		characterSelect = new ListBox();
		characterSelect.setVisibleItemCount(1);
		characterSelect.addStyleName("ribbonBarLabel");
		characterSelect.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				characterWasSelected();
			}
		});
		createCharacter = new Button("Create Character");
		createCharacter.addStyleName("ribbonBarLabel");
		createCharacter.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				characterCreate.show();
			}
		});
		characterCreate = new CharacterCreateDialog();
		monsterManageButton = new Button("Manage Monster");
		monsterManageButton.addStyleName("ribbonBarLabel");
		monsterManageButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				monsterManage.show();
			}
		});
		monsterManage = new MonsterManageDialog();
	}

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

	protected void setupView() {
		setupViewCommon();
		if (ServiceManager.getDungeonManager().isDungeonMaster()) {
			setupForDungeonMaster();
		} else {
			setupForPlayer();
		}
	}

	private void setupViewCommon() {
		panel.clear();
		ribbonGrid.clear();
		panel.getElement().getStyle().setBackgroundColor("grey");
		panel.add(selectedPog);
		panel.add(ribbonGrid);
	}

	private void setupForDungeonMaster() {
		if (ServiceManager.getDungeonManager().isEditMode()) {
			setupForEditDungeon();
			return;
		}
		setupForSession();
		ribbonGrid.setWidget(0, 2, fowToggle);
	}

	private void setupForEditDungeon() {
		ribbonGrid.setWidget(0, 0, levelSelect);
		ribbonGrid.setWidget(0, 1, levelOptions);
		ribbonGrid.setWidget(1, 0, manageDungeonsButton);
		ribbonGrid.setWidget(1, 1, monsterManageButton);
	}

	private void setupForSession() {
		ribbonGrid.setWidget(0, 0, levelSelect);
		ribbonGrid.setWidget(1, 0, manageDungeonsButton);
		ribbonGrid.setWidget(0, 1, characterSelect);
		ribbonGrid.setWidget(1, 1, createCharacter);
	}

	private void setupForPlayer() {
		setupForSession();
	}

	private void dungeonDataLoaded() {
		levelSelect.clear();
		String[] levelNames = ServiceManager.getDungeonManager().getDungeonLevelNames();
		for (String levelName : levelNames) {
			levelSelect.addItem(levelName);
		}
	}

	private void characterPogsLoaded() {
		characterSelect.clear();
		characterSelect.addItem("Select Character Pog", "");
		DungeonSessionData sessionData = ServiceManager.getDungeonManager().getSelectedSession();
		if (sessionData == null) {
			return;
		}
		PogData[] pogList = sessionData.getPlayers();
		for (PogData pogData : pogList) {
			characterSelect.addItem(pogData.getPogName(), pogData.getUUID());
		}
	}

	private void characterWasSelected() {
		String uuid = characterSelect.getSelectedValue();
		if (uuid == null || uuid.isEmpty()) {
			return;
		}
		PogData characterPog = ServiceManager.getDungeonManager().findCharacterPog(uuid);
		if (characterPog != null) {
			ServiceManager.getDungeonManager().setSelectedPog(characterPog);
			ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.PogWasSelected, null));
		}
	}
}
