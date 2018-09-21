package per.lambert.ebattleMat.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class DungeonSelectControl extends PopupPanel {

	DungeonSelectPresenter dungeonSelectPresenter;

	@Override
	protected void onLoad() {
		super.onLoad();
		List<String> dungeonList = dungeonSelectPresenter.getDungeonList();
		for (String dungeon : dungeonList) {
			dungeonDropdownList.addItem(dungeon);
		}
		dungeonDropdownList.setVisibleItemCount(1);
	}

	private static DungeonSelectControlUiBinder uiBinder = GWT.create(DungeonSelectControlUiBinder.class);

	interface DungeonSelectControlUiBinder extends UiBinder<Widget, DungeonSelectControl> {
	}

	public DungeonSelectControl() {
		add(uiBinder.createAndBindUi(this));
		dungeonSelectPresenter = new DungeonSelectPresenter();
		dungeonSelectPresenter.setView(this);
		centerPopupOnPage();
	}

	@UiField
	ListBox dungeonDropdownList;

	@UiHandler("dungeonDropdownList")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

	private void centerPopupOnPage() {
		int minOffset = 10; // px
		int knownDialogWidth = 400; // this is in the CSS
		int heightAboveCenter = 200; // will set the top to 200px above center

		int left = Math.max(minOffset, (Window.getClientWidth() / 2) - (knownDialogWidth / 2));
		int top = Math.max(minOffset, (Window.getClientHeight() / 2) - heightAboveCenter);
		setPopupPosition(left, top);
	}

}
