package per.lambert.ebattleMat.client.controls;

import java.util.Collection;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;

import per.lambert.ebattleMat.client.interfaces.Constants;
import per.lambert.ebattleMat.client.interfaces.FlagBits;

/**
 * Dialog to manage integer of bits controlled by the flags.
 * 
 * @author LLambert
 *
 */
public class FlagBitsDialog extends OkCancelDialog {
	/**
	 * Collection of flags.
	 */
	private Collection<FlagBits> flagBits;
	/**
	 * Bits that reflect the flags.
	 */
	private int bits;

	/**
	 * Get bits.
	 * 
	 * @return current bits
	 */
	public int getBits() {
		return bits;
	}

	/**
	 * Set bits.
	 * 
	 * @param bits starting bits
	 */
	public void setBits(final int bits) {
		this.bits = bits;
	}

	/**
	 * Grid for content.
	 */
	private Grid centerGrid;

	/**
	 * Constructor for flag bit dialog.
	 * 
	 * @param flagName name of dialog
	 * @param flagBits collection of flags
	 */
	public FlagBitsDialog(final String flagName, final Collection<FlagBits> flagBits) {
		super(flagName, true, true, 400, 400);
		this.flagBits = flagBits;
		getElement().getStyle().setZIndex(Constants.DIALOG_Z + 1);
		load();
	}

	/**
	 * Load in dialog.
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
		int amountOfFlagsPerColumn = (flagBits.size()) / 2;
		centerGrid.resize(amountOfFlagsPerColumn, 3);
		int row = 0;
		int column = 0;
		for (FlagBits flag : flagBits) {
			if (flag.getValue() == 0) {
				continue;
			}
			CheckBox checkBox = new CheckBox(flag.getName());
			checkBox.setStyleName("ribbonBarLabel");
			centerGrid.setWidget(row, column, checkBox);
			if (++row >= amountOfFlagsPerColumn) {
				row = 0;
				++column;
			}
		}
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
	public void show() {
		super.show();
		initialize();
		setUIFromBits();
		center();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onOkClick(final ClickEvent event) {
		fillBitsFromUI();
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
	 * close dialog.
	 */
	public void close() {
		hide();
	}

	/**
	 * Get bits that reflect boxes that are checked.
	 */
	private void fillBitsFromUI() {
		bits = 0;
		int amountOfFlagsPerColumn = (flagBits.size()) / 2;
		int row = 0;
		int column = 0;
		for (FlagBits flag : flagBits) {
			if (flag.getValue() == 0) {
				continue;
			}
			CheckBox checkBox = (CheckBox) centerGrid.getWidget(row, column);
			if (checkBox.getValue()) {
				bits |= flag.getValue();
			}
			if (++row >= amountOfFlagsPerColumn) {
				row = 0;
				++column;
			}
		}
	}

	/**
	 * Set check boxes to match bits.
	 */
	private void setUIFromBits() {
		int amountOfFlagsPerColumn = (flagBits.size()) / 2;
		int row = 0;
		int column = 0;
		for (FlagBits flag : flagBits) {
			if (flag.getValue() == 0) {
				continue;
			}
			CheckBox checkBox = (CheckBox) centerGrid.getWidget(row, column);
			if ((bits & flag.getValue()) != 0) {
				checkBox.setValue(true);
			} else {
				checkBox.setValue(false);
			}
			if (++row >= amountOfFlagsPerColumn) {
				row = 0;
				++column;
			}
		}
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
