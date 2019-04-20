package per.lambert.ebattleMat.client.battleMatDisplay;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

import per.lambert.ebattleMat.client.interfaces.Constants;

/**
 * Layout for battle mat.
 * 
 * @author LLambert
 *
 */
public class BattleMatLayout extends ResizeComposite {
	/**
	 * The LayoutPanel that is essentially this widget.
	 */
	private DockLayoutPanel holder;
	/**
	 * panel for ribbon bar.
	 */
	private RibbonBarContainer topPanel;
	/**
	 * Panel to hold battle mat canvas.
	 */
	private SimpleLayoutPanel battleMatCanvasPanel;
	/**
	 * Battle mat canvas.
	 */
	private BattleMatCanvas battleMatCanvas;
	/**
	 * main panel.
	 */
	private LayoutPanel mainPanel;

	/**
	 * Sets up the widget. We create the GUI, set up event handling, and call initWidget on the holder panel.
	 */
	public BattleMatLayout() {
		createConent();
		initWidget(holder);
		// ensure this widget takes up as much space as possible
		this.setSize("100%", "100%");
	}

	/**
	 * create content of panel.
	 */
	private void createConent() {
		holder = new DockLayoutPanel(Unit.PX);
		topPanel = new RibbonBarContainer();
		battleMatCanvasPanel = new SimpleLayoutPanel();
		mainPanel = new LayoutPanel();
		mainPanel.setSize("100%", "100%");
		battleMatCanvas = new BattleMatCanvas();
		battleMatCanvasPanel.clear();
		battleMatCanvasPanel.add(battleMatCanvas);
		mainPanel.add(battleMatCanvasPanel);

		holder.addNorth(topPanel, Constants.RIBBON_BAR_SIZE);
		holder.add(mainPanel);

		// MUST CALL THIS METHOD to set the constraints; if you don't not much
		// will be displayed!
		holder.forceLayout();

		Window.addResizeHandler(new ResizeHandler() {
			public void onResize(final ResizeEvent event) {
				doWindowResize(event);
			}
		});
	}

	/**
	 * delegate dungeon data changed.
	 */
	public void dungeonDataChanged() {
		battleMatCanvas.dungeonDataChanged();
	}

	/**
	 * delegate dungeon data updated.
	 */
	public void dungeonDataUpdated() {
		battleMatCanvas.dungeonDataUpdated();
	}

	/**
	 * delegate window resized.
	 * 
	 * @param event event data
	 */
	private void doWindowResize(final ResizeEvent event) {
		battleMatCanvas.dungeonDataChanged();
	}

}
