package per.lambert.ebattleMat.client.controls;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.FileList;

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
	 * Tree of files.
	 */
	private Tree	fileTree = new Tree();
	/**
	 * Constructor.
	 */
	public ArtAssetsPanel() {
		super(Unit.PX);
		setSize("100%", "100%");
		createContent();
		setupEventHandling();
	}

	private void setupEventHandling() {
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.DungeonDataLoaded) {
					loadFiles();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.SessionDataSaved) {
					loadFiles();
					return;
				}
			}
		});
	}

	private void createContent() {
		add.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(final ClickEvent event) {
				addArtAsset();
			}
		});
		addNorth(add, 30);
		add(fileTree);
	}
	/**
	 * Load in files.
	 */
	private void loadFiles() {
		ServiceManager.getDungeonManager().getFileList(ServiceManager.getDungeonManager().getDirectoryForCurrentDungeon(), new IUserCallback() {
			@Override
			public void onSuccess(final Object sender, final Object data) {
				buildTreeOfAssets((FileList)data);
			}
			@Override
			public void onError(final Object sender, final IErrorInformation error) {
			}
		});
	}

	/**
	 * Build tree of assets.
	 * @param data file list
	 */
	private void buildTreeOfAssets(final FileList data) {
		fileTree.clear();
		TreeItem dungeonAssets = new TreeItem();
		dungeonAssets.setText("Dungeon Assets");
		for (String filename : data.getFileNames()) {
			TreeItem asset = new TreeItem();
			asset.setText(filename);
			dungeonAssets.addItem(asset);
		}
		fileTree.addItem(dungeonAssets);
	}

	private void addArtAsset() {
	}
}
