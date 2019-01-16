package per.lambert.ebattleMat.client.controls.okCancelDialog;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.resizeableDialog.ResizableDialog;

public class OkCancelDialog extends ResizableDialog {
	private DockLayoutPanel dockLayoutPanel;
	private Grid centerGrid;
	private Button ok;
	private Button cancel;
	private FlowPanel centerContent;
	private HorizontalPanel southContent;

	protected Grid getCenterGrid() {
		return (centerGrid);
	}

	public OkCancelDialog() {
		this("", false, false);
	}

	public OkCancelDialog(String caption, boolean okVisible, boolean cancelVisble) {
		this(caption, okVisible, cancelVisble, 400, 350);
	}

	public OkCancelDialog(String caption, boolean okVisible, boolean cancelVisble, int height, int width) {
		super();
		setText(caption);
		createContent();
		ok.setVisible(okVisible);
		cancel.setVisible(cancelVisble);
		dockLayoutPanel.setWidth("" + width + "px");
		dockLayoutPanel.setHeight("" + height + "px");
	}

	private void createContent() {
		dockLayoutPanel = new DockLayoutPanel(Unit.PX);
		dockLayoutPanel.setStyleName("popupPanel");
		southContent = new HorizontalPanel();
		dockLayoutPanel.addSouth(southContent, 30);
		ok = new Button("Ok");
		ok.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onOkClick(event);
			}
		});
		southContent.add(ok);
		cancel = new Button("Cancel");
		cancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onCancelClick(event);
			}
		});
		southContent.add(cancel);
		centerContent = new FlowPanel();
		centerContent.setHeight("100%");
		centerGrid = new Grid();
		centerContent.add(centerGrid);
		dockLayoutPanel.add(centerContent);
		setWidget(dockLayoutPanel);
	}

	private void initialize() {
	}

	@Override
	public void show() {
		super.show();
		initialize();
	}


	public void enableOk(boolean enable) {
		enableWidget(ok, enable);
	}

	public void enableCancel(boolean enable) {
		enableWidget(cancel, enable);
	}

	protected void onOkClick(ClickEvent event) {
	}

	protected void onCancelClick(ClickEvent event) {
	}

	public static void enableWidget(Widget widget, boolean enable) {
		if (enable) {
			widget.getElement().removeAttribute("disabled");
		} else {
			widget.getElement().setAttribute("disabled", "disabled");
		}
	}
}
