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

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.HasDragStartHandlers;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.DungeonMasterFlag;
import per.lambert.ebattleMat.client.interfaces.PlayerFlag;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.ServiceManager;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

/**
 * Pog canvas.
 * 
 * This is used to display the picture for a Pog. It will scale the picture to a scale factor dictated by it's owner.
 * 
 * @author LLambert
 *
 */
public class PogCanvas extends Composite implements HasDragStartHandlers, MouseDownHandler {

	/**
	 * UI Binder.
	 */
	private static ScalablePogUiBinder uiBinder = GWT.create(ScalablePogUiBinder.class);

	/**
	 * Interface for UI binder.
	 * 
	 * @author LLambert
	 *
	 */
	interface ScalablePogUiBinder extends UiBinder<Widget, PogCanvas> {
	}

	/**
	 * Canvas for drawing pog.
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
	 * Image of pog.
	 */
	private Image image = new Image();
	/**
	 * Image count. Used to avoid browser caching images during editing.
	 */
	private int imageCount = 1;
	/**
	 * image context for drawing.
	 */
	private ImageElement imageElement;
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
	 * current zoom factor for image.
	 */
	private double totalZoom = 1;
	/**
	 * Offset for clearing rectangle.
	 */
	private static final int CLEAR_OFFEST = -10;
	/**
	 * Show as normal size only.
	 */
	private boolean showNormalSizeOnly = false;

	/**
	 * Get show normal size only.
	 * 
	 * @return true if set
	 */
	public boolean getShowNormalSizeOnly() {
		return showNormalSizeOnly;
	}

	/**
	 * Set show normal size only.
	 * 
	 * @param showNormalSizeOnly true if only normal size
	 */
	public void setShowNormalSizeOnly(final boolean showNormalSizeOnly) {
		this.showNormalSizeOnly = showNormalSizeOnly;
	}

	/**
	 * Pog data.
	 */
	private PogData pogData;

	/**
	 * Get Pog data.
	 * 
	 * @return pog data.
	 */
	public PogData getPogData() {
		return pogData;
	}

	/**
	 * Set pog data.
	 * 
	 * @param pogData pog data
	 */
	public void setPogData(final PogData pogData) {
		setPogData(pogData, false);
	}

	/**
	 * Was pog from ribbon bar. This is needed to inform Dungeon manager so it knows how to handle drag and drop properly.
	 */
	private boolean fromRibbonBar;

	/**
	 * set pog data.
	 * 
	 * @param pogData pog data
	 * @param fromRibbonBar true if from ribbon bar
	 */
	public void setPogData(final PogData pogData, final boolean fromRibbonBar) {
		this.fromRibbonBar = fromRibbonBar;
		setupWithPogData(pogData);
		drawEverything();
	}

	/**
	 * Get size of pog.
	 * 
	 * @return size of pog.
	 */
	public int getPogSize() {
		return pogData.getSize();
	}

	/**
	 * Set pog size.
	 * 
	 * @param pogSize pog size.
	 */
	public void setPogSize(final int pogSize) {
		pogData.setSize(pogSize);
	}

	/**
	 * Should image be shown.
	 */
	private boolean showImage = true;
	/**
	 * image has been loaded.
	 */
	private boolean imageLoaded = false;
	/**
	 * Width of pog displayed image.
	 */
	private double scaledWidth = 50;
	/**
	 * Current zoom factor.
	 */
	private double zoomFactor = 1;
	/**
	 * True to force a background color even if transparent is set.
	 */
	private boolean forceBackgroundColor = false;

	/**
	 * get force background color.
	 * 
	 * @return true if force background color.
	 */
	public boolean getForceBackgroundColor() {
		return forceBackgroundColor;
	}

	/**
	 * set force background color.
	 * 
	 * @param forceBackgroundColor to set
	 */
	public void setForceBackgroundColor(final boolean forceBackgroundColor) {
		this.forceBackgroundColor = forceBackgroundColor;
	}

	/**
	 * Main panel.
	 */
	@SuppressWarnings("VisibilityModifier")
	@UiField
	SimpleLayoutPanel pogMainPanel;

	/**
	 * Panel to draw in.
	 */
	@SuppressWarnings("VisibilityModifier")
	@UiField
	AbsolutePanel pogDrawPanel;

	/**
	 * Constructor.
	 */
	public PogCanvas() {
		initWidget(uiBinder.createAndBindUi(this));
		pogData = (PogData) JavaScriptObject.createObject().cast();
		createContent();
	}

	/**
	 * Constructor.
	 * 
	 * @param pogData data for pog
	 */
	public PogCanvas(final PogData pogData) {
		initWidget(uiBinder.createAndBindUi(this));
		createContent();
		setupWithPogData(pogData);
	}

