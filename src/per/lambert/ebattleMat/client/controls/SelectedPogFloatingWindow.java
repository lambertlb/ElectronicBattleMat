package per.lambert.ebattleMat.client.controls;

import com.google.gwt.user.client.ui.Grid;

import per.lambert.ebattleMat.client.battleMatDisplay.BattleMatCanvas;
import per.lambert.ebattleMat.client.controls.ribbonBar.SelectedPog;

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
		super("Selected Pog", false, false);
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
}
