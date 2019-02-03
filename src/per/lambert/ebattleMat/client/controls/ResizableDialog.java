package per.lambert.ebattleMat.client.controls;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * DialogBox has a grid that contains the the grow handles following is the coordinates for the the various handles.
 *  0,2 Top Right
 *  0,1 Top Center
 *  0,0 Top Left
 *  1,0 Middle Left
 *  1,2 Middle Right
 *  2,2 Bottom Right
 *  2,1 Bottom Center
 *  2,0 Bottom Left
 * 
 * @author LLambert
 *
 */
public class ResizableDialog extends DialogBox {

	/**
	 * Position of resize.
	 * @author LLambert
	 *
	 */
	public enum ResizePosition {
		/**
		 * Top of window.
		 */
		TOP,
		/**
		 * Top right corner of window.
		 */
		TOPRIGHT,
		/**
		 * Top left corner of window.
		 */
		TOPLEFT,
		/**
		 * Right side of window.
		 */
		RIGHT,
		/**
		 * Left side of window.
		 */
		LEFT,
		/**
		 * Bottom of window.
		 */
		BOTTOM,
		/**
		 * Bottom right corner of window.
		 */
		BOTTOMRIGHT,
		/**
		 * Bottom left of window.
		 */
		BOTTOMLEFT,
		/**
		 * Undefined position.
		 */
		UNDEFINED
	}

