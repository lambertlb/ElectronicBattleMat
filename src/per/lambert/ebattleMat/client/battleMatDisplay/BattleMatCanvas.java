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
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.ElectronicBattleMat;
import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.DungeonMasterFlag;
import per.lambert.ebattleMat.client.interfaces.IDungeonManager;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
import per.lambert.ebattleMat.client.services.serviceData.PogData;
import per.lambert.ebattleMat.client.services.serviceData.PogDataLite;

/**
 * @author LLambert Class to manage a scaled image with overlays.
 */
public class BattleMatCanvas extends AbsolutePanel implements MouseWheelHandler, MouseDownHandler, MouseMoveHandler, MouseUpHandler, TouchStartHandler, TouchMoveHandler, TouchEndHandler {

	/**
	 * Offset for clearing rectangle.
	 */
	private static final int CLEAR_OFFEST = -10;
	/**
	 * Default zoom constant.
	 */
	private static final double DEFAULT_ZOOM = 1.1;
	private static final int PLAYERS_Z = 5;
	private static final int MONSTERS_Z = 3;
	private static final int ROOMOBJECTS_Z = 1;
	private static final int GREYOUT_Z = 7;
	private static final int FOW_Z = 5;
	public static final int DIALOG_Z = 10;

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
	private Canvas fowCanvas = Canvas.createIfSupported();
	/**
	 * line style yellow.
	 */
	private final CssColor gridColor = CssColor.make("grey");
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

	private BattleMatLayout parentPanel;
	private Image image = new Image();
	private LayoutPanel greyOutPanel;
	private LayoutPanel hidePanel;

	private List<PogCanvas> pogs = new ArrayList<PogCanvas>();

	public BattleMatLayout getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(BattleMatLayout parentPanel) {
		this.parentPanel = parentPanel;
	}

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

	private void intializeView() {
		pogs.clear();
		super.clear();
		super.add(canvas, 0, 0);
		super.add(fowCanvas, 0, 0);
		super.add(hidePanel, -1, -1);
		super.add(greyOutPanel, 100, 100);
	}

