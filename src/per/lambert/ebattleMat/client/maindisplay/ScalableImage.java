package per.lambert.ebattleMat.client.maindisplay;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.services.ServiceManagement;

/**
 * @author LLambert Class to manage a scaled image with overlays.
 */
public class ScalableImage extends AbsolutePanel
		implements MouseWheelHandler, MouseDownHandler, MouseMoveHandler, MouseUpHandler {

	/**
	 * Default zoom constant.
	 */
	private static final double DEFAULT_ZOOM = 1.1;
	private static final int OVERLAYS_Z = 300;
	// private static final int ROOM_OBJECTS_Z = 200;

	private boolean showGrid = false;

	/**
	 * Adjusted grid spacing so the grid lines matches exactly to one side.
	 */
	private double gridSpacing = 30;
	/**
	 * Main canvas that is drawn.
	 */
	private Canvas canvas = Canvas.createIfSupported();
	/**
	 * Canvas context for drawing.
	 */
	private Context2d context = canvas.getContext2d();
	/**
	 * background canvas for temporary drawing.
	 */
	private Canvas backCanvas = Canvas.createIfSupported();
	/**
	 * background context for drawing.
	 */
	private Context2d backContext = backCanvas.getContext2d();
	/**
	 * line style yellow.
	 */
	private final CssColor colorYellow = CssColor.make("yellow");
	/**
	 * Width of actual image.
	 */
	private int imageWidth = 0;
	/**
	 * Height of actual image.
	 */
	private int imageHeight = 0;
	/**
	 * Width of parent window.
	 */
	private int parentWidth = 0;
	/**
	 * Height of parent window.
	 */
	private int parentHeight = 0;
	/**
	 * Width of image after scaling to parent.
	 */
	private int adjustedImageWidth = 0;
	/**
	 * Height of image after scaling to parent.
	 */
	private int adjustedImageHeight = 0;
	/**
	 * image to display.
	 */
	private Image image;
	/**
	 * image context for drawing.
	 */
	private ImageElement imageElement;
	/**
	 * current zoom factor for image.
	 */
	private double totalZoom = 1;
	/**
	 * Maximum zoom factor. We do not allow zooming out farther than the initial
	 * calculated zoom that fills the parent.
	 */
	private double maxZoom = 1;
	/**
	 * Offset of image in the horizontal direction.
	 */
	private double offsetX = 0;
	/**
	 * Offset of image in the vertical direction.
	 */
	private double offsetY = 0;

	private double gridOffsetX = 0;
	private double gridOffsetY = 0;
	/**
	 * Used by pan control.
	 */
	private boolean mouseDown = false;
	/**
	 * X position of mouse down.
	 */
	private double mouseDownXPos = 0;
	/**
	 * Y position of mouse down.
	 */
	private double mouseDownYPos = 0;
	/**
	 * Number of horizontal lines needed in the grid.
	 */
	private int horizontalLines = 0;
	/**
	 * Number of vertical lines needed in the grid.
	 */
	private int verticalLines = 0;

	/**
	 * Widget for scaling an image. This supports zoom and pan
	 */
	public ScalableImage() {

		canvas.addMouseWheelHandler(this);
		canvas.addMouseMoveHandler(this);
		canvas.addMouseDownHandler(this);
		canvas.addMouseUpHandler(this);
		super.add(canvas, 0, 0);

		showGrid = false;
		setupDragAndDrop();
		setupEventHandling();
	}

	private void setupDragAndDrop() {
		this.addDomHandler(new DragOverHandler() {

			@Override
			public void onDragOver(DragOverEvent event) {
			}
		}, DragOverEvent.getType());
		this.addDomHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				event.preventDefault();
				dropDeviceData(event);
			}
		}, DropEvent.getType());
		this.addDomHandler(new DragLeaveHandler() {

			@Override
			public void onDragLeave(DragLeaveEvent event) {
			}
		}, DragLeaveEvent.getType());
	}

	private void setupEventHandling() {
	}

	@Override
	public void add(Widget w, int left, int top) {
		super.add(w, left, top);
		w.getElement().getStyle().setZIndex(OVERLAYS_Z);
	}

	/**
	 * Set the image for this control.
	 * 
	 * @param imageToDisplay
	 *            image to display.
	 * @param widthOfParent
	 *            current width of parent window.
	 * @param heightOfParent
	 *            current height of parent window.
	 */
	public final void setImage(final Image imageToDisplay, final int widthOfParent, final int heightOfParent) {
		totalZoom = 1;
		maxZoom = 1;
		offsetX = 0;
		offsetY = 0;
		this.image = imageToDisplay;
		this.imageElement = (ImageElement) imageToDisplay.getElement().cast();

		parentWidthChanged(widthOfParent, heightOfParent);
	}

	/**
	 * Parent window size has changed.
	 * 
	 * @param widthOfParent
	 *            new width of window.
	 * @param heightOfParent
	 *            new height of window.
	 */
	public final void parentWidthChanged(final int widthOfParent, final int heightOfParent) {
		parentWidth = widthOfParent;
		parentHeight = heightOfParent;
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();

		canvas.setWidth(parentWidth + "px");
		canvas.setCoordinateSpaceWidth(parentWidth);
		canvas.setHeight(parentHeight + "px");
		canvas.setCoordinateSpaceHeight(parentHeight);

		backCanvas.setWidth(parentWidth + "px");
		backCanvas.setCoordinateSpaceWidth(parentWidth);
		backCanvas.setHeight(parentHeight + "px");
		backCanvas.setCoordinateSpaceHeight(parentHeight);

		calculateStartingZoom();
		backContext.setTransform(totalZoom, 0, 0, totalZoom, 0, 0);
		mainDraw();
	}

	/**
	 * Handle mouse wheel.
	 * 
	 * @param event
	 *            event data.
	 */
	public final void onMouseWheel(final MouseWheelEvent event) {
		int move = event.getDeltaY();
		double xPos = (event.getRelativeX(canvas.getElement()));
		double yPos = (event.getRelativeY(canvas.getElement()));

		double zoom = DEFAULT_ZOOM;
		if (move >= 0) {
			zoom = 1 / DEFAULT_ZOOM;
		}

		double newX = (xPos - offsetX) / totalZoom;
		double newY = (yPos - offsetY) / totalZoom;
		double xPosition = (-newX * zoom) + newX;
		double yPosition = (-newY * zoom) + newY;

		zoom = zoom * totalZoom;
		if (zoom < maxZoom) {
			zoom = maxZoom;
		} else {
			offsetX += (xPosition * totalZoom);
			offsetY += (yPosition * totalZoom);
		}
		totalZoom = zoom;
		mainDraw();
	}

	/**
	 * Handle mouse down.
	 * 
	 * @param event
	 *            event data.
	 */
	public final void onMouseDown(final MouseDownEvent event) {
		this.mouseDown = true;
		mouseDownXPos = event.getRelativeX(image.getElement());
		mouseDownYPos = event.getRelativeY(image.getElement());
	}

	/**
	 * Handle mouse move.
	 * 
	 * @param event
	 *            event data.
	 */
	public final void onMouseMove(final MouseMoveEvent event) {
		if (mouseDown) {
			double xPos = event.getRelativeX(image.getElement());
			double yPos = event.getRelativeY(image.getElement());
			offsetX += (xPos - mouseDownXPos);
			offsetY += (yPos - mouseDownYPos);
			try {
				mainDraw();
			} catch (Exception ex) {
				mouseDownXPos = xPos;
				mouseDownYPos = yPos;
			}
			mouseDownXPos = xPos;
			mouseDownYPos = yPos;
		}
	}

	/**
	 * Handle mouse up.
	 * 
	 * @param event
	 *            event data.
	 */
	public final void onMouseUp(final MouseUpEvent event) {
		this.mouseDown = false;
	}

	/**
	 * Offset for clearing rectangle.
	 */
	private static final int CLEAR_OFFEST = -10;

	/**
	 * Main method for drawing image.
	 */
	public final void mainDraw() {
		calculateDimensions();
		backContext.clearRect(CLEAR_OFFEST, CLEAR_OFFEST, imageWidth + gridSpacing, imageHeight + gridSpacing);
		backContext.setTransform(totalZoom, 0, 0, totalZoom, offsetX, offsetY);
		backContext.drawImage(imageElement, 0, 0);
		buffer(backContext, context);
	}

	/**
	 * This will draw the scaled image from the back canvas onto the main canvas the
	 * redraw all the superimposed items.
	 * 
	 * @param back
	 *            back canvas context
	 * @param front
	 *            front canvas context
	 */
	public final void buffer(final Context2d back, final Context2d front) {
		front.clearRect(CLEAR_OFFEST, CLEAR_OFFEST, parentWidth + gridSpacing, parentHeight + gridSpacing);
		front.drawImage(back.getCanvas(), 0, 0);
		drawGridLines();
	}

	/**
	 * Draw the grid line on main canvas. THis will be done based on scale and
	 * offset of the background image. This way the lines themselves do not get
	 * scaled and look weird.
	 */
	private void drawGridLines() {
		double scaledWidth = adjustedImageWidth * totalZoom;
		double scaledHeight = adjustedImageHeight * totalZoom;
		if (showGrid) {
			drawVerticalGridLines(scaledWidth, scaledHeight);
			drawHorizontalGridLines(scaledWidth, scaledHeight);
			outlinePicture(scaledWidth, scaledHeight);
		}
	}

	/**
	 * Draw vertical grid lines.
	 * 
	 * @param scaledWidth
	 *            scaled width.
	 * @param scaledHeight
	 *            scaled height.
	 */
	private void drawVerticalGridLines(final double scaledWidth, final double scaledHeight) {
		context.beginPath();
		context.setStrokeStyle(colorYellow);
		double interval = scaledWidth / verticalLines;
		double x = interval + offsetX + (gridOffsetX * totalZoom);
		double y = scaledHeight + offsetY + (gridOffsetY * totalZoom);
		for (int i = 0; i < verticalLines; ++i) {
			context.moveTo(x, offsetY + (gridOffsetY * totalZoom));
			context.lineTo(x, y);
			x += interval;
		}
		context.stroke();
	}

	/**
	 * Draw horizontal grid lines.
	 * 
	 * @param scaledWidth
	 *            scaled width
	 * @param scaledHeight
	 *            scaled height
	 */
	private void drawHorizontalGridLines(final double scaledWidth, final double scaledHeight) {
		context.beginPath();
		context.setStrokeStyle(colorYellow);
		double interval = scaledHeight / horizontalLines;
		double y = interval + offsetY + (gridOffsetY * totalZoom);
		double x = scaledWidth + offsetX + (gridOffsetX * totalZoom);
		for (int i = 0; i < horizontalLines; ++i) {
			context.moveTo(offsetX + (gridOffsetX * totalZoom), y);
			context.lineTo(x, y);
			y += interval;
		}
		context.stroke();
	}

	/**
	 * Outline the image.
	 * 
	 * @param scaledWidth
	 *            scaled width
	 * @param scaledHeight
	 *            scaled height
	 */
	private void outlinePicture(final double scaledWidth, final double scaledHeight) {
		context.beginPath();
		context.setStrokeStyle(colorYellow);
		context.rect(offsetX + (gridOffsetX * totalZoom), offsetY + (gridOffsetY * totalZoom), scaledWidth,
				scaledHeight);
		context.stroke();
	}

	/**
	 * Calculate the starting zoom factor so that one side of the image exactly
	 * fills the parent.
	 */
	private void calculateStartingZoom() {
		totalZoom = 1;
		maxZoom = totalZoom;
	}

	/**
	 * Calculate numbers defendant on parent to image size and grid spacing.
	 */
	private void calculateDimensions() {
		adjustedImageWidth = imageWidth;
		adjustedImageHeight = imageHeight;
		gridOffsetX = ServiceManagement.getDungeonManagment().getCurrentLevelData().getGridOffsetX();
		gridOffsetY = ServiceManagement.getDungeonManagment().getCurrentLevelData().getGridOffsetY();
		gridSpacing = ServiceManagement.getDungeonManagment().getCurrentLevelData().getGridSize();
		showGrid = ServiceManagement.getDungeonManagment().getSelectedDungeon().getShowGrid();
		verticalLines = (int) (imageWidth / gridSpacing) + 1;
		horizontalLines = (int) (imageHeight / gridSpacing) + 1;
	}

	private void dropDeviceData(DropEvent event) {
		int xCoord = event.getNativeEvent().getClientX();
		int yCoord = event.getNativeEvent().getClientY();
		xCoord = xCoord - getAbsoluteLeft();
		yCoord = yCoord - getAbsoluteTop();
	}
}
