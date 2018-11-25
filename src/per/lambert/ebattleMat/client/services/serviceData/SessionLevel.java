package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class SessionLevel extends JavaScriptObject {
	protected SessionLevel() {
	}

	public final native boolean[][] getFOW() /*-{
		if (this.fogOfWar === undefined) {
			this.fogOfWar = [[]];
		}
		return (this.fogOfWar);
	}-*/;

	public final native PogData[] getPlayers() /*-{
		if (this.players === undefined) {
			return (new PogData[0]);
		}
		return (this.players);
	}-*/;

	public final native PogData[] getMonsters() /*-{
		if (this.monsters === undefined) {
			return (new PogData[0]);
		}
		return (this.monsters);
	}-*/;

	public final native PogData[] getRoomObjects() /*-{
		if (this.roomObjects === undefined) {
			return (new PogData[0]);
		}
		return (this.roomObjects);
	}-*/;
}
