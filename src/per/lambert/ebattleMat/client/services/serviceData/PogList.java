package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * List of pogs.
 * @author LLambert
 *
 */
public class PogList extends JavaScriptObject {
	/**
	 * Constructor.
	 */
	protected PogList() {
	}

	/**
	 * List of pogs.
	 * @return list of pogs.
	 */
	public final native PogData[] getPogList() /*-{
		if (this.pogList === undefined) {
			this.pogList = [];
		}
		return this.pogList;
	}-*/;

	/**
	 * Add pog to list.
	 * @param pogToAdd pog to add
	 */
	public final native void addPog(PogData pogToAdd) /*-{
		if (this.pogList === undefined) {
			this.pogList = [];
		}
		this.pogList.push(pogToAdd);
	}-*/;
}
