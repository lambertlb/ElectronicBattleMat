package per.lambert.ebattleMat.client.battleMatDisplay;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import per.lambert.ebattleMat.client.controls.ArtAssetsPanel;

/**
 * Panel used for managing assets in battle map.
 * @author llambert
 *
 */
public class AssetManagementPanel extends DockLayoutPanel {

	/**
	 * Icon bar at top.
	 */
	private HorizontalPanel icons = new HorizontalPanel();
	/**
	 * Panel for center of dock.
	 */
	private SimplePanel centerPanel = new SimplePanel();
	/**
	 * Art assets.
	 */
	private Button artAssetsButton = new Button("Art Assets");
	/**
	 * Pog Editor.
	 */
	private Button pogEditorButton = new Button("Pog Editor");
	/**
	 * Panel for handling art assests.
	 */
	private ArtAssetsPanel artAssetsPanel = new ArtAssetsPanel();
	/**
	 * Constructor.
	 */
	public AssetManagementPanel() {
		super(Unit.PX);
		setSize("100%", "100%");
		createContent();
		addNorth(icons, 30);
		add(centerPanel);
		showArtAssets();
	}
	private void createContent() {
		icons.add(artAssetsButton);
		artAssetsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				showArtAssets();
			}
		});
		icons.add(pogEditorButton);
		pogEditorButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				editPog();
			}
		});
		setStyles();
	}
	private void setStyles() {
		artAssetsButton.setStyleName("ribbonBarLabel");
		pogEditorButton.setStyleName("ribbonBarLabel");
	}
	/**
	 * Show Art assets.
	 */
	private void showArtAssets() {
		centerPanel.clear();
		centerPanel.add(artAssetsPanel);
	}
	/**
	 * Edit pog.
	 */
	protected void editPog() {
		centerPanel.clear();
	}
}
