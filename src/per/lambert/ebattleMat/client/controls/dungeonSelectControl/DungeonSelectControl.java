package per.lambert.ebattleMat.client.controls.dungeonSelectControl;

import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.battleMatDisplay.BattleMatCanvas;
import per.lambert.ebattleMat.client.controls.okCancelDialog.OkCancelDialog;
import per.lambert.ebattleMat.client.services.serviceData.SessionListData;

public class DungeonSelectControl extends OkCancelDialog {
	private DungeonSelectPresenter dungeonSelectPresenter;
	private ListBox dungeonDropdownList;
	private ListBox sessionDropdownList;
	private Button editDungeonButton;
	private Button deleteDungeonButton;
	private Button deleteSessionButton;
	private Button createDungeonButton;
	private Button createSessionButton;
	private Button dmSessionButton;
	private Button joinASessionButton;
	private TextBox newDungeonName;
	private TextBox newSessionName;
	private Label sessionLabel;
	private Label templateLabel;
	private CheckBox asDM;
	private Grid dmGrid;
	private boolean dmStateChanged;
	private boolean gridPopulated;

	public DungeonSelectControl() {
		super("Dungeon Template Management", false, true, 400, 400);
		load();
	}

	private void load() {
		getElement().getStyle().setZIndex(BattleMatCanvas.DIALOG_Z);
		dungeonSelectPresenter = new DungeonSelectPresenter();
		dungeonSelectPresenter.setView(this);
		createContent();
		setGlassEnabled(true);
		setText("Dungeon Select");
		setupEventHandlers();
		initialize();
	}

	private void initialize() {
		setToDungeonMasterState();
		center();
	}

	private void createContent() {
		dmGrid = getCenterGrid();
		asDM = new CheckBox("I am DM");
		dungeonDropdownList = new ListBox();
		sessionDropdownList = new ListBox();
		editDungeonButton = new Button("Edit Dungeon");
		deleteDungeonButton = new Button("Delete Dungeon");
		deleteSessionButton = new Button("Delete Session");
		createDungeonButton = new Button("Create New Dungeon ->");
		createSessionButton = new Button("Create New Session ->");
		dmSessionButton = new Button("DM the Session");
		joinASessionButton = new Button("Join A Session");
		newDungeonName = new TextBox();
		newDungeonName.setText("Enter Dungeon Name");
		newSessionName = new TextBox();
		newSessionName.setText("Enter Session Name");
		sessionLabel = new Label("Session Management");
		templateLabel = new Label("Dungeon Template Management");
	}

