package per.lambert.ebattleMat.client.controls;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.Constants;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.FileList;

/**
 * Panel for handle all art assest in dungeon.
 * 
 * @author llambert
 *
 */
public class ArtAssetsPanel extends DockLayoutPanel {

	/**
	 * button bar at top.
	 */
	private HorizontalPanel buttonBar = new HorizontalPanel();
	/**
	 * Add image to dungeon.
	 */
	private Button uploadAsset = new Button("Upload Asset");
	/**
	 * Art assets.
	 */
	private Button downloadAssetsButton = new Button("Download Asset");
	/**
	 * Art assets.
	 */
	private Button deleteAssetsButton = new Button("Delete Asset");
	/**
	 * Form panel for uploading files.
	 */
	private FormPanel formPanel = new FormPanel();
	/**
	 * Up load widget.
	 */
	private FileUpload fileUpload;
	/**
	 * Tree of files.
	 */
	private Tree fileTree = new Tree();
	/**
	 * Dungeon asset tree.
	 */
	private TreeItem dungeonAssets = new TreeItem();
	/**
	 * Monster asset tree.
	 */
	private TreeItem monsterAssets = new TreeItem();
	/**
	 * Room asset tree.
	 */
	private TreeItem roomAssets = new TreeItem();

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
		fileTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(final SelectionEvent<TreeItem> event) {
				handleItemSelected(event.getSelectedItem());
			}

		});
	}

	private void createContent() {
		uploadAsset.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				upLoadAsset();
			}
		});
		buttonBar.add(uploadAsset);
		downloadAssetsButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				downloadAsset();
			}
		});
		downloadAssetsButton.setEnabled(false);
		buttonBar.add(downloadAssetsButton);
		deleteAssetsButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				deletAsset();
			}
		});
		deleteAssetsButton.setEnabled(false);
		buttonBar.add(deleteAssetsButton);

		formPanel.setWidget(fileUpload);
		buttonBar.add(formPanel);
		addNorth(buttonBar, 30);
		fileTree.clear();
		dungeonAssets.setText("Dungeon Assets");
		monsterAssets.setText("Global Monster Assets");
		roomAssets.setText("Global Room Assets");
		fileTree.addItem(dungeonAssets);
		fileTree.addItem(monsterAssets);
		fileTree.addItem(roomAssets);
		add(fileTree);
	}

	/**
	 * Load in files.
	 */
	private void loadFiles() {
		ServiceManager.getDungeonManager().getFileList(ServiceManager.getDungeonManager().getDirectoryForCurrentDungeon(), new IUserCallback() {
			@Override
			public void onSuccess(final Object sender, final Object data) {
				buildTreeOfAssets((FileList) data);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
			}
		});
		ServiceManager.getDungeonManager().getFileList("/" + Constants.DUNGEON_DATA_LOCATION +  Constants.DUNGEON_MONSTER_LOCATION, new IUserCallback() {
			@Override
			public void onSuccess(final Object sender, final Object data) {
				buildTreeOfAssets((FileList) data);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
			}
		});
		ServiceManager.getDungeonManager().getFileList("/" + Constants.DUNGEON_DATA_LOCATION + Constants.DUNGEON_ROOMOBJECT_LOCATION, new IUserCallback() {
			@Override
			public void onSuccess(final Object sender, final Object data) {
				buildTreeOfAssets((FileList) data);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
			}
		});
	}

	/**
	 * Build tree of assets.
	 * 
	 * @param data file list
	 */
	private void buildTreeOfAssets(final FileList data) {
		TreeItem itemToPopulate;
		if (data.getFilePath().contains("dungeons")) {
			itemToPopulate = dungeonAssets;
		} else if (data.getFilePath().contains("monsters")) {
			itemToPopulate = monsterAssets;
		} else {
			itemToPopulate = roomAssets;
		}
		itemToPopulate.removeItems();
		itemToPopulate.setUserObject(data.getFilePath());
		for (String filename : data.getFileNames()) {
			TreeItem asset = new TreeItem();
			asset.setText(filename);
			asset.setUserObject(filename);
			itemToPopulate.addItem(asset);
		}
	}

	/**
	 * Tree item selected.
	 * 
	 * @param selectedItem selected item
	 */
	private void handleItemSelected(final TreeItem selectedItem) {
		String data = (String) selectedItem.getUserObject();
		if (data == null) {
			deleteAssetsButton.setEnabled(false);
			downloadAssetsButton.setEnabled(false);
			return;
		}
		if (data.startsWith("/")) {
			deleteAssetsButton.setEnabled(false);
			downloadAssetsButton.setEnabled(false);
			return;
		}
		if (data.endsWith(".json")) {
			deleteAssetsButton.setEnabled(false);
			downloadAssetsButton.setEnabled(true);
			return;
		}
		deleteAssetsButton.setEnabled(true);
		downloadAssetsButton.setEnabled(true);
	}

	/**
	 * Up load a file.
	 */
	private void upLoadAsset() {
	}

	/**
	 * down load a file.
	 */
	private void downloadAsset() {
		String filename = (String)fileTree.getSelectedItem().getUserObject();
		String folder = (String)fileTree.getSelectedItem().getParentItem().getUserObject();
		ServiceManager.getDungeonManager().downloadFile(folder, filename);
	}

	/**
	 * down load a file.
	 */
	private void deletAsset() {
	}
}
