package per.lambert.ebattleMat.client.controls;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import per.lambert.ebattleMat.client.battleMatDisplay.PogCanvas;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

public class CharacterCreateDialog extends OkCancelDialog {
	private Grid centerGrid;
	private Label characterNameLabel;
	private TextBox characterName;
	private Label characterPictureLabel;
	private TextBox characterPicture;
	private FlowPanel pogPanel;
	private PogCanvas pogCanvas;
	private PogData pogData;

	public CharacterCreateDialog() {
		super("Character Creation", true, true);
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
		centerGrid.resize(10, 2);
		characterNameLabel = new Label("Character Name: ");
		characterNameLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(0, 0, characterNameLabel);
		characterName = new TextBox();
		characterName.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(0, 1, characterName);

		characterPictureLabel = new Label("Character Picture: ");
		characterPictureLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(1, 0, characterPictureLabel);
		characterPicture = new TextBox();
		characterPicture.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(1, 1, characterPicture);

		pogPanel = new FlowPanel();
		centerGrid.setWidget(4, 0, pogPanel);
		pogCanvas = new PogCanvas();
		pogPanel.add(pogCanvas);
	}

	private void setupEventHandlers() {
		characterPicture.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				characterPicture.selectAll();
			}
		});
		characterPicture.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				validateForm();
			}
		});
		characterName.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				characterName.selectAll();
			}
		});
		characterName.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				validateForm();
			}
		});
	}

	private void initialize() {
		pogData = ServiceManager.getDungeonManager().createPlayer();
		pogCanvas.setPogData(pogData);
		characterName.setValue("Enter Character Name");
		characterName.removeStyleName("badLabel");
		characterPicture.setValue("URL of character Picture");
		characterPicture.removeStyleName("badLabel");
		validateForm();
	}

	@Override
	public void show() {
		super.show();
		initialize();
		center();
	}

	private void validateForm() {
		boolean isValidCharacterName = validateCharacterName();
		boolean isValidUrl = validateUrl();
		enableOk(isValidCharacterName && isValidUrl);
	}

	private boolean validateCharacterName() {
		boolean valid = ServiceManager.getDungeonManager().isValidNewCharacterName(characterName.getValue());
		if (valid) {
			characterName.removeStyleName("badLabel");
		} else {
			characterName.addStyleName("badLabel");
		}
		return valid;
	}

	private boolean validateUrl() {
		String filename = characterPicture.getValue();
		int i = filename.lastIndexOf('.');
		String fileExtension = i > 0 ? filename.substring(i + 1) : "";
		boolean valid = fileExtension.equals("jpeg") || fileExtension.equals("jpg") || fileExtension.equals("png");
		if (valid) {
			characterPicture.removeStyleName("badLabel");
		} else {
			characterPicture.addStyleName("badLabel");
		}
		showPog(valid);
		return valid;
	}

	private void showPog(boolean isValid) {
		pogCanvas.setPogWidth(200);
		pogCanvas.showImage(isValid);
		if (isValid) {
			pogCanvas.setPogImageUrl(characterPicture.getValue());
		}
	}

	@Override
	protected void onOkClick(ClickEvent event) {
		acceptChanges();
	}

	private void acceptChanges() {
		pogData.setPogName(characterName.getValue());
		pogData.setPogImageUrl(characterPicture.getValue());
		pogData.setPogSize(1);
		pogData.setDungeonLevel(-1);
		ServiceManager.getDungeonManager().addPogDataToLevel(pogData);
		close();
	}

	@Override
	protected void onCancelClick(ClickEvent event) {
		close();
	}

	public void close() {
		hide();
	}
}
