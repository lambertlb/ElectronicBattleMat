package per.lambert.ebattleMat.client.controls.dungeonCreator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.services.ServiceManagement;
import per.lambert.ebattleMat.client.windowBox.WindowBox;

public class DungeonCreator extends WindowBox {

	private static DungeonCreatorUiBinder uiBinder = GWT.create(DungeonCreatorUiBinder.class);

	interface DungeonCreatorUiBinder extends UiBinder<Widget, DungeonCreator> {
	}

	public DungeonCreator() {
		super(false, true, true, false, true);
		setWidget(uiBinder.createAndBindUi(this));
		center();
		setGlassEnabled(true);
		setText("Dungeon Creation");
		accept.getElement().setAttribute("disabled", "disabled");
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		LoadDungeonList();
	}

	private void LoadDungeonList() {
		String[] dungeonList =ServiceManagement.getDungeonManagment().getDungeonList();
		for (String dungeon : dungeonList) {
			dungeonDropdownList.addItem(dungeon);
		}
		dungeonDropdownList.setVisibleItemCount(1);
	}

	@UiField
	Button accept;

	@UiField
	Button cancel;

	@UiField
	Button selectTemplate;

	@UiField
	ListBox dungeonDropdownList;

	@UiHandler("accept")
	void onAcceptClick(ClickEvent e) {
		close();
	}

	@UiHandler("cancel")
	void onCancelClick(ClickEvent e) {
		close();
	}

	@UiHandler("selectTemplate")
	void onSelectTemplateClick(ClickEvent e) {
		close();
	}

	public void close() {
		hide();
	}
}
