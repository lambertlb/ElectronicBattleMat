package per.lambert.ebattleMat.client.controls.scalablePog;

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
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.services.ServiceManagement;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

public class ScalablePog extends Composite implements HasDragStartHandlers {

	private static ScalablePogUiBinder uiBinder = GWT.create(ScalablePogUiBinder.class);

	interface ScalablePogUiBinder extends UiBinder<Widget, ScalablePog> {
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

	public int getPogSize() {
		return pogData.getPogSize();
	}

	public void setPogSize(int pogSize) {
		pogData.setPogSize(pogSize);
	}

	private boolean imageLoaded = false;

	@UiField
	SimpleLayoutPanel pogMainPanel;

	@UiField
	AbsolutePanel pogDrawPanel;

	public ScalablePog() {
		initWidget(uiBinder.createAndBindUi(this));
		pogData = (PogData) JavaScriptObject.createObject().cast();
		initialize();
	}

	public ScalablePog(PogData pogData) {
		initWidget(uiBinder.createAndBindUi(this));
		this.pogData = pogData;
		initialize();
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
		setupEventHandling();
		getElement().setDraggable(Element.DRAGGABLE_TRUE);
		addDragStartHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				ServiceManagement.getDungeonManagment().setPogBeingDragged(pogData);
				event.getDataTransfer().setDragImage(pogMainPanel.getElement(), 10, 120);
			}
		});
		addDragLeaveHandler(new DragLeaveHandler() {
			@Override
			public void onDragLeave(DragLeaveEvent event) {
				event.preventDefault();
				event.stopPropagation();
			}
		});
	}

	private void setupEventHandling() {
		image.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				setImage();
			}
		});
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
	 * Calculate the starting zoom factor so that one side of the image exactly
	 * fills the parent.
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
		int scaledWidth = (width - 2) * pogData.getPogSize();
		pogMainPanel.setWidth(scaledWidth + "px");
		pogMainPanel.setHeight(scaledWidth + "px");
		mainDraw();
	}

	private int imageCount = 1;

	public void setPogImageUrl(String pogImageUrl) {
		pogData.setPogImageUrl(pogImageUrl);
		String imageUrl = pogImageUrl + "?" + imageCount++;
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
		backContext.drawImage(imageElement, 0, 0);
		buffer(backContext, context);
	}

	public final void buffer(final Context2d back, final Context2d front) {
		front.setFillStyle("white");
		front.fillRect(0, 0, parentWidth, parentHeight);
		front.drawImage(back.getCanvas(), 0, 0);
	}
}
