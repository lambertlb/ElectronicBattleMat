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
import per.lambert.ebattleMat.client.interfaces.FlagBit;
import per.lambert.ebattleMat.client.interfaces.Gender;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
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
	 * Race of template.
	 */
	private TextBox race;
	/**
	 * Label for race;.
	 */
	private Label raceLabel;
	/**
	 * Class of template.
	 */
	private TextBox pogClass;
	/**
	 * Label for class;.
	 */
	private Label pogClassLabel;
	/**
	 * List of genders.
	 */
	private ListBox genderList;
	/**
	 * Label for race;.
	 */
	private Label genderLabel;
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
		centerGrid.resize(10, 2);
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
		createRace();
		createClass();
		createGender();
		createNotesWindow();
	}

	/**
	 * Create template name.
	 */
	private void createPogName() {
		pogNameLabel = new Label("Pog Name: ");
		pogNameLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(0, 0, pogNameLabel);
		pogName = new TextBox();
		pogName.setWidth("100%");
		pogName.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(0, 1, pogName);
		pogName.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				pogName.selectAll();
			}
		});
		pogName.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(final KeyUpEvent event) {
				validateForm();
			}
		});
	}

	/**
	 * Create pog type items.
	 */
	private void createPogType() {
		pogTypeLabel = new Label("Pog type");
		pogTypeLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(1, 0, pogTypeLabel);
		pogTypeList = new ListBox();
		pogTypeList.setStyleName("ribbonBarLabel");
		pogTypeList.setVisibleItemCount(1);
		centerGrid.setWidget(1, 1, pogTypeList);
		pogTypeList.addItem(Constants.POG_TYPE_MONSTER);
		pogTypeList.addItem(Constants.POG_TYPE_ROOMOBJECT);
		pogTypeList.addItem(Constants.POG_TYPE_PLAYER);
	}

	/**
	 * Create pog location items.
	 */
	private void createPogLocation() {
		pogLocationLabel = new Label("Pog Location");
		pogLocationLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(2, 0, pogLocationLabel);
		pogLocationList = new ListBox();
		pogLocationList.setStyleName("ribbonBarLabel");
		pogLocationList.setVisibleItemCount(1);
		centerGrid.setWidget(2, 1, pogLocationList);
		Collection<FlagBit> places = PogPlace.getValues();
		for (FlagBit flag : places) {
			pogLocationList.addItem(flag.getName());
		}
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

	/**
	 * Create race text.
	 */
	private void createRace() {
		raceLabel = new Label("Race");
		raceLabel.setStyleName("ribbonBarLabel");
		race = new TextBox();
		race.setStyleName("ribbonBarLabel");
		race.addChangeHandler(new ChangeHandler() {		
			@Override
			public void onChange(final ChangeEvent event) {
				raceChanged();
			}
		});
		race.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(final KeyUpEvent event) {
				raceChanged();
			}
		});
		pictureURL.setWidth("100%");
		centerGrid.setWidget(4, 0, raceLabel);
		centerGrid.setWidget(4, 1, race);
	}
	private void createClass() {
		pogClassLabel = new Label("Class");
		pogClassLabel.setStyleName("ribbonBarLabel");
		pogClass = new TextBox();
		pogClass.setStyleName("ribbonBarLabel");
		pogClass.addChangeHandler(new ChangeHandler() {		
			@Override
			public void onChange(final ChangeEvent event) {
				classChanged();
			}
		});
		pogClass.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(final KeyUpEvent event) {
				classChanged();
			}
		});
		pictureURL.setWidth("100%");
		centerGrid.setWidget(5, 0, pogClassLabel);
		centerGrid.setWidget(5, 1, pogClass);
	}

	private void createGender() {
		genderList = new ListBox();
		genderList.setStyleName("ribbonBarLabel");
		genderList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(final ChangeEvent event) {
				genderChanged();
			}
		});
		for (Gender pogGender : ServiceManager.getDungeonManager().getTemplateGenders()) {
			genderList.addItem(pogGender.getName(), pogGender.getName());
		}
		genderLabel = new Label("Gender");
		genderLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(6, 0, genderLabel);
		centerGrid.setWidget(6, 1, genderList);
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
		centerGrid.setWidget(7, 0, showNotesWindow);
	}

	private void genderChanged() {
		isDirty = true;
		validateForm();
	}

	private void classChanged() {
		isDirty = true;
		validateForm();
	}

	private void raceChanged() {
		isDirty = true;
		validateForm();
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
		PogData pog = ServiceManager.getDungeonManager().getSelectedPog();
		if (pog == null) {
			return;
		}
		pogData = pog.clone();
		pogData.setUUID(pog.getUUID());
		pogName.setValue(pogData.getName());
		setPogType();
		setPogLocation();
		setPictureData();
		setRaceData();
		setClassData();
		setGenderData();
		setNotesData();
		// for ()

		// templatePicture.setValue(pogData.getImageUrl());
		// race.setValue(pogData.getRace());
		// templateClass.setValue(pogData.getPogClass());
		// gender.setSelectedIndex(Gender.valueOf(pogData.getGender()).getValue());
		// int pogSize = pogData.getSize() - 1;
		// if (pogSize < 0) {
		// pogSize = 0;
		// }
		// size.setSelectedIndex(pogSize);
		// setFlagsDialogData();
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
	private void setRaceData() {
		race.setText(pogData.getRace());
	}
	/**
	 * Set pog class data.
	 */
	private void setClassData() {
		pogClass.setText(pogData.getPogClass());
	}

	private void setGenderData() {
		genderList.setSelectedIndex(Gender.valueOf(pogData.getGender()).getValue());
	}
	private void setNotesData() {
		notes = pogData.getNotes();
		dmNotes = pogData.getDmNotes();
		notesWindow.setNotesText(notes);
		notesWindow.setDMNotesText(dmNotes);
	}

	private void dungeonDataLoaded() {
	}

	private void createPog() {
	}

	private void deletePog() {
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
}