	/**
	 * Margin around edge of window.
	 */
	private static final int MARGIN = 5;
	/**
	 * Minimum wisth of window.
	 */
	private static final int MIN_WIDTH = 100;
	/**
	 * Minimum heifht of window.
	 */
	private static final int MIN_HEIGHT = 100;
	/**
	 * Starting X of drag.
	 */
	private int startingX;
	/**
	 * Starting Y of drag.
	 */
	private int startingY;
	/**
	 * Position of last click.
	 */
	private ResizePosition resizePosition = ResizePosition.UNDEFINED;
	/**
	 * Delta X from starting position.
	 */
	private int deltaXFromStarting;
	/**
	 * Delta Y from starting position.
	 */
	private int deltaYFromStarting;
	/**
	 * Height of client window.
	 */
	private int clientHeight;
	/**
	 * Width of client window.
	 */
	private int clientWidth;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void beginDragging(final MouseDownEvent event) {
		resizePosition = computeResizePosition(event.getNativeEvent());
		if (resizePosition == ResizePosition.UNDEFINED) {
			super.beginDragging(event);
			return;
		}
		DOM.setCapture(getElement());
		startingX = event.getClientX();
		startingY = event.getClientY();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void continueDragging(final MouseMoveEvent event) {
		if (resizePosition == ResizePosition.UNDEFINED) {
			super.continueDragging(event);
			return;
		}
		int deltaX = event.getClientX() - startingX;
		int deltaY = event.getClientY() - startingY;
		startingX = event.getClientX();
		startingY = event.getClientY();
		resizeWindow(deltaX, deltaY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void endDragging(final MouseUpEvent event) {
		if (resizePosition == ResizePosition.UNDEFINED) {
			super.endDragging(event);
			return;
		}
		DOM.releaseCapture(getElement());
		resizePosition = ResizePosition.UNDEFINED;
		onWindowResized();
	}

	/**
	 * On window resized.
	 * 
	 * for Sub-classes to know when window size changed.
	 */
	protected void onWindowResized() {
	}

	/**
	 * Set new size of window.
	 * @param deltaX delta X
	 * @param deltaY delta Y
	 */
	private void resizeWindow(final int deltaX, final int deltaY) {
		Widget widget = getWidget();
		int width = widget.getOffsetWidth() + deltaX;
		width = width < MIN_WIDTH ? MIN_WIDTH : width;
		widget.setWidth(width + "px");
		int height = widget.getOffsetHeight() + deltaY;
		height = height < MIN_HEIGHT ? MIN_HEIGHT : height;
		widget.setHeight(height + "px");
	}

	/**
	 * compute Resize Position.
	 * @param event native event.
	 * @return position of window being resized.
	 */
	private ResizePosition computeResizePosition(final NativeEvent event) {
		computeNewWindowSize();
		if (isBottomRight(event)) {
			return ResizePosition.BOTTOMRIGHT;
		}
		return ResizePosition.UNDEFINED;
	}

	/**
	 * Get new size of window.
	 */
	private void computeNewWindowSize() {
		Element growElement = this.getCellElement(2, 2).getParentElement();
		clientWidth = growElement.getClientWidth();
		clientHeight = growElement.getClientHeight();
	}

	/**
	 * Compute delta from starting positions.
	 * @param event native event.
	 * @param row to examine.
	 * @param column column to examine.
	 */
	private void computeDeltas(final NativeEvent event, final int row, final int column) {
		Element growElement = this.getCellElement(row, column).getParentElement();
		deltaXFromStarting = event.getClientX() - growElement.getAbsoluteLeft() + growElement.getScrollLeft() + growElement.getOwnerDocument().getScrollLeft();
		deltaYFromStarting = event.getClientY() - growElement.getAbsoluteTop() + growElement.getScrollTop() + growElement.getOwnerDocument().getScrollTop();
	}

	/**
	 * Was change to top of window.
	 * @param event native event.
	 * @return true if top was changed.
	 */
	@SuppressWarnings("unused")
	private boolean isTop(final NativeEvent event) {
		computeDeltas(event, 0, 1);
		return (deltaYFromStarting >= 0 && deltaYFromStarting < clientHeight);
	}

	/**
	 * Was change to top left of window.
	 * @param event native event.
	 * @return true if top left was changed.
	 */
	@SuppressWarnings("unused")
	private boolean isTopLeft(final NativeEvent event) {
		computeDeltas(event, 0, 0);
		return ((deltaXFromStarting >= 0 && deltaXFromStarting < clientWidth && deltaYFromStarting >= 0 && deltaYFromStarting < clientHeight + MARGIN)
				|| (deltaYFromStarting >= 0 && deltaYFromStarting < clientHeight && deltaXFromStarting >= 0 && deltaXFromStarting < clientWidth + MARGIN));
	}

	/**
	 * Was change to top right of window.
	 * @param event native event.
	 * @return true if top right was changed.
	 */
	@SuppressWarnings("unused")
	private boolean isTopRight(final NativeEvent event) {
		computeDeltas(event, 0, 2);
		return ((deltaXFromStarting >= 0 && deltaXFromStarting < clientWidth && deltaYFromStarting >= 0 && deltaYFromStarting < clientHeight + MARGIN)
				|| (deltaYFromStarting >= 0 && deltaYFromStarting < clientHeight && deltaXFromStarting >= -MARGIN && deltaXFromStarting < clientWidth));
	}

	/**
	 * Was change to bottom left of window.
	 * @param event native event.
	 * @return true if bottom left was changed.
	 */
	@SuppressWarnings("unused")
	private boolean isBottomLeft(final NativeEvent event) {
		computeDeltas(event, 2, 0);
		return ((deltaXFromStarting >= 0 && deltaXFromStarting < clientWidth && deltaYFromStarting >= -MARGIN && deltaYFromStarting < clientHeight)
				|| (deltaYFromStarting >= 0 && deltaYFromStarting < clientHeight && deltaXFromStarting >= 0 && deltaXFromStarting < clientWidth + MARGIN));
	}

	/**
	 * Was change to bottom right of window.
	 * @param event native event.
	 * @return true if bottom right was changed.
	 */
	private boolean isBottomRight(final NativeEvent event) {
		computeDeltas(event, 2, 2);
		return ((deltaXFromStarting >= 0 && deltaXFromStarting < clientWidth && deltaYFromStarting >= -MARGIN && deltaYFromStarting < clientHeight)
				|| (deltaYFromStarting >= 0 && deltaYFromStarting < clientHeight && deltaXFromStarting >= -MARGIN && deltaXFromStarting < clientWidth));
	}

	/**
	 * Was change to bottom of window.
	 * @param event native event.
	 * @return true if bottom was changed.
	 */
	@SuppressWarnings("unused")
	private boolean isBottom(final NativeEvent event) {
		computeDeltas(event, 2, 1);
		return (deltaYFromStarting >= 0 && deltaYFromStarting < clientHeight);
	}

	/**
	 * Was change to left of window.
	 * @param event native event.
	 * @return true if left was changed.
	 */
	@SuppressWarnings("unused")
	private boolean isLeft(final NativeEvent event) {
		computeDeltas(event, 1, 0);
		return (deltaXFromStarting >= 0 && deltaXFromStarting < clientWidth);
	}

	/**
	 * Was change to right of window.
	 * @param event native event.
	 * @return true if right was changed.
	 */
	@SuppressWarnings("unused")
	private boolean isRight(final NativeEvent event) {
		computeDeltas(event, 1, 2);
		return (deltaXFromStarting >= 0 && deltaXFromStarting < clientWidth);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onBrowserEvent(final Event event) {
		switch (event.getTypeInt()) {
		case Event.ONMOUSEDOWN:
		case Event.ONMOUSEUP:
		case Event.ONMOUSEMOVE:
		case Event.ONMOUSEOVER:
		case Event.ONMOUSEOUT:
			ResizePosition possible = computeResizePosition(event);
			if (resizePosition != ResizePosition.UNDEFINED || possible != ResizePosition.UNDEFINED) {
				switch (DOM.eventGetType(event)) {
				case Event.ONMOUSEOVER:
				case Event.ONMOUSEOUT:
					Element related = event.getRelatedEventTarget().cast();
					if (related != null && getElement().isOrHasChild(related)) {
						return;
					}
					break;
				default:
				}
				DomEvent.fireNativeEvent(event, this, this.getElement());
				getElement().getStyle().setCursor(possible != ResizePosition.UNDEFINED ? Cursor.POINTER : Cursor.AUTO);
				return;
			}
			getElement().getStyle().setCursor(possible != ResizePosition.UNDEFINED ? Cursor.POINTER : Cursor.AUTO);
		default:
			break;
		}
		super.onBrowserEvent(event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onPreviewNativeEvent(final NativePreviewEvent event) {
		NativeEvent nativeEvent = event.getNativeEvent();

		if (!event.isCanceled() && (event.getTypeInt() == Event.ONMOUSEDOWN) && computeResizePosition(event.getNativeEvent()) != ResizePosition.UNDEFINED) {
			nativeEvent.preventDefault();
		}
		super.onPreviewNativeEvent(event);
	}

	/**
	 * Get width of dialog.
	 * @return width of dialog
	 */
	protected int getDialogWidth() {
		return (getWidget().getOffsetWidth());
	}

	/**
	 * Get height of dialog.
	 * @return height of dialog
	 */
	protected int getDialogHeight() {
		return (getWidget().getOffsetHeight());
	}
}
