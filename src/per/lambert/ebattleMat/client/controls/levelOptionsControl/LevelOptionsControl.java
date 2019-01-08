package per.lambert.ebattleMat.client.controls.levelOptionsControl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.VerticalAlign;
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
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.battleMatDisplay.BattleMatCanvas;
import per.lambert.ebattleMat.client.controls.labeledTextBox.LabeledTextBox;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.resizeableDialog.ResizableDialog;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;

public class LevelOptionsControl extends ResizableDialog {

	private static LevelOptionsControlUiBinder uiBinder = GWT.create(LevelOptionsControlUiBinder.class);

	interface LevelOptionsControlUiBinder extends UiBinder<Widget, LevelOptionsControl> {
	}

	private CheckBox showGrid;
	private LabeledTextBox gridSize;
	private LabeledTextBox gridOffsetX;
	private LabeledTextBox gridOffsetY;

	@UiField
	Grid dmGrid;

	@UiField
	Button ok;

	@UiField
	Button cancel;

	public LevelOptionsControl() {
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		getElement().getStyle().setZIndex(BattleMatCanvas.DIALOG_Z);
		createContent();
		setupEventHandlers();
		center();
		getData();
	}

	private void createContent() {
		dmGrid.clear();
		dmGrid.resize(10, 2);
		createShowGrid();
		createGridSizeEntry();
		createGridOffsetX();
		createGridOffsetY();
	}

	private void createShowGrid() {
		showGrid = new CheckBox("Show Grid");
		showGrid.setStyleName("ribbonBarLabel");
		showGrid.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
		showGrid.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				showGridClicked();
			}
		});
		dmGrid.setWidget(0, 0, showGrid);
	}

	private void createGridSizeEntry() {
		gridSize = new LabeledTextBox("Grid Size", 0.0);
		gridSize.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
			}
		});
		gridSize.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
			}
		});
		dmGrid.setWidget(0, 1, gridSize);
	}
	private void createGridOffsetX() {
		gridOffsetX = new LabeledTextBox("Grid Offset X", 0.0);
		gridOffsetX.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
			}
		});
		gridOffsetX.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
			}
		});
		dmGrid.setWidget(1, 0, gridOffsetX);
	}
	private void createGridOffsetY() {
		gridOffsetY = new LabeledTextBox("Grid Offset Y", 0.0);
		gridOffsetY.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
			}
		});
		gridOffsetY.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
			}
		});
		dmGrid.setWidget(1, 1, gridOffsetY);
	}

	private void setupEventHandlers() {
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.DungeonDataLoaded) {
					getData();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonSelectedLevelChanged) {
					getData();
					return;
				}
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

	@UiHandler("ok")
	void okClicked(ClickEvent event) {
		DungeonLevel levelData = ServiceManager.getDungeonManager().getCurrentLevelData();
		if (levelData == null) {
			return;
		}
		ServiceManager.getDungeonManager().getSelectedDungeon().setShowGrid(showGrid.getValue());
		levelData.setGridSize(gridSize.getDoubleValue());
		levelData.setGridOffsetX(gridOffsetX.getDoubleValue());
		levelData.setGridOffsetY(gridOffsetY.getDoubleValue());
		ServiceManager.getDungeonManager().saveDungeonData();
		close();
	}

	@UiHandler("cancel")
	void cancelClicked(ClickEvent event) {
		close();
	}

	protected void showGridClicked() {
	}

	public void close() {
		hide();
	}

	@Override
	public void show() {
		super.show();
	}

	private void getData() {
		DungeonLevel levelData = ServiceManager.getDungeonManager().getCurrentLevelData();
		if (levelData == null) {
			return;
		}
		showGrid.setValue(ServiceManager.getDungeonManager().getSelectedDungeon().getShowGrid());
		gridSize.setValue(levelData.getGridSize());
		gridOffsetX.setValue(levelData.getGridOffsetX());
		gridOffsetY.setValue(levelData.getGridOffsetY());
	}
}
