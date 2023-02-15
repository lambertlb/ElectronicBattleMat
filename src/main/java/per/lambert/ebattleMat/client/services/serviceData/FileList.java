package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Java script class with response data from file lister.
 * 
 * @author llambert
 *
 */
public class FileList extends JavaScriptObject {
	/**
	 * Constructor.
	 */
	protected FileList() {
	}
	/**
	 * Get path of files.
	 * 
	 * @return path of files
	 */
	public final native String getFilePath() /*-{
		if (this.filePath === undefined) {
			this.filePath = "";
		}
		return (this.filePath);
	}-*/;
	/**
	 * get File names.
	 * 
	 * @return file names
	 */
	public final native String[] getFileNames() /*-{
		if (this.fileNames === undefined) {
			this.fileNames = [];
		}
		return (this.fileNames);
	}-*/;

}
