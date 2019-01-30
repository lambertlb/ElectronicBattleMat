package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Fog of war data.
 * @author LLambert
 *
 */
public class FogOfWarData extends JavaScriptObject {
	/**
	 * Constructor for fog of war data.
	 */
	protected FogOfWarData() {
	}

	/**
	 * get fog of war.
	 * @return data for fog of war.
	 */
	public final native boolean[][] getFOW() /*-{
		if (this.fogOfWar === undefined) {
			return (null);
		}
		return (this.fogOfWar);
	}-*/;

	/**
	 * Set fog of war data.
	 * @param fogOfWar data.
	 */
	public final native void setFOW(boolean[][] fogOfWar) /*-{
		this.fogOfWar = fogOfWar;
	}-*/;
}
