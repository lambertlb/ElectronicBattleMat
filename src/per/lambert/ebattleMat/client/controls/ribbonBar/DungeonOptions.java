package per.lambert.ebattleMat.client.controls.ribbonBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;

public class DungeonOptions extends Composite {

	private static DungeonOptionsUiBinder uiBinder = GWT.create(DungeonOptionsUiBinder.class);

	interface DungeonOptionsUiBinder extends UiBinder<Widget, DungeonOptions> {
	}

	public DungeonOptions() {
		initWidget(uiBinder.createAndBindUi(this));
		panel.getElement().getStyle().setBackgroundColor("grey");
	}

	@UiField
	VerticalPanel panel;

	@UiField
	CheckBox fowToggle;
	@UiField
	Button updateSession;
	@UiField
	Button dungeonControl;

	@UiHandler("fowToggle")
	void onClick(ClickEvent e) {
		ServiceManager.getDungeonManager().setFowToggle(fowToggle.getValue());
	}

	@UiHandler("updateSession")
	void onUpdateSession(ClickEvent e) {
		ServiceManager.getDungeonManager().doTimedTasks();
	}

	@UiHandler("dungeonControl")
	void dungeonControlClick(ClickEvent event) {
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.SelectNewDungeon, null));
	}
}
