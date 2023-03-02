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

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.Constants;
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
				if (event.getReasonForAction() == ReasonForAction.SessionDataSaved) {
					dungeonDataLoaded();
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
		playerTree.setVisible(!inEditMode);
		sessionLevelTree.setVisible(!inEditMode);
		fillCommonResourceTrees();
		fillLevelTrees();
	}

	/**
	 * Fill trees for common resources.
	 */
	private void fillCommonResourceTrees() {
		fillTree(commonMonsterTree, ServiceManager.getDungeonManager().getSortedCommonTemplates(Constants.POG_TYPE_MONSTER));
		fillTree(commonObjectsTree, ServiceManager.getDungeonManager().getSortedCommonTemplates(Constants.POG_TYPE_ROOMOBJECT));
	}

	/**
	 * Fill This tree.
	 * @param treeItem to fill
	 * @param dataArray to fill with
	 */
	private void fillTree(final TreeItem treeItem, final ArrayList<PogData> dataArray) {
		treeItem.removeItems();
		for (PogData pog : dataArray) {
			TreeItem newItem = new TreeItem();
			newItem.getElement().setClassName("my-TreeItem");                
			newItem.setText(pog.getName());
			newItem.setUserObject(pog);
			treeItem.addItem(newItem);
		}
	}

	/**
	 * Fill trees with level based pogs.
	 */
	private void fillLevelTrees() {
	}

	/**
	 * new level of dungeon selected.
	 */
	private void newLevelSelected() {
		fillLevelTrees();
	}

	/**
	 * Pog was selected.
	 */
	private void selectPog() {
	}
	
	/**
	 * handle tree item selected.
	 * @param selectedItem
	 */
	private void selectionChanged(final TreeItem selectedItem) {
		PogData data = (PogData)selectedItem.getUserObject();
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
