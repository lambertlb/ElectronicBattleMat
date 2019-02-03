package per.lambert.ebattleMat.client.battleMatDisplay;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * Container for ribbon bar.
 * @author LLambert
 *
 */
public class RibbonBarContainer extends Composite {

	/**
	 * UI binder.
	 */
	private static RibbonBarContainerUiBinder uiBinder = GWT.create(RibbonBarContainerUiBinder.class);

	/**
	 * Interface for UI binder.
	 * @author LLambert
	 *
	 */
	interface RibbonBarContainerUiBinder extends UiBinder<Widget, RibbonBarContainer> {
	}

	/**
	 * Constructor.
	 */
	public RibbonBarContainer() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
