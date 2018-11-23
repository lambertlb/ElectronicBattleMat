package per.lambert.ebattleMat.client.battleMatDisplay;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RibbonBarContainer extends Composite {

	private static RibbonBarContainerUiBinder uiBinder = GWT.create(RibbonBarContainerUiBinder.class);

	interface RibbonBarContainerUiBinder extends UiBinder<Widget, RibbonBarContainer> {
	}

	public RibbonBarContainer() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
