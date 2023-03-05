/*
 * Copyright (C) 2023 Leon Lambert.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package per.lambert.ebattleMat.client.controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

/**
 * Control for handling pog selection.
 * 
 * @author LLambert
 *
 */
public class PogSelection extends DockLayoutPanel {
	class PogComparator implements Comparator<PogData> {
		@Override
		public int compare(final PogData a, final PogData b) {
			int cp = a.getName().compareToIgnoreCase(b.getName());
			return cp == 0 ? 1 : cp;
		}
	}

	/**
	 * Tree of pogs.
	 */
	private Tree pogTree = new Tree();
	/**
	 * Player tree.
	 */
	private TreeItem playerTree = new TreeItem();
	/**
	 * Common Pogs.
	 */
	private TreeItem commonPogTree = new TreeItem();
	/**
	 * Common Monsters.
	 */
	private TreeItem commonMonsterTree = new TreeItem();
	/**
	 * Common Room Objects.
	 */
	private TreeItem commonObjectsTree = new TreeItem();
	/**
	 * Dungeon Level Pogs.
	 */
	private TreeItem dungeonLevelTree = new TreeItem();
	/**
	 * Dungeon Level Monsters.
	 */
	private TreeItem dungeonLevelMonsterTree = new TreeItem();
	/**
	 * Dungeon Level Room Objects.
	 */
	private TreeItem dungeonLevelObjectsTree = new TreeItem();
	/**
	 * Session Level Pogs.
	 */
	private TreeItem sessionLevelTree = new TreeItem();
	/**
	 * Session Level Monsters.
	 */
	private TreeItem sessionLevelMonsterTree = new TreeItem();
	/**
	 * Session Level Room Objects.
	 */
	private TreeItem sessionLevelObjectsTree = new TreeItem();

	/**
	 * Constructor.
	 */
	public PogSelection() {
		super(Unit.PX);
		setSize("100%", "100%");
		createContent();
		setupEventHandling();
	}

