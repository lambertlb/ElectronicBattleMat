package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonSessionLevel extends JavaScriptObject {
	protected DungeonSessionLevel() {
	}

	public final native PogData[] getPCPogs() /*-{
		if (this.pcPogs === undefined) {
			return (new PogData[0]);
		}
		return (this.pcPogs);
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
