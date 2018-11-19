package per.lambert.ebattleMat.client.controls.dungeonSelectControl;

import per.lambert.ebattleMat.client.services.ServiceManagement;

public class DungeonSelectPresenter {
	private DungeonSelectControl view;
	private boolean isDungeonMaster;

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

	public void selectDungeon(String dungeonsName) {
		templateSelected = !dungeonsName.startsWith("Select ");
		view.setToDungeonMasterState();
		// view.close();
		// ServiceManagement.getDungeonManagment().setDungeonMaster(isDungeonMaster);
		// ServiceManagement.getDungeonManagment().selectDungeon(dungeonsName);
	}

	public void newDungeonNameText(String newDungeonName) {
		okToCreateDungeon = !newDungeonName.startsWith("Enter ") && newDungeonName.length() > 4;
		view.setToDungeonMasterState();
	}

	public void createDungeon(String selectedValue) {
	}
}
