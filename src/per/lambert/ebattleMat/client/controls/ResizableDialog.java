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

/*
 * DialogBox has a grid that contains the the grow handles
 * following is the coordinates for the the various handles.
 *  0,2 Top Right
 * 	0,1 Top Center
 * 	0,0 Top Left
 * 	1,0 Middle Left
 * 	1,2 Middle Right
 * 	2,2 Bottom Right
 * 	2,1 Bottom Center
 * 	2,0 Bottom Left
 */
public class ResizableDialog extends DialogBox {

	public enum ResizeHandle {
		TOP, TOPRIGHT, TOPLEFT, RIGHT, LEFT, BOTTOM, BOTTOMRIGHT, BOTTOMLEFT, UNDEFINED
	}

	private static final int MARGIN = 5;
	private static final int MIN_WIDTH = 100;
	private static final int MIN_HEIGHT = 100;
	private int startingX;
	private int startingY;
	private ResizeHandle resizeHandle = ResizeHandle.UNDEFINED;

	private int deltaXFromHandle;
	private int deltaYFromHandle;
	private int handleHeight;
	private int handleWidth;

	@Override
	protected void beginDragging(MouseDownEvent event) {
		resizeHandle = computeResizeHandle(event.getNativeEvent());
		if (resizeHandle == ResizeHandle.UNDEFINED) {
			super.beginDragging(event);
			return;
		}
		DOM.setCapture(getElement());
		startingX = event.getClientX();
		startingY = event.getClientY();
	}

	@Override
	protected void continueDragging(MouseMoveEvent event) {
		if (resizeHandle == ResizeHandle.UNDEFINED) {
			super.continueDragging(event);
			return;
		}
		int deltaX = event.getClientX() - startingX;
		int deltaY = event.getClientY() - startingY;
		startingX = event.getClientX();
		startingY = event.getClientY();
		resizeWindow(deltaX, deltaY);
	}

	@Override
	protected void endDragging(MouseUpEvent event) {
		if (resizeHandle == ResizeHandle.UNDEFINED) {
			super.endDragging(event);
			return;
		}
		DOM.releaseCapture(getElement());
		resizeHandle = ResizeHandle.UNDEFINED;
		OnWindowResized();
	}

	protected void OnWindowResized() {
		Widget widget = getWidget();
		int width = widget.getOffsetWidth();
		int height = widget.getOffsetHeight();
	}

	private void resizeWindow(int deltaX, int deltaY) {
		Widget widget = getWidget();
		int width = widget.getOffsetWidth() + deltaX;
		width = width < MIN_WIDTH ? MIN_WIDTH : width;
		widget.setWidth(width + "px");
		int height = widget.getOffsetHeight() + deltaY;
		height = height < MIN_HEIGHT ? MIN_HEIGHT : height;
		widget.setHeight(height + "px");
	}

	private ResizeHandle computeResizeHandle(NativeEvent event) {
		computeHandleSize();
		if (isBottomRight(event)) {
			return ResizeHandle.BOTTOMRIGHT;
		}
		return ResizeHandle.UNDEFINED;
	}

	private void computeHandleSize() {
		Element growElement = this.getCellElement(2, 2).getParentElement();
		handleWidth = growElement.getClientWidth();
		handleHeight = growElement.getClientHeight();
	}

	private void computeDeltasDromHandle(NativeEvent event, int row, int column) {
		Element growElement = this.getCellElement(row, column).getParentElement();
		deltaXFromHandle = event.getClientX() - growElement.getAbsoluteLeft() + growElement.getScrollLeft() + growElement.getOwnerDocument().getScrollLeft();
		deltaYFromHandle = event.getClientY() - growElement.getAbsoluteTop() + growElement.getScrollTop() + growElement.getOwnerDocument().getScrollTop();
	}

	private boolean isTop(NativeEvent event) {
		computeDeltasDromHandle(event, 0, 1);
		return (deltaYFromHandle >= 0 && deltaYFromHandle < handleHeight);
	}

