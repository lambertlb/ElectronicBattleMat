package per.lambert.ebattleMat.client.controls.DungeonSelectControl;

import java.util.List;

import per.lambert.ebattleMat.client.services.ServiceManagement;

public class DungeonSelectPresenter {

	public void setView(DungeonSelectControl dungeonSelectControl) {
	}

	public List<String> getDungeonList() {
		return ServiceManagement.getDungeonManagment().getDungeonList();
	}

}
