package per.lambert.ebattleMat.client.maindisplay;

import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;

public class PropertyPanel extends ResizeComposite {

	ShellLayout mainLayoutShell;
	LayoutPanel mainPanel;

	public void setShellLayout(ShellLayout mainShell) {
		mainLayoutShell = mainShell;
	}
	public PropertyPanel() {
		setupPropertyPanel();
		setupEventHandling();
		initWidget(mainPanel);
	}
	private void setupPropertyPanel() {
		mainPanel = new LayoutPanel();
	}
	private void setupEventHandling() {
	}
}
