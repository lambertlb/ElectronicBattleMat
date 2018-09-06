package per.lambert.ebattleMat.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

import per.lambert.ebattleMat.client.interfaces.ReasonForAction;

public class ReasonForActionEvent extends GwtEvent<ReasonForActionEventHandler> {

	private static Type<ReasonForActionEventHandler> reasonForActionEventType = new Type<ReasonForActionEventHandler>();
	private ReasonForAction reason;
	private Object data;

	public ReasonForActionEvent(final ReasonForAction reason, final Object data) {
		this.reason = reason;
		this.data = data;
	}

	public final ReasonForAction getReasonForAction() {
		return reason;
	}
	public final Object getData() {
		return this.data;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ReasonForActionEventHandler> getAssociatedType() {
		return reasonForActionEventType;
	}

	@Override
	protected void dispatch(ReasonForActionEventHandler handler) {
		handler.onReasonForAction(this);
	}

	public static Type<ReasonForActionEventHandler> getReasonForActionEventType() {
		return reasonForActionEventType;
	}

}
