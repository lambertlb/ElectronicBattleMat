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
import per.lambert.ebattleMat.client.interfaces.PogPlace;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

/**
 * Template manage dialog.
 * 
 * @author LLambert
 *
 */
public class TemplateManageDialog extends OkCancelDialog {

	/**
	 * grid for content.
	 */
	private Grid centerGrid;
	/**
	 * label for Template name.
	 */
	private Label templateNameLabel;
	/**
	 * text box for template name.
	 */
	private TextBox templateName;
	/**
	 * Label for template picture.
	 */
	private Label templatePictureLabel;
	/**
	 * template picture URL.
	 */
	private TextBox templatePicture;
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
	 * Which pog templates to use.
	 */
	private PogPlace place;
	/**
	 * Type of templates to use.
	 */
	private String pogType;
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
	 * List of templates that match the filter.
	 */
	private ListBox filteredTemplateList;
	/**
	 * Label for edit section of display.
	 */
	private Label editSectionLabel;
	/**
	 * Race of template.
	 */
	private TextBox race;
	/**
	 * Class of template.
	 */
	private TextBox templateClass;
	/**
	 * Gender of template.
	 */
	private ListBox gender;
	/**
	 * Label for size.
	 */
	private Label sizeLabel;
	/**
	 * List of pog sizes.
	 */
	private ListBox size;
	/**
	 * Start a new template.
	 */
	private Button startNewTemplate;

