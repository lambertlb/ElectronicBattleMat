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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
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
 * Dialog to manage pogs.
 * 
 * @author LLambert
 *
 */
public class ManagePogDialog extends OkCancelDialog {
	/**
	 * Place to put pog.
	 */
	private PogPlace place;
	/**
	 * Place to put pog.
	 */
	private PogPlace originalPlace;
	/**
	 * Pog to manage.
	 */
	private PogData pogToManage;
	/**
	 * Save changes button.
	 */
	private Button save;
	/**
	 * Cancel button.
	 */
	private Button cancel;
	/**
	 * Delete button.
	 */
	private Button delete;
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
	 * Panel for pog display.
	 */
	private FlowPanel pogPanel;
	/**
	 * Canvas for pog display.
	 */
	private PogCanvas pogCanvas;
	/**
	 * Disable call backs for controls.
	 */
	private boolean disableCallbacks;
	/**
	 * Is Template name valid.
	 */
	private boolean isValidTemplateName;
	/**
	 * Is Template picture valid.
	 */
	private boolean isValidTemplatePicture;
	/**
	 * Is race valid.
	 */
	private boolean isValidRace;
	/**
	 * Is class valid.
	 */
	private boolean isValidTemplateClass;
	/**
	 * Where to Store Pog Drop down.
	 */
	private ListBox whereToStore;
	/**
	 * Where to store Pog Label.
	 */
	private Label whereToStoreLabel;

	public ManagePogDialog() {
		super("Manage Pog", true, true, 400, 400);
		pogToManage = (PogData) JavaScriptObject.createObject().cast();
		load();
	}

	private void load() {
		createContent();
		initialize();
		initializeControls();
		setupEventHandlers();
	}

	private void createContent() {
		createGrid();
		addButtonSupport();
	}

	private void createGrid() {
		centerGrid = this.getCenterGrid();
		centerGrid.resize(11, 3);
		centerGrid.getColumnFormatter().setWidth(0, "20px");
		centerGrid.getColumnFormatter().setWidth(1, "20px");
		createTemplateName();
		createTemplatePicture();
		createRaceText();
		createClassText();
		createGenderText();
		createSizeControls();
		createPlayerFlags();
		createDMFlags();
		createNotesDialog();
		createPlaceToStore();
		pogPanel = new FlowPanel();
		centerGrid.setWidget(6, 0, pogPanel);
		pogCanvas = new PogCanvas();
		pogCanvas.setShowNormalSizeOnly(true);
		pogCanvas.setForceBackgroundColor(true);
		pogCanvas.showImage(false);
		pogPanel.add(pogCanvas);
		Element element;
		templatePictureLabel = new Label("Template Picture: ");
		element = centerGrid.getCellFormatter().getElement(6, 0);
		element.setAttribute("colspan", "3");
	}

	private void createPlaceToStore() {
		whereToStoreLabel = new Label("Where to Store Pog");
		centerGrid.setWidget(5, 0, whereToStoreLabel);
		whereToStore = new ListBox();
		centerGrid.setWidget(5, 1, whereToStore);
	}

	private void createTemplateName() {
		templateNameLabel = new Label("Pog Name: ");
		templateNameLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(0, 0, templateNameLabel);
		templateName = new TextBox();
		templateName.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(0, 1, templateName);
	}

	/**
	 * create template picture controls.
	 */
	private void createTemplatePicture() {
		Element element;
		templatePictureLabel = new Label("Template Picture: ");
		templatePictureLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(1, 0, templatePictureLabel);
		templatePicture = new TextBox();
		templatePicture.setStyleName("ribbonBarLabel");
		templatePicture.setWidth("100%");
		centerGrid.setWidget(1, 1, templatePicture);
		element = centerGrid.getCellFormatter().getElement(1, 1);
		element.setAttribute("colspan", "2");
	}

	/**
	 * Create gender text.
	 */
	private void createGenderText() {
		gender = new ListBox();
		gender.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(2, 2, gender);
		for (Gender pogGender : ServiceManager.getDungeonManager().getTemplateGenders()) {
			gender.addItem(pogGender.getName(), pogGender.getName());
		}
	}

	/**
	 * Create class text.
	 */
	private void createClassText() {
		templateClass = new TextBox();
		templateClass.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(2, 1, templateClass);
	}

	/**
	 * create race text.
	 */
	private void createRaceText() {
		race = new TextBox();
		race.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(2, 0, race);
	}

	private void createSizeControls() {
		sizeLabel = new Label("Pog Size");
		sizeLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(3, 0, sizeLabel);
		size = new ListBox();
		size.setStyleName("ribbonBarLabel");
		size.setVisibleItemCount(1);
		centerGrid.setWidget(3, 1, size);
		for (String sizeName : ServiceManager.getDungeonManager().getPogSizes()) {
			size.addItem(sizeName);
		}
	}

	/**
	 * Create controls for notes dialog.
	 */
	private void createNotesDialog() {
		notesButton = new Button("Notes");
		notesButton.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(4, 2, notesButton);
		notesDialog = new NotesFloatingWindow();
		notesDialog.setModal(true);
		notesDialog.getElement().getStyle().setZIndex(Constants.DIALOG_Z + 2);
		notesDialog.center();
		notesDialog.hide();
	}

