package per.lambert.ebattleMat.client.controls.ribbonBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.battleMatDisplay.BattleMatLayout;
import per.lambert.ebattleMat.client.battleMatDisplay.PogCanvas;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

/**
 * Control for handling selected pog.
 * 
 * This will scale the picture to the size of the host panel. It will scale to keep proportions the same.
 * 
 * @author LLambert
 *
 */
public class SelectedPog extends Composite {

	/**
	 * UI binder.
	 */
	private static SelectedPogUiBinder uiBinder = GWT.create(SelectedPogUiBinder.class);

	/**
	 * Interface for UI binder.
	 * 
	 * @author LLambert
	 *
	 */
	interface SelectedPogUiBinder extends UiBinder<Widget, SelectedPog> {
	}

	/**
	 * Panel to show pog.
	 */
	@UiField
	@SuppressWarnings("VisibilityModifier")
	FlowPanel pogPanel;

	/**
	 * Host panel used for size.
	 */
	@UiField
	@SuppressWarnings("VisibilityModifier")
	HTMLPanel hostPanel;

	/**
	 * Canvas for showing pog.
	 */
	private PogCanvas scalablePog = new PogCanvas();
	
	/**
	 * widget to use for resizing.
	 */
	private Widget resizeWidget;

	/**
	 * Constructor.
	 * 
	 * @param resizeWidget widget to use for resizing.
	 */
	public SelectedPog(final Widget resizeWidget) {
		this.resizeWidget = resizeWidget;
		initWidget(uiBinder.createAndBindUi(this));
		scalablePog.setShowNormalSizeOnly(true);
		scalablePog.setForceBackgroundColor(true);
		pogPanel.add(scalablePog);
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.PogWasSelected) {
					pogSelected();
					return;
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onLoad() {
		super.onLoad();
		unselectedPogLook();
	}

	/**
	 * No pog yet so set the look.
	 */
	public void unselectedPogLook() {
		int height = (int) BattleMatLayout.RIBBON_BAR_SIZE;
		pogPanel.setWidth("" + height + "px");
		if (scalablePog != null) {
			scalablePog.showImage(false);
		}
	}

	/**
	 * Pog has been selected.
	 */
	public void pogSelected() {
		PogData selectePog = ServiceManager.getDungeonManager().getSelectedPog();
		if (selectePog == null || hostPanel == null) {
			return;
		}

		scalablePog.showImage(true);
		scalablePog.setPogData(selectePog, true);
		reDraw();
	}

	/**
	 * Redraw pog.
	 */
	public void reDraw() {
		int height = resizeWidget.getOffsetHeight();
		if (height < 20) {
			height = 20;
		}
		scalablePog.setPogSizing(height, 0.0, 1.0);
	}
}
