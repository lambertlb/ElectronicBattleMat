package per.lambert.ebattleMat.client.controls.dungeonSelectControl;

import com.google.gwt.event.shared.HandlerRegistration;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;

public class DungeonSelectPresenter {
	private DungeonSelectControl view;
	private boolean isDungeonMaster;
	private String newDungeonName;
	private String selectedTemplate;
	private String newSessionName;

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

	public boolean isOkToShowSessions() {
		return isValidDungeonForSessions && ServiceManager.getDungeonManager().getSessionNames() != null;
	}

	private boolean isValidDungeonForSessions;

	public boolean isValidDungeonForSessions() {
		return isValidDungeonForSessions;
	}

	private boolean okToCreateSession;

	public boolean isOkToCreateSession() {
		return okToCreateSession;
	}

	private boolean sessionSelected;

	public boolean isSessionSelected() {
		return sessionSelected;
	}

	private boolean okToDeleteSession;

	public boolean isOkToDeleteSession() {
		return okToDeleteSession;
	}

	private boolean okToDMSession;

	public boolean isOkToDMSession() {
		return okToDMSession;
	}

	private boolean okToJoinSession;

	public boolean isOkToJoinSession() {
		return okToJoinSession;
	}

	HandlerRegistration dungeonDataChangedEvent;

	public DungeonSelectPresenter() {
		IEventManager eventManager = ServiceManager.getEventManager();
		dungeonDataChangedEvent = eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.DungeonDataChanged) {
					refreshView();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.SessionListChanged) {
					refreshSessionData();
					return;
				}
			}
		});
	}

	public void refreshView() {
		resetDungeonLogic();
		view.loadDungeonList();
		view.setToDungeonMasterState();
		refreshSession();
	}

	private void resetDungeonLogic() {
		okToCreateDungeon = false;
		okToDelete = false;
		templateSelected = false;
		newDungeonName = "";
		selectedTemplate = "";
	}

	private void refreshSession() {
		resetSessionLogic();
		refreshSessionData();
	}

	private void resetSessionLogic() {
		isValidDungeonForSessions = false;
		okToCreateSession = false;
		sessionSelected = false;
		okToDeleteSession = false;
		okToDMSession = false;
		okToJoinSession = false;
	}

	protected void refreshSessionData() {
		view.loadSessionList();
	}

	public void setView(DungeonSelectControl dungeonSelectControl) {
		view = dungeonSelectControl;
	}

	public String[] getDungeonList() {
		return ServiceManager.getDungeonManager().getDungeonNames();
	}

	public String[] getSessionList() {
		return ServiceManager.getDungeonManager().getSessionNames();
	}

	public void selectNewDungeonName(String dungeonName) {
		resetSessionLogic();
		resetDungeonLogic();
		templateSelected = !dungeonName.startsWith("Select ");
		selectedTemplate = dungeonName;
		if (templateSelected) {
			okToDelete = ServiceManager.getDungeonManager().okToDeleteThisTemplate(dungeonName);
			isValdidDungeonTemplateForSessions(dungeonName);
		}
		if (isValidDungeonForSessions) {
			ServiceManager.getDungeonManager().getSessionList(dungeonName);
		}
		view.setToDungeonMasterState();
	}

	public void selectSessionName(String selectedValue) {
		newSessionName = selectedValue;
		okToDeleteSession = !newSessionName.startsWith("Select ");
		okToDMSession = okToDeleteSession;
		okToJoinSession = okToDeleteSession;
		view.setToDungeonMasterState();
	}

	private void isValdidDungeonTemplateForSessions(String dungeonName) {
		isValidDungeonForSessions = !dungeonName.startsWith("Select ") && !dungeonName.startsWith("Template ");
	}

	public void editDungeon() {
		ServiceManager.getDungeonManager().setDungeonMaster(isDungeonMaster);
		ServiceManager.getDungeonManager().selectDungeon(selectedTemplate);
		ServiceManager.getDungeonManager().setEditMode(true);
		view.close();
	}

	public void newDungeonNameText(String newDungeonName) {
		okToCreateDungeon = !newDungeonName.startsWith("Enter ") && newDungeonName.length() > 4;
		this.newDungeonName = newDungeonName;
		view.setToDungeonMasterState();
	}

	public void newSessionNameText(String newSessionName) {
		okToCreateSession = ServiceManager.getDungeonManager().isNameValidForNewSession(newSessionName);
		this.newSessionName = newSessionName;
		view.setToDungeonMasterState();
	}

	public void createDungeon() {
		ServiceManager.getDungeonManager().createNewDungeon(selectedTemplate, newDungeonName);
		view.close();
	}

	public void createSession() {
		ServiceManager.getDungeonManager().createNewSession(selectedTemplate, newSessionName);
	}

	public void deleteTemplate() {
		ServiceManager.getDungeonManager().deleteTemplate(selectedTemplate);
	}

	public void joinSession() {
		ServiceManager.getDungeonManager().joinSession(newSessionName);
	}

	public void deleteSession() {
		ServiceManager.getDungeonManager().deleteSession(selectedTemplate, newSessionName);
	}

	public void closing() {
		// if (dungeonDataChangedEvent != null) {
		// dungeonDataChangedEvent.removeHandler();
		// dungeonDataChangedEvent = null;
		// }
	}
}
