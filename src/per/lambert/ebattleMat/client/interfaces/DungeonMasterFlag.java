package per.lambert.ebattleMat.client.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Enumeration of Dungeon master flags. This is a bit mask meaning every item is a power of two.
 * 
 * @author LLambert
 *
 */
public final class DungeonMasterFlag extends FlagBits {
	/**
	 * Next available value.
	 */
	private static int nextValue = 0;
	/**
	 * No flag.
	 */
	public static final DungeonMasterFlag NONE = new DungeonMasterFlag("None"); // 0
	/**
	 * This pog is invisible to players.
	 */
	public static final DungeonMasterFlag INVISIBLE_FROM_PLAYER = new DungeonMasterFlag("Invis to Player"); // 1
	/**
	 * The background is transparent.
	 */
	public static final DungeonMasterFlag TRANSPARENT_BACKGROUND = new DungeonMasterFlag("Transparent Background"); // 2
	/**
	 * The pog is shifted a half cell to right.
	 */
	public static final DungeonMasterFlag SHIFT_RIGHT = new DungeonMasterFlag("Shifted to Right"); // 4
	/**
	 * The pog is shifted half a cell to the top.
	 */
	public static final DungeonMasterFlag SHIFT_TOP = new DungeonMasterFlag("Shifted to Top"); // 8
	/**
	 * Pog has dark background in edit mode.
	 */
	public static final DungeonMasterFlag DARK_BACKGROUND = new DungeonMasterFlag("Dark background in edit mode"); // 16

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
	private DungeonMasterFlag(final String flagName) {
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
		nameMap.put(flagName, (FlagBits)this);
		valueMap.put(getValue(), (FlagBits)this);
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
