package per.lambert.ebattleMat.client.maindisplay;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;

import per.lambert.ebattleMat.client.controls.TopPanel.TopPanelControl;

public class ShellLayout extends ResizeComposite {
	/**
	 * The LayoutPanel that is essentially this widget.
	 */
	DockLayoutPanel holder;
	SelectionPanel selectionPanel;
	PropertyPanel propertyPanel;
	TopPanelControl topPanel;
	LayoutPanel statusBar;
	public LayoutPanel mainPanel;

	final double SELECTION_PANEL_SIZE = 225;
	final double MAX_SELECTION_PANEL_SIZE = 450;
	final int MIN_SELECTION_PANEL_SIZE = 45;
	final double PROPERTY_PANEL_SIZE = 20;
	final double MAX_PROPERTY_PANEL_SIZE = 200;
	final int MIN_PROPERTY_PANEL_SIZE = 45;
	final double RIBBON_BAR_SIZE = 95;
	final double STATUS_BAR_SIZE = 30;

	/**
	 * Sets up the widget. We create the GUI, set up event handling, and call
	 * initWidget on the holder panel.
	 */
	public ShellLayout() {
		// Create the GUI
		// Note that in this setUpGui method we call the needed forceLayout
		// method to enforce the constraints
		// that are set up; not doing so means nothing would be displayed - it
		// is only needed for layout panels.
		holder = setUpGui();
		initWidget(holder);
		// ensure this widget takes up as much space as possible
		this.setSize("100%", "100%");
	}

	private DockLayoutPanel setUpGui() {

		// Create the layout panel
		holder = new DockLayoutPanel(Unit.PX);

		// Create some simple panels that we will color to use in the display
		// they are "final" as we use them in an anonymous class later
		selectionPanel = new SelectionPanel();
		selectionPanel.setShellLayout(this);
		topPanel = new TopPanelControl();
		propertyPanel = new PropertyPanel();
		propertyPanel.setShellLayout(this);
		statusBar = new LayoutPanel();
		mainPanel = new LayoutPanel();

		// Add the panels to the holder
		holder.addNorth(topPanel, RIBBON_BAR_SIZE);
		holder.addSouth(statusBar, STATUS_BAR_SIZE);
		holder.addWest(selectionPanel, SELECTION_PANEL_SIZE);
		holder.addEast(propertyPanel, PROPERTY_PANEL_SIZE);
		holder.add(mainPanel);

		// MUST CALL THIS METHOD to set the constraints; if you don't not much
		// will be displayed!
		holder.forceLayout();

		// Add some labels and a button to the panels

		statusBar.add(new Label("Status Bar"));

		// Throw in some colors and a white border so we can easily see the
		// panels.
		// This would normally be done in a CSS style sheet, but we use GWT's
		// DOM handling
		// methods here for visibility of what we are doing.
		selectionPanel.getElement().getStyle().setBackgroundColor("lightblue");
		propertyPanel.getElement().getStyle().setBackgroundColor("lightblue");
		statusBar.getElement().getStyle().setBackgroundColor("lightgray");

		return holder;
	}
	public void setSelectionPanelExpanded(boolean selectionPanelExpanded) {
	}

}
