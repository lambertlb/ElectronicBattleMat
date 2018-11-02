package per.lambert.ebattleMat.client.controls.dungeonSelectControl;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class DungeonSelectControl extends DialogBox {

	DungeonSelectPresenter dungeonSelectPresenter;

	@Override
	protected void onLoad() {
		super.onLoad();
		String[] dungeonList = dungeonSelectPresenter.getDungeonList();
		for (String dungeon : dungeonList) {
			dungeonDropdownList.addItem(dungeon);
		}
		dungeonDropdownList.setVisibleItemCount(1);
	}

	private static DungeonSelectControlUiBinder uiBinder = GWT.create(DungeonSelectControlUiBinder.class);

	interface DungeonSelectControlUiBinder extends UiBinder<Widget, DungeonSelectControl> {
	}

	public DungeonSelectControl() {
		dungeonSelectPresenter = new DungeonSelectPresenter();
		dungeonSelectPresenter.setView(this);
		setWidget(uiBinder.createAndBindUi(this));
		setAutoHideEnabled(false);
		setGlassEnabled(true);
		setModal(true);
		setText("Dungeon Select");
		center();
	}

	@UiField
	ListBox dungeonDropdownList;

	@UiField
	Button selectButton;
	
	@UiHandler("selectButton")
	void onClick(ClickEvent e) {
		dungeonSelectPresenter.selectDungeon(dungeonDropdownList.getSelectedValue());
	}
	public void close() {
		hide();
	}

}
