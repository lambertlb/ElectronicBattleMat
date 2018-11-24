package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

public class SessionData extends JavaScriptObject {

	protected SessionData() {
	}

	public final native String getTemplateDungeonName() /*-{
		if (this.templateDungeonName === undefined) {
			this.templateDungeonName = "";
		}
		return (this.templateDungeonName);
	}-*/;

	public final native void setTemplateDungeonName(String templateDungeonName) /*-{
		this.templateDungeonName = templateDungeonName;
	}-*/;

	public final native SessionLevel[] getSessionLevels() /*-{
		if (this.sessionLevels === undefined) {
			sessionLevels = null;
		}
		return this.sessionLevels;
	}-*/;

	public final native boolean getIsDirty() /*-{
		if (this.isDirty === undefined) {
			this.isDirty = false;
		}
		return this.isDirty;
	}-*/;

}
