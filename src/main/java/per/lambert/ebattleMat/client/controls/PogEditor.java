/*
 * Copyright (C) 2023 Leon Lambert.
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

import java.util.Collection;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import per.lambert.ebattleMat.client.controls.ribbonBar.SelectedPog;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.Constants;
import per.lambert.ebattleMat.client.interfaces.DungeonMasterFlag;
import per.lambert.ebattleMat.client.interfaces.FlagBit;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.PlayerFlag;
import per.lambert.ebattleMat.client.interfaces.PogPlace;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

public class PogEditor extends DockLayoutPanel {
	/**
	 * button bar at top.
	 */
	private HorizontalPanel buttonBar = new HorizontalPanel();
	/**
	 * Create new pog button.
	 */
	private Button createNewPogButton;
	/**
	 * Create remove pog button.
	 */
	private Button removePogButton;
	/**
	 * Panel to hold center content.
	 */
	private LayoutPanel centerContent;
	/**
	 * Grid for sub-classes to use for content.
	 */
	private Grid centerGrid;
	/**
	 * label for Template name.
	 */
	private Label pogNameLabel;
	/**
	 * text box for template name.
	 */
	private TextBox pogName;
	/**
	 * Control to show selected pog.
	 */
	private SelectedPog selectedPog;
	/**
	 * label for Pog type.
	 */
	private Label pogTypeLabel;
	/**
	 * List of pog type.
	 */
	private ListBox pogTypeList;
	/**
	 * label for Pog location.
	 */
	private Label pogLocationLabel;
	/**
	 * List of pog locations.
	 */
	private ListBox pogLocationList;
	/**
	 * Use to copy currently selected picture URL.
	 */
	private Button copyResourceURL;
	/**
	 * URL of level picture.
	 */
	private TextBox pictureURL;
	/**
	 * Scroll panel.
	 */
	private ScrollPanel scrollPanel = new ScrollPanel();
	/**
	 * Pog data.
	 */
	private PogData pogData;
	/**
	 * Form has changed.
	 */
	private boolean isDirty;
	/**
	 * Window for handling notes.
	 */
	private NotesFloatingWindow notesWindow;
	/**
	 * Show notes window.
	 */
	private Button showNotesWindow;
	/**
	 * Notes from host.
	 */
	private String notes;
	/**
	 * DM notes from host.
	 */
	private String dmNotes;
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
	 * List of pog sizes.
	 */
	private ListBox size;
	/**
	 * Save level information.
	 */
	private Button save;
	/**
	 * cancel Edits.
	 */
	private Button cancel;

	public PogEditor() {
		super(Unit.PX);
		setSize("100%", "100%");
		createContent();
		setupEventHandling();
	}

	private void createContent() {
		createNewPogButton = new Button("Create Pog");
		createNewPogButton.addStyleName("ribbonBarLabel");
		createNewPogButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				createPog();
			}
		});
		buttonBar.add(createNewPogButton);

		removePogButton = new Button("DELETE Pog");
		removePogButton.addStyleName("ribbonBarLabel");
		removePogButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				deletePog();
			}
		});
		buttonBar.add(removePogButton);
		ResizableDialog.enableWidget(removePogButton, false);

		addNorth(buttonBar, 30);
		createPogEditor();
		add(centerContent);
		forceLayout();
	}

	private void createPogEditor() {
		centerContent = new LayoutPanel();
		centerContent.setHeight("100%");
		centerContent.setWidth("100%");
		centerGrid = new Grid();
		centerGrid.setWidth("100%");
		centerGrid.resize(8, 2);
		centerGrid.getColumnFormatter().setWidth(0, "100px");
		selectedPog = new SelectedPog(null);
		VerticalPanel vpanel = new VerticalPanel();
		vpanel.add(centerGrid);
		vpanel.add(selectedPog);
		vpanel.add(scrollPanel);
		centerContent.add(vpanel);
		createGridContent();
	}

	private void createGridContent() {
		createPogName();
		createPogType();
		createPogLocation();
		createPictureUrl();
		createNotesWindow();
		createSizeControls();
		createPlayerFlags();
		createDMFlags();
		createSaveAndCancelButtons();
	}

	/**
	 * Create template name.
	 */
	private void createPogName() {
		pogNameLabel = new Label("Pog Name: ");
		pogNameLabel.setStyleName("ribbonBarLabel");
		pogName = new TextBox();
		pogName.setWidth("100%");
		pogName.setStyleName("ribbonBarLabel");
		pogName.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				pogName.selectAll();
			}
		});
		pogName.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(final KeyUpEvent event) {
				isDirty = true;
				validateForm();
			}
		});
		centerGrid.setWidget(0, 0, pogNameLabel);
		centerGrid.setWidget(0, 1, pogName);
	}

	/**
	 * Create pog type items.
	 */
	private void createPogType() {
		pogTypeLabel = new Label("Pog type");
		pogTypeLabel.setStyleName("ribbonBarLabel");
		pogTypeList = new ListBox();
		pogTypeList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(final ChangeEvent event) {
				isDirty = true;
				validateForm();
			}
		});
		pogTypeList.setStyleName("ribbonBarLabel");
		pogTypeList.setVisibleItemCount(1);
		pogTypeList.addItem(Constants.POG_TYPE_MONSTER);
		pogTypeList.addItem(Constants.POG_TYPE_ROOMOBJECT);
		pogTypeList.addItem(Constants.POG_TYPE_PLAYER);
		centerGrid.setWidget(1, 0, pogTypeLabel);
		centerGrid.setWidget(1, 1, pogTypeList);
	}

	/**
	 * Create pog location items.
	 */
	private void createPogLocation() {
		pogLocationLabel = new Label("Pog Location");
		pogLocationLabel.setStyleName("ribbonBarLabel");
		pogLocationList = new ListBox();
		pogLocationList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(final ChangeEvent event) {
				isDirty = true;
				validateForm();
			}
		});
		pogLocationList.setStyleName("ribbonBarLabel");
		pogLocationList.setVisibleItemCount(1);
		Collection<FlagBit> places = PogPlace.getValues();
		for (FlagBit flag : places) {
			pogLocationList.addItem(flag.getName());
		}
		centerGrid.setWidget(2, 0, pogLocationLabel);
		centerGrid.setWidget(2, 1, pogLocationList);
	}
	/**
	 * Content for handling picture url.
	 */
	private void createPictureUrl() {
		copyResourceURL = new Button("Use Select picture resource");
		copyResourceURL.setStyleName("ribbonBarLabel");
		copyResourceURL.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				copyResourceURL();
				urlChanged();
			}
		});
		pictureURL = new TextBox();
		pictureURL.addChangeHandler(new ChangeHandler() {		
			@Override
			public void onChange(final ChangeEvent event) {
				urlChanged();
			}
		});
		pictureURL.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(final KeyUpEvent event) {
				urlChanged();
			}
		});
		pictureURL.setWidth("100%");
		pictureURL.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(3, 0, copyResourceURL);
		centerGrid.setWidget(3, 1, pictureURL);
	}

	private void createNotesWindow() {
		showNotesWindow = new Button("Edit Notes");
		showNotesWindow.setStyleName("ribbonBarLabel");
		showNotesWindow.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				editNotes();
			}
		});
		notesWindow = new NotesFloatingWindow();
		notesWindow.setModal(true);
		notesWindow.addSaveClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				pogNotesSaved();
			}
		});
		notesWindow.addCancelClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				notesWindow.hide();
			}
		});
		centerGrid.setWidget(4, 0, showNotesWindow);
	}

	/**
	 * Create pog size controls.
	 */
	private void createSizeControls() {
		size = new ListBox();
		size.setStyleName("ribbonBarLabel");
		size.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(final ChangeEvent event) {
				isDirty = true;
				validateForm();
			}
		});
		size.setVisibleItemCount(1);
		for (String sizeName : ServiceManager.getDungeonManager().getPogSizes()) {
			size.addItem(sizeName);
		}
		centerGrid.setWidget(4, 1, size);
	}

	/**
	 * Create controls for player flags.
	 */
	private void createPlayerFlags() {
		playerFlagsButton = new Button("Player flags");
		playerFlagsButton.setStyleName("ribbonBarLabel");
		playerFlagDialog = new FlagBitsDialog("Player Flags", PlayerFlag.getValues());
		playerFlagsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				playerFlagDialog.show();
			}
		});
		playerFlagDialog.addOkClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				isDirty = true;
				playerFlagDialog.hide();
				validateForm();
			}
		});
		centerGrid.setWidget(5, 0, playerFlagsButton);
	}

	/**
	 * create controls for DM flags.
	 */
	private void createDMFlags() {
		dmFlagsButton = new Button("DM flags");
		dmFlagsButton.setStyleName("ribbonBarLabel");
		dmFlagDialog = new FlagBitsDialog("Dungeon Master Flags", DungeonMasterFlag.getValues());
		dmFlagsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				dmFlagDialog.show();
			}
		});
		dmFlagDialog.addOkClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				isDirty = true;
				dmFlagDialog.hide();
				validateForm();
			}
		});
		centerGrid.setWidget(5, 1, dmFlagsButton);
	}

	/**
	 * Create save and cancel buttons.
	 */
	private void createSaveAndCancelButtons() {
		save = new Button("Save");
		save.setStyleName("ribbonBarLabel");
		save.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				saveFormData();
			}
		});
		cancel = new Button("Cancel");
		cancel.setStyleName("ribbonBarLabel");
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				cancelFormData();
			}
		});
		centerGrid.setWidget(6, 0, save);
		centerGrid.setWidget(6, 1, cancel);
	}

	private void saveFormData() {
		isDirty = false;
		validateForm();
		getDialogData();
		ServiceManager.getDungeonManager().addOrUpdatePog(pogData, PogPlace.valueOf(pogLocationList.getSelectedItemText()));
		ServiceManager.getDungeonManager().setSelectedPog(pogData);
	}

	private void cancelFormData() {
		selectPog();
	}

	/**
	 * Url has changed.
	 */
	private void urlChanged() {
		isDirty = true;
		validateForm();
		pictureURL.setTitle(pictureURL.getText());
	}

	private void editNotes() {
		notesWindow.show();
	}

	private void pogNotesSaved() {
		notes = notesWindow.getNotesText();
		dmNotes = notesWindow.getDMNotesText();
		notesWindow.hide();
		isDirty = true;
		validateForm();
	}

	/**
	 * copy selected picture url.
	 */
	private void copyResourceURL() {
		String url = ServiceManager.getDungeonManager().getAssetURL();
		if (ServiceManager.getDungeonManager().isValidPictureURL(url)) {
			pictureURL.setText(url);
		}
	}
	private void setupEventHandling() {
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.DungeonDataLoaded) {
					dungeonDataLoaded();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.SessionDataSaved) {
					dungeonDataLoaded();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.PogWasSelected) {
					selectPog();
					return;
				}
			}
		});
	}

	private void selectPog() {
		isDirty = false;
		PogData pog = ServiceManager.getDungeonManager().getSelectedPog();
		if (pog == null) {
			createPog();
			return;
		}
		pogData = pog.clone();
		pogData.setUUID(pog.getUUID());
		setupPogData();
	}

	/**
	 * Set pog data into form.
	 */
	private void setupPogData() {
		pogName.setValue(pogData.getName());
		setPogType();
		setPogLocation();
		setPictureData();
		setNotesData();
		setSizeData();
		playerFlagDialog.setBits(pogData.getPlayerFlags());
		dmFlagDialog.setBits(pogData.getDungeonMasterFlags());
		validateForm();
	}

	/**
	 * Set pog type data.
	 */
	private void setPogType() {
		String type = pogData.getType();
		if (type == Constants.POG_TYPE_ROOMOBJECT) {
			pogTypeList.setSelectedIndex(1);
		} else if (type == Constants.POG_TYPE_PLAYER) {
			pogTypeList.setSelectedIndex(2);
		} else {
			pogTypeList.setSelectedIndex(0);
		}
	}

	/**
	 * set pog location data.
	 */
	private void setPogLocation() {
		PogPlace place = ServiceManager.getDungeonManager().computePlace(pogData);
		pogLocationList.setSelectedIndex(place.getValue());
	}

	private void setPictureData() {
		pictureURL.setText(pogData.getImageUrl());
		pictureURL.setTitle(pictureURL.getText());
	}
	private void setNotesData() {
		notes = pogData.getNotes();
		dmNotes = pogData.getDmNotes();
		notesWindow.setNotesText(notes);
		notesWindow.setDMNotesText(dmNotes);
	}

	private void setSizeData() {
		int pogSize = pogData.getSize() - 1;
		if (pogSize < 0) {
			pogSize = 0;
		}
		size.setSelectedIndex(pogSize);
	}

	private void dungeonDataLoaded() {
	}

	private void createPog() {
		pogData = ServiceManager.getDungeonManager().createTemplatePog(Constants.POG_TYPE_MONSTER);
		setupPogData();
	}

	private void deletePog() {
		ServiceManager.getDungeonManager().deleteSelectedPog();
	}

	private void validateForm() {
		boolean isOK = true;
		if (!ServiceManager.getDungeonManager().isValidNewMonsterName(pogName.getValue())) {
			isOK = false;
			pogNameLabel.addStyleName("badLabel");
		} else {
			pogNameLabel.removeStyleName("badLabel");
		}
		pogData.setImageUrl(pictureURL.getText());
		selectedPog.setPogData(pogData);
		if (!ServiceManager.getDungeonManager().isValidPictureURL(pictureURL.getText())) {
			isOK = false;
			pictureURL.addStyleName("badLabel");
		} else {
			pictureURL.removeStyleName("badLabel");
		}
		selectedPog.setPreventDrag(!isOK);
		ResizableDialog.enableWidget(save, isOK && isDirty);
		ResizableDialog.enableWidget(cancel, isDirty);
		ResizableDialog.enableWidget(removePogButton, isOK);
		ResizableDialog.enableWidget(createNewPogButton, !isDirty);
	}
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void onResize() {
		super.onResize();
		int width = getOffsetWidth() - 120;
		centerGrid.getColumnFormatter().setWidth(1, width + "px");
	}
	/**
	 * Get data from dialog.
	 */
	private void getDialogData() {
		pogData.setName(pogName.getValue());
		pogData.setImageUrl(pictureURL.getValue());
		pogData.setSize(size.getSelectedIndex() + 1);
		pogData.setPlayerFlagsNative(playerFlagDialog.getBits());
		pogData.setDungeonMasterFlagsNative(dmFlagDialog.getBits());
		pogData.setNotes(notes);
		pogData.setDmNotes(dmNotes);
	}
}
