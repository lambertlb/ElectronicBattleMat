/*
 * Copyright (C) 2019 Leon Lambert.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import per.lambert.ebattleMat.client.interfaces.Constants;
import per.lambert.ebattleMat.client.interfaces.DungeonMasterFlag;
import per.lambert.ebattleMat.client.interfaces.Gender;
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
	 * Button for player flags.
	 */
	private Button playerFlagsButton;
	/**
	 * Dialog for player flags.
	 */
	private FlagBitsDialog playerFlagDialog;
	/**
	 * Button for DM flags.
	 */
	private Button dmFlagsButton;
	/**
	 * Dialog for DM flags.
	 */
	private FlagBitsDialog dmFlagDialog;
	/**
	 * Call up notes.
	 */
	private Button notesButton;
	/**
	 * Dialog for notes.
	 */
	private NotesFloatingWindow notesDialog;

	/**
	 * selected pog is a template.
	 */
	private boolean templateSelected;

	/**
	 * Dialog for managing pog.
	 */
	private ManagePogDialog	managePogDialog;
	/**
	 * Constructor.
	 * 
	 * @param place Which pog templates to edit.
	 * @param pogType type of pog templates
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
		centerGrid.resize(11, 3);
		centerGrid.getColumnFormatter().setWidth(0, "20px");
		centerGrid.getColumnFormatter().setWidth(1, "20px");
		createSectionSelection();
		createRaceList();
		createClassList();
		createGenderList();
		createApplyFilters();
		createTemplateList();
		createSectionLabel();
		createTemplateName();
		createTemplatePicture();
		createRaceText();
		createClassText();
		createGenderText();
		createSizeControls();
		createNewTemplate();
		createPlayerFlags();
		createDMFlags();
		createNotesDialog();
		createManagePogDialog();
		pogPanel = new FlowPanel();
		centerGrid.setWidget(10, 0, pogPanel);
		pogCanvas = new PogCanvas();
		pogCanvas.setShowNormalSizeOnly(true);
		pogCanvas.setForceBackgroundColor(true);
		pogPanel.add(pogCanvas);
	}

	private void createManagePogDialog() {
		managePogDialog = new ManagePogDialog();
	}

	/**
	 * Create controls for notes dialog.
	 */
	private void createNotesDialog() {
		notesButton = new Button("Notes");
		notesButton.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(9, 2, notesButton);
		notesDialog = new NotesFloatingWindow();
		notesDialog.setModal(true);
		notesDialog.getElement().getStyle().setZIndex(Constants.DIALOG_Z + 1);
	}

	/**
	 * create controls for DM flags.
	 */
	private void createDMFlags() {
		dmFlagsButton = new Button("DM flags");
		dmFlagsButton.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(9, 1, dmFlagsButton);
		dmFlagDialog = new FlagBitsDialog("Dungeon Master Flags", DungeonMasterFlag.getValues());
	}

	/**
	 * Create controls for player flags.
	 */
	private void createPlayerFlags() {
		playerFlagsButton = new Button("Player flags");
		playerFlagsButton.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(9, 0, playerFlagsButton);
		playerFlagDialog = new FlagBitsDialog("Player Flags", PlayerFlag.getValues());
	}

	/**
	 * create new template button.
	 */
	private void createNewTemplate() {
		startNewTemplate = new Button("Create New Template");
		startNewTemplate.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(8, 2, startNewTemplate);
	}

	/**
	 * Create pog size controls.
	 */
	private void createSizeControls() {
		sizeLabel = new Label("Pog Size");
		sizeLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(8, 0, sizeLabel);
		size = new ListBox();
		size.setStyleName("ribbonBarLabel");
		size.setVisibleItemCount(1);
		centerGrid.setWidget(8, 1, size);
	}

	/**
	 * Create gender text.
	 */
	private void createGenderText() {
		gender = new ListBox();
		gender.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(7, 2, gender);
	}

	/**
	 * Create class text.
	 */
	private void createClassText() {
		templateClass = new TextBox();
		templateClass.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(7, 1, templateClass);
	}

	/**
	 * create race text.
	 */
	private void createRaceText() {
		race = new TextBox();
		race.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(7, 0, race);
	}

	/**
	 * create template picture controls.
	 */
	private void createTemplatePicture() {
		Element element;
		templatePictureLabel = new Label("Template Picture: ");
		templatePictureLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(6, 0, templatePictureLabel);
		templatePicture = new TextBox();
		templatePicture.setStyleName("ribbonBarLabel");
		templatePicture.setWidth("100%");
		centerGrid.setWidget(6, 1, templatePicture);
		element = centerGrid.getCellFormatter().getElement(6, 1);
		element.setAttribute("colspan", "2");
	}

	/**
	 * Create template name.
	 */
	private void createTemplateName() {
		templateNameLabel = new Label("Template Name: ");
		templateNameLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(5, 0, templateNameLabel);
		templateName = new TextBox();
		templateName.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(5, 1, templateName);
	}

	/**
	 * Create section label.
	 */
	private void createSectionLabel() {
		editSectionLabel = new Label("Edit Template");
		editSectionLabel.setStyleName("sessionLabel");
		centerGrid.setWidget(4, 0, editSectionLabel);
		Element element;
		element = centerGrid.getCellFormatter().getElement(4, 0);
		element.setAttribute("colspan", "3");
	}

	/**
	 * create template list.
	 */
	private void createTemplateList() {
		filteredTemplateList = new ListBox();
		filteredTemplateList.setStyleName("ribbonBarLabel");
		filteredTemplateList.setVisibleItemCount(1);
		centerGrid.setWidget(3, 0, filteredTemplateList);
	}

	/**
	 * create apply filters button.
	 */
	private void createApplyFilters() {
		applyFilters = new Button("Apply filters");
		applyFilters.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(2, 0, applyFilters);
	}

	/**
	 * Create gender list.
	 */
	private void createGenderList() {
		genderList = new ListBox();
		genderList.setStyleName("ribbonBarLabel");
		genderList.setVisibleItemCount(1);
		centerGrid.setWidget(1, 2, genderList);
	}

	/**
	 * Create class list.
	 */
	private void createClassList() {
		classList = new ListBox();
		classList.setStyleName("ribbonBarLabel");
		classList.setVisibleItemCount(1);
		centerGrid.setWidget(1, 1, classList);
	}

	/**
	 * Create race list.
	 */
	private void createRaceList() {
		raceList = new ListBox();
		raceList.setStyleName("ribbonBarLabel");
		raceList.setVisibleItemCount(1);
		centerGrid.setWidget(1, 0, raceList);
	}

	/**
	 * Create section selection.
	 */
	private void createSectionSelection() {
		selectionSectionLabel = new Label("Select existing template");
		selectionSectionLabel.setStyleName("sessionLabel");
		centerGrid.setWidget(0, 0, selectionSectionLabel);
		Element element = centerGrid.getCellFormatter().getElement(0, 0);
		element.setAttribute("colspan", "3");
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
		playerFlagsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
//				playerFlagDialog.setBits(pogData.getPlayerFlags());
//				playerFlagDialog.show();
				managePogDialog.editPog(PogPlace.COMMON_RESOURCE, pogData);
			}
		});
		playerFlagDialog.addOkClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				pogData.setPlayerFlagsNative(playerFlagDialog.getBits());
			}
		});
		dmFlagsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				dmFlagDialog.setBits(pogData.getDungeonMasterFlags());
				dmFlagDialog.show();
			}
		});
		dmFlagDialog.addOkClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				pogData.setDungeonMasterFlagsNative(dmFlagDialog.getBits());
			}
		});
		notesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				notesDialog.show();
			}
		});
		notesDialog.addSaveClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				notesDialog.hide();
			}
		});
		notesDialog.addCancelClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				notesDialog.hide();
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
		fillInFilters();
		validateForm();
	}

	/**
	 * Setup for new pog.
	 */
	private void setForNewPog() {
		pogData = ServiceManager.getDungeonManager().createTemplatePog(pogType);
		templateSelected = true;
		pogCanvas.setPogData(pogData);
		templateName.setValue("Enter Template Name");
		templateName.removeStyleName("badLabel");
		templatePicture.setValue("URL of Template Picture");
		templatePicture.removeStyleName("badLabel");
		race.setValue("Enter Race");
		templateClass.setValue("Enter Class");
		setFlagsDialogData();
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
	private void fillInFilters() {
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
		for (String race : ServiceManager.getDungeonManager().getTemplateRaces(place, pogType)) {
			raceList.addItem(race, race);
		}
	}

	/**
	 * Get class list.
	 */
	private void getClassList() {
		classList.clear();
		classList.addItem("Select Class", "");
		for (String pogClass : ServiceManager.getDungeonManager().getTemplateClasses(place, pogType)) {
			classList.addItem(pogClass, pogClass);
		}
	}

	/**
	 * Get gender list.
	 */
	private void getGenderList() {
		genderList.clear();
		gender.clear();
		for (Gender pogGender : ServiceManager.getDungeonManager().getTemplateGenders()) {
			genderList.addItem(pogGender.getName(), pogGender.getName());
			gender.addItem(pogGender.getName(), pogGender.getName());
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
		selectPog();
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
	 * TODO move to dungeon manager
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
		pogCanvas.setPogSizing(computePogSize(), 0.0, 1.0);
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
			filteredTemplateList.addItem(template.getName(), template.getUUID());
		}
	}

	/**
	 * Template was selected from filtered list.
	 */
	protected void templateSelected() {
		ServiceManager.getDungeonManager().setSelectedTemplate(place, pogType, filteredTemplateList.getSelectedValue());
		selectPog();
	}

	/**
	 * Select this pog to edit.
	 * 
	 * @param data pog data
	 */
	private void selectPog(final PogData data) {
		pogData = data;
		pogCanvas.setPogData(data);
		templateName.setValue(pogData.getName());
		templatePicture.setValue(pogData.getImageUrl());
		race.setValue(pogData.getRace());
		templateClass.setValue(pogData.getPogClass());
		gender.setSelectedIndex(Gender.valueOf(pogData.getGender()).getValue());
		int pogSize = pogData.getSize() - 1;
		if (pogSize < 0) {
			pogSize = 0;
		}
		size.setSelectedIndex(pogSize);
		setFlagsDialogData();
		validateForm();
	}

	/**
	 * Set flags dialog data.
	 */
	private void setFlagsDialogData() {
		playerFlagDialog.setBits(pogData.getPlayerFlags());
		dmFlagDialog.setBits(pogData.getDungeonMasterFlags());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onOkClick(final ClickEvent event) {
		super.onOkClick(event);
		getDialogData();
		if (templateSelected) {
			ServiceManager.getDungeonManager().addOrUpdatePog(pogData, PogPlace.COMMON_RESOURCE);
		} else {
			ServiceManager.getDungeonManager().addOrUpdatePog(pogData);
		}
		ServiceManager.getDungeonManager().setSelectedPog(pogData);
		close();
	}

	/**
	 * Get data from dialog.
	 */
	private void getDialogData() {
		pogData.setName(templateName.getValue());
		pogData.setImageUrl(templatePicture.getValue());
		String raceString = race.getValue();
		if (!raceString.startsWith("Enter")) {
			pogData.setRace(raceString);
		}
		String classString = templateClass.getValue();
		if (!classString.startsWith("Enter")) {
			pogData.setPogClass(classString);
		}
		int index = gender.getSelectedIndex();
		String genderName = Gender.valueOf(index).getName();
		pogData.setGender(genderName);

		pogData.setSize(size.getSelectedIndex() + 1);
		pogData.setPlayerFlagsNative(playerFlagDialog.getBits());
		pogData.setDungeonMasterFlagsNative(dmFlagDialog.getBits());
	}

	/**
	 * Set selected pog if correct type.
	 */
	private void selectPog() {
		PogData pog = ServiceManager.getDungeonManager().getSelectedPog();
		if (pog == null) {
			return;
		}
		if (pog.getType() != pogType && !pog.isThisAPlayer()) {
			return;
		}
		templateSelected = ServiceManager.getDungeonManager().isTemplate(pog);
		selectPog(pog);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinWidth() {
		return 400;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinHeight() {
		return 400;
	}
}
