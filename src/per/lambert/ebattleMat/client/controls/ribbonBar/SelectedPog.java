package per.lambert.ebattleMat.client.controls.ribbonBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.battleMatDisplay.BattleMatLayout;
import per.lambert.ebattleMat.client.battleMatDisplay.PogCanvas;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

public class SelectedPog extends Composite {

	private static SelectedPogUiBinder uiBinder = GWT.create(SelectedPogUiBinder.class);

	interface SelectedPogUiBinder extends UiBinder<Widget, SelectedPog> {
	}

	@UiField
	FlowPanel pogPanel;

	@UiField
	HTMLPanel hostPanel;

	public SelectedPog() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		unselectedPogLook();
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.PogWasSelected) {
					pogSelected();
					return;
				}
			}
		});
	}
	public void unselectedPogLook() {
		int height = (int)BattleMatLayout.RIBBON_BAR_SIZE;
		pogPanel.setWidth("" + height + "px");
	}

	private void pogSelected() {
		PogData selectePog = ServiceManager.getDungeonManager().getSelectedPog();
		pogPanel.clear();
		if (selectePog == null) {
			return;
		}
		PogCanvas scalablePog = new PogCanvas(selectePog);
		Widget parent = hostPanel.getParent().getParent();
		int height = parent.getOffsetHeight();
		scalablePog.setPogWidth(height);
		pogPanel.add(scalablePog);
	}
}
