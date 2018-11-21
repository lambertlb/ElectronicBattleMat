package per.lambert.ebattleMat.client.controls.dungeonSelectControl;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManagement;

public class DungeonSelectPresenter {
	private DungeonSelectControl view;
	private boolean isDungeonMaster;
	private String newDungeonName;
	private String selectedTemplate;

	public boolean isDungeonMaster() {
		return isDungeonMaster;
	}

	public void setDungeonMaster(boolean isDungeonMaster) {
		this.isDungeonMaster = isDungeonMaster;
		templateSelected = false;
		okToCreateDungeon = false;
		refreshView();
	}

	private boolean templateSelected;

	public boolean isTemplateSelected() {
		return templateSelected;
	}

	private boolean okToCreateDungeon;

	public boolean isOkToCreateDungeon() {
		return okToCreateDungeon;
	}

	private boolean okToDelete;

	public boolean isOkToDelete() {
		return okToDelete;
	}

	HandlerRegistration dungeonDataChangedEvent;

	public DungeonSelectPresenter() {
		IEventManager eventManager = ServiceManagement.getEventManager();
		dungeonDataChangedEvent = eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.DungeonDataChanged) {
					refreshView();
					return;
				}
			}
		});
	}

	private void refreshView() {
		okToCreateDungeon = false;
		okToDelete = false;
		templateSelected = false;
		newDungeonName = "";
		selectedTemplate = "";
		view.loadDungeonList();
		view.setToDungeonMasterState();
	}

	public void setView(DungeonSelectControl dungeonSelectControl) {
		view = dungeonSelectControl;
	}

	public String[] getDungeonList() {
		return ServiceManagement.getDungeonManagment().getDungeonNames();
	}

	public void selectNewDungeonName(String dungeonsName) {
		templateSelected = !dungeonsName.startsWith("Select ");
		selectedTemplate = dungeonsName;
		okToDelete = ServiceManagement.getDungeonManagment().okToDeleteThisTemplate(dungeonsName);
		view.setToDungeonMasterState();
	}

	public void selectDungeon() {
		ServiceManagement.getDungeonManagment().setDungeonMaster(isDungeonMaster);
		ServiceManagement.getDungeonManagment().selectDungeon(selectedTemplate);
		view.close();
	}

	public void newDungeonNameText(String newDungeonName) {
		okToCreateDungeon = !newDungeonName.startsWith("Enter ") && newDungeonName.length() > 4;
		this.newDungeonName = newDungeonName;
		view.setToDungeonMasterState();
	}

	public void createDungeon() {
		ServiceManagement.getDungeonManagment().createNewDungeon(selectedTemplate, newDungeonName);
		view.close();
	}

	public void deleteTemplate() {
		ServiceManagement.getDungeonManagment().deleteTemplate(selectedTemplate);
	}

	public void closing() {
		if (dungeonDataChangedEvent != null) {
			dungeonDataChangedEvent.removeHandler();
			dungeonDataChangedEvent = null;
		}
	}
}
