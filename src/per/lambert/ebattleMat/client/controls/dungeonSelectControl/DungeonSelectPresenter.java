package per.lambert.ebattleMat.client.controls.dungeonSelectControl;

import java.util.Map;

import com.google.gwt.event.shared.HandlerRegistration;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.SessionListData;

public class DungeonSelectPresenter {
	private DungeonSelectControl view;
	private boolean isDungeonMaster;
	private String newDungeonName;
	private String selectedDungeonUUID;
	private String newSessionName;
	private String newSessionUUID;

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
		return isValidDungeonForSessions && ServiceManager.getDungeonManager().getSessionListData() != null;
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
				if (event.getReasonForAction() == ReasonForAction.DungeonDataDeleted) {
					refreshView();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonDataLoaded) {
					refreshView();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonDataCreated) {
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
		selectedDungeonUUID = "";
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

	public Map<String, String> getDungeonToUUIDMap() {
		return ServiceManager.getDungeonManager().getDungeonToUUIDMap();
	}

	public SessionListData getSessionListData() {
		return ServiceManager.getDungeonManager().getSessionListData();
	}

	public void selectNewDungeonName(String dungeonName, String dungeonUUID) {
		resetSessionLogic();
		resetDungeonLogic();
		templateSelected = !dungeonName.startsWith("Select ");
		selectedDungeonUUID = dungeonUUID;
		if (templateSelected) {
			okToDelete = ServiceManager.getDungeonManager().okToDeleteThisTemplate(dungeonUUID);
			isValdidDungeonTemplateForSessions(dungeonName);
		}
		if (isValidDungeonForSessions) {
			ServiceManager.getDungeonManager().getSessionList(dungeonUUID);
		}
		view.setToDungeonMasterState();
	}

	public void selectSessionName(String sessionName, String sessionUUID) {
		newSessionName = sessionName;
		newSessionUUID = sessionUUID;
		okToDeleteSession = !newSessionName.startsWith("Select ");
		okToDMSession = okToDeleteSession;
		okToJoinSession = okToDeleteSession;
		view.setToDungeonMasterState();
	}

	private void isValdidDungeonTemplateForSessions(String dungeonName) {
		isValidDungeonForSessions = !dungeonName.startsWith("Select ") && !dungeonName.startsWith("Template ");
	}

	public void editDungeon() {
		ServiceManager.getDungeonManager().setEditMode(true);
		ServiceManager.getDungeonManager().setDungeonMaster(isDungeonMaster);
		ServiceManager.getDungeonManager().selectDungeon(selectedDungeonUUID);
		ServiceManager.getDungeonManager().editSelectedDungeon();
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
		ServiceManager.getDungeonManager().createNewDungeon(selectedDungeonUUID, newDungeonName);
	}

	public void createSession() {
		ServiceManager.getDungeonManager().createNewSession(selectedDungeonUUID, newSessionName);
		okToCreateSession = false;
		view.resetNewSessionText();
	}

	public void deleteTemplate() {
		ServiceManager.getDungeonManager().deleteTemplate(selectedDungeonUUID);
	}

	public void joinSession() {
		ServiceManager.getDungeonManager().setEditMode(false);
		ServiceManager.getDungeonManager().setDungeonMaster(false);
		ServiceManager.getDungeonManager().selectDungeon(selectedDungeonUUID);
		ServiceManager.getDungeonManager().joinSession(newSessionUUID);
		view.close();
	}

	public void dmSession() {
		ServiceManager.getDungeonManager().setEditMode(false);
		ServiceManager.getDungeonManager().setDungeonMaster(true);
		ServiceManager.getDungeonManager().selectDungeon(selectedDungeonUUID);
		ServiceManager.getDungeonManager().joinSession(newSessionUUID);
		view.close();
	}

	public void deleteSession() {
		ServiceManager.getDungeonManager().deleteSession(selectedDungeonUUID, newSessionUUID);
		okToDeleteSession = false;
	}

	public void closing() {
		// if (dungeonDataChangedEvent != null) {
		// dungeonDataChangedEvent.removeHandler();
		// dungeonDataChangedEvent = null;
		// }
	}

}
