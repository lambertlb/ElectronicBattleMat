package per.lambert.ebattleMat.client.controls.DungeonSelectControl;

import java.util.List;

import per.lambert.ebattleMat.client.services.ServiceManagement;

public class DungeonSelectPresenter {
	private DungeonSelectControl view;
	public void setView(DungeonSelectControl dungeonSelectControl) {
		view = dungeonSelectControl;
	}

	public List<String> getDungeonList() {
		return ServiceManagement.getDungeonManagment().getDungeonList();
	}

	public void selectDungeon(String dungeonsName) {
		view.close();
		ServiceManagement.getDungeonManagment().selectDungeon(dungeonsName);
	}
}
