package per.lambert.ebattleMat.client.controls;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Control to handl uploading files.
 * 
 * @author LLambert
 *
 */
public class FileUpLoadControl extends FormPanel {
	/**
	 * Panel for uploading.
	 */
	private HorizontalPanel uploader;
	/**
	 * Up load widget.
	 */
	private FileUpload fileUpload;
	/**
	 * Label for control.
	 */
	private Label fileUploadLabel;

	/**
	 * Constructor for upload control.
	 * 
	 * @param caption for control
	 */
	public FileUpLoadControl(final String caption) {
		createContent(caption);
	}

	/**
	 * Create content of control.
	 * 
	 * @param caption for control
	 */
	private void createContent(final String caption) {
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

	/**
	 * Set value of input control.
	 * 
	 * @param value to set
	 */
	public void setInput(final String value) {
		Element ele = fileUpload.getElement();
		InputElement inp = InputElement.as(ele);
		inp.setValue(value);
	}

	/**
	 * get file name selected in input.
	 * 
	 * @return file name
	 */
	public String getFilename() {
		return (fileUpload.getFilename());
	}

	/**
	 * set input as bad.
	 * 
	 * @param isBad if true
	 */
	public void setBadInput(final boolean isBad) {
		if (isBad) {
			fileUpload.addStyleName("badLabel");
		} else {
			fileUpload.removeStyleName("badLabel");
		}
	}

	/**
	 * Add change handler.
	 * 
	 * @param handler to add
	 * @return handle registration
	 */
	public HandlerRegistration addChangeHandler(final ChangeHandler handler) {
		return (fileUpload.addChangeHandler(handler));
	}
}
