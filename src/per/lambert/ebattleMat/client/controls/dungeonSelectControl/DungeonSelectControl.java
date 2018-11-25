package per.lambert.ebattleMat.client.controls.dungeonSelectControl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.windowBox.WindowBox;

public class DungeonSelectControl extends WindowBox {

	private DungeonSelectPresenter dungeonSelectPresenter;

	@Override
	protected void onLoad() {
		super.onLoad();
		setToDungeonMasterState();
	}

	private static DungeonSelectControlUiBinder uiBinder = GWT.create(DungeonSelectControlUiBinder.class);

	interface DungeonSelectControlUiBinder extends UiBinder<Widget, DungeonSelectControl> {
	}

	public DungeonSelectControl() {
		super(false, true, true, false, true);
		dungeonSelectPresenter = new DungeonSelectPresenter();
		dungeonSelectPresenter.setView(this);
		setWidget(uiBinder.createAndBindUi(this));
		setGlassEnabled(true);
		setText("Dungeon Select");
		center();
		Element element = dmGrid.getCellFormatter().getElement(0, 0);
		element.setAttribute("colspan", "3");
		Element element2 = dmGrid.getCellFormatter().getElement(5, 0);
		element2.setAttribute("colspan", "3");

		dungeonDropdownList.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				dungeonSelectPresenter.selectNewDungeonName(dungeonDropdownList.getSelectedValue());
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
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				if (isShowing()) {
					center();
				}
			}
		});
	}

	@UiField
	ListBox dungeonDropdownList;

	@UiField
	ListBox sessionList;

	@UiField
	ListBox sessionDropdownList;

	@UiField
	Button editDungeonButton;

	@UiField
	Button deleteDungeonButton;

	@UiField
	Button selectSessionButton;

	@UiField
	Button createDungeonButton;

	@UiField
	CheckBox asDM;

	@UiField
	Grid playerGrid;

	@UiField
	Grid dmGrid;

	@UiField
	TextBox newDungeonName;

	@UiField
	Label sessionLabel;

	@UiField
	Label templateLabel;

	@UiHandler("editDungeonButton")
	void onEditDungeonButtonClick(ClickEvent e) {
		dungeonSelectPresenter.editDungeon();
	}

	@UiHandler("deleteDungeonButton")
	void onDeleteDungeonButtonClick(ClickEvent e) {
		dungeonSelectPresenter.deleteTemplate();
	}

	@UiHandler("asDM")
	void onAsGMClick(ClickEvent e) {
		dungeonSelectPresenter.setDungeonMaster(asDM.getValue());
	}

	@UiHandler("createDungeonButton")
	void onCreateDungeonButtonClick(ClickEvent e) {
		dungeonSelectPresenter.createDungeon();
	}

	public void enableWidget(Widget widget, boolean enable) {
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
		if (dungeonSelectPresenter.isDungeonMaster()) {
			setupDisplayForDungeonMaster();
		} else {
			setupDisplayForPlayer();
		}
	}

	private void setupDisplayForDungeonMaster() {
		playerGrid.setVisible(false);
		dmGrid.setVisible(true);
		enableWidget(createDungeonButton, dungeonSelectPresenter.isOkToCreateDungeon());
		enableWidget(editDungeonButton, dungeonSelectPresenter.isTemplateSelected());
		enableWidget(deleteDungeonButton, dungeonSelectPresenter.isOkToDelete());
		enableWidget(newDungeonName, dungeonSelectPresenter.isTemplateSelected());
		if (!dungeonSelectPresenter.isTemplateSelected()) {
			newDungeonName.setText("Enter Dungeon Name");
		}
	}

	public void loadDungeonList() {
		dungeonDropdownList.clear();
		dungeonDropdownList.addItem("Select a Dungeon for Operations");
		String[] dungeonList = dungeonSelectPresenter.getDungeonList();
		for (String dungeon : dungeonList) {
			dungeonDropdownList.addItem(dungeon);
		}
		dungeonDropdownList.setVisibleItemCount(1);
	}

	private void setupDisplayForPlayer() {
		playerGrid.setVisible(true);
		dmGrid.setVisible(false);
	}

	public void setupAndShow() {
		dungeonSelectPresenter.refreshView();
		getElement().getStyle().setZIndex(100);
		show();
	}

	public void loadSessionList() {
		sessionDropdownList.clear();
		sessionDropdownList.addItem("Select a Dungeon to DM");
		// String[] sessionNames = dungeonSelectPresenter.getSessionList();
		// for (String session : sessionNames) {
		// sessionList.addItem(session);
		// }
		sessionDropdownList.setVisibleItemCount(1);
	}
}
