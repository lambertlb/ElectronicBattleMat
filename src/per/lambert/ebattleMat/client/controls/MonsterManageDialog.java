package per.lambert.ebattleMat.client.controls;

import java.util.ArrayList;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import per.lambert.ebattleMat.client.battleMatDisplay.PogCanvas;
import per.lambert.ebattleMat.client.interfaces.PlayerFlag;
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
	private TextBox race;
	private TextBox monsterClass;
	private ListBox gender;

	public MonsterManageDialog() {
		super("Manage Monsters", true, true, 400, 400);
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
		centerGrid.getColumnFormatter().setWidth(0, "20px");
		centerGrid.getColumnFormatter().setWidth(1, "20px");

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
		monsterPicture.setWidth("100%");
		centerGrid.setWidget(6, 1, monsterPicture);
		element = centerGrid.getCellFormatter().getElement(6, 1);
		element.setAttribute("colspan", "2");

		race = new TextBox();
		race.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(7, 0, race);

		monsterClass = new TextBox();
		monsterClass.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(7, 1, monsterClass);

		gender = new ListBox();
		gender.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(7, 2, gender);

		pogPanel = new FlowPanel();
		centerGrid.setWidget(8, 0, pogPanel);
		pogCanvas = new PogCanvas();
		pogPanel.add(pogCanvas);
	}

	private void setupEventHandlers() {
		applyFilters.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				applyFilters();
			}
		});
		filteredMonsterList.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				monsterSelected();
			}
		});
		monsterName.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				monsterName.selectAll();
			}
		});
		monsterName.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				validateForm();
			}
		});
		monsterPicture.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				monsterPicture.selectAll();
			}
		});
		monsterPicture.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				validateForm();
			}
		});
		race.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				race.selectAll();
			}
		});
		monsterClass.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				monsterClass.selectAll();
			}
		});
	}

	private void initialize() {
		pogData = ServiceManager.getDungeonManager().createMonster();
		pogCanvas.setPogData(pogData);
		monsterName.setValue("Enter Monster Name");
		monsterName.removeStyleName("badLabel");
		monsterPicture.setValue("URL of Monster Picture");
		monsterPicture.removeStyleName("badLabel");
		filteredMonsterList.clear();
		filteredMonsterList.addItem("Filter Monsters", "");
		race.setValue("Enter Race");
		monsterClass.setValue("Enter Class");
		setupGender();
		filInFilters();
		validateForm();
	}

	private void setupGender() {
		gender.clear();
		gender.addItem("Select Gender", "");
		gender.addItem("Male");
		gender.addItem("Female");
		gender.addItem("Neutral");
	}

	private void filInFilters() {
		getRaceList();
		getClassList();
		getGenderList();
	}

	private void getRaceList() {
		raceList.clear();
		raceList.addItem("Select Race", "");
		for (String race : ServiceManager.getDungeonManager().getMonsterRaces()) {
			raceList.addItem(race, race);
		}
	}

	private void getClassList() {
		classList.clear();
		classList.addItem("Select Class", "");
		for (String pogClass : ServiceManager.getDungeonManager().getMonsterClasses()) {
			classList.addItem(pogClass, pogClass);
		}
	}

	private void getGenderList() {
		sexList.clear();
		sexList.addItem("Select Gender", "");
		for (String pogGender : ServiceManager.getDungeonManager().getMonsterGenders()) {
			sexList.addItem(pogGender, pogGender);
		}
	}

	@Override
	public void show() {
		super.show();
		initialize();
		center();
	}

	private void validateForm() {
		boolean isValidMonsterName = validateMonsterName();
		boolean isValidUrl = validateUrl();
		enableOk(isValidMonsterName && isValidUrl);
	}

	private boolean validateUrl() {
		String filename = monsterPicture.getValue();
		int i = filename.lastIndexOf('.');
		String fileExtension = i > 0 ? filename.substring(i + 1) : "";
		boolean valid = fileExtension.equals("jpeg") || fileExtension.equals("jpg") || fileExtension.equals("png");
		if (valid) {
			monsterPicture.removeStyleName("badLabel");
		} else {
			monsterPicture.addStyleName("badLabel");
		}
		showPog(valid);
		return valid;
	}

	private void showPog(boolean valid) {
		pogCanvas.setPogWidth(150);
		pogCanvas.showImage(valid);
		if (valid) {
			pogCanvas.setPogImageUrl(monsterPicture.getValue());
		}
	}

	private boolean validateMonsterName() {
		boolean valid = ServiceManager.getDungeonManager().isValidNewMonsterName(monsterName.getValue());
		if (valid) {
			monsterName.removeStyleName("badLabel");
		} else {
			monsterName.addStyleName("badLabel");
		}
		return valid;
	}

	@Override
	protected void onCancelClick(ClickEvent event) {
		close();
	}

	public void close() {
		hide();
	}

	protected void applyFilters() {
		filteredMonsterList.clear();
		filteredMonsterList.addItem("Filter Monsters", "");
		ArrayList<PogData> filteredMonsters = ServiceManager.getDungeonManager().getFilteredMonsters(raceList.getSelectedValue(), classList.getSelectedValue(), sexList.getSelectedValue());
		for (PogData monster : filteredMonsters) {
			filteredMonsterList.addItem(monster.getPogName(), monster.getUUID());
		}
	}

	protected void monsterSelected() {
		ServiceManager.getDungeonManager().setSelectedMonster(filteredMonsterList.getSelectedValue());
		PogData data = ServiceManager.getDungeonManager().getSelectedPog();
		if (data == null) {
			return;
		}
		pogData = data;
		pogCanvas.setPogData(data);
		monsterName.setValue(pogData.getPogName());
		monsterPicture.setValue(pogData.getPogImageUrl());
		race.setValue(pogData.getRace());
		monsterClass.setValue(pogData.getPogClass());
		getGender();
		validateForm();
	}

	private void getGender() {
		if (pogData.isFlagSet(PlayerFlag.HAS_NO_SEX)) {
			gender.setSelectedIndex(3);
		} else if (pogData.isFlagSet(PlayerFlag.IS_FEMALE)) {
			gender.setSelectedIndex(2);
		} else {
			gender.setSelectedIndex(1);
		}
	}

	@Override
	protected void onOkClick(ClickEvent event) {
		super.onOkClick(event);
		getDialogData();
		ServiceManager.getDungeonManager().addOrUpdatePogResource(pogData);
		ServiceManager.getDungeonManager().setSelectedPog(pogData);
		close();
	}

	private void getDialogData() {
		pogData.setPogName(monsterName.getValue());
		pogData.setPogImageUrl(monsterPicture.getValue());
		String raceString = race.getValue();
		if (!raceString.startsWith("Enter")) {
			pogData.setRace(raceString);
		}
		String classString = monsterClass.getValue();
		if (!classString.startsWith("Enter")) {
			pogData.setPogClass(classString);
		}
		int index = gender.getSelectedIndex();
		if (index > 1) {
			pogData.clearFlags(PlayerFlag.HAS_NO_SEX);
			pogData.clearFlags(PlayerFlag.IS_FEMALE);
			pogData.setFlags(index == 3 ? PlayerFlag.HAS_NO_SEX : PlayerFlag.IS_FEMALE);
		}
	}
}
