package per.lambert.ebattleMat.client.battleMatDisplay;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
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
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
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

public class PogCanvas extends Composite implements HasDragStartHandlers, MouseDownHandler, TouchStartHandler, TouchMoveHandler, TouchEndHandler {

	private static ScalablePogUiBinder uiBinder = GWT.create(ScalablePogUiBinder.class);

	interface ScalablePogUiBinder extends UiBinder<Widget, PogCanvas> {
	}

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
	private Image image = new Image();
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
	private PogData pogData;

	public PogData getPogData() {
		return pogData;
	}

	public void setPogData(PogData pogData) {
		setupWithPogData(pogData);
	}

	private boolean isPlayer;

	public int getPogSize() {
		return pogData.getPogSize();
	}

	public void setPogSize(int pogSize) {
		pogData.setPogSize(pogSize);
	}

	private boolean showImage = true;
	private boolean imageLoaded = false;

	@UiField
	SimpleLayoutPanel pogMainPanel;

	@UiField
	AbsolutePanel pogDrawPanel;

	public PogCanvas() {
		initWidget(uiBinder.createAndBindUi(this));
		pogData = (PogData) JavaScriptObject.createObject().cast();
		initialize();
	}

	public PogCanvas(PogData pogData) {
		initWidget(uiBinder.createAndBindUi(this));
		initialize();
		setupWithPogData(pogData);
	}

	private void setupWithPogData(PogData pogData) {
		this.pogData = pogData;
		isPlayer = pogData.isThisAPlayer();
		if (pogData.getPogImageUrl() != "") {
			setPogImageUrl(pogData.getPogImageUrl());
		}
		if (pogData.getPogName() != "") {
			pogMainPanel.setTitle(pogData.getPogName());
		}
	}

	private void initialize() {
		LayoutPanel hidePanel = new LayoutPanel();
		hidePanel.setVisible(false);
		hidePanel.add(image);
		pogDrawPanel.add(hidePanel, -1, -1);
		pogDrawPanel.add(canvas, 0, 0);
		// pogDrawPanel.getElement().getStyle().setBackgroundColor("transparent");
		// backCanvas.getElement().getStyle().setBackgroundColor("transparent");
		// canvas.getElement().getStyle().setBackgroundColor("transparent");
		setupEventHandling();
		getElement().setDraggable(Element.DRAGGABLE_TRUE);
		addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				boolean isDM = ServiceManager.getDungeonManager().isDungeonMaster();
				if (ServiceManager.getDungeonManager().getFowToggle() || (!isDM && !isPlayer)) {
					event.preventDefault();
					return;
				}
				ServiceManager.getDungeonManager().setPogBeingDragged(pogData);
				event.getDataTransfer().setDragImage(pogMainPanel.getElement(), 10, 120);
			}
		});
		addDragLeaveHandler(new DragLeaveHandler() {
			@Override
			public void onDragLeave(DragLeaveEvent event) {
				event.preventDefault();
			}
		});
		canvas.addMouseDownHandler(this);
	}

	private void setupEventHandling() {
		image.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				setImage();
			}
		});
		pogDrawPanel.addDomHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				if (ServiceManager.getDungeonManager().getFowToggle()) {
					ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.MouseUpEventBubble, event));
				}
			}
		}, MouseUpEvent.getType());
		pogDrawPanel.addDomHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				if (ServiceManager.getDungeonManager().getFowToggle()) {
					ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.MouseMoveEventBubble, event));
				}
			}
		}, MouseMoveEvent.getType());
	}

	public HandlerRegistration addDragStartHandler(DragStartHandler handler) {
		return addBitlessDomHandler(handler, DragStartEvent.getType());
	}

	public HandlerRegistration addDragLeaveHandler(DragLeaveHandler handler) {
		return addBitlessDomHandler(handler, DragLeaveEvent.getType());
	}

	public final void setImage() {
		this.imageElement = (ImageElement) image.getElement().cast();
		imageLoaded = true;
		mainDraw();
	}

	public final void adjustCanvases() {
		parentWidth = pogMainPanel.getOffsetWidth();
		parentHeight = pogMainPanel.getOffsetHeight();
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
		calculateZoom();
		backContext.setTransform(totalZoom, 0, 0, totalZoom, 0, 0);
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

	public void setPogName(final String nameoFPog) {
		pogData.setPogName(nameoFPog);
		pogMainPanel.setTitle(pogData.getPogName());
	}

	public String getPogName() {
		return (pogData.getPogName());
	}

	public void setPogPosition(int column, int row) {
		pogData.setPogColumn(column);
		pogData.setPogRow(row);
	}

	public int getPogColumn() {
		return (pogData.getPogColumn());
	}

	public int getPogRow() {
		return (pogData.getPogRow());
	}

	public void setPogWidth(int width) {
		int scaledWidth = width * pogData.getPogSize();
		pogMainPanel.setWidth(scaledWidth + "px");
		pogMainPanel.setHeight(scaledWidth + "px");
		mainDraw();
	}

	private int imageCount = 1;

	public void setPogImageUrl(String pogImageUrl) {
		imageLoaded = false;
		pogData.setPogImageUrl(pogImageUrl);
		String imageUrl;
		if (pogImageUrl.contains("?")) {
			imageUrl = pogImageUrl + "," + imageCount++;
		} else {
			imageUrl = pogImageUrl + "?" + imageCount++;
		}
		image.setUrl(imageUrl);
	}

	/**
	 * Offset for clearing rectangle.
	 */
	private static final int CLEAR_OFFEST = -10;

	public void mainDraw() {
		if (!imageLoaded) {
			return;
		}
		adjustCanvases();
		backContext.clearRect(CLEAR_OFFEST, CLEAR_OFFEST, imageWidth, imageHeight);
		backContext.setTransform(totalZoom, 0, 0, totalZoom, 0, 0);
		if (showImage) {
			backContext.drawImage(imageElement, 0, 0);
		}
		buffer(backContext, context);
	}

	public final void buffer(final Context2d back, final Context2d front) {
		if (!pogData.isFlagSet(DungeonMasterFlag.TRANSPARENT_BACKGROUND)) {
			front.setFillStyle("white");
			front.fillRect(0, 0, parentWidth, parentHeight);
		}
		double opacity = 1.0;
		if (!ServiceManager.getDungeonManager().isEditMode() && (pogData.isFlagSet(PlayerFlag.INVISIBLE) || pogData.isFlagSet(DungeonMasterFlag.INVISIBLE_FROM_PLAYER))) {
			opacity = ServiceManager.getDungeonManager().isDungeonMaster() ? 0.5 : 0;
		}
		pogDrawPanel.getElement().getStyle().setOpacity(opacity);
		front.drawImage(back.getCanvas(), 0, 0);
	}

	public void showImage(boolean showing) {
		showImage = showing;
		mainDraw();
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		if (ServiceManager.getDungeonManager().getFowToggle()) {
			ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(ReasonForAction.MouseDownEventBubble, event));
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
