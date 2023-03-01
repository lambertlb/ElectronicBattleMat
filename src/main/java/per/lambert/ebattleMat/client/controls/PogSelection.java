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

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;

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
	 * Dungeon Instance Pogs.
	 */
	private TreeItem dungeonInstanceTree = new TreeItem();
	/**
	 * Dungeon Instance Monsters.
	 */
	private TreeItem dungeonInstanceMonsterTree = new TreeItem();
	/**
	 * Dungeon Instance Room Objects.
	 */
	private TreeItem dungeonInstanceObjectsTree = new TreeItem();
	/**
	 * Session Instance Pogs.
	 */
	private TreeItem sessionInstanceTree = new TreeItem();
	/**
	 * Session Instance Monsters.
	 */
	private TreeItem sessionInstanceMonsterTree = new TreeItem();
	/**
	 * Session Instance Room Objects.
	 */
	private TreeItem sessionInstanceObjectsTree = new TreeItem();

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
		dungeonInstanceTree.setText("Dungeon Instance Pogs");
		pogTree.addItem(dungeonInstanceTree);
		dungeonInstanceMonsterTree.setText("Monsters");
		dungeonInstanceTree.addItem(dungeonInstanceMonsterTree);
		dungeonInstanceObjectsTree.setText("Room Objects");
		dungeonInstanceTree.addItem(dungeonInstanceObjectsTree);
		sessionInstanceTree.setText("Session Instance Pogs");
		pogTree.addItem(sessionInstanceTree);
		sessionInstanceMonsterTree.setText("Monsters");
		sessionInstanceTree.addItem(sessionInstanceMonsterTree);
		sessionInstanceObjectsTree.setText("Room Objects");
		sessionInstanceTree.addItem(sessionInstanceObjectsTree);
		setStyles();
	}

	/**
	 * Set styles.
	 */
	private void setStyles() {
		playerTree.getElement().setClassName("my-TreeItem");                
		commonPogTree.getElement().setClassName("my-TreeItem");                
		dungeonInstanceTree.getElement().setClassName("my-TreeItem");                
		sessionInstanceTree.getElement().setClassName("my-TreeItem");                
	}

	/**
	 * Dungeon data just loaded.
	 */
	private void dungeonDataLoaded() {
	}

	/**
	 * Pog was selected.
	 */
	private void selectPog() {
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
