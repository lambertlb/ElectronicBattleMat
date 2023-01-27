package per.lambert.ebattleMat.client.controls;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;

/**
 * NumberSpinner Custom Control.
 * 
 * @author Pavan Andhukuri
 * 
 */
public class NumberSpinner extends Composite {

	/**
	 * Rate of change.
	 */
	private int rateOfChange = 1;
	/**
	 * Maximum number.
	 */
	private int maxNumber;
	/**
	 * Minimum number.
	 */
	private int minNumber;
	/**
	 * integer holder.
	 */
	private IntegerBox integerBox;

	public NumberSpinner() {
		this(1, 0, 1000);
	}

	public NumberSpinner(final int defaultValue, final int min, final int max) {
		maxNumber = max;
		minNumber = min;
		AbsolutePanel absolutePanel = new AbsolutePanel();
		initWidget(absolutePanel);
		absolutePanel.setSize("110px", "30px");

		integerBox = new IntegerBox();
		absolutePanel.add(integerBox, 0, 0);
		integerBox.setSize("30px", "20px");
		integerBox.setValue(defaultValue);

		Button upButton = new Button();
		upButton.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				if (getValue() >= maxNumber) {
					return;
				}
				setValue(getValue() + rateOfChange);
				ValueChangeEvent.fire(integerBox, getValue());
			}
		});
		absolutePanel.add(upButton, 34, 0);
		upButton.setText("\u2191");
		upButton.getElement().getStyle().setFontWeight(FontWeight.BOLD);

		Button downButton = new Button();
		downButton.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				if (getValue() <= minNumber) {
					return;
				}
				setValue(getValue() - rateOfChange);
				ValueChangeEvent.fire(integerBox, getValue());
			}
		});
		absolutePanel.add(downButton, 55, 0);
		downButton.setText("\u2193");
		downButton.getElement().getStyle().setFontWeight(FontWeight.BOLD);
	}

	/**
	 * Returns the value being held.
	 * 
	 * @return current value
	 */
	public int getValue() {
		return integerBox.getValue() == null ? 0 : integerBox.getValue();
	}

	/**
	 * Sets the value to the control.
	 * 
	 * @param value Value to be set
	 */
	public void setValue(final int value) {
		integerBox.setValue(value);
	}

	/**
	 * Sets the rate at which increment or decrement is done.
	 * 
	 * @param rate
	 */
	public void setRate(final int rate) {
		this.rateOfChange = rate;
	}

	/**
	 * Add a change handler.
	 * 
	 * @param changeHandler to trigger
	 */
	public void addChangeHandler(final ValueChangeHandler<Integer> changeHandler) {
		integerBox.addValueChangeHandler(changeHandler);
	}
}