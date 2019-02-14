package per.lambert.ebattleMat.client.battleMatDisplay;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.NativeEvent;
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

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.DungeonMasterFlag;
import per.lambert.ebattleMat.client.interfaces.IDungeonManager;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

/**
 * @author LLambert Class to manage a scaled image with overlays.
 */
public class BattleMatCanvas extends AbsolutePanel implements MouseWheelHandler, MouseDownHandler, MouseMoveHandler, MouseUpHandler {

	/**
	 * Offset for clearing rectangle.
	 */
	private static final int CLEAR_OFFEST = -10;
	/**
	 * Default zoom constant.
	 */
	private static final double DEFAULT_ZOOM = 1.1;
	/**
	 * Z order for player.
	 */
	private static final int PLAYERS_Z = 5;
	/**
	 * Z order for Monsters.
	 */
	private static final int MONSTERS_Z = 3;
	/**
	 * Z order for room objects.
	 */
	private static final int ROOMOBJECTS_Z = 1;
	/**
	 * Z order for grey out area.
	 */
	private static final int GREYOUT_Z = 9;
	/**
	 * Z order for fog of war.
	 */
	private static final int FOW_Z = 8;
	/**
	 * Z order for dialogs.
	 */
	public static final int DIALOG_Z = 10;
	/**
	 * Show grid.
	 */
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
	 * background canvas for temporary drawing.
	 */
	private Canvas backCanvas = Canvas.createIfSupported();
	/**
	 * Fog of war canvas.
	 */
	private Canvas fowCanvas = Canvas.createIfSupported();
	/**
	 * line style yellow.
	 */
	private final CssColor gridColor = CssColor.make("grey");
	/**
	 * Fog of war color.
	 */
	private final String fogOfWarColor = "black";
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
	 * Maximum zoom factor. We do not allow zooming out farther than the initial calculated zoom that fills the parent.
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
	/**
	 * grid origin offset X.
	 */
	private double gridOffsetX = 0;
	/**
	 * grid origin offset Y.
	 */
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
	 * column current drag operation is in.
	 */
	private int dragColumn = -1;
	/**
	 * row current drag operation is in.
	 */
	private int dragRow = -1;
	/**
	 * Image for dungeon level.
	 */
	private Image image = new Image();
	/**
	 * panel to managing the grey out area.
	 */
	private LayoutPanel greyOutPanel;
	/**
	 * Hidden panel to handle loading the image.
	 */
	private LayoutPanel hidePanel;
	/**
	 * List of pog canvases currently on level.
	 */
	private List<PogCanvas> pogs = new ArrayList<PogCanvas>();
	/**
	 * Toggle fog of war.
	 */
	private boolean toggleFOW;
	/**
	 * clear fog or war.
	 */
	private boolean clearFOW;
	/**
	 * Selected column.
	 */
	private int selectedColumn;
	/**
	 * Selected row.
	 */
	private int selectedRow;

	/**
	 * Widget for scaling an image. This supports zoom and pan
	 */
	public BattleMatCanvas() {

		canvas.addMouseWheelHandler(this);
		canvas.addMouseMoveHandler(this);
		canvas.addMouseDownHandler(this);
		canvas.addMouseUpHandler(this);
		hidePanel = new LayoutPanel();
		greyOutPanel = new LayoutPanel();
		greyOutPanel.getElement().getStyle().setZIndex(GREYOUT_Z);
		hidePanel.add(image);
		hidePanel.setVisible(false);
		fowCanvas.getElement().getStyle().setZIndex(FOW_Z);
		fowCanvas.setStyleName("noEvents");
		intializeView();
		showGrid = false;
		setupDragAndDrop();
		setupEventHandling();
	}

	/**
	 * Initialize view.
	 */
	private void intializeView() {
		pogs.clear();
		super.clear();
		super.add(canvas, 0, 0);
		super.add(fowCanvas, 0, 0);
		super.add(hidePanel, -1, -1);
		super.add(greyOutPanel, 100, 100);
	}

