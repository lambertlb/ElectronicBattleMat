package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class DungeonSessionData extends JavaScriptObject {
	protected DungeonSessionData() {
	}

	public final native String getSessionName() /*-{
		if (this.sessionName === undefined) {
			this.sessionName = "";
		}
		return (this.sessionName);
	}-*/;

	public final native void setSessionName(String sessionName) /*-{
		this.sessionName = sessionName;
	}-*/;

	public final native String getTemplateDungeon() /*-{
		if (this.templateDungeon === undefined) {
			this.templateDungeon = "";
		}
		return (this.templateDungeon);
	}-*/;

	public final native void setTemplateDungeon(String templateDungeon) /*-{
		this.templateDungeon = templateDungeon;
	}-*/;

	public final native DungeonSessionLevel[] getSessionlevels() /*-{
		if (this.sessionLevels === undefined) {
			sessionLevels = new DungeonSessionLevel[0];
		}
		return this.sessionLevels;
	}-*/;
}
