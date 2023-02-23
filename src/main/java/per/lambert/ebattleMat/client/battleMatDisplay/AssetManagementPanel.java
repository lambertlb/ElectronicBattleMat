package per.lambert.ebattleMat.client.battleMatDisplay;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

import per.lambert.ebattleMat.client.controls.ArtAssetsPanel;
import per.lambert.ebattleMat.client.controls.DungeonEditorPanel;

/**
 * Panel used for managing assets in battle map.
 * @author llambert
 *
 */
public class AssetManagementPanel extends DockLayoutPanel {

	/**
	 * Tab panel.
	 */
	private TabLayoutPanel tabPanel = new TabLayoutPanel(1.5, Unit.EM);
	/**
	 * Panel for handling art assests.
	 */
	private ArtAssetsPanel artAssetsPanel = new ArtAssetsPanel();
	/**
	 * Panel for editing dungeon.
	 */
	private DungeonEditorPanel dungeonPanel = new DungeonEditorPanel();
	/**
	 * Constructor.
	 */
	public AssetManagementPanel() {
		super(Unit.PX);
		setSize("100%", "100%");
		tabPanel.setWidth("100%");
		tabPanel.setHeight("100%");
		tabPanel.add(artAssetsPanel, "Art Assets");
		tabPanel.add(dungeonPanel, "Dungeon Editor");
		add(tabPanel);
		this.forceLayout();
	}
}
