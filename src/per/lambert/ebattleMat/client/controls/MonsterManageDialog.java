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

/**
 * Monster manage dialog.
 * 
 * @author LLambert
 *
 */
public class MonsterManageDialog extends OkCancelDialog {

	/**
	 * grid for content.
	 */
	private Grid centerGrid;
	/**
	 * label for monster name.
	 */
	private Label monsterNameLabel;
	/**
	 * text box for monster name.
	 */
	private TextBox monsterName;
	/**
	 * Label for monster picture.
	 */
	private Label monsterPictureLabel;
	/**
	 * Monster picture URL.
	 */
	private TextBox monsterPicture;
	/**
	 * Panel for pog display.
	 */
	private FlowPanel pogPanel;
	/**
	 * Canvas for pog display.
	 */
	private PogCanvas pogCanvas;
	/**
	 * Pog data.
	 */
	private PogData pogData;
	/**
	 * Label for select section of display.
	 */
	private Label selectionSectionLabel;
	/**
	 * List of races for filter.
	 */
	private ListBox raceList;
	/**
	 * List of classes for filter.
	 */
	private ListBox classList;
	/**
	 * List of genders for filter.
	 */
	private ListBox genderList;
	/**
	 * Apply filters button.
	 */
	private Button applyFilters;
	/**
	 * List of monsters tha tmatch the filter.
	 */
	private ListBox filteredMonsterList;
	/**
	 * Label for edit section of display.
	 */
	private Label editSectionLabel;
	/**
	 * Race of monster.
	 */
	private TextBox race;
	/**
	 * Class of monster.
	 */
	private TextBox monsterClass;
	/**
	 * Gender of monster.
	 */
	private ListBox gender;

	/**
	 * Constructor.
	 */
	public MonsterManageDialog() {
		super("Manage Monsters", true, true, 400, 400);
		load();
	}

	/**
	 * Load in view.
	 */
	protected void load() {
		createContent();
		initialize();
		setupEventHandlers();
	}

	/**
	 * Create content.
	 */
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

		genderList = new ListBox();
		genderList.setStyleName("ribbonBarLabel");
		genderList.setVisibleItemCount(1);
		centerGrid.setWidget(1, 2, genderList);

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

	/**
	 * Setup event handlers.
	 */
	private void setupEventHandlers() {
		applyFilters.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				applyFilters();
			}
		});
		filteredMonsterList.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(final ChangeEvent event) {
				monsterSelected();
			}
		});
		monsterName.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				monsterName.selectAll();
			}
		});
		monsterName.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(final KeyUpEvent event) {
				validateForm();
			}
		});
		monsterPicture.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				monsterPicture.selectAll();
			}
		});
		monsterPicture.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(final KeyUpEvent event) {
				validateForm();
			}
		});
		race.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				race.selectAll();
			}
		});
		monsterClass.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				monsterClass.selectAll();
			}
		});
	}

	/**
	 * Initialize view.
	 * 
	 * Must be called every time view is shown.
	 */
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
		filInFilters();
		validateForm();
	}

	/**
	 * Fill in filters.
	 */
	private void filInFilters() {
		getRaceList();
		getClassList();
		getGenderList();
	}

	/**
	 * Get race list.
	 */
	private void getRaceList() {
		raceList.clear();
		raceList.addItem("Select Race", "");
		for (String race : ServiceManager.getDungeonManager().getMonsterRaces()) {
			raceList.addItem(race, race);
		}
	}

	/**
	 * Get class list.
	 */
	private void getClassList() {
		classList.clear();
		classList.addItem("Select Class", "");
		for (String pogClass : ServiceManager.getDungeonManager().getMonsterClasses()) {
			classList.addItem(pogClass, pogClass);
		}
	}

	/**
	 * Get gender list.
	 */
	private void getGenderList() {
		genderList.clear();
		genderList.addItem("Select Gender", "");
		for (String pogGender : ServiceManager.getDungeonManager().getMonsterGenders()) {
			genderList.addItem(pogGender, pogGender);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void show() {
		super.show();
		initialize();
		center();
	}

	/**
	 * Validate form data.
	 */
	private void validateForm() {
		boolean isValidMonsterName = validateMonsterName();
		boolean isValidUrl = validateUrl();
		enableOk(isValidMonsterName && isValidUrl);
	}

	/**
	 * Validate URL.
	 * @return true if valid
	 */
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

	/**
	 * Show pog.
	 * @param valid true if image if valid
	 */
	private void showPog(final boolean valid) {
		pogCanvas.setPogWidth(150);
		pogCanvas.showImage(valid);
		if (valid) {
			pogCanvas.setPogImageUrl(monsterPicture.getValue());
		}
	}

	/**
	 * Validate monster name.
	 * @return true if valid
	 */
	private boolean validateMonsterName() {
		boolean valid = ServiceManager.getDungeonManager().isValidNewMonsterName(monsterName.getValue());
		if (valid) {
			monsterName.removeStyleName("badLabel");
		} else {
			monsterName.addStyleName("badLabel");
		}
		return valid;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCancelClick(final ClickEvent event) {
		close();
	}

	/**
	 * Close dialog.
	 */
	public void close() {
		hide();
	}

	/**
	 * Apply filters.
	 */
	protected void applyFilters() {
		filteredMonsterList.clear();
		filteredMonsterList.addItem("Filter Monsters", "");
		ArrayList<PogData> filteredMonsters = ServiceManager.getDungeonManager().getFilteredMonsters(raceList.getSelectedValue(), classList.getSelectedValue(), genderList.getSelectedValue());
		for (PogData monster : filteredMonsters) {
			filteredMonsterList.addItem(monster.getPogName(), monster.getUUID());
		}
	}

	/**
	 * Monster was selected from filtered list.
	 */
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

	/**
	 * Get gender.
	 */
	private void getGender() {
		if (pogData.isFlagSet(PlayerFlag.HAS_NO_GENDER)) {
			gender.setSelectedIndex(3);
		} else if (pogData.isFlagSet(PlayerFlag.IS_FEMALE)) {
			gender.setSelectedIndex(2);
		} else {
			gender.setSelectedIndex(1);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onOkClick(final ClickEvent event) {
		super.onOkClick(event);
		getDialogData();
		ServiceManager.getDungeonManager().addOrUpdatePogResource(pogData);
		ServiceManager.getDungeonManager().setSelectedPog(pogData);
		close();
	}

	/**
	 * Get data from dialog.
	 */
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
			pogData.clearFlags(PlayerFlag.HAS_NO_GENDER);
			pogData.clearFlags(PlayerFlag.IS_FEMALE);
			pogData.setFlags(index == 3 ? PlayerFlag.HAS_NO_GENDER : PlayerFlag.IS_FEMALE);
		}
	}
}