	/**
	 * Create content.
	 */
	private void createContent() {
		LayoutPanel hidePanel = new LayoutPanel();
		hidePanel.setVisible(false);
		hidePanel.add(image);
		pogDrawPanel.add(hidePanel, -1, -1);
		pogDrawPanel.add(canvas, 0, 0);
		setupEventHandling();
		getElement().setDraggable(Element.DRAGGABLE_TRUE);
	}

	/**
	 * Setup event handlers.
	 */
	private void setupEventHandling() {
		canvas.addMouseDownHandler(this);
		addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(final DragStartEvent event) {
				if (ServiceManager.getDungeonManager().getFowToggle()) {
					event.preventDefault();
					return;
				}
				ServiceManager.getDungeonManager().setPogBeingDragged(pogData, fromRibbonBar);
				event.getDataTransfer().setDragImage(canvas.getElement(), 0, 0);
			}
		});
		addDragLeaveHandler(new DragLeaveHandler() {
			@Override
			public void onDragLeave(final DragLeaveEvent event) {
				event.preventDefault();
			}
		});
		image.addLoadHandler(new LoadHandler() {
			public void onLoad(final LoadEvent event) {
				setImage();
			}
		});
		pogDrawPanel.addDomHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(final MouseUpEvent event) {
				if (ServiceManager.getDungeonManager().getFowToggle()) {
					ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.MouseUpEventBubble, event));
				}
			}
		}, MouseUpEvent.getType());
		pogDrawPanel.addDomHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(final MouseMoveEvent event) {
				if (ServiceManager.getDungeonManager().getFowToggle()) {
					ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.MouseMoveEventBubble, event));
				}
			}
		}, MouseMoveEvent.getType());
	}

	/**
	 * Setup view with this data.
	 * 
	 * @param pogData data to use.
	 */
	private void setupWithPogData(final PogData pogData) {
		this.pogData = pogData;
		forceBackgroundColor =  ServiceManager.getDungeonManager().isEditMode();
		String backgroundColor = getBackgroundColor(pogData);
		pogDrawPanel.getElement().getStyle().setBackgroundColor(backgroundColor);
		backCanvas.getElement().getStyle().setBackgroundColor(backgroundColor);
		canvas.getElement().getStyle().setBackgroundColor(backgroundColor);
		if (pogData.getImageUrl() != "") {
			setPogImageUrl(pogData.getImageUrl());
		} else {
			showImage = false;
		}
		pogMainPanel.setTitle(pogData.getName());
	}

	/**
	 * Get proper background color.
	 * 
	 * @param pogData with DM bits
	 * @return color
	 */
	private String getBackgroundColor(final PogData pogData) {
		String color = "white";
		if (forceBackgroundColor) {
			if (pogData.isFlagSet(DungeonMasterFlag.DARK_BACKGROUND)) {
				color = "black";
			}
		} else if (pogData.isFlagSet(DungeonMasterFlag.TRANSPARENT_BACKGROUND)) {
			color = "transparent";
		}
		return color;
	}

	/**
	 * {@inheritDoc}
	 */
	public HandlerRegistration addDragStartHandler(final DragStartHandler handler) {
		return addBitlessDomHandler(handler, DragStartEvent.getType());
	}

	/**
	 * {@inheritDoc}
	 */
	public HandlerRegistration addDragLeaveHandler(final DragLeaveHandler handler) {
		return addBitlessDomHandler(handler, DragLeaveEvent.getType());
	}

	/**
	 * Set image URL.
	 */
	public final void setImage() {
		this.imageElement = (ImageElement) image.getElement().cast();
		imageLoaded = true;
		drawEverything();
	}

	/**
	 * Adjust canvas based on size.
	 */
	public final void adjustCanvases() {
		parentWidth = pogMainPanel.getOffsetWidth();
		parentHeight = pogMainPanel.getOffsetHeight();
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();
		sizeACanvas(canvas);
		sizeACanvas(backCanvas);
		calculateZoom();
		backContext.setTransform(totalZoom, 0, 0, totalZoom, 0, 0);
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
	 * Should we scale by width.
	 * 
	 * @return true if we should scale by width
	 */
	private boolean isScaleByWidth() {
		double scaleWidth = (double) parentWidth / (double) imageWidth;
		double scaleHeight = (double) parentHeight / (double) imageHeight;
		return scaleWidth < scaleHeight;
	}

	/**
	 * Calculate the starting zoom factor so that one side of the image exactly fills the parent.
	 */
	private void calculateZoom() {
		if (isScaleByWidth()) {
			totalZoom = (double) parentWidth / (double) imageWidth;
		} else {
			totalZoom = (double) parentHeight / (double) imageHeight;
		}
	}

	/**
	 * Set pog name.
	 * 
	 * @param nameoFPog name of pog.
	 */
	public void setPogName(final String nameoFPog) {
		pogData.setName(nameoFPog);
		pogMainPanel.setTitle(pogData.getName());
	}

	/**
	 * Get pog name.
	 * 
	 * @return pog name
	 */
	public String getPogName() {
		return (pogData.getName());
	}

	/**
	 * Set position of pog in grid.
	 * 
	 * @param column to use
	 * @param row to use
	 */
	public void setPogPosition(final int column, final int row) {
		pogData.setColumn(column);
		pogData.setRow(row);
	}

	/**
	 * get column.
	 * 
	 * @return column
	 */
	public int getPogColumn() {
		return (pogData.getColumn());
	}

	/**
	 * get row.
	 * 
	 * @return row
	 */
	public int getPogRow() {
		return (pogData.getRow());
	}

	/**
	 * Set dungeon level the pog is on.
	 * 
	 * @param level in dungeon
	 */
	public void setPogDungeonLevel(final int level) {
		pogData.setDungeonLevel(level);
	}

	/**
	 * Set width of pog.
	 * 
	 * @param width of pog
	 * @param borderSize size of border
	 * @param zoomFactor zoom factor
	 */
	public void setPogSizing(final double width, final double borderSize, final double zoomFactor) {
		scaledWidth = width;
		this.zoomFactor = zoomFactor;
		if (!showNormalSizeOnly) {
			scaledWidth *= pogData.getSize();
		}
		scaledWidth -= 2 * borderSize;
		pogMainPanel.setWidth(scaledWidth + "px");
		pogMainPanel.setHeight(scaledWidth + "px");
		drawEverything();
	}

	/**
	 * Set URL for image. This will append a number to force the loading of picture from server to bypass caching.
	 * 
	 * @param pogImageUrl URL for image
	 */
	public void setPogImageUrl(final String pogImageUrl) {
		imageLoaded = false;
		pogData.setImageUrl(pogImageUrl);
		String imageUrl;
		if (pogImageUrl.contains("?")) {
			imageUrl = pogImageUrl + "," + imageCount++;
		} else {
			imageUrl = pogImageUrl + "?" + imageCount++;
		}
		image.setUrl(imageUrl);
	}

	/**
	 * refresh view.
	 */
	public void drawEverything() {
		if (!imageLoaded) {
			return;
		}
		adjustCanvases();
		backContext.clearRect(CLEAR_OFFEST, CLEAR_OFFEST, imageWidth, imageHeight);
		backContext.setTransform(totalZoom, 0, 0, totalZoom, 0, 0);
		if (showImage) {
			backContext.drawImage(imageElement, 0, 0);
		}
		handleAllDrawing();
	}

	/**
	 * Handle all canvas drawing.
	 */
	public final void handleAllDrawing() {
		if (!pogData.isFlagSet(DungeonMasterFlag.TRANSPARENT_BACKGROUND)) {
			context.setFillStyle("white");
			context.fillRect(0, 0, parentWidth, parentHeight);
		}
		double opacity = 1.0;
		if (isInVisibleToPlayer()) {
			opacity = ServiceManager.getDungeonManager().isDungeonMaster() ? 0.5 : 0;
		}
		pogDrawPanel.getElement().getStyle().setOpacity(opacity);
		context.drawImage(backContext.getCanvas(), 0, 0);
		drawOverlays();
	}

	/**
	 * Is pog currently invisible to player.
	 * 
	 * @return true if is
	 */
	public boolean isInVisibleToPlayer() {
		return (!showNormalSizeOnly && (pogData.isFlagSet(PlayerFlag.INVISIBLE) || pogData.isFlagSet(DungeonMasterFlag.INVISIBLE_FROM_PLAYER)));
	}

	/**
	 * Draw overlays on canvas over picture.
	 */
	private void drawOverlays() {
		if (showNormalSizeOnly) {
			return;
		}
		if (pogData.isFlagSet(PlayerFlag.DEAD)) {
			addDeadOverlay();
		}
	}

	/**
	 * Add dead pog overlay.
	 */
	private void addDeadOverlay() {
		double halfWidth = scaledWidth / 2;
		context.setStrokeStyle(CssColor.make(255, 0, 0));
		context.setFillStyle(CssColor.make(255, 0, 0));
		double lineWidth = zoomFactor * 3;
		if (lineWidth < 1) {
			lineWidth = 1.0;
		}
		context.setLineWidth(lineWidth);
		context.beginPath();
		context.arc(halfWidth, halfWidth, halfWidth, 0, 2 * Math.PI);
		context.moveTo(scaledWidth, 0);
		context.lineTo(0, scaledWidth);
		context.stroke();
	}

	/**
	 * Show the image if true.
	 * 
	 * @param showing true if image should be shown
	 */
	public void showImage(final boolean showing) {
		showImage = showing;
		drawEverything();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onMouseDown(final MouseDownEvent event) {
		if (ServiceManager.getDungeonManager().getFowToggle()) {
			ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.MouseDownEventBubble, event));
		} else {
			ServiceManager.getDungeonManager().setSelectedPog(pogData);
		}
	}
}
