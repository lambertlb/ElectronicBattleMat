package per.lambert.ebattleMat.client.controls.topPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TopPanelControl extends Composite {

	private static TopPanelControlUiBinder uiBinder = GWT.create(TopPanelControlUiBinder.class);

	interface TopPanelControlUiBinder extends UiBinder<Widget, TopPanelControl> {
	}

	public TopPanelControl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