	private void setupDragAndDrop() {
		this.addDomHandler(new DragOverHandler() {

			@Override
			public void onDragOver(DragOverEvent event) {
				highlightGridSquare(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
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
			}
		}, DragLeaveEvent.getType());
	}

	private void setupEventHandling() {
		image.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
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
			}
		});
	}

	@Override
	public void add(Widget w, int left, int top) {
		super.add(w, left, top);
	}

	/**
	 * Set the image for this control.
	 * 
	 * @param imageToDisplay image to display.
	 * @param widthOfParent current width of parent window.
	 * @param heightOfParent current height of parent window.
	 */
	private final void setImage() {
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
		mainDraw();
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
		getRibbonBarData();
		mainDraw();
	}

	private boolean toggleFOW;
	private boolean clearFOW;

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

	private void checkForFOWHandling(final NativeEvent event) {
		toggleFOW = ServiceManager.getDungeonManager().getFowToggle();
		computeSelectedColumnAndRow(event.getClientX(), event.getClientY());
		clearFOW = ServiceManager.getDungeonManager().isFowSet(selectedColumn, selectedRow);
		if (toggleFOW && ServiceManager.getDungeonManager().isDungeonMaster()) {
			handleProperFOWAtSelectedPosition();
		}
	}

	public final void onMouseMove(final MouseMoveEvent event) {
		if (mouseDown) {
			handleMouseMove(event);
		} else if (toggleFOW && ServiceManager.getDungeonManager().isDungeonMaster()) {
			handleFowMouseMove(event);
		}
	}

	private void handleFowMouseMove(MouseMoveEvent event) {
		computeSelectedColumnAndRow(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
		handleProperFOWAtSelectedPosition();
	}

	private void handleProperFOWAtSelectedPosition() {
		boolean currentFOW = ServiceManager.getDungeonManager().isFowSet(selectedColumn, selectedRow);
		if (currentFOW == !clearFOW) {
			return;
		}
		ServiceManager.getDungeonManager().setFow(selectedColumn, selectedRow, !currentFOW);
		drawFOW(!currentFOW, adjustedGridSize() + 2, selectedColumn, selectedRow);
	}

	private void handleMouseMove(final MouseMoveEvent event) {
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
	public final void mainDraw() {
		calculateDimensions();
		backCanvas.getContext2d().clearRect(CLEAR_OFFEST, CLEAR_OFFEST, imageWidth + gridSpacing, imageHeight + gridSpacing);
		backCanvas.getContext2d().setTransform(totalZoom, 0, 0, totalZoom, offsetX, offsetY);
		backCanvas.getContext2d().drawImage(imageElement, 0, 0);
		buffer();
	}

	/**
	 * Calculate numbers dependent on parent to image size and grid spacing.
	 */
	private void calculateDimensions() {
		getRibbonBarData();
		showGrid = ServiceManager.getDungeonManager().getSelectedDungeon().getShowGrid();
		verticalLines = (int) (imageWidth / gridSpacing) + 1;
		horizontalLines = (int) (imageHeight / gridSpacing) + 1;
		ServiceManager.getDungeonManager().setSessionLevelSize(verticalLines, horizontalLines);
	}

	private void getRibbonBarData() {
		gridOffsetX = ServiceManager.getDungeonManager().getCurrentLevelData().getGridOffsetX() * totalZoom;
		gridOffsetY = ServiceManager.getDungeonManager().getCurrentLevelData().getGridOffsetY() * totalZoom;
		gridSpacing = ServiceManager.getDungeonManager().getCurrentLevelData().getGridSize();
	}

	public final void buffer() {
		canvas.getContext2d().clearRect(CLEAR_OFFEST, CLEAR_OFFEST, parentWidth + gridSpacing, parentHeight + gridSpacing);
		canvas.getContext2d().drawImage(backCanvas.getCanvasElement(), 0, 0);
		fowCanvas.getContext2d().clearRect(CLEAR_OFFEST, CLEAR_OFFEST, parentWidth + gridSpacing, parentHeight + gridSpacing);
		drawGridLines();
		adjustPogs();
		drawFogOfWar();
	}

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

	private double columnToPixel(int column) {
		return ((adjustedGridSize() * column) + offsetX + gridOffsetX);
	}

	private double rowToPixel(int row) {
		return ((adjustedGridSize() * row) + offsetY + gridOffsetY);
	}

	/**
	 * Draw vertical grid lines.
	 * 
	 * @param scaledWidth scaled width.
	 * @param scaledHeight scaled height.
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
	 * 
	 * @param scaledWidth scaled width
	 * @param scaledHeight scaled height
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

	private void drawFogOfWar() {
		if (ServiceManager.getDungeonManager().isEditMode()) {
			return;
		}
		IDungeonManager dungeonManager = ServiceManager.getDungeonManager();
		// double size = adjustedGridSize() + 2;
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

	private void drawFOW(boolean isSet, double size, int i, int j) {
		int x = (int) columnToPixel(i);
		int y = (int) rowToPixel(j);
		if (isSet) {
			fowCanvas.getContext2d().setFillStyle(fogOfWarColor);
			fowCanvas.getContext2d().fillRect(x, y, size, size);
		} else {
			fowCanvas.getContext2d().clearRect(x, y, size, size);
		}
	}

	/**
	 * Outline the image.
	 * 
	 * @param scaledWidth scaled width
	 * @param scaledHeight scaled height
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

	private void dropPog(DropEvent event) {
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
		PogCanvas dragPog = getPogThatWasDragged();
		if (dragPog != null) {
			removeHighlightGridSquare();
			mainDraw();
		}
	}

	private PogCanvas getPogThatWasDragged() {
		PogData pogBeingDragged = ServiceManager.getDungeonManager().getPogBeingDragged();
		for (PogCanvas pog : pogs) {
			if (pog.getPogData() == pogBeingDragged) {
				pog.setPogPosition(dragColumn, dragRow);
				ServiceManager.getDungeonManager().updatePogDataOnLevel(pogBeingDragged);
				return (pog);
			}
		}
		return addPogToCanvas(pogBeingDragged);
	}

	private int getPogZ(PogData pogData) {
		if (pogData.getPogType().equals(ElectronicBattleMat.POG_TYPE_MONSTER)) {
			return (MONSTERS_Z);
		}
		if (pogData.getPogType().equals(ElectronicBattleMat.POG_TYPE_PLAYER)) {
			return (PLAYERS_Z);
		}
		return (ROOMOBJECTS_Z);
	}

	private PogCanvas addPogToCanvas(PogData pogData) {
		getRibbonBarData();
		PogData clonePog = ServiceManager.getDungeonManager().createPogInstance(pogData);
		clonePog.setPogColumn(dragColumn);
		clonePog.setPogRow(dragRow);
		ServiceManager.getDungeonManager().addPogDataToLevel(clonePog);
		return addPogToCanvas(clonePog, getPogZ(pogData));
	}

	private PogCanvas addPogToCanvas(PogData clonePog, int zLevel) {
		PogCanvas scalablePog = new PogCanvas(clonePog);
		scalablePog.setPogWidth((int) gridSpacing - 10);
		pogs.add(scalablePog);
		scalablePog.getElement().getStyle().setZIndex(zLevel);
		add(scalablePog, (int) columnToPixel(scalablePog.getPogColumn()), (int) rowToPixel(scalablePog.getPogRow()));
		return (scalablePog);
	}

	private double adjustedGridSize() {
		return (gridSpacing * totalZoom);
	}

	protected void removeHighlightGridSquare() {
		greyOutPanel.getElement().getStyle().setBackgroundColor("grey");
		greyOutPanel.setVisible(false);
	}

	private int selectedColumn;
	private int selectedRow;

	protected void highlightGridSquare(int clientX, int clientY) {
		computeSelectedColumnAndRow(clientX, clientY);
		PogData pogBeingDragged = ServiceManager.getDungeonManager().getPogBeingDragged();
		int pogWidth = pogBeingDragged.getPogSize() - 1;
		if (selectedColumn < 0 || selectedColumn + pogWidth >= verticalLines || selectedRow < 0 || selectedRow + pogWidth >= horizontalLines) {
			dragColumn = dragRow = -1;
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

	private void computeSelectedColumnAndRow(int clientX, int clientY) {
		double xCoord = clientX - getAbsoluteLeft();
		double yCoord = clientY - getAbsoluteTop();
		selectedColumn = ((int) (((xCoord - offsetX - gridOffsetY)) / adjustedGridSize()));
		selectedRow = ((int) (((yCoord - offsetY - gridOffsetY)) / adjustedGridSize()));
	}

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

	private String computeDragColor() {
		if (!ServiceManager.getDungeonManager().isDungeonMaster()) {
			if ( ServiceManager.getDungeonManager().isFowSet(dragColumn, dragRow)) {
				return("red");
			}
		}
		return("grey");
	}

	@SuppressWarnings("unused")
	private void setStatus(String status) {
		if (parentPanel != null) {
			parentPanel.setStatus(status);
		}
	}

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

	public void dungeonDataUpdated() {
		removePogs();
		addPogs();
	}

	private void removePogs() {
		for (PogCanvas pog : pogs) {
			this.remove(pog);
		}
		pogs.clear();
	}

	private void addPogs() {
		getRibbonBarData();
		addMonsterPogs();
		addPlayerPogs();
		addRoomPogs();
		mainDraw();
	}

	private void addMonsterPogs() {
		PogDataLite[] monsters = ServiceManager.getDungeonManager().getMonstersForCurrentLevel();
		if (monsters == null) {
			return;
		}
		for (PogDataLite monster : monsters) {
			PogData clonePog = ServiceManager.getDungeonManager().fullCLoneMonster(monster);
			if (clonePog != null) {
				addPogToCanvas(clonePog, MONSTERS_Z);
			}
		}
	}

	private void addRoomPogs() {
		PogDataLite[] roomObjects = ServiceManager.getDungeonManager().getRoomObjectsForCurrentLevel();
		if (roomObjects == null) {
			return;
		}
		for (PogDataLite roomObject : roomObjects) {
			PogData clonePog = ServiceManager.getDungeonManager().fullCLoneRoomObject(roomObject);
			if (clonePog != null) {
				addPogToCanvas(clonePog, ROOMOBJECTS_Z);
			}
		}
	}

	private void addPlayerPogs() {
		PogData[] players = ServiceManager.getDungeonManager().getPlayersForCurrentSession();
		if (players == null) {
			return;
		}
		for (PogData player : players) {
			if (player != null) {
				addPogToCanvas(player, PLAYERS_Z);
			}
		}
	}

	@Override
	public void onTouchEnd(TouchEndEvent event) {
		event.preventDefault();
	}

	@Override
	public void onTouchMove(TouchMoveEvent event) {
		event.preventDefault();
	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		event.preventDefault();
	}
}