	/**
	 * Setup event handling.
	 */
	private void setupEventHandling() {
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.DungeonDataLoaded) {
					dungeonDataLoaded();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.SessionDataChanged) {
					sessionDataChanged();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.SessionDataSaved) {
					sessionDataChanged();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.PogDataChanged) {
					pogDataChanged();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonDataReadyToJoin) {
					sessionDataChanged();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.PogWasSelected) {
					selectPog();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonSelectedLevelChanged) {
					newLevelSelected();
					return;
				}
			}
		});
		pogTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(final SelectionEvent<TreeItem> event) {
				selectionChanged(event.getSelectedItem());
			}
		});
	}

	/**
	 * Create form content.
	 */
	private void createContent() {
		DockLayoutPanel northPanel = new DockLayoutPanel(Unit.PX);
		northPanel.setSize("100%", "100%");
		ScrollPanel scrollPanel = new ScrollPanel();
		addBranches();
		scrollPanel.add(pogTree);
		add(scrollPanel);
		forceLayout();
	}

	/**
	 * Add branches to tree.
	 */
	private void addBranches() {
		playerTree.setText("Players");
		pogTree.addItem(playerTree);
		commonPogTree.setText("Common Resource Pogs");
		pogTree.addItem(commonPogTree);
		commonMonsterTree.setText("Monsters");
		commonPogTree.addItem(commonMonsterTree);
		commonObjectsTree.setText("Room Objects");
		commonPogTree.addItem(commonObjectsTree);
		dungeonLevelTree.setText("Dungeon Level Pogs");
		pogTree.addItem(dungeonLevelTree);
		dungeonLevelMonsterTree.setText("Monsters");
		dungeonLevelTree.addItem(dungeonLevelMonsterTree);
		dungeonLevelObjectsTree.setText("Room Objects");
		dungeonLevelTree.addItem(dungeonLevelObjectsTree);
		sessionLevelTree.setText("Session Level Pogs");
		pogTree.addItem(sessionLevelTree);
		sessionLevelMonsterTree.setText("Monsters");
		sessionLevelTree.addItem(sessionLevelMonsterTree);
		sessionLevelObjectsTree.setText("Room Objects");
		sessionLevelTree.addItem(sessionLevelObjectsTree);
		setStyles();
	}

	/**
	 * Set styles.
	 */
	private void setStyles() {
		playerTree.getElement().setClassName("my-TreeItem");
		commonPogTree.getElement().setClassName("my-TreeItem");
		dungeonLevelTree.getElement().setClassName("my-TreeItem");
		sessionLevelTree.getElement().setClassName("my-TreeItem");
	}

	/**
	 * Dungeon data just loaded.
	 */
	private void dungeonDataLoaded() {
		boolean inEditMode = ServiceManager.getDungeonManager().isEditMode();
		fillCommonResourceTrees();
		fillDungeonLevelTrees();
		if (!inEditMode) {
			fillPlayerTree();
			fillSessionLevelTree();
			playerTree.setVisible(!inEditMode);
			sessionLevelTree.setVisible(!inEditMode);
		}
	}

	/**
	 * new level of dungeon selected.
	 */
	private void newLevelSelected() {
		fillDungeonLevelTrees();
		if (!ServiceManager.getDungeonManager().isEditMode()) {
			fillSessionLevelTree();
		}
	}

	/**
	 * Session data changed.
	 */
	private void sessionDataChanged() {
		fillSessionLevelTree();
		fillPlayerTree();
	}

	/**
	 * Pog data has changed.
	 */
	private void pogDataChanged() {
		dungeonDataLoaded();
	}

	/**
	 * Fill trees for common resources.
	 */
	private void fillCommonResourceTrees() {
		buildSortedTree(commonMonsterTree, ServiceManager.getDungeonManager().getMonsterTemplatePogs());
		buildSortedTree(commonObjectsTree, ServiceManager.getDungeonManager().getRoomObjectTemplatePogs());
	}

	/**
	 * Fill trees with level based pogs.
	 */
	private void fillDungeonLevelTrees() {
		buildSortedTree(dungeonLevelMonsterTree, ServiceManager.getDungeonManager().getCurrentDungeonLevelData().getMonsters().getPogList());
		buildSortedTree(dungeonLevelObjectsTree, ServiceManager.getDungeonManager().getCurrentDungeonLevelData().getRoomObjects().getPogList());
	}

	/**
	 * Fill tree with player pogs.
	 */
	private void fillPlayerTree() {
		buildSortedTree(playerTree, ServiceManager.getDungeonManager().getPlayersForCurrentSession());
	}

	/**
	 * Fill tree with session level pogs.
	 */
	private void fillSessionLevelTree() {
		buildSortedTree(sessionLevelMonsterTree, ServiceManager.getDungeonManager().getMonstersForCurrentLevel());
		buildSortedTree(sessionLevelObjectsTree, ServiceManager.getDungeonManager().getRoomObjectsForCurrentLevel());
	}

	/**
	 * Add new tree item.
	 * 
	 * @param treeItem
	 * @param pog
	 */
	private void addTreeItem(final TreeItem treeItem, final PogData pog) {
		TreeItem newItem = new TreeItem();
		newItem.getElement().setClassName("my-TreeItem");
		newItem.setText(pog.getName());
		newItem.setUserObject(pog);
		treeItem.addItem(newItem);
	}

	/**
	 * sort and add these to tree.
	 * 
	 * @param treeItem
	 * @param pogs
	 */
	private void buildSortedTree(final TreeItem treeItem, final PogData[] pogs) {
		if (pogs == null) {
			return;
		}
		treeItem.removeItems();
		List<PogData> keys = new ArrayList<>();
		for (PogData pog : pogs) {
			keys.add(pog);
		}
		Collections.sort(keys, new PogComparator());
		for (PogData key : keys) {
			addTreeItem(treeItem, key);
		}
	}

	/**
	 * Pog was selected.
	 */
	private void selectPog() {
	}

	/**
	 * handle tree item selected.
	 * 
	 * @param selectedItem
	 */
	private void selectionChanged(final TreeItem selectedItem) {
		PogData data = (PogData) selectedItem.getUserObject();
		if (data != null) {
			ServiceManager.getDungeonManager().setSelectedPog(data);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void onResize() {
		super.onResize();
	}
}
