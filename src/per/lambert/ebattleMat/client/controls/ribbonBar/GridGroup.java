package per.lambert.ebattleMat.client.controls.ribbonBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;

public class GridGroup extends Composite {

	private static GridGroupUiBinder uiBinder = GWT.create(GridGroupUiBinder.class);

	interface GridGroupUiBinder extends UiBinder<Widget, GridGroup> {
	}

	public GridGroup() {
		HTMLPanel widget = (HTMLPanel) uiBinder.createAndBindUi(this);
		initWidget(widget);
		panel.getElement().getStyle().setBackgroundColor("grey");
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.DungeonSelected) {
					setupItems();
					return;
				}
			}
		});
	}

	private void setupItems() {
		showGrid.setValue(ServiceManager.getDungeonManager().getSelectedDungeon().getShowGrid());
		gridSizeBox.setValue(ServiceManager.getDungeonManager().getCurrentLevelData().getGridSize());
		gridOffsetX.setValue(ServiceManager.getDungeonManager().getCurrentLevelData().getGridOffsetX());
		gridOffsetY.setValue(ServiceManager.getDungeonManager().getCurrentLevelData().getGridOffsetY());
	}

	@UiField
	FlowPanel panel;
	@UiField
	CheckBox showGrid;

	@UiField
	DoubleBox gridSizeBox;
	@UiField
	DoubleBox gridOffsetX;
	@UiField
	DoubleBox gridOffsetY;

	@UiHandler("showGrid")
	void onClick(ClickEvent e) {
		ServiceManager.getDungeonManager().getSelectedDungeon().setShowGrid(showGrid.getValue());
		ServiceManager.getDungeonManager().saveDungeonData();
	}

	@UiHandler("gridSizeBox")
	void onValueChanged(ValueChangeEvent<Double> event) {
		ServiceManager.getDungeonManager().getCurrentLevelData().setGridSize(gridSizeBox.getValue());
		ServiceManager.getDungeonManager().saveDungeonData();
	}

	@UiHandler("gridOffsetX")
	void onGridOffsetXChanged(ValueChangeEvent<Double> event) {
		ServiceManager.getDungeonManager().getCurrentLevelData().setGridOffsetX(gridOffsetX.getValue());
		ServiceManager.getDungeonManager().saveDungeonData();
	}

	@UiHandler("gridOffsetY")
	void onGridOffsetYChanged(ValueChangeEvent<Double> event) {
		ServiceManager.getDungeonManager().getCurrentLevelData().setGridOffsetY(gridOffsetY.getValue());
		ServiceManager.getDungeonManager().saveDungeonData();
	}
}
