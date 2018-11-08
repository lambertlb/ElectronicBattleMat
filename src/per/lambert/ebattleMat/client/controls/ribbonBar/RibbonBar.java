package per.lambert.ebattleMat.client.controls.ribbonBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RibbonBar extends Composite {

	private static RibbonBarUiBinder uiBinder = GWT.create(RibbonBarUiBinder.class);

	interface RibbonBarUiBinder extends UiBinder<Widget, RibbonBar> {
	}

	public RibbonBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
