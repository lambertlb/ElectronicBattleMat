package per.lambert.ebattleMat.client.controls.dungeonSelectControl;

import per.lambert.ebattleMat.client.services.ServiceManagement;

public class DungeonSelectPresenter {
	private DungeonSelectControl view;

	public void setView(DungeonSelectControl dungeonSelectControl) {
		view = dungeonSelectControl;
	}

	public String[] getDungeonList() {
		return ServiceManagement.getDungeonManagment().getDungeonNames();
	}

	public void selectDungeon(String dungeonsName) {
		view.close();
		ServiceManagement.getDungeonManagment().selectDungeon(dungeonsName);
	}

	public void createDungeon(String selectedValue) {
	}
}
