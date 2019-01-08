package per.lambert.ebattleMat.client.controls.labeledTextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LabeledTextBox extends Composite {

	private static LabeledTextBoxUiBinder uiBinder = GWT.create(LabeledTextBoxUiBinder.class);

	interface LabeledTextBoxUiBinder extends UiBinder<Widget, LabeledTextBox> {
	}

	private boolean asDouble;

	public LabeledTextBox(String labelText, String editText) {
		initWidget(uiBinder.createAndBindUi(this));
		asDouble = false;
		setLabelText(labelText);
	}

	public LabeledTextBox(String labelText, Double vale) {
		initWidget(uiBinder.createAndBindUi(this));
		asDouble = true;
		setLabelText(labelText);
	}

	@UiField
	Label textBoxLabel;

	@UiField
	TextBox textBox;
	@UiField
	DoubleBox doubleBox;

	@Override
	protected void onLoad() {
		super.onLoad();
		if (asDouble) {
			textBox.setVisible(false);
			doubleBox.setVisible(true);
		} else {
			textBox.setVisible(true);
			doubleBox.setVisible(false);
		}
	}

	public void setLabelText(String labelText) {
		textBoxLabel.setText(labelText);
	}

	public void setValue(String value) {
		textBox.setValue(value);
	}

	public void setValue(Double value) {
		doubleBox.setValue(value);
	}

	public String getTextValue() {
		return (textBox.getValue());
	}

	public Double getDoubleValue() {
		return (doubleBox.getValue());
	}

	public void addChangeHandler(ChangeHandler changeHandler) {
		if (asDouble) {
			doubleBox.addChangeHandler(changeHandler);
		} else {
			textBox.addChangeHandler(changeHandler);
		}
	}

	public void addKeyUpHandler(KeyUpHandler keyUpHandler) {
		if (asDouble) {
			doubleBox.addKeyUpHandler(keyUpHandler);
		} else {
			textBox.addKeyUpHandler(keyUpHandler);
		}
	}
}
