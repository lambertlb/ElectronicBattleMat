package per.lambert.ebattleMat.client.controls.DraggableLabel;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;

public class DraggableLabel extends Label {
	private static DraggableLabel dragging = null;

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String zone;

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	private boolean droppable;

	public boolean isDroppable() {
		return droppable;
	}

	public void setDroppable(boolean droppable) {
		this.droppable = droppable;
		if (droppable) {
			initDrop();
			addStyleName("droppable");
		}
	}

	private boolean draggable;

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
		initDrag();
		initMouseOver();
		if (draggable) {
			addStyleName("draggable");
		}
	}

	public String getLabelText() {
		return super.getText();
	}

	public void setLabelText(String labelText) {
		super.setText(labelText);
	}

	private TreeItem ownerItem;

	public TreeItem getOwnerItem() {
		return ownerItem;
	}

	public void setOwnerItem(TreeItem ownerItem) {
		this.ownerItem = ownerItem;
	}

	public DraggableLabel() {
		setDraggable(draggable);
		setDroppable(droppable);
		disableTextSelectInternal(this.getElement(), true);
	}

	public DraggableLabel(String text, int id, boolean draggable, boolean droppable) {
		setId(id);
		setLabelText(text);
		setDraggable(draggable);
		setDroppable(droppable);
	}

	private void initDrag() {
		getElement().setDraggable(Element.DRAGGABLE_TRUE);
		addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				dragging = DraggableLabel.this;

				// Must set for FireFox
				event.setData("text", getLabelText());

				// Copy the label image for the drag icon
				// 10,10 indicates the pointer offset, not the image size.
				event.getDataTransfer().setDragImage(getElement(), 10, 10);
			}
		});
	}

	private void initDrop() {
		addDomHandler(new DragOverHandler() {
			@Override
			public void onDragOver(DragOverEvent event) {
				addStyleName("dropping");
			}
		}, DragOverEvent.getType());

		addDomHandler(new DragLeaveHandler() {
			@Override
			public void onDragLeave(DragLeaveEvent event) {
				removeStyleName("dropping");
			}
		}, DragLeaveEvent.getType());

		addDomHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				event.preventDefault();
				if (dragging != null) {
					dragging = null;
					removeStyleName("dropping");
				}
			}
		}, DropEvent.getType());
	}

	private void initMouseOver() {
		addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				addStyleName("candrag");
			}
		});
		addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				removeStyleName("candrag");
			}
		});
	}

	private native static void disableTextSelectInternal(Element e, boolean disable)/*-{
		if (disable) {
			e.ondrag = function() {
				return false;
			};
			e.onselectstart = function() {
				return false;
			};
			e.style.MozUserSelect = "none"
		} else {
			e.ondrag = null;
			e.onselectstart = null;
			e.style.MozUserSelect = "text"
		}
	}-*/;
}
