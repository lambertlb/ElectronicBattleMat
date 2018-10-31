package per.lambert.ebattleMat.client.controls.RibbonBar;

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

import per.lambert.ebattleMat.client.services.ServiceManagement;

public class GridGroup extends Composite {

	private static GridGroupUiBinder uiBinder = GWT.create(GridGroupUiBinder.class);

	interface GridGroupUiBinder extends UiBinder<Widget, GridGroup> {
	}

	public GridGroup() {
		HTMLPanel widget = (HTMLPanel) uiBinder.createAndBindUi(this);
		initWidget(widget);
		showGrid.setValue(ServiceManagement.getDungeonManagment().getSelectedDungeon().getShowGrid());
		gridSizeBox.setValue(ServiceManagement.getDungeonManagment().getCurrentLevelData().getGridSize());
		gridOffsetX.setValue(ServiceManagement.getDungeonManagment().getCurrentLevelData().getGridOffsetX());
		gridOffsetY.setValue(ServiceManagement.getDungeonManagment().getCurrentLevelData().getGridOffsetY());
		panel.getElement().getStyle().setBackgroundColor("grey");
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
		ServiceManagement.getDungeonManagment().getSelectedDungeon().setShowGrid(showGrid.getValue());
		ServiceManagement.getDungeonManagment().saveDungeonData();
	}

	@UiHandler("gridSizeBox")
	void onValueChanged(ValueChangeEvent<Double> event) {
		ServiceManagement.getDungeonManagment().getCurrentLevelData().setGridSize(gridSizeBox.getValue());
		ServiceManagement.getDungeonManagment().saveDungeonData();
	}

	@UiHandler("gridOffsetX")
	void onGridOffsetXChanged(ValueChangeEvent<Double> event) {
		ServiceManagement.getDungeonManagment().getCurrentLevelData().setGridOffsetX(gridOffsetX.getValue());
		ServiceManagement.getDungeonManagment().saveDungeonData();
	}

	@UiHandler("gridOffsetY")
	void onGridOffsetYChanged(ValueChangeEvent<Double> event) {
		ServiceManagement.getDungeonManagment().getCurrentLevelData().setGridOffsetY(gridOffsetY.getValue());
		ServiceManagement.getDungeonManagment().saveDungeonData();
	}
}
