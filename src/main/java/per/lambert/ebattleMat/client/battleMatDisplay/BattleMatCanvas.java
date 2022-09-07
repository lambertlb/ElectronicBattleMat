/*
 * Copyright (C) 2019 Leon Lambert.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package per.lambert.ebattleMat.client.battleMatDisplay;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.event.ReasonForActionEventHandler;
import per.lambert.ebattleMat.client.interfaces.Constants;
import per.lambert.ebattleMat.client.interfaces.DungeonMasterFlag;
import per.lambert.ebattleMat.client.interfaces.IDungeonManager;
import per.lambert.ebattleMat.client.interfaces.IEventManager;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.DungeonLevel;
import per.lambert.ebattleMat.client.services.serviceData.PogData;
import per.lambert.ebattleMat.client.touchHelper.DoubleTapEvent;
import per.lambert.ebattleMat.client.touchHelper.DoubleTapHandler;
import per.lambert.ebattleMat.client.touchHelper.PanEndEvent;
import per.lambert.ebattleMat.client.touchHelper.PanEndHandler;
import per.lambert.ebattleMat.client.touchHelper.PanEvent;
import per.lambert.ebattleMat.client.touchHelper.PanHandler;
import per.lambert.ebattleMat.client.touchHelper.PanStartEvent;
import per.lambert.ebattleMat.client.touchHelper.PanStartHandler;
import per.lambert.ebattleMat.client.touchHelper.TouchHelper;
import per.lambert.ebattleMat.client.touchHelper.TouchInformation;
import per.lambert.ebattleMat.client.touchHelper.ZoomEndEvent;
import per.lambert.ebattleMat.client.touchHelper.ZoomEndHandler;
import per.lambert.ebattleMat.client.touchHelper.ZoomEvent;
import per.lambert.ebattleMat.client.touchHelper.ZoomHandler;
import per.lambert.ebattleMat.client.touchHelper.ZoomStartEvent;
import per.lambert.ebattleMat.client.touchHelper.ZoomStartHandler;

/**
 * @author LLambert
 * 
 *         Class to manage a scaled image with overlays.
 * 
 *         This support panning and zooming the all images.
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
	 * Maximum zoom factor.
	 */
	private static final double MAX_ZOOM = .5;
	/**
	 * Show grid.
	 */
	private boolean showGrid = false;
	/**
	 * Adjusted grid spacing so the grid lines matches exactly to one that might already be in a picture.
	 */
	private double gridSpacing = 50;
	/**
	 * Main canvas that is drawn.
	 */
	private Canvas canvas = Canvas.createIfSupported();
	/**
	 * background canvas for temporary drawing and then scaled and drawn on the main canvas.
	 */
	private Canvas backCanvas = Canvas.createIfSupported();
	/**
	 * Fog of war canvas.
	 */
	private Canvas fowCanvas = Canvas.createIfSupported();
	/**
	 * line style grey.
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
	 * image element for drawing.
	 */
	private ImageElement imageElement;
	/**
	 * current zoom factor for image.
	 */
	private double totalZoom = 1;
	/**
	 * Offset of image in the horizontal direction. Used for panning the image.
	 */
	private double offsetX = 0;
	/**
	 * Offset of image in the vertical direction. Used for panning the image.
	 */
	private double offsetY = 0;
	/**
	 * grid origin offset X. Used to align the overlay grid with a grid that maybe in picture.
	 */
	private double gridOffsetX = 0;
	/**
	 * grid origin offset Y. Used to align the overlay grid with a grid that maybe in picture.
	 */
	private double gridOffsetY = 0;
	/**
	 * Used by pan images.
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
	 * Image for main canvas.
	 */
	private Image image = new Image();
	/**
	 * panel to managing the grey out area when pog is being dragged.
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
	 * true if clearing fog or war.
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
	 * Currently selected pog canvas.
	 */
	private PogCanvas selectedPogCanvas;
	/**
	 * Width of pog border.
	 */
	private double pogBorderWidth = 3;
	/**
	 * Helper for mobile touches.
	 */
	private TouchHelper touchHelper;
	/**
	 * Distance between fingers.
	 */
	private double distanceBetweenFingers;

	/**
	 * Widget for managing all battle mat activities.
	 */
	public BattleMatCanvas() {
		hidePanel = new LayoutPanel();
		greyOutPanel = new LayoutPanel();
		greyOutPanel.getElement().getStyle().setZIndex(Constants.GREYOUT_Z);
		hidePanel.add(image);
		hidePanel.setVisible(false);
		fowCanvas.getElement().getStyle().setZIndex(Constants.FOW_Z);
		fowCanvas.setStyleName("noEvents");
		intializeView();
		showGrid = false;
		touchHelper = new TouchHelper(canvas);
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
				ServiceManager.getDungeonManager().setPogBeingDragged(null, false);
			}
		}, DropEvent.getType());
		this.addDomHandler(new DragLeaveHandler() {
			@Override
			public void onDragLeave(final DragLeaveEvent event) {
			}
		}, DragLeaveEvent.getType());
	}

	/**
	 * Setup event handlers.
	 */
	private void setupEventHandling() {
		canvas.addMouseWheelHandler(this);
		canvas.addMouseMoveHandler(this);
		canvas.addMouseDownHandler(this);
		canvas.addMouseUpHandler(this);
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
		addTouchHandlerEvents();
	}

	/**
	 * Add touch handlers.
	 */
	private void addTouchHandlerEvents() {
		touchHelper.addDoubleTapHandler(new DoubleTapHandler() {
			@Override
			public void onDoubleTap(final DoubleTapEvent event) {
				doDoubleTap(event);
			}
		});
		touchHelper.addPanStartHandler(new PanStartHandler() {
			@Override
			public void onPanStart(final PanStartEvent event) {
				doPanStart(event);
			}
		});
		touchHelper.addPanEndHandler(new PanEndHandler() {
			@Override
			public void onPanEnd(final PanEndEvent event) {
				doPanEnd(event);
			}
		});
		touchHelper.addPanHandler(new PanHandler() {
			@Override
			public void onPan(final PanEvent event) {
				doPan(event);
			}
		});
		touchHelper.addZoomHandler(new ZoomHandler() {
			@Override
			public void onZoom(final ZoomEvent event) {
				doZoom(event);
			}
		});
		touchHelper.addZoomStartHandler(new ZoomStartHandler() {
			@Override
			public void onZoomStart(final ZoomStartEvent event) {
				doZoomStart(event);
			}
		});
		touchHelper.addZoomEndHandler(new ZoomEndHandler() {
			@Override
			public void onZoomEnd(final ZoomEndEvent event) {
				doZoomEnd(event);
			}
		});
		canvas.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(final DoubleClickEvent event) {
				restoreOriginalView();
			}
		});
	}

	/**
	 * Set the image for this control.
	 */
	private void setImage() {
		totalZoom = 1;
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
		sizeACanvas(canvas);
		sizeACanvas(backCanvas);
		sizeACanvas(fowCanvas);
		calculateStartingZoom();
		backCanvas.getContext2d().setTransform(totalZoom, 0, 0, totalZoom, 0, 0);
		drawEverything();
	}

	/**
	 * Adjust canvas size to parent size.
	 * 
	 * @param canvas to adjust
	 */
	private void sizeACanvas(final Canvas canvas) {
		canvas.setWidth(parentWidth + "px");
		canvas.setCoordinateSpaceWidth(parentWidth);
		canvas.setHeight(parentHeight + "px");
		canvas.setCoordinateSpaceHeight(parentHeight);
	}

	/**
	 * Calculate the starting zoom factor.
	 */
	private void calculateStartingZoom() {
		totalZoom = 1;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void onMouseWheel(final MouseWheelEvent event) {
		int move = event.getDeltaY();
		double xPos = (event.getRelativeX(canvas.getElement()));
		double yPos = (event.getRelativeY(canvas.getElement()));

		double deltaZoom = DEFAULT_ZOOM;
		if (move >= 0) {
			deltaZoom = 1 / DEFAULT_ZOOM;
		}
		zoomCanvas(xPos, yPos, deltaZoom);
	}

	/**
	 * Zoom canvas base on delta positions.
	 * 
	 * @param xPos current X
	 * @param yPos current Y
	 * @param deltaZoom delta zoom factor
	 */
	private void zoomCanvas(final double xPos, final double yPos, final double deltaZoom) {
		double newX = (xPos - offsetX) / totalZoom;
		double newY = (yPos - offsetY) / totalZoom;
		double xPosition = (-newX * deltaZoom) + newX;
		double yPosition = (-newY * deltaZoom) + newY;

		double newZoom = deltaZoom * totalZoom;
		if (newZoom < MAX_ZOOM) {
			newZoom = MAX_ZOOM;
		} else {
			offsetX += (xPosition * totalZoom);
			offsetY += (yPosition * totalZoom);
		}
		totalZoom = newZoom;
		drawEverything();
	}

	/**
	 * {@inheritDoc}
	 */
	public final void onMouseDown(final MouseDownEvent event) {
		if (DOM.getCaptureElement() == null) {
			mouseDownXPos = event.getRelativeX(image.getElement());
			mouseDownYPos = event.getRelativeY(image.getElement());
			if (event.isShiftKeyDown()) {
				toggleFOW = true;
			} else {
				toggleFOW = ServiceManager.getDungeonManager().getFowToggle();
			}
			checkForFOWHandling(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
			DOM.setCapture(canvas.getElement());
			this.mouseDown = true;
		}
	}

	public final boolean isSelectedVisible(final int clientX, final int clientY) {
		if (clientX < 0 || clientY < 0) {
			return (false);
		}
		return (ServiceManager.getDungeonManager().isInFOWMap(selectedColumn, selectedRow));
	}
	/**
	 * Check if we need to handle fog of war.
	 * 
	 * @param clientX X Coordinate of operation.
	 * @param clientY Y Coordinate of operation.
	 */
	private void checkForFOWHandling(final int clientX, final int clientY) {
		computeSelectedColumnAndRow(clientX, clientY);
		if (!isSelectedVisible(clientX, clientY)) {
			return;
		}
		clearFOW = ServiceManager.getDungeonManager().isFowSet(selectedColumn, selectedRow);
		if (toggleFOW && ServiceManager.getDungeonManager().isDungeonMaster()) {
			handleProperFOWAtSelectedPosition();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public final void onMouseMove(final MouseMoveEvent event) {
		if (!mouseDown) {
			return;
		}
		if (!toggleFOW) {
			handleMouseMoveWhilePanning(event);
		} else if (toggleFOW && ServiceManager.getDungeonManager().isDungeonMaster()) {
			handleFowMouseMove(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
		}
	}

	/**
	 * Handle mouse move dealing with fog of war.
	 * 
	 * @param clientX X Coordinate of operation.
	 * @param clientY Y Coordinate of operation.
	 */
	private void handleFowMouseMove(final int clientX, final int clientY) {
		computeSelectedColumnAndRow(clientX, clientY);
		if (isSelectedVisible(clientX, clientY)) {
			handleProperFOWAtSelectedPosition();
		}
	}

	/**
	 * Handle fog of war selected position.
	 */
	private void handleProperFOWAtSelectedPosition() {
		boolean currentFOW = ServiceManager.getDungeonManager().isFowSet(selectedColumn, selectedRow);
		if (currentFOW != !clearFOW) {
			ServiceManager.getDungeonManager().setFow(selectedColumn, selectedRow, !currentFOW);
			drawFOW(!currentFOW, adjustedGridSize() + 2, selectedColumn, selectedRow);
		}
	}

	/**
	 * Handle mouse move while panning.
	 * 
	 * @param event event data
	 */
	private void handleMouseMoveWhilePanning(final MouseMoveEvent event) {
		double xPos = event.getRelativeX(image.getElement());
		double yPos = event.getRelativeY(image.getElement());
		handleCanvasMoveWhilePanning(xPos, yPos);
	}

	/**
	 * Handle moving canvas while panning.
	 * 
	 * @param xPos center X of pan
	 * @param yPos center Y of pan
	 */
	private void handleCanvasMoveWhilePanning(final double xPos, final double yPos) {
		offsetX += (xPos - mouseDownXPos);
		offsetY += (yPos - mouseDownYPos);
		drawEverything();
		mouseDownXPos = xPos;
		mouseDownYPos = yPos;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void onMouseUp(final MouseUpEvent event) {
		panOperationComplete();
		DOM.releaseCapture(canvas.getElement());
	}

	/**
	 * pan completion.
	 */
	private void panOperationComplete() {
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
		verticalLines = (int) (imageWidth / gridSpacing) + 1;
		horizontalLines = (int) (imageHeight / gridSpacing) + 1;
		ServiceManager.getDungeonManager().setSessionLevelSize(verticalLines, horizontalLines);
	}

	/**
	 * Get grid data from dungeon manager.
	 */
	private void getGridData() {
		gridOffsetX = ServiceManager.getDungeonManager().getCurrentDungeonLevelData().getGridOffsetX() * totalZoom;
		gridOffsetY = ServiceManager.getDungeonManager().getCurrentDungeonLevelData().getGridOffsetY() * totalZoom;
		gridSpacing = ServiceManager.getDungeonManager().getCurrentDungeonLevelData().getGridSize();
		showGrid = ServiceManager.getDungeonManager().getSelectedDungeon().getShowGrid();
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
	 * Adjust pog positions.
	 * 
	 * Pog data has the column and row and the widget needs to be moved to the proper pixel.
	 */
	private void adjustPogs() {
		computPogBorderWidth();
		for (PogCanvas pog : pogs) {
			int x = (int) (columnToPixel(pog.getPogColumn()));
			int y = (int) (rowToPixel(pog.getPogRow()));
			if (pog.getPogData().isFlagSet(DungeonMasterFlag.SHIFT_RIGHT)) {
				x += (adjustedGridSize() / 2);
			}
			if (pog.getPogData().isFlagSet(DungeonMasterFlag.SHIFT_TOP)) {
				y -= (adjustedGridSize() / 2);
			}
			setWidgetPosition(pog, x, y);
			pog.setPogSizing(adjustedGridSize(), pogBorderWidth, totalZoom);
			if (!ServiceManager.getDungeonManager().isDungeonMaster() && pog.isInVisibleToPlayer()) {
				pog.getElement().getStyle().setBorderWidth(0, Unit.PX);
			} else {
				pog.getElement().getStyle().setBorderWidth(pogBorderWidth, Unit.PX);
			}
		}
	}

	/**
	 * Compute width of border.
	 */
	private void computPogBorderWidth() {
		pogBorderWidth = totalZoom * getStartingBorderWidth();
		if (pogBorderWidth < 1.0) {
			pogBorderWidth = 1.0;
		}
	}

	/**
	 * Starting border width.
	 * 
	 * @return starting border width
	 */
	private double getStartingBorderWidth() {
		return (gridSpacing / 15);
	}

	/**
	 * Draw fog of war.
	 * 
	 * If this is DM let them see through it.
	 */
	private void drawFogOfWar() {
		if (ServiceManager.getDungeonManager().isEditMode()) {
			return;
		}
		IDungeonManager dungeonManager = ServiceManager.getDungeonManager();
		double size = adjustedGridSize();
		if (dungeonManager.isDungeonMaster()) {
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
	 * If the fog of war bit is not set then make the cell transparent.
	 * 
	 * @param isSet true if need to be draw.
	 * @param size width and height of cell
	 * @param column of cell
	 * @param row of cell
	 */
	private void drawFOW(final boolean isSet, final double size, final int column, final int row) {
		int x = (int) columnToPixel(column);
		int y = (int) rowToPixel(row);
		double newSize = size + 1;
		if (isSet) {
			fowCanvas.getContext2d().setFillStyle(fogOfWarColor);
			fowCanvas.getContext2d().fillRect(x - 1, y - 1, newSize, newSize);
		} else {
			fowCanvas.getContext2d().clearRect(x - 1, y - 1, newSize, newSize);
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
	 * Drop a pog into a cell.
	 * 
	 * @param event event data
	 */
	private void dropPog(final DropEvent event) {
		PogData pogBeingDragged = ServiceManager.getDungeonManager().getPogBeingDragged();
		if (pogBeingDragged == null) {
			return;
		}
		boolean isDM = ServiceManager.getDungeonManager().isDungeonMaster();
		if (!isDM) {
			if (ServiceManager.getDungeonManager().isFowSet(dragColumn, dragRow) || !pogBeingDragged.isThisAPlayer()) {
				// players are only allowed to drag PLAYER pogs onto visible cells.
				removeHighlightGridSquare();
				return;
			}
		}
		if (dragColumn < 0 || dragRow < 0) { // no dragging off screen.
			return;
		}
		PogCanvas dragPog = updateOrCreatePogCanvasForThisCell();
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
	private PogCanvas updateOrCreatePogCanvasForThisCell() {
		PogCanvas existingPog = findCanvasForDraggedPog();
		if (existingPog == null || (!existingPog.getPogData().isThisAPlayer()) && ServiceManager.getDungeonManager().isFromRibbonBar()) {
			existingPog = addClonePogToCanvas(ServiceManager.getDungeonManager().getPogBeingDragged());
		} else {
			// ensure it is on top
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
		pog.getPogData().setDungeonLevel(ServiceManager.getDungeonManager().getCurrentLevelIndex());
	}

	/**
	 * get proper Z for pog based on type.
	 * 
	 * @param pogData pog data
	 * @return Proper Z
	 */
	private int getPogZ(final PogData pogData) {
		if (pogData.isThisAMonster()) {
			return (Constants.MONSTERS_Z);
		}
		if (pogData.isThisAPlayer()) {
			return (Constants.PLAYERS_Z);
		}
		return (Constants.ROOMOBJECTS_Z);
	}

	/**
	 * Add a pog to the canvas. If the pog is not a player then clone the data. This is because there can only be one player instance but there can be many of the other types.
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
		pogs.add(scalablePog);
		scalablePog.getElement().getStyle().setZIndex(getPogZ(clonePog));
		computPogBorderWidth();
		add(scalablePog, (int) columnToPixel(scalablePog.getPogColumn()) + (int) pogBorderWidth, (int) rowToPixel(scalablePog.getPogRow() + (int) pogBorderWidth));
		scalablePog.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		scalablePog.getElement().getStyle().setBorderColor("grey");
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
		PogData pogBeingDragged = ServiceManager.getDungeonManager().getPogBeingDragged();
		if (pogBeingDragged == null) {
			return;
		}
		computeSelectedColumnAndRow(clientX, clientY);
		int pogWidth = pogBeingDragged.getSize() - 1;
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
		selectedColumn = ((int) (((xCoord - offsetX - gridOffsetX)) / adjustedGridSize()));
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
		double size = adjustedGridSize() * pogBeingDragged.getSize();
		greyOutPanel.getElement().getStyle().setZIndex(Constants.GREYOUT_Z);
		greyOutPanel.getElement().getStyle().setBackgroundColor(computeDragColor());
		greyOutPanel.setWidth("" + size + "px");
		greyOutPanel.setHeight("" + size + "px");
		super.setWidgetPosition(greyOutPanel, (int) columnToPixel(dragColumn), (int) rowToPixel(dragRow));
		greyOutPanel.setVisible(true);
	}

	/**
	 * Get color for drag indicator. Players are only allowed to drag over non-fog of war areas.
	 * 
	 * @return color string
	 */
	private String computeDragColor() {
		if (!ServiceManager.getDungeonManager().isDungeonMaster()) {
			if (!ServiceManager.getDungeonManager().getPogBeingDragged().isThisAPlayer()) {
				return ("red");
			}
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
		DungeonLevel dungeonLevel = ServiceManager.getDungeonManager().getCurrentDungeonLevelData();
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
		deSelectPog();
		removePogs();
		addPogs();
		newSelectedPog();
	}

	/**
	 * Remove all pogs from canvas.
	 */
	private void removePogs() {
		for (PogCanvas pog : pogs) {
			remove(pog);
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
	 * Handle newly selected pog.
	 */
	protected void newSelectedPog() {
		deSelectPog();
		PogData pog = ServiceManager.getDungeonManager().getSelectedPog();
		if (pog == null) {
			return;
		}
		for (PogCanvas pogCanvas : pogs) {
			if (pogCanvas.getPogData().isEqual(pog)) {
				selectedPogCanvas = pogCanvas;
				selectedPogCanvas.getElement().getStyle().setBorderColor("black");
				break;
			}
		}
	}

	/**
	 * de-select current pog.
	 */
	private void deSelectPog() {
		if (selectedPogCanvas != null) {
			selectedPogCanvas.getElement().getStyle().setBorderColor("grey");
			selectedPogCanvas = null;
		}
	}

	/**
	 * Handle double tap.
	 * 
	 * @param event with data.
	 */
	protected void doDoubleTap(final DoubleTapEvent event) {
//		restoreOriginalView();
	}

	/**
	 * Restore view to original.
	 */
	private void restoreOriginalView() {
		offsetX = 0;
		offsetY = 0;
		calculateStartingZoom();
		drawEverything();
	}

	/**
	 * Get X coordinate relative between touch and target element.
	 * 
	 * @param touchInformation touch information
	 * @param target widget
	 * @return X coordinate relative between mouse click and target element.
	 */
	public int getRelativeX(final TouchInformation touchInformation, final Element target) {
		return touchInformation.getClientX() - target.getAbsoluteLeft() + target.getScrollLeft() + target.getOwnerDocument().getScrollLeft();
	}

	/**
	 * Get Y coordinate relative between touch and target element.
	 * 
	 * @param touchInformation touch information
	 * @param target widget
	 * @return Y coordinate relative between mouse click and target element.
	 */
	public int getRelativeY(final TouchInformation touchInformation, final Element target) {
		return touchInformation.getClientY() - target.getAbsoluteTop() + target.getScrollTop() + target.getOwnerDocument().getScrollTop();
	}

	/**
	 * Handle Pan start.
	 * 
	 * @param event with data
	 */
	protected void doPanStart(final PanStartEvent event) {
		mouseDownXPos = getRelativeX(event.getTouchInformation(), canvas.getElement());
		mouseDownYPos = getRelativeY(event.getTouchInformation(), canvas.getElement());
		toggleFOW = ServiceManager.getDungeonManager().getFowToggle();
		checkForFOWHandling(event.getTouchInformation().getClientX(), event.getTouchInformation().getClientY());
		this.mouseDown = !toggleFOW;
	}

	/**
	 * Handle Pan end.
	 * 
	 * @param event with data
	 */
	protected void doPanEnd(final PanEndEvent event) {
		this.mouseDown = false;
		panOperationComplete();
	}

	/**
	 * Handle Pan.
	 * 
	 * @param event with data
	 */
	protected void doPan(final PanEvent event) {
		if (mouseDown) {
			double xPos = event.getTouchInformation().getPageX();
			double yPos = event.getTouchInformation().getPageY();
			handleCanvasMoveWhilePanning(xPos, yPos);
		} else if (toggleFOW && ServiceManager.getDungeonManager().isDungeonMaster()) {
			handleFowMouseMove(event.getTouchInformation().getClientX(), event.getTouchInformation().getClientY());
		}
	}

	/**
	 * Handle zoom start event.
	 * 
	 * @param event with data
	 */
	protected void doZoomStart(final ZoomStartEvent event) {
		distanceBetweenFingers = event.getZoomInformation().getStartingDistance();
	}

	/**
	 * Handle zoom end event.
	 * 
	 * @param event with data
	 */
	protected void doZoomEnd(final ZoomEndEvent event) {
	}

	/**
	 * Handle zoom event.
	 * 
	 * @param event with data
	 */
	protected void doZoom(final ZoomEvent event) {
		double currentDistance = event.getZoomInformation().getCurrentDistance();
		double xPos = event.getZoomInformation().currentCenterX();
		double yPos = event.getZoomInformation().currentCenterY();
		zoomCanvas(xPos, yPos, currentDistance / distanceBetweenFingers);
		distanceBetweenFingers = currentDistance;
	}
}
