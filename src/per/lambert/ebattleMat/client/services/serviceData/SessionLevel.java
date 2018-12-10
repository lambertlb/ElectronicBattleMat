package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class SessionLevel extends JavaScriptObject {
	protected SessionLevel() {
	}

	public final native boolean[][] getFOW() /*-{
		if (this.fogOfWar === undefined) {
			this.fogOfWar = [ [] ];
		}
		return (this.fogOfWar);
	}-*/;

	public final native PogDataLite[] getPlayers() /*-{
		if (this.players === undefined) {
			return (new PogDataLite[0]);
		}
		return (this.players);
	}-*/;

	public final native PogDataLite[] getMonsters() /*-{
		if (this.monsters === undefined) {
			return (new PogDataLite[0]);
		}
		return (this.monsters);
	}-*/;

	public final native void addMonsters(PogDataLite monster) /*-{
		if (this.monsters === undefined) {
			this.monsters = new PogDataLite[0];
		}
		this.monsters.push(monster);
	}-*/;

	public final native PogDataLite[] getRoomObjects() /*-{
		if (this.roomObjects === undefined) {
			return (new PogDataLite[0]);
		}
		return (this.roomObjects);
	}-*/;
}
