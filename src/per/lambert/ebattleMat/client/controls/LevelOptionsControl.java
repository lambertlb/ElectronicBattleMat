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

public class LevelOptionsControl extends OkCancelDialog {

	private boolean fileNeedsToBeUploaded;
	private String fileExtension;
	private boolean legalFile;
	private String pictureName;
	private boolean newLevel;
	private DungeonLevel currentLevel;

	private CheckBox showGrid;
	private LabeledTextBox gridSize;
	private LabeledTextBox gridOffsetX;
	private LabeledTextBox gridOffsetY;
	private Label levelNameLabel;
	private TextBox levelName;
	private Button downloadLevelPicture;
	private FileUpLoadControl fileUpLoadControl;
	private Button createNewLevelButton;

	private Grid dmGrid;

	public LevelOptionsControl() {
		super("Level Options", true, true);
		load();
	}

	protected void load() {
		createContent();
		setupEventHandlers();
		initialize();
	}

	private void initialize() {
		newLevel = false;
		fileNeedsToBeUploaded = false;
		fileExtension = "";
		legalFile = false;
		pictureName = "";
		fileUpLoadControl.setInput("");
		fileUpLoadControl.setBadInput(false);
		levelNameLabel.removeStyleName("badLabel");
		checkForLegalContent();
		center();
		getData();
	}

	private void createContent() {
		dmGrid = getCenterGrid();
		dmGrid.clear();
		dmGrid.resize(10, 2);
		createShowGrid();
		createGridSizeEntry();
		createGridOffsetX();
		createGridOffsetY();
		createLevelName();
		createDownloadLevel();
		createUploadLevel();
		createNewLevel();
	}

