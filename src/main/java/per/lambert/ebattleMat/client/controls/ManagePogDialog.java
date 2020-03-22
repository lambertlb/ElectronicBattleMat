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

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import per.lambert.ebattleMat.client.battleMatDisplay.PogCanvas;
import per.lambert.ebattleMat.client.controls.loginControl.LoginPresenter;
import per.lambert.ebattleMat.client.interfaces.Constants;
import per.lambert.ebattleMat.client.interfaces.DungeonMasterFlag;
import per.lambert.ebattleMat.client.interfaces.PlayerFlag;
import per.lambert.ebattleMat.client.interfaces.PogPlace;
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
	 * Layout panel.
	 */
	private DockLayoutPanel dockLayoutPanel;
	/**
	 * Panel for buttons.
	 */
	private HorizontalPanel buttonPanel;
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
	 * Panel to hold center content.
	 */
	private FlowPanel centerContent;
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

	public ManagePogDialog() {
		super("Manage Pog", false, false, 400, 400);
		load();
	}

	private void load() {
		createContent();
		initialize();
		setupEventHandlers();
	}

	private void createContent() {
		createGrid();
		dockLayoutPanel = new DockLayoutPanel(Unit.PX);
		dockLayoutPanel.setStyleName("popupPanel");
		dockLayoutPanel.setSize("100%", "100%");
		addButtonSupport();
		setWidget(dockLayoutPanel);
	}

	private void createGrid() {
		centerContent = new FlowPanel();
		centerContent.setHeight("100%");
		centerContent.setWidth("100%");
		centerGrid = new Grid();
		centerGrid.setWidth("100%");
		centerGrid.resize(11, 3);
		centerGrid.getColumnFormatter().setWidth(0, "20px");
		centerGrid.getColumnFormatter().setWidth(1, "20px");
		centerContent.add(centerGrid);
		createTemplateName();
		createTemplatePicture();
		createRaceText();
		createClassText();
		createGenderText();
		createSizeControls();
		createPlayerFlags();
		createDMFlags();
		createNotesDialog();
		pogPanel = new FlowPanel();
		centerGrid.setWidget(5, 0, pogPanel);
		pogCanvas = new PogCanvas();
		pogCanvas.setShowNormalSizeOnly(true);
		pogCanvas.setForceBackgroundColor(true);
		pogCanvas.showImage(false);
		pogPanel.add(pogCanvas);
		Element element;
		templatePictureLabel = new Label("Template Picture: ");
		element = centerGrid.getCellFormatter().getElement(5, 0);
		element.setAttribute("colspan", "3");
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
	}

	/**
	 * create controls for DM flags.
	 */
	private void createDMFlags() {
		dmFlagsButton = new Button("DM flags");
		dmFlagsButton.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(4, 1, dmFlagsButton);
		dmFlagDialog = new FlagBitsDialog("Dungeon Master Flags", DungeonMasterFlag.getValues());
	}

	/**
	 * Create controls for player flags.
	 */
	private void createPlayerFlags() {
		playerFlagsButton = new Button("Player flags");
		playerFlagsButton.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(4, 0, playerFlagsButton);
		playerFlagDialog = new FlagBitsDialog("Player Flags", PlayerFlag.getValues());
	}

	/**
	 * Add button support.
	 */
	private void addButtonSupport() {
		buttonPanel = new HorizontalPanel();
		save = new Button("Save");
		save.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				onSave();
			}
		});
		buttonPanel.add(save);
		cancel = new Button("Cancel");
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				onCancel();
			}
		});
		buttonPanel.add(cancel);
		delete = new Button("Delete");
		delete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				onDelete();
			}
		});
		buttonPanel.add(delete);
	}

	private void initialize() {
		dockLayoutPanel.clear();
		dockLayoutPanel.addSouth(buttonPanel, 30);
		dockLayoutPanel.add(centerContent);
	}

	private void setupEventHandlers() {
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
	 * @param place place of pag
	 * @param pogData data for pog
	 */
	public	void	editPog(final PogPlace place, final PogData pogData) {
		this.place = place;
		originalPlace = place;
		pogToManage = pogData;
		getElement().getStyle().setZIndex(Constants.DIALOG_Z + 1);
		handlePogCanvas(pogData);
		show();
	}

	private void handlePogCanvas(final PogData pogData) {
		pogCanvas.showImage(false);
		pogCanvas.setPogData(pogData);
		pogCanvas.showImage(true);
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
		int width = getDialogWidth();
		int height = getDialogHeight();
		dockLayoutPanel.setWidth("" + width + "px");
		dockLayoutPanel.setHeight("" + height + "px");
		pogCanvas.setPogSizing(computePogSize(), 0.0, 1.0);
	}

	/**
	 * Save pog data.
	 */
	protected void onSave() {
		hide();
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
		hide();
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