	private void setupEventHandlers() {
		asDM.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dungeonSelectPresenter.setDungeonMaster(asDM.getValue());
			}
		});
		editDungeonButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dungeonSelectPresenter.editDungeon();
			}
		});
		deleteDungeonButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dungeonSelectPresenter.deleteTemplate();
			}
		});
		deleteSessionButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dungeonSelectPresenter.deleteSession();
			}
		});
		createDungeonButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dungeonSelectPresenter.createDungeon();
			}
		});
		createSessionButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dungeonSelectPresenter.createSession();
			}
		});
		joinASessionButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dungeonSelectPresenter.joinSession();
			}
		});
		dmSessionButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dungeonSelectPresenter.dmSession();
			}
		});
		dungeonDropdownList.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				dungeonSelectPresenter.selectNewDungeonName(dungeonDropdownList.getSelectedItemText(), dungeonDropdownList.getSelectedValue());
			}
		});
		newDungeonName.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				dungeonSelectPresenter.newDungeonNameText(newDungeonName.getValue());
			}
		});
		newDungeonName.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				dungeonSelectPresenter.newDungeonNameText(newDungeonName.getValue());
			}
		});
		newDungeonName.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				newDungeonName.selectAll();
			}
		});
		sessionDropdownList.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				dungeonSelectPresenter.selectSessionName(sessionDropdownList.getSelectedItemText(), sessionDropdownList.getSelectedValue());
			}
		});
		newSessionName.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				newSessionName.selectAll();
			}
		});
		newSessionName.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				dungeonSelectPresenter.newSessionNameText(newSessionName.getValue());
			}
		});
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				if (isShowing()) {
					center();
				}
			}
		});
	}

	private void populateGrid() {
		if (gridPopulated && dmStateChanged == dungeonSelectPresenter.isDungeonMaster()) {
			return;
		}
		gridPopulated = true;
		dmStateChanged = dungeonSelectPresenter.isDungeonMaster();
		dmGrid.clear();
		dmGrid.resize(12, 2);
		if (dungeonSelectPresenter.isDungeonMaster()) {
			populateDMView();
		} else {
			populatePlayerView();
		}
		Element element = dmGrid.getCellFormatter().getElement(1, 0);
		element.setAttribute("colspan", "3");
		sessionLabel.addStyleName("sessionLabel");
		templateLabel.addStyleName("sessionLabel");
	}

	private void populatePlayerView() {
		populateCommon();
		dmGrid.setWidget(3, 0, sessionDropdownList);
		dmGrid.setWidget(4, 0, joinASessionButton);
	}

	private void populateCommon() {
		dmGrid.setWidget(0, 0, asDM);
		dmGrid.setWidget(1, 0, templateLabel);
		dmGrid.setWidget(2, 0, dungeonDropdownList);
	}

	private void populateDMView() {
		populateCommon();
		dmGrid.setWidget(3, 0, createDungeonButton);
		dmGrid.setWidget(3, 1, newDungeonName);
		dmGrid.setWidget(4, 0, editDungeonButton);
		dmGrid.setWidget(5, 0, deleteDungeonButton);
		dmGrid.setWidget(6, 0, sessionLabel);
		dmGrid.setWidget(7, 0, sessionDropdownList);
		dmGrid.setWidget(8, 0, dmSessionButton);
		dmGrid.setWidget(9, 0, deleteSessionButton);
		dmGrid.setWidget(10, 0, createSessionButton);
		dmGrid.setWidget(10, 1, newSessionName);

		Element element2 = dmGrid.getCellFormatter().getElement(6, 0);
		element2.setAttribute("colspan", "3");
	}

	public static void enableWidget(Widget widget, boolean enable) {
		if (enable) {
			widget.getElement().removeAttribute("disabled");
		} else {
			widget.getElement().setAttribute("disabled", "disabled");
		}
	}

	public void close() {
		dungeonSelectPresenter.closing();
		hide();
	}

	public void setToDungeonMasterState() {
		populateGrid();
		if (dungeonSelectPresenter.isDungeonMaster()) {
			setupDisplayForDungeonMaster();
		} else {
			setupDisplayForPlayer();
		}
	}

	private void setupDisplayForDungeonMaster() {
		enableWidget(createDungeonButton, dungeonSelectPresenter.isOkToCreateDungeon());
		enableWidget(editDungeonButton, dungeonSelectPresenter.isTemplateSelected());
		enableWidget(deleteDungeonButton, dungeonSelectPresenter.isOkToDelete());
		enableWidget(newDungeonName, dungeonSelectPresenter.isTemplateSelected());
		if (!dungeonSelectPresenter.isTemplateSelected()) {
			newDungeonName.setText("Enter Dungeon Name");
		}
		setupSessionDisplayForDungeonMaster();
	}

	public void loadDungeonList() {
		dungeonDropdownList.clear();
		dungeonDropdownList.addItem("Select a Dungeon for Operations");
		Map<String, String> dungeonNameToUUIDMap = dungeonSelectPresenter.getDungeonToUUIDMap();
		for (Map.Entry<String, String> entry : dungeonNameToUUIDMap.entrySet()) {
			dungeonDropdownList.addItem(entry.getKey(), entry.getValue());
		}
		dungeonDropdownList.setVisibleItemCount(1);
	}

	private void setupDisplayForPlayer() {
		enableWidget(sessionDropdownList, dungeonSelectPresenter.isOkToShowSessions());
		enableWidget(joinASessionButton, dungeonSelectPresenter.isOkToJoinSession());
	}

	public void show() {
		super.show();
		dungeonSelectPresenter.refreshView();
		getElement().getStyle().setZIndex(100);
		initialize();
	}

	private void setupSessionDisplayForDungeonMaster() {
		enableWidget(sessionDropdownList, dungeonSelectPresenter.isOkToShowSessions());
		enableWidget(createSessionButton, dungeonSelectPresenter.isOkToCreateSession());
		enableWidget(deleteSessionButton, dungeonSelectPresenter.isOkToDeleteSession());
		enableWidget(dmSessionButton, dungeonSelectPresenter.isOkToDMSession());
		enableWidget(newSessionName, dungeonSelectPresenter.isOkToDelete());
		if (!dungeonSelectPresenter.isOkToDelete()) {
			resetNewSessionText();
		}
	}

	public void resetNewSessionText() {
		newSessionName.setText("Enter Session Name");
	}

	public void loadSessionList() {
		setupSessionDisplayForDungeonMaster();
		sessionDropdownList.clear();
		sessionDropdownList.addItem("Select a Session to DM");
		sessionDropdownList.setVisibleItemCount(1);
		if (!dungeonSelectPresenter.isOkToShowSessions()) {
			return;
		}
		SessionListData getSessionListData = dungeonSelectPresenter.getSessionListData();
		if (getSessionListData != null) {
			for (int i = 0; i < getSessionListData.getSessionNames().length; ++i) {
				sessionDropdownList.addItem(getSessionListData.getSessionNames()[i], getSessionListData.getSessionUUIDs()[i]);
			}
		}
	}
	@Override
	protected void onCancelClick(ClickEvent event) {
		hide();
	}

}
