package per.lambert.ebattleMat.client.controls.dungeonSelectControl;

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
		view.loadDungeonList();
		view.setToDungeonMasterState();
	}

	private boolean templateSelected;

	public boolean isTemplateSelected() {
		return templateSelected;
	}

	private boolean okToCreateDungeon;

	public boolean isOkToCreateDungeon() {
		return okToCreateDungeon;
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
}
