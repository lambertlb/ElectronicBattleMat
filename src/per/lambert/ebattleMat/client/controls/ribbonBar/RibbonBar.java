package per.lambert.ebattleMat.client.controls.ribbonBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;

public class RibbonBar extends Composite {

	private static RibbonBarUiBinder uiBinder = GWT.create(RibbonBarUiBinder.class);

	interface RibbonBarUiBinder extends UiBinder<Widget, RibbonBar> {
	}

	@UiField
	HorizontalPanel panel;

	public RibbonBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		setupEventHandler();
		handleDMStateChanged();
	}

	private void setupEventHandler() {
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.DMStateChange) {
					handleDMStateChanged();
					return;
				}
			}
		});
	}

	protected void handleDMStateChanged() {
		if (ServiceManager.getDungeonManager().isDungeonMaster()) {
			setupForDungeonMaster();
		} else {
			setupForPlayer();
		}
	}

	private void setupForDungeonMaster() {
		panel.clear();
		panel.add(new SelectedPog());
//		panel.add(new GridGroup());
		panel.add(new CharacterSelect());
//		panel.add(new DungeonOptions());
	}

	private void setupForPlayer() {
		panel.clear();
		panel.add(new SelectedPog());
//		panel.add(new CharacterSelect());
	}
}
