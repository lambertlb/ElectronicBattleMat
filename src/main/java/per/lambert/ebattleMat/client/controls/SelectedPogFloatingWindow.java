package per.lambert.ebattleMat.client.controls;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Grid;

import per.lambert.ebattleMat.client.battleMatDisplay.BattleMatCanvas;
import per.lambert.ebattleMat.client.controls.ribbonBar.SelectedPog;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

/**
 * Show selected pog in a floating window.
 * @author LLambert
 *
 */
public class SelectedPogFloatingWindow extends OkCancelDialog {
	/**
	 * Grid for content.
	 */
	private Grid centerGrid;
	/**
	 * Canvas for pog display.
	 */
	private SelectedPog selectedPog;

	/**
	 * Constructor.
	 */
	public SelectedPogFloatingWindow() {
		super("Selected Pog", false, false, 70, 70);
		getElement().getStyle().setZIndex(BattleMatCanvas.DIALOG_Z - 1);
		load();
		setModal(false);
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.PogWasSelected) {
					pogSelected();
					return;
				}
			}
		});
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
		centerGrid = getCenterGrid();
		centerGrid.clear();
		centerGrid.resize(1, 1);
		centerGrid.getColumnFormatter().setWidth(0, "100%");
		selectedPog = new SelectedPog(getResizeWidget());
		centerGrid.setWidget(0, 0, selectedPog);
	}

	/**
	 * Setup event handlers.
	 */
	private void setupEventHandlers() {
	}

	/**
	 * Initialize view.
	 * 
	 * Must be run before reusing the view.
	 */
	private void initialize() {
	}

	/**
	 * Pog was selected so set name in title.
	 */
	protected void pogSelected() {
		PogData selectedPog = ServiceManager.getDungeonManager().getSelectedPog();
		if (selectedPog == null) {
			return;
		}
		setText(selectedPog.getPogName());
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onWindowResized() {
		super.onWindowResized();
		selectedPog.reDraw();
	}
	/**
	 * Show window.
	 * {@inheritDoc}
	 */
	@Override
	public void show() {
		super.show();
		selectedPog.pogSelected();
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
		return 70;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinHeight() {
		return 70;
	}
}