	private boolean isTopLeft(NativeEvent event) {
		computeDeltasDromHandle(event, 0, 0);
		return ((deltaXFromHandle >= 0 && deltaXFromHandle < handleWidth && deltaYFromHandle >= 0 && deltaYFromHandle < handleHeight + MARGIN)
				|| (deltaYFromHandle >= 0 && deltaYFromHandle < handleHeight && deltaXFromHandle >= 0 && deltaXFromHandle < handleWidth + MARGIN));
	}

	private boolean isTopRight(NativeEvent event) {
		computeDeltasDromHandle(event, 0, 2);
		return ((deltaXFromHandle >= 0 && deltaXFromHandle < handleWidth && deltaYFromHandle >= 0 && deltaYFromHandle < handleHeight + MARGIN)
				|| (deltaYFromHandle >= 0 && deltaYFromHandle < handleHeight && deltaXFromHandle >= -MARGIN && deltaXFromHandle < handleWidth));
	}

	private boolean isBottomLeft(NativeEvent event) {
		computeDeltasDromHandle(event, 2, 0);
		return ((deltaXFromHandle >= 0 && deltaXFromHandle < handleWidth && deltaYFromHandle >= -MARGIN && deltaYFromHandle < handleHeight)
				|| (deltaYFromHandle >= 0 && deltaYFromHandle < handleHeight && deltaXFromHandle >= 0 && deltaXFromHandle < handleWidth + MARGIN));
	}

	private boolean isBottomRight(NativeEvent event) {
		computeDeltasDromHandle(event, 2, 2);
		return ((deltaXFromHandle >= 0 && deltaXFromHandle < handleWidth && deltaYFromHandle >= -MARGIN && deltaYFromHandle < handleHeight)
				|| (deltaYFromHandle >= 0 && deltaYFromHandle < handleHeight && deltaXFromHandle >= -MARGIN && deltaXFromHandle < handleWidth));
	}

	private boolean isBottom(NativeEvent event) {
		computeDeltasDromHandle(event, 2, 1);
		return (deltaYFromHandle >= 0 && deltaYFromHandle < handleHeight);
	}

	private boolean isLeft(NativeEvent event) {
		computeDeltasDromHandle(event, 1, 0);
		return (deltaXFromHandle >= 0 && deltaXFromHandle < handleWidth);
	}

	private boolean isRight(NativeEvent event) {
		computeDeltasDromHandle(event, 1, 2);
		return (deltaXFromHandle >= 0 && deltaXFromHandle < handleWidth);
	}

	@Override
	public void onBrowserEvent(Event event) {
		switch (event.getTypeInt()) {
		case Event.ONMOUSEDOWN:
		case Event.ONMOUSEUP:
		case Event.ONMOUSEMOVE:
		case Event.ONMOUSEOVER:
		case Event.ONMOUSEOUT:
			ResizeHandle possible = computeResizeHandle(event);
			if (resizeHandle != ResizeHandle.UNDEFINED || possible != ResizeHandle.UNDEFINED) {
				switch (DOM.eventGetType(event)) {
				case Event.ONMOUSEOVER:
				case Event.ONMOUSEOUT:
					Element related = event.getRelatedEventTarget().cast();
					if (related != null && getElement().isOrHasChild(related)) {
						return;
					}
					break;
				}
				DomEvent.fireNativeEvent(event, this, this.getElement());
				getElement().getStyle().setCursor(possible != ResizeHandle.UNDEFINED ? Cursor.POINTER : Cursor.AUTO);
				return;
			}
			getElement().getStyle().setCursor(possible != ResizeHandle.UNDEFINED ? Cursor.POINTER : Cursor.AUTO);
		}
		super.onBrowserEvent(event);
	}

	@Override
	protected void onPreviewNativeEvent(NativePreviewEvent event) {
		NativeEvent nativeEvent = event.getNativeEvent();

		if (!event.isCanceled() && (event.getTypeInt() == Event.ONMOUSEDOWN) && computeResizeHandle(event.getNativeEvent()) != ResizeHandle.UNDEFINED) {
			nativeEvent.preventDefault();
		}
		super.onPreviewNativeEvent(event);
	}
	protected int getDialogWidth() {
		return(getWidget().getOffsetWidth());
	}
	protected int getDialogHeight() {
		return(getWidget().getOffsetHeight());
	}
}
