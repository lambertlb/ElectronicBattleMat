package per.lambert.ebattleMat.client.interfaces;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Enumeration for player flags.
 * 
 * This is a bit mask so it can have up to 23 options.
 * 
 * @author LLambert
 *
 */
public enum PlayerFlag {
	/**
	 * No option.
	 */
	NONE("None"), // 0
	/**
	 * Pog is dead.
	 */
	DEAD("Dead"), // 1
	/**
	 * Pog is female.
	 */
	IS_FEMALE("Is Female"), // 2
	/**
	 * Pog has no gender.
	 */
	HAS_NO_GENDER("Has no gender"), // 4
	/**
	 * Pog is invisible.
	 */
	INVISIBLE("Invisible"); // 8

	/**
	 * Value of item.
	 */
	private int value;
	/**
	 * Name of item.
	 */
	private String name;

	/**
	 * Map of names vs values.
	 */
	private static Map<String, PlayerFlag> flagMap;
	/**
	 * Next available value.
	 */
	private static int nextValue = 0;

	/**
	 * Constructor.
	 * @param flagName flag name
	 */
	PlayerFlag(final String flagName) {
		setPlayerFlag(flagName);
	}

	/**
	 * set data for player flag.
	 * @param flagName flag name.
	 */
	private void setPlayerFlag(final String flagName) {
		value = PlayerFlag.nextValue;
		if (PlayerFlag.nextValue == 0) {
			PlayerFlag.nextValue = 1;
		} else {
			PlayerFlag.nextValue <<= 1;
		}
		name = flagName;
		if (flagMap == null) {
			flagMap = new LinkedHashMap<String, PlayerFlag>();
		}
		PlayerFlag.flagMap.put(flagName, this);
	}

	/**
	 * Get value of flag.
	 * @return value of flag.
	 */
	public int getValue() {
		return (value);
	}

	/**
	 * Get name for flag.
	 * @return name for flag.
	 */
	public String getName() {
		return (name);
	}

	/**
	 * Get collection of flags.
	 * @return collection of flags.
	 */
	public static Collection<PlayerFlag> getValues() {
		return (flagMap.values());
	}
}
