package per.lambert.ebattleMat.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * the handler to process the reason for action event.
 *
 */
public interface ReasonForActionEventHandler extends EventHandler {
	/**
	 * the reason for action event handler function.
	 * @param event reason for action
	 */
	void onReasonForAction(ReasonForActionEvent event);
}
