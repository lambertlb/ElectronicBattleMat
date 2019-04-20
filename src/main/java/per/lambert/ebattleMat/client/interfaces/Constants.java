package per.lambert.ebattleMat.client.interfaces;

/**
 * Class to hold all constants for APP.
 * 
 * @author LLambert
 *
 */
public final class Constants {
	/**
	 * Location of dungeon data.
	 */
	public static final String DUNGEON_DATA_LOCATION = "dungeonData/";
	/**
	 * sub-folder with dungeons in it.
	 */
	public static final String DUNGEONS_FOLDER = "dungeons/";
	/**
	 * sub-folder with session data in it.
	 */
	public static final String SESSIONS_FOLDER = "/sessions/";
	/**
	 * full path to dungeons folder.
	 */
	public static final String DUNGEONS_LOCATION = DUNGEON_DATA_LOCATION + DUNGEONS_FOLDER;
	/**
	 * sub-folder for resources.
	 */
	public static final String DUNGEON_RESOURCE_LOCATION = "resources/";
	/**
	 * full path to resources folder.
	 */
	public static final String RESOURCE_LOCATION = DUNGEON_DATA_LOCATION + DUNGEON_RESOURCE_LOCATION;
	/**
	 * Folder name for monsters.
	 */
	public static final String MONSTER_FOLDER = "monsters/";
	/**
	 * sub-folder for monster resources.
	 */
	public static final String DUNGEON_MONSTER_LOCATION = DUNGEON_RESOURCE_LOCATION + MONSTER_FOLDER;
	/**
	 * Folder name for room objects.
	 */
	public static final String ROOM_OBJECT_FOLDER = "roomObjects/";
	/**
	 * sub-folder for room object resources.
	 */
	public static final String DUNGEON_ROOMOBJECT_LOCATION = DUNGEON_RESOURCE_LOCATION + ROOM_OBJECT_FOLDER;
	/**
	 * Monster type pog.
	 */
	public static final String POG_TYPE_MONSTER = "MONSTER";
	/**
	 * Player type pog.
	 */
	public static final String POG_TYPE_PLAYER = "PLAYER";
	/**
	 * Room Object type pog.
	 */
	public static final String POG_TYPE_ROOMOBJECT = "ROOMOBJECT";
	/**
	 * Height of ribbon bar.
	 */
	public static final double RIBBON_BAR_SIZE = 50;
	/**
	 * Location of dungeon information.
	 */
	public static final String SERVER_DUNGEON_DATA_LOCATION = "/" + DUNGEON_DATA_LOCATION;
	/**
	 * Location of dungeon templates.
	 */
	public static final String SERVER_DUNGEONS_LOCATION = "/" + DUNGEONS_LOCATION;
	/**
	 * Location of resources.
	 */
	public static final String SERVER_RESOURCE_LOCATION = "/" + RESOURCE_LOCATION;

	/**
	 * Hidden constructor.
	 */
	private Constants() {

	}

	/**
	 * Generate UUID.
	 * 
	 * @return UUID.
	 */
	public static native String generateUUID() /*-{
		var d = new Date().getTime();
		if (typeof performance !== 'undefined'
				&& typeof performance.now === 'function') {
			d += performance.now(); //use high-precision timer if available
		}
		return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g,
				function(c) {
					var r = (d + Math.random() * 16) % 16 | 0;
					d = Math.floor(d / 16);
					return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
				});
	}-*/;
}
