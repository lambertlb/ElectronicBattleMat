package per.lambert.ebattleMat.client.controls;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;

/**
 * Panel for handle all art assest in dungeon.
 * @author llambert
 *
 */
public class ArtAssetsPanel extends DockLayoutPanel {

	/**
	 * Add image to dungeon.
	 */
	private Button add = new Button("Add Image");
	/**
	 * Constructor.
	 */
	public ArtAssetsPanel() {
		super(Unit.PX);
		setSize("100%", "100%");
		createContent();
	}

	private void createContent() {
		add.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(final ClickEvent event) {
				addArtAsset();
			}
		});
		addNorth(add, 30);
	}

	protected void addArtAsset() {
	}
}
