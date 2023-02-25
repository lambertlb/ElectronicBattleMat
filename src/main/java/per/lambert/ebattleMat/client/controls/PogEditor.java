package per.lambert.ebattleMat.client.controls;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import per.lambert.ebattleMat.client.controls.ribbonBar.SelectedPog;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;

public class PogEditor extends DockLayoutPanel {
	/**
	 * button bar at top.
	 */
	private HorizontalPanel buttonBar = new HorizontalPanel();
	/**
	 * Create new pog button.
	 */
	private Button createNewPogButton;
	/**
	 * Create remove pog button.
	 */
	private Button removePogButton;
	/**
	 * Panel to hold center content.
	 */
	private LayoutPanel centerContent;
	/**
	 * Grid for sub-classes to use for content.
	 */
	private Grid centerGrid;
	/**
	 * label for Template name.
	 */
	private Label templateNameLabel;
	/**
	 * text box for template name.
	 */
	private TextBox templateName;
	/**
	 * Control to show selected pog.
	 */
	private SelectedPog selectedPog;
	/**
	 * Scroll panel.
	 */
	private ScrollPanel scrollPanel = new ScrollPanel();

	public PogEditor() {
		super(Unit.PX);
		setSize("100%", "100%");
		createContent();
		setupEventHandling();
	}

	private void createContent() {
		createNewPogButton = new Button("Create Pog");
		createNewPogButton.addStyleName("ribbonBarLabel");
		createNewPogButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				createPog();
			}
		});
		buttonBar.add(createNewPogButton);

		removePogButton = new Button("DELETE Pog");
		removePogButton.addStyleName("ribbonBarLabel");
		removePogButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				deletePog();
			}
		});
		buttonBar.add(removePogButton);
				
		addNorth(buttonBar, 30);
		createPogEditor();
		add(centerContent);
		forceLayout();
	}

	private void createPogEditor() {
		centerContent = new LayoutPanel();
		centerContent.setHeight("100%");
		centerContent.setWidth("100%");
		centerGrid = new Grid();
		centerGrid.setWidth("100%");
		centerGrid.resize(6, 2);
		centerGrid.getColumnFormatter().setWidth(0, "100px");
		selectedPog = new SelectedPog(null);
		VerticalPanel vpanel = new VerticalPanel();
		vpanel.add(centerGrid);
		vpanel.add(selectedPog);
		vpanel.add(scrollPanel);
		centerContent.add(vpanel);
		createGridContent();
	}

	private void createGridContent() {
		createPogName();
	}
	/**
	 * Create template name.
	 */
	private void createPogName() {
		templateNameLabel = new Label("Pog Name: ");
		templateNameLabel.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(0, 0, templateNameLabel);
		templateName = new TextBox();
		templateName.setWidth("100%");
		templateName.setStyleName("ribbonBarLabel");
		centerGrid.setWidget(0, 1, templateName);
		templateName.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				templateName.selectAll();
			}
		});
		templateName.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(final KeyUpEvent event) {
				validateForm();
			}
		});
	}

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

	private void dungeonDataLoaded() {
	}
	private void selectPog() {
	}
	private void createPog() {
	}
	private void deletePog() {
	}
	private void validateForm() {
	}
}
