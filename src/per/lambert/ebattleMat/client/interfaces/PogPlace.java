package per.lambert.ebattleMat.client.interfaces;

/**
 * Enumeration of where Pogs can reside on server.
 * 
 * @author LLambert
 *
 */
public enum PogPlace {
	/**
	 * Resides in Common resource available to all dungeons.
	 */
	COMMON_RESOURCE,
	/**
	 * Resides in resource area.
	 */
	DUNGEON_RESOURCE,
	/**
	 * Resides in dungeon template area.
	 */
	DUNGEON_INSTANCE,
	/**
	 * Resides in Common Session data area.
	 */
	SESSION_RESOURCE,
	/**
	 * Resides in Session level instance area.
	 */
	SESSION_INSTANCE
}
