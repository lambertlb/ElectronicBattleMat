package per.lambert.ebattleMat.client.interfaces;

import java.util.ArrayList;
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
public final class PlayerFlag extends FlagBits {
	/**
	 * Next available value.
	 */
	private static int nextValue = 0;
	/**
	 * No option.
	 */
	public static final PlayerFlag NONE = new PlayerFlag("None"); // 0
	/**
	 * Pog is dead.
	 */
	public static final PlayerFlag DEAD = new PlayerFlag("Dead"); // 1
	/**
	 * Pog is female.
	 */
	public static final PlayerFlag IS_FEMALE = new PlayerFlag("Is Female"); // 2
	/**
	 * Pog has no gender.
	 */
	public static final PlayerFlag HAS_NO_GENDER = new PlayerFlag("Has no gender"); // 4
	/**
	 * Pog is invisible.
	 */
	public static final PlayerFlag INVISIBLE = new PlayerFlag("Invisible"); // 8
	/**
	 * Map of names vs flag bit.
	 */
	private static Map<String, FlagBits> nameMap;

	/**
	 * expose to sub-classes.
	 * 
	 * @return map of names
	 */
	protected static Map<String, FlagBits> getNameMap() {
		return nameMap;
	}

	/**
	 * Value vs flag bit.
	 */
	private static Map<Integer, FlagBits> valueMap;

	/**
	 * Constructor.
	 * 
	 * @param flagName flag name
	 */
	private PlayerFlag(final String flagName) {
		super(flagName, nextValue);
		if (nextValue == 0) {
			nextValue = 1;
		} else {
			nextValue <<= 1;
		}
		if (nameMap == null) {
			nameMap = new LinkedHashMap<String, FlagBits>();
			valueMap = new LinkedHashMap<Integer, FlagBits>();
		}
		nameMap.put(flagName, (FlagBits) this);
		valueMap.put(getValue(), (FlagBits) this);
	}

	/**
	 * Get value for this name.
	 * 
	 * @param name to get
	 * @return flag bit
	 */
	public static FlagBits valueOf(final String name) {
		return (nameMap.get(name));
	}

	/**
	 * Get flag bit for this ordinal.
	 * 
	 * @param ordinal to get
	 * @return flag bit
	 */
	public static FlagBits valueOf(final int ordinal) {
		return (valueMap.get(ordinal));
	}

	/**
	 * Get collection of flags.
	 * 
	 * @return collection of flags.
	 */
	public static Collection<FlagBits> getValues() {
		ArrayList<FlagBits> list = new ArrayList<FlagBits>();
		for (FlagBits flagBit : getNameMap().values()) {
			list.add(flagBit);
		}
		return (list);
	}
}
