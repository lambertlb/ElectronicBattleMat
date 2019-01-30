package per.lambert.ebattleMat.client.interfaces;

/**
 * Defines values for all errors that can occur in the ZFS application.
 *
 */
public enum DungeonServerError {
	/**
	 * Generic Error.
	 */
	GenericError(-1),
	/**
	 * Success so no error.
	 */
	Succsess(0),
	/**
	 * Undefined error.
	 */
	Undefined1(1);
	/**
	 * value of enumeration.
	 */
	private int value;

	/**
	 * Constructor for dungeon errors.
	 * 
	 * @param value of enumeration
	 */
	DungeonServerError(final int value) {
		this.value = value;
	}

	/**
	 * Get value of enumeration.
	 * 
	 * @return value of enumeration.
	 */
	public int getErrorValue() {
		return value;
	}

	/**
	 * Get enumeration value for this integer.
	 * 
	 * @param value to lookup
	 * @return error for integer
	 */
	public static DungeonServerError fromInt(final int value) {
		for (DungeonServerError error : DungeonServerError.values()) {
			if (error.getErrorValue() == value) {
				return error;
			}
		}
		return DungeonServerError.GenericError;
	}
}
