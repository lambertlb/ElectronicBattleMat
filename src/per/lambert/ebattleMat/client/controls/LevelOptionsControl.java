package per.lambert.ebattleMat.client.controls;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import per.lambert.ebattleMat.client.controls.labeledTextBox.LabeledTextBox;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.DataRequester;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;

/**
 * Dialog for Level Options.
 * 
 * @author LLambert
 *
 */
public class LevelOptionsControl extends OkCancelDialog {
	/**
	 * A file needs to be uploaded.
	 */
	private boolean fileNeedsToBeUploaded;
	/**
	 * Extension of file to be uploaded.
	 */
	private String fileExtension;
	/**
	 * Is this file legal to be uploaded.
	 */
	private boolean legalFile;
	/**
	 * Name of picture file.
	 */
	private String pictureName;
	/**
	 * New level needs to be created.
	 */
	private boolean newLevel;
	/**
	 * Current level we are working on.
	 */
	private DungeonLevel currentLevel;

	/**
	 * Show grid on dungeon levels.
	 */
	private CheckBox showGrid;
	/**
	 * grid size in pixels.
	 */
	private LabeledTextBox gridSize;
	/**
	 * X Offset of grid from top Left.
	 */
	private LabeledTextBox gridOffsetX;
	/**
	 * Y Offset of grid from top Left.
	 */
	private LabeledTextBox gridOffsetY;
	/**
	 * Label for new level.
	 */
	private Label levelNameLabel;
	/**
	 * Name of new level.
	 */
	private TextBox levelName;
	/**
	 * Start download of picture file.
	 */
	private Button downloadLevelPicture;
	/**
	 * File upload control.
	 */
	private FileUpLoadControl fileUpLoadControl;
	/**
	 * Create new level button.
	 */
	private Button createNewLevelButton;
	/**
	 * Grid for content.
	 */
	private Grid centerGrid;

	/**
	 * Constructor for level option control.
	 */
	public LevelOptionsControl() {
		super("Level Options", true, true);
		load();
	}

	/**
	 * Load in view.
	 */
	protected void load() {
		createContent();
		setupEventHandlers();
		initialize();
	}

	/**
	 * Initialize view.
	 * 
	 * Must be run before reusing the view.
	 */
	private void initialize() {
		newLevel = false;
		fileNeedsToBeUploaded = false;
		fileExtension = "";
		legalFile = false;
		pictureName = "";
		fileUpLoadControl.setInput("");
		fileUpLoadControl.setBadInput(false);
		levelNameLabel.removeStyleName("badLabel");
		validateContent();
		center();
		gatherData();
	}

	/**
	 * Create content for the view.
	 */
	private void createContent() {
		centerGrid = getCenterGrid();
		centerGrid.clear();
		centerGrid.resize(10, 2);
		createShowGrid();
		createGridSizeEntry();
		createGridOffsetX();
		createGridOffsetY();
		createLevelName();
		createDownloadLevelPicture();
		createUploadLevel();
		createNewLevel();
	}

