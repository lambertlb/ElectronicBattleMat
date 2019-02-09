package per.lambert.ebattleMat.client.interfaces;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Enumeration of Dungeon master flags. This is a bit mask meaning every item is a power of two.
 * 
 * @author LLambert
 *
 */
public enum DungeonMasterFlag {
	/**
	 * This pog is invisible to players.
	 */
	INVISIBLE_FROM_PLAYER("Invis to Player"),
	/**
	 * The background is transparent.
	 */
	TRANSPARENT_BACKGROUND("Transparent Background"),
	/**
	 * The pog is shifted a half cell to right.
	 */
	SHIFT_RIGHT("Shifted to Right"),
	/**
	 * The pog is shifted half a cell to the top.
	 */
	SHIFT_TOP("Shifted to Top"),
	/**
	 * Pog has dark background in edit mode.
	 */
	DARK_BACKGROUND("Dark background in edit mode");

	/**
	 * value of flag.
	 */
	private int value;
	/**
	 * Name of value.
	 */
	private String name;
	/**
	 * Map for names vs flag.
	 */
	private static Map<String, DungeonMasterFlag> flagMap;
	/**
	 * Next value for enum.
	 */
	private static int nextValue = 1;

	/**
	 * Constructor for dungeon master flags.
	 * @param flagName flag name
	 */
	DungeonMasterFlag(final String flagName) {
		setPogFlag(flagName);
	}
	/**
	 * Set enumeration data.
	 * @param flagName flag name
	 */
	private void setPogFlag(final String flagName) {
		value = DungeonMasterFlag.nextValue;
		DungeonMasterFlag.nextValue <<= 1;
		name = flagName;
		if (flagMap == null) {
			flagMap = new LinkedHashMap<String, DungeonMasterFlag>();
		}
		DungeonMasterFlag.flagMap.put(flagName, this);
	}
	/**
	 * Get value of enumeration.
	 * @return enumeration value
	 */
	public int getValue() {
		return (value);
	}
	/**
	 * Get enumeration name.
	 * @return enumeration name.
	 */
	public String getName() {
		return (name);
	}
	/**
	 * Get collection of enumeration values.
	 * @return collection of enumeration values.
	 */
	public static Collection<DungeonMasterFlag> getValues() {
		return (flagMap.values());
	}
}
