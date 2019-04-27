package per.lambert.ebattleMat.client.event;

import com.google.gwt.event.shared.GwtEvent;

import per.lambert.ebattleMat.client.interfaces.ReasonForAction;

/**
 * Event for handling reason for action.
 * 
 * @author LLambert
 *
 */
public class ReasonForActionEvent extends GwtEvent<ReasonForActionEventHandler> {
	/**
	 * Type of event.
	 */
	private static Type<ReasonForActionEventHandler> reasonForActionEventType = new Type<ReasonForActionEventHandler>();
	/**
	 * Reason for event.
	 */
	private ReasonForAction reason;
	/**
	 * Data for event.
	 */
	private Object data;

	/**
	 * Constructor.
	 * 
	 * @param reason for event
	 * @param data for event
	 */
	public ReasonForActionEvent(final ReasonForAction reason, final Object data) {
		this.reason = reason;
		this.data = data;
	}

	/**
	 * Get reason for action.
	 * 
	 * @return reason for action.
	 */
	public final ReasonForAction getReasonForAction() {
		return reason;
	}

	/**
	 * Get data for event.
	 * 
	 * @return data for event.
	 */
	public final Object getData() {
		return this.data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ReasonForActionEventHandler> getAssociatedType() {
		return reasonForActionEventType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void dispatch(final ReasonForActionEventHandler handler) {
		handler.onReasonForAction(this);
	}

	/**
	 * Get reason for event type.
	 * 
	 * @return type for reason of event
	 */
	public static Type<ReasonForActionEventHandler> getReasonForActionEventType() {
		return reasonForActionEventType;
	}
}