	/**
	 * create controls for DM flags.
	 */
	private void createDMFlags() {
		dmFlagsButton = new Button("DM flags");
		dmFlagsButton.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(4, 1, dmFlagsButton);
		dmFlagDialog = new FlagBitsDialog("Dungeon Master Flags", DungeonMasterFlag.getValues());
		dmFlagDialog.getElement().getStyle().setZIndex(Constants.DIALOG_Z + 2);
	}

	/**
	 * Create controls for player flags.
	 */
	private void createPlayerFlags() {
		playerFlagsButton = new Button("Player flags");
		playerFlagsButton.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(4, 0, playerFlagsButton);
		playerFlagDialog = new FlagBitsDialog("Player Flags", PlayerFlag.getValues());
		playerFlagDialog.getElement().getStyle().setZIndex(Constants.DIALOG_Z + 2);
	}

	/**
	 * Add button support.
	 */
	private void addButtonSupport() {
		save = this.getOk();
		save.setText("Save");
		save.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				onSave();
			}
		});
		cancel = this.getCancel();
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				onCancel();
			}
		});
		delete = new Button("Delete");
		delete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				onDelete();
			}
		});
		this.addButton(delete);
	}

	private void initialize() {
	}

	private void setupEventHandlers() {
		pogCanvas.getImage().addLoadHandler(new LoadHandler() {
			public void onLoad(final LoadEvent event) {
				imageLoaded();
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
				showPog(validateUrl());
			}
		});
		size.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(final ChangeEvent event) {
				onSizeChanged();
			}
		});
		race.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				race.selectAll();
			}
		});
		race.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(final KeyUpEvent event) {
				validateForm();
			}
		});
		gender.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(final ChangeEvent event) {
				onGenderChanged();
			}
		});
		templateClass.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				templateClass.selectAll();
			}
		});
		templateClass.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(final KeyUpEvent event) {
				validateForm();
			}
		});
		playerFlagsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				playerFlagDialog.setBits(pogToManage.getPlayerFlags());
				playerFlagDialog.show();
			}
		});
		playerFlagDialog.addOkClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				pogToManage.setPlayerFlagsNative(playerFlagDialog.getBits());
			}
		});
		dmFlagsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				dmFlagDialog.setBits(pogToManage.getDungeonMasterFlags());
				dmFlagDialog.show();
			}
		});
		dmFlagDialog.addOkClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				pogToManage.setDungeonMasterFlagsNative(dmFlagDialog.getBits());
			}
		});
		notesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				notesDialog.show();
				notesDialog.center();
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
		whereToStore.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(final ChangeEvent event) {
				onPlaceChange(event);
			}
		});
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
	 * Edit this pog.
	 * 
	 * @param place place of pag
	 * @param pogData data for pog
	 */
	public void editPog(final PogPlace place, final PogData pogData) {
		this.place = place;
		originalPlace = place;
		pogToManage = pogData.clone();
		pogToManage.setUUID(pogData.getUUID());
		getElement().getStyle().setZIndex(Constants.DIALOG_Z + 1);
		setupDialogData(pogToManage);
		show();
	}

	private void setupDialogData(final PogData pogData) {
		pogCanvas.showImage(false);
		initializeControls();
		disableCallbacks = true;
		if (pogData.getName() == "") {
			templateName.setText("Enter Pog Name");
		} else {
			templateName.setText(pogData.getName());
		}
		if (pogData.getImageUrl() == "") {
			templatePicture.setText("Url for picture");
		} else {
			templatePicture.setText(pogData.getImageUrl());
		}
		gender.setSelectedIndex(Gender.valueOf(pogData.getGender()).getValue());
		if (pogData.getRace() == "") {
			race.setText("Enter Race");
		} else {
			race.setText(pogData.getRace());
		}
		if (pogData.getPogClass() == "") {
			templateClass.setText("Enter Class");
		} else {
			templateClass.setText(pogData.getPogClass());
		}
		int pogSize = pogData.getSize() - 1;
		if (pogSize < 0) {
			pogSize = 0;
		}
		size.setSelectedIndex(pogSize);
		pogCanvas.setPogData(pogData);
		setupWhereToStore();
		disableCallbacks = false;
		showPog(validateUrl());
		validateForm();
	}

	private void setupWhereToStore() {
		boolean editingDungeon = ServiceManager.getDungeonManager().isEditMode();
		whereToStore.clear();
		whereToStore.addItem("Common Resource", "" + PogPlace.COMMON_RESOURCE);
		whereToStore.addItem("Dungeon Resource", "" + PogPlace.DUNGEON_RESOURCE);
		whereToStore.addItem("Dungeon Instance", "" + PogPlace.DUNGEON_INSTANCE);
		if (!editingDungeon) {
			whereToStore.addItem("Session Resource", "" + PogPlace.SESSION_RESOURCE);
			whereToStore.addItem("Session Instance", "" + PogPlace.SESSION_INSTANCE);
		}
		whereToStore.setSelectedIndex(place.ordinal());
	}

	/**
	 * Compute area pog can have.
	 * 
	 * @return area pog can have.
	 */
	private int computePogSize() {
		int pogTop = pogCanvas.getAbsoluteTop();
		int okTop = save.getAbsoluteTop();
		int deltaTop = okTop - pogTop;
		if (deltaTop < 50) {
			return (50);
		}
		return deltaTop - 10;
	}

	@Override
	public final void onLoad() {
		super.onLoad();
		pogCanvas.setPogSizing(computePogSize(), 0.0, 1.0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onWindowResized() {
		super.onWindowResized();
		pogCanvas.setPogSizing(computePogSize(), 0.0, 1.0);
	}

	/**
	 * Save pog data.
	 */
	protected void onSave() {
		updatePogData();
		if (place != originalPlace) {
			pogToManage.setUUID(Constants.generateUUID());
		}
		ServiceManager.getDungeonManager().addOrUpdatePog(pogToManage, place);
		hide();
	}

	private void updatePogData() {
		pogToManage.setName(templateName.getValue());
		pogToManage.setImageUrl(templatePicture.getValue());
		String raceString = race.getValue();
		if (!raceString.startsWith("Enter")) {
			pogToManage.setRace(raceString);
		}
		String classString = templateClass.getValue();
		if (!classString.startsWith("Enter")) {
			pogToManage.setPogClass(classString);
		}
	}
	private void onGenderChanged() {
		int index = gender.getSelectedIndex();
		String genderName = Gender.valueOf(index).getName();
		pogToManage.setGender(genderName);
	}
	private void onSizeChanged() {
		pogToManage.setSize(size.getSelectedIndex() + 1);
	}

	/**
	 * Cancel Edits.
	 */
	protected void onCancel() {
		hide();
	}

	/**
	 * Delete pog.
	 */
	protected void onDelete() {
		ServiceManager.getDungeonManager().deletePog(pogToManage, place);
		hide();
	}
	/**
	 * Place has changed.
	 * @param event of change
	 */
	protected void onPlaceChange(final ChangeEvent event) {
		String newPlaceString = whereToStore.getSelectedValue();
		place = Enum.valueOf(PogPlace.class, newPlaceString);
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

	private void initializeControls() {
		disableCallbacks = true;
		templateName.setText("Enter Template Name");
		templatePicture.setText("URL of Template Picture");
		gender.setSelectedIndex(0);
		race.setText("Enter Race");
		templateClass.setText("Enter Class");
		size.setSelectedIndex(0);
		isValidTemplateName = false;
		isValidTemplatePicture = false;
		isValidRace = false;
		isValidTemplateClass = false;
		showPog(false);
		enableControls();
		disableCallbacks = false;
	}

	private void enableControls() {
		enableWidget(templatePicture, isValidTemplateName);
		enableWidget(gender, isValidTemplatePicture);
		enableWidget(race, isValidTemplatePicture);
		enableWidget(templateClass, isValidTemplatePicture);
		enableWidget(size, isValidTemplatePicture);
		enableWidget(playerFlagsButton, isValidTemplatePicture);
		enableWidget(dmFlagsButton, isValidTemplatePicture);
		enableWidget(notesButton, isValidTemplatePicture);
		boolean everythingValid = isValidTemplateName && isValidTemplatePicture && isValidRace && isValidTemplateClass;
		enableWidget(save, everythingValid);
		enableWidget(delete, pogToManage.getUUID().length() > 0);

	}

	protected final void validateForm() {
		if (disableCallbacks) {
			return;
		}
		isValidTemplateName = validateTemplateName();
		if (isValidTemplateName) {
			isValidTemplateName = true;
		}
		validateUrl();
		validateRace();
		validateClass();
		enableControls();
	}

	private void validateClass() {
		String classString = templateClass.getValue();
		isValidTemplateClass = !classString.startsWith("Enter") && classString.length() >= 3;
		if (isValidTemplateClass) {
			templateClass.removeStyleName("badLabel");
		} else {
			templateClass.addStyleName("badLabel");
		}
	}

	private void validateRace() {
		String raceString = race.getValue();
		isValidRace = !raceString.startsWith("Enter") && raceString.length() >= 3;
		if (isValidRace) {
			race.removeStyleName("badLabel");
		} else {
			race.addStyleName("badLabel");
		}
	}

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
		return valid;
	}

	/**
	 * Show pog.
	 * 
	 * @param valid true if image if valid
	 */
	protected void showPog(final boolean valid) {
		pogCanvas.setPogSizing(computePogSize(), 0.0, 1.0);
		pogCanvas.showImage(valid);
		if (valid) {
			pogCanvas.setPogImageUrl(templatePicture.getValue());
		}
	}

	private boolean validateTemplateName() {
		boolean valid = ServiceManager.getDungeonManager().isValidNewMonsterName(templateName.getValue());
		if (valid) {
			templateName.removeStyleName("badLabel");
		} else {
			templateName.addStyleName("badLabel");
		}
		return valid;
	}

	private void imageLoaded() {
		isValidTemplatePicture = validateUrl();
		validateForm();
	}

}