	/**
	 * Create new level content.
	 */
	private void createNewLevel() {
		createNewLevelButton = new Button("Create New Level");
		createNewLevelButton.setStyleName("ribbonBarLabel");
		createNewLevelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				handleCreateNewLevel();
			}
		});
		centerGrid.setWidget(7, 0, createNewLevelButton);
	}

	/**
	 * Create upload level content.
	 */
	private void createUploadLevel() {
		fileUpLoadControl = new FileUpLoadControl("Select level Picture:  ");
		fileUpLoadControl.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(final ChangeEvent event) {
				fileSelected();
			}
		});
		fileUpLoadControl.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(final SubmitCompleteEvent event) {
				uploadComplete(event);
			}
		});
		centerGrid.setWidget(5, 0, fileUpLoadControl);
		Element element = centerGrid.getCellFormatter().getElement(5, 0);
		element.setAttribute("colspan", "3");
	}

	/**
	 * Create download level picture content.
	 */
	private void createDownloadLevelPicture() {
		downloadLevelPicture = new Button("Download Level Picture");
		downloadLevelPicture.setStyleName("ribbonBarLabel");
		downloadLevelPicture.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				DungeonLevel levelData = ServiceManager.getDungeonManager().getCurrentLevelData();
				ServiceManager.getDungeonManager().downloadDungeonFile(levelData.getLevelDrawing());
			}
		});
		centerGrid.setWidget(4, 0, downloadLevelPicture);
	}

	/**
	 * Create show grid content.
	 */
	private void createShowGrid() {
		showGrid = new CheckBox("Show Grid");
		showGrid.setStyleName("ribbonBarLabel");
		showGrid.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
		centerGrid.setWidget(1, 0, showGrid);
	}

	/**
	 * Create grid size content.
	 */
	private void createGridSizeEntry() {
		gridSize = new LabeledTextBox("Grid Size", 0.0);
		gridSize.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(final ChangeEvent event) {
			}
		});
		gridSize.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(final KeyUpEvent event) {
			}
		});
		centerGrid.setWidget(1, 1, gridSize);
	}

	/**
	 * Create X grid offset content.
	 */
	private void createGridOffsetX() {
		gridOffsetX = new LabeledTextBox("Grid Offset X", 0.0);
		gridOffsetX.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(final ChangeEvent event) {
			}
		});
		gridOffsetX.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(final KeyUpEvent event) {
			}
		});
		centerGrid.setWidget(2, 0, gridOffsetX);
	}

	/**
	 * Create Y grid offset content.
	 */
	private void createGridOffsetY() {
		gridOffsetY = new LabeledTextBox("Grid Offset Y", 0.0);
		gridOffsetY.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(final ChangeEvent event) {
			}
		});
		gridOffsetY.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(final KeyUpEvent event) {
			}
		});
		centerGrid.setWidget(2, 1, gridOffsetY);
	}

	/**
	 * Create level name content.
	 */
	private void createLevelName() {
		levelNameLabel = new Label("Level Name");
		levelNameLabel.setStyleName("ribbonBarLabel");
		levelName = new TextBox();
		levelName.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(final ValueChangeEvent<String> event) {
				validateContent();
			}
		});
		levelName.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(final KeyUpEvent event) {
				validateContent();
			}
		});

		levelName.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(0, 0, levelNameLabel);
		centerGrid.setWidget(0, 1, levelName);
	}

	/**
	 * Setup event handlers.
	 */
	private void setupEventHandlers() {
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.DungeonDataLoaded) {
					gatherData();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonSelectedLevelChanged) {
					gatherData();
					return;
				}
			}
		});
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(final ResizeEvent event) {
				if (isShowing()) {
					center();
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onOkClick(final ClickEvent event) {
		acceptChanges();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCancelClick(final ClickEvent event) {
		close();
	}

	/**
	 * close dialog.
	 */
	public void close() {
		hide();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void show() {
		super.show();
		initialize();
	}

	/**
	 * Gather data.
	 */
	private void gatherData() {
		currentLevel = ServiceManager.getDungeonManager().getCurrentLevelData();
		if (currentLevel == null) {
			return;
		}
		addLevelDataToForm();
		validateContent();
	}

	/**
	 * Add level to form.
	 */
	private void addLevelDataToForm() {
		showGrid.setValue(ServiceManager.getDungeonManager().getSelectedDungeon().getShowGrid());
		gridSize.setValue(currentLevel.getGridSize());
		gridOffsetX.setValue(currentLevel.getGridOffsetX());
		gridOffsetY.setValue(currentLevel.getGridOffsetY());
		levelName.setValue(currentLevel.getLevelName());
	}

	/**
	 * File was selected.
	 */
	protected void fileSelected() {
		fileNeedsToBeUploaded = true;
		legalFile = false;
		String filename = fileUpLoadControl.getFilename().toLowerCase();
		if (filename.length() == 0) {
			Window.alert("No File Specified!");
			validateContent();
			return;
		}
		isValidPictureExtension(filename);
		validateContent();
	}

	/**
	 * Is this a valid picture extension.
	 * @param filename to test
	 */
	private void isValidPictureExtension(final String filename) {
		int i = filename.lastIndexOf('.');
		fileExtension = i > 0 ? filename.substring(i + 1) : "";
		legalFile = fileExtension.equals("jpeg") || fileExtension.equals("jpg") || fileExtension.equals("png");
	}

	/**
	 * Accept changes to level.
	 */
	private void acceptChanges() {
		if (fileNeedsToBeUploaded && legalFile) {
			String baseURL = ServiceManager.getDungeonManager().getUrlToDungeonData();
			String levelString = getCorrectLevelString();
			pictureName = "level" + levelString + "." + fileExtension;
			String filePath = baseURL + pictureName;
			uploadFile(filePath);
		} else {
			acceptChangesToDungeonLevel();
		}
	}

	/**
	 * Get correct level number.
	 * @return correct level number as string.
	 */
	private String getCorrectLevelString() {
		if (newLevel) {
			return ("" + (ServiceManager.getDungeonManager().getNextLevelNumber()));
		} else {
			return ("" + (ServiceManager.getDungeonManager().getCurrentLevel() + 1));
		}
	}

	/**
	 * accept Changes To Dungeon Level.
	 */
	private void acceptChangesToDungeonLevel() {
		DungeonLevel levelData = ServiceManager.getDungeonManager().getCurrentLevelData();
		if (levelData == null) {
			close();
			return;
		}
		ServiceManager.getDungeonManager().getSelectedDungeon().setShowGrid(showGrid.getValue());
		currentLevel.setGridSize(gridSize.getDoubleValue());
		currentLevel.setGridOffsetX(gridOffsetX.getDoubleValue());
		currentLevel.setGridOffsetY(gridOffsetY.getDoubleValue());
		currentLevel.setLevelName(levelName.getValue());
		if (fileNeedsToBeUploaded) {
			currentLevel.setLevelDrawing(pictureName);
		}
		if (newLevel) {
			ServiceManager.getDungeonManager().addNewLevel(currentLevel);
		}

		ServiceManager.getDungeonManager().saveDungeonData();
		close();
	}

	/**
	 * Up load file.
	 * @param serverPath path on server
	 */
	private void uploadFile(final String serverPath) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("filePath", serverPath);
		String url = DataRequester.buildUrl("FILEUPLOAD", parameters);
		fileUpLoadControl.setAction(url);
		fileUpLoadControl.setEncoding(FormPanel.ENCODING_MULTIPART);
		fileUpLoadControl.setMethod(FormPanel.METHOD_POST);
		fileUpLoadControl.submit();
	}

	/**
	 * Upload complete.
	 * @param event with data
	 */
	protected void uploadComplete(final SubmitCompleteEvent event) {
		String results = event.getResults();
		if (!results.isEmpty()) {
			Window.alert("Failed to upload picture " + results);
			return;
		}
		acceptChangesToDungeonLevel();
	}

	/**
	 * Validate content.
	 */
	private void validateContent() {
		boolean isOK = true;
		if (fileNeedsToBeUploaded) {
			if (!legalFile) {
				isOK = false;
				fileUpLoadControl.setBadInput(true);
			} else {
				fileUpLoadControl.setBadInput(false);
			}
		}
		if (!ServiceManager.getDungeonManager().isLegalDungeonName(levelName.getValue())) {
			isOK = false;
			levelNameLabel.addStyleName("badLabel");
		} else {
			levelNameLabel.removeStyleName("badLabel");
		}
		enableOk(isOK);
	}

	/**
	 * Handle create new level.
	 */
	protected void handleCreateNewLevel() {
		initialize();
		currentLevel = (DungeonLevel) JavaScriptObject.createObject().cast();
		fileNeedsToBeUploaded = true;
		addLevelDataToForm();
		newLevel = true;
		validateContent();
	}
}
