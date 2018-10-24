package per.lambert.ebattleMat.client.controls.scalablePog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.controls.DraggableLabel.DraggableLabel;
import per.lambert.ebattleMat.client.services.serviceData.PogData;

public class ScalablePog extends Composite {

	private static ScalablePogUiBinder uiBinder = GWT.create(ScalablePogUiBinder.class);

	private PogData pogData;
	private int pogSize;

	interface ScalablePogUiBinder extends UiBinder<Widget, ScalablePog> {
	}

	public ScalablePog() {
		initWidget(uiBinder.createAndBindUi(this));
		pogData = (PogData) JavaScriptObject.createObject().cast();
	}

	@UiField
	FlowPanel pogPanel;

	@UiField
	DraggableLabel pogName;

	public ScalablePog(PogData pogData) {
		initWidget(uiBinder.createAndBindUi(this));
		this.pogData = pogData;
		pogName.setLabelText(pogData.getPogName());
	}

	public void setPogName(final String nameoFPog) {
		pogData.setPogName(nameoFPog);
		pogName.setLabelText(nameoFPog);
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

	public void setScale(double scale) {
		pogPanel.getElement().getStyle().setProperty("transform", "scale(" + scale + ")");
	}

	public void setPogSize(int size) {
		this.pogSize = size;
		pogPanel.setWidth(size + "px");
		pogPanel.setHeight(size + "px");
	}
}
