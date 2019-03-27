package per.lambert.ebattleMat.client.interfaces;

/**
 * Enumeration of reasons for events.
 * @author LLambert
 *
 */
public enum ReasonForAction {
	/**
	 * Login happened.
	 */
	Login,
	/**
	 * Dungeon has been selected and being loaded.
	 */
	DungeonSelected,
	/**
	 * Dungeon data has been loaded.
	 */
	DungeonDataLoaded,
	/**
	 * Dungeon data has been saved.
	 */
	DungeonDataSaved,
	/**
	 * Dungeon has been loaded and is ready to be edited.
	 */
	DungeonDataReadyToEdit,
	/**
	 * Session has been loaded for the first time and is ready to be joined.
	 */
	DungeonDataReadyToJoin,
	/**
	 * A new dungeon was created on server.
	 */
	DungeonDataCreated,
	/**
	 * Dungeon was deleted on server.
	 */
	DungeonDataDeleted,
	/**
	 * Dungeon level selection has changed.
	 */
	DungeonSelectedLevelChanged,
	/**
	 * Monster template pog list has changed.
	 */
	MonsterPogsLoaded,
	/**
	 * Room Object template pog list has changed.
	 */
	RoomObjectPogsLoaded,
	/**
	 * Selected Pog has changed.
	 */
	PogWasSelected,
	/**
	 * Toogle Fog of War has changed.
	 */
	ToggleFowSelected,
	/**
	 * Bubble to lower widgets that a mouse down occurred.
	 */
	MouseDownEventBubble,
	/**
	 * Bubble to lower widgets that a mouse up occurred.
	 */
	MouseUpEventBubble,
	/**
	 * Bubble to lower widgets that a mouse move occurred.
	 */
	MouseMoveEventBubble,
	/**
	 * Received a new list of sessions.
	 */
	SessionListChanged,
	/**
	 * Session data has been saved to server.
	 */
	SessionDataSaved,
	/**
	 * New session data has arrived from server.
	 */
	SessionDataChanged,
	/**
	 * Dungeon master state changed.
	 */
	DMStateChange,
	/**
	 * Last reason used for array sizing.
	 */
	LastReason
}
