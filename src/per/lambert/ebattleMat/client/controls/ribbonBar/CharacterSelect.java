package per.lambert.ebattleMat.client.controls.ribbonBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

public class CharacterSelect extends Composite {

	private static CharacterSelectUiBinder uiBinder = GWT.create(CharacterSelectUiBinder.class);

	interface CharacterSelectUiBinder extends UiBinder<Widget, CharacterSelect> {
	}

	public CharacterSelect() {
		initWidget(uiBinder.createAndBindUi(this));
		panel.getElement().getStyle().setBackgroundColor("grey");
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.CharacterPogsLoaded) {
					characterPogsLoaded();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.MonsterPogsLoaded) {
					monsterPogsLoaded();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.RoomObjectPogsLoaded) {
					roomObjectPogsLoaded();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonDataLoaded) {
					dungeonDataLoaded();
					return;
				}
			}
		});
		characterSelect.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				characterWasSelected();
			}
		});
		monsterSelect.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				monsterWasSelected();
			}
		});
//		roomObjectSelect.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(ChangeEvent event) {
//				roomObjectWasSelected();
//			}
//		});
//		levelSelect.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(ChangeEvent event) {
//				levelWasSelected();
//			}
//		});
	}

	@UiField
	VerticalPanel panel;

	@UiField
	ListBox characterSelect;
	@UiField
	ListBox monsterSelect;
//	@UiField
//	ListBox roomObjectSelect;
//	@UiField
//	ListBox levelSelect;

	private void characterWasSelected() {
		PogData characterPog = ServiceManager.getDungeonManager().findCharacterPog(characterSelect.getSelectedValue());
		ServiceManager.getDungeonManager().setSelectedPog(characterPog);
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.PogWasSelected, null));
	}

	private void characterPogsLoaded() {
		characterSelect.clear();
		characterSelect.addItem("Select Character Pog", "");
		PogData[] pogList = ServiceManager.getDungeonManager().getPcTemplatePogs();
		for (PogData pogData : pogList) {
			characterSelect.addItem(pogData.getPogName(), pogData.getUUID());
		}
	}

	protected void monsterWasSelected() {
		PogData monsterPog = ServiceManager.getDungeonManager().findMonsterPog(monsterSelect.getSelectedValue());
		ServiceManager.getDungeonManager().setSelectedPog(monsterPog);
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.PogWasSelected, null));
	}

	protected void monsterPogsLoaded() {
		monsterSelect.clear();
		monsterSelect.addItem("Select Monster Pog", "");
		PogData[] pogList = ServiceManager.getDungeonManager().getMonsterTemplatePogs();
		for (PogData pogData : pogList) {
			monsterSelect.addItem(pogData.getPogName(), pogData.getUUID());
		}
	}

	protected void roomObjectPogsLoaded() {
//		roomObjectSelect.clear();
//		roomObjectSelect.addItem("Select Room Object Pog", "");
//		PogData[] pogList = ServiceManager.getDungeonManager().getRoomObjectTemplatePogs();
//		for (PogData pogData : pogList) {
//			roomObjectSelect.addItem(pogData.getPogName(), pogData.getUUID());
//		}
	}

	protected void roomObjectWasSelected() {
//		PogData roomObjectPog = ServiceManager.getDungeonManager().findRoomObjectPog(roomObjectSelect.getSelectedValue());
//		ServiceManager.getDungeonManager().setSelectedPog(roomObjectPog);
//		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.PogWasSelected, null));
	}

	protected void dungeonDataLoaded() {
//		levelSelect.clear();
//		String[] levelNames = ServiceManager.getDungeonManager().getDungeonLevelNames();
//		for (String levelName : levelNames) {
//			levelSelect.addItem(levelName);
//		}
	}

	protected void levelWasSelected() {
//		ServiceManager.getDungeonManager().setCurrentLevel(levelSelect.getSelectedIndex());
	}
}