	/**
	 * Constructor.
	 * 
	 * @param place Which pog templates to edit.
	 * @param pogType type og pog templates
	 */
	public TemplateManageDialog(final PogPlace place, final String pogType) {
		super("Manage " + pogType + " Templates", true, true, 400, 400);
		this.place = place;
		this.pogType = pogType;
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

		selectionSectionLabel = new Label("Select existing template");
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

		filteredTemplateList = new ListBox();
		filteredTemplateList.setStyleName("ribbonBarLabel");
		filteredTemplateList.setVisibleItemCount(1);
		centerGrid.setWidget(3, 0, filteredTemplateList);

		editSectionLabel = new Label("Edit Template");
		editSectionLabel.setStyleName("sessionLabel");
		centerGrid.setWidget(4, 0, editSectionLabel);
		element = centerGrid.getCellFormatter().getElement(4, 0);
		element.setAttribute("colspan", "3");

		templateNameLabel = new Label("Template Name: ");
		templateNameLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(5, 0, templateNameLabel);
		templateName = new TextBox();
		templateName.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(5, 1, templateName);

		templatePictureLabel = new Label("Template Picture: ");
		templatePictureLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(6, 0, templatePictureLabel);
		templatePicture = new TextBox();
		templatePicture.setStyleName("ribbonBarLabel");
		templatePicture.setWidth("100%");
		centerGrid.setWidget(6, 1, templatePicture);
		element = centerGrid.getCellFormatter().getElement(6, 1);
		element.setAttribute("colspan", "2");

		race = new TextBox();
		race.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(7, 0, race);

		templateClass = new TextBox();
		templateClass.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(7, 1, templateClass);

		gender = new ListBox();
		gender.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(7, 2, gender);

		sizeLabel = new Label("Pog Size");
		sizeLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(8, 0, sizeLabel);

		size = new ListBox();
		size.setStyleName("ribbonBarLabel");
		size.setVisibleItemCount(1);
		centerGrid.setWidget(8, 1, size);

		startNewTemplate = new Button("Create New Template");
		startNewTemplate.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(8, 2, startNewTemplate);

		pogPanel = new FlowPanel();
		centerGrid.setWidget(9, 0, pogPanel);
		pogCanvas = new PogCanvas();
		pogCanvas.setShowNormalSizeOnly(true);
		pogCanvas.setForceBackgroundColor(true);
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
		filteredTemplateList.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(final ChangeEvent event) {
				templateSelected();
			}
		});
		templateName.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				templateName.selectAll();
			}
		});
		templateName.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(final KeyUpEvent event) {
				validateForm();
			}
		});
		templatePicture.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				templatePicture.selectAll();
			}
		});
		templatePicture.addKeyUpHandler(new KeyUpHandler() {

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
		templateClass.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				templateClass.selectAll();
			}
		});
		startNewTemplate.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				setForNewPog();
				filteredTemplateList.setSelectedIndex(0);
			}
		});
	}

	/**
	 * Initialize view.
	 * 
	 * Must be called every time view is shown.
	 */
	private void initialize() {
		setForNewPog();
		filteredTemplateList.clear();
		filteredTemplateList.addItem("Filter Templates", "");
		fillInSizes();
		filInFilters();
		validateForm();
	}

	/**
	 * Setup for noew pog.
	 */
	private void setForNewPog() {
		pogData = ServiceManager.getDungeonManager().createTemplatePog(pogType);
		pogCanvas.setPogData(pogData);
		templateName.setValue("Enter Template Name");
		templateName.removeStyleName("badLabel");
		templatePicture.setValue("URL of Template Picture");
		templatePicture.removeStyleName("badLabel");
		race.setValue("Enter Race");
		templateClass.setValue("Enter Class");
	}

	/**
	 * Fill in pog size list.
	 */
	private void fillInSizes() {
		size.clear();
		for (String sizeName : ServiceManager.getDungeonManager().getPogSizes()) {
			size.addItem(sizeName);
		}
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
		for (String race : ServiceManager.getDungeonManager().getTemplaceRaces(place, pogType)) {
			raceList.addItem(race, race);
		}
	}

	/**
	 * Get class list.
	 */
	private void getClassList() {
		classList.clear();
		classList.addItem("Select Class", "");
		for (String pogClass : ServiceManager.getDungeonManager().getTemplaceClasses(place, pogType)) {
			classList.addItem(pogClass, pogClass);
		}
	}

	/**
	 * Get gender list.
	 */
	private void getGenderList() {
		genderList.clear();
		gender.clear();
		genderList.addItem("Select Gender", "");
		gender.addItem("Select Gender", "");
		for (String pogGender : ServiceManager.getDungeonManager().getTemplateGenders()) {
			genderList.addItem(pogGender, pogGender);
			gender.addItem(pogGender, pogGender);
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
	 * {@inheritDoc}
	 */
	@Override
	protected void onWindowResized() {
		super.onWindowResized();
		validateUrl();
	}

	/**
	 * Validate form data.
	 */
	private void validateForm() {
		boolean isValidTemplateName = validateTemplateName();
		boolean isValidUrl = validateUrl();
		enableOk(isValidTemplateName && isValidUrl);
	}

	/**
	 * Validate URL.
	 * 
	 * @return true if valid
	 */
	private boolean validateUrl() {
		String filename = templatePicture.getValue();
		int i = filename.lastIndexOf('.');
		String fileExtension = i > 0 ? filename.substring(i + 1) : "";
		boolean valid = fileExtension.equals("jpeg") || fileExtension.equals("jpg") || fileExtension.equals("png");
		if (valid) {
			templatePicture.removeStyleName("badLabel");
		} else {
			templatePicture.addStyleName("badLabel");
		}
		showPog(valid);
		return valid;
	}

	/**
	 * Show pog.
	 * 
	 * @param valid true if image if valid
	 */
	private void showPog(final boolean valid) {
		pogCanvas.setPogWidth(computePogSize());
		pogCanvas.showImage(valid);
		if (valid) {
			pogCanvas.setPogImageUrl(templatePicture.getValue());
		}
	}

	/**
	 * Compute area pog can have.
	 * 
	 * @return area pog can have.
	 */
	private int computePogSize() {
		int pogTop = pogCanvas.getAbsoluteTop();
		int okTop = getOkTop();
		int deltaTop = okTop - pogTop;
		if (deltaTop < 50) {
			return (50);
		}
		return deltaTop - 10;
	}

	/**
	 * Validate template name.
	 * 
	 * @return true if valid
	 */
	private boolean validateTemplateName() {
		boolean valid = ServiceManager.getDungeonManager().isValidNewMonsterName(templateName.getValue());
		if (valid) {
			templateName.removeStyleName("badLabel");
		} else {
			templateName.addStyleName("badLabel");
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
		filteredTemplateList.clear();
		filteredTemplateList.addItem("Filter Templates", "");
		ArrayList<PogData> filteredTemplates = ServiceManager.getDungeonManager().getFilteredTemplates(place, pogType, raceList.getSelectedValue(), classList.getSelectedValue(), genderList.getSelectedValue());
		for (PogData template : filteredTemplates) {
			filteredTemplateList.addItem(template.getPogName(), template.getUUID());
		}
	}

	/**
	 * Template was selected from filtered list.
	 */
	protected void templateSelected() {
		ServiceManager.getDungeonManager().setSelectedTemplate(place, pogType, filteredTemplateList.getSelectedValue());
		PogData data = ServiceManager.getDungeonManager().getSelectedPog();
		if (data == null) {
			return;
		}
		pogData = data;
		pogCanvas.setPogData(data);
		templateName.setValue(pogData.getPogName());
		templatePicture.setValue(pogData.getPogImageUrl());
		race.setValue(pogData.getRace());
		templateClass.setValue(pogData.getPogClass());
		getGender();
		int pogSize = pogData.getPogSize() - 1;
		if (pogSize < 0) {
			pogSize = 0;
		}
		size.setSelectedIndex(pogSize);
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
		ServiceManager.getDungeonManager().addOrUpdatePog(pogData, place);
		ServiceManager.getDungeonManager().setSelectedPog(pogData);
		close();
	}

	/**
	 * Get data from dialog.
	 */
	private void getDialogData() {
		pogData.setPogName(templateName.getValue());
		pogData.setPogImageUrl(templatePicture.getValue());
		String raceString = race.getValue();
		if (!raceString.startsWith("Enter")) {
			pogData.setRace(raceString);
		}
		String classString = templateClass.getValue();
		if (!classString.startsWith("Enter")) {
			pogData.setPogClass(classString);
		}
		int index = gender.getSelectedIndex();
		if (index >= 1) {
			pogData.clearFlags(PlayerFlag.HAS_NO_GENDER);
			pogData.clearFlags(PlayerFlag.IS_FEMALE);
			if (index > 1) {
				pogData.setFlags(index == 3 ? PlayerFlag.HAS_NO_GENDER : PlayerFlag.IS_FEMALE);
			}
		}
		pogData.setPogSize(size.getSelectedIndex() + 1);
	}
}
