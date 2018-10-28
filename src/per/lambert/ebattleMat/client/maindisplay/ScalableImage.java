package per.lambert.ebattleMat.client.maindisplay;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
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
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.ElectronicBattleMat;
import per.lambert.ebattleMat.client.controls.scalablePog.ScalablePog;
import per.lambert.ebattleMat.client.services.ServiceManagement;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

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
	private double gridSpacing = 50;
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
	private final CssColor gridColor = CssColor.make("grey");
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
	private double maxZoom = .5;
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

	int dragColumn = -1;
	int dragRow = -1;

	ShellLayout parentPanel;
	private Image image = new Image();
	int pictureCount = 1;

	private List<ScalablePog> pogs = new ArrayList<ScalablePog>();

	public ShellLayout getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(ShellLayout parentPanel) {
		this.parentPanel = parentPanel;
	}

	/**
	 * Widget for scaling an image. This supports zoom and pan
	 */
	public ScalableImage() {

		canvas.addMouseWheelHandler(this);
		canvas.addMouseMoveHandler(this);
		canvas.addMouseDownHandler(this);
		canvas.addMouseUpHandler(this);
		super.add(canvas, 0, 0);
		LayoutPanel hidePanel = new LayoutPanel();
		hidePanel.setVisible(false);
		hidePanel.add(image);
		super.add(hidePanel, -1, -1);

		showGrid = false;
		setupDragAndDrop();
		setupEventHandling();
		addFakePod();
	}

	private void addFakePod() {
		PogData pogData = (PogData) JavaScriptObject.createObject().cast();
		pogData.setPogName("Reeve the Mighty");
		pogData.setPogColumn(1);
		pogData.setPogRow(1);
		pogData.setPogSize(1);
		pogData.setPogImageUrl("dungeonData/resources/pcPogs/dwarf.jpeg?1");
		addPogToCanvas(pogData);

		pogData = (PogData) JavaScriptObject.createObject().cast();
		pogData.setPogName("Red Dragon");
		pogData.setPogColumn(3);
		pogData.setPogRow(4);
		pogData.setPogImageUrl(
				"https://vignette.wikia.nocookie.net/forgottenrealms/images/6/6d/Monster_Manual_5e_-_Dragon%2C_Copper_-_p110.jpg");
		pogData.setPogSize(2);
		addPogToCanvas(pogData);

	}

	public void addPogToCanvas(PogData pogData) {
		getRibbonBarData();
		ScalablePog scalablePog = new ScalablePog(pogData);
		scalablePog.setPogWidth((int) gridSpacing - 4);
		pogs.add(scalablePog);
		add(scalablePog, (int) columnToPixel(scalablePog.getPogColumn()), (int) rowToPixel(scalablePog.getPogRow()));
	}

	private void setupDragAndDrop() {
		this.addDomHandler(new DragOverHandler() {

			@Override
			public void onDragOver(DragOverEvent event) {
				highlightGridSquare(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY(), true);
			}
		}, DragOverEvent.getType());

		this.addDomHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				event.preventDefault();
				dropPog(event);
			}
		}, DropEvent.getType());

		this.addDomHandler(new DragLeaveHandler() {

			@Override
			public void onDragLeave(DragLeaveEvent event) {
				highlightGridSquare(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY(), false);
			}
		}, DragLeaveEvent.getType());
	}

	private void setupEventHandling() {
		image.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				setImage();
			}
		});
	}

	@Override
	public void add(Widget w, int left, int top) {
		super.add(w, left, top);
		w.getElement().getStyle().setZIndex(OVERLAYS_Z);
	}

	public void loadImage() {
		DungeonLevel dungeonLevel = ServiceManagement.getDungeonManagment().getCurrentLevelData();
		String dungeonNameForUrl = ServiceManagement.getDungeonManagment().getDungeonNameForUrl();
		String dungeonPicture = dungeonLevel.getLevelDrawing();
		String imageUrl = ElectronicBattleMat.DUNGEON_DATA_LOCATION + dungeonNameForUrl + "/" + dungeonPicture + "?"
				+ pictureCount++;
		image.setUrl(imageUrl);
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
	public final void setImage() {
		totalZoom = 1;
		maxZoom = .5;
		offsetX = 0;
		offsetY = 0;
		this.imageElement = (ImageElement) image.getElement().cast();

		parentWidthChanged(getParent().getOffsetWidth(), getParent().getOffsetHeight());
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
		getRibbonBarData();
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
		adjustPogs();
	}

	private void adjustPogs() {
		for (ScalablePog pog : pogs) {
			pog.getElement().getStyle().setZIndex(OVERLAYS_Z);
			this.setWidgetPosition(pog, (int) (columnToPixel(pog.getPogColumn())), (int) (rowToPixel(pog.getPogRow())));
			pog.setPogWidth((int) adjustedGridSize());
		}
	}

	/**
	 * Draw the grid line on main canvas. THis will be done based on scale and
	 * offset of the background image. This way the lines themselves do not get
	 * scaled and look weird.
	 */
	private void drawGridLines() {
		if (showGrid) {
			drawVerticalGridLines();
			drawHorizontalGridLines();
			outlinePicture();
		}
	}

	private double columnToPixel(int column) {
		return ((adjustedGridSize() * column) + offsetX + gridOffsetX);
	}

	private double rowToPixel(int row) {
		return ((adjustedGridSize() * row) + offsetY + gridOffsetY);
	}

	/**
	 * Draw vertical grid lines.
	 * 
	 * @param scaledWidth
	 *            scaled width.
	 * @param scaledHeight
	 *            scaled height.
	 */
	private void drawVerticalGridLines() {
		context.beginPath();
		context.setStrokeStyle(gridColor);
		for (int i = 0; i < verticalLines; ++i) {
			double x = columnToPixel(i);
			double y = rowToPixel(horizontalLines);
			context.moveTo(x, gridOffsetY + offsetY);
			context.lineTo(x, y);
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
	private void drawHorizontalGridLines() {
		context.beginPath();
		context.setStrokeStyle(gridColor);
		for (int i = 0; i < horizontalLines; ++i) {
			double y = rowToPixel(i);
			double x = columnToPixel(verticalLines);
			context.moveTo(gridOffsetX + offsetX, y);
			context.lineTo(x, y);
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
	private void outlinePicture() {
		context.beginPath();
		context.setStrokeStyle(gridColor);
		double width = (adjustedGridSize() * (verticalLines));
		double height = (adjustedGridSize() * (horizontalLines));
		context.rect(offsetX + gridOffsetX, offsetY + gridOffsetY, width, height);
		context.stroke();
	}

	/**
	 * Calculate the starting zoom factor so that one side of the image exactly
	 * fills the parent.
	 */
	private void calculateStartingZoom() {
		totalZoom = 1;
	}

	/**
	 * Calculate numbers defendant on parent to image size and grid spacing.
	 */
	private void calculateDimensions() {
		getRibbonBarData();
		showGrid = ServiceManagement.getDungeonManagment().getSelectedDungeon().getShowGrid();
		verticalLines = (int) (imageWidth / gridSpacing) + 1;
		horizontalLines = (int) (imageHeight / gridSpacing) + 1;
	}

	private void getRibbonBarData() {
		gridOffsetX = ServiceManagement.getDungeonManagment().getCurrentLevelData().getGridOffsetX() * totalZoom;
		gridOffsetY = ServiceManagement.getDungeonManagment().getCurrentLevelData().getGridOffsetY() * totalZoom;
		gridSpacing = ServiceManagement.getDungeonManagment().getCurrentLevelData().getGridSize();
	}

	private void dropPog(DropEvent event) {
		int newColumn = dragColumn;
		int newRow = dragRow;
		ScalablePog dragPog = getPogThatWasDragged();
		if (dragPog != null && newColumn >= 0 && newRow >= 0) {
			dragPog.setPogPosition(newColumn, newRow);
			mainDraw();
		}
	}

	private ScalablePog getPogThatWasDragged() {
		PogData pogBeingDragged = ServiceManagement.getDungeonManagment().getPogBeingDragged();
		for (ScalablePog pog : pogs) {
			if (pog.getPogData() == pogBeingDragged) {
				return(pog);
			}
		}
		return null;
	}

	private double adjustedGridSize() {
		return (gridSpacing * totalZoom);
	}

	protected void highlightGridSquare(int clientX, int clientY, boolean needFill) {
		handleDragBox(false);
		if (!needFill) {
			return;
		}
		double xCoord = clientX - getAbsoluteLeft();
		double yCoord = clientY - getAbsoluteTop();
		int selectedColumn = ((int) (((xCoord - offsetX - gridOffsetY)) / adjustedGridSize()));
		int selectedRow = ((int) (((yCoord - offsetY - gridOffsetY)) / adjustedGridSize()));
		if (selectedColumn < 0 || selectedColumn > horizontalLines || selectedRow < 0 || selectedRow > verticalLines) {
			return;
		}
		if (selectedColumn >= verticalLines) {
			selectedColumn = verticalLines - 1;
		}
		if (selectedRow >= horizontalLines) {
			selectedRow = horizontalLines - 1;
		}
		dragColumn = selectedColumn;
		dragRow = selectedRow;
		handleDragBox(true);
	}

	private void handleDragBox(boolean draw) {
		if (dragColumn < 0 || dragRow < 0) {
			return;
		}
		PogData pogBeingDragged = ServiceManagement.getDungeonManagment().getPogBeingDragged();
		double size = adjustedGridSize() * pogBeingDragged.getPogSize();
		if (!draw) {
			context.clearRect(columnToPixel(dragColumn), rowToPixel(dragRow), size, size);
			dragColumn = dragRow = -1;
			mainDraw();
		} else {
			context.setFillStyle("grey");
			context.fillRect(columnToPixel(dragColumn), rowToPixel(dragRow), size, size);
		}
	}

	@SuppressWarnings("unused")
	private void setStatus(String status) {
		if (parentPanel != null) {
			parentPanel.setStatus(status);
		}
	}
}
