package per.lambert.ebattleMat.client.controls;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.battleMatDisplay.BattleMatCanvas;

/**
 * Base dialog for dialogs needing OK and cancel buttons.
 * @author LLambert
 *
 */
public class OkCancelDialog extends ResizableDialog {
	/**
	 * Dock layout panel.
	 */
	private DockLayoutPanel dockLayoutPanel;
	/**
	 * Grid for sub-classes to use for content.
	 */
	private Grid centerGrid;
	/**
	 * OK button.
	 */
	private Button ok;
	/**
	 * Cancel button.
	 */
	private Button cancel;
	/**
	 * Panel to hold center content.
	 */
	private FlowPanel centerContent;
	/**
	 * Content on south side of dialog.
	 */
	private HorizontalPanel southContent;

	/**
	 * grid for user content.
	 * @return grid for user content.
	 */
	protected Grid getCenterGrid() {
		return (centerGrid);
	}

	/**
	 * Constructor.
	 */
	public OkCancelDialog() {
		this("", false, false);
	}

	/**
	 * Constructor.
	 * @param caption for dialog
	 * @param okVisible true if OK button is visible.
	 * @param cancelVisble true if Cancel button is visible
	 */
	public OkCancelDialog(final String caption, final boolean okVisible, final boolean cancelVisble) {
		this(caption, okVisible, cancelVisble, 400, 350);
	}

	/**
	 * Constructor.
	 * @param caption for dialog
	 * @param okVisible true if OK button is visible.
	 * @param cancelVisble true if Cancel button is visible
	 * @param height of dialog
	 * @param width of dialog
	 */
	public OkCancelDialog(final String caption, final boolean okVisible, final boolean cancelVisble, final int height, final int width) {
		super();
		setText(caption);
		getElement().getStyle().setZIndex(BattleMatCanvas.DIALOG_Z);
		setSize("" + width + "px", "" + height + "px");
		createContent();
		ok.setVisible(okVisible);
		cancel.setVisible(cancelVisble);
		dockLayoutPanel.setWidth("" + width + "px");
		dockLayoutPanel.setHeight("" + height + "px");
	}

	/**
	 * Create content.
	 */
	private void createContent() {
		dockLayoutPanel = new DockLayoutPanel(Unit.PX);
		dockLayoutPanel.setStyleName("popupPanel");
		southContent = new HorizontalPanel();
		dockLayoutPanel.addSouth(southContent, 30);
		ok = new Button("Ok");
		ok.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				onOkClick(event);
			}
		});
		southContent.add(ok);
		cancel = new Button("Cancel");
		cancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				onCancelClick(event);
			}
		});
		southContent.add(cancel);
		centerContent = new FlowPanel();
		centerContent.setHeight("100%");
		centerContent.setWidth("100%");
		centerGrid = new Grid();
		centerGrid.setWidth("100%");
		centerContent.add(centerGrid);
		dockLayoutPanel.add(centerContent);
		setWidget(dockLayoutPanel);
	}

	/**
	 * Initialize content.
	 */
	private void initialize() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void OnWindowResized() {
		super.OnWindowResized();
		int width = getDialogWidth();
		int height = getDialogHeight();
		dockLayoutPanel.setWidth("" + width + "px");
		dockLayoutPanel.setHeight("" + height + "px");
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
	 * enable or disable OK button.
	 * @param enable true to enable.
	 */
	public void enableOk(final boolean enable) {
		enableWidget(ok, enable);
	}

	/**
	 * enable or disable Cancel button.
	 * @param enable true to enable.
	 */
	public void enableCancel(final boolean enable) {
		enableWidget(cancel, enable);
	}

	/**
	 * OK button clicked.
	 * @param event data
	 */
	protected void onOkClick(final ClickEvent event) {
	}

	/**
	 * Cancel button clicked.
	 * @param event data
	 */
	protected void onCancelClick(final ClickEvent event) {
	}

	/**
	 * Enable or Disable widget.
	 * @param widget to enable
	 * @param enable true if enabled
	 */
	public static void enableWidget(final Widget widget, final boolean enable) {
		if (enable) {
			widget.getElement().removeAttribute("disabled");
		} else {
			widget.getElement().setAttribute("disabled", "disabled");
		}
	}
}
