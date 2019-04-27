package per.lambert.ebattleMat.client.interfaces;

/**
 * Enumeration of where Pogs can reside on server.
 * 
 * @author LLambert
 *
 */
public enum PogPlace {
	/**
	 * Resides in Common resource area available to all dungeons.
	 */
	COMMON_RESOURCE,
	/**
	 * Resides in dungeon specific resource area.
	 */
	DUNGEON_RESOURCE,
	/**
	 * Resides in dungeon instance area.
	 */
	DUNGEON_INSTANCE,
	/**
	 * Resides in Session common resource area.
	 */
	SESSION_RESOURCE,
	/**
	 * Resides in Session level instance area.
	 */
	SESSION_INSTANCE,
	/**
	 * Invalid place.
	 */
	INVALID
}