	private void createNewLevel() {
		createNewLevelButton = new Button("Create New Level");
		createNewLevelButton.setStyleName("ribbonBarLabel");
		createNewLevelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handleCreateNewLevel();
			}
		});
		dmGrid.setWidget(7, 0, createNewLevelButton);
	}

	private void createUploadLevel() {
		 fileUpLoadControl = new FileUpLoadControl("Select level Picture:  ");
		 fileUpLoadControl.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				fileSelected();
			}
		});
		fileUpLoadControl.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				uploadComplete(event);
			}
		});
		dmGrid.setWidget(5, 0, fileUpLoadControl);
		Element element = dmGrid.getCellFormatter().getElement(5, 0);
		element.setAttribute("colspan", "3");
	}

	private void createDownloadLevel() {
		downloadLevelPicture = new Button("Download Level Picture");
		downloadLevelPicture.setStyleName("ribbonBarLabel");
		downloadLevelPicture.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				DungeonLevel levelData = ServiceManager.getDungeonManager().getCurrentLevelData();
				ServiceManager.getDungeonManager().downloadDungeonFile(levelData.getLevelDrawing());
			}
		});
		dmGrid.setWidget(4, 0, downloadLevelPicture);
	}

	private void createShowGrid() {
		showGrid = new CheckBox("Show Grid");
		showGrid.setStyleName("ribbonBarLabel");
		showGrid.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
		showGrid.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				showGridClicked();
			}
		});
		dmGrid.setWidget(1, 0, showGrid);
	}

	private void createGridSizeEntry() {
		gridSize = new LabeledTextBox("Grid Size", 0.0);
		gridSize.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
			}
		});
		gridSize.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
			}
		});
		dmGrid.setWidget(1, 1, gridSize);
	}

	private void createGridOffsetX() {
		gridOffsetX = new LabeledTextBox("Grid Offset X", 0.0);
		gridOffsetX.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
			}
		});
		gridOffsetX.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
			}
		});
		dmGrid.setWidget(2, 0, gridOffsetX);
	}

	private void createGridOffsetY() {
		gridOffsetY = new LabeledTextBox("Grid Offset Y", 0.0);
		gridOffsetY.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
			}
		});
		gridOffsetY.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
			}
		});
		dmGrid.setWidget(2, 1, gridOffsetY);
	}

	private void createLevelName() {
		levelNameLabel = new Label("Level Name");
		levelNameLabel.setStyleName("ribbonBarLabel");
		levelName = new TextBox();
		levelName.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				checkForLegalContent();
			}
		});
		levelName.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				checkForLegalContent();
			}
		});

		levelName.setStyleName("ribbonBarLabel");
		dmGrid.setWidget(0, 0, levelNameLabel);
		dmGrid.setWidget(0, 1, levelName);
	}

	private void setupEventHandlers() {
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.DungeonDataLoaded) {
					getData();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonSelectedLevelChanged) {
					getData();
					return;
				}
			}
		});
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				if (isShowing()) {
					center();
				}
			}
		});
	}

	@Override
	protected void onOkClick(ClickEvent event) {
		acceptChanges();
	}

	@Override
	protected void onCancelClick(ClickEvent event) {
		close();
	}

	protected void showGridClicked() {
	}

	public void close() {
		hide();
	}

	@Override
	public void show() {
		super.show();
		initialize();
	}

	private void getData() {
		currentLevel = ServiceManager.getDungeonManager().getCurrentLevelData();
		if (currentLevel == null) {
			return;
		}
		addLevelDataToForm();
		checkForLegalContent();
	}

	private void addLevelDataToForm() {
		showGrid.setValue(ServiceManager.getDungeonManager().getSelectedDungeon().getShowGrid());
		gridSize.setValue(currentLevel.getGridSize());
		gridOffsetX.setValue(currentLevel.getGridOffsetX());
		gridOffsetY.setValue(currentLevel.getGridOffsetY());
		levelName.setValue(currentLevel.getLevelName());
	}

	protected void fileSelected() {
		fileNeedsToBeUploaded = true;
		legalFile = false;
		String filename = fileUpLoadControl.getFilename().toLowerCase();
		if (filename.length() == 0) {
			Window.alert("No File Specified!");
			checkForLegalContent();
			return;
		}
		isValidPictureExtension(filename);
		checkForLegalContent();
	}

	private void isValidPictureExtension(String filename) {
		int i = filename.lastIndexOf('.');
		fileExtension = i > 0 ? filename.substring(i + 1) : "";
		legalFile = fileExtension.equals("jpeg") || fileExtension.equals("jpg") || fileExtension.equals("png");
	}

	private void acceptChanges() {
		if (fileNeedsToBeUploaded && legalFile) {
			String baseURL = ServiceManager.getDungeonManager().getUrlToDungeonData();
			String levelString = getCorrectLevelString();
			pictureName = "level" + levelString + "." + fileExtension;
			String filePath = baseURL + pictureName;
			uploadFile(filePath);
		} else {
			acceptChangedPart2();
		}
	}

	private String getCorrectLevelString() {
		if (newLevel) {
			return ("" + (ServiceManager.getDungeonManager().getNextLevelNumber()));
		} else {
			return ("" + (ServiceManager.getDungeonManager().getCurrentLevel() + 1));
		}
	}

	private void acceptChangedPart2() {
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

	private void uploadFile(String serverPath) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("filePath", serverPath);
		String url = DataRequester.buildUrl("FILEUPLOAD", parameters);
		fileUpLoadControl.setAction(url);
		fileUpLoadControl.setEncoding(FormPanel.ENCODING_MULTIPART);
		fileUpLoadControl.setMethod(FormPanel.METHOD_POST);
		fileUpLoadControl.submit();
	}

	protected void uploadComplete(SubmitCompleteEvent event) {
		String results = event.getResults();
		if (!results.isEmpty()) {
			Window.alert("Failed to upload picture " + results);
			return;
		}
		acceptChangedPart2();
	}

	private void checkForLegalContent() {
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

	protected void handleCreateNewLevel() {
		initialize();
		currentLevel = (DungeonLevel) JavaScriptObject.createObject().cast();
		fileNeedsToBeUploaded = true;
		addLevelDataToForm();
		newLevel = true;
		checkForLegalContent();
	}
}
