package per.lambert.ebattleMat.client.controls;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class FileUpLoadControl extends FormPanel {
	private HorizontalPanel uploader;
	private FileUpload fileUpload;
	private Label fileUploadLabel;

	public FileUpLoadControl(String caption) {
		createContent(caption);
	}

	private void createContent(String caption) {
		uploader = new HorizontalPanel();
		fileUpload = new FileUpload();
		fileUpload.setName("uploadFormElement");
		fileUpload.setStyleName("ribbonBarLabel");
		fileUploadLabel = new Label(caption);
		fileUploadLabel.setStyleName("ribbonBarLabel");
		uploader.add(fileUploadLabel);
		uploader.add(fileUpload);
		setBadInput(false);
		setWidget(uploader);
	}

	public void setInput(String value) {
		Element ele = fileUpload.getElement();
		InputElement inp = InputElement.as(ele);
		inp.setValue("");
	}

	public String getFilename() {
		return (fileUpload.getFilename());
	}

	public void setBadInput(boolean isBad) {
		if (isBad) {
			fileUpload.addStyleName("badLabel");
		} else {
			fileUpload.removeStyleName("badLabel");
		}
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return (fileUpload.addChangeHandler(handler));
	}
}
