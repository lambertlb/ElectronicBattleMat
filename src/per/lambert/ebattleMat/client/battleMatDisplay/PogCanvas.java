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
 * This is used to display the picture for a Pog.
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
		setupWithPogData(pogData);
	}

	/**
	 * Get size of pog in pixels.
	 * 
	 * @return size of pog in pixels.
	 */
	public int getPogSize() {
		return pogData.getPogSize();
	}

	/**
	 * Set size of pixel.
	 * 
	 * @param pogSize size of pixel.
	 */
	public void setPogSize(final int pogSize) {
		pogData.setPogSize(pogSize);
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
	 * @param pogData data for pog
	 */
	public PogCanvas(final PogData pogData) {
		initWidget(uiBinder.createAndBindUi(this));
		createContent();
		setupWithPogData(pogData);
	}

	/**
	 * Setup view with this data.
	 * @param pogData data to use.
	 */
	private void setupWithPogData(final PogData pogData) {
		this.pogData = pogData;
		if (pogData.isFlagSet(DungeonMasterFlag.TRANSPARENT_BACKGROUND)) {
			 pogDrawPanel.getElement().getStyle().setBackgroundColor("transparent");
			 backCanvas.getElement().getStyle().setBackgroundColor("transparent");
			 canvas.getElement().getStyle().setBackgroundColor("transparent");
		} else {
			 pogDrawPanel.getElement().getStyle().setBackgroundColor("white");
			 backCanvas.getElement().getStyle().setBackgroundColor("white");
			 canvas.getElement().getStyle().setBackgroundColor("white");
		}
		if (pogData.getPogImageUrl() != "") {
			setPogImageUrl(pogData.getPogImageUrl());
		}
		if (pogData.getPogName() != "") {
			pogMainPanel.setTitle(pogData.getPogName());
		}
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
		addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(final DragStartEvent event) {
				boolean isDM = ServiceManager.getDungeonManager().isDungeonMaster();
				if (ServiceManager.getDungeonManager().getFowToggle() || (!isDM && !pogData.isThisAPlayer())) {
					event.preventDefault();
					return;
				}
				ServiceManager.getDungeonManager().setPogBeingDragged(pogData);
				event.getDataTransfer().setDragImage(pogMainPanel.getElement(), 10, 120);
			}
		});
		addDragLeaveHandler(new DragLeaveHandler() {
			@Override
			public void onDragLeave(final DragLeaveEvent event) {
				event.preventDefault();
			}
		});
		canvas.addMouseDownHandler(this);
	}

	/**
	 * Setup event handlers.
	 */
	private void setupEventHandling() {
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

	/**
	 * Set pog name.
	 * @param nameoFPog name of pog.
	 */
	public void setPogName(final String nameoFPog) {
		pogData.setPogName(nameoFPog);
		pogMainPanel.setTitle(pogData.getPogName());
	}

	/**
	 * Get pog name.
	 * @return pog name
	 */
	public String getPogName() {
		return (pogData.getPogName());
	}

	/**
	 * Set position of pog in grid.
	 * @param column to use
	 * @param row to use
	 */
	public void setPogPosition(final int column, final int row) {
		pogData.setPogColumn(column);
		pogData.setPogRow(row);
	}

	/**
	 * Set dungeon level the pog is on.
	 * @param level in dungeon
	 */
	public void setPogDungeonLevel(final int level) {
		pogData.setDungeonLevel(level);
	}
	/**
	 * get column.
	 * @return column
	 */
	public int getPogColumn() {
		return (pogData.getPogColumn());
	}

	/**
	 * get row.
	 * @return row
	 */
	public int getPogRow() {
		return (pogData.getPogRow());
	}

	/**
	 * Set width of pog.
	 * @param width of pog
	 */
	public void setPogWidth(final int width) {
		int scaledWidth = width * pogData.getPogSize();
		pogMainPanel.setWidth(scaledWidth + "px");
		pogMainPanel.setHeight(scaledWidth + "px");
		drawEverything();
	}

	/**
	 * Image count.
	 */
	private int imageCount = 1;

	/**
	 * Set URL for image.
	 * @param pogImageUrl URL for image
	 */
	public void setPogImageUrl(final String pogImageUrl) {
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
		handleAllDrawing(backContext, context);
	}

	/**
	 * Handle all canvas drawing.
	 * @param back canvas.
	 * @param front canvas.
	 */
	public final void handleAllDrawing(final Context2d back, final Context2d front) {
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

	/**
	 * Show the image if true.
	 * @param showing trueif image should be shown
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
		}
	}
}
