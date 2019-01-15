package per.lambert.ebattleMat.client.controls.okCancelDialog;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.resizeableDialog.ResizableDialog;

public class OkCancelDialog extends ResizableDialog {
	private String labelText;
	private boolean okEnabled;
	private boolean cancelEnabled;

	private DockLayoutPanel dockLayoutPanel;
	private Grid centerGrid;
	private Button ok;
	private Button cancel;
	private Label dialogLabel;
	private FlowPanel centerContent;
	private HorizontalPanel southContent;

	public OkCancelDialog() {
		this("", false, false);
	}

	public OkCancelDialog(String caption, boolean enableOk, boolean enableCancel) {
		super();
		initialize();
		dialogLabel.setText(caption);
		enableOk(enableOk);
		enableCancel(enableCancel);
	}

	private void initialize() {
		dockLayoutPanel = new DockLayoutPanel(Unit.PX);
		dockLayoutPanel.setStyleName("popupPanel");
		dockLayoutPanel.setWidth("400px");
		dockLayoutPanel.setHeight("350px");
		dialogLabel = new Label();
		dialogLabel.setStyleName("sessionLabel");
		dockLayoutPanel.addNorth(dialogLabel, 30);
		southContent = new HorizontalPanel();
		dockLayoutPanel.addSouth(southContent, 30);
		ok = new Button("Ok");
		southContent.add(ok);
		cancel = new Button("Cancel");
		southContent.add(cancel);
		centerContent = new FlowPanel();
		centerContent.setHeight("100%");
		centerGrid = new Grid();
		centerContent.add(centerGrid);
		dockLayoutPanel.add(centerContent);
		setWidget(dockLayoutPanel);
	}

	protected void enableOk(boolean enable) {
		enableWidget(ok, enable);
	}

	protected void enableCancel(boolean enable) {
		enableWidget(cancel, enable);
	}

	protected HandlerRegistration addOkClickHandler(ClickHandler handler) {
		return (ok.addClickHandler(handler));
	}

	protected HandlerRegistration addCancelClickHandler(ClickHandler handler) {
		return (cancel.addClickHandler(handler));
	}

	protected Grid getCenterGrid() {
		return (centerGrid);
	}

	public static void enableWidget(Widget widget, boolean enable) {
		if (enable) {
			widget.getElement().removeAttribute("disabled");
		} else {
			widget.getElement().setAttribute("disabled", "disabled");
		}
	}
}
