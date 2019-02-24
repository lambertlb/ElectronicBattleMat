package per.lambert.ebattleMat.client.controls;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

import per.lambert.ebattleMat.client.battleMatDisplay.BattleMatCanvas;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

/**
 * Floating window display notes on a pog.
 * 
 * @author LLambert
 *
 */
public class NotesFloatingWindow extends OkCancelDialog {
	/**
	 * Canvas for pog display.
	 */
	private PogData selectedPog;
	/**
	 * Widget to hold content.
	 */
	private HTML theText;
	/**
	 * Panel to capture key events.
	 */
	private FocusPanel editPanel;
	/**
	 * Panel for scroll area.
	 */
	private ScrollPanel scrollPanel;
	/**
	 * Layout panel.
	 */
	private DockLayoutPanel dockLayoutPanel;
	private HorizontalPanel buttonPanel;
	/**
	 * Save changes button.
	 */
	private Button save;
	private Button cancel;

	/**
	 * Constructor.
	 */
	public NotesFloatingWindow() {
		super("Pog Notes", false, false, 200, 200);
		getElement().getStyle().setZIndex(BattleMatCanvas.DIALOG_Z - 1);
		load();
		setModal(false);
	}

	/**
	 * load in view.
	 */
	private void load() {
		createContent();
		setupEventHandlers();
		initialize();
	}

	/**
	 * Create content for the view.
	 */
	private void createContent() {
		editPanel = new FocusPanel();
		editPanel.setSize("100%", "100%");
		editPanel.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(final KeyUpEvent event) {
				handleTextChanged(event);
			}
		});
		theText = new HTML();
		theText.setSize("100%", "100%");
		editPanel.add(theText);
		scrollPanel = new ScrollPanel(editPanel);
		scrollPanel.setSize("100%", "100%");

		dockLayoutPanel = new DockLayoutPanel(Unit.PX);
		dockLayoutPanel.setStyleName("popupPanel");
		dockLayoutPanel.setSize("100%", "100%");
		addButtonSupport();
		dockLayoutPanel.add(scrollPanel);

		setWidget(dockLayoutPanel);
	}

	/**
	 * Add button support.
	 */
	private void addButtonSupport() {
		buttonPanel = new HorizontalPanel();
		dockLayoutPanel.addSouth(buttonPanel, 30);
		save = new Button("Save");
		save.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				saveNotes();
			}
		});
		buttonPanel.add(save);
		cancel = new Button("Cancel");
		save.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				onCancel();
			}
		});
		buttonPanel.add(cancel);
	}

	/**
	 * cancel pressed.
	 */
	protected void onCancel() {
		getTextFromSelectedPog();
	}

	/**
	 * Add an save click handler.
	 * 
	 * @param clickHandler to add
	 */
	public void addSaveClickHandler(final ClickHandler clickHandler) {
		save.addClickHandler(clickHandler);
	}

	/**
	 * Add an cancel click handler.
	 * 
	 * @param clickHandler to add
	 */
	public void addCancelClickHandler(final ClickHandler clickHandler) {
		cancel.addClickHandler(clickHandler);
	}

	/**
	 * Save changed notes.
	 */
	protected void saveNotes() {
		selectedPog.setNotes(theText.getHTML());
		ServiceManager.getDungeonManager().addOrUpdatePog(selectedPog);
		enableWidget(save, false);
	}

	/**
	 * Setup event handlers.
	 */
	private void setupEventHandlers() {
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.PogWasSelected) {
					getTextFromSelectedPog();
					return;
				}
			}
		});
	}

	/**
	 * Initialize view.
	 * 
	 * Must be run before reusing the view.
	 */
	private void initialize() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onWindowResized() {
		super.onWindowResized();
	}

	/**
	 * Get the text from the selected pog.
	 */
	private void getTextFromSelectedPog() {
		String textToSet = "";
		selectedPog = ServiceManager.getDungeonManager().getSelectedPog();
		if (selectedPog != null) {
			textToSet = selectedPog.getNotes();
		}
		theText.setHTML(textToSet);
		makeContentEditable(ServiceManager.getDungeonManager().isDungeonMaster());
		enableWidget(save, false);
	}

	/**
	 * Make html content editable.
	 * 
	 * @param editable true if editable
	 */
	private void makeContentEditable(final boolean editable) {
		if (editable) {
			editPanel.getElement().setAttribute("contenteditable", "true");
		} else {
			editPanel.getElement().setAttribute("contenteditable", "false");
		}
	}

	/**
	 * Handle text changed event.
	 * 
	 * @param event with ley information
	 */
	protected void handleTextChanged(final KeyUpEvent event) {
		if (save != null) {
			enableWidget(save, true);
		}
	}

	/**
	 * Show window. {@inheritDoc}
	 */
	@Override
	public void show() {
		super.show();
		getTextFromSelectedPog();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCancelClick(final ClickEvent event) {
		hide();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinWidth() {
		return 200;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinHeight() {
		return 200;
	}

}
