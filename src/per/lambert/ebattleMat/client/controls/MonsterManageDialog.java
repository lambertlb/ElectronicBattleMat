package per.lambert.ebattleMat.client.controls;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import per.lambert.ebattleMat.client.battleMatDisplay.PogCanvas;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

public class MonsterManageDialog extends OkCancelDialog {
	private Grid centerGrid;
	private Label monsterNameLabel;
	private TextBox monsterName;
	private Label monsterPictureLabel;
	private TextBox monsterPicture;
	private FlowPanel pogPanel;
	private PogCanvas pogCanvas;
	private PogData pogData;
	private Label selectionSectionLabel;
	private ListBox raceList;
	private ListBox classList;
	private ListBox sexList;
	private Button applyFilters;
	private ListBox filteredMonsterList;
	private Label editSectionLabel;

	public MonsterManageDialog() {
		super("Manage Monsters", true, true);
		load();
	}

	protected void load() {
		createContent();
		initialize();
		setupEventHandlers();
	}

	private void createContent() {
		centerGrid = getCenterGrid();
		centerGrid.clear();
		centerGrid.resize(10, 3);

		selectionSectionLabel = new Label("Select existing Monster");
		selectionSectionLabel.setStyleName("sessionLabel");
		centerGrid.setWidget(0, 0, selectionSectionLabel);
		Element element = centerGrid.getCellFormatter().getElement(0, 0);
		element.setAttribute("colspan", "3");

		raceList = new ListBox();
		raceList.setStyleName("ribbonBarLabel");
		raceList.setVisibleItemCount(1);
		centerGrid.setWidget(1, 0, raceList);

		classList = new ListBox();
		classList.setStyleName("ribbonBarLabel");
		classList.setVisibleItemCount(1);
		centerGrid.setWidget(1, 1, classList);

		sexList = new ListBox();
		sexList.setStyleName("ribbonBarLabel");
		sexList.setVisibleItemCount(1);
		centerGrid.setWidget(1, 2, sexList);

		applyFilters = new Button("Apply filters");
		applyFilters.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(2, 0, applyFilters);

		filteredMonsterList = new ListBox();
		filteredMonsterList.setStyleName("ribbonBarLabel");
		filteredMonsterList.setVisibleItemCount(1);
		centerGrid.setWidget(3, 0, filteredMonsterList);

		editSectionLabel = new Label("Edit Monster");
		editSectionLabel.setStyleName("sessionLabel");
		centerGrid.setWidget(4, 0, editSectionLabel);
		element = centerGrid.getCellFormatter().getElement(4, 0);
		element.setAttribute("colspan", "3");

		monsterNameLabel = new Label("Monster Name: ");
		monsterNameLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(5, 0, monsterNameLabel);
		monsterName = new TextBox();
		monsterName.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(5, 1, monsterName);

		monsterPictureLabel = new Label("Monster Picture: ");
		monsterPictureLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(6, 0, monsterPictureLabel);
		monsterPicture = new TextBox();
		monsterPicture.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(6, 1, monsterPicture);

		pogPanel = new FlowPanel();
		centerGrid.setWidget(7, 0, pogPanel);
		pogCanvas = new PogCanvas();
		pogPanel.add(pogCanvas);
	}

	private void setupEventHandlers() {
	}

	private void initialize() {
		pogData = ServiceManager.getDungeonManager().createMonster();
		pogCanvas.setPogData(pogData);
		monsterName.setValue("Enter Monster Name");
		monsterName.removeStyleName("badLabel");
		monsterPicture.setValue("URL of Monster Picture");
		monsterPicture.removeStyleName("badLabel");
		filInFilters();
		validateForm();
	}

	private void filInFilters() {
		getRaceList();
		getClassList();
		getGenderList();
	}

	private void getRaceList() {
		raceList.clear();
		raceList.addItem("Select Race");
		for (String race : ServiceManager.getDungeonManager().getMonsterRaces()) {
			raceList.addItem(race);
		}
	}

	private void getClassList() {
		classList.clear();
		classList.addItem("Select Class");
		for (String pogClass : ServiceManager.getDungeonManager().getMonsterClasses()) {
			classList.addItem(pogClass);
		}
	}

	private void getGenderList() {
		sexList.clear();
		sexList.addItem("Select Class");
		for (String pogClass : ServiceManager.getDungeonManager().getMonsterGenders()) {
			sexList.addItem(pogClass);
		}
	}

	@Override
	public void show() {
		super.show();
		initialize();
		center();
	}

	private void validateForm() {
	}

	@Override
	protected void onCancelClick(ClickEvent event) {
		close();
	}

	public void close() {
		hide();
	}
}
