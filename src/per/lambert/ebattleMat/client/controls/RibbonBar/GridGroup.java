package per.lambert.ebattleMat.client.controls.RibbonBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManagement;

public class GridGroup extends Composite {

	private static GridGroupUiBinder uiBinder = GWT.create(GridGroupUiBinder.class);

	interface GridGroupUiBinder extends UiBinder<Widget, GridGroup> {
	}

	public GridGroup() {
		initWidget(uiBinder.createAndBindUi(this));
		label.setStyleName("ribbonGroupLabel");
		double gridSize = ServiceManagement.getDungeonManagment().getSelectedDungeon().getGridSize();
		if (gridSize == 0.0) {
			gridSize = 30;
		}
		gridSizeBox.setStyleName("doubleboxstyle");
		gridSizeBox.setValue(gridSize);
	}

	@UiField
	CheckBox showGrid;

	@UiField
	Label label;

	@UiField DoubleBox gridSizeBox;

	@UiHandler("showGrid")
	void onClick(ClickEvent e) {
		ServiceManagement.getDungeonManagment().getSelectedDungeon().setShowGrid(showGrid.getValue());
		ServiceManagement.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.DungeonDataChanged, null));
	}
	@UiHandler("gridSizeBox")
	void onValueChanged(ValueChangeEvent<Double> event) {
        Window.alert( "Value Changed = New value: " + gridSizeBox.getValue() );
	}
}
