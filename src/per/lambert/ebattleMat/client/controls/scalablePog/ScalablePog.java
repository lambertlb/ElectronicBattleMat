package per.lambert.ebattleMat.client.controls.scalablePog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import per.lambert.ebattleMat.client.controls.DraggableLabel.DraggableLabel;

public class ScalablePog extends Composite {

	private static ScalablePogUiBinder uiBinder = GWT.create(ScalablePogUiBinder.class);

	interface ScalablePogUiBinder extends UiBinder<Widget, ScalablePog> {
	}

	public ScalablePog() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField
	FlowPanel pogPanel;
	
	@UiField
	DraggableLabel pogName;

	public ScalablePog(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setPogName(final String nameoFPog) {
		pogName.setLabelText(nameoFPog);
	}
	
	public void setScale(double scale) {
		pogPanel.getElement().getStyle().setProperty("transform", "scale(" + scale + ")");
	}
}