	/**
	 * Setup drag and drop.
	 */
	private void setupDragAndDrop() {
		this.addDomHandler(new DragOverHandler() {

			@Override
			public void onDragOver(final DragOverEvent event) {
				highlightGridSquare(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
			}
		}, DragOverEvent.getType());

		this.addDomHandler(new DropHandler() {
			@Override
			public void onDrop(final DropEvent event) {
				event.preventDefault();
				dropPog(event);
			}
		}, DropEvent.getType());
		this.addDomHandler(new DragLeaveHandler() {

			@Override
			public void onDragLeave(final DragLeaveEvent event) {
			}
		}, DragLeaveEvent.getType());
	}

	/**
	 * Setup even handlers.
	 */
	private void setupEventHandling() {
		image.addLoadHandler(new LoadHandler() {
			public void onLoad(final LoadEvent event) {
				setImage();
				addPogs();
			}
		});
		IEventManager eventManager = ServiceManager.getEventManager();
		eventManager.addHandler(ReasonForActionEvent.getReasonForActionEventType(), new ReasonForActionEventHandler() {
			public void onReasonForAction(final ReasonForActionEvent event) {
				if (event.getReasonForAction() == ReasonForAction.MouseDownEventBubble) {
					onMouseDown((MouseDownEvent) event.getData());
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.MouseUpEventBubble) {
					onMouseUp((MouseUpEvent) event.getData());
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.MouseMoveEventBubble) {
					onMouseMove((MouseMoveEvent) event.getData());
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.DungeonSelectedLevelChanged) {
					dungeonDataChanged();
					return;
				}
				if (event.getReasonForAction() == ReasonForAction.PogWasSelected) {
					newSelectedPog();
					return;
				}
			}
		});
	}

	/**
	 * Set the image for this control.
	 */
	private void setImage() {
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
	 * @param widthOfParent new width of window.
	 * @param heightOfParent new height of window.
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

		fowCanvas.setWidth(parentWidth + "px");
		fowCanvas.setCoordinateSpaceWidth(parentWidth);
		fowCanvas.setHeight(parentHeight + "px");
		fowCanvas.setCoordinateSpaceHeight(parentHeight);

		backCanvas.setWidth(parentWidth + "px");
		backCanvas.setCoordinateSpaceWidth(parentWidth);
		backCanvas.setHeight(parentHeight + "px");
		backCanvas.setCoordinateSpaceHeight(parentHeight);

		calculateStartingZoom();
		backCanvas.getContext2d().setTransform(totalZoom, 0, 0, totalZoom, 0, 0);
		drawEverything();
	}

	/**
	 * Handle mouse wheel.
	 * 
	 * @param event event data.
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
		getGridData();
		drawEverything();
	}

	/**
	 * Handle mouse down.
	 * 
	 * @param event event data.
	 */
	public final void onMouseDown(final MouseDownEvent event) {
		mouseDownXPos = event.getRelativeX(image.getElement());
		mouseDownYPos = event.getRelativeY(image.getElement());
		checkForFOWHandling(event.getNativeEvent());
		this.mouseDown = !toggleFOW;
	}

	/**
	 * Check if we need to handle fog of war.
	 * 
	 * @param event event data.
	 */
	private void checkForFOWHandling(final NativeEvent event) {
		toggleFOW = ServiceManager.getDungeonManager().getFowToggle();
		computeSelectedColumnAndRow(event.getClientX(), event.getClientY());
		clearFOW = ServiceManager.getDungeonManager().isFowSet(selectedColumn, selectedRow);
		if (toggleFOW && ServiceManager.getDungeonManager().isDungeonMaster()) {
			handleProperFOWAtSelectedPosition();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public final void onMouseMove(final MouseMoveEvent event) {
		if (mouseDown) {
			handleMouseMove(event);
		} else if (toggleFOW && ServiceManager.getDungeonManager().isDungeonMaster()) {
			handleFowMouseMove(event);
		}
	}

	/**
	 * Handle mouse move.
	 * 
	 * @param event event data
	 */
	private void handleFowMouseMove(final MouseMoveEvent event) {
		computeSelectedColumnAndRow(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
		handleProperFOWAtSelectedPosition();
	}

	/**
	 * Handle fog of war selected position.
	 */
	private void handleProperFOWAtSelectedPosition() {
		boolean currentFOW = ServiceManager.getDungeonManager().isFowSet(selectedColumn, selectedRow);
		if (currentFOW == !clearFOW) {
			return;
		}
		ServiceManager.getDungeonManager().setFow(selectedColumn, selectedRow, !currentFOW);
		drawFOW(!currentFOW, adjustedGridSize() + 2, selectedColumn, selectedRow);
	}

	/**
	 * Handle mouse move.
	 * 
	 * @param event event data
	 */
	private void handleMouseMove(final MouseMoveEvent event) {
		double xPos = event.getRelativeX(image.getElement());
		double yPos = event.getRelativeY(image.getElement());
		offsetX += (xPos - mouseDownXPos);
		offsetY += (yPos - mouseDownYPos);
		try {
			drawEverything();
		} catch (Exception ex) {
			mouseDownXPos = xPos;
			mouseDownYPos = yPos;
		}
		mouseDownXPos = xPos;
		mouseDownYPos = yPos;
	}

	/**
	 * Handle mouse up.
	 * 
	 * @param event event data.
	 */
	public final void onMouseUp(final MouseUpEvent event) {
		if (toggleFOW) {
			ServiceManager.getDungeonManager().saveFow();
		}
		this.mouseDown = false;
		toggleFOW = false;
		removeHighlightGridSquare();
	}

	/**
	 * Main method for drawing image.
	 */
	public final void drawEverything() {
		calculateDimensions();
		backCanvas.getContext2d().clearRect(CLEAR_OFFEST, CLEAR_OFFEST, imageWidth + gridSpacing, imageHeight + gridSpacing);
		backCanvas.getContext2d().setTransform(totalZoom, 0, 0, totalZoom, offsetX, offsetY);
		backCanvas.getContext2d().drawImage(imageElement, 0, 0);
		handleAllDrawing();
	}

	/**
	 * Calculate numbers dependent on parent to image size and grid spacing.
	 */
	private void calculateDimensions() {
		getGridData();
		showGrid = ServiceManager.getDungeonManager().getSelectedDungeon().getShowGrid();
		verticalLines = (int) (imageWidth / gridSpacing) + 1;
		horizontalLines = (int) (imageHeight / gridSpacing) + 1;
		ServiceManager.getDungeonManager().setSessionLevelSize(verticalLines, horizontalLines);
	}

	/**
	 * Get grid data.
	 */
	private void getGridData() {
		gridOffsetX = ServiceManager.getDungeonManager().getCurrentLevelData().getGridOffsetX() * totalZoom;
		gridOffsetY = ServiceManager.getDungeonManager().getCurrentLevelData().getGridOffsetY() * totalZoom;
		gridSpacing = ServiceManager.getDungeonManager().getCurrentLevelData().getGridSize();
	}

	/**
	 * Handle drawing everything.
	 */
	public final void handleAllDrawing() {
		canvas.getContext2d().clearRect(CLEAR_OFFEST, CLEAR_OFFEST, parentWidth + gridSpacing, parentHeight + gridSpacing);
		canvas.getContext2d().drawImage(backCanvas.getCanvasElement(), 0, 0);
		fowCanvas.getContext2d().clearRect(CLEAR_OFFEST, CLEAR_OFFEST, parentWidth + gridSpacing, parentHeight + gridSpacing);
		drawGridLines();
		adjustPogs();
		drawFogOfWar();
	}

	/**
	 * Adjust pog positions. Pog data has the column and row and the widget need to be moved to the proper pixel.
	 */
	private void adjustPogs() {
		for (PogCanvas pog : pogs) {
			int x = (int) (columnToPixel(pog.getPogColumn()));
			int y = (int) (rowToPixel(pog.getPogRow()));
			if (pog.getPogData().isFlagSet(DungeonMasterFlag.SHIFT_RIGHT)) {
				x += (adjustedGridSize() / 2);
			} else if (pog.getPogData().isFlagSet(DungeonMasterFlag.SHIFT_TOP)) {
				y -= (adjustedGridSize() / 2);
			}
			this.setWidgetPosition(pog, x, y);
			pog.setPogWidth((int) adjustedGridSize());
		}
	}

	/**
	 * Draw the grid line on main canvas. THis will be done based on scale and offset of the background image. This way the lines themselves do not get scaled and look weird.
	 */
	private void drawGridLines() {
		if (showGrid) {
			drawVerticalGridLines();
			drawHorizontalGridLines();
			outlinePicture();
		}
	}

	/**
	 * Convert column index to pixel position.
	 * 
	 * @param column to convert
	 * @return pixel position
	 */
	private double columnToPixel(final int column) {
		return ((adjustedGridSize() * column) + offsetX + gridOffsetX);
	}

	/**
	 * Convert row index to pixel position.
	 * 
	 * @param row to convert
	 * @return pixel position
	 */
	private double rowToPixel(final int row) {
		return ((adjustedGridSize() * row) + offsetY + gridOffsetY);
	}

	/**
	 * Draw vertical grid lines.
	 */
	private void drawVerticalGridLines() {
		canvas.getContext2d().beginPath();
		canvas.getContext2d().setStrokeStyle(gridColor);
		for (int i = 0; i < verticalLines; ++i) {
			double x = columnToPixel(i);
			double y = rowToPixel(horizontalLines);
			canvas.getContext2d().moveTo(x, gridOffsetY + offsetY);
			canvas.getContext2d().lineTo(x, y);
		}
		canvas.getContext2d().stroke();
	}

	/**
	 * Draw horizontal grid lines.
	 */
	private void drawHorizontalGridLines() {
		canvas.getContext2d().beginPath();
		canvas.getContext2d().setStrokeStyle(gridColor);
		for (int i = 0; i < horizontalLines; ++i) {
			double y = rowToPixel(i);
			double x = columnToPixel(verticalLines);
			canvas.getContext2d().moveTo(gridOffsetX + offsetX, y);
			canvas.getContext2d().lineTo(x, y);
		}
		canvas.getContext2d().stroke();
	}

	/**
	 * Draw fog of war. If this is DM let he see through it.
	 */
	private void drawFogOfWar() {
		if (ServiceManager.getDungeonManager().isEditMode()) {
			return;
		}
		IDungeonManager dungeonManager = ServiceManager.getDungeonManager();
		double size = adjustedGridSize();
		if (ServiceManager.getDungeonManager().isDungeonMaster()) {
			fowCanvas.getElement().getStyle().setOpacity(0.5);
		} else {
			fowCanvas.getElement().getStyle().setOpacity(1.0);
		}
		for (int i = 0; i < verticalLines; ++i) {
			for (int j = 0; j < horizontalLines; ++j) {
				drawFOW(dungeonManager.isFowSet(i, j), size, i, j);
			}
		}
	}

	/**
	 * Draw fog of war for this cell.
	 * 
	 * @param isSet true if need to be draw.
	 * @param size width and height of cell
	 * @param column of cell
	 * @param row of cell
	 */
	private void drawFOW(final boolean isSet, final double size, final int column, final int row) {
		int x = (int) columnToPixel(column);
		int y = (int) rowToPixel(row);
		if (isSet) {
			fowCanvas.getContext2d().setFillStyle(fogOfWarColor);
			fowCanvas.getContext2d().fillRect(x, y, size, size);
		} else {
			fowCanvas.getContext2d().clearRect(x, y, size, size);
		}
	}

	/**
	 * Outline the image.
	 */
	private void outlinePicture() {
		canvas.getContext2d().beginPath();
		canvas.getContext2d().setStrokeStyle(gridColor);
		double width = (adjustedGridSize() * (verticalLines));
		double height = (adjustedGridSize() * (horizontalLines));
		canvas.getContext2d().rect(offsetX + gridOffsetX, offsetY + gridOffsetY, width, height);
		canvas.getContext2d().stroke();
	}

	/**
	 * Calculate the starting zoom factor so that one side of the image exactly fills the parent.
	 */
	private void calculateStartingZoom() {
		totalZoom = 1;
	}

	/**
	 * Drop a pog into the cell.
	 * 
	 * @param event event data
	 */
	private void dropPog(final DropEvent event) {
		PogData pogBeingDragged = ServiceManager.getDungeonManager().getPogBeingDragged();
		boolean isDM = ServiceManager.getDungeonManager().isDungeonMaster();
		boolean forSetOnGridElement = ServiceManager.getDungeonManager().isFowSet(dragColumn, dragRow);
		boolean isPlayer = pogBeingDragged.isThisAPlayer();
		if (!isDM) {
			if (forSetOnGridElement || !isPlayer) {
				removeHighlightGridSquare();
				return;
			}
		}
		if (dragColumn < 0 || dragRow < 0) {
			return;
		}
		PogCanvas dragPog = updateOrCreatePogCanvasForTHisCell();
		if (dragPog != null) {
			removeHighlightGridSquare();
			drawEverything();
		}
	}

	/**
	 * Update the pog canvas for this cell. Create one if none exists.
	 * 
	 * @return pog canvas in the cell
	 */
	private PogCanvas updateOrCreatePogCanvasForTHisCell() {
		PogCanvas existingPog = findCanvasForDraggedPog();
		if (existingPog == null) {
			existingPog = addClonePogToCanvas(ServiceManager.getDungeonManager().getPogBeingDragged());
		} else { // ensure it is on top
			remove(existingPog);
			add(existingPog);
		}
		updatePogData(existingPog);
		ServiceManager.getDungeonManager().addOrUpdatePog(existingPog.getPogData());
		return (existingPog);
	}

	/**
	 * Find Pog canvas for pog being dragged.
	 * 
	 * @return Pog canvas or null.
	 */
	private PogCanvas findCanvasForDraggedPog() {
		PogData pogBeingDragged = ServiceManager.getDungeonManager().getPogBeingDragged();
		for (PogCanvas pog : pogs) {
			if (pog.getPogData() == pogBeingDragged) {
				return (pog);
			}
		}
		return (null);
	}

	/**
	 * Update pog position data.
	 * 
	 * @param pog to update
	 */
	private void updatePogData(final PogCanvas pog) {
		pog.setPogPosition(dragColumn, dragRow);
		pog.getPogData().setDungeonLevel(ServiceManager.getDungeonManager().getCurrentLevel());
	}

	/**
	 * get proper Z for pog based onb type.
	 * 
	 * @param pogData pog data
	 * @return Proper Z
	 */
	private int getPogZ(final PogData pogData) {
		if (pogData.isThisAMonster()) {
			return (MONSTERS_Z);
		}
		if (pogData.isThisAPlayer()) {
			return (PLAYERS_Z);
		}
		return (ROOMOBJECTS_Z);
	}

	/**
	 * Add a pog to the canvas. if the pog is not a player then clone the data from the template. This is because there can only be one player instance but there can be many of the other types.
	 * 
	 * @param pogData pog data
	 * @return pog canvas
	 */
	private PogCanvas addClonePogToCanvas(final PogData pogData) {
		getGridData();
		PogData clonePog;
		if (pogData.isThisAPlayer()) {
			clonePog = pogData;
		} else {
			clonePog = pogData.clone();
		}
		return (addPogToCanvas(clonePog));
	}

	/**
	 * Add this pog to canvas with the specified Z order.
	 * 
	 * @param clonePog pog data
	 * @return pog canvas
	 */
	private PogCanvas addPogToCanvas(final PogData clonePog) {
		PogCanvas scalablePog = new PogCanvas(clonePog);
		scalablePog.setPogWidth((int) gridSpacing - 10);
		pogs.add(scalablePog);
		scalablePog.getElement().getStyle().setZIndex(getPogZ(clonePog));
		add(scalablePog, (int) columnToPixel(scalablePog.getPogColumn()), (int) rowToPixel(scalablePog.getPogRow()));
		return (scalablePog);
	}

	/**
	 * Get adjusted grid size. This is compensated for zoom factor
	 * 
	 * @return adjusted grid size
	 */
	private double adjustedGridSize() {
		return (gridSpacing * totalZoom);
	}

	/**
	 * Remove highlighting from grid cell.
	 */
	protected void removeHighlightGridSquare() {
		greyOutPanel.getElement().getStyle().setBackgroundColor("grey");
		greyOutPanel.setVisible(false);
	}

	/**
	 * Highlight the cell at X and Y.
	 * 
	 * @param clientX Top left X
	 * @param clientY Top Left Y
	 */
	protected void highlightGridSquare(final int clientX, final int clientY) {
		computeSelectedColumnAndRow(clientX, clientY);
		PogData pogBeingDragged = ServiceManager.getDungeonManager().getPogBeingDragged();
		int pogWidth = pogBeingDragged.getPogSize() - 1;
		if (selectedColumn < 0 || selectedColumn + pogWidth >= verticalLines || selectedRow < 0 || selectedRow + pogWidth >= horizontalLines) {
			dragColumn = -1;
			dragRow = -1;
			removeHighlightGridSquare();
			return;
		}
		if (dragColumn == selectedColumn && dragRow == selectedRow) {
			return;
		}
		dragColumn = selectedColumn;
		dragRow = selectedRow;
		handleDragBox();
	}

	/**
	 * Compute Column and row from the X and Y positions.
	 * 
	 * @param clientX X position
	 * @param clientY Y position
	 */
	private void computeSelectedColumnAndRow(final int clientX, final int clientY) {
		double xCoord = clientX - getAbsoluteLeft();
		double yCoord = clientY - getAbsoluteTop();
		selectedColumn = ((int) (((xCoord - offsetX - gridOffsetY)) / adjustedGridSize()));
		selectedRow = ((int) (((yCoord - offsetY - gridOffsetY)) / adjustedGridSize()));
	}

	/**
	 * Handle the box that indicates which cell a pog is being dragged into.
	 */
	private void handleDragBox() {
		if (dragColumn < 0 || dragRow < 0) {
			removeHighlightGridSquare();
			return;
		}
		PogData pogBeingDragged = ServiceManager.getDungeonManager().getPogBeingDragged();
		double size = adjustedGridSize() * pogBeingDragged.getPogSize();
		greyOutPanel.getElement().getStyle().setZIndex(GREYOUT_Z);
		greyOutPanel.getElement().getStyle().setBackgroundColor(computeDragColor());
		greyOutPanel.setWidth("" + size + "px");
		greyOutPanel.setHeight("" + size + "px");
		super.setWidgetPosition(greyOutPanel, (int) columnToPixel(dragColumn), (int) rowToPixel(dragRow));
		greyOutPanel.setVisible(true);
	}

	/**
	 * Get color for drag indicator. Players are only all to drag over non-fog of war areas.
	 * 
	 * @return color string
	 */
	private String computeDragColor() {
		if (!ServiceManager.getDungeonManager().isDungeonMaster()) {
			if (ServiceManager.getDungeonManager().isFowSet(dragColumn, dragRow)) {
				return ("red");
			}
		}
		return ("grey");
	}

	/**
	 * Dungeon data has changed get the URL for current level picture. All other activities will get triggered when the picture actually loads.
	 */
	public void dungeonDataChanged() {
		intializeView();
		DungeonLevel dungeonLevel = ServiceManager.getDungeonManager().getCurrentLevelData();
		if (dungeonLevel == null) {
			return;
		}
		String dungeonPicture = dungeonLevel.getLevelDrawing();
		String imageUrl = ServiceManager.getDungeonManager().getUrlToDungeonResource(dungeonPicture);
		image.setUrl(imageUrl);
	}

	/**
	 * Dungeon data changed.
	 */
	public void dungeonDataUpdated() {
		removePogs();
		addPogs();
	}

	/**
	 * Remove all pogs from canvas.
	 */
	private void removePogs() {
		for (PogCanvas pog : pogs) {
			this.remove(pog);
		}
		pogs.clear();
	}

	/**
	 * Add all pogs from dungeon to canvas.
	 */
	private void addPogs() {
		getGridData();
		addMonsterPogs();
		addPlayerPogs();
		addRoomPogs();
		drawEverything();
	}

	/**
	 * Add monster pogs.
	 */
	private void addMonsterPogs() {
		PogData[] monsters = ServiceManager.getDungeonManager().getMonstersForCurrentLevel();
		if (monsters == null) {
			return;
		}
		for (PogData monster : monsters) {
			addPogToCanvas(monster);
		}
	}

	/**
	 * add room object pogs.
	 */
	private void addRoomPogs() {
		PogData[] roomObjects = ServiceManager.getDungeonManager().getRoomObjectsForCurrentLevel();
		if (roomObjects == null) {
			return;
		}
		for (PogData roomObject : roomObjects) {
			addPogToCanvas(roomObject);
		}
	}

	/**
	 * Add players pogs.
	 */
	private void addPlayerPogs() {
		PogData[] players = ServiceManager.getDungeonManager().getPlayersForCurrentSession();
		if (players == null) {
			return;
		}
		for (PogData player : players) {
			if (player != null) {
				addPogToCanvas(player);
			}
		}
	}
	
	/**
	 * Currently selected pog canvas.
	 */
	private PogCanvas selectedPogCanvas;
	
	/**
	 * Handle newly selected pog.
	 */
	protected void newSelectedPog() {
		if (selectedPogCanvas != null) {
			selectedPogCanvas.removeStyleName("selectedePog");
			selectedPogCanvas = null;
		}
		PogData pog = ServiceManager.getDungeonManager().getSelectedPog();
		if (pog == null) {
			return;
		}
		for (PogCanvas pogCanvas : pogs) {
			if (pogCanvas.getPogData().isEquals(pog)) {
				selectedPogCanvas = pogCanvas;
				selectedPogCanvas.addStyleName("selectedePog");
			}
		}
	}
}
