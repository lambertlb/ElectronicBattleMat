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

/**
 * Character creation dialog. Its main responsibility is to create a character that the player or DM can use for this one dungeon session.
 * 
 * @author LLambert
 *
 */
public class CharacterCreateDialog extends OkCancelDialog {
	/**
	 * Center grid with all the content. It comes from the parent dialog
	 */
	private Grid centerGrid;
	/**
	 * Label for character name.
	 */
	private Label characterNameLabel;
	/**
	 * Text box for character name.
	 */
	private TextBox characterName;
	/**
	 * Label for Level picture.
	 */
	private Label characterPictureLabel;
	/**
	 * Text box for level picture URL.
	 */
	private TextBox characterPicture;
	/**
	 * Panel to hold pog canvas.
	 */
	private FlowPanel pogPanel;
	/**
	 * Pog canvas to manage picture.
	 */
	private PogCanvas pogCanvas;
	/**
	 * Pog data.
	 */
	private PogData pogData;

	/**
	 * Constructor for character creation.
	 */
	public CharacterCreateDialog() {
		super("Character Creation", true, true, 400, 400);
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
		centerGrid.resize(10, 2);
		centerGrid.getColumnFormatter().setWidth(0, "20px");
//		centerGrid.getColumnFormatter().setWidth(1, "90%");
		characterNameLabel = new Label("Character Name: ");
		characterNameLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(0, 0, characterNameLabel);
		characterName = new TextBox();
		characterName.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(0, 1, characterName);
		characterName.setWidth("100%");

		characterPictureLabel = new Label("Character Picture: ");
		characterPictureLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(1, 0, characterPictureLabel);
		characterPicture = new TextBox();
		characterPicture.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(1, 1, characterPicture);
		characterPicture.setWidth("100%");

		pogPanel = new FlowPanel();
		centerGrid.setWidget(4, 0, pogPanel);
		pogCanvas = new PogCanvas();
		pogPanel.add(pogCanvas);
	}

	/**
	 * Setup event handlers.
	 */
	private void setupEventHandlers() {
		characterPicture.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				characterPicture.selectAll();
			}
		});
		characterPicture.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(final KeyUpEvent event) {
				validateForm();
			}
		});
		characterName.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				characterName.selectAll();
			}
		});
		characterName.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(final KeyUpEvent event) {
				validateForm();
			}
		});
	}

	/**
	 * initialize content. Needed since the dialog can be reused.
	 */
	private void initialize() {
		pogData = ServiceManager.getDungeonManager().createPlayer();
		pogCanvas.setPogData(pogData);
		characterName.setValue("Enter Character Name");
		characterName.removeStyleName("badLabel");
		characterPicture.setValue("URL of character Picture");
		characterPicture.removeStyleName("badLabel");
		validateForm();
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
	 * Validate all data on form.
	 */
	private void validateForm() {
		boolean isValidCharacterName = validateCharacterName();
		boolean isValidUrl = validateUrl();
		enableOk(isValidCharacterName && isValidUrl);
	}

	/**
	 * Validate character name.
	 * 
	 * @return true if valid.
	 */
	private boolean validateCharacterName() {
		boolean valid = ServiceManager.getDungeonManager().isValidNewCharacterName(characterName.getValue());
		if (valid) {
			characterName.removeStyleName("badLabel");
		} else {
			characterName.addStyleName("badLabel");
		}
		return valid;
	}

	/**
	 * Validate Url of picture. THis makes sure it is a picture type we support
	 * 
	 * @return true if ok.
	 */
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

	/**
	 * Show the pog image for the url.
	 * 
	 * @param isValid is valid url
	 */
	private void showPog(final boolean isValid) {
		pogCanvas.setPogSizing(computePogSize(), 0.0, 1.0);
		pogCanvas.showImage(isValid);
		if (isValid) {
			pogCanvas.setPogImageUrl(characterPicture.getValue());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onOkClick(final ClickEvent event) {
		acceptChanges();
	}

	/**
	 * Accept changes made in the dialog.
	 */
	private void acceptChanges() {
		pogData.setPogName(characterName.getValue());
		pogData.setPogImageUrl(characterPicture.getValue());
		pogData.setPogSize(1);
		pogData.setDungeonLevel(-1);
		ServiceManager.getDungeonManager().addOrUpdatePog(pogData);
		ServiceManager.getDungeonManager().setSelectedPog(pogData);
		close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCancelClick(final ClickEvent event) {
		close();
	}

	/**
	 * Close the dialog.
	 */
	public void close() {
		hide();
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
	 * {@inheritDoc}
	 */
	@Override
	protected void onWindowResized() {
		super.onWindowResized();
		pogCanvas.setPogSizing(computePogSize(), 0.0, 1.0);
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
