package per.lambert.ebattleMat.client.controls.RibbonBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CharacterSelect extends Composite {

	private static CharacterSelectUiBinder uiBinder = GWT.create(CharacterSelectUiBinder.class);

	interface CharacterSelectUiBinder extends UiBinder<Widget, CharacterSelect> {
	}

	public CharacterSelect() {
		initWidget(uiBinder.createAndBindUi(this));
		panel.getElement().getStyle().setBackgroundColor("grey");
	}
	@UiField
	VerticalPanel panel;
}
