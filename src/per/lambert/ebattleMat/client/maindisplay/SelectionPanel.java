package per.lambert.ebattleMat.client.maindisplay;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;

public class SelectionPanel extends ResizeComposite {
	LayoutPanel mainPanel;
	FlowPanel tabHolder;
	Button expandCollapseSelectionPanelButton;
	boolean selectionPanelExpanded = false;
	ShellLayout mainLayoutShell;

	public void setShellLayout(ShellLayout mainShell) {
		mainLayoutShell = mainShell;
	}

	public SelectionPanel() {
		setupSelectionPanel();
		setupEventHandling();
		initWidget(mainPanel);
	}

	private void setupEventHandling() {
		expandCollapseSelectionPanelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (selectionPanelExpanded) {
					expandCollapseSelectionPanelButton.setText("Expand");
				} else {
					expandCollapseSelectionPanelButton.setText("Collapse");
				}
				selectionPanelExpanded = !selectionPanelExpanded;
				if (mainLayoutShell != null)
					mainLayoutShell.setSelectionPanelExpanded(selectionPanelExpanded);
			}
		});
	}

	private void setupSelectionPanel() {
		mainPanel = new LayoutPanel();
		FlowPanel subMainPanel = new FlowPanel();
		expandCollapseSelectionPanelButton = new Button("Expand");
		subMainPanel.add(new Label("Selection Panel"));
		subMainPanel.add(expandCollapseSelectionPanelButton);
		mainPanel.add(subMainPanel);
	}
}
