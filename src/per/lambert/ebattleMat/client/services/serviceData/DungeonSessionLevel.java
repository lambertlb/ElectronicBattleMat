package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonSessionLevel extends JavaScriptObject {
	protected DungeonSessionLevel() {
	}

	public final native PogDataLite[] getPCPogs() /*-{
		if (this.pcPogs === undefined) {
			return (new PogDataLite[0]);
		}
		return (this.pcPogs);
	}-*/;

	public final native PogDataLite[] getMonsters() /*-{
		if (this.monsters === undefined) {
			return (new PogDataLite[0]);
		}
		return (this.monsters);
	}-*/;

	public final native PogDataLite[] getRoomObjects() /*-{
		if (this.roomObjects === undefined) {
			return (new PogDataLite[0]);
		}
		return (this.roomObjects);
	}-*/;
}
