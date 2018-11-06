package per.lambert.ebattleMat.client.controls.ribbonBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManagement;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

public class CharacterSelect extends Composite {

	private static CharacterSelectUiBinder uiBinder = GWT.create(CharacterSelectUiBinder.class);

	interface CharacterSelectUiBinder extends UiBinder<Widget, CharacterSelect> {
	}

	public CharacterSelect() {
		initWidget(uiBinder.createAndBindUi(this));
		panel.getElement().getStyle().setBackgroundColor("grey");
		IEventManager eventManager = ServiceManagement.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.CharacterPogsLoaded) {
					characterPogsLoaded();
					return;
				}
			}
		});
	}

	@UiField
	VerticalPanel panel;

	@UiField
	Button selectPog;
	@UiField
	ListBox characterSelect;

	@UiHandler("selectPog")
	void clickButton(ClickEvent event) {
	}

	@UiHandler("characterSelect")
	void characterWasSelected(ClickEvent event) {
		int pogIndex = characterSelect.getSelectedIndex();
		ServiceManagement.getDungeonManagment().setSelectedPog(ServiceManagement.getDungeonManagment().getPcPogs()[pogIndex]);
		ServiceManagement.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.PogWasSelected, null));
	}

	private void characterPogsLoaded() {
		characterSelect.clear();
		PogData[] pogList = ServiceManagement.getDungeonManagment().getPcPogs();
		for (PogData pogData : pogList) {
			characterSelect.addItem(pogData.getPogName());
		}
	}
}
