package per.lambert.ebattleMat.client.interfaces;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public enum DungeonMasterFlag {
	INVISIBLE_FROM_PLAYER("Invis to Player"),
	TRANSPARENT_BACKGROUND("Transparent Background"),
	SHIFT_RIGHT("Shifted to Right"),
	SHIFT_TOP("Shifted to Top");

	private int value;
	private String name;

	private static Map<String, DungeonMasterFlag> flagMap;
	private static int nextValue = 1;

	DungeonMasterFlag(String flagName) {
		setPogFlag(flagName);
	}

	private void setPogFlag(String flagName) {
		value = DungeonMasterFlag.nextValue;
		DungeonMasterFlag.nextValue <<= 1;
		name = flagName;
		if (flagMap == null ) {
			flagMap = new LinkedHashMap<String, DungeonMasterFlag>();
		}
		DungeonMasterFlag.flagMap.put(flagName, this);
	}

	public int getValue() {
		return (value);
	}

	public String getName() {
		return (name);
	}

	public static Collection<DungeonMasterFlag> getValues() {
		return (flagMap.values());
	}
}
