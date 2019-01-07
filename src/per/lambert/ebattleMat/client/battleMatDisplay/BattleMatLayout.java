package per.lambert.ebattleMat.client.battleMatDisplay;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

public class BattleMatLayout extends ResizeComposite {
	/**
	 * The LayoutPanel that is essentially this widget.
	 */
	DockLayoutPanel holder;
	RibbonBarContainer topPanel;
	LayoutPanel statusBar;
	SimpleLayoutPanel simplePanel;
	BattleMatCanvas battleMatCanvas;
	Label statusLabel;

	public LayoutPanel mainPanel;

	final double SELECTION_PANEL_SIZE = 225;
	final double MAX_SELECTION_PANEL_SIZE = 450;
	final int MIN_SELECTION_PANEL_SIZE = 45;
	final double PROPERTY_PANEL_SIZE = 20;
	final double MAX_PROPERTY_PANEL_SIZE = 200;
	final int MIN_PROPERTY_PANEL_SIZE = 45;
	public static final double RIBBON_BAR_SIZE = 50;
	final double STATUS_BAR_SIZE = 30;

	/**
	 * Sets up the widget. We create the GUI, set up event handling, and call initWidget on the holder panel.
	 */
	public BattleMatLayout() {
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
		topPanel = new RibbonBarContainer();
		statusBar = new LayoutPanel();
		simplePanel = new SimpleLayoutPanel();
		mainPanel = new LayoutPanel();
		mainPanel.setSize("100%", "100%");
		battleMatCanvas = new BattleMatCanvas();
		battleMatCanvas.setParentPanel(this);
		simplePanel.clear();
		simplePanel.add(battleMatCanvas);
		mainPanel.add(simplePanel);

		// Add the panels to the holder
		holder.addNorth(topPanel, RIBBON_BAR_SIZE);
		holder.addSouth(statusBar, STATUS_BAR_SIZE);
		// holder.addWest(selectionPanel, SELECTION_PANEL_SIZE);
		// holder.addEast(propertyPanel, PROPERTY_PANEL_SIZE);
		holder.add(mainPanel);

		// MUST CALL THIS METHOD to set the constraints; if you don't not much
		// will be displayed!
		holder.forceLayout();

		// Add some labels and a button to the panels
		statusLabel = new Label("Status Bar");
		statusBar.add(statusLabel);

		// Throw in some colors and a white border so we can easily see the
		// panels.
		// This would normally be done in a CSS style sheet, but we use GWT's
		// DOM handling
		// methods here for visibility of what we are doing.
		statusBar.getElement().getStyle().setBackgroundColor("lightgray");

		Window.addResizeHandler(new ResizeHandler() {
			public void onResize(ResizeEvent event) {
				doWindowResize(event);
			}
		});

		return holder;
	}

	public void dungeonDataChanged() {
		battleMatCanvas.dungeonDataChanged();
	}

	public void dungeonDataUpdated() {
		battleMatCanvas.dungeonDataUpdated();
	}

	private void doWindowResize(ResizeEvent event) {
		battleMatCanvas.dungeonDataChanged();
	}

	public void setStatus(String status) {
		statusLabel.setText(status);
	}
}
